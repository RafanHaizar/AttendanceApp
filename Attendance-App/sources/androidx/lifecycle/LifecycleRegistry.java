package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class LifecycleRegistry extends Lifecycle {
    private int mAddingObserverCounter;
    private final boolean mEnforceMainThread;
    private boolean mHandlingEvent;
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    private boolean mNewEventOccurred;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap;
    private ArrayList<Lifecycle.State> mParentStates;
    private Lifecycle.State mState;

    public LifecycleRegistry(LifecycleOwner provider) {
        this(provider, true);
    }

    private LifecycleRegistry(LifecycleOwner provider, boolean enforceMainThread) {
        this.mObserverMap = new FastSafeIterableMap<>();
        this.mAddingObserverCounter = 0;
        this.mHandlingEvent = false;
        this.mNewEventOccurred = false;
        this.mParentStates = new ArrayList<>();
        this.mLifecycleOwner = new WeakReference<>(provider);
        this.mState = Lifecycle.State.INITIALIZED;
        this.mEnforceMainThread = enforceMainThread;
    }

    @Deprecated
    public void markState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("markState");
        setCurrentState(state);
    }

    public void setCurrentState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }

    public void handleLifecycleEvent(Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    private void moveToState(Lifecycle.State next) {
        Lifecycle.State state = this.mState;
        if (state != next) {
            if (state == Lifecycle.State.INITIALIZED && next == Lifecycle.State.DESTROYED) {
                throw new IllegalStateException("no event down from " + this.mState);
            }
            this.mState = next;
            if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
                this.mNewEventOccurred = true;
                return;
            }
            this.mHandlingEvent = true;
            sync();
            this.mHandlingEvent = false;
            if (this.mState == Lifecycle.State.DESTROYED) {
                this.mObserverMap = new FastSafeIterableMap<>();
            }
        }
    }

    private boolean isSynced() {
        if (this.mObserverMap.size() == 0) {
            return true;
        }
        Lifecycle.State eldestObserverState = this.mObserverMap.eldest().getValue().mState;
        Lifecycle.State newestObserverState = this.mObserverMap.newest().getValue().mState;
        if (eldestObserverState == newestObserverState && this.mState == newestObserverState) {
            return true;
        }
        return false;
    }

    private Lifecycle.State calculateTargetState(LifecycleObserver observer) {
        Map.Entry<LifecycleObserver, ObserverWithState> previous = this.mObserverMap.ceil(observer);
        Lifecycle.State parentState = null;
        Lifecycle.State siblingState = previous != null ? previous.getValue().mState : null;
        if (!this.mParentStates.isEmpty()) {
            ArrayList<Lifecycle.State> arrayList = this.mParentStates;
            parentState = arrayList.get(arrayList.size() - 1);
        }
        return min(min(this.mState, siblingState), parentState);
    }

    public void addObserver(LifecycleObserver observer) {
        LifecycleOwner lifecycleOwner;
        enforceMainThreadIfNeeded("addObserver");
        ObserverWithState statefulObserver = new ObserverWithState(observer, this.mState == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED);
        if (this.mObserverMap.putIfAbsent(observer, statefulObserver) == null && (lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get()) != null) {
            boolean isReentrance = this.mAddingObserverCounter != 0 || this.mHandlingEvent;
            Lifecycle.State targetState = calculateTargetState(observer);
            this.mAddingObserverCounter++;
            while (statefulObserver.mState.compareTo(targetState) < 0 && this.mObserverMap.contains(observer)) {
                pushParentState(statefulObserver.mState);
                Lifecycle.Event event = Lifecycle.Event.upFrom(statefulObserver.mState);
                if (event != null) {
                    statefulObserver.dispatchEvent(lifecycleOwner, event);
                    popParentState();
                    targetState = calculateTargetState(observer);
                } else {
                    throw new IllegalStateException("no event up from " + statefulObserver.mState);
                }
            }
            if (!isReentrance) {
                sync();
            }
            this.mAddingObserverCounter--;
        }
    }

    private void popParentState() {
        ArrayList<Lifecycle.State> arrayList = this.mParentStates;
        arrayList.remove(arrayList.size() - 1);
    }

    private void pushParentState(Lifecycle.State state) {
        this.mParentStates.add(state);
    }

    public void removeObserver(LifecycleObserver observer) {
        enforceMainThreadIfNeeded("removeObserver");
        this.mObserverMap.remove(observer);
    }

    public int getObserverCount() {
        enforceMainThreadIfNeeded("getObserverCount");
        return this.mObserverMap.size();
    }

    public Lifecycle.State getCurrentState() {
        return this.mState;
    }

    private void forwardPass(LifecycleOwner lifecycleOwner) {
        Iterator<Map.Entry<LifecycleObserver, ObserverWithState>> ascendingIterator = this.mObserverMap.iteratorWithAdditions();
        while (ascendingIterator.hasNext() && !this.mNewEventOccurred) {
            Map.Entry<LifecycleObserver, ObserverWithState> entry = ascendingIterator.next();
            ObserverWithState observer = entry.getValue();
            while (observer.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                pushParentState(observer.mState);
                Lifecycle.Event event = Lifecycle.Event.upFrom(observer.mState);
                if (event != null) {
                    observer.dispatchEvent(lifecycleOwner, event);
                    popParentState();
                } else {
                    throw new IllegalStateException("no event up from " + observer.mState);
                }
            }
        }
    }

    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator<Map.Entry<LifecycleObserver, ObserverWithState>> descendingIterator = this.mObserverMap.descendingIterator();
        while (descendingIterator.hasNext() && !this.mNewEventOccurred) {
            Map.Entry<LifecycleObserver, ObserverWithState> entry = descendingIterator.next();
            ObserverWithState observer = entry.getValue();
            while (observer.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                Lifecycle.Event event = Lifecycle.Event.downFrom(observer.mState);
                if (event != null) {
                    pushParentState(event.getTargetState());
                    observer.dispatchEvent(lifecycleOwner, event);
                    popParentState();
                } else {
                    throw new IllegalStateException("no event down from " + observer.mState);
                }
            }
        }
    }

    private void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get();
        if (lifecycleOwner != null) {
            while (!isSynced()) {
                this.mNewEventOccurred = false;
                if (this.mState.compareTo(this.mObserverMap.eldest().getValue().mState) < 0) {
                    backwardPass(lifecycleOwner);
                }
                Map.Entry<LifecycleObserver, ObserverWithState> newest = this.mObserverMap.newest();
                if (!this.mNewEventOccurred && newest != null && this.mState.compareTo(newest.getValue().mState) > 0) {
                    forwardPass(lifecycleOwner);
                }
            }
            this.mNewEventOccurred = false;
            return;
        }
        throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
    }

    private void enforceMainThreadIfNeeded(String methodName) {
        if (this.mEnforceMainThread && !ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Method " + methodName + " must be called on the main thread");
        }
    }

    public static LifecycleRegistry createUnsafe(LifecycleOwner owner) {
        return new LifecycleRegistry(owner, false);
    }

    static Lifecycle.State min(Lifecycle.State state1, Lifecycle.State state2) {
        return (state2 == null || state2.compareTo(state1) >= 0) ? state1 : state2;
    }

    static class ObserverWithState {
        LifecycleEventObserver mLifecycleObserver;
        Lifecycle.State mState;

        ObserverWithState(LifecycleObserver observer, Lifecycle.State initialState) {
            this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);
            this.mState = initialState;
        }

        /* access modifiers changed from: package-private */
        public void dispatchEvent(LifecycleOwner owner, Lifecycle.Event event) {
            Lifecycle.State newState = event.getTargetState();
            this.mState = LifecycleRegistry.min(this.mState, newState);
            this.mLifecycleObserver.onStateChanged(owner, event);
            this.mState = newState;
        }
    }
}

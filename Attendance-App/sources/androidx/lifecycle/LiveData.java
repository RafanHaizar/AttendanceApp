package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.util.Iterator;
import java.util.Map;

public abstract class LiveData<T> {
    static final Object NOT_SET = new Object();
    static final int START_VERSION = -1;
    int mActiveCount;
    private boolean mChangingActiveState;
    private volatile Object mData;
    final Object mDataLock;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper> mObservers;
    volatile Object mPendingData;
    private final Runnable mPostValueRunnable;
    private int mVersion;

    public LiveData(T value) {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.mActiveCount = 0;
        this.mPendingData = NOT_SET;
        this.mPostValueRunnable = new Runnable() {
            public void run() {
                Object newValue;
                synchronized (LiveData.this.mDataLock) {
                    newValue = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(newValue);
            }
        };
        this.mData = value;
        this.mVersion = 0;
    }

    public LiveData() {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.mActiveCount = 0;
        Object obj = NOT_SET;
        this.mPendingData = obj;
        this.mPostValueRunnable = new Runnable() {
            public void run() {
                Object newValue;
                synchronized (LiveData.this.mDataLock) {
                    newValue = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(newValue);
            }
        };
        this.mData = obj;
        this.mVersion = -1;
    }

    private void considerNotify(LiveData<T>.ObserverWrapper observer) {
        if (observer.mActive) {
            if (!observer.shouldBeActive()) {
                observer.activeStateChanged(false);
                return;
            }
            int i = observer.mLastVersion;
            int i2 = this.mVersion;
            if (i < i2) {
                observer.mLastVersion = i2;
                observer.mObserver.onChanged(this.mData);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchingValue(LiveData<T>.ObserverWrapper initiator) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (initiator == null) {
                Iterator<Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper>> iterator = this.mObservers.iteratorWithAdditions();
                while (iterator.hasNext()) {
                    considerNotify((ObserverWrapper) iterator.next().getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            } else {
                considerNotify(initiator);
                initiator = null;
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    public void observe(LifecycleOwner owner, Observer<? super T> observer) {
        assertMainThread("observe");
        if (owner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED) {
            LiveData<T>.LifecycleBoundObserver wrapper = new LifecycleBoundObserver(owner, observer);
            LiveData<T>.ObserverWrapper existing = this.mObservers.putIfAbsent(observer, wrapper);
            if (existing != null && !existing.isAttachedTo(owner)) {
                throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
            } else if (existing == null) {
                owner.getLifecycle().addObserver(wrapper);
            }
        }
    }

    public void observeForever(Observer<? super T> observer) {
        assertMainThread("observeForever");
        LiveData<T>.AlwaysActiveObserver wrapper = new AlwaysActiveObserver(observer);
        LiveData<T>.ObserverWrapper existing = this.mObservers.putIfAbsent(observer, wrapper);
        if (existing instanceof LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        } else if (existing == null) {
            wrapper.activeStateChanged(true);
        }
    }

    public void removeObserver(Observer<? super T> observer) {
        assertMainThread("removeObserver");
        LiveData<T>.ObserverWrapper removed = this.mObservers.remove(observer);
        if (removed != null) {
            removed.detachObserver();
            removed.activeStateChanged(false);
        }
    }

    public void removeObservers(LifecycleOwner owner) {
        assertMainThread("removeObservers");
        Iterator<Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper>> it = this.mObservers.iterator();
        while (it.hasNext()) {
            Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper> entry = it.next();
            if (entry.getValue().isAttachedTo(owner)) {
                removeObserver(entry.getKey());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void postValue(T value) {
        boolean postTask;
        synchronized (this.mDataLock) {
            postTask = this.mPendingData == NOT_SET;
            this.mPendingData = value;
        }
        if (postTask) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public void setValue(T value) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = value;
        dispatchingValue((LiveData<T>.ObserverWrapper) null);
    }

    public T getValue() {
        Object data = this.mData;
        if (data != NOT_SET) {
            return data;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getVersion() {
        return this.mVersion;
    }

    /* access modifiers changed from: protected */
    public void onActive() {
    }

    /* access modifiers changed from: protected */
    public void onInactive() {
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    /* access modifiers changed from: package-private */
    public void changeActiveCounter(int change) {
        int previousActiveCount = this.mActiveCount;
        this.mActiveCount += change;
        if (!this.mChangingActiveState) {
            this.mChangingActiveState = true;
            while (true) {
                try {
                    int i = this.mActiveCount;
                    if (previousActiveCount != i) {
                        boolean needToCallActive = previousActiveCount == 0 && i > 0;
                        boolean needToCallInactive = previousActiveCount > 0 && i == 0;
                        previousActiveCount = i;
                        if (needToCallActive) {
                            onActive();
                        } else if (needToCallInactive) {
                            onInactive();
                        }
                    } else {
                        return;
                    }
                } finally {
                    this.mChangingActiveState = false;
                }
            }
        }
    }

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements LifecycleEventObserver {
        final LifecycleOwner mOwner;

        LifecycleBoundObserver(LifecycleOwner owner, Observer<? super T> observer) {
            super(observer);
            this.mOwner = owner;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            Lifecycle.State currentState = this.mOwner.getLifecycle().getCurrentState();
            if (currentState == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
                return;
            }
            Lifecycle.State prevState = null;
            while (prevState != currentState) {
                prevState = currentState;
                activeStateChanged(shouldBeActive());
                currentState = this.mOwner.getLifecycle().getCurrentState();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isAttachedTo(LifecycleOwner owner) {
            return this.mOwner == owner;
        }

        /* access modifiers changed from: package-private */
        public void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }
    }

    private abstract class ObserverWrapper {
        boolean mActive;
        int mLastVersion = -1;
        final Observer<? super T> mObserver;

        /* access modifiers changed from: package-private */
        public abstract boolean shouldBeActive();

        ObserverWrapper(Observer<? super T> observer) {
            this.mObserver = observer;
        }

        /* access modifiers changed from: package-private */
        public boolean isAttachedTo(LifecycleOwner owner) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public void detachObserver() {
        }

        /* access modifiers changed from: package-private */
        public void activeStateChanged(boolean newActive) {
            if (newActive != this.mActive) {
                this.mActive = newActive;
                LiveData.this.changeActiveCounter(newActive ? 1 : -1);
                if (this.mActive) {
                    LiveData.this.dispatchingValue(this);
                }
            }
        }
    }

    private class AlwaysActiveObserver extends LiveData<T>.ObserverWrapper {
        AlwaysActiveObserver(Observer<? super T> observer) {
            super(observer);
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeActive() {
            return true;
        }
    }

    static void assertMainThread(String methodName) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background thread");
        }
    }
}

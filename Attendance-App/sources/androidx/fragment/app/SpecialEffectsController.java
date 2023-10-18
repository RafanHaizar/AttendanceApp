package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.p001os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.C0798R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

abstract class SpecialEffectsController {
    private final ViewGroup mContainer;
    boolean mIsContainerPostponed = false;
    boolean mOperationDirectionIsPop = false;
    final ArrayList<Operation> mPendingOperations = new ArrayList<>();
    final ArrayList<Operation> mRunningOperations = new ArrayList<>();

    /* access modifiers changed from: package-private */
    public abstract void executeOperations(List<Operation> list, boolean z);

    static SpecialEffectsController getOrCreateController(ViewGroup container, FragmentManager fragmentManager) {
        return getOrCreateController(container, fragmentManager.getSpecialEffectsControllerFactory());
    }

    static SpecialEffectsController getOrCreateController(ViewGroup container, SpecialEffectsControllerFactory factory) {
        Object controller = container.getTag(C0798R.C0801id.special_effects_controller_view_tag);
        if (controller instanceof SpecialEffectsController) {
            return (SpecialEffectsController) controller;
        }
        SpecialEffectsController newController = factory.createController(container);
        container.setTag(C0798R.C0801id.special_effects_controller_view_tag, newController);
        return newController;
    }

    SpecialEffectsController(ViewGroup container) {
        this.mContainer = container;
    }

    public ViewGroup getContainer() {
        return this.mContainer;
    }

    /* access modifiers changed from: package-private */
    public Operation.LifecycleImpact getAwaitingCompletionLifecycleImpact(FragmentStateManager fragmentStateManager) {
        Operation.LifecycleImpact lifecycleImpact = null;
        Operation pendingOperation = findPendingOperation(fragmentStateManager.getFragment());
        if (pendingOperation != null) {
            lifecycleImpact = pendingOperation.getLifecycleImpact();
        }
        Operation runningOperation = findRunningOperation(fragmentStateManager.getFragment());
        if (runningOperation == null || (lifecycleImpact != null && lifecycleImpact != Operation.LifecycleImpact.NONE)) {
            return lifecycleImpact;
        }
        return runningOperation.getLifecycleImpact();
    }

    private Operation findPendingOperation(Fragment fragment) {
        Iterator<Operation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getFragment().equals(fragment) && !operation.isCanceled()) {
                return operation;
            }
        }
        return null;
    }

    private Operation findRunningOperation(Fragment fragment) {
        Iterator<Operation> it = this.mRunningOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getFragment().equals(fragment) && !operation.isCanceled()) {
                return operation;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void enqueueAdd(Operation.State finalState, FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(finalState, Operation.LifecycleImpact.ADDING, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueShow(FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.VISIBLE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueHide(FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.GONE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueRemove(FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.REMOVED, Operation.LifecycleImpact.REMOVING, fragmentStateManager);
    }

    private void enqueue(Operation.State finalState, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
        synchronized (this.mPendingOperations) {
            CancellationSignal signal = new CancellationSignal();
            Operation existingOperation = findPendingOperation(fragmentStateManager.getFragment());
            if (existingOperation != null) {
                existingOperation.mergeWith(finalState, lifecycleImpact);
                return;
            }
            final FragmentStateManagerOperation operation = new FragmentStateManagerOperation(finalState, lifecycleImpact, fragmentStateManager, signal);
            this.mPendingOperations.add(operation);
            operation.addCompletionListener(new Runnable() {
                public void run() {
                    if (SpecialEffectsController.this.mPendingOperations.contains(operation)) {
                        operation.getFinalState().applyState(operation.getFragment().mView);
                    }
                }
            });
            operation.addCompletionListener(new Runnable() {
                public void run() {
                    SpecialEffectsController.this.mPendingOperations.remove(operation);
                    SpecialEffectsController.this.mRunningOperations.remove(operation);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void updateOperationDirection(boolean isPop) {
        this.mOperationDirectionIsPop = isPop;
    }

    /* access modifiers changed from: package-private */
    public void markPostponedState() {
        synchronized (this.mPendingOperations) {
            updateFinalState();
            this.mIsContainerPostponed = false;
            int index = this.mPendingOperations.size() - 1;
            while (true) {
                if (index < 0) {
                    break;
                }
                Operation operation = this.mPendingOperations.get(index);
                Operation.State currentState = Operation.State.from(operation.getFragment().mView);
                if (operation.getFinalState() == Operation.State.VISIBLE && currentState != Operation.State.VISIBLE) {
                    this.mIsContainerPostponed = operation.getFragment().isPostponed();
                    break;
                }
                index--;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void forcePostponedExecutePendingOperations() {
        if (this.mIsContainerPostponed) {
            this.mIsContainerPostponed = false;
            executePendingOperations();
        }
    }

    /* access modifiers changed from: package-private */
    public void executePendingOperations() {
        if (!this.mIsContainerPostponed) {
            if (!ViewCompat.isAttachedToWindow(this.mContainer)) {
                forceCompleteAllOperations();
                this.mOperationDirectionIsPop = false;
                return;
            }
            synchronized (this.mPendingOperations) {
                if (!this.mPendingOperations.isEmpty()) {
                    ArrayList<Operation> currentlyRunningOperations = new ArrayList<>(this.mRunningOperations);
                    this.mRunningOperations.clear();
                    Iterator<Operation> it = currentlyRunningOperations.iterator();
                    while (it.hasNext()) {
                        Operation operation = it.next();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + operation);
                        }
                        operation.cancel();
                        if (!operation.isComplete()) {
                            this.mRunningOperations.add(operation);
                        }
                    }
                    updateFinalState();
                    ArrayList<Operation> newPendingOperations = new ArrayList<>(this.mPendingOperations);
                    this.mPendingOperations.clear();
                    this.mRunningOperations.addAll(newPendingOperations);
                    Iterator<Operation> it2 = newPendingOperations.iterator();
                    while (it2.hasNext()) {
                        it2.next().onStart();
                    }
                    executeOperations(newPendingOperations, this.mOperationDirectionIsPop);
                    this.mOperationDirectionIsPop = false;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void forceCompleteAllOperations() {
        String str;
        String str2;
        boolean attachedToWindow = ViewCompat.isAttachedToWindow(this.mContainer);
        synchronized (this.mPendingOperations) {
            updateFinalState();
            Iterator<Operation> it = this.mPendingOperations.iterator();
            while (it.hasNext()) {
                it.next().onStart();
            }
            Iterator<Operation> it2 = new ArrayList<>(this.mRunningOperations).iterator();
            while (it2.hasNext()) {
                Operation operation = it2.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder append = new StringBuilder().append("SpecialEffectsController: ");
                    if (attachedToWindow) {
                        str2 = "";
                    } else {
                        str2 = "Container " + this.mContainer + " is not attached to window. ";
                    }
                    Log.v("FragmentManager", append.append(str2).append("Cancelling running operation ").append(operation).toString());
                }
                operation.cancel();
            }
            Iterator<Operation> it3 = new ArrayList<>(this.mPendingOperations).iterator();
            while (it3.hasNext()) {
                Operation operation2 = it3.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder append2 = new StringBuilder().append("SpecialEffectsController: ");
                    if (attachedToWindow) {
                        str = "";
                    } else {
                        str = "Container " + this.mContainer + " is not attached to window. ";
                    }
                    Log.v("FragmentManager", append2.append(str).append("Cancelling pending operation ").append(operation2).toString());
                }
                operation2.cancel();
            }
        }
    }

    private void updateFinalState() {
        Iterator<Operation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                operation.mergeWith(Operation.State.from(operation.getFragment().requireView().getVisibility()), Operation.LifecycleImpact.NONE);
            }
        }
    }

    static class Operation {
        private final List<Runnable> mCompletionListeners = new ArrayList();
        private State mFinalState;
        private final Fragment mFragment;
        private boolean mIsCanceled = false;
        private boolean mIsComplete = false;
        private LifecycleImpact mLifecycleImpact;
        private final HashSet<CancellationSignal> mSpecialEffectsSignals = new HashSet<>();

        enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            static State from(View view) {
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    return INVISIBLE;
                }
                return from(view.getVisibility());
            }

            static State from(int visibility) {
                switch (visibility) {
                    case 0:
                        return VISIBLE;
                    case 4:
                        return INVISIBLE;
                    case 8:
                        return GONE;
                    default:
                        throw new IllegalArgumentException("Unknown visibility " + visibility);
                }
            }

            /* access modifiers changed from: package-private */
            public void applyState(View view) {
                switch (C08753.f1057xe493b431[ordinal()]) {
                    case 1:
                        ViewGroup parent = (ViewGroup) view.getParent();
                        if (parent != null) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Removing view " + view + " from container " + parent);
                            }
                            parent.removeView(view);
                            return;
                        }
                        return;
                    case 2:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to VISIBLE");
                        }
                        view.setVisibility(0);
                        return;
                    case 3:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to GONE");
                        }
                        view.setVisibility(8);
                        return;
                    case 4:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to INVISIBLE");
                        }
                        view.setVisibility(4);
                        return;
                    default:
                        return;
                }
            }
        }

        Operation(State finalState, LifecycleImpact lifecycleImpact, Fragment fragment, CancellationSignal cancellationSignal) {
            this.mFinalState = finalState;
            this.mLifecycleImpact = lifecycleImpact;
            this.mFragment = fragment;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                public void onCancel() {
                    Operation.this.cancel();
                }
            });
        }

        public State getFinalState() {
            return this.mFinalState;
        }

        /* access modifiers changed from: package-private */
        public LifecycleImpact getLifecycleImpact() {
            return this.mLifecycleImpact;
        }

        public final Fragment getFragment() {
            return this.mFragment;
        }

        /* access modifiers changed from: package-private */
        public final boolean isCanceled() {
            return this.mIsCanceled;
        }

        public String toString() {
            return "Operation " + "{" + Integer.toHexString(System.identityHashCode(this)) + "} " + "{" + "mFinalState = " + this.mFinalState + "} " + "{" + "mLifecycleImpact = " + this.mLifecycleImpact + "} " + "{" + "mFragment = " + this.mFragment + "}";
        }

        /* access modifiers changed from: package-private */
        public final void cancel() {
            if (!isCanceled()) {
                this.mIsCanceled = true;
                if (this.mSpecialEffectsSignals.isEmpty()) {
                    complete();
                    return;
                }
                Iterator<CancellationSignal> it = new ArrayList<>(this.mSpecialEffectsSignals).iterator();
                while (it.hasNext()) {
                    it.next().cancel();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void mergeWith(State finalState, LifecycleImpact lifecycleImpact) {
            switch (C08753.f1056xb9e640f0[lifecycleImpact.ordinal()]) {
                case 1:
                    if (this.mFinalState == State.REMOVED) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.mLifecycleImpact + " to ADDING.");
                        }
                        this.mFinalState = State.VISIBLE;
                        this.mLifecycleImpact = LifecycleImpact.ADDING;
                        return;
                    }
                    return;
                case 2:
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> REMOVED. mLifecycleImpact  = " + this.mLifecycleImpact + " to REMOVING.");
                    }
                    this.mFinalState = State.REMOVED;
                    this.mLifecycleImpact = LifecycleImpact.REMOVING;
                    return;
                case 3:
                    if (this.mFinalState != State.REMOVED) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> " + finalState + ". ");
                        }
                        this.mFinalState = finalState;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: package-private */
        public final void addCompletionListener(Runnable listener) {
            this.mCompletionListeners.add(listener);
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
        }

        public final void markStartedSpecialEffect(CancellationSignal signal) {
            onStart();
            this.mSpecialEffectsSignals.add(signal);
        }

        public final void completeSpecialEffect(CancellationSignal signal) {
            if (this.mSpecialEffectsSignals.remove(signal) && this.mSpecialEffectsSignals.isEmpty()) {
                complete();
            }
        }

        /* access modifiers changed from: package-private */
        public final boolean isComplete() {
            return this.mIsComplete;
        }

        public void complete() {
            if (!this.mIsComplete) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
                }
                this.mIsComplete = true;
                for (Runnable listener : this.mCompletionListeners) {
                    listener.run();
                }
            }
        }
    }

    /* renamed from: androidx.fragment.app.SpecialEffectsController$3 */
    static /* synthetic */ class C08753 {

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact */
        static final /* synthetic */ int[] f1056xb9e640f0;

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State */
        static final /* synthetic */ int[] f1057xe493b431;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            f1056xb9e640f0 = iArr;
            try {
                iArr[Operation.LifecycleImpact.ADDING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1056xb9e640f0[Operation.LifecycleImpact.REMOVING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1056xb9e640f0[Operation.LifecycleImpact.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[Operation.State.values().length];
            f1057xe493b431 = iArr2;
            try {
                iArr2[Operation.State.REMOVED.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1057xe493b431[Operation.State.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1057xe493b431[Operation.State.GONE.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1057xe493b431[Operation.State.INVISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private static class FragmentStateManagerOperation extends Operation {
        private final FragmentStateManager mFragmentStateManager;

        FragmentStateManagerOperation(Operation.State finalState, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager, CancellationSignal cancellationSignal) {
            super(finalState, lifecycleImpact, fragmentStateManager.getFragment(), cancellationSignal);
            this.mFragmentStateManager = fragmentStateManager;
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
            if (getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                Fragment fragment = this.mFragmentStateManager.getFragment();
                View focusedView = fragment.mView.findFocus();
                if (focusedView != null) {
                    fragment.setFocusedView(focusedView);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "requestFocus: Saved focused view " + focusedView + " for Fragment " + fragment);
                    }
                }
                View view = getFragment().requireView();
                if (view.getParent() == null) {
                    this.mFragmentStateManager.addViewToContainer();
                    view.setAlpha(0.0f);
                }
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    view.setVisibility(4);
                }
                view.setAlpha(fragment.getPostOnViewCreatedAlpha());
            }
        }

        public void complete() {
            super.complete();
            this.mFragmentStateManager.moveToExpectedState();
        }
    }
}

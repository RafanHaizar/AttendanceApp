package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.collection.ArraySet;
import androidx.core.p001os.CancellationSignal;
import androidx.fragment.C0798R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransition;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager implements FragmentResultOwner {
    private static boolean DEBUG = false;
    private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static final String TAG = "FragmentManager";
    static boolean USE_STATE_MANAGER = true;
    ArrayList<BackStackRecord> mBackStack;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    int mCurState = -1;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() {
        public SpecialEffectsController createController(ViewGroup container) {
            return new DefaultSpecialEffectsController(container);
        }
    };
    private boolean mDestroyed;
    private Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };
    private boolean mExecutingActions;
    private Map<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = Collections.synchronizedMap(new HashMap());
    private FragmentFactory mFragmentFactory = null;
    /* access modifiers changed from: private */
    public final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback() {
        public void onStart(Fragment fragment, CancellationSignal signal) {
            FragmentManager.this.addCancellationSignal(fragment, signal);
        }

        public void onComplete(Fragment f, CancellationSignal signal) {
            if (!signal.isCanceled()) {
                FragmentManager.this.removeCancellationSignal(f, signal);
            }
        }
    };
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() {
        public Fragment instantiate(ClassLoader classLoader, String className) {
            return FragmentManager.this.getHost().instantiate(FragmentManager.this.getHost().getContext(), className, (Bundle) null);
        }
    };
    ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments = new ArrayDeque<>();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
        public void handleOnBackPressed() {
            FragmentManager.this.handleOnBackPressed();
        }
    };
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    /* access modifiers changed from: private */
    public final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
    /* access modifiers changed from: private */
    public final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;

    public interface BackStackEntry {
        @Deprecated
        CharSequence getBreadCrumbShortTitle();

        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Deprecated
        CharSequence getBreadCrumbTitle();

        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        String getName();
    }

    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    public static void enableNewStateManager(boolean enabled) {
        USE_STATE_MANAGER = enabled;
    }

    @Deprecated
    public static void enableDebugLogging(boolean enabled) {
        DEBUG = enabled;
    }

    static boolean isLoggingEnabled(int level) {
        return DEBUG || Log.isLoggable(TAG, level);
    }

    private static class LifecycleAwareResultListener implements FragmentResultListener {
        private final Lifecycle mLifecycle;
        private final FragmentResultListener mListener;
        private final LifecycleEventObserver mObserver;

        LifecycleAwareResultListener(Lifecycle lifecycle, FragmentResultListener listener, LifecycleEventObserver observer) {
            this.mLifecycle = lifecycle;
            this.mListener = listener;
            this.mObserver = observer;
        }

        public boolean isAtLeast(Lifecycle.State state) {
            return this.mLifecycle.getCurrentState().isAtLeast(state);
        }

        public void onFragmentResult(String requestKey, Bundle result) {
            this.mListener.onFragmentResult(requestKey, result);
        }

        public void removeObserver() {
            this.mLifecycle.removeObserver(this.mObserver);
        }
    }

    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        }

        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        }

        public void onFragmentPreCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        }

        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        }

        @Deprecated
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        }

        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        }

        public void onFragmentStarted(FragmentManager fm, Fragment f) {
        }

        public void onFragmentResumed(FragmentManager fm, Fragment f) {
        }

        public void onFragmentPaused(FragmentManager fm, Fragment f) {
        }

        public void onFragmentStopped(FragmentManager fm, Fragment f) {
        }

        public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        }

        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        }

        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        }

        public void onFragmentDetached(FragmentManager fm, Fragment f) {
        }
    }

    private void throwException(RuntimeException ex) {
        Log.e(TAG, ex.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter pw = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", (FileDescriptor) null, pw, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", (FileDescriptor) null, pw, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw ex;
    }

    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean updates = execPendingActions(true);
        forcePostponedTransactions();
        return updates;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (getBackStackEntryCount() <= 0) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        if (isPrimaryNavigation(r3.mParent) == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        r0.setEnabled(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r0 = r3.mOnBackPressedCallback;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateOnBackPressedCallbackEnabled() {
        /*
            r3 = this;
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r0 = r3.mPendingActions
            monitor-enter(r0)
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r1 = r3.mPendingActions     // Catch:{ all -> 0x002a }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x002a }
            r2 = 1
            if (r1 != 0) goto L_0x0013
            androidx.activity.OnBackPressedCallback r1 = r3.mOnBackPressedCallback     // Catch:{ all -> 0x002a }
            r1.setEnabled(r2)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            androidx.activity.OnBackPressedCallback r0 = r3.mOnBackPressedCallback
            int r1 = r3.getBackStackEntryCount()
            if (r1 <= 0) goto L_0x0025
            androidx.fragment.app.Fragment r1 = r3.mParent
            boolean r1 = r3.isPrimaryNavigation(r1)
            if (r1 == 0) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r2 = 0
        L_0x0026:
            r0.setEnabled(r2)
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.updateOnBackPressedCallbackEnabled():void");
    }

    /* access modifiers changed from: package-private */
    public boolean isPrimaryNavigation(Fragment parent) {
        if (parent == null) {
            return true;
        }
        FragmentManager parentFragmentManager = parent.mFragmentManager;
        if (!parent.equals(parentFragmentManager.getPrimaryNavigationFragment()) || !isPrimaryNavigation(parentFragmentManager.mParent)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean isParentMenuVisible(Fragment parent) {
        if (parent == null) {
            return true;
        }
        return parent.isMenuVisible();
    }

    /* access modifiers changed from: package-private */
    public void handleOnBackPressed() {
        execPendingActions(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState((String) null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        return popBackStackImmediate((String) null, -1, 0);
    }

    public void popBackStack(String name, int flags) {
        enqueueAction(new PopBackStackState(name, -1, flags), false);
    }

    public boolean popBackStackImmediate(String name, int flags) {
        return popBackStackImmediate(name, -1, flags);
    }

    public void popBackStack(int id, int flags) {
        if (id >= 0) {
            enqueueAction(new PopBackStackState((String) null, id, flags), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    public boolean popBackStackImmediate(int id, int flags) {
        if (id >= 0) {
            return popBackStackImmediate((String) null, id, flags);
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    private boolean popBackStackImmediate(String name, int id, int flags) {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && id < 0 && name == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean executePop = popBackStackState(this.mTmpRecords, this.mTmpIsPop, name, id, flags);
        if (executePop) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return executePop;
    }

    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public BackStackEntry getBackStackEntryAt(int index) {
        return this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void addCancellationSignal(Fragment f, CancellationSignal signal) {
        if (this.mExitAnimationCancellationSignals.get(f) == null) {
            this.mExitAnimationCancellationSignals.put(f, new HashSet());
        }
        this.mExitAnimationCancellationSignals.get(f).add(signal);
    }

    /* access modifiers changed from: package-private */
    public void removeCancellationSignal(Fragment f, CancellationSignal signal) {
        HashSet<CancellationSignal> signals = this.mExitAnimationCancellationSignals.get(f);
        if (signals != null && signals.remove(signal) && signals.isEmpty()) {
            this.mExitAnimationCancellationSignals.remove(f);
            if (f.mState < 5) {
                destroyFragmentView(f);
                moveToState(f);
            }
        }
    }

    public final void setFragmentResult(String requestKey, Bundle result) {
        LifecycleAwareResultListener resultListener = this.mResultListeners.get(requestKey);
        if (resultListener == null || !resultListener.isAtLeast(Lifecycle.State.STARTED)) {
            this.mResults.put(requestKey, result);
        } else {
            resultListener.onFragmentResult(requestKey, result);
        }
    }

    public final void clearFragmentResult(String requestKey) {
        this.mResults.remove(requestKey);
    }

    public final void setFragmentResultListener(final String requestKey, LifecycleOwner lifecycleOwner, final FragmentResultListener listener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            LifecycleEventObserver observer = new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    Bundle storedResult;
                    if (event == Lifecycle.Event.ON_START && (storedResult = (Bundle) FragmentManager.this.mResults.get(requestKey)) != null) {
                        listener.onFragmentResult(requestKey, storedResult);
                        FragmentManager.this.clearFragmentResult(requestKey);
                    }
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        lifecycle.removeObserver(this);
                        FragmentManager.this.mResultListeners.remove(requestKey);
                    }
                }
            };
            lifecycle.addObserver(observer);
            LifecycleAwareResultListener storedListener = this.mResultListeners.put(requestKey, new LifecycleAwareResultListener(lifecycle, listener, observer));
            if (storedListener != null) {
                storedListener.removeObserver();
            }
        }
    }

    public final void clearFragmentResultListener(String requestKey) {
        LifecycleAwareResultListener listener = this.mResultListeners.remove(requestKey);
        if (listener != null) {
            listener.removeObserver();
        }
    }

    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(key, fragment.mWho);
    }

    public Fragment getFragment(Bundle bundle, String key) {
        String who = bundle.getString(key);
        if (who == null) {
            return null;
        }
        Fragment f = findActiveFragment(who);
        if (f == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + key + ": unique id " + who));
        }
        return f;
    }

    public static <F extends Fragment> F findFragment(View view) {
        Fragment fragment = findViewFragment(view);
        if (fragment != null) {
            return fragment;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.fragment.app.Fragment findViewFragment(android.view.View r4) {
        /*
        L_0x0001:
            r0 = 0
            if (r4 == 0) goto L_0x0018
            androidx.fragment.app.Fragment r1 = getViewFragment(r4)
            if (r1 == 0) goto L_0x000b
            return r1
        L_0x000b:
            android.view.ViewParent r2 = r4.getParent()
            boolean r3 = r2 instanceof android.view.View
            if (r3 == 0) goto L_0x0016
            r0 = r2
            android.view.View r0 = (android.view.View) r0
        L_0x0016:
            r4 = r0
            goto L_0x0001
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.findViewFragment(android.view.View):androidx.fragment.app.Fragment");
    }

    static Fragment getViewFragment(View view) {
        Object tag = view.getTag(C0798R.C0801id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onContainerAvailable(FragmentContainerView container) {
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            Fragment fragment = fragmentStateManager.getFragment();
            if (fragment.mContainerId == container.getId() && fragment.mView != null && fragment.mView.getParent() == null) {
                fragment.mContainer = container;
                fragmentStateManager.addViewToContainer();
            }
        }
    }

    static FragmentManager findFragmentManager(View view) {
        Fragment fragment = findViewFragment(view);
        if (fragment == null) {
            Context context = view.getContext();
            FragmentActivity fragmentActivity = null;
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    break;
                } else if (context instanceof FragmentActivity) {
                    fragmentActivity = (FragmentActivity) context;
                    break;
                } else {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (fragmentActivity != null) {
                return fragmentActivity.getSupportFragmentManager();
            }
            throw new IllegalStateException("View " + view + " is not within a subclass of FragmentActivity.");
        } else if (fragment.isAdded()) {
            return fragment.getChildFragmentManager();
        } else {
            throw new IllegalStateException("The Fragment " + fragment + " that owns View " + view + " has already been destroyed. Nested fragments should always use the child FragmentManager.");
        }
    }

    public List<Fragment> getFragments() {
        return this.mFragmentStore.getFragments();
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore getViewModelStore(Fragment f) {
        return this.mNonConfig.getViewModelStore(f);
    }

    private FragmentManagerViewModel getChildNonConfig(Fragment f) {
        return this.mNonConfig.getChildNonConfig(f);
    }

    /* access modifiers changed from: package-private */
    public void addRetainedFragment(Fragment f) {
        this.mNonConfig.addRetainedFragment(f);
    }

    /* access modifiers changed from: package-private */
    public void removeRetainedFragment(Fragment f) {
        this.mNonConfig.removeRetainedFragment(f);
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> getActiveFragments() {
        return this.mFragmentStore.getActiveFragments();
    }

    /* access modifiers changed from: package-private */
    public int getActiveFragmentCount() {
        return this.mFragmentStore.getActiveFragmentCount();
    }

    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager == null || !fragmentStateManager.getFragment().equals(fragment)) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return fragmentStateManager.saveInstanceState();
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        int count;
        int count2;
        String innerPrefix = prefix + "    ";
        this.mFragmentStore.dump(prefix, fd, writer, args);
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (count2 = arrayList.size()) > 0) {
            writer.print(prefix);
            writer.println("Fragments Created Menus:");
            for (int i = 0; i < count2; i++) {
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(": ");
                writer.println(this.mCreatedMenus.get(i).toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (count = arrayList2.size()) > 0) {
            writer.print(prefix);
            writer.println("Back Stack:");
            for (int i2 = 0; i2 < count; i2++) {
                BackStackRecord bs = this.mBackStack.get(i2);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i2);
                writer.print(": ");
                writer.println(bs.toString());
                bs.dump(innerPrefix, writer);
            }
        }
        writer.print(prefix);
        writer.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int count3 = this.mPendingActions.size();
            if (count3 > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                for (int i3 = 0; i3 < count3; i3++) {
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i3);
                    writer.print(": ");
                    writer.println(this.mPendingActions.get(i3));
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mStopped=");
        writer.print(this.mStopped);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
    }

    /* access modifiers changed from: package-private */
    public void performPendingDeferredStart(FragmentStateManager fragmentStateManager) {
        Fragment f = fragmentStateManager.getFragment();
        if (!f.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        f.mDeferStart = false;
        if (USE_STATE_MANAGER) {
            fragmentStateManager.moveToExpectedState();
        } else {
            moveToState(f);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isStateAtLeast(int state) {
        return this.mCurState >= state;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        if (r12 <= 0) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        r0.create();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        if (r12 <= -1) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        r0.ensureInflatedView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        if (r12 <= 1) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0061, code lost:
        r0.createView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0064, code lost:
        if (r12 <= 2) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        r0.activityCreated();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        if (r12 <= 4) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006b, code lost:
        r0.start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
        if (r12 <= 5) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0070, code lost:
        r0.resume();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0086, code lost:
        if (r12 >= 5) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0088, code lost:
        r0.stop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x008b, code lost:
        if (r12 >= 4) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0091, code lost:
        if (isLoggingEnabled(3) == false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0093, code lost:
        android.util.Log.d(TAG, "movefrom ACTIVITY_CREATED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ac, code lost:
        if (r11.mView == null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b4, code lost:
        if (r10.mHost.onShouldSaveFragmentState(r11) == false) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b8, code lost:
        if (r11.mSavedViewState != null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ba, code lost:
        r0.saveViewState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00bd, code lost:
        if (r12 >= 2) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00bf, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00c2, code lost:
        if (r11.mView == null) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c6, code lost:
        if (r11.mContainer == null) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00c8, code lost:
        r11.mContainer.endViewTransition(r11.mView);
        r11.mView.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00d8, code lost:
        if (r11.isRemovingParent() != false) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00dd, code lost:
        if (r10.mCurState <= -1) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e1, code lost:
        if (r10.mDestroyed != false) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00e9, code lost:
        if (r11.mView.getVisibility() != 0) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00ef, code lost:
        if (r11.mPostponedAlpha < 0.0f) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00f1, code lost:
        r2 = androidx.fragment.app.FragmentAnim.loadAnimation(r10.mHost.getContext(), r11, false, r11.getPopDirection());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0100, code lost:
        r11.mPostponedAlpha = 0.0f;
        r4 = r11.mContainer;
        r5 = r11.mView;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0106, code lost:
        if (r2 == null) goto L_0x010d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0108, code lost:
        androidx.fragment.app.FragmentAnim.animateRemoveFragment(r11, r2, r10.mFragmentTransitionCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x010d, code lost:
        r4.removeView(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0114, code lost:
        if (isLoggingEnabled(2) == false) goto L_0x0140;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0116, code lost:
        android.util.Log.v(TAG, "Removing view " + r5 + " for fragment " + r11 + " from container " + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0142, code lost:
        if (r4 == r11.mContainer) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0144, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x014b, code lost:
        if (r10.mExitAnimationCancellationSignals.get(r11) != null) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x014d, code lost:
        r0.destroyFragmentView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0150, code lost:
        if (r12 >= 1) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0158, code lost:
        if (r10.mExitAnimationCancellationSignals.get(r11) == null) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x015a, code lost:
        r12 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x015c, code lost:
        r0.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x015f, code lost:
        if (r12 >= 0) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0161, code lost:
        r0.detach();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(androidx.fragment.app.Fragment r11, int r12) {
        /*
            r10 = this;
            androidx.fragment.app.FragmentStore r0 = r10.mFragmentStore
            java.lang.String r1 = r11.mWho
            androidx.fragment.app.FragmentStateManager r0 = r0.getFragmentStateManager(r1)
            r1 = 1
            if (r0 != 0) goto L_0x0018
            androidx.fragment.app.FragmentStateManager r2 = new androidx.fragment.app.FragmentStateManager
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r3 = r10.mLifecycleCallbacksDispatcher
            androidx.fragment.app.FragmentStore r4 = r10.mFragmentStore
            r2.<init>(r3, r4, r11)
            r0 = r2
            r0.setFragmentManagerState(r1)
        L_0x0018:
            boolean r2 = r11.mFromLayout
            r3 = 2
            if (r2 == 0) goto L_0x0029
            boolean r2 = r11.mInLayout
            if (r2 == 0) goto L_0x0029
            int r2 = r11.mState
            if (r2 != r3) goto L_0x0029
            int r12 = java.lang.Math.max(r12, r3)
        L_0x0029:
            int r2 = r0.computeExpectedState()
            int r12 = java.lang.Math.min(r12, r2)
            int r2 = r11.mState
            r4 = 5
            r5 = 4
            r6 = 3
            java.lang.String r7 = "FragmentManager"
            r8 = -1
            if (r2 > r12) goto L_0x0075
            int r2 = r11.mState
            if (r2 >= r12) goto L_0x004a
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r2 = r10.mExitAnimationCancellationSignals
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x004a
            r10.cancelExitAnimation(r11)
        L_0x004a:
            int r2 = r11.mState
            switch(r2) {
                case -1: goto L_0x0050;
                case 0: goto L_0x0055;
                case 1: goto L_0x005a;
                case 2: goto L_0x0064;
                case 3: goto L_0x004f;
                case 4: goto L_0x0069;
                case 5: goto L_0x006e;
                default: goto L_0x004f;
            }
        L_0x004f:
            goto L_0x0073
        L_0x0050:
            if (r12 <= r8) goto L_0x0055
            r0.attach()
        L_0x0055:
            if (r12 <= 0) goto L_0x005a
            r0.create()
        L_0x005a:
            if (r12 <= r8) goto L_0x005f
            r0.ensureInflatedView()
        L_0x005f:
            if (r12 <= r1) goto L_0x0064
            r0.createView()
        L_0x0064:
            if (r12 <= r3) goto L_0x0069
            r0.activityCreated()
        L_0x0069:
            if (r12 <= r5) goto L_0x006e
            r0.start()
        L_0x006e:
            if (r12 <= r4) goto L_0x0073
            r0.resume()
        L_0x0073:
            goto L_0x0164
        L_0x0075:
            int r2 = r11.mState
            if (r2 <= r12) goto L_0x0164
            int r2 = r11.mState
            switch(r2) {
                case 0: goto L_0x015f;
                case 1: goto L_0x0150;
                case 2: goto L_0x00bd;
                case 3: goto L_0x007e;
                case 4: goto L_0x008b;
                case 5: goto L_0x0086;
                case 6: goto L_0x007e;
                case 7: goto L_0x0080;
                default: goto L_0x007e;
            }
        L_0x007e:
            goto L_0x0164
        L_0x0080:
            r2 = 7
            if (r12 >= r2) goto L_0x0086
            r0.pause()
        L_0x0086:
            if (r12 >= r4) goto L_0x008b
            r0.stop()
        L_0x008b:
            if (r12 >= r5) goto L_0x00bd
            boolean r2 = isLoggingEnabled(r6)
            if (r2 == 0) goto L_0x00aa
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "movefrom ACTIVITY_CREATED: "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r7, r2)
        L_0x00aa:
            android.view.View r2 = r11.mView
            if (r2 == 0) goto L_0x00bd
            androidx.fragment.app.FragmentHostCallback<?> r2 = r10.mHost
            boolean r2 = r2.onShouldSaveFragmentState(r11)
            if (r2 == 0) goto L_0x00bd
            android.util.SparseArray<android.os.Parcelable> r2 = r11.mSavedViewState
            if (r2 != 0) goto L_0x00bd
            r0.saveViewState()
        L_0x00bd:
            if (r12 >= r3) goto L_0x0150
            r2 = 0
            android.view.View r4 = r11.mView
            if (r4 == 0) goto L_0x0145
            android.view.ViewGroup r4 = r11.mContainer
            if (r4 == 0) goto L_0x0145
            android.view.ViewGroup r4 = r11.mContainer
            android.view.View r5 = r11.mView
            r4.endViewTransition(r5)
            android.view.View r4 = r11.mView
            r4.clearAnimation()
            boolean r4 = r11.isRemovingParent()
            if (r4 != 0) goto L_0x0145
            int r4 = r10.mCurState
            r5 = 0
            if (r4 <= r8) goto L_0x0100
            boolean r4 = r10.mDestroyed
            if (r4 != 0) goto L_0x0100
            android.view.View r4 = r11.mView
            int r4 = r4.getVisibility()
            if (r4 != 0) goto L_0x0100
            float r4 = r11.mPostponedAlpha
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 < 0) goto L_0x0100
            androidx.fragment.app.FragmentHostCallback<?> r4 = r10.mHost
            android.content.Context r4 = r4.getContext()
            boolean r8 = r11.getPopDirection()
            r9 = 0
            androidx.fragment.app.FragmentAnim$AnimationOrAnimator r2 = androidx.fragment.app.FragmentAnim.loadAnimation(r4, r11, r9, r8)
        L_0x0100:
            r11.mPostponedAlpha = r5
            android.view.ViewGroup r4 = r11.mContainer
            android.view.View r5 = r11.mView
            if (r2 == 0) goto L_0x010d
            androidx.fragment.app.FragmentTransition$Callback r8 = r10.mFragmentTransitionCallback
            androidx.fragment.app.FragmentAnim.animateRemoveFragment(r11, r2, r8)
        L_0x010d:
            r4.removeView(r5)
            boolean r3 = isLoggingEnabled(r3)
            if (r3 == 0) goto L_0x0140
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "Removing view "
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r8 = " for fragment "
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.StringBuilder r3 = r3.append(r11)
            java.lang.String r8 = " from container "
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r7, r3)
        L_0x0140:
            android.view.ViewGroup r3 = r11.mContainer
            if (r4 == r3) goto L_0x0145
            return
        L_0x0145:
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r3 = r10.mExitAnimationCancellationSignals
            java.lang.Object r3 = r3.get(r11)
            if (r3 != 0) goto L_0x0150
            r0.destroyFragmentView()
        L_0x0150:
            if (r12 >= r1) goto L_0x015f
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r1 = r10.mExitAnimationCancellationSignals
            java.lang.Object r1 = r1.get(r11)
            if (r1 == 0) goto L_0x015c
            r12 = 1
            goto L_0x015f
        L_0x015c:
            r0.destroy()
        L_0x015f:
            if (r12 >= 0) goto L_0x0164
            r0.detach()
        L_0x0164:
            int r1 = r11.mState
            if (r1 == r12) goto L_0x019d
            boolean r1 = isLoggingEnabled(r6)
            if (r1 == 0) goto L_0x019b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveToState: Fragment state for "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = " not updated inline; expected state "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r2 = " found "
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r11.mState
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r7, r1)
        L_0x019b:
            r11.mState = r12
        L_0x019d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.moveToState(androidx.fragment.app.Fragment, int):void");
    }

    private void cancelExitAnimation(Fragment f) {
        HashSet<CancellationSignal> signals = this.mExitAnimationCancellationSignals.get(f);
        if (signals != null) {
            Iterator<CancellationSignal> it = signals.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            signals.clear();
            destroyFragmentView(f);
            this.mExitAnimationCancellationSignals.remove(f);
        }
    }

    /* access modifiers changed from: package-private */
    public void setExitAnimationOrder(Fragment f, boolean isPop) {
        ViewGroup container = getFragmentContainer(f);
        if (container != null && (container instanceof FragmentContainerView)) {
            ((FragmentContainerView) container).setDrawDisappearingViewsLast(!isPop);
        }
    }

    private void destroyFragmentView(Fragment fragment) {
        fragment.performDestroyView();
        this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(fragment, false);
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        fragment.mInLayout = false;
    }

    /* access modifiers changed from: package-private */
    public void moveToState(Fragment f) {
        moveToState(f, this.mCurState);
    }

    private void completeShowHideFragment(final Fragment fragment) {
        int visibility;
        if (fragment.mView != null) {
            FragmentAnim.AnimationOrAnimator anim = FragmentAnim.loadAnimation(this.mHost.getContext(), fragment, !fragment.mHidden, fragment.getPopDirection());
            if (anim == null || anim.animator == null) {
                if (anim != null) {
                    fragment.mView.startAnimation(anim.animation);
                    anim.animation.start();
                }
                if (!fragment.mHidden || fragment.isHideReplaced()) {
                    visibility = 0;
                } else {
                    visibility = 8;
                }
                fragment.mView.setVisibility(visibility);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            } else {
                anim.animator.setTarget(fragment.mView);
                if (!fragment.mHidden) {
                    fragment.mView.setVisibility(0);
                } else if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                } else {
                    final ViewGroup container = fragment.mContainer;
                    final View animatingView = fragment.mView;
                    container.startViewTransition(animatingView);
                    anim.animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            container.endViewTransition(animatingView);
                            animation.removeListener(this);
                            if (fragment.mView != null && fragment.mHidden) {
                                fragment.mView.setVisibility(8);
                            }
                        }
                    });
                }
                anim.animator.start();
            }
        }
        invalidateMenuForFragment(fragment);
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /* access modifiers changed from: package-private */
    public void moveFragmentToExpectedState(Fragment f) {
        if (this.mFragmentStore.containsActiveFragment(f.mWho)) {
            moveToState(f);
            if (!(f.mView == null || !f.mIsNewlyAdded || f.mContainer == null)) {
                if (f.mPostponedAlpha > 0.0f) {
                    f.mView.setAlpha(f.mPostponedAlpha);
                }
                f.mPostponedAlpha = 0.0f;
                f.mIsNewlyAdded = false;
                FragmentAnim.AnimationOrAnimator anim = FragmentAnim.loadAnimation(this.mHost.getContext(), f, true, f.getPopDirection());
                if (anim != null) {
                    if (anim.animation != null) {
                        f.mView.startAnimation(anim.animation);
                    } else {
                        anim.animator.setTarget(f.mView);
                        anim.animator.start();
                    }
                }
            }
            if (f.mHiddenChanged) {
                completeShowHideFragment(f);
            }
        } else if (isLoggingEnabled(3)) {
            Log.d(TAG, "Ignoring moving " + f + " to state " + this.mCurState + "since it is not added to " + this);
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToState(int newState, boolean always) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.mHost == null && newState != -1) {
            throw new IllegalStateException("No activity");
        } else if (always || newState != this.mCurState) {
            this.mCurState = newState;
            if (USE_STATE_MANAGER) {
                this.mFragmentStore.moveToExpectedState();
            } else {
                for (Fragment f : this.mFragmentStore.getFragments()) {
                    moveFragmentToExpectedState(f);
                }
                for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
                    Fragment f2 = fragmentStateManager.getFragment();
                    if (!f2.mIsNewlyAdded) {
                        moveFragmentToExpectedState(f2);
                    }
                    if (f2.mRemoving && !f2.isInBackStack()) {
                        this.mFragmentStore.makeInactive(fragmentStateManager);
                    }
                }
            }
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    private void startPendingDeferredFragments() {
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            performPendingDeferredStart(fragmentStateManager);
        }
    }

    /* access modifiers changed from: package-private */
    public FragmentStateManager createOrGetFragmentStateManager(Fragment f) {
        FragmentStateManager existing = this.mFragmentStore.getFragmentStateManager(f.mWho);
        if (existing != null) {
            return existing;
        }
        FragmentStateManager fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, f);
        fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
        fragmentStateManager.setFragmentManagerState(this.mCurState);
        return fragmentStateManager;
    }

    /* access modifiers changed from: package-private */
    public FragmentStateManager addFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "add: " + fragment);
        }
        FragmentStateManager fragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.makeActive(fragmentStateManager);
        if (!fragment.mDetached) {
            this.mFragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return fragmentStateManager;
    }

    /* access modifiers changed from: package-private */
    public void removeFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean inactive = !fragment.isInBackStack();
        if (!fragment.mDetached || inactive) {
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public void hideFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
            setVisibleRemovingFragment(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public void showFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    /* access modifiers changed from: package-private */
    public void detachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "remove from detach: " + fragment);
                }
                this.mFragmentStore.removeFragment(fragment);
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
                setVisibleRemovingFragment(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void attachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                this.mFragmentStore.addFragment(fragment);
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "add from attach: " + fragment);
                }
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    public Fragment findFragmentById(int id) {
        return this.mFragmentStore.findFragmentById(id);
    }

    public Fragment findFragmentByTag(String tag) {
        return this.mFragmentStore.findFragmentByTag(tag);
    }

    /* access modifiers changed from: package-private */
    public Fragment findFragmentByWho(String who) {
        return this.mFragmentStore.findFragmentByWho(who);
    }

    /* access modifiers changed from: package-private */
    public Fragment findActiveFragment(String who) {
        return this.mFragmentStore.findActiveFragment(who);
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    /* access modifiers changed from: package-private */
    public void enqueueAction(OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            if (this.mHost != null) {
                checkStateLoss();
            } else if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
        }
        synchronized (this.mPendingActions) {
            if (this.mHost != null) {
                this.mPendingActions.add(action);
                scheduleCommit();
            } else if (!allowStateLoss) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void scheduleCommit() {
        synchronized (this.mPendingActions) {
            ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
            boolean pendingReady = false;
            boolean postponeReady = arrayList != null && !arrayList.isEmpty();
            if (this.mPendingActions.size() == 1) {
                pendingReady = true;
            }
            if (postponeReady || pendingReady) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int allocBackStackIndex() {
        return this.mBackStackIndex.getAndIncrement();
    }

    private void ensureExecReady(boolean allowStateLoss) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.mHost == null) {
            if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!allowStateLoss) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList<>();
                this.mTmpIsPop = new ArrayList<>();
            }
            this.mExecutingActions = true;
            try {
                executePostponedTransaction((ArrayList<BackStackRecord>) null, (ArrayList<Boolean>) null);
            } finally {
                this.mExecutingActions = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    /* access modifiers changed from: package-private */
    public void execSingleAction(OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss || (this.mHost != null && !this.mDestroyed)) {
            ensureExecReady(allowStateLoss);
            if (action.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
                this.mExecutingActions = true;
                try {
                    removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                } finally {
                    cleanupExec();
                }
            }
            updateOnBackPressedCallbackEnabled();
            doPendingDeferredStart();
            this.mFragmentStore.burpActive();
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public boolean execPendingActions(boolean allowStateLoss) {
        ensureExecReady(allowStateLoss);
        boolean didSomething = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                didSomething = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return didSomething;
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        int index;
        ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
        int numPostponed = arrayList == null ? 0 : arrayList.size();
        int i = 0;
        while (i < numPostponed) {
            StartEnterTransitionListener listener = this.mPostponedTransactions.get(i);
            if (records != null && !listener.mIsBack && (index = records.indexOf(listener.mRecord)) != -1 && isRecordPop != null && isRecordPop.get(index).booleanValue()) {
                this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                listener.cancelTransaction();
            } else if (listener.isReady() != 0 || (records != null && listener.mRecord.interactsWith(records, 0, records.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                if (records != null && !listener.mIsBack) {
                    int indexOf = records.indexOf(listener.mRecord);
                    int index2 = indexOf;
                    if (!(indexOf == -1 || isRecordPop == null || !isRecordPop.get(index2).booleanValue())) {
                        listener.cancelTransaction();
                    }
                }
                listener.completeTransaction();
            }
            i++;
        }
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        if (!records.isEmpty()) {
            if (records.size() == isRecordPop.size()) {
                executePostponedTransaction(records, isRecordPop);
                int numRecords = records.size();
                int startIndex = 0;
                int recordNum = 0;
                while (recordNum < numRecords) {
                    if (!records.get(recordNum).mReorderingAllowed) {
                        if (startIndex != recordNum) {
                            executeOpsTogether(records, isRecordPop, startIndex, recordNum);
                        }
                        int reorderingEnd = recordNum + 1;
                        if (isRecordPop.get(recordNum).booleanValue()) {
                            while (reorderingEnd < numRecords && isRecordPop.get(reorderingEnd).booleanValue() && !records.get(reorderingEnd).mReorderingAllowed) {
                                reorderingEnd++;
                            }
                        }
                        executeOpsTogether(records, isRecordPop, recordNum, reorderingEnd);
                        startIndex = reorderingEnd;
                        recordNum = reorderingEnd - 1;
                    }
                    recordNum++;
                }
                if (startIndex != numRecords) {
                    executeOpsTogether(records, isRecordPop, startIndex, numRecords);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        int i;
        ArrayList<Boolean> arrayList;
        boolean allowReordering;
        int i2;
        int i3;
        int i4;
        ArrayList<Boolean> arrayList2;
        int postponeIndex;
        boolean z;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i5 = startIndex;
        int i6 = endIndex;
        boolean allowReordering2 = arrayList3.get(i5).mReorderingAllowed;
        ArrayList<Fragment> arrayList5 = this.mTmpAddedFragments;
        if (arrayList5 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList5.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
        Fragment oldPrimaryNav = getPrimaryNavigationFragment();
        int recordNum = startIndex;
        boolean addToBackStack = false;
        while (true) {
            boolean addToBackStack2 = true;
            if (recordNum >= i6) {
                break;
            }
            BackStackRecord record = arrayList3.get(recordNum);
            if (!arrayList4.get(recordNum).booleanValue()) {
                oldPrimaryNav = record.expandOps(this.mTmpAddedFragments, oldPrimaryNav);
            } else {
                oldPrimaryNav = record.trackAddedFragmentsInPop(this.mTmpAddedFragments, oldPrimaryNav);
            }
            if (!addToBackStack && !record.mAddToBackStack) {
                addToBackStack2 = false;
            }
            addToBackStack = addToBackStack2;
            recordNum++;
        }
        this.mTmpAddedFragments.clear();
        if (!allowReordering2 && this.mCurState >= 1) {
            if (USE_STATE_MANAGER) {
                for (int index = startIndex; index < i6; index++) {
                    Iterator it = arrayList3.get(index).mOps.iterator();
                    while (it.hasNext()) {
                        Fragment fragment = ((FragmentTransaction.C0856Op) it.next()).mFragment;
                        if (!(fragment == null || fragment.mFragmentManager == null)) {
                            this.mFragmentStore.makeActive(createOrGetFragmentStateManager(fragment));
                        }
                    }
                }
            } else {
                FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, records, isRecordPop, startIndex, endIndex, false, this.mFragmentTransitionCallback);
            }
        }
        executeOps(records, isRecordPop, startIndex, endIndex);
        if (USE_STATE_MANAGER) {
            boolean isPop = arrayList4.get(i6 - 1).booleanValue();
            for (int index2 = startIndex; index2 < i6; index2++) {
                BackStackRecord record2 = arrayList3.get(index2);
                if (isPop) {
                    for (int opIndex = record2.mOps.size() - 1; opIndex >= 0; opIndex--) {
                        Fragment fragment2 = ((FragmentTransaction.C0856Op) record2.mOps.get(opIndex)).mFragment;
                        if (fragment2 != null) {
                            createOrGetFragmentStateManager(fragment2).moveToExpectedState();
                        }
                    }
                } else {
                    Iterator it2 = record2.mOps.iterator();
                    while (it2.hasNext()) {
                        Fragment fragment3 = ((FragmentTransaction.C0856Op) it2.next()).mFragment;
                        if (fragment3 != null) {
                            createOrGetFragmentStateManager(fragment3).moveToExpectedState();
                        }
                    }
                }
            }
            moveToState(this.mCurState, true);
            for (SpecialEffectsController controller : collectChangedControllers(arrayList3, i5, i6)) {
                controller.updateOperationDirection(isPop);
                controller.markPostponedState();
                controller.executePendingOperations();
            }
            Fragment fragment4 = oldPrimaryNav;
            boolean z2 = allowReordering2;
            i = i6;
            arrayList = arrayList4;
        } else {
            int postponeIndex2 = endIndex;
            if (allowReordering2) {
                ArraySet<Fragment> addedFragments = new ArraySet<>();
                addAddedFragments(addedFragments);
                i2 = 1;
                Fragment fragment5 = oldPrimaryNav;
                allowReordering = allowReordering2;
                i3 = i6;
                i4 = i5;
                arrayList2 = arrayList4;
                int postponeIndex3 = postponePostponableTransactions(records, isRecordPop, startIndex, endIndex, addedFragments);
                makeRemovedFragmentsInvisible(addedFragments);
                postponeIndex = postponeIndex3;
            } else {
                allowReordering = allowReordering2;
                i3 = i6;
                i4 = i5;
                arrayList2 = arrayList4;
                i2 = 1;
                postponeIndex = postponeIndex2;
            }
            if (postponeIndex == i4 || !allowReordering) {
                arrayList = arrayList2;
                i = i3;
            } else {
                if (this.mCurState >= i2) {
                    arrayList = arrayList2;
                    i = i3;
                    z = true;
                    FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, records, isRecordPop, startIndex, postponeIndex, true, this.mFragmentTransitionCallback);
                } else {
                    arrayList = arrayList2;
                    i = i3;
                    z = true;
                }
                moveToState(this.mCurState, z);
            }
        }
        for (int recordNum2 = startIndex; recordNum2 < i; recordNum2++) {
            BackStackRecord record3 = arrayList3.get(recordNum2);
            if (arrayList.get(recordNum2).booleanValue() && record3.mIndex >= 0) {
                record3.mIndex = -1;
            }
            record3.runOnCommitRunnables();
        }
        if (addToBackStack) {
            reportBackStackChanged();
        }
    }

    private Set<SpecialEffectsController> collectChangedControllers(ArrayList<BackStackRecord> records, int startIndex, int endIndex) {
        ViewGroup container;
        Set<SpecialEffectsController> controllers = new HashSet<>();
        for (int index = startIndex; index < endIndex; index++) {
            Iterator it = records.get(index).mOps.iterator();
            while (it.hasNext()) {
                Fragment fragment = ((FragmentTransaction.C0856Op) it.next()).mFragment;
                if (!(fragment == null || (container = fragment.mContainer) == null)) {
                    controllers.add(SpecialEffectsController.getOrCreateController(container, this));
                }
            }
        }
        return controllers;
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> fragments) {
        int numAdded = fragments.size();
        for (int i = 0; i < numAdded; i++) {
            Fragment fragment = fragments.valueAt(i);
            if (!fragment.mAdded) {
                View view = fragment.requireView();
                fragment.mPostponedAlpha = view.getAlpha();
                view.setAlpha(0.0f);
            }
        }
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, ArraySet<Fragment> added) {
        int postponeIndex = endIndex;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            BackStackRecord record = records.get(i);
            boolean isPop = isRecordPop.get(i).booleanValue();
            if (record.isPostponed() && !record.interactsWith(records, i + 1, endIndex)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener listener = new StartEnterTransitionListener(record, isPop);
                this.mPostponedTransactions.add(listener);
                record.setOnStartPostponedListener(listener);
                if (isPop) {
                    record.executeOps();
                } else {
                    record.executePopOps(false);
                }
                postponeIndex--;
                if (i != postponeIndex) {
                    records.remove(i);
                    records.add(postponeIndex, record);
                }
                addAddedFragments(added);
            }
        }
        return postponeIndex;
    }

    /* access modifiers changed from: package-private */
    public void completeExecute(BackStackRecord record, boolean isPop, boolean runTransitions, boolean moveToState) {
        if (isPop) {
            record.executePopOps(moveToState);
        } else {
            record.executeOps();
        }
        ArrayList<BackStackRecord> records = new ArrayList<>(1);
        ArrayList arrayList = new ArrayList(1);
        records.add(record);
        arrayList.add(Boolean.valueOf(isPop));
        if (runTransitions && this.mCurState >= 1) {
            FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, records, arrayList, 0, 1, true, this.mFragmentTransitionCallback);
        }
        if (moveToState) {
            moveToState(this.mCurState, true);
        }
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && record.interactsWith(fragment.mContainerId)) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha);
                }
                if (moveToState) {
                    fragment.mPostponedAlpha = 0.0f;
                } else {
                    fragment.mPostponedAlpha = -1.0f;
                    fragment.mIsNewlyAdded = false;
                }
            }
        }
    }

    private static void executeOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord record = records.get(i);
            boolean moveToState = true;
            if (isRecordPop.get(i).booleanValue()) {
                record.bumpBackStackNesting(-1);
                if (i != endIndex - 1) {
                    moveToState = false;
                }
                record.executePopOps(moveToState);
            } else {
                record.bumpBackStackNesting(1);
                record.executeOps();
            }
        }
    }

    private void setVisibleRemovingFragment(Fragment f) {
        ViewGroup container = getFragmentContainer(f);
        if (container != null && f.getEnterAnim() + f.getExitAnim() + f.getPopEnterAnim() + f.getPopExitAnim() > 0) {
            if (container.getTag(C0798R.C0801id.visible_removing_fragment_view_tag) == null) {
                container.setTag(C0798R.C0801id.visible_removing_fragment_view_tag, f);
            }
            ((Fragment) container.getTag(C0798R.C0801id.visible_removing_fragment_view_tag)).setPopDirection(f.getPopDirection());
        }
    }

    private ViewGroup getFragmentContainer(Fragment f) {
        if (f.mContainer != null) {
            return f.mContainer;
        }
        if (f.mContainerId > 0 && this.mContainer.onHasView()) {
            View view = this.mContainer.onFindViewById(f.mContainerId);
            if (view instanceof ViewGroup) {
                return (ViewGroup) view;
            }
        }
        return null;
    }

    private void addAddedFragments(ArraySet<Fragment> added) {
        int i = this.mCurState;
        if (i >= 1) {
            int state = Math.min(i, 5);
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment.mState < state) {
                    moveToState(fragment, state);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        added.add(fragment);
                    }
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (USE_STATE_MANAGER) {
            for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                controller.forcePostponedExecutePendingOperations();
            }
        } else if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        if (USE_STATE_MANAGER) {
            for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                controller.forceCompleteAllOperations();
            }
        } else if (!this.mExitAnimationCancellationSignals.isEmpty()) {
            for (Fragment fragment : this.mExitAnimationCancellationSignals.keySet()) {
                cancelExitAnimation(fragment);
                moveToState(fragment);
            }
        }
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        Set<SpecialEffectsController> controllers = new HashSet<>();
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            ViewGroup container = fragmentStateManager.getFragment().mContainer;
            if (container != null) {
                controllers.add(SpecialEffectsController.getOrCreateController(container, getSpecialEffectsControllerFactory()));
            }
        }
        return controllers;
    }

    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> records, ArrayList<Boolean> isPop) {
        boolean didSomething = false;
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                return false;
            }
            int numActions = this.mPendingActions.size();
            for (int i = 0; i < numActions; i++) {
                didSomething |= this.mPendingActions.get(i).generateOps(records, isPop);
            }
            this.mPendingActions.clear();
            this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            return didSomething;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(state);
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackState(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, String name, int id, int flags) {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList == null) {
            return false;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int last = arrayList.size() - 1;
            if (last < 0) {
                return false;
            }
            records.add(this.mBackStack.remove(last));
            isRecordPop.add(true);
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                int index2 = arrayList.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss = this.mBackStack.get(index);
                    if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                        break;
                    }
                    index2 = index - 1;
                }
                if (index < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    index--;
                    while (index >= 0) {
                        BackStackRecord bss2 = this.mBackStack.get(index);
                        if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                            break;
                        }
                        index--;
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return false;
            }
            for (int i = this.mBackStack.size() - 1; i > index; i--) {
                records.add(this.mBackStack.remove(i));
                isRecordPop.add(true);
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public FragmentManagerNonConfig retainNonConfig() {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.mNonConfig.getSnapshot();
    }

    /* access modifiers changed from: package-private */
    public Parcelable saveAllState() {
        int size;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.setIsStateSaved(true);
        ArrayList<FragmentState> active = this.mFragmentStore.saveActiveFragments();
        if (!active.isEmpty()) {
            ArrayList<String> added = this.mFragmentStore.saveAddedFragments();
            BackStackState[] backStack = null;
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            if (arrayList != null && (size = arrayList.size()) > 0) {
                backStack = new BackStackState[size];
                for (int i = 0; i < size; i++) {
                    backStack[i] = new BackStackState(this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
                    }
                }
            }
            FragmentManagerState fms = new FragmentManagerState();
            fms.mActive = active;
            fms.mAdded = added;
            fms.mBackStack = backStack;
            fms.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment = this.mPrimaryNav;
            if (fragment != null) {
                fms.mPrimaryNavActiveWho = fragment.mWho;
            }
            fms.mResultKeys.addAll(this.mResults.keySet());
            fms.mResults.addAll(this.mResults.values());
            fms.mLaunchedFragments = new ArrayList<>(this.mLaunchedFragments);
            return fms;
        } else if (!isLoggingEnabled(2)) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreAllState(Parcelable state, FragmentManagerNonConfig nonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.restoreFromSnapshot(nonConfig);
        restoreSaveState(state);
    }

    /* access modifiers changed from: package-private */
    public void restoreSaveState(Parcelable state) {
        FragmentStateManager fragmentStateManager;
        if (state != null) {
            FragmentManagerState fms = (FragmentManagerState) state;
            if (fms.mActive != null) {
                this.mFragmentStore.resetActiveFragments();
                Iterator<FragmentState> it = fms.mActive.iterator();
                while (it.hasNext()) {
                    FragmentState fs = it.next();
                    if (fs != null) {
                        Fragment retainedFragment = this.mNonConfig.findRetainedFragmentByWho(fs.mWho);
                        if (retainedFragment != null) {
                            if (isLoggingEnabled(2)) {
                                Log.v(TAG, "restoreSaveState: re-attaching retained " + retainedFragment);
                            }
                            fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, retainedFragment, fs);
                        } else {
                            fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.getContext().getClassLoader(), getFragmentFactory(), fs);
                        }
                        Fragment f = fragmentStateManager.getFragment();
                        f.mFragmentManager = this;
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "restoreSaveState: active (" + f.mWho + "): " + f);
                        }
                        fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
                        this.mFragmentStore.makeActive(fragmentStateManager);
                        fragmentStateManager.setFragmentManagerState(this.mCurState);
                    }
                }
                for (Fragment f2 : this.mNonConfig.getRetainedFragments()) {
                    if (!this.mFragmentStore.containsActiveFragment(f2.mWho)) {
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "Discarding retained Fragment " + f2 + " that was not found in the set of active Fragments " + fms.mActive);
                        }
                        this.mNonConfig.removeRetainedFragment(f2);
                        f2.mFragmentManager = this;
                        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, f2);
                        fragmentStateManager2.setFragmentManagerState(1);
                        fragmentStateManager2.moveToExpectedState();
                        f2.mRemoving = true;
                        fragmentStateManager2.moveToExpectedState();
                    }
                }
                this.mFragmentStore.restoreAddedFragments(fms.mAdded);
                if (fms.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fms.mBackStack.length);
                    for (int i = 0; i < fms.mBackStack.length; i++) {
                        BackStackRecord bse = fms.mBackStack[i].instantiate(this);
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "restoreAllState: back stack #" + i + " (index " + bse.mIndex + "): " + bse);
                            PrintWriter pw = new PrintWriter(new LogWriter(TAG));
                            bse.dump("  ", pw, false);
                            pw.close();
                        }
                        this.mBackStack.add(bse);
                    }
                } else {
                    this.mBackStack = null;
                }
                this.mBackStackIndex.set(fms.mBackStackIndex);
                if (fms.mPrimaryNavActiveWho != null) {
                    Fragment findActiveFragment = findActiveFragment(fms.mPrimaryNavActiveWho);
                    this.mPrimaryNav = findActiveFragment;
                    dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment);
                }
                ArrayList<String> savedResultKeys = fms.mResultKeys;
                if (savedResultKeys != null) {
                    for (int i2 = 0; i2 < savedResultKeys.size(); i2++) {
                        Bundle savedResult = fms.mResults.get(i2);
                        savedResult.setClassLoader(this.mHost.getContext().getClassLoader());
                        this.mResults.put(savedResultKeys.get(i2), savedResult);
                    }
                }
                this.mLaunchedFragments = new ArrayDeque<>(fms.mLaunchedFragments);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public FragmentHostCallback<?> getHost() {
        return this.mHost;
    }

    /* access modifiers changed from: package-private */
    public Fragment getParent() {
        return this.mParent;
    }

    /* access modifiers changed from: package-private */
    public FragmentContainer getContainer() {
        return this.mContainer;
    }

    /* access modifiers changed from: package-private */
    public FragmentStore getFragmentStore() {
        return this.mFragmentStore;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: androidx.activity.OnBackPressedDispatcherOwner} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: androidx.fragment.app.Fragment} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void attachController(androidx.fragment.app.FragmentHostCallback<?> r7, androidx.fragment.app.FragmentContainer r8, final androidx.fragment.app.Fragment r9) {
        /*
            r6 = this;
            androidx.fragment.app.FragmentHostCallback<?> r0 = r6.mHost
            if (r0 != 0) goto L_0x0115
            r6.mHost = r7
            r6.mContainer = r8
            r6.mParent = r9
            if (r9 == 0) goto L_0x0015
            androidx.fragment.app.FragmentManager$8 r0 = new androidx.fragment.app.FragmentManager$8
            r0.<init>(r9)
            r6.addFragmentOnAttachListener(r0)
            goto L_0x001f
        L_0x0015:
            boolean r0 = r7 instanceof androidx.fragment.app.FragmentOnAttachListener
            if (r0 == 0) goto L_0x001f
            r0 = r7
            androidx.fragment.app.FragmentOnAttachListener r0 = (androidx.fragment.app.FragmentOnAttachListener) r0
            r6.addFragmentOnAttachListener(r0)
        L_0x001f:
            androidx.fragment.app.Fragment r0 = r6.mParent
            if (r0 == 0) goto L_0x0026
            r6.updateOnBackPressedCallbackEnabled()
        L_0x0026:
            boolean r0 = r7 instanceof androidx.activity.OnBackPressedDispatcherOwner
            if (r0 == 0) goto L_0x003d
            r0 = r7
            androidx.activity.OnBackPressedDispatcherOwner r0 = (androidx.activity.OnBackPressedDispatcherOwner) r0
            androidx.activity.OnBackPressedDispatcher r1 = r0.getOnBackPressedDispatcher()
            r6.mOnBackPressedDispatcher = r1
            if (r9 == 0) goto L_0x0037
            r2 = r9
            goto L_0x0038
        L_0x0037:
            r2 = r0
        L_0x0038:
            androidx.activity.OnBackPressedCallback r3 = r6.mOnBackPressedCallback
            r1.addCallback(r2, r3)
        L_0x003d:
            if (r9 == 0) goto L_0x0048
            androidx.fragment.app.FragmentManager r0 = r9.mFragmentManager
            androidx.fragment.app.FragmentManagerViewModel r0 = r0.getChildNonConfig(r9)
            r6.mNonConfig = r0
            goto L_0x0062
        L_0x0048:
            boolean r0 = r7 instanceof androidx.lifecycle.ViewModelStoreOwner
            if (r0 == 0) goto L_0x005a
            r0 = r7
            androidx.lifecycle.ViewModelStoreOwner r0 = (androidx.lifecycle.ViewModelStoreOwner) r0
            androidx.lifecycle.ViewModelStore r0 = r0.getViewModelStore()
            androidx.fragment.app.FragmentManagerViewModel r1 = androidx.fragment.app.FragmentManagerViewModel.getInstance(r0)
            r6.mNonConfig = r1
            goto L_0x0062
        L_0x005a:
            androidx.fragment.app.FragmentManagerViewModel r0 = new androidx.fragment.app.FragmentManagerViewModel
            r1 = 0
            r0.<init>(r1)
            r6.mNonConfig = r0
        L_0x0062:
            androidx.fragment.app.FragmentManagerViewModel r0 = r6.mNonConfig
            boolean r1 = r6.isStateSaved()
            r0.setIsStateSaved(r1)
            androidx.fragment.app.FragmentStore r0 = r6.mFragmentStore
            androidx.fragment.app.FragmentManagerViewModel r1 = r6.mNonConfig
            r0.setNonConfig(r1)
            androidx.fragment.app.FragmentHostCallback<?> r0 = r6.mHost
            boolean r1 = r0 instanceof androidx.activity.result.ActivityResultRegistryOwner
            if (r1 == 0) goto L_0x0114
            androidx.activity.result.ActivityResultRegistryOwner r0 = (androidx.activity.result.ActivityResultRegistryOwner) r0
            androidx.activity.result.ActivityResultRegistry r0 = r0.getActivityResultRegistry()
            if (r9 == 0) goto L_0x0096
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r9.mWho
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ":"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0098
        L_0x0096:
            java.lang.String r1 = ""
        L_0x0098:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "FragmentManager:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r4 = "StartActivityForResult"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult r4 = new androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult
            r4.<init>()
            androidx.fragment.app.FragmentManager$9 r5 = new androidx.fragment.app.FragmentManager$9
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mStartActivityForResult = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r4 = "StartIntentSenderForResult"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.fragment.app.FragmentManager$FragmentIntentSenderContract r4 = new androidx.fragment.app.FragmentManager$FragmentIntentSenderContract
            r4.<init>()
            androidx.fragment.app.FragmentManager$10 r5 = new androidx.fragment.app.FragmentManager$10
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mStartIntentSenderForResult = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r4 = "RequestPermissions"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions r4 = new androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions
            r4.<init>()
            androidx.fragment.app.FragmentManager$11 r5 = new androidx.fragment.app.FragmentManager$11
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mRequestPermissions = r3
        L_0x0114:
            return
        L_0x0115:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Already attached"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.attachController(androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer, androidx.fragment.app.Fragment):void");
    }

    /* access modifiers changed from: package-private */
    public void noteStateNotSaved() {
        if (this.mHost != null) {
            this.mStateSaved = false;
            this.mStopped = false;
            this.mNonConfig.setIsStateSaved(false);
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment != null) {
                    fragment.noteStateNotSaved();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void launchStartActivityForResult(Fragment f, Intent intent, int requestCode, Bundle options) {
        if (this.mStartActivityForResult != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(f.mWho, requestCode));
            if (!(intent == null || options == null)) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, options);
            }
            this.mStartActivityForResult.launch(intent);
            return;
        }
        this.mHost.onStartActivityFromFragment(f, intent, requestCode, options);
    }

    /* access modifiers changed from: package-private */
    public void launchStartIntentSenderForResult(Fragment f, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        Intent fillInIntent2;
        Fragment fragment = f;
        Bundle bundle = options;
        if (this.mStartIntentSenderForResult != null) {
            if (bundle != null) {
                if (fillInIntent == null) {
                    fillInIntent2 = new Intent();
                    fillInIntent2.putExtra(EXTRA_CREATED_FILLIN_INTENT, true);
                } else {
                    fillInIntent2 = fillInIntent;
                }
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "ActivityOptions " + bundle + " were added to fillInIntent " + fillInIntent2 + " for fragment " + fragment);
                }
                fillInIntent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            } else {
                fillInIntent2 = fillInIntent;
            }
            IntentSenderRequest request = new IntentSenderRequest.Builder(intent).setFillInIntent(fillInIntent2).setFlags(flagsValues, flagsMask).build();
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(fragment.mWho, requestCode));
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "Fragment " + fragment + "is launching an IntentSender for result ");
            }
            this.mStartIntentSenderForResult.launch(request);
            return;
        }
        IntentSender intentSender = intent;
        int i = requestCode;
        int i2 = flagsMask;
        int i3 = flagsValues;
        this.mHost.onStartIntentSenderFromFragment(f, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        Intent intent2 = fillInIntent;
    }

    /* access modifiers changed from: package-private */
    public void launchRequestPermissions(Fragment f, String[] permissions, int requestCode) {
        if (this.mRequestPermissions != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(f.mWho, requestCode));
            this.mRequestPermissions.launch(permissions);
            return;
        }
        this.mHost.onRequestPermissionsFromFragment(f, permissions, requestCode);
    }

    /* access modifiers changed from: package-private */
    public void dispatchAttach() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(0);
    }

    /* access modifiers changed from: package-private */
    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(1);
    }

    /* access modifiers changed from: package-private */
    public void dispatchViewCreated() {
        dispatchStateChange(2);
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(4);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(5);
    }

    /* access modifiers changed from: package-private */
    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(7);
    }

    /* access modifiers changed from: package-private */
    public void dispatchPause() {
        dispatchStateChange(5);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStop() {
        this.mStopped = true;
        this.mNonConfig.setIsStateSaved(true);
        dispatchStateChange(4);
    }

    /* access modifiers changed from: package-private */
    public void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    /* access modifiers changed from: package-private */
    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions(true);
        endAnimatingAwayFragments();
        dispatchStateChange(-1);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    /* JADX INFO: finally extract failed */
    private void dispatchStateChange(int nextState) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.dispatchStateChange(nextState);
            moveToState(nextState, false);
            if (USE_STATE_MANAGER) {
                for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                    controller.forceCompleteAllOperations();
                }
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performMultiWindowModeChanged(isInMultiWindowMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performPictureInPictureModeChanged(isInPictureInPictureMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchConfigurationChanged(Configuration newConfig) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performConfigurationChanged(newConfig);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchLowMemory() {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performLowMemory();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        ArrayList<Fragment> newMenus = null;
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && isParentMenuVisible(f) && f.performCreateOptionsMenu(menu, inflater)) {
                show = true;
                if (newMenus == null) {
                    newMenus = new ArrayList<>();
                }
                newMenus.add(f);
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment f2 = this.mCreatedMenus.get(i);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && isParentMenuVisible(f) && f.performPrepareOptionsMenu(menu)) {
                show = true;
            }
        }
        return show;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && f.performOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchContextItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && f.performContextItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState >= 1) {
            for (Fragment f : this.mFragmentStore.getFragments()) {
                if (f != null) {
                    f.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setPrimaryNavigationFragment(Fragment f) {
        if (f == null || (f.equals(findActiveFragment(f.mWho)) && (f.mHost == null || f.mFragmentManager == this))) {
            Fragment previousPrimaryNav = this.mPrimaryNav;
            this.mPrimaryNav = f;
            dispatchParentPrimaryNavigationFragmentChanged(previousPrimaryNav);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + f + " is not an active fragment of FragmentManager " + this);
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(Fragment f) {
        if (f != null && f.equals(findActiveFragment(f.mWho))) {
            f.performPrimaryNavigationFragmentChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchPrimaryNavigationFragmentChanged() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    /* access modifiers changed from: package-private */
    public void setMaxLifecycle(Fragment f, Lifecycle.State state) {
        if (!f.equals(findActiveFragment(f.mWho)) || !(f.mHost == null || f.mFragmentManager == this)) {
            throw new IllegalArgumentException("Fragment " + f + " is not an active fragment of FragmentManager " + this);
        }
        f.mMaxState = state;
    }

    public void setFragmentFactory(FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getFragmentFactory();
        }
        return this.mHostFragmentFactory;
    }

    /* access modifiers changed from: package-private */
    public void setSpecialEffectsControllerFactory(SpecialEffectsControllerFactory specialEffectsControllerFactory) {
        this.mSpecialEffectsControllerFactory = specialEffectsControllerFactory;
    }

    /* access modifiers changed from: package-private */
    public SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        SpecialEffectsControllerFactory specialEffectsControllerFactory = this.mSpecialEffectsControllerFactory;
        if (specialEffectsControllerFactory != null) {
            return specialEffectsControllerFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getSpecialEffectsControllerFactory();
        }
        return this.mDefaultSpecialEffectsControllerFactory;
    }

    /* access modifiers changed from: package-private */
    public FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb, boolean recursive) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(cb, recursive);
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(cb);
    }

    public void addFragmentOnAttachListener(FragmentOnAttachListener listener) {
        this.mOnAttachListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnAttachFragment(Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    public void removeFragmentOnAttachListener(FragmentOnAttachListener listener) {
        this.mOnAttachListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean checkForMenus() {
        boolean hasMenu = false;
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                hasMenu = isMenuAvailable(fragment);
                continue;
            }
            if (hasMenu) {
                return true;
            }
        }
        return false;
    }

    private boolean isMenuAvailable(Fragment f) {
        return (f.mHasMenu && f.mMenuVisible) || f.mChildFragmentManager.checkForMenus();
    }

    /* access modifiers changed from: package-private */
    public void invalidateMenuForFragment(Fragment f) {
        if (f.mAdded && isMenuAvailable(f)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    static int reverseTransit(int transit) {
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return 8194;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case 8194:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory;
    }

    private class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String name, int id, int flags) {
            this.mName = name;
            this.mId = id;
            this.mFlags = flags;
        }

        public boolean generateOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
            if (FragmentManager.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && FragmentManager.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate()) {
                return false;
            }
            return FragmentManager.this.popBackStackState(records, isRecordPop, this.mName, this.mId, this.mFlags);
        }
    }

    static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        final boolean mIsBack;
        private int mNumPostponed;
        final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord record, boolean isBack) {
            this.mIsBack = isBack;
            this.mRecord = record;
        }

        public void onStartEnterTransition() {
            int i = this.mNumPostponed - 1;
            this.mNumPostponed = i;
            if (i == 0) {
                this.mRecord.mManager.scheduleCommit();
            }
        }

        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        /* access modifiers changed from: package-private */
        public void completeTransaction() {
            boolean z = false;
            boolean canceled = this.mNumPostponed > 0;
            for (Fragment fragment : this.mRecord.mManager.getFragments()) {
                fragment.setOnStartEnterTransitionListener((Fragment.OnStartEnterTransitionListener) null);
                if (canceled && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            FragmentManager fragmentManager = this.mRecord.mManager;
            BackStackRecord backStackRecord = this.mRecord;
            boolean z2 = this.mIsBack;
            if (!canceled) {
                z = true;
            }
            fragmentManager.completeExecute(backStackRecord, z2, z, true);
        }

        /* access modifiers changed from: package-private */
        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }
    }

    static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() {
            public LaunchedFragmentInfo createFromParcel(Parcel in) {
                return new LaunchedFragmentInfo(in);
            }

            public LaunchedFragmentInfo[] newArray(int size) {
                return new LaunchedFragmentInfo[size];
            }
        };
        int mRequestCode;
        String mWho;

        LaunchedFragmentInfo(String who, int requestCode) {
            this.mWho = who;
            this.mRequestCode = requestCode;
        }

        LaunchedFragmentInfo(Parcel in) {
            this.mWho = in.readString();
            this.mRequestCode = in.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mWho);
            dest.writeInt(this.mRequestCode);
        }
    }

    static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        FragmentIntentSenderContract() {
        }

        public Intent createIntent(Context context, IntentSenderRequest input) {
            Bundle activityOptions;
            Intent result = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = input.getFillInIntent();
            if (!(fillInIntent == null || (activityOptions = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) == null)) {
                result.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, activityOptions);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra(FragmentManager.EXTRA_CREATED_FILLIN_INTENT, false)) {
                    input = new IntentSenderRequest.Builder(input.getIntentSender()).setFillInIntent((Intent) null).setFlags(input.getFlagsValues(), input.getFlagsMask()).build();
                }
            }
            result.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, input);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "CreateIntent created the following intent: " + result);
            }
            return result;
        }

        public ActivityResult parseResult(int resultCode, Intent intent) {
            return new ActivityResult(resultCode, intent);
        }
    }
}

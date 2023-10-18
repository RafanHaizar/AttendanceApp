package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;

public class ReportFragment extends Fragment {
    private static final String REPORT_FRAGMENT_TAG = "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag";
    private ActivityInitializationListener mProcessListener;

    interface ActivityInitializationListener {
        void onCreate();

        void onResume();

        void onStart();
    }

    public static void injectIfNeededIn(Activity activity) {
        if (Build.VERSION.SDK_INT >= 29) {
            LifecycleCallbacks.registerIn(activity);
        }
        FragmentManager manager = activity.getFragmentManager();
        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
            manager.beginTransaction().add(new ReportFragment(), REPORT_FRAGMENT_TAG).commit();
            manager.executePendingTransactions();
        }
    }

    static void dispatch(Activity activity, Lifecycle.Event event) {
        if (activity instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event);
        } else if (activity instanceof LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
            }
        }
    }

    static ReportFragment get(Activity activity) {
        return (ReportFragment) activity.getFragmentManager().findFragmentByTag(REPORT_FRAGMENT_TAG);
    }

    private void dispatchCreate(ActivityInitializationListener listener) {
        if (listener != null) {
            listener.onCreate();
        }
    }

    private void dispatchStart(ActivityInitializationListener listener) {
        if (listener != null) {
            listener.onStart();
        }
    }

    private void dispatchResume(ActivityInitializationListener listener) {
        if (listener != null) {
            listener.onResume();
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dispatchCreate(this.mProcessListener);
        dispatch(Lifecycle.Event.ON_CREATE);
    }

    public void onStart() {
        super.onStart();
        dispatchStart(this.mProcessListener);
        dispatch(Lifecycle.Event.ON_START);
    }

    public void onResume() {
        super.onResume();
        dispatchResume(this.mProcessListener);
        dispatch(Lifecycle.Event.ON_RESUME);
    }

    public void onPause() {
        super.onPause();
        dispatch(Lifecycle.Event.ON_PAUSE);
    }

    public void onStop() {
        super.onStop();
        dispatch(Lifecycle.Event.ON_STOP);
    }

    public void onDestroy() {
        super.onDestroy();
        dispatch(Lifecycle.Event.ON_DESTROY);
        this.mProcessListener = null;
    }

    private void dispatch(Lifecycle.Event event) {
        if (Build.VERSION.SDK_INT < 29) {
            dispatch(getActivity(), event);
        }
    }

    /* access modifiers changed from: package-private */
    public void setProcessListener(ActivityInitializationListener processListener) {
        this.mProcessListener = processListener;
    }

    static class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        LifecycleCallbacks() {
        }

        static void registerIn(Activity activity) {
            activity.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityPostCreated(Activity activity, Bundle savedInstanceState) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_CREATE);
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityPostStarted(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_START);
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPostResumed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_RESUME);
        }

        public void onActivityPrePaused(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_PAUSE);
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityPreStopped(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_STOP);
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityPreDestroyed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_DESTROY);
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }
}

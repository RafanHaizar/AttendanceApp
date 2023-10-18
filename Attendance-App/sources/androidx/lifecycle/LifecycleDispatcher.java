package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import java.util.concurrent.atomic.AtomicBoolean;

class LifecycleDispatcher {
    private static AtomicBoolean sInitialized = new AtomicBoolean(false);

    static void init(Context context) {
        if (!sInitialized.getAndSet(true)) {
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new DispatcherActivityCallback());
        }
    }

    static class DispatcherActivityCallback extends EmptyActivityLifecycleCallbacks {
        DispatcherActivityCallback() {
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ReportFragment.injectIfNeededIn(activity);
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }
    }

    private LifecycleDispatcher() {
    }
}

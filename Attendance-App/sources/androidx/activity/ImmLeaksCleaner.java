package androidx.activity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.LifecycleEventObserver;
import java.lang.reflect.Field;

final class ImmLeaksCleaner implements LifecycleEventObserver {
    private static final int INIT_FAILED = 2;
    private static final int INIT_SUCCESS = 1;
    private static final int NOT_INITIALIAZED = 0;
    private static Field sHField;
    private static Field sNextServedViewField;
    private static int sReflectedFieldsInitialized = 0;
    private static Field sServedViewField;
    private Activity mActivity;

    ImmLeaksCleaner(Activity activity) {
        this.mActivity = activity;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0049, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0053, code lost:
        throw r2;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:17:0x0031, B:24:0x003b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onStateChanged(androidx.lifecycle.LifecycleOwner r6, androidx.lifecycle.Lifecycle.Event r7) {
        /*
            r5 = this;
            androidx.lifecycle.Lifecycle$Event r0 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY
            if (r7 == r0) goto L_0x0005
            return
        L_0x0005:
            int r0 = sReflectedFieldsInitialized
            if (r0 != 0) goto L_0x000c
            initializeReflectiveFields()
        L_0x000c:
            int r0 = sReflectedFieldsInitialized
            r1 = 1
            if (r0 != r1) goto L_0x0056
            android.app.Activity r0 = r5.mActivity
            java.lang.String r1 = "input_method"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.inputmethod.InputMethodManager r0 = (android.view.inputmethod.InputMethodManager) r0
            java.lang.reflect.Field r1 = sHField     // Catch:{ IllegalAccessException -> 0x0054 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ IllegalAccessException -> 0x0054 }
            if (r1 != 0) goto L_0x0025
            return
        L_0x0025:
            monitor-enter(r1)
            java.lang.reflect.Field r2 = sServedViewField     // Catch:{ IllegalAccessException -> 0x004f, ClassCastException -> 0x004c }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ IllegalAccessException -> 0x004f, ClassCastException -> 0x004c }
            android.view.View r2 = (android.view.View) r2     // Catch:{ IllegalAccessException -> 0x004f, ClassCastException -> 0x004c }
            if (r2 != 0) goto L_0x0033
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            return
        L_0x0033:
            boolean r3 = r2.isAttachedToWindow()     // Catch:{ all -> 0x004a }
            if (r3 == 0) goto L_0x003b
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            return
        L_0x003b:
            java.lang.reflect.Field r3 = sNextServedViewField     // Catch:{ IllegalAccessException -> 0x0047 }
            r4 = 0
            r3.set(r0, r4)     // Catch:{ IllegalAccessException -> 0x0047 }
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            r0.isActive()
            goto L_0x0056
        L_0x0047:
            r3 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            return
        L_0x004a:
            r2 = move-exception
            goto L_0x0052
        L_0x004c:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            return
        L_0x004f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            return
        L_0x0052:
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            throw r2
        L_0x0054:
            r1 = move-exception
            return
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.activity.ImmLeaksCleaner.onStateChanged(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Lifecycle$Event):void");
    }

    private static void initializeReflectiveFields() {
        try {
            sReflectedFieldsInitialized = 2;
            Field declaredField = InputMethodManager.class.getDeclaredField("mServedView");
            sServedViewField = declaredField;
            declaredField.setAccessible(true);
            Field declaredField2 = InputMethodManager.class.getDeclaredField("mNextServedView");
            sNextServedViewField = declaredField2;
            declaredField2.setAccessible(true);
            Field declaredField3 = InputMethodManager.class.getDeclaredField("mH");
            sHField = declaredField3;
            declaredField3.setAccessible(true);
            sReflectedFieldsInitialized = 1;
        } catch (NoSuchFieldException e) {
        }
    }
}

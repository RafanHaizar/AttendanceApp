package androidx.startup;

import android.util.Log;

public final class StartupLogger {
    static final boolean DEBUG = false;
    private static final String TAG = "StartupLogger";

    private StartupLogger() {
    }

    /* renamed from: i */
    public static void m217i(String message) {
        Log.i(TAG, message);
    }

    /* renamed from: w */
    public static void m218w(String message) {
        Log.w(TAG, message);
    }

    /* renamed from: e */
    public static void m216e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
}

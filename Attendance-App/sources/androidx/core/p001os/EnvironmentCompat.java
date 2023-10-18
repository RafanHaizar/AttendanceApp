package androidx.core.p001os;

import android.os.Build;
import android.os.Environment;
import java.io.File;

/* renamed from: androidx.core.os.EnvironmentCompat */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    public static String getStorageState(File path) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getExternalStorageState(path);
        }
        return Api19Impl.getStorageState(path);
    }

    private EnvironmentCompat() {
    }

    /* renamed from: androidx.core.os.EnvironmentCompat$Api21Impl */
    static class Api21Impl {
        private Api21Impl() {
        }

        static String getExternalStorageState(File path) {
            return Environment.getExternalStorageState(path);
        }
    }

    /* renamed from: androidx.core.os.EnvironmentCompat$Api19Impl */
    static class Api19Impl {
        private Api19Impl() {
        }

        static String getStorageState(File path) {
            return Environment.getStorageState(path);
        }
    }
}

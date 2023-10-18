package androidx.core.content.p000pm;

import android.content.pm.PermissionInfo;
import android.os.Build;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: androidx.core.content.pm.PermissionInfoCompat */
public final class PermissionInfoCompat {

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.core.content.pm.PermissionInfoCompat$Protection */
    public @interface Protection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.core.content.pm.PermissionInfoCompat$ProtectionFlags */
    public @interface ProtectionFlags {
    }

    private PermissionInfoCompat() {
    }

    public static int getProtection(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getProtection(permissionInfo);
        }
        return permissionInfo.protectionLevel & 15;
    }

    public static int getProtectionFlags(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getProtectionFlags(permissionInfo);
        }
        return permissionInfo.protectionLevel & -16;
    }

    /* renamed from: androidx.core.content.pm.PermissionInfoCompat$Api28Impl */
    static class Api28Impl {
        private Api28Impl() {
        }

        static int getProtection(PermissionInfo permissionInfo) {
            return permissionInfo.getProtection();
        }

        static int getProtectionFlags(PermissionInfo permissionInfo) {
            return permissionInfo.getProtectionFlags();
        }
    }
}

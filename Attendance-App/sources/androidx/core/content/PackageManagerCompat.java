package androidx.core.content;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.p001os.UserManagerCompat;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executors;

public final class PackageManagerCompat {
    public static final String ACTION_PERMISSION_REVOCATION_SETTINGS = "android.intent.action.AUTO_REVOKE_PERMISSIONS";
    public static final String LOG_TAG = "PackageManagerCompat";

    @Retention(RetentionPolicy.SOURCE)
    public @interface UnusedAppRestrictionsStatus {
    }

    private PackageManagerCompat() {
    }

    public static ListenableFuture<Integer> getUnusedAppRestrictionsStatus(Context context) {
        ResolvableFuture<Integer> resultFuture = ResolvableFuture.create();
        if (!UserManagerCompat.isUserUnlocked(context)) {
            resultFuture.set(0);
            Log.e(LOG_TAG, "User is in locked direct boot mode");
            return resultFuture;
        } else if (!areUnusedAppRestrictionsAvailable(context.getPackageManager())) {
            resultFuture.set(1);
            return resultFuture;
        } else {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion < 30) {
                resultFuture.set(0);
                Log.e(LOG_TAG, "Target SDK version below API 30");
                return resultFuture;
            }
            int i = 4;
            if (Build.VERSION.SDK_INT >= 31) {
                if (Api30Impl.areUnusedAppRestrictionsEnabled(context)) {
                    if (targetSdkVersion >= 31) {
                        i = 5;
                    }
                    resultFuture.set(Integer.valueOf(i));
                } else {
                    resultFuture.set(2);
                }
                return resultFuture;
            } else if (Build.VERSION.SDK_INT == 30) {
                if (!Api30Impl.areUnusedAppRestrictionsEnabled(context)) {
                    i = 2;
                }
                resultFuture.set(Integer.valueOf(i));
                return resultFuture;
            } else {
                UnusedAppRestrictionsBackportServiceConnection backportServiceConnection = new UnusedAppRestrictionsBackportServiceConnection(context);
                Objects.requireNonNull(backportServiceConnection);
                resultFuture.addListener(new PackageManagerCompat$$ExternalSyntheticLambda0(backportServiceConnection), Executors.newSingleThreadExecutor());
                backportServiceConnection.connectAndFetchResult(resultFuture);
                return resultFuture;
            }
        }
    }

    public static boolean areUnusedAppRestrictionsAvailable(PackageManager packageManager) {
        boolean restrictionsBuiltIntoOs = Build.VERSION.SDK_INT >= 30;
        boolean isOsMThroughQ = Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < 30;
        boolean hasBackportFeature = getPermissionRevocationVerifierApp(packageManager) != null;
        if (restrictionsBuiltIntoOs) {
            return true;
        }
        if (!isOsMThroughQ || !hasBackportFeature) {
            return false;
        }
        return true;
    }

    public static String getPermissionRevocationVerifierApp(PackageManager packageManager) {
        String verifierPackageName = null;
        for (ResolveInfo intentResolver : packageManager.queryIntentActivities(new Intent(ACTION_PERMISSION_REVOCATION_SETTINGS).setData(Uri.fromParts("package", "com.example", (String) null)), 0)) {
            String packageName = intentResolver.activityInfo.packageName;
            if (packageManager.checkPermission("android.permission.PACKAGE_VERIFICATION_AGENT", packageName) == 0) {
                if (verifierPackageName != null) {
                    return verifierPackageName;
                }
                verifierPackageName = packageName;
            }
        }
        return verifierPackageName;
    }

    private static class Api30Impl {
        private Api30Impl() {
        }

        static boolean areUnusedAppRestrictionsEnabled(Context context) {
            return !context.getPackageManager().isAutoRevokeWhitelisted();
        }
    }
}

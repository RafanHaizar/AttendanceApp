package androidx.appcompat.app;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;

public final class AppLocalesMetadataHolderService extends Service {
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    public static ServiceInfo getServiceInfo(Context context) throws PackageManager.NameNotFoundException {
        int flags;
        if (Build.VERSION.SDK_INT >= 24) {
            flags = 128 | Api24Impl.getDisabledComponentFlag();
        } else {
            flags = 128 | 512;
        }
        return context.getPackageManager().getServiceInfo(new ComponentName(context, AppLocalesMetadataHolderService.class), flags);
    }

    private static class Api24Impl {
        private Api24Impl() {
        }

        static int getDisabledComponentFlag() {
            return 512;
        }
    }
}

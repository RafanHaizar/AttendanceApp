package androidx.core.content;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

public abstract class UnusedAppRestrictionsBackportService extends Service {
    public static final String ACTION_UNUSED_APP_RESTRICTIONS_BACKPORT_CONNECTION = "android.support.unusedapprestrictions.action.CustomUnusedAppRestrictionsBackportService";
    private IUnusedAppRestrictionsBackportService.Stub mBinder = new IUnusedAppRestrictionsBackportService.Stub() {
        public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback callback) throws RemoteException {
            if (callback != null) {
                UnusedAppRestrictionsBackportService.this.isPermissionRevocationEnabled(new UnusedAppRestrictionsBackportCallback(callback));
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void isPermissionRevocationEnabled(UnusedAppRestrictionsBackportCallback unusedAppRestrictionsBackportCallback);

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}

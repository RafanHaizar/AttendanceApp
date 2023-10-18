package androidx.core.content;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

class UnusedAppRestrictionsBackportServiceConnection implements ServiceConnection {
    private final Context mContext;
    private boolean mHasBoundService = false;
    ResolvableFuture<Integer> mResultFuture;
    IUnusedAppRestrictionsBackportService mUnusedAppRestrictionsService = null;

    UnusedAppRestrictionsBackportServiceConnection(Context context) {
        this.mContext = context;
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        IUnusedAppRestrictionsBackportService asInterface = IUnusedAppRestrictionsBackportService.Stub.asInterface(service);
        this.mUnusedAppRestrictionsService = asInterface;
        try {
            asInterface.isPermissionRevocationEnabledForApp(getBackportCallback());
        } catch (RemoteException e) {
            this.mResultFuture.set(0);
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        this.mUnusedAppRestrictionsService = null;
    }

    public void connectAndFetchResult(ResolvableFuture<Integer> resultFuture) {
        if (!this.mHasBoundService) {
            this.mHasBoundService = true;
            this.mResultFuture = resultFuture;
            this.mContext.bindService(new Intent(UnusedAppRestrictionsBackportService.ACTION_UNUSED_APP_RESTRICTIONS_BACKPORT_CONNECTION).setPackage(PackageManagerCompat.getPermissionRevocationVerifierApp(this.mContext.getPackageManager())), this, 1);
            return;
        }
        throw new IllegalStateException("Each UnusedAppRestrictionsBackportServiceConnection can only be bound once.");
    }

    public void disconnectFromService() {
        if (this.mHasBoundService) {
            this.mHasBoundService = false;
            this.mContext.unbindService(this);
            return;
        }
        throw new IllegalStateException("bindService must be called before unbind");
    }

    private IUnusedAppRestrictionsBackportCallback getBackportCallback() {
        return new IUnusedAppRestrictionsBackportCallback.Stub() {
            public void onIsPermissionRevocationEnabledForAppResult(boolean success, boolean isEnabled) throws RemoteException {
                if (!success) {
                    UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(0);
                    Log.e(PackageManagerCompat.LOG_TAG, "Unable to retrieve the permission revocation setting from the backport");
                } else if (isEnabled) {
                    UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(3);
                } else {
                    UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(2);
                }
            }
        };
    }
}

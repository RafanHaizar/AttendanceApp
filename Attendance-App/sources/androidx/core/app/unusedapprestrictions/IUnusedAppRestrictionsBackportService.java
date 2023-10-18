package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;

public interface IUnusedAppRestrictionsBackportService extends IInterface {
    public static final String DESCRIPTOR = "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService";

    void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) throws RemoteException;

    public static class Default implements IUnusedAppRestrictionsBackportService {
        public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUnusedAppRestrictionsBackportService {
        static final int TRANSACTION_isPermissionRevocationEnabledForApp = 1;

        public Stub() {
            attachInterface(this, IUnusedAppRestrictionsBackportService.DESCRIPTOR);
        }

        public static IUnusedAppRestrictionsBackportService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
            if (iin == null || !(iin instanceof IUnusedAppRestrictionsBackportService)) {
                return new Proxy(obj);
            }
            return (IUnusedAppRestrictionsBackportService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback.Stub.asInterface(data.readStrongBinder()));
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUnusedAppRestrictionsBackportService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnusedAppRestrictionsBackportService.DESCRIPTOR;
            }

            public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}

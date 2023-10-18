package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUnusedAppRestrictionsBackportCallback extends IInterface {
    public static final String DESCRIPTOR = "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback";

    void onIsPermissionRevocationEnabledForAppResult(boolean z, boolean z2) throws RemoteException;

    public static class Default implements IUnusedAppRestrictionsBackportCallback {
        public void onIsPermissionRevocationEnabledForAppResult(boolean success, boolean isEnabled) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUnusedAppRestrictionsBackportCallback {
        static final int TRANSACTION_onIsPermissionRevocationEnabledForAppResult = 1;

        public Stub() {
            attachInterface(this, IUnusedAppRestrictionsBackportCallback.DESCRIPTOR);
        }

        public static IUnusedAppRestrictionsBackportCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUnusedAppRestrictionsBackportCallback.DESCRIPTOR);
            if (iin == null || !(iin instanceof IUnusedAppRestrictionsBackportCallback)) {
                return new Proxy(obj);
            }
            return (IUnusedAppRestrictionsBackportCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUnusedAppRestrictionsBackportCallback.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUnusedAppRestrictionsBackportCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _arg1 = false;
                            boolean _arg0 = data.readInt() != 0;
                            if (data.readInt() != 0) {
                                _arg1 = true;
                            }
                            onIsPermissionRevocationEnabledForAppResult(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUnusedAppRestrictionsBackportCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnusedAppRestrictionsBackportCallback.DESCRIPTOR;
            }

            public void onIsPermissionRevocationEnabledForAppResult(boolean success, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUnusedAppRestrictionsBackportCallback.DESCRIPTOR);
                    int i = 0;
                    _data.writeInt(success ? 1 : 0);
                    if (isEnabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}

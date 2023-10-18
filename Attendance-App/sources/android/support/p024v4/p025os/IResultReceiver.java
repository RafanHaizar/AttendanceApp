package android.support.p024v4.p025os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: android.support.v4.os.IResultReceiver */
public interface IResultReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.support.v4.os.IResultReceiver";

    void send(int i, Bundle bundle) throws RemoteException;

    /* renamed from: android.support.v4.os.IResultReceiver$Default */
    public static class Default implements IResultReceiver {
        public void send(int resultCode, Bundle resultData) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.support.v4.os.IResultReceiver$Stub */
    public static abstract class Stub extends Binder implements IResultReceiver {
        static final int TRANSACTION_send = 1;

        public Stub() {
            attachInterface(this, IResultReceiver.DESCRIPTOR);
        }

        public static IResultReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResultReceiver.DESCRIPTOR);
            if (iin == null || !(iin instanceof IResultReceiver)) {
                return new Proxy(obj);
            }
            return (IResultReceiver) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IResultReceiver.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IResultReceiver.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            send(data.readInt(), (Bundle) _Parcel.readTypedObject(data, Bundle.CREATOR));
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* renamed from: android.support.v4.os.IResultReceiver$Stub$Proxy */
        private static class Proxy implements IResultReceiver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResultReceiver.DESCRIPTOR;
            }

            public void send(int resultCode, Bundle resultData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResultReceiver.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _Parcel.writeTypedObject(_data, resultData, 0);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }

    /* renamed from: android.support.v4.os.IResultReceiver$_Parcel */
    public static class _Parcel {
        /* access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> c) {
            if (parcel.readInt() != 0) {
                return c.createFromParcel(parcel);
            }
            return null;
        }

        /* access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T value, int parcelableFlags) {
            if (value != null) {
                parcel.writeInt(1);
                value.writeToParcel(parcel, parcelableFlags);
                return;
            }
            parcel.writeInt(0);
        }
    }
}

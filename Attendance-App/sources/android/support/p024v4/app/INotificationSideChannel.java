package android.support.p024v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: android.support.v4.app.INotificationSideChannel */
public interface INotificationSideChannel extends IInterface {
    public static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";

    void cancel(String str, int i, String str2) throws RemoteException;

    void cancelAll(String str) throws RemoteException;

    void notify(String str, int i, String str2, Notification notification) throws RemoteException;

    /* renamed from: android.support.v4.app.INotificationSideChannel$Default */
    public static class Default implements INotificationSideChannel {
        public void notify(String packageName, int id, String tag, Notification notification) throws RemoteException {
        }

        public void cancel(String packageName, int id, String tag) throws RemoteException {
        }

        public void cancelAll(String packageName) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.support.v4.app.INotificationSideChannel$Stub */
    public static abstract class Stub extends Binder implements INotificationSideChannel {
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_cancelAll = 3;
        static final int TRANSACTION_notify = 1;

        public Stub() {
            attachInterface(this, INotificationSideChannel.DESCRIPTOR);
        }

        public static INotificationSideChannel asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INotificationSideChannel.DESCRIPTOR);
            if (iin == null || !(iin instanceof INotificationSideChannel)) {
                return new Proxy(obj);
            }
            return (INotificationSideChannel) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(INotificationSideChannel.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(INotificationSideChannel.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            notify(data.readString(), data.readInt(), data.readString(), (Notification) _Parcel.readTypedObject(data, Notification.CREATOR));
                            break;
                        case 2:
                            cancel(data.readString(), data.readInt(), data.readString());
                            break;
                        case 3:
                            cancelAll(data.readString());
                            break;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
                    return true;
            }
        }

        /* renamed from: android.support.v4.app.INotificationSideChannel$Stub$Proxy */
        private static class Proxy implements INotificationSideChannel {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INotificationSideChannel.DESCRIPTOR;
            }

            public void notify(String packageName, int id, String tag, Notification notification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INotificationSideChannel.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    _Parcel.writeTypedObject(_data, notification, 0);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void cancel(String packageName, int id, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INotificationSideChannel.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    this.mRemote.transact(2, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAll(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INotificationSideChannel.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(3, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }

    /* renamed from: android.support.v4.app.INotificationSideChannel$_Parcel */
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

package android.support.p024v4.p025os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.p024v4.p025os.IResultReceiver;

/* renamed from: android.support.v4.os.ResultReceiver */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>() {
        public ResultReceiver createFromParcel(Parcel in) {
            return new ResultReceiver(in);
        }

        public ResultReceiver[] newArray(int size) {
            return new ResultReceiver[size];
        }
    };
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    /* renamed from: android.support.v4.os.ResultReceiver$MyRunnable */
    class MyRunnable implements Runnable {
        final int mResultCode;
        final Bundle mResultData;

        MyRunnable(int resultCode, Bundle resultData) {
            this.mResultCode = resultCode;
            this.mResultData = resultData;
        }

        public void run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    /* renamed from: android.support.v4.os.ResultReceiver$MyResultReceiver */
    class MyResultReceiver extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        public void send(int resultCode, Bundle resultData) {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post(new MyRunnable(resultCode, resultData));
            } else {
                ResultReceiver.this.onReceiveResult(resultCode, resultData);
            }
        }
    }

    public ResultReceiver(Handler handler) {
        this.mLocal = true;
        this.mHandler = handler;
    }

    public void send(int resultCode, Bundle resultData) {
        if (this.mLocal) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new MyRunnable(resultCode, resultData));
            } else {
                onReceiveResult(resultCode, resultData);
            }
        } else {
            IResultReceiver iResultReceiver = this.mReceiver;
            if (iResultReceiver != null) {
                try {
                    iResultReceiver.send(resultCode, resultData);
                } catch (RemoteException e) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onReceiveResult(int resultCode, Bundle resultData) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new MyResultReceiver();
            }
            out.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    ResultReceiver(Parcel in) {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = IResultReceiver.Stub.asInterface(in.readStrongBinder());
    }
}

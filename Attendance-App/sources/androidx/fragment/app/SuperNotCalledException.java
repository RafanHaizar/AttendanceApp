package androidx.fragment.app;

import android.util.AndroidRuntimeException;

final class SuperNotCalledException extends AndroidRuntimeException {
    public SuperNotCalledException(String msg) {
        super(msg);
    }
}

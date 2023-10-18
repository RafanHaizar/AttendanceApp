package org.bouncycastle.util;

public class StoreException extends RuntimeException {

    /* renamed from: _e */
    private Throwable f965_e;

    public StoreException(String str, Throwable th) {
        super(str);
        this.f965_e = th;
    }

    public Throwable getCause() {
        return this.f965_e;
    }
}

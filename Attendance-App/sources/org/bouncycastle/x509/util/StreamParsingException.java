package org.bouncycastle.x509.util;

public class StreamParsingException extends Exception {

    /* renamed from: _e */
    Throwable f968_e;

    public StreamParsingException(String str, Throwable th) {
        super(str);
        this.f968_e = th;
    }

    public Throwable getCause() {
        return this.f968_e;
    }
}

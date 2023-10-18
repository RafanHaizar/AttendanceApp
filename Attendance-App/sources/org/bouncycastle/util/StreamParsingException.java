package org.bouncycastle.util;

public class StreamParsingException extends Exception {

    /* renamed from: _e */
    Throwable f966_e;

    public StreamParsingException(String str, Throwable th) {
        super(str);
        this.f966_e = th;
    }

    public Throwable getCause() {
        return this.f966_e;
    }
}

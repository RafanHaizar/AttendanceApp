package org.bouncycastle.cms;

public class CMSException extends Exception {

    /* renamed from: e */
    Exception f128e;

    public CMSException(String str) {
        super(str);
    }

    public CMSException(String str, Exception exc) {
        super(str);
        this.f128e = exc;
    }

    public Throwable getCause() {
        return this.f128e;
    }

    public Exception getUnderlyingException() {
        return this.f128e;
    }
}

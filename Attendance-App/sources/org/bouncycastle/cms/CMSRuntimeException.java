package org.bouncycastle.cms;

public class CMSRuntimeException extends RuntimeException {

    /* renamed from: e */
    Exception f129e;

    public CMSRuntimeException(String str) {
        super(str);
    }

    public CMSRuntimeException(String str, Exception exc) {
        super(str);
        this.f129e = exc;
    }

    public Throwable getCause() {
        return this.f129e;
    }

    public Exception getUnderlyingException() {
        return this.f129e;
    }
}

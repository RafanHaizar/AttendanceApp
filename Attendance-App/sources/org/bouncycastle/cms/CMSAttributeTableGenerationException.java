package org.bouncycastle.cms;

public class CMSAttributeTableGenerationException extends CMSRuntimeException {

    /* renamed from: e */
    Exception f127e;

    public CMSAttributeTableGenerationException(String str) {
        super(str);
    }

    public CMSAttributeTableGenerationException(String str, Exception exc) {
        super(str);
        this.f127e = exc;
    }

    public Throwable getCause() {
        return this.f127e;
    }

    public Exception getUnderlyingException() {
        return this.f127e;
    }
}

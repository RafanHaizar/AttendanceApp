package com.itextpdf.kernel.xmp;

public class XMPException extends Exception {
    private int errorCode;

    public XMPException(String message, int errorCode2) {
        super(message);
        this.errorCode = errorCode2;
    }

    public XMPException(String message, int errorCode2, Throwable t) {
        super(message, t);
        this.errorCode = errorCode2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}

package org.bouncycastle.util.p023io.pem;

import java.io.IOException;

/* renamed from: org.bouncycastle.util.io.pem.PemGenerationException */
public class PemGenerationException extends IOException {
    private Throwable cause;

    public PemGenerationException(String str) {
        super(str);
    }

    public PemGenerationException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }
}

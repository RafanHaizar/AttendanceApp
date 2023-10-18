package org.bouncycastle.jcajce.p014io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

/* renamed from: org.bouncycastle.jcajce.io.SignatureUpdatingOutputStream */
class SignatureUpdatingOutputStream extends OutputStream {
    private Signature sig;

    SignatureUpdatingOutputStream(Signature signature) {
        this.sig = signature;
    }

    public void write(int i) throws IOException {
        try {
            this.sig.update((byte) i);
        } catch (SignatureException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void write(byte[] bArr) throws IOException {
        try {
            this.sig.update(bArr);
        } catch (SignatureException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        try {
            this.sig.update(bArr, i, i2);
        } catch (SignatureException e) {
            throw new IOException(e.getMessage());
        }
    }
}

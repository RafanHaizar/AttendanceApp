package org.bouncycastle.jcajce.p014io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/* renamed from: org.bouncycastle.jcajce.io.DigestUpdatingOutputStream */
class DigestUpdatingOutputStream extends OutputStream {
    private MessageDigest digest;

    DigestUpdatingOutputStream(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    public void write(int i) throws IOException {
        this.digest.update((byte) i);
    }

    public void write(byte[] bArr) throws IOException {
        this.digest.update(bArr);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.digest.update(bArr, i, i2);
    }
}

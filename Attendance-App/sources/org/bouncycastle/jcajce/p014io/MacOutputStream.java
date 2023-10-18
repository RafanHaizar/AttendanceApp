package org.bouncycastle.jcajce.p014io;

import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Mac;

/* renamed from: org.bouncycastle.jcajce.io.MacOutputStream */
public final class MacOutputStream extends OutputStream {
    private Mac mac;

    public MacOutputStream(Mac mac2) {
        this.mac = mac2;
    }

    public byte[] getMac() {
        return this.mac.doFinal();
    }

    public void write(int i) throws IOException {
        this.mac.update((byte) i);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.mac.update(bArr, i, i2);
    }
}

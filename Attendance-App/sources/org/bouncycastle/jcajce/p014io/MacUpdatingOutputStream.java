package org.bouncycastle.jcajce.p014io;

import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Mac;

/* renamed from: org.bouncycastle.jcajce.io.MacUpdatingOutputStream */
class MacUpdatingOutputStream extends OutputStream {
    private Mac mac;

    MacUpdatingOutputStream(Mac mac2) {
        this.mac = mac2;
    }

    public void write(int i) throws IOException {
        this.mac.update((byte) i);
    }

    public void write(byte[] bArr) throws IOException {
        this.mac.update(bArr);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.mac.update(bArr, i, i2);
    }
}

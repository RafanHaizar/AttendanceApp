package org.bouncycastle.crypto.p013io;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.crypto.Mac;

/* renamed from: org.bouncycastle.crypto.io.MacOutputStream */
public class MacOutputStream extends OutputStream {
    protected Mac mac;

    public MacOutputStream(Mac mac2) {
        this.mac = mac2;
    }

    public byte[] getMac() {
        byte[] bArr = new byte[this.mac.getMacSize()];
        this.mac.doFinal(bArr, 0);
        return bArr;
    }

    public void write(int i) throws IOException {
        this.mac.update((byte) i);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.mac.update(bArr, i, i2);
    }
}

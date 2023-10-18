package org.bouncycastle.crypto.modes.gcm;

public class BasicGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private long[] f534H;

    public void init(byte[] bArr) {
        this.f534H = GCMUtil.asLongs(bArr);
    }

    public void multiplyH(byte[] bArr) {
        long[] asLongs = GCMUtil.asLongs(bArr);
        GCMUtil.multiply(asLongs, this.f534H);
        GCMUtil.asBytes(asLongs, bArr);
    }
}

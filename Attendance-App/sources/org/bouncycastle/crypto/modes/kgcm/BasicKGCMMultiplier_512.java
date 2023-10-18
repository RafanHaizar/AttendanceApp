package org.bouncycastle.crypto.modes.kgcm;

public class BasicKGCMMultiplier_512 implements KGCMMultiplier {

    /* renamed from: H */
    private final long[] f544H = new long[8];

    public void init(long[] jArr) {
        KGCMUtil_512.copy(jArr, this.f544H);
    }

    public void multiplyH(long[] jArr) {
        KGCMUtil_512.multiply(jArr, this.f544H, jArr);
    }
}

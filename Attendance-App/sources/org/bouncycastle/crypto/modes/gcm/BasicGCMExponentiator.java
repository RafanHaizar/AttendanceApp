package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

public class BasicGCMExponentiator implements GCMExponentiator {

    /* renamed from: x */
    private long[] f533x;

    public void exponentiateX(long j, byte[] bArr) {
        long[] oneAsLongs = GCMUtil.oneAsLongs();
        if (j > 0) {
            long[] clone = Arrays.clone(this.f533x);
            do {
                if ((1 & j) != 0) {
                    GCMUtil.multiply(oneAsLongs, clone);
                }
                GCMUtil.square(clone, clone);
                j >>>= 1;
            } while (j > 0);
        }
        GCMUtil.asBytes(oneAsLongs, bArr);
    }

    public void init(byte[] bArr) {
        this.f533x = GCMUtil.asLongs(bArr);
    }
}

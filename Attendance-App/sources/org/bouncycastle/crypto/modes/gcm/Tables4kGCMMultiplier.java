package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import kotlin.UByte;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class Tables4kGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private byte[] f536H;

    /* renamed from: T */
    private long[][] f537T;

    public void init(byte[] bArr) {
        if (this.f537T == null) {
            this.f537T = (long[][]) Array.newInstance(Long.TYPE, new int[]{256, 2});
        } else if (Arrays.areEqual(this.f536H, bArr)) {
            return;
        }
        byte[] clone = Arrays.clone(bArr);
        this.f536H = clone;
        GCMUtil.asLongs(clone, this.f537T[1]);
        long[] jArr = this.f537T[1];
        GCMUtil.multiplyP7(jArr, jArr);
        for (int i = 2; i < 256; i += 2) {
            long[][] jArr2 = this.f537T;
            GCMUtil.divideP(jArr2[i >> 1], jArr2[i]);
            long[][] jArr3 = this.f537T;
            GCMUtil.xor(jArr3[i], jArr3[1], jArr3[i + 1]);
        }
    }

    public void multiplyH(byte[] bArr) {
        byte[] bArr2 = bArr;
        long[] jArr = this.f537T[bArr2[15] & UByte.MAX_VALUE];
        long j = jArr[0];
        long j2 = jArr[1];
        for (int i = 14; i >= 0; i--) {
            long[] jArr2 = this.f537T[bArr2[i] & UByte.MAX_VALUE];
            long j3 = j2 << 56;
            j2 = ((j2 >>> 8) | (j << 56)) ^ jArr2[1];
            j = (((((j >>> 8) ^ jArr2[0]) ^ j3) ^ (j3 >>> 1)) ^ (j3 >>> 2)) ^ (j3 >>> 7);
        }
        Pack.longToBigEndian(j, bArr2, 0);
        Pack.longToBigEndian(j2, bArr2, 8);
    }
}

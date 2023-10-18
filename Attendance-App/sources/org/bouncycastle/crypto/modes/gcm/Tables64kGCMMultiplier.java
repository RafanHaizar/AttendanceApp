package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import kotlin.UByte;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private byte[] f538H;

    /* renamed from: T */
    private long[][][] f539T;

    public void init(byte[] bArr) {
        if (this.f539T == null) {
            this.f539T = (long[][][]) Array.newInstance(Long.TYPE, new int[]{16, 256, 2});
        } else if (Arrays.areEqual(this.f538H, bArr)) {
            return;
        }
        this.f538H = Arrays.clone(bArr);
        for (int i = 0; i < 16; i++) {
            long[][][] jArr = this.f539T;
            long[][] jArr2 = jArr[i];
            if (i == 0) {
                GCMUtil.asLongs(this.f538H, jArr2[1]);
                long[] jArr3 = jArr2[1];
                GCMUtil.multiplyP7(jArr3, jArr3);
            } else {
                GCMUtil.multiplyP8(jArr[i - 1][1], jArr2[1]);
            }
            for (int i2 = 2; i2 < 256; i2 += 2) {
                GCMUtil.divideP(jArr2[i2 >> 1], jArr2[i2]);
                GCMUtil.xor(jArr2[i2], jArr2[1], jArr2[i2 + 1]);
            }
        }
    }

    public void multiplyH(byte[] bArr) {
        long[] jArr = this.f539T[15][bArr[15] & UByte.MAX_VALUE];
        long j = jArr[0];
        long j2 = jArr[1];
        for (int i = 14; i >= 0; i--) {
            long[] jArr2 = this.f539T[i][bArr[i] & UByte.MAX_VALUE];
            j ^= jArr2[0];
            j2 ^= jArr2[1];
        }
        Pack.longToBigEndian(j, bArr, 0);
        Pack.longToBigEndian(j2, bArr, 8);
    }
}

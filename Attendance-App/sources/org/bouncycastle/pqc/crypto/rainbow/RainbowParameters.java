package org.bouncycastle.pqc.crypto.rainbow;

import org.bouncycastle.crypto.CipherParameters;

public class RainbowParameters implements CipherParameters {
    private final int[] DEFAULT_VI;

    /* renamed from: vi */
    private int[] f933vi;

    public RainbowParameters() {
        int[] iArr = {6, 12, 17, 22, 33};
        this.DEFAULT_VI = iArr;
        this.f933vi = iArr;
    }

    public RainbowParameters(int[] iArr) {
        this.DEFAULT_VI = new int[]{6, 12, 17, 22, 33};
        this.f933vi = iArr;
        checkParams();
    }

    private void checkParams() {
        int[] iArr;
        int i;
        int[] iArr2 = this.f933vi;
        if (iArr2 == null) {
            throw new IllegalArgumentException("no layers defined.");
        } else if (iArr2.length > 1) {
            int i2 = 0;
            do {
                iArr = this.f933vi;
                if (i2 < iArr.length - 1) {
                    i = iArr[i2];
                    i2++;
                } else {
                    return;
                }
            } while (i < iArr[i2]);
            throw new IllegalArgumentException("v[i] has to be smaller than v[i+1]");
        } else {
            throw new IllegalArgumentException("Rainbow needs at least 1 layer, such that v1 < v2.");
        }
    }

    public int getDocLength() {
        int[] iArr = this.f933vi;
        return iArr[iArr.length - 1] - iArr[0];
    }

    public int getNumOfLayers() {
        return this.f933vi.length - 1;
    }

    public int[] getVi() {
        return this.f933vi;
    }
}

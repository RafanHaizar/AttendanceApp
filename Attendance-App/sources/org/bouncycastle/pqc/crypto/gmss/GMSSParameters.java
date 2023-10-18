package org.bouncycastle.pqc.crypto.gmss;

import org.bouncycastle.util.Arrays;

public class GMSSParameters {

    /* renamed from: K */
    private int[] f874K;
    private int[] heightOfTrees;
    private int numOfLayers;
    private int[] winternitzParameter;

    public GMSSParameters(int i) throws IllegalArgumentException {
        if (i <= 10) {
            init(1, new int[]{10}, new int[]{3}, new int[]{2});
        } else if (i <= 20) {
            init(2, new int[]{10, 10}, new int[]{5, 4}, new int[]{2, 2});
        } else {
            init(4, new int[]{10, 10, 10, 10}, new int[]{9, 9, 9, 3}, new int[]{2, 2, 2, 2});
        }
    }

    public GMSSParameters(int i, int[] iArr, int[] iArr2, int[] iArr3) throws IllegalArgumentException {
        init(i, iArr, iArr2, iArr3);
    }

    private void init(int i, int[] iArr, int[] iArr2, int[] iArr3) throws IllegalArgumentException {
        boolean z;
        String str;
        this.numOfLayers = i;
        if (i == iArr2.length && i == iArr.length && i == iArr3.length) {
            z = true;
            str = "";
        } else {
            str = "Unexpected parameterset format";
            z = false;
        }
        for (int i2 = 0; i2 < this.numOfLayers; i2++) {
            int i3 = iArr3[i2];
            if (i3 < 2 || (iArr[i2] - i3) % 2 != 0) {
                str = "Wrong parameter K (K >= 2 and H-K even required)!";
                z = false;
            }
            if (iArr[i2] < 4 || iArr2[i2] < 2) {
                str = "Wrong parameter H or w (H > 3 and w > 1 required)!";
                z = false;
            }
        }
        if (z) {
            this.heightOfTrees = Arrays.clone(iArr);
            this.winternitzParameter = Arrays.clone(iArr2);
            this.f874K = Arrays.clone(iArr3);
            return;
        }
        throw new IllegalArgumentException(str);
    }

    public int[] getHeightOfTrees() {
        return Arrays.clone(this.heightOfTrees);
    }

    public int[] getK() {
        return Arrays.clone(this.f874K);
    }

    public int getNumOfLayers() {
        return this.numOfLayers;
    }

    public int[] getWinternitzParameter() {
        return Arrays.clone(this.winternitzParameter);
    }
}

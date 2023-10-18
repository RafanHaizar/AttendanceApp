package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McEliecePublicKeyParameters extends McElieceKeyParameters {

    /* renamed from: g */
    private GF2Matrix f919g;

    /* renamed from: n */
    private int f920n;

    /* renamed from: t */
    private int f921t;

    public McEliecePublicKeyParameters(int i, int i2, GF2Matrix gF2Matrix) {
        super(false, (McElieceParameters) null);
        this.f920n = i;
        this.f921t = i2;
        this.f919g = new GF2Matrix(gF2Matrix);
    }

    public GF2Matrix getG() {
        return this.f919g;
    }

    public int getK() {
        return this.f919g.getNumRows();
    }

    public int getN() {
        return this.f920n;
    }

    public int getT() {
        return this.f921t;
    }
}

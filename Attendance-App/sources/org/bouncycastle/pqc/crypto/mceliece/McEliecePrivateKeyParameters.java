package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McEliecePrivateKeyParameters extends McElieceKeyParameters {
    private GF2mField field;
    private PolynomialGF2mSmallM goppaPoly;

    /* renamed from: h */
    private GF2Matrix f914h;

    /* renamed from: k */
    private int f915k;

    /* renamed from: n */
    private int f916n;
    private String oid;

    /* renamed from: p1 */
    private Permutation f917p1;

    /* renamed from: p2 */
    private Permutation f918p2;
    private PolynomialGF2mSmallM[] qInv;
    private GF2Matrix sInv;

    public McEliecePrivateKeyParameters(int i, int i2, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix) {
        super(true, (McElieceParameters) null);
        this.f915k = i2;
        this.f916n = i;
        this.field = gF2mField;
        this.goppaPoly = polynomialGF2mSmallM;
        this.sInv = gF2Matrix;
        this.f917p1 = permutation;
        this.f918p2 = permutation2;
        this.f914h = GoppaCode.createCanonicalCheckMatrix(gF2mField, polynomialGF2mSmallM);
        this.qInv = new PolynomialRingGF2m(gF2mField, polynomialGF2mSmallM).getSquareRootMatrix();
    }

    public McEliecePrivateKeyParameters(int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, byte[][] bArr7) {
        super(true, (McElieceParameters) null);
        this.f916n = i;
        this.f915k = i2;
        GF2mField gF2mField = new GF2mField(bArr);
        this.field = gF2mField;
        this.goppaPoly = new PolynomialGF2mSmallM(gF2mField, bArr2);
        this.sInv = new GF2Matrix(bArr3);
        this.f917p1 = new Permutation(bArr4);
        this.f918p2 = new Permutation(bArr5);
        this.f914h = new GF2Matrix(bArr6);
        this.qInv = new PolynomialGF2mSmallM[bArr7.length];
        for (int i3 = 0; i3 < bArr7.length; i3++) {
            this.qInv[i3] = new PolynomialGF2mSmallM(this.field, bArr7[i3]);
        }
    }

    public GF2mField getField() {
        return this.field;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.goppaPoly;
    }

    public GF2Matrix getH() {
        return this.f914h;
    }

    public int getK() {
        return this.f915k;
    }

    public int getN() {
        return this.f916n;
    }

    public Permutation getP1() {
        return this.f917p1;
    }

    public Permutation getP2() {
        return this.f918p2;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.qInv;
    }

    public GF2Matrix getSInv() {
        return this.sInv;
    }
}

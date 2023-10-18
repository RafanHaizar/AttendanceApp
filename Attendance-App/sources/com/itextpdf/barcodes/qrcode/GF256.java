package com.itextpdf.barcodes.qrcode;

import com.itextpdf.p026io.codec.TIFFConstants;

final class GF256 {
    public static final GF256 DATA_MATRIX_FIELD = new GF256(301);
    public static final GF256 QR_CODE_FIELD = new GF256(TIFFConstants.TIFFTAG_PAGENAME);
    private final int[] expTable = new int[256];
    private final int[] logTable = new int[256];
    private final GF256Poly one;
    private final GF256Poly zero;

    private GF256(int primitive) {
        int x = 1;
        for (int i = 0; i < 256; i++) {
            this.expTable[i] = x;
            x <<= 1;
            if (x >= 256) {
                x ^= primitive;
            }
        }
        for (int i2 = 0; i2 < 255; i2++) {
            this.logTable[this.expTable[i2]] = i2;
        }
        this.zero = new GF256Poly(this, new int[]{0});
        this.one = new GF256Poly(this, new int[]{1});
    }

    /* access modifiers changed from: package-private */
    public GF256Poly getZero() {
        return this.zero;
    }

    /* access modifiers changed from: package-private */
    public GF256Poly getOne() {
        return this.one;
    }

    /* access modifiers changed from: package-private */
    public GF256Poly buildMonomial(int degree, int coefficient) {
        if (degree < 0) {
            throw new IllegalArgumentException();
        } else if (coefficient == 0) {
            return this.zero;
        } else {
            int[] coefficients = new int[(degree + 1)];
            coefficients[0] = coefficient;
            return new GF256Poly(this, coefficients);
        }
    }

    static int addOrSubtract(int a, int b) {
        return a ^ b;
    }

    /* access modifiers changed from: package-private */
    public int exp(int a) {
        return this.expTable[a];
    }

    /* access modifiers changed from: package-private */
    public int log(int a) {
        if (a != 0) {
            return this.logTable[a];
        }
        throw new IllegalArgumentException();
    }

    /* access modifiers changed from: package-private */
    public int inverse(int a) {
        if (a != 0) {
            return this.expTable[255 - this.logTable[a]];
        }
        throw new ArithmeticException();
    }

    /* access modifiers changed from: package-private */
    public int multiply(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a == 1) {
            return b;
        }
        if (b == 1) {
            return a;
        }
        int[] iArr = this.expTable;
        int[] iArr2 = this.logTable;
        return iArr[(iArr2[a] + iArr2[b]) % 255];
    }
}

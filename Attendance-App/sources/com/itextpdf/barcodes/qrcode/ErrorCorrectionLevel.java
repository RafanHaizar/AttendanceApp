package com.itextpdf.barcodes.qrcode;

import com.itextpdf.svg.SvgConstants;

public final class ErrorCorrectionLevel {
    private static final ErrorCorrectionLevel[] FOR_BITS;

    /* renamed from: H */
    public static final ErrorCorrectionLevel f1181H;

    /* renamed from: L */
    public static final ErrorCorrectionLevel f1182L;

    /* renamed from: M */
    public static final ErrorCorrectionLevel f1183M;

    /* renamed from: Q */
    public static final ErrorCorrectionLevel f1184Q;
    private final int bits;
    private final String name;
    private final int ordinal;

    static {
        ErrorCorrectionLevel errorCorrectionLevel = new ErrorCorrectionLevel(0, 1, "L");
        f1182L = errorCorrectionLevel;
        ErrorCorrectionLevel errorCorrectionLevel2 = new ErrorCorrectionLevel(1, 0, SvgConstants.Attributes.PATH_DATA_MOVE_TO);
        f1183M = errorCorrectionLevel2;
        ErrorCorrectionLevel errorCorrectionLevel3 = new ErrorCorrectionLevel(2, 3, SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO);
        f1184Q = errorCorrectionLevel3;
        ErrorCorrectionLevel errorCorrectionLevel4 = new ErrorCorrectionLevel(3, 2, "H");
        f1181H = errorCorrectionLevel4;
        FOR_BITS = new ErrorCorrectionLevel[]{errorCorrectionLevel2, errorCorrectionLevel, errorCorrectionLevel4, errorCorrectionLevel3};
    }

    private ErrorCorrectionLevel(int ordinal2, int bits2, String name2) {
        this.ordinal = ordinal2;
        this.bits = bits2;
        this.name = name2;
    }

    public int ordinal() {
        return this.ordinal;
    }

    public int getBits() {
        return this.bits;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public static ErrorCorrectionLevel forBits(int bits2) {
        if (bits2 >= 0) {
            ErrorCorrectionLevel[] errorCorrectionLevelArr = FOR_BITS;
            if (bits2 < errorCorrectionLevelArr.length) {
                return errorCorrectionLevelArr[bits2];
            }
        }
        throw new IllegalArgumentException();
    }
}

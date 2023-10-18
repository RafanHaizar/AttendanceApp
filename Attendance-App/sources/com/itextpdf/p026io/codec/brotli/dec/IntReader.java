package com.itextpdf.p026io.codec.brotli.dec;

import kotlin.UByte;

/* renamed from: com.itextpdf.io.codec.brotli.dec.IntReader */
final class IntReader {
    private byte[] byteBuffer;
    private int[] intBuffer;

    IntReader() {
    }

    static void init(IntReader ir, byte[] byteBuffer2, int[] intBuffer2) {
        ir.byteBuffer = byteBuffer2;
        ir.intBuffer = intBuffer2;
    }

    static void convert(IntReader ir, int intLen) {
        for (int i = 0; i < intLen; i++) {
            int[] iArr = ir.intBuffer;
            byte[] bArr = ir.byteBuffer;
            iArr[i] = ((bArr[(i * 4) + 3] & UByte.MAX_VALUE) << 24) | (bArr[i * 4] & UByte.MAX_VALUE) | ((bArr[(i * 4) + 1] & UByte.MAX_VALUE) << 8) | ((bArr[(i * 4) + 2] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
        }
    }
}

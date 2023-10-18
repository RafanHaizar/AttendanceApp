package com.itextpdf.p026io.codec.brotli.dec;

/* renamed from: com.itextpdf.io.codec.brotli.dec.Utils */
final class Utils {
    private static final byte[] BYTE_ZEROES = new byte[1024];
    private static final int[] INT_ZEROES = new int[1024];

    Utils() {
    }

    static void fillWithZeroes(byte[] dest, int offset, int length) {
        int cursor = 0;
        while (cursor < length) {
            int step = Math.min(cursor + 1024, length) - cursor;
            System.arraycopy(BYTE_ZEROES, 0, dest, offset + cursor, step);
            cursor += step;
        }
    }

    static void fillWithZeroes(int[] dest, int offset, int length) {
        int cursor = 0;
        while (cursor < length) {
            int step = Math.min(cursor + 1024, length) - cursor;
            System.arraycopy(INT_ZEROES, 0, dest, offset + cursor, step);
            cursor += step;
        }
    }
}

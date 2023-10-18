package com.itextpdf.p026io.font.woff2;

/* renamed from: com.itextpdf.io.font.woff2.Round */
class Round {
    Round() {
    }

    public static int round4(int value) {
        if (Integer.MAX_VALUE - value < 3) {
            return value;
        }
        return (value + 3) & -4;
    }

    public static long round4(long value) {
        if (Long.MAX_VALUE - value < 3) {
            return value;
        }
        return -4 & (3 + value);
    }
}

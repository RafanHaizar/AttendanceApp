package com.itextpdf.p026io.codec.brotli.dec;

import java.nio.ByteBuffer;

/* renamed from: com.itextpdf.io.codec.brotli.dec.Dictionary */
public final class Dictionary {
    static final int MAX_TRANSFORMED_WORD_LENGTH = 37;
    static final int MAX_WORD_LENGTH = 24;
    static final int MIN_WORD_LENGTH = 4;
    static final int[] OFFSETS_BY_LENGTH = {0, 0, 0, 0, 0, 4096, 9216, 21504, 35840, 44032, 53248, 63488, 74752, 87040, 93696, 100864, 104704, 106752, 108928, 113536, 115968, 118528, 119872, 121280, 122016};
    static final int[] SIZE_BITS_BY_LENGTH = {0, 0, 0, 0, 10, 10, 11, 11, 10, 10, 10, 10, 10, 9, 9, 8, 7, 7, 8, 7, 7, 6, 6, 5, 5};
    private static volatile ByteBuffer data;

    /* renamed from: com.itextpdf.io.codec.brotli.dec.Dictionary$DataLoader */
    private static class DataLoader {

        /* renamed from: OK */
        static final boolean f1206OK;

        private DataLoader() {
        }

        static {
            boolean ok = true;
            try {
                Class.forName(Dictionary.class.getPackage().getName() + ".DictionaryData");
            } catch (Throwable th) {
                ok = false;
            }
            f1206OK = ok;
        }
    }

    public static void setData(ByteBuffer data2) {
        data = data2;
    }

    public static ByteBuffer getData() {
        if (data != null) {
            return data;
        }
        if (DataLoader.f1206OK) {
            return data;
        }
        throw new BrotliRuntimeException("brotli dictionary is not set");
    }
}

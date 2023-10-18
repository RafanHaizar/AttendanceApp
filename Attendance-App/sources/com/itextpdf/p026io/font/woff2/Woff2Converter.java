package com.itextpdf.p026io.font.woff2;

/* renamed from: com.itextpdf.io.font.woff2.Woff2Converter */
public class Woff2Converter {
    public static boolean isWoff2Font(byte[] woff2Bytes) {
        if (woff2Bytes.length < 4) {
            return false;
        }
        try {
            if (new Buffer(woff2Bytes, 0, 4).readInt() == 2001684018) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static byte[] convert(byte[] woff2Bytes) {
        byte[] inner_byte_buffer = new byte[Woff2Dec.computeWoff2FinalSize(woff2Bytes, woff2Bytes.length)];
        Woff2Dec.convertWoff2ToTtf(woff2Bytes, woff2Bytes.length, new Woff2MemoryOut(inner_byte_buffer, inner_byte_buffer.length));
        return inner_byte_buffer;
    }
}

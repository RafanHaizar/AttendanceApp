package com.itextpdf.p026io.font.woff2;

import kotlin.UByte;
import org.bouncycastle.asn1.cmc.BodyPartID;

/* renamed from: com.itextpdf.io.font.woff2.JavaUnsignedUtil */
class JavaUnsignedUtil {
    JavaUnsignedUtil() {
    }

    public static int asU16(short number) {
        return 65535 & number;
    }

    public static int asU8(byte number) {
        return number & UByte.MAX_VALUE;
    }

    public static byte toU8(int number) {
        return (byte) (number & 255);
    }

    public static short toU16(int number) {
        return (short) (65535 & number);
    }

    public static int compareAsUnsigned(int left, int right) {
        return Long.valueOf(((long) left) & BodyPartID.bodyIdMax).compareTo(Long.valueOf(BodyPartID.bodyIdMax & ((long) right)));
    }
}

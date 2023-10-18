package com.itextpdf.p026io.codec.brotli.dec;

import java.nio.ByteBuffer;

/* renamed from: com.itextpdf.io.codec.brotli.dec.Transform */
final class Transform {
    static final Transform[] TRANSFORMS = {new Transform("", 0, ""), new Transform("", 0, " "), new Transform(" ", 0, " "), new Transform("", 12, ""), new Transform("", 10, " "), new Transform("", 0, " the "), new Transform(" ", 0, ""), new Transform("s ", 0, " "), new Transform("", 0, " of "), new Transform("", 10, ""), new Transform("", 0, " and "), new Transform("", 13, ""), new Transform("", 1, ""), new Transform(", ", 0, " "), new Transform("", 0, ", "), new Transform(" ", 10, " "), new Transform("", 0, " in "), new Transform("", 0, " to "), new Transform("e ", 0, " "), new Transform("", 0, "\""), new Transform("", 0, "."), new Transform("", 0, "\">"), new Transform("", 0, "\n"), new Transform("", 3, ""), new Transform("", 0, "]"), new Transform("", 0, " for "), new Transform("", 14, ""), new Transform("", 2, ""), new Transform("", 0, " a "), new Transform("", 0, " that "), new Transform(" ", 10, ""), new Transform("", 0, ". "), new Transform(".", 0, ""), new Transform(" ", 0, ", "), new Transform("", 15, ""), new Transform("", 0, " with "), new Transform("", 0, "'"), new Transform("", 0, " from "), new Transform("", 0, " by "), new Transform("", 16, ""), new Transform("", 17, ""), new Transform(" the ", 0, ""), new Transform("", 4, ""), new Transform("", 0, ". The "), new Transform("", 11, ""), new Transform("", 0, " on "), new Transform("", 0, " as "), new Transform("", 0, " is "), new Transform("", 7, ""), new Transform("", 1, "ing "), new Transform("", 0, "\n\t"), new Transform("", 0, ":"), new Transform(" ", 0, ". "), new Transform("", 0, "ed "), new Transform("", 20, ""), new Transform("", 18, ""), new Transform("", 6, ""), new Transform("", 0, "("), new Transform("", 10, ", "), new Transform("", 8, ""), new Transform("", 0, " at "), new Transform("", 0, "ly "), new Transform(" the ", 0, " of "), new Transform("", 5, ""), new Transform("", 9, ""), new Transform(" ", 10, ", "), new Transform("", 10, "\""), new Transform(".", 0, "("), new Transform("", 11, " "), new Transform("", 10, "\">"), new Transform("", 0, "=\""), new Transform(" ", 0, "."), new Transform(".com/", 0, ""), new Transform(" the ", 0, " of the "), new Transform("", 10, "'"), new Transform("", 0, ". This "), new Transform("", 0, ","), new Transform(".", 0, " "), new Transform("", 10, "("), new Transform("", 10, "."), new Transform("", 0, " not "), new Transform(" ", 0, "=\""), new Transform("", 0, "er "), new Transform(" ", 11, " "), new Transform("", 0, "al "), new Transform(" ", 11, ""), new Transform("", 0, "='"), new Transform("", 11, "\""), new Transform("", 10, ". "), new Transform(" ", 0, "("), new Transform("", 0, "ful "), new Transform(" ", 10, ". "), new Transform("", 0, "ive "), new Transform("", 0, "less "), new Transform("", 11, "'"), new Transform("", 0, "est "), new Transform(" ", 10, "."), new Transform("", 11, "\">"), new Transform(" ", 0, "='"), new Transform("", 10, ","), new Transform("", 0, "ize "), new Transform("", 11, "."), new Transform("Â ", 0, ""), new Transform(" ", 0, ","), new Transform("", 10, "=\""), new Transform("", 11, "=\""), new Transform("", 0, "ous "), new Transform("", 11, ", "), new Transform("", 10, "='"), new Transform(" ", 10, ","), new Transform(" ", 11, "=\""), new Transform(" ", 11, ", "), new Transform("", 11, ","), new Transform("", 11, "("), new Transform("", 11, ". "), new Transform(" ", 11, "."), new Transform("", 11, "='"), new Transform(" ", 11, ". "), new Transform(" ", 10, "=\""), new Transform(" ", 11, "='"), new Transform(" ", 10, "='")};
    private final byte[] prefix;
    private final byte[] suffix;
    private final int type;

    Transform(String prefix2, int type2, String suffix2) {
        this.prefix = readUniBytes(prefix2);
        this.type = type2;
        this.suffix = readUniBytes(suffix2);
    }

    static byte[] readUniBytes(String uniBytes) {
        byte[] result = new byte[uniBytes.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) uniBytes.charAt(i);
        }
        return result;
    }

    static int transformDictionaryWord(byte[] dst, int dstOffset, ByteBuffer data, int wordOffset, int len, Transform transform) {
        int len2;
        int offset = dstOffset;
        byte[] string = transform.prefix;
        int tmp = string.length;
        for (int i = 0; i < tmp; i++) {
            dst[offset] = string[i];
            offset++;
        }
        int op = transform.type;
        int tmp2 = WordTransformType.getOmitFirst(op);
        if (tmp2 > len) {
            tmp2 = len;
        }
        int wordOffset2 = wordOffset + tmp2;
        int len3 = (len - tmp2) - WordTransformType.getOmitLast(op);
        int i2 = len3;
        while (i2 > 0) {
            dst[offset] = data.get(wordOffset2);
            i2--;
            offset++;
            wordOffset2++;
        }
        if (op == 11 || op == 10) {
            int uppercaseOffset = offset - len3;
            if (op == 10) {
                len3 = 1;
            }
            while (len2 > 0) {
                int tmp3 = dst[uppercaseOffset] & 255;
                if (tmp3 < 192) {
                    if (tmp3 >= 97 && tmp3 <= 122) {
                        dst[uppercaseOffset] = (byte) (dst[uppercaseOffset] ^ 32);
                    }
                    uppercaseOffset++;
                    len2--;
                } else if (tmp3 < 224) {
                    int i3 = uppercaseOffset + 1;
                    dst[i3] = (byte) (dst[i3] ^ 32);
                    uppercaseOffset += 2;
                    len2 -= 2;
                } else {
                    int i4 = uppercaseOffset + 2;
                    dst[i4] = (byte) (dst[i4] ^ 5);
                    uppercaseOffset += 3;
                    len2 -= 3;
                }
            }
        }
        byte[] string2 = transform.suffix;
        int tmp4 = string2.length;
        for (int i5 = 0; i5 < tmp4; i5++) {
            dst[offset] = string2[i5];
            offset++;
        }
        return offset - dstOffset;
    }
}

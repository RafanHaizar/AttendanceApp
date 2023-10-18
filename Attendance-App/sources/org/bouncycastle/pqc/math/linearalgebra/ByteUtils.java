package org.bouncycastle.pqc.math.linearalgebra;

import com.itextpdf.barcodes.Barcode128;

public final class ByteUtils {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX};

    private ByteUtils() {
    }

    public static byte[] clone(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public static byte[] concatenate(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static byte[] concatenate(byte[][] bArr) {
        int length = bArr[0].length;
        byte[] bArr2 = new byte[(bArr.length * length)];
        int i = 0;
        for (byte[] arraycopy : bArr) {
            System.arraycopy(arraycopy, 0, bArr2, i, length);
            i += length;
        }
        return bArr2;
    }

    public static int deepHashCode(byte[] bArr) {
        int i = 1;
        for (byte b : bArr) {
            i = (i * 31) + b;
        }
        return i;
    }

    public static int deepHashCode(byte[][] bArr) {
        int i = 1;
        for (byte[] deepHashCode : bArr) {
            i = (i * 31) + deepHashCode(deepHashCode);
        }
        return i;
    }

    public static int deepHashCode(byte[][][] bArr) {
        int i = 1;
        for (byte[][] deepHashCode : bArr) {
            i = (i * 31) + deepHashCode(deepHashCode);
        }
        return i;
    }

    public static boolean equals(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null || bArr.length != bArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = bArr.length - 1; length >= 0; length--) {
            z &= bArr[length] == bArr2[length];
        }
        return z;
    }

    public static boolean equals(byte[][] bArr, byte[][] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = bArr.length - 1; length >= 0; length--) {
            z &= equals(bArr[length], bArr2[length]);
        }
        return z;
    }

    public static boolean equals(byte[][][] bArr, byte[][][] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte[][] bArr3 = bArr[length];
            if (bArr3.length != bArr2[length].length) {
                return false;
            }
            for (int length2 = bArr3.length - 1; length2 >= 0; length2--) {
                z &= equals(bArr[length][length2], bArr2[length][length2]);
            }
        }
        return z;
    }

    public static byte[] fromHexString(String str) {
        char[] charArray = str.toUpperCase().toCharArray();
        int i = 0;
        for (char c : charArray) {
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')) {
                i++;
            }
        }
        byte[] bArr = new byte[((i + 1) >> 1)];
        int i2 = i & 1;
        for (char c2 : charArray) {
            if (c2 < '0' || c2 > '9') {
                if (c2 >= 'A' && c2 <= 'F') {
                    int i3 = i2 >> 1;
                    byte b = (byte) (bArr[i3] << 4);
                    bArr[i3] = b;
                    bArr[i3] = (byte) (((c2 - 'A') + 10) | b);
                }
            } else {
                int i4 = i2 >> 1;
                byte b2 = (byte) (bArr[i4] << 4);
                bArr[i4] = b2;
                bArr[i4] = (byte) ((c2 - '0') | b2);
            }
            i2++;
        }
        return bArr;
    }

    public static byte[][] split(byte[] bArr, int i) throws ArrayIndexOutOfBoundsException {
        if (i <= bArr.length) {
            byte[] bArr2 = new byte[i];
            byte[][] bArr3 = {bArr2, new byte[(bArr.length - i)]};
            System.arraycopy(bArr, 0, bArr2, 0, i);
            System.arraycopy(bArr, i, bArr3[1], 0, bArr.length - i);
            return bArr3;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static byte[] subArray(byte[] bArr, int i) {
        return subArray(bArr, i, bArr.length);
    }

    public static byte[] subArray(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i, bArr2, 0, i3);
        return bArr2;
    }

    public static String toBinaryString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            for (int i2 = 0; i2 < 8; i2++) {
                str = str + ((b >>> i2) & 1);
            }
            if (i != bArr.length - 1) {
                str = str + " ";
            }
        }
        return str;
    }

    public static char[] toCharArray(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            cArr[i] = (char) bArr[i];
        }
        return cArr;
    }

    public static String toHexString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            StringBuilder append = new StringBuilder().append(str);
            char[] cArr = HEX_CHARS;
            str = append.append(cArr[(bArr[i] >>> 4) & 15]).toString() + cArr[bArr[i] & 15];
        }
        return str;
    }

    public static String toHexString(byte[] bArr, String str, String str2) {
        String str3 = new String(str);
        for (int i = 0; i < bArr.length; i++) {
            StringBuilder append = new StringBuilder().append(str3);
            char[] cArr = HEX_CHARS;
            str3 = append.append(cArr[(bArr[i] >>> 4) & 15]).toString() + cArr[bArr[i] & 15];
            if (i < bArr.length - 1) {
                str3 = str3 + str2;
            }
        }
        return str3;
    }

    public static byte[] xor(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int length = bArr.length - 1; length >= 0; length--) {
            bArr3[length] = (byte) (bArr[length] ^ bArr2[length]);
        }
        return bArr3;
    }
}

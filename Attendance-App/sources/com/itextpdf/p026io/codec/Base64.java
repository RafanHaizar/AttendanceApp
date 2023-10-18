package com.itextpdf.p026io.codec;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.itextpdf.io.codec.Base64 */
public class Base64 {
    public static final int DECODE = 0;
    public static final int DONT_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "UTF-8";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, Tnaf.POW_2_WIDTH, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, 62, 63, -9, -9, -9, -9};
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, Tnaf.POW_2_WIDTH, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, Tnaf.POW_2_WIDTH, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};

    /* access modifiers changed from: private */
    public static byte[] getAlphabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    /* access modifiers changed from: private */
    public static byte[] getDecodabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64() {
    }

    private static void usage(String msg) {
        System.err.println(msg);
        System.err.println("Usage: java Base64 -e|-d inputfile outputfile");
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
        return b4;
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
        byte[] ALPHABET = getAlphabet(options);
        int i = 0;
        int i2 = (numSigBytes > 0 ? (source[srcOffset] << 24) >>> 8 : 0) | (numSigBytes > 1 ? (source[srcOffset + 1] << 24) >>> 16 : 0);
        if (numSigBytes > 2) {
            i = (source[srcOffset + 2] << 24) >>> 24;
        }
        int inBuff = i | i2;
        switch (numSigBytes) {
            case 1:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            case 2:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            case 3:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                return destination;
            default:
                return destination;
        }
    }

    public static String encodeObject(Serializable serializableObject) {
        return encodeObject(serializableObject, 0);
    }

    public static String encodeObject(Serializable serializableObject, int options) {
        ObjectOutputStream oos;
        ByteArrayOutputStream baos = null;
        java.io.OutputStream b64os = null;
        ObjectOutputStream oos2 = null;
        GZIPOutputStream gzos = null;
        int gzip = options & 2;
        int i = options & 8;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            java.io.OutputStream b64os2 = new OutputStream(baos2, options | 1);
            if (gzip == 2) {
                gzos = new GZIPOutputStream(b64os2);
                oos = new ObjectOutputStream(gzos);
            } else {
                oos = new ObjectOutputStream(b64os2);
            }
            oos.writeObject(serializableObject);
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                gzos.close();
            } catch (Exception e2) {
            }
            try {
                b64os2.close();
            } catch (Exception e3) {
            }
            try {
                baos2.close();
            } catch (Exception e4) {
            }
            try {
                return new String(baos2.toByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException e5) {
                return new String(baos2.toByteArray());
            }
        } catch (IOException e6) {
            e6.printStackTrace();
            try {
                oos2.close();
            } catch (Exception e7) {
            }
            try {
                gzos.close();
            } catch (Exception e8) {
            }
            try {
                b64os.close();
            } catch (Exception e9) {
            }
            try {
                baos.close();
                return null;
            } catch (Exception e10) {
                return null;
            }
        } catch (Throwable uue) {
            try {
                oos2.close();
            } catch (Exception e11) {
            }
            try {
                gzos.close();
            } catch (Exception e12) {
            }
            try {
                b64os.close();
            } catch (Exception e13) {
            }
            try {
                baos.close();
            } catch (Exception e14) {
            }
            throw uue;
        }
    }

    public static String encodeBytes(byte[] source) {
        return encodeBytes(source, 0, source.length, 0);
    }

    public static String encodeBytes(byte[] source, int options) {
        return encodeBytes(source, 0, source.length, options);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        return encodeBytes(source, off, len, 0);
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) {
        int e;
        int i = off;
        int i2 = len;
        int dontBreakLines = options & 8;
        if ((options & 2) == 2) {
            ByteArrayOutputStream baos = null;
            GZIPOutputStream gzos = null;
            OutputStream b64os = null;
            try {
                baos = new ByteArrayOutputStream();
                b64os = new OutputStream(baos, options | 1);
                gzos = new GZIPOutputStream(b64os);
                try {
                    gzos.write(source, i, i2);
                    gzos.close();
                    try {
                        gzos.close();
                    } catch (Exception e2) {
                    }
                    try {
                        b64os.close();
                    } catch (Exception e3) {
                    }
                    try {
                        baos.close();
                    } catch (Exception e4) {
                    }
                    try {
                        return new String(baos.toByteArray(), "UTF-8");
                    } catch (UnsupportedEncodingException e5) {
                        return new String(baos.toByteArray());
                    }
                } catch (IOException e6) {
                    e = e6;
                    try {
                        e.printStackTrace();
                        try {
                            gzos.close();
                        } catch (Exception e7) {
                        }
                        try {
                            b64os.close();
                        } catch (Exception e8) {
                        }
                        try {
                            baos.close();
                            return null;
                        } catch (Exception e9) {
                            return null;
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            } catch (IOException e10) {
                e = e10;
                byte[] bArr = source;
                e.printStackTrace();
                gzos.close();
                b64os.close();
                baos.close();
                return null;
            } catch (Throwable th2) {
                th = th2;
                byte[] bArr2 = source;
                Throwable th3 = th;
                try {
                    gzos.close();
                } catch (Exception e11) {
                }
                try {
                    b64os.close();
                } catch (Exception e12) {
                }
                try {
                    baos.close();
                } catch (Exception e13) {
                }
                throw th3;
            }
        } else {
            byte[] bArr3 = source;
            boolean breakLines = dontBreakLines == 0;
            int len43 = (i2 * 4) / 3;
            byte[] outBuff = new byte[((i2 % 3 > 0 ? 4 : 0) + len43 + (breakLines ? len43 / 76 : 0))];
            int len2 = i2 - 2;
            int d = 0;
            int e14 = 0;
            int lineLength = 0;
            while (d < len2) {
                int d2 = d;
                int len22 = len2;
                int dontBreakLines2 = dontBreakLines;
                byte[] outBuff2 = outBuff;
                encode3to4(source, d + i, 3, outBuff, e14, options);
                int lineLength2 = lineLength + 4;
                if (breakLines && lineLength2 == 76) {
                    outBuff2[e14 + 4] = 10;
                    e14++;
                    lineLength2 = 0;
                }
                lineLength = lineLength2;
                d = d2 + 3;
                e14 += 4;
                outBuff = outBuff2;
                len2 = len22;
                dontBreakLines = dontBreakLines2;
            }
            int d3 = d;
            int i3 = len2;
            int i4 = dontBreakLines;
            byte[] outBuff3 = outBuff;
            if (d3 < i2) {
                encode3to4(source, d3 + i, i2 - d3, outBuff3, e14, options);
                e = e14 + 4;
            } else {
                e = e14;
            }
            try {
                return new String(outBuff3, 0, e, "UTF-8");
            } catch (UnsupportedEncodingException e15) {
                return new String(outBuff3, 0, e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
        byte[] DECODABET = getDecodabet(options);
        if (source[srcOffset + 2] == 61) {
            destination[destOffset] = (byte) ((((DECODABET[source[srcOffset]] & 255) << 18) | ((DECODABET[source[srcOffset + 1]] & 255) << 12)) >>> 16);
            return 1;
        } else if (source[srcOffset + 3] == 61) {
            int outBuff = ((DECODABET[source[srcOffset]] & 255) << 18) | ((DECODABET[source[srcOffset + 1]] & 255) << 12) | ((DECODABET[source[srcOffset + 2]] & 255) << 6);
            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        } else {
            try {
                int outBuff2 = ((DECODABET[source[srcOffset]] & 255) << 18) | ((DECODABET[source[srcOffset + 1]] & 255) << 12) | ((DECODABET[source[srcOffset + 2]] & 255) << 6) | (DECODABET[source[srcOffset + 3]] & 255);
                destination[destOffset] = (byte) (outBuff2 >> 16);
                destination[destOffset + 1] = (byte) (outBuff2 >> 8);
                destination[destOffset + 2] = (byte) outBuff2;
                return 3;
            } catch (Exception e) {
                System.out.println("" + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
                System.out.println("" + source[srcOffset + 1] + ": " + DECODABET[source[srcOffset + 1]]);
                System.out.println("" + source[srcOffset + 2] + ": " + DECODABET[source[srcOffset + 2]]);
                System.out.println("" + source[srcOffset + 3] + ": " + DECODABET[source[srcOffset + 3]]);
                return -1;
            }
        }
    }

    public static byte[] decode(byte[] source, int off, int len, int options) {
        byte[] DECODABET = getDecodabet(options);
        byte[] outBuff = new byte[((len * 3) / 4)];
        int outBuffPosn = 0;
        byte[] b4 = new byte[4];
        int b4Posn = 0;
        int i = off;
        while (i < off + len) {
            byte sbiCrop = (byte) (source[i] & ByteCompanionObject.MAX_VALUE);
            byte sbiDecode = DECODABET[sbiCrop];
            if (sbiDecode >= -5) {
                if (sbiDecode >= -1) {
                    int b4Posn2 = b4Posn + 1;
                    b4[b4Posn] = sbiCrop;
                    if (b4Posn2 > 3) {
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
                        b4Posn = 0;
                        if (sbiCrop == 61) {
                            break;
                        }
                    } else {
                        b4Posn = b4Posn2;
                    }
                }
                i++;
            } else {
                System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
                return null;
            }
        }
        byte[] out = new byte[outBuffPosn];
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
        return out;
    }

    public static byte[] decode(String s) {
        return decode(s, 0);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0060=Splitter:B:25:0x0060, B:46:0x0082=Splitter:B:46:0x0082} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r9, int r10) {
        /*
            java.lang.String r0 = "UTF-8"
            byte[] r0 = r9.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x0007 }
            goto L_0x000d
        L_0x0007:
            r0 = move-exception
            byte[] r1 = r9.getBytes()
            r0 = r1
        L_0x000d:
            int r1 = r0.length
            r2 = 0
            byte[] r0 = decode(r0, r2, r1, r10)
            if (r0 == 0) goto L_0x0086
            int r1 = r0.length
            r3 = 4
            if (r1 < r3) goto L_0x0086
            byte r1 = r0[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r3 = 1
            byte r3 = r0[r3]
            int r3 = r3 << 8
            r4 = 65280(0xff00, float:9.1477E-41)
            r3 = r3 & r4
            r1 = r1 | r3
            r3 = 35615(0x8b1f, float:4.9907E-41)
            if (r3 != r1) goto L_0x0086
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 2048(0x800, float:2.87E-42)
            byte[] r6 = new byte[r6]
            r7 = 0
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r8.<init>()     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r5 = r8
            java.io.ByteArrayInputStream r8 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r8.<init>(r0)     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r3 = r8
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r8.<init>(r3)     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r4 = r8
        L_0x0046:
            int r8 = r4.read(r6)     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r7 = r8
            if (r8 < 0) goto L_0x0051
            r5.write(r6, r2, r7)     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            goto L_0x0046
        L_0x0051:
            byte[] r2 = r5.toByteArray()     // Catch:{ IOException -> 0x0077, all -> 0x0066 }
            r0 = r2
            r5.close()     // Catch:{ Exception -> 0x005a }
            goto L_0x005b
        L_0x005a:
            r2 = move-exception
        L_0x005b:
            r4.close()     // Catch:{ Exception -> 0x005f }
            goto L_0x0060
        L_0x005f:
            r2 = move-exception
        L_0x0060:
            r3.close()     // Catch:{ Exception -> 0x0064 }
        L_0x0063:
            goto L_0x0086
        L_0x0064:
            r2 = move-exception
            goto L_0x0086
        L_0x0066:
            r2 = move-exception
            r5.close()     // Catch:{ Exception -> 0x006b }
            goto L_0x006c
        L_0x006b:
            r8 = move-exception
        L_0x006c:
            r4.close()     // Catch:{ Exception -> 0x0070 }
            goto L_0x0071
        L_0x0070:
            r8 = move-exception
        L_0x0071:
            r3.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x0076
        L_0x0075:
            r8 = move-exception
        L_0x0076:
            throw r2
        L_0x0077:
            r2 = move-exception
            r5.close()     // Catch:{ Exception -> 0x007c }
            goto L_0x007d
        L_0x007c:
            r2 = move-exception
        L_0x007d:
            r4.close()     // Catch:{ Exception -> 0x0081 }
            goto L_0x0082
        L_0x0081:
            r2 = move-exception
        L_0x0082:
            r3.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x0063
        L_0x0086:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.Base64.decode(java.lang.String, int):byte[]");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x003b=Splitter:B:25:0x003b, B:7:0x001d=Splitter:B:7:0x001d, B:17:0x002e=Splitter:B:17:0x002e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object decodeToObject(java.lang.String r6) {
        /*
            byte[] r0 = decode(r6)
            r1 = 0
            r2 = 0
            r3 = 0
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0025 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0025 }
            r1 = r4
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0025 }
            r4.<init>(r1)     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0025 }
            r2 = r4
            java.lang.Object r4 = r2.readObject()     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0025 }
            r3 = r4
            r1.close()     // Catch:{ Exception -> 0x001c }
            goto L_0x001d
        L_0x001c:
            r4 = move-exception
        L_0x001d:
            r2.close()     // Catch:{ Exception -> 0x0021 }
        L_0x0020:
            goto L_0x003f
        L_0x0021:
            r4 = move-exception
            goto L_0x003f
        L_0x0023:
            r4 = move-exception
            goto L_0x0040
        L_0x0025:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0023 }
            r1.close()     // Catch:{ Exception -> 0x002d }
            goto L_0x002e
        L_0x002d:
            r4 = move-exception
        L_0x002e:
            r2.close()     // Catch:{ Exception -> 0x0021 }
            goto L_0x0020
        L_0x0032:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0023 }
            r1.close()     // Catch:{ Exception -> 0x003a }
            goto L_0x003b
        L_0x003a:
            r4 = move-exception
        L_0x003b:
            r2.close()     // Catch:{ Exception -> 0x0021 }
            goto L_0x0020
        L_0x003f:
            return r3
        L_0x0040:
            r1.close()     // Catch:{ Exception -> 0x0044 }
            goto L_0x0045
        L_0x0044:
            r5 = move-exception
        L_0x0045:
            r2.close()     // Catch:{ Exception -> 0x0049 }
            goto L_0x004a
        L_0x0049:
            r5 = move-exception
        L_0x004a:
            goto L_0x004c
        L_0x004b:
            throw r4
        L_0x004c:
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.Base64.decodeToObject(java.lang.String):java.lang.Object");
    }

    public static boolean encodeToFile(byte[] dataToEncode, String filename) {
        boolean success;
        OutputStream bos = null;
        try {
            bos = new OutputStream(new FileOutputStream(filename), 1);
            bos.write(dataToEncode);
            success = true;
            try {
                bos.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            success = false;
            bos.close();
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Exception e3) {
            }
            throw th;
        }
        return success;
    }

    public static boolean decodeToFile(String dataToDecode, String filename) {
        boolean success;
        OutputStream bos = null;
        try {
            bos = new OutputStream(new FileOutputStream(filename), 0);
            bos.write(dataToDecode.getBytes("UTF-8"));
            success = true;
            try {
                bos.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            success = false;
            bos.close();
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Exception e3) {
            }
            throw th;
        }
        return success;
    }

    public static byte[] decodeFromFile(String filename) {
        byte[] decodedData = null;
        InputStream bis = null;
        try {
            File file = new File(filename);
            int length = 0;
            if (file.length() > 2147483647L) {
                System.err.println("File is too big for this convenience method (" + file.length() + " bytes).");
                if (bis == null) {
                    return null;
                }
                try {
                    bis.close();
                    return null;
                } catch (Exception e) {
                    return null;
                }
            } else {
                byte[] buffer = new byte[((int) file.length())];
                InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
                while (true) {
                    int read = bis2.read(buffer, length, 4096);
                    int numBytes = read;
                    if (read < 0) {
                        break;
                    }
                    length += numBytes;
                }
                decodedData = new byte[length];
                System.arraycopy(buffer, 0, decodedData, 0, length);
                try {
                    bis2.close();
                } catch (Exception e2) {
                }
                return decodedData;
            }
        } catch (IOException e3) {
            System.err.println("Error decoding from file " + filename);
            if (bis != null) {
                bis.close();
            }
        } catch (Throwable th) {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static String encodeFromFile(String filename) {
        String encodedData = null;
        InputStream bis = null;
        try {
            File file = new File(filename);
            double length = (double) file.length();
            Double.isNaN(length);
            byte[] buffer = new byte[Math.max((int) (length * 1.4d), 40)];
            int length2 = 0;
            InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
            while (true) {
                int read = bis2.read(buffer, length2, 4096);
                int numBytes = read;
                if (read < 0) {
                    break;
                }
                length2 += numBytes;
            }
            encodedData = new String(buffer, 0, length2, "UTF-8");
            try {
                bis2.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            System.err.println("Error encoding from file " + filename);
            bis.close();
        } catch (Throwable th) {
            try {
                bis.close();
            } catch (Exception e3) {
            }
            throw th;
        }
        return encodedData;
    }

    public static void encodeFileToFile(String infile, String outfile) {
        String encoded = encodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(encoded.getBytes("US-ASCII"));
            try {
                out.close();
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            out.close();
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Exception e2) {
            }
            throw th;
        }
    }

    public static void decodeFileToFile(String infile, String outfile) {
        byte[] decoded = decodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(decoded);
            try {
                out.close();
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            out.close();
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Exception e2) {
            }
            throw th;
        }
    }

    /* renamed from: com.itextpdf.io.codec.Base64$InputStream */
    public static class InputStream extends FilterInputStream {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private byte[] alphabet;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        static {
            Class<Base64> cls = Base64.class;
        }

        public InputStream(java.io.InputStream in) {
            this(in, 0);
        }

        public InputStream(java.io.InputStream in, int options2) {
            super(in);
            boolean z = true;
            this.breakLines = (options2 & 8) != 8;
            z = (options2 & 1) != 1 ? false : z;
            this.encode = z;
            int i = z ? 4 : 3;
            this.bufferLength = i;
            this.buffer = new byte[i];
            this.position = -1;
            this.lineLength = 0;
            this.options = options2;
            this.alphabet = Base64.getAlphabet(options2);
            this.decodabet = Base64.getDecodabet(options2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x0056 A[LOOP:1: B:19:0x003f->B:26:0x0056, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:58:0x005c A[EDGE_INSN: B:58:0x005c->B:27:0x005c ?: BREAK  , SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int read() throws java.io.IOException {
            /*
                r12 = this;
                int r0 = r12.position
                r1 = -1
                r2 = 0
                if (r0 >= 0) goto L_0x0076
                boolean r0 = r12.encode
                r3 = 4
                if (r0 == 0) goto L_0x003b
                r0 = 3
                byte[] r10 = new byte[r0]
                r4 = 0
                r5 = 0
                r11 = r4
            L_0x0011:
                if (r5 >= r0) goto L_0x0028
                java.io.InputStream r4 = r12.in     // Catch:{ IOException -> 0x0021 }
                int r4 = r4.read()     // Catch:{ IOException -> 0x0021 }
                if (r4 < 0) goto L_0x0020
                byte r6 = (byte) r4     // Catch:{ IOException -> 0x0021 }
                r10[r5] = r6     // Catch:{ IOException -> 0x0021 }
                int r11 = r11 + 1
            L_0x0020:
                goto L_0x0024
            L_0x0021:
                r4 = move-exception
                if (r5 == 0) goto L_0x0027
            L_0x0024:
                int r5 = r5 + 1
                goto L_0x0011
            L_0x0027:
                throw r4
            L_0x0028:
                if (r11 <= 0) goto L_0x003a
                r5 = 0
                byte[] r7 = r12.buffer
                r8 = 0
                int r9 = r12.options
                r4 = r10
                r6 = r11
                byte[] unused = com.itextpdf.p026io.codec.Base64.encode3to4(r4, r5, r6, r7, r8, r9)
                r12.position = r2
                r12.numSigBytes = r3
                goto L_0x0076
            L_0x003a:
                return r1
            L_0x003b:
                byte[] r0 = new byte[r3]
                r4 = 0
                r4 = 0
            L_0x003f:
                if (r4 >= r3) goto L_0x005c
                r5 = 0
            L_0x0042:
                java.io.InputStream r6 = r12.in
                int r5 = r6.read()
                if (r5 < 0) goto L_0x0053
                byte[] r6 = r12.decodabet
                r7 = r5 & 127(0x7f, float:1.78E-43)
                byte r6 = r6[r7]
                r7 = -5
                if (r6 <= r7) goto L_0x0042
            L_0x0053:
                if (r5 >= 0) goto L_0x0056
                goto L_0x005c
            L_0x0056:
                byte r6 = (byte) r5
                r0[r4] = r6
                int r4 = r4 + 1
                goto L_0x003f
            L_0x005c:
                if (r4 != r3) goto L_0x006b
                byte[] r3 = r12.buffer
                int r5 = r12.options
                int r3 = com.itextpdf.p026io.codec.Base64.decode4to3(r0, r2, r3, r2, r5)
                r12.numSigBytes = r3
                r12.position = r2
                goto L_0x0076
            L_0x006b:
                if (r4 != 0) goto L_0x006e
                return r1
            L_0x006e:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r2 = "improperly.padded.base64.input"
                r1.<init>(r2)
                throw r1
            L_0x0076:
                int r0 = r12.position
                if (r0 < 0) goto L_0x00a9
                int r3 = r12.numSigBytes
                if (r0 < r3) goto L_0x007f
                return r1
            L_0x007f:
                boolean r3 = r12.encode
                if (r3 == 0) goto L_0x0092
                boolean r3 = r12.breakLines
                if (r3 == 0) goto L_0x0092
                int r3 = r12.lineLength
                r4 = 76
                if (r3 < r4) goto L_0x0092
                r12.lineLength = r2
                r0 = 10
                return r0
            L_0x0092:
                int r2 = r12.lineLength
                int r2 = r2 + 1
                r12.lineLength = r2
                byte[] r2 = r12.buffer
                int r3 = r0 + 1
                r12.position = r3
                byte r0 = r2[r0]
                int r2 = r12.bufferLength
                if (r3 < r2) goto L_0x00a6
                r12.position = r1
            L_0x00a6:
                r1 = r0 & 255(0xff, float:3.57E-43)
                return r1
            L_0x00a9:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                goto L_0x00b0
            L_0x00af:
                throw r0
            L_0x00b0:
                goto L_0x00af
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.Base64.InputStream.read():int");
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i = 0;
            while (true) {
                if (i >= len) {
                    break;
                }
                int b = read();
                if (b >= 0) {
                    dest[off + i] = (byte) b;
                    i++;
                } else if (i == 0) {
                    return -1;
                }
            }
            return i;
        }
    }

    /* renamed from: com.itextpdf.io.codec.Base64$OutputStream */
    public static class OutputStream extends FilterOutputStream {
        private byte[] alphabet;

        /* renamed from: b4 */
        private byte[] f1197b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream out) {
            this(out, 1);
        }

        public OutputStream(java.io.OutputStream out, int options2) {
            super(out);
            boolean z = true;
            this.breakLines = (options2 & 8) != 8;
            z = (options2 & 1) != 1 ? false : z;
            this.encode = z;
            int i = z ? 3 : 4;
            this.bufferLength = i;
            this.buffer = new byte[i];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.f1197b4 = new byte[4];
            this.options = options2;
            this.alphabet = Base64.getAlphabet(options2);
            this.decodabet = Base64.getDecodabet(options2);
        }

        public void write(int theByte) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theByte);
            } else if (this.encode) {
                byte[] bArr = this.buffer;
                int i = this.position;
                int i2 = i + 1;
                this.position = i2;
                bArr[i] = (byte) theByte;
                if (i2 >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.f1197b4, this.buffer, this.bufferLength, this.options));
                    int i3 = this.lineLength + 4;
                    this.lineLength = i3;
                    if (this.breakLines && i3 >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else {
                byte[] bArr2 = this.decodabet;
                if (bArr2[theByte & 127] > -5) {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    int i5 = i4 + 1;
                    this.position = i5;
                    bArr3[i4] = (byte) theByte;
                    if (i5 >= this.bufferLength) {
                        this.out.write(this.f1197b4, 0, Base64.decode4to3(bArr3, 0, this.f1197b4, 0, this.options));
                        this.position = 0;
                    }
                } else if (bArr2[theByte & 127] != -5) {
                    throw new IOException("invalid.character.in.base64.data");
                }
            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theBytes, off, len);
                return;
            }
            for (int i = 0; i < len; i++) {
                write(theBytes[off + i]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64.encode3to4(this.f1197b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("base64.input.not.properly.padded");
        }

        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }
    }
}

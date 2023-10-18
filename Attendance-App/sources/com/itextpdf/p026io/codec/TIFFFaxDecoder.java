package com.itextpdf.p026io.codec;

import com.itextpdf.p026io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.signers.PSSSigner;

/* renamed from: com.itextpdf.io.codec.TIFFFaxDecoder */
public class TIFFFaxDecoder {
    public static short[] additionalMakeup = {28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567};
    static short[] black = {62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390};
    public static byte[] flipTable = {0, ByteCompanionObject.MIN_VALUE, 64, -64, 32, -96, 96, -32, Tnaf.POW_2_WIDTH, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, PSSSigner.TRAILER_IMPLICIT, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, ByteCompanionObject.MAX_VALUE, -1};
    static short[] initBlack = {3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68};
    static int[] table1 = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    static int[] table2 = {0, 128, 192, 224, 240, 248, 252, TIFFConstants.TIFFTAG_SUBFILETYPE, 255};
    static short[] twoBitBlack = {292, 260, 226, 226};
    static byte[] twoDCodes = {80, 88, 23, 71, 30, 30, 62, 62, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41};
    static short[] white = {6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232};
    private int bitPointer;
    private int bytePointer;
    private int changingElemSize = 0;
    private int compression = 2;
    private int[] currChangingElems;
    private byte[] data;
    private int fillBits = 0;
    private int fillOrder;

    /* renamed from: h */
    private int f1200h;
    private int lastChangingElement = 0;
    private int oneD;
    private int[] prevChangingElems;
    private boolean recoverFromImageError;
    private int uncompressedMode = 0;

    /* renamed from: w */
    private int f1201w;

    public TIFFFaxDecoder(int fillOrder2, int w, int h) {
        this.fillOrder = fillOrder2;
        this.f1201w = w;
        this.f1200h = h;
        this.bitPointer = 0;
        this.bytePointer = 0;
        this.prevChangingElems = new int[(w * 2)];
        this.currChangingElems = new int[(w * 2)];
    }

    public static void reverseBits(byte[] b) {
        for (int k = 0; k < b.length; k++) {
            b[k] = flipTable[b[k] & UByte.MAX_VALUE];
        }
    }

    public void decode1D(byte[] buffer, byte[] compData, int startX, int height) {
        this.data = compData;
        int lineOffset = 0;
        int scanlineStride = (this.f1201w + 7) / 8;
        this.bitPointer = 0;
        this.bytePointer = 0;
        for (int i = 0; i < height; i++) {
            decodeNextScanline(buffer, lineOffset, startX);
            lineOffset += scanlineStride;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0072, code lost:
        if (r3 != r0.f1201w) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0076, code lost:
        if (r0.compression != 2) goto L_0x0139;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0078, code lost:
        advancePointer();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007d, code lost:
        if (r4 != false) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007f, code lost:
        r11 = initBlack[nextLesserThan8Bits(4)];
        r12 = (r11 >>> 1) & 15;
        r13 = (r11 >>> 5) & 2047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0091, code lost:
        if (r13 != 100) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0093, code lost:
        r11 = black[nextNBits(9)];
        r14 = r11 & 1;
        r12 = (r11 >>> 1) & 15;
        r13 = (r11 >>> 5) & 2047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a7, code lost:
        if (r12 != r6) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a9, code lost:
        updatePointer(5);
        r10 = additionalMakeup[nextLesserThan8Bits(4)];
        r12 = (r10 >>> 4) & 4095;
        setToBlack(r1, r2, r3, r12);
        r3 = r3 + r12;
        updatePointer(4 - ((r10 >>> 1) & 7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c7, code lost:
        if (r12 == 15) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c9, code lost:
        setToBlack(r1, r2, r3, r13);
        r3 = r3 + r13;
        updatePointer(9 - r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d2, code lost:
        if (r14 != 0) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d4, code lost:
        r4 = true;
        r9 = r0.currChangingElems;
        r15 = r0.changingElemSize;
        r0.changingElemSize = r15 + 1;
        r9[r15] = r3;
        r6 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e2, code lost:
        r6 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ea, code lost:
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.EolCodeWordEncounteredInWhiteRun);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ed, code lost:
        if (r13 != 200) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ef, code lost:
        r9 = twoBitBlack[nextLesserThan8Bits(2)];
        r10 = (r9 >>> 5) & 2047;
        setToBlack(r1, r2, r3, r10);
        r3 = r3 + r10;
        updatePointer(2 - ((r9 >>> 1) & 15));
        r4 = true;
        r12 = r0.currChangingElems;
        r13 = r0.changingElemSize;
        r0.changingElemSize = r13 + 1;
        r12[r13] = r3;
        r6 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0116, code lost:
        setToBlack(r1, r2, r3, r13);
        r3 = r3 + r13;
        updatePointer(4 - r12);
        r4 = true;
        r6 = r0.currChangingElems;
        r9 = r0.changingElemSize;
        r0.changingElemSize = r9 + 1;
        r6[r9] = r3;
        r6 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0134, code lost:
        if (r0.compression != 2) goto L_0x0139;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0136, code lost:
        advancePointer();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decodeNextScanline(byte[] r17, int r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = 1
            r4 = 0
            r0.changingElemSize = r4
            r4 = r3
            r3 = r19
        L_0x000d:
            int r5 = r0.f1201w
            if (r3 >= r5) goto L_0x0139
        L_0x0011:
            java.lang.String r5 = "EOL code word encountered in White run."
            r6 = 12
            r7 = 2
            r8 = 15
            if (r4 == 0) goto L_0x0070
            r9 = 10
            int r9 = r0.nextNBits(r9)
            short[] r10 = white
            short r10 = r10[r9]
            r11 = r10 & 1
            int r12 = r10 >>> 1
            r12 = r12 & r8
            if (r12 != r6) goto L_0x0046
            int r5 = r0.nextLesserThan8Bits(r7)
            int r7 = r9 << 2
            r6 = r6 & r7
            r6 = r6 | r5
            short[] r7 = additionalMakeup
            short r7 = r7[r6]
            int r8 = r7 >>> 1
            r8 = r8 & 7
            int r9 = r7 >>> 4
            r9 = r9 & 4095(0xfff, float:5.738E-42)
            int r3 = r3 + r9
            int r10 = 4 - r8
            r0.updatePointer(r10)
            goto L_0x0011
        L_0x0046:
            if (r12 == 0) goto L_0x0068
            if (r12 == r8) goto L_0x0062
            int r5 = r10 >>> 5
            r5 = r5 & 2047(0x7ff, float:2.868E-42)
            int r3 = r3 + r5
            int r6 = 10 - r12
            r0.updatePointer(r6)
            if (r11 != 0) goto L_0x0011
            r4 = 0
            int[] r6 = r0.currChangingElems
            int r7 = r0.changingElemSize
            int r8 = r7 + 1
            r0.changingElemSize = r8
            r6[r7] = r3
            goto L_0x0011
        L_0x0062:
            com.itextpdf.io.IOException r6 = new com.itextpdf.io.IOException
            r6.<init>((java.lang.String) r5)
            throw r6
        L_0x0068:
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "Invalid code encountered."
            r5.<init>((java.lang.String) r6)
            throw r5
        L_0x0070:
            int r9 = r0.f1201w
            if (r3 != r9) goto L_0x007d
            int r5 = r0.compression
            if (r5 != r7) goto L_0x0139
            r16.advancePointer()
            goto L_0x0139
        L_0x007d:
            if (r4 != 0) goto L_0x012e
            r9 = 4
            int r10 = r0.nextLesserThan8Bits(r9)
            short[] r11 = initBlack
            short r11 = r11[r10]
            int r12 = r11 >>> 1
            r12 = r12 & r8
            int r13 = r11 >>> 5
            r13 = r13 & 2047(0x7ff, float:2.868E-42)
            r14 = 100
            if (r13 != r14) goto L_0x00eb
            r14 = 9
            int r10 = r0.nextNBits(r14)
            short[] r14 = black
            short r11 = r14[r10]
            r14 = r11 & 1
            int r15 = r11 >>> 1
            r12 = r15 & 15
            int r15 = r11 >>> 5
            r13 = r15 & 2047(0x7ff, float:2.868E-42)
            if (r12 != r6) goto L_0x00c7
            r15 = 5
            r0.updatePointer(r15)
            int r9 = r0.nextLesserThan8Bits(r9)
            short[] r10 = additionalMakeup
            short r10 = r10[r9]
            int r11 = r10 >>> 1
            r11 = r11 & 7
            int r12 = r10 >>> 4
            r12 = r12 & 4095(0xfff, float:5.738E-42)
            r0.setToBlack(r1, r2, r3, r12)
            int r3 = r3 + r12
            int r13 = 4 - r11
            r0.updatePointer(r13)
            goto L_0x007d
        L_0x00c7:
            if (r12 == r8) goto L_0x00e5
            r0.setToBlack(r1, r2, r3, r13)
            int r3 = r3 + r13
            int r9 = 9 - r12
            r0.updatePointer(r9)
            if (r14 != 0) goto L_0x00e2
            r4 = 1
            int[] r9 = r0.currChangingElems
            int r15 = r0.changingElemSize
            int r6 = r15 + 1
            r0.changingElemSize = r6
            r9[r15] = r3
            r6 = 12
            goto L_0x007d
        L_0x00e2:
            r6 = 12
            goto L_0x007d
        L_0x00e5:
            com.itextpdf.io.IOException r6 = new com.itextpdf.io.IOException
            r6.<init>((java.lang.String) r5)
            throw r6
        L_0x00eb:
            r6 = 200(0xc8, float:2.8E-43)
            if (r13 != r6) goto L_0x0116
            int r6 = r0.nextLesserThan8Bits(r7)
            short[] r9 = twoBitBlack
            short r9 = r9[r6]
            int r10 = r9 >>> 5
            r10 = r10 & 2047(0x7ff, float:2.868E-42)
            int r11 = r9 >>> 1
            r11 = r11 & r8
            r0.setToBlack(r1, r2, r3, r10)
            int r3 = r3 + r10
            int r12 = 2 - r11
            r0.updatePointer(r12)
            r4 = 1
            int[] r12 = r0.currChangingElems
            int r13 = r0.changingElemSize
            int r14 = r13 + 1
            r0.changingElemSize = r14
            r12[r13] = r3
            r6 = 12
            goto L_0x007d
        L_0x0116:
            r0.setToBlack(r1, r2, r3, r13)
            int r3 = r3 + r13
            int r6 = 4 - r12
            r0.updatePointer(r6)
            r4 = 1
            int[] r6 = r0.currChangingElems
            int r9 = r0.changingElemSize
            int r14 = r9 + 1
            r0.changingElemSize = r14
            r6[r9] = r3
            r6 = 12
            goto L_0x007d
        L_0x012e:
            int r5 = r0.f1201w
            if (r3 != r5) goto L_0x000d
            int r5 = r0.compression
            if (r5 != r7) goto L_0x0139
            r16.advancePointer()
        L_0x0139:
            int[] r5 = r0.currChangingElems
            int r6 = r0.changingElemSize
            int r7 = r6 + 1
            r0.changingElemSize = r7
            r5[r6] = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.TIFFFaxDecoder.decodeNextScanline(byte[], int, int):void");
    }

    public void decode2D(byte[] buffer, byte[] compData, int startX, int height, long tiffT4Options) {
        char c;
        int currIndex;
        byte[] bArr = buffer;
        int i = startX;
        this.data = compData;
        this.compression = 3;
        boolean z = false;
        this.bitPointer = 0;
        this.bytePointer = 0;
        int scanlineStride = (this.f1201w + 7) / 8;
        int[] b = new int[2];
        this.oneD = (int) (tiffT4Options & 1);
        this.uncompressedMode = (int) ((tiffT4Options & 2) >> 1);
        this.fillBits = (int) ((tiffT4Options & 4) >> 2);
        if (readEOL(true) == 1) {
            decodeNextScanline(bArr, 0, i);
            int lineOffset = 0 + scanlineStride;
            int lines = 1;
            while (lines < height) {
                if (readEOL(z) == 0) {
                    int[] temp = this.prevChangingElems;
                    this.prevChangingElems = this.currChangingElems;
                    this.currChangingElems = temp;
                    int currIndex2 = 0;
                    this.lastChangingElement = z ? 1 : 0;
                    int a0 = -1;
                    boolean isWhite = true;
                    int bitOffset = startX;
                    char c2 = z;
                    while (bitOffset < this.f1201w) {
                        getNextChangingElement(a0, isWhite, b);
                        int b1 = b[c2];
                        int b2 = b[1];
                        int i2 = twoDCodes[nextLesserThan8Bits(7)] & 255;
                        int code = (i2 & 120) >>> 3;
                        int bits = i2 & 7;
                        if (code == 0) {
                            if (!isWhite) {
                                int i3 = i2;
                                setToBlack(bArr, lineOffset, bitOffset, b2 - bitOffset);
                            } else {
                                int entry = i2;
                            }
                            a0 = b2;
                            bitOffset = b2;
                            updatePointer(7 - bits);
                            byte[] bArr2 = compData;
                            c = 0;
                        } else {
                            int entry2 = i2;
                            if (code == 1) {
                                updatePointer(7 - bits);
                                if (isWhite) {
                                    int number = decodeWhiteCodeWord();
                                    int bitOffset2 = bitOffset + number;
                                    int i4 = number;
                                    int currIndex3 = currIndex2 + 1;
                                    this.currChangingElems[currIndex2] = bitOffset2;
                                    int number2 = decodeBlackCodeWord();
                                    setToBlack(bArr, lineOffset, bitOffset2, number2);
                                    bitOffset = bitOffset2 + number2;
                                    currIndex = currIndex3 + 1;
                                    this.currChangingElems[currIndex3] = bitOffset;
                                } else {
                                    int number3 = decodeBlackCodeWord();
                                    setToBlack(bArr, lineOffset, bitOffset, number3);
                                    int bitOffset3 = bitOffset + number3;
                                    int i5 = number3;
                                    int currIndex4 = currIndex2 + 1;
                                    this.currChangingElems[currIndex2] = bitOffset3;
                                    bitOffset = bitOffset3 + decodeWhiteCodeWord();
                                    currIndex = currIndex4 + 1;
                                    this.currChangingElems[currIndex4] = bitOffset;
                                }
                                currIndex2 = currIndex;
                                a0 = bitOffset;
                                byte[] bArr3 = compData;
                                c = 0;
                            } else if (code <= 8) {
                                int a1 = b1 + (code - 5);
                                int currIndex5 = currIndex2 + 1;
                                this.currChangingElems[currIndex2] = a1;
                                if (!isWhite) {
                                    setToBlack(bArr, lineOffset, bitOffset, a1 - bitOffset);
                                }
                                a0 = a1;
                                bitOffset = a1;
                                isWhite = !isWhite;
                                updatePointer(7 - bits);
                                byte[] bArr4 = compData;
                                currIndex2 = currIndex5;
                                c = 0;
                            } else {
                                int i6 = code;
                                throw new IOException(IOException.InvalidCodeEncounteredWhileDecoding2dGroup3CompressedData);
                            }
                        }
                        c2 = c;
                    }
                    this.currChangingElems[currIndex2] = bitOffset;
                    this.changingElemSize = currIndex2 + 1;
                } else {
                    decodeNextScanline(bArr, lineOffset, i);
                }
                lineOffset += scanlineStride;
                lines++;
                byte[] bArr5 = compData;
                z = false;
            }
            return;
        }
        int i7 = height;
        throw new IOException(IOException.FirstScanlineMustBe1dEncoded);
    }

    public void decodeT6(byte[] buffer, byte[] compData, int startX, int height, long tiffT6Options) {
        int i;
        int[] b;
        int b1;
        int code;
        int bitOffset;
        boolean isWhite;
        int i2;
        byte[] bArr = buffer;
        this.data = compData;
        this.compression = 4;
        int i3 = 0;
        this.bitPointer = 0;
        this.bytePointer = 0;
        int i4 = this.f1201w;
        int scanlineStride = (i4 + 7) / 8;
        int[] b2 = new int[2];
        int i5 = 1;
        this.uncompressedMode = (int) ((tiffT6Options & 2) >> 1);
        this.fillBits = (int) ((tiffT6Options & 4) >> 2);
        int[] cce = this.currChangingElems;
        this.changingElemSize = 0;
        int i6 = 0 + 1;
        this.changingElemSize = i6;
        cce[0] = i4;
        this.changingElemSize = i6 + 1;
        cce[i6] = i4;
        int lineOffset = 0;
        int lines = 0;
        while (lines < height) {
            int a0 = -1;
            boolean isWhite2 = true;
            int[] temp = this.prevChangingElems;
            this.prevChangingElems = this.currChangingElems;
            this.currChangingElems = temp;
            int[] cce2 = temp;
            int currIndex = 0;
            int bitOffset2 = startX;
            if (this.fillBits != i || (i2 = this.bitPointer) <= 0 || nextNBits(8 - i2) == 0) {
                this.lastChangingElement = i3;
                int bitOffset3 = bitOffset2;
                while (bitOffset3 < this.f1201w && this.bytePointer < this.data.length - i) {
                    getNextChangingElement(a0, isWhite2, b);
                    int b12 = b[0];
                    int b22 = b[i];
                    int i7 = twoDCodes[nextLesserThan8Bits(7)] & 255;
                    int[] b3 = b;
                    int code2 = (i7 & 120) >>> 3;
                    int bits = i7 & 7;
                    if (code2 == 0) {
                        if (!isWhite2) {
                            setToBlack(bArr, lineOffset, bitOffset3, b22 - bitOffset3);
                        }
                        a0 = b22;
                        bitOffset3 = b22;
                        updatePointer(7 - bits);
                        byte[] bArr2 = compData;
                        b = b3;
                        i = 1;
                    } else if (code2 == 1) {
                        updatePointer(7 - bits);
                        if (isWhite2) {
                            int bitOffset4 = bitOffset3 + decodeWhiteCodeWord();
                            int currIndex2 = currIndex + 1;
                            cce2[currIndex] = bitOffset4;
                            int number = decodeBlackCodeWord();
                            setToBlack(bArr, lineOffset, bitOffset4, number);
                            bitOffset3 = bitOffset4 + number;
                            currIndex = currIndex2 + 1;
                            cce2[currIndex2] = bitOffset3;
                        } else {
                            int number2 = decodeBlackCodeWord();
                            setToBlack(bArr, lineOffset, bitOffset3, number2);
                            int bitOffset5 = bitOffset3 + number2;
                            int currIndex3 = currIndex + 1;
                            cce2[currIndex] = bitOffset5;
                            bitOffset3 = bitOffset5 + decodeWhiteCodeWord();
                            currIndex = currIndex3 + 1;
                            cce2[currIndex3] = bitOffset3;
                        }
                        a0 = bitOffset3;
                        byte[] bArr3 = compData;
                        b = b3;
                        i = 1;
                    } else if (code2 <= 8) {
                        int a1 = b12 + (code2 - 5);
                        int currIndex4 = currIndex + 1;
                        cce2[currIndex] = a1;
                        if (!isWhite2) {
                            setToBlack(bArr, lineOffset, bitOffset3, a1 - bitOffset3);
                        }
                        a0 = a1;
                        bitOffset3 = a1;
                        isWhite2 = !isWhite2;
                        updatePointer(7 - bits);
                        byte[] bArr4 = compData;
                        currIndex = currIndex4;
                        b = b3;
                        i = 1;
                    } else if (code2 == 11) {
                        int i8 = i7;
                        if (nextLesserThan8Bits(3) == 7) {
                            int zeros = 0;
                            boolean exit = false;
                            while (!exit) {
                                while (true) {
                                    b1 = b12;
                                    code = code2;
                                    if (nextLesserThan8Bits(1) == 1) {
                                        break;
                                    }
                                    zeros++;
                                    b12 = b1;
                                    code2 = code;
                                }
                                if (zeros > 5) {
                                    zeros -= 6;
                                    if (!isWhite2 && zeros > 0) {
                                        cce2[currIndex] = bitOffset3;
                                        currIndex++;
                                    }
                                    bitOffset3 += zeros;
                                    if (zeros > 0) {
                                        isWhite2 = true;
                                    }
                                    if (nextLesserThan8Bits(1) == 0) {
                                        if (!isWhite2) {
                                            cce2[currIndex] = bitOffset3;
                                            currIndex++;
                                        }
                                        isWhite2 = true;
                                    } else {
                                        if (isWhite2) {
                                            cce2[currIndex] = bitOffset3;
                                            currIndex++;
                                        }
                                        isWhite2 = false;
                                    }
                                    exit = true;
                                }
                                if (zeros == 5) {
                                    if (!isWhite2) {
                                        cce2[currIndex] = bitOffset3;
                                        currIndex++;
                                    }
                                    bitOffset = bitOffset3 + zeros;
                                    isWhite = true;
                                    b12 = b1;
                                    code2 = code;
                                } else {
                                    int bitOffset6 = bitOffset3 + zeros;
                                    cce2[currIndex] = bitOffset6;
                                    setToBlack(bArr, lineOffset, bitOffset6, 1);
                                    bitOffset = bitOffset6 + 1;
                                    isWhite = false;
                                    currIndex++;
                                    b12 = b1;
                                    code2 = code;
                                }
                            }
                            int i9 = code2;
                            byte[] bArr5 = compData;
                            b = b3;
                            i = 1;
                        } else {
                            throw new IOException(IOException.InvalidCodeEncounteredWhileDecoding2dGroup4CompressedData);
                        }
                    } else {
                        int entry = i7;
                        int i10 = b12;
                        int i11 = code2;
                        bitOffset3 = this.f1201w;
                        updatePointer(7 - bits);
                        byte[] bArr6 = compData;
                        b = b3;
                        i = 1;
                    }
                }
                int[] b4 = b;
                if (currIndex < cce2.length) {
                    cce2[currIndex] = bitOffset3;
                    currIndex++;
                }
                this.changingElemSize = currIndex;
                lineOffset += scanlineStride;
                lines++;
                byte[] bArr7 = compData;
                b2 = b4;
                i3 = 0;
                i5 = 1;
            } else {
                throw new IOException(IOException.ExpectedTrailingZeroBitsForByteAlignedLines);
            }
        }
    }

    private void setToBlack(byte[] buffer, int lineOffset, int bitOffset, int numBits) {
        int bitNum = (lineOffset * 8) + bitOffset;
        int lastBit = bitNum + numBits;
        int byteNum = bitNum >> 3;
        int shift = bitNum & 7;
        if (shift > 0) {
            int maskVal = 1 << (7 - shift);
            byte val = buffer[byteNum];
            while (maskVal > 0 && bitNum < lastBit) {
                val = (byte) (((byte) maskVal) | val);
                maskVal >>= 1;
                bitNum++;
            }
            buffer[byteNum] = val;
        }
        int byteNum2 = bitNum >> 3;
        while (bitNum < lastBit - 7) {
            buffer[byteNum2] = -1;
            bitNum += 8;
            byteNum2++;
        }
        while (bitNum < lastBit) {
            int byteNum3 = bitNum >> 3;
            if (!this.recoverFromImageError || byteNum3 < buffer.length) {
                buffer[byteNum3] = (byte) (buffer[byteNum3] | ((byte) (1 << (7 - (bitNum & 7)))));
            }
            bitNum++;
        }
    }

    private int decodeWhiteCodeWord() {
        int runLength = 0;
        boolean isWhite = true;
        while (isWhite) {
            int current = nextNBits(10);
            short entry = white[current];
            int isT = entry & 1;
            int bits = (entry >>> 1) & 15;
            if (bits == 12) {
                short entry2 = additionalMakeup[(12 & (current << 2)) | nextLesserThan8Bits(2)];
                runLength += (entry2 >>> 4) & 4095;
                updatePointer(4 - ((entry2 >>> 1) & 7));
            } else if (bits == 0) {
                throw new IOException(IOException.InvalidCodeEncountered);
            } else if (bits != 15) {
                runLength += (entry >>> 5) & 2047;
                updatePointer(10 - bits);
                if (isT == 0) {
                    isWhite = false;
                }
            } else if (runLength == 0) {
                isWhite = false;
            } else {
                throw new IOException(IOException.EolCodeWordEncounteredInWhiteRun);
            }
        }
        return runLength;
    }

    private int decodeBlackCodeWord() {
        int runLength = 0;
        boolean isWhite = false;
        while (!isWhite) {
            short entry = initBlack[nextLesserThan8Bits(4)];
            short s = entry & 1;
            int bits = (entry >>> 1) & 15;
            int code = (entry >>> 5) & 2047;
            if (code == 100) {
                short entry2 = black[nextNBits(9)];
                int isT = entry2 & 1;
                int bits2 = (entry2 >>> 1) & 15;
                int code2 = (entry2 >>> 5) & 2047;
                if (bits2 == 12) {
                    updatePointer(5);
                    short entry3 = additionalMakeup[nextLesserThan8Bits(4)];
                    runLength += (entry3 >>> 4) & 4095;
                    updatePointer(4 - ((entry3 >>> 1) & 7));
                } else if (bits2 != 15) {
                    runLength += code2;
                    updatePointer(9 - bits2);
                    if (isT == 0) {
                        isWhite = true;
                    }
                } else {
                    throw new IOException(IOException.EolCodeWordEncounteredInBlackRun);
                }
            } else if (code == 200) {
                short entry4 = twoBitBlack[nextLesserThan8Bits(2)];
                runLength += (entry4 >>> 5) & 2047;
                updatePointer(2 - ((entry4 >>> 1) & 15));
                isWhite = true;
            } else {
                runLength += code;
                updatePointer(4 - bits);
                isWhite = true;
            }
        }
        return runLength;
    }

    private int readEOL(boolean isFirstEOL) {
        int n;
        int next12Bits = this.fillBits;
        if (next12Bits == 0) {
            int next12Bits2 = nextNBits(12);
            if (isFirstEOL && next12Bits2 == 0 && nextNBits(4) == 1) {
                this.fillBits = 1;
                return 1;
            } else if (next12Bits2 != 1) {
                throw new IOException(IOException.ScanlineMustBeginWithEolCodeWord);
            }
        } else if (next12Bits == 1) {
            int bitsLeft = 8 - this.bitPointer;
            if (nextNBits(bitsLeft) != 0) {
                throw new IOException(IOException.AllFillBitsPrecedingEolCodeMustBe0);
            } else if (bitsLeft >= 4 || nextNBits(8) == 0) {
                do {
                    int nextNBits = nextNBits(8);
                    n = nextNBits;
                    if (nextNBits != 1) {
                    }
                } while (n == 0);
                throw new IOException(IOException.AllFillBitsPrecedingEolCodeMustBe0);
            } else {
                throw new IOException(IOException.AllFillBitsPrecedingEolCodeMustBe0);
            }
        }
        if (this.oneD == 0) {
            return 1;
        }
        return nextLesserThan8Bits(1);
    }

    private void getNextChangingElement(int a0, boolean isWhite, int[] ret) {
        int start;
        int[] pce = this.prevChangingElems;
        int ces = this.changingElemSize;
        int i = this.lastChangingElement;
        int start2 = i > 0 ? i - 1 : 0;
        if (isWhite) {
            start = start2 & -2;
        } else {
            start = start2 | 1;
        }
        int i2 = start;
        while (true) {
            if (i2 >= ces) {
                break;
            }
            int temp = pce[i2];
            if (temp > a0) {
                this.lastChangingElement = i2;
                ret[0] = temp;
                break;
            }
            i2 += 2;
        }
        if (i2 + 1 < ces) {
            ret[1] = pce[i2 + 1];
        }
    }

    private int nextNBits(int bitsToGet) {
        byte b;
        byte b2;
        byte next;
        byte[] bArr = this.data;
        int l = bArr.length - 1;
        int bp = this.bytePointer;
        int i = this.fillOrder;
        if (i == 1) {
            b2 = bArr[bp];
            if (bp == l) {
                next = 0;
                b = 0;
            } else if (bp + 1 == l) {
                next = bArr[bp + 1];
                b = 0;
            } else {
                byte b3 = bArr[bp + 1];
                b = bArr[bp + 2];
                next = b3;
            }
        } else if (i == 2) {
            byte[] bArr2 = flipTable;
            byte b4 = bArr2[bArr[bp] & UByte.MAX_VALUE];
            if (bp == l) {
                next = 0;
                byte b5 = b4;
                b = 0;
                b2 = b5;
            } else if (bp + 1 == l) {
                next = bArr2[bArr[bp + 1] & UByte.MAX_VALUE];
                byte b6 = b4;
                b = 0;
                b2 = b6;
            } else {
                byte next2 = bArr2[bArr[bp + 1] & UByte.MAX_VALUE];
                byte b7 = bArr2[bArr[bp + 2] & UByte.MAX_VALUE];
                b2 = b4;
                b = b7;
                next = next2;
            }
        } else {
            throw new IOException(IOException.TiffFillOrderTagMustBeEither1Or2);
        }
        int bitsLeft = 8 - this.bitPointer;
        int bitsFromNextByte = bitsToGet - bitsLeft;
        int bitsFromNext2NextByte = 0;
        if (bitsFromNextByte > 8) {
            bitsFromNext2NextByte = bitsFromNextByte - 8;
            bitsFromNextByte = 8;
        }
        int i2 = this.bytePointer + 1;
        this.bytePointer = i2;
        int i1 = (table1[bitsLeft] & b2) << (bitsToGet - bitsLeft);
        int[] iArr = table2;
        int i22 = (iArr[bitsFromNextByte] & next) >>> (8 - bitsFromNextByte);
        if (bitsFromNext2NextByte != 0) {
            i22 = (i22 << bitsFromNext2NextByte) | ((iArr[bitsFromNext2NextByte] & b) >>> (8 - bitsFromNext2NextByte));
            this.bytePointer = i2 + 1;
            this.bitPointer = bitsFromNext2NextByte;
        } else if (bitsFromNextByte == 8) {
            this.bitPointer = 0;
            this.bytePointer = i2 + 1;
        } else {
            this.bitPointer = bitsFromNextByte;
        }
        return i1 | i22;
    }

    private int nextLesserThan8Bits(int bitsToGet) {
        byte b = 0;
        byte next = 0;
        byte[] bArr = this.data;
        int l = bArr.length - 1;
        int bp = this.bytePointer;
        int i = this.fillOrder;
        if (i == 1) {
            b = bArr[bp];
            if (bp == l) {
                next = 0;
            } else {
                next = bArr[bp + 1];
            }
        } else if (i != 2) {
            throw new IOException(IOException.TiffFillOrderTagMustBeEither1Or2);
        } else if (!this.recoverFromImageError || bp < bArr.length) {
            byte[] bArr2 = flipTable;
            b = bArr2[bArr[bp] & UByte.MAX_VALUE];
            if (bp == l) {
                next = 0;
            } else {
                next = bArr2[bArr[bp + 1] & UByte.MAX_VALUE];
            }
        }
        int i2 = this.bitPointer;
        int bitsLeft = 8 - i2;
        int bitsFromNextByte = bitsToGet - bitsLeft;
        int shift = bitsLeft - bitsToGet;
        if (shift >= 0) {
            int i1 = (table1[bitsLeft] & b) >>> shift;
            int i3 = i2 + bitsToGet;
            this.bitPointer = i3;
            if (i3 != 8) {
                return i1;
            }
            this.bitPointer = 0;
            this.bytePointer++;
            return i1;
        }
        this.bytePointer++;
        this.bitPointer = bitsFromNextByte;
        return ((table1[bitsLeft] & b) << (-shift)) | ((table2[bitsFromNextByte] & next) >>> (8 - bitsFromNextByte));
    }

    private void updatePointer(int bitsToMoveBack) {
        int i = this.bitPointer - bitsToMoveBack;
        if (i < 0) {
            this.bytePointer--;
            this.bitPointer = i + 8;
            return;
        }
        this.bitPointer = i;
    }

    private boolean advancePointer() {
        if (this.bitPointer != 0) {
            this.bytePointer++;
            this.bitPointer = 0;
        }
        return true;
    }

    public void setRecoverFromImageError(boolean recoverFromImageError2) {
        this.recoverFromImageError = recoverFromImageError2;
    }
}

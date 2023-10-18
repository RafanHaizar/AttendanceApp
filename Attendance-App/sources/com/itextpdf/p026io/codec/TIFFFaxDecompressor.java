package com.itextpdf.p026io.codec;

import com.itextpdf.p026io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.signers.PSSSigner;

/* renamed from: com.itextpdf.io.codec.TIFFFaxDecompressor */
public class TIFFFaxDecompressor {
    public static short[] additionalMakeup = {28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567};
    static short[] black = {62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390};
    static byte[] flipTable = {0, ByteCompanionObject.MIN_VALUE, 64, -64, 32, -96, 96, -32, Tnaf.POW_2_WIDTH, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, PSSSigner.TRAILER_IMPLICIT, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, ByteCompanionObject.MAX_VALUE, -1};
    static short[] initBlack = {3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68};
    static int[] table1 = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    static int[] table2 = {0, 128, 192, 224, 240, 248, 252, TIFFConstants.TIFFTAG_SUBFILETYPE, 255};
    static short[] twoBitBlack = {292, 260, 226, 226};
    static byte[] twoDCodes = {80, 88, 23, 71, 30, 30, 62, 62, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41};
    static short[] white = {6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232};
    private int bitPointer;
    private int bitsPerScanline;
    private byte[] buffer;
    private int bytePointer;
    private int changingElemSize = 0;
    protected int compression;
    private int[] currChangingElems;
    private byte[] data;
    public int fails;
    protected int fillBits = 0;
    protected int fillOrder;

    /* renamed from: h */
    private int f1202h;
    private int lastChangingElement = 0;
    private int lineBitNum;
    private final Object lock = new Object();
    protected int oneD;
    private int[] prevChangingElems;
    private int t4Options;
    private int t6Options;
    protected int uncompressedMode = 0;

    /* renamed from: w */
    private int f1203w;

    public void SetOptions(int fillOrder2, int compression2, int t4Options2, int t6Options2) {
        this.fillOrder = fillOrder2;
        this.compression = compression2;
        this.t4Options = t4Options2;
        this.t6Options = t6Options2;
        this.oneD = t4Options2 & 1;
        this.uncompressedMode = (t4Options2 & 2) >> 1;
        this.fillBits = (t4Options2 & 4) >> 2;
    }

    public void decodeRaw(byte[] buffer2, byte[] compData, int w, int h) {
        this.buffer = buffer2;
        this.data = compData;
        this.f1203w = w;
        this.f1202h = h;
        this.bitsPerScanline = w;
        this.lineBitNum = 0;
        this.bitPointer = 0;
        this.bytePointer = 0;
        this.prevChangingElems = new int[(w + 1)];
        this.currChangingElems = new int[(w + 1)];
        this.fails = 0;
        try {
            int i = this.compression;
            if (i == 2) {
                decodeRLE();
            } else if (i == 3) {
                decodeT4();
            } else if (i == 4) {
                this.uncompressedMode = (this.t6Options & 2) >> 1;
                decodeT6();
            } else {
                throw new IOException(IOException.UnknownCompressionType1).setMessageParams(Integer.valueOf(this.compression));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void decodeRLE() {
        for (int i = 0; i < this.f1202h; i++) {
            decodeNextScanline();
            if (this.bitPointer != 0) {
                this.bytePointer++;
                this.bitPointer = 0;
            }
            this.lineBitNum += this.bitsPerScanline;
        }
    }

    public void decodeNextScanline() {
        boolean isWhite = true;
        int bitOffset = 0;
        this.changingElemSize = 0;
        while (true) {
            if (bitOffset >= this.f1203w) {
                break;
            }
            int runOffset = bitOffset;
            while (isWhite && bitOffset < this.f1203w) {
                int current = nextNBits(10);
                short entry = white[current];
                int isT = entry & 1;
                int bits = (entry >>> 1) & 15;
                if (bits == 12) {
                    short entry2 = additionalMakeup[(12 & (current << 2)) | nextLesserThan8Bits(2)];
                    bitOffset += (entry2 >>> 4) & 4095;
                    updatePointer(4 - ((entry2 >>> 1) & 7));
                } else if (bits == 0) {
                    this.fails++;
                } else if (bits == 15) {
                    this.fails++;
                    return;
                } else {
                    bitOffset += (entry >>> 5) & 2047;
                    updatePointer(10 - bits);
                    if (isT == 0) {
                        isWhite = false;
                        int[] iArr = this.currChangingElems;
                        int i = this.changingElemSize;
                        this.changingElemSize = i + 1;
                        iArr[i] = bitOffset;
                    }
                }
            }
            if (bitOffset == this.f1203w) {
                int runLength = bitOffset - runOffset;
                if (isWhite && runLength != 0 && runLength % 64 == 0 && nextNBits(8) != 53) {
                    this.fails++;
                    updatePointer(8);
                }
            } else {
                int runOffset2 = bitOffset;
                while (!isWhite && bitOffset < this.f1203w) {
                    short entry3 = initBlack[nextLesserThan8Bits(4)];
                    int isT2 = entry3 & 1;
                    int bits2 = (entry3 >>> 1) & 15;
                    int code = (entry3 >>> 5) & 2047;
                    if (code == 100) {
                        short entry4 = black[nextNBits(9)];
                        int isT3 = entry4 & 1;
                        int bits3 = (entry4 >>> 1) & 15;
                        int code2 = (entry4 >>> 5) & 2047;
                        if (bits3 == 12) {
                            updatePointer(5);
                            short entry5 = additionalMakeup[nextLesserThan8Bits(4)];
                            int code3 = (entry5 >>> 4) & 4095;
                            setToBlack(bitOffset, code3);
                            bitOffset += code3;
                            updatePointer(4 - ((entry5 >>> 1) & 7));
                        } else if (bits3 == 15) {
                            this.fails++;
                            return;
                        } else {
                            setToBlack(bitOffset, code2);
                            bitOffset += code2;
                            updatePointer(9 - bits3);
                            if (isT3 == 0) {
                                isWhite = true;
                                int[] iArr2 = this.currChangingElems;
                                int i2 = this.changingElemSize;
                                this.changingElemSize = i2 + 1;
                                iArr2[i2] = bitOffset;
                            }
                        }
                    } else if (code == 200) {
                        short entry6 = twoBitBlack[nextLesserThan8Bits(2)];
                        int code4 = (entry6 >>> 5) & 2047;
                        setToBlack(bitOffset, code4);
                        bitOffset += code4;
                        updatePointer(2 - ((entry6 >>> 1) & 15));
                        isWhite = true;
                        int[] iArr3 = this.currChangingElems;
                        int i3 = this.changingElemSize;
                        this.changingElemSize = i3 + 1;
                        iArr3[i3] = bitOffset;
                    } else {
                        setToBlack(bitOffset, code);
                        bitOffset += code;
                        updatePointer(4 - bits2);
                        isWhite = true;
                        int[] iArr4 = this.currChangingElems;
                        int i4 = this.changingElemSize;
                        this.changingElemSize = i4 + 1;
                        iArr4[i4] = bitOffset;
                    }
                }
                if (bitOffset == this.f1203w) {
                    int runLength2 = bitOffset - runOffset2;
                    if (!isWhite && runLength2 != 0 && runLength2 % 64 == 0 && nextNBits(10) != 55) {
                        this.fails++;
                        updatePointer(10);
                    }
                }
            }
        }
        int[] iArr5 = this.currChangingElems;
        int i5 = this.changingElemSize;
        this.changingElemSize = i5 + 1;
        iArr5[i5] = bitOffset;
    }

    public void decodeT4() {
        char c;
        int height;
        int modeFlag;
        int currIndex;
        int height2 = this.f1202h;
        int[] b = new int[2];
        if (this.data.length >= 2) {
            char c2 = 1;
            if (nextNBits(12) != 1) {
                this.fails++;
            }
            updatePointer(12);
            int lines = -1;
            int modeFlag2 = 0;
            while (modeFlag2 != 1) {
                try {
                    modeFlag2 = findNextLine();
                    lines++;
                } catch (Exception e) {
                    Exception exc = e;
                    throw new RuntimeException("No reference line present.");
                }
            }
            decodeNextScanline();
            int lines2 = lines + 1;
            this.lineBitNum += this.bitsPerScanline;
            while (lines2 < height2) {
                try {
                    int number = findNextLine();
                    if (number == 0) {
                        int[] temp = this.prevChangingElems;
                        this.prevChangingElems = this.currChangingElems;
                        this.currChangingElems = temp;
                        int currIndex2 = 0;
                        int a0 = -1;
                        boolean isWhite = true;
                        int bitOffset = 0;
                        int entry = 0;
                        this.lastChangingElement = 0;
                        while (true) {
                            if (bitOffset >= this.f1203w) {
                                int numLinesTested = number;
                                height = height2;
                                break;
                            }
                            getNextChangingElement(a0, isWhite, b);
                            int b1 = b[entry];
                            int b2 = b[c];
                            int entry2 = twoDCodes[nextLesserThan8Bits(7)] & 255;
                            int code = (entry2 & 120) >>> 3;
                            int bits = entry2 & 7;
                            if (code == 0) {
                                if (!isWhite) {
                                    setToBlack(bitOffset, b2 - bitOffset);
                                }
                                a0 = b2;
                                bitOffset = b2;
                                updatePointer(7 - bits);
                                c = 1;
                                entry = 0;
                            } else if (code == 1) {
                                updatePointer(7 - bits);
                                if (isWhite) {
                                    int bitOffset2 = bitOffset + decodeWhiteCodeWord();
                                    modeFlag = number;
                                    int currIndex3 = currIndex2 + 1;
                                    this.currChangingElems[currIndex2] = bitOffset2;
                                    int number2 = decodeBlackCodeWord();
                                    setToBlack(bitOffset2, number2);
                                    bitOffset = bitOffset2 + number2;
                                    currIndex = currIndex3 + 1;
                                    this.currChangingElems[currIndex3] = bitOffset;
                                } else {
                                    modeFlag = number;
                                    int modeFlag3 = decodeBlackCodeWord();
                                    setToBlack(bitOffset, modeFlag3);
                                    int bitOffset3 = bitOffset + modeFlag3;
                                    int currIndex4 = currIndex2 + 1;
                                    this.currChangingElems[currIndex2] = bitOffset3;
                                    bitOffset = bitOffset3 + decodeWhiteCodeWord();
                                    currIndex = currIndex4 + 1;
                                    this.currChangingElems[currIndex4] = bitOffset;
                                }
                                currIndex2 = currIndex;
                                a0 = bitOffset;
                                number = modeFlag;
                                c = 1;
                                entry = 0;
                            } else {
                                int modeFlag4 = number;
                                if (code <= 8) {
                                    int a1 = (code - 5) + b1;
                                    int currIndex5 = currIndex2 + 1;
                                    this.currChangingElems[currIndex2] = a1;
                                    if (!isWhite) {
                                        setToBlack(bitOffset, a1 - bitOffset);
                                    }
                                    a0 = a1;
                                    bitOffset = a1;
                                    isWhite = !isWhite;
                                    updatePointer(7 - bits);
                                    number = modeFlag4;
                                    currIndex2 = currIndex5;
                                    c = 1;
                                    entry = 0;
                                } else {
                                    int i = 1;
                                    this.fails++;
                                    height = height2;
                                    int modeFlag5 = modeFlag4;
                                    int numLinesTested2 = 0;
                                    while (modeFlag5 != i) {
                                        try {
                                            modeFlag5 = findNextLine();
                                            numLinesTested2++;
                                            i = 1;
                                        } catch (Exception e2) {
                                            Exception exc2 = e2;
                                            return;
                                        }
                                    }
                                    lines2 += numLinesTested2 - 1;
                                    updatePointer(13);
                                    number = modeFlag5;
                                }
                            }
                        }
                        int currIndex6 = currIndex2 + 1;
                        this.currChangingElems[currIndex2] = bitOffset;
                        this.changingElemSize = currIndex6;
                        int i2 = number;
                        int i3 = currIndex6;
                    } else {
                        height = height2;
                        decodeNextScanline();
                        int i4 = number;
                    }
                    this.lineBitNum += this.bitsPerScanline;
                    lines2++;
                    height2 = height;
                    c2 = 1;
                } catch (Exception e3) {
                    int i5 = height2;
                    Exception exc3 = e3;
                    this.fails++;
                    return;
                }
            }
            return;
        }
        throw new RuntimeException("Insufficient data to read initial EOL.");
    }

    public void decodeT6() {
        char c;
        int entry;
        int i;
        int entranceCode;
        int entry2;
        int bitOffset;
        boolean isWhite;
        int[] b;
        int height;
        synchronized (this.lock) {
            int height2 = this.f1202h;
            int[] b2 = new int[2];
            int[] cce = this.currChangingElems;
            int entry3 = 0;
            this.changingElemSize = 0;
            char c2 = 1;
            int i2 = 0 + 1;
            this.changingElemSize = i2;
            int i3 = this.f1203w;
            cce[0] = i3;
            this.changingElemSize = i2 + 1;
            cce[i2] = i3;
            int lines = 0;
            while (lines < height2) {
                int a0 = -1;
                boolean isWhite2 = true;
                int[] temp = this.prevChangingElems;
                this.prevChangingElems = this.currChangingElems;
                this.currChangingElems = temp;
                int[] cce2 = temp;
                int currIndex = 0;
                int bitOffset2 = 0;
                this.lastChangingElement = entry;
                while (true) {
                    i = this.f1203w;
                    if (bitOffset2 >= i) {
                        break;
                    }
                    getNextChangingElement(a0, isWhite2, b2);
                    int b1 = b2[entry];
                    int b22 = b2[c];
                    int entry4 = twoDCodes[nextLesserThan8Bits(7)] & 255;
                    int code = (entry4 & 120) >>> 3;
                    int bits = entry4 & 7;
                    if (code == 0) {
                        if (!isWhite2) {
                            int i4 = this.f1203w;
                            if (b22 > i4) {
                                b22 = i4;
                            }
                            setToBlack(bitOffset2, b22 - bitOffset2);
                        }
                        a0 = b22;
                        bitOffset2 = b22;
                        updatePointer(7 - bits);
                        entry = 0;
                        c = 1;
                    } else if (code == 1) {
                        updatePointer(7 - bits);
                        if (isWhite2) {
                            int bitOffset3 = bitOffset2 + decodeWhiteCodeWord();
                            int currIndex2 = currIndex + 1;
                            cce2[currIndex] = bitOffset3;
                            int number = decodeBlackCodeWord();
                            int i5 = this.f1203w;
                            height = height2;
                            if (number > i5 - bitOffset3) {
                                number = i5 - bitOffset3;
                            }
                            setToBlack(bitOffset3, number);
                            bitOffset2 = bitOffset3 + number;
                            cce2[currIndex2] = bitOffset2;
                            currIndex = currIndex2 + 1;
                            b = b2;
                        } else {
                            height = height2;
                            int number2 = decodeBlackCodeWord();
                            int i6 = this.f1203w;
                            b = b2;
                            if (number2 > i6 - bitOffset2) {
                                number2 = i6 - bitOffset2;
                            }
                            setToBlack(bitOffset2, number2);
                            int bitOffset4 = bitOffset2 + number2;
                            int currIndex3 = currIndex + 1;
                            cce2[currIndex] = bitOffset4;
                            bitOffset2 = bitOffset4 + decodeWhiteCodeWord();
                            cce2[currIndex3] = bitOffset2;
                            currIndex = currIndex3 + 1;
                        }
                        a0 = bitOffset2;
                        height2 = height;
                        b2 = b;
                        entry = 0;
                        c = 1;
                    } else {
                        int height3 = height2;
                        int[] b3 = b2;
                        if (code <= 8) {
                            int a1 = (code - 5) + b1;
                            int currIndex4 = currIndex + 1;
                            cce2[currIndex] = a1;
                            if (!isWhite2) {
                                int i7 = this.f1203w;
                                if (a1 > i7) {
                                    a1 = i7;
                                }
                                setToBlack(bitOffset2, a1 - bitOffset2);
                            }
                            a0 = a1;
                            bitOffset2 = a1;
                            isWhite2 = !isWhite2;
                            updatePointer(7 - bits);
                            currIndex = currIndex4;
                            height2 = height3;
                            b2 = b3;
                            entry = 0;
                            c = 1;
                        } else if (code == 11) {
                            int entranceCode2 = nextLesserThan8Bits(3);
                            int zeros = 0;
                            boolean exit = false;
                            while (!exit) {
                                while (true) {
                                    entranceCode = entranceCode2;
                                    entry2 = entry4;
                                    if (nextLesserThan8Bits(1) == 1) {
                                        break;
                                    }
                                    zeros++;
                                    entranceCode2 = entranceCode;
                                    entry4 = entry2;
                                }
                                if (zeros > 5) {
                                    zeros -= 6;
                                    if (!isWhite2 && zeros > 0) {
                                        cce2[currIndex] = bitOffset2;
                                        currIndex++;
                                    }
                                    bitOffset2 += zeros;
                                    if (zeros > 0) {
                                        isWhite2 = true;
                                    }
                                    if (nextLesserThan8Bits(1) == 0) {
                                        if (!isWhite2) {
                                            cce2[currIndex] = bitOffset2;
                                            currIndex++;
                                        }
                                        isWhite2 = true;
                                    } else {
                                        if (isWhite2) {
                                            cce2[currIndex] = bitOffset2;
                                            currIndex++;
                                        }
                                        isWhite2 = false;
                                    }
                                    exit = true;
                                }
                                if (zeros == 5) {
                                    if (!isWhite2) {
                                        cce2[currIndex] = bitOffset2;
                                        currIndex++;
                                    }
                                    bitOffset = bitOffset2 + zeros;
                                    isWhite = true;
                                    entranceCode2 = entranceCode;
                                    entry4 = entry2;
                                } else {
                                    int bitOffset5 = bitOffset2 + zeros;
                                    cce2[currIndex] = bitOffset5;
                                    setToBlack(bitOffset5, 1);
                                    bitOffset = bitOffset5 + 1;
                                    isWhite = false;
                                    currIndex++;
                                    entranceCode2 = entranceCode;
                                    entry4 = entry2;
                                }
                            }
                            int i8 = entry4;
                            height2 = height3;
                            b2 = b3;
                            entry = 0;
                            c = 1;
                        } else {
                            height2 = height3;
                            b2 = b3;
                            entry = 0;
                            c = 1;
                        }
                    }
                }
                int height4 = height2;
                int[] b4 = b2;
                if (currIndex <= i) {
                    cce2[currIndex] = bitOffset2;
                    currIndex++;
                }
                this.changingElemSize = currIndex;
                this.lineBitNum += this.bitsPerScanline;
                lines++;
                height2 = height4;
                b2 = b4;
                entry3 = 0;
                c2 = 1;
            }
            int[] iArr = b2;
        }
    }

    private void setToBlack(int bitNum, int numBits) {
        int bitNum2 = bitNum + this.lineBitNum;
        int lastBit = bitNum2 + numBits;
        int byteNum = bitNum2 >> 3;
        int shift = bitNum2 & 7;
        if (shift > 0) {
            int maskVal = 1 << (7 - shift);
            byte val = this.buffer[byteNum];
            while (maskVal > 0 && bitNum2 < lastBit) {
                val = (byte) (((byte) maskVal) | val);
                maskVal >>= 1;
                bitNum2++;
            }
            this.buffer[byteNum] = val;
        }
        int byteNum2 = bitNum2 >> 3;
        while (bitNum2 < lastBit - 7) {
            this.buffer[byteNum2] = -1;
            bitNum2 += 8;
            byteNum2++;
        }
        while (bitNum2 < lastBit) {
            int byteNum3 = bitNum2 >> 3;
            byte[] bArr = this.buffer;
            bArr[byteNum3] = (byte) (bArr[byteNum3] | ((byte) (1 << (7 - (bitNum2 & 7)))));
            bitNum2++;
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
                throw new RuntimeException("Error 0");
            } else if (bits != 15) {
                runLength += (entry >>> 5) & 2047;
                updatePointer(10 - bits);
                if (isT == 0) {
                    isWhite = false;
                }
            } else {
                throw new RuntimeException("Error 1");
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
                    throw new RuntimeException("Error 2");
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

    private int findNextLine() {
        int bitIndexMax = (this.data.length * 8) - 1;
        int bitIndexMax12 = bitIndexMax - 12;
        int bitIndex = (this.bytePointer * 8) + this.bitPointer;
        while (bitIndex <= bitIndexMax12) {
            int next12Bits = nextNBits(12);
            bitIndex += 12;
            while (next12Bits != 1 && bitIndex < bitIndexMax) {
                next12Bits = ((next12Bits & 2047) << 1) | (nextLesserThan8Bits(1) & 1);
                bitIndex++;
            }
            if (next12Bits == 1) {
                if (this.oneD != 1) {
                    return 1;
                }
                if (bitIndex < bitIndexMax) {
                    return nextLesserThan8Bits(1);
                }
            }
        }
        throw new RuntimeException();
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
            throw new RuntimeException("Invalid FillOrder");
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
        byte b;
        byte next;
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
        } else if (i == 2) {
            byte[] bArr2 = flipTable;
            byte b2 = bArr2[bArr[bp] & UByte.MAX_VALUE];
            if (bp == l) {
                next = 0;
                b = b2;
            } else {
                next = bArr2[bArr[bp + 1] & UByte.MAX_VALUE];
                b = b2;
            }
        } else {
            throw new RuntimeException("Invalid FillOrder");
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
        if (bitsToMoveBack > 8) {
            this.bytePointer -= bitsToMoveBack / 8;
            bitsToMoveBack %= 8;
        }
        int i = this.bitPointer - bitsToMoveBack;
        if (i < 0) {
            this.bytePointer--;
            this.bitPointer = i + 8;
            return;
        }
        this.bitPointer = i;
    }
}

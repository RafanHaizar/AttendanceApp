package com.itextpdf.p026io.codec;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.material.internal.ViewUtils;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.source.ByteBuffer;
import kotlin.UByte;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.Primes;

/* renamed from: com.itextpdf.io.codec.CCITTG4Encoder */
public class CCITTG4Encoder {
    private static final int CODE = 1;
    private static final int EOL = 1;
    private static final int G3CODE_EOF = -3;
    private static final int G3CODE_EOL = -1;
    private static final int G3CODE_INCOMP = -4;
    private static final int G3CODE_INVALID = -2;
    private static final int LENGTH = 0;
    private static final int RUNLEN = 2;
    private static byte[] oneruns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 7, 8};
    private static byte[] zeroruns = {8, 7, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[][] TIFFFaxBlackCodes = {new int[]{10, 55, 0}, new int[]{3, 2, 1}, new int[]{2, 3, 2}, new int[]{2, 2, 3}, new int[]{3, 3, 4}, new int[]{4, 3, 5}, new int[]{4, 2, 6}, new int[]{5, 3, 7}, new int[]{6, 5, 8}, new int[]{6, 4, 9}, new int[]{7, 4, 10}, new int[]{7, 5, 11}, new int[]{7, 7, 12}, new int[]{8, 4, 13}, new int[]{8, 7, 14}, new int[]{9, 24, 15}, new int[]{10, 23, 16}, new int[]{10, 24, 17}, new int[]{10, 8, 18}, new int[]{11, 103, 19}, new int[]{11, 104, 20}, new int[]{11, 108, 21}, new int[]{11, 55, 22}, new int[]{11, 40, 23}, new int[]{11, 23, 24}, new int[]{11, 24, 25}, new int[]{12, XMPError.BADRDF, 26}, new int[]{12, XMPError.BADXMP, 27}, new int[]{12, XMPError.BADSTREAM, 28}, new int[]{12, 205, 29}, new int[]{12, 104, 30}, new int[]{12, 105, 31}, new int[]{12, 106, 32}, new int[]{12, 107, 33}, new int[]{12, 210, 34}, new int[]{12, Primes.SMALL_FACTOR_LIMIT, 35}, new int[]{12, 212, 36}, new int[]{12, 213, 37}, new int[]{12, 214, 38}, new int[]{12, 215, 39}, new int[]{12, 108, 40}, new int[]{12, 109, 41}, new int[]{12, 218, 42}, new int[]{12, 219, 43}, new int[]{12, 84, 44}, new int[]{12, 85, 45}, new int[]{12, 86, 46}, new int[]{12, 87, 47}, new int[]{12, 100, 48}, new int[]{12, 101, 49}, new int[]{12, 82, 50}, new int[]{12, 83, 51}, new int[]{12, 36, 52}, new int[]{12, 55, 53}, new int[]{12, 56, 54}, new int[]{12, 39, 55}, new int[]{12, 40, 56}, new int[]{12, 88, 57}, new int[]{12, 89, 58}, new int[]{12, 43, 59}, new int[]{12, 44, 60}, new int[]{12, 90, 61}, new int[]{12, 102, 62}, new int[]{12, 103, 63}, new int[]{10, 15, 64}, new int[]{12, 200, 128}, new int[]{12, XMPError.BADXML, 192}, new int[]{12, 91, 256}, new int[]{12, 51, TIFFConstants.TIFFTAG_COLORMAP}, new int[]{12, 52, 384}, new int[]{12, 53, 448}, new int[]{13, 108, 512}, new int[]{13, 109, 576}, new int[]{13, 74, 640}, new int[]{13, 75, TypedValues.TransitionType.TYPE_AUTO_TRANSITION}, new int[]{13, 76, ViewUtils.EDGE_TO_EDGE_FLAGS}, new int[]{13, 77, 832}, new int[]{13, 114, 896}, new int[]{13, 115, 960}, new int[]{13, 116, 1024}, new int[]{13, 117, 1088}, new int[]{13, 118, 1152}, new int[]{13, 119, 1216}, new int[]{13, 82, 1280}, new int[]{13, 83, Polynomial.PRIVATE_KEY_I}, new int[]{13, 84, 1408}, new int[]{13, 85, 1472}, new int[]{13, 90, 1536}, new int[]{13, 91, 1600}, new int[]{13, 100, 1664}, new int[]{13, 101, 1728}, new int[]{11, 8, 1792}, new int[]{11, 12, 1856}, new int[]{11, 13, 1920}, new int[]{12, 18, 1984}, new int[]{12, 19, 2048}, new int[]{12, 20, Polynomial.PRIVATE_KEY_III_SIZE}, new int[]{12, 21, 2176}, new int[]{12, 22, 2240}, new int[]{12, 23, 2304}, new int[]{12, 28, 2368}, new int[]{12, 29, 2432}, new int[]{12, 30, 2496}, new int[]{12, 31, 2560}, new int[]{12, 1, -1}, new int[]{9, 1, -2}, new int[]{10, 1, -2}, new int[]{11, 1, -2}, new int[]{12, 0, -2}};
    private int[][] TIFFFaxWhiteCodes = {new int[]{8, 53, 0}, new int[]{6, 7, 1}, new int[]{4, 7, 2}, new int[]{4, 8, 3}, new int[]{4, 11, 4}, new int[]{4, 12, 5}, new int[]{4, 14, 6}, new int[]{4, 15, 7}, new int[]{5, 19, 8}, new int[]{5, 20, 9}, new int[]{5, 7, 10}, new int[]{5, 8, 11}, new int[]{6, 8, 12}, new int[]{6, 3, 13}, new int[]{6, 52, 14}, new int[]{6, 53, 15}, new int[]{6, 42, 16}, new int[]{6, 43, 17}, new int[]{7, 39, 18}, new int[]{7, 12, 19}, new int[]{7, 8, 20}, new int[]{7, 23, 21}, new int[]{7, 3, 22}, new int[]{7, 4, 23}, new int[]{7, 40, 24}, new int[]{7, 43, 25}, new int[]{7, 19, 26}, new int[]{7, 36, 27}, new int[]{7, 24, 28}, new int[]{8, 2, 29}, new int[]{8, 3, 30}, new int[]{8, 26, 31}, new int[]{8, 27, 32}, new int[]{8, 18, 33}, new int[]{8, 19, 34}, new int[]{8, 20, 35}, new int[]{8, 21, 36}, new int[]{8, 22, 37}, new int[]{8, 23, 38}, new int[]{8, 40, 39}, new int[]{8, 41, 40}, new int[]{8, 42, 41}, new int[]{8, 43, 42}, new int[]{8, 44, 43}, new int[]{8, 45, 44}, new int[]{8, 4, 45}, new int[]{8, 5, 46}, new int[]{8, 10, 47}, new int[]{8, 11, 48}, new int[]{8, 82, 49}, new int[]{8, 83, 50}, new int[]{8, 84, 51}, new int[]{8, 85, 52}, new int[]{8, 36, 53}, new int[]{8, 37, 54}, new int[]{8, 88, 55}, new int[]{8, 89, 56}, new int[]{8, 90, 57}, new int[]{8, 91, 58}, new int[]{8, 74, 59}, new int[]{8, 75, 60}, new int[]{8, 50, 61}, new int[]{8, 51, 62}, new int[]{8, 52, 63}, new int[]{5, 27, 64}, new int[]{5, 18, 128}, new int[]{6, 23, 192}, new int[]{7, 55, 256}, new int[]{8, 54, TIFFConstants.TIFFTAG_COLORMAP}, new int[]{8, 55, 384}, new int[]{8, 100, 448}, new int[]{8, 101, 512}, new int[]{8, 104, 576}, new int[]{8, 103, 640}, new int[]{9, XMPError.BADSTREAM, TypedValues.TransitionType.TYPE_AUTO_TRANSITION}, new int[]{9, 205, ViewUtils.EDGE_TO_EDGE_FLAGS}, new int[]{9, 210, 832}, new int[]{9, Primes.SMALL_FACTOR_LIMIT, 896}, new int[]{9, 212, 960}, new int[]{9, 213, 1024}, new int[]{9, 214, 1088}, new int[]{9, 215, 1152}, new int[]{9, 216, 1216}, new int[]{9, 217, 1280}, new int[]{9, 218, Polynomial.PRIVATE_KEY_I}, new int[]{9, 219, 1408}, new int[]{9, CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, 1472}, new int[]{9, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 1536}, new int[]{9, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 1600}, new int[]{6, 24, 1664}, new int[]{9, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, 1728}, new int[]{11, 8, 1792}, new int[]{11, 12, 1856}, new int[]{11, 13, 1920}, new int[]{12, 18, 1984}, new int[]{12, 19, 2048}, new int[]{12, 20, Polynomial.PRIVATE_KEY_III_SIZE}, new int[]{12, 21, 2176}, new int[]{12, 22, 2240}, new int[]{12, 23, 2304}, new int[]{12, 28, 2368}, new int[]{12, 29, 2432}, new int[]{12, 30, 2496}, new int[]{12, 31, 2560}, new int[]{12, 1, -1}, new int[]{9, 1, -2}, new int[]{10, 1, -2}, new int[]{11, 1, -2}, new int[]{12, 0, -2}};
    private int bit = 8;
    private int data;
    private byte[] dataBp;
    private int[] horizcode = {3, 1, 0};
    private int[] msbmask = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    private int offsetData;
    private ByteBuffer outBuf = new ByteBuffer(1024);
    private int[] passcode = {4, 1, 0};
    private byte[] refline;
    private int rowbytes;
    private int rowpixels;
    private int sizeData;
    private int[][] vcodes = {new int[]{7, 3, 0}, new int[]{6, 3, 0}, new int[]{3, 3, 0}, new int[]{1, 1, 0}, new int[]{3, 2, 0}, new int[]{6, 2, 0}, new int[]{7, 2, 0}};

    public CCITTG4Encoder(int width) {
        int i = width;
        this.rowpixels = i;
        int i2 = (i + 7) / 8;
        this.rowbytes = i2;
        this.refline = new byte[i2];
    }

    public void fax4Encode(byte[] data2, int offset, int size) {
        this.dataBp = data2;
        this.offsetData = offset;
        this.sizeData = size;
        while (this.sizeData > 0) {
            Fax3Encode2DRow();
            System.arraycopy(this.dataBp, this.offsetData, this.refline, 0, this.rowbytes);
            int i = this.offsetData;
            int i2 = this.rowbytes;
            this.offsetData = i + i2;
            this.sizeData -= i2;
        }
    }

    public static byte[] compress(byte[] data2, int width, int height) {
        CCITTG4Encoder g4 = new CCITTG4Encoder(width);
        g4.fax4Encode(data2, 0, g4.rowbytes * height);
        return g4.close();
    }

    public void fax4Encode(byte[] data2, int height) {
        fax4Encode(data2, 0, this.rowbytes * height);
    }

    private void putcode(int[] table) {
        putBits(table[1], table[0]);
    }

    private void putspan(int span, int[][] tab) {
        while (span >= 2624) {
            int[] te = tab[103];
            putBits(te[1], te[0]);
            span -= te[2];
        }
        if (span >= 64) {
            int[] te2 = tab[(span >> 6) + 63];
            putBits(te2[1], te2[0]);
            span -= te2[2];
        }
        putBits(tab[span][1], tab[span][0]);
    }

    private void putBits(int bits, int length) {
        int i;
        while (true) {
            i = this.bit;
            if (length <= i) {
                break;
            }
            int i2 = this.data | (bits >> (length - i));
            this.data = i2;
            length -= i;
            this.outBuf.append((byte) i2);
            this.data = 0;
            this.bit = 8;
        }
        int i3 = this.data | ((this.msbmask[length] & bits) << (i - length));
        this.data = i3;
        int i4 = i - length;
        this.bit = i4;
        if (i4 == 0) {
            this.outBuf.append((byte) i3);
            this.data = 0;
            this.bit = 8;
        }
    }

    private void Fax3Encode2DRow() {
        int a0 = 0;
        int a1 = pixel(this.dataBp, this.offsetData, 0) != 0 ? 0 : finddiff(this.dataBp, this.offsetData, 0, this.rowpixels, 0);
        int b1 = pixel(this.refline, 0, 0) != 0 ? 0 : finddiff(this.refline, 0, 0, this.rowpixels, 0);
        while (true) {
            byte[] bArr = this.refline;
            int b2 = finddiff2(bArr, 0, b1, this.rowpixels, pixel(bArr, 0, b1));
            if (b2 >= a1) {
                int d = b1 - a1;
                if (-3 > d || d > 3) {
                    byte[] bArr2 = this.dataBp;
                    int i = this.offsetData;
                    int a2 = finddiff2(bArr2, i, a1, this.rowpixels, pixel(bArr2, i, a1));
                    putcode(this.horizcode);
                    if (a0 + a1 == 0 || pixel(this.dataBp, this.offsetData, a0) == 0) {
                        putspan(a1 - a0, this.TIFFFaxWhiteCodes);
                        putspan(a2 - a1, this.TIFFFaxBlackCodes);
                    } else {
                        putspan(a1 - a0, this.TIFFFaxBlackCodes);
                        putspan(a2 - a1, this.TIFFFaxWhiteCodes);
                    }
                    a0 = a2;
                } else {
                    putcode(this.vcodes[d + 3]);
                    a0 = a1;
                }
            } else {
                putcode(this.passcode);
                a0 = b2;
            }
            int i2 = this.rowpixels;
            if (a0 < i2) {
                byte[] bArr3 = this.dataBp;
                int i3 = this.offsetData;
                a1 = finddiff(bArr3, i3, a0, i2, pixel(bArr3, i3, a0));
                b1 = finddiff(this.refline, 0, finddiff(this.refline, 0, a0, this.rowpixels, pixel(this.dataBp, this.offsetData, a0) ^ 1), this.rowpixels, pixel(this.dataBp, this.offsetData, a0));
            } else {
                return;
            }
        }
    }

    private void Fax4PostEncode() {
        putBits(1, 12);
        putBits(1, 12);
        if (this.bit != 8) {
            this.outBuf.append((byte) this.data);
            this.data = 0;
            this.bit = 8;
        }
    }

    public byte[] close() {
        Fax4PostEncode();
        return this.outBuf.toByteArray();
    }

    private int pixel(byte[] data2, int offset, int bit2) {
        if (bit2 >= this.rowpixels) {
            return 0;
        }
        return ((data2[(bit2 >> 3) + offset] & UByte.MAX_VALUE) >> (7 - (bit2 & 7))) & 1;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: byte} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int find1span(byte[] r6, int r7, int r8, int r9) {
        /*
            int r0 = r9 - r8
            int r1 = r8 >> 3
            int r1 = r1 + r7
            r2 = 8
            if (r0 <= 0) goto L_0x0029
            r3 = r8 & 7
            r4 = r3
            if (r3 == 0) goto L_0x0029
            byte[] r3 = oneruns
            byte r5 = r6[r1]
            int r5 = r5 << r4
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r3 = r3[r5]
            int r5 = 8 - r4
            if (r3 <= r5) goto L_0x001d
            int r3 = 8 - r4
        L_0x001d:
            if (r3 <= r0) goto L_0x0020
            r3 = r0
        L_0x0020:
            int r5 = r4 + r3
            if (r5 >= r2) goto L_0x0025
            return r3
        L_0x0025:
            int r0 = r0 - r3
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0029:
            r3 = 0
        L_0x002a:
            if (r0 < r2) goto L_0x0042
            byte r4 = r6[r1]
            r5 = -1
            if (r4 == r5) goto L_0x003b
            byte[] r2 = oneruns
            byte r4 = r6[r1]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r2 = r2[r4]
            int r2 = r2 + r3
            return r2
        L_0x003b:
            int r3 = r3 + 8
            int r0 = r0 + -8
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0042:
            if (r0 <= 0) goto L_0x0052
            byte[] r2 = oneruns
            byte r4 = r6[r1]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r2 = r2[r4]
            if (r2 <= r0) goto L_0x0050
            r4 = r0
            goto L_0x0051
        L_0x0050:
            r4 = r2
        L_0x0051:
            int r3 = r3 + r4
        L_0x0052:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.CCITTG4Encoder.find1span(byte[], int, int, int):int");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: byte} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int find0span(byte[] r6, int r7, int r8, int r9) {
        /*
            int r0 = r9 - r8
            int r1 = r8 >> 3
            int r1 = r1 + r7
            r2 = 8
            if (r0 <= 0) goto L_0x0029
            r3 = r8 & 7
            r4 = r3
            if (r3 == 0) goto L_0x0029
            byte[] r3 = zeroruns
            byte r5 = r6[r1]
            int r5 = r5 << r4
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r3 = r3[r5]
            int r5 = 8 - r4
            if (r3 <= r5) goto L_0x001d
            int r3 = 8 - r4
        L_0x001d:
            if (r3 <= r0) goto L_0x0020
            r3 = r0
        L_0x0020:
            int r5 = r4 + r3
            if (r5 >= r2) goto L_0x0025
            return r3
        L_0x0025:
            int r0 = r0 - r3
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0029:
            r3 = 0
        L_0x002a:
            if (r0 < r2) goto L_0x0041
            byte r4 = r6[r1]
            if (r4 == 0) goto L_0x003a
            byte[] r2 = zeroruns
            byte r4 = r6[r1]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r2 = r2[r4]
            int r2 = r2 + r3
            return r2
        L_0x003a:
            int r3 = r3 + 8
            int r0 = r0 + -8
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0041:
            if (r0 <= 0) goto L_0x0051
            byte[] r2 = zeroruns
            byte r4 = r6[r1]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r2 = r2[r4]
            if (r2 <= r0) goto L_0x004f
            r4 = r0
            goto L_0x0050
        L_0x004f:
            r4 = r2
        L_0x0050:
            int r3 = r3 + r4
        L_0x0051:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.CCITTG4Encoder.find0span(byte[], int, int, int):int");
    }

    private static int finddiff(byte[] bp, int offset, int bs, int be, int color) {
        return (color != 0 ? find1span(bp, offset, bs, be) : find0span(bp, offset, bs, be)) + bs;
    }

    private static int finddiff2(byte[] bp, int offset, int bs, int be, int color) {
        return bs < be ? finddiff(bp, offset, bs, be, color) : be;
    }
}

package com.itextpdf.barcodes;

import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.barcodes.dmcode.DmParams;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.codec.TIFFConstants;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;
import kotlin.UByte;
import org.bouncycastle.crypto.tls.CipherSuite;

public class BarcodeDataMatrix extends Barcode2D {
    public static final String DEFAULT_DATA_MATRIX_ENCODING = "iso-8859-1";
    public static final int DM_ASCII = 1;
    public static final int DM_AUTO = 0;
    public static final int DM_B256 = 4;
    public static final int DM_C40 = 2;
    public static final int DM_EDIFACT = 6;
    public static final int DM_ERROR_EXTENSION = 5;
    public static final int DM_ERROR_INVALID_SQUARE = 3;
    public static final int DM_ERROR_TEXT_TOO_BIG = 1;
    public static final int DM_EXTENSION = 32;
    public static final int DM_NO_ERROR = 0;
    public static final int DM_RAW = 7;
    public static final int DM_TEST = 64;
    public static final int DM_TEXT = 3;
    public static final int DM_X12 = 5;
    private static final byte EXTENDED_ASCII = -21;
    private static final byte LATCH_B256 = -25;
    private static final byte LATCH_C40 = -26;
    private static final byte LATCH_EDIFACT = -16;
    private static final byte LATCH_TEXT = -17;
    private static final byte LATCH_X12 = -18;
    private static final byte PADDING = -127;
    private static final byte UNLATCH = -2;
    private static final String X12 = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final DmParams[] dmSizes = {new DmParams(10, 10, 10, 10, 3, 3, 5), new DmParams(12, 12, 12, 12, 5, 5, 7), new DmParams(8, 18, 8, 18, 5, 5, 7), new DmParams(14, 14, 14, 14, 8, 8, 10), new DmParams(8, 32, 8, 16, 10, 10, 11), new DmParams(16, 16, 16, 16, 12, 12, 12), new DmParams(12, 26, 12, 26, 16, 16, 14), new DmParams(18, 18, 18, 18, 18, 18, 14), new DmParams(20, 20, 20, 20, 22, 22, 18), new DmParams(12, 36, 12, 18, 22, 22, 18), new DmParams(22, 22, 22, 22, 30, 30, 20), new DmParams(16, 36, 16, 18, 32, 32, 24), new DmParams(24, 24, 24, 24, 36, 36, 24), new DmParams(26, 26, 26, 26, 44, 44, 28), new DmParams(16, 48, 16, 24, 49, 49, 28), new DmParams(32, 32, 16, 16, 62, 62, 36), new DmParams(36, 36, 18, 18, 86, 86, 42), new DmParams(40, 40, 20, 20, 114, 114, 48), new DmParams(44, 44, 22, 22, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 56), new DmParams(48, 48, 24, 24, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 68), new DmParams(52, 52, 26, 26, XMPError.BADSTREAM, 102, 42), new DmParams(64, 64, 16, 16, TIFFConstants.TIFFTAG_MINSAMPLEVALUE, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 56), new DmParams(72, 72, 18, 18, 368, 92, 36), new DmParams(80, 80, 20, 20, 456, 114, 48), new DmParams(88, 88, 22, 22, 576, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 56), new DmParams(96, 96, 24, 24, 696, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 68), new DmParams(104, 104, 26, 26, 816, 136, 56), new DmParams(120, 120, 20, 20, 1050, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 68), new DmParams(CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 22, 22, 1304, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 62), new DmParams(CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 24, 24, 1558, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 62)};
    private String encoding;
    private int extOut;

    /* renamed from: f */
    private int[][] f1172f;
    private int height;
    private byte[] image;
    private int options;
    private short[] place;
    private int[][] switchMode;
    private int width;

    /* renamed from: ws */
    private int f1173ws;

    public BarcodeDataMatrix() {
        this.encoding = DEFAULT_DATA_MATRIX_ENCODING;
    }

    public BarcodeDataMatrix(String code) {
        this.encoding = DEFAULT_DATA_MATRIX_ENCODING;
        setCode(code);
    }

    public BarcodeDataMatrix(String code, String encoding2) {
        this.encoding = encoding2;
        setCode(code);
    }

    public Rectangle getBarcodeSize() {
        int i = this.width;
        int i2 = this.f1173ws;
        return new Rectangle(0.0f, 0.0f, (float) (i + (i2 * 2)), (float) (this.height + (i2 * 2)));
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color foreground) {
        return placeBarcode(canvas, foreground, 1.0f);
    }

    public PdfFormXObject createFormXObject(Color foreground, PdfDocument document) {
        return createFormXObject(foreground, 1.0f, document);
    }

    public PdfFormXObject createFormXObject(Color foreground, float moduleSide, PdfDocument document) {
        Rectangle rectangle = null;
        PdfFormXObject xObject = new PdfFormXObject((Rectangle) null);
        xObject.setBBox(new PdfArray(placeBarcode(new PdfCanvas(xObject, document), foreground, moduleSide)));
        return xObject;
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color foreground, float moduleSide) {
        int stride;
        int w;
        float f = moduleSide;
        if (this.image == null) {
            return null;
        }
        if (foreground != null) {
            canvas.setFillColor(foreground);
        }
        int i = this.width;
        int i2 = this.f1173ws;
        int w2 = i + (i2 * 2);
        int h = this.height + (i2 * 2);
        int stride2 = (w2 + 7) / 8;
        for (int k = 0; k < h; k++) {
            int p = k * stride2;
            int j = 0;
            while (j < w2) {
                if ((((this.image[(j / 8) + p] & 255) << (j % 8)) & 128) != 0) {
                    w = w2;
                    stride = stride2;
                    canvas.rectangle((double) (((float) j) * f), (double) (((float) ((h - k) - 1)) * f), (double) f, (double) f);
                } else {
                    w = w2;
                    stride = stride2;
                }
                j++;
                w2 = w;
                stride2 = stride;
            }
            int i3 = stride2;
        }
        canvas.fill();
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        if (this.image == null) {
            return null;
        }
        int f = foreground.getRGB();
        int g = background.getRGB();
        Canvas canvas = new Canvas();
        int i = this.width;
        int i2 = this.f1173ws;
        int w = i + (i2 * 2);
        int h = this.height + (i2 * 2);
        int[] pix = new int[(w * h)];
        int stride = (w + 7) / 8;
        int ptr = 0;
        for (int k = 0; k < h; k++) {
            int p = k * stride;
            int j = 0;
            while (j < w) {
                int ptr2 = ptr + 1;
                pix[ptr] = (((this.image[(j / 8) + p] & 255) << (j % 8)) & 128) == 0 ? g : f;
                j++;
                ptr = ptr2;
            }
        }
        return canvas.createImage(new MemoryImageSource(w, h, pix, 0, w));
    }

    public Rectangle getBarcodeSize(float moduleHeight, float moduleWidth) {
        int i = this.width;
        int i2 = this.f1173ws;
        return new Rectangle(0.0f, 0.0f, ((float) (i + (i2 * 2))) * moduleHeight, ((float) (this.height + (i2 * 2))) * moduleWidth);
    }

    public int setCode(String text) {
        try {
            byte[] t = text.getBytes(this.encoding);
            return setCode(t, 0, t.length);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("text has to be encoded in iso-8859-1");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00f6 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCode(byte[] r23, int r24, int r25) {
        /*
            r22 = this;
            r9 = r22
            r10 = r23
            r11 = r24
            r12 = r25
            java.lang.String r0 = ""
            if (r11 < 0) goto L_0x0161
            int r1 = r11 + r12
            int r2 = r10.length
            if (r1 > r2) goto L_0x014a
            if (r12 < 0) goto L_0x014a
            r0 = 2500(0x9c4, float:3.503E-42)
            byte[] r13 = new byte[r0]
            r14 = 0
            r9.extOut = r14
            int r15 = r9.processExtensions(r10, r11, r12, r13)
            if (r15 >= 0) goto L_0x0022
            r0 = 5
            return r0
        L_0x0022:
            r16 = -1
            int r0 = r9.extOut
            int r0 = r12 - r0
            r8 = 2
            int[] r1 = new int[r8]
            r17 = 1
            r1[r17] = r0
            r0 = 6
            r1[r14] = r0
            java.lang.Class r2 = java.lang.Integer.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r2, r1)
            int[][] r1 = (int[][]) r1
            r9.f1172f = r1
            int r1 = r9.extOut
            int r1 = r12 - r1
            int[] r2 = new int[r8]
            r2[r17] = r1
            r2[r14] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r0, r2)
            int[][] r0 = (int[][]) r0
            r9.switchMode = r0
            int r0 = r9.height
            if (r0 == 0) goto L_0x00ac
            int r0 = r9.width
            if (r0 != 0) goto L_0x005b
            r20 = 2
            goto L_0x00ae
        L_0x005b:
            r0 = 0
            r7 = r0
        L_0x005d:
            com.itextpdf.barcodes.dmcode.DmParams[] r0 = dmSizes
            int r1 = r0.length
            if (r7 >= r1) goto L_0x0076
            int r1 = r9.height
            r2 = r0[r7]
            int r2 = r2.height
            if (r1 != r2) goto L_0x0073
            int r1 = r9.width
            r2 = r0[r7]
            int r2 = r2.width
            if (r1 != r2) goto L_0x0073
            goto L_0x0076
        L_0x0073:
            int r7 = r7 + 1
            goto L_0x005d
        L_0x0076:
            int r1 = r0.length
            if (r7 != r1) goto L_0x007b
            r0 = 3
            return r0
        L_0x007b:
            r6 = r0[r7]
            int r0 = r9.extOut
            int r2 = r11 + r0
            int r3 = r12 - r0
            int r0 = r6.dataSize
            int r18 = r0 - r15
            int r5 = r9.options
            r19 = 1
            r0 = r22
            r1 = r23
            r4 = r13
            r20 = r5
            r5 = r15
            r21 = r6
            r6 = r18
            r18 = r7
            r7 = r20
            r20 = 2
            r8 = r19
            int r0 = r0.getEncodation(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 >= 0) goto L_0x00a6
            return r17
        L_0x00a6:
            int r0 = r0 + r15
            r7 = r18
            r6 = r21
            goto L_0x00f0
        L_0x00ac:
            r20 = 2
        L_0x00ae:
            com.itextpdf.barcodes.dmcode.DmParams[] r0 = dmSizes
            int r1 = r0.length
            int r1 = r1 + -1
            r8 = r0[r1]
            int r0 = r9.extOut
            int r2 = r11 + r0
            int r3 = r12 - r0
            int r0 = r8.dataSize
            int r6 = r0 - r15
            int r7 = r9.options
            r18 = 0
            r0 = r22
            r1 = r23
            r4 = r13
            r5 = r15
            r19 = r8
            r8 = r18
            int r0 = r0.getEncodation(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 >= 0) goto L_0x00d4
            return r17
        L_0x00d4:
            int r0 = r0 + r15
            r1 = 0
            r7 = r1
        L_0x00d7:
            com.itextpdf.barcodes.dmcode.DmParams[] r1 = dmSizes
            int r2 = r1.length
            if (r7 >= r2) goto L_0x00e6
            r2 = r1[r7]
            int r2 = r2.dataSize
            if (r2 < r0) goto L_0x00e3
            goto L_0x00e6
        L_0x00e3:
            int r7 = r7 + 1
            goto L_0x00d7
        L_0x00e6:
            r6 = r1[r7]
            int r1 = r6.height
            r9.height = r1
            int r1 = r6.width
            r9.width = r1
        L_0x00f0:
            int r1 = r9.options
            r1 = r1 & 64
            if (r1 == 0) goto L_0x00f7
            return r14
        L_0x00f7:
            int r1 = r6.width
            int r2 = r9.f1173ws
            int r2 = r2 * 2
            int r1 = r1 + r2
            int r1 = r1 + 7
            int r1 = r1 / 8
            int r2 = r6.height
            int r3 = r9.f1173ws
            int r3 = r3 * 2
            int r2 = r2 + r3
            int r1 = r1 * r2
            byte[] r1 = new byte[r1]
            r9.image = r1
            int r1 = r6.dataSize
            int r1 = r1 - r0
            makePadding(r13, r0, r1)
            int r1 = r6.height
            int r2 = r6.height
            int r3 = r6.heightSection
            int r2 = r2 / r3
            int r2 = r2 * 2
            int r1 = r1 - r2
            int r2 = r6.width
            int r3 = r6.width
            int r4 = r6.widthSection
            int r3 = r3 / r4
            int r3 = r3 * 2
            int r2 = r2 - r3
            short[] r1 = com.itextpdf.barcodes.dmcode.Placement.doPlacement(r1, r2)
            r9.place = r1
            int r1 = r6.dataSize
            int r2 = r6.dataSize
            int r2 = r2 + 2
            int r3 = r6.dataBlock
            int r2 = r2 / r3
            int r3 = r6.errorBlock
            int r2 = r2 * r3
            int r1 = r1 + r2
            int r2 = r6.dataSize
            int r3 = r6.dataBlock
            int r4 = r6.errorBlock
            com.itextpdf.barcodes.dmcode.ReedSolomon.generateECC(r13, r2, r3, r4)
            r9.draw(r13, r1, r6)
            return r14
        L_0x014a:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x0161:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            goto L_0x0179
        L_0x0178:
            throw r1
        L_0x0179:
            goto L_0x0178
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.barcodes.BarcodeDataMatrix.setCode(byte[], int, int):int");
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public int getWs() {
        return this.f1173ws;
    }

    public void setWs(int ws) {
        this.f1173ws = ws;
    }

    public int getOptions() {
        return this.options;
    }

    public void setOptions(int options2) {
        this.options = options2;
    }

    public void setEncoding(String encoding2) {
        this.encoding = encoding2;
    }

    public String getEncoding() {
        return this.encoding;
    }

    private static void makePadding(byte[] data, int position, int count) {
        if (count > 0) {
            int position2 = position + 1;
            data[position] = PADDING;
            while (true) {
                count--;
                if (count > 0) {
                    int t = (((position2 + 1) * CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA) % 253) + 129 + 1;
                    if (t > 254) {
                        t -= 254;
                    }
                    data[position2] = (byte) t;
                    position2++;
                } else {
                    return;
                }
            }
        }
    }

    private static boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    private int asciiEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset) {
        int c = textOffset;
        int ptrOut = dataOffset;
        int textLength2 = textLength + textOffset;
        int dataLength2 = dataLength + dataOffset;
        while (c < textLength2) {
            int ptrIn = c + 1;
            int c2 = text[c] & 255;
            if (!isDigit(c2) || symbolIndex <= 0) {
                int i = prevEnc;
            } else if (prevEnc == 1 && isDigit(text[ptrIn - 2] & UByte.MAX_VALUE) && data[dataOffset - 1] > 48 && data[dataOffset - 1] < 59) {
                data[ptrOut - 1] = (byte) ((((((text[ptrIn - 2] & UByte.MAX_VALUE) - 48) * 10) + c2) - 48) + 130);
                return ptrOut - origDataOffset;
            }
            if (ptrOut >= dataLength2) {
                return -1;
            }
            if (isDigit(c2) && symbolIndex < 0 && ptrIn < textLength2 && isDigit(text[ptrIn] & UByte.MAX_VALUE)) {
                data[ptrOut] = (byte) (((((c2 - 48) * 10) + (text[ptrIn] & UByte.MAX_VALUE)) - 48) + 130);
                ptrOut++;
                c = ptrIn + 1;
            } else if (c2 <= 127) {
                data[ptrOut] = (byte) (c2 + 1);
                c = ptrIn;
                ptrOut++;
            } else if (ptrOut + 1 >= dataLength2) {
                return -1;
            } else {
                int ptrOut2 = ptrOut + 1;
                data[ptrOut] = EXTENDED_ASCII;
                ptrOut = ptrOut2 + 1;
                data[ptrOut2] = (byte) ((c2 - 128) + 1);
                c = ptrIn;
            }
        }
        int i2 = prevEnc;
        return ptrOut - origDataOffset;
    }

    private int b256Encodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset) {
        int textLength2 = textLength;
        byte[] bArr = data;
        int i = dataLength;
        int i2 = prevEnc;
        int minRequiredDataIncrement = 0;
        if (textLength2 == 0) {
            return 0;
        }
        int simulatedDataOffset = dataOffset;
        if (i2 == 4) {
            int latestModeEntry = symbolIndex - 1;
            while (latestModeEntry > 0 && this.switchMode[3][latestModeEntry] == 4) {
                latestModeEntry--;
            }
            textLength2 = (symbolIndex - latestModeEntry) + 1;
            if (textLength2 != 250 && 1 > i) {
                return -1;
            }
            if (textLength2 == 250 && 2 > i) {
                return -1;
            }
            simulatedDataOffset -= (textLength2 - 1) + (textLength2 < 250 ? 2 : 3);
        } else if (textLength2 < 250 && textLength2 + 2 > i) {
            return -1;
        } else {
            if (textLength2 >= 250 && textLength2 + 3 > i) {
                return -1;
            }
            bArr[dataOffset] = LATCH_B256;
        }
        if (textLength2 < 250) {
            bArr[simulatedDataOffset + 1] = (byte) textLength2;
            if (i2 != 4) {
                minRequiredDataIncrement = 2;
            }
        } else if (textLength2 == 250 && i2 == 4) {
            bArr[simulatedDataOffset + 1] = (byte) ((textLength2 / ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) + 249);
            for (int i3 = dataOffset + 1; i3 > simulatedDataOffset + 2; i3--) {
                bArr[i3] = bArr[i3 - 1];
            }
            bArr[simulatedDataOffset + 2] = (byte) (textLength2 % ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            minRequiredDataIncrement = 1;
        } else {
            bArr[simulatedDataOffset + 1] = (byte) ((textLength2 / ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) + 249);
            bArr[simulatedDataOffset + 2] = (byte) (textLength2 % ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            if (i2 != 4) {
                minRequiredDataIncrement = 3;
            }
        }
        if (i2 == 4) {
            textLength2 = 1;
        }
        System.arraycopy(text, textOffset, bArr, minRequiredDataIncrement + dataOffset, textLength2);
        for (int j = i2 != 4 ? dataOffset + 1 : dataOffset; j < minRequiredDataIncrement + textLength2 + dataOffset; j++) {
            randomizationAlgorithm255(bArr, j);
        }
        if (i2 == 4) {
            randomizationAlgorithm255(bArr, simulatedDataOffset + 1);
        }
        return ((textLength2 + dataOffset) + minRequiredDataIncrement) - origDataOffset;
    }

    private void randomizationAlgorithm255(byte[] data, int j) {
        int tv = (data[j] & 255) + (((j + 1) * CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA) % 255) + 1;
        if (tv > 255) {
            tv += InputDeviceCompat.SOURCE_ANY;
        }
        data[j] = (byte) tv;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01fe  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0233  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int X12Encodation(byte[] r29, int r30, int r31, byte[] r32, int r33, int r34, int r35, int r36, int r37) {
        /*
            r28 = this;
            r0 = r31
            r10 = r35
            r1 = 1
            r11 = 0
            if (r0 != 0) goto L_0x0009
            return r11
        L_0x0009:
            r2 = 0
            r3 = 0
            byte[] r4 = new byte[r0]
            r5 = 0
        L_0x000e:
            r6 = 6
            r12 = 100
            java.lang.String r13 = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            r14 = 1
            if (r2 >= r0) goto L_0x003f
            int r7 = r2 + r30
            byte r7 = r29[r7]
            char r7 = (char) r7
            int r7 = r13.indexOf(r7)
            if (r7 < 0) goto L_0x0027
            byte r6 = (byte) r7
            r4[r2] = r6
            int r5 = r5 + 1
            goto L_0x003c
        L_0x0027:
            r4[r2] = r12
            if (r5 < r6) goto L_0x0030
            int r6 = r5 / 3
            int r6 = r6 * 3
            int r5 = r5 - r6
        L_0x0030:
            r6 = 0
        L_0x0031:
            if (r6 >= r5) goto L_0x003b
            int r8 = r2 - r6
            int r8 = r8 - r14
            r4[r8] = r12
            int r6 = r6 + 1
            goto L_0x0031
        L_0x003b:
            r5 = 0
        L_0x003c:
            int r2 = r2 + 1
            goto L_0x000e
        L_0x003f:
            if (r5 < r6) goto L_0x0048
            int r6 = r5 / 3
            int r6 = r6 * 3
            int r5 = r5 - r6
            r15 = r5
            goto L_0x0049
        L_0x0048:
            r15 = r5
        L_0x0049:
            r5 = 0
            r9 = r5
        L_0x004b:
            if (r9 >= r15) goto L_0x0055
            int r5 = r2 - r9
            int r5 = r5 - r14
            r4[r5] = r12
            int r9 = r9 + 1
            goto L_0x004b
        L_0x0055:
            r2 = 0
            r5 = 0
            r6 = r3
            r7 = r4
            r8 = r5
            r3 = r34
            r4 = r1
            r5 = r2
            r2 = r33
            r1 = r0
            r0 = r30
        L_0x0063:
            r17 = -1
            r12 = 40
            if (r5 >= r1) goto L_0x0250
            byte r8 = r7[r5]
            if (r6 <= r3) goto L_0x0073
            r34 = r4
            r27 = r9
            goto L_0x0256
        L_0x0073:
            if (r8 >= r12) goto L_0x00c2
            if (r5 != 0) goto L_0x0079
            if (r4 != 0) goto L_0x0081
        L_0x0079:
            if (r5 <= 0) goto L_0x0089
            int r19 = r5 + -1
            byte r11 = r7[r19]
            if (r11 <= r12) goto L_0x0089
        L_0x0081:
            int r11 = r6 + 1
            int r6 = r6 + r2
            r19 = -18
            r32[r6] = r19
            r6 = r11
        L_0x0089:
            int r11 = r6 + 2
            if (r11 <= r3) goto L_0x0093
            r34 = r4
            r27 = r9
            goto L_0x0256
        L_0x0093:
            byte r11 = r7[r5]
            int r11 = r11 * 1600
            int r17 = r5 + 1
            byte r17 = r7[r17]
            int r17 = r17 * 40
            int r11 = r11 + r17
            int r12 = r5 + 2
            byte r12 = r7[r12]
            int r11 = r11 + r12
            int r11 = r11 + r14
            int r12 = r6 + 1
            int r6 = r6 + r2
            int r14 = r11 / 256
            byte r14 = (byte) r14
            r32[r6] = r14
            int r6 = r12 + 1
            int r12 = r12 + r2
            byte r14 = (byte) r11
            r32[r12] = r14
            int r5 = r5 + 2
            r21 = r4
            r26 = r8
            r27 = r9
            r4 = 1
            r16 = 0
            r18 = 100
            goto L_0x0243
        L_0x00c2:
            r11 = 1
            if (r10 > 0) goto L_0x00f6
            if (r5 <= 0) goto L_0x00ea
            int r14 = r5 + -1
            byte r14 = r7[r14]
            if (r14 >= r12) goto L_0x00ea
            int r12 = r6 + 1
            int r6 = r6 + r2
            r14 = -2
            r32[r6] = r14
            r14 = r2
            r20 = r3
            r21 = r4
            r22 = r5
            r24 = r7
            r31 = r8
            r25 = r11
            r23 = r12
            r16 = 0
            r18 = 100
            r11 = r0
            r12 = r1
            goto L_0x01fc
        L_0x00ea:
            r34 = r4
            r33 = r6
            r31 = r8
            r16 = 0
            r18 = 100
            goto L_0x01ed
        L_0x00f6:
            r12 = 4
            if (r10 <= r12) goto L_0x01e3
            r14 = 5
            r31 = r8
            r8 = r36
            if (r8 != r14) goto L_0x01da
            byte r14 = r29[r0]
            char r14 = (char) r14
            int r14 = r13.indexOf(r14)
            if (r14 < 0) goto L_0x01da
            int r14 = r0 + -1
            byte r14 = r29[r14]
            char r14 = (char) r14
            int r14 = r13.indexOf(r14)
            if (r14 < 0) goto L_0x01da
            int r14 = r10 + -1
        L_0x0116:
            if (r14 <= 0) goto L_0x013c
            r8 = r28
            r34 = r4
            int[][] r4 = r8.switchMode
            r4 = r4[r12]
            r4 = r4[r14]
            r12 = 5
            if (r4 != r12) goto L_0x0140
            int r4 = r10 - r14
            r12 = 1
            int r4 = r4 + r12
            int r4 = r0 - r4
            byte r4 = r29[r4]
            char r4 = (char) r4
            int r4 = r13.indexOf(r4)
            if (r4 < 0) goto L_0x0140
            int r14 = r14 + -1
            r4 = r34
            r8 = r36
            r12 = 4
            goto L_0x0116
        L_0x013c:
            r8 = r28
            r34 = r4
        L_0x0140:
            r4 = -1
            int r12 = r10 - r14
            r20 = r4
            r4 = 5
            if (r12 < r4) goto L_0x01d3
            r4 = 1
        L_0x0149:
            int r12 = r10 - r14
            if (r4 > r12) goto L_0x015f
            int r12 = r2 - r4
            byte r12 = r32[r12]
            r33 = r6
            r6 = -2
            if (r12 != r6) goto L_0x015a
            int r6 = r2 - r4
            r4 = r6
            goto L_0x0163
        L_0x015a:
            int r4 = r4 + 1
            r6 = r33
            goto L_0x0149
        L_0x015f:
            r33 = r6
            r4 = r20
        L_0x0163:
            if (r4 < 0) goto L_0x016a
            int r6 = r2 - r4
            r12 = 1
            int r6 = r6 - r12
            goto L_0x016c
        L_0x016a:
            int r6 = r10 - r14
        L_0x016c:
            int r12 = r6 % 3
            r8 = 2
            if (r12 != r8) goto L_0x01ba
            r11 = 0
            int r1 = r6 + 1
            int r0 = r0 - r6
            if (r4 >= 0) goto L_0x0179
            r8 = r6
            goto L_0x017b
        L_0x0179:
            int r8 = r6 + 1
        L_0x017b:
            int r3 = r3 + r8
            if (r4 >= 0) goto L_0x0180
            r8 = r6
            goto L_0x0182
        L_0x0180:
            int r8 = r6 + 1
        L_0x0182:
            int r2 = r2 - r8
            r5 = -1
            if (r4 == r2) goto L_0x0188
            r8 = 1
            goto L_0x0189
        L_0x0188:
            r8 = 0
        L_0x0189:
            int r12 = r6 + 1
            byte[] r7 = new byte[r12]
            r12 = 0
        L_0x018e:
            if (r12 > r6) goto L_0x01a3
            int r20 = r0 + r12
            r30 = r0
            byte r0 = r29[r20]
            char r0 = (char) r0
            int r0 = r13.indexOf(r0)
            byte r0 = (byte) r0
            r7[r12] = r0
            int r12 = r12 + 1
            r0 = r30
            goto L_0x018e
        L_0x01a3:
            r30 = r0
            r23 = r33
            r12 = r1
            r14 = r2
            r20 = r3
            r22 = r5
            r24 = r7
            r21 = r8
            r25 = r11
            r16 = 0
            r18 = 100
            r11 = r30
            goto L_0x01fc
        L_0x01ba:
            r8 = 1
            byte[] r7 = new byte[r8]
            r16 = 0
            r18 = 100
            r7[r16] = r18
            r23 = r33
            r21 = r34
            r12 = r1
            r14 = r2
            r20 = r3
            r22 = r5
            r24 = r7
            r25 = r11
            r11 = r0
            goto L_0x01fc
        L_0x01d3:
            r33 = r6
            r16 = 0
            r18 = 100
            goto L_0x01ed
        L_0x01da:
            r34 = r4
            r33 = r6
            r16 = 0
            r18 = 100
            goto L_0x01ed
        L_0x01e3:
            r34 = r4
            r33 = r6
            r31 = r8
            r16 = 0
            r18 = 100
        L_0x01ed:
            r23 = r33
            r21 = r34
            r12 = r1
            r14 = r2
            r20 = r3
            r22 = r5
            r24 = r7
            r25 = r11
            r11 = r0
        L_0x01fc:
            if (r25 == 0) goto L_0x0233
            int r2 = r11 + r22
            r3 = 1
            int r5 = r14 + r23
            r7 = -1
            r8 = -1
            r0 = r28
            r1 = r29
            r4 = r32
            r6 = r20
            r26 = r31
            r27 = r9
            r9 = r37
            int r0 = r0.asciiEncodation(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            if (r0 >= 0) goto L_0x021a
            return r17
        L_0x021a:
            int r1 = r14 + r23
            byte r1 = r32[r1]
            r2 = -21
            if (r1 != r2) goto L_0x0224
            int r23 = r23 + 1
        L_0x0224:
            r4 = 1
            int r23 = r23 + 1
            r0 = r11
            r1 = r12
            r2 = r14
            r3 = r20
            r5 = r22
            r6 = r23
            r7 = r24
            goto L_0x0243
        L_0x0233:
            r26 = r31
            r27 = r9
            r4 = 1
            r0 = r11
            r1 = r12
            r2 = r14
            r3 = r20
            r5 = r22
            r6 = r23
            r7 = r24
        L_0x0243:
            int r5 = r5 + r4
            r4 = r21
            r8 = r26
            r9 = r27
            r11 = 0
            r12 = 100
            r14 = 1
            goto L_0x0063
        L_0x0250:
            r34 = r4
            r33 = r6
            r27 = r9
        L_0x0256:
            r4 = 100
            if (r1 <= 0) goto L_0x025e
            int r8 = r1 + -1
            byte r4 = r7[r8]
        L_0x025e:
            if (r5 == r1) goto L_0x0261
            return r17
        L_0x0261:
            if (r4 >= r12) goto L_0x026a
            int r8 = r6 + 1
            int r6 = r6 + r2
            r9 = -2
            r32[r6] = r9
            r6 = r8
        L_0x026a:
            if (r6 <= r3) goto L_0x026d
            return r17
        L_0x026d:
            int r8 = r6 + r2
            int r8 = r8 - r37
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.barcodes.BarcodeDataMatrix.X12Encodation(byte[], int, int, byte[], int, int, int, int, int):int");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v9, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v12, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v18, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v20, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v28, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v55, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v15, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v29, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v58, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v34, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v66, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v36, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v38, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v21, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v82, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v41, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v10, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v18, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v26, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v32, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v9, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r34v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v11, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v27, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v25, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v27, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v83, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v84, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r43v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v87, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v88, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: byte} */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b0, code lost:
        r19 = r1;
        r23 = -1;
        r6 = r4;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x03a7  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x03ef  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x040e  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x0460  */
    /* JADX WARNING: Removed duplicated region for block: B:222:0x04a8  */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x04a7 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int EdifactEncodation(byte[] r41, int r42, int r43, byte[] r44, int r45, int r46, int r47, int r48, int r49, boolean r50) {
        /*
            r40 = this;
            r10 = r40
            r11 = r41
            r0 = r45
            r12 = r47
            r13 = r48
            if (r43 != 0) goto L_0x000e
            r1 = 0
            return r1
        L_0x000e:
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 18
            r18 = 1
            r1 = -1
            r2 = -1
            r3 = -1
            r9 = 95
            r8 = 32
            r7 = 64
            r6 = 6
            r19 = r3
            r3 = 1
            if (r13 != r6) goto L_0x00bd
            byte r4 = r11[r42]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r4 = r4 & 224(0xe0, float:3.14E-43)
            if (r4 == r7) goto L_0x0035
            byte r4 = r11[r42]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r4 = r4 & 224(0xe0, float:3.14E-43)
            if (r4 != r8) goto L_0x00bd
        L_0x0035:
            byte r4 = r11[r42]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r4 == r9) goto L_0x00bd
            int r4 = r42 + -1
            byte r4 = r11[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r4 = r4 & 224(0xe0, float:3.14E-43)
            if (r4 == r7) goto L_0x004f
            int r4 = r42 + -1
            byte r4 = r11[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r4 = r4 & 224(0xe0, float:3.14E-43)
            if (r4 != r8) goto L_0x00bd
        L_0x004f:
            int r4 = r42 + -1
            byte r4 = r11[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r4 == r9) goto L_0x00bd
            int r1 = r12 + -1
        L_0x0059:
            r4 = 5
            if (r1 <= 0) goto L_0x007b
            int[][] r5 = r10.switchMode
            r5 = r5[r4]
            r5 = r5[r1]
            if (r5 != r6) goto L_0x007b
            int r5 = r12 - r1
            int r5 = r5 + r3
            int r5 = r42 - r5
            byte r5 = r11[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r6 = r5 & 224(0xe0, float:3.14E-43)
            if (r6 == r7) goto L_0x0075
            r6 = r5 & 224(0xe0, float:3.14E-43)
            if (r6 != r8) goto L_0x007b
        L_0x0075:
            if (r5 == r9) goto L_0x007b
            int r1 = r1 + -1
            r6 = 6
            goto L_0x0059
        L_0x007b:
            int[][] r5 = r10.switchMode
            r5 = r5[r4]
            r5 = r5[r1]
            r6 = 2
            if (r5 == r6) goto L_0x0088
            if (r5 != r4) goto L_0x0087
            goto L_0x0088
        L_0x0087:
            r5 = -1
        L_0x0088:
            r4 = r5
            if (r4 <= 0) goto L_0x008c
            r2 = r1
        L_0x008c:
            if (r4 <= 0) goto L_0x00b7
            if (r2 <= 0) goto L_0x00b7
            int[][] r5 = r10.switchMode
            int r6 = r4 + -1
            r5 = r5[r6]
            r5 = r5[r2]
            if (r5 != r4) goto L_0x00b7
            int r5 = r12 - r2
            int r5 = r5 + r3
            int r5 = r42 - r5
            byte r5 = r11[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r6 = r5 & 224(0xe0, float:3.14E-43)
            if (r6 == r7) goto L_0x00ab
            r6 = r5 & 224(0xe0, float:3.14E-43)
            if (r6 != r8) goto L_0x00b0
        L_0x00ab:
            if (r5 == r9) goto L_0x00b0
            int r2 = r2 + -1
            goto L_0x008c
        L_0x00b0:
            r2 = -1
            r19 = r1
            r23 = r2
            r6 = r4
            goto L_0x00c3
        L_0x00b7:
            r19 = r1
            r23 = r2
            r6 = r4
            goto L_0x00c3
        L_0x00bd:
            r23 = r2
            r6 = r19
            r19 = r1
        L_0x00c3:
            int r1 = r0 + r46
            r2 = 0
            r4 = -1
            if (r12 == r4) goto L_0x00ca
            r2 = 1
        L_0x00ca:
            r4 = 0
            r5 = 0
            r8 = 9
            r7 = 3
            if (r23 < 0) goto L_0x0170
            int r29 = r12 - r23
            int r9 = r29 + 1
            if (r9 <= r8) goto L_0x0170
            int r8 = r12 - r23
            int r8 = r8 + r3
            r4 = 0
            r5 = 0
            int r9 = r8 / 4
            int r9 = r9 * 3
            int r9 = r9 + r3
            int r5 = r5 + r9
            if (r50 != 0) goto L_0x0111
            int r9 = r11.length
            int r9 = r9 - r3
            if (r12 == r9) goto L_0x00ea
            if (r12 >= 0) goto L_0x0111
        L_0x00ea:
            int r9 = r8 % 4
            if (r9 >= r7) goto L_0x0111
            r1 = 2147483647(0x7fffffff, float:NaN)
            r9 = 0
        L_0x00f2:
            com.itextpdf.barcodes.dmcode.DmParams[] r7 = dmSizes
            int r3 = r7.length
            if (r9 >= r3) goto L_0x010f
            r3 = r7[r9]
            int r3 = r3.dataSize
            int r32 = r8 % 4
            r43 = r1
            int r1 = r5 + r32
            if (r3 < r1) goto L_0x0108
            r1 = r7[r9]
            int r1 = r1.dataSize
            goto L_0x0111
        L_0x0108:
            int r9 = r9 + 1
            r1 = r43
            r3 = 1
            r7 = 3
            goto L_0x00f2
        L_0x010f:
            r43 = r1
        L_0x0111:
            int r3 = r1 - r0
            int r3 = r3 - r5
            r7 = 2
            if (r3 > r7) goto L_0x011f
            int r3 = r8 % 4
            if (r3 > r7) goto L_0x011f
            int r3 = r8 % 4
            int r5 = r5 + r3
            goto L_0x012b
        L_0x011f:
            int r3 = r8 % 4
            r7 = 1
            int r3 = r3 + r7
            int r5 = r5 + r3
            int r3 = r8 % 4
            r7 = 3
            if (r3 != r7) goto L_0x012b
            int r5 = r5 + -1
        L_0x012b:
            int r3 = r0 + -1
        L_0x012d:
            if (r3 < 0) goto L_0x0141
            int r4 = r4 + 1
            byte r7 = r44[r3]
            r9 = 2
            if (r6 != r9) goto L_0x0139
            r9 = -26
            goto L_0x013b
        L_0x0139:
            r9 = -18
        L_0x013b:
            if (r7 != r9) goto L_0x013e
            goto L_0x0141
        L_0x013e:
            int r3 = r3 + -1
            goto L_0x012d
        L_0x0141:
            if (r5 > r4) goto L_0x015d
            r2 = 0
            int r3 = r8 + -1
            int r3 = r42 - r3
            int r0 = r0 - r4
            int r7 = r46 + r4
            r33 = r0
            r34 = r1
            r35 = r2
            r29 = r3
            r36 = r4
            r37 = r5
            r9 = r8
            r20 = 2
            r8 = r7
            goto L_0x0280
        L_0x015d:
            r29 = r42
            r33 = r0
            r34 = r1
            r35 = r2
            r36 = r4
            r37 = r5
            r9 = r8
            r20 = 2
            r8 = r46
            goto L_0x0280
        L_0x0170:
            if (r19 < 0) goto L_0x026a
            int r3 = r12 - r19
            r7 = 1
            int r3 = r3 + r7
            if (r3 <= r8) goto L_0x026a
            int r3 = r12 - r19
            int r3 = r3 + r7
            int r8 = r3 / 4
            r9 = 3
            int r8 = r8 * 3
            int r8 = r8 + r7
            int r5 = r5 + r8
            int r7 = r1 - r0
            int r7 = r7 - r5
            r8 = 2
            if (r7 > r8) goto L_0x0191
            int r7 = r3 % 4
            if (r7 > r8) goto L_0x0191
            int r7 = r3 % 4
            int r5 = r5 + r7
            r8 = 3
            goto L_0x019d
        L_0x0191:
            int r7 = r3 % 4
            r8 = 1
            int r7 = r7 + r8
            int r5 = r5 + r7
            int r7 = r3 % 4
            r8 = 3
            if (r7 != r8) goto L_0x019d
            int r5 = r5 + -1
        L_0x019d:
            r7 = 0
            r9 = -1
            r29 = r49
            r8 = r29
        L_0x01a3:
            if (r8 >= r0) goto L_0x01bc
            r29 = r1
            byte r1 = r44[r8]
            r33 = r2
            r2 = -16
            if (r1 != r2) goto L_0x01b5
            int r1 = r0 - r8
            if (r1 > r5) goto L_0x01b5
            r9 = r8
            goto L_0x01c0
        L_0x01b5:
            int r8 = r8 + 1
            r1 = r29
            r2 = r33
            goto L_0x01a3
        L_0x01bc:
            r29 = r1
            r33 = r2
        L_0x01c0:
            r8 = -1
            if (r9 == r8) goto L_0x0202
            int r1 = r0 - r9
            int r4 = r4 + r1
            byte r1 = r11[r42]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 127(0x7f, float:1.78E-43)
            if (r1 <= r2) goto L_0x01d3
            r20 = 2
            int r4 = r4 + 2
            goto L_0x01ff
        L_0x01d3:
            r20 = 2
            byte r1 = r11[r42]
            r1 = r1 & 255(0xff, float:3.57E-43)
            boolean r1 = isDigit(r1)
            if (r1 == 0) goto L_0x01fd
            int r1 = r42 + -1
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            boolean r1 = isDigit(r1)
            if (r1 == 0) goto L_0x01fd
            int r1 = r0 + -1
            byte r1 = r44[r1]
            r2 = 49
            if (r1 < r2) goto L_0x01fd
            int r1 = r0 + -1
            byte r1 = r44[r1]
            r2 = 58
            if (r1 > r2) goto L_0x01fd
            int r4 = r4 + -1
        L_0x01fd:
            r1 = 1
            int r4 = r4 + r1
        L_0x01ff:
            int r1 = r0 - r9
            goto L_0x0242
        L_0x0202:
            r20 = 2
            int r1 = r12 - r19
        L_0x0206:
            if (r1 < 0) goto L_0x0241
            int r2 = r42 - r1
            byte r2 = r11[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r8 = 127(0x7f, float:1.78E-43)
            if (r2 <= r8) goto L_0x0215
            int r4 = r4 + 2
            goto L_0x0238
        L_0x0215:
            if (r1 <= 0) goto L_0x0236
            int r2 = r42 - r1
            byte r2 = r11[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            boolean r2 = isDigit(r2)
            if (r2 == 0) goto L_0x0236
            int r2 = r42 - r1
            r8 = 1
            int r2 = r2 + r8
            byte r2 = r11[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            boolean r2 = isDigit(r2)
            if (r2 == 0) goto L_0x0236
            if (r1 != r8) goto L_0x0234
            r7 = r4
        L_0x0234:
            int r1 = r1 + -1
        L_0x0236:
            int r4 = r4 + 1
        L_0x0238:
            r2 = 1
            if (r1 != r2) goto L_0x023d
            r2 = r4
            r7 = r2
        L_0x023d:
            int r1 = r1 + -1
            r8 = -1
            goto L_0x0206
        L_0x0241:
            r1 = r7
        L_0x0242:
            if (r5 > r4) goto L_0x025a
            r2 = 0
            int r7 = r3 + -1
            int r7 = r42 - r7
            int r0 = r0 - r1
            int r8 = r46 + r1
            r33 = r0
            r35 = r2
            r9 = r3
            r36 = r4
            r37 = r5
            r34 = r29
            r29 = r7
            goto L_0x0280
        L_0x025a:
            r8 = r46
            r9 = r3
            r36 = r4
            r37 = r5
            r34 = r29
            r35 = r33
            r29 = r42
            r33 = r0
            goto L_0x0280
        L_0x026a:
            r29 = r1
            r33 = r2
            r20 = 2
            r9 = r43
            r8 = r46
            r36 = r4
            r37 = r5
            r34 = r29
            r35 = r33
            r29 = r42
            r33 = r0
        L_0x0280:
            if (r35 == 0) goto L_0x02ea
            byte r0 = r11[r29]
            r7 = r0 & 255(0xff, float:3.57E-43)
            boolean r0 = isDigit(r7)
            if (r0 == 0) goto L_0x02c8
            int r0 = r29 + r14
            if (r0 <= 0) goto L_0x02c8
            int r0 = r29 + -1
            byte r0 = r11[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            boolean r0 = isDigit(r0)
            if (r0 == 0) goto L_0x02c8
            r0 = 6
            if (r13 != r0) goto L_0x02c8
            int r0 = r33 + -1
            byte r0 = r44[r0]
            r1 = 49
            if (r0 < r1) goto L_0x02c8
            int r0 = r33 + -1
            byte r0 = r44[r0]
            r1 = 58
            if (r0 > r1) goto L_0x02c8
            int r0 = r33 + r15
            r1 = 1
            int r0 = r0 - r1
            int r1 = r29 + -1
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + -48
            int r1 = r1 * 10
            int r1 = r1 + r7
            int r1 = r1 + -48
            int r1 = r1 + 130
            byte r1 = (byte) r1
            r44[r0] = r1
            int r0 = r33 - r49
            return r0
        L_0x02c8:
            int r2 = r29 + r14
            r3 = 1
            int r5 = r33 + r15
            r20 = -1
            r21 = -1
            r0 = r40
            r1 = r41
            r4 = r44
            r38 = r6
            r6 = r8
            r22 = r7
            r7 = r20
            r39 = r8
            r8 = r21
            r10 = r9
            r9 = r49
            int r0 = r0.asciiEncodation(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            return r0
        L_0x02ea:
            r38 = r6
            r39 = r8
            r10 = r9
            r0 = r17
        L_0x02f1:
            r1 = 31
            r2 = 12
            if (r14 >= r10) goto L_0x04c3
            int r3 = r14 + r29
            byte r3 = r11[r3]
            r9 = r3 & 255(0xff, float:3.57E-43)
            r3 = r9 & 224(0xe0, float:3.14E-43)
            r7 = 64
            if (r3 == r7) goto L_0x0312
            r3 = r9 & 224(0xe0, float:3.14E-43)
            r8 = 32
            if (r3 != r8) goto L_0x030a
            goto L_0x0314
        L_0x030a:
            r5 = r39
            r6 = 95
            r17 = -16
            goto L_0x03a5
        L_0x0312:
            r8 = 32
        L_0x0314:
            r6 = 95
            if (r9 == r6) goto L_0x03a1
            if (r18 == 0) goto L_0x0336
            int r3 = r15 + 1
            r5 = r39
            if (r3 <= r5) goto L_0x032a
            r39 = r5
            r25 = 1
            r26 = -1
            r31 = 3
            goto L_0x04c9
        L_0x032a:
            int r3 = r15 + 1
            int r15 = r33 + r15
            r17 = -16
            r44[r15] = r17
            r18 = 0
            r15 = r3
            goto L_0x033a
        L_0x0336:
            r5 = r39
            r17 = -16
        L_0x033a:
            r9 = r9 & 63
            int r3 = r9 << r0
            r3 = r16 | r3
            if (r0 != 0) goto L_0x0387
            int r4 = r15 + 3
            if (r4 <= r5) goto L_0x0352
            r16 = r3
            r39 = r5
            r25 = 1
            r26 = -1
            r31 = 3
            goto L_0x04c9
        L_0x0352:
            int r1 = r15 + 1
            int r15 = r33 + r15
            int r2 = r3 >> 16
            byte r2 = (byte) r2
            r44[r15] = r2
            int r2 = r1 + 1
            int r1 = r33 + r1
            int r4 = r3 >> 8
            byte r4 = (byte) r4
            r44[r1] = r4
            int r1 = r2 + 1
            int r2 = r33 + r2
            byte r4 = (byte) r3
            r44[r2] = r4
            r2 = 0
            r0 = 18
            r15 = r1
            r16 = r2
            r39 = r5
            r20 = -16
            r21 = 58
            r22 = 49
            r25 = 1
            r26 = -1
            r27 = 64
            r28 = 32
            r30 = 95
            r31 = 3
            goto L_0x04bd
        L_0x0387:
            int r0 = r0 + -6
            r16 = r3
            r39 = r5
            r20 = -16
            r21 = 58
            r22 = 49
            r25 = 1
            r26 = -1
            r27 = 64
            r28 = 32
            r30 = 95
            r31 = 3
            goto L_0x04bd
        L_0x03a1:
            r5 = r39
            r17 = -16
        L_0x03a5:
            if (r18 != 0) goto L_0x03ef
            int r3 = r1 << r0
            r3 = r16 | r3
            int r4 = r15 + 3
            int r16 = r0 / 8
            int r4 = r4 - r16
            if (r4 <= r5) goto L_0x03bf
            r16 = r3
            r39 = r5
            r25 = 1
            r26 = -1
            r31 = 3
            goto L_0x04c9
        L_0x03bf:
            int r1 = r15 + 1
            int r15 = r33 + r15
            int r4 = r3 >> 16
            byte r4 = (byte) r4
            r44[r15] = r4
            if (r0 > r2) goto L_0x03d4
            int r2 = r1 + 1
            int r1 = r33 + r1
            int r4 = r3 >> 8
            byte r4 = (byte) r4
            r44[r1] = r4
            r1 = r2
        L_0x03d4:
            r2 = 6
            if (r0 > r2) goto L_0x03e0
            int r2 = r1 + 1
            int r1 = r33 + r1
            byte r4 = (byte) r3
            r44[r1] = r4
            r15 = r2
            goto L_0x03e1
        L_0x03e0:
            r15 = r1
        L_0x03e1:
            r18 = 1
            r0 = 18
            r16 = 0
            r24 = r18
            r18 = r16
            r16 = r15
            r15 = r0
            goto L_0x03f6
        L_0x03ef:
            r24 = r18
            r18 = r16
            r16 = r15
            r15 = r0
        L_0x03f6:
            boolean r0 = isDigit(r9)
            if (r0 == 0) goto L_0x0460
            int r0 = r29 + r14
            if (r0 <= 0) goto L_0x0460
            int r0 = r29 + r14
            r1 = 1
            int r0 = r0 - r1
            byte r0 = r11[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            boolean r0 = isDigit(r0)
            if (r0 == 0) goto L_0x0460
            r4 = 6
            if (r13 != r4) goto L_0x045b
            int r0 = r33 + -1
            byte r0 = r44[r0]
            r3 = 49
            if (r0 < r3) goto L_0x0458
            int r0 = r33 + -1
            byte r0 = r44[r0]
            r2 = 58
            if (r0 > r2) goto L_0x0465
            int r0 = r33 + r16
            r22 = 1
            int r0 = r0 + -1
            int r1 = r29 + -1
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + -48
            int r1 = r1 * 10
            int r1 = r1 + r9
            int r1 = r1 + -48
            int r1 = r1 + 130
            byte r1 = (byte) r1
            r44[r0] = r1
            int r16 = r16 + -1
            r39 = r5
            r0 = r15
            r15 = r16
            r16 = r18
            r18 = r24
            r20 = -16
            r21 = 58
            r22 = 49
            r25 = 1
            r26 = -1
            r27 = 64
            r28 = 32
            r30 = 95
            r31 = 3
            goto L_0x04bd
        L_0x0458:
            r2 = 58
            goto L_0x0465
        L_0x045b:
            r2 = 58
            r3 = 49
            goto L_0x0465
        L_0x0460:
            r2 = 58
            r3 = 49
            r4 = 6
        L_0x0465:
            r22 = 1
            int r25 = r29 + r14
            r26 = 1
            int r27 = r33 + r16
            r28 = -1
            r30 = -1
            r0 = r40
            r1 = r41
            r31 = 58
            r2 = r25
            r22 = 49
            r25 = 1
            r3 = r26
            r21 = 6
            r26 = -1
            r4 = r44
            r39 = r5
            r5 = r27
            r20 = 95
            r6 = r39
            r21 = 58
            r27 = 64
            r31 = 3
            r7 = r28
            r28 = 32
            r8 = r30
            r17 = r9
            r20 = -16
            r30 = 95
            r9 = r49
            int r0 = r0.asciiEncodation(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            if (r0 >= 0) goto L_0x04a8
            return r26
        L_0x04a8:
            int r1 = r33 + r16
            byte r1 = r44[r1]
            r2 = -21
            if (r1 != r2) goto L_0x04b2
            int r16 = r16 + 1
        L_0x04b2:
            int r16 = r16 + 1
            r0 = r15
            r15 = r16
            r9 = r17
            r16 = r18
            r18 = r24
        L_0x04bd:
            int r14 = r14 + 1
            r20 = 2
            goto L_0x02f1
        L_0x04c3:
            r25 = 1
            r26 = -1
            r31 = 3
        L_0x04c9:
            if (r14 == r10) goto L_0x04cc
            return r26
        L_0x04cc:
            if (r50 != 0) goto L_0x04f5
            int r3 = r11.length
            int r3 = r3 + -1
            if (r12 == r3) goto L_0x04d5
            if (r12 >= 0) goto L_0x04f5
        L_0x04d5:
            r34 = 2147483647(0x7fffffff, float:NaN)
            r3 = 0
        L_0x04d9:
            com.itextpdf.barcodes.dmcode.DmParams[] r4 = dmSizes
            int r5 = r4.length
            if (r3 >= r5) goto L_0x04f5
            r5 = r4[r3]
            int r5 = r5.dataSize
            int r6 = r33 + r15
            int r7 = r0 / 6
            int r7 = 3 - r7
            int r6 = r6 + r7
            if (r5 < r6) goto L_0x04f2
            r4 = r4[r3]
            int r4 = r4.dataSize
            r34 = r4
            goto L_0x04f5
        L_0x04f2:
            int r3 = r3 + 1
            goto L_0x04d9
        L_0x04f5:
            int r3 = r34 - r33
            int r3 = r3 - r15
            r4 = 2
            if (r3 > r4) goto L_0x0542
            r3 = 6
            if (r0 < r3) goto L_0x053f
            r1 = 18
            if (r0 == r1) goto L_0x050c
            int r1 = r15 + 2
            int r4 = r0 / 8
            int r1 = r1 - r4
            r7 = r39
            if (r1 <= r7) goto L_0x050e
            return r26
        L_0x050c:
            r7 = r39
        L_0x050e:
            if (r0 > r2) goto L_0x0526
            int r1 = r16 >> 18
            r1 = r1 & 63
            byte r1 = (byte) r1
            r2 = r1 & 32
            if (r2 != 0) goto L_0x051c
            r2 = r1 | 64
            byte r1 = (byte) r2
        L_0x051c:
            int r2 = r15 + 1
            int r15 = r33 + r15
            int r4 = r1 + 1
            byte r4 = (byte) r4
            r44[r15] = r4
            r15 = r2
        L_0x0526:
            if (r0 > r3) goto L_0x0578
            int r1 = r16 >> 12
            r1 = r1 & 63
            byte r1 = (byte) r1
            r2 = r1 & 32
            if (r2 != 0) goto L_0x0534
            r2 = r1 | 64
            byte r1 = (byte) r2
        L_0x0534:
            int r2 = r15 + 1
            int r15 = r33 + r15
            int r3 = r1 + 1
            byte r3 = (byte) r3
            r44[r15] = r3
            r15 = r2
            goto L_0x0578
        L_0x053f:
            r7 = r39
            goto L_0x0545
        L_0x0542:
            r7 = r39
            r3 = 6
        L_0x0545:
            if (r18 != 0) goto L_0x0578
            int r1 = r1 << r0
            r1 = r16 | r1
            int r4 = r15 + 3
            int r5 = r0 / 8
            int r4 = r4 - r5
            if (r4 <= r7) goto L_0x0552
            return r26
        L_0x0552:
            int r4 = r15 + 1
            int r15 = r33 + r15
            int r5 = r1 >> 16
            byte r5 = (byte) r5
            r44[r15] = r5
            if (r0 > r2) goto L_0x0568
            int r2 = r4 + 1
            int r4 = r33 + r4
            int r5 = r1 >> 8
            byte r5 = (byte) r5
            r44[r4] = r5
            r15 = r2
            goto L_0x0569
        L_0x0568:
            r15 = r4
        L_0x0569:
            if (r0 > r3) goto L_0x0576
            int r2 = r15 + 1
            int r15 = r33 + r15
            byte r3 = (byte) r1
            r44[r15] = r3
            r16 = r1
            r15 = r2
            goto L_0x0578
        L_0x0576:
            r16 = r1
        L_0x0578:
            int r1 = r15 + r33
            int r1 = r1 - r49
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.barcodes.BarcodeDataMatrix.EdifactEncodation(byte[], int, int, byte[], int, int, int, int, int, boolean):int");
    }

    private int C40OrTextEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, boolean c40, int symbolIndex, int prevEnc, int origDataOffset) {
        String basic;
        String shift3;
        int dataLength2;
        boolean addLatch;
        boolean usingASCII;
        int textOffset2;
        int latestModeEntry;
        int textLength2;
        int i;
        int encPtr;
        int ptrIn;
        int ptrOut;
        boolean usingASCII2;
        int textLength3;
        boolean i2;
        boolean addLatch2;
        int j;
        int dataOffset2 = dataOffset;
        int i3 = symbolIndex;
        int i4 = prevEnc;
        if (textLength == 0) {
            return 0;
        }
        int ptrOut2 = 0;
        if (c40) {
            basic = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            shift3 = "`abcdefghijklmnopqrstuvwxyz{|}~";
        } else {
            basic = " 0123456789abcdefghijklmnopqrstuvwxyz";
            shift3 = "`ABCDEFGHIJKLMNOPQRSTUVWXYZ{|}~";
        }
        boolean addLatch3 = true;
        int mode = c40 ? 2 : 3;
        if (i4 == mode) {
            boolean usingASCII3 = true;
            int latestModeEntry2 = i3 - 1;
            while (true) {
                if (latestModeEntry2 <= 0) {
                    usingASCII2 = usingASCII3;
                    break;
                }
                usingASCII2 = usingASCII3;
                if (this.switchMode[mode - 1][latestModeEntry2] != mode) {
                    break;
                }
                latestModeEntry2--;
                usingASCII3 = usingASCII2;
            }
            int unlatch = -1;
            if (i3 - latestModeEntry2 >= 5) {
                int dataAmountOfEncodedWithASCII = 0;
                for (int i5 = i3 - latestModeEntry2; i5 > 0; i5--) {
                    if ((text[textOffset - i5] & 255) > 127) {
                        dataAmountOfEncodedWithASCII += 2;
                    } else {
                        dataAmountOfEncodedWithASCII++;
                    }
                }
                int i6 = 1;
                while (true) {
                    if (i6 > dataAmountOfEncodedWithASCII || i6 > dataOffset2) {
                        break;
                    } else if (data[dataOffset2 - i6] == -2) {
                        unlatch = dataOffset2 - i6;
                        break;
                    } else {
                        i6++;
                    }
                }
                int amountOfEncodedWithASCII = 0;
                if (unlatch >= 0) {
                    int i7 = unlatch + 1;
                    while (i7 < dataOffset2) {
                        boolean addLatch4 = addLatch3;
                        if (data[i7] == -21) {
                            i7++;
                        }
                        if (data[i7] >= -127 && data[i7] <= -27) {
                            amountOfEncodedWithASCII++;
                        }
                        amountOfEncodedWithASCII++;
                        i7++;
                        addLatch3 = addLatch4;
                    }
                    addLatch2 = addLatch3;
                } else {
                    addLatch2 = true;
                    amountOfEncodedWithASCII = i3 - latestModeEntry2;
                }
                int dataOffsetNew = 0;
                int i8 = amountOfEncodedWithASCII;
                while (true) {
                    if (i8 <= 0) {
                        i = -1;
                        textLength3 = textLength;
                        dataLength2 = dataLength;
                        i2 = usingASCII2;
                        addLatch3 = addLatch2;
                        textOffset2 = textOffset;
                        break;
                    }
                    int requiredCapacityForASCII = 0;
                    int requiredCapacityForC40orText = 0;
                    int j2 = i8;
                    while (j2 >= 0) {
                        int c = text[textOffset - j2] & UByte.MAX_VALUE;
                        int dataAmountOfEncodedWithASCII2 = dataAmountOfEncodedWithASCII;
                        if (c > 127) {
                            c -= 128;
                            requiredCapacityForC40orText += 2;
                        }
                        requiredCapacityForC40orText += basic.indexOf((char) c) >= 0 ? 1 : 2;
                        if (c > 127) {
                            requiredCapacityForASCII += 2;
                            j = j2;
                        } else {
                            if (j2 > 0 && isDigit(c) && isDigit(text[(textOffset - j2) + 1] & UByte.MAX_VALUE)) {
                                requiredCapacityForC40orText += basic.indexOf((char) text[(textOffset - j2) + 1]) >= 0 ? 1 : 2;
                                j2--;
                                dataOffsetNew = requiredCapacityForASCII + 1;
                            }
                            requiredCapacityForASCII++;
                            j = j2;
                        }
                        int j3 = dataOffsetNew;
                        if (j == 1) {
                            dataOffsetNew = requiredCapacityForASCII;
                        } else {
                            dataOffsetNew = j3;
                        }
                        j2 = j - 1;
                        dataAmountOfEncodedWithASCII = dataAmountOfEncodedWithASCII2;
                    }
                    int dataAmountOfEncodedWithASCII3 = dataAmountOfEncodedWithASCII;
                    addLatch2 = unlatch < 0 || dataOffset2 - requiredCapacityForASCII != unlatch;
                    if (requiredCapacityForC40orText % 3 == 0) {
                        if (((requiredCapacityForC40orText / 3) * 2) + (addLatch2 ? 2 : 0) < requiredCapacityForASCII) {
                            textLength3 = i8 + 1;
                            textOffset2 = textOffset - i8;
                            dataOffset2 -= addLatch2 ? dataOffsetNew : dataOffsetNew + 1;
                            dataLength2 = dataLength + (addLatch2 ? dataOffsetNew : dataOffsetNew + 1);
                            i2 = false;
                            addLatch3 = addLatch2;
                            i = -1;
                        }
                    }
                    if (isDigit(text[textOffset - i8] & UByte.MAX_VALUE) && isDigit(text[(textOffset - i8) + 1] & UByte.MAX_VALUE)) {
                        i8--;
                    }
                    i8--;
                    dataAmountOfEncodedWithASCII = dataAmountOfEncodedWithASCII3;
                }
            } else {
                i = -1;
                textLength3 = textLength;
                dataLength2 = dataLength;
                i2 = usingASCII2;
                textOffset2 = textOffset;
            }
            latestModeEntry = dataOffset2;
            usingASCII = i2;
            addLatch = addLatch3;
            textLength2 = textLength3;
        } else {
            addLatch = true;
            i = -1;
            if (i3 != -1) {
                textOffset2 = textOffset;
                textLength2 = textLength;
                dataLength2 = dataLength;
                latestModeEntry = dataOffset2;
                usingASCII = true;
            } else {
                textOffset2 = textOffset;
                textLength2 = textLength;
                dataLength2 = dataLength;
                latestModeEntry = dataOffset2;
                usingASCII = false;
            }
        }
        if (latestModeEntry < 0) {
            return i;
        }
        if (usingASCII) {
            int textLength4 = i4 == mode ? 1 : -1;
            int i9 = textLength2;
            int i10 = mode;
            String str = shift3;
            return asciiEncodation(text, textOffset2, 1, data, latestModeEntry, dataLength2, textLength4, 1, origDataOffset);
        }
        int textLength5 = textLength2;
        int i11 = mode;
        String shift32 = shift3;
        if (addLatch) {
            int ptrOut3 = 0 + 1;
            data[latestModeEntry + 0] = c40 ? LATCH_C40 : LATCH_TEXT;
            ptrOut2 = ptrOut3;
        }
        int textLength6 = textLength5;
        int[] enc = new int[((textLength6 * 4) + 10)];
        int encPtr2 = 0;
        int last1 = 0;
        int last0 = 0;
        int c2 = 0;
        while (c2 < textLength6) {
            if (encPtr2 % 3 == 0) {
                last0 = c2;
                last1 = encPtr2;
            }
            int last02 = c2 + 1;
            int c3 = text[textOffset2 + c2] & UByte.MAX_VALUE;
            if (c3 > 127) {
                c3 -= 128;
                int encPtr3 = encPtr2 + 1;
                enc[encPtr2] = 1;
                encPtr2 = encPtr3 + 1;
                enc[encPtr3] = 30;
            }
            int idx = basic.indexOf((char) c3);
            if (idx >= 0) {
                enc[encPtr2] = idx + 3;
                encPtr2++;
            } else if (c3 < 32) {
                int encPtr4 = encPtr2 + 1;
                enc[encPtr2] = 0;
                encPtr2 = encPtr4 + 1;
                enc[encPtr4] = c3;
            } else {
                int indexOf = "!\"#$%&'()*+,-./:;<=>?@[\\]^_".indexOf((char) c3);
                int idx2 = indexOf;
                if (indexOf >= 0) {
                    int encPtr5 = encPtr2 + 1;
                    enc[encPtr2] = 1;
                    encPtr2 = encPtr5 + 1;
                    enc[encPtr5] = idx2;
                } else {
                    int indexOf2 = shift32.indexOf((char) c3);
                    int idx3 = indexOf2;
                    if (indexOf2 >= 0) {
                        int encPtr6 = encPtr2 + 1;
                        enc[encPtr2] = 2;
                        encPtr2 = encPtr6 + 1;
                        enc[encPtr6] = idx3;
                    }
                }
            }
            c2 = last02;
        }
        if (encPtr2 % 3 != 0) {
            encPtr = last1;
            ptrIn = last0;
        } else {
            encPtr = encPtr2;
            ptrIn = c2;
        }
        if ((encPtr / 3) * 2 > dataLength2 - 2) {
            return -1;
        }
        int i12 = ptrOut2;
        int ptrOut4 = i12;
        for (int i13 = 0; i13 < encPtr; i13 += 3) {
            int a = (enc[i13] * 1600) + (enc[i13 + 1] * 40) + enc[i13 + 2] + 1;
            int ptrOut5 = ptrOut4 + 1;
            data[latestModeEntry + ptrOut4] = (byte) (a / 256);
            ptrOut4 = ptrOut5 + 1;
            data[latestModeEntry + ptrOut5] = (byte) a;
        }
        if (dataLength2 - ptrOut4 > 2) {
            data[latestModeEntry + ptrOut4] = UNLATCH;
            ptrOut = ptrOut4 + 1;
        } else {
            ptrOut = ptrOut4;
        }
        if (i3 >= 0 || textLength6 <= ptrIn) {
            int i14 = encPtr;
            int[] iArr = enc;
            int i15 = textLength6;
            return (ptrOut + latestModeEntry) - origDataOffset;
        }
        int i16 = ptrIn;
        int i17 = encPtr;
        int[] iArr2 = enc;
        int i18 = textLength6;
        return asciiEncodation(text, textOffset2 + ptrIn, textLength6 - ptrIn, data, latestModeEntry + ptrOut, dataLength2 - ptrOut, -1, -1, origDataOffset);
    }

    private void setBit(int x, int y, int xByte) {
        byte[] bArr = this.image;
        int i = (y * xByte) + (x / 8);
        bArr[i] = (byte) (bArr[i] | ((byte) (128 >> (x & 7))));
    }

    private void draw(byte[] data, int dataSize, DmParams dm) {
        int xByte = ((dm.width + (this.f1173ws * 2)) + 7) / 8;
        Arrays.fill(this.image, (byte) 0);
        int i = this.f1173ws;
        while (i < dm.height + this.f1173ws) {
            for (int j = this.f1173ws; j < dm.width + this.f1173ws; j += 2) {
                setBit(j, i, xByte);
            }
            i += dm.heightSection;
        }
        int i2 = dm.heightSection - 1;
        int i3 = this.f1173ws;
        while (true) {
            i2 += i3;
            if (i2 >= dm.height + this.f1173ws) {
                break;
            }
            for (int j2 = this.f1173ws; j2 < dm.width + this.f1173ws; j2++) {
                setBit(j2, i2, xByte);
            }
            i3 = dm.heightSection;
        }
        int i4 = this.f1173ws;
        while (i4 < dm.width + this.f1173ws) {
            for (int j3 = this.f1173ws; j3 < dm.height + this.f1173ws; j3++) {
                setBit(i4, j3, xByte);
            }
            i4 += dm.widthSection;
        }
        int i5 = (dm.widthSection - 1) + this.f1173ws;
        while (true) {
            int i6 = dm.width;
            int i7 = this.f1173ws;
            if (i5 >= i6 + i7) {
                break;
            }
            for (int j4 = i7 + 1; j4 < dm.height + this.f1173ws; j4 += 2) {
                setBit(i5, j4, xByte);
            }
            i5 += dm.widthSection;
        }
        int z = 0;
        int ys = 0;
        while (ys < dm.height) {
            for (int y = 1; y < dm.heightSection - 1; y++) {
                int xs = 0;
                while (xs < dm.width) {
                    int x = 1;
                    while (x < dm.widthSection - 1) {
                        int p = z + 1;
                        short z2 = this.place[z];
                        if (z2 == 1 || (z2 > 1 && (data[(z2 / 8) - 1] & UByte.MAX_VALUE & (128 >> (z2 % 8))) != 0)) {
                            int i8 = this.f1173ws;
                            setBit(x + xs + i8, y + ys + i8, xByte);
                        }
                        x++;
                        z = p;
                    }
                    xs += dm.widthSection;
                }
            }
            ys += dm.heightSection;
        }
    }

    private static int minValueInColumn(int[][] array, int column) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 6; i++) {
            if (array[i][column] < min && array[i][column] >= 0) {
                min = array[i][column];
            }
        }
        if (min != Integer.MAX_VALUE) {
            return min;
        }
        return -1;
    }

    private static int valuePositionInColumn(int[][] array, int column, int value) {
        for (int i = 0; i < 6; i++) {
            if (array[i][column] == value) {
                return i;
            }
        }
        return -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void solveFAndSwitchMode(int[] r7, int r8, int r9) {
        /*
            r6 = this;
            r0 = r7[r8]
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 < 0) goto L_0x001e
            int[][] r0 = r6.f1172f
            r0 = r0[r8]
            int r2 = r9 + -1
            r2 = r0[r2]
            if (r2 < 0) goto L_0x001e
            r2 = r7[r8]
            r0[r9] = r2
            int[][] r0 = r6.switchMode
            r0 = r0[r8]
            int r2 = r8 + 1
            r0[r9] = r2
            goto L_0x0024
        L_0x001e:
            int[][] r0 = r6.f1172f
            r0 = r0[r8]
            r0[r9] = r1
        L_0x0024:
            r0 = 0
        L_0x0025:
            r2 = 6
            if (r0 >= r2) goto L_0x004d
            r2 = r7[r0]
            int[][] r3 = r6.f1172f
            r4 = r3[r8]
            r5 = r4[r9]
            if (r2 >= r5) goto L_0x004a
            r2 = r7[r0]
            if (r2 < 0) goto L_0x004a
            r2 = r3[r0]
            int r3 = r9 + -1
            r2 = r2[r3]
            if (r2 < 0) goto L_0x004a
            r2 = r7[r0]
            r4[r9] = r2
            int[][] r2 = r6.switchMode
            r2 = r2[r8]
            int r3 = r0 + 1
            r2[r9] = r3
        L_0x004a:
            int r0 = r0 + 1
            goto L_0x0025
        L_0x004d:
            int[][] r0 = r6.f1172f
            r0 = r0[r8]
            r2 = r0[r9]
            if (r2 != r1) goto L_0x0058
            r1 = -1
            r0[r9] = r1
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.barcodes.BarcodeDataMatrix.solveFAndSwitchMode(int[], int, int):void");
    }

    private int getEncodation(byte[] text, int textOffset, int textSize, byte[] data, int dataOffset, int dataSize, int options2, boolean sizeFixed) {
        int[] tempForMin;
        int prevEnc;
        int[] tempForMin2;
        int i;
        int currEnc;
        int currEnc2;
        int i2;
        int[] tempForMin3;
        int currEnc3;
        int[] tempForMin4;
        int currEnc4;
        int currEnc5 = textOffset;
        int i3 = textSize;
        int[] tempForMin5 = data;
        int i4 = dataOffset;
        int i5 = dataSize;
        if (i5 < 0) {
            return -1;
        }
        int options3 = options2 & 7;
        if (options3 != 0) {
            int i6 = i3;
            int i7 = i5;
            switch (options3) {
                case 1:
                    return asciiEncodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
                case 2:
                    return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, true, -1, -1, dataOffset);
                case 3:
                    return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, false, -1, -1, dataOffset);
                case 4:
                    return b256Encodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
                case 5:
                    return X12Encodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
                case 6:
                    byte[] bArr = text;
                    int i8 = textOffset;
                    return EdifactEncodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset, sizeFixed);
                case 7:
                    if (i6 > i7) {
                        return -1;
                    }
                    System.arraycopy(text, textOffset, tempForMin5, i4, i6);
                    return i6;
                default:
                    return -1;
            }
        } else if (i3 == 0) {
            return 0;
        } else {
            int[] iArr = new int[2];
            iArr[1] = tempForMin5.length;
            iArr[0] = 6;
            byte[][] dataDynamic = (byte[][]) Array.newInstance(Byte.TYPE, iArr);
            for (int i9 = 0; i9 < 6; i9++) {
                System.arraycopy(tempForMin5, 0, dataDynamic[i9], 0, tempForMin5.length);
                this.switchMode[i9][0] = i9 + 1;
            }
            byte[] bArr2 = text;
            int i10 = textOffset;
            int i11 = dataOffset;
            int i12 = dataSize;
            int i13 = 0;
            this.f1172f[0][0] = asciiEncodation(bArr2, i10, 1, dataDynamic[0], i11, i12, 0, -1, dataOffset);
            this.f1172f[1][0] = C40OrTextEncodation(bArr2, i10, 1, dataDynamic[1], i11, i12, true, 0, -1, dataOffset);
            this.f1172f[2][0] = C40OrTextEncodation(bArr2, i10, 1, dataDynamic[2], i11, i12, false, 0, -1, dataOffset);
            this.f1172f[3][0] = b256Encodation(bArr2, i10, 1, dataDynamic[3], i11, i12, 0, -1, dataOffset);
            this.f1172f[4][0] = X12Encodation(bArr2, i10, 1, dataDynamic[4], i11, i12, 0, -1, dataOffset);
            this.f1172f[5][0] = EdifactEncodation(bArr2, i10, 1, dataDynamic[5], i11, i12, 0, -1, dataOffset, sizeFixed);
            int i14 = 1;
            while (i14 < i3) {
                int i15 = 6;
                int[] tempForMin6 = new int[6];
                int currEnc6 = 0;
                while (currEnc6 < i15) {
                    int[] iArr2 = new int[2];
                    iArr2[1] = tempForMin5.length;
                    iArr2[i13] = i15;
                    byte[][] dataDynamicInner = (byte[][]) Array.newInstance(Byte.TYPE, iArr2);
                    int prevEnc2 = 0;
                    while (prevEnc2 < i15) {
                        System.arraycopy(dataDynamic[prevEnc2], i13, dataDynamicInner[prevEnc2], i13, tempForMin5.length);
                        int[] iArr3 = this.f1172f[prevEnc2];
                        if (iArr3[i14 - 1] < 0) {
                            tempForMin6[prevEnc2] = -1;
                            int i16 = dataOffset;
                            prevEnc = prevEnc2;
                            currEnc = currEnc6;
                            tempForMin2 = tempForMin6;
                            i = i14;
                        } else {
                            if (currEnc6 == 0) {
                                i2 = dataOffset;
                                prevEnc = prevEnc2;
                                currEnc2 = currEnc6;
                                tempForMin3 = tempForMin6;
                                tempForMin3[prevEnc] = asciiEncodation(text, currEnc5 + i14, 1, dataDynamicInner[prevEnc2], iArr3[i14 - 1] + i2, dataSize - iArr3[i14 - 1], i14, prevEnc2 + 1, dataOffset);
                            } else {
                                i2 = dataOffset;
                                prevEnc = prevEnc2;
                                currEnc2 = currEnc6;
                                tempForMin3 = tempForMin6;
                            }
                            int currEnc7 = currEnc2;
                            if (currEnc7 == 1) {
                                byte[] bArr3 = dataDynamicInner[prevEnc];
                                int[] iArr4 = this.f1172f[prevEnc];
                                currEnc3 = currEnc7;
                                i = i14;
                                int C40OrTextEncodation = C40OrTextEncodation(text, currEnc5 + i14, 1, bArr3, iArr4[i14 - 1] + i2, dataSize - iArr4[i14 - 1], true, i14, prevEnc + 1, dataOffset);
                                tempForMin4 = tempForMin3;
                                tempForMin4[prevEnc] = C40OrTextEncodation;
                            } else {
                                currEnc3 = currEnc7;
                                i = i14;
                                tempForMin4 = tempForMin3;
                            }
                            int currEnc8 = currEnc3;
                            if (currEnc8 == 2) {
                                byte[] bArr4 = dataDynamicInner[prevEnc];
                                int[] iArr5 = this.f1172f[prevEnc];
                                currEnc4 = currEnc8;
                                tempForMin2 = tempForMin4;
                                tempForMin2[prevEnc] = C40OrTextEncodation(text, currEnc5 + i, 1, bArr4, iArr5[i - 1] + i2, dataSize - iArr5[i - 1], false, i, prevEnc + 1, dataOffset);
                            } else {
                                currEnc4 = currEnc8;
                                tempForMin2 = tempForMin4;
                            }
                            int currEnc9 = currEnc4;
                            if (currEnc9 == 3) {
                                byte[] bArr5 = dataDynamicInner[prevEnc];
                                int[] iArr6 = this.f1172f[prevEnc];
                                tempForMin2[prevEnc] = b256Encodation(text, currEnc5 + i, 1, bArr5, iArr6[i - 1] + i2, dataSize - iArr6[i - 1], i, prevEnc + 1, dataOffset);
                            }
                            if (currEnc9 == 4) {
                                byte[] bArr6 = dataDynamicInner[prevEnc];
                                int[] iArr7 = this.f1172f[prevEnc];
                                tempForMin2[prevEnc] = X12Encodation(text, currEnc5 + i, 1, bArr6, iArr7[i - 1] + i2, dataSize - iArr7[i - 1], i, prevEnc + 1, dataOffset);
                            }
                            if (currEnc9 == 5) {
                                byte[] bArr7 = dataDynamicInner[prevEnc];
                                int[] iArr8 = this.f1172f[prevEnc];
                                currEnc = currEnc9;
                                tempForMin2[prevEnc] = EdifactEncodation(text, currEnc5 + i, 1, bArr7, iArr8[i - 1] + i2, dataSize - iArr8[i - 1], i, prevEnc + 1, dataOffset, sizeFixed);
                            } else {
                                currEnc = currEnc9;
                            }
                        }
                        prevEnc2 = prevEnc + 1;
                        currEnc6 = currEnc;
                        i14 = i;
                        tempForMin6 = tempForMin2;
                        i15 = 6;
                        i13 = 0;
                        currEnc5 = textOffset;
                        int i17 = textSize;
                        tempForMin5 = data;
                    }
                    int i18 = dataOffset;
                    int i19 = prevEnc2;
                    int currEnc10 = currEnc6;
                    int[] tempForMin7 = tempForMin6;
                    int i20 = i14;
                    solveFAndSwitchMode(tempForMin7, currEnc10, i20);
                    int i21 = this.switchMode[currEnc10][i20];
                    if (i21 != 0) {
                        tempForMin = tempForMin7;
                        tempForMin5 = data;
                        System.arraycopy(dataDynamicInner[i21 - 1], 0, dataDynamic[currEnc10], 0, tempForMin5.length);
                    } else {
                        tempForMin = tempForMin7;
                        tempForMin5 = data;
                    }
                    currEnc6 = currEnc10 + 1;
                    currEnc5 = textOffset;
                    tempForMin6 = tempForMin;
                    i14 = i20;
                    i15 = 6;
                    i13 = 0;
                    int i22 = textSize;
                }
                int i23 = dataOffset;
                int i24 = currEnc6;
                int[] iArr9 = tempForMin6;
                i14++;
                currEnc5 = textOffset;
                i3 = textSize;
                i13 = 0;
            }
            int i25 = dataOffset;
            int i26 = i14;
            int i27 = textSize;
            int e = minValueInColumn(this.f1172f, i27 - 1);
            if (e > dataSize || e < 0) {
                return -1;
            }
            System.arraycopy(dataDynamic[valuePositionInColumn(this.f1172f, i27 - 1, e)], 0, tempForMin5, 0, tempForMin5.length);
            return e;
        }
    }

    private static int getNumber(byte[] text, int c, int n) {
        int v = 0;
        int j = 0;
        while (j < n) {
            int ptrIn = c + 1;
            int c2 = text[c] & 255;
            if (c2 < 48 || c2 > 57) {
                return -1;
            }
            v = ((v * 10) + c2) - 48;
            j++;
            c = ptrIn;
        }
        return v;
    }

    private int processExtensions(byte[] text, int textOffset, int textSize, byte[] data) {
        int eci;
        int fn;
        if ((this.options & 32) == 0) {
            return 0;
        }
        int order = 0;
        int c = 0;
        int ptrOut = 0;
        while (c < textSize && order <= 20) {
            int ptrIn = c + 1;
            order++;
            switch (text[c + textOffset] & 255) {
                case 46:
                    this.extOut = ptrIn;
                    return ptrOut;
                case 101:
                    if (ptrIn + 6 <= textSize && (eci = getNumber(text, textOffset + ptrIn, 6)) >= 0) {
                        ptrIn += 6;
                        int ptrOut2 = ptrOut + 1;
                        data[ptrOut] = -15;
                        if (eci >= 127) {
                            if (eci >= 16383) {
                                int ptrOut3 = ptrOut2 + 1;
                                data[ptrOut2] = (byte) (((eci - 16383) / 64516) + 192);
                                int ptrOut4 = ptrOut3 + 1;
                                data[ptrOut3] = (byte) ((((eci - 16383) / TIFFConstants.TIFFTAG_SUBFILETYPE) % TIFFConstants.TIFFTAG_SUBFILETYPE) + 1);
                                ptrOut = ptrOut4 + 1;
                                data[ptrOut4] = (byte) (((eci - 16383) % TIFFConstants.TIFFTAG_SUBFILETYPE) + 1);
                                break;
                            } else {
                                int ptrOut5 = ptrOut2 + 1;
                                data[ptrOut2] = (byte) (((eci - 127) / TIFFConstants.TIFFTAG_SUBFILETYPE) + 128);
                                data[ptrOut5] = (byte) (((eci - 127) % TIFFConstants.TIFFTAG_SUBFILETYPE) + 1);
                                ptrOut = ptrOut5 + 1;
                                break;
                            }
                        } else {
                            ptrOut = ptrOut2 + 1;
                            data[ptrOut2] = (byte) (eci + 1);
                            break;
                        }
                    } else {
                        return -1;
                    }
                    break;
                case 102:
                    if (order == 1 || (order == 2 && (text[textOffset] == 115 || text[textOffset] == 109))) {
                        data[ptrOut] = -24;
                        ptrOut++;
                        break;
                    } else {
                        return -1;
                    }
                case 109:
                    if (order == 1 && ptrIn + 1 <= textSize) {
                        int ptrIn2 = ptrIn + 1;
                        if ((text[ptrIn + textOffset] & 255) == 53) {
                            int ptrOut6 = ptrOut + 1;
                            data[ptrOut] = -22;
                            ptrOut = ptrOut6 + 1;
                            data[ptrOut6] = -20;
                            ptrIn = ptrIn2;
                            break;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                case 112:
                    if (order == 1) {
                        data[ptrOut] = -22;
                        ptrOut++;
                        break;
                    } else {
                        return -1;
                    }
                case 115:
                    if (order == 1 && ptrIn + 9 <= textSize && (fn = getNumber(text, textOffset + ptrIn, 2)) > 0 && fn <= 16) {
                        int ptrIn3 = ptrIn + 2;
                        int ft = getNumber(text, textOffset + ptrIn3, 2);
                        if (ft > 1 && ft <= 16) {
                            int ptrIn4 = ptrIn3 + 2;
                            int fi = getNumber(text, textOffset + ptrIn4, 5);
                            if (fi >= 0 && fn < 64516) {
                                ptrIn = ptrIn4 + 5;
                                int ptrOut7 = ptrOut + 1;
                                data[ptrOut] = -23;
                                int ptrOut8 = ptrOut7 + 1;
                                data[ptrOut7] = (byte) (((fn - 1) << 4) | (17 - ft));
                                int ptrOut9 = ptrOut8 + 1;
                                data[ptrOut8] = (byte) ((fi / TIFFConstants.TIFFTAG_SUBFILETYPE) + 1);
                                ptrOut = ptrOut9 + 1;
                                data[ptrOut9] = (byte) ((fi % TIFFConstants.TIFFTAG_SUBFILETYPE) + 1);
                                break;
                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                    break;
            }
            c = ptrIn;
        }
        return -1;
    }
}

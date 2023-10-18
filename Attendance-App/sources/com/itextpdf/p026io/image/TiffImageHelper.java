package com.itextpdf.p026io.image;

import com.itextpdf.p026io.codec.TIFFDirectory;
import com.itextpdf.p026io.codec.TIFFField;
import com.itextpdf.p026io.source.DeflaterOutputStream;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import java.io.IOException;
import java.util.Map;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.itextpdf.io.image.TiffImageHelper */
class TiffImageHelper {
    TiffImageHelper() {
    }

    /* renamed from: com.itextpdf.io.image.TiffImageHelper$TiffParameters */
    private static class TiffParameters {
        Map<String, Object> additional;
        TiffImageData image;
        boolean jpegProcessing;

        TiffParameters(TiffImageData image2) {
            this.image = image2;
        }
    }

    public static void processImage(ImageData image) {
        if (image.getOriginalType() == ImageType.TIFF) {
            try {
                if (image.getData() == null) {
                    image.loadData();
                }
                RandomAccessFileOrArray raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(image.getData()));
                TiffParameters tiff = new TiffParameters((TiffImageData) image);
                processTiffImage(raf, tiff);
                raf.close();
                if (!tiff.jpegProcessing) {
                    RawImageHelper.updateImageAttributes(tiff.image, tiff.additional);
                }
            } catch (IOException e) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TiffImageException, (Throwable) e);
            }
        } else {
            throw new IllegalArgumentException("TIFF image expected");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0250, code lost:
        r17 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02c6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x02c7, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02cb, code lost:
        if (r3 == false) goto L_0x02e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x02e6, code lost:
        r36 = r8;
        r37 = r12;
        r8 = r53;
        r12 = r54;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02ee, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0330, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0331, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0335, code lost:
        if (r3 != false) goto L_0x0337;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0337, code lost:
        r17 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x033a, code lost:
        if (r13 != 1) goto L_0x033c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x033c, code lost:
        r18 = r7;
        r53 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:?, code lost:
        r7 = new byte[((int) r11[0])];
        r8 = r11;
        r1.seek(r14[0]);
        r1.readFully(r7);
        com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r2.image, r6, r51, false, r15, r52, r7, (int[]) null);
        r2.image.setInverted(true);
        r2.image.setDpi(r47, r28);
        r16 = r7;
        r7 = r31;
        r2.image.setXYRatio(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x037e, code lost:
        if (r50 != 0.0f) goto L_0x0380;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x0380, code lost:
        r31 = r7;
        r2.image.setRotation(r50);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x038a, code lost:
        r31 = r7;
        r7 = r50;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x038f, code lost:
        r18 = r7;
        r53 = r8;
        r8 = r11;
        r3 = r28;
        r11 = r47;
        r7 = r50;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x039a, code lost:
        throw r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x039b, code lost:
        r17 = r3;
        r18 = r7;
        r53 = r8;
        r8 = r11;
        r3 = r28;
        r11 = r47;
        r7 = r50;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x03a8, code lost:
        throw r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:141:0x020e, B:156:0x02c2] */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:15:0x004f, B:171:0x031a] */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x016c  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0181 A[SYNTHETIC, Splitter:B:113:0x0181] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0191 A[Catch:{ Exception -> 0x0150 }] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0199 A[Catch:{ Exception -> 0x0150 }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01bc A[Catch:{ Exception -> 0x0150 }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x01f8  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0206 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x026e A[Catch:{ Exception -> 0x0441 }] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x041c A[SYNTHETIC, Splitter:B:197:0x041c] */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x043a A[SYNTHETIC, Splitter:B:204:0x043a] */
    /* JADX WARNING: Removed duplicated region for block: B:229:? A[Catch:{  }, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ca A[Catch:{ Exception -> 0x045d }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d8 A[Catch:{ Exception -> 0x045d }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00f3 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x010b A[SYNTHETIC, Splitter:B:73:0x010b] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0135 A[Catch:{ Exception -> 0x0150 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0139 A[Catch:{ Exception -> 0x0150 }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0157  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0165  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processTiffImage(com.itextpdf.p026io.source.RandomAccessFileOrArray r55, com.itextpdf.p026io.image.TiffImageHelper.TiffParameters r56) {
        /*
            r1 = r55
            r2 = r56
            com.itextpdf.io.image.TiffImageData r0 = r2.image
            boolean r3 = r0.isRecoverFromImageError()
            com.itextpdf.io.image.TiffImageData r0 = r2.image
            int r4 = r0.getPage()
            com.itextpdf.io.image.TiffImageData r0 = r2.image
            boolean r5 = r0.isDirect()
            r6 = 1
            if (r4 < r6) goto L_0x046c
            com.itextpdf.io.codec.TIFFDirectory r0 = new com.itextpdf.io.codec.TIFFDirectory     // Catch:{ Exception -> 0x045d }
            int r7 = r4 + -1
            r0.<init>(r1, r7)     // Catch:{ Exception -> 0x045d }
            r7 = r0
            r0 = 322(0x142, float:4.51E-43)
            boolean r0 = r7.isTagPresent(r0)     // Catch:{ Exception -> 0x045d }
            if (r0 != 0) goto L_0x044c
            r0 = 1
            r8 = 259(0x103, float:3.63E-43)
            boolean r9 = r7.isTagPresent(r8)     // Catch:{ Exception -> 0x045d }
            if (r9 == 0) goto L_0x0042
            long r8 = r7.getFieldAsLong(r8)     // Catch:{ Exception -> 0x0039 }
            int r0 = (int) r8
            r8 = r0
            goto L_0x0043
        L_0x0039:
            r0 = move-exception
            r17 = r3
            r29 = r4
            r46 = r5
            goto L_0x0464
        L_0x0042:
            r8 = r0
        L_0x0043:
            switch(r8) {
                case 2: goto L_0x0054;
                case 3: goto L_0x0054;
                case 4: goto L_0x0054;
                case 32771: goto L_0x0054;
                default: goto L_0x0046;
            }
        L_0x0046:
            r17 = r3
            r29 = r4
            r46 = r5
            r4 = r7
            r36 = r8
            processTiffImageColor(r4, r1, r2)     // Catch:{ Exception -> 0x045b }
            goto L_0x044b
        L_0x0054:
            r0 = 0
            r9 = 274(0x112, float:3.84E-43)
            boolean r10 = r7.isTagPresent(r9)     // Catch:{ Exception -> 0x045d }
            r11 = 8
            if (r10 == 0) goto L_0x0087
            long r9 = r7.getFieldAsLong(r9)     // Catch:{ Exception -> 0x0039 }
            int r10 = (int) r9
            r9 = 3
            if (r10 == r9) goto L_0x0082
            r9 = 4
            if (r10 != r9) goto L_0x006c
            goto L_0x0082
        L_0x006c:
            r9 = 5
            if (r10 == r9) goto L_0x007d
            if (r10 != r11) goto L_0x0072
            goto L_0x007d
        L_0x0072:
            r9 = 6
            if (r10 == r9) goto L_0x0078
            r9 = 7
            if (r10 != r9) goto L_0x0087
        L_0x0078:
            r0 = -1077342245(0xffffffffbfc90fdb, float:-1.5707964)
            r9 = r0
            goto L_0x0088
        L_0x007d:
            r0 = 1070141403(0x3fc90fdb, float:1.5707964)
            r9 = r0
            goto L_0x0088
        L_0x0082:
            r0 = 1078530011(0x40490fdb, float:3.1415927)
            r9 = r0
            goto L_0x0088
        L_0x0087:
            r9 = r0
        L_0x0088:
            r12 = 0
            r14 = 0
            r0 = 1
            r10 = 257(0x101, float:3.6E-43)
            r17 = r12
            long r11 = r7.getFieldAsLong(r10)     // Catch:{ Exception -> 0x045d }
            int r10 = (int) r11     // Catch:{ Exception -> 0x045d }
            r11 = 256(0x100, float:3.59E-43)
            long r11 = r7.getFieldAsLong(r11)     // Catch:{ Exception -> 0x045d }
            int r12 = (int) r11     // Catch:{ Exception -> 0x045d }
            r11 = 0
            r13 = 2
            r6 = 296(0x128, float:4.15E-43)
            boolean r19 = r7.isTagPresent(r6)     // Catch:{ Exception -> 0x045d }
            if (r19 == 0) goto L_0x00b0
            r19 = r14
            r15 = r13
            long r13 = r7.getFieldAsLong(r6)     // Catch:{ Exception -> 0x0039 }
            int r13 = (int) r13
            goto L_0x00b3
        L_0x00b0:
            r19 = r14
            r15 = r13
        L_0x00b3:
            r6 = 282(0x11a, float:3.95E-43)
            com.itextpdf.io.codec.TIFFField r6 = r7.getField(r6)     // Catch:{ Exception -> 0x045d }
            int r6 = getDpi(r6, r13)     // Catch:{ Exception -> 0x045d }
            r14 = 283(0x11b, float:3.97E-43)
            com.itextpdf.io.codec.TIFFField r14 = r7.getField(r14)     // Catch:{ Exception -> 0x045d }
            int r14 = getDpi(r14, r13)     // Catch:{ Exception -> 0x045d }
            r15 = 1
            if (r13 != r15) goto L_0x00d8
            if (r14 == 0) goto L_0x00d3
            float r15 = (float) r6     // Catch:{ Exception -> 0x045d }
            r21 = r0
            float r0 = (float) r14     // Catch:{ Exception -> 0x045d }
            float r11 = r15 / r0
            goto L_0x00d5
        L_0x00d3:
            r21 = r0
        L_0x00d5:
            r6 = 0
            r14 = 0
            goto L_0x00da
        L_0x00d8:
            r21 = r0
        L_0x00da:
            r0 = r10
            r15 = 278(0x116, float:3.9E-43)
            boolean r22 = r7.isTagPresent(r15)     // Catch:{ Exception -> 0x045d }
            if (r22 == 0) goto L_0x00ed
            r27 = r13
            r28 = r14
            long r13 = r7.getFieldAsLong(r15)     // Catch:{ Exception -> 0x0039 }
            int r0 = (int) r13
            goto L_0x00f1
        L_0x00ed:
            r27 = r13
            r28 = r14
        L_0x00f1:
            if (r0 <= 0) goto L_0x00f8
            if (r0 <= r10) goto L_0x00f6
            goto L_0x00f8
        L_0x00f6:
            r13 = r0
            goto L_0x00fa
        L_0x00f8:
            r0 = r10
            r13 = r0
        L_0x00fa:
            r0 = 273(0x111, float:3.83E-43)
            long[] r0 = getArrayLongShort(r7, r0)     // Catch:{ Exception -> 0x045d }
            r14 = r0
            r0 = 279(0x117, float:3.91E-43)
            long[] r0 = getArrayLongShort(r7, r0)     // Catch:{ Exception -> 0x045d }
            r22 = 0
            if (r0 == 0) goto L_0x0135
            int r15 = r0.length     // Catch:{ Exception -> 0x012c }
            r29 = r4
            r4 = 1
            if (r15 != r4) goto L_0x0127
            r4 = 0
            r24 = r0[r4]     // Catch:{ Exception -> 0x0150 }
            int r15 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1))
            if (r15 == 0) goto L_0x0137
            r25 = r0[r4]     // Catch:{ Exception -> 0x0150 }
            r30 = r14[r4]     // Catch:{ Exception -> 0x0150 }
            long r25 = r25 + r30
            long r30 = r55.length()     // Catch:{ Exception -> 0x0150 }
            int r4 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1))
            if (r4 <= 0) goto L_0x0127
            goto L_0x0137
        L_0x0127:
            r31 = r11
            r30 = r12
            goto L_0x015b
        L_0x012c:
            r0 = move-exception
            r29 = r4
            r17 = r3
            r46 = r5
            goto L_0x0464
        L_0x0135:
            r29 = r4
        L_0x0137:
            if (r10 != r13) goto L_0x0157
            r4 = 1
            long[] r15 = new long[r4]     // Catch:{ Exception -> 0x0150 }
            long r25 = r55.length()     // Catch:{ Exception -> 0x0150 }
            r31 = r11
            r30 = r12
            r4 = 0
            r11 = r14[r4]     // Catch:{ Exception -> 0x0150 }
            int r12 = (int) r11     // Catch:{ Exception -> 0x0150 }
            long r11 = (long) r12     // Catch:{ Exception -> 0x0150 }
            long r25 = r25 - r11
            r15[r4] = r25     // Catch:{ Exception -> 0x0150 }
            r0 = r15
            r4 = r0
            goto L_0x015c
        L_0x0150:
            r0 = move-exception
            r17 = r3
            r46 = r5
            goto L_0x0464
        L_0x0157:
            r31 = r11
            r30 = r12
        L_0x015b:
            r4 = r0
        L_0x015c:
            r0 = 0
            r11 = 266(0x10a, float:3.73E-43)
            com.itextpdf.io.codec.TIFFField r11 = r7.getField(r11)     // Catch:{ Exception -> 0x0445 }
            if (r11 == 0) goto L_0x016c
            r12 = 0
            int r15 = r11.getAsInt(r12)     // Catch:{ Exception -> 0x0150 }
            r12 = r15
            goto L_0x016e
        L_0x016c:
            r12 = r21
        L_0x016e:
            r15 = 2
            if (r12 != r15) goto L_0x0173
            r15 = 1
            goto L_0x0174
        L_0x0173:
            r15 = 0
        L_0x0174:
            r0 = 0
            r32 = r11
            r11 = 262(0x106, float:3.67E-43)
            boolean r21 = r7.isTagPresent(r11)     // Catch:{ Exception -> 0x0445 }
            r25 = 1
            if (r21 == 0) goto L_0x018b
            long r33 = r7.getFieldAsLong(r11)     // Catch:{ Exception -> 0x0150 }
            int r11 = (r33 > r25 ? 1 : (r33 == r25 ? 0 : -1))
            if (r11 != 0) goto L_0x018b
            r0 = r0 | 1
        L_0x018b:
            r11 = 0
            r33 = 4
            switch(r8) {
                case 2: goto L_0x01f8;
                case 3: goto L_0x01bc;
                case 4: goto L_0x0199;
                case 32771: goto L_0x01f8;
                default: goto L_0x0191;
            }     // Catch:{ Exception -> 0x0150 }
        L_0x0191:
            r35 = r15
            r15 = r11
            r43 = r19
            r11 = r0
            goto L_0x0202
        L_0x0199:
            r11 = 256(0x100, float:3.59E-43)
            r21 = r11
            r11 = 293(0x125, float:4.1E-43)
            com.itextpdf.io.codec.TIFFField r11 = r7.getField(r11)     // Catch:{ Exception -> 0x0150 }
            if (r11 == 0) goto L_0x01b4
            r35 = r15
            r15 = 0
            long r22 = r11.getAsLong(r15)     // Catch:{ Exception -> 0x0150 }
            r19 = r22
            r11 = r0
            r43 = r19
            r15 = r21
            goto L_0x0202
        L_0x01b4:
            r35 = r15
            r11 = r0
            r43 = r19
            r15 = r21
            goto L_0x0202
        L_0x01bc:
            r35 = r15
            r11 = 257(0x101, float:3.6E-43)
            r0 = r0 | 12
            r15 = 292(0x124, float:4.09E-43)
            com.itextpdf.io.codec.TIFFField r15 = r7.getField(r15)     // Catch:{ Exception -> 0x0150 }
            if (r15 == 0) goto L_0x01f0
            r21 = r11
            r11 = 0
            long r36 = r15.getAsLong(r11)     // Catch:{ Exception -> 0x0150 }
            r17 = r36
            long r25 = r17 & r25
            int r11 = (r25 > r22 ? 1 : (r25 == r22 ? 0 : -1))
            if (r11 == 0) goto L_0x01dc
            r11 = 258(0x102, float:3.62E-43)
            goto L_0x01de
        L_0x01dc:
            r11 = r21
        L_0x01de:
            long r25 = r17 & r33
            int r21 = (r25 > r22 ? 1 : (r25 == r22 ? 0 : -1))
            if (r21 == 0) goto L_0x01eb
            r0 = r0 | 2
            r15 = r11
            r43 = r19
            r11 = r0
            goto L_0x0202
        L_0x01eb:
            r15 = r11
            r43 = r19
            r11 = r0
            goto L_0x0202
        L_0x01f0:
            r21 = r11
            r11 = r0
            r43 = r19
            r15 = r21
            goto L_0x0202
        L_0x01f8:
            r35 = r15
            r11 = 257(0x101, float:3.6E-43)
            r0 = r0 | 10
            r15 = r11
            r43 = r19
            r11 = r0
        L_0x0202:
            r45 = 0
            if (r5 == 0) goto L_0x0254
            if (r13 != r10) goto L_0x0254
            r46 = r5
            r47 = r6
            r16 = 0
            r5 = r4[r16]     // Catch:{ Exception -> 0x024f }
            int r0 = (int) r5     // Catch:{ Exception -> 0x024f }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x024f }
            r5 = r14[r16]     // Catch:{ Exception -> 0x024f }
            r1.seek(r5)     // Catch:{ Exception -> 0x024f }
            r1.readFully(r0)     // Catch:{ Exception -> 0x024f }
            com.itextpdf.io.image.TiffImageData r5 = r2.image     // Catch:{ Exception -> 0x024f }
            r22 = 0
            r26 = 0
            r19 = r5
            r20 = r30
            r21 = r10
            r23 = r15
            r24 = r11
            r25 = r0
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r19, r20, r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x024f }
            com.itextpdf.io.image.TiffImageData r5 = r2.image     // Catch:{ Exception -> 0x024f }
            r6 = 1
            r5.setInverted(r6)     // Catch:{ Exception -> 0x024f }
            r36 = r8
            r51 = r10
            r52 = r11
            r37 = r12
            r48 = r17
            r6 = r30
            r11 = r47
            r17 = r3
            r30 = r7
            r7 = r9
            r3 = r28
            r28 = r4
            goto L_0x040c
        L_0x024f:
            r0 = move-exception
            r17 = r3
            goto L_0x0464
        L_0x0254:
            r46 = r5
            r47 = r6
            r0 = r10
            com.itextpdf.io.codec.CCITTG4Encoder r5 = new com.itextpdf.io.codec.CCITTG4Encoder     // Catch:{ Exception -> 0x0441 }
            r6 = r30
            r5.<init>(r6)     // Catch:{ Exception -> 0x0441 }
            r19 = 0
            r30 = r7
            r50 = r9
            r48 = r17
            r9 = r19
            r7 = r0
        L_0x026b:
            int r0 = r14.length     // Catch:{ Exception -> 0x0441 }
            if (r9 >= r0) goto L_0x03e0
            r51 = r10
            r52 = r11
            r10 = r4[r9]     // Catch:{ Exception -> 0x0441 }
            int r0 = (int) r10     // Catch:{ Exception -> 0x0441 }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0441 }
            r10 = r0
            r11 = r4
            r53 = r5
            r4 = r14[r9]     // Catch:{ Exception -> 0x0441 }
            r1.seek(r4)     // Catch:{ Exception -> 0x0441 }
            r1.readFully(r10)     // Catch:{ Exception -> 0x0441 }
            int r0 = java.lang.Math.min(r13, r7)     // Catch:{ Exception -> 0x0441 }
            r4 = r0
            com.itextpdf.io.codec.TIFFFaxDecoder r0 = new com.itextpdf.io.codec.TIFFFaxDecoder     // Catch:{ Exception -> 0x0441 }
            r0.<init>(r12, r6, r4)     // Catch:{ Exception -> 0x0441 }
            r5 = r0
            r5.setRecoverFromImageError(r3)     // Catch:{ Exception -> 0x0441 }
            int r0 = r6 + 7
            r16 = 8
            int r0 = r0 / 8
            int r0 = r0 * r4
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0441 }
            r54 = r0
            switch(r8) {
                case 2: goto L_0x03a9;
                case 3: goto L_0x02ef;
                case 4: goto L_0x02b6;
                case 32771: goto L_0x03a9;
                default: goto L_0x02a0;
            }
        L_0x02a0:
            r17 = r3
            r18 = r7
            r36 = r8
            r37 = r12
            r3 = r28
            r7 = r50
            r8 = r53
            r12 = r54
            r28 = r11
            r11 = r47
            goto L_0x03c6
        L_0x02b6:
            r39 = 0
            r36 = r5
            r37 = r54
            r38 = r10
            r40 = r4
            r41 = r43
            r36.decodeT6(r37, r38, r39, r40, r41)     // Catch:{ IOException -> 0x02c6 }
            goto L_0x02cd
        L_0x02c6:
            r0 = move-exception
            r17 = r0
            r0 = r17
            if (r3 == 0) goto L_0x02e6
        L_0x02cd:
            r36 = r8
            r37 = r12
            r8 = r53
            r12 = r54
            r8.fax4Encode(r12, r4)     // Catch:{ Exception -> 0x024f }
            r17 = r3
            r18 = r7
            r3 = r28
            r7 = r50
            r28 = r11
            r11 = r47
            goto L_0x03c6
        L_0x02e6:
            r36 = r8
            r37 = r12
            r8 = r53
            r12 = r54
            throw r0     // Catch:{ Exception -> 0x024f }
        L_0x02ef:
            r36 = r8
            r37 = r12
            r8 = r53
            r12 = r54
            r20 = 0
            r17 = r5
            r18 = r12
            r19 = r10
            r21 = r4
            r22 = r48
            r17.decode2D(r18, r19, r20, r21, r22)     // Catch:{ RuntimeException -> 0x0307 }
            goto L_0x031f
        L_0x0307:
            r0 = move-exception
            r17 = r0
            r38 = r17
            long r39 = r48 ^ r33
            r20 = 0
            r17 = r5
            r18 = r12
            r19 = r10
            r21 = r4
            r22 = r39
            r17.decode2D(r18, r19, r20, r21, r22)     // Catch:{ RuntimeException -> 0x0330 }
            r48 = r39
        L_0x031f:
            r8.fax4Encode(r12, r4)     // Catch:{ Exception -> 0x024f }
            r17 = r3
            r18 = r7
            r3 = r28
            r7 = r50
            r28 = r11
            r11 = r47
            goto L_0x03c6
        L_0x0330:
            r0 = move-exception
            r16 = r0
            r0 = r16
            if (r3 == 0) goto L_0x039b
            r17 = r3
            r3 = 1
            if (r13 == r3) goto L_0x038f
            r18 = r7
            r53 = r8
            r3 = 0
            r7 = r11[r3]     // Catch:{ Exception -> 0x045b }
            int r8 = (int) r7     // Catch:{ Exception -> 0x045b }
            byte[] r7 = new byte[r8]     // Catch:{ Exception -> 0x045b }
            r8 = r11
            r10 = r14[r3]     // Catch:{ Exception -> 0x045b }
            r1.seek(r10)     // Catch:{ Exception -> 0x045b }
            r1.readFully(r7)     // Catch:{ Exception -> 0x045b }
            com.itextpdf.io.image.TiffImageData r3 = r2.image     // Catch:{ Exception -> 0x045b }
            r22 = 0
            r26 = 0
            r19 = r3
            r20 = r6
            r21 = r51
            r23 = r15
            r24 = r52
            r25 = r7
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r19, r20, r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x045b }
            com.itextpdf.io.image.TiffImageData r3 = r2.image     // Catch:{ Exception -> 0x045b }
            r10 = 1
            r3.setInverted(r10)     // Catch:{ Exception -> 0x045b }
            com.itextpdf.io.image.TiffImageData r3 = r2.image     // Catch:{ Exception -> 0x045b }
            r10 = r28
            r11 = r47
            r3.setDpi(r11, r10)     // Catch:{ Exception -> 0x045b }
            com.itextpdf.io.image.TiffImageData r3 = r2.image     // Catch:{ Exception -> 0x045b }
            r16 = r7
            r7 = r31
            r3.setXYRatio(r7)     // Catch:{ Exception -> 0x045b }
            int r3 = (r50 > r45 ? 1 : (r50 == r45 ? 0 : -1))
            if (r3 == 0) goto L_0x038a
            com.itextpdf.io.image.TiffImageData r3 = r2.image     // Catch:{ Exception -> 0x045b }
            r31 = r7
            r7 = r50
            r3.setRotation(r7)     // Catch:{ Exception -> 0x045b }
            goto L_0x038e
        L_0x038a:
            r31 = r7
            r7 = r50
        L_0x038e:
            return
        L_0x038f:
            r18 = r7
            r53 = r8
            r8 = r11
            r3 = r28
            r11 = r47
            r7 = r50
            throw r38     // Catch:{ Exception -> 0x045b }
        L_0x039b:
            r17 = r3
            r18 = r7
            r53 = r8
            r8 = r11
            r3 = r28
            r11 = r47
            r7 = r50
            throw r38     // Catch:{ Exception -> 0x045b }
        L_0x03a9:
            r17 = r3
            r18 = r7
            r36 = r8
            r8 = r11
            r37 = r12
            r3 = r28
            r11 = r47
            r7 = r50
            r12 = r54
            r28 = r8
            r8 = 0
            r5.decode1D(r12, r10, r8, r4)     // Catch:{ Exception -> 0x045b }
            r8 = r53
            r8.fax4Encode(r12, r4)     // Catch:{ Exception -> 0x045b }
        L_0x03c6:
            int r0 = r18 - r13
            int r9 = r9 + 1
            r50 = r7
            r5 = r8
            r47 = r11
            r4 = r28
            r8 = r36
            r12 = r37
            r10 = r51
            r11 = r52
            r7 = r0
            r28 = r3
            r3 = r17
            goto L_0x026b
        L_0x03e0:
            r17 = r3
            r18 = r7
            r36 = r8
            r51 = r10
            r52 = r11
            r37 = r12
            r3 = r28
            r11 = r47
            r7 = r50
            r28 = r4
            r8 = r5
            byte[] r25 = r8.close()     // Catch:{ Exception -> 0x045b }
            com.itextpdf.io.image.TiffImageData r0 = r2.image     // Catch:{ Exception -> 0x045b }
            r22 = 0
            r23 = 256(0x100, float:3.59E-43)
            r24 = r52 & 1
            r26 = 0
            r19 = r0
            r20 = r6
            r21 = r51
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r19, r20, r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x045b }
        L_0x040c:
            com.itextpdf.io.image.TiffImageData r0 = r2.image     // Catch:{ Exception -> 0x045b }
            r0.setDpi(r11, r3)     // Catch:{ Exception -> 0x045b }
            r0 = 34675(0x8773, float:4.859E-41)
            r4 = r30
            boolean r5 = r4.isTagPresent(r0)     // Catch:{ Exception -> 0x045b }
            if (r5 == 0) goto L_0x0436
            com.itextpdf.io.codec.TIFFField r0 = r4.getField(r0)     // Catch:{ RuntimeException -> 0x0435 }
            byte[] r5 = r0.getAsBytes()     // Catch:{ RuntimeException -> 0x0435 }
            com.itextpdf.io.colors.IccProfile r5 = com.itextpdf.p026io.colors.IccProfile.getInstance((byte[]) r5)     // Catch:{ RuntimeException -> 0x0435 }
            int r8 = r5.getNumComponents()     // Catch:{ RuntimeException -> 0x0435 }
            r9 = 1
            if (r8 != r9) goto L_0x0434
            com.itextpdf.io.image.TiffImageData r8 = r2.image     // Catch:{ RuntimeException -> 0x0435 }
            r8.setProfile(r5)     // Catch:{ RuntimeException -> 0x0435 }
        L_0x0434:
            goto L_0x0436
        L_0x0435:
            r0 = move-exception
        L_0x0436:
            int r0 = (r7 > r45 ? 1 : (r7 == r45 ? 0 : -1))
            if (r0 == 0) goto L_0x043f
            com.itextpdf.io.image.TiffImageData r0 = r2.image     // Catch:{ Exception -> 0x045b }
            r0.setRotation(r7)     // Catch:{ Exception -> 0x045b }
        L_0x043f:
            return
        L_0x0441:
            r0 = move-exception
            r17 = r3
            goto L_0x0464
        L_0x0445:
            r0 = move-exception
            r17 = r3
            r46 = r5
            goto L_0x0464
        L_0x044b:
            return
        L_0x044c:
            r17 = r3
            r29 = r4
            r46 = r5
            r4 = r7
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x045b }
            java.lang.String r3 = "Tiles are not supported."
            r0.<init>((java.lang.String) r3)     // Catch:{ Exception -> 0x045b }
            throw r0     // Catch:{ Exception -> 0x045b }
        L_0x045b:
            r0 = move-exception
            goto L_0x0464
        L_0x045d:
            r0 = move-exception
            r17 = r3
            r29 = r4
            r46 = r5
        L_0x0464:
            com.itextpdf.io.IOException r3 = new com.itextpdf.io.IOException
            java.lang.String r4 = "Cannot read TIFF image."
            r3.<init>((java.lang.String) r4)
            throw r3
        L_0x046c:
            r17 = r3
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException
            java.lang.String r3 = "Page number must be >= 1."
            r0.<init>((java.lang.String) r3)
            goto L_0x0477
        L_0x0476:
            throw r0
        L_0x0477:
            goto L_0x0476
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.TiffImageHelper.processTiffImage(com.itextpdf.io.source.RandomAccessFileOrArray, com.itextpdf.io.image.TiffImageHelper$TiffParameters):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x01af A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01b0 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0202 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0209 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0216 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x022d A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x023b A[ADDED_TO_REGION, Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0260 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x026e A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0277 A[Catch:{ Exception -> 0x062b }] */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02ef  */
    /* JADX WARNING: Removed duplicated region for block: B:229:0x04a9  */
    /* JADX WARNING: Removed duplicated region for block: B:267:0x05b3 A[Catch:{ Exception -> 0x0629 }] */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x05c1 A[Catch:{ Exception -> 0x0629 }] */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x05cc A[Catch:{ Exception -> 0x0629 }] */
    /* JADX WARNING: Removed duplicated region for block: B:273:0x05d4 A[Catch:{ Exception -> 0x0629 }] */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x05d8 A[Catch:{ Exception -> 0x0629 }] */
    /* JADX WARNING: Removed duplicated region for block: B:294:? A[Catch:{ Exception -> 0x0629 }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processTiffImageColor(com.itextpdf.p026io.codec.TIFFDirectory r52, com.itextpdf.p026io.source.RandomAccessFileOrArray r53, com.itextpdf.p026io.image.TiffImageHelper.TiffParameters r54) {
        /*
            r1 = r52
            r2 = r53
            r3 = r54
            r0 = 1
            r4 = 259(0x103, float:3.63E-43)
            boolean r5 = r1.isTagPresent(r4)     // Catch:{ Exception -> 0x062b }
            if (r5 == 0) goto L_0x0016
            long r4 = r1.getFieldAsLong(r4)     // Catch:{ Exception -> 0x062b }
            int r0 = (int) r4
            r4 = r0
            goto L_0x0017
        L_0x0016:
            r4 = r0
        L_0x0017:
            r0 = 1
            r5 = 0
            r6 = 0
            r7 = 1
            switch(r4) {
                case 1: goto L_0x0027;
                case 5: goto L_0x0027;
                case 6: goto L_0x0027;
                case 7: goto L_0x0027;
                case 8: goto L_0x0027;
                case 32773: goto L_0x0027;
                case 32946: goto L_0x0027;
                default: goto L_0x001e;
            }
        L_0x001e:
            r24 = r0
            r10 = r1
            r23 = r5
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x0629 }
            goto L_0x0615
        L_0x0027:
            r8 = 262(0x106, float:3.67E-43)
            long r8 = r1.getFieldAsLong(r8)     // Catch:{ Exception -> 0x062b }
            int r9 = (int) r8     // Catch:{ Exception -> 0x062b }
            r8 = 6
            r10 = 7
            switch(r9) {
                case 0: goto L_0x0039;
                case 1: goto L_0x0039;
                case 2: goto L_0x0039;
                case 3: goto L_0x0039;
                case 4: goto L_0x0034;
                case 5: goto L_0x0039;
                default: goto L_0x0034;
            }     // Catch:{ Exception -> 0x062b }
        L_0x0034:
            if (r4 == r8) goto L_0x004e
            if (r4 != r10) goto L_0x003a
            goto L_0x004e
        L_0x0039:
            goto L_0x004e
        L_0x003a:
            com.itextpdf.io.IOException r8 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x062b }
            java.lang.String r10 = "Photometric {0} is not supported."
            r8.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x062b }
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x062b }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x062b }
            r7[r6] = r10     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.IOException r6 = r8.setMessageParams(r7)     // Catch:{ Exception -> 0x062b }
            throw r6     // Catch:{ Exception -> 0x062b }
        L_0x004e:
            r11 = 0
            r12 = 274(0x112, float:3.84E-43)
            boolean r13 = r1.isTagPresent(r12)     // Catch:{ Exception -> 0x062b }
            r14 = 4
            r15 = 5
            r7 = 8
            r6 = 3
            if (r13 == 0) goto L_0x007a
            long r12 = r1.getFieldAsLong(r12)     // Catch:{ Exception -> 0x062b }
            int r13 = (int) r12     // Catch:{ Exception -> 0x062b }
            if (r13 == r6) goto L_0x0077
            if (r13 != r14) goto L_0x0066
            goto L_0x0077
        L_0x0066:
            if (r13 == r15) goto L_0x0073
            if (r13 != r7) goto L_0x006b
            goto L_0x0073
        L_0x006b:
            if (r13 == r8) goto L_0x006f
            if (r13 != r10) goto L_0x007a
        L_0x006f:
            r11 = -1077342245(0xffffffffbfc90fdb, float:-1.5707964)
            goto L_0x007a
        L_0x0073:
            r11 = 1070141403(0x3fc90fdb, float:1.5707964)
            goto L_0x007a
        L_0x0077:
            r11 = 1078530011(0x40490fdb, float:3.1415927)
        L_0x007a:
            r12 = 284(0x11c, float:3.98E-43)
            boolean r13 = r1.isTagPresent(r12)     // Catch:{ Exception -> 0x062b }
            if (r13 == 0) goto L_0x0095
            long r12 = r1.getFieldAsLong(r12)     // Catch:{ Exception -> 0x062b }
            r18 = 2
            int r20 = (r12 > r18 ? 1 : (r12 == r18 ? 0 : -1))
            if (r20 == 0) goto L_0x008d
            goto L_0x0095
        L_0x008d:
            com.itextpdf.io.IOException r6 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x062b }
            java.lang.String r7 = "Planar images are not supported."
            r6.<init>((java.lang.String) r7)     // Catch:{ Exception -> 0x062b }
            throw r6     // Catch:{ Exception -> 0x062b }
        L_0x0095:
            r12 = 0
            r13 = 338(0x152, float:4.74E-43)
            boolean r13 = r1.isTagPresent(r13)     // Catch:{ Exception -> 0x062b }
            if (r13 == 0) goto L_0x009f
            r12 = 1
        L_0x009f:
            r13 = 1
            r14 = 277(0x115, float:3.88E-43)
            boolean r19 = r1.isTagPresent(r14)     // Catch:{ Exception -> 0x062b }
            if (r19 == 0) goto L_0x00b0
            r19 = r11
            long r10 = r1.getFieldAsLong(r14)     // Catch:{ Exception -> 0x062b }
            int r13 = (int) r10     // Catch:{ Exception -> 0x062b }
            goto L_0x00b2
        L_0x00b0:
            r19 = r11
        L_0x00b2:
            r10 = 1
            r11 = 258(0x102, float:3.62E-43)
            boolean r14 = r1.isTagPresent(r11)     // Catch:{ Exception -> 0x062b }
            if (r14 == 0) goto L_0x00c2
            r14 = r9
            long r8 = r1.getFieldAsLong(r11)     // Catch:{ Exception -> 0x062b }
            int r10 = (int) r8
            goto L_0x00c3
        L_0x00c2:
            r14 = r9
        L_0x00c3:
            switch(r10) {
                case 1: goto L_0x00d6;
                case 2: goto L_0x00d6;
                case 4: goto L_0x00d6;
                case 8: goto L_0x00d6;
                default: goto L_0x00c6;
            }
        L_0x00c6:
            r24 = r0
            r23 = r5
            r37 = r12
            r15 = r14
            r14 = r13
            r13 = r10
            r10 = r1
            r1 = r19
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x0629 }
            goto L_0x0601
        L_0x00d6:
            r8 = 257(0x101, float:3.6E-43)
            long r8 = r1.getFieldAsLong(r8)     // Catch:{ Exception -> 0x062b }
            int r9 = (int) r8     // Catch:{ Exception -> 0x062b }
            r8 = 256(0x100, float:3.59E-43)
            long r6 = r1.getFieldAsLong(r8)     // Catch:{ Exception -> 0x062b }
            int r7 = (int) r6     // Catch:{ Exception -> 0x062b }
            r6 = 2
            r8 = 296(0x128, float:4.15E-43)
            boolean r21 = r1.isTagPresent(r8)     // Catch:{ Exception -> 0x062b }
            if (r21 == 0) goto L_0x00f6
            r37 = r12
            long r11 = r1.getFieldAsLong(r8)     // Catch:{ Exception -> 0x062b }
            int r6 = (int) r11     // Catch:{ Exception -> 0x062b }
            goto L_0x00f8
        L_0x00f6:
            r37 = r12
        L_0x00f8:
            r8 = 282(0x11a, float:3.95E-43)
            com.itextpdf.io.codec.TIFFField r8 = r1.getField(r8)     // Catch:{ Exception -> 0x062b }
            int r8 = getDpi(r8, r6)     // Catch:{ Exception -> 0x062b }
            r11 = 283(0x11b, float:3.97E-43)
            com.itextpdf.io.codec.TIFFField r11 = r1.getField(r11)     // Catch:{ Exception -> 0x062b }
            int r11 = getDpi(r11, r6)     // Catch:{ Exception -> 0x062b }
            r12 = r11
            r11 = 1
            r15 = 266(0x10a, float:3.73E-43)
            com.itextpdf.io.codec.TIFFField r15 = r1.getField(r15)     // Catch:{ Exception -> 0x062b }
            if (r15 == 0) goto L_0x0121
            r23 = r5
            r5 = 0
            int r24 = r15.getAsInt(r5)     // Catch:{ Exception -> 0x062b }
            r11 = r24
            r5 = r11
            goto L_0x0124
        L_0x0121:
            r23 = r5
            r5 = r11
        L_0x0124:
            r11 = 2
            if (r5 != r11) goto L_0x012a
            r24 = 1
            goto L_0x012c
        L_0x012a:
            r24 = 0
        L_0x012c:
            r38 = r24
            r24 = r9
            r11 = 278(0x116, float:3.9E-43)
            boolean r26 = r1.isTagPresent(r11)     // Catch:{ Exception -> 0x062b }
            if (r26 == 0) goto L_0x0142
            r40 = r5
            r39 = r6
            long r5 = r1.getFieldAsLong(r11)     // Catch:{ Exception -> 0x062b }
            int r6 = (int) r5     // Catch:{ Exception -> 0x062b }
            goto L_0x0148
        L_0x0142:
            r40 = r5
            r39 = r6
            r6 = r24
        L_0x0148:
            if (r6 <= 0) goto L_0x014c
            if (r6 <= r9) goto L_0x014d
        L_0x014c:
            r6 = r9
        L_0x014d:
            r5 = 273(0x111, float:3.83E-43)
            long[] r5 = getArrayLongShort(r1, r5)     // Catch:{ Exception -> 0x062b }
            r11 = 279(0x117, float:3.91E-43)
            long[] r11 = getArrayLongShort(r1, r11)     // Catch:{ Exception -> 0x062b }
            if (r11 == 0) goto L_0x0180
            r24 = r0
            int r0 = r11.length     // Catch:{ Exception -> 0x062b }
            r26 = r14
            r14 = 1
            if (r0 != r14) goto L_0x017b
            r14 = 0
            r29 = r11[r14]     // Catch:{ Exception -> 0x062b }
            r31 = 0
            int r0 = (r29 > r31 ? 1 : (r29 == r31 ? 0 : -1))
            if (r0 == 0) goto L_0x0184
            r29 = r11[r14]     // Catch:{ Exception -> 0x062b }
            r31 = r5[r14]     // Catch:{ Exception -> 0x062b }
            long r29 = r29 + r31
            long r31 = r53.length()     // Catch:{ Exception -> 0x062b }
            int r0 = (r29 > r31 ? 1 : (r29 == r31 ? 0 : -1))
            if (r0 <= 0) goto L_0x017b
            goto L_0x0184
        L_0x017b:
            r27 = r11
            r41 = r12
            goto L_0x01a1
        L_0x0180:
            r24 = r0
            r26 = r14
        L_0x0184:
            if (r9 != r6) goto L_0x019d
            r14 = 1
            long[] r0 = new long[r14]     // Catch:{ Exception -> 0x062b }
            long r29 = r53.length()     // Catch:{ Exception -> 0x062b }
            r27 = r11
            r41 = r12
            r14 = 0
            r11 = r5[r14]     // Catch:{ Exception -> 0x062b }
            int r12 = (int) r11     // Catch:{ Exception -> 0x062b }
            long r11 = (long) r12     // Catch:{ Exception -> 0x062b }
            long r29 = r29 - r11
            r0[r14] = r29     // Catch:{ Exception -> 0x062b }
            r11 = r0
            r12 = r11
            goto L_0x01a3
        L_0x019d:
            r27 = r11
            r41 = r12
        L_0x01a1:
            r12 = r27
        L_0x01a3:
            r0 = 5
            if (r4 == r0) goto L_0x01b2
            r0 = 32946(0x80b2, float:4.6167E-41)
            if (r4 == r0) goto L_0x01b2
            r11 = 8
            if (r4 != r11) goto L_0x01b0
            goto L_0x01b2
        L_0x01b0:
            r14 = 2
            goto L_0x01fd
        L_0x01b2:
            r14 = 2
            r0 = 317(0x13d, float:4.44E-43)
            com.itextpdf.io.codec.TIFFField r0 = r1.getField(r0)     // Catch:{ Exception -> 0x062b }
            if (r0 == 0) goto L_0x01fb
            r11 = 0
            int r25 = r0.getAsInt(r11)     // Catch:{ Exception -> 0x062b }
            r11 = r25
            r14 = 1
            if (r11 == r14) goto L_0x01d5
            r14 = 2
            if (r11 != r14) goto L_0x01cb
            r25 = r0
            goto L_0x01d7
        L_0x01cb:
            com.itextpdf.io.IOException r14 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x062b }
            r25 = r0
            java.lang.String r0 = "Illegal value for predictor in TIFF file."
            r14.<init>((java.lang.String) r0)     // Catch:{ Exception -> 0x062b }
            throw r14     // Catch:{ Exception -> 0x062b }
        L_0x01d5:
            r25 = r0
        L_0x01d7:
            r14 = 2
            if (r11 != r14) goto L_0x01f8
            r14 = 8
            if (r10 != r14) goto L_0x01df
            goto L_0x01f8
        L_0x01df:
            r0 = r11
            com.itextpdf.io.IOException r11 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x062b }
            java.lang.String r14 = "{0} bit samples are not supported for horizontal differencing predictor."
            r11.<init>((java.lang.String) r14)     // Catch:{ Exception -> 0x062b }
            r14 = 1
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x062b }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x062b }
            r17 = 0
            r14[r17] = r16     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.IOException r11 = r11.setMessageParams(r14)     // Catch:{ Exception -> 0x062b }
            throw r11     // Catch:{ Exception -> 0x062b }
        L_0x01f8:
            r0 = r11
            r14 = r0
            goto L_0x01ff
        L_0x01fb:
            r25 = r0
        L_0x01fd:
            r14 = r24
        L_0x01ff:
            r0 = 5
            if (r4 != r0) goto L_0x0209
            com.itextpdf.io.codec.TIFFLZWDecoder r0 = new com.itextpdf.io.codec.TIFFLZWDecoder     // Catch:{ Exception -> 0x062b }
            r0.<init>(r7, r14, r13)     // Catch:{ Exception -> 0x062b }
            r11 = r0
            goto L_0x020b
        L_0x0209:
            r11 = r23
        L_0x020b:
            r0 = r9
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            if (r37 <= 0) goto L_0x022d
            com.itextpdf.io.source.ByteArrayOutputStream r27 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ Exception -> 0x062b }
            r27.<init>()     // Catch:{ Exception -> 0x062b }
            r23 = r27
            r27 = r0
            com.itextpdf.io.source.DeflaterOutputStream r0 = new com.itextpdf.io.source.DeflaterOutputStream     // Catch:{ Exception -> 0x062b }
            r42 = r15
            r15 = r23
            r0.<init>(r15)     // Catch:{ Exception -> 0x062b }
            r25 = r0
            r43 = r25
            goto L_0x0235
        L_0x022d:
            r27 = r0
            r42 = r15
            r15 = r23
            r43 = r25
        L_0x0235:
            r0 = 0
            r44 = r15
            r15 = 1
            if (r10 != r15) goto L_0x024f
            if (r13 != r15) goto L_0x024f
            r45 = r8
            r15 = r26
            r8 = 3
            if (r15 == r8) goto L_0x0253
            com.itextpdf.io.codec.CCITTG4Encoder r8 = new com.itextpdf.io.codec.CCITTG4Encoder     // Catch:{ Exception -> 0x062b }
            r8.<init>(r7)     // Catch:{ Exception -> 0x062b }
            r0 = r8
            r46 = r22
            r47 = r24
            goto L_0x0274
        L_0x024f:
            r45 = r8
            r15 = r26
        L_0x0253:
            com.itextpdf.io.source.ByteArrayOutputStream r8 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ Exception -> 0x062b }
            r8.<init>()     // Catch:{ Exception -> 0x062b }
            r22 = r0
            r0 = 6
            if (r4 == r0) goto L_0x026e
            r0 = 7
            if (r4 == r0) goto L_0x026e
            com.itextpdf.io.source.DeflaterOutputStream r0 = new com.itextpdf.io.source.DeflaterOutputStream     // Catch:{ Exception -> 0x062b }
            r0.<init>(r8)     // Catch:{ Exception -> 0x062b }
            r24 = r0
            r46 = r8
            r8 = r22
            r47 = r24
            goto L_0x0274
        L_0x026e:
            r46 = r8
            r8 = r22
            r47 = r24
        L_0x0274:
            r0 = 6
            if (r4 != r0) goto L_0x02ef
            r0 = 513(0x201, float:7.19E-43)
            boolean r22 = r1.isTagPresent(r0)     // Catch:{ Exception -> 0x062b }
            if (r22 == 0) goto L_0x02de
            r49 = r8
            r48 = r9
            long r8 = r1.getFieldAsLong(r0)     // Catch:{ Exception -> 0x062b }
            int r0 = (int) r8     // Catch:{ Exception -> 0x062b }
            long r8 = r53.length()     // Catch:{ Exception -> 0x062b }
            int r9 = (int) r8     // Catch:{ Exception -> 0x062b }
            int r9 = r9 - r0
            r8 = 514(0x202, float:7.2E-43)
            boolean r22 = r1.isTagPresent(r8)     // Catch:{ Exception -> 0x062b }
            if (r22 == 0) goto L_0x02a7
            r22 = r9
            long r8 = r1.getFieldAsLong(r8)     // Catch:{ Exception -> 0x062b }
            int r9 = (int) r8     // Catch:{ Exception -> 0x062b }
            r50 = r13
            r51 = r14
            r8 = 0
            r13 = r12[r8]     // Catch:{ Exception -> 0x062b }
            int r8 = (int) r13     // Catch:{ Exception -> 0x062b }
            int r9 = r9 + r8
            goto L_0x02ad
        L_0x02a7:
            r22 = r9
            r50 = r13
            r51 = r14
        L_0x02ad:
            long r13 = r53.length()     // Catch:{ Exception -> 0x062b }
            int r8 = (int) r13     // Catch:{ Exception -> 0x062b }
            int r8 = r8 - r0
            int r8 = java.lang.Math.min(r9, r8)     // Catch:{ Exception -> 0x062b }
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x062b }
            long r13 = r53.getPosition()     // Catch:{ Exception -> 0x062b }
            int r14 = (int) r13     // Catch:{ Exception -> 0x062b }
            int r14 = r14 + r0
            r22 = r9
            r13 = r10
            long r9 = (long) r14     // Catch:{ Exception -> 0x062b }
            r2.seek(r9)     // Catch:{ Exception -> 0x062b }
            r2.readFully(r8)     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.image.TiffImageData r9 = r3.image     // Catch:{ Exception -> 0x062b }
            r9.data = r8     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.image.TiffImageData r9 = r3.image     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.image.ImageType r10 = com.itextpdf.p026io.image.ImageType.JPEG     // Catch:{ Exception -> 0x062b }
            r9.setOriginalType(r10)     // Catch:{ Exception -> 0x062b }
            com.itextpdf.io.image.TiffImageData r9 = r3.image     // Catch:{ Exception -> 0x062b }
            com.itextpdf.p026io.image.JpegImageHelper.processImage(r9)     // Catch:{ Exception -> 0x062b }
            r9 = 1
            r3.jpegProcessing = r9     // Catch:{ Exception -> 0x062b }
            goto L_0x038b
        L_0x02de:
            r49 = r8
            r48 = r9
            r50 = r13
            r51 = r14
            r13 = r10
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x062b }
            java.lang.String r8 = "Missing tag(s) for OJPEG compression"
            r0.<init>((java.lang.String) r8)     // Catch:{ Exception -> 0x062b }
            throw r0     // Catch:{ Exception -> 0x062b }
        L_0x02ef:
            r49 = r8
            r48 = r9
            r50 = r13
            r51 = r14
            r13 = r10
            r0 = 7
            if (r4 != r0) goto L_0x03b3
            int r0 = r12.length     // Catch:{ Exception -> 0x03ae }
            r8 = 1
            if (r0 > r8) goto L_0x0397
            r8 = 0
            r9 = r12[r8]     // Catch:{ Exception -> 0x03ae }
            int r0 = (int) r9     // Catch:{ Exception -> 0x03ae }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x03ae }
            r9 = r5[r8]     // Catch:{ Exception -> 0x03ae }
            r2.seek(r9)     // Catch:{ Exception -> 0x03ae }
            r2.readFully(r0)     // Catch:{ Exception -> 0x03ae }
            r8 = 347(0x15b, float:4.86E-43)
            com.itextpdf.io.codec.TIFFField r8 = r1.getField(r8)     // Catch:{ Exception -> 0x03ae }
            if (r8 == 0) goto L_0x036c
            byte[] r9 = r8.getAsBytes()     // Catch:{ Exception -> 0x03ae }
            r10 = 0
            int r14 = r9.length     // Catch:{ Exception -> 0x03ae }
            r22 = r8
            r17 = 0
            byte r8 = r9[r17]     // Catch:{ Exception -> 0x03ae }
            r23 = r10
            r10 = -1
            if (r8 != r10) goto L_0x0331
            r8 = 1
            byte r10 = r9[r8]     // Catch:{ Exception -> 0x062b }
            r8 = -40
            if (r10 != r8) goto L_0x0331
            r10 = 2
            int r14 = r14 + -2
            goto L_0x0333
        L_0x0331:
            r10 = r23
        L_0x0333:
            int r8 = r9.length     // Catch:{ Exception -> 0x03ae }
            r23 = 2
            int r8 = r8 + -2
            byte r8 = r9[r8]     // Catch:{ Exception -> 0x03ae }
            r1 = -1
            if (r8 != r1) goto L_0x0348
            int r1 = r9.length     // Catch:{ Exception -> 0x03ae }
            r8 = 1
            int r1 = r1 - r8
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x03ae }
            r8 = -39
            if (r1 != r8) goto L_0x0348
            int r14 = r14 + -2
        L_0x0348:
            byte[] r1 = new byte[r14]     // Catch:{ Exception -> 0x03ae }
            r8 = 0
            java.lang.System.arraycopy(r9, r10, r1, r8, r14)     // Catch:{ Exception -> 0x03ae }
            int r8 = r0.length     // Catch:{ Exception -> 0x03ae }
            r23 = r9
            int r9 = r1.length     // Catch:{ Exception -> 0x03ae }
            int r8 = r8 + r9
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x03ae }
            r24 = r10
            r9 = 2
            r10 = 0
            java.lang.System.arraycopy(r0, r10, r8, r10, r9)     // Catch:{ Exception -> 0x03ae }
            int r9 = r1.length     // Catch:{ Exception -> 0x03ae }
            r26 = r14
            r14 = 2
            java.lang.System.arraycopy(r1, r10, r8, r14, r9)     // Catch:{ Exception -> 0x03ae }
            int r9 = r1.length     // Catch:{ Exception -> 0x03ae }
            int r9 = r9 + r14
            int r10 = r0.length     // Catch:{ Exception -> 0x03ae }
            int r10 = r10 - r14
            java.lang.System.arraycopy(r0, r14, r8, r9, r10)     // Catch:{ Exception -> 0x03ae }
            r0 = r8
            goto L_0x036e
        L_0x036c:
            r22 = r8
        L_0x036e:
            com.itextpdf.io.image.TiffImageData r1 = r3.image     // Catch:{ Exception -> 0x03ae }
            r1.data = r0     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.image.TiffImageData r1 = r3.image     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.image.ImageType r8 = com.itextpdf.p026io.image.ImageType.JPEG     // Catch:{ Exception -> 0x03ae }
            r1.setOriginalType(r8)     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.image.TiffImageData r1 = r3.image     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.p026io.image.JpegImageHelper.processImage(r1)     // Catch:{ Exception -> 0x03ae }
            r1 = 1
            r3.jpegProcessing = r1     // Catch:{ Exception -> 0x03ae }
            r1 = 2
            if (r15 != r1) goto L_0x038a
            com.itextpdf.io.image.TiffImageData r1 = r3.image     // Catch:{ Exception -> 0x03ae }
            r8 = 0
            r1.setColorTransform(r8)     // Catch:{ Exception -> 0x03ae }
        L_0x038a:
        L_0x038b:
            r1 = r27
            r2 = r49
            r14 = r50
            r27 = r5
            r5 = r47
            goto L_0x049a
        L_0x0397:
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException     // Catch:{ Exception -> 0x03ae }
            java.lang.String r1 = "Compression jpeg is only supported with a single strip. This image has {0} strips."
            r0.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x03ae }
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x03ae }
            int r8 = r12.length     // Catch:{ Exception -> 0x03ae }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x03ae }
            r9 = 0
            r1[r9] = r8     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.IOException r0 = r0.setMessageParams(r1)     // Catch:{ Exception -> 0x03ae }
            throw r0     // Catch:{ Exception -> 0x03ae }
        L_0x03ae:
            r0 = move-exception
            r10 = r52
            goto L_0x062d
        L_0x03b3:
            r0 = 0
            r1 = r27
        L_0x03b6:
            int r8 = r5.length     // Catch:{ Exception -> 0x03ae }
            if (r0 >= r8) goto L_0x0451
            r8 = r12[r0]     // Catch:{ Exception -> 0x03ae }
            int r9 = (int) r8     // Catch:{ Exception -> 0x03ae }
            byte[] r8 = new byte[r9]     // Catch:{ Exception -> 0x03ae }
            r9 = r5[r0]     // Catch:{ Exception -> 0x03ae }
            r2.seek(r9)     // Catch:{ Exception -> 0x03ae }
            r2.readFully(r8)     // Catch:{ Exception -> 0x03ae }
            int r9 = java.lang.Math.min(r6, r1)     // Catch:{ Exception -> 0x03ae }
            r10 = 0
            r14 = 1
            if (r4 == r14) goto L_0x03df
            int r14 = r7 * r13
            int r14 = r14 * r50
            r20 = 7
            int r14 = r14 + 7
            r21 = 8
            int r14 = r14 / 8
            int r14 = r14 * r9
            byte[] r14 = new byte[r14]     // Catch:{ Exception -> 0x03ae }
            r10 = r14
        L_0x03df:
            if (r38 == 0) goto L_0x03e4
            com.itextpdf.p026io.codec.TIFFFaxDecoder.reverseBits(r8)     // Catch:{ Exception -> 0x03ae }
        L_0x03e4:
            switch(r4) {
                case 1: goto L_0x0407;
                case 5: goto L_0x03ff;
                case 8: goto L_0x03f4;
                case 32773: goto L_0x03ec;
                case 32946: goto L_0x03f4;
                default: goto L_0x03e7;
            }     // Catch:{ Exception -> 0x03ae }
        L_0x03e7:
            r14 = r50
            r2 = r51
            goto L_0x040d
        L_0x03ec:
            decodePackbits(r8, r10)     // Catch:{ Exception -> 0x03ae }
            r14 = r50
            r2 = r51
            goto L_0x040d
        L_0x03f4:
            com.itextpdf.p026io.util.FilterUtil.inflateData(r8, r10)     // Catch:{ Exception -> 0x03ae }
            r14 = r50
            r2 = r51
            applyPredictor(r10, r2, r7, r9, r14)     // Catch:{ Exception -> 0x03ae }
            goto L_0x040d
        L_0x03ff:
            r14 = r50
            r2 = r51
            r11.decode(r8, r10, r9)     // Catch:{ Exception -> 0x03ae }
            goto L_0x040d
        L_0x0407:
            r14 = r50
            r2 = r51
            r10 = r8
        L_0x040d:
            r51 = r2
            r2 = 1
            if (r13 != r2) goto L_0x0421
            if (r14 != r2) goto L_0x0421
            r2 = 3
            if (r15 == r2) goto L_0x0421
            r2 = r49
            r2.fax4Encode(r10, r9)     // Catch:{ Exception -> 0x03ae }
            r27 = r5
            r5 = r47
            goto L_0x0442
        L_0x0421:
            r2 = r49
            if (r37 <= 0) goto L_0x043b
            r21 = r47
            r22 = r43
            r23 = r10
            r24 = r14
            r25 = r13
            r26 = r7
            r27 = r9
            processExtraSamples(r21, r22, r23, r24, r25, r26, r27)     // Catch:{ Exception -> 0x03ae }
            r27 = r5
            r5 = r47
            goto L_0x0442
        L_0x043b:
            r27 = r5
            r5 = r47
            r5.write(r10)     // Catch:{ Exception -> 0x03ae }
        L_0x0442:
            int r1 = r1 - r6
            int r0 = r0 + 1
            r49 = r2
            r47 = r5
            r50 = r14
            r5 = r27
            r2 = r53
            goto L_0x03b6
        L_0x0451:
            r27 = r5
            r5 = r47
            r2 = r49
            r14 = r50
            r8 = 1
            if (r13 != r8) goto L_0x047e
            if (r14 != r8) goto L_0x047e
            r9 = 3
            if (r15 == r9) goto L_0x047e
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x03ae }
            r32 = 0
            r33 = 256(0x100, float:3.59E-43)
            if (r15 != r8) goto L_0x046c
            r34 = 1
            goto L_0x046e
        L_0x046c:
            r34 = 0
        L_0x046e:
            byte[] r35 = r2.close()     // Catch:{ Exception -> 0x03ae }
            r36 = 0
            r29 = r0
            r30 = r7
            r31 = r48
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r29, r30, r31, r32, r33, r34, r35, r36)     // Catch:{ Exception -> 0x03ae }
            goto L_0x049a
        L_0x047e:
            r5.close()     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x03ae }
            int r24 = r14 - r37
            byte[] r26 = r46.toByteArray()     // Catch:{ Exception -> 0x03ae }
            r21 = r0
            r22 = r7
            r23 = r48
            r25 = r13
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x03ae }
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x03ae }
            r8 = 1
            r0.setDeflated(r8)     // Catch:{ Exception -> 0x03ae }
        L_0x049a:
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x03ae }
            r9 = r41
            r8 = r45
            r0.setDpi(r8, r9)     // Catch:{ Exception -> 0x03ae }
            r0 = 6
            if (r4 == r0) goto L_0x05b3
            r0 = 7
            if (r4 == r0) goto L_0x05b3
            r0 = 34675(0x8773, float:4.859E-41)
            r10 = r52
            boolean r20 = r10.isTagPresent(r0)     // Catch:{ Exception -> 0x0629 }
            if (r20 == 0) goto L_0x04df
            com.itextpdf.io.codec.TIFFField r0 = r10.getField(r0)     // Catch:{ RuntimeException -> 0x04db }
            byte[] r20 = r0.getAsBytes()     // Catch:{ RuntimeException -> 0x04db }
            com.itextpdf.io.colors.IccProfile r20 = com.itextpdf.p026io.colors.IccProfile.getInstance((byte[]) r20)     // Catch:{ RuntimeException -> 0x04db }
            r21 = r20
            r20 = r0
            int r0 = r14 - r37
            r28 = r1
            int r1 = r21.getNumComponents()     // Catch:{ RuntimeException -> 0x04d9 }
            if (r0 != r1) goto L_0x04d6
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ RuntimeException -> 0x04d9 }
            r1 = r21
            r0.setProfile(r1)     // Catch:{ RuntimeException -> 0x04d9 }
            goto L_0x04d8
        L_0x04d6:
            r1 = r21
        L_0x04d8:
            goto L_0x04e1
        L_0x04d9:
            r0 = move-exception
            goto L_0x04e1
        L_0x04db:
            r0 = move-exception
            r28 = r1
            goto L_0x04e1
        L_0x04df:
            r28 = r1
        L_0x04e1:
            r0 = 320(0x140, float:4.48E-43)
            boolean r1 = r10.isTagPresent(r0)     // Catch:{ Exception -> 0x0629 }
            if (r1 == 0) goto L_0x05aa
            com.itextpdf.io.codec.TIFFField r0 = r10.getField(r0)     // Catch:{ Exception -> 0x0629 }
            char[] r1 = r0.getAsChars()     // Catch:{ Exception -> 0x0629 }
            r20 = r0
            int r0 = r1.length     // Catch:{ Exception -> 0x0629 }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0629 }
            r49 = r2
            int r2 = r1.length     // Catch:{ Exception -> 0x0629 }
            r21 = 3
            int r2 = r2 / 3
            int r21 = r2 * 2
            r22 = 0
            r29 = r5
            r5 = r22
        L_0x0505:
            if (r5 >= r2) goto L_0x053d
            int r22 = r5 * 3
            char r23 = r1[r5]     // Catch:{ Exception -> 0x0629 }
            r30 = r6
            r24 = 8
            int r6 = r23 >> 8
            byte r6 = (byte) r6     // Catch:{ Exception -> 0x0629 }
            r0[r22] = r6     // Catch:{ Exception -> 0x0629 }
            int r6 = r5 * 3
            r16 = 1
            int r6 = r6 + 1
            int r22 = r5 + r2
            char r22 = r1[r22]     // Catch:{ Exception -> 0x0629 }
            r45 = r8
            r23 = 8
            int r8 = r22 >> 8
            byte r8 = (byte) r8     // Catch:{ Exception -> 0x0629 }
            r0[r6] = r8     // Catch:{ Exception -> 0x0629 }
            int r6 = r5 * 3
            r8 = 2
            int r6 = r6 + r8
            int r8 = r5 + r21
            char r8 = r1[r8]     // Catch:{ Exception -> 0x0629 }
            r22 = 8
            int r8 = r8 >> 8
            byte r8 = (byte) r8     // Catch:{ Exception -> 0x0629 }
            r0[r6] = r8     // Catch:{ Exception -> 0x0629 }
            int r5 = r5 + 1
            r6 = r30
            r8 = r45
            goto L_0x0505
        L_0x053d:
            r30 = r6
            r45 = r8
            r5 = 1
            r6 = 0
        L_0x0543:
            int r8 = r0.length     // Catch:{ Exception -> 0x0629 }
            if (r6 >= r8) goto L_0x054f
            byte r8 = r0[r6]     // Catch:{ Exception -> 0x0629 }
            if (r8 == 0) goto L_0x054c
            r5 = 0
            goto L_0x054f
        L_0x054c:
            int r6 = r6 + 1
            goto L_0x0543
        L_0x054f:
            if (r5 == 0) goto L_0x057b
            r6 = 0
        L_0x0552:
            if (r6 >= r2) goto L_0x0578
            int r8 = r6 * 3
            r22 = r5
            char r5 = r1[r6]     // Catch:{ Exception -> 0x0629 }
            byte r5 = (byte) r5     // Catch:{ Exception -> 0x0629 }
            r0[r8] = r5     // Catch:{ Exception -> 0x0629 }
            int r5 = r6 * 3
            r8 = 1
            int r5 = r5 + r8
            int r8 = r6 + r2
            char r8 = r1[r8]     // Catch:{ Exception -> 0x0629 }
            byte r8 = (byte) r8     // Catch:{ Exception -> 0x0629 }
            r0[r5] = r8     // Catch:{ Exception -> 0x0629 }
            int r5 = r6 * 3
            r8 = 2
            int r5 = r5 + r8
            int r8 = r6 + r21
            char r8 = r1[r8]     // Catch:{ Exception -> 0x0629 }
            byte r8 = (byte) r8     // Catch:{ Exception -> 0x0629 }
            r0[r5] = r8     // Catch:{ Exception -> 0x0629 }
            int r6 = r6 + 1
            r5 = r22
            goto L_0x0552
        L_0x0578:
            r22 = r5
            goto L_0x057d
        L_0x057b:
            r22 = r5
        L_0x057d:
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0629 }
            java.lang.String r6 = "/Indexed"
            r8 = 0
            r5[r8] = r6     // Catch:{ Exception -> 0x0629 }
            java.lang.String r6 = "/DeviceRGB"
            r8 = 1
            r5[r8] = r6     // Catch:{ Exception -> 0x0629 }
            int r6 = r2 + -1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x0629 }
            r8 = 2
            r5[r8] = r6     // Catch:{ Exception -> 0x0629 }
            r6 = 0
            java.lang.String r6 = com.itextpdf.p026io.font.PdfEncodings.convertToString(r0, r6)     // Catch:{ Exception -> 0x0629 }
            r8 = 3
            r5[r8] = r6     // Catch:{ Exception -> 0x0629 }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Exception -> 0x0629 }
            r6.<init>()     // Catch:{ Exception -> 0x0629 }
            r3.additional = r6     // Catch:{ Exception -> 0x0629 }
            java.util.Map<java.lang.String, java.lang.Object> r6 = r3.additional     // Catch:{ Exception -> 0x0629 }
            java.lang.String r8 = "ColorSpace"
            r6.put(r8, r5)     // Catch:{ Exception -> 0x0629 }
            goto L_0x05bf
        L_0x05aa:
            r49 = r2
            r29 = r5
            r30 = r6
            r45 = r8
            goto L_0x05bf
        L_0x05b3:
            r10 = r52
            r28 = r1
            r49 = r2
            r29 = r5
            r30 = r6
            r45 = r8
        L_0x05bf:
            if (r15 != 0) goto L_0x05c7
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x0629 }
            r1 = 1
            r0.setInverted(r1)     // Catch:{ Exception -> 0x0629 }
        L_0x05c7:
            r0 = 0
            int r0 = (r19 > r0 ? 1 : (r19 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x05d4
            com.itextpdf.io.image.TiffImageData r0 = r3.image     // Catch:{ Exception -> 0x0629 }
            r1 = r19
            r0.setRotation(r1)     // Catch:{ Exception -> 0x0629 }
            goto L_0x05d6
        L_0x05d4:
            r1 = r19
        L_0x05d6:
            if (r37 <= 0) goto L_0x05ff
            r43.close()     // Catch:{ Exception -> 0x0629 }
            r0 = 0
            com.itextpdf.io.image.ImageData r0 = com.itextpdf.p026io.image.ImageDataFactory.createRawImage(r0)     // Catch:{ Exception -> 0x0629 }
            com.itextpdf.io.image.RawImageData r0 = (com.itextpdf.p026io.image.RawImageData) r0     // Catch:{ Exception -> 0x0629 }
            r24 = 1
            byte[] r26 = r44.toByteArray()     // Catch:{ Exception -> 0x0629 }
            r21 = r0
            r22 = r7
            r23 = r48
            r25 = r13
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r21, r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x0629 }
            r0.makeMask()     // Catch:{ Exception -> 0x0629 }
            r2 = 1
            r0.setDeflated(r2)     // Catch:{ Exception -> 0x0629 }
            com.itextpdf.io.image.TiffImageData r2 = r3.image     // Catch:{ Exception -> 0x0629 }
            r2.setImageMask(r0)     // Catch:{ Exception -> 0x0629 }
        L_0x05ff:
            return
        L_0x0601:
            java.lang.String r2 = "Bits per sample {0} is not supported."
            r0.<init>((java.lang.String) r2)     // Catch:{ Exception -> 0x0629 }
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0629 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x0629 }
            r6 = 0
            r2[r6] = r5     // Catch:{ Exception -> 0x0629 }
            com.itextpdf.io.IOException r0 = r0.setMessageParams(r2)     // Catch:{ Exception -> 0x0629 }
            throw r0     // Catch:{ Exception -> 0x0629 }
        L_0x0615:
            java.lang.String r1 = "Compression {0} is not supported."
            r0.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x0629 }
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0629 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0629 }
            r5 = 0
            r1[r5] = r2     // Catch:{ Exception -> 0x0629 }
            com.itextpdf.io.IOException r0 = r0.setMessageParams(r1)     // Catch:{ Exception -> 0x0629 }
            throw r0     // Catch:{ Exception -> 0x0629 }
        L_0x0629:
            r0 = move-exception
            goto L_0x062d
        L_0x062b:
            r0 = move-exception
            r10 = r1
        L_0x062d:
            com.itextpdf.io.IOException r1 = new com.itextpdf.io.IOException
            java.lang.String r2 = "Cannot get TIFF image color."
            r1.<init>((java.lang.String) r2)
            goto L_0x0636
        L_0x0635:
            throw r1
        L_0x0636:
            goto L_0x0635
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.TiffImageHelper.processTiffImageColor(com.itextpdf.io.codec.TIFFDirectory, com.itextpdf.io.source.RandomAccessFileOrArray, com.itextpdf.io.image.TiffImageHelper$TiffParameters):void");
    }

    private static int getDpi(TIFFField fd, int resolutionUnit) {
        if (fd == null) {
            return 0;
        }
        long[] res = fd.getAsRational(0);
        float frac = ((float) res[0]) / ((float) res[1]);
        switch (resolutionUnit) {
            case 1:
            case 2:
                double d = (double) frac;
                Double.isNaN(d);
                return (int) (d + 0.5d);
            case 3:
                double d2 = (double) frac;
                Double.isNaN(d2);
                return (int) ((d2 * 2.54d) + 0.5d);
            default:
                return 0;
        }
    }

    private static void processExtraSamples(DeflaterOutputStream zip, DeflaterOutputStream mzip, byte[] outBuf, int samplePerPixel, int bitsPerSample, int width, int height) throws IOException {
        if (bitsPerSample == 8) {
            byte[] mask = new byte[(width * height)];
            int mptr = 0;
            int optr = 0;
            int total = width * height * samplePerPixel;
            int k = 0;
            while (k < total) {
                int s = 0;
                while (s < samplePerPixel - 1) {
                    outBuf[optr] = outBuf[k + s];
                    s++;
                    optr++;
                }
                mask[mptr] = outBuf[(k + samplePerPixel) - 1];
                k += samplePerPixel;
                mptr++;
            }
            zip.write(outBuf, 0, optr);
            mzip.write(mask, 0, mptr);
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ExtraSamplesAreNotSupported);
    }

    private static long[] getArrayLongShort(TIFFDirectory dir, int tag) {
        TIFFField field = dir.getField(tag);
        if (field == null) {
            return null;
        }
        if (field.getType() == 4) {
            return field.getAsLongs();
        }
        char[] offset = field.getAsChars();
        long[] offset2 = new long[offset.length];
        for (int k = 0; k < offset.length; k++) {
            offset2[k] = (long) offset[k];
        }
        return offset2;
    }

    private static void decodePackbits(byte[] data, byte[] dst) {
        int srcCount = 0;
        int dstCount = 0;
        while (dstCount < dst.length) {
            try {
                int srcCount2 = srcCount + 1;
                try {
                    byte b = data[srcCount];
                    if (b >= 0 && b <= Byte.MAX_VALUE) {
                        int i = 0;
                        while (i < b + 1) {
                            int dstCount2 = dstCount + 1;
                            int srcCount3 = srcCount2 + 1;
                            try {
                                dst[dstCount] = data[srcCount2];
                                i++;
                                dstCount = dstCount2;
                                srcCount2 = srcCount3;
                            } catch (Exception e) {
                                int i2 = dstCount2;
                                int i3 = srcCount3;
                                return;
                            }
                        }
                        srcCount = srcCount2;
                    } else if ((b & ByteCompanionObject.MIN_VALUE) == 0 || b == Byte.MIN_VALUE) {
                        srcCount = srcCount2 + 1;
                    } else {
                        int srcCount4 = srcCount2 + 1;
                        try {
                            byte repeat = data[srcCount2];
                            int i4 = 0;
                            while (i4 < ((b ^ -1) & UByte.MAX_VALUE) + 2) {
                                int dstCount3 = dstCount + 1;
                                try {
                                    dst[dstCount] = repeat;
                                    i4++;
                                    dstCount = dstCount3;
                                } catch (Exception e2) {
                                    int i5 = srcCount4;
                                    int i6 = dstCount3;
                                    return;
                                }
                            }
                            srcCount = srcCount4;
                        } catch (Exception e3) {
                            int i7 = srcCount4;
                            return;
                        }
                    }
                } catch (Exception e4) {
                    int i8 = srcCount2;
                    return;
                }
            } catch (Exception e5) {
                return;
            }
        }
    }

    private static void applyPredictor(byte[] uncompData, int predictor, int w, int h, int samplesPerPixel) {
        if (predictor == 2) {
            for (int j = 0; j < h; j++) {
                int count = ((j * w) + 1) * samplesPerPixel;
                for (int i = samplesPerPixel; i < w * samplesPerPixel; i++) {
                    uncompData[count] = (byte) (uncompData[count] + uncompData[count - samplesPerPixel]);
                    count++;
                }
            }
        }
    }
}

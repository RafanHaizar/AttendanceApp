package com.itextpdf.p026io.image;

import com.itextpdf.kernel.xmp.XMPError;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.Primes;

/* renamed from: com.itextpdf.io.image.JpegImageHelper */
class JpegImageHelper {
    private static final byte[] JFIF_ID = {74, 70, 73, 70, 0};
    private static final int M_APP0 = 224;
    private static final int M_APP2 = 226;
    private static final int M_APPD = 237;
    private static final int M_APPE = 238;
    private static final int NOPARAM_MARKER = 2;
    private static final int[] NOPARAM_MARKERS = {208, 209, 210, Primes.SMALL_FACTOR_LIMIT, 212, 213, 214, 215, 216, 1};
    private static final int NOT_A_MARKER = -1;
    private static final byte[] PS_8BIM_RESO = {56, 66, 73, 77, 3, -19};
    private static final int UNSUPPORTED_MARKER = 1;
    private static final int[] UNSUPPORTED_MARKERS = {CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 198, 199, 200, XMPError.BADXML, XMPError.BADRDF, XMPError.BADXMP, 205, 206, 207};
    private static final int VALID_MARKER = 0;
    private static final int[] VALID_MARKERS = {192, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256};

    JpegImageHelper() {
    }

    public static void processImage(ImageData image) {
        String errorID;
        if (image.getOriginalType() == ImageType.JPEG) {
            InputStream jpegStream = null;
            try {
                if (image.getData() == null) {
                    image.loadData();
                    errorID = image.getUrl().toString();
                } else {
                    errorID = "Byte array";
                }
                InputStream jpegStream2 = new ByteArrayInputStream(image.getData());
                image.imageSize = image.getData().length;
                processParameters(jpegStream2, errorID, image);
                try {
                    jpegStream2.close();
                } catch (IOException e) {
                }
                updateAttributes(image);
            } catch (IOException e2) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.JpegImageException, (Throwable) e2);
            } catch (Throwable th) {
                if (jpegStream != null) {
                    try {
                        jpegStream.close();
                    } catch (IOException e3) {
                    }
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("JPEG image expected");
        }
    }

    private static void updateAttributes(ImageData image) {
        image.filter = "DCTDecode";
        if (image.getColorTransform() == 0) {
            Map<String, Object> decodeParms = new HashMap<>();
            decodeParms.put("ColorTransform", 0);
            image.decodeParms = decodeParms;
        }
        if (image.getColorSpace() != 1 && image.getColorSpace() != 3 && image.isInverted()) {
            image.decode = new float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f};
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0256, code lost:
        if (r13 == 2) goto L_0x025a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x025c  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0267  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0297  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processParameters(java.io.InputStream r23, java.lang.String r24, com.itextpdf.p026io.image.ImageData r25) throws java.io.IOException {
        /*
            r1 = r23
            r2 = r25
            r0 = 0
            r3 = r0
            byte[][] r3 = (byte[][]) r3
            int r4 = r23.read()
            r6 = 1
            r7 = 255(0xff, float:3.57E-43)
            if (r4 != r7) goto L_0x036e
            int r4 = r23.read()
            r8 = 216(0xd8, float:3.03E-43)
            if (r4 != r8) goto L_0x036e
            r4 = 1
        L_0x001a:
            int r8 = r23.read()
            if (r8 < 0) goto L_0x0364
            if (r8 != r7) goto L_0x035b
            int r9 = r23.read()
            r10 = 2
            r12 = 16
            if (r4 == 0) goto L_0x00ba
            r0 = 224(0xe0, float:3.14E-43)
            if (r9 != r0) goto L_0x00ba
            r4 = 0
            int r0 = getShort(r23)
            if (r0 >= r12) goto L_0x003e
            int r10 = r0 + -2
            long r10 = (long) r10
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r10)
            goto L_0x00a1
        L_0x003e:
            byte[] r12 = JFIF_ID
            int r12 = r12.length
            byte[] r12 = new byte[r12]
            int r7 = r1.read(r12)
            int r5 = r12.length
            if (r7 != r5) goto L_0x00a7
            r5 = 1
            r17 = 0
            r13 = r17
        L_0x004f:
            int r14 = r12.length
            if (r13 >= r14) goto L_0x005f
            byte r14 = r12[r13]
            byte[] r19 = JFIF_ID
            byte r15 = r19[r13]
            if (r14 == r15) goto L_0x005c
            r5 = 0
            goto L_0x005f
        L_0x005c:
            int r13 = r13 + 1
            goto L_0x004f
        L_0x005f:
            if (r5 != 0) goto L_0x006a
            int r10 = r0 + -2
            int r11 = r12.length
            int r10 = r10 - r11
            long r10 = (long) r10
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r10)
            goto L_0x00a1
        L_0x006a:
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r10)
            int r10 = r23.read()
            int r11 = getShort(r23)
            int r13 = getShort(r23)
            if (r10 != r6) goto L_0x007f
            r2.setDpi(r11, r13)
            goto L_0x0096
        L_0x007f:
            r14 = 2
            if (r10 != r14) goto L_0x0096
            float r14 = (float) r11
            r15 = 1076006748(0x40228f5c, float:2.54)
            float r14 = r14 * r15
            r17 = 1056964608(0x3f000000, float:0.5)
            float r14 = r14 + r17
            int r14 = (int) r14
            float r6 = (float) r13
            float r6 = r6 * r15
            float r6 = r6 + r17
            int r6 = (int) r6
            r2.setDpi(r14, r6)
        L_0x0096:
            int r6 = r0 + -2
            int r14 = r12.length
            int r6 = r6 - r14
            int r6 = r6 + -7
            long r14 = (long) r6
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r14)
        L_0x00a1:
            r0 = 0
            r6 = 1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x001a
        L_0x00a7:
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "{0} corrupted jfif marker."
            r5.<init>((java.lang.String) r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r10 = 0
            r6[r10] = r24
            com.itextpdf.io.IOException r5 = r5.setMessageParams(r6)
            throw r5
        L_0x00ba:
            r0 = 238(0xee, float:3.34E-43)
            java.lang.String r5 = "ISO-8859-1"
            r6 = 12
            if (r9 != r0) goto L_0x00f6
            int r0 = getShort(r23)
            r7 = 2
            int r0 = r0 - r7
            byte[] r7 = new byte[r0]
            r10 = 0
        L_0x00cb:
            if (r10 >= r0) goto L_0x00d7
            int r11 = r23.read()
            byte r11 = (byte) r11
            r7[r10] = r11
            int r10 = r10 + 1
            goto L_0x00cb
        L_0x00d7:
            int r10 = r7.length
            if (r10 < r6) goto L_0x00f2
            java.lang.String r6 = new java.lang.String
            r10 = 5
            r11 = 0
            r6.<init>(r7, r11, r10, r5)
            r5 = r6
            java.lang.String r6 = "Adobe"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x00ee
            r6 = 1
            r2.setInverted(r6)
        L_0x00ee:
            r22 = r4
            goto L_0x02a3
        L_0x00f2:
            r22 = r4
            goto L_0x02a3
        L_0x00f6:
            r0 = 226(0xe2, float:3.17E-43)
            r7 = 14
            if (r9 != r0) goto L_0x0148
            int r0 = getShort(r23)
            r10 = 2
            int r0 = r0 - r10
            byte[] r10 = new byte[r0]
            r11 = 0
        L_0x0105:
            if (r11 >= r0) goto L_0x0111
            int r12 = r23.read()
            byte r12 = (byte) r12
            r10[r11] = r12
            int r11 = r11 + 1
            goto L_0x0105
        L_0x0111:
            int r11 = r10.length
            if (r11 < r7) goto L_0x0144
            java.lang.String r7 = new java.lang.String
            r11 = 11
            r12 = 0
            r7.<init>(r10, r12, r11, r5)
            r5 = r7
            java.lang.String r7 = "ICC_PROFILE"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x013e
            byte r6 = r10[r6]
            r7 = 255(0xff, float:3.57E-43)
            r6 = r6 & r7
            r11 = 13
            byte r11 = r10[r11]
            r11 = r11 & r7
            r7 = 1
            if (r6 >= r7) goto L_0x0133
            r6 = 1
        L_0x0133:
            if (r11 >= r7) goto L_0x0136
            r11 = 1
        L_0x0136:
            if (r3 != 0) goto L_0x013a
            byte[][] r3 = new byte[r11][]
        L_0x013a:
            int r7 = r6 + -1
            r3[r7] = r10
        L_0x013e:
            r0 = 0
            r6 = 1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x001a
        L_0x0144:
            r22 = r4
            goto L_0x02a3
        L_0x0148:
            r0 = 237(0xed, float:3.32E-43)
            r5 = 8
            if (r9 != r0) goto L_0x02ab
            int r0 = getShort(r23)
            r6 = 2
            int r0 = r0 - r6
            byte[] r6 = new byte[r0]
            r7 = 0
        L_0x0157:
            if (r7 >= r0) goto L_0x0163
            int r10 = r23.read()
            byte r10 = (byte) r10
            r6[r7] = r10
            int r7 = r7 + 1
            goto L_0x0157
        L_0x0163:
            r7 = 0
        L_0x0164:
            byte[] r10 = PS_8BIM_RESO
            int r10 = r10.length
            int r10 = r0 - r10
            if (r7 >= r10) goto L_0x0185
            r10 = 1
            r11 = 0
        L_0x016d:
            byte[] r13 = PS_8BIM_RESO
            int r14 = r13.length
            if (r11 >= r14) goto L_0x017f
            int r14 = r7 + r11
            byte r14 = r6[r14]
            byte r13 = r13[r11]
            if (r14 == r13) goto L_0x017c
            r10 = 0
            goto L_0x017f
        L_0x017c:
            int r11 = r11 + 1
            goto L_0x016d
        L_0x017f:
            if (r10 == 0) goto L_0x0182
            goto L_0x0185
        L_0x0182:
            int r7 = r7 + 1
            goto L_0x0164
        L_0x0185:
            byte[] r10 = PS_8BIM_RESO
            int r11 = r10.length
            int r7 = r7 + r11
            int r10 = r10.length
            int r10 = r0 - r10
            if (r7 >= r10) goto L_0x029f
            byte r10 = r6[r7]
            int r11 = r10 + 1
            byte r10 = (byte) r11
            int r11 = r10 % 2
            r13 = 1
            if (r11 != r13) goto L_0x019b
            int r11 = r10 + 1
            byte r10 = (byte) r11
        L_0x019b:
            int r7 = r7 + r10
            byte r11 = r6[r7]
            int r11 = r11 << 24
            int r13 = r7 + 1
            byte r13 = r6[r13]
            int r13 = r13 << r12
            int r11 = r11 + r13
            int r13 = r7 + 2
            byte r13 = r6[r13]
            int r13 = r13 << r5
            int r11 = r11 + r13
            int r13 = r7 + 3
            byte r13 = r6[r13]
            int r11 = r11 + r13
            if (r11 == r12) goto L_0x01b7
            r22 = r4
            goto L_0x02a3
        L_0x01b7:
            int r7 = r7 + 4
            byte r12 = r6[r7]
            int r12 = r12 << r5
            int r13 = r7 + 1
            byte r13 = r6[r13]
            r14 = 255(0xff, float:3.57E-43)
            r13 = r13 & r14
            int r12 = r12 + r13
            r13 = 2
            int r7 = r7 + r13
            int r7 = r7 + r13
            byte r14 = r6[r7]
            int r14 = r14 << r5
            int r15 = r7 + 1
            byte r15 = r6[r15]
            r5 = 255(0xff, float:3.57E-43)
            r15 = r15 & r5
            int r14 = r14 + r15
            int r7 = r7 + r13
            int r7 = r7 + r13
            byte r5 = r6[r7]
            r15 = 8
            int r5 = r5 << r15
            int r15 = r7 + 1
            byte r15 = r6[r15]
            r13 = 255(0xff, float:3.57E-43)
            r15 = r15 & r13
            int r5 = r5 + r15
            r13 = 2
            int r7 = r7 + r13
            int r7 = r7 + r13
            byte r13 = r6[r7]
            r15 = 8
            int r13 = r13 << r15
            int r15 = r7 + 1
            byte r15 = r6[r15]
            r20 = r7
            r7 = 255(0xff, float:3.57E-43)
            r15 = r15 & r7
            int r13 = r13 + r15
            java.lang.Class<com.itextpdf.io.image.JpegImageHelper> r15 = com.itextpdf.p026io.image.JpegImageHelper.class
            r7 = 1
            if (r14 == r7) goto L_0x0201
            r7 = 2
            if (r14 != r7) goto L_0x01fc
            goto L_0x0202
        L_0x01fc:
            r21 = r0
            r22 = r4
            goto L_0x0252
        L_0x0201:
            r7 = 2
        L_0x0202:
            if (r14 != r7) goto L_0x0210
            float r7 = (float) r12
            r17 = 1076006748(0x40228f5c, float:2.54)
            float r7 = r7 * r17
            r18 = 1056964608(0x3f000000, float:0.5)
            float r7 = r7 + r18
            int r7 = (int) r7
            goto L_0x0211
        L_0x0210:
            r7 = r12
        L_0x0211:
            r12 = r7
            int r7 = r25.getDpiX()
            if (r7 == 0) goto L_0x0247
            int r7 = r25.getDpiX()
            if (r7 == r12) goto L_0x0247
            org.slf4j.Logger r7 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r15)
            r21 = r0
            r22 = r4
            r0 = 2
            java.lang.Object[] r4 = new java.lang.Object[r0]
            int r0 = r25.getDpiX()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r16 = 0
            r4[r16] = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r12)
            r19 = 1
            r4[r19] = r0
            java.lang.String r0 = "Inconsistent metadata (dpiX: {0} vs {1})"
            java.lang.String r0 = com.itextpdf.p026io.util.MessageFormatUtil.format(r0, r4)
            r7.debug(r0)
            goto L_0x0252
        L_0x0247:
            r21 = r0
            r22 = r4
            int r0 = r25.getDpiY()
            r2.setDpi(r12, r0)
        L_0x0252:
            r0 = 1
            if (r13 == r0) goto L_0x0259
            r0 = 2
            if (r13 != r0) goto L_0x029e
            goto L_0x025a
        L_0x0259:
            r0 = 2
        L_0x025a:
            if (r13 != r0) goto L_0x0267
            float r0 = (float) r5
            r4 = 1076006748(0x40228f5c, float:2.54)
            float r0 = r0 * r4
            r4 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r4
            int r0 = (int) r0
            goto L_0x0268
        L_0x0267:
            r0 = r5
        L_0x0268:
            int r4 = r25.getDpiY()
            if (r4 == 0) goto L_0x0297
            int r4 = r25.getDpiY()
            if (r4 == r0) goto L_0x0297
            org.slf4j.Logger r4 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r15)
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            int r7 = r25.getDpiY()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r15 = 0
            r5[r15] = r7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)
            r15 = 1
            r5[r15] = r7
            java.lang.String r7 = "Inconsistent metadata (dpiY: {0} vs {1})"
            java.lang.String r5 = com.itextpdf.p026io.util.MessageFormatUtil.format(r7, r5)
            r4.debug(r5)
            goto L_0x029e
        L_0x0297:
            int r4 = r25.getDpiX()
            r2.setDpi(r4, r12)
        L_0x029e:
            goto L_0x02a3
        L_0x029f:
            r21 = r0
            r22 = r4
        L_0x02a3:
            r4 = r22
            r0 = 0
            r6 = 1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x001a
        L_0x02ab:
            r22 = r4
            r0 = 14
            r4 = 0
            int r5 = marker(r9)
            if (r5 != 0) goto L_0x0330
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r10)
            int r6 = r23.read()
            r7 = 8
            if (r6 != r7) goto L_0x031d
            int r6 = getShort(r23)
            float r6 = (float) r6
            r2.setHeight(r6)
            int r6 = getShort(r23)
            float r6 = (float) r6
            r2.setWidth(r6)
            int r6 = r23.read()
            r2.setColorSpace(r6)
            r6 = 8
            r2.setBpc(r6)
            if (r3 == 0) goto L_0x031c
            r5 = 0
            r6 = 0
        L_0x02e2:
            int r7 = r3.length
            if (r6 >= r7) goto L_0x02f7
            r7 = r3[r6]
            if (r7 != 0) goto L_0x02ee
            r7 = 0
            r0 = r7
            byte[][] r0 = (byte[][]) r0
            return
        L_0x02ee:
            r7 = 0
            r8 = r3[r6]
            int r8 = r8.length
            int r8 = r8 - r0
            int r5 = r5 + r8
            int r6 = r6 + 1
            goto L_0x02e2
        L_0x02f7:
            byte[] r6 = new byte[r5]
            r5 = 0
            r7 = 0
        L_0x02fb:
            int r8 = r3.length
            if (r7 >= r8) goto L_0x030f
            r8 = r3[r7]
            r9 = r3[r7]
            int r9 = r9.length
            int r9 = r9 - r0
            java.lang.System.arraycopy(r8, r0, r6, r5, r9)
            r8 = r3[r7]
            int r8 = r8.length
            int r8 = r8 - r0
            int r5 = r5 + r8
            int r7 = r7 + 1
            goto L_0x02fb
        L_0x030f:
            int r0 = r25.getColorSpace()     // Catch:{ IllegalArgumentException -> 0x031b }
            com.itextpdf.io.colors.IccProfile r0 = com.itextpdf.p026io.colors.IccProfile.getInstance(r6, r0)     // Catch:{ IllegalArgumentException -> 0x031b }
            r2.setProfile(r0)     // Catch:{ IllegalArgumentException -> 0x031b }
            goto L_0x031c
        L_0x031b:
            r0 = move-exception
        L_0x031c:
            return
        L_0x031d:
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException
            java.lang.String r6 = "{0} must have 8 bits per component."
            r0.<init>((java.lang.String) r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = 0
            r6[r7] = r24
            com.itextpdf.io.IOException r0 = r0.setMessageParams(r6)
            throw r0
        L_0x0330:
            r6 = 1
            r7 = 0
            if (r5 == r6) goto L_0x0341
            r0 = 2
            if (r5 == r0) goto L_0x035e
            int r6 = getShort(r23)
            int r6 = r6 - r0
            long r10 = (long) r6
            com.itextpdf.p026io.util.StreamUtil.skip(r1, r10)
            goto L_0x035e
        L_0x0341:
            r0 = 2
            com.itextpdf.io.IOException r6 = new com.itextpdf.io.IOException
            java.lang.String r7 = "{0} unsupported jpeg marker {1}."
            r6.<init>((java.lang.String) r7)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r7 = 0
            r0[r7] = r24
            java.lang.String r7 = java.lang.Integer.toString(r9)
            r10 = 1
            r0[r10] = r7
            com.itextpdf.io.IOException r0 = r6.setMessageParams(r0)
            throw r0
        L_0x035b:
            r7 = r0
            r22 = r4
        L_0x035e:
            r0 = r7
            r6 = 1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x001a
        L_0x0364:
            r22 = r4
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException
            java.lang.String r4 = "Premature EOF while reading JPEG."
            r0.<init>((java.lang.String) r4)
            throw r0
        L_0x036e:
            com.itextpdf.io.IOException r0 = new com.itextpdf.io.IOException
            java.lang.String r4 = "{0} is not a valid jpeg file."
            r0.<init>((java.lang.String) r4)
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 0
            r4[r5] = r24
            com.itextpdf.io.IOException r0 = r0.setMessageParams(r4)
            goto L_0x0382
        L_0x0381:
            throw r0
        L_0x0382:
            goto L_0x0381
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.JpegImageHelper.processParameters(java.io.InputStream, java.lang.String, com.itextpdf.io.image.ImageData):void");
    }

    private static int getShort(InputStream jpegStream) throws IOException {
        return (jpegStream.read() << 8) + jpegStream.read();
    }

    private static int marker(int marker) {
        int i = 0;
        while (true) {
            int[] iArr = VALID_MARKERS;
            if (i >= iArr.length) {
                int i2 = 0;
                while (true) {
                    int[] iArr2 = NOPARAM_MARKERS;
                    if (i2 >= iArr2.length) {
                        int i3 = 0;
                        while (true) {
                            int[] iArr3 = UNSUPPORTED_MARKERS;
                            if (i3 >= iArr3.length) {
                                return -1;
                            }
                            if (marker == iArr3[i3]) {
                                return 1;
                            }
                            i3++;
                        }
                    } else if (marker == iArr2[i2]) {
                        return 2;
                    } else {
                        i2++;
                    }
                }
            } else if (marker == iArr[i]) {
                return 0;
            } else {
                i++;
            }
        }
    }
}

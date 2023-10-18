package com.itextpdf.p026io.image;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

/* renamed from: com.itextpdf.io.image.AwtImageDataFactory */
class AwtImageDataFactory {
    AwtImageDataFactory() {
    }

    public static ImageData create(Image image, Color color) throws IOException {
        return create(image, color, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:129:0x02ce  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x00d2 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.p026io.image.ImageData create(java.awt.Image r24, java.awt.Color r25, boolean r26) throws java.io.IOException {
        /*
            r8 = r24
            boolean r0 = r8 instanceof java.awt.image.BufferedImage
            r9 = 1
            if (r0 == 0) goto L_0x001f
            r0 = r8
            java.awt.image.BufferedImage r0 = (java.awt.image.BufferedImage) r0
            int r1 = r0.getType()
            r2 = 12
            if (r1 != r2) goto L_0x001f
            java.awt.image.ColorModel r1 = r0.getColorModel()
            int r1 = r1.getPixelSize()
            if (r1 != r9) goto L_0x001f
            r1 = 1
            r10 = r1
            goto L_0x0021
        L_0x001f:
            r10 = r26
        L_0x0021:
            java.awt.image.PixelGrabber r0 = new java.awt.image.PixelGrabber
            r3 = 0
            r4 = 0
            r5 = -1
            r6 = -1
            r7 = 1
            r1 = r0
            r2 = r24
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r1.grabPixels()     // Catch:{ InterruptedException -> 0x02d8 }
            int r0 = r1.getStatus()
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 != 0) goto L_0x02ce
            int r0 = r1.getWidth()
            int r11 = r1.getHeight()
            java.lang.Object r2 = r1.getPixels()
            int[] r2 = (int[]) r2
            r12 = r2
            int[] r12 = (int[]) r12
            r5 = 255(0xff, float:3.57E-43)
            if (r10 == 0) goto L_0x0145
            int r6 = r0 / 8
            r7 = r0 & 7
            if (r7 == 0) goto L_0x0057
            r7 = 1
            goto L_0x0058
        L_0x0057:
            r7 = 0
        L_0x0058:
            int r13 = r6 + r7
            int r6 = r13 * r11
            byte[] r14 = new byte[r6]
            r6 = 0
            int r15 = r11 * r0
            r7 = 1
            if (r25 == 0) goto L_0x007c
            int r16 = r25.getRed()
            int r17 = r25.getGreen()
            int r16 = r16 + r17
            int r17 = r25.getBlue()
            int r4 = r16 + r17
            r2 = 384(0x180, float:5.38E-43)
            if (r4 >= r2) goto L_0x007a
            r2 = 0
            goto L_0x007b
        L_0x007a:
            r2 = 1
        L_0x007b:
            r7 = r2
        L_0x007c:
            r2 = 0
            r4 = 128(0x80, float:1.794E-43)
            r17 = 0
            r18 = 0
            if (r25 == 0) goto L_0x00e0
            r16 = 0
            r9 = r16
        L_0x0089:
            if (r9 >= r15) goto L_0x00d9
            r16 = r12[r9]
            int r3 = r16 >> 24
            r3 = r3 & r5
            r5 = 250(0xfa, float:3.5E-43)
            if (r3 >= r5) goto L_0x009c
            r5 = 1
            if (r7 != r5) goto L_0x00a7
            r18 = r18 | r4
            r5 = r18
            goto L_0x00a9
        L_0x009c:
            r5 = r12[r9]
            r5 = r5 & 2184(0x888, float:3.06E-42)
            if (r5 == 0) goto L_0x00a7
            r18 = r18 | r4
            r5 = r18
            goto L_0x00a9
        L_0x00a7:
            r5 = r18
        L_0x00a9:
            int r4 = r4 >> 1
            if (r4 == 0) goto L_0x00b7
            r20 = r1
            int r1 = r17 + 1
            if (r1 < r0) goto L_0x00b4
            goto L_0x00b9
        L_0x00b4:
            r18 = r5
            goto L_0x00c8
        L_0x00b7:
            r20 = r1
        L_0x00b9:
            int r1 = r6 + 1
            r26 = r1
            byte r1 = (byte) r5
            r14[r6] = r1
            r1 = 128(0x80, float:1.794E-43)
            r4 = 0
            r6 = r26
            r18 = r4
            r4 = r1
        L_0x00c8:
            int r1 = r17 + 1
            if (r1 < r0) goto L_0x00d0
            r1 = 0
            r17 = r1
            goto L_0x00d2
        L_0x00d0:
            r17 = r1
        L_0x00d2:
            int r9 = r9 + 1
            r1 = r20
            r5 = 255(0xff, float:3.57E-43)
            goto L_0x0089
        L_0x00d9:
            r20 = r1
            r1 = r2
            r9 = r4
            r16 = r6
            goto L_0x0138
        L_0x00e0:
            r20 = r1
            r1 = 0
        L_0x00e3:
            if (r1 >= r15) goto L_0x0134
            if (r2 != 0) goto L_0x0103
            r3 = r12[r1]
            int r3 = r3 >> 24
            r5 = 255(0xff, float:3.57E-43)
            r3 = r3 & r5
            if (r3 != 0) goto L_0x0103
            r5 = 2
            int[] r2 = new int[r5]
            r5 = r12[r1]
            r5 = r5 & 2184(0x888, float:3.06E-42)
            if (r5 == 0) goto L_0x00fc
            r5 = 255(0xff, float:3.57E-43)
            goto L_0x00fd
        L_0x00fc:
            r5 = 0
        L_0x00fd:
            r9 = 1
            r2[r9] = r5
            r9 = 0
            r2[r9] = r5
        L_0x0103:
            r3 = r12[r1]
            r3 = r3 & 2184(0x888, float:3.06E-42)
            if (r3 == 0) goto L_0x010e
            r18 = r18 | r4
            r3 = r18
            goto L_0x0110
        L_0x010e:
            r3 = r18
        L_0x0110:
            int r4 = r4 >> 1
            if (r4 == 0) goto L_0x011c
            int r5 = r17 + 1
            if (r5 < r0) goto L_0x0119
            goto L_0x011c
        L_0x0119:
            r18 = r3
            goto L_0x0127
        L_0x011c:
            int r5 = r6 + 1
            byte r9 = (byte) r3
            r14[r6] = r9
            r4 = 128(0x80, float:1.794E-43)
            r3 = 0
            r18 = r3
            r6 = r5
        L_0x0127:
            int r3 = r17 + 1
            if (r3 < r0) goto L_0x012f
            r3 = 0
            r17 = r3
            goto L_0x0131
        L_0x012f:
            r17 = r3
        L_0x0131:
            int r1 = r1 + 1
            goto L_0x00e3
        L_0x0134:
            r1 = r2
            r9 = r4
            r16 = r6
        L_0x0138:
            r4 = 1
            r5 = 1
            r2 = r0
            r3 = r11
            r6 = r14
            r19 = r7
            r7 = r1
            com.itextpdf.io.image.ImageData r2 = com.itextpdf.p026io.image.ImageDataFactory.create(r2, r3, r4, r5, r6, r7)
            return r2
        L_0x0145:
            r20 = r1
            int r1 = r0 * r11
            r2 = 3
            int r1 = r1 * 3
            byte[] r1 = new byte[r1]
            r3 = 0
            r4 = 0
            int r9 = r11 * r0
            r5 = 255(0xff, float:3.57E-43)
            r6 = 255(0xff, float:3.57E-43)
            r7 = 255(0xff, float:3.57E-43)
            if (r25 == 0) goto L_0x016a
            int r5 = r25.getRed()
            int r6 = r25.getGreen()
            int r7 = r25.getBlue()
            r13 = r5
            r14 = r6
            r15 = r7
            goto L_0x016d
        L_0x016a:
            r13 = r5
            r14 = r6
            r15 = r7
        L_0x016d:
            r5 = 0
            if (r25 == 0) goto L_0x01c6
            r2 = 0
        L_0x0171:
            if (r2 >= r9) goto L_0x01bd
            r6 = r12[r2]
            int r6 = r6 >> 24
            r7 = 255(0xff, float:3.57E-43)
            r6 = r6 & r7
            r7 = 250(0xfa, float:3.5E-43)
            if (r6 >= r7) goto L_0x0193
            int r16 = r4 + 1
            byte r7 = (byte) r13
            r1[r4] = r7
            int r4 = r16 + 1
            byte r7 = (byte) r14
            r1[r16] = r7
            int r7 = r4 + 1
            r17 = r3
            byte r3 = (byte) r15
            r1[r4] = r3
            r18 = r5
            r4 = r7
            goto L_0x01b6
        L_0x0193:
            r17 = r3
            int r3 = r4 + 1
            r7 = r12[r2]
            int r7 = r7 >> 16
            r18 = r5
            r5 = 255(0xff, float:3.57E-43)
            r7 = r7 & r5
            byte r7 = (byte) r7
            r1[r4] = r7
            int r4 = r3 + 1
            r7 = r12[r2]
            int r7 = r7 >> 8
            r7 = r7 & r5
            byte r7 = (byte) r7
            r1[r3] = r7
            int r3 = r4 + 1
            r7 = r12[r2]
            r7 = r7 & r5
            byte r5 = (byte) r7
            r1[r4] = r5
            r4 = r3
        L_0x01b6:
            int r2 = r2 + 1
            r3 = r17
            r5 = r18
            goto L_0x0171
        L_0x01bd:
            r17 = r3
            r18 = r5
            r8 = r4
            r22 = r9
            goto L_0x02ad
        L_0x01c6:
            r17 = r3
            r18 = r5
            r3 = 0
            int r5 = r0 * r11
            byte[] r5 = new byte[r5]
            r6 = 0
            r7 = 0
        L_0x01d1:
            if (r7 >= r9) goto L_0x029c
            r17 = r12[r7]
            int r2 = r17 >> 24
            r8 = 255(0xff, float:3.57E-43)
            r2 = r2 & r8
            byte r2 = (byte) r2
            r5[r7] = r2
            if (r6 != 0) goto L_0x0268
            if (r2 == 0) goto L_0x01f1
            r8 = -1
            if (r2 == r8) goto L_0x01f1
            r6 = 1
            r21 = r5
            r22 = r9
            r16 = 2
            r19 = 1
            r23 = 0
            goto L_0x0272
        L_0x01f1:
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            if (r18 != 0) goto L_0x0244
            if (r2 != 0) goto L_0x0239
            r17 = r12[r7]
            r3 = r17 & r8
            r8 = 6
            int[] r8 = new int[r8]
            r21 = r5
            int r5 = r3 >> 16
            r22 = r9
            r9 = 255(0xff, float:3.57E-43)
            r5 = r5 & r9
            r19 = 1
            r8[r19] = r5
            r23 = 0
            r8[r23] = r5
            int r5 = r3 >> 8
            r5 = r5 & r9
            r9 = 3
            r8[r9] = r5
            r16 = 2
            r8[r16] = r5
            r5 = r3 & 255(0xff, float:3.57E-43)
            r18 = 5
            r8[r18] = r5
            r18 = 4
            r8[r18] = r5
            r5 = 0
        L_0x0225:
            if (r5 >= r7) goto L_0x0236
            r18 = r12[r5]
            r17 = 16777215(0xffffff, float:2.3509886E-38)
            r9 = r18 & r17
            if (r9 != r3) goto L_0x0232
            r6 = 1
            goto L_0x0236
        L_0x0232:
            int r5 = r5 + 1
            r9 = 3
            goto L_0x0225
        L_0x0236:
            r18 = r8
            goto L_0x0272
        L_0x0239:
            r21 = r5
            r22 = r9
            r16 = 2
            r19 = 1
            r23 = 0
            goto L_0x0272
        L_0x0244:
            r21 = r5
            r22 = r9
            r16 = 2
            r19 = 1
            r23 = 0
            r5 = r12[r7]
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            r5 = r5 & r8
            if (r5 == r3) goto L_0x025b
            if (r2 != 0) goto L_0x025b
            r5 = 1
            r6 = r5
            goto L_0x0272
        L_0x025b:
            r5 = r12[r7]
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            r5 = r5 & r8
            if (r5 != r3) goto L_0x0272
            if (r2 == 0) goto L_0x0272
            r5 = 1
            r6 = r5
            goto L_0x0272
        L_0x0268:
            r21 = r5
            r22 = r9
            r16 = 2
            r19 = 1
            r23 = 0
        L_0x0272:
            int r5 = r4 + 1
            r8 = r12[r7]
            int r8 = r8 >> 16
            r9 = 255(0xff, float:3.57E-43)
            r8 = r8 & r9
            byte r8 = (byte) r8
            r1[r4] = r8
            int r4 = r5 + 1
            r8 = r12[r7]
            int r8 = r8 >> 8
            r8 = r8 & r9
            byte r8 = (byte) r8
            r1[r5] = r8
            int r5 = r4 + 1
            r8 = r12[r7]
            r8 = r8 & r9
            byte r8 = (byte) r8
            r1[r4] = r8
            int r7 = r7 + 1
            r8 = r24
            r4 = r5
            r5 = r21
            r9 = r22
            r2 = 3
            goto L_0x01d1
        L_0x029c:
            r21 = r5
            r22 = r9
            if (r6 == 0) goto L_0x02a9
            r5 = 0
            r8 = r4
            r18 = r5
            r17 = r21
            goto L_0x02ad
        L_0x02a9:
            r2 = 0
            r17 = r2
            r8 = r4
        L_0x02ad:
            r4 = 3
            r5 = 8
            r2 = r0
            r3 = r11
            r6 = r1
            r7 = r18
            com.itextpdf.io.image.ImageData r9 = com.itextpdf.p026io.image.ImageDataFactory.create(r2, r3, r4, r5, r6, r7)
            if (r17 == 0) goto L_0x02cd
            r4 = 1
            r5 = 8
            r7 = 0
            r2 = r0
            r3 = r11
            r6 = r17
            com.itextpdf.io.image.ImageData r2 = com.itextpdf.p026io.image.ImageDataFactory.create(r2, r3, r4, r5, r6, r7)
            r2.makeMask()
            r9.setImageMask(r2)
        L_0x02cd:
            return r9
        L_0x02ce:
            r20 = r1
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Java.awt.image fetch aborted or errored"
            r0.<init>(r1)
            throw r0
        L_0x02d8:
            r0 = move-exception
            r20 = r1
            r1 = r0
            r0 = r1
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "Java.awt.image was interrupted. Waiting for pixels"
            r1.<init>(r2)
            goto L_0x02e6
        L_0x02e5:
            throw r1
        L_0x02e6:
            goto L_0x02e5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.AwtImageDataFactory.create(java.awt.Image, java.awt.Color, boolean):com.itextpdf.io.image.ImageData");
    }
}

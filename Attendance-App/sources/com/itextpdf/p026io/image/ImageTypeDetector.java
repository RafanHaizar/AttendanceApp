package com.itextpdf.p026io.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

/* renamed from: com.itextpdf.io.image.ImageTypeDetector */
public final class ImageTypeDetector {
    private static final byte[] bmp = {66, 77};
    private static final byte[] gif = {71, 73, 70};
    private static final byte[] jbig2 = {-105, 74, 66, 50, 13, 10, 26, 10};
    private static final byte[] jpeg = {-1, -40};
    private static final byte[] jpeg2000_1 = {0, 0, 0, 12};
    private static final byte[] jpeg2000_2 = {-1, 79, -1, 81};
    private static final byte[] png = {-119, 80, 78, 71};
    private static final byte[] tiff_1 = {77, 77, 0, 42};
    private static final byte[] tiff_2 = {73, 73, 42, 0};
    private static final byte[] wmf = {-41, -51};

    private ImageTypeDetector() {
    }

    public static ImageType detectImageType(byte[] source) {
        return detectImageTypeByHeader(readImageType(source));
    }

    public static ImageType detectImageType(URL source) {
        return detectImageTypeByHeader(readImageType(source));
    }

    private static ImageType detectImageTypeByHeader(byte[] header) {
        if (imageTypeIs(header, gif)) {
            return ImageType.GIF;
        }
        if (imageTypeIs(header, jpeg)) {
            return ImageType.JPEG;
        }
        if (imageTypeIs(header, jpeg2000_1) || imageTypeIs(header, jpeg2000_2)) {
            return ImageType.JPEG2000;
        }
        if (imageTypeIs(header, png)) {
            return ImageType.PNG;
        }
        if (imageTypeIs(header, bmp)) {
            return ImageType.BMP;
        }
        if (imageTypeIs(header, tiff_1) || imageTypeIs(header, tiff_2)) {
            return ImageType.TIFF;
        }
        if (imageTypeIs(header, jbig2)) {
            return ImageType.JBIG2;
        }
        if (imageTypeIs(header, wmf)) {
            return ImageType.WMF;
        }
        return ImageType.NONE;
    }

    private static boolean imageTypeIs(byte[] imageType, byte[] compareWith) {
        for (int i = 0; i < compareWith.length; i++) {
            if (imageType[i] != compareWith[i]) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        if (r0 != null) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x001f, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] readImageType(java.net.URL r4) {
        /*
            java.io.InputStream r0 = com.itextpdf.p026io.util.UrlUtil.openStream(r4)     // Catch:{ IOException -> 0x0020 }
            r1 = 8
            byte[] r1 = new byte[r1]     // Catch:{ all -> 0x0012 }
            r0.read(r1)     // Catch:{ all -> 0x0012 }
            if (r0 == 0) goto L_0x0011
            r0.close()     // Catch:{ IOException -> 0x0020 }
        L_0x0011:
            return r1
        L_0x0012:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0014:
            r2 = move-exception
            if (r0 == 0) goto L_0x001f
            r0.close()     // Catch:{ all -> 0x001b }
            goto L_0x001f
        L_0x001b:
            r3 = move-exception
            r1.addSuppressed(r3)     // Catch:{ IOException -> 0x0020 }
        L_0x001f:
            throw r2     // Catch:{ IOException -> 0x0020 }
        L_0x0020:
            r0 = move-exception
            com.itextpdf.io.IOException r1 = new com.itextpdf.io.IOException
            java.lang.String r2 = "I/O exception."
            r1.<init>((java.lang.String) r2, (java.lang.Throwable) r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.ImageTypeDetector.readImageType(java.net.URL):byte[]");
    }

    private static byte[] readImageType(byte[] source) {
        try {
            byte[] bytes = new byte[8];
            new ByteArrayInputStream(source).read(bytes);
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }
}

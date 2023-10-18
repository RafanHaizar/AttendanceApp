package com.itextpdf.p026io.image;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.colors.IccProfile;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.util.FilterUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.image.PngImageHelper */
class PngImageHelper {
    public static final String IDAT = "IDAT";
    public static final String IEND = "IEND";
    public static final String IHDR = "IHDR";
    public static final String PLTE = "PLTE";
    public static final int[] PNGID = {CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 80, 78, 71, 13, 10, 26, 10};
    private static final int PNG_FILTER_AVERAGE = 3;
    private static final int PNG_FILTER_NONE = 0;
    private static final int PNG_FILTER_PAETH = 4;
    private static final int PNG_FILTER_SUB = 1;
    private static final int PNG_FILTER_UP = 2;
    private static final int TRANSFERSIZE = 4096;
    public static final String cHRM = "cHRM";
    public static final String gAMA = "gAMA";
    public static final String iCCP = "iCCP";
    private static final String[] intents = {PngImageHelperConstants.PERCEPTUAL, PngImageHelperConstants.RELATIVE_COLORIMETRIC, PngImageHelperConstants.SATURATION, PngImageHelperConstants.ABSOLUTE_COLORMETRIC};
    public static final String pHYs = "pHYs";
    public static final String sRGB = "sRGB";
    public static final String tRNS = "tRNS";

    PngImageHelper() {
    }

    /* renamed from: com.itextpdf.io.image.PngImageHelper$PngParameters */
    private static class PngParameters {
        float XYRatio;
        Map<String, Object> additional = new HashMap();
        int bitDepth;
        int bytesPerPixel;
        int compressionMethod;
        InputStream dataStream;
        int dpiX;
        int dpiY;
        int filterMethod;
        boolean genBWMask;
        int height;
        IccProfile iccProfile;
        ByteArrayOutputStream idat = new ByteArrayOutputStream();
        PngImageData image;
        byte[] imageData;
        int inputBands;
        String intent;
        int interlaceMethod;
        boolean palShades;
        byte[] smask;
        byte[] trans;
        int transBlue = -1;
        int transGreen = -1;
        int transRedGray = -1;
        int width;

        PngParameters(PngImageData image2) {
            this.image = image2;
        }
    }

    public static void processImage(ImageData image) {
        if (image.getOriginalType() == ImageType.PNG) {
            InputStream pngStream = null;
            try {
                if (image.getData() == null) {
                    image.loadData();
                }
                InputStream pngStream2 = new ByteArrayInputStream(image.getData());
                image.imageSize = image.getData().length;
                PngParameters png = new PngParameters((PngImageData) image);
                processPng(pngStream2, png);
                try {
                    pngStream2.close();
                } catch (IOException e) {
                }
                RawImageHelper.updateImageAttributes(png.image, png.additional);
            } catch (IOException e2) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.PngImageException, (Throwable) e2);
            } catch (Throwable th) {
                if (pngStream != null) {
                    try {
                        pngStream.close();
                    } catch (IOException e3) {
                    }
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("PNG image expected");
        }
    }

    private static void processPng(InputStream pngStream, PngParameters png) throws IOException {
        int bpc;
        PngParameters pngParameters = png;
        readPng(pngStream, png);
        int colorType = pngParameters.image.getColorType();
        if (!(pngParameters.iccProfile == null || pngParameters.iccProfile.getNumComponents() == getExpectedNumberOfColorComponents(png))) {
            LoggerFactory.getLogger((Class<?>) PngImageHelper.class).warn(LogMessageConstant.f1195xe55b147c);
        }
        int pal0 = 0;
        int palIdx = 0;
        boolean needDecode = false;
        try {
            pngParameters.palShades = false;
            int i = 1;
            if (pngParameters.trans != null) {
                int k = 0;
                while (true) {
                    if (k < pngParameters.trans.length) {
                        int n = pngParameters.trans[k] & 255;
                        if (n == 0) {
                            pal0++;
                            palIdx = k;
                        }
                        if (n != 0 && n != 255) {
                            pngParameters.palShades = true;
                            break;
                        }
                        k++;
                    } else {
                        break;
                    }
                }
            }
            if ((colorType & 4) != 0) {
                pngParameters.palShades = true;
            }
            pngParameters.genBWMask = !pngParameters.palShades && (pal0 > 1 || pngParameters.transRedGray >= 0);
            if (!pngParameters.palShades && !pngParameters.genBWMask && pal0 == 1) {
                pngParameters.additional.put(PngImageHelperConstants.MASK, new int[]{palIdx, palIdx});
            }
            if (pngParameters.interlaceMethod == 1 || pngParameters.bitDepth == 16 || (colorType & 4) != 0 || pngParameters.palShades || pngParameters.genBWMask) {
                needDecode = true;
            }
            switch (colorType) {
                case 0:
                    pngParameters.inputBands = 1;
                    break;
                case 2:
                    pngParameters.inputBands = 3;
                    break;
                case 3:
                    pngParameters.inputBands = 1;
                    break;
                case 4:
                    pngParameters.inputBands = 2;
                    break;
                case 6:
                    pngParameters.inputBands = 4;
                    break;
            }
            if (needDecode) {
                decodeIdat(png);
            }
            int components = pngParameters.inputBands;
            if ((colorType & 4) != 0) {
                components--;
            }
            int bpc2 = pngParameters.bitDepth;
            if (bpc2 == 16) {
                bpc = 8;
            } else {
                bpc = bpc2;
            }
            if (pngParameters.imageData == null) {
                RawImageHelper.updateRawImageParameters(pngParameters.image, pngParameters.width, pngParameters.height, components, bpc, pngParameters.idat.toByteArray());
                pngParameters.image.setDeflated(true);
                Map<String, Object> decodeparms = new HashMap<>();
                decodeparms.put(PngImageHelperConstants.BITS_PER_COMPONENT, Integer.valueOf(pngParameters.bitDepth));
                decodeparms.put(PngImageHelperConstants.PREDICTOR, 15);
                decodeparms.put(PngImageHelperConstants.COLUMNS, Integer.valueOf(pngParameters.width));
                if (!pngParameters.image.isIndexed()) {
                    if (!pngParameters.image.isGrayscaleImage()) {
                        i = 3;
                    }
                }
                decodeparms.put(PngImageHelperConstants.COLORS, Integer.valueOf(i));
                pngParameters.image.decodeParms = decodeparms;
            } else if (pngParameters.image.isIndexed()) {
                RawImageHelper.updateRawImageParameters(pngParameters.image, pngParameters.width, pngParameters.height, components, bpc, pngParameters.imageData);
            } else {
                RawImageHelper.updateRawImageParameters(pngParameters.image, pngParameters.width, pngParameters.height, components, bpc, pngParameters.imageData, (int[]) null);
            }
            if (pngParameters.intent != null) {
                pngParameters.additional.put(PngImageHelperConstants.INTENT, pngParameters.intent);
            }
            if (pngParameters.iccProfile != null) {
                pngParameters.image.setProfile(pngParameters.iccProfile);
            }
            if (pngParameters.palShades) {
                RawImageData im2 = (RawImageData) ImageDataFactory.createRawImage((byte[]) null);
                RawImageHelper.updateRawImageParameters(im2, pngParameters.width, pngParameters.height, 1, 8, pngParameters.smask);
                im2.makeMask();
                pngParameters.image.setImageMask(im2);
            }
            if (pngParameters.genBWMask) {
                RawImageData im22 = (RawImageData) ImageDataFactory.createRawImage((byte[]) null);
                RawImageHelper.updateRawImageParameters(im22, pngParameters.width, pngParameters.height, 1, 1, pngParameters.smask);
                im22.makeMask();
                pngParameters.image.setImageMask(im22);
            }
            pngParameters.image.setDpi(pngParameters.dpiX, pngParameters.dpiY);
            pngParameters.image.setXYRatio(pngParameters.XYRatio);
        } catch (Exception e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.PngImageException, (Throwable) e);
        }
    }

    private static int getExpectedNumberOfColorComponents(PngParameters png) {
        return png.image.isGrayscaleImage() ? 1 : 3;
    }

    private static void readPng(InputStream pngStream, PngParameters png) throws IOException {
        InputStream inputStream = pngStream;
        PngParameters pngParameters = png;
        int i = 0;
        while (true) {
            int[] iArr = PNGID;
            if (i >= iArr.length) {
                byte[] buffer = new byte[4096];
                while (true) {
                    int len = getInt(pngStream);
                    String marker = getString(pngStream);
                    if (len >= 0 && checkMarker(marker)) {
                        if (IDAT.equals(marker)) {
                            while (len != 0) {
                                int size = inputStream.read(buffer, 0, Math.min(len, 4096));
                                if (size >= 0) {
                                    pngParameters.idat.write(buffer, 0, size);
                                    len -= size;
                                } else {
                                    return;
                                }
                            }
                            continue;
                        } else if (tRNS.equals(marker)) {
                            switch (pngParameters.image.getColorType()) {
                                case 0:
                                    if (len >= 2) {
                                        len -= 2;
                                        int gray = getWord(pngStream);
                                        if (pngParameters.bitDepth != 16) {
                                            pngParameters.additional.put(PngImageHelperConstants.MASK, MessageFormatUtil.format("[{0} {1}]", Integer.valueOf(gray), Integer.valueOf(gray)));
                                            break;
                                        } else {
                                            pngParameters.transRedGray = gray;
                                            break;
                                        }
                                    }
                                    break;
                                case 2:
                                    if (len >= 6) {
                                        len -= 6;
                                        int red = getWord(pngStream);
                                        int green = getWord(pngStream);
                                        int blue = getWord(pngStream);
                                        if (pngParameters.bitDepth != 16) {
                                            pngParameters.additional.put(PngImageHelperConstants.MASK, MessageFormatUtil.format("[{0} {1} {2} {3} {4} {5}]", Integer.valueOf(red), Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(blue)));
                                            break;
                                        } else {
                                            pngParameters.transRedGray = red;
                                            pngParameters.transGreen = green;
                                            pngParameters.transBlue = blue;
                                            break;
                                        }
                                    }
                                    break;
                                case 3:
                                    if (len > 0) {
                                        pngParameters.trans = new byte[len];
                                        for (int k = 0; k < len; k++) {
                                            pngParameters.trans[k] = (byte) pngStream.read();
                                        }
                                        len = 0;
                                        break;
                                    }
                                    break;
                            }
                            StreamUtil.skip(inputStream, (long) len);
                        } else if (IHDR.equals(marker)) {
                            pngParameters.width = getInt(pngStream);
                            pngParameters.height = getInt(pngStream);
                            pngParameters.bitDepth = pngStream.read();
                            pngParameters.image.setColorType(pngStream.read());
                            pngParameters.compressionMethod = pngStream.read();
                            pngParameters.filterMethod = pngStream.read();
                            pngParameters.interlaceMethod = pngStream.read();
                        } else if (PLTE.equals(marker)) {
                            if (pngParameters.image.isIndexed()) {
                                ByteBuffer colorTableBuf = new ByteBuffer();
                                while (true) {
                                    int len2 = len - 1;
                                    if (len > 0) {
                                        colorTableBuf.append(pngStream.read());
                                        len = len2;
                                    } else {
                                        pngParameters.image.setColorPalette(colorTableBuf.toByteArray());
                                        int i2 = len2;
                                    }
                                }
                            } else {
                                StreamUtil.skip(inputStream, (long) len);
                            }
                        } else if (pHYs.equals(marker)) {
                            int dx = getInt(pngStream);
                            int dy = getInt(pngStream);
                            if (pngStream.read() == 1) {
                                pngParameters.dpiX = (int) ((((float) dx) * 0.0254f) + 0.5f);
                                pngParameters.dpiY = (int) ((((float) dy) * 0.0254f) + 0.5f);
                            } else if (dy != 0) {
                                pngParameters.XYRatio = ((float) dx) / ((float) dy);
                            }
                        } else if (cHRM.equals(marker)) {
                            PngChromaticities pngChromaticities = new PngChromaticities(((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f, ((float) getInt(pngStream)) / 100000.0f);
                            if (Math.abs(pngChromaticities.getXW()) >= 1.0E-4f && Math.abs(pngChromaticities.getYW()) >= 1.0E-4f && Math.abs(pngChromaticities.getXR()) >= 1.0E-4f && Math.abs(pngChromaticities.getYR()) >= 1.0E-4f && Math.abs(pngChromaticities.getXG()) >= 1.0E-4f && Math.abs(pngChromaticities.getYG()) >= 1.0E-4f && Math.abs(pngChromaticities.getXB()) >= 1.0E-4f && Math.abs(pngChromaticities.getYB()) >= 1.0E-4f) {
                                pngParameters.image.setPngChromaticities(pngChromaticities);
                            }
                        } else if (sRGB.equals(marker)) {
                            pngParameters.intent = intents[pngStream.read()];
                            pngParameters.image.setGamma(2.2f);
                            pngParameters.image.setPngChromaticities(new PngChromaticities(0.3127f, 0.329f, 0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f));
                        } else if (gAMA.equals(marker)) {
                            int gm = getInt(pngStream);
                            if (gm != 0) {
                                pngParameters.image.setGamma(100000.0f / ((float) gm));
                                if (!pngParameters.image.isHasCHRM()) {
                                    pngParameters.image.setPngChromaticities(new PngChromaticities(0.3127f, 0.329f, 0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f));
                                }
                            }
                        } else if (iCCP.equals(marker)) {
                            do {
                                len--;
                            } while (pngStream.read() != 0);
                            pngStream.read();
                            int len3 = len - 1;
                            byte[] icccom = new byte[len3];
                            int p = 0;
                            int len4 = len3;
                            while (len4 > 0) {
                                int r = inputStream.read(icccom, p, len4);
                                if (r >= 0) {
                                    p += r;
                                    len4 -= r;
                                } else {
                                    throw new IOException("premature.end.of.file");
                                }
                            }
                            try {
                                pngParameters.iccProfile = IccProfile.getInstance(FilterUtil.flateDecode(icccom, true));
                            } catch (RuntimeException e) {
                                pngParameters.iccProfile = null;
                            }
                            int i3 = len4;
                        } else if (!IEND.equals(marker)) {
                            StreamUtil.skip(inputStream, (long) len);
                        } else {
                            return;
                        }
                        StreamUtil.skip(inputStream, 4);
                    }
                }
                throw new IOException("corrupted.png.file");
            } else if (iArr[i] == pngStream.read()) {
                i++;
            } else {
                throw new IOException("file.is.not.a.valid.png");
            }
        }
    }

    private static boolean checkMarker(String s) {
        if (s.length() != 4) {
            return false;
        }
        for (int k = 0; k < 4; k++) {
            char c = s.charAt(k);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    private static void decodeIdat(PngParameters png) {
        int nbitDepth;
        int size;
        int nbitDepth2 = png.bitDepth;
        if (nbitDepth2 == 16) {
            nbitDepth = 8;
        } else {
            nbitDepth = nbitDepth2;
        }
        int size2 = -1;
        png.bytesPerPixel = png.bitDepth == 16 ? 2 : 1;
        switch (png.image.getColorType()) {
            case 0:
                size = (((png.width * nbitDepth) + 7) / 8) * png.height;
                break;
            case 2:
                int size3 = png.width * 3 * png.height;
                png.bytesPerPixel *= 3;
                size = size3;
                break;
            case 3:
                if (png.interlaceMethod == 1) {
                    size2 = (((png.width * nbitDepth) + 7) / 8) * png.height;
                }
                png.bytesPerPixel = 1;
                size = size2;
                break;
            case 4:
                int size4 = png.width * png.height;
                png.bytesPerPixel *= 2;
                size = size4;
                break;
            case 6:
                int size5 = png.width * 3 * png.height;
                png.bytesPerPixel *= 4;
                size = size5;
                break;
            default:
                size = -1;
                break;
        }
        if (size >= 0) {
            png.imageData = new byte[size];
        }
        if (png.palShades) {
            png.smask = new byte[(png.width * png.height)];
        } else if (png.genBWMask) {
            png.smask = new byte[(((png.width + 7) / 8) * png.height)];
        }
        png.dataStream = FilterUtil.getInflaterInputStream(new ByteArrayInputStream(png.idat.toByteArray()));
        if (png.interlaceMethod != 1) {
            decodePass(0, 0, 1, 1, png.width, png.height, png);
            return;
        }
        PngParameters pngParameters = png;
        decodePass(0, 0, 8, 8, (png.width + 7) / 8, (png.height + 7) / 8, pngParameters);
        decodePass(4, 0, 8, 8, (png.width + 3) / 8, (png.height + 7) / 8, pngParameters);
        decodePass(0, 4, 4, 8, (png.width + 3) / 4, (png.height + 3) / 8, pngParameters);
        decodePass(2, 0, 4, 4, (png.width + 1) / 4, (png.height + 3) / 4, pngParameters);
        decodePass(0, 2, 2, 4, (png.width + 1) / 2, (png.height + 1) / 4, pngParameters);
        decodePass(1, 0, 2, 2, png.width / 2, (png.height + 1) / 2, pngParameters);
        decodePass(0, 1, 1, 2, png.width, png.height / 2, pngParameters);
    }

    private static void decodePass(int xOffset, int yOffset, int xStep, int yStep, int passWidth, int passHeight, PngParameters png) {
        int filter;
        int i = passHeight;
        PngParameters pngParameters = png;
        if (passWidth != 0 && i != 0) {
            int bytesPerRow = (((pngParameters.inputBands * passWidth) * pngParameters.bitDepth) + 7) / 8;
            byte[] curr = new byte[bytesPerRow];
            byte[] prior = new byte[bytesPerRow];
            int srcY = 0;
            int dstY = yOffset;
            while (srcY < i) {
                int filter2 = 0;
                try {
                    filter2 = pngParameters.dataStream.read();
                    StreamUtil.readFully(pngParameters.dataStream, curr, 0, bytesPerRow);
                    filter = filter2;
                } catch (Exception e) {
                    filter = filter2;
                }
                switch (filter) {
                    case 0:
                        break;
                    case 1:
                        decodeSubFilter(curr, bytesPerRow, pngParameters.bytesPerPixel);
                        break;
                    case 2:
                        decodeUpFilter(curr, prior, bytesPerRow);
                        break;
                    case 3:
                        decodeAverageFilter(curr, prior, bytesPerRow, pngParameters.bytesPerPixel);
                        break;
                    case 4:
                        decodePaethFilter(curr, prior, bytesPerRow, pngParameters.bytesPerPixel);
                        break;
                    default:
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.UnknownPngFilter);
                }
                processPixels(curr, xOffset, xStep, dstY, passWidth, png);
                byte[] tmp = prior;
                prior = curr;
                curr = tmp;
                srcY++;
                dstY += yStep;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r10v8, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r10v9, types: [byte] */
    private static void processPixels(byte[] curr, int xOffset, int step, int y, int width, PngParameters png) {
        int sizes;
        int i = width;
        PngParameters pngParameters = png;
        int colorType = pngParameters.image.getColorType();
        int[] outPixel = getPixel(curr, pngParameters);
        switch (colorType) {
            case 0:
            case 3:
            case 4:
                sizes = 1;
                break;
            case 2:
            case 6:
                sizes = 3;
                break;
            default:
                sizes = 0;
                break;
        }
        if (pngParameters.imageData != null) {
            int dstX = xOffset;
            int yStride = (((pngParameters.width * sizes) * (pngParameters.bitDepth == 16 ? 8 : pngParameters.bitDepth)) + 7) / 8;
            int dstX2 = dstX;
            int srcX = 0;
            while (srcX < i) {
                setPixel(pngParameters.imageData, outPixel, pngParameters.inputBands * srcX, sizes, dstX2, y, pngParameters.bitDepth, yStride);
                dstX2 += step;
                srcX++;
            }
            int i2 = srcX;
        }
        if (pngParameters.palShades) {
            if ((colorType & 4) != 0) {
                if (pngParameters.bitDepth == 16) {
                    for (int k = 0; k < i; k++) {
                        int i3 = (pngParameters.inputBands * k) + sizes;
                        outPixel[i3] = outPixel[i3] >>> 8;
                    }
                }
                int yStride2 = pngParameters.width;
                int dstX3 = xOffset;
                int srcX2 = 0;
                while (srcX2 < i) {
                    setPixel(pngParameters.smask, outPixel, (pngParameters.inputBands * srcX2) + sizes, 1, dstX3, y, 8, yStride2);
                    dstX3 += step;
                    srcX2++;
                }
                int i4 = srcX2;
                return;
            }
            int yStride3 = pngParameters.width;
            int[] v = new int[1];
            int dstX4 = xOffset;
            for (int srcX3 = 0; srcX3 < i; srcX3++) {
                int idx = outPixel[srcX3];
                if (idx < pngParameters.trans.length) {
                    v[0] = pngParameters.trans[idx];
                } else {
                    v[0] = 255;
                }
                setPixel(pngParameters.smask, v, 0, 1, dstX4, y, 8, yStride3);
                dstX4 += step;
            }
        } else if (pngParameters.genBWMask) {
            switch (colorType) {
                case 0:
                    int yStride4 = (pngParameters.width + 7) / 8;
                    int[] v2 = new int[1];
                    int dstX5 = xOffset;
                    for (int srcX4 = 0; srcX4 < i; srcX4++) {
                        v2[0] = outPixel[srcX4] == pngParameters.transRedGray ? 1 : 0;
                        setPixel(pngParameters.smask, v2, 0, 1, dstX5, y, 1, yStride4);
                        dstX5 += step;
                    }
                    return;
                case 2:
                    int yStride5 = (pngParameters.width + 7) / 8;
                    int[] v3 = new int[1];
                    int dstX6 = xOffset;
                    for (int srcX5 = 0; srcX5 < i; srcX5++) {
                        int markRed = pngParameters.inputBands * srcX5;
                        v3[0] = (outPixel[markRed] == pngParameters.transRedGray && outPixel[markRed + 1] == pngParameters.transGreen && outPixel[markRed + 2] == pngParameters.transBlue) ? 1 : 0;
                        setPixel(pngParameters.smask, v3, 0, 1, dstX6, y, 1, yStride5);
                        dstX6 += step;
                    }
                    return;
                case 3:
                    int yStride6 = (pngParameters.width + 7) / 8;
                    int[] v4 = new int[1];
                    int dstX7 = xOffset;
                    for (int srcX6 = 0; srcX6 < i; srcX6++) {
                        int idx2 = outPixel[srcX6];
                        v4[0] = (idx2 >= pngParameters.trans.length || pngParameters.trans[idx2] != 0) ? 0 : 1;
                        setPixel(pngParameters.smask, v4, 0, 1, dstX7, y, 1, yStride6);
                        dstX7 += step;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private static int getPixel(byte[] image, int x, int y, int bitDepth, int bytesPerRow) {
        if (bitDepth == 8) {
            return image[(bytesPerRow * y) + x] & UByte.MAX_VALUE;
        }
        return (image[(bytesPerRow * y) + (x / (8 / bitDepth))] >> ((8 - ((x % (8 / bitDepth)) * bitDepth)) - bitDepth)) & ((1 << bitDepth) - 1);
    }

    static void setPixel(byte[] image, int[] data, int offset, int size, int x, int y, int bitDepth, int bytesPerRow) {
        if (bitDepth == 8) {
            int pos = (bytesPerRow * y) + (size * x);
            for (int k = 0; k < size; k++) {
                image[pos + k] = (byte) data[k + offset];
            }
        } else if (bitDepth == 16) {
            int pos2 = (bytesPerRow * y) + (size * x);
            for (int k2 = 0; k2 < size; k2++) {
                image[pos2 + k2] = (byte) (data[k2 + offset] >>> 8);
            }
        } else {
            int pos3 = (bytesPerRow * y) + (x / (8 / bitDepth));
            image[pos3] = (byte) (image[pos3] | ((byte) (data[offset] << ((8 - ((x % (8 / bitDepth)) * bitDepth)) - bitDepth))));
        }
    }

    private static int[] getPixel(byte[] curr, PngParameters png) {
        switch (png.bitDepth) {
            case 8:
                int[] res = new int[curr.length];
                for (int k = 0; k < res.length; k++) {
                    res[k] = curr[k] & UByte.MAX_VALUE;
                }
                return res;
            case 16:
                int[] res2 = new int[(curr.length / 2)];
                for (int k2 = 0; k2 < res2.length; k2++) {
                    res2[k2] = ((curr[k2 * 2] & UByte.MAX_VALUE) << 8) + (curr[(k2 * 2) + 1] & UByte.MAX_VALUE);
                }
                return res2;
            default:
                int[] res3 = new int[((curr.length * 8) / png.bitDepth)];
                int idx = 0;
                int passes = 8 / png.bitDepth;
                int mask = (1 << png.bitDepth) - 1;
                for (int k3 = 0; k3 < curr.length; k3++) {
                    int j = passes - 1;
                    while (j >= 0) {
                        res3[idx] = (curr[k3] >>> (png.bitDepth * j)) & mask;
                        j--;
                        idx++;
                    }
                }
                return res3;
        }
    }

    private static void decodeSubFilter(byte[] curr, int count, int bpp) {
        for (int i = bpp; i < count; i++) {
            curr[i] = (byte) ((curr[i] & 255) + (curr[i - bpp] & 255));
        }
    }

    private static void decodeUpFilter(byte[] curr, byte[] prev, int count) {
        for (int i = 0; i < count; i++) {
            curr[i] = (byte) ((curr[i] & 255) + (prev[i] & 255));
        }
    }

    private static void decodeAverageFilter(byte[] curr, byte[] prev, int count, int bpp) {
        for (int i = 0; i < bpp; i++) {
            curr[i] = (byte) (((prev[i] & 255) / 2) + (curr[i] & 255));
        }
        for (int i2 = bpp; i2 < count; i2++) {
            curr[i2] = (byte) ((((curr[i2 - bpp] & 255) + (prev[i2] & 255)) / 2) + (curr[i2] & 255));
        }
    }

    private static int paethPredictor(int a, int b, int c) {
        int p = (a + b) - c;
        int pa = Math.abs(p - a);
        int pb = Math.abs(p - b);
        int pc = Math.abs(p - c);
        if (pa <= pb && pa <= pc) {
            return a;
        }
        if (pb <= pc) {
            return b;
        }
        return c;
    }

    private static void decodePaethFilter(byte[] curr, byte[] prev, int count, int bpp) {
        for (int i = 0; i < bpp; i++) {
            curr[i] = (byte) ((curr[i] & 255) + (prev[i] & 255));
        }
        for (int i2 = bpp; i2 < count; i2++) {
            curr[i2] = (byte) (paethPredictor(curr[i2 - bpp] & 255, prev[i2] & 255, prev[i2 - bpp] & 255) + (curr[i2] & 255));
        }
    }

    public static int getInt(InputStream pngStream) throws IOException {
        return (pngStream.read() << 24) + (pngStream.read() << 16) + (pngStream.read() << 8) + pngStream.read();
    }

    public static int getWord(InputStream pngStream) throws IOException {
        return (pngStream.read() << 8) + pngStream.read();
    }

    public static String getString(InputStream pngStream) throws IOException {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            buf.append((char) pngStream.read());
        }
        return buf.toString();
    }
}

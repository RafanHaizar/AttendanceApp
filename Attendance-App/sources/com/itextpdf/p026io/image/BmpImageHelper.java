package com.itextpdf.p026io.image;

import com.google.android.material.internal.ViewUtils;
import com.itextpdf.p026io.font.PdfEncodings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.image.BmpImageHelper */
final class BmpImageHelper {
    private static final int BI_BITFIELDS = 3;
    private static final int BI_RGB = 0;
    private static final int BI_RLE4 = 2;
    private static final int BI_RLE8 = 1;
    private static final int LCS_CALIBRATED_RGB = 0;
    private static final int LCS_CMYK = 2;
    private static final int LCS_SRGB = 1;
    private static final int VERSION_2_1_BIT = 0;
    private static final int VERSION_2_24_BIT = 3;
    private static final int VERSION_2_4_BIT = 1;
    private static final int VERSION_2_8_BIT = 2;
    private static final int VERSION_3_1_BIT = 4;
    private static final int VERSION_3_24_BIT = 7;
    private static final int VERSION_3_4_BIT = 5;
    private static final int VERSION_3_8_BIT = 6;
    private static final int VERSION_3_NT_16_BIT = 8;
    private static final int VERSION_3_NT_32_BIT = 9;
    private static final int VERSION_4_16_BIT = 13;
    private static final int VERSION_4_1_BIT = 10;
    private static final int VERSION_4_24_BIT = 14;
    private static final int VERSION_4_32_BIT = 15;
    private static final int VERSION_4_4_BIT = 11;
    private static final int VERSION_4_8_BIT = 12;

    BmpImageHelper() {
    }

    /* renamed from: com.itextpdf.io.image.BmpImageHelper$BmpParameters */
    private static class BmpParameters {
        Map<String, Object> additional;
        int alphaMask;
        long bitmapFileSize;
        long bitmapOffset;
        int bitsPerPixel;
        int blueMask;
        long compression;
        int greenMask;
        int height;
        BmpImageData image;
        long imageSize;
        int imageType;
        InputStream inputStream;
        boolean isBottomUp;
        int numBands;
        byte[] palette;
        Map<String, Object> properties = new HashMap();
        int redMask;
        int width;
        long xPelsPerMeter;
        long yPelsPerMeter;

        public BmpParameters(BmpImageData image2) {
            this.image = image2;
        }
    }

    public static void processImage(ImageData image) {
        if (image.getOriginalType() == ImageType.BMP) {
            try {
                if (image.getData() == null) {
                    image.loadData();
                }
                InputStream bmpStream = new ByteArrayInputStream(image.getData());
                image.imageSize = image.getData().length;
                BmpParameters bmp = new BmpParameters((BmpImageData) image);
                process(bmp, bmpStream);
                if (getImage(bmp)) {
                    image.setWidth((float) bmp.width);
                    image.setHeight((float) bmp.height);
                    double d = (double) bmp.xPelsPerMeter;
                    Double.isNaN(d);
                    int i = (int) ((d * 0.0254d) + 0.5d);
                    double d2 = (double) bmp.yPelsPerMeter;
                    Double.isNaN(d2);
                    image.setDpi(i, (int) ((d2 * 0.0254d) + 0.5d));
                }
                RawImageHelper.updateImageAttributes(bmp.image, bmp.additional);
            } catch (IOException e) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BmpImageException, (Throwable) e);
            }
        } else {
            throw new IllegalArgumentException("BMP image expected");
        }
    }

    private static void process(BmpParameters bmp, InputStream stream) throws IOException {
        int i;
        BmpParameters bmpParameters = bmp;
        bmpParameters.inputStream = stream;
        if (!bmpParameters.image.isNoHeader()) {
            if (readUnsignedByte(bmpParameters.inputStream) == 66 && readUnsignedByte(bmpParameters.inputStream) == 77) {
                bmpParameters.bitmapFileSize = readDWord(bmpParameters.inputStream);
                readWord(bmpParameters.inputStream);
                readWord(bmpParameters.inputStream);
                bmpParameters.bitmapOffset = readDWord(bmpParameters.inputStream);
            } else {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidMagicValueForBmpFileMustBeBM);
            }
        }
        long size = readDWord(bmpParameters.inputStream);
        if (size == 12) {
            bmpParameters.width = readWord(bmpParameters.inputStream);
            bmpParameters.height = readWord(bmpParameters.inputStream);
        } else {
            bmpParameters.width = readLong(bmpParameters.inputStream);
            bmpParameters.height = readLong(bmpParameters.inputStream);
        }
        int planes = readWord(bmpParameters.inputStream);
        bmpParameters.bitsPerPixel = readWord(bmpParameters.inputStream);
        bmpParameters.properties.put("color_planes", Integer.valueOf(planes));
        bmpParameters.properties.put("bits_per_pixel", Integer.valueOf(bmpParameters.bitsPerPixel));
        bmpParameters.numBands = 3;
        if (bmpParameters.bitmapOffset == 0) {
            bmpParameters.bitmapOffset = size;
        }
        if (size == 12) {
            bmpParameters.properties.put("bmp_version", "BMP v. 2.x");
            if (bmpParameters.bitsPerPixel == 1) {
                bmpParameters.imageType = 0;
            } else if (bmpParameters.bitsPerPixel == 4) {
                bmpParameters.imageType = 1;
            } else if (bmpParameters.bitsPerPixel == 8) {
                bmpParameters.imageType = 2;
            } else if (bmpParameters.bitsPerPixel == 24) {
                bmpParameters.imageType = 3;
            }
            int sizeOfPalette = ((int) (((bmpParameters.bitmapOffset - 14) - size) / 3)) * 3;
            if (bmpParameters.bitmapOffset == size) {
                switch (bmpParameters.imageType) {
                    case 0:
                        sizeOfPalette = 6;
                        break;
                    case 1:
                        sizeOfPalette = 48;
                        break;
                    case 2:
                        sizeOfPalette = ViewUtils.EDGE_TO_EDGE_FLAGS;
                        break;
                    case 3:
                        sizeOfPalette = 0;
                        break;
                }
                bmpParameters.bitmapOffset = ((long) sizeOfPalette) + size;
            }
            readPalette(sizeOfPalette, bmpParameters);
            int i2 = planes;
        } else {
            bmpParameters.compression = readDWord(bmpParameters.inputStream);
            bmpParameters.imageSize = readDWord(bmpParameters.inputStream);
            bmpParameters.xPelsPerMeter = (long) readLong(bmpParameters.inputStream);
            bmpParameters.yPelsPerMeter = (long) readLong(bmpParameters.inputStream);
            long colorsUsed = readDWord(bmpParameters.inputStream);
            long colorsImportant = readDWord(bmpParameters.inputStream);
            switch ((int) bmpParameters.compression) {
                case 0:
                    bmpParameters.properties.put("compression", "BI_RGB");
                    break;
                case 1:
                    bmpParameters.properties.put("compression", "BI_RLE8");
                    break;
                case 2:
                    bmpParameters.properties.put("compression", "BI_RLE4");
                    break;
                case 3:
                    bmpParameters.properties.put("compression", "BI_BITFIELDS");
                    break;
            }
            bmpParameters.properties.put("x_pixels_per_meter", Long.valueOf(bmpParameters.xPelsPerMeter));
            bmpParameters.properties.put("y_pixels_per_meter", Long.valueOf(bmpParameters.yPelsPerMeter));
            bmpParameters.properties.put("colors_used", Long.valueOf(colorsUsed));
            bmpParameters.properties.put("colors_important", Long.valueOf(colorsImportant));
            if (size == 40 || size == 52) {
                long j = colorsImportant;
            } else if (size == 56) {
                int i3 = planes;
                long j2 = colorsImportant;
            } else if (size == 108) {
                bmpParameters.properties.put("bmp_version", "BMP v. 4.x");
                bmpParameters.redMask = (int) readDWord(bmpParameters.inputStream);
                bmpParameters.greenMask = (int) readDWord(bmpParameters.inputStream);
                bmpParameters.blueMask = (int) readDWord(bmpParameters.inputStream);
                bmpParameters.alphaMask = (int) readDWord(bmpParameters.inputStream);
                long csType = readDWord(bmpParameters.inputStream);
                int redX = readLong(bmpParameters.inputStream);
                int i4 = planes;
                int redY = readLong(bmpParameters.inputStream);
                long j3 = colorsImportant;
                int redZ = readLong(bmpParameters.inputStream);
                int greenX = readLong(bmpParameters.inputStream);
                int greenY = readLong(bmpParameters.inputStream);
                int greenZ = readLong(bmpParameters.inputStream);
                int blueX = readLong(bmpParameters.inputStream);
                int blueY = readLong(bmpParameters.inputStream);
                int blueZ = readLong(bmpParameters.inputStream);
                long gammaRed = readDWord(bmpParameters.inputStream);
                long gammaGreen = readDWord(bmpParameters.inputStream);
                long gammaBlue = readDWord(bmpParameters.inputStream);
                int redZ2 = redZ;
                if (bmpParameters.bitsPerPixel == 1) {
                    bmpParameters.imageType = 10;
                } else if (bmpParameters.bitsPerPixel == 4) {
                    bmpParameters.imageType = 11;
                } else if (bmpParameters.bitsPerPixel == 8) {
                    bmpParameters.imageType = 12;
                } else if (bmpParameters.bitsPerPixel == 16) {
                    bmpParameters.imageType = 13;
                    if (((int) bmpParameters.compression) == 0) {
                        bmpParameters.redMask = 31744;
                        bmpParameters.greenMask = 992;
                        bmpParameters.blueMask = 31;
                    }
                } else if (bmpParameters.bitsPerPixel == 24) {
                    bmpParameters.imageType = 14;
                } else if (bmpParameters.bitsPerPixel == 32) {
                    bmpParameters.imageType = 15;
                    if (((int) bmpParameters.compression) == 0) {
                        bmpParameters.redMask = 16711680;
                        bmpParameters.greenMask = 65280;
                        bmpParameters.blueMask = 255;
                    }
                }
                bmpParameters.properties.put("red_mask", Integer.valueOf(bmpParameters.redMask));
                bmpParameters.properties.put("green_mask", Integer.valueOf(bmpParameters.greenMask));
                bmpParameters.properties.put("blue_mask", Integer.valueOf(bmpParameters.blueMask));
                bmpParameters.properties.put("alpha_mask", Integer.valueOf(bmpParameters.alphaMask));
                int sizeOfPalette2 = ((int) (((bmpParameters.bitmapOffset - 14) - size) / 4)) * 4;
                if (bmpParameters.bitmapOffset == size) {
                    switch (bmpParameters.imageType) {
                        case 10:
                            sizeOfPalette2 = ((int) (colorsUsed == 0 ? 2 : colorsUsed)) * 4;
                            break;
                        case 11:
                            sizeOfPalette2 = ((int) (colorsUsed == 0 ? 16 : colorsUsed)) * 4;
                            break;
                        case 12:
                            sizeOfPalette2 = ((int) (colorsUsed == 0 ? 256 : colorsUsed)) * 4;
                            break;
                        default:
                            sizeOfPalette2 = 0;
                            break;
                    }
                    bmpParameters.bitmapOffset = ((long) sizeOfPalette2) + size;
                }
                readPalette(sizeOfPalette2, bmpParameters);
                switch ((int) csType) {
                    case 0:
                        bmpParameters.properties.put("color_space", "LCS_CALIBRATED_RGB");
                        bmpParameters.properties.put("redX", Integer.valueOf(redX));
                        bmpParameters.properties.put("redY", Integer.valueOf(redY));
                        bmpParameters.properties.put("redZ", Integer.valueOf(redZ2));
                        bmpParameters.properties.put("greenX", Integer.valueOf(greenX));
                        bmpParameters.properties.put("greenY", Integer.valueOf(greenY));
                        bmpParameters.properties.put("greenZ", Integer.valueOf(greenZ));
                        bmpParameters.properties.put("blueX", Integer.valueOf(blueX));
                        bmpParameters.properties.put("blueY", Integer.valueOf(blueY));
                        bmpParameters.properties.put("blueZ", Integer.valueOf(blueZ));
                        bmpParameters.properties.put("gamma_red", Long.valueOf(gammaRed));
                        bmpParameters.properties.put("gamma_green", Long.valueOf(gammaGreen));
                        bmpParameters.properties.put("gamma_blue", Long.valueOf(gammaBlue));
                        throw new RuntimeException("Not implemented yet.");
                    case 1:
                        bmpParameters.properties.put("color_space", "LCS_sRGB");
                        break;
                    case 2:
                        bmpParameters.properties.put("color_space", "LCS_CMYK");
                        throw new RuntimeException("Not implemented yet.");
                }
            } else {
                long j4 = colorsImportant;
                bmpParameters.properties.put("bmp_version", "BMP v. 5.x");
                throw new RuntimeException("Not implemented yet.");
            }
            switch ((int) bmpParameters.compression) {
                case 0:
                case 1:
                case 2:
                    if (bmpParameters.bitsPerPixel == 1) {
                        bmpParameters.imageType = 4;
                    } else if (bmpParameters.bitsPerPixel == 4) {
                        bmpParameters.imageType = 5;
                    } else if (bmpParameters.bitsPerPixel == 8) {
                        bmpParameters.imageType = 6;
                    } else if (bmpParameters.bitsPerPixel == 24) {
                        bmpParameters.imageType = 7;
                    } else if (bmpParameters.bitsPerPixel == 16) {
                        bmpParameters.imageType = 8;
                        bmpParameters.redMask = 31744;
                        bmpParameters.greenMask = 992;
                        bmpParameters.blueMask = 31;
                        bmpParameters.properties.put("red_mask", Integer.valueOf(bmpParameters.redMask));
                        bmpParameters.properties.put("green_mask", Integer.valueOf(bmpParameters.greenMask));
                        bmpParameters.properties.put("blue_mask", Integer.valueOf(bmpParameters.blueMask));
                    } else if (bmpParameters.bitsPerPixel == 32) {
                        bmpParameters.imageType = 9;
                        bmpParameters.redMask = 16711680;
                        bmpParameters.greenMask = 65280;
                        bmpParameters.blueMask = 255;
                        bmpParameters.properties.put("red_mask", Integer.valueOf(bmpParameters.redMask));
                        bmpParameters.properties.put("green_mask", Integer.valueOf(bmpParameters.greenMask));
                        bmpParameters.properties.put("blue_mask", Integer.valueOf(bmpParameters.blueMask));
                    }
                    if (size >= 52) {
                        bmpParameters.redMask = (int) readDWord(bmpParameters.inputStream);
                        bmpParameters.greenMask = (int) readDWord(bmpParameters.inputStream);
                        bmpParameters.blueMask = (int) readDWord(bmpParameters.inputStream);
                        bmpParameters.properties.put("red_mask", Integer.valueOf(bmpParameters.redMask));
                        bmpParameters.properties.put("green_mask", Integer.valueOf(bmpParameters.greenMask));
                        bmpParameters.properties.put("blue_mask", Integer.valueOf(bmpParameters.blueMask));
                    }
                    if (size == 56) {
                        bmpParameters.alphaMask = (int) readDWord(bmpParameters.inputStream);
                        bmpParameters.properties.put("alpha_mask", Integer.valueOf(bmpParameters.alphaMask));
                    }
                    int sizeOfPalette3 = ((int) (((bmpParameters.bitmapOffset - 14) - size) / 4)) * 4;
                    if (bmpParameters.bitmapOffset == size) {
                        switch (bmpParameters.imageType) {
                            case 4:
                                sizeOfPalette3 = ((int) (colorsUsed == 0 ? 2 : colorsUsed)) * 4;
                                break;
                            case 5:
                                sizeOfPalette3 = ((int) (colorsUsed == 0 ? 16 : colorsUsed)) * 4;
                                break;
                            case 6:
                                sizeOfPalette3 = ((int) (colorsUsed == 0 ? 256 : colorsUsed)) * 4;
                                break;
                            default:
                                sizeOfPalette3 = 0;
                                break;
                        }
                        bmpParameters.bitmapOffset = ((long) sizeOfPalette3) + size;
                    }
                    readPalette(sizeOfPalette3, bmpParameters);
                    bmpParameters.properties.put("bmp_version", "BMP v. 3.x");
                    break;
                case 3:
                    if (bmpParameters.bitsPerPixel == 16) {
                        bmpParameters.imageType = 8;
                    } else if (bmpParameters.bitsPerPixel == 32) {
                        bmpParameters.imageType = 9;
                    }
                    bmpParameters.redMask = (int) readDWord(bmpParameters.inputStream);
                    bmpParameters.greenMask = (int) readDWord(bmpParameters.inputStream);
                    bmpParameters.blueMask = (int) readDWord(bmpParameters.inputStream);
                    if (size == 56) {
                        bmpParameters.alphaMask = (int) readDWord(bmpParameters.inputStream);
                        bmpParameters.properties.put("alpha_mask", Integer.valueOf(bmpParameters.alphaMask));
                    }
                    bmpParameters.properties.put("red_mask", Integer.valueOf(bmpParameters.redMask));
                    bmpParameters.properties.put("green_mask", Integer.valueOf(bmpParameters.greenMask));
                    bmpParameters.properties.put("blue_mask", Integer.valueOf(bmpParameters.blueMask));
                    if (colorsUsed != 0) {
                        readPalette(((int) colorsUsed) * 4, bmpParameters);
                    }
                    bmpParameters.properties.put("bmp_version", "BMP v. 3.x NT");
                    break;
                default:
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidBmpFileCompression);
            }
        }
        if (bmpParameters.height > 0) {
            i = 1;
            bmpParameters.isBottomUp = true;
        } else {
            i = 1;
            bmpParameters.isBottomUp = false;
            bmpParameters.height = Math.abs(bmpParameters.height);
        }
        if (bmpParameters.bitsPerPixel == i || bmpParameters.bitsPerPixel == 4 || bmpParameters.bitsPerPixel == 8) {
            bmpParameters.numBands = 1;
            if (bmpParameters.imageType == 0 || bmpParameters.imageType == 1 || bmpParameters.imageType == 2) {
                int sizep = bmpParameters.palette.length / 3;
                if (sizep > 256) {
                    sizep = 256;
                }
                byte[] r = new byte[sizep];
                byte[] g = new byte[sizep];
                byte[] b = new byte[sizep];
                for (int i5 = 0; i5 < sizep; i5++) {
                    int off = i5 * 3;
                    b[i5] = bmpParameters.palette[off];
                    g[i5] = bmpParameters.palette[off + 1];
                    r[i5] = bmpParameters.palette[off + 2];
                }
                return;
            }
            int sizep2 = bmpParameters.palette.length / 4;
            if (sizep2 > 256) {
                sizep2 = 256;
            }
            byte[] r2 = new byte[sizep2];
            byte[] g2 = new byte[sizep2];
            byte[] b2 = new byte[sizep2];
            for (int i6 = 0; i6 < sizep2; i6++) {
                int off2 = i6 * 4;
                b2[i6] = bmpParameters.palette[off2];
                g2[i6] = bmpParameters.palette[off2 + 1];
                r2[i6] = bmpParameters.palette[off2 + 2];
            }
        } else if (bmpParameters.bitsPerPixel == 16) {
            bmpParameters.numBands = 3;
        } else if (bmpParameters.bitsPerPixel == 32) {
            bmpParameters.numBands = bmpParameters.alphaMask == 0 ? 3 : 4;
        } else {
            bmpParameters.numBands = 3;
        }
    }

    private static byte[] getPalette(int group, BmpParameters bmp) {
        if (bmp.palette == null) {
            return null;
        }
        byte[] np = new byte[((bmp.palette.length / group) * 3)];
        int e = bmp.palette.length / group;
        for (int k = 0; k < e; k++) {
            int src = k * group;
            int dest = k * 3;
            int src2 = src + 1;
            np[dest + 2] = bmp.palette[src];
            np[dest + 1] = bmp.palette[src2];
            np[dest] = bmp.palette[src2 + 1];
        }
        return np;
    }

    private static boolean getImage(BmpParameters bmp) throws IOException {
        switch (bmp.imageType) {
            case 0:
                read1Bit(3, bmp);
                return true;
            case 1:
                read4Bit(3, bmp);
                return true;
            case 2:
                read8Bit(3, bmp);
                return true;
            case 3:
                byte[] bdata = new byte[(bmp.width * bmp.height * 3)];
                read24Bit(bdata, bmp);
                RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata);
                return true;
            case 4:
                read1Bit(4, bmp);
                return true;
            case 5:
                switch ((int) bmp.compression) {
                    case 0:
                        read4Bit(4, bmp);
                        break;
                    case 2:
                        readRLE4(bmp);
                        break;
                    default:
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidBmpFileCompression);
                }
                return true;
            case 6:
                switch ((int) bmp.compression) {
                    case 0:
                        read8Bit(4, bmp);
                        break;
                    case 1:
                        readRLE8(bmp);
                        break;
                    default:
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidBmpFileCompression);
                }
                return true;
            case 7:
                byte[] bdata2 = new byte[(bmp.width * bmp.height * 3)];
                read24Bit(bdata2, bmp);
                RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata2);
                return true;
            case 8:
                read1632Bit(false, bmp);
                return true;
            case 9:
                read1632Bit(true, bmp);
                return true;
            case 10:
                read1Bit(4, bmp);
                return true;
            case 11:
                switch ((int) bmp.compression) {
                    case 0:
                        read4Bit(4, bmp);
                        break;
                    case 2:
                        readRLE4(bmp);
                        break;
                    default:
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidBmpFileCompression);
                }
                return true;
            case 12:
                switch ((int) bmp.compression) {
                    case 0:
                        read8Bit(4, bmp);
                        break;
                    case 1:
                        readRLE8(bmp);
                        break;
                    default:
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidBmpFileCompression);
                }
                return true;
            case 13:
                read1632Bit(false, bmp);
                return true;
            case 14:
                byte[] bdata3 = new byte[(bmp.width * bmp.height * 3)];
                read24Bit(bdata3, bmp);
                RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata3);
                return true;
            case 15:
                read1632Bit(true, bmp);
                return true;
            default:
                return false;
        }
    }

    private static void indexedModel(byte[] bdata, int bpc, int paletteEntries, BmpParameters bmp) {
        RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 1, bpc, bdata);
        byte[] np = getPalette(paletteEntries, bmp);
        Object[] colorSpace = {"/Indexed", "/DeviceRGB", Integer.valueOf((np.length / 3) - 1), PdfEncodings.convertToString(np, (String) null)};
        bmp.additional = new HashMap();
        bmp.additional.put("ColorSpace", colorSpace);
    }

    private static void readPalette(int sizeOfPalette, BmpParameters bmp) throws IOException {
        if (sizeOfPalette != 0) {
            bmp.palette = new byte[sizeOfPalette];
            int bytesRead = 0;
            while (bytesRead < sizeOfPalette) {
                int r = bmp.inputStream.read(bmp.palette, bytesRead, sizeOfPalette - bytesRead);
                if (r >= 0) {
                    bytesRead += r;
                } else {
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.IncompletePalette);
                }
            }
            bmp.properties.put("palette", bmp.palette);
        }
    }

    private static void read1Bit(int paletteEntries, BmpParameters bmp) throws IOException {
        byte[] bdata = new byte[(((bmp.width + 7) / 8) * bmp.height)];
        int padding = 0;
        double d = (double) bmp.width;
        Double.isNaN(d);
        int bytesPerScanline = (int) Math.ceil(d / 8.0d);
        int remainder = bytesPerScanline % 4;
        if (remainder != 0) {
            padding = 4 - remainder;
        }
        int imSize = (bytesPerScanline + padding) * bmp.height;
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
        }
        if (bmp.isBottomUp) {
            for (int i = 0; i < bmp.height; i++) {
                System.arraycopy(values, imSize - ((i + 1) * (bytesPerScanline + padding)), bdata, i * bytesPerScanline, bytesPerScanline);
            }
        } else {
            for (int i2 = 0; i2 < bmp.height; i2++) {
                System.arraycopy(values, (bytesPerScanline + padding) * i2, bdata, i2 * bytesPerScanline, bytesPerScanline);
            }
        }
        indexedModel(bdata, 1, paletteEntries, bmp);
    }

    private static void read4Bit(int paletteEntries, BmpParameters bmp) throws IOException {
        byte[] bdata = new byte[(((bmp.width + 1) / 2) * bmp.height)];
        int padding = 0;
        double d = (double) bmp.width;
        Double.isNaN(d);
        int bytesPerScanline = (int) Math.ceil(d / 2.0d);
        int remainder = bytesPerScanline % 4;
        if (remainder != 0) {
            padding = 4 - remainder;
        }
        int imSize = (bytesPerScanline + padding) * bmp.height;
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
        }
        if (bmp.isBottomUp) {
            for (int i = 0; i < bmp.height; i++) {
                System.arraycopy(values, imSize - ((i + 1) * (bytesPerScanline + padding)), bdata, i * bytesPerScanline, bytesPerScanline);
            }
        } else {
            for (int i2 = 0; i2 < bmp.height; i2++) {
                System.arraycopy(values, (bytesPerScanline + padding) * i2, bdata, i2 * bytesPerScanline, bytesPerScanline);
            }
        }
        indexedModel(bdata, 4, paletteEntries, bmp);
    }

    private static void read8Bit(int paletteEntries, BmpParameters bmp) throws IOException {
        byte[] bdata = new byte[(bmp.width * bmp.height)];
        int padding = 0;
        int bitsPerScanline = bmp.width * 8;
        if (bitsPerScanline % 32 != 0) {
            double d = (double) ((((bitsPerScanline / 32) + 1) * 32) - bitsPerScanline);
            Double.isNaN(d);
            padding = (int) Math.ceil(d / 8.0d);
        }
        int imSize = (bmp.width + padding) * bmp.height;
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
        }
        if (bmp.isBottomUp) {
            for (int i = 0; i < bmp.height; i++) {
                System.arraycopy(values, imSize - ((i + 1) * (bmp.width + padding)), bdata, bmp.width * i, bmp.width);
            }
        } else {
            for (int i2 = 0; i2 < bmp.height; i2++) {
                System.arraycopy(values, (bmp.width + padding) * i2, bdata, bmp.width * i2, bmp.width);
            }
        }
        indexedModel(bdata, 8, paletteEntries, bmp);
    }

    private static void read24Bit(byte[] bdata, BmpParameters bmp) throws IOException {
        int padding = 0;
        int bitsPerScanline = bmp.width * 24;
        if (bitsPerScanline % 32 != 0) {
            double d = (double) ((((bitsPerScanline / 32) + 1) * 32) - bitsPerScanline);
            Double.isNaN(d);
            padding = (int) Math.ceil(d / 8.0d);
        }
        int imSize = (((bmp.width * 3) + 3) / 4) * 4 * bmp.height;
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            int r = bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
            if (r < 0) {
                break;
            }
            bytesRead += r;
        }
        int l = 0;
        if (bmp.isBottomUp) {
            int max = ((bmp.width * bmp.height) * 3) - 1;
            int count = -padding;
            int i = 0;
            while (i < bmp.height) {
                int l2 = (max - (((i + 1) * bmp.width) * 3)) + 1;
                count += padding;
                for (int j = 0; j < bmp.width; j++) {
                    int count2 = count + 1;
                    bdata[l2 + 2] = values[count];
                    int count3 = count2 + 1;
                    bdata[l2 + 1] = values[count2];
                    count = count3 + 1;
                    bdata[l2] = values[count3];
                    l2 += 3;
                }
                i++;
                int i2 = l2;
            }
            return;
        }
        int count4 = -padding;
        for (int i3 = 0; i3 < bmp.height; i3++) {
            count4 += padding;
            for (int j2 = 0; j2 < bmp.width; j2++) {
                int count5 = count4 + 1;
                bdata[l + 2] = values[count4];
                int count6 = count5 + 1;
                bdata[l + 1] = values[count5];
                count4 = count6 + 1;
                bdata[l] = values[count6];
                l += 3;
            }
        }
        int i4 = count4;
    }

    private static int findMask(int mask) {
        for (int k = 0; k < 32 && (mask & 1) != 1; k++) {
            mask >>>= 1;
        }
        return mask;
    }

    private static int findShift(int mask) {
        int k = 0;
        while (k < 32 && (mask & 1) != 1) {
            mask >>>= 1;
            k++;
        }
        return k;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void read1632Bit(boolean r20, com.itextpdf.p026io.image.BmpImageHelper.BmpParameters r21) throws java.io.IOException {
        /*
            r0 = r21
            int r1 = r0.redMask
            int r1 = findMask(r1)
            int r2 = r0.redMask
            int r2 = findShift(r2)
            int r3 = r1 + 1
            int r4 = r0.greenMask
            int r4 = findMask(r4)
            int r5 = r0.greenMask
            int r5 = findShift(r5)
            int r6 = r4 + 1
            int r7 = r0.blueMask
            int r7 = findMask(r7)
            int r8 = r0.blueMask
            int r8 = findShift(r8)
            int r9 = r7 + 1
            int r10 = r0.width
            int r11 = r0.height
            int r10 = r10 * r11
            int r10 = r10 * 3
            byte[] r10 = new byte[r10]
            r11 = 0
            if (r20 != 0) goto L_0x0057
            int r12 = r0.width
            int r12 = r12 * 16
            int r13 = r12 % 32
            if (r13 == 0) goto L_0x0057
            int r13 = r12 / 32
            int r13 = r13 + 1
            int r13 = r13 * 32
            int r13 = r13 - r12
            double r14 = (double) r13
            r16 = 4620693217682128896(0x4020000000000000, double:8.0)
            java.lang.Double.isNaN(r14)
            double r14 = r14 / r16
            double r14 = java.lang.Math.ceil(r14)
            int r11 = (int) r14
            r15 = r11
            goto L_0x0058
        L_0x0057:
            r15 = r11
        L_0x0058:
            long r11 = r0.imageSize
            int r12 = (int) r11
            if (r12 != 0) goto L_0x0068
            long r13 = r0.bitmapFileSize
            r16 = r12
            long r11 = r0.bitmapOffset
            long r13 = r13 - r11
            int r12 = (int) r13
            r17 = r12
            goto L_0x006c
        L_0x0068:
            r16 = r12
            r17 = r16
        L_0x006c:
            r11 = 0
            boolean r12 = r0.isBottomUp
            if (r12 == 0) goto L_0x00de
            int r12 = r0.height
            int r12 = r12 + -1
        L_0x0075:
            if (r12 < 0) goto L_0x00da
            int r13 = r0.width
            int r13 = r13 * 3
            int r13 = r13 * r12
            r11 = 0
        L_0x007e:
            int r14 = r0.width
            if (r11 >= r14) goto L_0x00c1
            if (r20 == 0) goto L_0x008e
            java.io.InputStream r14 = r0.inputStream
            r16 = r15
            long r14 = readDWord(r14)
            int r15 = (int) r14
            goto L_0x0096
        L_0x008e:
            r16 = r15
            java.io.InputStream r14 = r0.inputStream
            int r15 = readWord(r14)
        L_0x0096:
            int r14 = r13 + 1
            int r18 = r15 >>> r2
            r19 = r2
            r2 = r18 & r1
            int r2 = r2 * 256
            int r2 = r2 / r3
            byte r2 = (byte) r2
            r10[r13] = r2
            int r2 = r14 + 1
            int r13 = r15 >>> r5
            r13 = r13 & r4
            int r13 = r13 * 256
            int r13 = r13 / r6
            byte r13 = (byte) r13
            r10[r14] = r13
            int r13 = r2 + 1
            int r14 = r15 >>> r8
            r14 = r14 & r7
            int r14 = r14 * 256
            int r14 = r14 / r9
            byte r14 = (byte) r14
            r10[r2] = r14
            int r11 = r11 + 1
            r15 = r16
            r2 = r19
            goto L_0x007e
        L_0x00c1:
            r19 = r2
            r16 = r15
            r2 = 0
        L_0x00c6:
            r15 = r16
            if (r2 >= r15) goto L_0x00d4
            java.io.InputStream r11 = r0.inputStream
            r11.read()
            int r2 = r2 + 1
            r16 = r15
            goto L_0x00c6
        L_0x00d4:
            int r12 = r12 + -1
            r11 = r13
            r2 = r19
            goto L_0x0075
        L_0x00da:
            r19 = r2
            r2 = r11
            goto L_0x0136
        L_0x00de:
            r19 = r2
            r2 = 0
        L_0x00e1:
            int r12 = r0.height
            if (r2 >= r12) goto L_0x0133
            r12 = 0
        L_0x00e6:
            int r13 = r0.width
            if (r12 >= r13) goto L_0x0123
            if (r20 == 0) goto L_0x00f4
            java.io.InputStream r13 = r0.inputStream
            long r13 = readDWord(r13)
            int r14 = (int) r13
            goto L_0x00fa
        L_0x00f4:
            java.io.InputStream r13 = r0.inputStream
            int r14 = readWord(r13)
        L_0x00fa:
            int r13 = r11 + 1
            int r16 = r14 >>> r19
            r18 = r2
            r2 = r16 & r1
            int r2 = r2 * 256
            int r2 = r2 / r3
            byte r2 = (byte) r2
            r10[r11] = r2
            int r2 = r13 + 1
            int r11 = r14 >>> r5
            r11 = r11 & r4
            int r11 = r11 * 256
            int r11 = r11 / r6
            byte r11 = (byte) r11
            r10[r13] = r11
            int r11 = r2 + 1
            int r13 = r14 >>> r8
            r13 = r13 & r7
            int r13 = r13 * 256
            int r13 = r13 / r9
            byte r13 = (byte) r13
            r10[r2] = r13
            int r12 = r12 + 1
            r2 = r18
            goto L_0x00e6
        L_0x0123:
            r18 = r2
            r2 = 0
        L_0x0126:
            if (r2 >= r15) goto L_0x0130
            java.io.InputStream r12 = r0.inputStream
            r12.read()
            int r2 = r2 + 1
            goto L_0x0126
        L_0x0130:
            int r2 = r18 + 1
            goto L_0x00e1
        L_0x0133:
            r18 = r2
            r2 = r11
        L_0x0136:
            com.itextpdf.io.image.BmpImageData r11 = r0.image
            int r12 = r0.width
            int r13 = r0.height
            r14 = 3
            r16 = 8
            r18 = r15
            r15 = r16
            r16 = r10
            com.itextpdf.p026io.image.RawImageHelper.updateRawImageParameters(r11, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.BmpImageHelper.read1632Bit(boolean, com.itextpdf.io.image.BmpImageHelper$BmpParameters):void");
    }

    private static void readRLE8(BmpParameters bmp) throws IOException {
        int imSize = (int) bmp.imageSize;
        if (imSize == 0) {
            imSize = (int) (bmp.bitmapFileSize - bmp.bitmapOffset);
        }
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
        }
        byte[] val = decodeRLE(true, values, bmp);
        int imSize2 = bmp.width * bmp.height;
        if (bmp.isBottomUp != 0) {
            byte[] temp = new byte[val.length];
            int bytesPerScanline = bmp.width;
            for (int i = 0; i < bmp.height; i++) {
                System.arraycopy(val, imSize2 - ((i + 1) * bytesPerScanline), temp, i * bytesPerScanline, bytesPerScanline);
            }
            val = temp;
        }
        indexedModel(val, 8, 4, bmp);
    }

    private static void readRLE4(BmpParameters bmp) throws IOException {
        int imSize = (int) bmp.imageSize;
        if (imSize == 0) {
            imSize = (int) (bmp.bitmapFileSize - bmp.bitmapOffset);
        }
        byte[] values = new byte[imSize];
        int bytesRead = 0;
        while (bytesRead < imSize) {
            bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
        }
        byte[] val = decodeRLE(false, values, bmp);
        if (bmp.isBottomUp) {
            byte[] inverted = val;
            val = new byte[(bmp.width * bmp.height)];
            int l = 0;
            for (int i = bmp.height - 1; i >= 0; i--) {
                int index = bmp.width * i;
                int lineEnd = bmp.width + l;
                while (l != lineEnd) {
                    val[l] = inverted[index];
                    l++;
                    index++;
                }
            }
        }
        int stride = (bmp.width + 1) / 2;
        byte[] bdata = new byte[(bmp.height * stride)];
        int ptr = 0;
        int sh = 0;
        for (int h = 0; h < bmp.height; h++) {
            for (int w = 0; w < bmp.width; w++) {
                if ((w & 1) == 0) {
                    bdata[(w / 2) + sh] = (byte) (val[ptr] << 4);
                    ptr++;
                } else {
                    int i2 = (w / 2) + sh;
                    bdata[i2] = (byte) (((byte) (val[ptr] & 15)) | bdata[i2]);
                    ptr++;
                }
            }
            sh += stride;
        }
        indexedModel(bdata, 4, 4, bmp);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] decodeRLE(boolean r11, byte[] r12, com.itextpdf.p026io.image.BmpImageHelper.BmpParameters r13) {
        /*
            int r0 = r13.width
            int r1 = r13.height
            int r0 = r0 * r1
            byte[] r0 = new byte[r0]
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x000c:
            int r5 = r13.height     // Catch:{ Exception -> 0x00d1 }
            if (r4 >= r5) goto L_0x00d0
            int r5 = r12.length     // Catch:{ Exception -> 0x00d1 }
            if (r1 >= r5) goto L_0x00d0
            int r5 = r1 + 1
            byte r1 = r12[r1]     // Catch:{ Exception -> 0x00d1 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r6 = 1
            if (r1 == 0) goto L_0x004c
            int r7 = r5 + 1
            byte r5 = r12[r5]     // Catch:{ Exception -> 0x00d1 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            if (r11 == 0) goto L_0x0031
            r6 = r1
        L_0x0025:
            if (r6 == 0) goto L_0x0030
            int r8 = r3 + 1
            byte r9 = (byte) r5     // Catch:{ Exception -> 0x00d1 }
            r0[r3] = r9     // Catch:{ Exception -> 0x00d1 }
            int r6 = r6 + -1
            r3 = r8
            goto L_0x0025
        L_0x0030:
            goto L_0x0048
        L_0x0031:
            r8 = 0
        L_0x0032:
            if (r8 >= r1) goto L_0x0048
            int r9 = r3 + 1
            r10 = r8 & 1
            if (r10 != r6) goto L_0x003d
            r10 = r5 & 15
            goto L_0x0041
        L_0x003d:
            int r10 = r5 >>> 4
            r10 = r10 & 15
        L_0x0041:
            byte r10 = (byte) r10     // Catch:{ Exception -> 0x00d1 }
            r0[r3] = r10     // Catch:{ Exception -> 0x00d1 }
            int r8 = r8 + 1
            r3 = r9
            goto L_0x0032
        L_0x0048:
            int r2 = r2 + r1
            r1 = r7
            goto L_0x00ce
        L_0x004c:
            int r7 = r5 + 1
            byte r5 = r12[r5]     // Catch:{ Exception -> 0x00d1 }
            r1 = r5 & 255(0xff, float:3.57E-43)
            if (r1 != r6) goto L_0x0056
            goto L_0x00d0
        L_0x0056:
            switch(r1) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0059;
                case 2: goto L_0x005d;
                default: goto L_0x0059;
            }     // Catch:{ Exception -> 0x00d1 }
        L_0x0059:
            if (r11 == 0) goto L_0x0091
            r5 = r1
            goto L_0x007e
        L_0x005d:
            int r5 = r7 + 1
            byte r6 = r12[r7]     // Catch:{ Exception -> 0x00d1 }
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r6
            int r6 = r5 + 1
            byte r5 = r12[r5]     // Catch:{ Exception -> 0x00d1 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r4 = r4 + r5
            int r5 = r13.width     // Catch:{ Exception -> 0x00d1 }
            int r5 = r5 * r4
            int r5 = r5 + r2
            r3 = r5
            r1 = r6
            goto L_0x00ce
        L_0x0074:
            r2 = 0
            int r4 = r4 + 1
            int r5 = r13.width     // Catch:{ Exception -> 0x00d1 }
            int r5 = r5 * r4
            r3 = r5
            r1 = r7
            goto L_0x00ce
        L_0x007e:
            if (r5 == 0) goto L_0x0090
            int r8 = r3 + 1
            int r9 = r7 + 1
            byte r7 = r12[r7]     // Catch:{ Exception -> 0x00d1 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            byte r7 = (byte) r7     // Catch:{ Exception -> 0x00d1 }
            r0[r3] = r7     // Catch:{ Exception -> 0x00d1 }
            int r5 = r5 + -1
            r3 = r8
            r7 = r9
            goto L_0x007e
        L_0x0090:
            goto L_0x00b4
        L_0x0091:
            r5 = 0
            r8 = 0
        L_0x0093:
            if (r8 >= r1) goto L_0x00b4
            r9 = r8 & 1
            if (r9 != 0) goto L_0x00a0
            int r9 = r7 + 1
            byte r7 = r12[r7]     // Catch:{ Exception -> 0x00d1 }
            r5 = r7 & 255(0xff, float:3.57E-43)
            r7 = r9
        L_0x00a0:
            int r9 = r3 + 1
            r10 = r8 & 1
            if (r10 != r6) goto L_0x00a9
            r10 = r5 & 15
            goto L_0x00ad
        L_0x00a9:
            int r10 = r5 >>> 4
            r10 = r10 & 15
        L_0x00ad:
            byte r10 = (byte) r10     // Catch:{ Exception -> 0x00d1 }
            r0[r3] = r10     // Catch:{ Exception -> 0x00d1 }
            int r8 = r8 + 1
            r3 = r9
            goto L_0x0093
        L_0x00b4:
            int r2 = r2 + r1
            if (r11 == 0) goto L_0x00bf
            r5 = r1 & 1
            if (r5 != r6) goto L_0x00c9
            int r7 = r7 + 1
            r1 = r7
            goto L_0x00ce
        L_0x00bf:
            r5 = r1 & 3
            if (r5 == r6) goto L_0x00cb
            r5 = r1 & 3
            r6 = 2
            if (r5 != r6) goto L_0x00c9
            goto L_0x00cb
        L_0x00c9:
            r1 = r7
            goto L_0x00ce
        L_0x00cb:
            int r7 = r7 + 1
            r1 = r7
        L_0x00ce:
            goto L_0x000c
        L_0x00d0:
            goto L_0x00d2
        L_0x00d1:
            r1 = move-exception
        L_0x00d2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.BmpImageHelper.decodeRLE(boolean, byte[], com.itextpdf.io.image.BmpImageHelper$BmpParameters):byte[]");
    }

    private static int readUnsignedByte(InputStream stream) throws IOException {
        return stream.read() & 255;
    }

    private static int readUnsignedShort(InputStream stream) throws IOException {
        return ((readUnsignedByte(stream) << 8) | readUnsignedByte(stream)) & 65535;
    }

    private static int readShort(InputStream stream) throws IOException {
        return (readUnsignedByte(stream) << 8) | readUnsignedByte(stream);
    }

    private static int readWord(InputStream stream) throws IOException {
        return readUnsignedShort(stream);
    }

    private static long readUnsignedInt(InputStream stream) throws IOException {
        int b1 = readUnsignedByte(stream);
        int b2 = readUnsignedByte(stream);
        return -1 & ((long) ((readUnsignedByte(stream) << 24) | (readUnsignedByte(stream) << 16) | (b2 << 8) | b1));
    }

    private static int readInt(InputStream stream) throws IOException {
        int b1 = readUnsignedByte(stream);
        int b2 = readUnsignedByte(stream);
        return (readUnsignedByte(stream) << 24) | (readUnsignedByte(stream) << 16) | (b2 << 8) | b1;
    }

    private static long readDWord(InputStream stream) throws IOException {
        return readUnsignedInt(stream);
    }

    private static int readLong(InputStream stream) throws IOException {
        return readInt(stream);
    }
}

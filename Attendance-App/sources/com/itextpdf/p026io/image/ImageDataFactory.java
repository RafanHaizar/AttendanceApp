package com.itextpdf.p026io.image;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.codec.CCITTG4Encoder;
import com.itextpdf.p026io.codec.TIFFFaxDecoder;
import com.itextpdf.p026io.util.UrlUtil;
import java.awt.Color;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.itextpdf.io.image.ImageDataFactory */
public final class ImageDataFactory {
    private ImageDataFactory() {
    }

    public static ImageData create(byte[] bytes, boolean recoverImage) {
        return createImageInstance(bytes, recoverImage);
    }

    public static ImageData create(byte[] bytes) {
        return create(bytes, false);
    }

    public static ImageData create(URL url, boolean recoverImage) {
        return createImageInstance(url, recoverImage);
    }

    public static ImageData create(URL url) {
        return create(url, false);
    }

    public static ImageData create(String filename, boolean recoverImage) throws MalformedURLException {
        return create(UrlUtil.toURL(filename), recoverImage);
    }

    public static ImageData create(String filename) throws MalformedURLException {
        return create(filename, false);
    }

    public static ImageData create(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data, int[] transparency) {
        if (transparency != null && transparency.length != 2) {
            throw new IOException(IOException.TransparencyLengthMustBeEqualTo2WithCcittImages);
        } else if (typeCCITT == 256 || typeCCITT == 257 || typeCCITT == 258) {
            if (reverseBits) {
                TIFFFaxDecoder.reverseBits(data);
            }
            RawImageData image = new RawImageData(data, ImageType.RAW);
            image.setTypeCcitt(typeCCITT);
            image.height = (float) height;
            image.width = (float) width;
            image.colorSpace = parameters;
            image.transparency = transparency;
            return image;
        } else {
            throw new IOException(IOException.CcittCompressionTypeMustBeCcittg4Ccittg3_1dOrCcittg3_2d);
        }
    }

    public static ImageData create(int width, int height, int components, int bpc, byte[] data, int[] transparency) {
        if (transparency != null && transparency.length != components * 2) {
            throw new IOException(IOException.TransparencyLengthMustBeEqualTo2WithCcittImages);
        } else if (components == 1 && bpc == 1) {
            return create(width, height, false, 256, 1, CCITTG4Encoder.compress(data, width, height), transparency);
        } else {
            RawImageData image = new RawImageData(data, ImageType.RAW);
            image.height = (float) height;
            image.width = (float) width;
            if (components != 1 && components != 3 && components != 4) {
                throw new IOException(IOException.ComponentsMustBe1_3Or4);
            } else if (bpc == 1 || bpc == 2 || bpc == 4 || bpc == 8) {
                image.colorSpace = components;
                image.bpc = bpc;
                image.data = data;
                image.transparency = transparency;
                return image;
            } else {
                throw new IOException(IOException.BitsPerComponentMustBe1_2_4or8);
            }
        }
    }

    public static ImageData create(Image image, Color color) throws java.io.IOException {
        return create(image, color, false);
    }

    public static ImageData create(Image image, Color color, boolean forceBW) throws java.io.IOException {
        return AwtImageDataFactory.create(image, color, forceBW);
    }

    public static ImageData createBmp(URL url, boolean noHeader) {
        return createBmp(url, noHeader, 0);
    }

    @Deprecated
    public static ImageData createBmp(URL url, boolean noHeader, int size) {
        validateImageType(url, ImageType.BMP);
        ImageData image = new BmpImageData(url, noHeader, size);
        BmpImageHelper.processImage(image);
        return image;
    }

    public static ImageData createBmp(byte[] bytes, boolean noHeader) {
        return createBmp(bytes, noHeader, 0);
    }

    @Deprecated
    public static ImageData createBmp(byte[] bytes, boolean noHeader, int size) {
        if (noHeader || ImageTypeDetector.detectImageType(bytes) == ImageType.BMP) {
            ImageData image = new BmpImageData(bytes, noHeader, size);
            BmpImageHelper.processImage(image);
            return image;
        }
        throw new IllegalArgumentException("BMP image expected.");
    }

    public static GifImageData createGif(byte[] bytes) {
        validateImageType(bytes, ImageType.GIF);
        GifImageData image = new GifImageData(bytes);
        GifImageHelper.processImage(image);
        return image;
    }

    public static ImageData createGifFrame(URL url, int frame) {
        return createGifFrames(url, new int[]{frame}).get(0);
    }

    public static ImageData createGifFrame(byte[] bytes, int frame) {
        return createGifFrames(bytes, new int[]{frame}).get(0);
    }

    public static List<ImageData> createGifFrames(byte[] bytes, int[] frameNumbers) {
        validateImageType(bytes, ImageType.GIF);
        return processGifImageAndExtractFrames(frameNumbers, new GifImageData(bytes));
    }

    public static List<ImageData> createGifFrames(URL url, int[] frameNumbers) {
        validateImageType(url, ImageType.GIF);
        return processGifImageAndExtractFrames(frameNumbers, new GifImageData(url));
    }

    public static List<ImageData> createGifFrames(byte[] bytes) {
        validateImageType(bytes, ImageType.GIF);
        GifImageData image = new GifImageData(bytes);
        GifImageHelper.processImage(image);
        return image.getFrames();
    }

    public static List<ImageData> createGifFrames(URL url) {
        validateImageType(url, ImageType.GIF);
        GifImageData image = new GifImageData(url);
        GifImageHelper.processImage(image);
        return image.getFrames();
    }

    public static ImageData createJbig2(URL url, int page) {
        if (page >= 1) {
            validateImageType(url, ImageType.JBIG2);
            ImageData image = new Jbig2ImageData(url, page);
            Jbig2ImageHelper.processImage(image);
            return image;
        }
        throw new IllegalArgumentException("The page number must be greater than 0");
    }

    public static ImageData createJbig2(byte[] bytes, int page) {
        if (page >= 1) {
            validateImageType(bytes, ImageType.JBIG2);
            ImageData image = new Jbig2ImageData(bytes, page);
            Jbig2ImageHelper.processImage(image);
            return image;
        }
        throw new IllegalArgumentException("The page number must be greater than 0");
    }

    public static ImageData createJpeg(URL url) {
        validateImageType(url, ImageType.JPEG);
        ImageData image = new JpegImageData(url);
        JpegImageHelper.processImage(image);
        return image;
    }

    public static ImageData createJpeg(byte[] bytes) {
        validateImageType(bytes, ImageType.JPEG);
        ImageData image = new JpegImageData(bytes);
        JpegImageHelper.processImage(image);
        return image;
    }

    public static ImageData createJpeg2000(URL url) {
        validateImageType(url, ImageType.JPEG2000);
        ImageData image = new Jpeg2000ImageData(url);
        Jpeg2000ImageHelper.processImage(image);
        return image;
    }

    public static ImageData createJpeg2000(byte[] bytes) {
        validateImageType(bytes, ImageType.JPEG2000);
        ImageData image = new Jpeg2000ImageData(bytes);
        Jpeg2000ImageHelper.processImage(image);
        return image;
    }

    public static ImageData createPng(URL url) {
        validateImageType(url, ImageType.PNG);
        ImageData image = new PngImageData(url);
        PngImageHelper.processImage(image);
        return image;
    }

    public static ImageData createPng(byte[] bytes) {
        validateImageType(bytes, ImageType.PNG);
        ImageData image = new PngImageData(bytes);
        PngImageHelper.processImage(image);
        return image;
    }

    public static ImageData createTiff(URL url, boolean recoverFromImageError, int page, boolean direct) {
        validateImageType(url, ImageType.TIFF);
        ImageData image = new TiffImageData(url, recoverFromImageError, page, direct);
        TiffImageHelper.processImage(image);
        return image;
    }

    public static ImageData createTiff(byte[] bytes, boolean recoverFromImageError, int page, boolean direct) {
        validateImageType(bytes, ImageType.TIFF);
        ImageData image = new TiffImageData(bytes, recoverFromImageError, page, direct);
        TiffImageHelper.processImage(image);
        return image;
    }

    public static ImageData createRawImage(byte[] bytes) {
        return new RawImageData(bytes, ImageType.RAW);
    }

    public static boolean isSupportedType(byte[] source) {
        if (source == null) {
            return false;
        }
        return isSupportedType(ImageTypeDetector.detectImageType(source));
    }

    public static boolean isSupportedType(URL source) {
        if (source == null) {
            return false;
        }
        return isSupportedType(ImageTypeDetector.detectImageType(source));
    }

    public static boolean isSupportedType(ImageType imageType) {
        return imageType == ImageType.GIF || imageType == ImageType.JPEG || imageType == ImageType.JPEG2000 || imageType == ImageType.PNG || imageType == ImageType.BMP || imageType == ImageType.TIFF || imageType == ImageType.JBIG2;
    }

    /* renamed from: com.itextpdf.io.image.ImageDataFactory$1 */
    static /* synthetic */ class C14161 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$io$image$ImageType;

        static {
            int[] iArr = new int[ImageType.values().length];
            $SwitchMap$com$itextpdf$io$image$ImageType = iArr;
            try {
                iArr[ImageType.GIF.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.JPEG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.JPEG2000.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.PNG.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.BMP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.TIFF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.JBIG2.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private static ImageData createImageInstance(URL source, boolean recoverImage) {
        switch (C14161.$SwitchMap$com$itextpdf$io$image$ImageType[ImageTypeDetector.detectImageType(source).ordinal()]) {
            case 1:
                GifImageData image = new GifImageData(source);
                GifImageHelper.processImage(image, 0);
                return image.getFrames().get(0);
            case 2:
                ImageData image2 = new JpegImageData(source);
                JpegImageHelper.processImage(image2);
                return image2;
            case 3:
                ImageData image3 = new Jpeg2000ImageData(source);
                Jpeg2000ImageHelper.processImage(image3);
                return image3;
            case 4:
                ImageData image4 = new PngImageData(source);
                PngImageHelper.processImage(image4);
                return image4;
            case 5:
                ImageData image5 = new BmpImageData(source, false);
                BmpImageHelper.processImage(image5);
                return image5;
            case 6:
                ImageData image6 = new TiffImageData(source, recoverImage, 1, false);
                TiffImageHelper.processImage(image6);
                return image6;
            case 7:
                ImageData image7 = new Jbig2ImageData(source, 1);
                Jbig2ImageHelper.processImage(image7);
                return image7;
            default:
                throw new IOException(IOException.ImageFormatCannotBeRecognized);
        }
    }

    private static ImageData createImageInstance(byte[] bytes, boolean recoverImage) {
        switch (C14161.$SwitchMap$com$itextpdf$io$image$ImageType[ImageTypeDetector.detectImageType(bytes).ordinal()]) {
            case 1:
                GifImageData image = new GifImageData(bytes);
                GifImageHelper.processImage(image, 0);
                return image.getFrames().get(0);
            case 2:
                ImageData image2 = new JpegImageData(bytes);
                JpegImageHelper.processImage(image2);
                return image2;
            case 3:
                ImageData image3 = new Jpeg2000ImageData(bytes);
                Jpeg2000ImageHelper.processImage(image3);
                return image3;
            case 4:
                ImageData image4 = new PngImageData(bytes);
                PngImageHelper.processImage(image4);
                return image4;
            case 5:
                ImageData image5 = new BmpImageData(bytes, false);
                BmpImageHelper.processImage(image5);
                return image5;
            case 6:
                ImageData image6 = new TiffImageData(bytes, recoverImage, 1, false);
                TiffImageHelper.processImage(image6);
                return image6;
            case 7:
                ImageData image7 = new Jbig2ImageData(bytes, 1);
                Jbig2ImageHelper.processImage(image7);
                return image7;
            default:
                throw new IOException(IOException.ImageFormatCannotBeRecognized);
        }
    }

    private static List<ImageData> processGifImageAndExtractFrames(int[] frameNumbers, GifImageData image) {
        Arrays.sort(frameNumbers);
        GifImageHelper.processImage(image, frameNumbers[frameNumbers.length - 1] - 1);
        List<ImageData> frames = new ArrayList<>();
        int length = frameNumbers.length;
        for (int i = 0; i < length; i++) {
            frames.add(image.getFrames().get(frameNumbers[i] - 1));
        }
        return frames;
    }

    private static void validateImageType(byte[] image, ImageType expectedType) {
        ImageType detectedType = ImageTypeDetector.detectImageType(image);
        if (detectedType != expectedType) {
            throw new IllegalArgumentException(expectedType.name() + " image expected. Detected image type: " + detectedType.name());
        }
    }

    private static void validateImageType(URL imageUrl, ImageType expectedType) {
        ImageType detectedType = ImageTypeDetector.detectImageType(imageUrl);
        if (detectedType != expectedType) {
            throw new IllegalArgumentException(expectedType.name() + " image expected. Detected image type: " + detectedType.name());
        }
    }
}

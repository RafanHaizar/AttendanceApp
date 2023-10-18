package com.itextpdf.kernel.pdf.xobject;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.filters.DoNothingFilter;
import com.itextpdf.kernel.pdf.filters.FilterHandlers;
import com.itextpdf.kernel.pdf.filters.IFilterHandler;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.colors.IccProfile;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageType;
import com.itextpdf.p026io.image.PngChromaticities;
import com.itextpdf.p026io.image.PngImageData;
import com.itextpdf.p026io.image.PngImageHelperConstants;
import com.itextpdf.p026io.image.RawImageData;
import com.itextpdf.p026io.image.RawImageHelper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.slf4j.LoggerFactory;

public class PdfImageXObject extends PdfXObject {
    private static final long serialVersionUID = -205889576153966580L;
    private float height;
    private boolean mask;
    private boolean softMask;
    private float width;

    public PdfImageXObject(ImageData image) {
        this(image, (PdfImageXObject) null);
    }

    public PdfImageXObject(ImageData image, PdfImageXObject imageMask) {
        this(createPdfStream(checkImageType(image), imageMask));
        this.mask = image.isMask();
        this.softMask = image.isSoftMask();
    }

    public PdfImageXObject(PdfStream pdfStream) {
        super(pdfStream);
        if (!pdfStream.isFlushed()) {
            initWidthField();
            initHeightField();
        }
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void flush() {
        super.flush();
    }

    public PdfImageXObject copyTo(PdfDocument document) {
        PdfImageXObject image = new PdfImageXObject((PdfStream) ((PdfStream) getPdfObject()).copyTo(document));
        image.mask = this.mask;
        image.softMask = this.softMask;
        return image;
    }

    public BufferedImage getBufferedImage() throws IOException {
        return ImageIO.read(new ByteArrayInputStream(getImageBytes()));
    }

    public byte[] getImageBytes() {
        return getImageBytes(true);
    }

    public byte[] getImageBytes(boolean decoded) {
        byte[] bytes = ((PdfStream) getPdfObject()).getBytes(false);
        if (!decoded) {
            return bytes;
        }
        Map<PdfName, IFilterHandler> filters = new HashMap<>(FilterHandlers.getDefaultFilterHandlers());
        filters.put(PdfName.JBIG2Decode, new DoNothingFilter());
        byte[] bytes2 = PdfReader.decodeBytes(bytes, (PdfDictionary) getPdfObject(), filters);
        ImageType imageType = identifyImageType();
        if (imageType != ImageType.TIFF && imageType != ImageType.PNG) {
            return bytes2;
        }
        try {
            return new ImagePdfBytesInfo(this).decodeTiffAndPngBytes(bytes2);
        } catch (IOException e) {
            throw new RuntimeException("IO exception in PdfImageXObject", e);
        }
    }

    public ImageType identifyImageType() {
        PdfObject filter = ((PdfStream) getPdfObject()).get(PdfName.Filter);
        PdfArray filters = new PdfArray();
        if (filter != null) {
            if (filter.getType() == 6) {
                filters.add(filter);
            } else if (filter.getType() == 1) {
                filters = (PdfArray) filter;
            }
        }
        for (int i = filters.size() - 1; i >= 0; i--) {
            PdfName filterName = (PdfName) filters.get(i);
            if (PdfName.DCTDecode.equals(filterName)) {
                return ImageType.JPEG;
            }
            if (PdfName.JBIG2Decode.equals(filterName)) {
                return ImageType.JBIG2;
            }
            if (PdfName.JPXDecode.equals(filterName)) {
                return ImageType.JPEG2000;
            }
        }
        if (new ImagePdfBytesInfo(this).getPngColorType() < 0) {
            return ImageType.TIFF;
        }
        return ImageType.PNG;
    }

    /* renamed from: com.itextpdf.kernel.pdf.xobject.PdfImageXObject$1 */
    static /* synthetic */ class C14411 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$io$image$ImageType;

        static {
            int[] iArr = new int[ImageType.values().length];
            $SwitchMap$com$itextpdf$io$image$ImageType = iArr;
            try {
                iArr[ImageType.PNG.ordinal()] = 1;
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
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.TIFF.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$io$image$ImageType[ImageType.JBIG2.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public String identifyImageFileExtension() {
        switch (C14411.$SwitchMap$com$itextpdf$io$image$ImageType[identifyImageType().ordinal()]) {
            case 1:
                return "png";
            case 2:
                return "jpg";
            case 3:
                return "jp2";
            case 4:
                return "tif";
            case 5:
                return "jbig2";
            default:
                throw new IllegalStateException("Should have never happened. This type of image is not allowed for ImageXObject");
        }
    }

    public PdfImageXObject put(PdfName key, PdfObject value) {
        ((PdfStream) getPdfObject()).put(key, value);
        return this;
    }

    private float initWidthField() {
        PdfNumber wNum = ((PdfStream) getPdfObject()).getAsNumber(PdfName.Width);
        if (wNum != null) {
            this.width = wNum.floatValue();
        }
        return this.width;
    }

    private float initHeightField() {
        PdfNumber hNum = ((PdfStream) getPdfObject()).getAsNumber(PdfName.Height);
        if (hNum != null) {
            this.height = hNum.floatValue();
        }
        return this.height;
    }

    private static PdfStream createPdfStream(ImageData image, PdfImageXObject imageMask) {
        PdfName colorSpace;
        ImageData imageData = image;
        PdfImageXObject pdfImageXObject = imageMask;
        if (image.getOriginalType() == ImageType.RAW) {
            RawImageHelper.updateImageAttributes((RawImageData) imageData, (Map<String, Object>) null);
        }
        PdfStream stream = new PdfStream(image.getData());
        String filter = image.getFilter();
        if (filter != null && "JPXDecode".equals(filter) && image.getColorSpace() <= 0) {
            stream.setCompressionLevel(0);
            imageData.setBpc(0);
        }
        stream.put(PdfName.Type, PdfName.XObject);
        stream.put(PdfName.Subtype, PdfName.Image);
        PdfDictionary decodeParms = createDictionaryFromMap(stream, image.getDecodeParms());
        if (decodeParms != null) {
            stream.put(PdfName.DecodeParms, decodeParms);
        }
        if (!(imageData instanceof PngImageData)) {
            switch (image.getColorSpace()) {
                case 1:
                    colorSpace = PdfName.DeviceGray;
                    break;
                case 3:
                    colorSpace = PdfName.DeviceRGB;
                    break;
                default:
                    colorSpace = PdfName.DeviceCMYK;
                    break;
            }
            stream.put(PdfName.ColorSpace, colorSpace);
        }
        if (image.getBpc() != 0) {
            stream.put(PdfName.BitsPerComponent, new PdfNumber(image.getBpc()));
        }
        if (image.getFilter() != null) {
            stream.put(PdfName.Filter, new PdfName(image.getFilter()));
        }
        if (image.getColorSpace() == -1) {
            stream.remove(PdfName.ColorSpace);
        }
        if (imageData instanceof PngImageData) {
            PngImageData pngImage = (PngImageData) imageData;
            if (pngImage.isIndexed()) {
                PdfArray colorspace = new PdfArray();
                colorspace.add(PdfName.Indexed);
                colorspace.add(getColorSpaceInfo(pngImage));
                if (pngImage.getColorPalette() != null && pngImage.getColorPalette().length > 0) {
                    colorspace.add(new PdfNumber((pngImage.getColorPalette().length / 3) - 1));
                }
                if (pngImage.getColorPalette() != null) {
                    colorspace.add(new PdfString(PdfEncodings.convertToString(pngImage.getColorPalette(), (String) null)));
                }
                stream.put(PdfName.ColorSpace, colorspace);
            } else {
                stream.put(PdfName.ColorSpace, getColorSpaceInfo(pngImage));
            }
        }
        PdfDictionary additional = createDictionaryFromMap(stream, image.getImageAttributes());
        if (additional != null) {
            stream.putAll(additional);
        }
        IccProfile iccProfile = image.getProfile();
        if (iccProfile != null) {
            PdfStream iccProfileStream = PdfCieBasedCs.IccBased.getIccProfileStream(iccProfile);
            PdfArray iccBasedColorSpace = new PdfArray();
            iccBasedColorSpace.add(PdfName.ICCBased);
            iccBasedColorSpace.add(iccProfileStream);
            PdfObject colorSpaceObject = stream.get(PdfName.ColorSpace);
            boolean iccProfileShouldBeApplied = true;
            if (colorSpaceObject != null) {
                PdfColorSpace cs = PdfColorSpace.makeColorSpace(colorSpaceObject);
                Class<PdfImageXObject> cls = PdfImageXObject.class;
                if (cs == null) {
                    LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.f1194xbd0142fe);
                } else if (cs instanceof PdfSpecialCs.Indexed) {
                    PdfColorSpace baseCs = ((PdfSpecialCs.Indexed) cs).getBaseCs();
                    if (baseCs == null) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.f1193x3d959059);
                    } else if (baseCs.getNumberOfComponents() != iccProfile.getNumComponents()) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.f1191xe908ea8);
                        iccProfileShouldBeApplied = false;
                    } else {
                        iccProfileStream.put(PdfName.Alternate, baseCs.getPdfObject());
                    }
                    if (iccProfileShouldBeApplied) {
                        ((PdfArray) colorSpaceObject).set(1, iccBasedColorSpace);
                        iccProfileShouldBeApplied = false;
                    }
                } else if (cs.getNumberOfComponents() != iccProfile.getNumComponents()) {
                    LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.f1192x4563ea8d);
                    iccProfileShouldBeApplied = false;
                } else {
                    iccProfileStream.put(PdfName.Alternate, colorSpaceObject);
                }
            }
            if (iccProfileShouldBeApplied) {
                stream.put(PdfName.ColorSpace, iccBasedColorSpace);
            }
        }
        if (image.isMask() && (image.getBpc() == 1 || image.getBpc() > 255)) {
            stream.put(PdfName.ImageMask, PdfBoolean.TRUE);
        }
        if (pdfImageXObject != null) {
            if (pdfImageXObject.softMask) {
                stream.put(PdfName.SMask, imageMask.getPdfObject());
            } else if (pdfImageXObject.mask) {
                stream.put(PdfName.Mask, imageMask.getPdfObject());
            }
        }
        ImageData mask2 = image.getImageMask();
        if (mask2 != null) {
            if (mask2.isSoftMask()) {
                stream.put(PdfName.SMask, new PdfImageXObject(image.getImageMask()).getPdfObject());
            } else if (mask2.isMask()) {
                stream.put(PdfName.Mask, new PdfImageXObject(image.getImageMask()).getPdfObject());
            }
        }
        if (image.getDecode() != null) {
            stream.put(PdfName.Decode, new PdfArray(image.getDecode()));
        }
        if (image.isMask() && image.isInverted()) {
            stream.put(PdfName.Decode, new PdfArray(new float[]{1.0f, 0.0f}));
        }
        if (image.isInterpolation()) {
            stream.put(PdfName.Interpolate, PdfBoolean.TRUE);
        }
        int[] transparency = image.getTransparency();
        if (transparency != null && !image.isMask() && pdfImageXObject == null) {
            PdfArray t = new PdfArray();
            for (int transparencyItem : transparency) {
                t.add(new PdfNumber(transparencyItem));
            }
            stream.put(PdfName.Mask, t);
        }
        stream.put(PdfName.Width, new PdfNumber((double) image.getWidth()));
        stream.put(PdfName.Height, new PdfNumber((double) image.getHeight()));
        return stream;
    }

    private static PdfDictionary createDictionaryFromMap(PdfStream stream, Map<String, Object> parms) {
        if (parms == null) {
            return null;
        }
        PdfDictionary dictionary = new PdfDictionary();
        for (Map.Entry<String, Object> entry : parms.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            if (value instanceof Integer) {
                dictionary.put(new PdfName(key), new PdfNumber(((Integer) value).intValue()));
            } else if (value instanceof Float) {
                dictionary.put(new PdfName(key), new PdfNumber((double) ((Float) value).floatValue()));
            } else if (value instanceof String) {
                if (value.equals(PngImageHelperConstants.MASK)) {
                    dictionary.put(PdfName.Mask, new PdfLiteral((String) value));
                } else {
                    String str = (String) value;
                    if (str.indexOf(47) == 0) {
                        dictionary.put(new PdfName(key), new PdfName(str.substring(1)));
                    } else {
                        dictionary.put(new PdfName(key), new PdfString(str));
                    }
                }
            } else if (value instanceof byte[]) {
                PdfStream globalsStream = new PdfStream();
                globalsStream.getOutputStream().writeBytes((byte[]) value);
                dictionary.put(PdfName.JBIG2Globals, globalsStream);
            } else if (value instanceof Boolean) {
                dictionary.put(new PdfName(key), PdfBoolean.valueOf(((Boolean) value).booleanValue()));
            } else if (value instanceof Object[]) {
                dictionary.put(new PdfName(key), createArray(stream, (Object[]) value));
            } else if (value instanceof float[]) {
                dictionary.put(new PdfName(key), new PdfArray((float[]) value));
            } else if (value instanceof int[]) {
                dictionary.put(new PdfName(key), new PdfArray((int[]) value));
            }
        }
        return dictionary;
    }

    private static PdfArray createArray(PdfStream stream, Object[] objects) {
        PdfArray array = new PdfArray();
        for (String str : objects) {
            if (str instanceof String) {
                String str2 = str;
                if (str2.indexOf(47) == 0) {
                    array.add(new PdfName(str2.substring(1)));
                } else {
                    array.add(new PdfString(str2));
                }
            } else if (str instanceof Integer) {
                array.add(new PdfNumber(str.intValue()));
            } else if (str instanceof Float) {
                array.add(new PdfNumber((double) str.floatValue()));
            } else if (str instanceof Object[]) {
                array.add(createArray(stream, str));
            } else {
                array.add(createDictionaryFromMap(stream, str));
            }
        }
        return array;
    }

    private static ImageData checkImageType(ImageData image) {
        if (!(image instanceof WmfImageData)) {
            return image;
        }
        throw new PdfException(PdfException.CannotCreatePdfImageXObjectByWmfImage);
    }

    private static PdfObject getColorSpaceInfo(PngImageData pngImageData) {
        if (pngImageData.getProfile() != null) {
            if (pngImageData.isGrayscaleImage()) {
                return PdfName.DeviceGray;
            }
            return PdfName.DeviceRGB;
        } else if (pngImageData.getGamma() != 1.0f || pngImageData.isHasCHRM()) {
            PdfArray array = new PdfArray();
            PdfDictionary map = new PdfDictionary();
            if (!pngImageData.isGrayscaleImage()) {
                float[] wp = {1.0f, 1.0f, 1.0f};
                array.add(PdfName.CalRGB);
                float gamma = pngImageData.getGamma();
                if (gamma != 1.0f) {
                    map.put(PdfName.Gamma, new PdfArray(new float[]{gamma, gamma, gamma}));
                }
                if (pngImageData.isHasCHRM()) {
                    PngChromaticitiesHelper helper = new PngChromaticitiesHelper((C14411) null);
                    helper.constructMatrix(pngImageData);
                    wp = helper.f1520wp;
                    map.put(PdfName.Matrix, new PdfArray(helper.matrix));
                }
                map.put(PdfName.WhitePoint, new PdfArray(wp));
            } else if (pngImageData.getGamma() == 1.0f) {
                return PdfName.DeviceGray;
            } else {
                array.add(PdfName.CalGray);
                map.put(PdfName.Gamma, new PdfNumber((double) pngImageData.getGamma()));
                map.put(PdfName.WhitePoint, new PdfArray(new int[]{1, 1, 1}));
            }
            array.add(map);
            return array;
        } else if (pngImageData.isGrayscaleImage()) {
            return PdfName.DeviceGray;
        } else {
            return PdfName.DeviceRGB;
        }
    }

    private static class PngChromaticitiesHelper {
        float[] matrix;

        /* renamed from: wp */
        float[] f1520wp;

        private PngChromaticitiesHelper() {
            this.matrix = new float[9];
            this.f1520wp = new float[3];
        }

        /* synthetic */ PngChromaticitiesHelper(C14411 x0) {
            this();
        }

        public void constructMatrix(PngImageData pngImageData) {
            PngChromaticities pngChromaticities = pngImageData.getPngChromaticities();
            float z = pngChromaticities.getYW() * ((((pngChromaticities.getXG() - pngChromaticities.getXB()) * pngChromaticities.getYR()) - ((pngChromaticities.getXR() - pngChromaticities.getXB()) * pngChromaticities.getYG())) + ((pngChromaticities.getXR() - pngChromaticities.getXG()) * pngChromaticities.getYB()));
            float YA = (pngChromaticities.getYR() * ((((pngChromaticities.getXG() - pngChromaticities.getXB()) * pngChromaticities.getYW()) - ((pngChromaticities.getXW() - pngChromaticities.getXB()) * pngChromaticities.getYG())) + ((pngChromaticities.getXW() - pngChromaticities.getXG()) * pngChromaticities.getYB()))) / z;
            float XA = (pngChromaticities.getXR() * YA) / pngChromaticities.getYR();
            float ZA = (((1.0f - pngChromaticities.getXR()) / pngChromaticities.getYR()) - 1.0f) * YA;
            float YB = ((-pngChromaticities.getYG()) * ((((pngChromaticities.getXR() - pngChromaticities.getXB()) * pngChromaticities.getYW()) - ((pngChromaticities.getXW() - pngChromaticities.getXB()) * pngChromaticities.getYR())) + ((pngChromaticities.getXW() - pngChromaticities.getXR()) * pngChromaticities.getYB()))) / z;
            float XB = (pngChromaticities.getXG() * YB) / pngChromaticities.getYG();
            float ZB = (((1.0f - pngChromaticities.getXG()) / pngChromaticities.getYG()) - 1.0f) * YB;
            float YC = (pngChromaticities.getYB() * ((((pngChromaticities.getXR() - pngChromaticities.getXG()) * pngChromaticities.getYW()) - ((pngChromaticities.getXW() - pngChromaticities.getXG()) * pngChromaticities.getYW())) + ((pngChromaticities.getXW() - pngChromaticities.getXR()) * pngChromaticities.getYG()))) / z;
            float XC = (pngChromaticities.getXB() * YC) / pngChromaticities.getYB();
            float ZC = (((1.0f - pngChromaticities.getXB()) / pngChromaticities.getYB()) - 1.0f) * YC;
            PngChromaticities pngChromaticities2 = pngChromaticities;
            float f = z;
            this.f1520wp = Arrays.copyOf(new float[]{XA + XB + XC, 1.0f, ZA + ZB + ZC}, 3);
            this.matrix = Arrays.copyOf(new float[]{XA, YA, ZA, XB, YB, ZB, XC, YC, ZC}, 9);
        }
    }
}

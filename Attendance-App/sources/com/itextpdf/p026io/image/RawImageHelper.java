package com.itextpdf.p026io.image;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.codec.CCITTG4Encoder;
import com.itextpdf.p026io.codec.TIFFFaxDecoder;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.image.RawImageHelper */
public final class RawImageHelper {
    public static void updateImageAttributes(RawImageData image, Map<String, Object> additional) {
        if (image.isRawImage()) {
            int colorSpace = image.getColorSpace();
            int typeCCITT = image.getTypeCcitt();
            if (typeCCITT > 255) {
                if (!image.isMask()) {
                    image.setColorSpace(1);
                }
                image.setBpc(1);
                image.setFilter("CCITTFaxDecode");
                int k = typeCCITT - 257;
                Map<String, Object> decodeparms = new HashMap<>();
                if (k != 0) {
                    decodeparms.put("K", Integer.valueOf(k));
                }
                if ((colorSpace & 1) != 0) {
                    decodeparms.put("BlackIs1", true);
                }
                if ((colorSpace & 2) != 0) {
                    decodeparms.put("EncodedByteAlign", true);
                }
                if ((colorSpace & 4) != 0) {
                    decodeparms.put("EndOfLine", true);
                }
                if ((colorSpace & 8) != 0) {
                    decodeparms.put("EndOfBlock", false);
                }
                decodeparms.put(PngImageHelperConstants.COLUMNS, Float.valueOf(image.getWidth()));
                decodeparms.put("Rows", Float.valueOf(image.getHeight()));
                image.decodeParms = decodeparms;
                return;
            }
            switch (colorSpace) {
                case 1:
                    if (image.isInverted()) {
                        image.decode = new float[]{1.0f, 0.0f};
                        break;
                    }
                    break;
                case 3:
                    if (image.isInverted()) {
                        image.decode = new float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f};
                        break;
                    }
                    break;
                default:
                    if (image.isInverted()) {
                        image.decode = new float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f};
                        break;
                    }
                    break;
            }
            if (additional != null) {
                image.setImageAttributes(additional);
            }
            if (image.isMask() && (image.getBpc() == 1 || image.getBpc() > 8)) {
                image.setColorSpace(-1);
            }
            if (image.isDeflated()) {
                image.setFilter("FlateDecode");
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Raw image expected.");
    }

    protected static void updateRawImageParameters(RawImageData image, int width, int height, int components, int bpc, byte[] data) {
        image.setHeight((float) height);
        image.setWidth((float) width);
        if (components != 1 && components != 3 && components != 4) {
            throw new IOException(IOException.ComponentsMustBe1_3Or4);
        } else if (bpc == 1 || bpc == 2 || bpc == 4 || bpc == 8) {
            image.setColorSpace(components);
            image.setBpc(bpc);
            image.data = data;
        } else {
            throw new IOException(IOException.BitsPerComponentMustBe1_2_4or8);
        }
    }

    protected static void updateRawImageParameters(RawImageData image, int width, int height, int components, int bpc, byte[] data, int[] transparency) {
        if (transparency != null && transparency.length != components * 2) {
            throw new IOException(IOException.TransparencyLengthMustBeEqualTo2WithCcittImages);
        } else if (components == 1 && bpc == 1) {
            updateRawImageParameters(image, width, height, false, 256, 1, CCITTG4Encoder.compress(data, width, height), transparency);
        } else {
            updateRawImageParameters(image, width, height, components, bpc, data);
            image.setTransparency(transparency);
        }
    }

    protected static void updateRawImageParameters(RawImageData image, int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data, int[] transparency) {
        if (transparency == null || transparency.length == 2) {
            updateCcittImageParameters(image, width, height, reverseBits, typeCCITT, parameters, data);
            image.setTransparency(transparency);
            return;
        }
        throw new IOException(IOException.TransparencyLengthMustBeEqualTo2WithCcittImages);
    }

    protected static void updateCcittImageParameters(RawImageData image, int width, int height, boolean reverseBits, int typeCcitt, int parameters, byte[] data) {
        if (typeCcitt == 256 || typeCcitt == 257 || typeCcitt == 258) {
            if (reverseBits) {
                TIFFFaxDecoder.reverseBits(data);
            }
            image.setHeight((float) height);
            image.setWidth((float) width);
            image.setColorSpace(parameters);
            image.setTypeCcitt(typeCcitt);
            image.data = data;
            return;
        }
        throw new IOException(IOException.CcittCompressionTypeMustBeCcittg4Ccittg3_1dOrCcittg3_2d);
    }
}

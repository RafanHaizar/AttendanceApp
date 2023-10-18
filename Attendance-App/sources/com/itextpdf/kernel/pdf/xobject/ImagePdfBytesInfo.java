package com.itextpdf.kernel.pdf.xobject;

import com.itextpdf.kernel.Version;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.codec.PngWriter;
import com.itextpdf.p026io.codec.TIFFConstants;
import com.itextpdf.p026io.codec.TiffWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import kotlin.UByte;

class ImagePdfBytesInfo {
    private int bpc;
    private PdfObject colorspace;
    private PdfArray decode;
    private int height;
    private byte[] icc;
    private byte[] palette;
    private int pngBitDepth;
    private int pngColorType = -1;
    private int stride;
    private int width;

    public ImagePdfBytesInfo(PdfImageXObject imageXObject) {
        int intValue = ((PdfStream) imageXObject.getPdfObject()).getAsNumber(PdfName.BitsPerComponent).intValue();
        this.bpc = intValue;
        this.pngBitDepth = intValue;
        this.palette = null;
        this.icc = null;
        this.stride = 0;
        this.width = (int) imageXObject.getWidth();
        this.height = (int) imageXObject.getHeight();
        this.colorspace = ((PdfStream) imageXObject.getPdfObject()).get(PdfName.ColorSpace);
        this.decode = ((PdfStream) imageXObject.getPdfObject()).getAsArray(PdfName.Decode);
        findColorspace(this.colorspace, true);
    }

    public int getPngColorType() {
        return this.pngColorType;
    }

    public byte[] decodeTiffAndPngBytes(byte[] imageBytes) throws IOException {
        ByteArrayOutputStream ms = new ByteArrayOutputStream();
        if (this.pngColorType >= 0) {
            PngWriter png = new PngWriter(ms);
            PdfArray pdfArray = this.decode;
            if (pdfArray != null && this.pngBitDepth == 1 && pdfArray.getAsNumber(0).intValue() == 1 && this.decode.getAsNumber(1).intValue() == 0) {
                int len = imageBytes.length;
                for (int t = 0; t < len; t++) {
                    imageBytes[t] = (byte) (imageBytes[t] ^ UByte.MAX_VALUE);
                }
            }
            png.writeHeader(this.width, this.height, this.pngBitDepth, this.pngColorType);
            byte[] bArr = this.icc;
            if (bArr != null) {
                png.writeIccProfile(bArr);
            }
            byte[] bArr2 = this.palette;
            if (bArr2 != null) {
                png.writePalette(bArr2);
            }
            png.writeData(imageBytes, this.stride);
            png.writeEnd();
            return ms.toByteArray();
        } else if (this.bpc == 8) {
            PdfObject pdfObject = this.colorspace;
            if (pdfObject instanceof PdfArray) {
                PdfArray ca = (PdfArray) pdfObject;
                PdfObject tyca = ca.get(0);
                if (PdfName.ICCBased.equals(tyca)) {
                    PdfStream pr = (PdfStream) ca.get(1);
                    int n = pr.getAsNumber(PdfName.f1357N).intValue();
                    if (n == 4) {
                        this.icc = pr.getBytes();
                    } else {
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.NValueIsNotSupported).setMessageParams(Integer.valueOf(n));
                    }
                } else {
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ColorSpaceIsNotSupported).setMessageParams(tyca.toString());
                }
            } else if (!PdfName.DeviceCMYK.equals(this.colorspace)) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ColorSpaceIsNotSupported).setMessageParams(this.colorspace.toString());
            }
            this.stride = this.width * 4;
            TiffWriter wr = new TiffWriter();
            wr.addField(new TiffWriter.FieldShort((int) TIFFConstants.TIFFTAG_SAMPLESPERPIXEL, 4));
            wr.addField(new TiffWriter.FieldShort(258, new int[]{8, 8, 8, 8}));
            wr.addField(new TiffWriter.FieldShort(262, 5));
            wr.addField(new TiffWriter.FieldLong(256, this.width));
            wr.addField(new TiffWriter.FieldLong(257, this.height));
            wr.addField(new TiffWriter.FieldShort(259, 5));
            wr.addField(new TiffWriter.FieldShort(317, 2));
            wr.addField(new TiffWriter.FieldLong((int) TIFFConstants.TIFFTAG_ROWSPERSTRIP, this.height));
            wr.addField(new TiffWriter.FieldRational((int) TIFFConstants.TIFFTAG_XRESOLUTION, new int[]{300, 1}));
            wr.addField(new TiffWriter.FieldRational((int) TIFFConstants.TIFFTAG_YRESOLUTION, new int[]{300, 1}));
            wr.addField(new TiffWriter.FieldShort((int) TIFFConstants.TIFFTAG_RESOLUTIONUNIT, 2));
            wr.addField(new TiffWriter.FieldAscii(305, Version.getInstance().getVersion()));
            ByteArrayOutputStream comp = new ByteArrayOutputStream();
            TiffWriter.compressLZW(comp, 2, imageBytes, this.height, 4, this.stride);
            byte[] buf = comp.toByteArray();
            wr.addField(new TiffWriter.FieldImage(buf));
            wr.addField(new TiffWriter.FieldLong((int) TIFFConstants.TIFFTAG_STRIPBYTECOUNTS, buf.length));
            if (this.icc != null) {
                wr.addField(new TiffWriter.FieldUndefined(TIFFConstants.TIFFTAG_ICCPROFILE, this.icc));
            }
            wr.writeFile(ms);
            return ms.toByteArray();
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ColorDepthIsNotSupported).setMessageParams(Integer.valueOf(this.bpc));
        }
    }

    private void findColorspace(PdfObject csObj, boolean allowIndexed) {
        if (PdfName.DeviceGray.equals(csObj) || (csObj == null && this.bpc == 1)) {
            this.stride = ((this.width * this.bpc) + 7) / 8;
            this.pngColorType = 0;
        } else if (PdfName.DeviceRGB.equals(csObj)) {
            int i = this.bpc;
            if (i == 8 || i == 16) {
                this.stride = (((this.width * i) * 3) + 7) / 8;
                this.pngColorType = 2;
            }
        } else if (csObj instanceof PdfArray) {
            PdfArray ca = (PdfArray) csObj;
            PdfObject tyca = ca.get(0);
            if (PdfName.CalGray.equals(tyca)) {
                this.stride = ((this.width * this.bpc) + 7) / 8;
                this.pngColorType = 0;
            } else if (PdfName.CalRGB.equals(tyca)) {
                int i2 = this.bpc;
                if (i2 == 8 || i2 == 16) {
                    this.stride = (((this.width * i2) * 3) + 7) / 8;
                    this.pngColorType = 2;
                }
            } else if (PdfName.ICCBased.equals(tyca)) {
                PdfStream pr = (PdfStream) ca.get(1);
                int n = pr.getAsNumber(PdfName.f1357N).intValue();
                if (n == 1) {
                    this.stride = ((this.width * this.bpc) + 7) / 8;
                    this.pngColorType = 0;
                    this.icc = pr.getBytes();
                } else if (n == 3) {
                    this.stride = (((this.width * this.bpc) * 3) + 7) / 8;
                    this.pngColorType = 2;
                    this.icc = pr.getBytes();
                }
            } else if (allowIndexed && PdfName.Indexed.equals(tyca)) {
                findColorspace(ca.get(1), false);
                if (this.pngColorType == 2) {
                    PdfObject id2 = ca.get(3);
                    if (id2 instanceof PdfString) {
                        this.palette = ((PdfString) id2).getValueBytes();
                    } else if (id2 instanceof PdfStream) {
                        this.palette = ((PdfStream) id2).getBytes();
                    }
                    this.stride = ((this.width * this.bpc) + 7) / 8;
                    this.pngColorType = 3;
                }
            }
        }
    }
}

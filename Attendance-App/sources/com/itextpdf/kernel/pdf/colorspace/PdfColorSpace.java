package com.itextpdf.kernel.pdf.colorspace;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class PdfColorSpace extends PdfObjectWrapper<PdfObject> {
    public static final Set<PdfName> directColorSpaces = new HashSet(Arrays.asList(new PdfName[]{PdfName.DeviceGray, PdfName.DeviceRGB, PdfName.DeviceCMYK, PdfName.Pattern}));
    private static final long serialVersionUID = 2553991039779429813L;

    public abstract int getNumberOfComponents();

    protected PdfColorSpace(PdfObject pdfObject) {
        super(pdfObject);
    }

    public static PdfColorSpace makeColorSpace(PdfObject pdfObject) {
        if (pdfObject.isIndirectReference()) {
            pdfObject = ((PdfIndirectReference) pdfObject).getRefersTo();
        }
        if (pdfObject.isArray() && ((PdfArray) pdfObject).size() == 1) {
            pdfObject = ((PdfArray) pdfObject).get(0);
        }
        if (PdfName.DeviceGray.equals(pdfObject)) {
            return new PdfDeviceCs.Gray();
        }
        if (PdfName.DeviceRGB.equals(pdfObject)) {
            return new PdfDeviceCs.Rgb();
        }
        if (PdfName.DeviceCMYK.equals(pdfObject)) {
            return new PdfDeviceCs.Cmyk();
        }
        if (PdfName.Pattern.equals(pdfObject)) {
            return new PdfSpecialCs.Pattern();
        }
        if (!pdfObject.isArray()) {
            return null;
        }
        PdfArray array = (PdfArray) pdfObject;
        PdfName csType = array.getAsName(0);
        if (PdfName.CalGray.equals(csType)) {
            return new PdfCieBasedCs.CalGray(array);
        }
        if (PdfName.CalRGB.equals(csType)) {
            return new PdfCieBasedCs.CalRgb(array);
        }
        if (PdfName.Lab.equals(csType)) {
            return new PdfCieBasedCs.Lab(array);
        }
        if (PdfName.ICCBased.equals(csType)) {
            return new PdfCieBasedCs.IccBased(array);
        }
        if (PdfName.Indexed.equals(csType)) {
            return new PdfSpecialCs.Indexed(array);
        }
        if (PdfName.Separation.equals(csType)) {
            return new PdfSpecialCs.Separation(array);
        }
        if (PdfName.DeviceN.equals(csType)) {
            return array.size() == 4 ? new PdfSpecialCs.DeviceN(array) : new PdfSpecialCs.NChannel(array);
        }
        if (PdfName.Pattern.equals(csType)) {
            return new PdfSpecialCs.UncoloredTilingPattern(array);
        }
        return null;
    }
}

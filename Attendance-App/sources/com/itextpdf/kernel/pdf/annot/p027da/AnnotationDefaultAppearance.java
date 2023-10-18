package com.itextpdf.kernel.pdf.annot.p027da;

import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.svg.SvgConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.kernel.pdf.annot.da.AnnotationDefaultAppearance */
public class AnnotationDefaultAppearance {
    private static final Map<ExtendedAnnotationFont, String> extAnnotFontNames;
    private static final Map<StandardAnnotationFont, String> stdAnnotFontNames;
    private String colorOperand = "0 g";
    private float fontSize = 0.0f;
    private String rawFontName = "/Helv";

    static {
        HashMap hashMap = new HashMap();
        stdAnnotFontNames = hashMap;
        HashMap hashMap2 = new HashMap();
        extAnnotFontNames = hashMap2;
        hashMap.put(StandardAnnotationFont.CourierBoldOblique, "/Courier-BoldOblique");
        hashMap.put(StandardAnnotationFont.CourierBold, "/Courier-Bold");
        hashMap.put(StandardAnnotationFont.CourierOblique, "/Courier-Oblique");
        hashMap.put(StandardAnnotationFont.Courier, "/Courier");
        hashMap.put(StandardAnnotationFont.HelveticaBoldOblique, "/Helvetica-BoldOblique");
        hashMap.put(StandardAnnotationFont.HelveticaBold, "/Helvetica-Bold");
        hashMap.put(StandardAnnotationFont.HelveticaOblique, "/Courier-Oblique");
        hashMap.put(StandardAnnotationFont.Helvetica, "/Helvetica");
        hashMap.put(StandardAnnotationFont.Symbol, "/Symbol");
        hashMap.put(StandardAnnotationFont.TimesBoldItalic, "/Times-BoldItalic");
        hashMap.put(StandardAnnotationFont.TimesBold, "/Times-Bold");
        hashMap.put(StandardAnnotationFont.TimesItalic, "/Times-Italic");
        hashMap.put(StandardAnnotationFont.TimesRoman, "/Times-Roman");
        hashMap.put(StandardAnnotationFont.ZapfDingbats, "/ZapfDingbats");
        hashMap2.put(ExtendedAnnotationFont.HYSMyeongJoMedium, "/HySm");
        hashMap2.put(ExtendedAnnotationFont.HYGoThicMedium, "/HyGo");
        hashMap2.put(ExtendedAnnotationFont.HeiseiKakuGoW5, "/KaGo");
        hashMap2.put(ExtendedAnnotationFont.HeiseiMinW3, "/KaMi");
        hashMap2.put(ExtendedAnnotationFont.MHeiMedium, "/MHei");
        hashMap2.put(ExtendedAnnotationFont.MSungLight, "/MSun");
        hashMap2.put(ExtendedAnnotationFont.STSongLight, "/STSo");
        hashMap2.put(ExtendedAnnotationFont.MSungStdLight, "/MSun");
        hashMap2.put(ExtendedAnnotationFont.STSongStdLight, "/STSo");
        hashMap2.put(ExtendedAnnotationFont.HYSMyeongJoStdMedium, "/HySm");
        hashMap2.put(ExtendedAnnotationFont.KozMinProRegular, "/KaMi");
    }

    public AnnotationDefaultAppearance() {
        setFont(StandardAnnotationFont.Helvetica);
        setFontSize(12.0f);
    }

    public AnnotationDefaultAppearance setFont(StandardAnnotationFont font) {
        setRawFontName(stdAnnotFontNames.get(font));
        return this;
    }

    public AnnotationDefaultAppearance setFont(ExtendedAnnotationFont font) {
        setRawFontName(extAnnotFontNames.get(font));
        return this;
    }

    public AnnotationDefaultAppearance setFontSize(float fontSize2) {
        this.fontSize = fontSize2;
        return this;
    }

    public AnnotationDefaultAppearance setColor(DeviceRgb rgbColor) {
        setColorOperand(rgbColor.getColorValue(), "rg");
        return this;
    }

    public AnnotationDefaultAppearance setColor(DeviceCmyk cmykColor) {
        setColorOperand(cmykColor.getColorValue(), "k");
        return this;
    }

    public AnnotationDefaultAppearance setColor(DeviceGray grayColor) {
        setColorOperand(grayColor.getColorValue(), SvgConstants.Tags.f1648G);
        return this;
    }

    public PdfString toPdfString() {
        return new PdfString(MessageFormatUtil.format("{0} {1} Tf {2}", this.rawFontName, Float.valueOf(this.fontSize), this.colorOperand));
    }

    private void setColorOperand(float[] colorValues, String operand) {
        StringBuilder builder = new StringBuilder();
        int length = colorValues.length;
        for (int i = 0; i < length; i++) {
            builder.append(MessageFormatUtil.format("{0} ", Float.valueOf(colorValues[i])));
        }
        builder.append(operand);
        this.colorOperand = builder.toString();
    }

    private void setRawFontName(String rawFontName2) {
        if (rawFontName2 != null) {
            this.rawFontName = rawFontName2;
            return;
        }
        throw new IllegalArgumentException("Passed raw font name can not be null");
    }
}

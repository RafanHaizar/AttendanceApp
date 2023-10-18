package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;

public class PatternColor extends Color {
    private static final long serialVersionUID = -2405470180325720440L;
    private PdfPattern pattern;
    private Color underlyingColor;

    public PatternColor(PdfPattern coloredPattern) {
        super(new PdfSpecialCs.Pattern(), (float[]) null);
        this.pattern = coloredPattern;
    }

    public PatternColor(PdfPattern.Tiling uncoloredPattern, Color color) {
        this(uncoloredPattern, color.getColorSpace(), color.getColorValue());
    }

    public PatternColor(PdfPattern.Tiling uncoloredPattern, PdfColorSpace underlyingCS, float[] colorValue) {
        this(uncoloredPattern, new PdfSpecialCs.UncoloredTilingPattern(ensureNotPatternCs(underlyingCS)), colorValue);
    }

    public PatternColor(PdfPattern.Tiling uncoloredPattern, PdfSpecialCs.UncoloredTilingPattern uncoloredTilingCS, float[] colorValue) {
        super(uncoloredTilingCS, colorValue);
        this.pattern = uncoloredPattern;
        this.underlyingColor = makeColor(uncoloredTilingCS.getUnderlyingColorSpace(), colorValue);
    }

    public PdfPattern getPattern() {
        return this.pattern;
    }

    public void setColorValue(float[] value) {
        super.setColorValue(value);
        this.underlyingColor.setColorValue(value);
    }

    @Deprecated
    public void setPattern(PdfPattern pattern2) {
        this.pattern = pattern2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0026 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            boolean r0 = super.equals(r5)
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            r0 = r5
            com.itextpdf.kernel.colors.PatternColor r0 = (com.itextpdf.kernel.colors.PatternColor) r0
            com.itextpdf.kernel.pdf.colorspace.PdfPattern r2 = r4.pattern
            com.itextpdf.kernel.pdf.colorspace.PdfPattern r3 = r0.pattern
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0028
            com.itextpdf.kernel.colors.Color r2 = r4.underlyingColor
            if (r2 == 0) goto L_0x0022
            com.itextpdf.kernel.colors.Color r3 = r0.underlyingColor
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0028
            goto L_0x0026
        L_0x0022:
            com.itextpdf.kernel.colors.Color r2 = r0.underlyingColor
            if (r2 != 0) goto L_0x0028
        L_0x0026:
            r1 = 1
            goto L_0x0029
        L_0x0028:
        L_0x0029:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.colors.PatternColor.equals(java.lang.Object):boolean");
    }

    private static PdfColorSpace ensureNotPatternCs(PdfColorSpace underlyingCS) {
        if (!(underlyingCS instanceof PdfSpecialCs.Pattern)) {
            return underlyingCS;
        }
        throw new IllegalArgumentException("underlyingCS");
    }
}

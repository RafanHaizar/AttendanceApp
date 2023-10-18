package com.itextpdf.kernel.pdf.canvas;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CanvasGraphicsState implements Serializable {
    private static final long serialVersionUID = -9151840268986283292L;
    private boolean alphaIsShape = false;
    private boolean automaticStrokeAdjustment = false;
    private PdfObject blackGenerationFunction;
    private PdfObject blackGenerationFunction2;
    private PdfObject blendMode = PdfName.Normal;
    private float charSpacing = 0.0f;
    private Matrix ctm = new Matrix();
    private PdfArray dashPattern = new PdfArray((List<? extends PdfObject>) Arrays.asList(new PdfObject[]{new PdfArray(), new PdfNumber(0)}));
    private float fillAlpha = 1.0f;
    private Color fillColor = DeviceGray.BLACK;
    private boolean fillOverprint = false;
    private float flatnessTolerance = 1.0f;
    private PdfFont font;
    private float fontSize;
    private PdfObject halftone;
    private PdfObject htp;
    private float leading = 0.0f;
    private int lineCapStyle = 0;
    private int lineJoinStyle = 0;
    private float lineWidth = 1.0f;
    private float miterLimit = 10.0f;
    private int overprintMode = 0;
    private PdfName renderingIntent = PdfName.RelativeColorimetric;
    private float scale = 100.0f;
    private Float smoothnessTolerance;
    private PdfObject softMask = PdfName.None;
    private float strokeAlpha = 1.0f;
    private Color strokeColor = DeviceGray.BLACK;
    private boolean strokeOverprint = false;
    private boolean textKnockout = true;
    private int textRenderingMode = 0;
    private float textRise = 0.0f;
    private PdfObject transferFunction;
    private PdfObject transferFunction2;
    private PdfObject underColorRemovalFunction;
    private PdfObject underColorRemovalFunction2;
    private float wordSpacing = 0.0f;

    protected CanvasGraphicsState() {
    }

    public CanvasGraphicsState(CanvasGraphicsState source) {
        copyFrom(source);
    }

    public void updateFromExtGState(PdfDictionary extGState) {
        updateFromExtGState(new PdfExtGState(extGState), extGState.getIndirectReference() == null ? null : extGState.getIndirectReference().getDocument());
    }

    public Matrix getCtm() {
        return this.ctm;
    }

    public void updateCtm(float a, float b, float c, float d, float e, float f) {
        updateCtm(new Matrix(a, b, c, d, e, f));
    }

    public void updateCtm(Matrix newCtm) {
        this.ctm = newCtm.multiply(this.ctm);
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(Color fillColor2) {
        this.fillColor = fillColor2;
    }

    public Color getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(Color strokeColor2) {
        this.strokeColor = strokeColor2;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(float lineWidth2) {
        this.lineWidth = lineWidth2;
    }

    public int getLineCapStyle() {
        return this.lineCapStyle;
    }

    public void setLineCapStyle(int lineCapStyle2) {
        this.lineCapStyle = lineCapStyle2;
    }

    public int getLineJoinStyle() {
        return this.lineJoinStyle;
    }

    public void setLineJoinStyle(int lineJoinStyle2) {
        this.lineJoinStyle = lineJoinStyle2;
    }

    public float getMiterLimit() {
        return this.miterLimit;
    }

    public void setMiterLimit(float miterLimit2) {
        this.miterLimit = miterLimit2;
    }

    public PdfArray getDashPattern() {
        return this.dashPattern;
    }

    public void setDashPattern(PdfArray dashPattern2) {
        this.dashPattern = dashPattern2;
    }

    public PdfName getRenderingIntent() {
        return this.renderingIntent;
    }

    public void setRenderingIntent(PdfName renderingIntent2) {
        this.renderingIntent = renderingIntent2;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(float fontSize2) {
        this.fontSize = fontSize2;
    }

    public PdfFont getFont() {
        return this.font;
    }

    public void setFont(PdfFont font2) {
        this.font = font2;
    }

    public int getTextRenderingMode() {
        return this.textRenderingMode;
    }

    public void setTextRenderingMode(int textRenderingMode2) {
        this.textRenderingMode = textRenderingMode2;
    }

    public float getTextRise() {
        return this.textRise;
    }

    public void setTextRise(float textRise2) {
        this.textRise = textRise2;
    }

    public float getFlatnessTolerance() {
        return this.flatnessTolerance;
    }

    public void setFlatnessTolerance(float flatnessTolerance2) {
        this.flatnessTolerance = flatnessTolerance2;
    }

    public void setWordSpacing(float wordSpacing2) {
        this.wordSpacing = wordSpacing2;
    }

    public float getWordSpacing() {
        return this.wordSpacing;
    }

    public void setCharSpacing(float characterSpacing) {
        this.charSpacing = characterSpacing;
    }

    public float getCharSpacing() {
        return this.charSpacing;
    }

    public float getLeading() {
        return this.leading;
    }

    public void setLeading(float leading2) {
        this.leading = leading2;
    }

    public float getHorizontalScaling() {
        return this.scale;
    }

    public void setHorizontalScaling(float scale2) {
        this.scale = scale2;
    }

    public boolean getStrokeOverprint() {
        return this.strokeOverprint;
    }

    public boolean getFillOverprint() {
        return this.fillOverprint;
    }

    public int getOverprintMode() {
        return this.overprintMode;
    }

    public PdfObject getBlackGenerationFunction() {
        return this.blackGenerationFunction;
    }

    public PdfObject getBlackGenerationFunction2() {
        return this.blackGenerationFunction2;
    }

    public PdfObject getUnderColorRemovalFunction() {
        return this.underColorRemovalFunction;
    }

    public PdfObject getUnderColorRemovalFunction2() {
        return this.underColorRemovalFunction2;
    }

    public PdfObject getTransferFunction() {
        return this.transferFunction;
    }

    public PdfObject getTransferFunction2() {
        return this.transferFunction2;
    }

    public PdfObject getHalftone() {
        return this.halftone;
    }

    public Float getSmoothnessTolerance() {
        return this.smoothnessTolerance;
    }

    public boolean getAutomaticStrokeAdjustment() {
        return this.automaticStrokeAdjustment;
    }

    public PdfObject getBlendMode() {
        return this.blendMode;
    }

    public PdfObject getSoftMask() {
        return this.softMask;
    }

    public float getStrokeOpacity() {
        return this.strokeAlpha;
    }

    public float getFillOpacity() {
        return this.fillAlpha;
    }

    public boolean getAlphaIsShape() {
        return this.alphaIsShape;
    }

    public boolean getTextKnockout() {
        return this.textKnockout;
    }

    public PdfObject getHTP() {
        return this.htp;
    }

    public void updateFromExtGState(PdfExtGState extGState) {
        updateFromExtGState(extGState, (PdfDocument) null);
    }

    /* access modifiers changed from: package-private */
    public void updateFromExtGState(PdfExtGState extGState, PdfDocument pdfDocument) {
        Float lw = extGState.getLineWidth();
        if (lw != null) {
            this.lineWidth = lw.floatValue();
        }
        Integer lc = extGState.getLineCapStyle();
        if (lc != null) {
            this.lineCapStyle = lc.intValue();
        }
        Integer lj = extGState.getLineJoinStyle();
        if (lj != null) {
            this.lineJoinStyle = lj.intValue();
        }
        Float ml = extGState.getMiterLimit();
        if (ml != null) {
            this.miterLimit = ml.floatValue();
        }
        PdfArray d = extGState.getDashPattern();
        if (d != null) {
            this.dashPattern = d;
        }
        PdfName ri = extGState.getRenderingIntent();
        if (ri != null) {
            this.renderingIntent = ri;
        }
        Boolean op = extGState.getStrokeOverprintFlag();
        if (op != null) {
            this.strokeOverprint = op.booleanValue();
        }
        Boolean op2 = extGState.getFillOverprintFlag();
        if (op2 != null) {
            this.fillOverprint = op2.booleanValue();
        }
        Integer opm = extGState.getOverprintMode();
        if (opm != null) {
            this.overprintMode = opm.intValue();
        }
        PdfArray fnt = extGState.getFont();
        if (fnt != null) {
            PdfDictionary fontDictionary = fnt.getAsDictionary(0);
            PdfFont pdfFont = this.font;
            if (pdfFont == null || pdfFont.getPdfObject() != fontDictionary) {
                this.font = pdfDocument.getFont(fontDictionary);
            } else {
                PdfDocument pdfDocument2 = pdfDocument;
            }
            PdfNumber fntSz = fnt.getAsNumber(1);
            if (fntSz != null) {
                this.fontSize = fntSz.floatValue();
            }
        } else {
            PdfDocument pdfDocument3 = pdfDocument;
        }
        PdfObject bg = extGState.getBlackGenerationFunction();
        if (bg != null) {
            this.blackGenerationFunction = bg;
        }
        PdfObject bg2 = extGState.getBlackGenerationFunction2();
        if (bg2 != null) {
            this.blackGenerationFunction2 = bg2;
        }
        PdfObject ucr = extGState.getUndercolorRemovalFunction();
        if (ucr != null) {
            this.underColorRemovalFunction = ucr;
        }
        PdfObject ucr2 = extGState.getUndercolorRemovalFunction2();
        if (ucr2 != null) {
            this.underColorRemovalFunction2 = ucr2;
        }
        PdfObject tr = extGState.getTransferFunction();
        if (tr != null) {
            this.transferFunction = tr;
        }
        Float f = lw;
        PdfObject tr2 = extGState.getTransferFunction2();
        if (tr2 != null) {
            this.transferFunction2 = tr2;
        }
        PdfObject pdfObject = tr2;
        PdfObject tr22 = extGState.getHalftone();
        if (tr22 != null) {
            this.halftone = tr22;
        }
        PdfObject pdfObject2 = tr22;
        Integer num = lc;
        PdfObject local_htp = ((PdfDictionary) extGState.getPdfObject()).get(PdfName.HTP);
        if (local_htp != null) {
            this.htp = local_htp;
        }
        Float fl = extGState.getFlatnessTolerance();
        if (fl != null) {
            PdfObject pdfObject3 = local_htp;
            this.flatnessTolerance = fl.floatValue();
        }
        Float sm = extGState.getSmothnessTolerance();
        if (sm != null) {
            this.smoothnessTolerance = sm;
        }
        Boolean sa = extGState.getAutomaticStrokeAdjustmentFlag();
        if (sa != null) {
            Float f2 = sm;
            this.automaticStrokeAdjustment = sa.booleanValue();
        }
        PdfObject bm = extGState.getBlendMode();
        if (bm != null) {
            this.blendMode = bm;
        }
        PdfObject pdfObject4 = bm;
        PdfObject bm2 = extGState.getSoftMask();
        if (bm2 != null) {
            this.softMask = bm2;
        }
        Float ca = extGState.getStrokeOpacity();
        if (ca != null) {
            PdfObject pdfObject5 = bm2;
            this.strokeAlpha = ca.floatValue();
        } else {
            PdfObject sMask = bm2;
        }
        Float ca2 = extGState.getFillOpacity();
        if (ca2 != null) {
            Float f3 = fl;
            this.fillAlpha = ca2.floatValue();
        }
        Boolean ais = extGState.getAlphaSourceFlag();
        if (ais != null) {
            Float f4 = ca2;
            this.alphaIsShape = ais.booleanValue();
        }
        Boolean tk = extGState.getTextKnockoutFlag();
        if (tk != null) {
            Boolean bool = ais;
            this.textKnockout = tk.booleanValue();
            return;
        }
    }

    private void copyFrom(CanvasGraphicsState source) {
        this.ctm = source.ctm;
        this.strokeColor = source.strokeColor;
        this.fillColor = source.fillColor;
        this.charSpacing = source.charSpacing;
        this.wordSpacing = source.wordSpacing;
        this.scale = source.scale;
        this.leading = source.leading;
        this.font = source.font;
        this.fontSize = source.fontSize;
        this.textRenderingMode = source.textRenderingMode;
        this.textRise = source.textRise;
        this.textKnockout = source.textKnockout;
        this.lineWidth = source.lineWidth;
        this.lineCapStyle = source.lineCapStyle;
        this.lineJoinStyle = source.lineJoinStyle;
        this.miterLimit = source.miterLimit;
        this.dashPattern = source.dashPattern;
        this.renderingIntent = source.renderingIntent;
        this.automaticStrokeAdjustment = source.automaticStrokeAdjustment;
        this.blendMode = source.blendMode;
        this.softMask = source.softMask;
        this.strokeAlpha = source.strokeAlpha;
        this.fillAlpha = source.fillAlpha;
        this.alphaIsShape = source.alphaIsShape;
        this.strokeOverprint = source.strokeOverprint;
        this.fillOverprint = source.fillOverprint;
        this.overprintMode = source.overprintMode;
        this.blackGenerationFunction = source.blackGenerationFunction;
        this.blackGenerationFunction2 = source.blackGenerationFunction2;
        this.underColorRemovalFunction = source.underColorRemovalFunction;
        this.underColorRemovalFunction2 = source.underColorRemovalFunction2;
        this.transferFunction = source.transferFunction;
        this.transferFunction2 = source.transferFunction2;
        this.halftone = source.halftone;
        this.flatnessTolerance = source.flatnessTolerance;
        this.smoothnessTolerance = source.smoothnessTolerance;
        this.htp = source.htp;
    }
}

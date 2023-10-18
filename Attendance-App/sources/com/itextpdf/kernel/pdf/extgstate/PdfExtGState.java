package com.itextpdf.kernel.pdf.extgstate;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfExtGState extends PdfObjectWrapper<PdfDictionary> {
    public static PdfName BM_COLOR = PdfName.Color;
    public static PdfName BM_COLOR_BURN = PdfName.ColorBurn;
    public static PdfName BM_COLOR_DODGE = PdfName.ColorDodge;
    public static PdfName BM_DARKEN = PdfName.Darken;
    public static PdfName BM_DIFFERENCE = PdfName.Difference;
    public static PdfName BM_EXCLUSION = PdfName.Exclusion;
    public static PdfName BM_HARD_LIGHT = PdfName.HardLight;
    public static PdfName BM_HUE = PdfName.Hue;
    public static PdfName BM_LIGHTEN = PdfName.Lighten;
    public static PdfName BM_LUMINOSITY = PdfName.Luminosity;
    public static PdfName BM_MULTIPLY = PdfName.Multiply;
    public static PdfName BM_NORMAL = PdfName.Normal;
    public static PdfName BM_OVERLAY = PdfName.Overlay;
    public static PdfName BM_SATURATION = PdfName.Saturation;
    public static PdfName BM_SCREEN = PdfName.Screen;
    public static PdfName BM_SOFT_LIGHT = PdfName.SoftLight;
    private static final long serialVersionUID = 5205219918362853395L;

    public PdfExtGState(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfExtGState() {
        this(new PdfDictionary());
    }

    public Float getLineWidth() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1351LW);
    }

    public PdfExtGState setLineWidth(float lineWidth) {
        return put(PdfName.f1351LW, new PdfNumber((double) lineWidth));
    }

    public Integer getLineCapStyle() {
        return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.f1346LC);
    }

    public PdfExtGState setLineCapStyle(int lineCapStyle) {
        return put(PdfName.f1346LC, new PdfNumber(lineCapStyle));
    }

    public Integer getLineJoinStyle() {
        return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.f1349LJ);
    }

    public PdfExtGState setLineJoinStyle(int lineJoinStyle) {
        return put(PdfName.f1349LJ, new PdfNumber(lineJoinStyle));
    }

    public Float getMiterLimit() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1354ML);
    }

    public PdfExtGState setMiterLimit(float miterLimit) {
        return put(PdfName.f1354ML, new PdfNumber((double) miterLimit));
    }

    public PdfArray getDashPattern() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1312D);
    }

    public PdfExtGState setDashPattern(PdfArray dashPattern) {
        return put(PdfName.f1312D, dashPattern);
    }

    public PdfName getRenderingIntent() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1380RI);
    }

    public PdfExtGState setRenderingIntent(PdfName renderingIntent) {
        return put(PdfName.f1380RI, renderingIntent);
    }

    public Boolean getStrokeOverprintFlag() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1365OP);
    }

    public PdfExtGState setStrokeOverPrintFlag(boolean strokeOverPrintFlag) {
        return put(PdfName.f1365OP, PdfBoolean.valueOf(strokeOverPrintFlag));
    }

    public Boolean getFillOverprintFlag() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1418op);
    }

    public PdfExtGState setFillOverPrintFlag(boolean fillOverprintFlag) {
        return put(PdfName.f1418op, PdfBoolean.valueOf(fillOverprintFlag));
    }

    public Integer getOverprintMode() {
        return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.OPM);
    }

    public PdfExtGState setOverprintMode(int overprintMode) {
        return put(PdfName.OPM, new PdfNumber(overprintMode));
    }

    public PdfArray getFont() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Font);
    }

    public PdfExtGState setFont(PdfArray font) {
        return put(PdfName.Font, font);
    }

    public PdfObject getBlackGenerationFunction() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1296BG);
    }

    public PdfExtGState setBlackGenerationFunction(PdfObject blackGenerationFunction) {
        return put(PdfName.f1296BG, blackGenerationFunction);
    }

    public PdfObject getBlackGenerationFunction2() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.BG2);
    }

    public PdfExtGState setBlackGenerationFunction2(PdfObject blackGenerationFunction2) {
        return put(PdfName.BG2, blackGenerationFunction2);
    }

    public PdfObject getUndercolorRemovalFunction() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.UCR);
    }

    public PdfExtGState setUndercolorRemovalFunction(PdfObject undercolorRemovalFunction) {
        return put(PdfName.UCR, undercolorRemovalFunction);
    }

    public PdfObject getUndercolorRemovalFunction2() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.UCR2);
    }

    public PdfExtGState setUndercolorRemovalFunction2(PdfObject undercolorRemovalFunction2) {
        return put(PdfName.UCR2, undercolorRemovalFunction2);
    }

    public PdfObject getTransferFunction() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1400TR);
    }

    public PdfExtGState setTransferFunction(PdfObject transferFunction) {
        return put(PdfName.f1400TR, transferFunction);
    }

    public PdfObject getTransferFunction2() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.TR2);
    }

    public PdfExtGState setTransferFunction2(PdfObject transferFunction2) {
        return put(PdfName.TR2, transferFunction2);
    }

    public PdfObject getHalftone() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1338HT);
    }

    public PdfExtGState setHalftone(PdfObject halftone) {
        return put(PdfName.f1338HT, halftone);
    }

    public Float getFlatnessTolerance() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1325FL);
    }

    public PdfExtGState setFlatnessTolerance(float flatnessTolerance) {
        return put(PdfName.f1325FL, new PdfNumber((double) flatnessTolerance));
    }

    public Float getSmothnessTolerance() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1388SM);
    }

    public PdfExtGState setSmoothnessTolerance(float smoothnessTolerance) {
        return put(PdfName.f1388SM, new PdfNumber((double) smoothnessTolerance));
    }

    public Boolean getAutomaticStrokeAdjustmentFlag() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1386SA);
    }

    public PdfExtGState setAutomaticStrokeAdjustmentFlag(boolean strokeAdjustment) {
        return put(PdfName.f1386SA, PdfBoolean.valueOf(strokeAdjustment));
    }

    public PdfObject getBlendMode() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1297BM);
    }

    public PdfExtGState setBlendMode(PdfObject blendMode) {
        return put(PdfName.f1297BM, blendMode);
    }

    public PdfObject getSoftMask() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.SMask);
    }

    public PdfExtGState setSoftMask(PdfObject sMask) {
        return put(PdfName.SMask, sMask);
    }

    public Float getStrokeOpacity() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1303CA);
    }

    public PdfExtGState setStrokeOpacity(float strokingAlphaConstant) {
        return put(PdfName.f1303CA, new PdfNumber((double) strokingAlphaConstant));
    }

    public Float getFillOpacity() {
        return ((PdfDictionary) getPdfObject()).getAsFloat(PdfName.f1417ca);
    }

    public PdfExtGState setFillOpacity(float fillingAlphaConstant) {
        return put(PdfName.f1417ca, new PdfNumber((double) fillingAlphaConstant));
    }

    public Boolean getAlphaSourceFlag() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.AIS);
    }

    public PdfExtGState setAlphaSourceFlag(boolean alphaSourceFlag) {
        return put(PdfName.AIS, PdfBoolean.valueOf(alphaSourceFlag));
    }

    public Boolean getTextKnockoutFlag() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1397TK);
    }

    public PdfExtGState setTextKnockoutFlag(boolean textKnockoutFlag) {
        return put(PdfName.f1397TK, PdfBoolean.valueOf(textKnockoutFlag));
    }

    public PdfExtGState setUseBlackPointCompensation(boolean useBlackPointCompensation) {
        return put(PdfName.UseBlackPtComp, useBlackPointCompensation ? PdfName.f1364ON : PdfName.OFF);
    }

    public Boolean isBlackPointCompensationUsed() {
        PdfName useBlackPointCompensation = ((PdfDictionary) getPdfObject()).getAsName(PdfName.UseBlackPtComp);
        if (PdfName.f1364ON.equals(useBlackPointCompensation)) {
            return true;
        }
        if (PdfName.OFF.equals(useBlackPointCompensation)) {
            return false;
        }
        return null;
    }

    public PdfExtGState setHalftoneOrigin(float x, float y) {
        PdfArray hto = new PdfArray();
        hto.add(new PdfNumber((double) x));
        hto.add(new PdfNumber((double) y));
        return put(PdfName.HTO, hto);
    }

    public float[] getHalftoneOrigin() {
        PdfArray hto = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.HTO);
        if (hto == null || hto.size() != 2 || !hto.get(0).isNumber() || !hto.get(1).isNumber()) {
            return null;
        }
        return new float[]{hto.getAsNumber(0).floatValue(), hto.getAsNumber(1).floatValue()};
    }

    public PdfExtGState put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}

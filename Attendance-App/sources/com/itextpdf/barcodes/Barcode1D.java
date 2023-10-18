package com.itextpdf.barcodes;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import java.awt.Color;
import java.awt.Image;

public abstract class Barcode1D {
    public static final int ALIGN_CENTER = 3;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    protected final Color DEFAULT_BAR_BACKGROUND_COLOR = Color.WHITE;
    protected final Color DEFAULT_BAR_FOREGROUND_COLOR = Color.BLACK;
    protected String altText;
    protected float barHeight;
    protected float baseline;
    protected boolean checksumText;
    protected String code = "";
    protected int codeType;
    protected PdfDocument document;
    protected boolean extended;
    protected PdfFont font;
    protected boolean generateChecksum;
    protected boolean guardBars;
    protected float inkSpreading = 0.0f;

    /* renamed from: n */
    protected float f1170n;
    protected float size;
    protected boolean startStopText;
    protected int textAlignment;

    /* renamed from: x */
    protected float f1171x;

    public abstract Image createAwtImage(Color color, Color color2);

    public abstract Rectangle getBarcodeSize();

    public abstract Rectangle placeBarcode(PdfCanvas pdfCanvas, com.itextpdf.kernel.colors.Color color, com.itextpdf.kernel.colors.Color color2);

    protected Barcode1D(PdfDocument document2) {
        this.document = document2;
    }

    public float getX() {
        return this.f1171x;
    }

    public void setX(float x) {
        this.f1171x = x;
    }

    public float getN() {
        return this.f1170n;
    }

    public void setN(float n) {
        this.f1170n = n;
    }

    public PdfFont getFont() {
        return this.font;
    }

    public void setFont(PdfFont font2) {
        this.font = font2;
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size2) {
        this.size = size2;
    }

    public float getBaseline() {
        return this.baseline;
    }

    public void setBaseline(float baseline2) {
        this.baseline = baseline2;
    }

    public float getBarHeight() {
        return this.barHeight;
    }

    public void setBarHeight(float barHeight2) {
        this.barHeight = barHeight2;
    }

    public int getTextAlignment() {
        return this.textAlignment;
    }

    public void setTextAlignment(int textAlignment2) {
        this.textAlignment = textAlignment2;
    }

    public boolean isGenerateChecksum() {
        return this.generateChecksum;
    }

    public void setGenerateChecksum(boolean generateChecksum2) {
        this.generateChecksum = generateChecksum2;
    }

    public boolean isChecksumText() {
        return this.checksumText;
    }

    public void setChecksumText(boolean checksumText2) {
        this.checksumText = checksumText2;
    }

    public boolean isStartStopText() {
        return this.startStopText;
    }

    public void setStartStopText(boolean startStopText2) {
        this.startStopText = startStopText2;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public void setExtended(boolean extended2) {
        this.extended = extended2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public boolean isGuardBars() {
        return this.guardBars;
    }

    public void setGuardBars(boolean guardBars2) {
        this.guardBars = guardBars2;
    }

    public int getCodeType() {
        return this.codeType;
    }

    public void setCodeType(int codeType2) {
        this.codeType = codeType2;
    }

    public float getInkSpreading() {
        return this.inkSpreading;
    }

    public void setInkSpreading(float inkSpreading2) {
        this.inkSpreading = inkSpreading2;
    }

    public String getAltText() {
        return this.altText;
    }

    public void setAltText(String altText2) {
        this.altText = altText2;
    }

    public PdfFormXObject createFormXObject(PdfDocument document2) {
        return createFormXObject((com.itextpdf.kernel.colors.Color) null, (com.itextpdf.kernel.colors.Color) null, document2);
    }

    public PdfFormXObject createFormXObject(com.itextpdf.kernel.colors.Color barColor, com.itextpdf.kernel.colors.Color textColor, PdfDocument document2) {
        Rectangle rectangle = null;
        PdfFormXObject xObject = new PdfFormXObject((Rectangle) null);
        xObject.setBBox(new PdfArray(placeBarcode(new PdfCanvas(xObject, document2), barColor, textColor)));
        return xObject;
    }

    public void fitWidth(float width) {
        setX((this.f1171x * width) / getBarcodeSize().getWidth());
    }

    /* access modifiers changed from: protected */
    public float getDescender() {
        return ((float) this.font.getFontProgram().getFontMetrics().getTypoDescender()) * (this.size / 1000.0f);
    }
}

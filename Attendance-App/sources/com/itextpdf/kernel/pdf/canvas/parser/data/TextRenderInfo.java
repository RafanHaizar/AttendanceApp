package com.itextpdf.kernel.pdf.canvas.parser.data;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfType0Font;
import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.p026io.font.otf.GlyphLine;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import kotlin.UByte;

public class TextRenderInfo extends AbstractRenderInfo {
    private final List<CanvasTag> canvasTagHierarchy;
    private double[] fontMatrix = null;
    private final PdfString string;
    private String text = null;
    private final Matrix textMatrix;
    private final Matrix textToUserSpaceTransformMatrix;
    private float unscaledWidth = Float.NaN;

    public TextRenderInfo(PdfString str, CanvasGraphicsState gs, Matrix textMatrix2, Stack<CanvasTag> canvasTagHierarchy2) {
        super(gs);
        this.string = str;
        this.textToUserSpaceTransformMatrix = textMatrix2.multiply(gs.getCtm());
        this.textMatrix = textMatrix2;
        this.canvasTagHierarchy = Collections.unmodifiableList(new ArrayList(canvasTagHierarchy2));
        this.fontMatrix = gs.getFont().getFontMatrix();
    }

    private TextRenderInfo(TextRenderInfo parent, PdfString str, float horizontalOffset) {
        super(parent.f1495gs);
        this.string = str;
        Matrix offsetMatrix = new Matrix(horizontalOffset, 0.0f);
        this.textToUserSpaceTransformMatrix = offsetMatrix.multiply(parent.textToUserSpaceTransformMatrix);
        this.textMatrix = offsetMatrix.multiply(parent.textMatrix);
        this.canvasTagHierarchy = parent.canvasTagHierarchy;
        this.fontMatrix = parent.f1495gs.getFont().getFontMatrix();
    }

    public String getText() {
        checkGraphicsState();
        if (this.text == null) {
            GlyphLine gl = this.f1495gs.getFont().decodeIntoGlyphLine(this.string);
            if (!isReversedChars()) {
                this.text = gl.toUnicodeString(gl.start, gl.end);
            } else {
                StringBuilder sb = new StringBuilder(gl.end - gl.start);
                int i = gl.end;
                while (true) {
                    i--;
                    if (i < gl.start) {
                        break;
                    }
                    sb.append(gl.get(i).getUnicodeChars());
                }
                this.text = sb.toString();
            }
        }
        return this.text;
    }

    public PdfString getPdfString() {
        return this.string;
    }

    public Matrix getTextMatrix() {
        return this.textMatrix;
    }

    public boolean hasMcid(int mcid) {
        return hasMcid(mcid, false);
    }

    public boolean hasMcid(int mcid, boolean checkTheTopmostLevelOnly) {
        int infoMcid;
        if (!checkTheTopmostLevelOnly) {
            for (CanvasTag tag : this.canvasTagHierarchy) {
                if (tag.hasMcid() && tag.getMcid() == mcid) {
                    return true;
                }
            }
        } else if (this.canvasTagHierarchy == null || (infoMcid = getMcid()) == -1 || infoMcid != mcid) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    public int getMcid() {
        for (CanvasTag tag : this.canvasTagHierarchy) {
            if (tag.hasMcid()) {
                return tag.getMcid();
            }
        }
        return -1;
    }

    public LineSegment getBaseline() {
        checkGraphicsState();
        return getUnscaledBaselineWithOffset(this.f1495gs.getTextRise() + 0.0f).transformBy(this.textToUserSpaceTransformMatrix);
    }

    public LineSegment getUnscaledBaseline() {
        checkGraphicsState();
        return getUnscaledBaselineWithOffset(this.f1495gs.getTextRise() + 0.0f);
    }

    public LineSegment getAscentLine() {
        checkGraphicsState();
        return getUnscaledBaselineWithOffset(getAscentDescent()[0] + this.f1495gs.getTextRise()).transformBy(this.textToUserSpaceTransformMatrix);
    }

    public LineSegment getDescentLine() {
        checkGraphicsState();
        return getUnscaledBaselineWithOffset(getAscentDescent()[1] + this.f1495gs.getTextRise()).transformBy(this.textToUserSpaceTransformMatrix);
    }

    public PdfFont getFont() {
        checkGraphicsState();
        return this.f1495gs.getFont();
    }

    public float getRise() {
        checkGraphicsState();
        if (this.f1495gs.getTextRise() == 0.0f) {
            return 0.0f;
        }
        return convertHeightFromTextSpaceToUserSpace(this.f1495gs.getTextRise());
    }

    public List<TextRenderInfo> getCharacterRenderInfos() {
        checkGraphicsState();
        List<TextRenderInfo> rslt = new ArrayList<>(this.string.getValue().length());
        float totalWidth = 0.0f;
        for (PdfString str : splitString(this.string)) {
            float[] widthAndWordSpacing = getWidthAndWordSpacing(str);
            rslt.add(new TextRenderInfo(this, str, totalWidth));
            totalWidth += ((widthAndWordSpacing[0] * this.f1495gs.getFontSize()) + this.f1495gs.getCharSpacing() + widthAndWordSpacing[1]) * (this.f1495gs.getHorizontalScaling() / 100.0f);
        }
        for (TextRenderInfo tri : rslt) {
            tri.getUnscaledWidth();
        }
        return rslt;
    }

    public float getSingleSpaceWidth() {
        return convertWidthFromTextSpaceToUserSpace(getUnscaledFontSpaceWidth());
    }

    public int getTextRenderMode() {
        checkGraphicsState();
        return this.f1495gs.getTextRenderingMode();
    }

    public Color getFillColor() {
        checkGraphicsState();
        return this.f1495gs.getFillColor();
    }

    public Color getStrokeColor() {
        checkGraphicsState();
        return this.f1495gs.getStrokeColor();
    }

    public float getFontSize() {
        checkGraphicsState();
        return this.f1495gs.getFontSize();
    }

    public float getHorizontalScaling() {
        checkGraphicsState();
        return this.f1495gs.getHorizontalScaling();
    }

    public float getCharSpacing() {
        checkGraphicsState();
        return this.f1495gs.getCharSpacing();
    }

    public float getWordSpacing() {
        checkGraphicsState();
        return this.f1495gs.getWordSpacing();
    }

    public float getLeading() {
        checkGraphicsState();
        return this.f1495gs.getLeading();
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0007 A[LOOP:0: B:1:0x0007->B:4:0x0017, LOOP_START, PHI: r0 
      PHI: (r0v1 'lastActualText' java.lang.String) = (r0v0 'lastActualText' java.lang.String), (r0v3 'lastActualText' java.lang.String) binds: [B:0:0x0000, B:4:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getActualText() {
        /*
            r3 = this;
            r0 = 0
            java.util.List<com.itextpdf.kernel.pdf.canvas.CanvasTag> r1 = r3.canvasTagHierarchy
            java.util.Iterator r1 = r1.iterator()
        L_0x0007:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x001b
            java.lang.Object r2 = r1.next()
            com.itextpdf.kernel.pdf.canvas.CanvasTag r2 = (com.itextpdf.kernel.pdf.canvas.CanvasTag) r2
            java.lang.String r0 = r2.getActualText()
            if (r0 == 0) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            goto L_0x0007
        L_0x001b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo.getActualText():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0007 A[LOOP:0: B:1:0x0007->B:4:0x0017, LOOP_START, PHI: r0 
      PHI: (r0v1 'expansionText' java.lang.String) = (r0v0 'expansionText' java.lang.String), (r0v3 'expansionText' java.lang.String) binds: [B:0:0x0000, B:4:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getExpansionText() {
        /*
            r3 = this;
            r0 = 0
            java.util.List<com.itextpdf.kernel.pdf.canvas.CanvasTag> r1 = r3.canvasTagHierarchy
            java.util.Iterator r1 = r1.iterator()
        L_0x0007:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x001b
            java.lang.Object r2 = r1.next()
            com.itextpdf.kernel.pdf.canvas.CanvasTag r2 = (com.itextpdf.kernel.pdf.canvas.CanvasTag) r2
            java.lang.String r0 = r2.getExpansionText()
            if (r0 == 0) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            goto L_0x0007
        L_0x001b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo.getExpansionText():java.lang.String");
    }

    public boolean isReversedChars() {
        for (CanvasTag tag : this.canvasTagHierarchy) {
            if (tag != null && PdfName.ReversedChars.equals(tag.getRole())) {
                return true;
            }
        }
        return false;
    }

    public List<CanvasTag> getCanvasTagHierarchy() {
        return this.canvasTagHierarchy;
    }

    public float getUnscaledWidth() {
        if (Float.isNaN(this.unscaledWidth)) {
            this.unscaledWidth = getPdfStringWidth(this.string, false);
        }
        return this.unscaledWidth;
    }

    private LineSegment getUnscaledBaselineWithOffset(float yOffset) {
        checkGraphicsState();
        String unicodeStr = this.string.toUnicodeString();
        return new LineSegment(new Vector(0.0f, yOffset, 1.0f), new Vector(getUnscaledWidth() - ((this.f1495gs.getCharSpacing() + ((unicodeStr.length() <= 0 || unicodeStr.charAt(unicodeStr.length() + -1) != ' ') ? 0.0f : this.f1495gs.getWordSpacing())) * (this.f1495gs.getHorizontalScaling() / 100.0f)), yOffset, 1.0f));
    }

    private float convertWidthFromTextSpaceToUserSpace(float width) {
        return new LineSegment(new Vector(0.0f, 0.0f, 1.0f), new Vector(width, 0.0f, 1.0f)).transformBy(this.textToUserSpaceTransformMatrix).getLength();
    }

    private float convertHeightFromTextSpaceToUserSpace(float height) {
        return new LineSegment(new Vector(0.0f, 0.0f, 1.0f), new Vector(0.0f, height, 1.0f)).transformBy(this.textToUserSpaceTransformMatrix).getLength();
    }

    private float getUnscaledFontSpaceWidth() {
        checkGraphicsState();
        int charWidth = this.f1495gs.getFont().getWidth(32);
        if (charWidth == 0) {
            charWidth = this.f1495gs.getFont().getFontProgram().getAvgWidth();
        }
        double d = (double) charWidth;
        double d2 = this.fontMatrix[0];
        Double.isNaN(d);
        return ((((this.f1495gs.getFontSize() * ((float) (d * d2))) + this.f1495gs.getCharSpacing()) + this.f1495gs.getWordSpacing()) * this.f1495gs.getHorizontalScaling()) / 100.0f;
    }

    private float getPdfStringWidth(PdfString string2, boolean singleCharString) {
        checkGraphicsState();
        if (singleCharString) {
            float[] widthAndWordSpacing = getWidthAndWordSpacing(string2);
            double d = (double) widthAndWordSpacing[0];
            double fontSize = (double) this.f1495gs.getFontSize();
            Double.isNaN(d);
            Double.isNaN(fontSize);
            double d2 = d * fontSize;
            double charSpacing = (double) this.f1495gs.getCharSpacing();
            Double.isNaN(charSpacing);
            double d3 = (double) widthAndWordSpacing[1];
            Double.isNaN(d3);
            double d4 = d2 + charSpacing + d3;
            double horizontalScaling = (double) this.f1495gs.getHorizontalScaling();
            Double.isNaN(horizontalScaling);
            return (float) ((d4 * horizontalScaling) / 100.0d);
        }
        float totalWidth = 0.0f;
        for (PdfString str : splitString(string2)) {
            totalWidth += getPdfStringWidth(str, true);
        }
        return totalWidth;
    }

    private float[] getWidthAndWordSpacing(PdfString string2) {
        checkGraphicsState();
        float[] result = new float[2];
        double contentWidth = (double) this.f1495gs.getFont().getContentWidth(string2);
        double d = this.fontMatrix[0];
        Double.isNaN(contentWidth);
        result[0] = (float) (contentWidth * d);
        result[1] = " ".equals(string2.getValue()) ? this.f1495gs.getWordSpacing() : 0.0f;
        return result;
    }

    private int getCharCode(String string2) {
        try {
            byte[] b = string2.getBytes("UTF-16BE");
            int value = 0;
            for (int i = 0; i < b.length - 1; i++) {
                value = (value + (b[i] & UByte.MAX_VALUE)) << 8;
            }
            if (b.length > 0) {
                return value + (b[b.length - 1] & UByte.MAX_VALUE);
            }
            return value;
        } catch (UnsupportedEncodingException e) {
            return 0;
        }
    }

    private PdfString[] splitString(PdfString string2) {
        checkGraphicsState();
        if (this.f1495gs.getFont() instanceof PdfType0Font) {
            List<PdfString> strings = new ArrayList<>();
            GlyphLine glyphLine = this.f1495gs.getFont().decodeIntoGlyphLine(string2);
            for (int i = glyphLine.start; i < glyphLine.end; i++) {
                strings.add(new PdfString(this.f1495gs.getFont().convertToBytes(glyphLine.get(i))));
            }
            return (PdfString[]) strings.toArray(new PdfString[strings.size()]);
        }
        PdfString[] strings2 = new PdfString[string2.getValue().length()];
        for (int i2 = 0; i2 < string2.getValue().length(); i2++) {
            strings2[i2] = new PdfString(string2.getValue().substring(i2, i2 + 1), string2.getEncoding());
        }
        return strings2;
    }

    private float[] getAscentDescent() {
        checkGraphicsState();
        float ascent = (float) this.f1495gs.getFont().getFontProgram().getFontMetrics().getTypoAscender();
        float descent = (float) this.f1495gs.getFont().getFontProgram().getFontMetrics().getTypoDescender();
        if (descent > 0.0f) {
            descent = -descent;
        }
        float scale = ascent - descent < 700.0f ? ascent - descent : 1000.0f;
        return new float[]{(ascent / scale) * this.f1495gs.getFontSize(), (descent / scale) * this.f1495gs.getFontSize()};
    }
}

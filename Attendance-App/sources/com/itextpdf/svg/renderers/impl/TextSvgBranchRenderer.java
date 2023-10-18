package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.font.FontCharacteristics;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCssUtils;
import com.itextpdf.svg.utils.SvgTextUtil;
import com.itextpdf.svg.utils.TextRectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextSvgBranchRenderer extends AbstractSvgNodeRenderer implements ISvgTextNodeRenderer, ISvgTextNodeHelper {
    private static final float DEFAULT_FONT_SIZE = 12.0f;
    protected static final AffineTransform TEXTFLIP = new AffineTransform(1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d);
    private final List<ISvgTextNodeRenderer> children = new ArrayList();
    private PdfFont font;
    private float fontSize;
    private boolean moveResolved = false;
    protected boolean performRootTransformations = true;
    private boolean posResolved = false;
    private boolean whiteSpaceProcessed = false;
    private float xMove;
    private float[] xPos;
    private float yMove;
    private float[] yPos;

    public ISvgNodeRenderer createDeepCopy() {
        TextSvgBranchRenderer copy = new TextSvgBranchRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }

    public final void addChild(ISvgTextNodeRenderer child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public final List<ISvgTextNodeRenderer> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    public float getTextContentLength(float parentFontSize, PdfFont font2) {
        return 0.0f;
    }

    public float[] getRelativeTranslation() {
        if (!this.moveResolved) {
            resolveTextMove();
        }
        return new float[]{this.xMove, this.yMove};
    }

    public boolean containsRelativeMove() {
        if (!this.moveResolved) {
            resolveTextMove();
        }
        if (!(CssUtils.compareFloats(0.0f, this.xMove) && CssUtils.compareFloats(0.0f, this.yMove))) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000e, code lost:
        r0 = r1.yPos;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean containsAbsolutePositionChange() {
        /*
            r1 = this;
            boolean r0 = r1.posResolved
            if (r0 != 0) goto L_0x0007
            r1.resolveTextPosition()
        L_0x0007:
            float[] r0 = r1.xPos
            if (r0 == 0) goto L_0x000e
            int r0 = r0.length
            if (r0 > 0) goto L_0x0015
        L_0x000e:
            float[] r0 = r1.yPos
            if (r0 == 0) goto L_0x0017
            int r0 = r0.length
            if (r0 <= 0) goto L_0x0017
        L_0x0015:
            r0 = 1
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer.containsAbsolutePositionChange():boolean");
    }

    public float[][] getAbsolutePositionChanges() {
        if (!this.posResolved) {
            resolveTextPosition();
        }
        return new float[][]{this.xPos, this.yPos};
    }

    public void markWhiteSpaceProcessed() {
        this.whiteSpaceProcessed = true;
    }

    public TextRectangle getTextRectangle(SvgDrawContext context, Point basePoint) {
        if (this.attributesAndStyles != null) {
            resolveFontSize();
            resolveFont(context);
            double x = 0.0d;
            double y = 0.0d;
            if (getAbsolutePositionChanges()[0] != null) {
                x = (double) getAbsolutePositionChanges()[0][0];
            } else if (basePoint != null) {
                x = basePoint.getX();
            }
            if (getAbsolutePositionChanges()[1] != null) {
                y = (double) getAbsolutePositionChanges()[1][0];
            } else if (basePoint != null) {
                y = basePoint.getY();
            }
            Point basePoint2 = new Point(x, y);
            basePoint2.translate((double) getRelativeTranslation()[0], (double) getRelativeTranslation()[1]);
            Rectangle commonRect = null;
            for (ISvgTextNodeRenderer child : getChildren()) {
                if (child instanceof ISvgTextNodeHelper) {
                    TextRectangle rectangle = ((ISvgTextNodeHelper) child).getTextRectangle(context, basePoint2);
                    basePoint2 = rectangle.getTextBaseLineRightPoint();
                    commonRect = Rectangle.getCommonRectangle(commonRect, rectangle);
                } else {
                    SvgDrawContext svgDrawContext = context;
                }
            }
            SvgDrawContext svgDrawContext2 = context;
            if (commonRect != null) {
                return new TextRectangle(commonRect.getX(), commonRect.getY(), commonRect.getWidth(), commonRect.getHeight(), (float) basePoint2.getY());
            }
            return null;
        }
        SvgDrawContext svgDrawContext3 = context;
        Point point = basePoint;
        return null;
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        return getTextRectangle(context, (Point) null);
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        AffineTransform rootTf;
        if (getChildren().size() > 0) {
            PdfCanvas currentCanvas = context.getCurrentCanvas();
            if (this.performRootTransformations) {
                currentCanvas.beginText();
                if (containsAbsolutePositionChange()) {
                    rootTf = getTextTransform(getAbsolutePositionChanges(), context);
                } else {
                    rootTf = new AffineTransform(TEXTFLIP);
                }
                currentCanvas.setTextMatrix(rootTf);
                context.resetTextMove();
                if (containsRelativeMove()) {
                    float[] rootMove = getRelativeTranslation();
                    context.addTextMove(rootMove[0], -rootMove[1]);
                }
                if (!this.whiteSpaceProcessed) {
                    SvgTextUtil.processWhiteSpace(this, true);
                }
            }
            applyTextRenderingMode(currentCanvas);
            if (this.attributesAndStyles != null) {
                resolveFontSize();
                resolveFont(context);
                currentCanvas.setFontAndSize(this.font, this.fontSize);
                for (ISvgTextNodeRenderer c : this.children) {
                    float childLength = c.getTextContentLength(this.fontSize, this.font);
                    if (c.containsAbsolutePositionChange()) {
                        AffineTransform newTransform = getTextTransform(c.getAbsolutePositionChanges(), context);
                        context.setLastTextTransform(newTransform);
                        currentCanvas.setTextMatrix(newTransform);
                        context.resetTextMove();
                    }
                    float textAnchorCorrection = getTextAnchorAlignmentCorrection(childLength);
                    if (!CssUtils.compareFloats(0.0f, textAnchorCorrection)) {
                        context.addTextMove(textAnchorCorrection, 0.0f);
                    }
                    if (c.containsRelativeMove()) {
                        float[] childMove = c.getRelativeTranslation();
                        context.addTextMove(childMove[0], -childMove[1]);
                    }
                    currentCanvas.saveState();
                    c.draw(context);
                    context.addTextMove(childLength, 0.0f);
                    currentCanvas.restoreState();
                    if (!context.getLastTextTransform().isIdentity()) {
                        currentCanvas.setTextMatrix(context.getLastTextTransform());
                    }
                }
                if (this.performRootTransformations) {
                    currentCanvas.endText();
                }
            }
        }
    }

    private void resolveTextMove() {
        if (this.attributesAndStyles != null) {
            List<String> xValuesList = SvgCssUtils.splitValueList((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1635DX));
            List<String> yValuesList = SvgCssUtils.splitValueList((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1636DY));
            this.xMove = 0.0f;
            this.yMove = 0.0f;
            if (!xValuesList.isEmpty()) {
                this.xMove = CssUtils.parseAbsoluteLength(xValuesList.get(0));
            }
            if (!yValuesList.isEmpty()) {
                this.yMove = CssUtils.parseAbsoluteLength(yValuesList.get(0));
            }
            this.moveResolved = true;
        }
    }

    private FontInfo resolveFontName(String fontFamily, String fontWeight, String fontStyle, FontProvider provider, FontSet tempFonts) {
        boolean isItalic = true;
        boolean isBold = fontWeight != null && "bold".equalsIgnoreCase(fontWeight);
        if (fontStyle == null || !"italic".equalsIgnoreCase(fontStyle)) {
            isItalic = false;
        }
        FontCharacteristics fontCharacteristics = new FontCharacteristics();
        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(fontFamily);
        fontCharacteristics.setBoldFlag(isBold);
        fontCharacteristics.setItalicFlag(isItalic);
        return provider.getFontSelector(stringArrayList, fontCharacteristics, tempFonts).bestMatch();
    }

    /* access modifiers changed from: package-private */
    public void resolveFont(SvgDrawContext context) {
        FontProvider provider = context.getFontProvider();
        FontSet tempFonts = context.getTempFonts();
        this.font = null;
        if (!provider.getFontSet().isEmpty() || (tempFonts != null && !tempFonts.isEmpty())) {
            String fontFamily = (String) this.attributesAndStyles.get("font-family");
            this.font = provider.getPdfFont(resolveFontName(fontFamily != null ? fontFamily.trim() : "", (String) this.attributesAndStyles.get("font-weight"), (String) this.attributesAndStyles.get("font-style"), provider, tempFonts), tempFonts);
        }
        if (this.font == null) {
            try {
                this.font = PdfFontFactory.createFont();
            } catch (IOException e) {
                throw new SvgProcessingException(SvgLogMessageConstant.FONT_NOT_FOUND, e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resolveFontSize() {
        this.fontSize = SvgTextUtil.resolveFontSize(this, DEFAULT_FONT_SIZE);
    }

    /* access modifiers changed from: package-private */
    public PdfFont getFont() {
        return this.font;
    }

    /* access modifiers changed from: package-private */
    public float getFontSize() {
        return this.fontSize;
    }

    private void resolveTextPosition() {
        if (this.attributesAndStyles != null) {
            this.xPos = getPositionsFromString((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1641X));
            this.yPos = getPositionsFromString((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1644Y));
            this.posResolved = true;
        }
    }

    private static float[] getPositionsFromString(String rawValuesString) {
        float[] result = null;
        List<String> valuesList = SvgCssUtils.splitValueList(rawValuesString);
        if (!valuesList.isEmpty()) {
            result = new float[valuesList.size()];
            for (int i = 0; i < valuesList.size(); i++) {
                result[i] = CssUtils.parseAbsoluteLength(valuesList.get(i));
            }
        }
        return result;
    }

    private static AffineTransform getTextTransform(float[][] absolutePositions, SvgDrawContext context) {
        AffineTransform tf = new AffineTransform();
        if (absolutePositions[0] == null && absolutePositions[1] != null) {
            absolutePositions[0] = new float[]{context.getTextMove()[0]};
        }
        if (absolutePositions[1] == null) {
            absolutePositions[1] = new float[]{0.0f};
        }
        tf.concatenate(TEXTFLIP);
        tf.concatenate(AffineTransform.getTranslateInstance((double) absolutePositions[0][0], (double) (-absolutePositions[1][0])));
        return tf;
    }

    private void applyTextRenderingMode(PdfCanvas currentCanvas) {
        if (this.doStroke && this.doFill) {
            currentCanvas.setTextRenderingMode(2);
        } else if (this.doStroke) {
            currentCanvas.setTextRenderingMode(1);
        } else {
            currentCanvas.setTextRenderingMode(0);
        }
    }

    private void deepCopyChildren(TextSvgBranchRenderer deepCopy) {
        for (ISvgTextNodeRenderer child : this.children) {
            child.setParent(deepCopy);
            deepCopy.addChild((ISvgTextNodeRenderer) child.createDeepCopy());
        }
    }

    private float getTextAnchorAlignmentCorrection(float childContentLength) {
        float[] fArr;
        float[] fArr2;
        float textAnchorXCorrection = 0.0f;
        if (this.attributesAndStyles == null || !this.attributesAndStyles.containsKey(SvgConstants.Attributes.TEXT_ANCHOR)) {
            return 0.0f;
        }
        String textAnchorValue = getAttribute(SvgConstants.Attributes.TEXT_ANCHOR);
        if (SvgConstants.Values.TEXT_ANCHOR_MIDDLE.equals(textAnchorValue) && (fArr2 = this.xPos) != null && fArr2.length > 0) {
            textAnchorXCorrection = 0.0f - (childContentLength / 2.0f);
        }
        if (!"end".equals(textAnchorValue) || (fArr = this.xPos) == null || fArr.length <= 0) {
            return textAnchorXCorrection;
        }
        return textAnchorXCorrection - childContentLength;
    }
}

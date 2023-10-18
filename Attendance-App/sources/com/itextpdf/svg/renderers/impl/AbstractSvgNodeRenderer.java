package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.TransformUtils;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSvgNodeRenderer implements ISvgNodeRenderer {
    private static final MarkerVertexType[] MARKER_VERTEX_TYPES = {MarkerVertexType.MARKER_START, MarkerVertexType.MARKER_END};
    protected Map<String, String> attributesAndStyles;
    boolean doFill = false;
    boolean doStroke = false;
    private ISvgNodeRenderer parent;
    boolean partOfClipPath;

    /* access modifiers changed from: protected */
    public abstract void doDraw(SvgDrawContext svgDrawContext);

    public void setParent(ISvgNodeRenderer parent2) {
        this.parent = parent2;
    }

    public ISvgNodeRenderer getParent() {
        return this.parent;
    }

    public void setAttributesAndStyles(Map<String, String> attributesAndStyles2) {
        this.attributesAndStyles = attributesAndStyles2;
    }

    public String getAttribute(String key) {
        return this.attributesAndStyles.get(key);
    }

    public String getAttributeOrDefault(String key, String defaultValue) {
        String rawValue = getAttribute(key);
        return rawValue != null ? rawValue : defaultValue;
    }

    public void setAttribute(String key, String value) {
        if (this.attributesAndStyles == null) {
            this.attributesAndStyles = new HashMap();
        }
        this.attributesAndStyles.put(key, value);
    }

    public Map<String, String> getAttributeMapCopy() {
        HashMap<String, String> copy = new HashMap<>();
        Map<String, String> map = this.attributesAndStyles;
        if (map == null) {
            return copy;
        }
        copy.putAll(map);
        return copy;
    }

    public final void draw(SvgDrawContext context) {
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        Map<String, String> map = this.attributesAndStyles;
        if (map != null) {
            String transformString = map.get("transform");
            if (transformString != null && !transformString.isEmpty()) {
                AffineTransform transformation = TransformUtils.parseTransform(transformString);
                if (!transformation.isIdentity()) {
                    currentCanvas.concatMatrix(transformation);
                }
            }
            if (this.attributesAndStyles.containsKey("id")) {
                context.addUsedId(this.attributesAndStyles.get("id"));
            }
        }
        if (!drawInClipPath(context)) {
            preDraw(context);
            doDraw(context);
            postDraw(context);
        }
        if (this.attributesAndStyles.containsKey("id")) {
            context.removeUsedId(this.attributesAndStyles.get("id"));
        }
    }

    /* access modifiers changed from: protected */
    public boolean canElementFill() {
        return true;
    }

    public boolean canConstructViewPort() {
        return false;
    }

    public float getCurrentFontSize() {
        return CssUtils.parseAbsoluteFontSize(getAttribute("font-size"));
    }

    /* access modifiers changed from: protected */
    public void deepCopyAttributesAndStyles(ISvgNodeRenderer deepCopy) {
        Map<String, String> stylesDeepCopy = new HashMap<>();
        Map<String, String> map = this.attributesAndStyles;
        if (map != null) {
            stylesDeepCopy.putAll(map);
            deepCopy.setAttributesAndStyles(stylesDeepCopy);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        return null;
    }

    static float getAlphaFromRGBA(String value) {
        try {
            return WebColors.getRGBAColor(value)[3];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return 1.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public AffineTransform calculateViewPortTranslation(SvgDrawContext context) {
        Rectangle viewPort = context.getCurrentViewPort();
        return AffineTransform.getTranslateInstance((double) viewPort.getX(), (double) viewPort.getY());
    }

    /* access modifiers changed from: package-private */
    public void postDraw(SvgDrawContext context) {
        if (this.attributesAndStyles != null) {
            PdfCanvas currentCanvas = context.getCurrentCanvas();
            if (this.partOfClipPath) {
                if (SvgConstants.Values.FILL_RULE_EVEN_ODD.equalsIgnoreCase(getAttribute(SvgConstants.Attributes.CLIP_RULE))) {
                    currentCanvas.eoClip();
                } else {
                    currentCanvas.clip();
                }
                currentCanvas.endPath();
            } else if (!this.doFill || !canElementFill()) {
                if (this.doStroke) {
                    currentCanvas.stroke();
                } else if (!TextSvgBranchRenderer.class.isInstance(this)) {
                    currentCanvas.endPath();
                }
            } else if (SvgConstants.Values.FILL_RULE_EVEN_ODD.equalsIgnoreCase(getAttribute(SvgConstants.Attributes.FILL_RULE))) {
                if (this.doStroke) {
                    currentCanvas.eoFillStroke();
                } else {
                    currentCanvas.eoFill();
                }
            } else if (this.doStroke) {
                currentCanvas.fillStroke();
            } else {
                currentCanvas.fill();
            }
            if (this instanceof IMarkerCapable) {
                for (MarkerVertexType markerVertexType : MARKER_VERTEX_TYPES) {
                    if (this.attributesAndStyles.containsKey(markerVertexType.toString())) {
                        currentCanvas.saveState();
                        ((IMarkerCapable) this).drawMarker(context, markerVertexType);
                        currentCanvas.restoreState();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setPartOfClipPath(boolean value) {
        this.partOfClipPath = value;
    }

    /* access modifiers changed from: package-private */
    public void preDraw(SvgDrawContext context) {
        if (this.attributesAndStyles != null) {
            PdfCanvas currentCanvas = context.getCurrentCanvas();
            PdfExtGState opacityGraphicsState = new PdfExtGState();
            if (!this.partOfClipPath) {
                float generalOpacity = getOpacity();
                String fillRawValue = getAttributeOrDefault(SvgConstants.Attributes.FILL, "black");
                boolean z = !"none".equalsIgnoreCase(fillRawValue);
                this.doFill = z;
                if (z && canElementFill()) {
                    float fillOpacity = getOpacityByAttributeName(SvgConstants.Attributes.FILL_OPACITY, generalOpacity);
                    Color fillColor = null;
                    TransparentColor transparentColor = getColorFromAttributeValue(context, fillRawValue, 0.0f, fillOpacity);
                    if (transparentColor != null) {
                        fillColor = transparentColor.getColor();
                        fillOpacity = transparentColor.getOpacity();
                    }
                    if (!CssUtils.compareFloats(fillOpacity, 1.0f)) {
                        opacityGraphicsState.setFillOpacity(fillOpacity);
                    }
                    if (fillColor == null) {
                        fillColor = ColorConstants.BLACK;
                    }
                    currentCanvas.setFillColor(fillColor);
                }
                String strokeRawValue = getAttributeOrDefault(SvgConstants.Attributes.STROKE, "none");
                if (!"none".equalsIgnoreCase(strokeRawValue)) {
                    String strokeWidthRawValue = getAttribute(SvgConstants.Attributes.STROKE_WIDTH);
                    float strokeWidth = 0.75f;
                    if (strokeWidthRawValue != null) {
                        strokeWidth = CssUtils.parseAbsoluteLength(strokeWidthRawValue);
                    }
                    float strokeOpacity = getOpacityByAttributeName(SvgConstants.Attributes.STROKE_OPACITY, generalOpacity);
                    Color strokeColor = null;
                    TransparentColor transparentColor2 = getColorFromAttributeValue(context, strokeRawValue, strokeWidth / 2.0f, strokeOpacity);
                    if (transparentColor2 != null) {
                        strokeColor = transparentColor2.getColor();
                        strokeOpacity = transparentColor2.getOpacity();
                    }
                    if (!CssUtils.compareFloats(strokeOpacity, 1.0f)) {
                        opacityGraphicsState.setStrokeOpacity(strokeOpacity);
                    }
                    if (strokeColor != null) {
                        currentCanvas.setStrokeColor(strokeColor);
                    }
                    currentCanvas.setLineWidth(strokeWidth);
                    this.doStroke = true;
                }
                if (!((PdfDictionary) opacityGraphicsState.getPdfObject()).isEmpty()) {
                    currentCanvas.setExtGState(opacityGraphicsState);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public float parseAbsoluteLength(String length, float percentRelativeValue, float defaultValue, SvgDrawContext context) {
        if (CssUtils.isPercentageValue(length)) {
            return CssUtils.parseRelativeValue(length, percentRelativeValue);
        }
        UnitValue unitValue = CssUtils.parseLengthValueToPt(length, getCurrentFontSize(), context.getRemValue());
        if (unitValue == null || !unitValue.isPointValue()) {
            return defaultValue;
        }
        return unitValue.getValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0004, code lost:
        r1 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer(r12);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.layout.property.TransparentColor getColorFromAttributeValue(com.itextpdf.svg.renderers.SvgDrawContext r11, java.lang.String r12, float r13, float r14) {
        /*
            r10 = this;
            r0 = 0
            if (r12 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer r1 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer
            r1.<init>(r12)
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = r1.getNextValidToken()
            if (r2 != 0) goto L_0x0010
            return r0
        L_0x0010:
            java.lang.String r3 = r2.getValue()
            java.lang.String r4 = "url(#"
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L_0x0056
            java.lang.String r4 = ")"
            boolean r4 = r3.endsWith(r4)
            if (r4 == 0) goto L_0x0056
            r4 = 0
            r5 = 1065353216(0x3f800000, float:1.0)
            int r6 = r3.length()
            int r6 = r6 + -1
            r7 = 5
            java.lang.String r6 = r3.substring(r7, r6)
            java.lang.String r6 = r6.trim()
            com.itextpdf.svg.renderers.ISvgNodeRenderer r7 = r11.getNamedObject(r6)
            boolean r8 = r7 instanceof com.itextpdf.svg.renderers.impl.AbstractGradientSvgNodeRenderer
            if (r8 == 0) goto L_0x004a
            r8 = r7
            com.itextpdf.svg.renderers.impl.AbstractGradientSvgNodeRenderer r8 = (com.itextpdf.svg.renderers.impl.AbstractGradientSvgNodeRenderer) r8
            com.itextpdf.kernel.geom.Rectangle r9 = r10.getObjectBoundingBox(r11)
            com.itextpdf.kernel.colors.Color r4 = r8.createColor(r11, r9, r13, r14)
        L_0x004a:
            if (r4 == 0) goto L_0x0052
            com.itextpdf.layout.property.TransparentColor r0 = new com.itextpdf.layout.property.TransparentColor
            r0.<init>(r4, r5)
            return r0
        L_0x0052:
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = r1.getNextValidToken()
        L_0x0056:
            if (r2 == 0) goto L_0x0075
            java.lang.String r4 = r2.getValue()
            java.lang.String r5 = "none"
            boolean r5 = r5.equalsIgnoreCase(r4)
            if (r5 != 0) goto L_0x0075
            com.itextpdf.layout.property.TransparentColor r0 = new com.itextpdf.layout.property.TransparentColor
            com.itextpdf.kernel.colors.DeviceRgb r5 = com.itextpdf.kernel.colors.WebColors.getRGBColor(r4)
            float r6 = getAlphaFromRGBA(r4)
            float r6 = r6 * r14
            r0.<init>(r5, r6)
            return r0
        L_0x0075:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer.getColorFromAttributeValue(com.itextpdf.svg.renderers.SvgDrawContext, java.lang.String, float, float):com.itextpdf.layout.property.TransparentColor");
    }

    private float getOpacityByAttributeName(String attributeName, float generalOpacity) {
        float opacity = generalOpacity;
        String opacityValue = getAttribute(attributeName);
        if (opacityValue == null || "none".equalsIgnoreCase(opacityValue)) {
            return opacity;
        }
        return opacity * Float.valueOf(opacityValue).floatValue();
    }

    private boolean drawInClipPath(SvgDrawContext context) {
        if (!this.attributesAndStyles.containsKey(SvgConstants.Attributes.CLIP_PATH)) {
            return false;
        }
        ISvgNodeRenderer template = context.getNamedObject(normalizeLocalUrlName(this.attributesAndStyles.get(SvgConstants.Attributes.CLIP_PATH)));
        if (!(template instanceof ClipPathSvgNodeRenderer)) {
            return false;
        }
        ClipPathSvgNodeRenderer clipPath = (ClipPathSvgNodeRenderer) template.createDeepCopy();
        clipPath.setClippedRenderer(this);
        clipPath.draw(context);
        return !clipPath.getChildren().isEmpty();
    }

    private String normalizeLocalUrlName(String name) {
        return name.replace("url(#", "").replace(")", "").trim();
    }

    private float getOpacity() {
        float result = 1.0f;
        String opacityValue = getAttribute("opacity");
        if (opacityValue != null && !"none".equalsIgnoreCase(opacityValue)) {
            result = Float.valueOf(opacityValue).floatValue();
        }
        ISvgNodeRenderer iSvgNodeRenderer = this.parent;
        if (iSvgNodeRenderer == null || !(iSvgNodeRenderer instanceof AbstractSvgNodeRenderer)) {
            return result;
        }
        return result * ((AbstractSvgNodeRenderer) iSvgNodeRenderer).getOpacity();
    }
}

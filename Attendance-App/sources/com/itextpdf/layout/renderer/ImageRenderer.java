package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.ObjectFit;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.objectfit.ObjectFitApplyingResult;
import com.itextpdf.layout.renderer.objectfit.ObjectFitCalculator;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.List;
import org.slf4j.LoggerFactory;

public class ImageRenderer extends AbstractRenderer implements ILeafElementRenderer {
    protected float deltaX;
    private boolean doesObjectFitRequireCutting;
    protected Float fixedXPosition;
    protected Float fixedYPosition;
    private Float height;
    protected float imageHeight;
    protected float imageWidth;
    private Rectangle initialOccupiedAreaBBox;
    float[] matrix = new float[6];
    protected float pivotY;
    private float renderedImageHeight;
    private float renderedImageWidth;
    private float rotatedDeltaX;
    private float rotatedDeltaY;
    private Float width;

    public ImageRenderer(Image image) {
        super((IElement) image);
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        boolean nowrap;
        float clearHeightCorrection;
        OverflowPropertyValue overflowY;
        Float angle;
        float imageItselfHeight;
        float imageItselfWidth;
        List<Rectangle> floatRendererAreas;
        float scaleCoef;
        LayoutArea area = layoutContext.getArea().clone();
        Rectangle layoutBox = area.getBBox().clone();
        AffineTransform t = new AffineTransform();
        Image modelElement = (Image) getModelElement();
        PdfXObject xObject = modelElement.getXObject();
        this.imageWidth = modelElement.getImageWidth();
        this.imageHeight = modelElement.getImageHeight();
        calculateImageDimensions(layoutBox, t, xObject);
        OverflowPropertyValue overflowX = this.parent != null ? (OverflowPropertyValue) this.parent.getProperty(103) : OverflowPropertyValue.FIT;
        if (this.parent instanceof LineRenderer) {
            nowrap = Boolean.TRUE.equals(this.parent.getOwnProperty(118));
        } else {
            nowrap = false;
        }
        List<Rectangle> floatRendererAreas2 = layoutContext.getFloatRendererAreas();
        float clearHeightCorrection2 = FloatingHelper.calculateClearHeightCorrection(this, floatRendererAreas2, layoutBox);
        FloatPropertyValue floatPropertyValue = (FloatPropertyValue) getProperty(99);
        if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
            layoutBox.decreaseHeight(clearHeightCorrection2);
            FloatingHelper.adjustFloatedBlockLayoutBox(this, layoutBox, this.width, floatRendererAreas2, floatPropertyValue, overflowX);
            clearHeightCorrection = clearHeightCorrection2;
        } else {
            clearHeightCorrection = FloatingHelper.adjustLayoutBoxAccordingToFloats(floatRendererAreas2, layoutBox, this.width, clearHeightCorrection2, (MarginsCollapseHandler) null);
        }
        applyMargins(layoutBox, false);
        Border[] borders = getBorders();
        applyBorderBox(layoutBox, borders, false);
        Float declaredMaxHeight = retrieveMaxHeight();
        if (this.parent == null || ((declaredMaxHeight == null || declaredMaxHeight.floatValue() > layoutBox.getHeight()) && !layoutContext.isClippedHeight())) {
            overflowY = OverflowPropertyValue.FIT;
        } else {
            overflowY = (OverflowPropertyValue) this.parent.getProperty(104);
        }
        boolean processOverflowX = !isOverflowFit(overflowX) || nowrap;
        boolean processOverflowY = !isOverflowFit(overflowY);
        if (isAbsolutePosition()) {
            applyAbsolutePosition(layoutBox);
        }
        Float f = declaredMaxHeight;
        OverflowPropertyValue overflowPropertyValue = overflowY;
        this.occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(layoutBox.getX(), layoutBox.getY() + layoutBox.getHeight(), 0.0f, 0.0f));
        float imageContainerWidth = this.width.floatValue();
        float imageContainerHeight = this.height.floatValue();
        if (isFixedLayout()) {
            this.fixedXPosition = getPropertyAsFloat(34);
            this.fixedYPosition = getPropertyAsFloat(14);
        }
        Float angle2 = getPropertyAsFloat(55);
        if (angle2 == null) {
            angle = Float.valueOf(0.0f);
        } else {
            angle = angle2;
        }
        t.rotate((double) angle.floatValue());
        this.initialOccupiedAreaBBox = getOccupiedAreaBBox().clone();
        float scaleCoef2 = adjustPositionAfterRotation(angle.floatValue(), layoutBox.getWidth(), layoutBox.getHeight());
        float imageContainerHeight2 = imageContainerHeight * scaleCoef2;
        float imageContainerWidth2 = imageContainerWidth * scaleCoef2;
        this.initialOccupiedAreaBBox.moveDown(imageContainerHeight2);
        this.initialOccupiedAreaBBox.setHeight(imageContainerHeight2);
        this.initialOccupiedAreaBBox.setWidth(imageContainerWidth2);
        if (xObject instanceof PdfFormXObject) {
            OverflowPropertyValue overflowPropertyValue2 = overflowX;
            t.scale((double) scaleCoef2, (double) scaleCoef2);
        }
        applyObjectFit(modelElement.getObjectFit(), this.imageWidth, this.imageHeight);
        if (modelElement.getObjectFit() == ObjectFit.FILL) {
            imageItselfWidth = imageContainerWidth2;
            imageItselfHeight = imageContainerHeight2;
        } else {
            imageItselfWidth = this.renderedImageWidth;
            imageItselfHeight = this.renderedImageHeight;
        }
        getMatrix(t, imageItselfWidth, imageItselfHeight);
        boolean isPlacingForced = false;
        if (this.width.floatValue() > layoutBox.getWidth() || this.height.floatValue() > layoutBox.getHeight()) {
            if (Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
                scaleCoef = scaleCoef2;
                FloatPropertyValue floatPropertyValue2 = floatPropertyValue;
                floatRendererAreas = floatRendererAreas2;
                PdfXObject pdfXObject = xObject;
            } else if ((this.width.floatValue() <= layoutBox.getWidth() || !processOverflowX) && (this.height.floatValue() <= layoutBox.getHeight() || !processOverflowY)) {
                applyMargins(this.initialOccupiedAreaBBox, true);
                applyBorderBox(this.initialOccupiedAreaBBox, true);
                this.occupiedArea.getBBox().setHeight(this.initialOccupiedAreaBBox.getHeight());
                float f2 = scaleCoef2;
                FloatPropertyValue floatPropertyValue3 = floatPropertyValue;
                List<Rectangle> list = floatRendererAreas2;
                PdfXObject pdfXObject2 = xObject;
                return new MinMaxWidthLayoutResult(3, this.occupiedArea, (IRenderer) null, this, this);
            } else {
                scaleCoef = scaleCoef2;
                FloatPropertyValue floatPropertyValue4 = floatPropertyValue;
                floatRendererAreas = floatRendererAreas2;
                PdfXObject pdfXObject3 = xObject;
            }
            isPlacingForced = true;
        } else {
            scaleCoef = scaleCoef2;
            FloatPropertyValue floatPropertyValue5 = floatPropertyValue;
            floatRendererAreas = floatRendererAreas2;
            PdfXObject pdfXObject4 = xObject;
        }
        this.occupiedArea.getBBox().moveDown(this.height.floatValue());
        if (borders[3] != null) {
            float delta = ((float) Math.sin((double) angle.floatValue())) * borders[3].getWidth();
            float renderScaling = this.renderedImageHeight / this.height.floatValue();
            this.height = Float.valueOf(this.height.floatValue() + delta);
            this.renderedImageHeight += delta * renderScaling;
        }
        this.occupiedArea.getBBox().setHeight(this.height.floatValue());
        this.occupiedArea.getBBox().setWidth(this.width.floatValue());
        UnitValue leftMargin = getPropertyAsUnitValue(44);
        Class<ImageRenderer> cls = ImageRenderer.class;
        if (!leftMargin.isPointValue()) {
            float f3 = imageItselfWidth;
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        UnitValue topMargin = getPropertyAsUnitValue(46);
        if (!topMargin.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 46));
        }
        if (!(0.0f == leftMargin.getValue() && 0.0f == topMargin.getValue())) {
            translateImage(leftMargin.getValue(), topMargin.getValue(), t);
            getMatrix(t, imageContainerWidth2, imageContainerHeight2);
        }
        applyBorderBox(this.occupiedArea.getBBox(), borders, true);
        applyMargins(this.occupiedArea.getBBox(), true);
        if (angle.floatValue() != 0.0f) {
            applyRotationLayout(angle.floatValue());
        }
        float unscaledWidth = this.occupiedArea.getBBox().getWidth() / scaleCoef;
        MinMaxWidth minMaxWidth = new MinMaxWidth(unscaledWidth, unscaledWidth, 0.0f);
        UnitValue rendererWidth = (UnitValue) getProperty(77);
        if (rendererWidth == null || !rendererWidth.isPercentValue()) {
            boolean autoScale = hasProperty(3) && ((Boolean) getProperty(3)).booleanValue();
            boolean autoScaleWidth = hasProperty(5) && ((Boolean) getProperty(5)).booleanValue();
            if (autoScale || autoScaleWidth) {
                float f4 = imageContainerWidth2;
                minMaxWidth.setChildrenMinWidth(0.0f);
            } else {
                float f5 = imageContainerWidth2;
            }
        } else {
            minMaxWidth.setChildrenMinWidth(0.0f);
            minMaxWidth.setChildrenMaxWidth(unscaledWidth * (this.imageWidth / retrieveWidth(area.getBBox().getWidth()).floatValue()));
            float f6 = imageContainerWidth2;
        }
        List<Rectangle> floatRendererAreas3 = floatRendererAreas;
        FloatingHelper.removeFloatsAboveRendererBottom(floatRendererAreas3, this);
        LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, floatRendererAreas3, layoutContext.getArea().getBBox(), clearHeightCorrection, false);
        applyAbsolutePositionIfNeeded(layoutContext);
        return new MinMaxWidthLayoutResult(1, editedArea, (IRenderer) null, (IRenderer) null, isPlacingForced ? this : null).setMinMaxWidth(minMaxWidth);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: com.itextpdf.layout.tagging.LayoutTaggingHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(com.itextpdf.layout.renderer.DrawContext r24) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            com.itextpdf.layout.layout.LayoutArea r2 = r0.occupiedArea
            r3 = 1
            r4 = 0
            if (r2 != 0) goto L_0x0020
            java.lang.Class<com.itextpdf.layout.renderer.ImageRenderer> r2 = com.itextpdf.layout.renderer.ImageRenderer.class
            org.slf4j.Logger r2 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r2)
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "Drawing won't be performed."
            r3[r4] = r5
            java.lang.String r4 = "Occupied area has not been initialized. {0}"
            java.lang.String r3 = com.itextpdf.p026io.util.MessageFormatUtil.format(r4, r3)
            r2.error(r3)
            return
        L_0x0020:
            boolean r2 = r23.isRelativePosition()
            if (r2 == 0) goto L_0x0029
            r0.applyRelativePositioningTranslation(r4)
        L_0x0029:
            boolean r5 = r24.isTaggingEnabled()
            r6 = 0
            r7 = 0
            r8 = 0
            if (r5 == 0) goto L_0x005a
            r9 = 108(0x6c, float:1.51E-43)
            java.lang.Object r9 = r0.getProperty(r9)
            r6 = r9
            com.itextpdf.layout.tagging.LayoutTaggingHelper r6 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r6
            if (r6 != 0) goto L_0x003f
            r7 = 1
            goto L_0x005a
        L_0x003f:
            boolean r7 = r6.isArtifact(r0)
            if (r7 != 0) goto L_0x005a
            com.itextpdf.kernel.pdf.tagutils.TagTreePointer r8 = r6.useAutoTaggingPointerAndRememberItsPosition(r0)
            boolean r9 = r6.createTag((com.itextpdf.layout.renderer.IRenderer) r0, (com.itextpdf.kernel.pdf.tagutils.TagTreePointer) r8)
            if (r9 == 0) goto L_0x005a
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r9 = r8.getProperties()
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r10 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getLayoutAttributes(r0, r8)
            r9.addAttributes(r4, r10)
        L_0x005a:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r9 = r24.getCanvas()
            r0.beginTransformationIfApplied(r9)
            r9 = 55
            java.lang.Float r9 = r0.getPropertyAsFloat(r9)
            if (r9 == 0) goto L_0x0073
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r10 = r24.getCanvas()
            r10.saveState()
            r0.applyConcatMatrix(r1, r9)
        L_0x0073:
            super.draw(r24)
            com.itextpdf.kernel.geom.Rectangle r10 = r23.getOccupiedAreaBBox()
            com.itextpdf.kernel.geom.Rectangle r10 = r0.applyMargins(r10, r4)
            boolean r10 = r0.clipBackgroundArea(r1, r10, r3)
            com.itextpdf.layout.layout.LayoutArea r11 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            r0.applyMargins(r11, r4)
            com.itextpdf.layout.layout.LayoutArea r11 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            com.itextpdf.layout.borders.Border[] r12 = r23.getBorders()
            r0.applyBorderBox(r11, r12, r4)
            java.lang.Float r11 = r0.fixedYPosition
            if (r11 != 0) goto L_0x00af
            com.itextpdf.layout.layout.LayoutArea r11 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            float r11 = r11.getY()
            float r12 = r0.pivotY
            float r11 = r11 + r12
            java.lang.Float r11 = java.lang.Float.valueOf(r11)
            r0.fixedYPosition = r11
        L_0x00af:
            java.lang.Float r11 = r0.fixedXPosition
            if (r11 != 0) goto L_0x00c3
            com.itextpdf.layout.layout.LayoutArea r11 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            float r11 = r11.getX()
            java.lang.Float r11 = java.lang.Float.valueOf(r11)
            r0.fixedXPosition = r11
        L_0x00c3:
            if (r9 == 0) goto L_0x00ea
            java.lang.Float r11 = r0.fixedXPosition
            float r11 = r11.floatValue()
            float r12 = r0.rotatedDeltaX
            float r11 = r11 + r12
            java.lang.Float r11 = java.lang.Float.valueOf(r11)
            r0.fixedXPosition = r11
            java.lang.Float r11 = r0.fixedYPosition
            float r11 = r11.floatValue()
            float r12 = r0.rotatedDeltaY
            float r11 = r11 - r12
            java.lang.Float r11 = java.lang.Float.valueOf(r11)
            r0.fixedYPosition = r11
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r11 = r24.getCanvas()
            r11.restoreState()
        L_0x00ea:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r11 = r24.getCanvas()
            if (r5 == 0) goto L_0x0102
            if (r7 == 0) goto L_0x00fb
            com.itextpdf.kernel.pdf.canvas.CanvasArtifact r12 = new com.itextpdf.kernel.pdf.canvas.CanvasArtifact
            r12.<init>()
            r11.openTag((com.itextpdf.kernel.pdf.canvas.CanvasTag) r12)
            goto L_0x0102
        L_0x00fb:
            com.itextpdf.kernel.pdf.tagutils.TagReference r12 = r8.getTagReference()
            r11.openTag((com.itextpdf.kernel.pdf.tagutils.TagReference) r12)
        L_0x0102:
            r0.beginObjectFitImageClipping(r11)
            com.itextpdf.layout.IPropertyContainer r12 = r23.getModelElement()
            com.itextpdf.layout.element.Image r12 = (com.itextpdf.layout.element.Image) r12
            r13 = r12
            com.itextpdf.layout.element.Image r13 = (com.itextpdf.layout.element.Image) r13
            com.itextpdf.kernel.pdf.xobject.PdfXObject r20 = r12.getXObject()
            r23.beginElementOpacityApplying(r24)
            java.lang.Float r12 = r0.width
            float r12 = r12.floatValue()
            float r13 = r0.renderedImageWidth
            float r12 = r12 - r13
            r13 = 1073741824(0x40000000, float:2.0)
            float r21 = r12 / r13
            java.lang.Float r12 = r0.height
            float r12 = r12.floatValue()
            float r14 = r0.renderedImageHeight
            float r12 = r12 - r14
            float r22 = r12 / r13
            float[] r12 = r0.matrix
            r14 = r12[r4]
            r15 = r12[r3]
            r4 = 2
            r16 = r12[r4]
            r4 = 3
            r17 = r12[r4]
            java.lang.Float r4 = r0.fixedXPosition
            float r4 = r4.floatValue()
            float r12 = r0.deltaX
            float r4 = r4 + r12
            float r18 = r4 + r21
            java.lang.Float r4 = r0.fixedYPosition
            float r4 = r4.floatValue()
            float r19 = r4 + r22
            r12 = r11
            r13 = r20
            r12.addXObject(r13, r14, r15, r16, r17, r18, r19)
            r23.endElementOpacityApplying(r24)
            r0.endObjectFitImageClipping(r11)
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r4 = r24.getCanvas()
            r0.endTransformationIfApplied(r4)
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r12 = 19
            java.lang.Boolean r12 = r0.getPropertyAsBoolean(r12)
            boolean r4 = r4.equals(r12)
            if (r4 == 0) goto L_0x0170
            r20.flush()
        L_0x0170:
            if (r5 == 0) goto L_0x0175
            r11.closeTag()
        L_0x0175:
            if (r10 == 0) goto L_0x017a
            r11.restoreState()
        L_0x017a:
            if (r2 == 0) goto L_0x017f
            r0.applyRelativePositioningTranslation(r3)
        L_0x017f:
            com.itextpdf.layout.layout.LayoutArea r4 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            com.itextpdf.layout.borders.Border[] r12 = r23.getBorders()
            r0.applyBorderBox(r4, r12, r3)
            com.itextpdf.layout.layout.LayoutArea r4 = r0.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            r0.applyMargins(r4, r3)
            if (r5 == 0) goto L_0x019f
            if (r7 != 0) goto L_0x019f
            r6.finishTaggingHint(r0)
            r6.restoreAutoTaggingPointerPosition(r0)
        L_0x019f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.ImageRenderer.draw(com.itextpdf.layout.renderer.DrawContext):void");
    }

    public IRenderer getNextRenderer() {
        return null;
    }

    public Rectangle getBorderAreaBBox() {
        applyMargins(this.initialOccupiedAreaBBox, false);
        applyBorderBox(this.initialOccupiedAreaBBox, getBorders(), false);
        if (isRelativePosition()) {
            applyRelativePositioningTranslation(false);
        }
        applyMargins(this.initialOccupiedAreaBBox, true);
        applyBorderBox(this.initialOccupiedAreaBBox, true);
        return this.initialOccupiedAreaBBox;
    }

    /* access modifiers changed from: protected */
    public Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
        return rect;
    }

    public void move(float dxRight, float dyUp) {
        super.move(dxRight, dyUp);
        Rectangle rectangle = this.initialOccupiedAreaBBox;
        if (rectangle != null) {
            rectangle.moveRight(dxRight);
            this.initialOccupiedAreaBBox.moveUp(dyUp);
        }
        Float f = this.fixedXPosition;
        if (f != null) {
            this.fixedXPosition = Float.valueOf(f.floatValue() + dxRight);
        }
        Float f2 = this.fixedYPosition;
        if (f2 != null) {
            this.fixedYPosition = Float.valueOf(f2.floatValue() + dyUp);
        }
    }

    public MinMaxWidth getMinMaxWidth() {
        return ((MinMaxWidthLayoutResult) layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0f))))).getMinMaxWidth();
    }

    /* access modifiers changed from: protected */
    public ImageRenderer autoScale(LayoutArea layoutArea) {
        Rectangle area = layoutArea.getBBox().clone();
        applyMargins(area, false);
        applyBorderBox(area, false);
        float angleScaleCoef = this.imageWidth / this.width.floatValue();
        if (this.width.floatValue() > area.getWidth() * angleScaleCoef) {
            updateHeight(UnitValue.createPointValue((area.getWidth() / this.width.floatValue()) * this.imageHeight));
            updateWidth(UnitValue.createPointValue(area.getWidth() * angleScaleCoef));
        }
        return this;
    }

    private void applyObjectFit(ObjectFit objectFit, float imageWidth2, float imageHeight2) {
        ObjectFit objectFit2 = objectFit;
        ObjectFitApplyingResult result = ObjectFitCalculator.calculateRenderedImageSize(objectFit2, (double) imageWidth2, (double) imageHeight2, (double) this.width.floatValue(), (double) this.height.floatValue());
        this.renderedImageWidth = (float) result.getRenderedImageWidth();
        this.renderedImageHeight = (float) result.getRenderedImageHeight();
        this.doesObjectFitRequireCutting = result.isImageCuttingRequired();
    }

    private void beginObjectFitImageClipping(PdfCanvas canvas) {
        if (this.doesObjectFitRequireCutting) {
            canvas.saveState();
            canvas.rectangle(new Rectangle(this.fixedXPosition.floatValue(), this.fixedYPosition.floatValue(), this.width.floatValue(), this.height.floatValue())).clip().endPath();
        }
    }

    private void endObjectFitImageClipping(PdfCanvas canvas) {
        if (this.doesObjectFitRequireCutting) {
            canvas.restoreState();
        }
    }

    private void calculateImageDimensions(Rectangle layoutBox, AffineTransform t, PdfXObject xObject) {
        this.width = getProperty(77) != null ? retrieveWidth(layoutBox.getWidth()) : null;
        Float declaredHeight = retrieveHeight();
        this.height = declaredHeight;
        Float f = this.width;
        if (f == null && declaredHeight == null) {
            Float valueOf = Float.valueOf(this.imageWidth);
            this.width = valueOf;
            this.height = Float.valueOf((valueOf.floatValue() / this.imageWidth) * this.imageHeight);
        } else if (f == null) {
            this.width = Float.valueOf((declaredHeight.floatValue() / this.imageHeight) * this.imageWidth);
        } else if (declaredHeight == null) {
            this.height = Float.valueOf((f.floatValue() / this.imageWidth) * this.imageHeight);
        }
        Float horizontalScaling = getPropertyAsFloat(29, Float.valueOf(1.0f));
        Float verticalScaling = getPropertyAsFloat(76, Float.valueOf(1.0f));
        if ((xObject instanceof PdfFormXObject) && this.width.floatValue() != this.imageWidth) {
            horizontalScaling = Float.valueOf(horizontalScaling.floatValue() * (this.width.floatValue() / this.imageWidth));
            verticalScaling = Float.valueOf(verticalScaling.floatValue() * (this.height.floatValue() / this.imageHeight));
        }
        if (horizontalScaling.floatValue() != 1.0f) {
            if (xObject instanceof PdfFormXObject) {
                t.scale((double) horizontalScaling.floatValue(), 1.0d);
                this.width = Float.valueOf(this.imageWidth * horizontalScaling.floatValue());
            } else {
                this.width = Float.valueOf(this.width.floatValue() * horizontalScaling.floatValue());
            }
        }
        if (verticalScaling.floatValue() != 1.0f) {
            if (xObject instanceof PdfFormXObject) {
                t.scale(1.0d, (double) verticalScaling.floatValue());
                this.height = Float.valueOf(this.imageHeight * verticalScaling.floatValue());
            } else {
                this.height = Float.valueOf(this.height.floatValue() * verticalScaling.floatValue());
            }
        }
        Float minWidth = retrieveMinWidth(layoutBox.getWidth());
        Float maxWidth = retrieveMaxWidth(layoutBox.getWidth());
        if (minWidth != null && this.width.floatValue() < minWidth.floatValue()) {
            this.height = Float.valueOf(this.height.floatValue() * (minWidth.floatValue() / this.width.floatValue()));
            this.width = minWidth;
        } else if (maxWidth != null && this.width.floatValue() > maxWidth.floatValue()) {
            this.height = Float.valueOf(this.height.floatValue() * (maxWidth.floatValue() / this.width.floatValue()));
            this.width = maxWidth;
        }
        Float minHeight = retrieveMinHeight();
        Float maxHeight = retrieveMaxHeight();
        if (minHeight != null && this.height.floatValue() < minHeight.floatValue()) {
            this.width = Float.valueOf(this.width.floatValue() * (minHeight.floatValue() / this.height.floatValue()));
            this.height = minHeight;
        } else if (maxHeight != null && this.height.floatValue() > maxHeight.floatValue()) {
            this.width = Float.valueOf(this.width.floatValue() * (maxHeight.floatValue() / this.height.floatValue()));
            this.height = maxHeight;
        } else if (declaredHeight != null && !this.height.equals(declaredHeight)) {
            this.width = Float.valueOf(this.width.floatValue() * (declaredHeight.floatValue() / this.height.floatValue()));
            this.height = declaredHeight;
        }
    }

    private void getMatrix(AffineTransform t, float imageItselfScaledWidth, float imageItselfScaledHeight) {
        t.getMatrix(this.matrix);
        Image image = (Image) getModelElement();
        Image image2 = image;
        if (image.getXObject() instanceof PdfImageXObject) {
            float[] fArr = this.matrix;
            fArr[0] = fArr[0] * imageItselfScaledWidth;
            fArr[1] = fArr[1] * imageItselfScaledWidth;
            fArr[2] = fArr[2] * imageItselfScaledHeight;
            fArr[3] = fArr[3] * imageItselfScaledHeight;
        }
    }

    private float adjustPositionAfterRotation(float angle, float maxWidth, float maxHeight) {
        float f = angle;
        if (f != 0.0f) {
            AffineTransform t = AffineTransform.getRotateInstance((double) f);
            Point p00 = t.transform(new Point(0, 0), new Point());
            Point p01 = t.transform(new Point(0.0d, (double) this.height.floatValue()), new Point());
            Point p10 = t.transform(new Point((double) this.width.floatValue(), 0.0d), new Point());
            Point p11 = t.transform(new Point((double) this.width.floatValue(), (double) this.height.floatValue()), new Point());
            double[] xValues = {p01.getX(), p10.getX(), p11.getX()};
            double[] yValues = {p01.getY(), p10.getY(), p11.getY()};
            double minX = p00.getX();
            double minY = p00.getY();
            double maxY = minY;
            int length = xValues.length;
            Point p002 = p00;
            double maxX = minX;
            int i = 0;
            while (i < length) {
                double x = xValues[i];
                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                i++;
                float f2 = angle;
                t = t;
            }
            int length2 = yValues.length;
            Point p012 = p01;
            Point point = p10;
            double maxY2 = maxY;
            int i2 = 0;
            while (i2 < length2) {
                int i3 = length2;
                double y = yValues[i2];
                minY = Math.min(minY, y);
                maxY2 = Math.max(maxY2, y);
                i2++;
                length2 = i3;
                p012 = p012;
            }
            this.height = Float.valueOf((float) (maxY2 - minY));
            this.width = Float.valueOf((float) (maxX - minX));
            this.pivotY = (float) (p002.getY() - minY);
            this.deltaX = -((float) minX);
        }
        float scaleCoeff = 1.0f;
        if (Boolean.TRUE.equals(getPropertyAsBoolean(3))) {
            if (maxWidth / this.width.floatValue() < maxHeight / this.height.floatValue()) {
                scaleCoeff = maxWidth / this.width.floatValue();
                this.height = Float.valueOf(this.height.floatValue() * (maxWidth / this.width.floatValue()));
                this.width = Float.valueOf(maxWidth);
            } else {
                scaleCoeff = maxHeight / this.height.floatValue();
                this.width = Float.valueOf(this.width.floatValue() * (maxHeight / this.height.floatValue()));
                this.height = Float.valueOf(maxHeight);
            }
        } else if (Boolean.TRUE.equals(getPropertyAsBoolean(5))) {
            scaleCoeff = maxWidth / this.width.floatValue();
            this.height = Float.valueOf(this.height.floatValue() * scaleCoeff);
            this.width = Float.valueOf(maxWidth);
        } else if (Boolean.TRUE.equals(getPropertyAsBoolean(4))) {
            scaleCoeff = maxHeight / this.height.floatValue();
            this.height = Float.valueOf(maxHeight);
            this.width = Float.valueOf(this.width.floatValue() * scaleCoeff);
        }
        this.pivotY *= scaleCoeff;
        this.deltaX *= scaleCoeff;
        return scaleCoeff;
    }

    private void translateImage(float xDistance, float yDistance, AffineTransform t) {
        t.translate((double) xDistance, (double) yDistance);
        t.getMatrix(this.matrix);
        Float f = this.fixedXPosition;
        if (f != null) {
            this.fixedXPosition = Float.valueOf(f.floatValue() + ((float) t.getTranslateX()));
        }
        Float f2 = this.fixedYPosition;
        if (f2 != null) {
            this.fixedYPosition = Float.valueOf(f2.floatValue() + ((float) t.getTranslateY()));
        }
    }

    private void applyConcatMatrix(DrawContext drawContext, Float angle) {
        AffineTransform rotationTransform = AffineTransform.getRotateInstance((double) angle.floatValue());
        Rectangle rect = getBorderAreaBBox();
        List<Point> rotatedPoints = transformPoints(rectangleToPointsList(rect), rotationTransform);
        float[] shift = calculateShiftToPositionBBoxOfPointsAt(rect.getX(), rect.getY() + rect.getHeight(), rotatedPoints);
        double[] matrix2 = new double[6];
        rotationTransform.getMatrix(matrix2);
        AffineTransform affineTransform = rotationTransform;
        Rectangle rectangle = rect;
        List<Point> list = rotatedPoints;
        drawContext.getCanvas().concatMatrix(matrix2[0], matrix2[1], matrix2[2], matrix2[3], (double) shift[0], (double) shift[1]);
    }

    private void applyRotationLayout(float angle) {
        float f = angle;
        Border[] borders = getBorders();
        Rectangle rect = getBorderAreaBBox();
        float leftBorderWidth = borders[3] == null ? 0.0f : borders[3].getWidth();
        float rightBorderWidth = borders[1] == null ? 0.0f : borders[1].getWidth();
        float topBorderWidth = borders[0] == null ? 0.0f : borders[0].getWidth();
        if (leftBorderWidth != 0.0f) {
            float gip = (float) Math.sqrt(Math.pow((double) topBorderWidth, 2.0d) + Math.pow((double) leftBorderWidth, 2.0d));
            double atan = Math.atan((double) (topBorderWidth / leftBorderWidth));
            if (f < 0.0f) {
                atan = -atan;
            }
            double d = (double) gip;
            double d2 = (double) f;
            Double.isNaN(d2);
            double cos = Math.cos(d2 - atan);
            Double.isNaN(d);
            double d3 = d * cos;
            double d4 = (double) leftBorderWidth;
            Double.isNaN(d4);
            this.rotatedDeltaX = Math.abs((float) (d3 - d4));
        } else {
            this.rotatedDeltaX = 0.0f;
        }
        rect.moveRight(this.rotatedDeltaX);
        this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() + this.rotatedDeltaX);
        if (rightBorderWidth != 0.0f) {
            float gip2 = (float) Math.sqrt(Math.pow((double) topBorderWidth, 2.0d) + Math.pow((double) leftBorderWidth, 2.0d));
            double atan2 = Math.atan((double) (rightBorderWidth / topBorderWidth));
            if (f < 0.0f) {
                atan2 = -atan2;
            }
            double d5 = (double) gip2;
            double d6 = (double) f;
            Double.isNaN(d6);
            double cos2 = Math.cos(d6 - atan2);
            Double.isNaN(d5);
            double d7 = d5 * cos2;
            double d8 = (double) topBorderWidth;
            Double.isNaN(d8);
            this.rotatedDeltaY = Math.abs((float) (d7 - d8));
        } else {
            this.rotatedDeltaY = 0.0f;
        }
        rect.moveDown(this.rotatedDeltaY);
        if (f < 0.0f) {
            this.rotatedDeltaY += rightBorderWidth;
        }
        this.occupiedArea.getBBox().increaseHeight(this.rotatedDeltaY);
    }

    public float getAscent() {
        return this.occupiedArea.getBBox().getHeight();
    }

    public float getDescent() {
        return 0.0f;
    }
}

package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontCharacteristics;
import com.itextpdf.layout.font.FontFamilySplitter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.PositionedLayoutContext;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.BackgroundBox;
import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.BlendMode;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.BoxSizingPropertyValue;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.Transform;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.NumberUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public abstract class AbstractRenderer implements IRenderer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final float EPS = 1.0E-4f;
    protected static final float INF = 1000000.0f;
    public static final float OVERLAP_EPSILON = 1.0E-4f;
    protected List<IRenderer> childRenderers = new ArrayList();
    protected boolean flushed = false;
    protected boolean isLastRendererForModelElement = true;
    protected IPropertyContainer modelElement;
    protected LayoutArea occupiedArea;
    protected IRenderer parent;
    protected List<IRenderer> positionedRenderers = new ArrayList();
    protected Map<Integer, Object> properties = new HashMap();

    protected AbstractRenderer() {
    }

    protected AbstractRenderer(IElement modelElement2) {
        this.modelElement = modelElement2;
    }

    protected AbstractRenderer(AbstractRenderer other) {
        this.childRenderers = other.childRenderers;
        this.positionedRenderers = other.positionedRenderers;
        this.modelElement = other.modelElement;
        this.flushed = other.flushed;
        LayoutArea layoutArea = other.occupiedArea;
        this.occupiedArea = layoutArea != null ? layoutArea.clone() : null;
        this.parent = other.parent;
        this.properties.putAll(other.properties);
        this.isLastRendererForModelElement = other.isLastRendererForModelElement;
    }

    /* JADX WARNING: type inference failed for: r3v7, types: [com.itextpdf.layout.renderer.IRenderer] */
    /* JADX WARNING: type inference failed for: r2v8, types: [com.itextpdf.layout.renderer.IRenderer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addChild(com.itextpdf.layout.renderer.IRenderer r6) {
        /*
            r5 = this;
            r0 = 52
            java.lang.Object r0 = r6.getProperty(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r0 == 0) goto L_0x0062
            int r1 = r0.intValue()
            r2 = 2
            if (r1 == r2) goto L_0x0062
            int r1 = r0.intValue()
            r2 = 1
            if (r1 != r2) goto L_0x0019
            goto L_0x0062
        L_0x0019:
            int r1 = r0.intValue()
            r2 = 4
            if (r1 != r2) goto L_0x0037
            r1 = r5
        L_0x0021:
            com.itextpdf.layout.renderer.IRenderer r2 = r1.parent
            boolean r3 = r2 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r3 == 0) goto L_0x002b
            r1 = r2
            com.itextpdf.layout.renderer.AbstractRenderer r1 = (com.itextpdf.layout.renderer.AbstractRenderer) r1
            goto L_0x0021
        L_0x002b:
            if (r1 != r5) goto L_0x0033
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r2 = r5.positionedRenderers
            r2.add(r6)
            goto L_0x0061
        L_0x0033:
            r1.addChild(r6)
            goto L_0x0061
        L_0x0037:
            int r1 = r0.intValue()
            r2 = 3
            if (r1 != r2) goto L_0x0061
            r1 = r5
            boolean r2 = noAbsolutePositionInfo(r6)
        L_0x0043:
            boolean r3 = r1.isPositioned()
            if (r3 != 0) goto L_0x0055
            if (r2 != 0) goto L_0x0055
            com.itextpdf.layout.renderer.IRenderer r3 = r1.parent
            boolean r4 = r3 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r4 == 0) goto L_0x0055
            r1 = r3
            com.itextpdf.layout.renderer.AbstractRenderer r1 = (com.itextpdf.layout.renderer.AbstractRenderer) r1
            goto L_0x0043
        L_0x0055:
            if (r1 != r5) goto L_0x005d
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r3 = r5.positionedRenderers
            r3.add(r6)
            goto L_0x0067
        L_0x005d:
            r1.addChild(r6)
            goto L_0x0067
        L_0x0061:
            goto L_0x0067
        L_0x0062:
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r1 = r5.childRenderers
            r1.add(r6)
        L_0x0067:
            boolean r1 = r6 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r1 == 0) goto L_0x00a7
            r1 = r6
            com.itextpdf.layout.renderer.AbstractRenderer r1 = (com.itextpdf.layout.renderer.AbstractRenderer) r1
            boolean r1 = r1.isPositioned()
            if (r1 != 0) goto L_0x00a7
            r1 = r6
            com.itextpdf.layout.renderer.AbstractRenderer r1 = (com.itextpdf.layout.renderer.AbstractRenderer) r1
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r1 = r1.positionedRenderers
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x00a7
            r1 = 0
            r2 = r6
            com.itextpdf.layout.renderer.AbstractRenderer r2 = (com.itextpdf.layout.renderer.AbstractRenderer) r2
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r2 = r2.positionedRenderers
        L_0x0085:
            int r3 = r2.size()
            if (r1 >= r3) goto L_0x00a7
            java.lang.Object r3 = r2.get(r1)
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            boolean r3 = noAbsolutePositionInfo(r3)
            if (r3 == 0) goto L_0x009a
            int r1 = r1 + 1
            goto L_0x0085
        L_0x009a:
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r3 = r5.positionedRenderers
            java.lang.Object r4 = r2.get(r1)
            r3.add(r4)
            r2.remove(r1)
            goto L_0x0085
        L_0x00a7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.AbstractRenderer.addChild(com.itextpdf.layout.renderer.IRenderer):void");
    }

    public IPropertyContainer getModelElement() {
        return this.modelElement;
    }

    public List<IRenderer> getChildRenderers() {
        return this.childRenderers;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.modelElement;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasProperty(int r2) {
        /*
            r1 = this;
            boolean r0 = r1.hasOwnProperty(r2)
            if (r0 != 0) goto L_0x0025
            com.itextpdf.layout.IPropertyContainer r0 = r1.modelElement
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.hasProperty(r2)
            if (r0 != 0) goto L_0x0025
        L_0x0010:
            com.itextpdf.layout.renderer.IRenderer r0 = r1.parent
            if (r0 == 0) goto L_0x0023
            boolean r0 = com.itextpdf.layout.property.Property.isPropertyInherited(r2)
            if (r0 == 0) goto L_0x0023
            com.itextpdf.layout.renderer.IRenderer r0 = r1.parent
            boolean r0 = r0.hasProperty(r2)
            if (r0 == 0) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = 1
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.AbstractRenderer.hasProperty(int):boolean");
    }

    public boolean hasOwnProperty(int property) {
        return this.properties.containsKey(Integer.valueOf(property));
    }

    public boolean hasOwnOrModelProperty(int property) {
        return hasOwnOrModelProperty(this, property);
    }

    public void deleteOwnProperty(int property) {
        this.properties.remove(Integer.valueOf(property));
    }

    public void deleteProperty(int property) {
        if (this.properties.containsKey(Integer.valueOf(property))) {
            this.properties.remove(Integer.valueOf(property));
            return;
        }
        IPropertyContainer iPropertyContainer = this.modelElement;
        if (iPropertyContainer != null) {
            iPropertyContainer.deleteOwnProperty(property);
        }
    }

    public <T1> T1 getProperty(int key) {
        Object obj = this.properties.get(Integer.valueOf(key));
        Object property = obj;
        if (obj != null || this.properties.containsKey(Integer.valueOf(key))) {
            return property;
        }
        IPropertyContainer iPropertyContainer = this.modelElement;
        if (iPropertyContainer != null) {
            Object property2 = iPropertyContainer.getProperty(key);
            Object property3 = property2;
            if (property2 != null || this.modelElement.hasProperty(key)) {
                return property3;
            }
        }
        if (this.parent != null && Property.isPropertyInherited(key)) {
            Object property4 = this.parent.getProperty(key);
            Object property5 = property4;
            if (property4 != null) {
                return property5;
            }
        }
        Object property6 = getDefaultProperty(key);
        if (property6 != null) {
            return property6;
        }
        IPropertyContainer iPropertyContainer2 = this.modelElement;
        if (iPropertyContainer2 != null) {
            return iPropertyContainer2.getDefaultProperty(key);
        }
        return null;
    }

    public <T1> T1 getOwnProperty(int property) {
        return this.properties.get(Integer.valueOf(property));
    }

    public <T1> T1 getProperty(int property, T1 defaultValue) {
        T1 result = getProperty(property);
        return result != null ? result : defaultValue;
    }

    public void setProperty(int property, Object value) {
        this.properties.put(Integer.valueOf(property), value);
    }

    public <T1> T1 getDefaultProperty(int property) {
        return null;
    }

    public PdfFont getPropertyAsFont(int property) {
        return (PdfFont) getProperty(property);
    }

    public Color getPropertyAsColor(int property) {
        return (Color) getProperty(property);
    }

    public TransparentColor getPropertyAsTransparentColor(int property) {
        return (TransparentColor) getProperty(property);
    }

    public Float getPropertyAsFloat(int property) {
        return NumberUtil.asFloat(getProperty(property));
    }

    public Float getPropertyAsFloat(int property, Float defaultValue) {
        return NumberUtil.asFloat(getProperty(property, defaultValue));
    }

    public Boolean getPropertyAsBoolean(int property) {
        return (Boolean) getProperty(property);
    }

    public UnitValue getPropertyAsUnitValue(int property) {
        return (UnitValue) getProperty(property);
    }

    public Integer getPropertyAsInteger(int property) {
        return NumberUtil.asInteger(getProperty(property));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IRenderer renderer : this.childRenderers) {
            sb.append(renderer.toString());
        }
        return sb.toString();
    }

    public LayoutArea getOccupiedArea() {
        return this.occupiedArea;
    }

    public void draw(DrawContext drawContext) {
        applyDestinationsAndAnnotation(drawContext);
        boolean relativePosition = isRelativePosition();
        if (relativePosition) {
            applyRelativePositioningTranslation(false);
        }
        beginElementOpacityApplying(drawContext);
        drawBackground(drawContext);
        drawBorder(drawContext);
        drawChildren(drawContext);
        drawPositionedChildren(drawContext);
        endElementOpacityApplying(drawContext);
        if (relativePosition) {
            applyRelativePositioningTranslation(true);
        }
        this.flushed = true;
    }

    /* access modifiers changed from: protected */
    public void beginElementOpacityApplying(DrawContext drawContext) {
        Float opacity = getPropertyAsFloat(92);
        if (opacity != null && opacity.floatValue() < 1.0f) {
            PdfExtGState extGState = new PdfExtGState();
            extGState.setStrokeOpacity(opacity.floatValue()).setFillOpacity(opacity.floatValue());
            drawContext.getCanvas().saveState().setExtGState(extGState);
        }
    }

    /* access modifiers changed from: protected */
    public void endElementOpacityApplying(DrawContext drawContext) {
        Float opacity = getPropertyAsFloat(92);
        if (opacity != null && opacity.floatValue() < 1.0f) {
            drawContext.getCanvas().restoreState();
        }
    }

    public void drawBackground(DrawContext drawContext) {
        List<BackgroundImage> backgroundImagesList;
        Background background = (Background) getProperty(6);
        Object uncastedBackgroundImage = getProperty(90);
        if (uncastedBackgroundImage instanceof BackgroundImage) {
            backgroundImagesList = Collections.singletonList((BackgroundImage) uncastedBackgroundImage);
        } else {
            backgroundImagesList = (List) getProperty(90);
        }
        if (background != null || backgroundImagesList != null) {
            Rectangle bBox = getOccupiedAreaBBox();
            boolean isTagged = drawContext.isTaggingEnabled();
            if (isTagged) {
                drawContext.getCanvas().openTag((CanvasTag) new CanvasArtifact());
            }
            Rectangle backgroundArea = getBackgroundArea(applyMargins(bBox, false));
            if (backgroundArea.getWidth() <= 0.0f || backgroundArea.getHeight() <= 0.0f) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).info(MessageFormatUtil.format(LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, CommonCssConstants.BACKGROUND));
            } else {
                boolean backgroundAreaIsClipped = false;
                if (background != null) {
                    Rectangle clippedBackgroundArea = applyBackgroundBoxProperty(backgroundArea.clone(), background.getBackgroundClip());
                    backgroundAreaIsClipped = clipBackgroundArea(drawContext, clippedBackgroundArea);
                    drawColorBackground(background, drawContext, clippedBackgroundArea);
                }
                if (backgroundImagesList != null) {
                    backgroundAreaIsClipped = drawBackgroundImagesList(backgroundImagesList, backgroundAreaIsClipped, drawContext, backgroundArea);
                }
                if (backgroundAreaIsClipped) {
                    drawContext.getCanvas().restoreState();
                }
            }
            if (isTagged) {
                drawContext.getCanvas().closeTag();
            }
        }
    }

    private void drawColorBackground(Background background, DrawContext drawContext, Rectangle colorBackgroundArea) {
        TransparentColor backgroundColor = new TransparentColor(background.getColor(), background.getOpacity());
        drawContext.getCanvas().saveState().setFillColor(backgroundColor.getColor());
        backgroundColor.applyFillTransparency(drawContext.getCanvas());
        PdfCanvas canvas = drawContext.getCanvas();
        double x = (double) colorBackgroundArea.getX();
        double extraLeft = (double) background.getExtraLeft();
        Double.isNaN(x);
        Double.isNaN(extraLeft);
        double d = x - extraLeft;
        double y = (double) colorBackgroundArea.getY();
        double extraBottom = (double) background.getExtraBottom();
        Double.isNaN(y);
        Double.isNaN(extraBottom);
        double d2 = y - extraBottom;
        double width = (double) colorBackgroundArea.getWidth();
        double extraLeft2 = (double) background.getExtraLeft();
        Double.isNaN(width);
        Double.isNaN(extraLeft2);
        double d3 = width + extraLeft2;
        double extraRight = (double) background.getExtraRight();
        Double.isNaN(extraRight);
        double d4 = d3 + extraRight;
        double height = (double) colorBackgroundArea.getHeight();
        double extraTop = (double) background.getExtraTop();
        Double.isNaN(height);
        Double.isNaN(extraTop);
        double d5 = height + extraTop;
        double extraBottom2 = (double) background.getExtraBottom();
        Double.isNaN(extraBottom2);
        canvas.rectangle(d, d2, d4, d5 + extraBottom2).fill().restoreState();
    }

    private Rectangle applyBackgroundBoxProperty(Rectangle rectangle, BackgroundBox clip) {
        if (BackgroundBox.PADDING_BOX == clip) {
            applyBorderBox(rectangle, false);
        } else if (BackgroundBox.CONTENT_BOX == clip) {
            applyBorderBox(rectangle, false);
            applyPaddings(rectangle, false);
        }
        return rectangle;
    }

    private boolean drawBackgroundImagesList(List<BackgroundImage> backgroundImagesList, boolean backgroundAreaIsClipped, DrawContext drawContext, Rectangle backgroundArea) {
        for (int i = backgroundImagesList.size() - 1; i >= 0; i--) {
            BackgroundImage backgroundImage = backgroundImagesList.get(i);
            if (backgroundImage != null && backgroundImage.isBackgroundSpecified()) {
                if (!backgroundAreaIsClipped) {
                    backgroundAreaIsClipped = clipBackgroundArea(drawContext, backgroundArea);
                }
                drawBackgroundImage(backgroundImage, drawContext, backgroundArea);
            }
        }
        return backgroundAreaIsClipped;
    }

    private void drawBackgroundImage(BackgroundImage backgroundImage, DrawContext drawContext, Rectangle backgroundArea) {
        Rectangle imageRectangle;
        PdfXObject backgroundXObject;
        Rectangle originBackgroundArea = applyBackgroundBoxProperty(backgroundArea.clone(), backgroundImage.getBackgroundOrigin());
        float[] imageWidthAndHeight = BackgroundSizeCalculationUtil.calculateBackgroundImageSize(backgroundImage, originBackgroundArea.getWidth(), originBackgroundArea.getHeight());
        PdfXObject backgroundXObject2 = backgroundImage.getImage();
        if (backgroundXObject2 == null) {
            backgroundXObject2 = backgroundImage.getForm();
        }
        UnitValue xPosition = UnitValue.createPointValue(0.0f);
        UnitValue yPosition = UnitValue.createPointValue(0.0f);
        if (backgroundXObject2 == null) {
            AbstractLinearGradientBuilder gradientBuilder = backgroundImage.getLinearGradientBuilder();
            if (gradientBuilder != null) {
                backgroundImage.getBackgroundPosition().calculatePositionValues(0.0f, 0.0f, xPosition, yPosition);
                backgroundXObject = createXObject(gradientBuilder, originBackgroundArea, drawContext.getDocument());
                imageRectangle = new Rectangle(originBackgroundArea.getLeft() + xPosition.getValue(), (originBackgroundArea.getTop() - imageWidthAndHeight[1]) - yPosition.getValue(), imageWidthAndHeight[0], imageWidthAndHeight[1]);
            } else {
                return;
            }
        } else {
            backgroundImage.getBackgroundPosition().calculatePositionValues(originBackgroundArea.getWidth() - imageWidthAndHeight[0], originBackgroundArea.getHeight() - imageWidthAndHeight[1], xPosition, yPosition);
            backgroundXObject = backgroundXObject2;
            imageRectangle = new Rectangle(originBackgroundArea.getLeft() + xPosition.getValue(), (originBackgroundArea.getTop() - imageWidthAndHeight[1]) - yPosition.getValue(), imageWidthAndHeight[0], imageWidthAndHeight[1]);
        }
        if (imageRectangle.getWidth() <= 0.0f || imageRectangle.getHeight() <= 0.0f) {
            LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).info(MessageFormatUtil.format(LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, CommonCssConstants.BACKGROUND_IMAGE));
            return;
        }
        drawContext.getCanvas().saveState().rectangle(applyBackgroundBoxProperty(backgroundArea.clone(), backgroundImage.getBackgroundClip())).clip().endPath();
        drawPdfXObject(imageRectangle, backgroundImage, drawContext, backgroundXObject, backgroundArea, originBackgroundArea);
        drawContext.getCanvas().restoreState();
    }

    private static void drawPdfXObject(Rectangle imageRectangle, BackgroundImage backgroundImage, DrawContext drawContext, PdfXObject backgroundXObject, Rectangle backgroundArea, Rectangle originBackgroundArea) {
        boolean isNextOverlaps;
        Rectangle rectangle = imageRectangle;
        Rectangle rectangle2 = backgroundArea;
        BlendMode blendMode = backgroundImage.getBlendMode();
        if (blendMode != BlendMode.NORMAL) {
            drawContext.getCanvas().setExtGState(new PdfExtGState().setBlendMode(blendMode.getPdfRepresentation()));
        }
        Point whitespace = backgroundImage.getRepeat().prepareRectangleToDrawingAndGetWhitespace(imageRectangle, originBackgroundArea, backgroundImage.getBackgroundSize());
        float initialX = imageRectangle.getX();
        int counterY = 1;
        boolean firstDraw = true;
        while (true) {
            drawPdfXObjectHorizontally(imageRectangle, backgroundImage, drawContext, backgroundXObject, backgroundArea, firstDraw, (float) whitespace.getX());
            firstDraw = false;
            imageRectangle.setX(initialX);
            boolean isCurrentOverlaps = imageRectangle.overlaps(rectangle2, 1.0E-4f);
            if (counterY % 2 == 1) {
                isNextOverlaps = imageRectangle.moveDown((imageRectangle.getHeight() + ((float) whitespace.getY())) * ((float) counterY)).overlaps(rectangle2, 1.0E-4f);
            } else {
                isNextOverlaps = imageRectangle.moveUp((imageRectangle.getHeight() + ((float) whitespace.getY())) * ((float) counterY)).overlaps(rectangle2, 1.0E-4f);
            }
            counterY++;
            if (backgroundImage.getRepeat().isNoRepeatOnYAxis()) {
                return;
            }
            if (!isCurrentOverlaps && !isNextOverlaps) {
                return;
            }
        }
    }

    private static void drawPdfXObjectHorizontally(Rectangle imageRectangle, BackgroundImage backgroundImage, DrawContext drawContext, PdfXObject backgroundXObject, Rectangle backgroundArea, boolean firstDraw, float xWhitespace) {
        boolean isNextOverlaps;
        boolean isItFirstDraw = firstDraw;
        int counterX = 1;
        while (true) {
            if (imageRectangle.overlaps(backgroundArea, 1.0E-4f) || isItFirstDraw) {
                drawContext.getCanvas().addXObjectFittedIntoRectangle(backgroundXObject, imageRectangle);
                isItFirstDraw = false;
            }
            boolean isCurrentOverlaps = imageRectangle.overlaps(backgroundArea, 1.0E-4f);
            if (counterX % 2 == 1) {
                isNextOverlaps = imageRectangle.moveRight((imageRectangle.getWidth() + xWhitespace) * ((float) counterX)).overlaps(backgroundArea, 1.0E-4f);
            } else {
                isNextOverlaps = imageRectangle.moveLeft((imageRectangle.getWidth() + xWhitespace) * ((float) counterX)).overlaps(backgroundArea, 1.0E-4f);
            }
            counterX++;
            if (backgroundImage.getRepeat().isNoRepeatOnXAxis()) {
                return;
            }
            if (!isCurrentOverlaps && !isNextOverlaps) {
                return;
            }
        }
    }

    public static PdfFormXObject createXObject(AbstractLinearGradientBuilder linearGradientBuilder, Rectangle xObjectArea, PdfDocument document) {
        Color gradientColor;
        Rectangle formBBox = new Rectangle(0.0f, 0.0f, xObjectArea.getWidth(), xObjectArea.getHeight());
        PdfFormXObject xObject = new PdfFormXObject(formBBox);
        if (!(linearGradientBuilder == null || (gradientColor = linearGradientBuilder.buildColor(formBBox, (AffineTransform) null, document)) == null)) {
            new PdfCanvas(xObject, document).setColor(gradientColor, true).rectangle(formBBox).fill();
        }
        return xObject;
    }

    /* access modifiers changed from: protected */
    public Rectangle getBackgroundArea(Rectangle occupiedAreaWithMargins) {
        return occupiedAreaWithMargins;
    }

    /* access modifiers changed from: protected */
    public boolean clipBorderArea(DrawContext drawContext, Rectangle outerBorderBox) {
        return clipArea(drawContext, outerBorderBox, true, true, false, true);
    }

    /* access modifiers changed from: protected */
    public boolean clipBackgroundArea(DrawContext drawContext, Rectangle outerBorderBox) {
        return clipArea(drawContext, outerBorderBox, true, false, false, false);
    }

    /* access modifiers changed from: protected */
    public boolean clipBackgroundArea(DrawContext drawContext, Rectangle outerBorderBox, boolean considerBordersBeforeClipping) {
        return clipArea(drawContext, outerBorderBox, true, false, considerBordersBeforeClipping, false);
    }

    private boolean clipArea(DrawContext drawContext, Rectangle outerBorderBox, boolean clipOuter, boolean clipInner, boolean considerBordersBeforeOuterClipping, boolean considerBordersBeforeInnerClipping) {
        float[] borderWidths;
        Rectangle rectangle = outerBorderBox;
        if (!considerBordersBeforeOuterClipping || !considerBordersBeforeInnerClipping) {
            float[] borderWidths2 = {0.0f, 0.0f, 0.0f, 0.0f};
            float[] outerBox = {outerBorderBox.getTop(), outerBorderBox.getRight(), outerBorderBox.getBottom(), outerBorderBox.getLeft()};
            BorderRadius[] borderRadii = getBorderRadii();
            float[] verticalRadii = calculateRadii(borderRadii, rectangle, false);
            float[] horizontalRadii = calculateRadii(borderRadii, rectangle, true);
            boolean hasNotNullRadius = false;
            for (int i = 0; i < 4; i++) {
                verticalRadii[i] = Math.min(verticalRadii[i], outerBorderBox.getHeight() / 2.0f);
                horizontalRadii[i] = Math.min(horizontalRadii[i], outerBorderBox.getWidth() / 2.0f);
                if (!hasNotNullRadius && !(0.0f == verticalRadii[i] && 0.0f == horizontalRadii[i])) {
                    hasNotNullRadius = true;
                }
            }
            if (hasNotNullRadius) {
                float[] cornersX = {outerBox[3] + horizontalRadii[0], outerBox[1] - horizontalRadii[1], outerBox[1] - horizontalRadii[2], outerBox[3] + horizontalRadii[3]};
                float[] cornersY = {outerBox[0] - verticalRadii[0], outerBox[0] - verticalRadii[1], outerBox[2] + verticalRadii[2], outerBox[2] + verticalRadii[3]};
                PdfCanvas canvas = drawContext.getCanvas();
                canvas.saveState();
                if (considerBordersBeforeOuterClipping) {
                    borderWidths = decreaseBorderRadiiWithBorders(horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
                } else {
                    borderWidths = borderWidths2;
                }
                if (clipOuter) {
                    BorderRadius[] borderRadiusArr = borderRadii;
                    clipOuterArea(canvas, 0.44769999384880066d, horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
                }
                if (considerBordersBeforeInnerClipping) {
                    borderWidths = decreaseBorderRadiiWithBorders(horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
                }
                if (clipInner) {
                    clipInnerArea(canvas, 0.44769999384880066d, horizontalRadii, verticalRadii, outerBox, cornersX, cornersY, borderWidths);
                }
                float[] fArr = borderWidths;
            }
            return hasNotNullRadius;
        }
        throw new AssertionError();
    }

    private void clipOuterArea(PdfCanvas canvas, double curv, float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY) {
        float y4;
        float y3;
        float x3;
        float y42;
        float right;
        float left;
        float top;
        PdfCanvas pdfCanvas = canvas;
        float top2 = outerBox[0];
        float right2 = outerBox[1];
        float bottom = outerBox[2];
        float left2 = outerBox[3];
        float x1 = cornersX[0];
        float y1 = cornersY[0];
        float x2 = cornersX[1];
        float y2 = cornersY[1];
        float x32 = cornersX[2];
        float y32 = cornersY[2];
        float x4 = cornersX[3];
        float y43 = cornersY[3];
        if (0.0f == horizontalRadii[0] && 0.0f == verticalRadii[0]) {
            right = right2;
            y4 = y43;
            left = left2;
            float f = x1;
            float f2 = y1;
            x3 = x32;
            y3 = y32;
            y42 = top2;
        } else {
            float right3 = right2;
            PdfCanvas lineTo = pdfCanvas.moveTo((double) left2, (double) bottom).lineTo((double) left2, (double) y1);
            left = left2;
            double d = (double) y1;
            y4 = y43;
            double d2 = (double) verticalRadii[0];
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (d2 * curv);
            double d4 = (double) x1;
            double d5 = (double) horizontalRadii[0];
            Double.isNaN(d5);
            Double.isNaN(d4);
            y42 = top2;
            x3 = x32;
            y3 = y32;
            float f3 = x1;
            float f4 = y1;
            double d6 = (double) left;
            right = right3;
            lineTo.curveTo(d6, d3, d4 - (d5 * curv), (double) y42, (double) x1, (double) y42).lineTo((double) right, (double) y42).lineTo((double) right, (double) bottom).lineTo((double) left, (double) bottom);
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[1] && 0.0f == verticalRadii[1]) {
            top = y42;
        } else {
            PdfCanvas lineTo2 = pdfCanvas.moveTo((double) left, (double) y42).lineTo((double) x2, (double) y42);
            double d7 = (double) x2;
            double d8 = (double) horizontalRadii[1];
            Double.isNaN(d8);
            Double.isNaN(d7);
            double d9 = (double) y2;
            double d10 = (double) verticalRadii[1];
            Double.isNaN(d10);
            Double.isNaN(d9);
            top = y42;
            lineTo2.curveTo(d7 + (d8 * curv), (double) y42, (double) right, d9 + (d10 * curv), (double) right, (double) y2).lineTo((double) right, (double) bottom).lineTo((double) left, (double) bottom).lineTo((double) left, (double) top);
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[2] && 0.0f == verticalRadii[2]) {
            float f5 = x2;
        } else {
            float y33 = y3;
            PdfCanvas lineTo3 = pdfCanvas.moveTo((double) right, (double) top).lineTo((double) right, (double) y33);
            double d11 = (double) y33;
            double d12 = (double) verticalRadii[2];
            Double.isNaN(d12);
            Double.isNaN(d11);
            double d13 = d11 - (d12 * curv);
            float x33 = x3;
            double d14 = (double) x33;
            float f6 = x2;
            double d15 = (double) horizontalRadii[2];
            Double.isNaN(d15);
            Double.isNaN(d14);
            lineTo3.curveTo((double) right, d13, d14 + (d15 * curv), (double) bottom, (double) x33, (double) bottom).lineTo((double) left, (double) bottom).lineTo((double) left, (double) top).lineTo((double) right, (double) top);
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[3] && 0.0f == verticalRadii[3]) {
            float f7 = y2;
            float f8 = y4;
            return;
        }
        PdfCanvas lineTo4 = pdfCanvas.moveTo((double) right, (double) bottom).lineTo((double) x4, (double) bottom);
        double d16 = (double) x4;
        double d17 = (double) horizontalRadii[3];
        Double.isNaN(d17);
        Double.isNaN(d16);
        float y44 = y4;
        double d18 = (double) y44;
        float f9 = y2;
        double d19 = (double) verticalRadii[3];
        Double.isNaN(d19);
        Double.isNaN(d18);
        lineTo4.curveTo(d16 - (d17 * curv), (double) bottom, (double) left, d18 - (d19 * curv), (double) left, (double) y44).lineTo((double) left, (double) top).lineTo((double) right, (double) top).lineTo((double) right, (double) bottom);
        canvas.clip().endPath();
    }

    private void clipInnerArea(PdfCanvas canvas, double curv, float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY, float[] borderWidths) {
        float x1;
        float y3;
        float y1;
        float x12;
        float right;
        float top;
        float top2;
        float y2;
        float x3;
        float x13;
        float x32;
        float y32;
        float x14;
        float x33;
        float y33;
        float right2;
        float y34;
        PdfCanvas pdfCanvas = canvas;
        float top3 = outerBox[0];
        float right3 = outerBox[1];
        float bottom = outerBox[2];
        float left = outerBox[3];
        float x15 = cornersX[0];
        float y12 = cornersY[0];
        float x2 = cornersX[1];
        float y22 = cornersY[1];
        float x34 = cornersX[2];
        float y35 = cornersY[2];
        float x4 = cornersX[3];
        float y4 = cornersY[3];
        float topBorderWidth = borderWidths[0];
        float rightBorderWidth = borderWidths[1];
        float bottomBorderWidth = borderWidths[2];
        float leftBorderWidth = borderWidths[3];
        if (0.0f == horizontalRadii[0] && 0.0f == verticalRadii[0]) {
            top = top3;
            top2 = right3;
            right = left;
            x1 = x15;
            x12 = y12;
            y1 = x34;
            y3 = y35;
        } else {
            float right4 = right3;
            PdfCanvas moveTo = pdfCanvas.moveTo((double) left, (double) y12);
            double d = (double) y12;
            double d2 = (double) verticalRadii[0];
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (d2 * curv);
            double d4 = (double) x15;
            double d5 = (double) horizontalRadii[0];
            Double.isNaN(d5);
            Double.isNaN(d4);
            double d6 = d4 - (d5 * curv);
            top = top3;
            x1 = x15;
            float y13 = y12;
            top2 = right4;
            PdfCanvas lineTo = moveTo.curveTo((double) left, d3, d6, (double) top, (double) x15, (double) top).lineTo((double) x2, (double) top).lineTo((double) top2, (double) y22).lineTo((double) top2, (double) y35).lineTo((double) x34, (double) bottom).lineTo((double) x4, (double) bottom);
            right = left;
            y4 = y4;
            y1 = x34;
            y3 = y35;
            x12 = y13;
            lineTo.lineTo((double) right, (double) y4).lineTo((double) right, (double) x12).lineTo((double) (right - leftBorderWidth), (double) x12).lineTo((double) (right - leftBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (top2 + rightBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (top2 + rightBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) x12);
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[1] && 0.0f == verticalRadii[1]) {
            x32 = y1;
            x3 = x2;
            y2 = y22;
            y32 = y3;
            x13 = x1;
        } else {
            PdfCanvas moveTo2 = pdfCanvas.moveTo((double) x2, (double) top);
            double d7 = (double) x2;
            double d8 = (double) horizontalRadii[1];
            Double.isNaN(d8);
            Double.isNaN(d7);
            float x35 = y1;
            double d9 = (double) y22;
            double d10 = (double) verticalRadii[1];
            Double.isNaN(d10);
            Double.isNaN(d9);
            y32 = y3;
            x32 = x35;
            x13 = x1;
            x3 = x2;
            y2 = y22;
            moveTo2.curveTo(d7 + (d8 * curv), (double) top, (double) top2, d9 + (d10 * curv), (double) top2, (double) y22).lineTo((double) top2, (double) y32).lineTo((double) x32, (double) bottom).lineTo((double) x4, (double) bottom).lineTo((double) right, (double) y4).lineTo((double) right, (double) x12).lineTo((double) x13, (double) top).lineTo((double) x3, (double) top).lineTo((double) x3, (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (top2 + rightBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (top2 + rightBorderWidth), (double) (top + topBorderWidth)).lineTo((double) x3, (double) (top + topBorderWidth));
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[2] && 0.0f == verticalRadii[2]) {
            y33 = y32;
            y34 = top2;
            x33 = x32;
            x14 = x13;
            right2 = y2;
        } else {
            PdfCanvas moveTo3 = canvas.moveTo((double) top2, (double) y32);
            double d11 = (double) y32;
            float y36 = y32;
            double d12 = (double) verticalRadii[2];
            Double.isNaN(d12);
            Double.isNaN(d11);
            double d13 = d11 - (d12 * curv);
            double d14 = (double) x32;
            double d15 = (double) horizontalRadii[2];
            Double.isNaN(d15);
            Double.isNaN(d14);
            top = top;
            PdfCanvas lineTo2 = moveTo3.curveTo((double) top2, d13, d14 + (d15 * curv), (double) bottom, (double) x32, (double) bottom).lineTo((double) x4, (double) bottom).lineTo((double) right, (double) y4).lineTo((double) right, (double) x12).lineTo((double) x13, (double) top).lineTo((double) x3, (double) top);
            y34 = top2;
            right2 = y2;
            x33 = x32;
            x14 = x13;
            y33 = y36;
            lineTo2.lineTo((double) y34, (double) right2).lineTo((double) y34, (double) y33).lineTo((double) (y34 + rightBorderWidth), (double) y33).lineTo((double) (y34 + rightBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (y34 + rightBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (y34 + rightBorderWidth), (double) y33);
            canvas.clip().endPath();
        }
        if (0.0f == horizontalRadii[3] && 0.0f == verticalRadii[3]) {
            float f = y34;
            float f2 = right2;
            float f3 = x4;
            float f4 = x33;
            return;
        }
        PdfCanvas moveTo4 = canvas.moveTo((double) x4, (double) bottom);
        double d16 = (double) x4;
        double d17 = (double) horizontalRadii[3];
        Double.isNaN(d17);
        Double.isNaN(d16);
        double d18 = (double) y4;
        double d19 = (double) verticalRadii[3];
        Double.isNaN(d19);
        Double.isNaN(d18);
        float right5 = y34;
        float x42 = x4;
        float f5 = right2;
        moveTo4.curveTo(d16 - (d17 * curv), (double) bottom, (double) right, d18 - (d19 * curv), (double) right, (double) y4).lineTo((double) right, (double) x12).lineTo((double) x14, (double) top).lineTo((double) x3, (double) top).lineTo((double) right5, (double) right2).lineTo((double) right5, (double) y33).lineTo((double) x33, (double) bottom).lineTo((double) x42, (double) bottom).lineTo((double) x42, (double) (bottom - bottomBorderWidth)).lineTo((double) (right5 + rightBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) (right5 + rightBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (top + topBorderWidth)).lineTo((double) (right - leftBorderWidth), (double) (bottom - bottomBorderWidth)).lineTo((double) x42, (double) (bottom - bottomBorderWidth));
        canvas.clip().endPath();
    }

    private float[] decreaseBorderRadiiWithBorders(float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY) {
        Border[] borders = getBorders();
        float[] borderWidths = {0.0f, 0.0f, 0.0f, 0.0f};
        if (borders[0] != null) {
            borderWidths[0] = borders[0].getWidth();
            outerBox[0] = outerBox[0] - borders[0].getWidth();
            if (cornersY[1] > outerBox[0]) {
                cornersY[1] = outerBox[0];
            }
            if (cornersY[0] > outerBox[0]) {
                cornersY[0] = outerBox[0];
            }
            verticalRadii[0] = Math.max(0.0f, verticalRadii[0] - borders[0].getWidth());
            verticalRadii[1] = Math.max(0.0f, verticalRadii[1] - borders[0].getWidth());
        }
        if (borders[1] != null) {
            borderWidths[1] = borders[1].getWidth();
            outerBox[1] = outerBox[1] - borders[1].getWidth();
            if (cornersX[1] > outerBox[1]) {
                cornersX[1] = outerBox[1];
            }
            if (cornersX[2] > outerBox[1]) {
                cornersX[2] = outerBox[1];
            }
            horizontalRadii[1] = Math.max(0.0f, horizontalRadii[1] - borders[1].getWidth());
            horizontalRadii[2] = Math.max(0.0f, horizontalRadii[2] - borders[1].getWidth());
        }
        if (borders[2] != null) {
            borderWidths[2] = borders[2].getWidth();
            outerBox[2] = outerBox[2] + borders[2].getWidth();
            if (cornersY[2] < outerBox[2]) {
                cornersY[2] = outerBox[2];
            }
            if (cornersY[3] < outerBox[2]) {
                cornersY[3] = outerBox[2];
            }
            verticalRadii[2] = Math.max(0.0f, verticalRadii[2] - borders[2].getWidth());
            verticalRadii[3] = Math.max(0.0f, verticalRadii[3] - borders[2].getWidth());
        }
        if (borders[3] != null) {
            borderWidths[3] = borders[3].getWidth();
            outerBox[3] = outerBox[3] + borders[3].getWidth();
            if (cornersX[3] < outerBox[3]) {
                cornersX[3] = outerBox[3];
            }
            if (cornersX[0] < outerBox[3]) {
                cornersX[0] = outerBox[3];
            }
            horizontalRadii[3] = Math.max(0.0f, horizontalRadii[3] - borders[3].getWidth());
            horizontalRadii[0] = Math.max(0.0f, horizontalRadii[0] - borders[3].getWidth());
        }
        return borderWidths;
    }

    public void drawChildren(DrawContext drawContext) {
        List<IRenderer> waitingRenderers = new ArrayList<>();
        for (IRenderer child : this.childRenderers) {
            Transform transformProp = (Transform) child.getProperty(53);
            RootRenderer rootRenderer = getRootRenderer();
            processWaitingDrawing(child, transformProp, (rootRenderer == null || rootRenderer.waitingDrawingElements.contains(child)) ? waitingRenderers : rootRenderer.waitingDrawingElements);
            if (!FloatingHelper.isRendererFloating(child) && transformProp == null) {
                child.draw(drawContext);
            }
        }
        for (IRenderer waitingRenderer : waitingRenderers) {
            waitingRenderer.draw(drawContext);
        }
    }

    public void drawBorder(DrawContext drawContext) {
        PdfCanvas canvas;
        Border[] borders = getBorders();
        boolean gotBorders = false;
        int length = borders.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
                break;
            }
            Border border = borders[i];
            if (!gotBorders && border == null) {
                z = false;
            }
            gotBorders = z;
            i++;
        }
        if (gotBorders) {
            float topWidth = borders[0] != null ? borders[0].getWidth() : 0.0f;
            float rightWidth = borders[1] != null ? borders[1].getWidth() : 0.0f;
            float bottomWidth = borders[2] != null ? borders[2].getWidth() : 0.0f;
            float leftWidth = borders[3] != null ? borders[3].getWidth() : 0.0f;
            Rectangle bBox = getBorderAreaBBox();
            if (bBox.getWidth() < 0.0f || bBox.getHeight() < 0.0f) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE, CommonCssConstants.BORDER));
                return;
            }
            float x1 = bBox.getX();
            float y1 = bBox.getY();
            float x2 = bBox.getX() + bBox.getWidth();
            float y2 = bBox.getY() + bBox.getHeight();
            boolean isTagged = drawContext.isTaggingEnabled();
            PdfCanvas canvas2 = drawContext.getCanvas();
            if (isTagged) {
                canvas2.openTag((CanvasTag) new CanvasArtifact());
            }
            Rectangle borderRect = applyMargins(this.occupiedArea.getBBox().clone(), getMargins(), false);
            boolean isAreaClipped = clipBorderArea(drawContext, borderRect);
            BorderRadius[] borderRadii = getBorderRadii();
            float[] verticalRadii = calculateRadii(borderRadii, borderRect, false);
            float[] horizontalRadii = calculateRadii(borderRadii, borderRect, true);
            for (int i2 = 0; i2 < 4; i2++) {
                verticalRadii[i2] = Math.min(verticalRadii[i2], borderRect.getHeight() / 2.0f);
                horizontalRadii[i2] = Math.min(horizontalRadii[i2], borderRect.getWidth() / 2.0f);
            }
            if (borders[0] != null) {
                if (0.0f == horizontalRadii[0] && 0.0f == verticalRadii[0] && 0.0f == horizontalRadii[1] && 0.0f == verticalRadii[1]) {
                    borders[0].draw(canvas2, x1, y2, x2, y2, Border.Side.TOP, leftWidth, rightWidth);
                } else {
                    borders[0].draw(canvas2, x1, y2, x2, y2, horizontalRadii[0], verticalRadii[0], horizontalRadii[1], verticalRadii[1], Border.Side.TOP, leftWidth, rightWidth);
                }
            }
            if (borders[1] != null) {
                if (0.0f != horizontalRadii[1] || 0.0f != verticalRadii[1] || 0.0f != horizontalRadii[2]) {
                    Rectangle rectangle = borderRect;
                    canvas = canvas2;
                } else if (0.0f != verticalRadii[2]) {
                    BorderRadius[] borderRadiusArr = borderRadii;
                    Rectangle rectangle2 = borderRect;
                    canvas = canvas2;
                } else {
                    BorderRadius[] borderRadiusArr2 = borderRadii;
                    Rectangle rectangle3 = borderRect;
                    canvas = canvas2;
                    borders[1].draw(canvas2, x2, y2, x2, y1, Border.Side.RIGHT, topWidth, bottomWidth);
                }
                borders[1].draw(canvas, x2, y2, x2, y1, horizontalRadii[1], verticalRadii[1], horizontalRadii[2], verticalRadii[2], Border.Side.RIGHT, topWidth, bottomWidth);
            } else {
                Rectangle rectangle4 = borderRect;
                canvas = canvas2;
            }
            if (borders[2] != null) {
                if (0.0f == horizontalRadii[2] && 0.0f == verticalRadii[2] && 0.0f == horizontalRadii[3] && 0.0f == verticalRadii[3]) {
                    borders[2].draw(canvas, x2, y1, x1, y1, Border.Side.BOTTOM, rightWidth, leftWidth);
                } else {
                    borders[2].draw(canvas, x2, y1, x1, y1, horizontalRadii[2], verticalRadii[2], horizontalRadii[3], verticalRadii[3], Border.Side.BOTTOM, rightWidth, leftWidth);
                }
            }
            if (borders[3] != null) {
                if (0.0f == horizontalRadii[3] && 0.0f == verticalRadii[3] && 0.0f == horizontalRadii[0] && 0.0f == verticalRadii[0]) {
                    borders[3].draw(canvas, x1, y1, x1, y2, Border.Side.LEFT, bottomWidth, topWidth);
                } else {
                    borders[3].draw(canvas, x1, y1, x1, y2, horizontalRadii[3], verticalRadii[3], horizontalRadii[0], verticalRadii[0], Border.Side.LEFT, bottomWidth, topWidth);
                }
            }
            if (isAreaClipped) {
                drawContext.getCanvas().restoreState();
            }
            if (isTagged) {
                canvas.closeTag();
            }
        }
    }

    public boolean isFlushed() {
        return this.flushed;
    }

    public IRenderer setParent(IRenderer parent2) {
        this.parent = parent2;
        return this;
    }

    public IRenderer getParent() {
        return this.parent;
    }

    public void move(float dxRight, float dyUp) {
        this.occupiedArea.getBBox().moveRight(dxRight);
        this.occupiedArea.getBBox().moveUp(dyUp);
        for (IRenderer childRenderer : this.childRenderers) {
            childRenderer.move(dxRight, dyUp);
        }
        for (IRenderer childRenderer2 : this.positionedRenderers) {
            childRenderer2.move(dxRight, dyUp);
        }
    }

    public List<Rectangle> initElementAreas(LayoutArea area) {
        return Collections.singletonList(area.getBBox());
    }

    public Rectangle getOccupiedAreaBBox() {
        return this.occupiedArea.getBBox().clone();
    }

    public Rectangle getBorderAreaBBox() {
        Rectangle rect = getOccupiedAreaBBox();
        applyMargins(rect, false);
        applyBorderBox(rect, false);
        return rect;
    }

    public Rectangle getInnerAreaBBox() {
        Rectangle rect = getOccupiedAreaBBox();
        applyMargins(rect, false);
        applyBorderBox(rect, false);
        applyPaddings(rect, false);
        return rect;
    }

    public Rectangle applyMargins(Rectangle rect, boolean reverse) {
        return applyMargins(rect, getMargins(), reverse);
    }

    public Rectangle applyBorderBox(Rectangle rect, boolean reverse) {
        return applyBorderBox(rect, getBorders(), reverse);
    }

    public Rectangle applyPaddings(Rectangle rect, boolean reverse) {
        return applyPaddings(rect, getPaddings(), reverse);
    }

    public boolean isFirstOnRootArea() {
        return isFirstOnRootArea(false);
    }

    /* access modifiers changed from: protected */
    public void applyDestinationsAndAnnotation(DrawContext drawContext) {
        applyDestination(drawContext.getDocument());
        applyAction(drawContext.getDocument());
        applyLinkAnnotation(drawContext.getDocument());
    }

    protected static boolean isBorderBoxSizing(IRenderer renderer) {
        BoxSizingPropertyValue boxSizing = (BoxSizingPropertyValue) renderer.getProperty(105);
        return boxSizing != null && boxSizing.equals(BoxSizingPropertyValue.BORDER_BOX);
    }

    /* access modifiers changed from: protected */
    public boolean isOverflowProperty(OverflowPropertyValue equalsTo, int overflowProperty) {
        return isOverflowProperty(equalsTo, (OverflowPropertyValue) getProperty(overflowProperty));
    }

    protected static boolean isOverflowProperty(OverflowPropertyValue equalsTo, IRenderer renderer, int overflowProperty) {
        return isOverflowProperty(equalsTo, (OverflowPropertyValue) renderer.getProperty(overflowProperty));
    }

    protected static boolean isOverflowProperty(OverflowPropertyValue equalsTo, OverflowPropertyValue rendererOverflowProperty) {
        return equalsTo.equals(rendererOverflowProperty) || (equalsTo.equals(OverflowPropertyValue.FIT) && rendererOverflowProperty == null);
    }

    protected static boolean isOverflowFit(OverflowPropertyValue rendererOverflowProperty) {
        return rendererOverflowProperty == null || OverflowPropertyValue.FIT.equals(rendererOverflowProperty);
    }

    static void processWaitingDrawing(IRenderer child, Transform transformProp, List<IRenderer> waitingDrawing) {
        if (FloatingHelper.isRendererFloating(child) || transformProp != null) {
            waitingDrawing.add(child);
        }
        Border outlineProp = (Border) child.getProperty(106);
        if (outlineProp != null && (child instanceof AbstractRenderer)) {
            AbstractRenderer abstractChild = (AbstractRenderer) child;
            if (abstractChild.isRelativePosition()) {
                abstractChild.applyRelativePositioningTranslation(false);
            }
            Div outlines = new Div();
            outlines.getAccessibilityProperties().setRole((String) null);
            if (transformProp != null) {
                outlines.setProperty(53, transformProp);
            }
            outlines.setProperty(9, outlineProp);
            float offset = ((Border) outlines.getProperty(9)).getWidth();
            if (abstractChild.getPropertyAsFloat(107) != null) {
                offset += abstractChild.getPropertyAsFloat(107).floatValue();
            }
            DivRenderer div = new DivRenderer(outlines);
            div.setParent(abstractChild.getParent());
            Rectangle divOccupiedArea = abstractChild.applyMargins(abstractChild.occupiedArea.clone().getBBox(), false).moveLeft(offset).moveDown(offset);
            divOccupiedArea.setWidth(divOccupiedArea.getWidth() + (offset * 2.0f)).setHeight(divOccupiedArea.getHeight() + (offset * 2.0f));
            div.occupiedArea = new LayoutArea(abstractChild.getOccupiedArea().getPageNumber(), divOccupiedArea);
            float outlineWidth = ((Border) div.getProperty(9)).getWidth();
            if (divOccupiedArea.getWidth() >= outlineWidth * 2.0f && divOccupiedArea.getHeight() >= 2.0f * outlineWidth) {
                waitingDrawing.add(div);
            }
            if (abstractChild.isRelativePosition()) {
                abstractChild.applyRelativePositioningTranslation(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Float retrieveWidth(float parentBoxWidth) {
        Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
        Float maxWidth = retrieveUnitValue(parentBoxWidth, 79);
        if (!(maxWidth == null || minWidth == null || minWidth.floatValue() <= maxWidth.floatValue())) {
            maxWidth = minWidth;
        }
        Float width = retrieveUnitValue(parentBoxWidth, 77);
        if (width != null) {
            if (maxWidth != null) {
                width = width.floatValue() > maxWidth.floatValue() ? maxWidth : width;
            }
            if (minWidth != null) {
                width = width.floatValue() < minWidth.floatValue() ? minWidth : width;
            }
        } else if (maxWidth != null) {
            width = maxWidth.floatValue() < parentBoxWidth ? maxWidth : null;
        }
        if (width != null && isBorderBoxSizing(this)) {
            width = Float.valueOf(width.floatValue() - calculatePaddingBorderWidth(this));
        }
        if (width != null) {
            return Float.valueOf(Math.max(0.0f, width.floatValue()));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Float retrieveMaxWidth(float parentBoxWidth) {
        Float maxWidth = retrieveUnitValue(parentBoxWidth, 79);
        if (maxWidth == null) {
            return null;
        }
        Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
        if (minWidth != null && minWidth.floatValue() > maxWidth.floatValue()) {
            maxWidth = minWidth;
        }
        if (isBorderBoxSizing(this)) {
            maxWidth = Float.valueOf(maxWidth.floatValue() - calculatePaddingBorderWidth(this));
        }
        float f = 0.0f;
        if (maxWidth.floatValue() > 0.0f) {
            f = maxWidth.floatValue();
        }
        return Float.valueOf(f);
    }

    /* access modifiers changed from: protected */
    public Float retrieveMinWidth(float parentBoxWidth) {
        Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
        if (minWidth == null) {
            return null;
        }
        if (isBorderBoxSizing(this)) {
            minWidth = Float.valueOf(minWidth.floatValue() - calculatePaddingBorderWidth(this));
        }
        float f = 0.0f;
        if (minWidth.floatValue() > 0.0f) {
            f = minWidth.floatValue();
        }
        return Float.valueOf(f);
    }

    /* access modifiers changed from: protected */
    public void updateWidth(UnitValue updatedWidthValue) {
        if (updatedWidthValue.isPointValue() && isBorderBoxSizing(this)) {
            updatedWidthValue.setValue(updatedWidthValue.getValue() + calculatePaddingBorderWidth(this));
        }
        setProperty(77, updatedWidthValue);
    }

    /* access modifiers changed from: protected */
    public Float retrieveHeight() {
        Float height = null;
        UnitValue heightUV = getPropertyAsUnitValue(27);
        Float parentResolvedHeight = retrieveResolvedParentDeclaredHeight();
        Float minHeight = null;
        Float maxHeight = null;
        if (heightUV != null) {
            if (parentResolvedHeight != null) {
                minHeight = retrieveUnitValue(parentResolvedHeight.floatValue(), 85);
                maxHeight = retrieveUnitValue(parentResolvedHeight.floatValue(), 84);
                height = retrieveUnitValue(parentResolvedHeight.floatValue(), 27);
            } else if (heightUV.isPercentValue()) {
                height = null;
            } else {
                UnitValue minHeightUV = getPropertyAsUnitValue(85);
                if (minHeightUV != null && minHeightUV.isPointValue()) {
                    minHeight = Float.valueOf(minHeightUV.getValue());
                }
                UnitValue maxHeightUV = getPropertyAsUnitValue(84);
                if (maxHeightUV != null && maxHeightUV.isPointValue()) {
                    maxHeight = Float.valueOf(maxHeightUV.getValue());
                }
                height = Float.valueOf(heightUV.getValue());
            }
            if (!(maxHeight == null || minHeight == null || minHeight.floatValue() <= maxHeight.floatValue())) {
                maxHeight = minHeight;
            }
            if (height != null) {
                if (maxHeight != null) {
                    height = height.floatValue() > maxHeight.floatValue() ? maxHeight : height;
                }
                if (minHeight != null) {
                    height = height.floatValue() < minHeight.floatValue() ? minHeight : height;
                }
            }
            if (height != null && isBorderBoxSizing(this)) {
                height = Float.valueOf(height.floatValue() - calculatePaddingBorderHeight(this));
            }
        }
        if (height != null) {
            return Float.valueOf(Math.max(0.0f, height.floatValue()));
        }
        return null;
    }

    private float[] calculateRadii(BorderRadius[] radii, Rectangle area, boolean horizontal) {
        float[] results = new float[4];
        for (int i = 0; i < 4; i++) {
            if (radii[i] != null) {
                UnitValue value = horizontal ? radii[i].getHorizontalRadius() : radii[i].getVerticalRadius();
                if (value == null) {
                    results[i] = 0.0f;
                } else if (value.getUnitType() == 2) {
                    results[i] = (value.getValue() * (horizontal ? area.getWidth() : area.getHeight())) / 100.0f;
                } else if (value.getUnitType() == 1) {
                    results[i] = value.getValue();
                } else {
                    throw new AssertionError();
                }
            } else {
                results[i] = 0.0f;
            }
        }
        return results;
    }

    /* access modifiers changed from: protected */
    public void updateHeight(UnitValue updatedHeight) {
        if (isBorderBoxSizing(this) && updatedHeight.isPointValue()) {
            updatedHeight.setValue(updatedHeight.getValue() + calculatePaddingBorderHeight(this));
        }
        setProperty(27, updatedHeight);
    }

    /* access modifiers changed from: protected */
    public Float retrieveMaxHeight() {
        Float maxHeight;
        Float minHeight = null;
        Float directParentDeclaredHeight = retrieveDirectParentDeclaredHeight();
        UnitValue maxHeightAsUV = getPropertyAsUnitValue(84);
        if (maxHeightAsUV != null) {
            if (directParentDeclaredHeight != null) {
                maxHeight = retrieveUnitValue(directParentDeclaredHeight.floatValue(), 84);
            } else if (maxHeightAsUV.isPercentValue()) {
                maxHeight = null;
            } else {
                minHeight = retrieveMinHeight();
                UnitValue minHeightUV = getPropertyAsUnitValue(85);
                if (minHeightUV != null && minHeightUV.isPointValue()) {
                    minHeight = Float.valueOf(minHeightUV.getValue());
                }
                maxHeight = Float.valueOf(maxHeightAsUV.getValue());
            }
            if (maxHeight != null) {
                if (minHeight != null && minHeight.floatValue() > maxHeight.floatValue()) {
                    maxHeight = minHeight;
                }
                if (isBorderBoxSizing(this)) {
                    maxHeight = Float.valueOf(maxHeight.floatValue() - calculatePaddingBorderHeight(this));
                }
                float f = 0.0f;
                if (maxHeight.floatValue() > 0.0f) {
                    f = maxHeight.floatValue();
                }
                return Float.valueOf(f);
            }
        }
        return retrieveHeight();
    }

    /* access modifiers changed from: protected */
    public void updateMaxHeight(UnitValue updatedMaxHeight) {
        if (isBorderBoxSizing(this) && updatedMaxHeight.isPointValue()) {
            updatedMaxHeight.setValue(updatedMaxHeight.getValue() + calculatePaddingBorderHeight(this));
        }
        setProperty(84, updatedMaxHeight);
    }

    /* access modifiers changed from: protected */
    public Float retrieveMinHeight() {
        Float minHeight;
        Float directParentDeclaredHeight = retrieveDirectParentDeclaredHeight();
        UnitValue minHeightUV = getPropertyAsUnitValue(this, 85);
        if (minHeightUV != null) {
            if (directParentDeclaredHeight != null) {
                minHeight = retrieveUnitValue(directParentDeclaredHeight.floatValue(), 85);
            } else if (minHeightUV.isPercentValue()) {
                minHeight = null;
            } else {
                minHeight = Float.valueOf(minHeightUV.getValue());
            }
            if (minHeight != null) {
                if (isBorderBoxSizing(this)) {
                    minHeight = Float.valueOf(minHeight.floatValue() - calculatePaddingBorderHeight(this));
                }
                float f = 0.0f;
                if (minHeight.floatValue() > 0.0f) {
                    f = minHeight.floatValue();
                }
                return Float.valueOf(f);
            }
        }
        return retrieveHeight();
    }

    /* access modifiers changed from: protected */
    public void updateMinHeight(UnitValue updatedMinHeight) {
        if (isBorderBoxSizing(this) && updatedMinHeight.isPointValue()) {
            updatedMinHeight.setValue(updatedMinHeight.getValue() + calculatePaddingBorderHeight(this));
        }
        setProperty(85, updatedMinHeight);
    }

    /* access modifiers changed from: protected */
    public Float retrieveUnitValue(float baseValue, int property) {
        return retrieveUnitValue(baseValue, property, false);
    }

    /* access modifiers changed from: protected */
    public Float retrieveUnitValue(float baseValue, int property, boolean pointOnly) {
        UnitValue value = (UnitValue) getProperty(property);
        if (pointOnly && value.getUnitType() == 1) {
            LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, Integer.valueOf(property)));
        }
        if (value == null) {
            return null;
        }
        if (value.getUnitType() == 2) {
            return Float.valueOf(value.getValue() != 100.0f ? (value.getValue() * baseValue) / 100.0f : baseValue);
        } else if (value.getUnitType() == 1) {
            return Float.valueOf(value.getValue());
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: protected */
    public Map<Integer, Object> getOwnProperties() {
        return this.properties;
    }

    /* access modifiers changed from: protected */
    public void addAllProperties(Map<Integer, Object> properties2) {
        this.properties.putAll(properties2);
    }

    /* access modifiers changed from: protected */
    public Float getFirstYLineRecursively() {
        if (this.childRenderers.size() == 0) {
            return null;
        }
        return ((AbstractRenderer) this.childRenderers.get(0)).getFirstYLineRecursively();
    }

    /* access modifiers changed from: protected */
    public Float getLastYLineRecursively() {
        Float lastYLine;
        if (!allowLastYLineRecursiveExtraction()) {
            return null;
        }
        for (int i = this.childRenderers.size() - 1; i >= 0; i--) {
            IRenderer child = this.childRenderers.get(i);
            if ((child instanceof AbstractRenderer) && (lastYLine = ((AbstractRenderer) child).getLastYLineRecursively()) != null) {
                return lastYLine;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean allowLastYLineRecursiveExtraction() {
        return !isOverflowProperty(OverflowPropertyValue.HIDDEN, 103) && !isOverflowProperty(OverflowPropertyValue.HIDDEN, 104);
    }

    /* access modifiers changed from: protected */
    public Rectangle applyMargins(Rectangle rect, UnitValue[] margins, boolean reverse) {
        Class<AbstractRenderer> cls = AbstractRenderer.class;
        if (!margins[0].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 46));
        }
        if (!margins[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
        }
        if (!margins[2].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 43));
        }
        if (!margins[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        return rect.applyMargins(margins[0].getValue(), margins[1].getValue(), margins[2].getValue(), margins[3].getValue(), reverse);
    }

    /* access modifiers changed from: protected */
    public UnitValue[] getMargins() {
        return getMargins(this);
    }

    /* access modifiers changed from: protected */
    public UnitValue[] getPaddings() {
        return getPaddings(this);
    }

    /* access modifiers changed from: protected */
    public Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
        Class<AbstractRenderer> cls = AbstractRenderer.class;
        if (!paddings[0].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 50));
        }
        if (!paddings[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 49));
        }
        if (!paddings[2].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 47));
        }
        if (!paddings[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 48));
        }
        return rect.applyMargins(paddings[0].getValue(), paddings[1].getValue(), paddings[2].getValue(), paddings[3].getValue(), reverse);
    }

    /* access modifiers changed from: protected */
    public Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
        return rect.applyMargins(borders[0] != null ? borders[0].getWidth() : 0.0f, borders[1] != null ? borders[1].getWidth() : 0.0f, borders[2] != null ? borders[2].getWidth() : 0.0f, borders[3] != null ? borders[3].getWidth() : 0.0f, reverse);
    }

    /* access modifiers changed from: protected */
    public void applyAbsolutePosition(Rectangle parentRect) {
        Float top = getPropertyAsFloat(73);
        Float bottom = getPropertyAsFloat(14);
        Float left = getPropertyAsFloat(34);
        Float right = getPropertyAsFloat(54);
        if (left == null && right == null && BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7))) {
            right = Float.valueOf(0.0f);
        }
        if (top == null && bottom == null) {
            top = Float.valueOf(0.0f);
        }
        if (right != null) {
            try {
                move((parentRect.getRight() - right.floatValue()) - this.occupiedArea.getBBox().getRight(), 0.0f);
            } catch (Exception e) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Absolute positioning might be applied incorrectly."));
                return;
            }
        }
        if (left != null) {
            move((parentRect.getLeft() + left.floatValue()) - this.occupiedArea.getBBox().getLeft(), 0.0f);
        }
        if (top != null) {
            move(0.0f, (parentRect.getTop() - top.floatValue()) - this.occupiedArea.getBBox().getTop());
        }
        if (bottom != null) {
            move(0.0f, (parentRect.getBottom() + bottom.floatValue()) - this.occupiedArea.getBBox().getBottom());
        }
    }

    /* access modifiers changed from: protected */
    public void applyRelativePositioningTranslation(boolean reverse) {
        Float valueOf = Float.valueOf(0.0f);
        float top = getPropertyAsFloat(73, valueOf).floatValue();
        float bottom = getPropertyAsFloat(14, valueOf).floatValue();
        float left = getPropertyAsFloat(34, valueOf).floatValue();
        float right = getPropertyAsFloat(54, valueOf).floatValue();
        int reverseMultiplier = reverse ? -1 : 1;
        float dxRight = left != 0.0f ? ((float) reverseMultiplier) * left : (-right) * ((float) reverseMultiplier);
        float dyUp = top != 0.0f ? (-top) * ((float) reverseMultiplier) : ((float) reverseMultiplier) * bottom;
        if (dxRight != 0.0f || dyUp != 0.0f) {
            move(dxRight, dyUp);
        }
    }

    /* access modifiers changed from: protected */
    public void applyDestination(PdfDocument document) {
        String destination = (String) getProperty(17);
        if (destination != null) {
            int pageNumber = this.occupiedArea.getPageNumber();
            if (pageNumber < 1 || pageNumber > document.getNumberOfPages()) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).warn(MessageFormatUtil.format(LogMessageConstant.f1196x4ac94ef4, "Property.DESTINATION, which specifies this element location as destination, see ElementPropertyContainer.setDestination."));
                return;
            }
            PdfArray array = new PdfArray();
            array.add(document.getPage(pageNumber).getPdfObject());
            array.add(PdfName.XYZ);
            array.add(new PdfNumber((double) this.occupiedArea.getBBox().getX()));
            array.add(new PdfNumber((double) (this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight())));
            array.add(new PdfNumber(0));
            document.addNamedDestination(destination, array.makeIndirect(document));
            deleteProperty(17);
        }
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [com.itextpdf.kernel.pdf.annot.PdfAnnotation] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void applyAction(com.itextpdf.kernel.pdf.PdfDocument r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object r1 = r9.getProperty(r0)
            com.itextpdf.kernel.pdf.action.PdfAction r1 = (com.itextpdf.kernel.pdf.action.PdfAction) r1
            if (r1 == 0) goto L_0x005b
            r2 = 88
            java.lang.Object r3 = r9.getProperty(r2)
            com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r3 = (com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation) r3
            if (r3 != 0) goto L_0x0058
            com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r4 = new com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation
            com.itextpdf.kernel.geom.Rectangle r5 = new com.itextpdf.kernel.geom.Rectangle
            r6 = 0
            r5.<init>(r6, r6, r6, r6)
            r4.<init>((com.itextpdf.kernel.geom.Rectangle) r5)
            r5 = 4
            com.itextpdf.kernel.pdf.annot.PdfAnnotation r4 = r4.setFlags(r5)
            r3 = r4
            com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r3 = (com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation) r3
            r4 = 9
            java.lang.Object r4 = r9.getProperty(r4)
            com.itextpdf.layout.borders.Border r4 = (com.itextpdf.layout.borders.Border) r4
            r5 = 3
            if (r4 == 0) goto L_0x0048
            com.itextpdf.kernel.pdf.PdfArray r7 = new com.itextpdf.kernel.pdf.PdfArray
            float[] r5 = new float[r5]
            r8 = 0
            r5[r8] = r6
            r5[r0] = r6
            r0 = 2
            float r6 = r4.getWidth()
            r5[r0] = r6
            r7.<init>((float[]) r5)
            r3.setBorder((com.itextpdf.kernel.pdf.PdfArray) r7)
            goto L_0x0055
        L_0x0048:
            com.itextpdf.kernel.pdf.PdfArray r0 = new com.itextpdf.kernel.pdf.PdfArray
            float[] r5 = new float[r5]
            r5 = {0, 0, 0} // fill-array
            r0.<init>((float[]) r5)
            r3.setBorder((com.itextpdf.kernel.pdf.PdfArray) r0)
        L_0x0055:
            r9.setProperty(r2, r3)
        L_0x0058:
            r3.setAction((com.itextpdf.kernel.pdf.action.PdfAction) r1)
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.AbstractRenderer.applyAction(com.itextpdf.kernel.pdf.PdfDocument):void");
    }

    /* access modifiers changed from: protected */
    public void applyLinkAnnotation(PdfDocument document) {
        PdfLinkAnnotation linkAnnotation = (PdfLinkAnnotation) getProperty(88);
        if (linkAnnotation != null) {
            int pageNumber = this.occupiedArea.getPageNumber();
            if (pageNumber < 1 || pageNumber > document.getNumberOfPages()) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).warn(MessageFormatUtil.format(LogMessageConstant.f1196x4ac94ef4, "Property.LINK_ANNOTATION, which specifies a link associated with this element content area, see com.itextpdf.layout.element.Link."));
                return;
            }
            PdfLinkAnnotation linkAnnotation2 = (PdfLinkAnnotation) PdfAnnotation.makeAnnotation((PdfDictionary) ((PdfDictionary) linkAnnotation.getPdfObject()).clone());
            linkAnnotation2.setRectangle(new PdfArray(calculateAbsolutePdfBBox()));
            document.getPage(pageNumber).addAnnotation(linkAnnotation2);
        }
    }

    private Float retrieveResolvedParentDeclaredHeight() {
        IRenderer iRenderer = this.parent;
        if (iRenderer == null || iRenderer.getProperty(27) == null) {
            return null;
        }
        UnitValue parentHeightUV = getPropertyAsUnitValue(this.parent, 27);
        if (parentHeightUV.isPointValue()) {
            return Float.valueOf(parentHeightUV.getValue());
        }
        return ((AbstractRenderer) this.parent).retrieveHeight();
    }

    private Float retrieveDirectParentDeclaredHeight() {
        IRenderer iRenderer = this.parent;
        if (iRenderer == null || iRenderer.getProperty(27) == null) {
            return null;
        }
        UnitValue parentHeightUV = getPropertyAsUnitValue(this.parent, 27);
        if (parentHeightUV.isPointValue()) {
            return Float.valueOf(parentHeightUV.getValue());
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void updateHeightsOnSplit(boolean wasHeightClipped, AbstractRenderer splitRenderer, AbstractRenderer overflowRenderer) {
        updateHeightsOnSplit(this.occupiedArea.getBBox().getHeight(), wasHeightClipped, splitRenderer, overflowRenderer, true);
    }

    /* access modifiers changed from: package-private */
    public void updateHeightsOnSplit(float usedHeight, boolean wasHeightClipped, AbstractRenderer splitRenderer, AbstractRenderer overflowRenderer, boolean enlargeOccupiedAreaOnHeightWasClipped) {
        if (wasHeightClipped) {
            LoggerFactory.getLogger((Class<?>) BlockRenderer.class).warn(LogMessageConstant.CLIP_ELEMENT);
            if (enlargeOccupiedAreaOnHeightWasClipped) {
                Float maxHeight = retrieveMaxHeight();
                splitRenderer.occupiedArea.getBBox().moveDown(maxHeight.floatValue() - usedHeight).setHeight(maxHeight.floatValue());
                usedHeight = maxHeight.floatValue();
            }
        }
        if (overflowRenderer != null && !isKeepTogether()) {
            Float parentResolvedHeightPropertyValue = retrieveResolvedParentDeclaredHeight();
            UnitValue maxHeightUV = getPropertyAsUnitValue(this, 84);
            if (maxHeightUV != null) {
                if (maxHeightUV.isPointValue()) {
                    overflowRenderer.updateMaxHeight(UnitValue.createPointValue(retrieveMaxHeight().floatValue() - usedHeight));
                } else if (parentResolvedHeightPropertyValue != null) {
                    overflowRenderer.updateMinHeight(UnitValue.createPercentValue(maxHeightUV.getValue() - ((usedHeight / parentResolvedHeightPropertyValue.floatValue()) * 100.0f)));
                }
            }
            UnitValue minHeightUV = getPropertyAsUnitValue(this, 85);
            if (minHeightUV != null) {
                if (minHeightUV.isPointValue()) {
                    overflowRenderer.updateMinHeight(UnitValue.createPointValue(retrieveMinHeight().floatValue() - usedHeight));
                } else if (parentResolvedHeightPropertyValue != null) {
                    overflowRenderer.updateMinHeight(UnitValue.createPercentValue(minHeightUV.getValue() - ((usedHeight / parentResolvedHeightPropertyValue.floatValue()) * 100.0f)));
                }
            }
            UnitValue heightUV = getPropertyAsUnitValue(this, 27);
            if (heightUV == null) {
                return;
            }
            if (heightUV.isPointValue()) {
                overflowRenderer.updateHeight(UnitValue.createPointValue(retrieveHeight().floatValue() - usedHeight));
            } else if (parentResolvedHeightPropertyValue != null) {
                overflowRenderer.updateMinHeight(UnitValue.createPercentValue(heightUV.getValue() - ((usedHeight / parentResolvedHeightPropertyValue.floatValue()) * 100.0f)));
            }
        }
    }

    public MinMaxWidth getMinMaxWidth() {
        return MinMaxWidthUtils.countDefaultMinMaxWidth(this);
    }

    /* access modifiers changed from: protected */
    public boolean setMinMaxWidthBasedOnFixedWidth(MinMaxWidth minMaxWidth) {
        Float width;
        if (!hasAbsoluteUnitValue(77) || (width = retrieveWidth(0.0f)) == null) {
            return false;
        }
        minMaxWidth.setChildrenMaxWidth(width.floatValue());
        minMaxWidth.setChildrenMinWidth(width.floatValue());
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isNotFittingHeight(LayoutArea layoutArea) {
        return !isPositioned() && this.occupiedArea.getBBox().getHeight() > layoutArea.getBBox().getHeight();
    }

    /* access modifiers changed from: protected */
    public boolean isNotFittingWidth(LayoutArea layoutArea) {
        return !isPositioned() && this.occupiedArea.getBBox().getWidth() > layoutArea.getBBox().getWidth();
    }

    /* access modifiers changed from: protected */
    public boolean isNotFittingLayoutArea(LayoutArea layoutArea) {
        return isNotFittingHeight(layoutArea) || isNotFittingWidth(layoutArea);
    }

    /* access modifiers changed from: protected */
    public boolean isPositioned() {
        return !isStaticLayout();
    }

    /* access modifiers changed from: protected */
    public boolean isFixedLayout() {
        Integer num = 4;
        return num.equals(getProperty(52));
    }

    /* access modifiers changed from: protected */
    public boolean isStaticLayout() {
        Object positioning = getProperty(52);
        if (positioning == null) {
            return true;
        }
        Integer num = 1;
        return num.equals(positioning);
    }

    /* access modifiers changed from: protected */
    public boolean isRelativePosition() {
        Integer num = 2;
        return num.equals(getPropertyAsInteger(52));
    }

    /* access modifiers changed from: protected */
    public boolean isAbsolutePosition() {
        Integer num = 3;
        return num.equals(getPropertyAsInteger(52));
    }

    /* access modifiers changed from: protected */
    public boolean isKeepTogether() {
        return Boolean.TRUE.equals(getPropertyAsBoolean(32));
    }

    /* access modifiers changed from: protected */
    public void alignChildHorizontally(IRenderer childRenderer, Rectangle currentArea) {
        float availableWidth = currentArea.getWidth();
        HorizontalAlignment horizontalAlignment = (HorizontalAlignment) childRenderer.getProperty(28);
        if (horizontalAlignment != null && horizontalAlignment != HorizontalAlignment.LEFT) {
            float freeSpace = availableWidth - childRenderer.getOccupiedArea().getBBox().getWidth();
            if (freeSpace > 0.0f) {
                try {
                    switch (C14681.$SwitchMap$com$itextpdf$layout$property$HorizontalAlignment[horizontalAlignment.ordinal()]) {
                        case 1:
                            childRenderer.move(freeSpace, 0.0f);
                            return;
                        case 2:
                            childRenderer.move(freeSpace / 2.0f, 0.0f);
                            return;
                        default:
                            return;
                    }
                } catch (NullPointerException e) {
                    LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Some of the children might not end up aligned horizontally."));
                }
            }
        }
    }

    /* renamed from: com.itextpdf.layout.renderer.AbstractRenderer$1 */
    static /* synthetic */ class C14681 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment;

        static {
            int[] iArr = new int[HorizontalAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment = iArr;
            try {
                iArr[HorizontalAlignment.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment[HorizontalAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Border[] getBorders() {
        return getBorders(this);
    }

    /* access modifiers changed from: protected */
    public BorderRadius[] getBorderRadii() {
        return getBorderRadii(this);
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer setBorders(Border border, int borderNumber) {
        switch (borderNumber) {
            case 0:
                setProperty(13, border);
                break;
            case 1:
                setProperty(12, border);
                break;
            case 2:
                setProperty(10, border);
                break;
            case 3:
                setProperty(11, border);
                break;
        }
        return this;
    }

    /* JADX WARNING: type inference failed for: r3v5, types: [com.itextpdf.layout.renderer.IRenderer] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.geom.Rectangle calculateAbsolutePdfBBox() {
        /*
            r6 = this;
            com.itextpdf.kernel.geom.Rectangle r0 = r6.getOccupiedAreaBBox()
            java.util.List r1 = r6.rectangleToPointsList(r0)
            r2 = r6
        L_0x0009:
            com.itextpdf.layout.renderer.IRenderer r3 = r2.parent
            if (r3 == 0) goto L_0x0046
            boolean r3 = r2 instanceof com.itextpdf.layout.renderer.BlockRenderer
            if (r3 == 0) goto L_0x0025
            r3 = 55
            java.lang.Object r3 = r2.getProperty(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            if (r3 == 0) goto L_0x0025
            r4 = r2
            com.itextpdf.layout.renderer.BlockRenderer r4 = (com.itextpdf.layout.renderer.BlockRenderer) r4
            com.itextpdf.kernel.geom.AffineTransform r5 = r4.createRotationTransformInsideOccupiedArea()
            r6.transformPoints(r1, r5)
        L_0x0025:
            r3 = 53
            java.lang.Object r3 = r2.getProperty(r3)
            if (r3 == 0) goto L_0x0040
            boolean r3 = r2 instanceof com.itextpdf.layout.renderer.BlockRenderer
            if (r3 != 0) goto L_0x0039
            boolean r3 = r2 instanceof com.itextpdf.layout.renderer.ImageRenderer
            if (r3 != 0) goto L_0x0039
            boolean r3 = r2 instanceof com.itextpdf.layout.renderer.TableRenderer
            if (r3 == 0) goto L_0x0040
        L_0x0039:
            com.itextpdf.kernel.geom.AffineTransform r3 = r2.createTransformationInsideOccupiedArea()
            r6.transformPoints(r1, r3)
        L_0x0040:
            com.itextpdf.layout.renderer.IRenderer r3 = r2.parent
            r2 = r3
            com.itextpdf.layout.renderer.AbstractRenderer r2 = (com.itextpdf.layout.renderer.AbstractRenderer) r2
            goto L_0x0009
        L_0x0046:
            com.itextpdf.kernel.geom.Rectangle r3 = r6.calculateBBox(r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.AbstractRenderer.calculateAbsolutePdfBBox():com.itextpdf.kernel.geom.Rectangle");
    }

    /* access modifiers changed from: protected */
    public Rectangle calculateBBox(List<Point> points) {
        return Rectangle.calculateBBox(points);
    }

    /* access modifiers changed from: protected */
    public List<Point> rectangleToPointsList(Rectangle rect) {
        return Arrays.asList(rect.toPointsArray());
    }

    /* access modifiers changed from: protected */
    public List<Point> transformPoints(List<Point> points, AffineTransform transform) {
        for (Point point : points) {
            transform.transform(point, point);
        }
        return points;
    }

    /* access modifiers changed from: protected */
    public float[] calculateShiftToPositionBBoxOfPointsAt(float left, float top, List<Point> points) {
        double minX = Double.MAX_VALUE;
        double maxY = -1.7976931348623157E308d;
        for (Point point : points) {
            minX = Math.min(point.getX(), minX);
            maxY = Math.max(point.getY(), maxY);
        }
        double d = (double) left;
        Double.isNaN(d);
        double d2 = (double) top;
        Double.isNaN(d2);
        return new float[]{(float) (d - minX), (float) (d2 - maxY)};
    }

    /* access modifiers changed from: protected */
    public boolean hasAbsoluteUnitValue(int property) {
        UnitValue value = (UnitValue) getProperty(property);
        return value != null && value.isPointValue();
    }

    /* access modifiers changed from: protected */
    public boolean hasRelativeUnitValue(int property) {
        UnitValue value = (UnitValue) getProperty(property);
        return value != null && value.isPercentValue();
    }

    /* access modifiers changed from: package-private */
    public boolean isFirstOnRootArea(boolean checkRootAreaOnly) {
        boolean isFirstOnRootArea = true;
        IRenderer ancestor = this;
        while (isFirstOnRootArea && ancestor.getParent() != null) {
            IRenderer parent2 = ancestor.getParent();
            if (parent2 instanceof RootRenderer) {
                isFirstOnRootArea = ((RootRenderer) parent2).currentArea.isEmptyArea();
            } else if (parent2.getOccupiedArea() == null) {
                break;
            } else if (!checkRootAreaOnly) {
                isFirstOnRootArea = parent2.getOccupiedArea().getBBox().getHeight() < 1.0E-4f;
            }
            ancestor = parent2;
        }
        return isFirstOnRootArea;
    }

    /* access modifiers changed from: package-private */
    public RootRenderer getRootRenderer() {
        for (IRenderer currentRenderer = this; currentRenderer instanceof AbstractRenderer; currentRenderer = ((AbstractRenderer) currentRenderer).getParent()) {
            if (currentRenderer instanceof RootRenderer) {
                return (RootRenderer) currentRenderer;
            }
        }
        return null;
    }

    static float calculateAdditionalWidth(AbstractRenderer renderer) {
        Rectangle dummy = new Rectangle(0.0f, 0.0f);
        renderer.applyMargins(dummy, true);
        renderer.applyBorderBox(dummy, true);
        renderer.applyPaddings(dummy, true);
        return dummy.getWidth();
    }

    static boolean noAbsolutePositionInfo(IRenderer renderer) {
        return !renderer.hasProperty(73) && !renderer.hasProperty(14) && !renderer.hasProperty(34) && !renderer.hasProperty(54);
    }

    static Float getPropertyAsFloat(IRenderer renderer, int property) {
        return NumberUtil.asFloat(renderer.getProperty(property));
    }

    static UnitValue getPropertyAsUnitValue(IRenderer renderer, int property) {
        return (UnitValue) renderer.getProperty(property);
    }

    /* access modifiers changed from: package-private */
    public void shrinkOccupiedAreaForAbsolutePosition() {
        if (isAbsolutePosition()) {
            Float left = getPropertyAsFloat(34);
            Float right = getPropertyAsFloat(54);
            UnitValue width = (UnitValue) getProperty(77);
            if (left == null && right == null && width == null) {
                this.occupiedArea.getBBox().setWidth(0.0f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void drawPositionedChildren(DrawContext drawContext) {
        for (IRenderer positionedChild : this.positionedRenderers) {
            positionedChild.draw(drawContext);
        }
    }

    /* access modifiers changed from: package-private */
    public FontCharacteristics createFontCharacteristics() {
        FontCharacteristics fc = new FontCharacteristics();
        if (hasProperty(95)) {
            fc.setFontWeight((String) getProperty(95));
        }
        if (hasProperty(94)) {
            fc.setFontStyle((String) getProperty(94));
        }
        return fc;
    }

    /* access modifiers changed from: package-private */
    public PdfFont resolveFirstPdfFont() {
        Object font = getProperty(20);
        if (font instanceof PdfFont) {
            return (PdfFont) font;
        }
        if ((font instanceof String) || (font instanceof String[])) {
            boolean z = font instanceof String;
            Object font2 = font;
            if (z) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).warn(LogMessageConstant.f1190x1cf9924b);
                List<String> splitFontFamily = FontFamilySplitter.splitFontFamily((String) font);
                font2 = splitFontFamily.toArray(new String[splitFontFamily.size()]);
            }
            FontProvider provider = (FontProvider) getProperty(91);
            if (provider != null) {
                FontSet fontSet = (FontSet) getProperty(98);
                if (!provider.getFontSet().isEmpty() || (fontSet != null && !fontSet.isEmpty())) {
                    return resolveFirstPdfFont((String[]) font2, provider, createFontCharacteristics(), fontSet);
                }
                throw new IllegalStateException(PdfException.FontProviderNotSetFontFamilyNotResolved);
            }
            throw new IllegalStateException(PdfException.FontProviderNotSetFontFamilyNotResolved);
        }
        throw new IllegalStateException("String[] or PdfFont expected as value of FONT property");
    }

    /* access modifiers changed from: package-private */
    public PdfFont resolveFirstPdfFont(String[] font, FontProvider provider, FontCharacteristics fc, FontSet additionalFonts) {
        return provider.getPdfFont(provider.getFontSelector(Arrays.asList(font), fc, additionalFonts).bestMatch(), additionalFonts);
    }

    static Border[] getBorders(IRenderer renderer) {
        Border border = (Border) renderer.getProperty(9);
        Border[] borders = {(Border) renderer.getProperty(13), (Border) renderer.getProperty(12), (Border) renderer.getProperty(10), (Border) renderer.getProperty(11)};
        if (!hasOwnOrModelProperty(renderer, 13)) {
            borders[0] = border;
        }
        if (!hasOwnOrModelProperty(renderer, 12)) {
            borders[1] = border;
        }
        if (!hasOwnOrModelProperty(renderer, 10)) {
            borders[2] = border;
        }
        if (!hasOwnOrModelProperty(renderer, 11)) {
            borders[3] = border;
        }
        return borders;
    }

    /* access modifiers changed from: package-private */
    public void applyAbsolutePositionIfNeeded(LayoutContext layoutContext) {
        if (isAbsolutePosition()) {
            applyAbsolutePosition((layoutContext instanceof PositionedLayoutContext ? ((PositionedLayoutContext) layoutContext).getParentOccupiedArea() : layoutContext.getArea()).getBBox());
        }
    }

    /* access modifiers changed from: package-private */
    public void preparePositionedRendererAndAreaForLayout(IRenderer childPositionedRenderer, Rectangle fullBbox, Rectangle parentBbox) {
        Float left = getPropertyAsFloat(childPositionedRenderer, 34);
        Float right = getPropertyAsFloat(childPositionedRenderer, 54);
        Float top = getPropertyAsFloat(childPositionedRenderer, 73);
        Float bottom = getPropertyAsFloat(childPositionedRenderer, 14);
        childPositionedRenderer.setParent(this);
        adjustPositionedRendererLayoutBoxWidth(childPositionedRenderer, fullBbox, left, right);
        Integer num = 3;
        if (num.equals(childPositionedRenderer.getProperty(52))) {
            updateMinHeightForAbsolutelyPositionedRenderer(childPositionedRenderer, parentBbox, top, bottom);
        }
    }

    private void updateMinHeightForAbsolutelyPositionedRenderer(IRenderer renderer, Rectangle parentRendererBox, Float top, Float bottom) {
        if (top != null && bottom != null && !renderer.hasProperty(27)) {
            UnitValue currentMaxHeight = getPropertyAsUnitValue(renderer, 84);
            UnitValue currentMinHeight = getPropertyAsUnitValue(renderer, 85);
            float resolvedMinHeight = Math.max(0.0f, ((parentRendererBox.getTop() - top.floatValue()) - parentRendererBox.getBottom()) - bottom.floatValue());
            Rectangle dummy = new Rectangle(0.0f, 0.0f);
            if (!isBorderBoxSizing(renderer)) {
                applyPaddings(dummy, getPaddings(renderer), true);
                applyBorderBox(dummy, getBorders(renderer), true);
            }
            applyMargins(dummy, getMargins(renderer), true);
            float resolvedMinHeight2 = resolvedMinHeight - dummy.getHeight();
            if (currentMinHeight != null) {
                resolvedMinHeight2 = Math.max(resolvedMinHeight2, currentMinHeight.getValue());
            }
            if (currentMaxHeight != null) {
                resolvedMinHeight2 = Math.min(resolvedMinHeight2, currentMaxHeight.getValue());
            }
            renderer.setProperty(85, UnitValue.createPointValue(resolvedMinHeight2));
        }
    }

    private void adjustPositionedRendererLayoutBoxWidth(IRenderer renderer, Rectangle fullBbox, Float left, Float right) {
        if (left != null) {
            fullBbox.setWidth(fullBbox.getWidth() - left.floatValue()).setX(fullBbox.getX() + left.floatValue());
        }
        if (right != null) {
            fullBbox.setWidth(fullBbox.getWidth() - right.floatValue());
        }
        if (left == null && right == null && !renderer.hasProperty(77)) {
            MinMaxWidth minMaxWidth = renderer instanceof BlockRenderer ? ((BlockRenderer) renderer).getMinMaxWidth() : null;
            if (minMaxWidth != null && minMaxWidth.getMaxWidth() < fullBbox.getWidth()) {
                fullBbox.setWidth(minMaxWidth.getMaxWidth() + 1.0E-4f);
            }
        }
    }

    private static float calculatePaddingBorderWidth(AbstractRenderer renderer) {
        Rectangle dummy = new Rectangle(0.0f, 0.0f);
        renderer.applyBorderBox(dummy, true);
        renderer.applyPaddings(dummy, true);
        return dummy.getWidth();
    }

    private static float calculatePaddingBorderHeight(AbstractRenderer renderer) {
        Rectangle dummy = new Rectangle(0.0f, 0.0f);
        renderer.applyBorderBox(dummy, true);
        renderer.applyPaddings(dummy, true);
        return dummy.getHeight();
    }

    private AffineTransform createTransformationInsideOccupiedArea() {
        Rectangle backgroundArea = applyMargins(this.occupiedArea.clone().getBBox(), false);
        float x = backgroundArea.getX();
        float y = backgroundArea.getY();
        float height = backgroundArea.getHeight();
        float width = backgroundArea.getWidth();
        AffineTransform transform = AffineTransform.getTranslateInstance((double) (((width / 2.0f) + x) * -1.0f), (double) (((height / 2.0f) + y) * -1.0f));
        transform.preConcatenate(Transform.getAffineTransform((Transform) getProperty(53), width, height));
        transform.preConcatenate(AffineTransform.getTranslateInstance((double) ((width / 2.0f) + x), (double) ((height / 2.0f) + y)));
        return transform;
    }

    /* access modifiers changed from: protected */
    public void beginTransformationIfApplied(PdfCanvas canvas) {
        if (getProperty(53) != null) {
            canvas.saveState().concatMatrix(createTransformationInsideOccupiedArea());
        }
    }

    /* access modifiers changed from: protected */
    public void endTransformationIfApplied(PdfCanvas canvas) {
        if (getProperty(53) != null) {
            canvas.restoreState();
        }
    }

    private static UnitValue[] getMargins(IRenderer renderer) {
        return new UnitValue[]{(UnitValue) renderer.getProperty(46), (UnitValue) renderer.getProperty(45), (UnitValue) renderer.getProperty(43), (UnitValue) renderer.getProperty(44)};
    }

    private static BorderRadius[] getBorderRadii(IRenderer renderer) {
        BorderRadius radius = (BorderRadius) renderer.getProperty(101);
        BorderRadius[] borderRadii = {(BorderRadius) renderer.getProperty(110), (BorderRadius) renderer.getProperty(111), (BorderRadius) renderer.getProperty(112), (BorderRadius) renderer.getProperty(113)};
        if (!hasOwnOrModelProperty(renderer, 110)) {
            borderRadii[0] = radius;
        }
        if (!hasOwnOrModelProperty(renderer, 111)) {
            borderRadii[1] = radius;
        }
        if (!hasOwnOrModelProperty(renderer, 112)) {
            borderRadii[2] = radius;
        }
        if (!hasOwnOrModelProperty(renderer, 113)) {
            borderRadii[3] = radius;
        }
        return borderRadii;
    }

    private static UnitValue[] getPaddings(IRenderer renderer) {
        return new UnitValue[]{(UnitValue) renderer.getProperty(50), (UnitValue) renderer.getProperty(49), (UnitValue) renderer.getProperty(47), (UnitValue) renderer.getProperty(48)};
    }

    private static boolean hasOwnOrModelProperty(IRenderer renderer, int property) {
        return renderer.hasOwnProperty(property) || (renderer.getModelElement() != null && renderer.getModelElement().hasProperty(property));
    }
}

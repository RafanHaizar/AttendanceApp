package com.itextpdf.layout.element;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.property.ObjectFit;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ImageRenderer;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.image.ImageData;
import org.slf4j.LoggerFactory;

public class Image extends AbstractElement<Image> implements ILeafElement, IAccessibleElement {
    protected DefaultAccessibilityProperties tagProperties;
    protected PdfXObject xObject;

    public Image(PdfImageXObject xObject2) {
        this.xObject = xObject2;
    }

    public Image(PdfFormXObject xObject2) {
        this.xObject = xObject2;
    }

    public Image(PdfImageXObject xObject2, float width) {
        this.xObject = xObject2;
        setWidth(width);
    }

    public Image(PdfImageXObject xObject2, float left, float bottom, float width) {
        this.xObject = xObject2;
        setProperty(34, Float.valueOf(left));
        setProperty(14, Float.valueOf(bottom));
        setWidth(width);
        setProperty(52, 4);
    }

    public Image(PdfImageXObject xObject2, float left, float bottom) {
        this.xObject = xObject2;
        setProperty(34, Float.valueOf(left));
        setProperty(14, Float.valueOf(bottom));
        setProperty(52, 4);
    }

    public Image(PdfFormXObject xObject2, float left, float bottom) {
        this.xObject = xObject2;
        setProperty(34, Float.valueOf(left));
        setProperty(14, Float.valueOf(bottom));
        setProperty(52, 4);
    }

    public Image(ImageData img) {
        this(new PdfImageXObject(checkImageType(img)));
        setProperty(19, true);
    }

    public Image(ImageData img, float left, float bottom) {
        this(new PdfImageXObject(checkImageType(img)), left, bottom);
        setProperty(19, true);
    }

    public Image(ImageData img, float left, float bottom, float width) {
        this(new PdfImageXObject(checkImageType(img)), left, bottom, width);
        setProperty(19, true);
    }

    public PdfXObject getXObject() {
        return this.xObject;
    }

    public Image setRotationAngle(double radAngle) {
        setProperty(55, Double.valueOf(radAngle));
        return this;
    }

    public UnitValue getMarginLeft() {
        return (UnitValue) getProperty(44);
    }

    public Image setMarginLeft(float value) {
        setProperty(44, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getMarginRight() {
        return (UnitValue) getProperty(45);
    }

    public Image setMarginRight(float value) {
        setProperty(45, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getMarginTop() {
        return (UnitValue) getProperty(46);
    }

    public Image setMarginTop(float value) {
        setProperty(46, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getMarginBottom() {
        return (UnitValue) getProperty(43);
    }

    public Image setMarginBottom(float value) {
        setProperty(43, UnitValue.createPointValue(value));
        return this;
    }

    public Image setMargins(float marginTop, float marginRight, float marginBottom, float marginLeft) {
        return setMarginTop(marginTop).setMarginRight(marginRight).setMarginBottom(marginBottom).setMarginLeft(marginLeft);
    }

    public UnitValue getPaddingLeft() {
        return (UnitValue) getProperty(48);
    }

    public Image setPaddingLeft(float value) {
        setProperty(48, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getPaddingRight() {
        return (UnitValue) getProperty(49);
    }

    public Image setPaddingRight(float value) {
        setProperty(49, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getPaddingTop() {
        return (UnitValue) getProperty(50);
    }

    public Image setPaddingTop(float value) {
        setProperty(50, UnitValue.createPointValue(value));
        return this;
    }

    public UnitValue getPaddingBottom() {
        return (UnitValue) getProperty(47);
    }

    public Image setPaddingBottom(float value) {
        setProperty(47, UnitValue.createPointValue(value));
        return this;
    }

    public Image setPadding(float commonPadding) {
        return setPaddings(commonPadding, commonPadding, commonPadding, commonPadding);
    }

    public Image setPaddings(float paddingTop, float paddingRight, float paddingBottom, float paddingLeft) {
        setPaddingTop(paddingTop);
        setPaddingRight(paddingRight);
        setPaddingBottom(paddingBottom);
        setPaddingLeft(paddingLeft);
        return this;
    }

    public Image scale(float horizontalScaling, float verticalScaling) {
        setProperty(29, Float.valueOf(horizontalScaling));
        setProperty(76, Float.valueOf(verticalScaling));
        return this;
    }

    public Image scaleToFit(float fitWidth, float fitHeight) {
        float horizontalScaling = fitWidth / this.xObject.getWidth();
        float verticalScaling = fitHeight / this.xObject.getHeight();
        return scale(Math.min(horizontalScaling, verticalScaling), Math.min(horizontalScaling, verticalScaling));
    }

    public Image scaleAbsolute(float fitWidth, float fitHeight) {
        return scale(fitWidth / this.xObject.getWidth(), fitHeight / this.xObject.getHeight());
    }

    public Image setAutoScale(boolean autoScale) {
        if (hasProperty(5) && hasProperty(4) && autoScale && (((Boolean) getProperty(5)).booleanValue() || ((Boolean) getProperty(4)).booleanValue())) {
            LoggerFactory.getLogger((Class<?>) Image.class).warn(LogMessageConstant.IMAGE_HAS_AMBIGUOUS_SCALE);
        }
        setProperty(3, Boolean.valueOf(autoScale));
        return this;
    }

    public Image setAutoScaleHeight(boolean autoScale) {
        if (!hasProperty(5) || !autoScale || !((Boolean) getProperty(5)).booleanValue()) {
            setProperty(5, Boolean.valueOf(autoScale));
        } else {
            setProperty(5, false);
            setProperty(4, false);
            setProperty(3, true);
        }
        return this;
    }

    public Image setAutoScaleWidth(boolean autoScale) {
        if (!hasProperty(4) || !autoScale || !((Boolean) getProperty(4)).booleanValue()) {
            setProperty(5, Boolean.valueOf(autoScale));
        } else {
            setProperty(5, false);
            setProperty(4, false);
            setProperty(3, true);
        }
        return this;
    }

    public Image setFixedPosition(float left, float bottom) {
        setFixedPosition(left, bottom, getWidth());
        return this;
    }

    public Image setFixedPosition(int pageNumber, float left, float bottom) {
        setFixedPosition(pageNumber, left, bottom, getWidth());
        return this;
    }

    public float getImageWidth() {
        return this.xObject.getWidth();
    }

    public float getImageHeight() {
        return this.xObject.getHeight();
    }

    public Image setHeight(float height) {
        setProperty(27, UnitValue.createPointValue(height));
        return this;
    }

    public Image setHeight(UnitValue height) {
        setProperty(27, height);
        return this;
    }

    public Image setMaxHeight(float maxHeight) {
        setProperty(84, UnitValue.createPointValue(maxHeight));
        return this;
    }

    public Image setMaxHeight(UnitValue maxHeight) {
        setProperty(84, maxHeight);
        return this;
    }

    public Image setMinHeight(float minHeight) {
        setProperty(85, UnitValue.createPointValue(minHeight));
        return this;
    }

    public Image setMinHeight(UnitValue minHeight) {
        setProperty(85, minHeight);
        return this;
    }

    public Image setMaxWidth(float maxWidth) {
        setProperty(79, UnitValue.createPointValue(maxWidth));
        return this;
    }

    public Image setMaxWidth(UnitValue maxWidth) {
        setProperty(79, maxWidth);
        return this;
    }

    public Image setMinWidth(float minWidth) {
        setProperty(80, UnitValue.createPointValue(minWidth));
        return this;
    }

    public Image setMinWidth(UnitValue minWidth) {
        setProperty(80, minWidth);
        return this;
    }

    public Image setWidth(float width) {
        setProperty(77, UnitValue.createPointValue(width));
        return this;
    }

    public Image setWidth(UnitValue width) {
        setProperty(77, width);
        return this;
    }

    public UnitValue getWidth() {
        return (UnitValue) getProperty(77);
    }

    public float getImageScaledWidth() {
        if (getProperty(29) == null) {
            return this.xObject.getWidth();
        }
        return ((Float) getProperty(29)).floatValue() * this.xObject.getWidth();
    }

    public float getImageScaledHeight() {
        if (getProperty(76) == null) {
            return this.xObject.getHeight();
        }
        return ((Float) getProperty(76)).floatValue() * this.xObject.getHeight();
    }

    public Image setObjectFit(ObjectFit objectFit) {
        setProperty(125, objectFit);
        return this;
    }

    public ObjectFit getObjectFit() {
        if (hasProperty(125)) {
            return (ObjectFit) getProperty(125);
        }
        return ObjectFit.FILL;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.FIGURE);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new ImageRenderer(this);
    }

    private static ImageData checkImageType(ImageData image) {
        if (!(image instanceof WmfImageData)) {
            return image;
        }
        throw new PdfException(PdfException.CannotCreateLayoutImageByWmfImage);
    }
}

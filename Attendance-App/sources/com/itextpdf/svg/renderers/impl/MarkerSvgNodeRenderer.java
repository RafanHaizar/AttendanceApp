package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCssUtils;
import com.itextpdf.svg.utils.SvgTextUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarkerSvgNodeRenderer extends AbstractBranchSvgNodeRenderer {
    private static final float DEFAULT_MARKER_HEIGHT = 2.25f;
    private static final float DEFAULT_MARKER_WIDTH = 2.25f;
    private static final float DEFAULT_REF_X = 0.0f;
    private static final float DEFAULT_REF_Y = 0.0f;

    public ISvgNodeRenderer createDeepCopy() {
        MarkerSvgNodeRenderer copy = new MarkerSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }

    /* access modifiers changed from: package-private */
    public void preDraw(SvgDrawContext context) {
        super.preDraw(context);
        float[] markerWidthHeight = getMarkerWidthHeightValues();
        float markerWidth = markerWidthHeight[0];
        float markerHeight = markerWidthHeight[1];
        String xAttribute = getAttribute(SvgConstants.Attributes.f1641X);
        String yAttribute = getAttribute(SvgConstants.Attributes.f1644Y);
        float y = 0.0f;
        float x = xAttribute != null ? CssUtils.parseAbsoluteLength(xAttribute) : 0.0f;
        if (yAttribute != null) {
            y = CssUtils.parseAbsoluteLength(yAttribute);
        }
        context.addViewPort(new Rectangle(x, y, markerWidth, markerHeight));
    }

    /* access modifiers changed from: package-private */
    public void applyMarkerAttributes(SvgDrawContext context) {
        applyRotation(context);
        applyUserSpaceScaling(context);
        applyCoordinatesTranslation(context);
    }

    static void drawMarker(SvgDrawContext context, String moveX, String moveY, MarkerVertexType markerToUse, AbstractSvgNodeRenderer parent) {
        ISvgNodeRenderer template = context.getNamedObject(SvgTextUtil.filterReferenceValue(parent.attributesAndStyles.get(markerToUse.toString())));
        ISvgNodeRenderer namedObject = template == null ? null : template.createDeepCopy();
        if ((namedObject instanceof MarkerSvgNodeRenderer) && markerWidthHeightAreCorrect((MarkerSvgNodeRenderer) namedObject)) {
            namedObject.setParent(parent);
            namedObject.setAttribute(SvgConstants.Tags.MARKER, markerToUse.toString());
            namedObject.setAttribute(SvgConstants.Attributes.f1641X, moveX);
            namedObject.setAttribute(SvgConstants.Attributes.f1644Y, moveY);
            namedObject.draw(context);
            namedObject.setParent((ISvgNodeRenderer) null);
        }
    }

    /* access modifiers changed from: protected */
    public void applyViewBox(SvgDrawContext context) {
        if (this.attributesAndStyles != null) {
            float[] markerWidthHeight = getMarkerWidthHeightValues();
            super.calculateAndApplyViewBox(context, getViewBoxValues(markerWidthHeight[0], markerWidthHeight[1]), context.getCurrentViewPort());
        }
    }

    private float[] getMarkerWidthHeightValues() {
        float markerWidth = 2.25f;
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_WIDTH)) {
            markerWidth = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_WIDTH));
        }
        float markerHeight = 2.25f;
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_HEIGHT)) {
            markerHeight = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_HEIGHT));
        }
        return new float[]{markerWidth, markerHeight};
    }

    private static boolean markerWidthHeightAreCorrect(MarkerSvgNodeRenderer namedObject) {
        Logger log = LoggerFactory.getLogger((Class<?>) MarkerSvgNodeRenderer.class);
        String markerWidth = namedObject.getAttribute(SvgConstants.Attributes.MARKER_WIDTH);
        String markerHeight = namedObject.getAttribute(SvgConstants.Attributes.MARKER_HEIGHT);
        boolean isCorrect = true;
        if (markerWidth != null) {
            float absoluteMarkerWidthValue = CssUtils.parseAbsoluteLength(markerWidth);
            if (absoluteMarkerWidthValue == 0.0f) {
                log.warn(SvgLogMessageConstant.MARKER_WIDTH_IS_ZERO_VALUE);
                isCorrect = false;
            } else if (absoluteMarkerWidthValue < 0.0f) {
                log.warn(SvgLogMessageConstant.MARKER_WIDTH_IS_NEGATIVE_VALUE);
                isCorrect = false;
            }
        }
        if (markerHeight == null) {
            return isCorrect;
        }
        float absoluteMarkerHeightValue = CssUtils.parseAbsoluteLength(markerHeight);
        if (absoluteMarkerHeightValue == 0.0f) {
            log.warn(SvgLogMessageConstant.MARKER_HEIGHT_IS_ZERO_VALUE);
            return false;
        } else if (absoluteMarkerHeightValue >= 0.0f) {
            return isCorrect;
        } else {
            log.warn(SvgLogMessageConstant.MARKER_HEIGHT_IS_NEGATIVE_VALUE);
            return false;
        }
    }

    private void applyRotation(SvgDrawContext context) {
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.ORIENT)) {
            String orient = (String) this.attributesAndStyles.get(SvgConstants.Attributes.ORIENT);
            double rotAngle = Double.NaN;
            if ("auto".equals(orient) || (SvgConstants.Values.AUTO_START_REVERSE.equals(orient) && !SvgConstants.Attributes.MARKER_START.equals(this.attributesAndStyles.get(SvgConstants.Tags.MARKER)))) {
                rotAngle = ((IMarkerCapable) getParent()).getAutoOrientAngle(this, false);
            } else if (SvgConstants.Values.AUTO_START_REVERSE.equals(orient) && SvgConstants.Attributes.MARKER_START.equals(this.attributesAndStyles.get(SvgConstants.Tags.MARKER))) {
                rotAngle = ((IMarkerCapable) getParent()).getAutoOrientAngle(this, true);
            } else if (CssUtils.isAngleValue(orient) || CssUtils.isNumericValue(orient)) {
                rotAngle = (double) CssUtils.parseAngle((String) this.attributesAndStyles.get(SvgConstants.Attributes.ORIENT));
            }
            if (!Double.isNaN(rotAngle)) {
                context.getCurrentCanvas().concatMatrix(AffineTransform.getRotateInstance(rotAngle));
            }
        }
    }

    private void applyUserSpaceScaling(SvgDrawContext context) {
        String parentValue;
        if ((!this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_UNITS) || SvgConstants.Values.STROKEWIDTH.equals(this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_UNITS))) && (parentValue = getParent().getAttribute(SvgConstants.Attributes.STROKE_WIDTH)) != null) {
            double rootViewPortHeight = (double) context.getRootViewPort().getHeight();
            double rootViewPortWidth = (double) context.getRootViewPort().getWidth();
            Double.isNaN(rootViewPortHeight);
            Double.isNaN(rootViewPortHeight);
            Double.isNaN(rootViewPortWidth);
            Double.isNaN(rootViewPortWidth);
            float strokeWidthScale = CssUtils.convertPtsToPx(parseAbsoluteLength(parentValue, (float) CssUtils.convertPxToPts(Math.sqrt((rootViewPortHeight * rootViewPortHeight) + (rootViewPortWidth * rootViewPortWidth))), 1.0f, context));
            context.getCurrentCanvas().concatMatrix(AffineTransform.getScaleInstance((double) strokeWidthScale, (double) strokeWidthScale));
        }
    }

    private void applyCoordinatesTranslation(SvgDrawContext context) {
        float xScale = 1.0f;
        float yScale = 1.0f;
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
            List<String> splitValueList = SvgCssUtils.splitValueList((String) this.attributesAndStyles.get(SvgConstants.Attributes.VIEWBOX));
            float[] viewBox = getViewBoxValues();
            xScale = context.getCurrentViewPort().getWidth() / viewBox[2];
            yScale = context.getCurrentViewPort().getHeight() / viewBox[3];
        }
        float moveX = 0.0f;
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.REFX)) {
            moveX = parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.REFX), context.getRootViewPort().getWidth(), 0.0f, context) * xScale * -1.0f;
        }
        float moveY = 0.0f;
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.REFY)) {
            moveY = parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.REFY), context.getRootViewPort().getHeight(), 0.0f, context) * -1.0f * yScale;
        }
        AffineTransform translation = AffineTransform.getTranslateInstance((double) moveX, (double) moveY);
        if (!translation.isIdentity()) {
            context.getCurrentCanvas().concatMatrix(translation);
        }
    }

    private float[] getViewBoxValues(float defaultWidth, float defaultHeight) {
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
            return super.getViewBoxValues();
        }
        return new float[]{0.0f, 0.0f, defaultWidth, defaultHeight};
    }
}

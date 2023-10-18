package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.gradients.GradientColorStop;
import com.itextpdf.kernel.colors.gradients.LinearGradientBuilder;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import java.util.ArrayList;
import java.util.List;

public class LinearGradientSvgNodeRenderer extends AbstractGradientSvgNodeRenderer implements INoDrawSvgNodeRenderer {
    public Color createColor(SvgDrawContext context, Rectangle objectBoundingBox, float objectBoundingBoxMargin, float parentOpacity) {
        Rectangle rectangle = objectBoundingBox;
        if (rectangle == null) {
            return null;
        }
        LinearGradientBuilder builder = new LinearGradientBuilder();
        for (GradientColorStop stopColor : parseStops(parentOpacity)) {
            builder.addColorStop(stopColor);
        }
        builder.setSpreadMethod(parseSpreadMethod());
        boolean isObjectBoundingBox = isObjectBoundingBoxUnits();
        Point[] coordinates = getCoordinates(context, isObjectBoundingBox);
        builder.setGradientVector(coordinates[0].getX(), coordinates[0].getY(), coordinates[1].getX(), coordinates[1].getY());
        builder.setCurrentSpaceToGradientVectorSpaceTransformation(getGradientTransformToUserSpaceOnUse(rectangle, isObjectBoundingBox));
        boolean z = isObjectBoundingBox;
        return builder.buildColor(objectBoundingBox.applyMargins(objectBoundingBoxMargin, objectBoundingBoxMargin, objectBoundingBoxMargin, objectBoundingBoxMargin, true), context.getCurrentCanvasTransform(), context.getCurrentCanvas().getDocument());
    }

    public ISvgNodeRenderer createDeepCopy() {
        LinearGradientSvgNodeRenderer copy = new LinearGradientSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }

    private List<GradientColorStop> parseStops(float parentOpacity) {
        List<GradientColorStop> stopsList = new ArrayList<>();
        for (StopSvgNodeRenderer stopRenderer : getChildStopRenderers()) {
            stopsList.add(new GradientColorStop(stopRenderer.getStopColor(), stopRenderer.getOffset(), GradientColorStop.OffsetType.RELATIVE));
        }
        if (!stopsList.isEmpty()) {
            GradientColorStop firstStop = stopsList.get(0);
            if (firstStop.getOffset() > 0.0d) {
                stopsList.add(0, new GradientColorStop(firstStop, 0.0d, GradientColorStop.OffsetType.RELATIVE));
            }
            GradientColorStop lastStop = stopsList.get(stopsList.size() - 1);
            if (lastStop.getOffset() < 1.0d) {
                stopsList.add(new GradientColorStop(lastStop, 1.0d, GradientColorStop.OffsetType.RELATIVE));
            }
        }
        return stopsList;
    }

    private AffineTransform getGradientTransformToUserSpaceOnUse(Rectangle objectBoundingBox, boolean isObjectBoundingBox) {
        AffineTransform gradientTransform = new AffineTransform();
        if (isObjectBoundingBox) {
            gradientTransform.translate((double) objectBoundingBox.getX(), (double) objectBoundingBox.getY());
            double width = (double) objectBoundingBox.getWidth();
            Double.isNaN(width);
            double height = (double) objectBoundingBox.getHeight();
            Double.isNaN(height);
            gradientTransform.scale(width / 0.75d, height / 0.75d);
        }
        AffineTransform svgGradientTransformation = getGradientTransform();
        if (svgGradientTransformation != null) {
            gradientTransform.concatenate(svgGradientTransformation);
        }
        return gradientTransform;
    }

    private Point[] getCoordinates(SvgDrawContext context, boolean isObjectBoundingBox) {
        Point end;
        Point start;
        if (isObjectBoundingBox) {
            start = new Point(getCoordinateForObjectBoundingBox(SvgConstants.Attributes.f1642X1, 0.0d), getCoordinateForObjectBoundingBox(SvgConstants.Attributes.f1645Y1, 0.0d));
            end = new Point(getCoordinateForObjectBoundingBox(SvgConstants.Attributes.f1643X2, 1.0d), getCoordinateForObjectBoundingBox(SvgConstants.Attributes.f1646Y2, 0.0d));
        } else {
            Rectangle currentViewPort = context.getCurrentViewPort();
            double x = (double) currentViewPort.getX();
            double y = (double) currentViewPort.getY();
            double width = (double) currentViewPort.getWidth();
            double height = (double) currentViewPort.getHeight();
            float em = getCurrentFontSize();
            float rem = context.getRemValue();
            double height2 = height;
            double width2 = width;
            double y2 = y;
            double y3 = getCoordinateForUserSpaceOnUse(SvgConstants.Attributes.f1642X1, x, x, width, em, rem);
            float f = em;
            float f2 = rem;
            Point start2 = new Point(y3, getCoordinateForUserSpaceOnUse(SvgConstants.Attributes.f1645Y1, y, y, height2, f, f2));
            Double.isNaN(x);
            Double.isNaN(width2);
            Rectangle rectangle = currentViewPort;
            end = new Point(getCoordinateForUserSpaceOnUse(SvgConstants.Attributes.f1643X2, x + width2, x, width2, f, f2), getCoordinateForUserSpaceOnUse(SvgConstants.Attributes.f1646Y2, y2, y2, height2, em, rem));
            start = start2;
        }
        return new Point[]{start, end};
    }

    private double getCoordinateForObjectBoundingBox(String attributeName, double defaultValue) {
        int unitsPosition;
        String attributeValue = getAttribute(attributeName);
        double absoluteValue = defaultValue;
        if (CssUtils.isPercentageValue(attributeValue)) {
            absoluteValue = (double) CssUtils.parseRelativeValue(attributeValue, 1.0f);
        } else if ((CssUtils.isNumericValue(attributeValue) || CssUtils.isMetricValue(attributeValue) || CssUtils.isRelativeValue(attributeValue)) && (unitsPosition = CssUtils.determinePositionBetweenValueAndUnit(attributeValue)) > 0) {
            absoluteValue = CssUtils.parseDouble(attributeValue.substring(0, unitsPosition)).doubleValue();
        }
        return 0.75d * absoluteValue;
    }

    private double getCoordinateForUserSpaceOnUse(String attributeName, double defaultValue, double start, double length, float em, float rem) {
        UnitValue unitValue = CssUtils.parseLengthValueToPt(getAttribute(attributeName), em, rem);
        if (unitValue == null) {
            return defaultValue;
        }
        if (unitValue.getUnitType() != 2) {
            return (double) unitValue.getValue();
        }
        double value = (double) unitValue.getValue();
        Double.isNaN(value);
        return ((value * length) / 100.0d) + start;
    }
}

package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.renderers.path.IPathShape;
import com.itextpdf.svg.renderers.path.SvgPathShapeFactory;
import com.itextpdf.svg.renderers.path.impl.AbstractPathShape;
import com.itextpdf.svg.renderers.path.impl.ClosePath;
import com.itextpdf.svg.renderers.path.impl.IControlPointCurve;
import com.itextpdf.svg.renderers.path.impl.MoveTo;
import com.itextpdf.svg.renderers.path.impl.QuadraticSmoothCurveTo;
import com.itextpdf.svg.renderers.path.impl.SmoothSCurveTo;
import com.itextpdf.svg.utils.SvgCoordinateUtils;
import com.itextpdf.svg.utils.SvgCssUtils;
import com.itextpdf.svg.utils.SvgRegexUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class PathSvgNodeRenderer extends AbstractSvgNodeRenderer implements IMarkerCapable {
    private static final String INVALID_OPERATOR_REGEX = "(?:(?![mzlhvcsqtae])\\p{L})";
    private static final String SPACE_CHAR = " ";
    private static final Pattern SPLIT_PATTERN = Pattern.compile("(?=[mlhvcsqtaz])", 2);
    private static Pattern invalidRegexPattern = Pattern.compile(INVALID_OPERATOR_REGEX, 2);
    private Point currentPoint = new Point(0, 0);
    private ClosePath zOperator = null;

    public void doDraw(SvgDrawContext context) {
        PdfCanvas canvas = context.getCurrentCanvas();
        canvas.writeLiteral("% path\n");
        this.currentPoint = new Point(0, 0);
        for (IPathShape item : getShapes()) {
            item.draw(canvas);
        }
    }

    public ISvgNodeRenderer createDeepCopy() {
        PathSvgNodeRenderer copy = new PathSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        Point lastPoint = null;
        Rectangle commonRectangle = null;
        for (IPathShape item : getShapes()) {
            if (lastPoint == null) {
                lastPoint = item.getEndingPoint();
            }
            if (item instanceof AbstractPathShape) {
                commonRectangle = Rectangle.getCommonRectangle(commonRectangle, ((AbstractPathShape) item).getPathShapeRectangle(lastPoint));
            }
            lastPoint = item.getEndingPoint();
        }
        return commonRectangle;
    }

    private String[] getShapeCoordinates(IPathShape shape, IPathShape previousShape, String[] pathProperties) {
        if (shape instanceof ClosePath) {
            return null;
        }
        String[] shapeCoordinates = null;
        if ((shape instanceof SmoothSCurveTo) || (shape instanceof QuadraticSmoothCurveTo)) {
            String[] startingControlPoint = new String[2];
            if (previousShape != null) {
                Point previousEndPoint = previousShape.getEndingPoint();
                if (previousShape instanceof IControlPointCurve) {
                    Point lastControlPoint = ((IControlPointCurve) previousShape).getLastControlPoint();
                    startingControlPoint[0] = SvgCssUtils.convertFloatToString((float) ((previousEndPoint.getX() * 2.0d) - lastControlPoint.getX()));
                    startingControlPoint[1] = SvgCssUtils.convertFloatToString((float) ((previousEndPoint.getY() * 2.0d) - lastControlPoint.getY()));
                } else {
                    startingControlPoint[0] = SvgCssUtils.convertDoubleToString(previousEndPoint.getX());
                    startingControlPoint[1] = SvgCssUtils.convertDoubleToString(previousEndPoint.getY());
                }
                shapeCoordinates = concatenate(startingControlPoint, pathProperties);
            } else {
                throw new SvgProcessingException(SvgExceptionMessageConstant.INVALID_SMOOTH_CURVE_USE);
            }
        }
        if (shapeCoordinates == null) {
            return pathProperties;
        }
        return shapeCoordinates;
    }

    private List<IPathShape> processPathOperator(String[] pathProperties, IPathShape previousShape) {
        List<IPathShape> shapes = new ArrayList<>();
        if (pathProperties.length == 0 || pathProperties[0].isEmpty() || SvgPathShapeFactory.getArgumentCount(pathProperties[0]) < 0) {
            return shapes;
        }
        int argumentCount = SvgPathShapeFactory.getArgumentCount(pathProperties[0]);
        if (argumentCount != 0) {
            int index = 1;
            while (index < pathProperties.length && index + argumentCount <= pathProperties.length) {
                IPathShape pathShape = SvgPathShapeFactory.createPathShape(pathProperties[0]);
                if (pathShape instanceof MoveTo) {
                    shapes.addAll(addMoveToShapes(pathShape, pathProperties));
                    return shapes;
                }
                String[] shapeCoordinates = getShapeCoordinates(pathShape, previousShape, (String[]) Arrays.copyOfRange(pathProperties, index, index + argumentCount));
                if (pathShape != null) {
                    if (shapeCoordinates != null) {
                        pathShape.setCoordinates(shapeCoordinates, this.currentPoint);
                    }
                    this.currentPoint = pathShape.getEndingPoint();
                    shapes.add(pathShape);
                }
                previousShape = pathShape;
                index += argumentCount;
            }
            return shapes;
        } else if (previousShape != null) {
            shapes.add(this.zOperator);
            this.currentPoint = this.zOperator.getEndingPoint();
            return shapes;
        } else {
            throw new SvgProcessingException(SvgLogMessageConstant.INVALID_CLOSEPATH_OPERATOR_USE);
        }
    }

    private List<IPathShape> addMoveToShapes(IPathShape pathShape, String[] pathProperties) {
        IPathShape iPathShape;
        List<IPathShape> shapes = new ArrayList<>();
        String[] shapeCoordinates = getShapeCoordinates(pathShape, (IPathShape) null, (String[]) Arrays.copyOfRange(pathProperties, 1, 3));
        ClosePath closePath = new ClosePath(pathShape.isRelative());
        this.zOperator = closePath;
        closePath.setCoordinates(shapeCoordinates, this.currentPoint);
        pathShape.setCoordinates(shapeCoordinates, this.currentPoint);
        this.currentPoint = pathShape.getEndingPoint();
        shapes.add(pathShape);
        IPathShape previousShape = pathShape;
        if (pathProperties.length > 3) {
            int index = 3;
            while (index < pathProperties.length && index + 2 <= pathProperties.length) {
                if (pathShape.isRelative()) {
                    iPathShape = SvgPathShapeFactory.createPathShape(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO);
                } else {
                    iPathShape = SvgPathShapeFactory.createPathShape("L");
                }
                pathShape = iPathShape;
                pathShape.setCoordinates(getShapeCoordinates(pathShape, previousShape, (String[]) Arrays.copyOfRange(pathProperties, index, index + 2)), previousShape.getEndingPoint());
                shapes.add(pathShape);
                previousShape = pathShape;
                index += 2;
            }
        }
        return shapes;
    }

    /* access modifiers changed from: package-private */
    public Collection<IPathShape> getShapes() {
        Collection<String> parsedResults = parsePathOperations();
        List<IPathShape> shapes = new ArrayList<>();
        for (String parsedResult : parsedResults) {
            shapes.addAll(processPathOperator(parsedResult.split(" +"), shapes.size() == 0 ? null : shapes.get(shapes.size() - 1)));
        }
        return shapes;
    }

    private static String[] concatenate(String[] first, String[] second) {
        String[] arr = new String[(first.length + second.length)];
        System.arraycopy(first, 0, arr, 0, first.length);
        System.arraycopy(second, 0, arr, first.length, second.length);
        return arr;
    }

    /* access modifiers changed from: package-private */
    public boolean containsInvalidAttributes(String attributes) {
        return SvgRegexUtils.containsAtLeastOneMatch(invalidRegexPattern, attributes);
    }

    /* access modifiers changed from: package-private */
    public Collection<String> parsePathOperations() {
        Collection<String> result = new ArrayList<>();
        String attributes = (String) this.attributesAndStyles.get(SvgConstants.Attributes.f1634D);
        if (attributes == null) {
            throw new SvgProcessingException(SvgExceptionMessageConstant.PATH_OBJECT_MUST_HAVE_D_ATTRIBUTE);
        } else if (!containsInvalidAttributes(attributes)) {
            for (String inst : splitPathStringIntoOperators(attributes)) {
                String instTrim = inst.trim();
                if (!instTrim.isEmpty()) {
                    result.add(separateDecimalPoints(instTrim.charAt(0) + SPACE_CHAR + instTrim.substring(1).replace(",", SPACE_CHAR).trim()));
                }
            }
            return result;
        } else {
            throw new SvgProcessingException(SvgLogMessageConstant.INVALID_PATH_D_ATTRIBUTE_OPERATORS).setMessageParams(attributes);
        }
    }

    /* access modifiers changed from: package-private */
    public String separateDecimalPoints(String input) {
        StringBuilder res = new StringBuilder();
        boolean fractionalPartAfterDecimalPoint = false;
        boolean exponentSignMagnitude = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '-' || Character.isWhitespace(c)) {
                fractionalPartAfterDecimalPoint = false;
            }
            if (Character.isWhitespace(c)) {
                exponentSignMagnitude = false;
            }
            if (endsWithNonWhitespace(res) && ((c == '.' && fractionalPartAfterDecimalPoint) || (c == '-' && !exponentSignMagnitude))) {
                res.append(SPACE_CHAR);
            }
            if (c == '.') {
                fractionalPartAfterDecimalPoint = true;
            } else if (c == 'e') {
                exponentSignMagnitude = true;
            }
            res.append(c);
        }
        return res.toString();
    }

    static String[] splitPathStringIntoOperators(String path) {
        return SPLIT_PATTERN.split(path);
    }

    private static boolean endsWithNonWhitespace(StringBuilder sb) {
        return sb.length() > 0 && !Character.isWhitespace(sb.charAt(sb.length() - 1));
    }

    public void drawMarker(SvgDrawContext context, MarkerVertexType markerVertexType) {
        Object[] allShapesOrdered = getShapes().toArray();
        Point point = null;
        if (MarkerVertexType.MARKER_START.equals(markerVertexType)) {
            point = ((AbstractPathShape) allShapesOrdered[0]).getEndingPoint();
        } else if (MarkerVertexType.MARKER_END.equals(markerVertexType)) {
            point = ((AbstractPathShape) allShapesOrdered[allShapesOrdered.length - 1]).getEndingPoint();
        }
        if (point != null) {
            MarkerSvgNodeRenderer.drawMarker(context, SvgCssUtils.convertDoubleToString(point.f1280x), SvgCssUtils.convertDoubleToString(point.f1281y), markerVertexType, this);
        }
    }

    public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
        Object[] pathShapes = getShapes().toArray();
        if (pathShapes.length <= 1) {
            return 0.0d;
        }
        Vector v = new Vector(0.0f, 0.0f, 0.0f);
        if (SvgConstants.Attributes.MARKER_END.equals(marker.attributesAndStyles.get(SvgConstants.Tags.MARKER))) {
            IPathShape lastShape = (IPathShape) pathShapes[pathShapes.length - 1];
            IPathShape secondToLastShape = (IPathShape) pathShapes[pathShapes.length - 2];
            v = new Vector((float) (lastShape.getEndingPoint().getX() - secondToLastShape.getEndingPoint().getX()), (float) (lastShape.getEndingPoint().getY() - secondToLastShape.getEndingPoint().getY()), 0.0f);
        } else if (SvgConstants.Attributes.MARKER_START.equals(marker.attributesAndStyles.get(SvgConstants.Tags.MARKER))) {
            IPathShape firstShape = (IPathShape) pathShapes[0];
            IPathShape secondShape = (IPathShape) pathShapes[1];
            v = new Vector((float) (secondShape.getEndingPoint().getX() - firstShape.getEndingPoint().getX()), (float) (secondShape.getEndingPoint().getY() - firstShape.getEndingPoint().getY()), 0.0f);
        }
        double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(new Vector(1.0f, 0.0f, 0.0f), v);
        return (v.get(1) < 0.0f || reverse) ? -1.0d * rotAngle : rotAngle;
    }
}

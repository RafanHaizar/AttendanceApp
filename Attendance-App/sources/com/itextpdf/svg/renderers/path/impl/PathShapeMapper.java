package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.path.IPathShape;
import com.itextpdf.svg.renderers.path.IPathShapeMapper;
import java.util.HashMap;
import java.util.Map;

public class PathShapeMapper implements IPathShapeMapper {
    public Map<String, IPathShape> getMapping() {
        Map<String, IPathShape> result = new HashMap<>();
        result.put("L", new LineTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, new LineTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_LINE_TO_V, new VerticalLineTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_V, new VerticalLineTo(true));
        result.put("H", new HorizontalLineTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H, new HorizontalLineTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_CLOSE_PATH, new ClosePath());
        result.put(SvgConstants.Attributes.PATH_DATA_CLOSE_PATH.toLowerCase(), new ClosePath());
        result.put(SvgConstants.Attributes.PATH_DATA_MOVE_TO, new MoveTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO, new MoveTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_CURVE_TO, new CurveTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, new CurveTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_CURVE_TO_S, new SmoothSCurveTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, new SmoothSCurveTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO, new QuadraticCurveTo());
        result.put("q", new QuadraticCurveTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO, new QuadraticSmoothCurveTo());
        result.put(SvgConstants.Attributes.PATH_DATA_REL_SHORTHAND_CURVE_TO, new QuadraticSmoothCurveTo(true));
        result.put(SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, new EllipticalCurveTo());
        result.put("a", new EllipticalCurveTo(true));
        return result;
    }

    public Map<String, Integer> getArgumentCount() {
        Map<String, Integer> result = new HashMap<>();
        result.put("L", 2);
        result.put(SvgConstants.Attributes.PATH_DATA_LINE_TO_V, 1);
        result.put("H", 1);
        result.put(SvgConstants.Attributes.PATH_DATA_CLOSE_PATH, 0);
        result.put(SvgConstants.Attributes.PATH_DATA_MOVE_TO, 2);
        result.put(SvgConstants.Attributes.PATH_DATA_CURVE_TO, 6);
        result.put(SvgConstants.Attributes.PATH_DATA_CURVE_TO_S, 4);
        result.put(SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO, 4);
        result.put(SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO, 2);
        result.put(SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, 7);
        return result;
    }
}

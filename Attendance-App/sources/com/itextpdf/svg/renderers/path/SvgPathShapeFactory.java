package com.itextpdf.svg.renderers.path;

import com.itextpdf.svg.renderers.path.impl.PathShapeMapper;
import java.util.Map;

public class SvgPathShapeFactory {
    private SvgPathShapeFactory() {
    }

    public static IPathShape createPathShape(String name) {
        return new PathShapeMapper().getMapping().get(name);
    }

    public static int getArgumentCount(String name) {
        Map<String, Integer> map = new PathShapeMapper().getArgumentCount();
        if (map.containsKey(name.toUpperCase())) {
            return map.get(name.toUpperCase()).intValue();
        }
        return -1;
    }
}

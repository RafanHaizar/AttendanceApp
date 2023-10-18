package com.itextpdf.svg.renderers.path;

import java.util.Map;

public interface IPathShapeMapper {
    Map<String, Integer> getArgumentCount();

    Map<String, IPathShape> getMapping();
}

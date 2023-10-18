package com.itextpdf.svg.renderers.factories;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Collection;
import java.util.Map;

@Deprecated
public interface ISvgNodeRendererMapper {
    Collection<String> getIgnoredTags();

    Map<String, Class<? extends ISvgNodeRenderer>> getMapping();
}

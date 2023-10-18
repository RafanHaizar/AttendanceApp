package com.itextpdf.svg.renderers.factories;

import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.CircleSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.ClipPathSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.EllipseSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.GroupSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.ImageSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.LineSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.LinearGradientSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.PathSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.PolygonSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.PolylineSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.RectangleSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.StopSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.SvgTagSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.SymbolSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;
import com.itextpdf.svg.renderers.impl.TextSvgTSpanBranchRenderer;
import com.itextpdf.svg.renderers.impl.UseSvgNodeRenderer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Deprecated
public class DefaultSvgNodeRendererMapper implements ISvgNodeRendererMapper {
    public Map<String, Class<? extends ISvgNodeRenderer>> getMapping() {
        Map<String, Class<? extends ISvgNodeRenderer>> result = new HashMap<>();
        result.put("circle", CircleSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.CLIP_PATH, ClipPathSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.DEFS, DefsSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.ELLIPSE, EllipseSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.f1648G, GroupSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.IMAGE, ImageSvgNodeRenderer.class);
        result.put("line", LineSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.LINEAR_GRADIENT, LinearGradientSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.MARKER, MarkerSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.PATH, PathSvgNodeRenderer.class);
        result.put("polygon", PolygonSvgNodeRenderer.class);
        result.put("polyline", PolylineSvgNodeRenderer.class);
        result.put("rect", RectangleSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.STOP, StopSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.SVG, SvgTagSvgNodeRenderer.class);
        result.put(SvgConstants.Tags.SYMBOL, SymbolSvgNodeRenderer.class);
        result.put("text", TextSvgBranchRenderer.class);
        result.put(SvgConstants.Tags.TSPAN, TextSvgTSpanBranchRenderer.class);
        result.put(SvgConstants.Tags.USE, UseSvgNodeRenderer.class);
        return result;
    }

    public Collection<String> getIgnoredTags() {
        Collection<String> ignored = new HashSet<>();
        ignored.add("a");
        ignored.add(SvgConstants.Tags.ALT_GLYPH);
        ignored.add(SvgConstants.Tags.ALT_GLYPH_DEF);
        ignored.add(SvgConstants.Tags.ALT_GLYPH_ITEM);
        ignored.add(SvgConstants.Tags.COLOR_PROFILE);
        ignored.add(SvgConstants.Tags.DESC);
        ignored.add(SvgConstants.Tags.FE_BLEND);
        ignored.add(SvgConstants.Tags.FE_COLOR_MATRIX);
        ignored.add(SvgConstants.Tags.FE_COMPONENT_TRANSFER);
        ignored.add(SvgConstants.Tags.FE_COMPOSITE);
        ignored.add(SvgConstants.Tags.FE_COMVOLVE_MATRIX);
        ignored.add(SvgConstants.Tags.FE_DIFFUSE_LIGHTING);
        ignored.add(SvgConstants.Tags.FE_DISPLACEMENT_MAP);
        ignored.add(SvgConstants.Tags.FE_DISTANT_LIGHT);
        ignored.add(SvgConstants.Tags.FE_FLOOD);
        ignored.add(SvgConstants.Tags.FE_FUNC_A);
        ignored.add(SvgConstants.Tags.FE_FUNC_B);
        ignored.add(SvgConstants.Tags.FE_FUNC_G);
        ignored.add(SvgConstants.Tags.FE_FUNC_R);
        ignored.add(SvgConstants.Tags.FE_GAUSSIAN_BLUR);
        ignored.add(SvgConstants.Tags.FE_IMAGE);
        ignored.add(SvgConstants.Tags.FE_MERGE);
        ignored.add(SvgConstants.Tags.FE_MERGE_NODE);
        ignored.add(SvgConstants.Tags.FE_MORPHOLOGY);
        ignored.add(SvgConstants.Tags.FE_OFFSET);
        ignored.add(SvgConstants.Tags.FE_POINT_LIGHT);
        ignored.add(SvgConstants.Tags.FE_SPECULAR_LIGHTING);
        ignored.add(SvgConstants.Tags.FE_SPOTLIGHT);
        ignored.add(SvgConstants.Tags.FE_TILE);
        ignored.add(SvgConstants.Tags.FE_TURBULENCE);
        ignored.add(SvgConstants.Tags.FILTER);
        ignored.add("font");
        ignored.add("font-face");
        ignored.add(SvgConstants.Tags.FONT_FACE_FORMAT);
        ignored.add(SvgConstants.Tags.FONT_FACE_NAME);
        ignored.add(SvgConstants.Tags.FONT_FACE_SRC);
        ignored.add(SvgConstants.Tags.FONT_FACE_URI);
        ignored.add(SvgConstants.Tags.FOREIGN_OBJECT);
        ignored.add(SvgConstants.Tags.GLYPH);
        ignored.add(SvgConstants.Tags.GLYPH_REF);
        ignored.add(SvgConstants.Tags.HKERN);
        ignored.add(SvgConstants.Tags.MASK);
        ignored.add(SvgConstants.Tags.METADATA);
        ignored.add(SvgConstants.Tags.MISSING_GLYPH);
        ignored.add(SvgConstants.Tags.PATTERN);
        ignored.add(SvgConstants.Tags.RADIAL_GRADIENT);
        ignored.add("style");
        ignored.add("title");
        return ignored;
    }
}

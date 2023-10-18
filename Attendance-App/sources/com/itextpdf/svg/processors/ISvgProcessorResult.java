package com.itextpdf.svg.processors;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Map;

public interface ISvgProcessorResult {
    FontProvider getFontProvider();

    Map<String, ISvgNodeRenderer> getNamedObjects();

    ISvgNodeRenderer getRootRenderer();

    FontSet getTempFonts();
}

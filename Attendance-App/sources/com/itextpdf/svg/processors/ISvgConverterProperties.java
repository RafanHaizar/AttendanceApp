package com.itextpdf.svg.processors;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;

public interface ISvgConverterProperties {
    String getBaseUri();

    String getCharset();

    FontProvider getFontProvider();

    MediaDeviceDescription getMediaDeviceDescription();

    ISvgNodeRendererFactory getRendererFactory();
}

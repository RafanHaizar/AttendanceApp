package com.itextpdf.svg.processors.impl;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.resolver.resource.DefaultResourceRetriever;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
import com.itextpdf.svg.processors.ISvgConverterProperties;
import com.itextpdf.svg.renderers.factories.DefaultSvgNodeRendererFactory;
import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;
import java.nio.charset.StandardCharsets;

public class SvgConverterProperties implements ISvgConverterProperties {
    private String baseUri = "";
    private String charset = StandardCharsets.UTF_8.name();
    private FontProvider fontProvider;
    private MediaDeviceDescription mediaDeviceDescription;
    private ISvgNodeRendererFactory rendererFactory = new DefaultSvgNodeRendererFactory();
    private IResourceRetriever resourceRetriever = new DefaultResourceRetriever();

    public SvgConverterProperties setRendererFactory(ISvgNodeRendererFactory rendererFactory2) {
        this.rendererFactory = rendererFactory2;
        return this;
    }

    public SvgConverterProperties setFontProvider(FontProvider fontProvider2) {
        this.fontProvider = fontProvider2;
        return this;
    }

    public ISvgNodeRendererFactory getRendererFactory() {
        return this.rendererFactory;
    }

    public String getCharset() {
        return this.charset;
    }

    public SvgConverterProperties setCharset(String charset2) {
        this.charset = charset2;
        return this;
    }

    public String getBaseUri() {
        return this.baseUri;
    }

    public FontProvider getFontProvider() {
        return this.fontProvider;
    }

    public MediaDeviceDescription getMediaDeviceDescription() {
        return this.mediaDeviceDescription;
    }

    public SvgConverterProperties setMediaDeviceDescription(MediaDeviceDescription mediaDeviceDescription2) {
        this.mediaDeviceDescription = mediaDeviceDescription2;
        return this;
    }

    public SvgConverterProperties setBaseUri(String baseUri2) {
        this.baseUri = baseUri2;
        return this;
    }

    public IResourceRetriever getResourceRetriever() {
        return this.resourceRetriever;
    }

    public SvgConverterProperties setResourceRetriever(IResourceRetriever resourceRetriever2) {
        this.resourceRetriever = resourceRetriever2;
        return this;
    }
}

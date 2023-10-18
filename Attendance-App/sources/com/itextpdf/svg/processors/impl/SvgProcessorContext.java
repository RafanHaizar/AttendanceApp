package com.itextpdf.svg.processors.impl;

import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.font.Range;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.processors.ISvgConverterProperties;

public class SvgProcessorContext {
    private MediaDeviceDescription deviceDescription;
    private FontProvider fontProvider;
    private ResourceResolver resourceResolver;
    private FontSet tempFonts;

    public SvgProcessorContext(ISvgConverterProperties converterProperties) {
        MediaDeviceDescription mediaDeviceDescription = converterProperties.getMediaDeviceDescription();
        this.deviceDescription = mediaDeviceDescription;
        if (mediaDeviceDescription == null) {
            this.deviceDescription = MediaDeviceDescription.getDefault();
        }
        FontProvider fontProvider2 = converterProperties.getFontProvider();
        this.fontProvider = fontProvider2;
        if (fontProvider2 == null) {
            this.fontProvider = new BasicFontProvider();
        }
        this.resourceResolver = new ResourceResolver(converterProperties.getBaseUri(), converterProperties instanceof SvgConverterProperties ? ((SvgConverterProperties) converterProperties).getResourceRetriever() : null);
    }

    public FontProvider getFontProvider() {
        return this.fontProvider;
    }

    public ResourceResolver getResourceResolver() {
        return this.resourceResolver;
    }

    public MediaDeviceDescription getDeviceDescription() {
        return this.deviceDescription;
    }

    public FontSet getTempFonts() {
        return this.tempFonts;
    }

    public void addTemporaryFont(FontProgram fontProgram, String encoding, String alias, Range unicodeRange) {
        if (this.tempFonts == null) {
            this.tempFonts = new FontSet();
        }
        this.tempFonts.addFont(fontProgram, encoding, alias, unicodeRange);
    }

    public void addTemporaryFont(FontProgram fontProgram, String encoding, String alias) {
        if (this.tempFonts == null) {
            this.tempFonts = new FontSet();
        }
        this.tempFonts.addFont(fontProgram, encoding, alias);
    }

    public void addTemporaryFont(FontInfo fontInfo, String alias) {
        if (this.tempFonts == null) {
            this.tempFonts = new FontSet();
        }
        this.tempFonts.addFont(fontInfo, alias);
    }
}

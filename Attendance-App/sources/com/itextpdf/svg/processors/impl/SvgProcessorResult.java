package com.itextpdf.svg.processors.impl;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Map;

public class SvgProcessorResult implements ISvgProcessorResult {
    private SvgProcessorContext context;
    @Deprecated
    private FontProvider fontProvider;
    private Map<String, ISvgNodeRenderer> namedObjects;
    private ISvgNodeRenderer root;
    @Deprecated
    private FontSet tempFonts;

    @Deprecated
    public SvgProcessorResult(Map<String, ISvgNodeRenderer> namedObjects2, ISvgNodeRenderer root2, FontProvider fontProvider2, FontSet tempFonts2) {
        this.namedObjects = namedObjects2;
        this.root = root2;
        this.fontProvider = fontProvider2;
        this.tempFonts = tempFonts2;
    }

    public SvgProcessorResult(Map<String, ISvgNodeRenderer> namedObjects2, ISvgNodeRenderer root2, SvgProcessorContext context2) {
        this.namedObjects = namedObjects2;
        this.root = root2;
        this.fontProvider = context2.getFontProvider();
        this.tempFonts = context2.getTempFonts();
        this.context = context2;
    }

    public Map<String, ISvgNodeRenderer> getNamedObjects() {
        return this.namedObjects;
    }

    public ISvgNodeRenderer getRootRenderer() {
        return this.root;
    }

    public FontProvider getFontProvider() {
        return this.fontProvider;
    }

    public FontSet getTempFonts() {
        return this.tempFonts;
    }

    public SvgProcessorContext getContext() {
        return this.context;
    }

    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(getClass())) {
            return false;
        }
        SvgProcessorResult otherResult = (SvgProcessorResult) o;
        if (!otherResult.getNamedObjects().equals(getNamedObjects()) || !otherResult.getRootRenderer().equals(getRootRenderer())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getNamedObjects().hashCode() + (getRootRenderer().hashCode() * 43);
    }
}

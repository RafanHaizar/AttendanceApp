package com.itextpdf.svg.renderers;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class SvgDrawContext {
    private final Deque<PdfCanvas> canvases;
    private FontProvider fontProvider;
    private AffineTransform lastTextTransform;
    private final Map<String, ISvgNodeRenderer> namedObjects;
    private float remValue;
    private ResourceResolver resourceResolver;
    private FontSet tempFonts;
    private float[] textMove;
    private final Stack<String> useIds;
    private final Deque<Rectangle> viewports;

    public SvgDrawContext(ResourceResolver resourceResolver2, FontProvider fontProvider2) {
        this(resourceResolver2, fontProvider2, (ISvgNodeRenderer) null);
    }

    public SvgDrawContext(ResourceResolver resourceResolver2, FontProvider fontProvider2, ISvgNodeRenderer svgRootRenderer) {
        this.namedObjects = new HashMap();
        this.canvases = new LinkedList();
        this.viewports = new LinkedList();
        this.useIds = new Stack<>();
        this.lastTextTransform = new AffineTransform();
        this.textMove = new float[]{0.0f, 0.0f};
        this.resourceResolver = resourceResolver2 == null ? new ResourceResolver((String) null) : resourceResolver2;
        this.fontProvider = fontProvider2 == null ? new BasicFontProvider() : fontProvider2;
        if (svgRootRenderer instanceof AbstractSvgNodeRenderer) {
            this.remValue = ((AbstractSvgNodeRenderer) svgRootRenderer).getCurrentFontSize();
        } else {
            this.remValue = CssUtils.parseAbsoluteFontSize(CssDefaults.getDefaultValue("font-size"));
        }
    }

    public PdfCanvas getCurrentCanvas() {
        return this.canvases.getFirst();
    }

    public PdfCanvas popCanvas() {
        PdfCanvas canvas = this.canvases.getFirst();
        this.canvases.removeFirst();
        return canvas;
    }

    public void pushCanvas(PdfCanvas canvas) {
        this.canvases.addFirst(canvas);
    }

    public int size() {
        return this.canvases.size();
    }

    public void addViewPort(Rectangle viewPort) {
        this.viewports.addFirst(viewPort);
    }

    public Rectangle getCurrentViewPort() {
        return this.viewports.getFirst();
    }

    public Rectangle getRootViewPort() {
        return this.viewports.getLast();
    }

    public void removeCurrentViewPort() {
        if (this.viewports.size() > 0) {
            this.viewports.removeFirst();
        }
    }

    public void addNamedObject(String name, ISvgNodeRenderer namedObject) {
        if (namedObject == null) {
            throw new SvgProcessingException(SvgLogMessageConstant.NAMED_OBJECT_NULL);
        } else if (name == null || name.isEmpty()) {
            throw new SvgProcessingException(SvgLogMessageConstant.NAMED_OBJECT_NAME_NULL_OR_EMPTY);
        } else if (!this.namedObjects.containsKey(name)) {
            this.namedObjects.put(name, namedObject);
        }
    }

    public ISvgNodeRenderer getNamedObject(String name) {
        return this.namedObjects.get(name);
    }

    public ResourceResolver getResourceResolver() {
        return this.resourceResolver;
    }

    public void addNamedObjects(Map<String, ISvgNodeRenderer> namedObjects2) {
        this.namedObjects.putAll(namedObjects2);
    }

    public FontProvider getFontProvider() {
        return this.fontProvider;
    }

    public FontSet getTempFonts() {
        return this.tempFonts;
    }

    public void setTempFonts(FontSet tempFonts2) {
        this.tempFonts = tempFonts2;
    }

    public boolean isIdUsedByUseTagBefore(String elementId) {
        return this.useIds.contains(elementId);
    }

    public void addUsedId(String elementId) {
        this.useIds.push(elementId);
    }

    public void removeUsedId(String elementId) {
        this.useIds.pop();
    }

    public AffineTransform getLastTextTransform() {
        if (this.lastTextTransform == null) {
            this.lastTextTransform = new AffineTransform();
        }
        return this.lastTextTransform;
    }

    public void setLastTextTransform(AffineTransform newTransform) {
        this.lastTextTransform = newTransform;
    }

    public float[] getTextMove() {
        return this.textMove;
    }

    public void resetTextMove() {
        this.textMove = new float[]{0.0f, 0.0f};
    }

    public void addTextMove(float additionalMoveX, float additionalMoveY) {
        float[] fArr = this.textMove;
        fArr[0] = fArr[0] + additionalMoveX;
        fArr[1] = fArr[1] + additionalMoveY;
    }

    public AffineTransform getCurrentCanvasTransform() {
        Matrix currentTransform = getCurrentCanvas().getGraphicsState().getCtm();
        if (currentTransform != null) {
            return new AffineTransform((double) currentTransform.get(0), (double) currentTransform.get(1), (double) currentTransform.get(3), (double) currentTransform.get(4), (double) currentTransform.get(6), (double) currentTransform.get(7));
        }
        return new AffineTransform();
    }

    public float getRemValue() {
        return this.remValue;
    }
}

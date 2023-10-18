package com.itextpdf.layout;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.FontKerning;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.RootRenderer;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;
import com.itextpdf.layout.splitting.ISplitCharacters;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RootElement<T extends IPropertyContainer> extends ElementPropertyContainer<T> implements Closeable {
    protected List<IElement> childElements = new ArrayList();
    protected PdfFont defaultFont;
    protected FontProvider defaultFontProvider;
    private LayoutTaggingHelper defaultLayoutTaggingHelper;
    protected ISplitCharacters defaultSplitCharacters;
    protected boolean immediateFlush = true;
    protected PdfDocument pdfDocument;
    protected RootRenderer rootRenderer;

    /* access modifiers changed from: protected */
    public abstract RootRenderer ensureRootRendererNotNull();

    public T add(IBlockElement element) {
        this.childElements.add(element);
        createAndAddRendererSubTree(element);
        if (this.immediateFlush) {
            List<IElement> list = this.childElements;
            list.remove(list.size() - 1);
        }
        return this;
    }

    public T add(Image image) {
        this.childElements.add(image);
        createAndAddRendererSubTree(image);
        if (this.immediateFlush) {
            List<IElement> list = this.childElements;
            list.remove(list.size() - 1);
        }
        return this;
    }

    public FontProvider getFontProvider() {
        Object fontProvider = getProperty(91);
        if (fontProvider instanceof FontProvider) {
            return (FontProvider) fontProvider;
        }
        return null;
    }

    public void setFontProvider(FontProvider fontProvider) {
        setProperty(91, fontProvider);
    }

    public boolean hasProperty(int property) {
        return hasOwnProperty(property);
    }

    public boolean hasOwnProperty(int property) {
        return this.properties.containsKey(Integer.valueOf(property));
    }

    public <T1> T1 getProperty(int property) {
        return getOwnProperty(property);
    }

    public <T1> T1 getOwnProperty(int property) {
        return this.properties.get(Integer.valueOf(property));
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 20:
                if (this.defaultFont == null) {
                    this.defaultFont = PdfFontFactory.createFont();
                }
                return this.defaultFont;
            case 24:
                return UnitValue.createPointValue(12.0f);
            case 61:
                return Float.valueOf(0.75f);
            case 62:
                if (this.defaultSplitCharacters == null) {
                    this.defaultSplitCharacters = new DefaultSplitCharacters();
                }
                return this.defaultSplitCharacters;
            case 71:
                return 0;
            case 72:
                return Float.valueOf(0.0f);
            case 91:
                if (this.defaultFontProvider == null) {
                    this.defaultFontProvider = new FontProvider();
                }
                return this.defaultFontProvider;
            case 108:
                try {
                    return initTaggingHelperIfNeeded();
                } catch (IOException exc) {
                    throw new RuntimeException(exc.toString(), exc);
                }
            default:
                return null;
        }
    }

    public void deleteOwnProperty(int property) {
        this.properties.remove(Integer.valueOf(property));
    }

    public void setProperty(int property, Object value) {
        this.properties.put(Integer.valueOf(property), value);
    }

    public RootRenderer getRenderer() {
        return ensureRootRendererNotNull();
    }

    public T showTextAligned(String text, float x, float y, TextAlignment textAlign) {
        return showTextAligned(text, x, y, textAlign, 0.0f);
    }

    public T showTextAligned(String text, float x, float y, TextAlignment textAlign, float angle) {
        return showTextAligned(text, x, y, textAlign, VerticalAlignment.BOTTOM, angle);
    }

    public T showTextAligned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, float angle) {
        return showTextAligned((Paragraph) new Paragraph(text).setMultipliedLeading(1.0f).setMargin(0.0f), x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, angle);
    }

    public T showTextAlignedKerned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, float radAngle) {
        return showTextAligned((Paragraph) ((Paragraph) new Paragraph(text).setMultipliedLeading(1.0f).setMargin(0.0f)).setFontKerning(FontKerning.YES), x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, radAngle);
    }

    public T showTextAligned(Paragraph p, float x, float y, TextAlignment textAlign) {
        return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, VerticalAlignment.BOTTOM, 0.0f);
    }

    public T showTextAligned(Paragraph p, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign) {
        return showTextAligned(p, x, y, this.pdfDocument.getNumberOfPages(), textAlign, vertAlign, 0.0f);
    }

    public T showTextAligned(Paragraph p, float x, float y, int pageNumber, TextAlignment textAlign, VerticalAlignment vertAlign, float radAngle) {
        Div div = new Div();
        ((Div) div.setTextAlignment(textAlign)).setVerticalAlignment(vertAlign);
        if (radAngle != 0.0f) {
            div.setRotationAngle(radAngle);
        }
        div.setProperty(58, Float.valueOf(x));
        div.setProperty(59, Float.valueOf(y));
        float divX = x;
        float divY = y;
        if (textAlign == TextAlignment.CENTER) {
            divX = x - (5000.0f / 2.0f);
            p.setHorizontalAlignment(HorizontalAlignment.CENTER);
        } else if (textAlign == TextAlignment.RIGHT) {
            divX = x - 5000.0f;
            p.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        }
        if (vertAlign == VerticalAlignment.MIDDLE) {
            divY = y - (5000.0f / 2.0f);
        } else if (vertAlign == VerticalAlignment.TOP) {
            divY = y - 5000.0f;
        }
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        ((Div) div.setFixedPosition(pageNumber, divX, divY, 5000.0f)).setMinHeight(5000.0f);
        if (p.getProperty(33) == null) {
            p.setMultipliedLeading(1.0f);
        }
        div.add((IBlockElement) p.setMargins(0.0f, 0.0f, 0.0f, 0.0f));
        div.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
        add((IBlockElement) div);
        return this;
    }

    /* access modifiers changed from: protected */
    public void createAndAddRendererSubTree(IElement element) {
        IRenderer rendererSubTreeRoot = element.createRendererSubTree();
        LayoutTaggingHelper taggingHelper = initTaggingHelperIfNeeded();
        if (taggingHelper != null) {
            taggingHelper.addKidsHint(this.pdfDocument.getTagStructureContext().getAutoTaggingPointer(), (Iterable<? extends IPropertyContainer>) Collections.singletonList(rendererSubTreeRoot));
        }
        ensureRootRendererNotNull().addChild(rendererSubTreeRoot);
    }

    private LayoutTaggingHelper initTaggingHelperIfNeeded() {
        if (this.defaultLayoutTaggingHelper != null || !this.pdfDocument.isTagged()) {
            return this.defaultLayoutTaggingHelper;
        }
        LayoutTaggingHelper layoutTaggingHelper = new LayoutTaggingHelper(this.pdfDocument, this.immediateFlush);
        this.defaultLayoutTaggingHelper = layoutTaggingHelper;
        return layoutTaggingHelper;
    }
}

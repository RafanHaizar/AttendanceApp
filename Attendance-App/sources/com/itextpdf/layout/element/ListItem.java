package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ListItemRenderer;

public class ListItem extends Div {
    public ListItem() {
    }

    public ListItem(String text) {
        this();
        add((IBlockElement) ((Paragraph) new Paragraph(text).setMarginTop(0.0f)).setMarginBottom(0.0f));
    }

    public ListItem setListSymbolOrdinalValue(int ordinalValue) {
        setProperty(120, Integer.valueOf(ordinalValue));
        return this;
    }

    public ListItem(Image image) {
        this();
        add(image);
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 83:
                return ListSymbolPosition.DEFAULT;
            default:
                return super.getDefaultProperty(property);
        }
    }

    public ListItem setListSymbol(String symbol) {
        return setListSymbol(new Text(symbol));
    }

    public ListItem setListSymbol(Text text) {
        setProperty(37, text);
        return this;
    }

    public ListItem setListSymbol(Image image) {
        setProperty(37, image);
        return this;
    }

    public ListItem setListSymbol(ListNumberingType listNumberingType) {
        if (listNumberingType == ListNumberingType.ZAPF_DINGBATS_1 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_2 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_3 || listNumberingType == ListNumberingType.ZAPF_DINGBATS_4) {
            setProperty(42, " ");
        }
        setProperty(37, listNumberingType);
        return this;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.LBODY);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new ListItemRenderer(this);
    }
}

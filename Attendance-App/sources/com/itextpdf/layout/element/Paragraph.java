package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.ParagraphOrphansControl;
import com.itextpdf.layout.property.ParagraphWidowsControl;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Paragraph extends BlockElement<Paragraph> {
    protected DefaultAccessibilityProperties tagProperties;

    public Paragraph() {
    }

    public Paragraph(String text) {
        this(new Text(text));
    }

    public Paragraph(Text text) {
        add((ILeafElement) text);
    }

    public Paragraph add(String text) {
        return add((ILeafElement) new Text(text));
    }

    public Paragraph add(ILeafElement element) {
        this.childElements.add(element);
        return this;
    }

    public Paragraph add(IBlockElement element) {
        this.childElements.add(element);
        return this;
    }

    public <T2 extends ILeafElement> Paragraph addAll(List<T2> elements) {
        for (T2 element : elements) {
            add((ILeafElement) element);
        }
        return this;
    }

    public Paragraph addTabStops(TabStop... tabStops) {
        addTabStopsAsProperty(Arrays.asList(tabStops));
        return this;
    }

    public Paragraph addTabStops(List<TabStop> tabStops) {
        addTabStopsAsProperty(tabStops);
        return this;
    }

    public Paragraph removeTabStop(float tabStopPosition) {
        Map<Float, TabStop> tabStops = (Map) getProperty(69);
        if (tabStops != null) {
            tabStops.remove(Float.valueOf(tabStopPosition));
        }
        return this;
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 18:
                return Float.valueOf(0.0f);
            case 33:
                return new Leading(2, (this.childElements.size() != 1 || !(this.childElements.get(0) instanceof Image)) ? 1.35f : 1.0f);
            case 43:
            case 46:
                return UnitValue.createPointValue(4.0f);
            case 67:
                return Float.valueOf(50.0f);
            default:
                return super.getDefaultProperty(property);
        }
    }

    public Paragraph setFirstLineIndent(float indent) {
        setProperty(18, Float.valueOf(indent));
        return this;
    }

    public Paragraph setOrphansControl(ParagraphOrphansControl orphansControl) {
        setProperty(121, orphansControl);
        return this;
    }

    public Paragraph setWidowsControl(ParagraphWidowsControl widowsControl) {
        setProperty(122, widowsControl);
        return this;
    }

    public Paragraph setFixedLeading(float leading) {
        setProperty(33, new Leading(1, leading));
        return this;
    }

    public Paragraph setMultipliedLeading(float leading) {
        setProperty(33, new Leading(2, leading));
        return this;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.f1511P);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new ParagraphRenderer(this);
    }

    private void addTabStopsAsProperty(List<TabStop> newTabStops) {
        Map<Float, TabStop> tabStops = (Map) getProperty(69);
        if (tabStops == null) {
            tabStops = new TreeMap<>();
            setProperty(69, tabStops);
        }
        for (TabStop tabStop : newTabStops) {
            tabStops.put(Float.valueOf(tabStop.getTabPosition()), tabStop);
        }
    }
}

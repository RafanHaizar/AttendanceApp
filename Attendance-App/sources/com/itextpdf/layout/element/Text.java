package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;
import com.itextpdf.layout.tagging.IAccessibleElement;

public class Text extends AbstractElement<Text> implements ILeafElement, IAccessibleElement {
    protected DefaultAccessibilityProperties tagProperties;
    protected String text;

    public Text(String text2) {
        if (text2 != null) {
            this.text = text2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public float getTextRise() {
        return ((Float) getProperty(72)).floatValue();
    }

    public Text setTextRise(float textRise) {
        setProperty(72, Float.valueOf(textRise));
        return this;
    }

    public Float getHorizontalScaling() {
        return (Float) getProperty(29);
    }

    public Text setSkew(float alpha, float beta) {
        double d = (double) alpha;
        Double.isNaN(d);
        float alpha2 = (float) Math.tan((d * 3.141592653589793d) / 180.0d);
        double d2 = (double) beta;
        Double.isNaN(d2);
        setProperty(65, new float[]{alpha2, (float) Math.tan((d2 * 3.141592653589793d) / 180.0d)});
        return this;
    }

    public Text setHorizontalScaling(float horizontalScaling) {
        setProperty(29, Float.valueOf(horizontalScaling));
        return this;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.SPAN);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new TextRenderer(this, this.text);
    }
}

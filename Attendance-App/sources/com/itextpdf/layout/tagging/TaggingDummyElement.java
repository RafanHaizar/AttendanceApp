package com.itextpdf.layout.tagging;

import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.IPropertyContainer;

public class TaggingDummyElement implements IAccessibleElement, IPropertyContainer {

    /* renamed from: id */
    private Object f1545id;
    private DefaultAccessibilityProperties properties;

    public TaggingDummyElement(String role) {
        this.properties = new DefaultAccessibilityProperties(role);
    }

    public AccessibilityProperties getAccessibilityProperties() {
        return this.properties;
    }

    public <T1> T1 getProperty(int property) {
        if (property == 109) {
            return this.f1545id;
        }
        return null;
    }

    public void setProperty(int property, Object value) {
        if (property == 109) {
            this.f1545id = value;
        }
    }

    public boolean hasProperty(int property) {
        throw new UnsupportedOperationException();
    }

    public boolean hasOwnProperty(int property) {
        throw new UnsupportedOperationException();
    }

    public <T1> T1 getOwnProperty(int property) {
        throw new UnsupportedOperationException();
    }

    public <T1> T1 getDefaultProperty(int property) {
        throw new UnsupportedOperationException();
    }

    public void deleteOwnProperty(int property) {
        throw new UnsupportedOperationException();
    }
}

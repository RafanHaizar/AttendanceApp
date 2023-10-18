package com.itextpdf.layout.element;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LinkRenderer;

public class Link extends Text {
    public Link(String text, PdfLinkAnnotation linkAnnotation) {
        super(text);
        setProperty(88, linkAnnotation);
    }

    public Link(String text, PdfAction action) {
        this(text, (PdfLinkAnnotation) new PdfLinkAnnotation(new Rectangle(0.0f, 0.0f, 0.0f, 0.0f)).setAction(action).setFlags(4));
    }

    public Link(String text, PdfDestination destination) {
        this(text, (PdfLinkAnnotation) new PdfLinkAnnotation(new Rectangle(0.0f, 0.0f, 0.0f, 0.0f)).setDestination(destination).setFlags(4));
    }

    public PdfLinkAnnotation getLinkAnnotation() {
        return (PdfLinkAnnotation) getProperty(88);
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.LINK);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new LinkRenderer(this, this.text);
    }
}

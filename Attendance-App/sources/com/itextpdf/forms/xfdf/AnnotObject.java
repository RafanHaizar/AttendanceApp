package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import java.util.ArrayList;
import java.util.List;

public class AnnotObject {
    private ActionObject action;
    private String appearance;
    private List<AttributeObject> attributes = new ArrayList();
    private BorderStyleAltObject borderStyleAlt;
    private PdfString contents;
    private PdfString contentsRichText;
    private String defaultAppearance;
    private String defaultStyle;
    private DestObject destination;
    private boolean hasPopup;
    private String name;
    private AnnotObject popup;
    private PdfIndirectReference ref;
    private String vertices;

    public String getName() {
        return this.name;
    }

    public AnnotObject setName(String name2) {
        this.name = name2;
        return this;
    }

    public List<AttributeObject> getAttributes() {
        return this.attributes;
    }

    public AttributeObject getAttribute(String name2) {
        for (AttributeObject attr : this.attributes) {
            if (attr.getName().equals(name2)) {
                return attr;
            }
        }
        return null;
    }

    public String getAttributeValue(String name2) {
        for (AttributeObject attr : this.attributes) {
            if (attr.getName().equals(name2)) {
                return attr.getValue();
            }
        }
        return null;
    }

    public AnnotObject getPopup() {
        return this.popup;
    }

    public AnnotObject setPopup(AnnotObject popup2) {
        this.popup = popup2;
        return this;
    }

    public boolean isHasPopup() {
        return this.hasPopup;
    }

    public AnnotObject setHasPopup(boolean hasPopup2) {
        this.hasPopup = hasPopup2;
        return this;
    }

    public PdfString getContents() {
        return this.contents;
    }

    public AnnotObject setContents(PdfString contents2) {
        this.contents = contents2;
        return this;
    }

    public PdfString getContentsRichText() {
        return this.contentsRichText;
    }

    public AnnotObject setContentsRichText(PdfString contentsRichRext) {
        this.contentsRichText = contentsRichRext;
        return this;
    }

    public ActionObject getAction() {
        return this.action;
    }

    public AnnotObject setAction(ActionObject action2) {
        this.action = action2;
        return this;
    }

    public void addAttribute(AttributeObject attr) {
        this.attributes.add(attr);
    }

    /* access modifiers changed from: package-private */
    public void addAttribute(String name2, boolean value) {
        this.attributes.add(new AttributeObject(name2, value ? "yes" : "no"));
    }

    /* access modifiers changed from: package-private */
    public void addAttribute(String name2, float value) {
        this.attributes.add(new AttributeObject(name2, String.valueOf(value)));
    }

    /* access modifiers changed from: package-private */
    public void addAttribute(String name2, Rectangle value) {
        this.attributes.add(new AttributeObject(name2, XfdfObjectUtils.convertRectToString(value)));
    }

    /* access modifiers changed from: package-private */
    public void addAttribute(String name2, PdfObject valueObject, boolean required) {
        if (valueObject != null) {
            String valueString = null;
            if (valueObject.getType() == 2) {
                PdfBoolean pdfBoolean = (PdfBoolean) valueObject;
                PdfBoolean pdfBoolean2 = pdfBoolean;
                valueString = pdfBoolean.getValue() ? "yes" : "no";
            } else if (valueObject.getType() == 6) {
                PdfName pdfName = (PdfName) valueObject;
                PdfName pdfName2 = pdfName;
                valueString = pdfName.getValue();
            } else if (valueObject.getType() == 8) {
                PdfNumber pdfNumber = (PdfNumber) valueObject;
                PdfNumber pdfNumber2 = pdfNumber;
                valueString = String.valueOf(pdfNumber.getValue());
            } else if (valueObject.getType() == 10) {
                PdfString pdfString = (PdfString) valueObject;
                PdfString pdfString2 = pdfString;
                valueString = pdfString.getValue();
            }
            this.attributes.add(new AttributeObject(name2, valueString));
        } else if (required) {
            throw new AttributeNotFoundException(name2);
        }
    }

    /* access modifiers changed from: package-private */
    public void addAttribute(String name2, PdfObject valueObject) {
        addAttribute(name2, valueObject, false);
    }

    /* access modifiers changed from: package-private */
    public void addFdfAttributes(int pageNumber) {
        addAttribute(new AttributeObject("page", String.valueOf(pageNumber)));
    }

    public DestObject getDestination() {
        return this.destination;
    }

    public AnnotObject setDestination(DestObject destination2) {
        this.destination = destination2;
        return this;
    }

    public String getAppearance() {
        return this.appearance;
    }

    public AnnotObject setAppearance(String appearance2) {
        this.appearance = appearance2;
        return this;
    }

    public String getDefaultAppearance() {
        return this.defaultAppearance;
    }

    public AnnotObject setDefaultAppearance(String defaultAppearance2) {
        this.defaultAppearance = defaultAppearance2;
        return this;
    }

    public String getDefaultStyle() {
        return this.defaultStyle;
    }

    public AnnotObject setDefaultStyle(String defaultStyle2) {
        this.defaultStyle = defaultStyle2;
        return this;
    }

    public BorderStyleAltObject getBorderStyleAlt() {
        return this.borderStyleAlt;
    }

    public AnnotObject setBorderStyleAlt(BorderStyleAltObject borderStyleAlt2) {
        this.borderStyleAlt = borderStyleAlt2;
        return this;
    }

    public String getVertices() {
        return this.vertices;
    }

    public AnnotObject setVertices(String vertices2) {
        this.vertices = vertices2;
        return this;
    }

    public PdfIndirectReference getRef() {
        return this.ref;
    }

    public AnnotObject setRef(PdfIndirectReference ref2) {
        this.ref = ref2;
        return this;
    }
}

package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.PdfEncodings;

public class PdfStructureAttributes extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = 3972284224659975750L;

    public PdfStructureAttributes(PdfDictionary attributesDict) {
        super(attributesDict);
    }

    public PdfStructureAttributes(String owner) {
        super(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1361O, PdfStructTreeRoot.convertRoleToPdfName(owner));
    }

    public PdfStructureAttributes(PdfNamespace namespace) {
        super(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1361O, PdfName.NSO);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1360NS, namespace.getPdfObject());
    }

    public PdfStructureAttributes addEnumAttribute(String attributeName, String attributeValue) {
        ((PdfDictionary) getPdfObject()).put(PdfStructTreeRoot.convertRoleToPdfName(attributeName), new PdfName(attributeValue));
        setModified();
        return this;
    }

    public PdfStructureAttributes addTextAttribute(String attributeName, String attributeValue) {
        ((PdfDictionary) getPdfObject()).put(PdfStructTreeRoot.convertRoleToPdfName(attributeName), new PdfString(attributeValue, PdfEncodings.UNICODE_BIG));
        setModified();
        return this;
    }

    public PdfStructureAttributes addIntAttribute(String attributeName, int attributeValue) {
        ((PdfDictionary) getPdfObject()).put(PdfStructTreeRoot.convertRoleToPdfName(attributeName), new PdfNumber(attributeValue));
        setModified();
        return this;
    }

    public PdfStructureAttributes addFloatAttribute(String attributeName, float attributeValue) {
        ((PdfDictionary) getPdfObject()).put(PdfStructTreeRoot.convertRoleToPdfName(attributeName), new PdfNumber((double) attributeValue));
        setModified();
        return this;
    }

    public String getAttributeAsEnum(String attributeName) {
        PdfName attrVal = ((PdfDictionary) getPdfObject()).getAsName(PdfStructTreeRoot.convertRoleToPdfName(attributeName));
        if (attrVal != null) {
            return attrVal.getValue();
        }
        return null;
    }

    public String getAttributeAsText(String attributeName) {
        PdfString attrVal = ((PdfDictionary) getPdfObject()).getAsString(PdfStructTreeRoot.convertRoleToPdfName(attributeName));
        if (attrVal != null) {
            return attrVal.toUnicodeString();
        }
        return null;
    }

    public Integer getAttributeAsInt(String attributeName) {
        PdfNumber attrVal = ((PdfDictionary) getPdfObject()).getAsNumber(PdfStructTreeRoot.convertRoleToPdfName(attributeName));
        if (attrVal != null) {
            return Integer.valueOf(attrVal.intValue());
        }
        Integer num = null;
        return null;
    }

    public Float getAttributeAsFloat(String attributeName) {
        PdfNumber attrVal = ((PdfDictionary) getPdfObject()).getAsNumber(PdfStructTreeRoot.convertRoleToPdfName(attributeName));
        if (attrVal != null) {
            return Float.valueOf(attrVal.floatValue());
        }
        Float f = null;
        return null;
    }

    public PdfStructureAttributes removeAttribute(String attributeName) {
        ((PdfDictionary) getPdfObject()).remove(PdfStructTreeRoot.convertRoleToPdfName(attributeName));
        setModified();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}

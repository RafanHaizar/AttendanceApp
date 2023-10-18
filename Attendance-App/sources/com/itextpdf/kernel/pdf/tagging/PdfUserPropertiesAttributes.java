package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import java.util.List;

public class PdfUserPropertiesAttributes extends PdfStructureAttributes {
    private static final long serialVersionUID = -3680551925943527773L;

    public PdfUserPropertiesAttributes(PdfDictionary attributesDict) {
        super(attributesDict);
    }

    public PdfUserPropertiesAttributes() {
        super(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1361O, PdfName.UserProperties);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1367P, new PdfArray());
    }

    public PdfUserPropertiesAttributes(List<PdfUserProperty> userProperties) {
        this();
        PdfArray arr = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1367P);
        for (PdfUserProperty userProperty : userProperties) {
            arr.add(userProperty.getPdfObject());
        }
    }

    public PdfUserPropertiesAttributes addUserProperty(PdfUserProperty userProperty) {
        ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1367P).add(userProperty.getPdfObject());
        setModified();
        return this;
    }

    public PdfUserProperty getUserProperty(int i) {
        PdfDictionary propDict = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1367P).getAsDictionary(i);
        if (propDict == null) {
            return null;
        }
        return new PdfUserProperty(propDict);
    }

    public PdfUserPropertiesAttributes removeUserProperty(int i) {
        ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1367P).remove(i);
        return this;
    }

    public int getNumberOfUserProperties() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1367P).size();
    }
}

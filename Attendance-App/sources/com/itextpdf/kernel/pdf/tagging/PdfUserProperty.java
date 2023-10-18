package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.PdfEncodings;

public class PdfUserProperty extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -347021704725128837L;

    public enum ValueType {
        UNKNOWN,
        TEXT,
        NUMBER,
        BOOLEAN
    }

    public PdfUserProperty(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfUserProperty(String name, String value) {
        super(new PdfDictionary());
        setName(name);
        setValue(value);
    }

    public PdfUserProperty(String name, int value) {
        super(new PdfDictionary());
        setName(name);
        setValue(value);
    }

    public PdfUserProperty(String name, float value) {
        super(new PdfDictionary());
        setName(name);
        setValue(value);
    }

    public PdfUserProperty(String name, boolean value) {
        super(new PdfDictionary());
        setName(name);
        setValue(value);
    }

    public String getName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1357N).toUnicodeString();
    }

    public PdfUserProperty setName(String name) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1357N, new PdfString(name, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public ValueType getValueType() {
        PdfObject valObj = ((PdfDictionary) getPdfObject()).get(PdfName.f1406V);
        if (valObj == null) {
            return ValueType.UNKNOWN;
        }
        switch (valObj.getType()) {
            case 2:
                return ValueType.BOOLEAN;
            case 8:
                return ValueType.NUMBER;
            case 10:
                return ValueType.TEXT;
            default:
                return ValueType.UNKNOWN;
        }
    }

    public PdfUserProperty setValue(String value) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, new PdfString(value, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public PdfUserProperty setValue(int value) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, new PdfNumber(value));
        return this;
    }

    public PdfUserProperty setValue(float value) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, new PdfNumber((double) value));
        return this;
    }

    public PdfUserProperty setValue(boolean value) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, new PdfBoolean(value));
        return this;
    }

    public String getValueAsText() {
        PdfString str = ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1406V);
        if (str != null) {
            return str.toUnicodeString();
        }
        return null;
    }

    public Float getValueAsFloat() {
        PdfNumber num = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1406V);
        if (num != null) {
            return Float.valueOf(num.floatValue());
        }
        Float f = null;
        return null;
    }

    public Boolean getValueAsBool() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1406V);
    }

    public String getValueFormattedRepresentation() {
        PdfString f = ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1324F);
        if (f != null) {
            return f.toUnicodeString();
        }
        return null;
    }

    public PdfUserProperty setValueFormattedRepresentation(String formattedRepresentation) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, new PdfString(formattedRepresentation, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public Boolean isHidden() {
        return ((PdfDictionary) getPdfObject()).getAsBool(PdfName.f1331H);
    }

    public PdfUserProperty setHidden(boolean isHidden) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1331H, new PdfBoolean(isHidden));
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}

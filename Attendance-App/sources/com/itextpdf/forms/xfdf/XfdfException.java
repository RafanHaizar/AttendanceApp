package com.itextpdf.forms.xfdf;

public class XfdfException extends RuntimeException {
    public static final String ATTRIBUTE_NAME_OR_VALUE_MISSING = "Attribute name or value are missing";
    public static final String PAGE_IS_MISSING = "Required Page attribute is missing.";

    public XfdfException(String message) {
        super(message);
    }
}

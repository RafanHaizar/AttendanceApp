package com.itextpdf.forms.xfdf;

public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(String attribute) {
        super("Required attribute " + attribute + " is not found");
    }
}

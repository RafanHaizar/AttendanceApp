package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssIdentifierValidator implements ICssDataTypeValidator {
    public boolean isValid(String objectString) {
        if ((objectString.length() < 2 || !objectString.startsWith("--")) && !objectString.matches("^[0-9].*")) {
            return true;
        }
        return false;
    }
}

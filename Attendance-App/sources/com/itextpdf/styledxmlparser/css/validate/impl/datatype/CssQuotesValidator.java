package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.resolve.CssQuotes;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssQuotesValidator implements ICssDataTypeValidator {
    public boolean isValid(String objectString) {
        if (CssQuotes.createQuotes(objectString, false) != null) {
            return true;
        }
        return false;
    }
}

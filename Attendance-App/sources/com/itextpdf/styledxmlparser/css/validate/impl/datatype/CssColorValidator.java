package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssColorValidator implements ICssDataTypeValidator {
    public boolean isValid(String objectString) {
        return WebColors.getRGBAColor(objectString) != null;
    }
}

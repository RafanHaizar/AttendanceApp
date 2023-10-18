package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class ArrayDataTypeValidator implements ICssDataTypeValidator {
    private final ICssDataTypeValidator dataTypeValidator;

    public ArrayDataTypeValidator(ICssDataTypeValidator dataTypeValidator2) {
        this.dataTypeValidator = dataTypeValidator2;
    }

    public boolean isValid(String objectString) {
        if (objectString == null) {
            return false;
        }
        for (String value : CssUtils.splitStringWithComma(objectString)) {
            if (!this.dataTypeValidator.isValid(value.trim())) {
                return false;
            }
        }
        return true;
    }
}

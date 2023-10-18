package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssBlendModeValidator implements ICssDataTypeValidator {
    public boolean isValid(String objectString) {
        return CommonCssConstants.BLEND_MODE_VALUES.contains(objectString);
    }
}

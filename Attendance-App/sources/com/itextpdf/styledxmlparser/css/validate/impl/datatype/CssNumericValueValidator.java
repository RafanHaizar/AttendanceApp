package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssNumericValueValidator implements ICssDataTypeValidator {
    private final boolean allowedNormal;
    private final boolean allowedPercent;

    public CssNumericValueValidator(boolean allowedPercent2, boolean allowedNormal2) {
        this.allowedPercent = allowedPercent2;
        this.allowedNormal = allowedNormal2;
    }

    public boolean isValid(String objectString) {
        if (objectString == null) {
            return false;
        }
        if (CommonCssConstants.INITIAL.equals(objectString) || CommonCssConstants.INHERIT.equals(objectString) || CommonCssConstants.UNSET.equals(objectString)) {
            return true;
        }
        if (CommonCssConstants.NORMAL.equals(objectString)) {
            return this.allowedNormal;
        }
        if (!CssUtils.isValidNumericValue(objectString)) {
            return false;
        }
        if (CssUtils.isPercentageValue(objectString)) {
            return this.allowedPercent;
        }
        return true;
    }
}

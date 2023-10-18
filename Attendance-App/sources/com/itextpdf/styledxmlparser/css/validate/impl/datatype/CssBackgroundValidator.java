package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssBackgroundUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
import java.util.List;

public class CssBackgroundValidator implements ICssDataTypeValidator {
    private static final int MAX_AMOUNT_OF_VALUES = 2;
    private final String backgroundProperty;

    public CssBackgroundValidator(String backgroundProperty2) {
        this.backgroundProperty = backgroundProperty2;
    }

    public boolean isValid(String objectString) {
        if (objectString == null) {
            return false;
        }
        if (CssUtils.isInitialOrInheritOrUnset(objectString)) {
            return true;
        }
        for (List<String> propertyValues : CssUtils.extractShorthandProperties(objectString)) {
            if (propertyValues.isEmpty() || propertyValues.size() > 2) {
                return false;
            }
            int i = 0;
            while (true) {
                if (i < propertyValues.size()) {
                    if (!isValidProperty(propertyValues, i)) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return true;
    }

    private boolean isValidProperty(List<String> propertyValues, int index) {
        if (!isPropertyValueCorrespondsPropertyType(propertyValues.get(index))) {
            return false;
        }
        if (propertyValues.size() != 2) {
            return true;
        }
        if (!isMultiValueAllowedForThisType() || !isMultiValueAllowedForThisValue(propertyValues.get(index))) {
            return false;
        }
        return checkMultiValuePositionXY(propertyValues, index);
    }

    private boolean checkMultiValuePositionXY(List<String> propertyValues, int index) {
        if (!CommonCssConstants.BACKGROUND_POSITION_X.equals(this.backgroundProperty) && !CommonCssConstants.BACKGROUND_POSITION_Y.equals(this.backgroundProperty)) {
            return true;
        }
        if (CommonCssConstants.BACKGROUND_POSITION_VALUES.contains(propertyValues.get(index)) && index == 1) {
            return false;
        }
        if (CommonCssConstants.BACKGROUND_POSITION_VALUES.contains(propertyValues.get(index)) || index == 1) {
            return true;
        }
        return false;
    }

    private boolean isMultiValueAllowedForThisType() {
        return !CommonCssConstants.BACKGROUND_ORIGIN.equals(this.backgroundProperty) && !CommonCssConstants.BACKGROUND_CLIP.equals(this.backgroundProperty) && !CommonCssConstants.BACKGROUND_IMAGE.equals(this.backgroundProperty) && !CommonCssConstants.BACKGROUND_ATTACHMENT.equals(this.backgroundProperty);
    }

    private static boolean isMultiValueAllowedForThisValue(String value) {
        return !CommonCssConstants.REPEAT_X.equals(value) && !CommonCssConstants.REPEAT_Y.equals(value) && !CommonCssConstants.COVER.equals(value) && !CommonCssConstants.CONTAIN.equals(value) && !CommonCssConstants.CENTER.equals(value);
    }

    private boolean isPropertyValueCorrespondsPropertyType(String value) {
        CssBackgroundUtils.BackgroundPropertyType propertyType = CssBackgroundUtils.resolveBackgroundPropertyType(value);
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.UNDEFINED) {
            return false;
        }
        if (CssBackgroundUtils.getBackgroundPropertyNameFromType(propertyType).equals(this.backgroundProperty)) {
            return true;
        }
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION && (CommonCssConstants.BACKGROUND_POSITION_X.equals(this.backgroundProperty) || CommonCssConstants.BACKGROUND_POSITION_Y.equals(this.backgroundProperty))) {
            return true;
        }
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP && (CommonCssConstants.BACKGROUND_CLIP.equals(this.backgroundProperty) || CommonCssConstants.BACKGROUND_ORIGIN.equals(this.backgroundProperty))) {
            return true;
        }
        if (propertyType != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
            return false;
        }
        if (CommonCssConstants.BACKGROUND_POSITION_X.equals(this.backgroundProperty) || CommonCssConstants.BACKGROUND_POSITION_Y.equals(this.backgroundProperty) || CommonCssConstants.BACKGROUND_SIZE.equals(this.backgroundProperty)) {
            return true;
        }
        return false;
    }
}

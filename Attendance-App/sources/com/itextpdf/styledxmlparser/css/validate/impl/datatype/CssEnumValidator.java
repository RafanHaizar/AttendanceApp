package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CssEnumValidator implements ICssDataTypeValidator {
    private List<String> allowedValues;

    public CssEnumValidator(String... allowedValues2) {
        this.allowedValues = new ArrayList(Arrays.asList(allowedValues2));
    }

    public void addAllowedValues(Collection<String> allowedValues2) {
        this.allowedValues.addAll(allowedValues2);
    }

    public boolean isValid(String objectString) {
        return this.allowedValues.contains(objectString);
    }
}

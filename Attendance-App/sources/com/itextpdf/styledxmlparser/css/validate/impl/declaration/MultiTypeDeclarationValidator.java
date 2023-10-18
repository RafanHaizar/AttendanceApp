package com.itextpdf.styledxmlparser.css.validate.impl.declaration;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
import com.itextpdf.styledxmlparser.css.validate.ICssDeclarationValidator;
import java.util.Arrays;
import java.util.List;

public class MultiTypeDeclarationValidator implements ICssDeclarationValidator {
    private List<ICssDataTypeValidator> allowedTypes;

    public MultiTypeDeclarationValidator(ICssDataTypeValidator... allowedTypes2) {
        this.allowedTypes = Arrays.asList(allowedTypes2);
    }

    public boolean isValid(CssDeclaration cssDeclaration) {
        for (ICssDataTypeValidator dTypeValidator : this.allowedTypes) {
            if (dTypeValidator.isValid(cssDeclaration.getExpression())) {
                return true;
            }
        }
        return false;
    }
}

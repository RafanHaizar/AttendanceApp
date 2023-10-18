package com.itextpdf.styledxmlparser.css.validate.impl.declaration;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
import com.itextpdf.styledxmlparser.css.validate.ICssDeclarationValidator;

public class SingleTypeDeclarationValidator implements ICssDeclarationValidator {
    private ICssDataTypeValidator dataTypeValidator;

    public SingleTypeDeclarationValidator(ICssDataTypeValidator dataTypeValidator2) {
        this.dataTypeValidator = dataTypeValidator2;
    }

    public boolean isValid(CssDeclaration cssDeclaration) {
        return this.dataTypeValidator.isValid(cssDeclaration.getExpression());
    }
}

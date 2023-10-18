package com.itextpdf.styledxmlparser.css.validate;

import com.itextpdf.styledxmlparser.css.CssDeclaration;

public interface ICssDeclarationValidator {
    boolean isValid(CssDeclaration cssDeclaration);
}

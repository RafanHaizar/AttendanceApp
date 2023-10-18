package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

public class OutlineShorthandResolver extends AbstractBorderShorthandResolver {
    /* access modifiers changed from: protected */
    public String getPrefix() {
        return CommonCssConstants.OUTLINE;
    }
}

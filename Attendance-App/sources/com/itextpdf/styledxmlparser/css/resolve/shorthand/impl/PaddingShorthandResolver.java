package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

public class PaddingShorthandResolver extends AbstractBoxShorthandResolver {
    /* access modifiers changed from: protected */
    public String getPrefix() {
        return CommonCssConstants.PADDING;
    }

    /* access modifiers changed from: protected */
    public String getPostfix() {
        return "";
    }
}

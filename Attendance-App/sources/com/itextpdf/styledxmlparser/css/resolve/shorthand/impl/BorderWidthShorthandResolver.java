package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

public class BorderWidthShorthandResolver extends AbstractBoxShorthandResolver {
    /* access modifiers changed from: protected */
    public String getPrefix() {
        return CommonCssConstants.BORDER;
    }

    /* access modifiers changed from: protected */
    public String getPostfix() {
        return "-width";
    }
}

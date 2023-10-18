package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

public abstract class AbstractBorderShorthandResolver implements IShorthandResolver {
    private static final String _0_COLOR = "{0}-color";
    private static final String _0_STYLE = "{0}-style";
    private static final String _0_WIDTH = "{0}-width";

    /* access modifiers changed from: protected */
    public abstract String getPrefix();

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String str = shorthandExpression;
        String widthPropName = MessageFormatUtil.format(_0_WIDTH, getPrefix());
        String stylePropName = MessageFormatUtil.format(_0_STYLE, getPrefix());
        String colorPropName = MessageFormatUtil.format(_0_COLOR, getPrefix());
        String str2 = CommonCssConstants.INITIAL;
        if (str2.equals(str) || CommonCssConstants.INHERIT.equals(str)) {
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(widthPropName, str), new CssDeclaration(stylePropName, str), new CssDeclaration(colorPropName, str)});
        }
        String borderColorValue = null;
        String borderStyleValue = null;
        String borderWidthValue = null;
        for (String value : str.split("\\s+")) {
            if (str2.equals(value) || CommonCssConstants.INHERIT.equals(value)) {
                LoggerFactory.getLogger((Class<?>) AbstractBorderShorthandResolver.class).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, str));
                return Collections.emptyList();
            }
            if (CommonCssConstants.BORDER_WIDTH_VALUES.contains(value) || CssUtils.isNumericValue(value) || CssUtils.isMetricValue(value) || CssUtils.isRelativeValue(value)) {
                borderWidthValue = value;
            } else if (CommonCssConstants.BORDER_STYLE_VALUES.contains(value) || value.equals("auto")) {
                borderStyleValue = value;
            } else if (CssUtils.isColorProperty(value)) {
                borderColorValue = value;
            }
        }
        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        resolvedDecl.add(new CssDeclaration(widthPropName, borderWidthValue == null ? str2 : borderWidthValue));
        resolvedDecl.add(new CssDeclaration(stylePropName, borderStyleValue == null ? str2 : borderStyleValue));
        if (borderColorValue != null) {
            str2 = borderColorValue;
        }
        resolvedDecl.add(new CssDeclaration(colorPropName, str2));
        return resolvedDecl;
    }
}

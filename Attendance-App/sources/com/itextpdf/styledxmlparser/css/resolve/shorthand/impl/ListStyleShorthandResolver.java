package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListStyleShorthandResolver implements IShorthandResolver {
    private static final Set<String> LIST_STYLE_POSITION_VALUES = new HashSet(Arrays.asList(new String[]{CommonCssConstants.INSIDE, CommonCssConstants.OUTSIDE}));
    private static final Set<String> LIST_STYLE_TYPE_VALUES = new HashSet(Arrays.asList(new String[]{CommonCssConstants.DISC, CommonCssConstants.ARMENIAN, "circle", CommonCssConstants.CJK_IDEOGRAPHIC, CommonCssConstants.DECIMAL, CommonCssConstants.DECIMAL_LEADING_ZERO, CommonCssConstants.GEORGIAN, CommonCssConstants.HEBREW, CommonCssConstants.HIRAGANA, CommonCssConstants.HIRAGANA_IROHA, CommonCssConstants.LOWER_ALPHA, CommonCssConstants.LOWER_GREEK, CommonCssConstants.LOWER_LATIN, CommonCssConstants.LOWER_ROMAN, "none", "square", CommonCssConstants.UPPER_ALPHA, CommonCssConstants.UPPER_LATIN, CommonCssConstants.UPPER_ROMAN}));

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String str = CommonCssConstants.INITIAL;
        if (str.equals(shorthandExpression) || CommonCssConstants.INHERIT.equals(shorthandExpression)) {
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CommonCssConstants.LIST_STYLE_TYPE, shorthandExpression), new CssDeclaration(CommonCssConstants.LIST_STYLE_POSITION, shorthandExpression), new CssDeclaration(CommonCssConstants.LIST_STYLE_IMAGE, shorthandExpression)});
        }
        String listStyleTypeValue = null;
        String listStylePositionValue = null;
        String listStyleImageValue = null;
        for (String value : CssUtils.extractShorthandProperties(shorthandExpression).get(0)) {
            if (value.contains("url(") || CssGradientUtil.isCssLinearGradientValue(value) || ("none".equals(value) && listStyleTypeValue != null)) {
                listStyleImageValue = value;
            } else if (LIST_STYLE_TYPE_VALUES.contains(value)) {
                listStyleTypeValue = value;
            } else if (LIST_STYLE_POSITION_VALUES.contains(value)) {
                listStylePositionValue = value;
            }
        }
        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        resolvedDecl.add(new CssDeclaration(CommonCssConstants.LIST_STYLE_TYPE, listStyleTypeValue == null ? str : listStyleTypeValue));
        resolvedDecl.add(new CssDeclaration(CommonCssConstants.LIST_STYLE_POSITION, listStylePositionValue == null ? str : listStylePositionValue));
        if (listStyleImageValue != null) {
            str = listStyleImageValue;
        }
        resolvedDecl.add(new CssDeclaration(CommonCssConstants.LIST_STYLE_IMAGE, str));
        return resolvedDecl;
    }
}

package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextDecorationShorthandResolver implements IShorthandResolver {
    private static final Set<String> TEXT_DECORATION_LINE_VALUES = new HashSet(Arrays.asList(new String[]{"underline", CommonCssConstants.OVERLINE, CommonCssConstants.LINE_THROUGH, CommonCssConstants.BLINK}));
    private static final Set<String> TEXT_DECORATION_STYLE_VALUES = new HashSet(Arrays.asList(new String[]{CommonCssConstants.SOLID, CommonCssConstants.DOUBLE, CommonCssConstants.DOTTED, CommonCssConstants.DASHED, CommonCssConstants.WAVY}));

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String str = CommonCssConstants.INITIAL;
        if (str.equals(shorthandExpression) || CommonCssConstants.INHERIT.equals(shorthandExpression)) {
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CommonCssConstants.TEXT_DECORATION_LINE, shorthandExpression), new CssDeclaration(CommonCssConstants.TEXT_DECORATION_STYLE, shorthandExpression), new CssDeclaration(CommonCssConstants.TEXT_DECORATION_COLOR, shorthandExpression)});
        }
        String[] props = shorthandExpression.split("\\s+(?![^\\(]*\\))");
        List<String> textDecorationLineValues = new ArrayList<>();
        String textDecorationStyleValue = null;
        String textDecorationColorValue = null;
        for (String value : props) {
            if (TEXT_DECORATION_LINE_VALUES.contains(value) || "none".equals(value)) {
                textDecorationLineValues.add(value);
            } else if (TEXT_DECORATION_STYLE_VALUES.contains(value)) {
                textDecorationStyleValue = value;
            } else if (!value.isEmpty()) {
                textDecorationColorValue = value;
            }
        }
        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        if (textDecorationLineValues.isEmpty()) {
            resolvedDecl.add(new CssDeclaration(CommonCssConstants.TEXT_DECORATION_LINE, str));
        } else {
            StringBuilder resultLine = new StringBuilder();
            for (String line : textDecorationLineValues) {
                resultLine.append(line).append(" ");
            }
            resolvedDecl.add(new CssDeclaration(CommonCssConstants.TEXT_DECORATION_LINE, resultLine.toString().trim()));
        }
        resolvedDecl.add(new CssDeclaration(CommonCssConstants.TEXT_DECORATION_STYLE, textDecorationStyleValue == null ? str : textDecorationStyleValue));
        if (textDecorationColorValue != null) {
            str = textDecorationColorValue;
        }
        resolvedDecl.add(new CssDeclaration(CommonCssConstants.TEXT_DECORATION_COLOR, str));
        return resolvedDecl;
    }
}

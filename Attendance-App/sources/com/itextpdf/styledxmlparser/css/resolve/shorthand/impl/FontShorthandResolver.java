package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class FontShorthandResolver implements IShorthandResolver {
    private static final Set<String> FONT_SIZE_VALUES = new HashSet(Arrays.asList(new String[]{CommonCssConstants.MEDIUM, CommonCssConstants.XX_SMALL, CommonCssConstants.X_SMALL, CommonCssConstants.SMALL, CommonCssConstants.LARGE, CommonCssConstants.X_LARGE, CommonCssConstants.XX_LARGE, CommonCssConstants.SMALLER, CommonCssConstants.LARGER}));
    private static final Set<String> FONT_WEIGHT_NOT_DEFAULT_VALUES = new HashSet(Arrays.asList(new String[]{"bold", CommonCssConstants.BOLDER, CommonCssConstants.LIGHTER, "100", "200", "300", "400", "500", "600", "700", "800", "900"}));
    private static final Set<String> UNSUPPORTED_VALUES_OF_FONT_SHORTHAND = new HashSet(Arrays.asList(new String[]{"caption", "icon", CommonCssConstants.MENU, CommonCssConstants.MESSAGE_BOX, CommonCssConstants.SMALL_CAPTION, CommonCssConstants.STATUS_BAR}));

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String str = shorthandExpression;
        int i = 0;
        if (UNSUPPORTED_VALUES_OF_FONT_SHORTHAND.contains(str)) {
            LoggerFactory.getLogger((Class<?>) FontShorthandResolver.class).error(MessageFormatUtil.format("The \"{0}\" value of CSS shorthand property \"font\" is not supported", str));
        }
        String str2 = CommonCssConstants.INITIAL;
        if (!str2.equals(str)) {
            if (!CommonCssConstants.INHERIT.equals(str)) {
                String fontStyleValue = null;
                String fontVariantValue = null;
                String fontWeightValue = null;
                String fontSizeValue = null;
                String lineHeightValue = null;
                String fontFamilyValue = null;
                for (String value : getFontProperties(str.replaceAll("\\s*,\\s*", ","))) {
                    int slashSymbolIndex = value.indexOf(47);
                    if ("italic".equals(value) || CommonCssConstants.OBLIQUE.equals(value)) {
                        fontStyleValue = value;
                    } else if (CommonCssConstants.SMALL_CAPS.equals(value)) {
                        fontVariantValue = value;
                    } else if (FONT_WEIGHT_NOT_DEFAULT_VALUES.contains(value)) {
                        fontWeightValue = value;
                    } else if (slashSymbolIndex > 0) {
                        String fontSizeValue2 = value.substring(i, slashSymbolIndex);
                        lineHeightValue = value.substring(slashSymbolIndex + 1, value.length());
                        fontSizeValue = fontSizeValue2;
                    } else if (FONT_SIZE_VALUES.contains(value) || CssUtils.isMetricValue(value) || CssUtils.isNumericValue(value) || CssUtils.isRelativeValue(value)) {
                        fontSizeValue = value;
                    } else {
                        fontFamilyValue = value;
                    }
                    i = 0;
                }
                CssDeclaration[] cssDeclarationArr = new CssDeclaration[6];
                cssDeclarationArr[0] = new CssDeclaration("font-style", fontStyleValue == null ? str2 : fontStyleValue);
                cssDeclarationArr[1] = new CssDeclaration(CommonCssConstants.FONT_VARIANT, fontVariantValue == null ? str2 : fontVariantValue);
                cssDeclarationArr[2] = new CssDeclaration("font-weight", fontWeightValue == null ? str2 : fontWeightValue);
                cssDeclarationArr[3] = new CssDeclaration("font-size", fontSizeValue == null ? str2 : fontSizeValue);
                cssDeclarationArr[4] = new CssDeclaration(CommonCssConstants.LINE_HEIGHT, lineHeightValue == null ? str2 : lineHeightValue);
                if (fontFamilyValue != null) {
                    str2 = fontFamilyValue;
                }
                cssDeclarationArr[5] = new CssDeclaration("font-family", str2);
                return Arrays.asList(cssDeclarationArr);
            }
        }
        return Arrays.asList(new CssDeclaration[]{new CssDeclaration("font-style", str), new CssDeclaration(CommonCssConstants.FONT_VARIANT, str), new CssDeclaration("font-weight", str), new CssDeclaration("font-size", str), new CssDeclaration(CommonCssConstants.LINE_HEIGHT, str), new CssDeclaration("font-family", str)});
    }

    private List<String> getFontProperties(String shorthandExpression) {
        boolean doubleQuotesAreSpotted = false;
        boolean singleQuoteIsSpotted = false;
        List<String> properties = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shorthandExpression.length(); i++) {
            char currentChar = shorthandExpression.charAt(i);
            boolean z = false;
            if (currentChar == '\"') {
                if (!doubleQuotesAreSpotted) {
                    z = true;
                }
                doubleQuotesAreSpotted = z;
                sb.append(currentChar);
            } else if (currentChar == '\'') {
                if (!singleQuoteIsSpotted) {
                    z = true;
                }
                singleQuoteIsSpotted = z;
                sb.append(currentChar);
            } else if (doubleQuotesAreSpotted || singleQuoteIsSpotted || !Character.isWhitespace(currentChar)) {
                sb.append(currentChar);
            } else if (sb.length() > 0) {
                properties.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            properties.add(sb.toString());
        }
        return properties;
    }
}

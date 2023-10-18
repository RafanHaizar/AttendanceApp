package com.itextpdf.styledxmlparser.util;

import com.itextpdf.p026io.util.DecimalFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.resolve.CssPropertyMerger;
import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class StyleUtil {
    private static final List<String> fontSizeDependentPercentage;

    private StyleUtil() {
    }

    static {
        ArrayList arrayList = new ArrayList(2);
        fontSizeDependentPercentage = arrayList;
        arrayList.add("font-size");
        arrayList.add(CommonCssConstants.LINE_HEIGHT);
    }

    public static Map<String, String> mergeParentStyleDeclaration(Map<String, String> styles, String styleProperty, String parentPropValue, String parentFontSizeString, Set<IStyleInheritance> inheritanceRules) {
        String childPropValue = styles.get(styleProperty);
        if ((childPropValue != null || !checkInheritance(styleProperty, inheritanceRules)) && !CommonCssConstants.INHERIT.equals(childPropValue)) {
            if (CommonCssConstants.TEXT_DECORATION_LINE.equals(styleProperty) && !CommonCssConstants.INLINE_BLOCK.equals(styles.get(CommonCssConstants.DISPLAY))) {
                styles.put(styleProperty, CssPropertyMerger.mergeTextDecoration(childPropValue, parentPropValue));
            }
        } else if (valueIsOfMeasurement(parentPropValue, CommonCssConstants.f1611EM) || valueIsOfMeasurement(parentPropValue, CommonCssConstants.f1612EX) || (valueIsOfMeasurement(parentPropValue, CommonCssConstants.PERCENTAGE) && fontSizeDependentPercentage.contains(styleProperty))) {
            styles.put(styleProperty, DecimalFormatUtil.formatNumber((double) CssUtils.parseRelativeValue(parentPropValue, CssUtils.parseAbsoluteLength(parentFontSizeString)), "0.####") + CommonCssConstants.f1616PT);
        } else {
            styles.put(styleProperty, parentPropValue);
        }
        return styles;
    }

    private static boolean checkInheritance(String styleProperty, Set<IStyleInheritance> inheritanceRules) {
        for (IStyleInheritance inheritanceRule : inheritanceRules) {
            if (inheritanceRule.isInheritable(styleProperty)) {
                return true;
            }
        }
        return false;
    }

    private static boolean valueIsOfMeasurement(String value, String measurement) {
        if (value != null && value.endsWith(measurement) && CssUtils.isNumericValue(value.substring(0, value.length() - measurement.length()).trim())) {
            return true;
        }
        return false;
    }
}

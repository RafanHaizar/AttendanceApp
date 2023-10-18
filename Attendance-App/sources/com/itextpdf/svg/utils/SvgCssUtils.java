package com.itextpdf.svg.utils;

import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SvgCssUtils {
    private SvgCssUtils() {
    }

    public static List<String> splitValueList(String value) {
        List<String> result = new ArrayList<>();
        if (value != null && value.length() > 0) {
            result.addAll(Arrays.asList(value.trim().split("\\s*(,|\\s)\\s*")));
        }
        return result;
    }

    public static String convertFloatToString(float value) {
        return String.valueOf(value);
    }

    public static String convertDoubleToString(double value) {
        return String.valueOf(value);
    }

    @Deprecated
    public static float convertPtsToPx(float pts) {
        return pts / 0.75f;
    }

    @Deprecated
    public static double convertPtsToPx(double pts) {
        return pts / 0.75d;
    }

    @Deprecated
    public static boolean isStyleSheetLink(IElementNode headChildElement) {
        return CssUtils.isStyleSheetLink(headChildElement);
    }
}

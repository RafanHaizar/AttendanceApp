package com.itextpdf.styledxmlparser.css.resolve;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class CssPropertyMerger {
    private CssPropertyMerger() {
    }

    public static String mergeTextDecoration(String firstValue, String secondValue) {
        if (firstValue == null) {
            return secondValue;
        }
        if (secondValue == null) {
            return firstValue;
        }
        Set<String> merged = normalizeTextDecoration(firstValue);
        merged.addAll(normalizeTextDecoration(secondValue));
        StringBuilder sb = new StringBuilder();
        for (String mergedProp : merged) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(mergedProp);
        }
        return sb.length() != 0 ? sb.toString() : "none";
    }

    private static Set<String> normalizeTextDecoration(String value) {
        String[] parts = value.split("\\s+");
        Set<String> merged = new LinkedHashSet<>();
        merged.addAll(Arrays.asList(parts));
        if (merged.contains("none")) {
            merged.clear();
        }
        return merged;
    }
}

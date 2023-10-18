package com.itextpdf.svg.utils;

import java.util.regex.Pattern;

public class SvgRegexUtils {
    public static boolean containsAtLeastOneMatch(Pattern regexPattern, String stringToExamine) {
        return regexPattern.matcher(stringToExamine).find();
    }
}

package com.itextpdf.styledxmlparser;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.regex.Pattern;

public class PortUtil {
    private PortUtil() {
    }

    public static Reader wrapInBufferedReader(Reader inputStreamReader) {
        return new BufferedReader(inputStreamReader);
    }

    public static Pattern createRegexPatternWithDotMatchingNewlines(String regex) {
        return Pattern.compile(regex, 32);
    }
}

package com.itextpdf.styledxmlparser.jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortUtil {
    public static final String escapedSingleBracket = "''";
    public static final String signedNumberFormat = ",number,+#;-#";

    public static boolean hasMatch(Pattern pattern, String input) {
        return pattern.matcher(input).find();
    }

    public static boolean charsetIsSupported(String charsetName) {
        try {
            return Charset.isSupported(charsetName);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static RandomAccessFile getReadOnlyRandomAccesFile(File file) throws FileNotFoundException {
        return new RandomAccessFile(file, "r");
    }

    public static boolean isSuccessful(Matcher m) {
        return m.find();
    }
}

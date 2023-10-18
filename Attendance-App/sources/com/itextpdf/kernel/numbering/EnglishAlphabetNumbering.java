package com.itextpdf.kernel.numbering;

public class EnglishAlphabetNumbering {
    protected static final int ALPHABET_LENGTH = 26;
    protected static final char[] ALPHABET_LOWERCASE = new char[26];
    protected static final char[] ALPHABET_UPPERCASE = new char[26];

    static {
        for (int i = 0; i < 26; i++) {
            ALPHABET_LOWERCASE[i] = (char) (i + 97);
            ALPHABET_UPPERCASE[i] = (char) (i + 65);
        }
    }

    public static String toLatinAlphabetNumberLowerCase(int number) {
        return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_LOWERCASE);
    }

    public static String toLatinAlphabetNumberUpperCase(int number) {
        return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_UPPERCASE);
    }

    public static String toLatinAlphabetNumber(int number, boolean upperCase) {
        return upperCase ? toLatinAlphabetNumberUpperCase(number) : toLatinAlphabetNumberLowerCase(number);
    }
}

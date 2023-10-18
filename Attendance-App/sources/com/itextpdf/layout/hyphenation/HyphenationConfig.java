package com.itextpdf.layout.hyphenation;

public class HyphenationConfig {
    protected char hyphenSymbol = '-';
    protected Hyphenator hyphenator;

    public HyphenationConfig(int leftMin, int rightMin) {
        this.hyphenator = new Hyphenator((String) null, (String) null, leftMin, rightMin);
    }

    public HyphenationConfig(Hyphenator hyphenator2) {
        this.hyphenator = hyphenator2;
    }

    public HyphenationConfig(String lang, String country, int leftMin, int rightMin) {
        this.hyphenator = new Hyphenator(lang, country, leftMin, rightMin);
    }

    public Hyphenation hyphenate(String word) {
        Hyphenator hyphenator2 = this.hyphenator;
        if (hyphenator2 != null) {
            return hyphenator2.hyphenate(word);
        }
        return null;
    }

    public char getHyphenSymbol() {
        return this.hyphenSymbol;
    }

    public void setHyphenSymbol(char hyphenSymbol2) {
        this.hyphenSymbol = hyphenSymbol2;
    }
}

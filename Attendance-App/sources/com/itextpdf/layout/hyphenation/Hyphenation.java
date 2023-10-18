package com.itextpdf.layout.hyphenation;

public class Hyphenation {
    private int[] hyphenPoints;
    private int len;
    private String word;

    Hyphenation(String word2, int[] points) {
        this.word = word2;
        this.hyphenPoints = points;
        this.len = points.length;
    }

    public int length() {
        return this.len;
    }

    public String getPreHyphenText(int index) {
        return this.word.substring(0, this.hyphenPoints[index]);
    }

    public String getPostHyphenText(int index) {
        return this.word.substring(this.hyphenPoints[index]);
    }

    public int[] getHyphenationPoints() {
        return this.hyphenPoints;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        int start = 0;
        for (int i = 0; i < this.len; i++) {
            str.append(this.word.substring(start, this.hyphenPoints[i]) + "-");
            start = this.hyphenPoints[i];
        }
        str.append(this.word.substring(start));
        return str.toString();
    }
}

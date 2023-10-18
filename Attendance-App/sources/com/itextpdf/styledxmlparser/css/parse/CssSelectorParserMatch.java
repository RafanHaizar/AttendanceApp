package com.itextpdf.styledxmlparser.css.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CssSelectorParserMatch {
    private Matcher matcher;
    private String source;
    private boolean success;

    public CssSelectorParserMatch(String source2, Pattern pattern) {
        this.source = source2;
        this.matcher = pattern.matcher(source2);
        next();
    }

    public int getIndex() {
        return this.matcher.start();
    }

    public String getValue() {
        return this.matcher.group(0);
    }

    public String getSource() {
        return this.source;
    }

    public boolean success() {
        return this.success;
    }

    public void next() {
        this.success = this.matcher.find();
    }

    public void next(int startIndex) {
        this.success = this.matcher.find(startIndex);
    }
}

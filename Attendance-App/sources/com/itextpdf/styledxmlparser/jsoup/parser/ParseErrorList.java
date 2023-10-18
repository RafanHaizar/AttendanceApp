package com.itextpdf.styledxmlparser.jsoup.parser;

import java.util.ArrayList;

class ParseErrorList extends ArrayList<ParseError> {
    private static final int INITIAL_CAPACITY = 16;
    private final int maxSize;

    ParseErrorList(int initialCapacity, int maxSize2) {
        super(initialCapacity);
        this.maxSize = maxSize2;
    }

    /* access modifiers changed from: package-private */
    public boolean canAddError() {
        return size() < this.maxSize;
    }

    /* access modifiers changed from: package-private */
    public int getMaxSize() {
        return this.maxSize;
    }

    static ParseErrorList noTracking() {
        return new ParseErrorList(0, 0);
    }

    static ParseErrorList tracking(int maxSize2) {
        return new ParseErrorList(16, maxSize2);
    }
}

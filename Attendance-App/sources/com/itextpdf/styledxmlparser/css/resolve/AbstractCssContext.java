package com.itextpdf.styledxmlparser.css.resolve;

public abstract class AbstractCssContext {
    private int quotesDepth = 0;

    public int getQuotesDepth() {
        return this.quotesDepth;
    }

    public void setQuotesDepth(int quotesDepth2) {
        this.quotesDepth = quotesDepth2;
    }
}

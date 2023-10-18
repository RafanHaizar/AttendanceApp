package com.itextpdf.p026io.font.otf;

/* renamed from: com.itextpdf.io.font.otf.ContextualSubTable */
public abstract class ContextualSubTable extends ContextualTable<ContextualSubstRule> {
    private static final long serialVersionUID = 1802216575331243298L;

    protected ContextualSubTable(OpenTypeFontTableReader openReader, int lookupFlag) {
        super(openReader, lookupFlag);
    }

    public ContextualSubstRule getMatchingContextRule(GlyphLine line) {
        return (ContextualSubstRule) super.getMatchingContextRule(line);
    }

    /* access modifiers changed from: protected */
    public int checkIfContextMatch(GlyphLine line, ContextualSubstRule rule) {
        return super.checkIfContextMatch(line, rule);
    }
}

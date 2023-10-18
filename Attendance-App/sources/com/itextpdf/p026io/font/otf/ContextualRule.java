package com.itextpdf.p026io.font.otf;

import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.otf.ContextualRule */
public abstract class ContextualRule implements Serializable {
    private static final long serialVersionUID = -9013175115747848532L;

    public abstract int getContextLength();

    public abstract boolean isGlyphMatchesInput(int i, int i2);

    public int getLookaheadContextLength() {
        return 0;
    }

    public int getBacktrackContextLength() {
        return 0;
    }

    public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
        return false;
    }

    public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
        return false;
    }
}

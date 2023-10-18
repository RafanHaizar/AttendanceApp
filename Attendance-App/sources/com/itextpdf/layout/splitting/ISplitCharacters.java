package com.itextpdf.layout.splitting;

import com.itextpdf.p026io.font.otf.GlyphLine;

public interface ISplitCharacters {
    boolean isSplitCharacter(GlyphLine glyphLine, int i);
}

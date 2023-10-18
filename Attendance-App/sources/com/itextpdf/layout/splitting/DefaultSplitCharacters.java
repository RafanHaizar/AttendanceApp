package com.itextpdf.layout.splitting;

import com.itextpdf.p026io.font.otf.GlyphLine;

public class DefaultSplitCharacters implements ISplitCharacters {
    public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
        if (!text.get(glyphPos).hasValidUnicode()) {
            return false;
        }
        int charCode = text.get(glyphPos).getUnicode();
        if (glyphPos == 0 && charCode == 45 && text.size() - 1 > glyphPos && isADigitChar(text, glyphPos + 1)) {
            return false;
        }
        if (charCode <= 32 || charCode == 45 || charCode == 8208 || ((charCode >= 8194 && charCode <= 8203) || ((charCode >= 11904 && charCode < 55200) || ((charCode >= 63744 && charCode < 64256) || ((charCode >= 65072 && charCode < 65104) || (charCode >= 65377 && charCode < 65440)))))) {
            return true;
        }
        return false;
    }

    private boolean isADigitChar(GlyphLine text, int glyphPos) {
        return Character.isDigit(text.get(glyphPos).getChars()[0]);
    }
}

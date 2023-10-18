package com.itextpdf.layout.font;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.util.TextUtil;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComplexFontSelectorStrategy extends FontSelectorStrategy {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private PdfFont font = null;
    private FontSelector selector;

    public ComplexFontSelectorStrategy(String text, FontSelector selector2, FontProvider provider, FontSet additionalFonts) {
        super(text, provider, additionalFonts);
        this.selector = selector2;
    }

    public ComplexFontSelectorStrategy(String text, FontSelector selector2, FontProvider provider) {
        super(text, provider, (FontSet) null);
        this.selector = selector2;
    }

    public PdfFont getCurrentFont() {
        return this.font;
    }

    public List<Glyph> nextGlyphs() {
        boolean z;
        int codePoint;
        PdfFont currentFont;
        Glyph glyph;
        this.font = null;
        int nextUnignorable = nextSignificantIndex();
        if (nextUnignorable < this.text.length()) {
            Iterator<FontInfo> it = this.selector.getFonts().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                FontInfo f = it.next();
                if (isSurrogatePair(this.text, nextUnignorable)) {
                    codePoint = TextUtil.convertToUtf32(this.text, nextUnignorable);
                } else {
                    codePoint = this.text.charAt(nextUnignorable);
                }
                if (f.getFontUnicodeRange().contains(codePoint) && (glyph = currentFont.getGlyph(codePoint)) != null && glyph.getCode() != 0) {
                    this.font = (currentFont = getPdfFont(f));
                    break;
                }
            }
        }
        List<Glyph> glyphs = new ArrayList<>();
        boolean anyGlyphsAppended = false;
        if (this.font != null) {
            Character.UnicodeScript unicodeScript = nextSignificantUnicodeScript(nextUnignorable);
            int to = nextUnignorable;
            int i = nextUnignorable;
            while (true) {
                z = true;
                if (i < this.text.length()) {
                    int codePoint2 = isSurrogatePair(this.text, i) ? TextUtil.convertToUtf32(this.text, i) : this.text.charAt(i);
                    Character.UnicodeScript currScript = Character.UnicodeScript.of(codePoint2);
                    if (isSignificantUnicodeScript(currScript) && currScript != unicodeScript) {
                        break;
                    }
                    if (codePoint2 > 65535) {
                        i++;
                    }
                    to = i;
                    i++;
                } else {
                    break;
                }
            }
            int numOfAppendedGlyphs = this.font.appendGlyphs(this.text, this.index, to, glyphs);
            if (numOfAppendedGlyphs <= 0) {
                z = false;
            }
            anyGlyphsAppended = z;
            if (anyGlyphsAppended) {
                this.index += numOfAppendedGlyphs;
            } else {
                throw new AssertionError();
            }
        }
        if (!anyGlyphsAppended) {
            this.font = getPdfFont(this.selector.bestMatch());
            if (this.index != nextUnignorable) {
                this.index += this.font.appendGlyphs(this.text, this.index, nextUnignorable - 1, glyphs);
            }
            while (this.index <= nextUnignorable && this.index < this.text.length()) {
                this.index += this.font.appendAnyGlyph(this.text, this.index, glyphs);
            }
        }
        return glyphs;
    }

    private int nextSignificantIndex() {
        int nextValidChar = this.index;
        while (nextValidChar < this.text.length() && TextUtil.isWhitespaceOrNonPrintable(this.text.charAt(nextValidChar))) {
            nextValidChar++;
        }
        return nextValidChar;
    }

    private Character.UnicodeScript nextSignificantUnicodeScript(int from) {
        int codePoint;
        int i = from;
        while (i < this.text.length()) {
            if (isSurrogatePair(this.text, i)) {
                codePoint = TextUtil.convertToUtf32(this.text, i);
                i++;
            } else {
                codePoint = this.text.charAt(i);
            }
            Character.UnicodeScript unicodeScript = Character.UnicodeScript.of(codePoint);
            if (isSignificantUnicodeScript(unicodeScript)) {
                return unicodeScript;
            }
            i++;
        }
        return Character.UnicodeScript.COMMON;
    }

    private static boolean isSignificantUnicodeScript(Character.UnicodeScript unicodeScript) {
        return (unicodeScript == Character.UnicodeScript.COMMON || unicodeScript == Character.UnicodeScript.INHERITED) ? false : true;
    }

    private static boolean isSurrogatePair(String text, int idx) {
        if (!TextUtil.isSurrogateHigh(text.charAt(idx)) || idx >= text.length() - 1 || !TextUtil.isSurrogateLow(text.charAt(idx + 1))) {
            return false;
        }
        return true;
    }
}

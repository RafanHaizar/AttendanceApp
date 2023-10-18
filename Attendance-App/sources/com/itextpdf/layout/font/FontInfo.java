package com.itextpdf.layout.font;

import com.itextpdf.p026io.font.FontCacheKey;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.FontProgramDescriptor;
import com.itextpdf.p026io.font.FontProgramDescriptorFactory;
import com.itextpdf.p026io.util.ArrayUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FontInfo {
    private static final Map<FontCacheKey, FontProgramDescriptor> fontNamesCache = new ConcurrentHashMap();
    private final String alias;
    private final FontProgramDescriptor descriptor;
    private final String encoding;
    private final byte[] fontData;
    private final String fontName;
    private final int hash;
    private final Range range;

    private FontInfo(String fontName2, byte[] fontData2, String encoding2, FontProgramDescriptor descriptor2, Range unicodeRange, String alias2) {
        this.fontName = fontName2;
        this.fontData = fontData2;
        this.encoding = encoding2;
        this.descriptor = descriptor2;
        Range fullRange = unicodeRange != null ? unicodeRange : RangeBuilder.getFullRange();
        this.range = fullRange;
        this.alias = alias2 != null ? alias2.toLowerCase() : null;
        this.hash = calculateHashCode(fontName2, fontData2, encoding2, fullRange);
    }

    public static FontInfo create(FontInfo fontInfo, String alias2, Range range2) {
        return new FontInfo(fontInfo.fontName, fontInfo.fontData, fontInfo.encoding, fontInfo.descriptor, range2, alias2);
    }

    public static FontInfo create(FontInfo fontInfo, String alias2) {
        return create(fontInfo, alias2, (Range) null);
    }

    public static FontInfo create(FontProgram fontProgram, String encoding2, String alias2, Range range2) {
        FontProgramDescriptor descriptor2 = FontProgramDescriptorFactory.fetchDescriptor(fontProgram);
        return new FontInfo(descriptor2.getFontName(), (byte[]) null, encoding2, descriptor2, range2, alias2);
    }

    public static FontInfo create(FontProgram fontProgram, String encoding2, String alias2) {
        return create(fontProgram, encoding2, alias2, (Range) null);
    }

    static FontInfo create(String fontName2, String encoding2, String alias2, Range range2) {
        FontCacheKey cacheKey = FontCacheKey.create(fontName2);
        FontProgramDescriptor descriptor2 = getFontNamesFromCache(cacheKey);
        if (descriptor2 == null) {
            descriptor2 = FontProgramDescriptorFactory.fetchDescriptor(fontName2);
            putFontNamesToCache(cacheKey, descriptor2);
        }
        if (descriptor2 != null) {
            return new FontInfo(fontName2, (byte[]) null, encoding2, descriptor2, range2, alias2);
        }
        return null;
    }

    static FontInfo create(byte[] fontProgram, String encoding2, String alias2, Range range2) {
        FontCacheKey cacheKey = FontCacheKey.create(fontProgram);
        FontProgramDescriptor descriptor2 = getFontNamesFromCache(cacheKey);
        if (descriptor2 == null) {
            descriptor2 = FontProgramDescriptorFactory.fetchDescriptor(fontProgram);
            putFontNamesToCache(cacheKey, descriptor2);
        }
        if (descriptor2 != null) {
            return new FontInfo((String) null, fontProgram, encoding2, descriptor2, range2, alias2);
        }
        return null;
    }

    public FontProgramDescriptor getDescriptor() {
        return this.descriptor;
    }

    public Range getFontUnicodeRange() {
        return this.range;
    }

    public String getFontName() {
        return this.fontName;
    }

    public byte[] getFontData() {
        return this.fontData;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getAlias() {
        return this.alias;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FontInfo)) {
            return false;
        }
        FontInfo that = (FontInfo) o;
        String str = this.fontName;
        if (str == null ? that.fontName == null : str.equals(that.fontName)) {
            if (this.range.equals(that.range) && Arrays.equals(this.fontData, that.fontData)) {
                String str2 = this.encoding;
                if (str2 != null) {
                    if (str2.equals(that.encoding)) {
                        return true;
                    }
                } else if (that.encoding == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        String name = this.descriptor.getFontName();
        if (name.length() <= 0) {
            return super.toString();
        }
        String str = this.encoding;
        if (str == null) {
            return name;
        }
        return MessageFormatUtil.format("{0}+{1}", name, str);
    }

    private static int calculateHashCode(String fontName2, byte[] bytes, String encoding2, Range range2) {
        int i = 0;
        int result = (((fontName2 != null ? fontName2.hashCode() : 0) * 31) + ArrayUtil.hashCode(bytes)) * 31;
        if (encoding2 != null) {
            i = encoding2.hashCode();
        }
        return ((result + i) * 31) + range2.hashCode();
    }

    private static FontProgramDescriptor getFontNamesFromCache(FontCacheKey key) {
        return fontNamesCache.get(key);
    }

    private static void putFontNamesToCache(FontCacheKey key, FontProgramDescriptor descriptor2) {
        if (descriptor2 != null) {
            fontNamesCache.put(key, descriptor2);
        }
    }
}

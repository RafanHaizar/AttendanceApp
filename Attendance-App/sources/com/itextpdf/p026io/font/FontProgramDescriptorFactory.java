package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.StandardFonts;
import com.itextpdf.p026io.font.woff2.Woff2Converter;
import java.io.IOException;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.FontProgramDescriptorFactory */
public final class FontProgramDescriptorFactory {
    private static boolean FETCH_CACHED_FIRST = true;

    public static FontProgramDescriptor fetchDescriptor(String fontName) {
        byte[] fontProgram;
        FontProgramDescriptor fontDescriptor;
        if (fontName == null || fontName.length() == 0) {
            return null;
        }
        String baseName = FontProgram.trimFontStyle(fontName);
        boolean isBuiltinFonts14 = StandardFonts.isStandardFont(fontName);
        boolean isCidFont = !isBuiltinFonts14 && FontCache.isPredefinedCidFont(baseName);
        if (FETCH_CACHED_FIRST && (fontDescriptor = fetchCachedDescriptor(fontName, (byte[]) null)) != null) {
            return fontDescriptor;
        }
        try {
            String fontNameLowerCase = baseName.toLowerCase();
            if (!isBuiltinFonts14 && !fontNameLowerCase.endsWith(".afm")) {
                if (!fontNameLowerCase.endsWith(".pfm")) {
                    if (isCidFont) {
                        return fetchCidFontDescriptor(fontName);
                    }
                    if (!fontNameLowerCase.endsWith(".ttf")) {
                        if (!fontNameLowerCase.endsWith(".otf")) {
                            if (!fontNameLowerCase.endsWith(".woff")) {
                                if (!fontNameLowerCase.endsWith(".woff2")) {
                                    return fetchTTCDescriptor(baseName);
                                }
                            }
                            if (fontNameLowerCase.endsWith(".woff")) {
                                fontProgram = WoffConverter.convert(FontProgramFactory.readFontBytesFromPath(baseName));
                            } else {
                                fontProgram = Woff2Converter.convert(FontProgramFactory.readFontBytesFromPath(baseName));
                            }
                            return fetchTrueTypeFontDescriptor(fontProgram);
                        }
                    }
                    return fetchTrueTypeFontDescriptor(fontName);
                }
            }
            return fetchType1FontDescriptor(fontName, (byte[]) null);
        } catch (Exception e) {
            return null;
        }
    }

    public static FontProgramDescriptor fetchDescriptor(byte[] fontProgram) {
        if (fontProgram == null || fontProgram.length == 0) {
            return null;
        }
        FontProgramDescriptor fontDescriptor = null;
        if (FETCH_CACHED_FIRST && (fontDescriptor = fetchCachedDescriptor((String) null, fontProgram)) != null) {
            return fontDescriptor;
        }
        try {
            fontDescriptor = fetchTrueTypeFontDescriptor(fontProgram);
        } catch (Exception e) {
        }
        if (fontDescriptor != null) {
            return fontDescriptor;
        }
        try {
            return fetchType1FontDescriptor((String) null, fontProgram);
        } catch (Exception e2) {
            return fontDescriptor;
        }
    }

    public static FontProgramDescriptor fetchDescriptor(FontProgram fontProgram) {
        return fetchDescriptorFromFontProgram(fontProgram);
    }

    private static FontProgramDescriptor fetchCachedDescriptor(String fontName, byte[] fontProgram) {
        FontCacheKey key;
        if (fontName != null) {
            key = FontCacheKey.create(fontName);
        } else {
            key = FontCacheKey.create(fontProgram);
        }
        FontProgram fontFound = FontCache.getFont(key);
        if (fontFound != null) {
            return fetchDescriptorFromFontProgram(fontFound);
        }
        return null;
    }

    private static FontProgramDescriptor fetchTTCDescriptor(String baseName) throws IOException {
        int ttcSplit = baseName.toLowerCase().indexOf(".ttc,");
        if (ttcSplit <= 0) {
            return null;
        }
        try {
            OpenTypeParser parser = new OpenTypeParser(baseName.substring(0, ttcSplit + 4), Integer.parseInt(baseName.substring(ttcSplit + 5)));
            FontProgramDescriptor descriptor = fetchOpenTypeFontDescriptor(parser);
            parser.close();
            return descriptor;
        } catch (NumberFormatException nfe) {
            throw new com.itextpdf.p026io.IOException(nfe.getMessage(), (Throwable) nfe);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.p026io.font.FontProgramDescriptor fetchTrueTypeFontDescriptor(java.lang.String r4) throws java.io.IOException {
        /*
            com.itextpdf.io.font.OpenTypeParser r0 = new com.itextpdf.io.font.OpenTypeParser
            r0.<init>((java.lang.String) r4)
            com.itextpdf.io.font.FontProgramDescriptor r1 = fetchOpenTypeFontDescriptor(r0)     // Catch:{ all -> 0x000d }
            r0.close()
            return r1
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0018:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.FontProgramDescriptorFactory.fetchTrueTypeFontDescriptor(java.lang.String):com.itextpdf.io.font.FontProgramDescriptor");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.p026io.font.FontProgramDescriptor fetchTrueTypeFontDescriptor(byte[] r4) throws java.io.IOException {
        /*
            com.itextpdf.io.font.OpenTypeParser r0 = new com.itextpdf.io.font.OpenTypeParser
            r0.<init>((byte[]) r4)
            com.itextpdf.io.font.FontProgramDescriptor r1 = fetchOpenTypeFontDescriptor(r0)     // Catch:{ all -> 0x000d }
            r0.close()
            return r1
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0018:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.FontProgramDescriptorFactory.fetchTrueTypeFontDescriptor(byte[]):com.itextpdf.io.font.FontProgramDescriptor");
    }

    private static FontProgramDescriptor fetchOpenTypeFontDescriptor(OpenTypeParser fontParser) throws IOException {
        fontParser.loadTables(false);
        return new FontProgramDescriptor(fontParser.getFontNames(), fontParser.getPostTable().italicAngle, fontParser.getPostTable().isFixedPitch);
    }

    private static FontProgramDescriptor fetchType1FontDescriptor(String fontName, byte[] afm) throws IOException {
        Type1Font fp = new Type1Font(fontName, (String) null, afm, (byte[]) null);
        return new FontProgramDescriptor(fp.getFontNames(), fp.getFontMetrics());
    }

    private static FontProgramDescriptor fetchCidFontDescriptor(String fontName) {
        CidFont font = new CidFont(fontName, (Set<String>) null);
        return new FontProgramDescriptor(font.getFontNames(), font.getFontMetrics());
    }

    private static FontProgramDescriptor fetchDescriptorFromFontProgram(FontProgram fontProgram) {
        return new FontProgramDescriptor(fontProgram.getFontNames(), fontProgram.getFontMetrics());
    }
}

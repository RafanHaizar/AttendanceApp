package com.itextpdf.p026io.font;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.FontProgramFactory */
public final class FontProgramFactory {
    private static boolean DEFAULT_CACHED = true;
    private static FontRegisterProvider fontRegisterProvider = new FontRegisterProvider();

    private FontProgramFactory() {
    }

    public static FontProgram createFont() throws IOException {
        return createFont("Helvetica");
    }

    public static FontProgram createFont(String fontProgram) throws IOException {
        return createFont(fontProgram, (byte[]) null, DEFAULT_CACHED);
    }

    public static FontProgram createFont(String fontProgram, boolean cached) throws IOException {
        return createFont(fontProgram, (byte[]) null, cached);
    }

    public static FontProgram createFont(byte[] fontProgram) throws IOException {
        return createFont((String) null, fontProgram, DEFAULT_CACHED);
    }

    public static FontProgram createFont(byte[] fontProgram, boolean cached) throws IOException {
        return createFont((String) null, fontProgram, cached);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        r5 = createFontCacheKey(r13, r14);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.p026io.font.FontProgram createFont(java.lang.String r13, byte[] r14, boolean r15) throws java.io.IOException {
        /*
            java.lang.String r0 = com.itextpdf.p026io.font.FontProgram.trimFontStyle(r13)
            boolean r1 = com.itextpdf.p026io.font.constants.StandardFonts.isStandardFont(r13)
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0014
            boolean r4 = com.itextpdf.p026io.font.FontCache.isPredefinedCidFont(r0)
            if (r4 == 0) goto L_0x0014
            r4 = 1
            goto L_0x0015
        L_0x0014:
            r4 = 0
        L_0x0015:
            r5 = 0
            if (r15 == 0) goto L_0x0023
            com.itextpdf.io.font.FontCacheKey r5 = createFontCacheKey(r13, r14)
            com.itextpdf.io.font.FontProgram r6 = com.itextpdf.p026io.font.FontCache.getFont((com.itextpdf.p026io.font.FontCacheKey) r5)
            if (r6 == 0) goto L_0x0023
            return r6
        L_0x0023:
            r6 = 0
            r7 = 0
            if (r13 != 0) goto L_0x0054
            if (r14 == 0) goto L_0x011f
            boolean r8 = com.itextpdf.p026io.font.WoffConverter.isWoffFont(r14)     // Catch:{ Exception -> 0x0047 }
            if (r8 == 0) goto L_0x0035
            byte[] r8 = com.itextpdf.p026io.font.WoffConverter.convert(r14)     // Catch:{ Exception -> 0x0047 }
            r14 = r8
            goto L_0x0040
        L_0x0035:
            boolean r8 = com.itextpdf.p026io.font.woff2.Woff2Converter.isWoff2Font(r14)     // Catch:{ Exception -> 0x0047 }
            if (r8 == 0) goto L_0x0040
            byte[] r8 = com.itextpdf.p026io.font.woff2.Woff2Converter.convert(r14)     // Catch:{ Exception -> 0x0047 }
            r14 = r8
        L_0x0040:
            com.itextpdf.io.font.TrueTypeFont r8 = new com.itextpdf.io.font.TrueTypeFont     // Catch:{ Exception -> 0x0047 }
            r8.<init>((byte[]) r14)     // Catch:{ Exception -> 0x0047 }
            r6 = r8
            goto L_0x0048
        L_0x0047:
            r8 = move-exception
        L_0x0048:
            if (r6 != 0) goto L_0x011f
            com.itextpdf.io.font.Type1Font r8 = new com.itextpdf.io.font.Type1Font     // Catch:{ Exception -> 0x0052 }
            r8.<init>(r7, r7, r14, r7)     // Catch:{ Exception -> 0x0052 }
            r6 = r8
        L_0x0050:
            goto L_0x011f
        L_0x0052:
            r7 = move-exception
            goto L_0x0050
        L_0x0054:
            r8 = 0
            r9 = 46
            int r9 = r0.lastIndexOf(r9)
            if (r9 <= 0) goto L_0x0065
            java.lang.String r10 = r0.substring(r9)
            java.lang.String r8 = r10.toLowerCase()
        L_0x0065:
            if (r1 != 0) goto L_0x0119
            java.lang.String r10 = ".afm"
            boolean r10 = r10.equals(r8)
            if (r10 != 0) goto L_0x0119
            java.lang.String r10 = ".pfm"
            boolean r10 = r10.equals(r8)
            if (r10 == 0) goto L_0x0079
            goto L_0x0119
        L_0x0079:
            if (r4 == 0) goto L_0x0087
            com.itextpdf.io.font.CidFont r7 = new com.itextpdf.io.font.CidFont
            java.util.Set r10 = com.itextpdf.p026io.font.FontCache.getCompatibleCmaps(r0)
            r7.<init>(r13, r10)
            r6 = r7
            goto L_0x011f
        L_0x0087:
            java.lang.String r7 = ".ttf"
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x0109
            java.lang.String r7 = ".otf"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0099
            goto L_0x0109
        L_0x0099:
            java.lang.String r7 = ".woff"
            boolean r10 = r7.equals(r8)
            if (r10 != 0) goto L_0x00d8
            java.lang.String r10 = ".woff2"
            boolean r10 = r10.equals(r8)
            if (r10 == 0) goto L_0x00aa
            goto L_0x00d8
        L_0x00aa:
            java.lang.String r7 = r0.toLowerCase()
            java.lang.String r10 = ".ttc,"
            int r7 = r7.indexOf(r10)
            if (r7 <= 0) goto L_0x011f
            int r10 = r7 + 4
            java.lang.String r10 = r0.substring(r3, r10)     // Catch:{ NumberFormatException -> 0x00cd }
            int r11 = r7 + 5
            java.lang.String r11 = r0.substring(r11)     // Catch:{ NumberFormatException -> 0x00cd }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ NumberFormatException -> 0x00cd }
            com.itextpdf.io.font.TrueTypeFont r12 = new com.itextpdf.io.font.TrueTypeFont     // Catch:{ NumberFormatException -> 0x00cd }
            r12.<init>((java.lang.String) r10, (int) r11)     // Catch:{ NumberFormatException -> 0x00cd }
            r6 = r12
            goto L_0x011f
        L_0x00cd:
            r2 = move-exception
            com.itextpdf.io.IOException r3 = new com.itextpdf.io.IOException
            java.lang.String r10 = r2.getMessage()
            r3.<init>((java.lang.String) r10, (java.lang.Throwable) r2)
            throw r3
        L_0x00d8:
            if (r14 != 0) goto L_0x00de
            byte[] r14 = readFontBytesFromPath(r0)
        L_0x00de:
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00f3
            byte[] r7 = com.itextpdf.p026io.font.WoffConverter.convert(r14)     // Catch:{ IllegalArgumentException -> 0x00ea }
            r14 = r7
            goto L_0x00f9
        L_0x00ea:
            r2 = move-exception
            com.itextpdf.io.IOException r3 = new com.itextpdf.io.IOException
            java.lang.String r7 = "Invalid WOFF font file."
            r3.<init>((java.lang.String) r7, (java.lang.Throwable) r2)
            throw r3
        L_0x00f3:
            byte[] r7 = com.itextpdf.p026io.font.woff2.Woff2Converter.convert(r14)     // Catch:{ FontCompressionException -> 0x0100 }
            r14 = r7
        L_0x00f9:
            com.itextpdf.io.font.TrueTypeFont r7 = new com.itextpdf.io.font.TrueTypeFont
            r7.<init>((byte[]) r14)
            r6 = r7
            goto L_0x011f
        L_0x0100:
            r2 = move-exception
            com.itextpdf.io.IOException r3 = new com.itextpdf.io.IOException
            java.lang.String r7 = "Invalid WOFF2 font file."
            r3.<init>((java.lang.String) r7, (java.lang.Throwable) r2)
            throw r3
        L_0x0109:
            if (r14 == 0) goto L_0x0112
            com.itextpdf.io.font.TrueTypeFont r7 = new com.itextpdf.io.font.TrueTypeFont
            r7.<init>((byte[]) r14)
            r6 = r7
            goto L_0x011f
        L_0x0112:
            com.itextpdf.io.font.TrueTypeFont r7 = new com.itextpdf.io.font.TrueTypeFont
            r7.<init>((java.lang.String) r13)
            r6 = r7
            goto L_0x011f
        L_0x0119:
            com.itextpdf.io.font.Type1Font r10 = new com.itextpdf.io.font.Type1Font
            r10.<init>(r13, r7, r7, r7)
            r6 = r10
        L_0x011f:
            if (r6 != 0) goto L_0x013b
            if (r13 == 0) goto L_0x0133
            com.itextpdf.io.IOException r7 = new com.itextpdf.io.IOException
            java.lang.String r8 = "Type of font {0} is not recognized."
            r7.<init>((java.lang.String) r8)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r3] = r13
            com.itextpdf.io.IOException r2 = r7.setMessageParams(r2)
            throw r2
        L_0x0133:
            com.itextpdf.io.IOException r2 = new com.itextpdf.io.IOException
            java.lang.String r3 = "Type of font is not recognized."
            r2.<init>((java.lang.String) r3)
            throw r2
        L_0x013b:
            if (r15 == 0) goto L_0x0142
            com.itextpdf.io.font.FontProgram r2 = com.itextpdf.p026io.font.FontCache.saveFont((com.itextpdf.p026io.font.FontProgram) r6, (com.itextpdf.p026io.font.FontCacheKey) r5)
            goto L_0x0143
        L_0x0142:
            r2 = r6
        L_0x0143:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.FontProgramFactory.createFont(java.lang.String, byte[], boolean):com.itextpdf.io.font.FontProgram");
    }

    public static FontProgram createType1Font(byte[] afm, byte[] pfb) throws IOException {
        return createType1Font(afm, pfb, DEFAULT_CACHED);
    }

    public static FontProgram createType1Font(byte[] afm, byte[] pfb, boolean cached) throws IOException {
        return createType1Font((String) null, (String) null, afm, pfb, cached);
    }

    public static FontProgram createType1Font(String metricsPath, String binaryPath) throws IOException {
        return createType1Font(metricsPath, binaryPath, DEFAULT_CACHED);
    }

    public static FontProgram createType1Font(String metricsPath, String binaryPath, boolean cached) throws IOException {
        return createType1Font(metricsPath, binaryPath, (byte[]) null, (byte[]) null, cached);
    }

    public static FontProgram createFont(String ttc, int ttcIndex, boolean cached) throws IOException {
        FontProgram fontFound;
        FontCacheKey fontCacheKey = FontCacheKey.create(ttc, ttcIndex);
        if (cached && (fontFound = FontCache.getFont(fontCacheKey)) != null) {
            return fontFound;
        }
        FontProgram fontBuilt = new TrueTypeFont(ttc, ttcIndex);
        return cached ? FontCache.saveFont(fontBuilt, fontCacheKey) : fontBuilt;
    }

    public static FontProgram createFont(byte[] ttc, int ttcIndex, boolean cached) throws IOException {
        FontProgram fontFound;
        FontCacheKey fontKey = FontCacheKey.create(ttc, ttcIndex);
        if (cached && (fontFound = FontCache.getFont(fontKey)) != null) {
            return fontFound;
        }
        FontProgram fontBuilt = new TrueTypeFont(ttc, ttcIndex);
        return cached ? FontCache.saveFont(fontBuilt, fontKey) : fontBuilt;
    }

    public static FontProgram createRegisteredFont(String fontName, int style, boolean cached) throws IOException {
        return fontRegisterProvider.getFont(fontName, style, cached);
    }

    public static FontProgram createRegisteredFont(String fontName, int style) throws IOException {
        return fontRegisterProvider.getFont(fontName, style);
    }

    public static FontProgram createRegisteredFont(String fontName) throws IOException {
        return fontRegisterProvider.getFont(fontName, -1);
    }

    public static void registerFontFamily(String familyName, String fullName, String path) {
        fontRegisterProvider.registerFontFamily(familyName, fullName, path);
    }

    public static void registerFont(String path) {
        registerFont(path, (String) null);
    }

    public static void registerFont(String path, String alias) {
        fontRegisterProvider.registerFont(path, alias);
    }

    public static int registerFontDirectory(String dir) {
        return fontRegisterProvider.registerFontDirectory(dir);
    }

    public static int registerSystemFontDirectories() {
        return fontRegisterProvider.registerSystemFontDirectories();
    }

    public static Set<String> getRegisteredFonts() {
        return fontRegisterProvider.getRegisteredFonts();
    }

    public static Set<String> getRegisteredFontFamilies() {
        return fontRegisterProvider.getRegisteredFontFamilies();
    }

    public static boolean isRegisteredFont(String fontName) {
        return fontRegisterProvider.isRegisteredFont(fontName);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r0 = createFontCacheKey(r3, r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.p026io.font.FontProgram createType1Font(java.lang.String r3, java.lang.String r4, byte[] r5, byte[] r6, boolean r7) throws java.io.IOException {
        /*
            r0 = 0
            if (r7 == 0) goto L_0x000e
            com.itextpdf.io.font.FontCacheKey r0 = createFontCacheKey(r3, r5)
            com.itextpdf.io.font.FontProgram r1 = com.itextpdf.p026io.font.FontCache.getFont((com.itextpdf.p026io.font.FontCacheKey) r0)
            if (r1 == 0) goto L_0x000e
            return r1
        L_0x000e:
            com.itextpdf.io.font.Type1Font r1 = new com.itextpdf.io.font.Type1Font
            r1.<init>(r3, r4, r5, r6)
            if (r7 == 0) goto L_0x001a
            com.itextpdf.io.font.FontProgram r2 = com.itextpdf.p026io.font.FontCache.saveFont((com.itextpdf.p026io.font.FontProgram) r1, (com.itextpdf.p026io.font.FontCacheKey) r0)
            goto L_0x001b
        L_0x001a:
            r2 = r1
        L_0x001b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.FontProgramFactory.createType1Font(java.lang.String, java.lang.String, byte[], byte[], boolean):com.itextpdf.io.font.FontProgram");
    }

    private static FontCacheKey createFontCacheKey(String name, byte[] fontProgram) {
        if (name != null) {
            return FontCacheKey.create(name);
        }
        return FontCacheKey.create(fontProgram);
    }

    public static void clearRegisteredFonts() {
        fontRegisterProvider.clearRegisteredFonts();
    }

    public static void clearRegisteredFontFamilies() {
        fontRegisterProvider.clearRegisteredFontFamilies();
    }

    static byte[] readFontBytesFromPath(String path) throws IOException {
        RandomAccessFileOrArray raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(path));
        int bufLen = (int) raf.length();
        if (((long) bufLen) >= raf.length()) {
            byte[] buf = new byte[bufLen];
            raf.readFully(buf);
            return buf;
        }
        throw new com.itextpdf.p026io.IOException(MessageFormatUtil.format("Source data from \"{0}\" is bigger than byte array can hold.", path));
    }
}

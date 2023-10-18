package com.itextpdf.layout.font;

import com.itextpdf.kernel.font.Type3Font;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.util.FileUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public final class FontSet {
    private static final AtomicLong lastId = new AtomicLong();
    private final Map<FontInfo, FontProgram> fontPrograms = new HashMap();
    private final Set<FontInfo> fonts = new LinkedHashSet();

    /* renamed from: id */
    private final long f1523id = lastId.incrementAndGet();

    public int addDirectory(String dir, boolean scanSubdirectories) {
        int count = 0;
        String[] files = FileUtil.listFilesInDirectory(dir, scanSubdirectories);
        if (files == null) {
            return 0;
        }
        for (String file : files) {
            try {
                String suffix = file.length() < 4 ? null : file.substring(file.length() - 4).toLowerCase();
                if (!".afm".equals(suffix)) {
                    if (!".pfm".equals(suffix)) {
                        if ((".ttf".equals(suffix) || ".otf".equals(suffix) || ".ttc".equals(suffix)) && addFont(file)) {
                            count++;
                        }
                    }
                }
                if (FileUtil.fileExists(file.substring(0, file.length() - 4) + ".pfb") && addFont(file)) {
                    count++;
                }
            } catch (Exception e) {
            }
        }
        return count;
    }

    public int addDirectory(String dir) {
        return addDirectory(dir, false);
    }

    public boolean addFont(FontProgram fontProgram, String encoding, String alias, Range unicodeRange) {
        if (fontProgram == null) {
            return false;
        }
        if (fontProgram instanceof Type3Font) {
            LoggerFactory.getLogger((Class<?>) FontSet.class).error(LogMessageConstant.TYPE3_FONT_CANNOT_BE_ADDED);
            return false;
        }
        FontInfo fi = FontInfo.create(fontProgram, encoding, alias, unicodeRange);
        if (!addFont(fi)) {
            return false;
        }
        this.fontPrograms.put(fi, fontProgram);
        return true;
    }

    public boolean addFont(FontProgram fontProgram, String encoding, String alias) {
        return addFont(fontProgram, encoding, alias, (Range) null);
    }

    public boolean addFont(FontProgram fontProgram, String encoding) {
        return addFont(fontProgram, encoding, (String) null);
    }

    public boolean addFont(String fontPath, String encoding, String alias, Range unicodeRange) {
        return addFont(FontInfo.create(fontPath, encoding, alias, unicodeRange));
    }

    public boolean addFont(String fontPath, String encoding, String alias) {
        return addFont(fontPath, encoding, alias, (Range) null);
    }

    public boolean addFont(String fontPath, String encoding) {
        return addFont(FontInfo.create(fontPath, encoding, (String) null, (Range) null));
    }

    public boolean addFont(byte[] fontData, String encoding, String alias, Range unicodeRange) {
        return addFont(FontInfo.create(fontData, encoding, alias, unicodeRange));
    }

    public boolean addFont(byte[] fontData, String encoding, String alias) {
        return addFont(fontData, encoding, alias, (Range) null);
    }

    public boolean addFont(byte[] fontData, String encoding) {
        return addFont(FontInfo.create(fontData, encoding, (String) null, (Range) null));
    }

    public boolean addFont(String fontPath) {
        return addFont(fontPath, (String) null, (String) null);
    }

    public boolean addFont(byte[] fontData) {
        return addFont(fontData, (String) null, (String) null);
    }

    public boolean addFont(FontInfo fontInfo, String alias, Range unicodeRange) {
        return addFont(FontInfo.create(fontInfo, alias, unicodeRange));
    }

    public boolean addFont(FontInfo fontInfo, String alias) {
        return addFont(fontInfo, alias, (Range) null);
    }

    public final boolean addFont(FontInfo fontInfo) {
        if (fontInfo == null || this.fonts.contains(fontInfo)) {
            return false;
        }
        this.fonts.add(fontInfo);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean contains(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x0043
            int r1 = r5.length()
            if (r1 != 0) goto L_0x000a
            goto L_0x0043
        L_0x000a:
            java.lang.String r5 = r5.toLowerCase()
            java.util.Collection r1 = r4.getFonts()
            java.util.Iterator r1 = r1.iterator()
        L_0x0016:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0042
            java.lang.Object r2 = r1.next()
            com.itextpdf.layout.font.FontInfo r2 = (com.itextpdf.layout.font.FontInfo) r2
            com.itextpdf.io.font.FontProgramDescriptor r3 = r2.getDescriptor()
            java.lang.String r3 = r3.getFullNameLowerCase()
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0040
            com.itextpdf.io.font.FontProgramDescriptor r3 = r2.getDescriptor()
            java.lang.String r3 = r3.getFontNameLowerCase()
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003f
            goto L_0x0040
        L_0x003f:
            goto L_0x0016
        L_0x0040:
            r0 = 1
            return r0
        L_0x0042:
            return r0
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.font.FontSet.contains(java.lang.String):boolean");
    }

    public Collection<FontInfo> get(String fontName) {
        if (fontName == null || fontName.length() == 0) {
            return Collections.emptyList();
        }
        String fontName2 = fontName.toLowerCase();
        List<FontInfo> list = new ArrayList<>();
        for (FontInfo fi : getFonts()) {
            if (fontName2.equals(fi.getDescriptor().getFullNameLowerCase()) || fontName2.equals(fi.getDescriptor().getFontNameLowerCase())) {
                list.add(fi);
            }
        }
        return list;
    }

    public Collection<FontInfo> getFonts() {
        return getFonts((FontSet) null);
    }

    public Collection<FontInfo> getFonts(FontSet additionalFonts) {
        return new FontSetCollection(this.fonts, additionalFonts != null ? additionalFonts.fonts : null);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.fonts.size();
    }

    /* access modifiers changed from: package-private */
    public long getId() {
        return this.f1523id;
    }

    /* access modifiers changed from: package-private */
    public FontProgram getFontProgram(FontInfo fontInfo) {
        return this.fontPrograms.get(fontInfo);
    }
}

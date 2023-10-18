package com.itextpdf.p026io.font;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.constants.StandardFonts;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.Type1Font */
public class Type1Font extends FontProgram {
    private static final int[] PFB_TYPES = {1, 2, 1};
    private static final long serialVersionUID = -1078208220942939920L;
    private String characterSet;
    private Type1Parser fontParser;
    private byte[] fontStreamBytes;
    private int[] fontStreamLengths;
    private Map<Long, Integer> kernPairs;

    protected static Type1Font createStandardFont(String name) throws IOException {
        if (StandardFonts.isStandardFont(name)) {
            return new Type1Font(name, (String) null, (byte[]) null, (byte[]) null);
        }
        throw new com.itextpdf.p026io.IOException("{0} is not a standard type1 font.").setMessageParams(name);
    }

    protected Type1Font() {
        this.kernPairs = new HashMap();
        this.fontNames = new FontNames();
    }

    protected Type1Font(String metricsPath, String binaryPath, byte[] afm, byte[] pfb) throws IOException {
        this();
        this.fontParser = new Type1Parser(metricsPath, binaryPath, afm, pfb);
        process();
    }

    protected Type1Font(String baseFont) {
        this();
        getFontNames().setFontName(baseFont);
    }

    public boolean isBuiltInFont() {
        Type1Parser type1Parser = this.fontParser;
        return type1Parser != null && type1Parser.isBuiltInFont();
    }

    public int getPdfFontFlags() {
        int flags = 0;
        if (this.fontMetrics.isFixedPitch()) {
            flags = 0 | 1;
        }
        int flags2 = flags | (isFontSpecific() ? 4 : 32);
        if (this.fontMetrics.getItalicAngle() < 0.0f) {
            flags2 |= 64;
        }
        if (this.fontNames.getFontName().contains("Caps") || this.fontNames.getFontName().endsWith("SC")) {
            flags2 |= 131072;
        }
        if (this.fontNames.isBold() || this.fontNames.getFontWeight() > 500) {
            return flags2 | 262144;
        }
        return flags2;
    }

    public String getCharacterSet() {
        return this.characterSet;
    }

    public boolean hasKernPairs() {
        return this.kernPairs.size() > 0;
    }

    public int getKerning(Glyph first, Glyph second) {
        if (!first.hasValidUnicode() || !second.hasValidUnicode()) {
            return 0;
        }
        long record = (((long) first.getUnicode()) << 32) + ((long) second.getUnicode());
        if (this.kernPairs.containsKey(Long.valueOf(record))) {
            return this.kernPairs.get(Long.valueOf(record)).intValue();
        }
        return 0;
    }

    public boolean setKerning(int first, int second, int kern) {
        this.kernPairs.put(Long.valueOf((((long) first) << 32) + ((long) second)), Integer.valueOf(kern));
        return true;
    }

    public Glyph getGlyph(String name) {
        int unicode = AdobeGlyphList.nameToUnicode(name);
        if (unicode != -1) {
            return getGlyph(unicode);
        }
        return null;
    }

    public byte[] getFontStreamBytes() {
        Class<Type1Font> cls = Type1Font.class;
        if (this.fontParser.isBuiltInFont()) {
            return null;
        }
        byte[] bArr = this.fontStreamBytes;
        if (bArr != null) {
            return bArr;
        }
        RandomAccessFileOrArray raf = null;
        try {
            raf = this.fontParser.getPostscriptBinary();
            this.fontStreamBytes = new byte[(((int) raf.length()) - 18)];
            this.fontStreamLengths = new int[3];
            int bytePtr = 0;
            int k = 0;
            while (k < 3) {
                if (raf.read() != 128) {
                    LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.START_MARKER_MISSING_IN_PFB_FILE);
                    if (raf != null) {
                        try {
                            raf.close();
                        } catch (Exception e) {
                        }
                    }
                    return null;
                } else if (raf.read() != PFB_TYPES[k]) {
                    LoggerFactory.getLogger((Class<?>) cls).error("incorrect.segment.type.in.pfb.file");
                    if (raf != null) {
                        try {
                            raf.close();
                        } catch (Exception e2) {
                        }
                    }
                    return null;
                } else {
                    int size = raf.read() + (raf.read() << 8) + (raf.read() << 16) + (raf.read() << 24);
                    this.fontStreamLengths[k] = size;
                    while (size != 0) {
                        int got = raf.read(this.fontStreamBytes, bytePtr, size);
                        if (got < 0) {
                            LoggerFactory.getLogger((Class<?>) cls).error("premature.end.in.pfb.file");
                            if (raf != null) {
                                try {
                                    raf.close();
                                } catch (Exception e3) {
                                }
                            }
                            return null;
                        }
                        bytePtr += got;
                        size -= got;
                    }
                    k++;
                }
            }
            byte[] bArr2 = this.fontStreamBytes;
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e4) {
                }
            }
            return bArr2;
        } catch (Exception e5) {
            LoggerFactory.getLogger((Class<?>) cls).error("type1.font.file.exception");
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e6) {
                }
            }
            return null;
        } catch (Throwable th) {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e7) {
                }
            }
            throw th;
        }
    }

    public int[] getFontStreamLengths() {
        return this.fontStreamLengths;
    }

    public boolean isBuiltWith(String fontProgram) {
        return Objects.equals(this.fontParser.getAfmPath(), fontProgram);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void process() throws java.io.IOException {
        /*
            r19 = this;
            r0 = r19
            com.itextpdf.io.font.Type1Parser r1 = r0.fontParser
            com.itextpdf.io.source.RandomAccessFileOrArray r1 = r1.getMetricsFile()
            r2 = 0
        L_0x0009:
            r3 = 4
            r4 = 3
            r5 = 2
            r7 = 0
            r8 = 1
            if (r2 != 0) goto L_0x0246
            java.lang.String r9 = r1.readLine()
            r10 = r9
            if (r9 == 0) goto L_0x0246
            java.util.StringTokenizer r9 = new java.util.StringTokenizer
            java.lang.String r11 = " ,\n\r\t\f"
            r9.<init>(r10, r11)
            boolean r11 = r9.hasMoreTokens()
            if (r11 != 0) goto L_0x0025
            goto L_0x0009
        L_0x0025:
            java.lang.String r11 = r9.nextToken()
            int r12 = r11.hashCode()
            switch(r12) {
                case -2037328797: goto L_0x00ee;
                case -1707725160: goto L_0x00e4;
                case -1587834632: goto L_0x00d9;
                case -1502948305: goto L_0x00cf;
                case -1346249825: goto L_0x00c4;
                case -1278893927: goto L_0x00ba;
                case -812484743: goto L_0x00af;
                case -802988361: goto L_0x00a4;
                case -766799081: goto L_0x0099;
                case -265632490: goto L_0x008f;
                case 80206418: goto L_0x0083;
                case 80206852: goto L_0x0077;
                case 425555957: goto L_0x006b;
                case 429700888: goto L_0x0060;
                case 430088090: goto L_0x0055;
                case 1395496410: goto L_0x004a;
                case 1672376043: goto L_0x003e;
                case 1887629864: goto L_0x0032;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x00f8
        L_0x0032:
            java.lang.String r12 = "UnderlineThickness"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 9
            goto L_0x00f9
        L_0x003e:
            java.lang.String r12 = "StartCharMetrics"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 17
            goto L_0x00f9
        L_0x004a:
            java.lang.String r12 = "FullName"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 1
            goto L_0x00f9
        L_0x0055:
            java.lang.String r12 = "FontName"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 0
            goto L_0x00f9
        L_0x0060:
            java.lang.String r12 = "FontBBox"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 7
            goto L_0x00f9
        L_0x006b:
            java.lang.String r12 = "UnderlinePosition"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 8
            goto L_0x00f9
        L_0x0077:
            java.lang.String r12 = "StdVW"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 16
            goto L_0x00f9
        L_0x0083:
            java.lang.String r12 = "StdHW"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 15
            goto L_0x00f9
        L_0x008f:
            java.lang.String r12 = "IsFixedPitch"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 5
            goto L_0x00f9
        L_0x0099:
            java.lang.String r12 = "Ascender"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 13
            goto L_0x00f9
        L_0x00a4:
            java.lang.String r12 = "Descender"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 14
            goto L_0x00f9
        L_0x00af:
            java.lang.String r12 = "CapHeight"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 11
            goto L_0x00f9
        L_0x00ba:
            java.lang.String r12 = "CharacterSet"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 6
            goto L_0x00f9
        L_0x00c4:
            java.lang.String r12 = "XHeight"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 12
            goto L_0x00f9
        L_0x00cf:
            java.lang.String r12 = "FamilyName"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 2
            goto L_0x00f9
        L_0x00d9:
            java.lang.String r12 = "EncodingScheme"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 10
            goto L_0x00f9
        L_0x00e4:
            java.lang.String r12 = "Weight"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 3
            goto L_0x00f9
        L_0x00ee:
            java.lang.String r12 = "ItalicAngle"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x0030
            r6 = 4
            goto L_0x00f9
        L_0x00f8:
            r6 = -1
        L_0x00f9:
            java.lang.String r12 = ""
            java.lang.String r13 = "ÿ"
            switch(r6) {
                case 0: goto L_0x0236;
                case 1: goto L_0x021a;
                case 2: goto L_0x01fe;
                case 3: goto L_0x01ec;
                case 4: goto L_0x01de;
                case 5: goto L_0x01cd;
                case 6: goto L_0x01c1;
                case 7: goto L_0x0196;
                case 8: goto L_0x0186;
                case 9: goto L_0x0176;
                case 10: goto L_0x0166;
                case 11: goto L_0x0156;
                case 12: goto L_0x0146;
                case 13: goto L_0x0136;
                case 14: goto L_0x0126;
                case 15: goto L_0x0116;
                case 16: goto L_0x0106;
                case 17: goto L_0x0103;
                default: goto L_0x0101;
            }
        L_0x0101:
            goto L_0x0244
        L_0x0103:
            r2 = 1
            goto L_0x0244
        L_0x0106:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setStemV(r4)
            goto L_0x0244
        L_0x0116:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setStemH(r4)
            goto L_0x0244
        L_0x0126:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setTypoDescender(r4)
            goto L_0x0244
        L_0x0136:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setTypoAscender(r4)
            goto L_0x0244
        L_0x0146:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setXHeight(r4)
            goto L_0x0244
        L_0x0156:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setCapHeight(r4)
            goto L_0x0244
        L_0x0166:
            java.lang.String r3 = r9.nextToken(r13)
            java.lang.String r3 = r3.substring(r8)
            java.lang.String r3 = r3.trim()
            r0.encodingScheme = r3
            goto L_0x0244
        L_0x0176:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setUnderlineThickness(r4)
            goto L_0x0244
        L_0x0186:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            r3.setUnderlinePosition(r4)
            goto L_0x0244
        L_0x0196:
            java.lang.String r3 = r9.nextToken()
            float r3 = java.lang.Float.parseFloat(r3)
            int r3 = (int) r3
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            int r4 = (int) r4
            java.lang.String r5 = r9.nextToken()
            float r5 = java.lang.Float.parseFloat(r5)
            int r5 = (int) r5
            java.lang.String r6 = r9.nextToken()
            float r6 = java.lang.Float.parseFloat(r6)
            int r6 = (int) r6
            com.itextpdf.io.font.FontMetrics r7 = r0.fontMetrics
            r7.setBbox(r3, r4, r5, r6)
            goto L_0x0244
        L_0x01c1:
            java.lang.String r3 = r9.nextToken(r13)
            java.lang.String r3 = r3.substring(r8)
            r0.characterSet = r3
            goto L_0x0244
        L_0x01cd:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            java.lang.String r5 = "true"
            boolean r4 = r4.equals(r5)
            r3.setIsFixedPitch(r4)
            goto L_0x0244
        L_0x01de:
            com.itextpdf.io.font.FontMetrics r3 = r0.fontMetrics
            java.lang.String r4 = r9.nextToken()
            float r4 = java.lang.Float.parseFloat(r4)
            r3.setItalicAngle(r4)
            goto L_0x0244
        L_0x01ec:
            com.itextpdf.io.font.FontNames r3 = r0.fontNames
            java.lang.String r4 = r9.nextToken(r13)
            java.lang.String r4 = r4.substring(r8)
            int r4 = com.itextpdf.p026io.font.constants.FontWeights.fromType1FontWeight(r4)
            r3.setFontWeight(r4)
            goto L_0x0244
        L_0x01fe:
            java.lang.String r6 = r9.nextToken(r13)
            java.lang.String r6 = r6.substring(r8)
            com.itextpdf.io.font.FontNames r13 = r0.fontNames
            java.lang.String[][] r14 = new java.lang.String[r8][]
            java.lang.String[] r3 = new java.lang.String[r3]
            r3[r7] = r12
            r3[r8] = r12
            r3[r5] = r12
            r3[r4] = r6
            r14[r7] = r3
            r13.setFamilyName((java.lang.String[][]) r14)
            goto L_0x0244
        L_0x021a:
            java.lang.String r6 = r9.nextToken(r13)
            java.lang.String r6 = r6.substring(r8)
            com.itextpdf.io.font.FontNames r13 = r0.fontNames
            java.lang.String[][] r14 = new java.lang.String[r8][]
            java.lang.String[] r3 = new java.lang.String[r3]
            r3[r7] = r12
            r3[r8] = r12
            r3[r5] = r12
            r3[r4] = r6
            r14[r7] = r3
            r13.setFullName((java.lang.String[][]) r14)
            goto L_0x0244
        L_0x0236:
            com.itextpdf.io.font.FontNames r3 = r0.fontNames
            java.lang.String r4 = r9.nextToken(r13)
            java.lang.String r4 = r4.substring(r8)
            r3.setFontName(r4)
        L_0x0244:
            goto L_0x0009
        L_0x0246:
            if (r2 != 0) goto L_0x026a
            com.itextpdf.io.font.Type1Parser r3 = r0.fontParser
            java.lang.String r3 = r3.getAfmPath()
            if (r3 == 0) goto L_0x0261
            com.itextpdf.io.IOException r4 = new com.itextpdf.io.IOException
            java.lang.String r5 = "startcharmetrics is missing in {0}."
            r4.<init>((java.lang.String) r5)
            java.lang.Object[] r5 = new java.lang.Object[r8]
            r5[r7] = r3
            com.itextpdf.io.IOException r4 = r4.setMessageParams(r5)
            throw r4
        L_0x0261:
            com.itextpdf.io.IOException r4 = new com.itextpdf.io.IOException
            java.lang.String r5 = "startcharmetrics is missing in the metrics file."
            r4.<init>((java.lang.String) r5)
            throw r4
        L_0x026a:
            r0.avgWidth = r7
            r9 = 0
        L_0x026d:
            java.lang.String r10 = r1.readLine()
            r11 = r10
            if (r10 == 0) goto L_0x0380
            java.util.StringTokenizer r10 = new java.util.StringTokenizer
            r10.<init>(r11)
            boolean r12 = r10.hasMoreTokens()
            if (r12 != 0) goto L_0x0280
            goto L_0x026d
        L_0x0280:
            java.lang.String r12 = r10.nextToken()
            java.lang.String r13 = "EndCharMetrics"
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x028f
            r2 = 0
            goto L_0x0380
        L_0x028f:
            r13 = -1
            r14 = 250(0xfa, float:3.5E-43)
            java.lang.String r15 = ""
            r16 = 0
            java.util.StringTokenizer r6 = new java.util.StringTokenizer
            java.lang.String r4 = ";"
            r6.<init>(r11, r4)
            r4 = r6
            r6 = r16
        L_0x02a0:
            boolean r10 = r4.hasMoreTokens()
            if (r10 == 0) goto L_0x034e
            java.util.StringTokenizer r10 = new java.util.StringTokenizer
            java.lang.String r5 = r4.nextToken()
            r10.<init>(r5)
            r5 = r10
            boolean r10 = r5.hasMoreTokens()
            if (r10 != 0) goto L_0x02b8
            r5 = 2
            goto L_0x02a0
        L_0x02b8:
            java.lang.String r12 = r5.nextToken()
            int r10 = r12.hashCode()
            switch(r10) {
                case 66: goto L_0x02e2;
                case 67: goto L_0x02d8;
                case 78: goto L_0x02ce;
                case 2785: goto L_0x02c4;
                default: goto L_0x02c3;
            }
        L_0x02c3:
            goto L_0x02ec
        L_0x02c4:
            java.lang.String r10 = "WX"
            boolean r10 = r12.equals(r10)
            if (r10 == 0) goto L_0x02c3
            r10 = 1
            goto L_0x02ed
        L_0x02ce:
            java.lang.String r10 = "N"
            boolean r10 = r12.equals(r10)
            if (r10 == 0) goto L_0x02c3
            r10 = 2
            goto L_0x02ed
        L_0x02d8:
            java.lang.String r10 = "C"
            boolean r10 = r12.equals(r10)
            if (r10 == 0) goto L_0x02c3
            r10 = 0
            goto L_0x02ed
        L_0x02e2:
            java.lang.String r10 = "B"
            boolean r10 = r12.equals(r10)
            if (r10 == 0) goto L_0x02c3
            r10 = 3
            goto L_0x02ed
        L_0x02ec:
            r10 = -1
        L_0x02ed:
            switch(r10) {
                case 0: goto L_0x033e;
                case 1: goto L_0x032f;
                case 2: goto L_0x0325;
                case 3: goto L_0x02f5;
                default: goto L_0x02f0;
            }
        L_0x02f0:
            r16 = 2
            r17 = 3
            goto L_0x034b
        L_0x02f5:
            int[] r10 = new int[r3]
            java.lang.String r18 = r5.nextToken()
            int r18 = java.lang.Integer.parseInt(r18)
            r10[r7] = r18
            java.lang.String r18 = r5.nextToken()
            int r18 = java.lang.Integer.parseInt(r18)
            r10[r8] = r18
            java.lang.String r18 = r5.nextToken()
            int r18 = java.lang.Integer.parseInt(r18)
            r16 = 2
            r10[r16] = r18
            java.lang.String r18 = r5.nextToken()
            int r18 = java.lang.Integer.parseInt(r18)
            r17 = 3
            r10[r17] = r18
            r6 = r10
            goto L_0x034b
        L_0x0325:
            r16 = 2
            r17 = 3
            java.lang.String r10 = r5.nextToken()
            r15 = r10
            goto L_0x034b
        L_0x032f:
            r16 = 2
            r17 = 3
            java.lang.String r10 = r5.nextToken()
            float r10 = java.lang.Float.parseFloat(r10)
            int r10 = (int) r10
            r14 = r10
            goto L_0x034b
        L_0x033e:
            r16 = 2
            r17 = 3
            java.lang.String r10 = r5.nextToken()
            int r10 = java.lang.Integer.parseInt(r10)
            r13 = r10
        L_0x034b:
            r5 = 2
            goto L_0x02a0
        L_0x034e:
            r16 = 2
            r17 = 3
            int r5 = com.itextpdf.p026io.font.AdobeGlyphList.nameToUnicode(r15)
            com.itextpdf.io.font.otf.Glyph r10 = new com.itextpdf.io.font.otf.Glyph
            r10.<init>(r13, r14, r5, r6)
            if (r13 < 0) goto L_0x0366
            java.util.Map r3 = r0.codeToGlyph
            java.lang.Integer r7 = java.lang.Integer.valueOf(r13)
            r3.put(r7, r10)
        L_0x0366:
            r3 = -1
            if (r5 == r3) goto L_0x0372
            java.util.Map r3 = r0.unicodeToGlyph
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)
            r3.put(r7, r10)
        L_0x0372:
            int r3 = r0.avgWidth
            int r3 = r3 + r14
            r0.avgWidth = r3
            int r9 = r9 + 1
            r3 = 4
            r4 = 3
            r5 = 2
            r7 = 0
            goto L_0x026d
        L_0x0380:
            if (r9 == 0) goto L_0x0387
            int r3 = r0.avgWidth
            int r3 = r3 / r9
            r0.avgWidth = r3
        L_0x0387:
            if (r2 == 0) goto L_0x03aa
            com.itextpdf.io.font.Type1Parser r3 = r0.fontParser
            java.lang.String r3 = r3.getAfmPath()
            if (r3 == 0) goto L_0x03a2
            com.itextpdf.io.IOException r4 = new com.itextpdf.io.IOException
            java.lang.String r5 = "endcharmetrics is missing in {0}."
            r4.<init>((java.lang.String) r5)
            java.lang.Object[] r5 = new java.lang.Object[r8]
            r6 = 0
            r5[r6] = r3
            com.itextpdf.io.IOException r4 = r4.setMessageParams(r5)
            throw r4
        L_0x03a2:
            com.itextpdf.io.IOException r4 = new com.itextpdf.io.IOException
            java.lang.String r5 = "endcharmetrics is missing in the metrics file."
            r4.<init>((java.lang.String) r5)
            throw r4
        L_0x03aa:
            java.util.Map r3 = r0.unicodeToGlyph
            r4 = 160(0xa0, float:2.24E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            boolean r3 = r3.containsKey(r5)
            r5 = 32
            if (r3 != 0) goto L_0x03e2
            java.util.Map r3 = r0.unicodeToGlyph
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            java.lang.Object r3 = r3.get(r6)
            com.itextpdf.io.font.otf.Glyph r3 = (com.itextpdf.p026io.font.otf.Glyph) r3
            if (r3 == 0) goto L_0x03e2
            java.util.Map r6 = r0.unicodeToGlyph
            java.lang.Integer r7 = java.lang.Integer.valueOf(r4)
            com.itextpdf.io.font.otf.Glyph r10 = new com.itextpdf.io.font.otf.Glyph
            int r12 = r3.getCode()
            int r13 = r3.getWidth()
            int[] r14 = r3.getBbox()
            r10.<init>(r12, r13, r4, r14)
            r6.put(r7, r10)
        L_0x03e2:
            r3 = 0
        L_0x03e3:
            java.lang.String r4 = r1.readLine()
            r11 = r4
            if (r4 == 0) goto L_0x040f
            java.util.StringTokenizer r4 = new java.util.StringTokenizer
            r4.<init>(r11)
            boolean r6 = r4.hasMoreTokens()
            if (r6 != 0) goto L_0x03f6
            goto L_0x03e3
        L_0x03f6:
            java.lang.String r6 = r4.nextToken()
            java.lang.String r7 = "EndFontMetrics"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x0404
            r3 = 1
            goto L_0x040f
        L_0x0404:
            java.lang.String r7 = "StartKernPairs"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x040e
            r2 = 1
            goto L_0x040f
        L_0x040e:
            goto L_0x03e3
        L_0x040f:
            if (r2 == 0) goto L_0x0485
        L_0x0411:
            java.lang.String r4 = r1.readLine()
            r11 = r4
            if (r4 == 0) goto L_0x0482
            java.util.StringTokenizer r4 = new java.util.StringTokenizer
            r4.<init>(r11)
            boolean r6 = r4.hasMoreTokens()
            if (r6 != 0) goto L_0x0427
            r16 = r9
            r15 = -1
            goto L_0x047c
        L_0x0427:
            java.lang.String r6 = r4.nextToken()
            java.lang.String r7 = "KPX"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x046c
            java.lang.String r7 = r4.nextToken()
            java.lang.String r10 = r4.nextToken()
            java.lang.String r12 = r4.nextToken()
            float r12 = java.lang.Float.parseFloat(r12)
            int r12 = (int) r12
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            int r13 = com.itextpdf.p026io.font.AdobeGlyphList.nameToUnicode(r7)
            int r14 = com.itextpdf.p026io.font.AdobeGlyphList.nameToUnicode(r10)
            r15 = -1
            if (r13 == r15) goto L_0x0467
            if (r14 == r15) goto L_0x0467
            r16 = r9
            long r8 = (long) r13
            long r8 = r8 << r5
            r18 = r6
            long r5 = (long) r14
            long r8 = r8 + r5
            java.util.Map<java.lang.Long, java.lang.Integer> r5 = r0.kernPairs
            java.lang.Long r6 = java.lang.Long.valueOf(r8)
            r5.put(r6, r12)
            goto L_0x046b
        L_0x0467:
            r18 = r6
            r16 = r9
        L_0x046b:
            goto L_0x047b
        L_0x046c:
            r18 = r6
            r16 = r9
            r15 = -1
            java.lang.String r5 = "EndKernPairs"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x047b
            r2 = 0
            goto L_0x04ab
        L_0x047b:
        L_0x047c:
            r9 = r16
            r5 = 32
            r8 = 1
            goto L_0x0411
        L_0x0482:
            r16 = r9
            goto L_0x04ab
        L_0x0485:
            r16 = r9
            if (r3 != 0) goto L_0x04ab
            com.itextpdf.io.font.Type1Parser r4 = r0.fontParser
            java.lang.String r4 = r4.getAfmPath()
            if (r4 == 0) goto L_0x04a3
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "endfontmetrics is missing in {0}."
            r5.<init>((java.lang.String) r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = 0
            r6[r7] = r4
            com.itextpdf.io.IOException r5 = r5.setMessageParams(r6)
            throw r5
        L_0x04a3:
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "endfontmetrics is missing in the metrics file."
            r5.<init>((java.lang.String) r6)
            throw r5
        L_0x04ab:
            if (r2 == 0) goto L_0x04cf
            com.itextpdf.io.font.Type1Parser r4 = r0.fontParser
            java.lang.String r4 = r4.getAfmPath()
            if (r4 == 0) goto L_0x04c7
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "endkernpairs is missing in {0}."
            r5.<init>((java.lang.String) r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = 0
            r6[r7] = r4
            com.itextpdf.io.IOException r5 = r5.setMessageParams(r6)
            throw r5
        L_0x04c7:
            com.itextpdf.io.IOException r5 = new com.itextpdf.io.IOException
            java.lang.String r6 = "endkernpairs is missing in the metrics file."
            r5.<init>((java.lang.String) r6)
            throw r5
        L_0x04cf:
            r6 = 1
            r7 = 0
            r1.close()
            java.lang.String r4 = r0.encodingScheme
            java.lang.String r5 = "AdobeStandardEncoding"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x04e9
            java.lang.String r4 = r0.encodingScheme
            java.lang.String r5 = "StandardEncoding"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x04e9
            r7 = 1
        L_0x04e9:
            r0.isFontSpecific = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.Type1Font.process():void");
    }
}

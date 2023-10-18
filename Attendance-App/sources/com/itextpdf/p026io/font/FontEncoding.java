package com.itextpdf.p026io.font;

import com.itextpdf.p026io.util.ArrayUtil;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.TextUtil;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringTokenizer;

/* renamed from: com.itextpdf.io.font.FontEncoding */
public class FontEncoding implements Serializable {
    public static final String FONT_SPECIFIC = "FontSpecific";
    public static final String NOTDEF = ".notdef";
    private static final byte[] emptyBytes = new byte[0];
    private static final long serialVersionUID = -684967385759439083L;
    protected String baseEncoding;
    protected int[] codeToUnicode = ArrayUtil.fillWithValue(new int[256], -1);
    protected String[] differences;
    protected boolean fontSpecific = false;
    protected IntHashtable unicodeDifferences = new IntHashtable(256);
    protected IntHashtable unicodeToCode = new IntHashtable(256);

    protected FontEncoding() {
    }

    public static FontEncoding createFontEncoding(String baseEncoding2) {
        FontEncoding encoding = new FontEncoding();
        String normalizeEncoding = normalizeEncoding(baseEncoding2);
        encoding.baseEncoding = normalizeEncoding;
        if (normalizeEncoding.startsWith("#")) {
            encoding.fillCustomEncoding();
        } else {
            encoding.fillNamedEncoding();
        }
        return encoding;
    }

    public static FontEncoding createEmptyFontEncoding() {
        FontEncoding encoding = new FontEncoding();
        encoding.baseEncoding = null;
        encoding.fontSpecific = false;
        encoding.differences = new String[256];
        for (int ch = 0; ch < 256; ch++) {
            encoding.unicodeDifferences.put(ch, ch);
        }
        return encoding;
    }

    public static FontEncoding createFontSpecificEncoding() {
        FontEncoding encoding = new FontEncoding();
        encoding.fontSpecific = true;
        for (int ch = 0; ch < 256; ch++) {
            encoding.unicodeToCode.put(ch, ch);
            encoding.codeToUnicode[ch] = ch;
            encoding.unicodeDifferences.put(ch, ch);
        }
        return encoding;
    }

    public String getBaseEncoding() {
        return this.baseEncoding;
    }

    public boolean isFontSpecific() {
        return this.fontSpecific;
    }

    public boolean addSymbol(int code, int unicode) {
        String glyphName;
        if (code < 0 || code > 255 || (glyphName = AdobeGlyphList.unicodeToName(unicode)) == null) {
            return false;
        }
        this.unicodeToCode.put(unicode, code);
        this.codeToUnicode[code] = unicode;
        this.differences[code] = glyphName;
        this.unicodeDifferences.put(unicode, unicode);
        return true;
    }

    public int getUnicode(int index) {
        return this.codeToUnicode[index];
    }

    public int getUnicodeDifference(int index) {
        return this.unicodeDifferences.get(index);
    }

    public boolean hasDifferences() {
        return this.differences != null;
    }

    public String getDifference(int index) {
        String[] strArr = this.differences;
        if (strArr != null) {
            return strArr[index];
        }
        return null;
    }

    public byte[] convertToBytes(String text) {
        if (text == null || text.length() == 0) {
            return emptyBytes;
        }
        int ptr = 0;
        byte[] bytes = new byte[text.length()];
        for (int i = 0; i < text.length(); i++) {
            if (this.unicodeToCode.containsKey(text.charAt(i))) {
                bytes[ptr] = (byte) convertToByte(text.charAt(i));
                ptr++;
            }
        }
        return ArrayUtil.shortenArray(bytes, ptr);
    }

    public int convertToByte(int unicode) {
        return this.unicodeToCode.get(unicode);
    }

    public boolean canEncode(int unicode) {
        return this.unicodeToCode.containsKey(unicode) || TextUtil.isNonPrintable(unicode) || TextUtil.isNewLine(unicode);
    }

    public boolean canDecode(int code) {
        return this.codeToUnicode[code] > -1;
    }

    public boolean isBuiltWith(String encoding) {
        return Objects.equals(normalizeEncoding(encoding), this.baseEncoding);
    }

    /* access modifiers changed from: protected */
    public void fillCustomEncoding() {
        int orderK;
        this.differences = new String[256];
        StringTokenizer tok = new StringTokenizer(this.baseEncoding.substring(1), " ,\t\n\r\f");
        if (tok.nextToken().equals("full")) {
            while (tok.hasMoreTokens()) {
                String order = tok.nextToken();
                String name = tok.nextToken();
                int parseInt = (char) Integer.parseInt(tok.nextToken(), 16);
                int uniName = AdobeGlyphList.nameToUnicode(name);
                if (order.startsWith("'")) {
                    orderK = order.charAt(1);
                } else {
                    orderK = Integer.parseInt(order);
                }
                int orderK2 = orderK % 256;
                this.unicodeToCode.put(parseInt, orderK2);
                this.codeToUnicode[orderK2] = parseInt;
                this.differences[orderK2] = name;
                this.unicodeDifferences.put(parseInt, uniName);
            }
        } else {
            int k = 0;
            if (tok.hasMoreTokens()) {
                k = Integer.parseInt(tok.nextToken());
            }
            while (tok.hasMoreTokens() && k < 256) {
                String hex = tok.nextToken();
                int uni = Integer.parseInt(hex, 16) % 65536;
                String name2 = AdobeGlyphList.unicodeToName(uni);
                if (name2 == null) {
                    name2 = "uni" + hex;
                }
                this.unicodeToCode.put(uni, k);
                this.codeToUnicode[k] = uni;
                this.differences[k] = name2;
                this.unicodeDifferences.put(uni, uni);
                k++;
            }
        }
        for (int k2 = 0; k2 < 256; k2++) {
            String[] strArr = this.differences;
            if (strArr[k2] == null) {
                strArr[k2] = NOTDEF;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void fillNamedEncoding() {
        PdfEncodings.convertToBytes(" ", this.baseEncoding);
        if (!("Cp1252".equals(this.baseEncoding) || PdfEncodings.MACROMAN.equals(this.baseEncoding)) && this.differences == null) {
            this.differences = new String[256];
        }
        byte[] b = new byte[256];
        for (int k = 0; k < 256; k++) {
            b[k] = (byte) k;
        }
        char[] encoded = PdfEncodings.convertToString(b, this.baseEncoding).toCharArray();
        for (int ch = 0; ch < 256; ch++) {
            char uni = encoded[ch];
            String name = AdobeGlyphList.unicodeToName(uni);
            if (name == null) {
                name = NOTDEF;
            } else {
                this.unicodeToCode.put(uni, ch);
                this.codeToUnicode[ch] = uni;
                this.unicodeDifferences.put(uni, uni);
            }
            String[] strArr = this.differences;
            if (strArr != null) {
                strArr[ch] = name;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void fillStandardEncoding() {
        int[] encoded = PdfEncodings.standardEncoding;
        for (int ch = 0; ch < 256; ch++) {
            int uni = encoded[ch];
            String name = AdobeGlyphList.unicodeToName(uni);
            if (name == null) {
                name = NOTDEF;
            } else {
                this.unicodeToCode.put(uni, ch);
                this.codeToUnicode[ch] = uni;
                this.unicodeDifferences.put(uni, uni);
            }
            String[] strArr = this.differences;
            if (strArr != null) {
                strArr[ch] = name;
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.String normalizeEncoding(java.lang.String r3) {
        /*
            java.lang.String r0 = ""
            if (r3 != 0) goto L_0x0006
            r1 = r0
            goto L_0x000a
        L_0x0006:
            java.lang.String r1 = r3.toLowerCase()
        L_0x000a:
            int r2 = r1.hashCode()
            switch(r2) {
                case -1125785742: goto L_0x0044;
                case -175708658: goto L_0x003a;
                case 0: goto L_0x0032;
                case 217982305: goto L_0x0028;
                case 1349402911: goto L_0x001d;
                case 2128309164: goto L_0x0012;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x004f
        L_0x0012:
            java.lang.String r0 = "zapfdingbatsencoding"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 5
            goto L_0x0050
        L_0x001d:
            java.lang.String r0 = "winansi"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 1
            goto L_0x0050
        L_0x0028:
            java.lang.String r0 = "macromanencoding"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 4
            goto L_0x0050
        L_0x0032:
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 0
            goto L_0x0050
        L_0x003a:
            java.lang.String r0 = "macroman"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 3
            goto L_0x0050
        L_0x0044:
            java.lang.String r0 = "winansiencoding"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            r0 = 2
            goto L_0x0050
        L_0x004f:
            r0 = -1
        L_0x0050:
            switch(r0) {
                case 0: goto L_0x005a;
                case 1: goto L_0x005a;
                case 2: goto L_0x005a;
                case 3: goto L_0x0057;
                case 4: goto L_0x0057;
                case 5: goto L_0x0054;
                default: goto L_0x0053;
            }
        L_0x0053:
            return r3
        L_0x0054:
            java.lang.String r0 = "ZapfDingbats"
            return r0
        L_0x0057:
            java.lang.String r0 = "MacRoman"
            return r0
        L_0x005a:
            java.lang.String r0 = "Cp1252"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.FontEncoding.normalizeEncoding(java.lang.String):java.lang.String");
    }
}

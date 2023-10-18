package com.itextpdf.styledxmlparser.css.font;

import com.itextpdf.layout.font.FontFamilySplitter;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;

public class CssFontFace {
    private final String alias;
    private final List<CssFontFaceSrc> sources;

    public enum FontFormat {
        None,
        TrueType,
        OpenType,
        WOFF,
        WOFF2,
        EOT,
        SVG
    }

    public static CssFontFace create(List<CssDeclaration> properties) {
        String fontFamily = null;
        String srcs = null;
        for (CssDeclaration descriptor : properties) {
            if ("font-family".equals(descriptor.getProperty())) {
                fontFamily = FontFamilySplitter.removeQuotes(descriptor.getExpression());
            } else if ("src".equals(descriptor.getProperty())) {
                srcs = descriptor.getExpression();
            }
        }
        if (fontFamily == null || srcs == null) {
            return null;
        }
        List<CssFontFaceSrc> sources2 = new ArrayList<>();
        for (String src : splitSourcesSequence(srcs)) {
            CssFontFaceSrc source = CssFontFaceSrc.create(src.trim());
            if (source != null) {
                sources2.add(source);
            }
        }
        if (sources2.size() > 0) {
            return new CssFontFace(fontFamily, sources2);
        }
        return null;
    }

    public static String[] splitSourcesSequence(String src) {
        int indexToCut;
        List<String> list = new ArrayList<>();
        for (int indexToStart = 0; indexToStart < src.length(); indexToStart = indexToCut + 1) {
            int i = Integer.MAX_VALUE;
            int findNextUnescapedChar = CssUtils.findNextUnescapedChar(src, '\'', indexToStart) >= 0 ? CssUtils.findNextUnescapedChar(src, '\'', indexToStart) : Integer.MAX_VALUE;
            if (CssUtils.findNextUnescapedChar(src, Typography.quote, indexToStart) >= 0) {
                i = CssUtils.findNextUnescapedChar(src, Typography.quote, indexToStart);
            }
            int indexUnescapedOpeningQuoteMark = Math.min(findNextUnescapedChar, i);
            int indexUnescapedBracket = CssUtils.findNextUnescapedChar(src, ')', indexToStart);
            if (indexUnescapedOpeningQuoteMark < indexUnescapedBracket) {
                indexToCut = CssUtils.findNextUnescapedChar(src, src.charAt(indexUnescapedOpeningQuoteMark), indexUnescapedOpeningQuoteMark + 1);
                if (indexToCut == -1) {
                    indexToCut = src.length();
                }
            } else {
                indexToCut = indexUnescapedBracket;
            }
            while (indexToCut < src.length() && src.charAt(indexToCut) != ',') {
                indexToCut++;
            }
            list.add(src.substring(indexToStart, indexToCut).trim());
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    /* renamed from: com.itextpdf.styledxmlparser.css.font.CssFontFace$1 */
    static /* synthetic */ class C14861 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$css$font$CssFontFace$FontFormat */
        static final /* synthetic */ int[] f1619xd5684689;

        static {
            int[] iArr = new int[FontFormat.values().length];
            f1619xd5684689 = iArr;
            try {
                iArr[FontFormat.None.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1619xd5684689[FontFormat.TrueType.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1619xd5684689[FontFormat.OpenType.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1619xd5684689[FontFormat.WOFF.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1619xd5684689[FontFormat.WOFF2.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static boolean isSupportedFontFormat(FontFormat format) {
        switch (C14861.f1619xd5684689[format.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public String getFontFamily() {
        return this.alias;
    }

    public List<CssFontFaceSrc> getSources() {
        return new ArrayList(this.sources);
    }

    private CssFontFace(String alias2, List<CssFontFaceSrc> sources2) {
        this.alias = alias2;
        this.sources = new ArrayList(sources2);
    }

    public static class CssFontFaceSrc {
        public static final int FormatGroup = 9;
        public static final int TypeGroup = 1;
        public static final int UrlGroup = 4;
        public static final Pattern UrlPattern = Pattern.compile("^((local)|(url))\\((('[^']*')|(\"[^\"]*\")|([^'\"\\)]*))\\)( format\\((('[^']*')|(\"[^\"]*\")|([^'\"\\)]*))\\))?$");
        final FontFormat format;
        final boolean isLocal;
        final String src;

        public FontFormat getFormat() {
            return this.format;
        }

        public String getSrc() {
            return this.src;
        }

        public boolean isLocal() {
            return this.isLocal;
        }

        public String toString() {
            Object[] objArr = new Object[3];
            objArr[0] = this.isLocal ? CommonCssConstants.LOCAL : "url";
            objArr[1] = this.src;
            objArr[2] = this.format != FontFormat.None ? MessageFormatUtil.format(" format({0})", this.format) : "";
            return MessageFormatUtil.format("{0}({1}){2}", objArr);
        }

        public static CssFontFaceSrc create(String src2) {
            Matcher m = UrlPattern.matcher(src2);
            if (!m.matches()) {
                return null;
            }
            return new CssFontFaceSrc(unquote(m.group(4)), CommonCssConstants.LOCAL.equals(m.group(1)), parseFormat(m.group(9)));
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat parseFormat(java.lang.String r2) {
            /*
                if (r2 == 0) goto L_0x0070
                int r0 = r2.length()
                if (r0 <= 0) goto L_0x0070
                java.lang.String r0 = unquote(r2)
                java.lang.String r0 = r0.toLowerCase()
                int r1 = r0.hashCode()
                switch(r1) {
                    case -1440799641: goto L_0x004f;
                    case -503676796: goto L_0x0044;
                    case 114276: goto L_0x0039;
                    case 3655064: goto L_0x002e;
                    case 113307034: goto L_0x0023;
                    case 1845202376: goto L_0x0018;
                    default: goto L_0x0017;
                }
            L_0x0017:
                goto L_0x0059
            L_0x0018:
                java.lang.String r1 = "truetype"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 0
                goto L_0x005a
            L_0x0023:
                java.lang.String r1 = "woff2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 3
                goto L_0x005a
            L_0x002e:
                java.lang.String r1 = "woff"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 2
                goto L_0x005a
            L_0x0039:
                java.lang.String r1 = "svg"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 5
                goto L_0x005a
            L_0x0044:
                java.lang.String r1 = "opentype"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 1
                goto L_0x005a
            L_0x004f:
                java.lang.String r1 = "embedded-opentype"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0017
                r0 = 4
                goto L_0x005a
            L_0x0059:
                r0 = -1
            L_0x005a:
                switch(r0) {
                    case 0: goto L_0x006d;
                    case 1: goto L_0x006a;
                    case 2: goto L_0x0067;
                    case 3: goto L_0x0064;
                    case 4: goto L_0x0061;
                    case 5: goto L_0x005e;
                    default: goto L_0x005d;
                }
            L_0x005d:
                goto L_0x0070
            L_0x005e:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.SVG
                return r0
            L_0x0061:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.EOT
                return r0
            L_0x0064:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.WOFF2
                return r0
            L_0x0067:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.WOFF
                return r0
            L_0x006a:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.OpenType
                return r0
            L_0x006d:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.TrueType
                return r0
            L_0x0070:
                com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat r0 = com.itextpdf.styledxmlparser.css.font.CssFontFace.FontFormat.None
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.font.CssFontFace.CssFontFaceSrc.parseFormat(java.lang.String):com.itextpdf.styledxmlparser.css.font.CssFontFace$FontFormat");
        }

        public static String unquote(String quotedString) {
            if (quotedString.charAt(0) == '\'' || quotedString.charAt(0) == '\"') {
                return quotedString.substring(1, quotedString.length() - 1);
            }
            return quotedString;
        }

        private CssFontFaceSrc(String src2, boolean isLocal2, FontFormat format2) {
            this.format = format2;
            this.src = src2;
            this.isLocal = isLocal2;
        }
    }
}

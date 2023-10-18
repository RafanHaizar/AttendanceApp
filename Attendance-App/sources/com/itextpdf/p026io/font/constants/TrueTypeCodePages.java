package com.itextpdf.p026io.font.constants;

/* renamed from: com.itextpdf.io.font.constants.TrueTypeCodePages */
public final class TrueTypeCodePages {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String[] CODE_PAGES = {"1252 Latin 1", "1250 Latin 2: Eastern Europe", "1251 Cyrillic", "1253 Greek", "1254 Turkish", "1255 Hebrew", "1256 Arabic", "1257 Windows Baltic", "1258 Vietnamese", null, null, null, null, null, null, null, "874 Thai", "932 JIS/Japan", "936 Chinese: Simplified chars--PRC and Singapore", "949 Korean Wansung", "950 Chinese: Traditional chars--Taiwan and Hong Kong", "1361 Korean Johab", null, null, null, null, null, null, null, "Macintosh Character Set (US Roman)", "OEM Character Set", "Symbol Character Set", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "869 IBM Greek", "866 MS-DOS Russian", "865 MS-DOS Nordic", "864 Arabic", "863 MS-DOS Canadian French", "862 Hebrew", "861 MS-DOS Icelandic", "860 MS-DOS Portuguese", "857 IBM Turkish", "855 IBM Cyrillic; primarily Russian", "852 Latin 2", "775 MS-DOS Baltic", "737 Greek; former 437 G", "708 Arabic; ASMO 708", "850 WE/Latin 1", "437 US"};

    private TrueTypeCodePages() {
    }

    public static String get(int bit) {
        if (bit >= 0 && bit < 64) {
            return CODE_PAGES[bit];
        }
        throw new AssertionError();
    }
}

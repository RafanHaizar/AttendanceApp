package com.itextpdf.styledxmlparser.css;

public final class CssNestedAtRuleFactory {
    private CssNestedAtRuleFactory() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.styledxmlparser.css.CssNestedAtRule createNestedRule(java.lang.String r3) {
        /*
            java.lang.String r3 = r3.trim()
            java.lang.String r0 = extractRuleNameFromDeclaration(r3)
            int r1 = r0.length()
            java.lang.String r1 = r3.substring(r1)
            java.lang.String r1 = r1.trim()
            int r2 = r0.hashCode()
            switch(r2) {
                case -1586477797: goto L_0x00ee;
                case -1570272732: goto L_0x00e2;
                case -1398869405: goto L_0x00d7;
                case -1314880604: goto L_0x00cc;
                case -1012429441: goto L_0x00c1;
                case -655373719: goto L_0x00b6;
                case -634754168: goto L_0x00ab;
                case -61818722: goto L_0x00a0;
                case 3433103: goto L_0x0095;
                case 103772132: goto L_0x008a;
                case 273738492: goto L_0x007d;
                case 582625894: goto L_0x0070;
                case 1163912186: goto L_0x0064;
                case 1288627767: goto L_0x0058;
                case 1353595449: goto L_0x004c;
                case 1355259569: goto L_0x0040;
                case 1664146971: goto L_0x0034;
                case 1717271183: goto L_0x0029;
                case 1755462605: goto L_0x001d;
                default: goto L_0x001b;
            }
        L_0x001b:
            goto L_0x00f9
        L_0x001d:
            java.lang.String r2 = "top-center"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 4
            goto L_0x00fa
        L_0x0029:
            java.lang.String r2 = "left-top"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 7
            goto L_0x00fa
        L_0x0034:
            java.lang.String r2 = "left-middle"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 8
            goto L_0x00fa
        L_0x0040:
            java.lang.String r2 = "left-bottom"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 9
            goto L_0x00fa
        L_0x004c:
            java.lang.String r2 = "bottom-left-corner"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 13
            goto L_0x00fa
        L_0x0058:
            java.lang.String r2 = "bottom-center"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 15
            goto L_0x00fa
        L_0x0064:
            java.lang.String r2 = "bottom-right"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 16
            goto L_0x00fa
        L_0x0070:
            java.lang.String r2 = "right-middle"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 11
            goto L_0x00fa
        L_0x007d:
            java.lang.String r2 = "right-bottom"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 12
            goto L_0x00fa
        L_0x008a:
            java.lang.String r2 = "media"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 0
            goto L_0x00fa
        L_0x0095:
            java.lang.String r2 = "page"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 1
            goto L_0x00fa
        L_0x00a0:
            java.lang.String r2 = "top-right-corner"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 6
            goto L_0x00fa
        L_0x00ab:
            java.lang.String r2 = "bottom-right-corner"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 17
            goto L_0x00fa
        L_0x00b6:
            java.lang.String r2 = "bottom-left"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 14
            goto L_0x00fa
        L_0x00c1:
            java.lang.String r2 = "top-left"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 3
            goto L_0x00fa
        L_0x00cc:
            java.lang.String r2 = "top-right"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 5
            goto L_0x00fa
        L_0x00d7:
            java.lang.String r2 = "top-left-corner"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 2
            goto L_0x00fa
        L_0x00e2:
            java.lang.String r2 = "right-top"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 10
            goto L_0x00fa
        L_0x00ee:
            java.lang.String r2 = "font-face"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x001b
            r2 = 18
            goto L_0x00fa
        L_0x00f9:
            r2 = -1
        L_0x00fa:
            switch(r2) {
                case 0: goto L_0x0115;
                case 1: goto L_0x010f;
                case 2: goto L_0x0109;
                case 3: goto L_0x0109;
                case 4: goto L_0x0109;
                case 5: goto L_0x0109;
                case 6: goto L_0x0109;
                case 7: goto L_0x0109;
                case 8: goto L_0x0109;
                case 9: goto L_0x0109;
                case 10: goto L_0x0109;
                case 11: goto L_0x0109;
                case 12: goto L_0x0109;
                case 13: goto L_0x0109;
                case 14: goto L_0x0109;
                case 15: goto L_0x0109;
                case 16: goto L_0x0109;
                case 17: goto L_0x0109;
                case 18: goto L_0x0103;
                default: goto L_0x00fd;
            }
        L_0x00fd:
            com.itextpdf.styledxmlparser.css.CssNestedAtRule r2 = new com.itextpdf.styledxmlparser.css.CssNestedAtRule
            r2.<init>(r0, r1)
            return r2
        L_0x0103:
            com.itextpdf.styledxmlparser.css.CssFontFaceRule r2 = new com.itextpdf.styledxmlparser.css.CssFontFaceRule
            r2.<init>()
            return r2
        L_0x0109:
            com.itextpdf.styledxmlparser.css.page.CssMarginRule r2 = new com.itextpdf.styledxmlparser.css.page.CssMarginRule
            r2.<init>(r0)
            return r2
        L_0x010f:
            com.itextpdf.styledxmlparser.css.page.CssPageRule r2 = new com.itextpdf.styledxmlparser.css.page.CssPageRule
            r2.<init>(r1)
            return r2
        L_0x0115:
            com.itextpdf.styledxmlparser.css.media.CssMediaRule r2 = new com.itextpdf.styledxmlparser.css.media.CssMediaRule
            r2.<init>(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.CssNestedAtRuleFactory.createNestedRule(java.lang.String):com.itextpdf.styledxmlparser.css.CssNestedAtRule");
    }

    static String extractRuleNameFromDeclaration(String ruleDeclaration) {
        int separatorIndex;
        int spaceIndex = ruleDeclaration.indexOf(32);
        int colonIndex = ruleDeclaration.indexOf(58);
        if (spaceIndex == -1) {
            separatorIndex = colonIndex;
        } else if (colonIndex == -1) {
            separatorIndex = spaceIndex;
        } else {
            separatorIndex = Math.min(spaceIndex, colonIndex);
        }
        return separatorIndex == -1 ? ruleDeclaration : ruleDeclaration.substring(0, separatorIndex);
    }
}

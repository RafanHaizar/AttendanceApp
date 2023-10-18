package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.INode;

public abstract class CssPseudoClassSelectorItem implements ICssSelectorItem {
    protected String arguments;
    private String pseudoClass;

    protected CssPseudoClassSelectorItem(String pseudoClass2) {
        this(pseudoClass2, "");
    }

    protected CssPseudoClassSelectorItem(String pseudoClass2, String arguments2) {
        this.pseudoClass = pseudoClass2;
        this.arguments = arguments2;
    }

    public static CssPseudoClassSelectorItem create(String fullSelectorString) {
        String arguments2;
        String pseudoClass2;
        int indexOfParentheses = fullSelectorString.indexOf(40);
        if (indexOfParentheses == -1) {
            pseudoClass2 = fullSelectorString;
            arguments2 = "";
        } else {
            pseudoClass2 = fullSelectorString.substring(0, indexOfParentheses);
            arguments2 = fullSelectorString.substring(indexOfParentheses + 1, fullSelectorString.length() - 1).trim();
        }
        return create(pseudoClass2, arguments2);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00ec  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem create(java.lang.String r5, java.lang.String r6) {
        /*
            int r0 = r5.hashCode()
            switch(r0) {
                case -2136991809: goto L_0x00b3;
                case -1754914063: goto L_0x00a8;
                case -1422950650: goto L_0x009d;
                case -897532411: goto L_0x0092;
                case -880905839: goto L_0x0086;
                case 109267: goto L_0x007b;
                case 3321850: goto L_0x0070;
                case 3506402: goto L_0x0064;
                case 96634189: goto L_0x005a;
                case 97604824: goto L_0x004f;
                case 99469628: goto L_0x0043;
                case 270940796: goto L_0x0037;
                case 466760490: goto L_0x002a;
                case 835834661: goto L_0x001f;
                case 1292941139: goto L_0x0014;
                case 2025926969: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x00bd
        L_0x0009:
            java.lang.String r0 = "last-of-type"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x00be
        L_0x0014:
            java.lang.String r0 = "first-of-type"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x00be
        L_0x001f:
            java.lang.String r0 = "last-child"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x00be
        L_0x002a:
            java.lang.String r0 = "visited"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 14
            goto L_0x00be
        L_0x0037:
            java.lang.String r0 = "disabled"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 15
            goto L_0x00be
        L_0x0043:
            java.lang.String r0 = "hover"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 12
            goto L_0x00be
        L_0x004f:
            java.lang.String r0 = "focus"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 11
            goto L_0x00be
        L_0x005a:
            java.lang.String r0 = "empty"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x00be
        L_0x0064:
            java.lang.String r0 = "root"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 8
            goto L_0x00be
        L_0x0070:
            java.lang.String r0 = "link"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 9
            goto L_0x00be
        L_0x007b:
            java.lang.String r0 = "not"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 7
            goto L_0x00be
        L_0x0086:
            java.lang.String r0 = "target"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 13
            goto L_0x00be
        L_0x0092:
            java.lang.String r0 = "nth-of-type"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 6
            goto L_0x00be
        L_0x009d:
            java.lang.String r0 = "active"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 10
            goto L_0x00be
        L_0x00a8:
            java.lang.String r0 = "nth-child"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 5
            goto L_0x00be
        L_0x00b3:
            java.lang.String r0 = "first-child"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x00be
        L_0x00bd:
            r0 = -1
        L_0x00be:
            r1 = 0
            switch(r0) {
                case 0: goto L_0x0123;
                case 1: goto L_0x011e;
                case 2: goto L_0x0119;
                case 3: goto L_0x0114;
                case 4: goto L_0x010f;
                case 5: goto L_0x0109;
                case 6: goto L_0x0103;
                case 7: goto L_0x00d9;
                case 8: goto L_0x00d4;
                case 9: goto L_0x00ce;
                case 10: goto L_0x00c8;
                case 11: goto L_0x00c8;
                case 12: goto L_0x00c8;
                case 13: goto L_0x00c8;
                case 14: goto L_0x00c8;
                case 15: goto L_0x00c3;
                default: goto L_0x00c2;
            }
        L_0x00c2:
            return r1
        L_0x00c3:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassDisabledSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassDisabledSelectorItem.getInstance()
            return r0
        L_0x00c8:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem$AlwaysNotApplySelectorItem r0 = new com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem$AlwaysNotApplySelectorItem
            r0.<init>(r5, r6)
            return r0
        L_0x00ce:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem$AlwaysApplySelectorItem r0 = new com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem$AlwaysApplySelectorItem
            r0.<init>(r5, r6)
            return r0
        L_0x00d4:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassRootSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassRootSelectorItem.getInstance()
            return r0
        L_0x00d9:
            com.itextpdf.styledxmlparser.css.selector.CssSelector r0 = new com.itextpdf.styledxmlparser.css.selector.CssSelector
            r0.<init>((java.lang.String) r6)
            java.util.List r2 = r0.getSelectorItems()
            java.util.Iterator r2 = r2.iterator()
        L_0x00e6:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00fd
            java.lang.Object r3 = r2.next()
            com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem r3 = (com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem) r3
            boolean r4 = r3 instanceof com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNotSelectorItem
            if (r4 != 0) goto L_0x00fc
            boolean r4 = r3 instanceof com.itextpdf.styledxmlparser.css.selector.item.CssPseudoElementSelectorItem
            if (r4 == 0) goto L_0x00fb
            goto L_0x00fc
        L_0x00fb:
            goto L_0x00e6
        L_0x00fc:
            return r1
        L_0x00fd:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNotSelectorItem r1 = new com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNotSelectorItem
            r1.<init>(r0)
            return r1
        L_0x0103:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNthOfTypeSelectorItem r0 = new com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNthOfTypeSelectorItem
            r0.<init>(r6)
            return r0
        L_0x0109:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNthChildSelectorItem r0 = new com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassNthChildSelectorItem
            r0.<init>(r6)
            return r0
        L_0x010f:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassLastOfTypeSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassLastOfTypeSelectorItem.getInstance()
            return r0
        L_0x0114:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassLastChildSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassLastChildSelectorItem.getInstance()
            return r0
        L_0x0119:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassFirstOfTypeSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassFirstOfTypeSelectorItem.getInstance()
            return r0
        L_0x011e:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassFirstChildSelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassFirstChildSelectorItem.getInstance()
            return r0
        L_0x0123:
            com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassEmptySelectorItem r0 = com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassEmptySelectorItem.getInstance()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem.create(java.lang.String, java.lang.String):com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem");
    }

    public int getSpecificity() {
        return 1024;
    }

    public boolean matches(INode node) {
        return false;
    }

    public String toString() {
        return ":" + this.pseudoClass + (!this.arguments.isEmpty() ? "(" + this.arguments + ")" : "");
    }

    public String getPseudoClass() {
        return this.pseudoClass;
    }

    private static class AlwaysApplySelectorItem extends CssPseudoClassSelectorItem {
        AlwaysApplySelectorItem(String pseudoClass, String arguments) {
            super(pseudoClass, arguments);
        }

        public boolean matches(INode node) {
            return true;
        }
    }

    private static class AlwaysNotApplySelectorItem extends CssPseudoClassSelectorItem {
        AlwaysNotApplySelectorItem(String pseudoClass, String arguments) {
            super(pseudoClass, arguments);
        }

        public boolean matches(INode node) {
            return false;
        }
    }
}

package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

class CssPseudoClassEmptySelectorItem extends CssPseudoClassSelectorItem {
    private static final CssPseudoClassEmptySelectorItem instance = new CssPseudoClassEmptySelectorItem();

    private CssPseudoClassEmptySelectorItem() {
        super(CommonCssConstants.EMPTY);
    }

    public static CssPseudoClassEmptySelectorItem getInstance() {
        return instance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean matches(com.itextpdf.styledxmlparser.node.INode r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.itextpdf.styledxmlparser.node.IElementNode
            r1 = 0
            if (r0 == 0) goto L_0x0043
            boolean r0 = r6 instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode
            if (r0 != 0) goto L_0x0043
            boolean r0 = r6 instanceof com.itextpdf.styledxmlparser.node.IDocumentNode
            if (r0 == 0) goto L_0x000e
            goto L_0x0043
        L_0x000e:
            java.util.List r0 = r6.childNodes()
            boolean r0 = r0.isEmpty()
            r2 = 1
            if (r0 == 0) goto L_0x001a
            return r2
        L_0x001a:
            java.util.List r0 = r6.childNodes()
            java.util.Iterator r0 = r0.iterator()
        L_0x0022:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0042
            java.lang.Object r3 = r0.next()
            com.itextpdf.styledxmlparser.node.INode r3 = (com.itextpdf.styledxmlparser.node.INode) r3
            boolean r4 = r3 instanceof com.itextpdf.styledxmlparser.node.ITextNode
            if (r4 == 0) goto L_0x0041
            r4 = r3
            com.itextpdf.styledxmlparser.node.ITextNode r4 = (com.itextpdf.styledxmlparser.node.ITextNode) r4
            java.lang.String r4 = r4.wholeText()
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x0040
            goto L_0x0041
        L_0x0040:
            goto L_0x0022
        L_0x0041:
            return r1
        L_0x0042:
            return r2
        L_0x0043:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassEmptySelectorItem.matches(com.itextpdf.styledxmlparser.node.INode):boolean");
    }
}

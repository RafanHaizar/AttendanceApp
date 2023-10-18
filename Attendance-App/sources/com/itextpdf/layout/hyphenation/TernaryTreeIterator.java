package com.itextpdf.layout.hyphenation;

import java.util.Enumeration;
import java.util.Stack;

class TernaryTreeIterator implements Enumeration {
    int cur = -1;
    String curkey;

    /* renamed from: ks */
    StringBuffer f1533ks = new StringBuffer();

    /* renamed from: ns */
    Stack f1534ns = new Stack();

    /* renamed from: tt */
    TernaryTree f1535tt;

    private class Item {
        char child;
        char parent;

        public Item() {
            this.parent = 0;
            this.child = 0;
        }

        public Item(char p, char c) {
            this.parent = p;
            this.child = c;
        }

        public Item(Item i) {
            this.parent = i.parent;
            this.child = i.child;
        }
    }

    public TernaryTreeIterator(TernaryTree tt) {
        this.f1535tt = tt;
        reset();
    }

    public void reset() {
        this.f1534ns.removeAllElements();
        this.f1533ks.setLength(0);
        this.cur = this.f1535tt.root;
        run();
    }

    public Object nextElement() {
        String res = this.curkey;
        this.cur = m275up();
        run();
        return res;
    }

    public char getValue() {
        if (this.cur >= 0) {
            return this.f1535tt.f1527eq[this.cur];
        }
        return 0;
    }

    public boolean hasMoreElements() {
        return this.cur != -1;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v4, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v5, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v6, types: [char] */
    /* renamed from: up */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m275up() {
        /*
            r7 = this;
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r0 = new com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item
            r0.<init>()
            r1 = 0
            java.util.Stack r2 = r7.f1534ns
            int r2 = r2.size()
            r3 = -1
            if (r2 != 0) goto L_0x0010
            return r3
        L_0x0010:
            int r2 = r7.cur
            if (r2 == 0) goto L_0x0027
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1531sc
            int r4 = r7.cur
            char r2 = r2[r4]
            if (r2 != 0) goto L_0x0027
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1530lo
            int r3 = r7.cur
            char r2 = r2[r3]
            return r2
        L_0x0027:
            r2 = 1
        L_0x0028:
            if (r2 == 0) goto L_0x00b8
            java.util.Stack r4 = r7.f1534ns
            java.lang.Object r4 = r4.pop()
            r0 = r4
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r0 = (com.itextpdf.layout.hyphenation.TernaryTreeIterator.Item) r0
            char r4 = r0.child
            int r4 = r4 + 1
            char r4 = (char) r4
            r0.child = r4
            char r4 = r0.child
            switch(r4) {
                case 1: goto L_0x006f;
                case 2: goto L_0x0048;
                default: goto L_0x003f;
            }
        L_0x003f:
            java.util.Stack r4 = r7.f1534ns
            int r4 = r4.size()
            if (r4 != 0) goto L_0x00b5
            return r3
        L_0x0048:
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            char[] r4 = r4.f1528hi
            char r5 = r0.parent
            char r1 = r4[r5]
            java.util.Stack r4 = r7.f1534ns
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r5 = new com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item
            r5.<init>(r0)
            r4.push(r5)
            java.lang.StringBuffer r4 = r7.f1533ks
            int r4 = r4.length()
            if (r4 <= 0) goto L_0x006d
            java.lang.StringBuffer r4 = r7.f1533ks
            int r5 = r4.length()
            int r5 = r5 + -1
            r4.setLength(r5)
        L_0x006d:
            r2 = 0
            goto L_0x0028
        L_0x006f:
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            char[] r4 = r4.f1531sc
            char r5 = r0.parent
            char r4 = r4[r5]
            if (r4 == 0) goto L_0x0099
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            char[] r4 = r4.f1527eq
            char r5 = r0.parent
            char r1 = r4[r5]
            java.util.Stack r4 = r7.f1534ns
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r5 = new com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item
            r5.<init>(r0)
            r4.push(r5)
            java.lang.StringBuffer r4 = r7.f1533ks
            com.itextpdf.layout.hyphenation.TernaryTree r5 = r7.f1535tt
            char[] r5 = r5.f1531sc
            char r6 = r0.parent
            char r5 = r5[r6]
            r4.append(r5)
            goto L_0x00b2
        L_0x0099:
            char r4 = r0.child
            int r4 = r4 + 1
            char r4 = (char) r4
            r0.child = r4
            java.util.Stack r4 = r7.f1534ns
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r5 = new com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item
            r5.<init>(r0)
            r4.push(r5)
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            char[] r4 = r4.f1528hi
            char r5 = r0.parent
            char r1 = r4[r5]
        L_0x00b2:
            r2 = 0
            goto L_0x0028
        L_0x00b5:
            r2 = 1
            goto L_0x0028
        L_0x00b8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.hyphenation.TernaryTreeIterator.m275up():int");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r2v9, types: [char] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int run() {
        /*
            r7 = this;
            int r0 = r7.cur
            r1 = -1
            if (r0 != r1) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 0
        L_0x0007:
            int r2 = r7.cur
            r3 = 0
            r4 = 65535(0xffff, float:9.1834E-41)
            if (r2 == 0) goto L_0x003d
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1531sc
            int r5 = r7.cur
            char r2 = r2[r5]
            if (r2 != r4) goto L_0x001b
            r0 = 1
            goto L_0x003d
        L_0x001b:
            java.util.Stack r2 = r7.f1534ns
            com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item r6 = new com.itextpdf.layout.hyphenation.TernaryTreeIterator$Item
            char r5 = (char) r5
            r6.<init>(r5, r3)
            r2.push(r6)
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1531sc
            int r5 = r7.cur
            char r2 = r2[r5]
            if (r2 != 0) goto L_0x0032
            r0 = 1
            goto L_0x003d
        L_0x0032:
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1530lo
            int r3 = r7.cur
            char r2 = r2[r3]
            r7.cur = r2
            goto L_0x0007
        L_0x003d:
            if (r0 == 0) goto L_0x007d
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            java.lang.StringBuffer r2 = r7.f1533ks
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1531sc
            int r5 = r7.cur
            char r2 = r2[r5]
            if (r2 != r4) goto L_0x0076
            com.itextpdf.layout.hyphenation.TernaryTree r2 = r7.f1535tt
            char[] r2 = r2.f1530lo
            int r4 = r7.cur
            char r2 = r2[r4]
        L_0x005d:
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            com.itextpdf.layout.hyphenation.CharVector r4 = r4.f1529kv
            char r4 = r4.get(r2)
            if (r4 == 0) goto L_0x0076
            com.itextpdf.layout.hyphenation.TernaryTree r4 = r7.f1535tt
            com.itextpdf.layout.hyphenation.CharVector r4 = r4.f1529kv
            int r5 = r2 + 1
            char r2 = r4.get(r2)
            r1.append(r2)
            r2 = r5
            goto L_0x005d
        L_0x0076:
            java.lang.String r2 = r1.toString()
            r7.curkey = r2
            return r3
        L_0x007d:
            int r2 = r7.m275up()
            r7.cur = r2
            if (r2 != r1) goto L_0x0007
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.hyphenation.TernaryTreeIterator.run():int");
    }
}

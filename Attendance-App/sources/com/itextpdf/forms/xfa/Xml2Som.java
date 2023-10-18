package com.itextpdf.forms.xfa;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.w3c.dom.Node;

class Xml2Som {
    protected int anform;
    protected Map<String, InverseStore> inverseSearch;
    protected Map<String, Node> name2Node;
    protected List<String> order;
    protected Stack<String> stack;

    Xml2Som() {
    }

    public static String escapeSom(String s) {
        if (s == null) {
            return "";
        }
        int idx = s.indexOf(46);
        if (idx < 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int last = 0;
        while (idx >= 0) {
            sb.append(s.substring(last, idx));
            sb.append('\\');
            last = idx;
            idx = s.indexOf(46, idx + 1);
        }
        sb.append(s.substring(last));
        return sb.toString();
    }

    public static String unescapeSom(String s) {
        int idx = s.indexOf(92);
        if (idx < 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int last = 0;
        while (idx >= 0) {
            sb.append(s.substring(last, idx));
            last = idx + 1;
            idx = s.indexOf(92, idx + 1);
        }
        sb.append(s.substring(last));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String printStack() {
        if (this.stack.size() == 0) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (int k = 0; k < this.stack.size(); k++) {
            s.append('.').append((String) this.stack.get(k));
        }
        return s.substring(1);
    }

    public static String getShortName(String s) {
        int idx = s.indexOf(".#subform[");
        if (idx < 0) {
            return s;
        }
        int last = 0;
        StringBuilder sb = new StringBuilder();
        while (idx >= 0) {
            sb.append(s.substring(last, idx));
            int idx2 = s.indexOf("]", idx + 10);
            if (idx2 < 0) {
                return sb.toString();
            }
            last = idx2 + 1;
            idx = s.indexOf(".#subform[", last);
        }
        sb.append(s.substring(last));
        return sb.toString();
    }

    public void inverseSearchAdd(String unstack) {
        inverseSearchAdd(this.inverseSearch, this.stack, unstack);
    }

    public static void inverseSearchAdd(Map<String, InverseStore> inverseSearch2, Stack<String> stack2, String unstack) {
        InverseStore store2;
        String last = stack2.peek();
        InverseStore store = inverseSearch2.get(last);
        if (store == null) {
            store = new InverseStore();
            inverseSearch2.put(last, store);
        }
        for (int k = stack2.size() - 2; k >= 0; k--) {
            String last2 = (String) stack2.get(k);
            int idx = store.part.indexOf(last2);
            if (idx < 0) {
                store.part.add(last2);
                store2 = new InverseStore();
                store.follow.add(store2);
            } else {
                store2 = (InverseStore) store.follow.get(idx);
            }
            store = store2;
        }
        store.part.add("");
        store.follow.add(unstack);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: com.itextpdf.forms.xfa.InverseStore} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String inverseSearchGlobal(java.util.List<java.lang.String> r7) {
        /*
            r6 = this;
            int r0 = r7.size()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.util.Map<java.lang.String, com.itextpdf.forms.xfa.InverseStore> r0 = r6.inverseSearch
            int r2 = r7.size()
            int r2 = r2 + -1
            java.lang.Object r2 = r7.get(r2)
            java.lang.Object r0 = r0.get(r2)
            com.itextpdf.forms.xfa.InverseStore r0 = (com.itextpdf.forms.xfa.InverseStore) r0
            if (r0 != 0) goto L_0x001d
            return r1
        L_0x001d:
            int r2 = r7.size()
            int r2 = r2 + -2
        L_0x0023:
            if (r2 < 0) goto L_0x004b
            java.lang.Object r3 = r7.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            java.util.List<java.lang.String> r4 = r0.part
            int r4 = r4.indexOf(r3)
            if (r4 >= 0) goto L_0x003f
            boolean r5 = r0.isSimilar(r3)
            if (r5 == 0) goto L_0x003a
            return r1
        L_0x003a:
            java.lang.String r1 = r0.getDefaultName()
            return r1
        L_0x003f:
            java.util.List<java.lang.Object> r5 = r0.follow
            java.lang.Object r5 = r5.get(r4)
            r0 = r5
            com.itextpdf.forms.xfa.InverseStore r0 = (com.itextpdf.forms.xfa.InverseStore) r0
            int r2 = r2 + -1
            goto L_0x0023
        L_0x004b:
            java.lang.String r1 = r0.getDefaultName()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.xfa.Xml2Som.inverseSearchGlobal(java.util.List):java.lang.String");
    }

    public static Stack<String> splitParts(String name) {
        int pos;
        while (name.startsWith(".")) {
            name = name.substring(1);
        }
        Stack<String> parts = new Stack<>();
        int last = 0;
        while (true) {
            int pos2 = last;
            while (true) {
                pos = name.indexOf(46, pos2);
                if (pos >= 0 && name.charAt(pos - 1) == '\\') {
                    pos2 = pos + 1;
                }
            }
            if (pos < 0) {
                break;
            }
            String part = name.substring(last, pos);
            if (!part.endsWith("]")) {
                part = part + "[0]";
            }
            parts.add(part);
            last = pos + 1;
        }
        String part2 = name.substring(last);
        if (!part2.endsWith("]")) {
            part2 = part2 + "[0]";
        }
        parts.add(part2);
        return parts;
    }

    public List<String> getOrder() {
        return this.order;
    }

    public void setOrder(List<String> order2) {
        this.order = order2;
    }

    public Map<String, Node> getName2Node() {
        return this.name2Node;
    }

    public void setName2Node(Map<String, Node> name2Node2) {
        this.name2Node = name2Node2;
    }

    public Map<String, InverseStore> getInverseSearch() {
        return this.inverseSearch;
    }

    public void setInverseSearch(Map<String, InverseStore> inverseSearch2) {
        this.inverseSearch = inverseSearch2;
    }
}

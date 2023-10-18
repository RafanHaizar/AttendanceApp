package com.itextpdf.forms.xfa;

import java.util.ArrayList;
import java.util.List;

class InverseStore {
    protected List<Object> follow = new ArrayList();
    protected List<String> part = new ArrayList();

    InverseStore() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.itextpdf.forms.xfa.InverseStore} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getDefaultName() {
        /*
            r3 = this;
            r0 = r3
        L_0x0001:
            java.util.List<java.lang.Object> r1 = r0.follow
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            boolean r2 = r1 instanceof java.lang.String
            if (r2 == 0) goto L_0x0010
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2
            return r2
        L_0x0010:
            r0 = r1
            com.itextpdf.forms.xfa.InverseStore r0 = (com.itextpdf.forms.xfa.InverseStore) r0
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.xfa.InverseStore.getDefaultName():java.lang.String");
    }

    public boolean isSimilar(String name) {
        String name2 = name.substring(0, name.indexOf(91) + 1);
        for (int k = 0; k < this.part.size(); k++) {
            if (this.part.get(k).startsWith(name2)) {
                return true;
            }
        }
        return false;
    }
}

package com.itextpdf.kernel.xmp.impl;

public class QName {
    private String localName;
    private String prefix;

    public QName(String qname) {
        int colon = qname.indexOf(58);
        if (colon >= 0) {
            this.prefix = qname.substring(0, colon);
            this.localName = qname.substring(colon + 1);
            return;
        }
        this.prefix = "";
        this.localName = qname;
    }

    public QName(String prefix2, String localName2) {
        this.prefix = prefix2;
        this.localName = localName2;
    }

    public boolean hasPrefix() {
        String str = this.prefix;
        return str != null && str.length() > 0;
    }

    public String getLocalName() {
        return this.localName;
    }

    public String getPrefix() {
        return this.prefix;
    }
}

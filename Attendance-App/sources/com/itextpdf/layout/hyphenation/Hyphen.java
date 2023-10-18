package com.itextpdf.layout.hyphenation;

import java.io.Serializable;

public class Hyphen implements Serializable {
    private static final long serialVersionUID = 8989909741110279085L;
    public String noBreak;
    public String postBreak;
    public String preBreak;

    Hyphen(String pre, String no, String post) {
        this.preBreak = pre;
        this.noBreak = no;
        this.postBreak = post;
    }

    Hyphen(String pre) {
        this.preBreak = pre;
        this.noBreak = null;
        this.postBreak = null;
    }

    public String toString() {
        String str;
        if (this.noBreak == null && this.postBreak == null && (str = this.preBreak) != null && str.equals("-")) {
            return "-";
        }
        StringBuffer res = new StringBuffer("{");
        res.append(this.preBreak);
        res.append("}{");
        res.append(this.postBreak);
        res.append("}{");
        res.append(this.noBreak);
        res.append('}');
        return res.toString();
    }
}

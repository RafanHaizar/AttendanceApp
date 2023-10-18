package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPException;

/* compiled from: ISO8601Converter */
class ParseState {
    private int pos = 0;
    private String str;

    public ParseState(String str2) {
        this.str = str2;
    }

    public int length() {
        return this.str.length();
    }

    public boolean hasNext() {
        return this.pos < this.str.length();
    }

    /* renamed from: ch */
    public char mo29238ch(int index) {
        if (index < this.str.length()) {
            return this.str.charAt(index);
        }
        return 0;
    }

    /* renamed from: ch */
    public char mo29237ch() {
        if (this.pos < this.str.length()) {
            return this.str.charAt(this.pos);
        }
        return 0;
    }

    public void skip() {
        this.pos++;
    }

    public int pos() {
        return this.pos;
    }

    public int gatherInt(String errorMsg, int maxValue) throws XMPException {
        int value = 0;
        boolean success = false;
        char ch = mo29238ch(this.pos);
        while ('0' <= ch && ch <= '9') {
            value = (value * 10) + (ch - '0');
            success = true;
            int i = this.pos + 1;
            this.pos = i;
            ch = mo29238ch(i);
        }
        if (!success) {
            throw new XMPException(errorMsg, 5);
        } else if (value > maxValue) {
            return maxValue;
        } else {
            if (value < 0) {
                return 0;
            }
            return value;
        }
    }
}

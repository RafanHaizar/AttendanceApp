package com.itextpdf.p026io.font.cmap;

import kotlin.UByte;

/* renamed from: com.itextpdf.io.font.cmap.CMapObject */
public class CMapObject {
    protected static final int ARRAY = 6;
    protected static final int DICTIONARY = 7;
    protected static final int HEX_STRING = 2;
    protected static final int LITERAL = 5;
    protected static final int NAME = 3;
    protected static final int NUMBER = 4;
    protected static final int STRING = 1;
    protected static final int TOKEN = 8;
    private int type;
    private Object value;

    public CMapObject(int objectType, Object value2) {
        this.type = objectType;
        this.value = value2;
    }

    public Object getValue() {
        return this.value;
    }

    public int getType() {
        return this.type;
    }

    public void setValue(Object value2) {
        this.value = value2;
    }

    public boolean isString() {
        int i = this.type;
        return i == 1 || i == 2;
    }

    public boolean isHexString() {
        return this.type == 2;
    }

    public boolean isName() {
        return this.type == 3;
    }

    public boolean isNumber() {
        return this.type == 4;
    }

    public boolean isLiteral() {
        return this.type == 5;
    }

    public boolean isArray() {
        return this.type == 6;
    }

    public boolean isDictionary() {
        return this.type == 7;
    }

    public boolean isToken() {
        return this.type == 8;
    }

    public String toString() {
        int i = this.type;
        if (i != 1 && i != 2) {
            return this.value.toString();
        }
        byte[] content = (byte[]) this.value;
        StringBuilder str = new StringBuilder(content.length);
        for (byte b : content) {
            str.append((char) (b & UByte.MAX_VALUE));
        }
        return str.toString();
    }

    public byte[] toHexByteArray() {
        if (this.type == 2) {
            return (byte[]) this.value;
        }
        return null;
    }
}

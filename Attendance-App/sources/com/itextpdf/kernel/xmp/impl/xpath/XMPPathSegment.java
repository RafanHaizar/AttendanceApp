package com.itextpdf.kernel.xmp.impl.xpath;

public class XMPPathSegment {
    private boolean alias;
    private int aliasForm;
    private int kind;
    private String name;

    public XMPPathSegment(String name2) {
        this.name = name2;
    }

    public XMPPathSegment(String name2, int kind2) {
        this.name = name2;
        this.kind = kind2;
    }

    public int getKind() {
        return this.kind;
    }

    public void setKind(int kind2) {
        this.kind = kind2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setAlias(boolean alias2) {
        this.alias = alias2;
    }

    public boolean isAlias() {
        return this.alias;
    }

    public int getAliasForm() {
        return this.aliasForm;
    }

    public void setAliasForm(int aliasForm2) {
        this.aliasForm = aliasForm2;
    }

    public String toString() {
        switch (this.kind) {
            case 1:
            case 2:
            case 3:
            case 4:
                return this.name;
            case 5:
            case 6:
                return this.name;
            default:
                return this.name;
        }
    }
}

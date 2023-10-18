package com.itextpdf.kernel;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 2410734474798313936L;
    private int major;
    private int minor;
    private String name;
    private int patch;
    private boolean snapshot;

    public ProductInfo(String name2, int major2, int minor2, int patch2, boolean snapshot2) {
        this.name = name2;
        this.major = major2;
        this.minor = minor2;
        this.patch = patch2;
        this.snapshot = snapshot2;
    }

    public String getName() {
        return this.name;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getPatch() {
        return this.patch;
    }

    public boolean isSnapshot() {
        return this.snapshot;
    }

    public String toString() {
        return this.name + "-" + this.major + "." + this.minor + "." + this.patch + (this.snapshot ? "-SNAPSHOT" : "");
    }
}

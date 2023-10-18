package com.itextpdf.p026io.font.otf;

import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.otf.GposAnchor */
public class GposAnchor implements Serializable {
    private static final long serialVersionUID = 7153858421411686094L;
    public int XCoordinate;
    public int YCoordinate;

    public GposAnchor() {
    }

    public GposAnchor(GposAnchor other) {
        this.XCoordinate = other.XCoordinate;
        this.YCoordinate = other.YCoordinate;
    }
}

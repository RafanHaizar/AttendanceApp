package com.itextpdf.p026io.image;

/* renamed from: com.itextpdf.io.image.PngChromaticities */
public class PngChromaticities {

    /* renamed from: xB */
    private float f1223xB;

    /* renamed from: xG */
    private float f1224xG;

    /* renamed from: xR */
    private float f1225xR;

    /* renamed from: xW */
    private float f1226xW;

    /* renamed from: yB */
    private float f1227yB;

    /* renamed from: yG */
    private float f1228yG;

    /* renamed from: yR */
    private float f1229yR;

    /* renamed from: yW */
    private float f1230yW;

    public PngChromaticities(float xW, float yW, float xR, float yR, float xG, float yG, float xB, float yB) {
        this.f1226xW = xW;
        this.f1230yW = yW;
        this.f1225xR = xR;
        this.f1229yR = yR;
        this.f1224xG = xG;
        this.f1228yG = yG;
        this.f1223xB = xB;
        this.f1227yB = yB;
    }

    public float getXW() {
        return this.f1226xW;
    }

    public float getYW() {
        return this.f1230yW;
    }

    public float getXR() {
        return this.f1225xR;
    }

    public float getYR() {
        return this.f1229yR;
    }

    public float getXG() {
        return this.f1224xG;
    }

    public float getYG() {
        return this.f1228yG;
    }

    public float getXB() {
        return this.f1223xB;
    }

    public float getYB() {
        return this.f1227yB;
    }
}

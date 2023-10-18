package com.itextpdf.forms.xfdf;

public class DestObject {
    private FitObject fit;
    private FitObject fitB;
    private FitObject fitBH;
    private FitObject fitBV;
    private FitObject fitH;
    private FitObject fitR;
    private FitObject fitV;
    private String name;
    private FitObject xyz;

    public String getName() {
        return this.name;
    }

    public DestObject setName(String name2) {
        this.name = name2;
        return this;
    }

    public FitObject getXyz() {
        return this.xyz;
    }

    public DestObject setXyz(FitObject xyz2) {
        this.xyz = xyz2;
        return this;
    }

    public FitObject getFit() {
        return this.fit;
    }

    public DestObject setFit(FitObject fit2) {
        this.fit = fit2;
        return this;
    }

    public FitObject getFitH() {
        return this.fitH;
    }

    public DestObject setFitH(FitObject fitH2) {
        this.fitH = fitH2;
        return this;
    }

    public FitObject getFitV() {
        return this.fitV;
    }

    public DestObject setFitV(FitObject fitV2) {
        this.fitV = fitV2;
        return this;
    }

    public FitObject getFitR() {
        return this.fitR;
    }

    public DestObject setFitR(FitObject fitR2) {
        this.fitR = fitR2;
        return this;
    }

    public FitObject getFitB() {
        return this.fitB;
    }

    public DestObject setFitB(FitObject fitB2) {
        this.fitB = fitB2;
        return this;
    }

    public FitObject getFitBH() {
        return this.fitBH;
    }

    public DestObject setFitBH(FitObject fitBH2) {
        this.fitBH = fitBH2;
        return this;
    }

    public FitObject getFitBV() {
        return this.fitBV;
    }

    public DestObject setFitBV(FitObject fitBV2) {
        this.fitBV = fitBV2;
        return this;
    }
}

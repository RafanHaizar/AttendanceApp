package com.itextpdf.layout.minmaxwidth;

import java.io.Serializable;

public class MinMaxWidth implements Serializable {
    private static final long serialVersionUID = -4642527900783929637L;
    private float additionalWidth;
    private float childrenMaxWidth;
    private float childrenMinWidth;

    public MinMaxWidth() {
        this(0.0f);
    }

    public MinMaxWidth(float additionalWidth2) {
        this(0.0f, 0.0f, additionalWidth2);
    }

    public MinMaxWidth(float childrenMinWidth2, float childrenMaxWidth2, float additionalWidth2) {
        this.childrenMinWidth = childrenMinWidth2;
        this.childrenMaxWidth = childrenMaxWidth2;
        this.additionalWidth = additionalWidth2;
    }

    public float getChildrenMinWidth() {
        return this.childrenMinWidth;
    }

    public void setChildrenMinWidth(float childrenMinWidth2) {
        this.childrenMinWidth = childrenMinWidth2;
    }

    public float getChildrenMaxWidth() {
        return this.childrenMaxWidth;
    }

    public void setChildrenMaxWidth(float childrenMaxWidth2) {
        this.childrenMaxWidth = childrenMaxWidth2;
    }

    public float getAdditionalWidth() {
        return this.additionalWidth;
    }

    public void setAdditionalWidth(float additionalWidth2) {
        this.additionalWidth = additionalWidth2;
    }

    public float getMaxWidth() {
        return Math.min(this.childrenMaxWidth + this.additionalWidth, MinMaxWidthUtils.getInfWidth());
    }

    public float getMinWidth() {
        return Math.min(this.childrenMinWidth + this.additionalWidth, getMaxWidth());
    }

    public String toString() {
        return "min=" + (this.childrenMinWidth + this.additionalWidth) + ", max=" + (this.childrenMaxWidth + this.additionalWidth);
    }
}

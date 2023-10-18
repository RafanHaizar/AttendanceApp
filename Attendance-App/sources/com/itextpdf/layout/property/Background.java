package com.itextpdf.layout.property;

import com.itextpdf.kernel.colors.Color;

public class Background {
    private BackgroundBox backgroundClip;
    protected float extraBottom;
    protected float extraLeft;
    protected float extraRight;
    protected float extraTop;
    protected TransparentColor transparentColor;

    public Background(Color color) {
        this(color, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Background(Color color, float opacity) {
        this(color, opacity, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Background(Color color, float extraLeft2, float extraTop2, float extraRight2, float extraBottom2) {
        this(color, 1.0f, extraLeft2, extraTop2, extraRight2, extraBottom2);
    }

    public Background(Color color, float opacity, float extraLeft2, float extraTop2, float extraRight2, float extraBottom2) {
        this.backgroundClip = BackgroundBox.BORDER_BOX;
        this.transparentColor = new TransparentColor(color, opacity);
        this.extraLeft = extraLeft2;
        this.extraRight = extraRight2;
        this.extraTop = extraTop2;
        this.extraBottom = extraBottom2;
    }

    public Background(Color color, float opacity, BackgroundBox clip) {
        this(color, opacity);
        this.backgroundClip = clip;
    }

    public Color getColor() {
        return this.transparentColor.getColor();
    }

    public float getOpacity() {
        return this.transparentColor.getOpacity();
    }

    public float getExtraLeft() {
        return this.extraLeft;
    }

    public float getExtraRight() {
        return this.extraRight;
    }

    public float getExtraTop() {
        return this.extraTop;
    }

    public float getExtraBottom() {
        return this.extraBottom;
    }

    public BackgroundBox getBackgroundClip() {
        return this.backgroundClip;
    }
}

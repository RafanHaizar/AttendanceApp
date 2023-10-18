package com.itextpdf.layout.property;

import com.itextpdf.kernel.colors.Color;

public class Underline {
    protected int lineCapStyle;
    protected float thickness;
    protected float thicknessMul;
    protected TransparentColor transparentColor;
    protected float yPosition;
    protected float yPositionMul;

    public Underline(Color color, float thickness2, float thicknessMul2, float yPosition2, float yPositionMul2, int lineCapStyle2) {
        this(color, 1.0f, thickness2, thicknessMul2, yPosition2, yPositionMul2, lineCapStyle2);
    }

    public Underline(Color color, float opacity, float thickness2, float thicknessMul2, float yPosition2, float yPositionMul2, int lineCapStyle2) {
        this.lineCapStyle = 0;
        this.transparentColor = new TransparentColor(color, opacity);
        this.thickness = thickness2;
        this.thicknessMul = thicknessMul2;
        this.yPosition = yPosition2;
        this.yPositionMul = yPositionMul2;
        this.lineCapStyle = lineCapStyle2;
    }

    public Color getColor() {
        return this.transparentColor.getColor();
    }

    public float getOpacity() {
        return this.transparentColor.getOpacity();
    }

    public float getThickness(float fontSize) {
        return this.thickness + (this.thicknessMul * fontSize);
    }

    public float getYPosition(float fontSize) {
        return this.yPosition + (this.yPositionMul * fontSize);
    }

    public float getYPositionMul() {
        return this.yPositionMul;
    }

    public int getLineCapStyle() {
        return this.lineCapStyle;
    }
}

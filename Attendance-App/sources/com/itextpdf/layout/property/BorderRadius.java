package com.itextpdf.layout.property;

public class BorderRadius {
    private UnitValue horizontalRadius;
    private UnitValue verticalRadius;

    public BorderRadius(UnitValue radius) {
        this.horizontalRadius = radius;
        this.verticalRadius = radius;
    }

    public BorderRadius(float radius) {
        UnitValue createPointValue = UnitValue.createPointValue(radius);
        this.horizontalRadius = createPointValue;
        this.verticalRadius = createPointValue;
    }

    public BorderRadius(UnitValue horizontalRadius2, UnitValue verticalRadius2) {
        this.horizontalRadius = horizontalRadius2;
        this.verticalRadius = verticalRadius2;
    }

    public BorderRadius(float horizontalRadius2, float verticalRadius2) {
        this.horizontalRadius = UnitValue.createPointValue(horizontalRadius2);
        this.verticalRadius = UnitValue.createPointValue(verticalRadius2);
    }

    public UnitValue getHorizontalRadius() {
        return this.horizontalRadius;
    }

    public UnitValue getVerticalRadius() {
        return this.verticalRadius;
    }
}

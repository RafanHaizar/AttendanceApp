package com.itextpdf.layout.property;

public class LineHeight {
    private static final int FIXED = 1;
    private static final int MULTIPLIED = 2;
    private static final int NORMAL = 4;
    private int type;
    private float value;

    private LineHeight(int type2, float value2) {
        this.type = type2;
        this.value = value2;
    }

    public float getValue() {
        return this.value;
    }

    public static LineHeight createFixedValue(float value2) {
        return new LineHeight(1, value2);
    }

    public static LineHeight createMultipliedValue(float value2) {
        return new LineHeight(2, value2);
    }

    public static LineHeight createNormalValue() {
        return new LineHeight(4, 0.0f);
    }

    public boolean isFixedValue() {
        return this.type == 1;
    }

    public boolean isMultipliedValue() {
        return this.type == 2;
    }

    public boolean isNormalValue() {
        return this.type == 4;
    }
}

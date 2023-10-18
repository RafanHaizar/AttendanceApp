package com.itextpdf.layout.property;

import com.itextpdf.p026io.util.MessageFormatUtil;

public class UnitValue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int PERCENT = 2;
    public static final int POINT = 1;
    protected int unitType;
    protected float value;

    public UnitValue(int unitType2, float value2) {
        this.unitType = unitType2;
        if (!Float.isNaN(value2)) {
            this.value = value2;
            return;
        }
        throw new AssertionError();
    }

    public UnitValue(UnitValue unitValue) {
        this(unitValue.unitType, unitValue.value);
    }

    public static UnitValue createPointValue(float value2) {
        return new UnitValue(1, value2);
    }

    public static UnitValue createPercentValue(float value2) {
        return new UnitValue(2, value2);
    }

    public static UnitValue[] createPercentArray(float[] values) {
        UnitValue[] resultArray = new UnitValue[values.length];
        float sum = 0.0f;
        for (float val : values) {
            sum += val;
        }
        for (int i = 0; i < values.length; i++) {
            resultArray[i] = createPercentValue((values[i] * 100.0f) / sum);
        }
        return resultArray;
    }

    public static UnitValue[] createPercentArray(int size) {
        UnitValue[] resultArray = new UnitValue[size];
        for (int i = 0; i < size; i++) {
            resultArray[i] = createPercentValue(100.0f / ((float) size));
        }
        return resultArray;
    }

    public static UnitValue[] createPointArray(float[] values) {
        UnitValue[] resultArray = new UnitValue[values.length];
        for (int i = 0; i < values.length; i++) {
            resultArray[i] = createPointValue(values[i]);
        }
        return resultArray;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public void setUnitType(int unitType2) {
        this.unitType = unitType2;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value2) {
        if (!Float.isNaN(value2)) {
            this.value = value2;
            return;
        }
        throw new AssertionError();
    }

    public boolean isPointValue() {
        return this.unitType == 1;
    }

    public boolean isPercentValue() {
        return this.unitType == 2;
    }

    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        UnitValue other = (UnitValue) obj;
        if (Integer.compare(this.unitType, other.unitType) == 0 && Float.compare(this.value, other.value) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((7 * 71) + this.unitType) * 71) + Float.floatToIntBits(this.value);
    }

    public String toString() {
        return MessageFormatUtil.format(this.unitType == 2 ? "{0}%" : "{0}pt", Float.valueOf(this.value));
    }
}

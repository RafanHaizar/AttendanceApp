package com.itextpdf.layout.property;

import java.util.Objects;

public class BackgroundPosition {
    private static final double EPS = 9.999999747378752E-5d;
    private static final int FULL_VALUE = 100;
    private static final int HALF_VALUE = 50;
    private PositionX positionX = PositionX.LEFT;
    private PositionY positionY = PositionY.TOP;
    private UnitValue xShift = new UnitValue(1, 0.0f);
    private UnitValue yShift = new UnitValue(1, 0.0f);

    public enum PositionX {
        LEFT,
        RIGHT,
        CENTER
    }

    public enum PositionY {
        TOP,
        BOTTOM,
        CENTER
    }

    public void calculatePositionValues(float fullWidth, float fullHeight, UnitValue outXValue, UnitValue outYValue) {
        UnitValue unitValue;
        UnitValue unitValue2;
        int posMultiplier = parsePositionXToUnitValueAndReturnMultiplier(outXValue);
        if (posMultiplier != 0 || (unitValue2 = this.xShift) == null || ((double) Math.abs(unitValue2.getValue())) <= EPS) {
            outXValue.setValue(calculateValue(outXValue, fullWidth) + (calculateValue(this.xShift, fullWidth) * ((float) posMultiplier)));
        } else {
            outXValue.setValue(0.0f);
        }
        outXValue.setUnitType(1);
        int posMultiplier2 = parsePositionYToUnitValueAndReturnMultiplier(outYValue);
        if (posMultiplier2 != 0 || (unitValue = this.yShift) == null || ((double) Math.abs(unitValue.getValue())) <= EPS) {
            outYValue.setValue(calculateValue(outYValue, fullHeight) + (calculateValue(this.yShift, fullHeight) * ((float) posMultiplier2)));
        } else {
            outYValue.setValue(0.0f);
        }
        outYValue.setUnitType(1);
    }

    public PositionX getPositionX() {
        return this.positionX;
    }

    public BackgroundPosition setPositionX(PositionX xPosition) {
        this.positionX = xPosition;
        return this;
    }

    public PositionY getPositionY() {
        return this.positionY;
    }

    public BackgroundPosition setPositionY(PositionY yPosition) {
        this.positionY = yPosition;
        return this;
    }

    public UnitValue getXShift() {
        return this.xShift;
    }

    public BackgroundPosition setXShift(UnitValue xShift2) {
        this.xShift = xShift2;
        return this;
    }

    public UnitValue getYShift() {
        return this.yShift;
    }

    public BackgroundPosition setYShift(UnitValue yShift2) {
        this.yShift = yShift2;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BackgroundPosition position = (BackgroundPosition) o;
        if (!Objects.equals(this.positionX, position.positionX) || !Objects.equals(this.positionY, position.positionY) || !Objects.equals(this.xShift, position.xShift) || !Objects.equals(this.yShift, position.yShift)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.positionX.ordinal()), Integer.valueOf(this.positionY.ordinal()), this.xShift, this.yShift});
    }

    private int parsePositionXToUnitValueAndReturnMultiplier(UnitValue outValue) {
        outValue.setUnitType(2);
        switch (C14671.f1536x6bce409c[this.positionX.ordinal()]) {
            case 1:
                outValue.setValue(0.0f);
                return 1;
            case 2:
                outValue.setValue(100.0f);
                return -1;
            case 3:
                outValue.setValue(50.0f);
                return 0;
            default:
                return 0;
        }
    }

    /* renamed from: com.itextpdf.layout.property.BackgroundPosition$1 */
    static /* synthetic */ class C14671 {

        /* renamed from: $SwitchMap$com$itextpdf$layout$property$BackgroundPosition$PositionX */
        static final /* synthetic */ int[] f1536x6bce409c;

        /* renamed from: $SwitchMap$com$itextpdf$layout$property$BackgroundPosition$PositionY */
        static final /* synthetic */ int[] f1537x6bce409d;

        static {
            int[] iArr = new int[PositionY.values().length];
            f1537x6bce409d = iArr;
            try {
                iArr[PositionY.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1537x6bce409d[PositionY.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1537x6bce409d[PositionY.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[PositionX.values().length];
            f1536x6bce409c = iArr2;
            try {
                iArr2[PositionX.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1536x6bce409c[PositionX.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1536x6bce409c[PositionX.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private int parsePositionYToUnitValueAndReturnMultiplier(UnitValue outValue) {
        outValue.setUnitType(2);
        switch (C14671.f1537x6bce409d[this.positionY.ordinal()]) {
            case 1:
                outValue.setValue(0.0f);
                return 1;
            case 2:
                outValue.setValue(100.0f);
                return -1;
            case 3:
                outValue.setValue(50.0f);
                return 0;
            default:
                return 0;
        }
    }

    private static float calculateValue(UnitValue value, float fullValue) {
        if (value == null) {
            return 0.0f;
        }
        return value.isPercentValue() ? (value.getValue() / 100.0f) * fullValue : value.getValue();
    }
}

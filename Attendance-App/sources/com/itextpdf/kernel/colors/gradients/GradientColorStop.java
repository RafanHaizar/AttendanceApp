package com.itextpdf.kernel.colors.gradients;

import java.util.Arrays;
import java.util.Objects;

public class GradientColorStop {
    private double hintOffset;
    private HintOffsetType hintOffsetType;
    private double offset;
    private OffsetType offsetType;
    private final float opacity;
    private final float[] rgb;

    public enum HintOffsetType {
        ABSOLUTE_ON_GRADIENT,
        RELATIVE_ON_GRADIENT,
        RELATIVE_BETWEEN_COLORS,
        NONE
    }

    public enum OffsetType {
        ABSOLUTE,
        AUTO,
        RELATIVE
    }

    public GradientColorStop(float[] rgb2) {
        this(rgb2, 1.0f, 0.0d, OffsetType.AUTO);
    }

    public GradientColorStop(float[] rgb2, double offset2, OffsetType offsetType2) {
        this(rgb2, 1.0f, offset2, offsetType2);
    }

    public GradientColorStop(GradientColorStop gradientColorStop, double offset2, OffsetType offsetType2) {
        this(gradientColorStop.getRgbArray(), gradientColorStop.getOpacity(), offset2, offsetType2);
    }

    private GradientColorStop(float[] rgb2, float opacity2, double offset2, OffsetType offsetType2) {
        this.hintOffset = 0.0d;
        this.hintOffsetType = HintOffsetType.NONE;
        this.rgb = copyRgbArray(rgb2);
        this.opacity = normalize(opacity2);
        setOffset(offset2, offsetType2);
    }

    public float[] getRgbArray() {
        return copyRgbArray(this.rgb);
    }

    private float getOpacity() {
        return this.opacity;
    }

    public OffsetType getOffsetType() {
        return this.offsetType;
    }

    public double getOffset() {
        return this.offset;
    }

    public double getHintOffset() {
        return this.hintOffset;
    }

    public HintOffsetType getHintOffsetType() {
        return this.hintOffsetType;
    }

    public GradientColorStop setOffset(double offset2, OffsetType offsetType2) {
        OffsetType offsetType3 = offsetType2 != null ? offsetType2 : OffsetType.AUTO;
        this.offsetType = offsetType3;
        this.offset = offsetType3 != OffsetType.AUTO ? offset2 : 0.0d;
        return this;
    }

    public GradientColorStop setHint(double hintOffset2, HintOffsetType hintOffsetType2) {
        HintOffsetType hintOffsetType3 = hintOffsetType2 != null ? hintOffsetType2 : HintOffsetType.NONE;
        this.hintOffsetType = hintOffsetType3;
        this.hintOffset = hintOffsetType3 != HintOffsetType.NONE ? hintOffset2 : 0.0d;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GradientColorStop that = (GradientColorStop) o;
        if (Float.compare(that.opacity, this.opacity) == 0 && Double.compare(that.offset, this.offset) == 0 && Double.compare(that.hintOffset, this.hintOffset) == 0 && Arrays.equals(this.rgb, that.rgb) && this.offsetType == that.offsetType && this.hintOffsetType == that.hintOffsetType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((Objects.hash(new Object[]{Float.valueOf(this.opacity), Double.valueOf(this.offset), Double.valueOf(this.hintOffset)}) * 31) + this.offsetType.hashCode()) * 31) + this.hintOffsetType.hashCode()) * 31) + Arrays.hashCode(this.rgb);
    }

    private static float normalize(float toNormalize) {
        if (toNormalize > 1.0f) {
            return 1.0f;
        }
        if (toNormalize > 0.0f) {
            return toNormalize;
        }
        return 0.0f;
    }

    private static float[] copyRgbArray(float[] toCopy) {
        if (toCopy == null || toCopy.length < 3) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        return new float[]{normalize(toCopy[0]), normalize(toCopy[1]), normalize(toCopy[2])};
    }
}

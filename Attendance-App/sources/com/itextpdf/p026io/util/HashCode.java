package com.itextpdf.p026io.util;

/* renamed from: com.itextpdf.io.util.HashCode */
public final class HashCode {
    public static final int EMPTY_HASH_CODE = 1;
    private int hashCode = 1;

    public final int hashCode() {
        return this.hashCode;
    }

    public static int combine(int hashCode2, boolean value) {
        return combine(hashCode2, value ? 1231 : 1237);
    }

    public static int combine(int hashCode2, long value) {
        return combine(hashCode2, (int) ((value >>> 32) ^ value));
    }

    public static int combine(int hashCode2, float value) {
        return combine(hashCode2, Float.floatToIntBits(value));
    }

    public static int combine(int hashCode2, double value) {
        return combine(hashCode2, Double.doubleToLongBits(value));
    }

    public static int combine(int hashCode2, Object value) {
        return combine(hashCode2, value.hashCode());
    }

    public static int combine(int hashCode2, int value) {
        return (hashCode2 * 31) + value;
    }

    public final HashCode append(int value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }

    public final HashCode append(long value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }

    public final HashCode append(float value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }

    public final HashCode append(double value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }

    public final HashCode append(boolean value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }

    public final HashCode append(Object value) {
        this.hashCode = combine(this.hashCode, value);
        return this;
    }
}

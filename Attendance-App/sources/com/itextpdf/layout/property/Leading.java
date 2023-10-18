package com.itextpdf.layout.property;

import java.util.Objects;

public class Leading {
    public static final int FIXED = 1;
    public static final int MULTIPLIED = 2;
    protected int type;
    protected float value;

    public Leading(int type2, float value2) {
        this.type = type2;
        this.value = value2;
    }

    public int getType() {
        return this.type;
    }

    public float getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && this.type == ((Leading) obj).type && this.value == ((Leading) obj).value;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.type), Float.valueOf(this.value)});
    }
}

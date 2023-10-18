package com.google.android.material.shape;

public class RoundedCornerTreatment extends CornerTreatment {
    float radius = -1.0f;

    public RoundedCornerTreatment() {
    }

    @Deprecated
    public RoundedCornerTreatment(float radius2) {
        this.radius = radius2;
    }

    public void getCornerPath(ShapePath shapePath, float angle, float interpolation, float radius2) {
        shapePath.reset(0.0f, radius2 * interpolation, 180.0f, 180.0f - angle);
        shapePath.addArc(0.0f, 0.0f, radius2 * 2.0f * interpolation, 2.0f * radius2 * interpolation, 180.0f, angle);
    }
}

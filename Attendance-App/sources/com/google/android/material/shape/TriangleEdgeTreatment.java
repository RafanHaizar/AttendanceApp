package com.google.android.material.shape;

public class TriangleEdgeTreatment extends EdgeTreatment {
    private final boolean inside;
    private final float size;

    public TriangleEdgeTreatment(float size2, boolean inside2) {
        this.size = size2;
        this.inside = inside2;
    }

    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        if (this.inside) {
            shapePath.lineTo(center - (this.size * interpolation), 0.0f);
            float f = this.size;
            shapePath.lineTo(center, f * interpolation, (f * interpolation) + center, 0.0f);
            shapePath.lineTo(length, 0.0f);
            return;
        }
        float f2 = this.size;
        shapePath.lineTo(center - (f2 * interpolation), 0.0f, center, (-f2) * interpolation);
        shapePath.lineTo((this.size * interpolation) + center, 0.0f, length, 0.0f);
    }
}

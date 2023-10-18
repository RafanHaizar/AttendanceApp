package com.google.android.material.shape;

public class CutCornerTreatment extends CornerTreatment {
    float size = -1.0f;

    public CutCornerTreatment() {
    }

    @Deprecated
    public CutCornerTreatment(float size2) {
        this.size = size2;
    }

    public void getCornerPath(ShapePath shapePath, float angle, float interpolation, float radius) {
        shapePath.reset(0.0f, radius * interpolation, 180.0f, 180.0f - angle);
        double sin = Math.sin(Math.toRadians((double) angle));
        double d = (double) radius;
        Double.isNaN(d);
        double d2 = sin * d;
        double d3 = (double) interpolation;
        Double.isNaN(d3);
        double sin2 = Math.sin(Math.toRadians((double) (90.0f - angle)));
        double d4 = (double) radius;
        Double.isNaN(d4);
        double d5 = sin2 * d4;
        double d6 = (double) interpolation;
        Double.isNaN(d6);
        shapePath.lineTo((float) (d2 * d3), (float) (d5 * d6));
    }
}

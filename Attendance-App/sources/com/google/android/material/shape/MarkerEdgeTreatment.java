package com.google.android.material.shape;

public final class MarkerEdgeTreatment extends EdgeTreatment {
    private final float radius;

    public MarkerEdgeTreatment(float radius2) {
        this.radius = radius2 - 0.001f;
    }

    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        double d = (double) this.radius;
        double sqrt = Math.sqrt(2.0d);
        Double.isNaN(d);
        float side = (float) ((d * sqrt) / 2.0d);
        float side2 = (float) Math.sqrt(Math.pow((double) this.radius, 2.0d) - Math.pow((double) side, 2.0d));
        double d2 = (double) this.radius;
        double sqrt2 = Math.sqrt(2.0d);
        Double.isNaN(d2);
        double d3 = d2 * sqrt2;
        double d4 = (double) this.radius;
        Double.isNaN(d4);
        shapePath.reset(center - side, ((float) (-(d3 - d4))) + side2);
        double d5 = (double) this.radius;
        double sqrt3 = Math.sqrt(2.0d);
        Double.isNaN(d5);
        double d6 = d5 * sqrt3;
        double d7 = (double) this.radius;
        Double.isNaN(d7);
        shapePath.lineTo(center, (float) (-(d6 - d7)));
        double d8 = (double) this.radius;
        double sqrt4 = Math.sqrt(2.0d);
        Double.isNaN(d8);
        double d9 = d8 * sqrt4;
        double d10 = (double) this.radius;
        Double.isNaN(d10);
        shapePath.lineTo(center + side, ((float) (-(d9 - d10))) + side2);
    }

    /* access modifiers changed from: package-private */
    public boolean forceIntersection() {
        return true;
    }
}

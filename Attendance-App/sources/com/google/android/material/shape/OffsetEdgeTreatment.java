package com.google.android.material.shape;

public final class OffsetEdgeTreatment extends EdgeTreatment {
    private final float offset;
    private final EdgeTreatment other;

    public OffsetEdgeTreatment(EdgeTreatment other2, float offset2) {
        this.other = other2;
        this.offset = offset2;
    }

    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        this.other.getEdgePath(length, center - this.offset, interpolation, shapePath);
    }

    /* access modifiers changed from: package-private */
    public boolean forceIntersection() {
        return this.other.forceIntersection();
    }
}

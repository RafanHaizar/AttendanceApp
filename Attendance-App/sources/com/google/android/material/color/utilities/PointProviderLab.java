package com.google.android.material.color.utilities;

public final class PointProviderLab implements PointProvider {
    public double[] fromInt(int argb) {
        double[] lab = ColorUtils.labFromArgb(argb);
        return new double[]{lab[0], lab[1], lab[2]};
    }

    public int toInt(double[] lab) {
        return ColorUtils.argbFromLab(lab[0], lab[1], lab[2]);
    }

    public double distance(double[] one, double[] two) {
        double dL = one[0] - two[0];
        double dA = one[1] - two[1];
        double dB = one[2] - two[2];
        return (dL * dL) + (dA * dA) + (dB * dB);
    }
}

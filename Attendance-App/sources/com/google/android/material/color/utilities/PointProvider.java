package com.google.android.material.color.utilities;

public interface PointProvider {
    double distance(double[] dArr, double[] dArr2);

    double[] fromInt(int i);

    int toInt(double[] dArr);
}

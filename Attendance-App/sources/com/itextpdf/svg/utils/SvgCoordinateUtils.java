package com.itextpdf.svg.utils;

import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;

public class SvgCoordinateUtils {
    public static String[] makeRelativeOperatorCoordinatesAbsolute(String[] relativeCoordinates, double[] currentCoordinates) {
        if (relativeCoordinates.length % currentCoordinates.length == 0) {
            String[] absoluteOperators = new String[relativeCoordinates.length];
            int i = 0;
            while (i < relativeCoordinates.length) {
                int j = 0;
                while (j < currentCoordinates.length) {
                    absoluteOperators[i] = SvgCssUtils.convertDoubleToString(Double.parseDouble(relativeCoordinates[i]) + currentCoordinates[j]);
                    j++;
                    i++;
                }
            }
            return absoluteOperators;
        }
        throw new IllegalArgumentException(SvgExceptionMessageConstant.f1649xcc3e6cee);
    }

    public static double calculateAngleBetweenTwoVectors(Vector vectorA, Vector vectorB) {
        double dot = (double) vectorA.dot(vectorB);
        double length = (double) vectorA.length();
        double length2 = (double) vectorB.length();
        Double.isNaN(length);
        Double.isNaN(length2);
        Double.isNaN(dot);
        return Math.acos(dot / (length * length2));
    }
}

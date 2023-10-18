package com.itextpdf.svg.renderers.path.impl;

public interface IOperatorConverter {
    String[] makeCoordinatesAbsolute(String[] strArr, double[] dArr);
}

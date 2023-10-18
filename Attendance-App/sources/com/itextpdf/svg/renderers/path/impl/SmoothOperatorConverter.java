package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.svg.utils.SvgCoordinateUtils;

/* compiled from: IOperatorConverter */
class SmoothOperatorConverter implements IOperatorConverter {
    SmoothOperatorConverter() {
    }

    public String[] makeCoordinatesAbsolute(String[] relativeCoordinates, double[] initialPoint) {
        String[] result = new String[relativeCoordinates.length];
        System.arraycopy(relativeCoordinates, 0, result, 0, 2);
        String[] relativeCoordinates2 = SvgCoordinateUtils.makeRelativeOperatorCoordinatesAbsolute(relativeCoordinates, initialPoint);
        System.arraycopy(relativeCoordinates2, 2, result, 2, relativeCoordinates2.length - 2);
        return result;
    }
}

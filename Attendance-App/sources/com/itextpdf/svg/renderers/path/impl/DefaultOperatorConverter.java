package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.svg.utils.SvgCoordinateUtils;

/* compiled from: IOperatorConverter */
class DefaultOperatorConverter implements IOperatorConverter {
    DefaultOperatorConverter() {
    }

    public String[] makeCoordinatesAbsolute(String[] relativeCoordinates, double[] initialPoint) {
        return SvgCoordinateUtils.makeRelativeOperatorCoordinatesAbsolute(relativeCoordinates, initialPoint);
    }
}

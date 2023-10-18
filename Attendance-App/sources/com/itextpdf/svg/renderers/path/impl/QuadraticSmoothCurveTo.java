package com.itextpdf.svg.renderers.path.impl;

public class QuadraticSmoothCurveTo extends QuadraticCurveTo {
    static final int ARGUMENT_SIZE = 2;

    public QuadraticSmoothCurveTo() {
        this(false);
    }

    public QuadraticSmoothCurveTo(boolean relative) {
        super(relative, new SmoothOperatorConverter());
    }
}

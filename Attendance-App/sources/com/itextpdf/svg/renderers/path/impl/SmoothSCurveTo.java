package com.itextpdf.svg.renderers.path.impl;

public class SmoothSCurveTo extends CurveTo {
    static final int ARGUMENT_SIZE = 4;

    public SmoothSCurveTo() {
        this(false);
    }

    public SmoothSCurveTo(boolean relative) {
        super(relative, new SmoothOperatorConverter());
    }
}

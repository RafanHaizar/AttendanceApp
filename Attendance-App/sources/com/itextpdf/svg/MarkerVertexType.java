package com.itextpdf.svg;

import com.itextpdf.svg.SvgConstants;

public enum MarkerVertexType {
    MARKER_START(SvgConstants.Attributes.MARKER_START),
    MARKER_MID(SvgConstants.Attributes.MARKER_MID),
    MARKER_END(SvgConstants.Attributes.MARKER_END);
    
    private final String name;

    private MarkerVertexType(String s) {
        this.name = s;
    }

    public String toString() {
        return this.name;
    }
}

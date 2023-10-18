package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Vector;

public interface ITextChunkLocation {
    float distParallelEnd();

    float distParallelStart();

    int distPerpendicular();

    float distanceFromEndOf(ITextChunkLocation iTextChunkLocation);

    float getCharSpaceWidth();

    Vector getEndLocation();

    Vector getStartLocation();

    boolean isAtWordBoundary(ITextChunkLocation iTextChunkLocation);

    int orientationMagnitude();

    boolean sameLine(ITextChunkLocation iTextChunkLocation);
}

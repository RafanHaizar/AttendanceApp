package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Vector;

class TextChunkLocationDefaultImp implements ITextChunkLocation {
    private static final float DIACRITICAL_MARKS_ALLOWED_VERTICAL_DEVIATION = 2.0f;
    private final float charSpaceWidth;
    private final float distParallelEnd;
    private final float distParallelStart;
    private final int distPerpendicular;
    private final Vector endLocation;
    private final int orientationMagnitude;
    private final Vector orientationVector;
    private final Vector startLocation;

    public TextChunkLocationDefaultImp(Vector startLocation2, Vector endLocation2, float charSpaceWidth2) {
        this.startLocation = startLocation2;
        this.endLocation = endLocation2;
        this.charSpaceWidth = charSpaceWidth2;
        Vector oVector = endLocation2.subtract(startLocation2);
        Vector normalize = (oVector.length() == 0.0f ? new Vector(1.0f, 0.0f, 0.0f) : oVector).normalize();
        this.orientationVector = normalize;
        this.orientationMagnitude = (int) (Math.atan2((double) normalize.get(1), (double) normalize.get(0)) * 1000.0d);
        this.distPerpendicular = (int) startLocation2.subtract(new Vector(0.0f, 0.0f, 1.0f)).cross(normalize).get(2);
        this.distParallelStart = normalize.dot(startLocation2);
        this.distParallelEnd = normalize.dot(endLocation2);
    }

    public int orientationMagnitude() {
        return this.orientationMagnitude;
    }

    public int distPerpendicular() {
        return this.distPerpendicular;
    }

    public float distParallelStart() {
        return this.distParallelStart;
    }

    public float distParallelEnd() {
        return this.distParallelEnd;
    }

    public Vector getStartLocation() {
        return this.startLocation;
    }

    public Vector getEndLocation() {
        return this.endLocation;
    }

    public float getCharSpaceWidth() {
        return this.charSpaceWidth;
    }

    public boolean sameLine(ITextChunkLocation as) {
        if (orientationMagnitude() != as.orientationMagnitude()) {
            return false;
        }
        float distPerpendicularDiff = (float) (distPerpendicular() - as.distPerpendicular());
        if (distPerpendicularDiff == 0.0f) {
            return true;
        }
        LineSegment mySegment = new LineSegment(this.startLocation, this.endLocation);
        LineSegment otherSegment = new LineSegment(as.getStartLocation(), as.getEndLocation());
        if (Math.abs(distPerpendicularDiff) > 2.0f) {
            return false;
        }
        if (mySegment.getLength() == 0.0f || otherSegment.getLength() == 0.0f) {
            return true;
        }
        return false;
    }

    public float distanceFromEndOf(ITextChunkLocation other) {
        return distParallelStart() - other.distParallelEnd();
    }

    public boolean isAtWordBoundary(ITextChunkLocation previous) {
        if (this.startLocation.equals(this.endLocation) || previous.getEndLocation().equals(previous.getStartLocation())) {
            return false;
        }
        float dist = distanceFromEndOf(previous);
        if (dist < 0.0f) {
            dist = previous.distanceFromEndOf(this);
            if (dist < 0.0f) {
                return false;
            }
        }
        if (dist > getCharSpaceWidth() / 2.0f) {
            return true;
        }
        return false;
    }

    static boolean containsMark(ITextChunkLocation baseLocation, ITextChunkLocation markLocation) {
        if (baseLocation.getStartLocation().get(0) > markLocation.getStartLocation().get(0) || baseLocation.getEndLocation().get(0) < markLocation.getEndLocation().get(0) || ((float) Math.abs(baseLocation.distPerpendicular() - markLocation.distPerpendicular())) > 2.0f) {
            return false;
        }
        return true;
    }
}

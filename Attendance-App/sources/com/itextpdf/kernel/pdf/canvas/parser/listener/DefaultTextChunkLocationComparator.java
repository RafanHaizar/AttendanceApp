package com.itextpdf.kernel.pdf.canvas.parser.listener;

import java.util.Comparator;

class DefaultTextChunkLocationComparator implements Comparator<ITextChunkLocation> {
    private boolean leftToRight;

    public DefaultTextChunkLocationComparator() {
        this(true);
    }

    public DefaultTextChunkLocationComparator(boolean leftToRight2) {
        this.leftToRight = true;
        this.leftToRight = leftToRight2;
    }

    public int compare(ITextChunkLocation first, ITextChunkLocation second) {
        if (first == second) {
            return 0;
        }
        int result = Integer.compare(first.orientationMagnitude(), second.orientationMagnitude());
        if (result != 0) {
            return result;
        }
        int distPerpendicularDiff = first.distPerpendicular() - second.distPerpendicular();
        if (distPerpendicularDiff != 0) {
            return distPerpendicularDiff;
        }
        if (this.leftToRight) {
            return Float.compare(first.distParallelStart(), second.distParallelStart());
        }
        return -Float.compare(first.distParallelEnd(), second.distParallelEnd());
    }
}

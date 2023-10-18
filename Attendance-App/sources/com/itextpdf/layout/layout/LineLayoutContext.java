package com.itextpdf.layout.layout;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
import java.util.List;

public class LineLayoutContext extends LayoutContext {
    private boolean floatOverflowedToNextPageWithNothing = false;
    private float textIndent;

    public LineLayoutContext(LayoutArea area, MarginsCollapseInfo marginsCollapseInfo, List<Rectangle> floatedRendererAreas, boolean clippedHeight) {
        super(area, marginsCollapseInfo, floatedRendererAreas, clippedHeight);
    }

    public LineLayoutContext(LayoutContext layoutContext) {
        super(layoutContext.area, layoutContext.marginsCollapseInfo, layoutContext.floatRendererAreas, layoutContext.clippedHeight);
    }

    public boolean isFloatOverflowedToNextPageWithNothing() {
        return this.floatOverflowedToNextPageWithNothing;
    }

    public LineLayoutContext setFloatOverflowedToNextPageWithNothing(boolean floatOverflowedToNextPageWithNothing2) {
        this.floatOverflowedToNextPageWithNothing = floatOverflowedToNextPageWithNothing2;
        return this;
    }

    public float getTextIndent() {
        return this.textIndent;
    }

    public LineLayoutContext setTextIndent(float textIndent2) {
        this.textIndent = textIndent2;
        return this;
    }
}

package com.itextpdf.layout.layout;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
import java.util.ArrayList;
import java.util.List;

public class LayoutContext {
    protected LayoutArea area;
    protected boolean clippedHeight;
    protected List<Rectangle> floatRendererAreas;
    protected MarginsCollapseInfo marginsCollapseInfo;

    public LayoutContext(LayoutArea area2) {
        this.floatRendererAreas = new ArrayList();
        this.clippedHeight = false;
        this.area = area2;
    }

    public LayoutContext(LayoutArea area2, MarginsCollapseInfo marginsCollapseInfo2) {
        this.floatRendererAreas = new ArrayList();
        this.clippedHeight = false;
        this.area = area2;
        this.marginsCollapseInfo = marginsCollapseInfo2;
    }

    public LayoutContext(LayoutArea area2, MarginsCollapseInfo marginsCollapseInfo2, List<Rectangle> floatedRendererAreas) {
        this(area2, marginsCollapseInfo2);
        if (floatedRendererAreas != null) {
            this.floatRendererAreas = floatedRendererAreas;
        }
    }

    public LayoutContext(LayoutArea area2, boolean clippedHeight2) {
        this(area2);
        this.clippedHeight = clippedHeight2;
    }

    public LayoutContext(LayoutArea area2, MarginsCollapseInfo marginsCollapseInfo2, List<Rectangle> floatedRendererAreas, boolean clippedHeight2) {
        this(area2, marginsCollapseInfo2);
        if (floatedRendererAreas != null) {
            this.floatRendererAreas = floatedRendererAreas;
        }
        this.clippedHeight = clippedHeight2;
    }

    public LayoutArea getArea() {
        return this.area;
    }

    public MarginsCollapseInfo getMarginsCollapseInfo() {
        return this.marginsCollapseInfo;
    }

    public List<Rectangle> getFloatRendererAreas() {
        return this.floatRendererAreas;
    }

    public boolean isClippedHeight() {
        return this.clippedHeight;
    }

    public void setClippedHeight(boolean clippedHeight2) {
        this.clippedHeight = clippedHeight2;
    }

    public String toString() {
        return this.area.toString();
    }
}

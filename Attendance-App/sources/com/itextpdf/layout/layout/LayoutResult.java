package com.itextpdf.layout.layout;

import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.renderer.IRenderer;

public class LayoutResult {
    public static final int FULL = 1;
    public static final int NOTHING = 3;
    public static final int PARTIAL = 2;
    protected AreaBreak areaBreak;
    protected IRenderer causeOfNothing;
    protected LayoutArea occupiedArea;
    protected IRenderer overflowRenderer;
    protected IRenderer splitRenderer;
    protected int status;

    public LayoutResult(int status2, LayoutArea occupiedArea2, IRenderer splitRenderer2, IRenderer overflowRenderer2) {
        this(status2, occupiedArea2, splitRenderer2, overflowRenderer2, (IRenderer) null);
    }

    public LayoutResult(int status2, LayoutArea occupiedArea2, IRenderer splitRenderer2, IRenderer overflowRenderer2, IRenderer cause) {
        this.status = status2;
        this.occupiedArea = occupiedArea2;
        this.splitRenderer = splitRenderer2;
        this.overflowRenderer = overflowRenderer2;
        this.causeOfNothing = cause;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public LayoutArea getOccupiedArea() {
        return this.occupiedArea;
    }

    public IRenderer getSplitRenderer() {
        return this.splitRenderer;
    }

    public void setSplitRenderer(IRenderer splitRenderer2) {
        this.splitRenderer = splitRenderer2;
    }

    public IRenderer getOverflowRenderer() {
        return this.overflowRenderer;
    }

    public void setOverflowRenderer(IRenderer overflowRenderer2) {
        this.overflowRenderer = overflowRenderer2;
    }

    public AreaBreak getAreaBreak() {
        return this.areaBreak;
    }

    public LayoutResult setAreaBreak(AreaBreak areaBreak2) {
        this.areaBreak = areaBreak2;
        return this;
    }

    public IRenderer getCauseOfNothing() {
        return this.causeOfNothing;
    }

    public String toString() {
        String status2;
        switch (getStatus()) {
            case 1:
                status2 = "Full";
                break;
            case 2:
                status2 = "Partial";
                break;
            case 3:
                status2 = "Nothing";
                break;
            default:
                status2 = "None";
                break;
        }
        return "LayoutResult{" + status2 + ", areaBreak=" + this.areaBreak + ", occupiedArea=" + this.occupiedArea + '}';
    }
}

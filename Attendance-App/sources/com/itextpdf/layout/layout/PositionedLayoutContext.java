package com.itextpdf.layout.layout;

public class PositionedLayoutContext extends LayoutContext {
    private LayoutArea parentOccupiedArea;

    public PositionedLayoutContext(LayoutArea area, LayoutArea parentOccupiedArea2) {
        super(area);
        this.parentOccupiedArea = parentOccupiedArea2;
    }

    public LayoutArea getParentOccupiedArea() {
        return this.parentOccupiedArea;
    }
}

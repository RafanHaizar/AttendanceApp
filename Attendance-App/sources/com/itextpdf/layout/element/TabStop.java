package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.property.TabAlignment;

public class TabStop {
    private TabAlignment tabAlignment;
    private Character tabAnchor;
    private ILineDrawer tabLeader;
    private float tabPosition;

    public TabStop(float tabPosition2) {
        this(tabPosition2, TabAlignment.LEFT);
    }

    public TabStop(float tabPosition2, TabAlignment tabAlignment2) {
        this(tabPosition2, tabAlignment2, (ILineDrawer) null);
    }

    public TabStop(float tabPosition2, TabAlignment tabAlignment2, ILineDrawer tabLeader2) {
        this.tabPosition = tabPosition2;
        this.tabAlignment = tabAlignment2;
        this.tabLeader = tabLeader2;
        this.tabAnchor = '.';
    }

    public float getTabPosition() {
        return this.tabPosition;
    }

    public TabAlignment getTabAlignment() {
        return this.tabAlignment;
    }

    public void setTabAlignment(TabAlignment tabAlignment2) {
        this.tabAlignment = tabAlignment2;
    }

    public Character getTabAnchor() {
        return this.tabAnchor;
    }

    public void setTabAnchor(Character tabAnchor2) {
        this.tabAnchor = tabAnchor2;
    }

    public ILineDrawer getTabLeader() {
        return this.tabLeader;
    }

    public void setTabLeader(ILineDrawer tabLeader2) {
        this.tabLeader = tabLeader2;
    }
}

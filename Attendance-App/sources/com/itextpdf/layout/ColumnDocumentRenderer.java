package com.itextpdf.layout;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.RootLayoutArea;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;

public class ColumnDocumentRenderer extends DocumentRenderer {
    protected Rectangle[] columns;
    protected int nextAreaNumber;

    public ColumnDocumentRenderer(Document document, Rectangle[] columns2) {
        super(document);
        this.columns = columns2;
    }

    public ColumnDocumentRenderer(Document document, boolean immediateFlush, Rectangle[] columns2) {
        super(document, immediateFlush);
        this.columns = columns2;
    }

    public int getNextAreaNumber() {
        return this.nextAreaNumber;
    }

    public IRenderer getNextRenderer() {
        return new ColumnDocumentRenderer(this.document, this.immediateFlush, this.columns);
    }

    /* access modifiers changed from: protected */
    public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
        if (!(overflowResult == null || overflowResult.getAreaBreak() == null || overflowResult.getAreaBreak().getType() == AreaBreakType.NEXT_AREA)) {
            this.nextAreaNumber = 0;
        }
        if (this.nextAreaNumber % this.columns.length == 0) {
            super.updateCurrentArea(overflowResult);
        }
        int i = this.currentPageNumber;
        Rectangle[] rectangleArr = this.columns;
        int i2 = this.nextAreaNumber;
        this.nextAreaNumber = i2 + 1;
        RootLayoutArea rootLayoutArea = new RootLayoutArea(i, rectangleArr[i2 % rectangleArr.length].clone());
        this.currentArea = rootLayoutArea;
        return rootLayoutArea;
    }
}

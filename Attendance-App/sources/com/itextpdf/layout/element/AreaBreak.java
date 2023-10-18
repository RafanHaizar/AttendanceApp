package com.itextpdf.layout.element;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.IRenderer;

public class AreaBreak extends AbstractElement<AreaBreak> {
    protected PageSize pageSize;

    public AreaBreak() {
        this(AreaBreakType.NEXT_AREA);
    }

    public AreaBreak(AreaBreakType areaBreakType) {
        setProperty(2, areaBreakType);
    }

    public AreaBreak(PageSize pageSize2) {
        this(AreaBreakType.NEXT_PAGE);
        this.pageSize = pageSize2;
    }

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize2) {
        this.pageSize = pageSize2;
    }

    public AreaBreakType getType() {
        return (AreaBreakType) getProperty(2);
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new AreaBreakRenderer(this);
    }
}

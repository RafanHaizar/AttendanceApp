package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Cell extends BlockElement<Cell> {
    private static final Border DEFAULT_BORDER = new SolidBorder(0.5f);
    private int col;
    private int colspan;
    private int row;
    private int rowspan;
    protected DefaultAccessibilityProperties tagProperties;

    public Cell(int rowspan2, int colspan2) {
        this.rowspan = Math.max(rowspan2, 1);
        this.colspan = Math.max(colspan2, 1);
    }

    public Cell() {
        this(1, 1);
    }

    /* JADX WARNING: type inference failed for: r1v7, types: [com.itextpdf.layout.renderer.IRenderer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.renderer.IRenderer getRenderer() {
        /*
            r3 = this;
            r0 = 0
            com.itextpdf.layout.renderer.IRenderer r1 = r3.nextRenderer
            if (r1 == 0) goto L_0x0024
            com.itextpdf.layout.renderer.IRenderer r1 = r3.nextRenderer
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.CellRenderer
            if (r1 == 0) goto L_0x0019
            com.itextpdf.layout.renderer.IRenderer r1 = r3.nextRenderer
            com.itextpdf.layout.renderer.IRenderer r2 = r3.nextRenderer
            com.itextpdf.layout.renderer.IRenderer r2 = r2.getNextRenderer()
            r3.nextRenderer = r2
            r0 = r1
            com.itextpdf.layout.renderer.CellRenderer r0 = (com.itextpdf.layout.renderer.CellRenderer) r0
            goto L_0x0024
        L_0x0019:
            java.lang.Class<com.itextpdf.layout.element.Table> r1 = com.itextpdf.layout.element.Table.class
            org.slf4j.Logger r1 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r1)
            java.lang.String r2 = "Invalid renderer for Table: must be inherited from TableRenderer"
            r1.error(r2)
        L_0x0024:
            if (r0 != 0) goto L_0x002b
            com.itextpdf.layout.renderer.IRenderer r1 = r3.makeNewRenderer()
            goto L_0x002c
        L_0x002b:
            r1 = r0
        L_0x002c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.element.Cell.getRenderer():com.itextpdf.layout.renderer.IRenderer");
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public int getColspan() {
        return this.colspan;
    }

    public Cell add(IBlockElement element) {
        this.childElements.add(element);
        return this;
    }

    public Cell add(Image element) {
        this.childElements.add(element);
        return this;
    }

    public Cell clone(boolean includeContent) {
        Cell newCell = new Cell(this.rowspan, this.colspan);
        newCell.row = this.row;
        newCell.col = this.col;
        newCell.properties = new HashMap(this.properties);
        if (this.styles != null) {
            newCell.styles = new LinkedHashSet(this.styles);
        }
        if (includeContent) {
            newCell.childElements = new ArrayList(this.childElements);
        }
        return newCell;
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 9:
                return DEFAULT_BORDER;
            case 47:
            case 48:
            case 49:
            case 50:
                return UnitValue.createPointValue(2.0f);
            default:
                return super.getDefaultProperty(property);
        }
    }

    public String toString() {
        return MessageFormatUtil.format("Cell[row={0}, col={1}, rowspan={2}, colspan={3}]", Integer.valueOf(this.row), Integer.valueOf(this.col), Integer.valueOf(this.rowspan), Integer.valueOf(this.colspan));
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.f1515TD);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new CellRenderer(this);
    }

    /* access modifiers changed from: protected */
    public Cell updateCellIndexes(int row2, int col2, int numberOfColumns) {
        this.row = row2;
        this.col = col2;
        this.colspan = Math.min(this.colspan, numberOfColumns - col2);
        return this;
    }
}

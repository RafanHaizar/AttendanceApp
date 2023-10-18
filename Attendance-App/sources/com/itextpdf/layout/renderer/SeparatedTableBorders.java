package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

class SeparatedTableBorders extends TableBorders {
    public SeparatedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders) {
        super(rows, numberOfColumns, tableBoundingBorders);
    }

    public SeparatedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders, int largeTableIndexOffset) {
        super(rows, numberOfColumns, tableBoundingBorders, largeTableIndexOffset);
    }

    /* access modifiers changed from: protected */
    public TableBorders drawHorizontalBorder(int i, float startX, float y1, PdfCanvas canvas, float[] countedColumnWidth) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders drawVerticalBorder(int i, float startY, float x1, PdfCanvas canvas, List<Float> list) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
        return applyTopTableBorder(occupiedBox, layoutBox, reverse);
    }

    /* access modifiers changed from: protected */
    public TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
        float topIndent = ((float) (reverse ? -1 : 1)) * getMaxTopWidth();
        layoutBox.decreaseHeight(topIndent);
        occupiedBox.moveDown(topIndent).increaseHeight(topIndent);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
        return applyBottomTableBorder(occupiedBox, layoutBox, reverse);
    }

    /* access modifiers changed from: protected */
    public TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
        float bottomTableBorderWidth = ((float) (reverse ? -1 : 1)) * getMaxBottomWidth();
        layoutBox.decreaseHeight(bottomTableBorderWidth);
        occupiedBox.moveDown(bottomTableBorderWidth).increaseHeight(bottomTableBorderWidth);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyLeftAndRightTableBorder(Rectangle layoutBox, boolean reverse) {
        if (layoutBox != null) {
            layoutBox.applyMargins(0.0f, this.rightBorderMaxWidth, 0.0f, this.leftBorderMaxWidth, reverse);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders skipFooter(Border[] borders) {
        setTableBoundingBorders(borders);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders skipHeader(Border[] borders) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders collapseTableWithFooter(TableBorders footerBordersHandler, boolean hasContent) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders collapseTableWithHeader(TableBorders headerBordersHandler, boolean updateBordersHandler) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders fixHeaderOccupiedArea(Rectangle occupiedBox, Rectangle layoutBox) {
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyCellIndents(Rectangle box, float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
        box.applyMargins(topIndent, rightIndent, bottomIndent, leftIndent, false);
        return this;
    }

    public List<Border> getVerticalBorder(int index) {
        return (List) this.verticalBorders.get(index);
    }

    public List<Border> getHorizontalBorder(int index) {
        return (List) this.horizontalBorders.get(index - this.largeTableIndexOffset);
    }

    /* access modifiers changed from: protected */
    public float getCellVerticalAddition(float[] indents) {
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public TableBorders updateBordersOnNewPage(boolean isOriginalNonSplitRenderer, boolean isFooterOrHeader, TableRenderer currentRenderer, TableRenderer headerRenderer, TableRenderer footerRenderer) {
        if (!isFooterOrHeader && isOriginalNonSplitRenderer && this.rows != null) {
            processAllBordersAndEmptyRows();
            this.rightBorderMaxWidth = getMaxRightWidth();
            this.leftBorderMaxWidth = getMaxLeftWidth();
        }
        if (footerRenderer != null) {
            float rightFooterBorderWidth = footerRenderer.bordersHandler.getMaxRightWidth();
            this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, footerRenderer.bordersHandler.getMaxLeftWidth());
            this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightFooterBorderWidth);
        }
        if (headerRenderer != null) {
            float rightHeaderBorderWidth = headerRenderer.bordersHandler.getMaxRightWidth();
            this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, headerRenderer.bordersHandler.getMaxLeftWidth());
            this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightHeaderBorderWidth);
        }
        return this;
    }

    public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
        float[] indents = new float[4];
        Border[] borders = ((CellRenderer[]) this.rows.get((this.startRow + row) - this.largeTableIndexOffset))[col].getBorders();
        for (int i = 0; i < 4; i++) {
            if (borders[i] != null) {
                indents[i] = borders[i].getWidth();
            }
        }
        return indents;
    }

    /* access modifiers changed from: protected */
    public void buildBordersArrays(CellRenderer cell, int row, int col, int[] rowspansToDeduct) {
        int rowspan;
        CellRenderer cellRenderer = cell;
        int i = row;
        int colspan = cellRenderer.getPropertyAsInteger(16).intValue();
        int rowspan2 = cellRenderer.getPropertyAsInteger(60).intValue();
        int colN = ((Cell) cell.getModelElement()).getCol();
        Border[] cellBorders = cell.getBorders();
        if ((i + 1) - rowspan2 < 0) {
            rowspan = i + 1;
        } else {
            rowspan = rowspan2;
        }
        for (int i2 = 0; i2 < colspan; i2++) {
            checkAndReplaceBorderInArray(this.horizontalBorders, ((i + 1) - rowspan) * 2, colN + i2, cellBorders[0], false);
        }
        for (int i3 = 0; i3 < colspan; i3++) {
            checkAndReplaceBorderInArray(this.horizontalBorders, (i * 2) + 1, colN + i3, cellBorders[2], true);
        }
        for (int j = (i - rowspan) + 1; j <= i; j++) {
            checkAndReplaceBorderInArray(this.verticalBorders, colN * 2, j, cellBorders[3], false);
        }
        for (int i4 = (i - rowspan) + 1; i4 <= i; i4++) {
            checkAndReplaceBorderInArray(this.verticalBorders, ((colN + colspan) * 2) - 1, i4, cellBorders[1], true);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkAndReplaceBorderInArray(List<List<Border>> borderArray, int i, int j, Border borderToAdd, boolean hasPriority) {
        List<Border> borders = borderArray.get(i);
        if (borders.get(j) == null) {
            borders.set(j, borderToAdd);
            return true;
        }
        LoggerFactory.getLogger((Class<?>) TableRenderer.class).warn(LogMessageConstant.UNEXPECTED_BEHAVIOUR_DURING_TABLE_ROW_COLLAPSING);
        return true;
    }

    /* access modifiers changed from: protected */
    public TableBorders initializeBorders() {
        while (Math.max(this.numberOfColumns, 1) * 2 > this.verticalBorders.size()) {
            List<Border> tempBorders = new ArrayList<>();
            while (Math.max(this.rows.size(), 1) * 2 > tempBorders.size()) {
                tempBorders.add((Object) null);
            }
            this.verticalBorders.add(tempBorders);
        }
        while (Math.max(this.rows.size(), 1) * 2 > this.horizontalBorders.size()) {
            List<Border> tempBorders2 = new ArrayList<>();
            while (this.numberOfColumns > tempBorders2.size()) {
                tempBorders2.add((Object) null);
            }
            this.horizontalBorders.add(tempBorders2);
        }
        return this;
    }

    public List<Border> getFirstHorizontalBorder() {
        return getHorizontalBorder(this.startRow * 2);
    }

    public List<Border> getLastHorizontalBorder() {
        return getHorizontalBorder((this.finishRow * 2) + 1);
    }

    public float getMaxTopWidth() {
        if (this.tableBoundingBorders[0] == null) {
            return 0.0f;
        }
        return this.tableBoundingBorders[0].getWidth();
    }

    public float getMaxBottomWidth() {
        if (this.tableBoundingBorders[2] == null) {
            return 0.0f;
        }
        return this.tableBoundingBorders[2].getWidth();
    }

    public float getMaxRightWidth() {
        if (this.tableBoundingBorders[1] == null) {
            return 0.0f;
        }
        return this.tableBoundingBorders[1].getWidth();
    }

    public float getMaxLeftWidth() {
        if (this.tableBoundingBorders[3] == null) {
            return 0.0f;
        }
        return this.tableBoundingBorders[3].getWidth();
    }
}

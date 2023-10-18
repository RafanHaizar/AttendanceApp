package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

abstract class TableBorders {
    protected int finishRow;
    protected List<List<Border>> horizontalBorders;
    protected int largeTableIndexOffset;
    protected float leftBorderMaxWidth;
    protected final int numberOfColumns;
    protected float rightBorderMaxWidth;
    protected List<CellRenderer[]> rows;
    protected int startRow;
    protected Border[] tableBoundingBorders;
    protected List<List<Border>> verticalBorders;

    /* access modifiers changed from: protected */
    public abstract TableBorders applyBottomTableBorder(Rectangle rectangle, Rectangle rectangle2, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders applyBottomTableBorder(Rectangle rectangle, Rectangle rectangle2, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public abstract TableBorders applyCellIndents(Rectangle rectangle, float f, float f2, float f3, float f4, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders applyLeftAndRightTableBorder(Rectangle rectangle, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders applyTopTableBorder(Rectangle rectangle, Rectangle rectangle2, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders applyTopTableBorder(Rectangle rectangle, Rectangle rectangle2, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public abstract void buildBordersArrays(CellRenderer cellRenderer, int i, int i2, int[] iArr);

    /* access modifiers changed from: protected */
    public abstract TableBorders collapseTableWithFooter(TableBorders tableBorders, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders collapseTableWithHeader(TableBorders tableBorders, boolean z);

    /* access modifiers changed from: protected */
    public abstract TableBorders drawHorizontalBorder(int i, float f, float f2, PdfCanvas pdfCanvas, float[] fArr);

    /* access modifiers changed from: protected */
    public abstract TableBorders drawVerticalBorder(int i, float f, float f2, PdfCanvas pdfCanvas, List<Float> list);

    /* access modifiers changed from: protected */
    public abstract TableBorders fixHeaderOccupiedArea(Rectangle rectangle, Rectangle rectangle2);

    /* access modifiers changed from: protected */
    public abstract float getCellVerticalAddition(float[] fArr);

    public abstract List<Border> getHorizontalBorder(int i);

    public abstract List<Border> getVerticalBorder(int i);

    /* access modifiers changed from: protected */
    public abstract TableBorders skipFooter(Border[] borderArr);

    /* access modifiers changed from: protected */
    public abstract TableBorders skipHeader(Border[] borderArr);

    /* access modifiers changed from: protected */
    public abstract TableBorders updateBordersOnNewPage(boolean z, boolean z2, TableRenderer tableRenderer, TableRenderer tableRenderer2, TableRenderer tableRenderer3);

    public TableBorders(List<CellRenderer[]> rows2, int numberOfColumns2, Border[] tableBoundingBorders2) {
        this.horizontalBorders = new ArrayList();
        this.verticalBorders = new ArrayList();
        this.tableBoundingBorders = new Border[4];
        this.largeTableIndexOffset = 0;
        this.rows = rows2;
        this.numberOfColumns = numberOfColumns2;
        setTableBoundingBorders(tableBoundingBorders2);
    }

    public TableBorders(List<CellRenderer[]> rows2, int numberOfColumns2, Border[] tableBoundingBorders2, int largeTableIndexOffset2) {
        this(rows2, numberOfColumns2, tableBoundingBorders2);
        this.largeTableIndexOffset = largeTableIndexOffset2;
    }

    /* access modifiers changed from: protected */
    public TableBorders processAllBordersAndEmptyRows() {
        int[] rowspansToDeduct = new int[this.numberOfColumns];
        int numOfRowsToRemove = 0;
        if (!this.rows.isEmpty()) {
            int row = this.startRow - this.largeTableIndexOffset;
            while (row <= this.finishRow - this.largeTableIndexOffset) {
                CellRenderer[] currentRow = this.rows.get(row);
                boolean hasCells = false;
                int col = 0;
                while (col < this.numberOfColumns) {
                    if (currentRow[col] != null) {
                        int colspan = currentRow[col].getPropertyAsInteger(16).intValue();
                        if (rowspansToDeduct[col] > 0) {
                            int rowspan = currentRow[col].getPropertyAsInteger(60).intValue() - rowspansToDeduct[col];
                            if (rowspan < 1) {
                                LoggerFactory.getLogger((Class<?>) TableRenderer.class).warn(LogMessageConstant.UNEXPECTED_BEHAVIOUR_DURING_TABLE_ROW_COLLAPSING);
                                rowspan = 1;
                            }
                            currentRow[col].setProperty(60, Integer.valueOf(rowspan));
                            if (numOfRowsToRemove != 0) {
                                removeRows(row - numOfRowsToRemove, numOfRowsToRemove);
                                row -= numOfRowsToRemove;
                                numOfRowsToRemove = 0;
                            }
                        }
                        buildBordersArrays(currentRow[col], row, col, rowspansToDeduct);
                        hasCells = true;
                        for (int i = 0; i < colspan; i++) {
                            rowspansToDeduct[col + i] = 0;
                        }
                        col += colspan - 1;
                    } else if (this.horizontalBorders.get(row).size() <= col) {
                        this.horizontalBorders.get(row).add((Object) null);
                    }
                    col++;
                }
                if (!hasCells) {
                    if (row == this.rows.size() - 1) {
                        removeRows(row - rowspansToDeduct[0], rowspansToDeduct[0]);
                        this.rows.remove(row - rowspansToDeduct[0]);
                        setFinishRow(this.finishRow - 1);
                        LoggerFactory.getLogger((Class<?>) TableRenderer.class).warn(LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE);
                    } else {
                        for (int i2 = 0; i2 < this.numberOfColumns; i2++) {
                            rowspansToDeduct[i2] = rowspansToDeduct[i2] + 1;
                        }
                        numOfRowsToRemove++;
                    }
                }
                row++;
            }
        }
        int row2 = this.finishRow;
        int i3 = this.startRow;
        if (row2 < i3) {
            setFinishRow(i3);
        }
        return this;
    }

    private void removeRows(int startRow2, int numOfRows) {
        for (int row = startRow2; row < startRow2 + numOfRows; row++) {
            this.rows.remove(startRow2);
            this.horizontalBorders.remove(startRow2 + 1);
            for (int j = 0; j <= this.numberOfColumns; j++) {
                this.verticalBorders.get(j).remove(startRow2 + 1);
            }
        }
        setFinishRow(this.finishRow - numOfRows);
    }

    /* access modifiers changed from: protected */
    public TableBorders initializeBorders() {
        while (this.numberOfColumns + 1 > this.verticalBorders.size()) {
            List<Border> tempBorders = new ArrayList<>();
            while (Math.max(this.rows.size(), 1) > tempBorders.size()) {
                tempBorders.add((Object) null);
            }
            this.verticalBorders.add(tempBorders);
        }
        while (Math.max(this.rows.size(), 1) + 1 > this.horizontalBorders.size()) {
            List<Border> tempBorders2 = new ArrayList<>();
            while (this.numberOfColumns > tempBorders2.size()) {
                tempBorders2.add((Object) null);
            }
            this.horizontalBorders.add(tempBorders2);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders setTableBoundingBorders(Border[] borders) {
        this.tableBoundingBorders = new Border[4];
        if (borders != null) {
            for (int i = 0; i < borders.length; i++) {
                this.tableBoundingBorders[i] = borders[i];
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders setRowRange(int startRow2, int finishRow2) {
        this.startRow = startRow2;
        this.finishRow = finishRow2;
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders setStartRow(int row) {
        this.startRow = row;
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders setFinishRow(int row) {
        this.finishRow = row;
        return this;
    }

    public float getLeftBorderMaxWidth() {
        return this.leftBorderMaxWidth;
    }

    public float getRightBorderMaxWidth() {
        return this.rightBorderMaxWidth;
    }

    public float getMaxTopWidth() {
        Border widestBorder = getWidestHorizontalBorder(this.startRow);
        if (widestBorder == null || widestBorder.getWidth() < 0.0f) {
            return 0.0f;
        }
        return widestBorder.getWidth();
    }

    public float getMaxBottomWidth() {
        Border widestBorder = getWidestHorizontalBorder(this.finishRow + 1);
        if (widestBorder == null || widestBorder.getWidth() < 0.0f) {
            return 0.0f;
        }
        return widestBorder.getWidth();
    }

    public float getMaxRightWidth() {
        Border widestBorder = getWidestVerticalBorder(this.verticalBorders.size() - 1);
        if (widestBorder == null || widestBorder.getWidth() < 0.0f) {
            return 0.0f;
        }
        return widestBorder.getWidth();
    }

    public float getMaxLeftWidth() {
        Border widestBorder = getWidestVerticalBorder(0);
        if (widestBorder == null || widestBorder.getWidth() < 0.0f) {
            return 0.0f;
        }
        return widestBorder.getWidth();
    }

    public Border getWidestVerticalBorder(int col) {
        return TableBorderUtil.getWidestBorder(getVerticalBorder(col));
    }

    public Border getWidestVerticalBorder(int col, int start, int end) {
        return TableBorderUtil.getWidestBorder(getVerticalBorder(col), start, end);
    }

    public Border getWidestHorizontalBorder(int row) {
        return TableBorderUtil.getWidestBorder(getHorizontalBorder(row));
    }

    public Border getWidestHorizontalBorder(int row, int start, int end) {
        return TableBorderUtil.getWidestBorder(getHorizontalBorder(row), start, end);
    }

    public List<Border> getFirstHorizontalBorder() {
        return getHorizontalBorder(this.startRow);
    }

    public List<Border> getLastHorizontalBorder() {
        return getHorizontalBorder(this.finishRow + 1);
    }

    public List<Border> getFirstVerticalBorder() {
        return getVerticalBorder(0);
    }

    public List<Border> getLastVerticalBorder() {
        return getVerticalBorder(this.verticalBorders.size() - 1);
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getFinishRow() {
        return this.finishRow;
    }

    public Border[] getTableBoundingBorders() {
        return this.tableBoundingBorders;
    }

    public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
        int i;
        float[] indents = new float[4];
        List<Border> borderList = getHorizontalBorder(((this.startRow + row) - rowspan) + 1);
        for (int i2 = col; i2 < col + colspan; i2++) {
            Border border = borderList.get(i2);
            if (border != null && border.getWidth() > indents[0]) {
                indents[0] = border.getWidth();
            }
        }
        List<Border> borderList2 = getVerticalBorder(col + colspan);
        int i3 = ((this.startRow + row) - rowspan) + 1;
        while (true) {
            i = this.startRow;
            if (i3 >= i + row + 1) {
                break;
            }
            Border border2 = borderList2.get(i3);
            if (border2 != null && border2.getWidth() > indents[1]) {
                indents[1] = border2.getWidth();
            }
            i3++;
        }
        List<Border> borderList3 = getHorizontalBorder(i + row + 1);
        for (int i4 = col; i4 < col + colspan; i4++) {
            Border border3 = borderList3.get(i4);
            if (border3 != null && border3.getWidth() > indents[2]) {
                indents[2] = border3.getWidth();
            }
        }
        List<Border> borderList4 = getVerticalBorder(col);
        for (int i5 = ((this.startRow + row) - rowspan) + 1; i5 < this.startRow + row + 1; i5++) {
            Border border4 = borderList4.get(i5);
            if (border4 != null && border4.getWidth() > indents[3]) {
                indents[3] = border4.getWidth();
            }
        }
        return indents;
    }
}

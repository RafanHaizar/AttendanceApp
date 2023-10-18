package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.util.ArrayList;
import java.util.List;

class CollapsedTableBorders extends TableBorders {
    private List<Border> bottomBorderCollapseWith = new ArrayList();
    private List<Border> topBorderCollapseWith = new ArrayList();

    public CollapsedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders) {
        super(rows, numberOfColumns, tableBoundingBorders);
    }

    public CollapsedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders, int largeTableIndexOffset) {
        super(rows, numberOfColumns, tableBoundingBorders, largeTableIndexOffset);
    }

    public List<Border> getTopBorderCollapseWith() {
        return this.topBorderCollapseWith;
    }

    public List<Border> getBottomBorderCollapseWith() {
        return this.bottomBorderCollapseWith;
    }

    public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
        float[] indents = new float[4];
        List<Border> borderList = getHorizontalBorder(((this.startRow + row) - rowspan) + 1);
        for (int i = col; i < col + colspan; i++) {
            Border border = borderList.get(i);
            if (border != null && border.getWidth() > indents[0]) {
                indents[0] = border.getWidth();
            }
        }
        List<Border> borderList2 = getVerticalBorder(col + colspan);
        for (int i2 = (((this.startRow - this.largeTableIndexOffset) + row) - rowspan) + 1; i2 < (this.startRow - this.largeTableIndexOffset) + row + 1; i2++) {
            Border border2 = borderList2.get(i2);
            if (border2 != null && border2.getWidth() > indents[1]) {
                indents[1] = border2.getWidth();
            }
        }
        List<Border> borderList3 = getHorizontalBorder(this.startRow + row + 1);
        for (int i3 = col; i3 < col + colspan; i3++) {
            Border border3 = borderList3.get(i3);
            if (border3 != null && border3.getWidth() > indents[2]) {
                indents[2] = border3.getWidth();
            }
        }
        List<Border> borderList4 = getVerticalBorder(col);
        for (int i4 = (((this.startRow - this.largeTableIndexOffset) + row) - rowspan) + 1; i4 < (this.startRow - this.largeTableIndexOffset) + row + 1; i4++) {
            Border border4 = borderList4.get(i4);
            if (border4 != null && border4.getWidth() > indents[3]) {
                indents[3] = border4.getWidth();
            }
        }
        return indents;
    }

    public List<Border> getVerticalBorder(int index) {
        if (index == 0) {
            return getCollapsedList((List) this.verticalBorders.get(0), TableBorderUtil.createAndFillBorderList((List<Border>) null, this.tableBoundingBorders[3], ((List) this.verticalBorders.get(0)).size()));
        } else if (index != this.numberOfColumns) {
            return (List) this.verticalBorders.get(index);
        } else {
            return getCollapsedList((List) this.verticalBorders.get(this.verticalBorders.size() - 1), TableBorderUtil.createAndFillBorderList((List<Border>) null, this.tableBoundingBorders[1], ((List) this.verticalBorders.get(0)).size()));
        }
    }

    public List<Border> getHorizontalBorder(int index) {
        if (index == this.startRow) {
            List<Border> firstBorderOnCurrentPage = TableBorderUtil.createAndFillBorderList(this.topBorderCollapseWith, this.tableBoundingBorders[0], this.numberOfColumns);
            if (index == this.largeTableIndexOffset) {
                return getCollapsedList((List) this.horizontalBorders.get(index - this.largeTableIndexOffset), firstBorderOnCurrentPage);
            }
            if (this.rows.size() != 0) {
                int col = 0;
                int row = index;
                while (col < this.numberOfColumns) {
                    if (((CellRenderer[]) this.rows.get(row - this.largeTableIndexOffset))[col] == null || (row - index) + 1 > ((Cell) ((CellRenderer[]) this.rows.get(row - this.largeTableIndexOffset))[col].getModelElement()).getRowspan()) {
                        row++;
                        if (row == this.rows.size()) {
                            break;
                        }
                    } else {
                        CellRenderer cell = ((CellRenderer[]) this.rows.get(row - this.largeTableIndexOffset))[col];
                        Border cellModelTopBorder = TableBorderUtil.getCellSideBorder((Cell) cell.getModelElement(), 13);
                        int colspan = cell.getPropertyAsInteger(16).intValue();
                        if (firstBorderOnCurrentPage.get(col) == null || (cellModelTopBorder != null && cellModelTopBorder.getWidth() > firstBorderOnCurrentPage.get(col).getWidth())) {
                            for (int i = col; i < col + colspan; i++) {
                                firstBorderOnCurrentPage.set(i, cellModelTopBorder);
                            }
                        }
                        col += colspan;
                        row = index;
                    }
                }
            }
            return firstBorderOnCurrentPage;
        } else if (index != this.finishRow + 1) {
            return (List) this.horizontalBorders.get(index - this.largeTableIndexOffset);
        } else {
            List<Border> lastBorderOnCurrentPage = TableBorderUtil.createAndFillBorderList(this.bottomBorderCollapseWith, this.tableBoundingBorders[2], this.numberOfColumns);
            if (index - this.largeTableIndexOffset == this.horizontalBorders.size() - 1) {
                return getCollapsedList((List) this.horizontalBorders.get(index - this.largeTableIndexOffset), lastBorderOnCurrentPage);
            }
            if (this.rows.size() != 0) {
                int col2 = 0;
                int row2 = index - 1;
                while (col2 < this.numberOfColumns) {
                    if (((CellRenderer[]) this.rows.get(row2 - this.largeTableIndexOffset))[col2] != null) {
                        CellRenderer cell2 = ((CellRenderer[]) this.rows.get(row2 - this.largeTableIndexOffset))[col2];
                        Border cellModelBottomBorder = TableBorderUtil.getCellSideBorder((Cell) cell2.getModelElement(), 10);
                        int colspan2 = cell2.getPropertyAsInteger(16).intValue();
                        if (lastBorderOnCurrentPage.get(col2) == null || (cellModelBottomBorder != null && cellModelBottomBorder.getWidth() > lastBorderOnCurrentPage.get(col2).getWidth())) {
                            for (int i2 = col2; i2 < col2 + colspan2; i2++) {
                                lastBorderOnCurrentPage.set(i2, cellModelBottomBorder);
                            }
                        }
                        col2 += colspan2;
                        row2 = index - 1;
                    } else {
                        row2++;
                        if (row2 == this.rows.size()) {
                            break;
                        }
                    }
                }
            }
            return lastBorderOnCurrentPage;
        }
    }

    public CollapsedTableBorders setTopBorderCollapseWith(List<Border> topBorderCollapseWith2) {
        ArrayList arrayList = new ArrayList();
        this.topBorderCollapseWith = arrayList;
        if (topBorderCollapseWith2 != null) {
            arrayList.addAll(topBorderCollapseWith2);
        }
        return this;
    }

    public CollapsedTableBorders setBottomBorderCollapseWith(List<Border> bottomBorderCollapseWith2) {
        ArrayList arrayList = new ArrayList();
        this.bottomBorderCollapseWith = arrayList;
        if (bottomBorderCollapseWith2 != null) {
            arrayList.addAll(bottomBorderCollapseWith2);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b1 A[LOOP:2: B:26:0x00ad->B:28:0x00b1, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void buildBordersArrays(com.itextpdf.layout.renderer.CellRenderer r12, int r13, int r14, int[] r15) {
        /*
            r11 = this;
            java.util.List r0 = r11.horizontalBorders
            int r0 = r0.size()
            if (r13 <= r0) goto L_0x000a
            int r13 = r13 + -1
        L_0x000a:
            r0 = 16
            java.lang.Integer r1 = r12.getPropertyAsInteger(r0)
            int r1 = r1.intValue()
            r2 = 0
            r3 = 60
            r4 = 1
            if (r14 == 0) goto L_0x00b9
            java.util.List r5 = r11.rows
            java.lang.Object r5 = r5.get(r13)
            com.itextpdf.layout.renderer.CellRenderer[] r5 = (com.itextpdf.layout.renderer.CellRenderer[]) r5
            int r6 = r14 + -1
            r5 = r5[r6]
            if (r5 != 0) goto L_0x00b9
            r5 = r14
        L_0x0029:
            int r5 = r5 + -1
            r6 = r13
        L_0x002c:
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r7 == r6) goto L_0x0043
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            r7 = r7[r5]
            if (r7 != 0) goto L_0x0043
            int r6 = r6 + 1
            goto L_0x002c
        L_0x0043:
            if (r5 <= 0) goto L_0x007c
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r7 == r6) goto L_0x007c
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            r7 = r7[r5]
            java.lang.Integer r7 = r7.getPropertyAsInteger(r0)
            int r7 = r7.intValue()
            int r7 = r7 + r5
            if (r7 != r14) goto L_0x0029
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            r7 = r7[r5]
            java.lang.Integer r7 = r7.getPropertyAsInteger(r3)
            int r7 = r7.intValue()
            int r7 = r6 - r7
            int r7 = r7 + r4
            r8 = r15[r5]
            int r7 = r7 + r8
            if (r7 != r13) goto L_0x0029
        L_0x007c:
            if (r5 < 0) goto L_0x00b9
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r6 == r7) goto L_0x00b9
            if (r6 <= r13) goto L_0x00b9
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            r7 = r7[r5]
            java.lang.Integer r8 = r7.getPropertyAsInteger(r3)
            int r8 = r8.intValue()
            r9 = r15[r5]
            int r8 = r8 - r9
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r7.setProperty(r3, r8)
            java.lang.Integer r8 = r7.getPropertyAsInteger(r0)
            int r8 = r8.intValue()
            r9 = r5
        L_0x00ad:
            int r10 = r5 + r8
            if (r9 >= r10) goto L_0x00b6
            r15[r9] = r2
            int r9 = r9 + 1
            goto L_0x00ad
        L_0x00b6:
            r11.buildBordersArrays(r7, r6, r4)
        L_0x00b9:
            r5 = 0
        L_0x00ba:
            if (r5 >= r1) goto L_0x0105
            int r6 = r13 + 1
        L_0x00be:
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r6 >= r7) goto L_0x00d7
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            int r8 = r14 + r5
            r7 = r7[r8]
            if (r7 != 0) goto L_0x00d7
            int r6 = r6 + 1
            goto L_0x00be
        L_0x00d7:
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r6 != r7) goto L_0x00e0
            goto L_0x0105
        L_0x00e0:
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            int r8 = r14 + r5
            r7 = r7[r8]
            java.lang.Integer r8 = r7.getPropertyAsInteger(r3)
            int r8 = r8.intValue()
            int r8 = r6 - r8
            if (r13 != r8) goto L_0x00fb
            r11.buildBordersArrays(r7, r6, r4)
        L_0x00fb:
            java.lang.Integer r8 = r7.getPropertyAsInteger(r0)
            int r8 = r8.intValue()
            int r5 = r5 + r8
            goto L_0x00ba
        L_0x0105:
            int r6 = r14 + r1
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r13)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            int r7 = r7.length
            if (r6 >= r7) goto L_0x016b
            r6 = r13
        L_0x0113:
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r6 >= r7) goto L_0x012c
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            int r8 = r14 + r1
            r7 = r7[r8]
            if (r7 != 0) goto L_0x012c
            int r6 = r6 + 1
            goto L_0x0113
        L_0x012c:
            java.util.List r7 = r11.rows
            int r7 = r7.size()
            if (r6 == r7) goto L_0x016b
            java.util.List r7 = r11.rows
            java.lang.Object r7 = r7.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r7 = (com.itextpdf.layout.renderer.CellRenderer[]) r7
            int r8 = r14 + r1
            r7 = r7[r8]
            java.lang.Integer r8 = r7.getPropertyAsInteger(r3)
            int r8 = r8.intValue()
            int r9 = r14 + r1
            r9 = r15[r9]
            int r8 = r8 - r9
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r7.setProperty(r3, r8)
            java.lang.Integer r0 = r7.getPropertyAsInteger(r0)
            int r0 = r0.intValue()
            int r3 = r14 + r1
        L_0x015e:
            int r8 = r14 + r1
            int r8 = r8 + r0
            if (r3 >= r8) goto L_0x0168
            r15[r3] = r2
            int r3 = r3 + 1
            goto L_0x015e
        L_0x0168:
            r11.buildBordersArrays(r7, r6, r4)
        L_0x016b:
            r11.buildBordersArrays(r12, r13, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.CollapsedTableBorders.buildBordersArrays(com.itextpdf.layout.renderer.CellRenderer, int, int, int[]):void");
    }

    /* access modifiers changed from: protected */
    public void buildBordersArrays(CellRenderer cell, int row, boolean isNeighbourCell) {
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
            checkAndReplaceBorderInArray(this.horizontalBorders, (i + 1) - rowspan, colN + i2, cellBorders[0], false);
        }
        for (int i3 = 0; i3 < colspan; i3++) {
            checkAndReplaceBorderInArray(this.horizontalBorders, i + 1, colN + i3, cellBorders[2], true);
        }
        for (int j = (i - rowspan) + 1; j <= i; j++) {
            checkAndReplaceBorderInArray(this.verticalBorders, colN, j, cellBorders[3], false);
        }
        for (int i4 = (i - rowspan) + 1; i4 <= i; i4++) {
            checkAndReplaceBorderInArray(this.verticalBorders, colN + colspan, i4, cellBorders[1], true);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkAndReplaceBorderInArray(List<List<Border>> borderArray, int i, int j, Border borderToAdd, boolean hasPriority) {
        List<Border> borders = borderArray.get(i);
        Border neighbour = borders.get(j);
        if (neighbour == null) {
            borders.set(j, borderToAdd);
            return true;
        } else if (neighbour == borderToAdd || borderToAdd == null || neighbour.getWidth() > borderToAdd.getWidth()) {
            return false;
        } else {
            if (!hasPriority && neighbour.getWidth() == borderToAdd.getWidth()) {
                return false;
            }
            borders.set(j, borderToAdd);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public TableBorders drawHorizontalBorder(int i, float startX, float y1, PdfCanvas canvas, float[] countedColumnWidth) {
        Border firstBorder;
        int i2 = i;
        List<Border> borders = getHorizontalBorder(this.startRow + i2);
        float x1 = startX;
        float x2 = countedColumnWidth[0] + x1;
        if (i2 == 0) {
            Border firstBorder2 = getFirstVerticalBorder().get(this.startRow - this.largeTableIndexOffset);
            if (firstBorder2 != null) {
                x1 -= firstBorder2.getWidth() / 2.0f;
            }
        } else if (i2 == (this.finishRow - this.startRow) + 1 && (firstBorder = getFirstVerticalBorder().get(((((this.startRow - this.largeTableIndexOffset) + this.finishRow) - this.startRow) + 1) - 1)) != null) {
            x1 -= firstBorder.getWidth() / 2.0f;
        }
        int j = 1;
        while (j < borders.size()) {
            Border prevBorder = borders.get(j - 1);
            Border curBorder = borders.get(j);
            if (prevBorder == null) {
                x1 += countedColumnWidth[j - 1];
                x2 = x1;
            } else if (!prevBorder.equals(curBorder)) {
                prevBorder.drawCellBorder(canvas, x1, y1, x2, y1, Border.Side.NONE);
                x1 = x2;
            }
            if (curBorder != null) {
                x2 += countedColumnWidth[j];
            }
            j++;
        }
        Border lastBorder = borders.size() > j + -1 ? borders.get(j - 1) : null;
        if (lastBorder != null) {
            if (i2 == 0) {
                if (getVerticalBorder(j).get((this.startRow - this.largeTableIndexOffset) + i2) != null) {
                    x2 += getVerticalBorder(j).get((this.startRow - this.largeTableIndexOffset) + i2).getWidth() / 2.0f;
                }
            } else if (i2 == (this.finishRow - this.startRow) + 1 && getVerticalBorder(j).size() > ((this.startRow - this.largeTableIndexOffset) + i2) - 1 && getVerticalBorder(j).get(((this.startRow - this.largeTableIndexOffset) + i2) - 1) != null) {
                x2 += getVerticalBorder(j).get(((this.startRow - this.largeTableIndexOffset) + i2) - 1).getWidth() / 2.0f;
            }
            lastBorder.drawCellBorder(canvas, x1, y1, x2, y1, Border.Side.NONE);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders drawVerticalBorder(int i, float startY, float x1, PdfCanvas canvas, List<Float> heights) {
        Border lastBorder;
        List<Float> list = heights;
        List<Border> borders = getVerticalBorder(i);
        float y1 = startY;
        float y2 = y1;
        if (!heights.isEmpty()) {
            y2 = y1 - list.get(0).floatValue();
        }
        int j = 1;
        while (j < heights.size()) {
            Border prevBorder = borders.get(((this.startRow - this.largeTableIndexOffset) + j) - 1);
            Border curBorder = borders.get((this.startRow - this.largeTableIndexOffset) + j);
            if (prevBorder == null) {
                y1 -= list.get(j - 1).floatValue();
                y2 = y1;
            } else if (!prevBorder.equals(curBorder)) {
                prevBorder.drawCellBorder(canvas, x1, y1, x1, y2, Border.Side.NONE);
                y1 = y2;
            }
            if (curBorder != null) {
                y2 -= list.get(j).floatValue();
            }
            j++;
        }
        if (!(borders.size() == 0 || (lastBorder = borders.get(((this.startRow - this.largeTableIndexOffset) + j) - 1)) == null)) {
            lastBorder.drawCellBorder(canvas, x1, y1, x1, y2, Border.Side.NONE);
        }
        return this;
    }

    public static Border getCollapsedBorder(Border cellBorder, Border tableBorder) {
        if (tableBorder != null && (cellBorder == null || cellBorder.getWidth() < tableBorder.getWidth())) {
            return tableBorder;
        }
        if (cellBorder != null) {
            return cellBorder;
        }
        return Border.NO_BORDER;
    }

    public static List<Border> getCollapsedList(List<Border> innerList, List<Border> outerList) {
        int i = 0;
        int size = innerList == null ? 0 : innerList.size();
        if (outerList != null) {
            i = outerList.size();
        }
        int size2 = Math.min(size, i);
        List<Border> collapsedList = new ArrayList<>();
        for (int i2 = 0; i2 < size2; i2++) {
            collapsedList.add(getCollapsedBorder(innerList.get(i2), outerList.get(i2)));
        }
        return collapsedList;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyLeftAndRightTableBorder(Rectangle layoutBox, boolean reverse) {
        if (layoutBox != null) {
            layoutBox.applyMargins(0.0f, this.rightBorderMaxWidth / 2.0f, 0.0f, this.leftBorderMaxWidth / 2.0f, reverse);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
        if (!isEmpty) {
            return applyTopTableBorder(occupiedBox, layoutBox, reverse);
        }
        if (!force) {
            return this;
        }
        applyTopTableBorder(occupiedBox, layoutBox, reverse);
        return applyTopTableBorder(occupiedBox, layoutBox, reverse);
    }

    /* access modifiers changed from: protected */
    public TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
        if (!isEmpty) {
            return applyBottomTableBorder(occupiedBox, layoutBox, reverse);
        }
        if (!force) {
            return this;
        }
        applyBottomTableBorder(occupiedBox, layoutBox, reverse);
        return applyBottomTableBorder(occupiedBox, layoutBox, reverse);
    }

    /* access modifiers changed from: protected */
    public TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
        float topIndent = ((float) (reverse ? -1 : 1)) * getMaxTopWidth();
        layoutBox.decreaseHeight(topIndent / 2.0f);
        occupiedBox.moveDown(topIndent / 2.0f).increaseHeight(topIndent / 2.0f);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
        float bottomTableBorderWidth = ((float) (reverse ? -1 : 1)) * getMaxBottomWidth();
        layoutBox.decreaseHeight(bottomTableBorderWidth / 2.0f);
        occupiedBox.moveDown(bottomTableBorderWidth / 2.0f).increaseHeight(bottomTableBorderWidth / 2.0f);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders applyCellIndents(Rectangle box, float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
        box.applyMargins(topIndent / 2.0f, rightIndent / 2.0f, bottomIndent / 2.0f, leftIndent / 2.0f, false);
        return this;
    }

    /* access modifiers changed from: protected */
    public float getCellVerticalAddition(float[] indents) {
        return (indents[0] / 2.0f) + (indents[2] / 2.0f);
    }

    /* access modifiers changed from: protected */
    public TableBorders updateBordersOnNewPage(boolean isOriginalNonSplitRenderer, boolean isFooterOrHeader, TableRenderer currentRenderer, TableRenderer headerRenderer, TableRenderer footerRenderer) {
        if (!isFooterOrHeader) {
            if (isOriginalNonSplitRenderer) {
                if (this.rows != null) {
                    processAllBordersAndEmptyRows();
                    this.rightBorderMaxWidth = getMaxRightWidth();
                    this.leftBorderMaxWidth = getMaxLeftWidth();
                }
                setTopBorderCollapseWith(((Table) currentRenderer.getModelElement()).getLastRowBottomBorder());
            } else {
                setTopBorderCollapseWith((List<Border>) null);
                setBottomBorderCollapseWith((List<Border>) null);
            }
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

    /* access modifiers changed from: protected */
    public TableBorders skipFooter(Border[] borders) {
        setTableBoundingBorders(borders);
        setBottomBorderCollapseWith((List<Border>) null);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders skipHeader(Border[] borders) {
        setTableBoundingBorders(borders);
        setTopBorderCollapseWith((List<Border>) null);
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders collapseTableWithFooter(TableBorders footerBordersHandler, boolean hasContent) {
        ((CollapsedTableBorders) footerBordersHandler).setTopBorderCollapseWith(hasContent ? getLastHorizontalBorder() : getTopBorderCollapseWith());
        setBottomBorderCollapseWith(footerBordersHandler.getHorizontalBorder(0));
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders collapseTableWithHeader(TableBorders headerBordersHandler, boolean updateBordersHandler) {
        ((CollapsedTableBorders) headerBordersHandler).setBottomBorderCollapseWith(getHorizontalBorder(this.startRow));
        if (updateBordersHandler) {
            setTopBorderCollapseWith(headerBordersHandler.getLastHorizontalBorder());
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public TableBorders fixHeaderOccupiedArea(Rectangle occupiedBox, Rectangle layoutBox) {
        float topBorderMaxWidth = getMaxTopWidth();
        layoutBox.increaseHeight(topBorderMaxWidth);
        occupiedBox.moveUp(topBorderMaxWidth).decreaseHeight(topBorderMaxWidth);
        return this;
    }
}

package com.itextpdf.layout.renderer;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.ArrayUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

final class TableWidths {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final UnitValue ZeroWidth = UnitValue.createPointValue(0.0f);
    private List<CellInfo> cells;
    private boolean fixedTableLayout = false;
    private boolean fixedTableWidth;
    private final float horizontalBorderSpacing;
    private float layoutMinWidth;
    private final float leftBorderMaxWidth;
    private final int numberOfColumns;
    private final float rightBorderMaxWidth;
    private float tableMaxWidth;
    private float tableMinWidth;
    private final TableRenderer tableRenderer;
    private float tableWidth;
    private final ColumnWidthData[] widths;

    TableWidths(TableRenderer tableRenderer2, float availableWidth, boolean calculateTableMaxWidth, float rightBorderMaxWidth2, float leftBorderMaxWidth2) {
        this.tableRenderer = tableRenderer2;
        int numberOfColumns2 = ((Table) tableRenderer2.getModelElement()).getNumberOfColumns();
        this.numberOfColumns = numberOfColumns2;
        this.widths = new ColumnWidthData[numberOfColumns2];
        this.rightBorderMaxWidth = rightBorderMaxWidth2;
        this.leftBorderMaxWidth = leftBorderMaxWidth2;
        float f = 0.0f;
        if (tableRenderer2.bordersHandler instanceof SeparatedTableBorders) {
            Float horizontalSpacing = tableRenderer2.getPropertyAsFloat(115);
            this.horizontalBorderSpacing = horizontalSpacing != null ? horizontalSpacing.floatValue() : f;
        } else {
            this.horizontalBorderSpacing = 0.0f;
        }
        calculateTableWidth(availableWidth, calculateTableMaxWidth);
    }

    /* access modifiers changed from: package-private */
    public boolean hasFixedLayout() {
        return this.fixedTableLayout;
    }

    /* access modifiers changed from: package-private */
    public float[] layout() {
        if (hasFixedLayout()) {
            return fixedLayout();
        }
        return autoLayout();
    }

    /* access modifiers changed from: package-private */
    public float getMinWidth() {
        return this.layoutMinWidth;
    }

    /* access modifiers changed from: package-private */
    public float[] autoLayout() {
        if (this.tableRenderer.getTable().isComplete()) {
            fillAndSortCells();
            calculateMinMaxWidths();
            float minSum = 0.0f;
            for (ColumnWidthData width : this.widths) {
                minSum += width.min;
            }
            for (CellInfo cell : this.cells) {
                processCell(cell);
            }
            processColumns();
            recalculate(minSum);
            return extractWidths();
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public List<CellInfo> autoLayoutCustom() {
        if (this.tableRenderer.getTable().isComplete()) {
            fillAndSortCells();
            calculateMinMaxWidths();
            return this.cells;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void processCell(CellInfo cell) {
        UnitValue cellWidth = getCellWidth(cell.getCell(), false);
        if (cellWidth != null) {
            if (cellWidth.getValue() <= 0.0f) {
                throw new AssertionError();
            } else if (cellWidth.isPercentValue()) {
                if (cell.getColspan() == 1) {
                    this.widths[cell.getCol()].setPercents(cellWidth.getValue());
                    return;
                }
                int pointColumns = 0;
                float percentSum = 0.0f;
                for (int i = cell.getCol(); i < cell.getCol() + cell.getColspan(); i++) {
                    if (!this.widths[i].isPercent) {
                        pointColumns++;
                    } else {
                        percentSum += this.widths[i].width;
                    }
                }
                float percentAddition = cellWidth.getValue() - percentSum;
                if (percentAddition <= 0.0f) {
                    return;
                }
                if (pointColumns == 0) {
                    for (int i2 = cell.getCol(); i2 < cell.getCol() + cell.getColspan(); i2++) {
                        this.widths[i2].addPercents(percentAddition / ((float) cell.getColspan()));
                    }
                    return;
                }
                for (int i3 = cell.getCol(); i3 < cell.getCol() + cell.getColspan(); i3++) {
                    if (!this.widths[i3].isPercent) {
                        this.widths[i3].setPercents(percentAddition / ((float) pointColumns));
                    }
                }
            } else if (cell.getColspan() != 1) {
                processCellsRemainWidth(cell, cellWidth);
            } else if (this.widths[cell.getCol()].isPercent) {
            } else {
                if (this.widths[cell.getCol()].min <= cellWidth.getValue()) {
                    this.widths[cell.getCol()].setPoints(cellWidth.getValue()).setFixed(true);
                } else {
                    this.widths[cell.getCol()].setPoints(this.widths[cell.getCol()].min);
                }
            }
        } else if (this.widths[cell.getCol()].isFlexible()) {
            int flexibleCols = 0;
            float remainWidth = 0.0f;
            for (int i4 = cell.getCol(); i4 < cell.getCol() + cell.getColspan(); i4++) {
                if (this.widths[i4].isFlexible()) {
                    remainWidth += this.widths[i4].max - this.widths[i4].width;
                    flexibleCols++;
                }
            }
            if (remainWidth > 0.0f) {
                for (int i5 = cell.getCol(); i5 < cell.getCol() + cell.getColspan(); i5++) {
                    if (this.widths[i5].isFlexible()) {
                        this.widths[i5].addPoints(remainWidth / ((float) flexibleCols));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void processColumns() {
        for (int i = 0; i < this.numberOfColumns; i++) {
            UnitValue colWidth = getTable().getColumnWidth(i);
            if (colWidth != null && colWidth.getValue() > 0.0f) {
                if (colWidth.isPercentValue()) {
                    if (!this.widths[i].isPercent) {
                        if (this.widths[i].isFixed && this.widths[i].width > this.widths[i].min) {
                            ColumnWidthData columnWidthData = this.widths[i];
                            columnWidthData.max = columnWidthData.width;
                        }
                        this.widths[i].setPercents(colWidth.getValue());
                    }
                } else if (!this.widths[i].isPercent && colWidth.getValue() >= this.widths[i].min) {
                    if (this.widths[i].isFixed) {
                        this.widths[i].setPoints(colWidth.getValue());
                    } else {
                        this.widths[i].resetPoints(colWidth.getValue()).setFixed(true);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void recalculate(float minSum) {
        float sumOfPercents;
        if (this.tableWidth - minSum < 0.0f) {
            for (int i = 0; i < this.numberOfColumns; i++) {
                ColumnWidthData columnWidthData = this.widths[i];
                columnWidthData.finalWidth = columnWidthData.min;
            }
            return;
        }
        float sumOfPercents2 = 0.0f;
        float minTableWidth = 0.0f;
        float totalNonPercent = 0.0f;
        int i2 = 0;
        while (true) {
            ColumnWidthData[] columnWidthDataArr = this.widths;
            if (i2 >= columnWidthDataArr.length) {
                break;
            }
            if (!columnWidthDataArr[i2].isPercent) {
                minTableWidth += this.widths[i2].min;
                totalNonPercent += this.widths[i2].width;
            } else if (sumOfPercents2 < 100.0f && this.widths[i2].width + sumOfPercents2 > 100.0f) {
                this.widths[i2].width = 100.0f - sumOfPercents2;
                sumOfPercents2 += this.widths[i2].width;
                warn100percent();
            } else if (sumOfPercents2 >= 100.0f) {
                ColumnWidthData columnWidthData2 = this.widths[i2];
                columnWidthData2.resetPoints(columnWidthData2.min);
                minTableWidth += this.widths[i2].min;
                warn100percent();
            } else {
                sumOfPercents2 += this.widths[i2].width;
            }
            i2++;
        }
        if (sumOfPercents2 <= 100.0f) {
            boolean toBalance = true;
            if (!this.fixedTableWidth) {
                float tableWidthBasedOnPercents = sumOfPercents2 < 100.0f ? (totalNonPercent * 100.0f) / (100.0f - sumOfPercents2) : 0.0f;
                for (int i3 = 0; i3 < this.numberOfColumns; i3++) {
                    if (this.widths[i3].isPercent && this.widths[i3].width > 0.0f) {
                        tableWidthBasedOnPercents = Math.max((this.widths[i3].max * 100.0f) / this.widths[i3].width, tableWidthBasedOnPercents);
                    }
                }
                if (tableWidthBasedOnPercents <= this.tableWidth) {
                    if (tableWidthBasedOnPercents >= minTableWidth) {
                        this.tableWidth = tableWidthBasedOnPercents;
                        toBalance = false;
                    } else {
                        this.tableWidth = minTableWidth;
                    }
                }
            }
            if (sumOfPercents2 > 0.0f && sumOfPercents2 < 100.0f && totalNonPercent == 0.0f) {
                int i4 = 0;
                while (true) {
                    ColumnWidthData[] columnWidthDataArr2 = this.widths;
                    if (i4 >= columnWidthDataArr2.length) {
                        break;
                    }
                    ColumnWidthData columnWidthData3 = columnWidthDataArr2[i4];
                    columnWidthData3.width = (columnWidthData3.width * 100.0f) / sumOfPercents2;
                    i4++;
                }
                sumOfPercents2 = 100.0f;
            }
            if (!toBalance) {
                for (int i5 = 0; i5 < this.numberOfColumns; i5++) {
                    ColumnWidthData columnWidthData4 = this.widths[i5];
                    columnWidthData4.finalWidth = columnWidthData4.isPercent ? (this.tableWidth * this.widths[i5].width) / 100.0f : this.widths[i5].width;
                }
            } else if (sumOfPercents2 >= 100.0f) {
                float sumOfPercents3 = 100.0f;
                boolean recalculatePercents = false;
                float remainWidth = this.tableWidth - minTableWidth;
                for (int i6 = 0; i6 < this.numberOfColumns; i6++) {
                    if (!this.widths[i6].isPercent) {
                        ColumnWidthData columnWidthData5 = this.widths[i6];
                        columnWidthData5.finalWidth = columnWidthData5.min;
                    } else if ((this.widths[i6].width * remainWidth) / 100.0f >= this.widths[i6].min) {
                        ColumnWidthData columnWidthData6 = this.widths[i6];
                        columnWidthData6.finalWidth = (columnWidthData6.width * remainWidth) / 100.0f;
                    } else {
                        ColumnWidthData columnWidthData7 = this.widths[i6];
                        columnWidthData7.finalWidth = columnWidthData7.min;
                        this.widths[i6].isPercent = false;
                        remainWidth -= this.widths[i6].min;
                        sumOfPercents3 -= this.widths[i6].width;
                        recalculatePercents = true;
                    }
                }
                if (recalculatePercents) {
                    for (int i7 = 0; i7 < this.numberOfColumns; i7++) {
                        if (this.widths[i7].isPercent) {
                            ColumnWidthData columnWidthData8 = this.widths[i7];
                            columnWidthData8.finalWidth = (columnWidthData8.width * remainWidth) / sumOfPercents3;
                        }
                    }
                }
            } else {
                float totalPercent = 0.0f;
                float minTotalNonPercent = 0.0f;
                float fixedAddition = 0.0f;
                float flexibleAddition = 0.0f;
                boolean hasFlexibleCell = false;
                for (int i8 = 0; i8 < this.numberOfColumns; i8++) {
                    if (!this.widths[i8].isPercent) {
                        ColumnWidthData columnWidthData9 = this.widths[i8];
                        columnWidthData9.finalWidth = columnWidthData9.min;
                        minTotalNonPercent += this.widths[i8].min;
                        float addition = this.widths[i8].width - this.widths[i8].min;
                        if (this.widths[i8].isFixed) {
                            fixedAddition += addition;
                        } else {
                            flexibleAddition += addition;
                            hasFlexibleCell = true;
                        }
                    } else if ((this.tableWidth * this.widths[i8].width) / 100.0f >= this.widths[i8].min) {
                        ColumnWidthData columnWidthData10 = this.widths[i8];
                        columnWidthData10.finalWidth = (this.tableWidth * columnWidthData10.width) / 100.0f;
                        totalPercent += this.widths[i8].finalWidth;
                    } else {
                        sumOfPercents2 -= this.widths[i8].width;
                        ColumnWidthData columnWidthData11 = this.widths[i8];
                        columnWidthData11.resetPoints(columnWidthData11.min);
                        ColumnWidthData columnWidthData12 = this.widths[i8];
                        columnWidthData12.finalWidth = columnWidthData12.min;
                        minTotalNonPercent += this.widths[i8].min;
                    }
                }
                float f = this.tableWidth;
                if (totalPercent + minTotalNonPercent > f) {
                    float extraWidth = f - minTotalNonPercent;
                    if (sumOfPercents2 > 0.0f) {
                        for (int i9 = 0; i9 < this.numberOfColumns; i9++) {
                            if (this.widths[i9].isPercent) {
                                ColumnWidthData columnWidthData13 = this.widths[i9];
                                columnWidthData13.finalWidth = (columnWidthData13.width * extraWidth) / sumOfPercents2;
                            }
                        }
                        return;
                    }
                    return;
                }
                float extraWidth2 = (f - totalPercent) - minTotalNonPercent;
                if (fixedAddition <= 0.0f || (extraWidth2 >= fixedAddition && hasFlexibleCell)) {
                    float extraWidth3 = extraWidth2 - fixedAddition;
                    if (extraWidth3 < flexibleAddition) {
                        for (int i10 = 0; i10 < this.numberOfColumns; i10++) {
                            if (this.widths[i10].isFixed) {
                                ColumnWidthData columnWidthData14 = this.widths[i10];
                                columnWidthData14.finalWidth = columnWidthData14.width;
                            } else if (!this.widths[i10].isPercent) {
                                this.widths[i10].finalWidth += ((this.widths[i10].width - this.widths[i10].min) * extraWidth3) / flexibleAddition;
                            }
                        }
                        return;
                    }
                    float totalFixed = 0.0f;
                    float totalFlexible = 0.0f;
                    float flexibleCount = 0.0f;
                    int i11 = 0;
                    while (i11 < this.numberOfColumns) {
                        if (this.widths[i11].isFixed) {
                            ColumnWidthData columnWidthData15 = this.widths[i11];
                            sumOfPercents = sumOfPercents2;
                            columnWidthData15.finalWidth = columnWidthData15.width;
                            totalFixed += this.widths[i11].width;
                        } else {
                            sumOfPercents = sumOfPercents2;
                            if (!this.widths[i11].isPercent) {
                                totalFlexible += this.widths[i11].width;
                                flexibleCount += 1.0f;
                            }
                        }
                        i11++;
                        sumOfPercents2 = sumOfPercents;
                    }
                    if (totalFlexible > 0.0f || flexibleCount > 0.0f) {
                        float extraWidth4 = (this.tableWidth - totalPercent) - totalFixed;
                        for (int i12 = 0; i12 < this.numberOfColumns; i12++) {
                            if (!this.widths[i12].isPercent && !this.widths[i12].isFixed) {
                                ColumnWidthData columnWidthData16 = this.widths[i12];
                                columnWidthData16.finalWidth = totalFlexible > 0.0f ? (columnWidthData16.width * extraWidth4) / totalFlexible : extraWidth4 / flexibleCount;
                            }
                        }
                        return;
                    }
                    throw new AssertionError();
                }
                for (int i13 = 0; i13 < this.numberOfColumns; i13++) {
                    if (this.widths[i13].isFixed) {
                        this.widths[i13].finalWidth += ((this.widths[i13].width - this.widths[i13].min) * extraWidth2) / fixedAddition;
                    }
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: package-private */
    public void processCellsRemainWidth(CellInfo cell, UnitValue cellWidth) {
        int flexibleCols = 0;
        float remainWidth = cellWidth.getValue();
        int i = cell.getCol();
        while (true) {
            if (i < cell.getCol() + cell.getColspan()) {
                if (this.widths[i].isPercent) {
                    remainWidth = 0.0f;
                    break;
                }
                remainWidth -= this.widths[i].width;
                if (!this.widths[i].isFixed) {
                    flexibleCols++;
                }
                i++;
            } else {
                break;
            }
        }
        if (remainWidth > 0.0f) {
            int[] flexibleColIndexes = ArrayUtil.fillWithValue(new int[cell.getColspan()], -1);
            if (flexibleCols > 0) {
                for (int i2 = cell.getCol(); i2 < cell.getCol() + cell.getColspan(); i2++) {
                    if (this.widths[i2].isFlexible()) {
                        if (this.widths[i2].min > this.widths[i2].width + (remainWidth / ((float) flexibleCols))) {
                            ColumnWidthData columnWidthData = this.widths[i2];
                            columnWidthData.resetPoints(columnWidthData.min);
                            remainWidth -= this.widths[i2].min - this.widths[i2].width;
                            flexibleCols--;
                            if (flexibleCols == 0 || remainWidth <= 0.0f) {
                                break;
                            }
                        } else {
                            flexibleColIndexes[i2 - cell.getCol()] = i2;
                        }
                    }
                }
                if (flexibleCols > 0 && remainWidth > 0.0f) {
                    for (int i3 = 0; i3 < flexibleColIndexes.length; i3++) {
                        if (flexibleColIndexes[i3] >= 0) {
                            this.widths[flexibleColIndexes[i3]].addPoints(remainWidth / ((float) flexibleCols)).setFixed(true);
                        }
                    }
                    return;
                }
                return;
            }
            for (int i4 = cell.getCol(); i4 < cell.getCol() + cell.getColspan(); i4++) {
                this.widths[i4].addPoints(remainWidth / ((float) cell.getColspan()));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public float[] fixedLayout() {
        float f;
        CellRenderer[] firtsRow;
        UnitValue cellWidth;
        float width;
        float[] columnWidths = new float[this.numberOfColumns];
        int i = 0;
        while (true) {
            f = 0.0f;
            if (i >= this.numberOfColumns) {
                break;
            }
            UnitValue colWidth = getTable().getColumnWidth(i);
            if (colWidth == null || colWidth.getValue() < 0.0f) {
                columnWidths[i] = -1.0f;
            } else if (colWidth.isPercentValue()) {
                columnWidths[i] = (colWidth.getValue() * this.tableWidth) / 100.0f;
            } else {
                columnWidths[i] = colWidth.getValue();
            }
            i++;
        }
        int processedColumns = 0;
        float remainWidth = this.tableWidth;
        if (this.tableRenderer.headerRenderer != null && this.tableRenderer.headerRenderer.rows.size() > 0) {
            firtsRow = this.tableRenderer.headerRenderer.rows.get(0);
        } else if (this.tableRenderer.rows.size() <= 0 || !getTable().isComplete() || getTable().getLastRowBottomBorder().size() != 0) {
            firtsRow = null;
        } else {
            firtsRow = this.tableRenderer.rows.get(0);
        }
        float[] columnWidthIfPercent = new float[columnWidths.length];
        for (int i2 = 0; i2 < columnWidthIfPercent.length; i2++) {
            columnWidthIfPercent[i2] = -1.0f;
        }
        float sumOfPercents = 0.0f;
        if (firtsRow == null || !getTable().isComplete() || !getTable().getLastRowBottomBorder().isEmpty()) {
            for (int i3 = 0; i3 < this.numberOfColumns; i3++) {
                if (columnWidths[i3] != -1.0f) {
                    processedColumns++;
                    remainWidth -= columnWidths[i3];
                }
            }
        } else {
            int i4 = 0;
            while (i4 < this.numberOfColumns) {
                if (columnWidths[i4] == -1.0f) {
                    CellRenderer cell = firtsRow[i4];
                    if (!(cell == null || (cellWidth = getCellWidth(cell, true)) == null)) {
                        if (cellWidth.getValue() >= f) {
                            if (cellWidth.isPercentValue()) {
                                width = (this.tableWidth * cellWidth.getValue()) / 100.0f;
                                columnWidthIfPercent[i4] = cellWidth.getValue();
                                sumOfPercents += columnWidthIfPercent[i4];
                            } else {
                                width = cellWidth.getValue();
                            }
                            int colspan = ((Cell) cell.getModelElement()).getColspan();
                            for (int j = 0; j < colspan; j++) {
                                columnWidths[i4 + j] = width / ((float) colspan);
                            }
                            remainWidth -= columnWidths[i4];
                            processedColumns++;
                        } else {
                            throw new AssertionError();
                        }
                    }
                } else {
                    remainWidth -= columnWidths[i4];
                    processedColumns++;
                }
                i4++;
                f = 0.0f;
            }
        }
        if (sumOfPercents > 100.0f) {
            warn100percent();
        }
        if (remainWidth > 0.0f) {
            if (this.numberOfColumns == processedColumns) {
                for (int i5 = 0; i5 < this.numberOfColumns; i5++) {
                    float f2 = this.tableWidth;
                    columnWidths[i5] = (columnWidths[i5] * f2) / (f2 - remainWidth);
                }
            }
        } else if (remainWidth < 0.0f) {
            for (int i6 = 0; i6 < this.numberOfColumns; i6++) {
                columnWidths[i6] = columnWidths[i6] + (-1.0f != columnWidthIfPercent[i6] ? (columnWidthIfPercent[i6] * remainWidth) / sumOfPercents : 0.0f);
            }
        }
        int i7 = 0;
        while (true) {
            int i8 = this.numberOfColumns;
            if (i7 >= i8) {
                break;
            }
            if (columnWidths[i7] == -1.0f) {
                columnWidths[i7] = Math.max(0.0f, remainWidth / ((float) (i8 - processedColumns)));
            }
            i7++;
        }
        if (this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) {
            for (int i9 = 0; i9 < this.numberOfColumns; i9++) {
                columnWidths[i9] = columnWidths[i9] + this.horizontalBorderSpacing;
            }
        }
        return columnWidths;
    }

    private void calculateTableWidth(float availableWidth, boolean calculateTableMaxWidth) {
        this.fixedTableLayout = CommonCssConstants.FIXED.equals(((String) this.tableRenderer.getProperty(93, "auto")).toLowerCase());
        UnitValue width = (UnitValue) this.tableRenderer.getProperty(77);
        float f = 0.0f;
        if (!this.fixedTableLayout || width == null || width.getValue() < 0.0f) {
            this.fixedTableLayout = false;
            this.layoutMinWidth = -1.0f;
            if (calculateTableMaxWidth) {
                this.fixedTableWidth = false;
                this.tableWidth = retrieveTableWidth(availableWidth);
            } else if (width == null || width.getValue() < 0.0f) {
                this.fixedTableWidth = false;
                this.tableWidth = retrieveTableWidth(availableWidth);
            } else {
                this.fixedTableWidth = true;
                this.tableWidth = retrieveTableWidth(width, availableWidth).floatValue();
            }
        } else {
            if (getTable().getLastRowBottomBorder().size() != 0) {
                width = getTable().getWidth();
            } else if (!getTable().isComplete() && getTable().getWidth() != null && getTable().getWidth().isPercentValue()) {
                getTable().setWidth(this.tableRenderer.retrieveUnitValue(availableWidth, 77).floatValue());
            }
            this.fixedTableWidth = true;
            this.tableWidth = retrieveTableWidth(width, availableWidth).floatValue();
            if (!width.isPercentValue()) {
                f = this.tableWidth;
            }
            this.layoutMinWidth = f;
        }
        Float min = retrieveTableWidth((UnitValue) this.tableRenderer.getProperty(80), availableWidth);
        Float max = retrieveTableWidth((UnitValue) this.tableRenderer.getProperty(79), availableWidth);
        this.tableMinWidth = min != null ? min.floatValue() : this.layoutMinWidth;
        float floatValue = max != null ? max.floatValue() : this.tableWidth;
        this.tableMaxWidth = floatValue;
        float f2 = this.tableMinWidth;
        if (f2 > floatValue) {
            this.tableMaxWidth = f2;
        }
        if (f2 > this.tableWidth) {
            this.tableWidth = f2;
        }
        float f3 = this.tableMaxWidth;
        if (f3 < this.tableWidth) {
            this.tableWidth = f3;
        }
    }

    private Float retrieveTableWidth(UnitValue width, float availableWidth) {
        float f;
        if (width == null) {
            return null;
        }
        if (width.isPercentValue()) {
            f = (width.getValue() * availableWidth) / 100.0f;
        } else {
            f = width.getValue();
        }
        return Float.valueOf(retrieveTableWidth(f));
    }

    private float retrieveTableWidth(float width) {
        float width2;
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.tableRenderer.getProperty(114))) {
            width2 = (width - (this.rightBorderMaxWidth + this.leftBorderMaxWidth)) - (((float) (this.numberOfColumns + 1)) * this.horizontalBorderSpacing);
        } else {
            width2 = width - ((this.rightBorderMaxWidth + this.leftBorderMaxWidth) / 2.0f);
        }
        return Math.max(width2, 0.0f);
    }

    private Table getTable() {
        return (Table) this.tableRenderer.getModelElement();
    }

    private void calculateMinMaxWidths() {
        int i = this.numberOfColumns;
        float[] minWidths = new float[i];
        float[] maxWidths = new float[i];
        for (CellInfo cell : this.cells) {
            cell.setParent(this.tableRenderer);
            MinMaxWidth minMax = cell.getCell().getMinMaxWidth();
            float[] indents = getCellBorderIndents(cell);
            if (BorderCollapsePropertyValue.SEPARATE.equals(this.tableRenderer.getProperty(114))) {
                minMax.setAdditionalWidth(minMax.getAdditionalWidth() - this.horizontalBorderSpacing);
            } else {
                minMax.setAdditionalWidth(minMax.getAdditionalWidth() + (indents[1] / 2.0f) + (indents[3] / 2.0f));
            }
            if (cell.getColspan() == 1) {
                minWidths[cell.getCol()] = Math.max(minMax.getMinWidth(), minWidths[cell.getCol()]);
                maxWidths[cell.getCol()] = Math.max(minMax.getMaxWidth(), maxWidths[cell.getCol()]);
            } else {
                float remainMin = minMax.getMinWidth();
                float remainMax = minMax.getMaxWidth();
                for (int i2 = cell.getCol(); i2 < cell.getCol() + cell.getColspan(); i2++) {
                    remainMin -= minWidths[i2];
                    remainMax -= maxWidths[i2];
                }
                if (remainMin > 0.0f) {
                    for (int i3 = cell.getCol(); i3 < cell.getCol() + cell.getColspan(); i3++) {
                        minWidths[i3] = minWidths[i3] + (remainMin / ((float) cell.getColspan()));
                    }
                }
                if (remainMax > 0.0f) {
                    for (int i4 = cell.getCol(); i4 < cell.getCol() + cell.getColspan(); i4++) {
                        maxWidths[i4] = maxWidths[i4] + (remainMax / ((float) cell.getColspan()));
                    }
                }
            }
        }
        int i5 = 0;
        while (true) {
            ColumnWidthData[] columnWidthDataArr = this.widths;
            if (i5 < columnWidthDataArr.length) {
                columnWidthDataArr[i5] = new ColumnWidthData(minWidths[i5], maxWidths[i5]);
                i5++;
            } else {
                return;
            }
        }
    }

    private float[] getCellBorderIndents(CellInfo cell) {
        TableRenderer renderer;
        if (cell.region == 1) {
            renderer = this.tableRenderer.headerRenderer;
        } else if (cell.region == 3) {
            renderer = this.tableRenderer.footerRenderer;
        } else {
            renderer = this.tableRenderer;
        }
        return renderer.bordersHandler.getCellBorderIndents(cell.getRow(), cell.getCol(), cell.getRowspan(), cell.getColspan());
    }

    private void fillAndSortCells() {
        this.cells = new ArrayList();
        if (this.tableRenderer.headerRenderer != null) {
            fillRendererCells(this.tableRenderer.headerRenderer, (byte) 1);
        }
        fillRendererCells(this.tableRenderer, (byte) 2);
        if (this.tableRenderer.footerRenderer != null) {
            fillRendererCells(this.tableRenderer.footerRenderer, (byte) 3);
        }
        Collections.sort(this.cells);
    }

    private void fillRendererCells(TableRenderer renderer, byte region) {
        for (int row = 0; row < renderer.rows.size(); row++) {
            for (int col = 0; col < this.numberOfColumns; col++) {
                CellRenderer cell = renderer.rows.get(row)[col];
                if (cell != null) {
                    this.cells.add(new CellInfo(cell, row, col, region));
                }
            }
        }
    }

    private void warn100percent() {
        LoggerFactory.getLogger((Class<?>) TableWidths.class).warn(LogMessageConstant.SUM_OF_TABLE_COLUMNS_IS_GREATER_THAN_100);
    }

    private float[] extractWidths() {
        float actualWidth = 0.0f;
        this.layoutMinWidth = 0.0f;
        float[] columnWidths = new float[this.widths.length];
        int i = 0;
        while (true) {
            ColumnWidthData[] columnWidthDataArr = this.widths;
            if (i >= columnWidthDataArr.length) {
                if (actualWidth > this.tableWidth + (MinMaxWidthUtils.getEps() * ((float) this.widths.length))) {
                    LoggerFactory.getLogger((Class<?>) TableWidths.class).warn(LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH);
                }
                return columnWidths;
            } else if (columnWidthDataArr[i].finalWidth >= 0.0f) {
                columnWidths[i] = this.widths[i].finalWidth + this.horizontalBorderSpacing;
                actualWidth += this.widths[i].finalWidth;
                this.layoutMinWidth += this.widths[i].min + this.horizontalBorderSpacing;
                i++;
            } else {
                throw new AssertionError();
            }
        }
    }

    public String toString() {
        return "width=" + this.tableWidth + (this.fixedTableWidth ? "!!" : "");
    }

    private static class ColumnWidthData {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        float finalWidth = -1.0f;
        boolean isFixed = false;
        boolean isPercent = false;
        float max;
        final float min;
        float width = 0.0f;

        static {
            Class<TableWidths> cls = TableWidths.class;
        }

        ColumnWidthData(float min2, float max2) {
            float f = 0.0f;
            if (min2 < 0.0f) {
                throw new AssertionError();
            } else if (max2 >= 0.0f) {
                this.min = min2 > 0.0f ? MinMaxWidthUtils.getEps() + min2 : 0.0f;
                this.max = max2 > 0.0f ? Math.min(MinMaxWidthUtils.getEps() + max2, 32760.0f) : f;
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData setPoints(float width2) {
            if (this.isPercent) {
                throw new AssertionError();
            } else if (this.min <= width2) {
                this.width = Math.max(this.width, width2);
                return this;
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData resetPoints(float width2) {
            if (this.min <= width2) {
                this.width = width2;
                this.isPercent = false;
                return this;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData addPoints(float width2) {
            if (!this.isPercent) {
                this.width += width2;
                return this;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData setPercents(float percent) {
            if (this.isPercent) {
                this.width = Math.max(this.width, percent);
            } else {
                this.isPercent = true;
                this.width = percent;
            }
            this.isFixed = false;
            return this;
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData addPercents(float width2) {
            if (this.isPercent) {
                this.width += width2;
                return this;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public ColumnWidthData setFixed(boolean fixed) {
            this.isFixed = fixed;
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean isFlexible() {
            return !this.isFixed && !this.isPercent;
        }

        public String toString() {
            return "w=" + this.width + (this.isPercent ? CommonCssConstants.PERCENTAGE : CommonCssConstants.f1616PT) + (this.isFixed ? " !!" : "") + ", min=" + this.min + ", max=" + this.max + ", finalWidth=" + this.finalWidth;
        }
    }

    private UnitValue getCellWidth(CellRenderer cell, boolean zeroIsValid) {
        float f;
        float f2;
        UnitValue widthValue = (UnitValue) cell.getProperty(77);
        if (widthValue == null || widthValue.getValue() < 0.0f) {
            return null;
        }
        if (widthValue.getValue() == 0.0f) {
            if (zeroIsValid) {
                return ZeroWidth;
            }
            return null;
        } else if (widthValue.isPercentValue()) {
            return widthValue;
        } else {
            UnitValue widthValue2 = resolveMinMaxCollision(cell, widthValue);
            if (!AbstractRenderer.isBorderBoxSizing(cell)) {
                Border[] borders = cell.getBorders();
                if (borders[1] != null) {
                    float value = widthValue2.getValue();
                    if (this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) {
                        f2 = borders[1].getWidth();
                    } else {
                        f2 = borders[1].getWidth() / 2.0f;
                    }
                    widthValue2.setValue(value + f2);
                }
                if (borders[3] != null) {
                    float value2 = widthValue2.getValue();
                    if (this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) {
                        f = borders[3].getWidth();
                    } else {
                        f = borders[3].getWidth() / 2.0f;
                    }
                    widthValue2.setValue(value2 + f);
                }
                UnitValue[] paddings = cell.getPaddings();
                Class<TableWidths> cls = TableWidths.class;
                if (!paddings[1].isPointValue()) {
                    LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 48));
                }
                if (!paddings[3].isPointValue()) {
                    LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 49));
                }
                widthValue2.setValue(widthValue2.getValue() + paddings[1].getValue() + paddings[3].getValue());
            }
            return widthValue2;
        }
    }

    private UnitValue resolveMinMaxCollision(CellRenderer cell, UnitValue widthValue) {
        if (widthValue.isPointValue()) {
            UnitValue minWidthValue = (UnitValue) cell.getProperty(80);
            if (minWidthValue != null && minWidthValue.isPointValue() && minWidthValue.getValue() > widthValue.getValue()) {
                return minWidthValue;
            }
            UnitValue maxWidthValue = (UnitValue) cell.getProperty(79);
            if (maxWidthValue == null || !maxWidthValue.isPointValue() || maxWidthValue.getValue() >= widthValue.getValue()) {
                return widthValue;
            }
            return maxWidthValue;
        }
        throw new AssertionError();
    }

    static class CellInfo implements Comparable<CellInfo> {
        static final byte BODY = 2;
        static final byte FOOTER = 3;
        static final byte HEADER = 1;
        private final CellRenderer cell;
        private final int col;
        final byte region;
        private final int row;

        CellInfo(CellRenderer cell2, int row2, int col2, byte region2) {
            this.cell = cell2;
            this.region = region2;
            this.row = row2;
            this.col = col2;
        }

        /* access modifiers changed from: package-private */
        public CellRenderer getCell() {
            return this.cell;
        }

        /* access modifiers changed from: package-private */
        public int getCol() {
            return this.col;
        }

        /* access modifiers changed from: package-private */
        public int getColspan() {
            return this.cell.getPropertyAsInteger(16).intValue();
        }

        /* access modifiers changed from: package-private */
        public int getRow() {
            return this.row;
        }

        /* access modifiers changed from: package-private */
        public int getRowspan() {
            return this.cell.getPropertyAsInteger(60).intValue();
        }

        public int compareTo(CellInfo o) {
            boolean z = false;
            boolean z2 = getColspan() == 1;
            if (o.getColspan() == 1) {
                z = true;
            }
            if (z2 ^ z) {
                return getColspan() - o.getColspan();
            }
            if (this.region == o.region && getRow() == o.getRow()) {
                return ((getCol() + getColspan()) - o.getCol()) - o.getColspan();
            }
            int i = this.region;
            int i2 = o.region;
            if (i == i2) {
                i = getRow();
                i2 = o.getRow();
            }
            return i - i2;
        }

        public String toString() {
            String str = MessageFormatUtil.format("row={0}, col={1}, rowspan={2}, colspan={3}, ", Integer.valueOf(getRow()), Integer.valueOf(getCol()), Integer.valueOf(getRowspan()), Integer.valueOf(getColspan()));
            byte b = this.region;
            if (b == 1) {
                return str + "header";
            }
            if (b == 2) {
                return str + "body";
            }
            if (b == 3) {
                return str + "footer";
            }
            return str;
        }

        public void setParent(TableRenderer tableRenderer) {
            byte b = this.region;
            if (b == 1) {
                this.cell.setParent(tableRenderer.headerRenderer);
            } else if (b == 3) {
                this.cell.setParent(tableRenderer.footerRenderer);
            } else {
                this.cell.setParent(tableRenderer);
            }
        }
    }
}

package com.itextpdf.layout.element;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.CaptionSide;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class Table extends BlockElement<Table> implements ILargeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Div caption;
    private UnitValue[] columnWidths;
    private int currentColumn;
    private int currentRow;
    private Document document;
    private Table footer;
    private Table header;
    private boolean isComplete;
    private Cell[] lastAddedRow;
    private List<RowRange> lastAddedRowGroups;
    private int rowWindowStart;
    private List<Cell[]> rows;
    private boolean skipFirstHeader;
    private boolean skipLastFooter;
    protected DefaultAccessibilityProperties tagProperties;

    public Table(float[] columnWidths2, boolean largeTable) {
        this.currentColumn = 0;
        this.currentRow = -1;
        this.rowWindowStart = 0;
        if (columnWidths2 == null) {
            throw new IllegalArgumentException("The widths array in table constructor can not be null.");
        } else if (columnWidths2.length != 0) {
            this.columnWidths = normalizeColumnWidths(columnWidths2);
            initializeLargeTable(largeTable);
            initializeRows();
        } else {
            throw new IllegalArgumentException("The widths array in table constructor can not have zero length.");
        }
    }

    public Table(UnitValue[] columnWidths2, boolean largeTable) {
        this.currentColumn = 0;
        this.currentRow = -1;
        this.rowWindowStart = 0;
        if (columnWidths2 == null) {
            throw new IllegalArgumentException("The widths array in table constructor can not be null.");
        } else if (columnWidths2.length != 0) {
            this.columnWidths = normalizeColumnWidths(columnWidths2);
            initializeLargeTable(largeTable);
            initializeRows();
        } else {
            throw new IllegalArgumentException("The widths array in table constructor can not have zero length.");
        }
    }

    public Table(UnitValue[] columnWidths2) {
        this(columnWidths2, false);
    }

    public Table(float[] pointColumnWidths) {
        this(pointColumnWidths, false);
    }

    public Table(int numColumns, boolean largeTable) {
        this.currentColumn = 0;
        this.currentRow = -1;
        this.rowWindowStart = 0;
        if (numColumns > 0) {
            this.columnWidths = normalizeColumnWidths(numColumns);
            initializeLargeTable(largeTable);
            initializeRows();
            return;
        }
        throw new IllegalArgumentException("The number of columns in Table constructor must be greater than zero");
    }

    public Table(int numColumns) {
        this(numColumns, false);
    }

    public Table setFixedLayout() {
        setProperty(93, CommonCssConstants.FIXED);
        return this;
    }

    public Table setAutoLayout() {
        setProperty(93, "auto");
        return this;
    }

    public Table useAllAvailableWidth() {
        setProperty(77, UnitValue.createPercentValue(100.0f));
        return this;
    }

    public UnitValue getColumnWidth(int column) {
        return this.columnWidths[column];
    }

    public int getNumberOfColumns() {
        return this.columnWidths.length;
    }

    public int getNumberOfRows() {
        return this.rows.size();
    }

    public Table addHeaderCell(Cell headerCell) {
        ensureHeaderIsInitialized();
        this.header.addCell(headerCell);
        return this;
    }

    public <T extends IElement> Table addHeaderCell(BlockElement<T> blockElement) {
        ensureHeaderIsInitialized();
        this.header.addCell(blockElement);
        return this;
    }

    public Table addHeaderCell(Image image) {
        ensureHeaderIsInitialized();
        this.header.addCell(image);
        return this;
    }

    public Table addHeaderCell(String content) {
        ensureHeaderIsInitialized();
        this.header.addCell(content);
        return this;
    }

    public Table getHeader() {
        return this.header;
    }

    public Table addFooterCell(Cell footerCell) {
        ensureFooterIsInitialized();
        this.footer.addCell(footerCell);
        return this;
    }

    public <T extends IElement> Table addFooterCell(BlockElement<T> blockElement) {
        ensureFooterIsInitialized();
        this.footer.addCell(blockElement);
        return this;
    }

    public Table addFooterCell(Image image) {
        ensureFooterIsInitialized();
        this.footer.addCell(image);
        return this;
    }

    public Table addFooterCell(String content) {
        ensureFooterIsInitialized();
        this.footer.addCell(content);
        return this;
    }

    public Table getFooter() {
        return this.footer;
    }

    public boolean isSkipFirstHeader() {
        return this.skipFirstHeader;
    }

    public Table setSkipFirstHeader(boolean skipFirstHeader2) {
        this.skipFirstHeader = skipFirstHeader2;
        return this;
    }

    public boolean isSkipLastFooter() {
        return this.skipLastFooter;
    }

    public Table setSkipLastFooter(boolean skipLastFooter2) {
        this.skipLastFooter = skipLastFooter2;
        return this;
    }

    public Table setCaption(Div caption2) {
        this.caption = caption2;
        if (caption2 != null) {
            ensureCaptionPropertiesAreSet();
        }
        return this;
    }

    public Table setCaption(Div caption2, CaptionSide side) {
        if (caption2 != null) {
            caption2.setProperty(119, side);
        }
        setCaption(caption2);
        return this;
    }

    private void ensureCaptionPropertiesAreSet() {
        this.caption.getAccessibilityProperties().setRole(StandardRoles.CAPTION);
    }

    public Div getCaption() {
        return this.caption;
    }

    public Table startNewRow() {
        this.currentColumn = 0;
        int i = this.currentRow + 1;
        this.currentRow = i;
        if (i >= this.rows.size()) {
            this.rows.add(new Cell[this.columnWidths.length]);
        }
        return this;
    }

    public Table addCell(Cell cell) {
        if (!this.isComplete || this.lastAddedRow == null) {
            while (true) {
                int i = this.currentColumn;
                if (i >= this.columnWidths.length || i == -1) {
                    startNewRow();
                }
                int i2 = this.currentColumn;
                if (this.rows.get(this.currentRow - this.rowWindowStart)[i2] == null) {
                    break;
                }
                this.currentColumn = i2 + 1;
            }
            this.childElements.add(cell);
            cell.updateCellIndexes(this.currentRow, this.currentColumn, this.columnWidths.length);
            while ((this.currentRow - this.rowWindowStart) + cell.getRowspan() > this.rows.size()) {
                this.rows.add(new Cell[this.columnWidths.length]);
            }
            for (int i3 = this.currentRow; i3 < this.currentRow + cell.getRowspan(); i3++) {
                Cell[] row = this.rows.get(i3 - this.rowWindowStart);
                for (int j = this.currentColumn; j < this.currentColumn + cell.getColspan(); j++) {
                    if (row[j] == null) {
                        row[j] = cell;
                    }
                }
            }
            this.currentColumn += cell.getColspan();
            return this;
        }
        throw new PdfException(PdfException.CannotAddCellToCompletedLargeTable);
    }

    public <T extends IElement> Table addCell(BlockElement<T> blockElement) {
        return addCell(new Cell().add((IBlockElement) blockElement));
    }

    public Table addCell(Image image) {
        return addCell(new Cell().add(image));
    }

    public Table addCell(String content) {
        return addCell(new Cell().add((IBlockElement) new Paragraph(content)));
    }

    public Cell getCell(int row, int column) {
        Cell cell;
        if (row - this.rowWindowStart >= this.rows.size() || (cell = this.rows.get(row - this.rowWindowStart)[column]) == null || cell.getRow() != row || cell.getCol() != column) {
            return null;
        }
        return cell;
    }

    public IRenderer createRendererSubTree() {
        TableRenderer rendererRoot = (TableRenderer) getRenderer();
        for (IElement child : this.childElements) {
            if (this.isComplete || cellBelongsToAnyRowGroup((Cell) child, this.lastAddedRowGroups)) {
                rendererRoot.addChild(child.createRendererSubTree());
            }
        }
        return rendererRoot;
    }

    public IRenderer getRenderer() {
        int rowWindowFinish;
        if (this.nextRenderer != null) {
            if (this.nextRenderer instanceof TableRenderer) {
                IRenderer renderer = this.nextRenderer;
                this.nextRenderer = this.nextRenderer.getNextRenderer();
                return renderer;
            }
            LoggerFactory.getLogger((Class<?>) Table.class).error("Invalid renderer for Table: must be inherited from TableRenderer");
        }
        if (!this.isComplete) {
            this.lastAddedRowGroups = getRowGroups();
        } else if (!(this.lastAddedRow == null || this.rows.size() == 0)) {
            List<RowRange> allRows = new ArrayList<>();
            int i = this.rowWindowStart;
            allRows.add(new RowRange(i, (this.rows.size() + i) - 1));
            this.lastAddedRowGroups = allRows;
        }
        if (this.isComplete) {
            int i2 = this.rowWindowStart;
            return new TableRenderer(this, new RowRange(i2, (this.rows.size() + i2) - 1));
        }
        if (this.lastAddedRowGroups.size() != 0) {
            List<RowRange> list = this.lastAddedRowGroups;
            rowWindowFinish = list.get(list.size() - 1).finishRow;
        } else {
            rowWindowFinish = -1;
        }
        return new TableRenderer(this, new RowRange(this.rowWindowStart, rowWindowFinish));
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public void complete() {
        if (!this.isComplete) {
            this.isComplete = true;
            flush();
            return;
        }
        throw new AssertionError();
    }

    public void flush() {
        Cell[] row = null;
        int rowNum = this.rows.size();
        if (!this.rows.isEmpty()) {
            List<Cell[]> list = this.rows;
            row = list.get(list.size() - 1);
        }
        this.document.add((IBlockElement) this);
        if (row != null && rowNum != this.rows.size()) {
            this.lastAddedRow = row;
        }
    }

    public void flushContent() {
        List<RowRange> list = this.lastAddedRowGroups;
        if (list != null && !list.isEmpty()) {
            int firstRow = this.lastAddedRowGroups.get(0).startRow;
            List<RowRange> list2 = this.lastAddedRowGroups;
            int lastRow = list2.get(list2.size() - 1).finishRow;
            List<IElement> toRemove = new ArrayList<>();
            for (IElement cell : this.childElements) {
                if (((Cell) cell).getRow() >= firstRow && ((Cell) cell).getRow() <= lastRow) {
                    toRemove.add(cell);
                }
            }
            this.childElements.removeAll(toRemove);
            for (int i = 0; i < lastRow - firstRow; i++) {
                this.rows.remove(firstRow - this.rowWindowStart);
            }
            this.lastAddedRow = this.rows.remove(firstRow - this.rowWindowStart);
            List<RowRange> list3 = this.lastAddedRowGroups;
            this.rowWindowStart = list3.get(list3.size() - 1).getFinishRow() + 1;
            this.lastAddedRowGroups = null;
        }
    }

    public void setDocument(Document document2) {
        this.document = document2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.itextpdf.layout.borders.Border} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: com.itextpdf.layout.borders.Border} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: com.itextpdf.layout.borders.Border} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.itextpdf.layout.borders.Border> getLastRowBottomBorder() {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.itextpdf.layout.element.Cell[] r1 = r6.lastAddedRow
            if (r1 == 0) goto L_0x0041
            r1 = 0
        L_0x000a:
            com.itextpdf.layout.element.Cell[] r2 = r6.lastAddedRow
            int r3 = r2.length
            if (r1 >= r3) goto L_0x0041
            r2 = r2[r1]
            r3 = 0
            if (r2 == 0) goto L_0x003b
            r4 = 10
            boolean r5 = r2.hasProperty(r4)
            if (r5 == 0) goto L_0x0024
            java.lang.Object r4 = r2.getProperty(r4)
            r3 = r4
            com.itextpdf.layout.borders.Border r3 = (com.itextpdf.layout.borders.Border) r3
            goto L_0x003b
        L_0x0024:
            r4 = 9
            boolean r5 = r2.hasProperty(r4)
            if (r5 == 0) goto L_0x0034
            java.lang.Object r4 = r2.getProperty(r4)
            r3 = r4
            com.itextpdf.layout.borders.Border r3 = (com.itextpdf.layout.borders.Border) r3
            goto L_0x003b
        L_0x0034:
            java.lang.Object r4 = r2.getDefaultProperty(r4)
            r3 = r4
            com.itextpdf.layout.borders.Border r3 = (com.itextpdf.layout.borders.Border) r3
        L_0x003b:
            r0.add(r3)
            int r1 = r1 + 1
            goto L_0x000a
        L_0x0041:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.element.Table.getLastRowBottomBorder():java.util.List");
    }

    public Table setExtendBottomRow(boolean isExtended) {
        setProperty(86, Boolean.valueOf(isExtended));
        return this;
    }

    public Table setExtendBottomRowOnSplit(boolean isExtended) {
        setProperty(87, Boolean.valueOf(isExtended));
        return this;
    }

    public Table setBorderCollapse(BorderCollapsePropertyValue collapsePropertyValue) {
        setProperty(114, collapsePropertyValue);
        Table table = this.header;
        if (table != null) {
            table.setBorderCollapse(collapsePropertyValue);
        }
        Table table2 = this.footer;
        if (table2 != null) {
            table2.setBorderCollapse(collapsePropertyValue);
        }
        return this;
    }

    public Table setHorizontalBorderSpacing(float spacing) {
        setProperty(115, Float.valueOf(spacing));
        Table table = this.header;
        if (table != null) {
            table.setHorizontalBorderSpacing(spacing);
        }
        Table table2 = this.footer;
        if (table2 != null) {
            table2.setHorizontalBorderSpacing(spacing);
        }
        return this;
    }

    public Table setVerticalBorderSpacing(float spacing) {
        setProperty(116, Float.valueOf(spacing));
        Table table = this.header;
        if (table != null) {
            table.setVerticalBorderSpacing(spacing);
        }
        Table table2 = this.footer;
        if (table2 != null) {
            table2.setVerticalBorderSpacing(spacing);
        }
        return this;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.TABLE);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new TableRenderer(this);
    }

    private static UnitValue[] normalizeColumnWidths(float[] pointColumnWidths) {
        UnitValue[] normalized = new UnitValue[pointColumnWidths.length];
        for (int i = 0; i < normalized.length; i++) {
            if (pointColumnWidths[i] >= 0.0f) {
                normalized[i] = UnitValue.createPointValue(pointColumnWidths[i]);
            }
        }
        return normalized;
    }

    private static UnitValue[] normalizeColumnWidths(UnitValue[] unitColumnWidths) {
        UnitValue[] normalized = new UnitValue[unitColumnWidths.length];
        for (int i = 0; i < unitColumnWidths.length; i++) {
            normalized[i] = (unitColumnWidths[i] == null || unitColumnWidths[i].getValue() < 0.0f) ? null : new UnitValue(unitColumnWidths[i]);
        }
        return normalized;
    }

    private static UnitValue[] normalizeColumnWidths(int numberOfColumns) {
        return new UnitValue[numberOfColumns];
    }

    /* access modifiers changed from: protected */
    public List<RowRange> getRowGroups() {
        int maxRowGroupFinish;
        int i = this.currentColumn;
        UnitValue[] unitValueArr = this.columnWidths;
        int lastRowWeCanFlush = i == unitValueArr.length ? this.currentRow : this.currentRow - 1;
        int[] cellBottomRows = new int[unitValueArr.length];
        List<RowRange> rowGroups = new ArrayList<>();
        for (int currentRowGroupStart = this.rowWindowStart; currentRowGroupStart <= lastRowWeCanFlush; currentRowGroupStart = maxRowGroupFinish + 1) {
            for (int i2 = 0; i2 < this.columnWidths.length; i2++) {
                cellBottomRows[i2] = currentRowGroupStart;
            }
            maxRowGroupFinish = (cellBottomRows[0] + this.rows.get(cellBottomRows[0] - this.rowWindowStart)[0].getRowspan()) - 1;
            boolean converged = false;
            boolean rowGroupComplete = true;
            while (!converged) {
                converged = true;
                int i3 = 0;
                while (i3 < this.columnWidths.length) {
                    while (cellBottomRows[i3] < lastRowWeCanFlush && (cellBottomRows[i3] + this.rows.get(cellBottomRows[i3] - this.rowWindowStart)[i3].getRowspan()) - 1 < maxRowGroupFinish) {
                        cellBottomRows[i3] = cellBottomRows[i3] + this.rows.get(cellBottomRows[i3] - this.rowWindowStart)[i3].getRowspan();
                    }
                    if ((cellBottomRows[i3] + this.rows.get(cellBottomRows[i3] - this.rowWindowStart)[i3].getRowspan()) - 1 > maxRowGroupFinish) {
                        maxRowGroupFinish = (cellBottomRows[i3] + this.rows.get(cellBottomRows[i3] - this.rowWindowStart)[i3].getRowspan()) - 1;
                        converged = false;
                    } else if ((cellBottomRows[i3] + this.rows.get(cellBottomRows[i3] - this.rowWindowStart)[i3].getRowspan()) - 1 < maxRowGroupFinish) {
                        rowGroupComplete = false;
                    }
                    i3++;
                }
            }
            if (rowGroupComplete) {
                rowGroups.add(new RowRange(currentRowGroupStart, maxRowGroupFinish));
            }
        }
        return rowGroups;
    }

    private void initializeRows() {
        this.rows = new ArrayList();
        this.currentColumn = -1;
    }

    private boolean cellBelongsToAnyRowGroup(Cell cell, List<RowRange> rowGroups) {
        if (rowGroups == null || rowGroups.size() <= 0 || cell.getRow() < rowGroups.get(0).getStartRow() || cell.getRow() > rowGroups.get(rowGroups.size() - 1).getFinishRow()) {
            return false;
        }
        return true;
    }

    private void ensureHeaderIsInitialized() {
        if (this.header == null) {
            this.header = new Table(this.columnWidths);
            UnitValue width = getWidth();
            if (width != null) {
                this.header.setWidth(width);
            }
            this.header.getAccessibilityProperties().setRole(StandardRoles.THEAD);
            if (hasOwnProperty(114)) {
                this.header.setBorderCollapse((BorderCollapsePropertyValue) getProperty(114));
            }
            if (hasOwnProperty(115)) {
                this.header.setHorizontalBorderSpacing(((Float) getProperty(115)).floatValue());
            }
            if (hasOwnProperty(116)) {
                this.header.setVerticalBorderSpacing(((Float) getProperty(116)).floatValue());
            }
        }
    }

    private void ensureFooterIsInitialized() {
        if (this.footer == null) {
            this.footer = new Table(this.columnWidths);
            UnitValue width = getWidth();
            if (width != null) {
                this.footer.setWidth(width);
            }
            this.footer.getAccessibilityProperties().setRole(StandardRoles.TFOOT);
            if (hasOwnProperty(114)) {
                this.footer.setBorderCollapse((BorderCollapsePropertyValue) getProperty(114));
            }
            if (hasOwnProperty(115)) {
                this.footer.setHorizontalBorderSpacing(((Float) getProperty(115)).floatValue());
            }
            if (hasOwnProperty(116)) {
                this.footer.setVerticalBorderSpacing(((Float) getProperty(116)).floatValue());
            }
        }
    }

    private void initializeLargeTable(boolean largeTable) {
        this.isComplete = !largeTable;
        if (largeTable) {
            setWidth(UnitValue.createPercentValue(100.0f));
            setFixedLayout();
        }
    }

    public static class RowRange {
        int finishRow;
        int startRow;

        public RowRange(int startRow2, int finishRow2) {
            this.startRow = startRow2;
            this.finishRow = finishRow2;
        }

        public int getStartRow() {
            return this.startRow;
        }

        public int getFinishRow() {
            return this.finishRow;
        }
    }
}

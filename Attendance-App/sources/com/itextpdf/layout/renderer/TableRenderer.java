package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.CaptionSide;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.slf4j.LoggerFactory;

public class TableRenderer extends AbstractRenderer {
    TableBorders bordersHandler;
    protected DivRenderer captionRenderer;
    private float[] columnWidths;
    private float[] countedColumnWidth;
    protected TableRenderer footerRenderer;
    protected TableRenderer headerRenderer;
    private List<Float> heights;
    protected boolean isOriginalNonSplitRenderer;
    protected Table.RowRange rowRange;
    protected List<CellRenderer[]> rows;
    private float topBorderMaxWidth;
    private float totalWidthForColumns;

    private TableRenderer() {
        this.rows = new ArrayList();
        this.isOriginalNonSplitRenderer = true;
        this.columnWidths = null;
        this.heights = new ArrayList();
        this.countedColumnWidth = null;
    }

    public TableRenderer(Table modelElement, Table.RowRange rowRange2) {
        super((IElement) modelElement);
        this.rows = new ArrayList();
        this.isOriginalNonSplitRenderer = true;
        this.columnWidths = null;
        this.heights = new ArrayList();
        this.countedColumnWidth = null;
        setRowRange(rowRange2);
    }

    public TableRenderer(Table modelElement) {
        this(modelElement, new Table.RowRange(0, modelElement.getNumberOfRows() - 1));
    }

    public void addChild(IRenderer renderer) {
        if (renderer instanceof CellRenderer) {
            Cell cell = (Cell) renderer.getModelElement();
            this.rows.get(((cell.getRow() - this.rowRange.getStartRow()) + cell.getRowspan()) - 1)[cell.getCol()] = (CellRenderer) renderer;
            return;
        }
        LoggerFactory.getLogger((Class<?>) TableRenderer.class).error("Only CellRenderer could be added");
    }

    /* access modifiers changed from: protected */
    public Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
        if (this.bordersHandler instanceof SeparatedTableBorders) {
            super.applyBorderBox(rect, borders, reverse);
        }
        return rect;
    }

    /* access modifiers changed from: protected */
    public Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
        if (this.bordersHandler instanceof SeparatedTableBorders) {
            super.applyPaddings(rect, paddings, reverse);
        }
        return rect;
    }

    public Rectangle applyPaddings(Rectangle rect, boolean reverse) {
        if (this.bordersHandler instanceof SeparatedTableBorders) {
            super.applyPaddings(rect, reverse);
        }
        return rect;
    }

    private Rectangle applySpacing(Rectangle rect, float horizontalSpacing, float verticalSpacing, boolean reverse) {
        if (!(this.bordersHandler instanceof SeparatedTableBorders)) {
            return rect;
        }
        return rect.applyMargins(verticalSpacing / 2.0f, horizontalSpacing / 2.0f, verticalSpacing / 2.0f, horizontalSpacing / 2.0f, reverse);
    }

    private Rectangle applySingleSpacing(Rectangle rect, float spacing, boolean isHorizontal, boolean reverse) {
        if (!(this.bordersHandler instanceof SeparatedTableBorders)) {
            return rect;
        }
        if (isHorizontal) {
            return rect.applyMargins(0.0f, spacing / 2.0f, 0.0f, spacing / 2.0f, reverse);
        }
        return rect.applyMargins(spacing / 2.0f, 0.0f, spacing / 2.0f, 0.0f, reverse);
    }

    /* access modifiers changed from: package-private */
    public Table getTable() {
        return (Table) getModelElement();
    }

    private void initializeHeaderAndFooter(boolean isFirstOnThePage) {
        Table table = (Table) getModelElement();
        Border[] tableBorder = getBorders();
        Table headerElement = table.getHeader();
        boolean footerShouldBeApplied = false;
        boolean headerShouldBeApplied = (table.isComplete() || !this.rows.isEmpty()) && isFirstOnThePage && (!table.isSkipFirstHeader() || !(this.rowRange.getStartRow() == 0 && this.isOriginalNonSplitRenderer)) && !Boolean.TRUE.equals(getOwnProperty(97));
        if (headerElement != null && headerShouldBeApplied) {
            this.headerRenderer = initFooterOrHeaderRenderer(false, tableBorder);
        }
        Table footerElement = table.getFooter();
        if ((!table.isComplete() || table.getLastRowBottomBorder().size() == 0 || !table.isSkipLastFooter()) && !Boolean.TRUE.equals(getOwnProperty(96))) {
            footerShouldBeApplied = true;
        }
        if (footerElement != null && footerShouldBeApplied) {
            this.footerRenderer = initFooterOrHeaderRenderer(true, tableBorder);
        }
    }

    private void initializeCaptionRenderer(Div caption) {
        if (this.isOriginalNonSplitRenderer && caption != null) {
            DivRenderer divRenderer = (DivRenderer) caption.createRendererSubTree();
            this.captionRenderer = divRenderer;
            divRenderer.setParent(this.parent);
            LayoutTaggingHelper taggingHelper = (LayoutTaggingHelper) getProperty(108);
            if (taggingHelper != null) {
                taggingHelper.addKidsHint((IPropertyContainer) this, (Iterable<? extends IPropertyContainer>) Collections.singletonList(this.captionRenderer));
                LayoutTaggingHelper.addTreeHints(taggingHelper, this.captionRenderer);
            }
        }
    }

    private boolean isOriginalRenderer() {
        return this.isOriginalNonSplitRenderer && !isFooterRenderer() && !isHeaderRenderer();
    }

    /* JADX WARNING: type inference failed for: r0v20, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r13v12, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r13v21 */
    /* JADX WARNING: type inference failed for: r13v23 */
    /* JADX WARNING: type inference failed for: r0v51 */
    /* JADX WARNING: type inference failed for: r0v52 */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x02e8  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0339  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x034c  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x03b0  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0492  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0494  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x04aa  */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x04cd  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x07bc  */
    /* JADX WARNING: Removed duplicated region for block: B:301:0x09c3  */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x09dc  */
    /* JADX WARNING: Removed duplicated region for block: B:407:0x0d28  */
    /* JADX WARNING: Removed duplicated region for block: B:408:0x0d33  */
    /* JADX WARNING: Removed duplicated region for block: B:607:0x12ea A[LOOP:0: B:155:0x04c3->B:607:0x12ea, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:654:0x1444  */
    /* JADX WARNING: Removed duplicated region for block: B:664:0x1462  */
    /* JADX WARNING: Removed duplicated region for block: B:665:0x146e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:671:0x148f  */
    /* JADX WARNING: Removed duplicated region for block: B:693:0x1522  */
    /* JADX WARNING: Removed duplicated region for block: B:708:0x1584  */
    /* JADX WARNING: Removed duplicated region for block: B:711:0x15a3 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:721:0x164d  */
    /* JADX WARNING: Removed duplicated region for block: B:722:0x1653  */
    /* JADX WARNING: Removed duplicated region for block: B:729:0x167c  */
    /* JADX WARNING: Removed duplicated region for block: B:735:0x1696  */
    /* JADX WARNING: Removed duplicated region for block: B:736:0x1698  */
    /* JADX WARNING: Removed duplicated region for block: B:741:0x16a7  */
    /* JADX WARNING: Removed duplicated region for block: B:742:0x16a9  */
    /* JADX WARNING: Removed duplicated region for block: B:758:0x1323 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:759:0x0d4c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x02b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.layout.LayoutResult layout(com.itextpdf.layout.layout.LayoutContext r88) {
        /*
            r87 = this;
            r11 = r87
            java.lang.Float r0 = r87.retrieveMinHeight()
            java.lang.Float r12 = r87.retrieveMaxHeight()
            com.itextpdf.layout.layout.LayoutArea r13 = r88.getArea()
            boolean r14 = r88.isClippedHeight()
            r7 = 0
            com.itextpdf.kernel.geom.Rectangle r1 = r13.getBBox()
            com.itextpdf.kernel.geom.Rectangle r10 = r1.clone()
            com.itextpdf.layout.IPropertyContainer r1 = r87.getModelElement()
            r21 = r1
            com.itextpdf.layout.element.Table r21 = (com.itextpdf.layout.element.Table) r21
            boolean r1 = r21.isComplete()
            r9 = 43
            r8 = 0
            if (r1 != 0) goto L_0x0033
            com.itextpdf.layout.property.UnitValue r1 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r11.setProperty(r9, r1)
        L_0x0033:
            com.itextpdf.layout.element.Table$RowRange r1 = r11.rowRange
            int r1 = r1.getStartRow()
            r15 = 46
            if (r1 == 0) goto L_0x0044
            com.itextpdf.layout.property.UnitValue r1 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r11.setProperty(r15, r1)
        L_0x0044:
            java.util.List<java.lang.Float> r1 = r11.heights
            r1.clear()
            java.util.List r1 = r11.childRenderers
            r1.clear()
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r6 = r1
            com.itextpdf.layout.IPropertyContainer r1 = r87.getModelElement()
            com.itextpdf.layout.element.Table r1 = (com.itextpdf.layout.element.Table) r1
            int r5 = r1.getNumberOfColumns()
            java.util.List r22 = r21.getLastRowBottomBorder()
            boolean r1 = r21.isComplete()
            r3 = 1
            if (r1 == 0) goto L_0x0071
            int r1 = r22.size()
            if (r1 != 0) goto L_0x0071
            r1 = 1
            goto L_0x0072
        L_0x0071:
            r1 = 0
        L_0x0072:
            r23 = r1
            com.itextpdf.layout.element.Table$RowRange r1 = r11.rowRange
            int r1 = r1.getStartRow()
            if (r1 == 0) goto L_0x0085
            boolean r1 = r11.isFirstOnRootArea(r3)
            if (r1 == 0) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            r1 = 0
            goto L_0x0086
        L_0x0085:
            r1 = 1
        L_0x0086:
            r2 = r1
            boolean r1 = r87.isFooterRenderer()
            if (r1 != 0) goto L_0x00d5
            boolean r1 = r87.isHeaderRenderer()
            if (r1 != 0) goto L_0x00d5
            boolean r1 = r11.isOriginalNonSplitRenderer
            if (r1 == 0) goto L_0x00d5
            com.itextpdf.layout.property.BorderCollapsePropertyValue r1 = com.itextpdf.layout.property.BorderCollapsePropertyValue.SEPARATE
            r3 = 114(0x72, float:1.6E-43)
            java.lang.Object r3 = r11.getProperty(r3)
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00bb
            com.itextpdf.layout.renderer.SeparatedTableBorders r3 = new com.itextpdf.layout.renderer.SeparatedTableBorders
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r4 = r11.rows
            com.itextpdf.layout.borders.Border[] r15 = r87.getBorders()
            if (r23 != 0) goto L_0x00b6
            com.itextpdf.layout.element.Table$RowRange r9 = r11.rowRange
            int r9 = r9.getStartRow()
            goto L_0x00b7
        L_0x00b6:
            r9 = 0
        L_0x00b7:
            r3.<init>(r4, r5, r15, r9)
            goto L_0x00d0
        L_0x00bb:
            com.itextpdf.layout.renderer.CollapsedTableBorders r3 = new com.itextpdf.layout.renderer.CollapsedTableBorders
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r4 = r11.rows
            com.itextpdf.layout.borders.Border[] r9 = r87.getBorders()
            if (r23 != 0) goto L_0x00cc
            com.itextpdf.layout.element.Table$RowRange r15 = r11.rowRange
            int r15 = r15.getStartRow()
            goto L_0x00cd
        L_0x00cc:
            r15 = 0
        L_0x00cd:
            r3.<init>(r4, r5, r9, r15)
        L_0x00d0:
            r11.bordersHandler = r3
            r3.initializeBorders()
        L_0x00d5:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.element.Table$RowRange r3 = r11.rowRange
            int r3 = r3.getStartRow()
            com.itextpdf.layout.element.Table$RowRange r4 = r11.rowRange
            int r4 = r4.getFinishRow()
            r1.setRowRange(r3, r4)
            r11.initializeHeaderAndFooter(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r3 = r11.isOriginalNonSplitRenderer
            boolean r4 = r87.isFooterRenderer()
            if (r4 != 0) goto L_0x00fc
            boolean r4 = r87.isHeaderRenderer()
            if (r4 == 0) goto L_0x00fa
            goto L_0x00fc
        L_0x00fa:
            r4 = 0
            goto L_0x00fd
        L_0x00fc:
            r4 = 1
        L_0x00fd:
            com.itextpdf.layout.renderer.TableRenderer r9 = r11.headerRenderer
            com.itextpdf.layout.renderer.TableRenderer r15 = r11.footerRenderer
            r25 = r2
            r2 = r3
            r8 = 1
            r3 = r4
            r8 = 0
            r4 = r87
            r28 = r5
            r5 = r9
            r9 = r6
            r6 = r15
            r1.updateBordersOnNewPage(r2, r3, r4, r5, r6)
            boolean r1 = r11.isOriginalNonSplitRenderer
            if (r1 == 0) goto L_0x0118
            r87.correctRowRange()
        L_0x0118:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r1 == 0) goto L_0x012f
            r1 = 115(0x73, float:1.61E-43)
            java.lang.Float r2 = r11.getPropertyAsFloat(r1)
            if (r2 == 0) goto L_0x012f
            java.lang.Float r1 = r11.getPropertyAsFloat(r1)
            float r1 = r1.floatValue()
            goto L_0x0130
        L_0x012f:
            r1 = 0
        L_0x0130:
            r15 = r1
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r1 == 0) goto L_0x0148
            r1 = 116(0x74, float:1.63E-43)
            java.lang.Float r2 = r11.getPropertyAsFloat(r1)
            if (r2 == 0) goto L_0x0148
            java.lang.Float r1 = r11.getPropertyAsFloat(r1)
            float r1 = r1.floatValue()
            goto L_0x0149
        L_0x0148:
            r1 = 0
        L_0x0149:
            r6 = r1
            if (r23 != 0) goto L_0x0151
            if (r25 != 0) goto L_0x0151
            r10.increaseHeight(r6)
        L_0x0151:
            boolean r1 = r87.isOriginalRenderer()
            if (r1 == 0) goto L_0x015a
            r11.applyMarginsAndPaddingsAndCalculateColumnWidths(r10)
        L_0x015a:
            float r5 = r87.getTableWidth()
            r1 = 0
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 89
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r3)
            boolean r4 = r2.equals(r3)
            if (r4 == 0) goto L_0x0179
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r2 = new com.itextpdf.layout.margincollapse.MarginsCollapseHandler
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r3 = r88.getMarginsCollapseInfo()
            r2.<init>(r11, r3)
            r1 = r2
            r3 = r1
            goto L_0x017a
        L_0x0179:
            r3 = r1
        L_0x017a:
            java.util.List r2 = r88.getFloatRendererAreas()
            float r1 = com.itextpdf.layout.renderer.FloatingHelper.calculateClearHeightCorrection(r11, r2, r10)
            r8 = 99
            java.lang.Object r8 = r11.getProperty(r8)
            com.itextpdf.layout.property.FloatPropertyValue r8 = (com.itextpdf.layout.property.FloatPropertyValue) r8
            boolean r16 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r11, r8)
            if (r16 == 0) goto L_0x019a
            r10.decreaseHeight(r1)
            com.itextpdf.layout.renderer.FloatingHelper.adjustFloatedTableLayoutBox(r11, r10, r5, r2, r8)
            r16 = r6
            r6 = r1
            goto L_0x01a5
        L_0x019a:
            r16 = r6
            java.lang.Float r6 = java.lang.Float.valueOf(r5)
            float r1 = com.itextpdf.layout.renderer.FloatingHelper.adjustLayoutBoxAccordingToFloats(r2, r10, r6, r1, r3)
            r6 = r1
        L_0x01a5:
            if (r4 == 0) goto L_0x01aa
            r3.startMarginsCollapse(r10)
        L_0x01aa:
            r1 = 0
            r11.applyMargins(r10, r1)
            r1 = 1
            r11.applyFixedXOrYPosition(r1, r10)
            r1 = 0
            r11.applyPaddings(r10, r1)
            r1 = 26
            if (r12 == 0) goto L_0x01ee
            float r17 = r12.floatValue()
            float r19 = r10.getHeight()
            int r17 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r17 > 0) goto L_0x01ee
            r17 = r2
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r19 = r3
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r1)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x01f2
            float r2 = r10.getHeight()
            float r3 = r12.floatValue()
            float r2 = r2 - r3
            com.itextpdf.kernel.geom.Rectangle r2 = r10.moveUp(r2)
            float r3 = r12.floatValue()
            r2.setHeight(r3)
            r7 = 1
            r30 = r7
            goto L_0x01f4
        L_0x01ee:
            r17 = r2
            r19 = r3
        L_0x01f2:
            r30 = r7
        L_0x01f4:
            com.itextpdf.layout.element.Table r2 = r87.getTable()
            com.itextpdf.layout.element.Div r2 = r2.getCaption()
            r11.initializeCaptionRenderer(r2)
            com.itextpdf.layout.renderer.DivRenderer r2 = r11.captionRenderer
            if (r2 == 0) goto L_0x02b0
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r2 = r2.getMinMaxWidth()
            float r7 = r2.getMinWidth()
            com.itextpdf.layout.renderer.DivRenderer r2 = r11.captionRenderer
            com.itextpdf.layout.layout.LayoutContext r3 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r1 = new com.itextpdf.layout.layout.LayoutArea
            r20 = r4
            int r4 = r13.getPageNumber()
            r32 = r6
            com.itextpdf.kernel.geom.Rectangle r6 = new com.itextpdf.kernel.geom.Rectangle
            r33 = r8
            float r8 = r10.getX()
            r34 = r12
            float r12 = r10.getY()
            r35 = r0
            float r0 = java.lang.Math.max(r5, r7)
            r36 = r5
            float r5 = r10.getHeight()
            r6.<init>(r8, r12, r0, r5)
            r1.<init>(r4, r6)
            if (r30 != 0) goto L_0x0240
            if (r14 == 0) goto L_0x023e
            goto L_0x0240
        L_0x023e:
            r4 = 0
            goto L_0x0241
        L_0x0240:
            r4 = 1
        L_0x0241:
            r3.<init>((com.itextpdf.layout.layout.LayoutArea) r1, (boolean) r4)
            com.itextpdf.layout.layout.LayoutResult r0 = r2.layout(r3)
            int r1 = r0.getStatus()
            r2 = 1
            if (r2 == r1) goto L_0x026f
            com.itextpdf.layout.layout.LayoutResult r8 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r4 = 0
            com.itextpdf.layout.renderer.IRenderer r6 = r0.getCauseOfNothing()
            r1 = r8
            r12 = r17
            r5 = r19
            r37 = r20
            r39 = r5
            r38 = r36
            r5 = r87
            r36 = r12
            r12 = r16
            r40 = r32
            r1.<init>(r2, r3, r4, r5, r6)
            return r8
        L_0x026f:
            r12 = r16
            r39 = r19
            r37 = r20
            r40 = r32
            r38 = r36
            r36 = r17
            com.itextpdf.layout.layout.LayoutArea r1 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.property.CaptionSide r2 = com.itextpdf.layout.property.CaptionSide.BOTTOM
            com.itextpdf.layout.element.Div r3 = r21.getCaption()
            r4 = 119(0x77, float:1.67E-43)
            java.lang.Object r3 = r3.getProperty(r4)
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x02ac
            com.itextpdf.layout.renderer.DivRenderer r2 = r11.captionRenderer
            float r3 = r10.getHeight()
            float r3 = r3 - r1
            float r3 = -r3
            r4 = 0
            r2.move(r4, r3)
            r10.decreaseHeight(r1)
            r10.moveUp(r1)
            goto L_0x02c2
        L_0x02ac:
            r10.decreaseHeight(r1)
            goto L_0x02c2
        L_0x02b0:
            r35 = r0
            r37 = r4
            r38 = r5
            r40 = r6
            r33 = r8
            r34 = r12
            r12 = r16
            r36 = r17
            r39 = r19
        L_0x02c2:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            int r1 = r13.getPageNumber()
            com.itextpdf.kernel.geom.Rectangle r2 = new com.itextpdf.kernel.geom.Rectangle
            float r3 = r10.getX()
            float r4 = r10.getY()
            float r5 = r10.getHeight()
            float r4 = r4 + r5
            r8 = r38
            r5 = 0
            r2.<init>(r3, r4, r8, r5)
            r0.<init>(r1, r2)
            r11.occupiedArea = r0
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.footerRenderer
            r7 = 10
            if (r0 == 0) goto L_0x03aa
            float r1 = r10.getWidth()
            r11.prepareFooterOrHeaderRendererForLayout(r0, r1)
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r0 = r11.rows
            int r0 = r0.size()
            if (r0 != 0) goto L_0x030c
            if (r23 != 0) goto L_0x02fb
            r2 = 0
            goto L_0x030d
        L_0x02fb:
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.headerRenderer
            if (r0 == 0) goto L_0x030a
            com.itextpdf.layout.renderer.TableBorders r0 = r0.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            r2 = 0
            r0.collapseTableWithFooter(r1, r2)
            goto L_0x0316
        L_0x030a:
            r2 = 0
            goto L_0x0316
        L_0x030c:
            r2 = 0
        L_0x030d:
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            r0.collapseTableWithFooter(r1, r2)
        L_0x0316:
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutContext r1 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r2 = new com.itextpdf.layout.layout.LayoutArea
            int r3 = r13.getPageNumber()
            r2.<init>(r3, r10)
            if (r30 != 0) goto L_0x032a
            if (r14 == 0) goto L_0x0328
            goto L_0x032a
        L_0x0328:
            r4 = 0
            goto L_0x032b
        L_0x032a:
            r4 = 1
        L_0x032b:
            r1.<init>((com.itextpdf.layout.layout.LayoutArea) r2, (boolean) r4)
            com.itextpdf.layout.layout.LayoutResult r0 = r0.layout(r1)
            int r1 = r0.getStatus()
            r2 = 1
            if (r1 == r2) goto L_0x034c
            r11.deleteOwnProperty(r7)
            com.itextpdf.layout.layout.LayoutResult r7 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r4 = 0
            com.itextpdf.layout.renderer.IRenderer r6 = r0.getCauseOfNothing()
            r1 = r7
            r5 = r87
            r1.<init>(r2, r3, r4, r5, r6)
            return r7
        L_0x034c:
            com.itextpdf.layout.layout.LayoutArea r1 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            float r3 = r10.getHeight()
            float r3 = r3 - r1
            float r3 = -r3
            r4 = 0
            r2.move(r4, r3)
            com.itextpdf.kernel.geom.Rectangle r2 = r10.moveUp(r1)
            r2.decreaseHeight(r1)
            com.itextpdf.kernel.geom.Rectangle r2 = r10.moveDown(r12)
            r2.increaseHeight(r12)
            boolean r2 = r21.isEmpty()
            if (r2 != 0) goto L_0x0392
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r2 = r2.bordersHandler
            float r2 = r2.getMaxTopWidth()
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r3 = r3.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r3.decreaseHeight(r2)
            com.itextpdf.kernel.geom.Rectangle r3 = r10.moveDown(r2)
            r3.increaseHeight(r2)
        L_0x0392:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 26
            java.lang.Boolean r4 = r11.getPropertyAsBoolean(r3)
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x03aa
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            r4 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r4)
            r2.setProperty(r3, r5)
        L_0x03aa:
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.headerRenderer
            r6 = 13
            if (r0 == 0) goto L_0x0452
            float r1 = r10.getWidth()
            r11.prepareFooterOrHeaderRendererForLayout(r0, r1)
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r0 = r11.rows
            int r0 = r0.size()
            if (r0 == 0) goto L_0x03cf
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            boolean r2 = r21.isEmpty()
            r3 = 1
            r2 = r2 ^ r3
            r0.collapseTableWithHeader(r1, r2)
            goto L_0x03dd
        L_0x03cf:
            r3 = 1
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.footerRenderer
            if (r0 == 0) goto L_0x03dd
            com.itextpdf.layout.renderer.TableBorders r0 = r0.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            r0.collapseTableWithHeader(r1, r3)
        L_0x03dd:
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            float r0 = r0.getMaxTopWidth()
            r11.topBorderMaxWidth = r0
            com.itextpdf.layout.renderer.TableRenderer r0 = r11.headerRenderer
            com.itextpdf.layout.layout.LayoutContext r1 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r2 = new com.itextpdf.layout.layout.LayoutArea
            int r3 = r13.getPageNumber()
            r2.<init>(r3, r10)
            if (r30 != 0) goto L_0x03f9
            if (r14 == 0) goto L_0x03f7
            goto L_0x03f9
        L_0x03f7:
            r4 = 0
            goto L_0x03fa
        L_0x03f9:
            r4 = 1
        L_0x03fa:
            r1.<init>((com.itextpdf.layout.layout.LayoutArea) r2, (boolean) r4)
            com.itextpdf.layout.layout.LayoutResult r0 = r0.layout(r1)
            int r1 = r0.getStatus()
            r2 = 1
            if (r1 == r2) goto L_0x041b
            r11.deleteOwnProperty(r6)
            com.itextpdf.layout.layout.LayoutResult r7 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r4 = 0
            com.itextpdf.layout.renderer.IRenderer r6 = r0.getCauseOfNothing()
            r1 = r7
            r5 = r87
            r1.<init>(r2, r3, r4, r5, r6)
            return r7
        L_0x041b:
            com.itextpdf.layout.layout.LayoutArea r1 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            r10.decreaseHeight(r1)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.moveDown(r1)
            r2.increaseHeight(r1)
            com.itextpdf.layout.renderer.TableBorders r2 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r2.fixHeaderOccupiedArea(r3, r10)
            r10.increaseHeight(r12)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.moveUp(r12)
            r2.decreaseHeight(r12)
        L_0x0452:
            r0 = 0
            r11.applySpacing(r10, r15, r12, r0)
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r2 = 1
            r11.applySingleSpacing(r1, r15, r2, r0)
            com.itextpdf.layout.layout.LayoutArea r0 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r32 = 1073741824(0x40000000, float:2.0)
            float r1 = r12 / r32
            r0.moveDown(r1)
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            float r0 = r0.getMaxTopWidth()
            r11.topBorderMaxWidth = r0
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            r1 = 0
            r0.applyLeftAndRightTableBorder(r10, r1)
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r1.getBBox()
            boolean r1 = r21.isEmpty()
            if (r1 != 0) goto L_0x0494
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0492
            goto L_0x0494
        L_0x0492:
            r1 = 0
            goto L_0x0495
        L_0x0494:
            r1 = 1
        L_0x0495:
            r20 = 0
            r5 = r15
            r4 = 46
            r15 = r0
            r17 = r10
            r18 = r1
            r19 = r23
            r15.applyTopTableBorder(r16, r17, r18, r19, r20)
            com.itextpdf.layout.renderer.TableBorders r0 = r11.bordersHandler
            boolean r1 = r0 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r1 == 0) goto L_0x04b6
            float r0 = r0.getMaxBottomWidth()
            com.itextpdf.kernel.geom.Rectangle r1 = r10.moveUp(r0)
            r1.decreaseHeight(r0)
        L_0x04b6:
            r0 = r28
            com.itextpdf.layout.layout.LayoutResult[] r15 = new com.itextpdf.layout.layout.LayoutResult[r0]
            int[] r3 = new int[r0]
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = r1
            r1 = 0
        L_0x04c3:
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r7 = r11.rows
            int r7 = r7.size()
            java.lang.Class<com.itextpdf.layout.renderer.TableRenderer> r28 = com.itextpdf.layout.renderer.TableRenderer.class
            if (r1 >= r7) goto L_0x1323
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r4 = 1
            if (r1 != r4) goto L_0x04fe
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r42 = r8
            r6 = 26
            java.lang.Object r8 = r11.getProperty(r6)
            boolean r4 = r4.equals(r8)
            if (r4 == 0) goto L_0x0500
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            java.lang.Object r8 = r11.getOwnProperty(r6)
            boolean r4 = r4.equals(r8)
            if (r4 == 0) goto L_0x04f5
            r11.deleteOwnProperty(r6)
            goto L_0x0500
        L_0x04f5:
            r4 = 0
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r4)
            r11.setProperty(r6, r8)
            goto L_0x0500
        L_0x04fe:
            r42 = r8
        L_0x0500:
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r4 = r11.rows
            java.lang.Object r4 = r4.get(r1)
            r8 = r4
            com.itextpdf.layout.renderer.CellRenderer[] r8 = (com.itextpdf.layout.renderer.CellRenderer[]) r8
            r4 = 0
            r6 = 0
            r43 = 1
            r44 = 0
            java.util.ArrayList r45 = new java.util.ArrayList
            r45.<init>()
            r46 = r45
            java.util.ArrayDeque r45 = new java.util.ArrayDeque
            r45.<init>()
            r47 = r45
            r45 = 0
            r48 = r4
            r4 = r45
        L_0x0523:
            r45 = r6
            int r6 = r8.length
            if (r4 >= r6) goto L_0x0548
            r6 = r8[r4]
            if (r6 == 0) goto L_0x053b
            com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo r6 = new com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo
            r49 = r0
            r0 = r8[r4]
            r6.<init>(r0, r4, r1)
            r0 = r47
            r0.addLast(r6)
            goto L_0x053f
        L_0x053b:
            r49 = r0
            r0 = r47
        L_0x053f:
            int r4 = r4 + 1
            r47 = r0
            r6 = r45
            r0 = r49
            goto L_0x0523
        L_0x0548:
            r49 = r0
            r0 = r47
            r6 = 0
            r47 = 0
            r50 = r4
            com.itextpdf.layout.renderer.TableBorders r4 = r11.bordersHandler
            r51 = r6
            com.itextpdf.layout.element.Table$RowRange r6 = r11.rowRange
            int r6 = r6.getStartRow()
            int r6 = r6 + r1
            r4.setFinishRow(r6)
            com.itextpdf.layout.renderer.TableBorders r4 = r11.bordersHandler
            com.itextpdf.layout.element.Table$RowRange r6 = r11.rowRange
            int r6 = r6.getStartRow()
            int r6 = r6 + r1
            r27 = 1
            int r6 = r6 + 1
            com.itextpdf.layout.borders.Border r4 = r4.getWidestHorizontalBorder(r6)
            com.itextpdf.layout.renderer.TableBorders r6 = r11.bordersHandler
            r52 = r2
            com.itextpdf.layout.element.Table$RowRange r2 = r11.rowRange
            int r2 = r2.getFinishRow()
            r6.setFinishRow(r2)
            if (r4 != 0) goto L_0x0581
            r2 = 0
            goto L_0x0585
        L_0x0581:
            float r2 = r4.getWidth()
        L_0x0585:
            r6 = r48
            r86 = r43
            r43 = r2
            r2 = r44
            r44 = r4
            r4 = r86
        L_0x0591:
            int r48 = r0.size()
            r53 = r2
            if (r48 <= 0) goto L_0x0abd
            java.lang.Object r48 = r0.pop()
            r2 = r48
            com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo r2 = (com.itextpdf.layout.renderer.TableRenderer.CellRendererInfo) r2
            r48 = r6
            int r6 = r2.column
            r54 = r0
            com.itextpdf.layout.renderer.CellRenderer r0 = r2.cellRenderer
            r55 = r12
            r12 = 16
            java.lang.Integer r50 = r0.getPropertyAsInteger(r12)
            int r12 = r50.intValue()
            r56 = r5
            r5 = 60
            java.lang.Integer r50 = r0.getPropertyAsInteger(r5)
            int r5 = r50.intValue()
            r58 = r13
            r13 = 1
            if (r13 == r5) goto L_0x05c9
            r13 = 1
            r53 = r13
        L_0x05c9:
            int r13 = r2.finishRowInd
            r3[r6] = r13
            int r13 = r2.finishRowInd
            if (r1 == r13) goto L_0x05d3
            r13 = 1
            goto L_0x05d4
        L_0x05d3:
            r13 = 0
        L_0x05d4:
            r59 = r3
            r3 = 27
            boolean r3 = r0.hasOwnOrModelProperty(r3)
            if (r3 == 0) goto L_0x05e1
            r3 = 1
            r51 = r3
        L_0x05e1:
            r3 = 0
            r50 = 0
            r60 = r6
            r86 = r60
            r60 = r9
            r9 = r86
        L_0x05ec:
            r61 = r8
            int r8 = r6 + r12
            if (r9 >= r8) goto L_0x05fc
            float[] r8 = r11.countedColumnWidth
            r8 = r8[r9]
            float r3 = r3 + r8
            int r9 = r9 + 1
            r8 = r61
            goto L_0x05ec
        L_0x05fc:
            r8 = 0
        L_0x05fd:
            if (r8 >= r6) goto L_0x0608
            float[] r9 = r11.countedColumnWidth
            r9 = r9[r8]
            float r50 = r50 + r9
            int r8 = r8 + 1
            goto L_0x05fd
        L_0x0608:
            r8 = 0
            int r9 = r1 + -1
        L_0x060b:
            r62 = r1
            int r1 = r2.finishRowInd
            int r1 = r1 - r5
            if (r9 <= r1) goto L_0x0626
            if (r9 < 0) goto L_0x0626
            java.util.List<java.lang.Float> r1 = r11.heights
            java.lang.Object r1 = r1.get(r9)
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            float r8 = r8 + r1
            int r9 = r9 + -1
            r1 = r62
            goto L_0x060b
        L_0x0626:
            if (r13 == 0) goto L_0x062d
            if (r4 == 0) goto L_0x062b
            goto L_0x062d
        L_0x062b:
            r1 = 0
            goto L_0x0631
        L_0x062d:
            float r1 = r10.getHeight()
        L_0x0631:
            float r1 = r1 + r8
            float r9 = r10.getY()
            if (r13 == 0) goto L_0x0640
            if (r4 == 0) goto L_0x063b
            goto L_0x0640
        L_0x063b:
            float r63 = r10.getHeight()
            goto L_0x0642
        L_0x0640:
            r63 = 0
        L_0x0642:
            float r9 = r9 + r63
            r63 = r4
            com.itextpdf.kernel.geom.Rectangle r4 = new com.itextpdf.kernel.geom.Rectangle
            float r64 = r10.getX()
            r65 = r8
            float r8 = r64 + r50
            r4.<init>(r8, r9, r3, r1)
            com.itextpdf.layout.layout.LayoutArea r8 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r64 = r88.getArea()
            r66 = r1
            int r1 = r64.getPageNumber()
            r8.<init>(r1, r4)
            r1 = r8
            r8 = 75
            java.lang.Object r64 = r0.getProperty(r8)
            r67 = r4
            r4 = r64
            com.itextpdf.layout.property.VerticalAlignment r4 = (com.itextpdf.layout.property.VerticalAlignment) r4
            r64 = r9
            r9 = 0
            r0.setProperty(r8, r9)
            r9 = 77
            java.lang.Object r68 = r0.getProperty(r9)
            com.itextpdf.layout.property.UnitValue r68 = (com.itextpdf.layout.property.UnitValue) r68
            if (r68 == 0) goto L_0x068c
            boolean r69 = r68.isPercentValue()
            if (r69 == 0) goto L_0x068c
            com.itextpdf.layout.property.UnitValue r8 = com.itextpdf.layout.property.UnitValue.createPointValue(r3)
            r0.setProperty(r9, r8)
        L_0x068c:
            com.itextpdf.layout.renderer.TableBorders r8 = r11.bordersHandler
            int r9 = r2.finishRowInd
            float[] r8 = r8.getCellBorderIndents(r9, r6, r5, r12)
            com.itextpdf.layout.renderer.TableBorders r9 = r11.bordersHandler
            r77 = r3
            boolean r3 = r9 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r3 != 0) goto L_0x06b5
            com.itextpdf.kernel.geom.Rectangle r71 = r1.getBBox()
            r3 = 0
            r72 = r8[r3]
            r3 = 1
            r73 = r8[r3]
            r3 = 2
            r70 = r8[r3]
            float r74 = r70 + r43
            r3 = 3
            r75 = r8[r3]
            r76 = 0
            r70 = r9
            r70.applyCellIndents(r71, r72, r73, r74, r75, r76)
        L_0x06b5:
            com.itextpdf.kernel.geom.Rectangle r3 = r1.getBBox()
            float r3 = r3.getWidth()
            r9 = 108(0x6c, float:1.51E-43)
            java.lang.Object r70 = r11.getProperty(r9)
            r9 = r70
            com.itextpdf.layout.tagging.LayoutTaggingHelper r9 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r9
            if (r9 == 0) goto L_0x06d6
            r70 = r5
            java.util.List r5 = java.util.Collections.singletonList(r0)
            r9.addKidsHint((com.itextpdf.layout.IPropertyContainer) r11, (java.lang.Iterable<? extends com.itextpdf.layout.IPropertyContainer>) r5)
            com.itextpdf.layout.tagging.LayoutTaggingHelper.addTreeHints(r9, r0)
            goto L_0x06d8
        L_0x06d6:
            r70 = r5
        L_0x06d8:
            com.itextpdf.layout.renderer.IRenderer r5 = r0.setParent(r11)
            r71 = r12
            com.itextpdf.layout.layout.LayoutContext r12 = new com.itextpdf.layout.layout.LayoutContext
            if (r30 != 0) goto L_0x06e9
            if (r14 == 0) goto L_0x06e5
            goto L_0x06e9
        L_0x06e5:
            r72 = r8
            r8 = 0
            goto L_0x06ec
        L_0x06e9:
            r72 = r8
            r8 = 1
        L_0x06ec:
            r73 = r9
            r9 = 0
            r12.<init>(r1, r9, r7, r8)
            com.itextpdf.layout.layout.LayoutResult r5 = r5.layout(r12)
            r8 = 75
            r0.setProperty(r8, r4)
            int r8 = r5.getStatus()
            r9 = 3
            if (r8 == r9) goto L_0x070e
            com.itextpdf.layout.layout.LayoutArea r8 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r8 = r8.getBBox()
            r8.setWidth(r3)
            goto L_0x0716
        L_0x070e:
            if (r47 != 0) goto L_0x0716
            com.itextpdf.layout.renderer.IRenderer r8 = r5.getCauseOfNothing()
            r47 = r8
        L_0x0716:
            if (r13 == 0) goto L_0x078a
            int r8 = r5.getStatus()
            r9 = 1
            if (r8 == r9) goto L_0x0735
            r15[r6] = r5
            int r8 = r5.getStatus()
            r9 = 3
            if (r8 == r9) goto L_0x0735
            r8 = r15[r6]
            com.itextpdf.layout.renderer.IRenderer r8 = r8.getOverflowRenderer()
            com.itextpdf.layout.property.VerticalAlignment r9 = com.itextpdf.layout.property.VerticalAlignment.TOP
            r12 = 75
            r8.setProperty(r12, r9)
        L_0x0735:
            int r8 = r5.getStatus()
            r9 = 2
            if (r8 != r9) goto L_0x0758
            com.itextpdf.layout.renderer.IRenderer r8 = r5.getSplitRenderer()
            com.itextpdf.layout.renderer.CellRenderer r8 = (com.itextpdf.layout.renderer.CellRenderer) r8
            r61[r6] = r8
            r75 = r3
            r77 = r7
            r7 = r54
            r78 = r56
            r9 = r62
            r12 = r73
            r56 = r2
            r62 = r60
            r60 = r1
            goto L_0x0a73
        L_0x0758:
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r8 = r11.rows
            int r9 = r2.finishRowInd
            java.lang.Object r8 = r8.get(r9)
            com.itextpdf.layout.renderer.CellRenderer[] r8 = (com.itextpdf.layout.renderer.CellRenderer[]) r8
            r9 = 0
            r8[r6] = r9
            r61[r6] = r0
            java.lang.Integer r8 = java.lang.Integer.valueOf(r6)
            int r9 = r2.finishRowInd
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            r12 = r60
            r12.put(r8, r9)
            r60 = r1
            r75 = r3
            r77 = r7
            r7 = r54
            r78 = r56
            r9 = r62
            r56 = r2
            r62 = r12
            r12 = r73
            goto L_0x0a73
        L_0x078a:
            r12 = r60
            int r8 = r5.getStatus()
            r9 = 1
            if (r8 == r9) goto L_0x0a61
            if (r45 != 0) goto L_0x0a31
            com.itextpdf.layout.renderer.TableRenderer r8 = r11.footerRenderer
            if (r8 == 0) goto L_0x07b7
            boolean r8 = r21.isSkipLastFooter()
            if (r8 == 0) goto L_0x07b7
            boolean r8 = r21.isComplete()
            if (r8 == 0) goto L_0x07b7
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            r60 = r1
            r9 = 26
            java.lang.Object r1 = r11.getOwnProperty(r9)
            boolean r1 = r8.equals(r1)
            if (r1 != 0) goto L_0x07b9
            r1 = 1
            goto L_0x07ba
        L_0x07b7:
            r60 = r1
        L_0x07b9:
            r1 = 0
        L_0x07ba:
            if (r1 == 0) goto L_0x09c3
            com.itextpdf.layout.layout.LayoutArea r8 = new com.itextpdf.layout.layout.LayoutArea
            int r9 = r58.getPageNumber()
            r74 = r1
            com.itextpdf.kernel.geom.Rectangle r1 = r10.clone()
            r8.<init>(r9, r1)
            r1 = r8
            com.itextpdf.kernel.geom.Rectangle r8 = r1.getBBox()
            r9 = r56
            r56 = r2
            r2 = 1
            r11.applySingleSpacing(r8, r9, r2, r2)
            com.itextpdf.layout.renderer.TableBorders r2 = r11.bordersHandler
            com.itextpdf.layout.element.Table$RowRange r8 = r11.rowRange
            int r8 = r8.getStartRow()
            int r8 = r8 + r62
            com.itextpdf.layout.borders.Border r2 = r2.getWidestHorizontalBorder(r8)
            com.itextpdf.layout.renderer.TableBorders r8 = r11.bordersHandler
            boolean r8 = r8 instanceof com.itextpdf.layout.renderer.CollapsedTableBorders
            if (r8 == 0) goto L_0x07fe
            if (r2 == 0) goto L_0x07fe
            com.itextpdf.kernel.geom.Rectangle r8 = r1.getBBox()
            float r75 = r2.getWidth()
            r76 = r2
            float r2 = r75 / r32
            r8.increaseHeight(r2)
            goto L_0x0800
        L_0x07fe:
            r76 = r2
        L_0x0800:
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.headerRenderer
            if (r2 != 0) goto L_0x0811
            com.itextpdf.kernel.geom.Rectangle r2 = r1.getBBox()
            com.itextpdf.layout.renderer.TableBorders r8 = r11.bordersHandler
            float r8 = r8.getMaxTopWidth()
            r2.increaseHeight(r8)
        L_0x0811:
            com.itextpdf.layout.renderer.TableBorders r2 = r11.bordersHandler
            com.itextpdf.kernel.geom.Rectangle r8 = r1.getBBox()
            r75 = r3
            r3 = 1
            r2.applyLeftAndRightTableBorder(r8, r3)
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r2 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            com.itextpdf.kernel.geom.Rectangle r3 = r1.getBBox()
            float r8 = r55 / r32
            float r8 = r2 - r8
            com.itextpdf.kernel.geom.Rectangle r3 = r3.moveDown(r8)
            r3.increaseHeight(r2)
            com.itextpdf.layout.element.Table$RowRange r3 = new com.itextpdf.layout.element.Table$RowRange
            com.itextpdf.layout.element.Table$RowRange r8 = r11.rowRange
            int r8 = r8.getStartRow()
            int r8 = r8 + r62
            r77 = r7
            com.itextpdf.layout.element.Table$RowRange r7 = r11.rowRange
            int r7 = r7.getFinishRow()
            r3.<init>(r8, r7)
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.createOverflowRenderer(r3)
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r7 = r11.rows
            int r8 = r7.size()
            r78 = r9
            r9 = r62
            java.util.List r7 = r7.subList(r9, r8)
            r3.rows = r7
            r7 = 97
            r62 = r12
            r8 = 1
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r8)
            r3.setProperty(r7, r12)
            r7 = 96
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r8)
            r3.setProperty(r7, r12)
            r8 = 0
            com.itextpdf.layout.property.UnitValue r7 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r12 = 46
            r3.setProperty(r12, r7)
            com.itextpdf.layout.property.UnitValue r7 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r12 = 43
            r3.setProperty(r12, r7)
            r7 = 44
            com.itextpdf.layout.property.UnitValue r12 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r3.setProperty(r7, r12)
            r7 = 45
            com.itextpdf.layout.property.UnitValue r12 = com.itextpdf.layout.property.UnitValue.createPointValue(r8)
            r3.setProperty(r7, r12)
            com.itextpdf.layout.renderer.TableRenderer r7 = r11.headerRenderer
            if (r7 == 0) goto L_0x08a8
            com.itextpdf.layout.borders.Border r7 = com.itextpdf.layout.borders.Border.NO_BORDER
            r12 = 13
            r3.setProperty(r12, r7)
        L_0x08a8:
            com.itextpdf.layout.renderer.TableBorders r7 = r11.bordersHandler
            r3.bordersHandler = r7
            com.itextpdf.layout.renderer.TableBorders r7 = r11.bordersHandler
            com.itextpdf.layout.borders.Border[] r12 = r3.getBorders()
            r7.skipFooter(r12)
            com.itextpdf.layout.renderer.TableRenderer r7 = r11.headerRenderer
            if (r7 == 0) goto L_0x08c2
            com.itextpdf.layout.renderer.TableBorders r7 = r11.bordersHandler
            com.itextpdf.layout.borders.Border[] r12 = r3.getBorders()
            r7.skipHeader(r12)
        L_0x08c2:
            com.itextpdf.layout.renderer.TableBorders r7 = r3.bordersHandler
            int r7 = r7.startRow
            com.itextpdf.layout.renderer.TableBorders r12 = r3.bordersHandler
            r12.setStartRow(r9)
            com.itextpdf.kernel.geom.Rectangle r12 = r1.getBBox()
            float r12 = r12.getWidth()
            r11.prepareFooterOrHeaderRendererForLayout(r3, r12)
            com.itextpdf.layout.layout.LayoutContext r12 = new com.itextpdf.layout.layout.LayoutContext
            if (r30 != 0) goto L_0x08df
            if (r14 == 0) goto L_0x08dd
            goto L_0x08df
        L_0x08dd:
            r8 = 0
            goto L_0x08e0
        L_0x08df:
            r8 = 1
        L_0x08e0:
            r12.<init>((com.itextpdf.layout.layout.LayoutArea) r1, (boolean) r8)
            com.itextpdf.layout.layout.LayoutResult r8 = r3.layout(r12)
            com.itextpdf.layout.renderer.TableBorders r12 = r11.bordersHandler
            r12.setStartRow(r7)
            int r12 = r8.getStatus()
            r79 = r1
            r1 = 1
            if (r1 != r12) goto L_0x0991
            if (r73 == 0) goto L_0x08ff
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            r12 = r73
            r12.markArtifactHint((com.itextpdf.layout.IPropertyContainer) r1)
            goto L_0x0901
        L_0x08ff:
            r12 = r73
        L_0x0901:
            r1 = 0
            r11.footerRenderer = r1
            com.itextpdf.kernel.geom.Rectangle r1 = r10.increaseHeight(r2)
            r1.moveDown(r2)
            r1 = 10
            r11.deleteOwnProperty(r1)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            r73 = r2
            com.itextpdf.layout.element.Table$RowRange r2 = r11.rowRange
            int r2 = r2.getStartRow()
            int r2 = r2 + r9
            r1.setFinishRow(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.element.Table$RowRange r2 = r11.rowRange
            int r2 = r2.getStartRow()
            int r2 = r2 + r9
            r27 = 1
            int r2 = r2 + 1
            com.itextpdf.layout.borders.Border r44 = r1.getWidestHorizontalBorder(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.element.Table$RowRange r2 = r11.rowRange
            int r2 = r2.getFinishRow()
            r1.setFinishRow(r2)
            if (r44 != 0) goto L_0x093e
            r1 = 0
            goto L_0x0942
        L_0x093e:
            float r1 = r44.getWidth()
        L_0x0942:
            r43 = r1
            r54.clear()
            r46.clear()
            r1 = 0
        L_0x094b:
            r2 = r61
            r61 = r3
            int r3 = r2.length
            if (r1 >= r3) goto L_0x0974
            r3 = r2[r1]
            if (r3 == 0) goto L_0x0965
            com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo r3 = new com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo
            r80 = r7
            r7 = r2[r1]
            r3.<init>(r7, r1, r9)
            r7 = r54
            r7.addLast(r3)
            goto L_0x0969
        L_0x0965:
            r80 = r7
            r7 = r54
        L_0x0969:
            int r1 = r1 + 1
            r54 = r7
            r3 = r61
            r7 = r80
            r61 = r2
            goto L_0x094b
        L_0x0974:
            r80 = r7
            r7 = r54
            r8 = r2
            r50 = r6
            r0 = r7
            r1 = r9
            r6 = r48
            r2 = r53
            r12 = r55
            r13 = r58
            r3 = r59
            r9 = r62
            r4 = r63
            r7 = r77
            r5 = r78
            goto L_0x0591
        L_0x0991:
            r80 = r7
            r7 = r54
            r12 = r73
            r73 = r2
            r2 = r61
            r61 = r3
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 == 0) goto L_0x09ac
            com.itextpdf.layout.renderer.TableBorders r3 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            r54 = r8
            r8 = 0
            r3.collapseTableWithHeader(r1, r8)
            goto L_0x09af
        L_0x09ac:
            r54 = r8
            r8 = 0
        L_0x09af:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r3 = r3.bordersHandler
            r1.collapseTableWithFooter(r3, r8)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.borders.Border[] r1 = r1.tableBoundingBorders
            com.itextpdf.layout.borders.Border r3 = com.itextpdf.layout.borders.Border.NO_BORDER
            r29 = 2
            r1[r29] = r3
            goto L_0x09d8
        L_0x09c3:
            r74 = r1
            r75 = r3
            r77 = r7
            r7 = r54
            r78 = r56
            r9 = r62
            r8 = 0
            r56 = r2
            r62 = r12
            r2 = r61
            r12 = r73
        L_0x09d8:
            r1 = 0
        L_0x09d9:
            int r3 = r2.length
            if (r1 >= r3) goto L_0x0a2e
            r3 = r2[r1]
            if (r3 != 0) goto L_0x0a26
            int r3 = r9 + 1
        L_0x09e2:
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r8 = r11.rows
            int r8 = r8.size()
            if (r3 >= r8) goto L_0x0a23
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r8 = r11.rows
            java.lang.Object r8 = r8.get(r3)
            com.itextpdf.layout.renderer.CellRenderer[] r8 = (com.itextpdf.layout.renderer.CellRenderer[]) r8
            r8 = r8[r1]
            if (r8 == 0) goto L_0x0a1d
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r8 = r11.rows
            java.lang.Object r8 = r8.get(r3)
            com.itextpdf.layout.renderer.CellRenderer[] r8 = (com.itextpdf.layout.renderer.CellRenderer[]) r8
            r8 = r8[r1]
            r61 = r2
            r2 = 60
            java.lang.Integer r29 = r8.getPropertyAsInteger(r2)
            int r29 = r29.intValue()
            int r29 = r9 + r29
            r27 = 1
            int r2 = r29 + -1
            if (r2 < r3) goto L_0x0a28
            com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo r2 = new com.itextpdf.layout.renderer.TableRenderer$CellRendererInfo
            r2.<init>(r8, r1, r3)
            r7.addLast(r2)
            goto L_0x0a28
        L_0x0a1d:
            r61 = r2
            int r3 = r3 + 1
            r8 = 0
            goto L_0x09e2
        L_0x0a23:
            r61 = r2
            goto L_0x0a28
        L_0x0a26:
            r61 = r2
        L_0x0a28:
            int r1 = r1 + 1
            r2 = r61
            r8 = 0
            goto L_0x09d9
        L_0x0a2e:
            r61 = r2
            goto L_0x0a43
        L_0x0a31:
            r60 = r1
            r75 = r3
            r77 = r7
            r7 = r54
            r78 = r56
            r9 = r62
            r56 = r2
            r62 = r12
            r12 = r73
        L_0x0a43:
            r1 = 1
            r15[r6] = r5
            int r2 = r5.getStatus()
            r3 = 3
            if (r2 != r3) goto L_0x0a5e
            r2 = 0
            r3 = r15[r6]
            com.itextpdf.layout.renderer.IRenderer r3 = r3.getOverflowRenderer()
            r8 = 75
            r3.setProperty(r8, r4)
            r45 = r1
            r63 = r2
            goto L_0x0a73
        L_0x0a5e:
            r45 = r1
            goto L_0x0a73
        L_0x0a61:
            r60 = r1
            r75 = r3
            r77 = r7
            r7 = r54
            r78 = r56
            r9 = r62
            r56 = r2
            r62 = r12
            r12 = r73
        L_0x0a73:
            r8 = r46
            r8.add(r0)
            int r1 = r5.getStatus()
            r2 = 3
            if (r1 == r2) goto L_0x0a9d
            com.itextpdf.layout.layout.LayoutArea r1 = r5.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.renderer.TableBorders r2 = r11.bordersHandler
            r3 = r72
            float r2 = r2.getCellVerticalAddition(r3)
            float r1 = r1 + r2
            float r1 = r1 - r65
            r2 = r48
            float r1 = java.lang.Math.max(r2, r1)
            goto L_0x0aa2
        L_0x0a9d:
            r2 = r48
            r3 = r72
            r1 = r2
        L_0x0aa2:
            r50 = r6
            r0 = r7
            r46 = r8
            r2 = r53
            r12 = r55
            r13 = r58
            r3 = r59
            r8 = r61
            r4 = r63
            r7 = r77
            r5 = r78
            r6 = r1
            r1 = r9
            r9 = r62
            goto L_0x0591
        L_0x0abd:
            r59 = r3
            r63 = r4
            r78 = r5
            r77 = r7
            r61 = r8
            r62 = r9
            r55 = r12
            r58 = r13
            r8 = r46
            r2 = 3
            r29 = 2
            r7 = r0
            r9 = r1
            r0 = r6
            if (r63 == 0) goto L_0x0aff
            java.util.List<java.lang.Float> r1 = r11.heights
            java.lang.Float r3 = java.lang.Float.valueOf(r0)
            r1.add(r3)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r51)
            r3 = r52
            r3.add(r1)
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r1.moveDown(r0)
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r1.increaseHeight(r0)
            r10.decreaseHeight(r0)
            goto L_0x0b01
        L_0x0aff:
            r3 = r52
        L_0x0b01:
            if (r45 != 0) goto L_0x0b33
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r1 = r1.size()
            r4 = 1
            int r1 = r1 - r4
            if (r9 != r1) goto L_0x0b0e
            goto L_0x0b33
        L_0x0b0e:
            r41 = r0
            r29 = r3
            r54 = r7
            r83 = r8
            r84 = r9
            r85 = r10
            r27 = r33
            r26 = r42
            r13 = r53
            r46 = r59
            r82 = r61
            r24 = r62
            r12 = r63
            r53 = r77
            r81 = r78
            r0 = 1
            r33 = 43
            r48 = 46
            goto L_0x0bfc
        L_0x0b33:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            int r4 = r1.getStartRow()
            int r4 = r4 + r9
            r1.setFinishRow(r4)
            if (r63 != 0) goto L_0x0b59
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            int r1 = r1.getFinishRow()
            com.itextpdf.layout.renderer.TableBorders r4 = r11.bordersHandler
            int r4 = r4.getStartRow()
            if (r1 == r4) goto L_0x0b59
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            int r4 = r1.getFinishRow()
            r12 = 1
            int r4 = r4 - r12
            r1.setFinishRow(r4)
            goto L_0x0b5a
        L_0x0b59:
            r12 = 1
        L_0x0b5a:
            r1 = 0
            com.itextpdf.layout.renderer.TableRenderer r4 = r11.footerRenderer
            if (r4 == 0) goto L_0x0ba5
            boolean r4 = r21.isComplete()
            if (r4 == 0) goto L_0x0ba5
            boolean r4 = r21.isSkipLastFooter()
            if (r4 == 0) goto L_0x0ba5
            if (r45 != 0) goto L_0x0ba5
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r5 = 26
            java.lang.Object r6 = r11.getOwnProperty(r5)
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L_0x0b9f
            r4 = 108(0x6c, float:1.51E-43)
            java.lang.Object r6 = r11.getProperty(r4)
            com.itextpdf.layout.tagging.LayoutTaggingHelper r6 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r6
            if (r6 == 0) goto L_0x0b8a
            com.itextpdf.layout.renderer.TableRenderer r13 = r11.footerRenderer
            r6.markArtifactHint((com.itextpdf.layout.IPropertyContainer) r13)
        L_0x0b8a:
            r13 = 0
            r11.footerRenderer = r13
            boolean r20 = r21.isEmpty()
            if (r20 == 0) goto L_0x0b99
            r12 = 13
            r11.deleteOwnProperty(r12)
            goto L_0x0b9b
        L_0x0b99:
            r12 = 13
        L_0x0b9b:
            r1 = 1
            r17 = r1
            goto L_0x0bae
        L_0x0b9f:
            r4 = 108(0x6c, float:1.51E-43)
            r12 = 13
            r13 = 0
            goto L_0x0bac
        L_0x0ba5:
            r4 = 108(0x6c, float:1.51E-43)
            r5 = 26
            r12 = 13
            r13 = 0
        L_0x0bac:
            r17 = r1
        L_0x0bae:
            r20 = r45 ^ 1
            if (r63 != 0) goto L_0x0bb7
            if (r53 == 0) goto L_0x0bb7
            r31 = 1
            goto L_0x0bb9
        L_0x0bb7:
            r31 = 0
        L_0x0bb9:
            r6 = 26
            r16 = 10
            r1 = r87
            r29 = r3
            r3 = r53
            r38 = r61
            r5 = 2
            r12 = 3
            r2 = r15
            r13 = r3
            r46 = r59
            r3 = r9
            r19 = r0
            r12 = r63
            r0 = 0
            r48 = 46
            r4 = r46
            r81 = r78
            r5 = r35
            r41 = r19
            r6 = r10
            r54 = r7
            r53 = r77
            r7 = r29
            r83 = r8
            r27 = r33
            r82 = r38
            r26 = r42
            r0 = 1
            r8 = r20
            r84 = r9
            r24 = r62
            r33 = 43
            r9 = r31
            r85 = r10
            r10 = r17
            r1.correctLayoutedCellsOccupiedAreas(r2, r3, r4, r5, r6, r7, r8, r9, r10)
        L_0x0bfc:
            if (r45 != 0) goto L_0x0c13
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r1 = r1.size()
            int r1 = r1 - r0
            r9 = r84
            if (r9 != r1) goto L_0x0c0a
            goto L_0x0c15
        L_0x0c0a:
            r31 = r15
            r10 = r85
            r7 = 0
            r8 = 0
            r15 = 2
            goto L_0x0d26
        L_0x0c13:
            r9 = r84
        L_0x0c15:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x0d1f
            if (r12 != 0) goto L_0x0c33
            java.util.List r1 = r11.childRenderers
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0c33
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r10 = r85
            r1.applyTopTableBorder(r2, r10, r0)
            r31 = r15
            goto L_0x0c4d
        L_0x0c33:
            r10 = r85
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r2.getBBox()
            boolean r18 = r21.isEmpty()
            r19 = 0
            r20 = 1
            r31 = r15
            r15 = r1
            r17 = r10
            r15.applyBottomTableBorder(r16, r17, r18, r19, r20)
        L_0x0c4d:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r1 != 0) goto L_0x0d1b
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r1 = r1.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.kernel.geom.Rectangle r1 = r10.moveDown(r1)
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r2 = r2.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            r1.increaseHeight(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            r1.applyLeftAndRightTableBorder(r10, r0)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            float r2 = r10.getWidth()
            r11.prepareFooterOrHeaderRendererForLayout(r1, r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r2 = r1 instanceof com.itextpdf.layout.renderer.CollapsedTableBorders
            if (r2 == 0) goto L_0x0c8c
            com.itextpdf.layout.renderer.CollapsedTableBorders r1 = (com.itextpdf.layout.renderer.CollapsedTableBorders) r1
            r2 = 0
            r1.setBottomBorderCollapseWith(r2)
        L_0x0c8c:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r2 = r2.bordersHandler
            if (r12 != 0) goto L_0x0c9f
            java.util.List r3 = r11.childRenderers
            int r3 = r3.size()
            if (r3 == 0) goto L_0x0c9d
            goto L_0x0c9f
        L_0x0c9d:
            r4 = 0
            goto L_0x0ca0
        L_0x0c9f:
            r4 = 1
        L_0x0ca0:
            r1.collapseTableWithFooter(r2, r4)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.CollapsedTableBorders
            if (r1 == 0) goto L_0x0cc0
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.borders.Border[] r2 = r1.getBorders()
            r15 = 2
            r2 = r2[r15]
            com.itextpdf.layout.borders.Border[] r3 = r87.getBorders()
            r3 = r3[r15]
            com.itextpdf.layout.borders.Border r2 = com.itextpdf.layout.renderer.CollapsedTableBorders.getCollapsedBorder(r2, r3)
            r1.setBorders(r2, r15)
            goto L_0x0cc1
        L_0x0cc0:
            r15 = 2
        L_0x0cc1:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutContext r2 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r3 = new com.itextpdf.layout.layout.LayoutArea
            int r4 = r58.getPageNumber()
            r3.<init>(r4, r10)
            if (r30 != 0) goto L_0x0cd5
            if (r14 == 0) goto L_0x0cd3
            goto L_0x0cd5
        L_0x0cd3:
            r4 = 0
            goto L_0x0cd6
        L_0x0cd5:
            r4 = 1
        L_0x0cd6:
            r2.<init>((com.itextpdf.layout.layout.LayoutArea) r3, (boolean) r4)
            r1.layout(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            r8 = 0
            r1.applyLeftAndRightTableBorder(r10, r8)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getOccupiedAreaBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            float r3 = r10.getHeight()
            float r3 = r3 - r1
            float r3 = -r3
            r7 = 0
            r2.move(r7, r3)
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r2 = r2.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getTop()
            com.itextpdf.kernel.geom.Rectangle r2 = r10.setY(r2)
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            float r3 = r3.getBottom()
            float r4 = r10.getBottom()
            float r3 = r3 - r4
            r2.setHeight(r3)
            goto L_0x0d26
        L_0x0d1b:
            r7 = 0
            r8 = 0
            r15 = 2
            goto L_0x0d26
        L_0x0d1f:
            r31 = r15
            r10 = r85
            r7 = 0
            r8 = 0
            r15 = 2
        L_0x0d26:
            if (r45 != 0) goto L_0x0d33
            java.util.List r1 = r11.childRenderers
            r6 = r83
            r1.addAll(r6)
            r6.clear()
            goto L_0x0d35
        L_0x0d33:
            r6 = r83
        L_0x0d35:
            if (r45 == 0) goto L_0x0d4a
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x0d4a
            r1 = 108(0x6c, float:1.51E-43)
            java.lang.Object r1 = r11.getProperty(r1)
            com.itextpdf.layout.tagging.LayoutTaggingHelper r1 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r1
            if (r1 == 0) goto L_0x0d4a
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            r1.markArtifactHint((com.itextpdf.layout.IPropertyContainer) r2)
        L_0x0d4a:
            if (r45 == 0) goto L_0x12ea
            r5 = r37
            if (r5 == 0) goto L_0x0d56
            r4 = r39
            r4.endMarginsCollapse(r10)
            goto L_0x0d58
        L_0x0d56:
            r4 = r39
        L_0x0d58:
            com.itextpdf.layout.renderer.TableRenderer[] r33 = r11.split(r9, r12, r13)
            com.itextpdf.layout.renderer.TableRenderer$OverflowRowsWrapper r1 = new com.itextpdf.layout.renderer.TableRenderer$OverflowRowsWrapper
            r2 = r33[r0]
            r1.<init>(r2)
            r3 = r1
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 != 0) goto L_0x0d6c
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            if (r2 == 0) goto L_0x0d8c
        L_0x0d6c:
            if (r1 != 0) goto L_0x0d74
            boolean r1 = r21.isEmpty()
            if (r1 == 0) goto L_0x0d7b
        L_0x0d74:
            r1 = r33[r0]
            r2 = 13
            r1.deleteOwnProperty(r2)
        L_0x0d7b:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 != 0) goto L_0x0d85
            boolean r1 = r21.isEmpty()
            if (r1 == 0) goto L_0x0d8c
        L_0x0d85:
            r1 = r33[r0]
            r2 = 10
            r1.deleteOwnProperty(r2)
        L_0x0d8c:
            if (r45 == 0) goto L_0x0f0e
            r2 = r82
            int r1 = r2.length
            int[] r1 = new int[r1]
            int r7 = r2.length
            boolean[] r7 = new boolean[r7]
            r16 = 0
            r15 = r16
        L_0x0d9a:
            int r8 = r2.length
            if (r15 >= r8) goto L_0x0e7b
            r8 = r31[r15]
            if (r8 == 0) goto L_0x0e39
            r8 = r31[r15]
            com.itextpdf.layout.renderer.IRenderer r8 = r8.getSplitRenderer()
            com.itextpdf.layout.renderer.CellRenderer r8 = (com.itextpdf.layout.renderer.CellRenderer) r8
            if (r8 == 0) goto L_0x0db7
            com.itextpdf.layout.IPropertyContainer r16 = r8.getModelElement()
            com.itextpdf.layout.element.Cell r16 = (com.itextpdf.layout.element.Cell) r16
            int r16 = r16.getRowspan()
            r1[r15] = r16
        L_0x0db7:
            r16 = r31[r15]
            int r0 = r16.getStatus()
            r19 = r4
            r4 = 3
            if (r0 == r4) goto L_0x0dcb
            if (r12 != 0) goto L_0x0dc6
            if (r13 == 0) goto L_0x0dcb
        L_0x0dc6:
            java.util.List r0 = r11.childRenderers
            r0.add(r8)
        L_0x0dcb:
            r0 = r2[r15]
            com.itextpdf.layout.layout.LayoutArea r0 = r0.getOccupiedArea()
            if (r12 != 0) goto L_0x0dfa
            if (r13 != 0) goto L_0x0dfa
            r4 = r31[r15]
            int r4 = r4.getStatus()
            r20 = r5
            r5 = 3
            if (r4 != r5) goto L_0x0de3
            r83 = r6
            goto L_0x0dfe
        L_0x0de3:
            r4 = r46[r15]
            int r4 = r4 - r9
            r5 = r2[r15]
            r83 = r6
            r16 = 1
            r6 = r33[r16]
            com.itextpdf.layout.renderer.IRenderer r5 = r5.setParent(r6)
            com.itextpdf.layout.renderer.CellRenderer r5 = (com.itextpdf.layout.renderer.CellRenderer) r5
            r3.setCell(r4, r15, r5)
            r16 = r8
            goto L_0x0e2f
        L_0x0dfa:
            r20 = r5
            r83 = r6
        L_0x0dfe:
            r4 = r31[r15]
            com.itextpdf.layout.renderer.IRenderer r4 = r4.getOverflowRenderer()
            com.itextpdf.layout.renderer.CellRenderer r4 = (com.itextpdf.layout.renderer.CellRenderer) r4
            r5 = r2[r15]
            r6 = 0
            r2[r15] = r6
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r11.rows
            r16 = r8
            r8 = r46[r15]
            java.lang.Object r6 = r6.get(r8)
            com.itextpdf.layout.renderer.CellRenderer[] r6 = (com.itextpdf.layout.renderer.CellRenderer[]) r6
            r6[r15] = r5
            r6 = 0
            r8 = 0
            r3.setCell(r8, r15, r6)
            r6 = r46[r15]
            int r6 = r6 - r9
            r17 = 1
            r8 = r33[r17]
            com.itextpdf.layout.renderer.IRenderer r8 = r4.setParent(r8)
            com.itextpdf.layout.renderer.CellRenderer r8 = (com.itextpdf.layout.renderer.CellRenderer) r8
            r3.setCell(r6, r15, r8)
        L_0x0e2f:
            r4 = r46[r15]
            int r4 = r4 - r9
            com.itextpdf.layout.renderer.CellRenderer r4 = r3.getCell(r4, r15)
            r4.occupiedArea = r0
            goto L_0x0e6e
        L_0x0e39:
            r19 = r4
            r20 = r5
            r83 = r6
            r0 = r2[r15]
            if (r0 == 0) goto L_0x0e6e
            if (r12 == 0) goto L_0x0e53
            r0 = r2[r15]
            com.itextpdf.layout.IPropertyContainer r0 = r0.getModelElement()
            com.itextpdf.layout.element.Cell r0 = (com.itextpdf.layout.element.Cell) r0
            int r0 = r0.getRowspan()
            r1[r15] = r0
        L_0x0e53:
            r0 = r2[r15]
            com.itextpdf.layout.IPropertyContainer r0 = r0.getModelElement()
            com.itextpdf.layout.element.Cell r0 = (com.itextpdf.layout.element.Cell) r0
            int r0 = r0.getRowspan()
            r4 = 1
            if (r4 == r0) goto L_0x0e64
            r4 = 1
            goto L_0x0e65
        L_0x0e64:
            r4 = 0
        L_0x0e65:
            r0 = r4
            if (r12 != 0) goto L_0x0e6a
            if (r0 == 0) goto L_0x0e6f
        L_0x0e6a:
            r4 = 1
            r7[r15] = r4
            goto L_0x0e6f
        L_0x0e6e:
        L_0x0e6f:
            int r15 = r15 + 1
            r4 = r19
            r5 = r20
            r6 = r83
            r0 = 1
            r8 = 0
            goto L_0x0d9a
        L_0x0e7b:
            r19 = r4
            r20 = r5
            r83 = r6
            r0 = 2147483647(0x7fffffff, float:NaN)
            r4 = 0
        L_0x0e85:
            int r5 = r1.length
            if (r4 >= r5) goto L_0x0e95
            r5 = r1[r4]
            if (r5 == 0) goto L_0x0e92
            r5 = r1[r4]
            int r0 = java.lang.Math.min(r0, r5)
        L_0x0e92:
            int r4 = r4 + 1
            goto L_0x0e85
        L_0x0e95:
            r4 = 0
            r15 = r4
        L_0x0e97:
            r8 = r49
            if (r15 >= r8) goto L_0x0ef6
            boolean r4 = r7[r15]
            if (r4 == 0) goto L_0x0eca
            r16 = r1
            r1 = r87
            r39 = r2
            r2 = r15
            r5 = r3
            r3 = r9
            r6 = r19
            r4 = r0
            r17 = r0
            r42 = r5
            r0 = r20
            r5 = r39
            r49 = r13
            r52 = r83
            r13 = r6
            r6 = r42
            r18 = r7
            r56 = r13
            r13 = 0
            r7 = r46
            r57 = r8
            r13 = 0
            r8 = r33
            r1.enlargeCell(r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0edf
        L_0x0eca:
            r17 = r0
            r16 = r1
            r39 = r2
            r42 = r3
            r18 = r7
            r57 = r8
            r49 = r13
            r56 = r19
            r0 = r20
            r52 = r83
            r13 = 0
        L_0x0edf:
            int r15 = r15 + 1
            r20 = r0
            r1 = r16
            r0 = r17
            r7 = r18
            r2 = r39
            r3 = r42
            r13 = r49
            r83 = r52
            r19 = r56
            r49 = r57
            goto L_0x0e97
        L_0x0ef6:
            r17 = r0
            r16 = r1
            r39 = r2
            r42 = r3
            r18 = r7
            r57 = r8
            r49 = r13
            r56 = r19
            r0 = r20
            r52 = r83
            r13 = 0
            r50 = r15
            goto L_0x0f1c
        L_0x0f0e:
            r42 = r3
            r56 = r4
            r0 = r5
            r52 = r6
            r57 = r49
            r39 = r82
            r49 = r13
            r13 = 0
        L_0x0f1c:
            r8 = r55
            r7 = r81
            r1 = 1
            r11.applySpacing(r10, r7, r8, r1)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r11.applySingleSpacing(r2, r7, r1, r1)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x0f38
            com.itextpdf.kernel.geom.Rectangle r1 = r10.moveUp(r8)
            r1.decreaseHeight(r8)
        L_0x0f38:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 != 0) goto L_0x0f42
            boolean r1 = r21.isEmpty()
            if (r1 != 0) goto L_0x0f45
        L_0x0f42:
            r10.decreaseHeight(r8)
        L_0x0f45:
            if (r9 != 0) goto L_0x0f59
            if (r12 != 0) goto L_0x0f59
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 != 0) goto L_0x0f59
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r6 = r8 / r32
            r1.moveUp(r6)
            goto L_0x0f63
        L_0x0f59:
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r2 = 1
            r11.applySingleSpacing(r1, r8, r13, r2)
        L_0x0f63:
            if (r23 != 0) goto L_0x0f76
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x0f76
            r1 = r33[r13]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r1.rows
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0f76
            r10.increaseHeight(r8)
        L_0x0f76:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 != 0) goto L_0x0fc1
            java.util.List r1 = r11.childRenderers
            int r1 = r1.size()
            if (r1 == 0) goto L_0x0f8f
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r1.applyBottomTableBorder(r2, r10, r13)
            r1 = 2
            goto L_0x0fc2
        L_0x0f8f:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r3 = 1
            r1.applyTopTableBorder(r2, r10, r3)
            if (r23 != 0) goto L_0x0fbf
            if (r25 != 0) goto L_0x0fbf
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r1.getBBox()
            java.util.List r1 = r11.childRenderers
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0fb2
            r18 = 1
            goto L_0x0fb4
        L_0x0fb2:
            r18 = 0
        L_0x0fb4:
            r19 = 1
            r20 = 0
            r1 = 2
            r17 = r10
            r15.applyTopTableBorder(r16, r17, r18, r19, r20)
            goto L_0x0fc2
        L_0x0fbf:
            r1 = 2
            goto L_0x0fc2
        L_0x0fc1:
            r1 = 2
        L_0x0fc2:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 86
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r3)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0fde
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 87
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r3)
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0fec
        L_0x0fde:
            r2 = 1
            r3 = r33[r2]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r2 = r3.rows
            java.lang.Object r2 = r2.get(r13)
            com.itextpdf.layout.renderer.CellRenderer[] r2 = (com.itextpdf.layout.renderer.CellRenderer[]) r2
            r11.extendLastRow(r2, r10)
        L_0x0fec:
            java.util.List<java.lang.Float> r2 = r11.heights
            int r2 = r2.size()
            if (r2 == 0) goto L_0x0ff6
            r2 = r8
            goto L_0x0ff7
        L_0x0ff6:
            r2 = 0
        L_0x0ff7:
            r11.adjustFooterAndFixOccupiedArea(r10, r2)
            java.util.List<java.lang.Float> r2 = r11.heights
            int r2 = r2.size()
            if (r2 == 0) goto L_0x1004
            r2 = r8
            goto L_0x1005
        L_0x1004:
            r2 = 0
        L_0x1005:
            r11.adjustCaptionAndFixOccupiedArea(r10, r2)
            java.util.Set r2 = r24.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x1010:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x10f5
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            r4 = 1
            r5 = r33[r4]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r4 = r5.rows
            java.lang.Object r5 = r3.getValue()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r6 = r33[r13]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r6.rows
            int r6 = r6.size()
            int r5 = r5 - r6
            java.lang.Object r4 = r4.get(r5)
            com.itextpdf.layout.renderer.CellRenderer[] r4 = (com.itextpdf.layout.renderer.CellRenderer[]) r4
            java.lang.Object r5 = r3.getKey()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r4 = r4[r5]
            if (r4 != 0) goto L_0x10ee
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r4 = r11.rows
            java.lang.Object r4 = r4.get(r9)
            com.itextpdf.layout.renderer.CellRenderer[] r4 = (com.itextpdf.layout.renderer.CellRenderer[]) r4
            java.lang.Object r5 = r3.getKey()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r4 = r4[r5]
            r5 = 1
            r6 = r33[r5]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r5 = r6.rows
            r6 = r33[r13]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r6.rows
            int r6 = r6.size()
            int r6 = r9 - r6
            java.lang.Object r5 = r5.get(r6)
            com.itextpdf.layout.renderer.CellRenderer[] r5 = (com.itextpdf.layout.renderer.CellRenderer[]) r5
            java.lang.Object r6 = r3.getKey()
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r5 = r5[r6]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r11.rows
            java.lang.Object r15 = r3.getValue()
            java.lang.Integer r15 = (java.lang.Integer) r15
            int r15 = r15.intValue()
            java.lang.Object r6 = r6.get(r15)
            com.itextpdf.layout.renderer.CellRenderer[] r6 = (com.itextpdf.layout.renderer.CellRenderer[]) r6
            java.lang.Object r15 = r3.getKey()
            java.lang.Integer r15 = (java.lang.Integer) r15
            int r15 = r15.intValue()
            r6[r15] = r4
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r11.rows
            java.lang.Object r6 = r6.get(r9)
            com.itextpdf.layout.renderer.CellRenderer[] r6 = (com.itextpdf.layout.renderer.CellRenderer[]) r6
            java.lang.Object r15 = r3.getKey()
            java.lang.Integer r15 = (java.lang.Integer) r15
            int r15 = r15.intValue()
            r16 = 0
            r6[r15] = r16
            java.lang.Object r6 = r3.getValue()
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r15 = r33[r13]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r15 = r15.rows
            int r15 = r15.size()
            int r6 = r6 - r15
            java.lang.Object r15 = r3.getKey()
            java.lang.Integer r15 = (java.lang.Integer) r15
            int r15 = r15.intValue()
            r16 = r4
            r4 = r42
            r4.setCell(r6, r15, r5)
            r6 = r33[r13]
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r6 = r6.rows
            int r6 = r6.size()
            int r6 = r9 - r6
            java.lang.Object r15 = r3.getKey()
            java.lang.Integer r15 = (java.lang.Integer) r15
            int r15 = r15.intValue()
            r1 = 0
            r4.setCell(r6, r15, r1)
            goto L_0x10f0
        L_0x10ee:
            r4 = r42
        L_0x10f0:
            r42 = r4
            r1 = 2
            goto L_0x1010
        L_0x10f5:
            r4 = r42
            boolean r1 = r87.isKeepTogether()
            if (r1 == 0) goto L_0x1129
            int r1 = r22.size()
            if (r1 != 0) goto L_0x1129
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r5 = 26
            java.lang.Boolean r2 = r11.getPropertyAsBoolean(r5)
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x1126
            com.itextpdf.layout.layout.LayoutResult r13 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r5 = 0
            if (r47 != 0) goto L_0x111a
            r6 = r11
            goto L_0x111c
        L_0x111a:
            r6 = r47
        L_0x111c:
            r1 = r13
            r42 = r4
            r4 = r5
            r5 = r87
            r1.<init>(r2, r3, r4, r5, r6)
            return r13
        L_0x1126:
            r42 = r4
            goto L_0x112d
        L_0x1129:
            r42 = r4
            r5 = 26
        L_0x112d:
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            if (r2 != 0) goto L_0x113d
            r2 = 0
            goto L_0x1149
        L_0x113d:
            com.itextpdf.layout.layout.LayoutArea r2 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
        L_0x1149:
            float r1 = r1 - r2
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.headerRenderer
            if (r2 != 0) goto L_0x1150
            r2 = 0
            goto L_0x1165
        L_0x1150:
            com.itextpdf.layout.layout.LayoutArea r2 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.headerRenderer
            com.itextpdf.layout.renderer.TableBorders r3 = r3.bordersHandler
            float r3 = r3.getMaxBottomWidth()
            float r2 = r2 - r3
        L_0x1165:
            float r1 = r1 - r2
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x1171
            if (r23 != 0) goto L_0x116f
            if (r25 == 0) goto L_0x1171
        L_0x116f:
            r2 = 3
            goto L_0x1172
        L_0x1171:
            r2 = 2
        L_0x1172:
            r1 = r2
            r2 = 3
            if (r1 != r2) goto L_0x1182
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r5)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x1184
        L_0x1182:
            if (r30 == 0) goto L_0x12a1
        L_0x1184:
            if (r30 == 0) goto L_0x1270
            org.slf4j.Logger r2 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r28)
            java.lang.String r3 = "Element content was clipped because some height properties are set."
            r2.warn(r3)
            r3 = 3
            if (r1 != r3) goto L_0x11ce
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r3.getBBox()
            java.util.List r3 = r11.childRenderers
            int r3 = r3.size()
            if (r3 != 0) goto L_0x11a5
            r18 = 1
            goto L_0x11a7
        L_0x11a5:
            r18 = 0
        L_0x11a7:
            r19 = 1
            r20 = 0
            r17 = r10
            r15.applyTopTableBorder(r16, r17, r18, r19, r20)
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r3.getBBox()
            java.util.List r3 = r11.childRenderers
            int r3 = r3.size()
            if (r3 != 0) goto L_0x11c3
            r18 = 1
            goto L_0x11c5
        L_0x11c3:
            r18 = 0
        L_0x11c5:
            r19 = 1
            r20 = 0
            r17 = r10
            r15.applyBottomTableBorder(r16, r17, r18, r19, r20)
        L_0x11ce:
            if (r35 == 0) goto L_0x1270
            float r3 = r35.floatValue()
            com.itextpdf.layout.layout.LayoutArea r4 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            float r4 = r4.getHeight()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x1270
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            float r3 = r3.getBottom()
            float r4 = r35.floatValue()
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            float r5 = r5.getHeight()
            float r4 = r4 - r5
            float r3 = r3 - r4
            float r4 = r10.getBottom()
            float r3 = java.lang.Math.max(r3, r4)
            java.util.List<java.lang.Float> r4 = r11.heights
            int r4 = r4.size()
            if (r4 != 0) goto L_0x1227
            java.util.List<java.lang.Float> r4 = r11.heights
            float r5 = r35.floatValue()
            com.itextpdf.layout.layout.LayoutArea r6 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            float r6 = r6.getHeight()
            float r6 = r6 / r32
            float r5 = r5 - r6
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            r4.add(r5)
            goto L_0x1258
        L_0x1227:
            java.util.List<java.lang.Float> r4 = r11.heights
            int r5 = r4.size()
            r6 = 1
            int r5 = r5 - r6
            java.util.List<java.lang.Float> r15 = r11.heights
            int r16 = r15.size()
            int r13 = r16 + -1
            java.lang.Object r6 = r15.get(r13)
            java.lang.Float r6 = (java.lang.Float) r6
            float r6 = r6.floatValue()
            float r13 = r35.floatValue()
            float r6 = r6 + r13
            com.itextpdf.layout.layout.LayoutArea r13 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r13 = r13.getBBox()
            float r13 = r13.getHeight()
            float r6 = r6 - r13
            java.lang.Float r6 = java.lang.Float.valueOf(r6)
            r4.set(r5, r6)
        L_0x1258:
            com.itextpdf.layout.layout.LayoutArea r4 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            float r5 = r5.getBottom()
            float r5 = r5 - r3
            com.itextpdf.kernel.geom.Rectangle r4 = r4.increaseHeight(r5)
            r4.setY(r3)
        L_0x1270:
            r2 = 0
            r11.applyFixedXOrYPosition(r2, r10)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r3 = 1
            r11.applyPaddings(r2, r3)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r11.applyMargins(r2, r3)
            com.itextpdf.layout.layout.LayoutArea r2 = r88.getArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r4 = r36
            r6 = r40
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r11, r4, r2, r6, r0)
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r13 = 0
            r13 = r33[r13]
            r15 = 0
            r5.<init>(r3, r2, r13, r15)
            return r5
        L_0x12a1:
            r4 = r36
            r6 = r40
            r3 = 1
            r2 = r33[r13]
            r5 = r33[r3]
            r11.updateHeightsOnSplit(r13, r2, r5)
            r11.applyFixedXOrYPosition(r13, r10)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r11.applyPaddings(r2, r3)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r11.applyMargins(r2, r3)
            r2 = 0
            r3 = 3
            if (r1 == r3) goto L_0x12d2
            com.itextpdf.layout.layout.LayoutArea r3 = r88.getArea()
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r11, r4, r3, r6, r0)
        L_0x12d2:
            com.itextpdf.layout.layout.LayoutResult r3 = new com.itextpdf.layout.layout.LayoutResult
            r5 = 0
            r18 = r33[r5]
            r5 = 1
            r19 = r33[r5]
            if (r47 != 0) goto L_0x12df
            r20 = r11
            goto L_0x12e1
        L_0x12df:
            r20 = r47
        L_0x12e1:
            r15 = r3
            r16 = r1
            r17 = r2
            r15.<init>(r16, r17, r18, r19, r20)
            return r3
        L_0x12ea:
            r52 = r6
            r4 = r36
            r0 = r37
            r56 = r39
            r6 = r40
            r57 = r49
            r8 = r55
            r7 = r81
            r39 = r82
            r1 = 10
            r2 = 13
            r5 = 26
            r49 = r13
            int r3 = r9 + 1
            r1 = r3
            r5 = r7
            r12 = r8
            r9 = r24
            r8 = r26
            r33 = r27
            r2 = r29
            r15 = r31
            r3 = r46
            r39 = r56
            r0 = r57
            r13 = r58
            r4 = 46
            r6 = 13
            r7 = 10
            goto L_0x04c3
        L_0x1323:
            r57 = r0
            r29 = r2
            r46 = r3
            r7 = r5
            r26 = r8
            r24 = r9
            r8 = r12
            r58 = r13
            r31 = r15
            r27 = r33
            r4 = r36
            r0 = r37
            r56 = r39
            r6 = r40
            r3 = 86
            r9 = r1
            boolean r1 = r21.isComplete()
            if (r1 == 0) goto L_0x1380
            boolean r1 = r21.isEmpty()
            if (r1 != 0) goto L_0x1380
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r2 = r1.size()
            r5 = 1
            int r2 = r2 - r5
            java.lang.Object r1 = r1.get(r2)
            com.itextpdf.layout.renderer.CellRenderer[] r1 = (com.itextpdf.layout.renderer.CellRenderer[]) r1
            int r2 = r1.length
            int r2 = r2 - r5
        L_0x135c:
            if (r2 < 0) goto L_0x1365
            r5 = r1[r2]
            if (r5 != 0) goto L_0x1365
            int r2 = r2 + -1
            goto L_0x135c
        L_0x1365:
            if (r2 < 0) goto L_0x1377
            int r5 = r1.length
            r12 = r1[r2]
            r13 = 16
            java.lang.Integer r12 = r12.getPropertyAsInteger(r13)
            int r12 = r12.intValue()
            int r12 = r12 + r2
            if (r5 == r12) goto L_0x1380
        L_0x1377:
            org.slf4j.Logger r5 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r28)
            java.lang.String r12 = "Last row is not completed. Table bottom border may collapse as you do not expect it"
            r5.warn(r12)
        L_0x1380:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            boolean r1 = r1 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r1 != 0) goto L_0x1432
            boolean r1 = r21.isComplete()
            if (r1 == 0) goto L_0x1432
            int r1 = r22.size()
            if (r1 != 0) goto L_0x139c
            boolean r1 = r21.isEmpty()
            if (r1 == 0) goto L_0x1399
            goto L_0x139c
        L_0x1399:
            r12 = 0
            goto L_0x1433
        L_0x139c:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x1432
            com.itextpdf.layout.layout.LayoutArea r1 = r1.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            com.itextpdf.kernel.geom.Rectangle r1 = r10.moveDown(r1)
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutArea r2 = r2.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            r1.increaseHeight(r2)
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            r2 = 1
            r1.applyLeftAndRightTableBorder(r10, r2)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            float r2 = r10.getWidth()
            r11.prepareFooterOrHeaderRendererForLayout(r1, r2)
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r1 = r1.size()
            if (r1 != 0) goto L_0x13e9
            if (r23 != 0) goto L_0x13d8
            r5 = 1
            goto L_0x13ea
        L_0x13d8:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 == 0) goto L_0x13e7
            com.itextpdf.layout.renderer.TableBorders r1 = r1.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r2 = r2.bordersHandler
            r5 = 1
            r1.collapseTableWithFooter(r2, r5)
            goto L_0x13f3
        L_0x13e7:
            r5 = 1
            goto L_0x13f3
        L_0x13e9:
            r5 = 1
        L_0x13ea:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            com.itextpdf.layout.renderer.TableBorders r2 = r2.bordersHandler
            r1.collapseTableWithFooter(r2, r5)
        L_0x13f3:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.layout.layout.LayoutContext r2 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r5 = new com.itextpdf.layout.layout.LayoutArea
            int r12 = r58.getPageNumber()
            r5.<init>(r12, r10)
            if (r30 != 0) goto L_0x1407
            if (r14 == 0) goto L_0x1405
            goto L_0x1407
        L_0x1405:
            r12 = 0
            goto L_0x1408
        L_0x1407:
            r12 = 1
        L_0x1408:
            r2.<init>((com.itextpdf.layout.layout.LayoutArea) r5, (boolean) r12)
            r1.layout(r2)     // Catch:{ all -> 0x16ec }
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            r2 = 0
            r1.applyLeftAndRightTableBorder(r10, r2)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getOccupiedAreaBBox()
            float r1 = r1.getHeight()
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            float r5 = r10.getHeight()
            float r5 = r5 - r1
            float r5 = -r5
            r12 = 0
            r2.move(r12, r5)
            com.itextpdf.kernel.geom.Rectangle r2 = r10.moveUp(r1)
            r2.decreaseHeight(r1)
            goto L_0x1433
        L_0x1432:
            r12 = 0
        L_0x1433:
            r1 = 1
            r11.applySpacing(r10, r7, r8, r1)
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r11.applySingleSpacing(r2, r7, r1, r1)
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.footerRenderer
            if (r1 == 0) goto L_0x144b
            com.itextpdf.kernel.geom.Rectangle r1 = r10.moveUp(r8)
            r1.decreaseHeight(r8)
        L_0x144b:
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 != 0) goto L_0x1455
            boolean r1 = r21.isEmpty()
            if (r1 != 0) goto L_0x1458
        L_0x1455:
            r10.decreaseHeight(r8)
        L_0x1458:
            boolean r1 = r21.isEmpty()
            if (r1 == 0) goto L_0x146e
            com.itextpdf.layout.renderer.TableRenderer r1 = r11.headerRenderer
            if (r1 != 0) goto L_0x146e
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r2 = r8 / r32
            r1.moveUp(r2)
            goto L_0x1483
        L_0x146e:
            if (r23 != 0) goto L_0x1478
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r1 = r11.rows
            int r1 = r1.size()
            if (r1 == 0) goto L_0x1483
        L_0x1478:
            com.itextpdf.layout.layout.LayoutArea r1 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r2 = 0
            r5 = 1
            r11.applySingleSpacing(r1, r8, r2, r5)
        L_0x1483:
            com.itextpdf.layout.renderer.TableBorders r1 = r11.bordersHandler
            float r1 = r1.getMaxBottomWidth()
            boolean r2 = r21.isComplete()
            if (r2 == 0) goto L_0x1522
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            if (r2 != 0) goto L_0x14ef
            java.util.List r2 = r11.childRenderers
            int r2 = r2.size()
            if (r2 == 0) goto L_0x14a9
            com.itextpdf.layout.renderer.TableBorders r2 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r13 = 0
            r2.applyBottomTableBorder(r5, r10, r13)
            goto L_0x157c
        L_0x14a9:
            int r2 = r22.size()
            if (r2 == 0) goto L_0x14cf
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r2.getBBox()
            java.util.List r2 = r11.childRenderers
            int r2 = r2.size()
            if (r2 != 0) goto L_0x14c2
            r18 = 1
            goto L_0x14c4
        L_0x14c2:
            r18 = 0
        L_0x14c4:
            r19 = 1
            r20 = 0
            r17 = r10
            r15.applyTopTableBorder(r16, r17, r18, r19, r20)
            goto L_0x157c
        L_0x14cf:
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r2.getBBox()
            java.util.List r2 = r11.childRenderers
            int r2 = r2.size()
            if (r2 != 0) goto L_0x14e2
            r18 = 1
            goto L_0x14e4
        L_0x14e2:
            r18 = 0
        L_0x14e4:
            r19 = 1
            r20 = 0
            r17 = r10
            r15.applyBottomTableBorder(r16, r17, r18, r19, r20)
            goto L_0x157c
        L_0x14ef:
            boolean r2 = r21.isEmpty()
            if (r2 == 0) goto L_0x157c
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.headerRenderer
            if (r2 == 0) goto L_0x157c
            com.itextpdf.layout.renderer.TableBorders r2 = r2.bordersHandler
            float r2 = r2.getMaxBottomWidth()
            com.itextpdf.layout.renderer.TableRenderer r5 = r11.headerRenderer
            com.itextpdf.layout.renderer.TableBorders r15 = r5.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r5 = r5.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r5.getBBox()
            r18 = 1
            r19 = 1
            r20 = 1
            r17 = r10
            r15.applyBottomTableBorder(r16, r17, r18, r19, r20)
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            com.itextpdf.kernel.geom.Rectangle r5 = r5.moveUp(r2)
            r5.decreaseHeight(r2)
            goto L_0x157c
        L_0x1522:
            java.util.List<java.lang.Float> r2 = r11.heights
            int r2 = r2.size()
            if (r2 == 0) goto L_0x154e
            java.util.List<java.lang.Float> r2 = r11.heights
            int r5 = r2.size()
            r13 = 1
            int r5 = r5 - r13
            java.util.List<java.lang.Float> r15 = r11.heights
            int r16 = r15.size()
            int r12 = r16 + -1
            java.lang.Object r12 = r15.get(r12)
            java.lang.Float r12 = (java.lang.Float) r12
            float r12 = r12.floatValue()
            float r13 = r1 / r32
            float r12 = r12 - r13
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            r2.set(r5, r12)
        L_0x154e:
            com.itextpdf.layout.renderer.TableRenderer r2 = r11.footerRenderer
            if (r2 != 0) goto L_0x1579
            java.util.List r2 = r11.childRenderers
            int r2 = r2.size()
            if (r2 == 0) goto L_0x157c
            com.itextpdf.layout.renderer.TableBorders r15 = r11.bordersHandler
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r16 = r2.getBBox()
            java.util.List r2 = r11.childRenderers
            int r2 = r2.size()
            if (r2 != 0) goto L_0x156d
            r18 = 1
            goto L_0x156f
        L_0x156d:
            r18 = 0
        L_0x156f:
            r19 = 0
            r20 = 1
            r17 = r10
            r15.applyBottomTableBorder(r16, r17, r18, r19, r20)
            goto L_0x157c
        L_0x1579:
            r10.increaseHeight(r1)
        L_0x157c:
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r2 = r11.rows
            int r2 = r2.size()
            if (r2 == 0) goto L_0x15a3
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            java.lang.Boolean r3 = r11.getPropertyAsBoolean(r3)
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x1647
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r2 = r11.rows
            int r3 = r2.size()
            r5 = 1
            int r3 = r3 - r5
            java.lang.Object r2 = r2.get(r3)
            com.itextpdf.layout.renderer.CellRenderer[] r2 = (com.itextpdf.layout.renderer.CellRenderer[]) r2
            r11.extendLastRow(r2, r10)
            goto L_0x1647
        L_0x15a3:
            if (r35 == 0) goto L_0x1647
            float r2 = r35.floatValue()
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            float r3 = r3.getHeight()
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x1647
            com.itextpdf.layout.layout.LayoutArea r2 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getBottom()
            float r3 = r35.floatValue()
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            float r5 = r5.getHeight()
            float r3 = r3 - r5
            float r2 = r2 - r3
            float r3 = r10.getBottom()
            float r2 = java.lang.Math.max(r2, r3)
            java.util.List<java.lang.Float> r3 = r11.heights
            int r3 = r3.size()
            if (r3 == 0) goto L_0x160e
            java.util.List<java.lang.Float> r3 = r11.heights
            int r5 = r3.size()
            r12 = 1
            int r5 = r5 - r12
            java.util.List<java.lang.Float> r13 = r11.heights
            int r15 = r13.size()
            int r15 = r15 - r12
            java.lang.Object r12 = r13.get(r15)
            java.lang.Float r12 = (java.lang.Float) r12
            float r12 = r12.floatValue()
            com.itextpdf.layout.layout.LayoutArea r13 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r13 = r13.getBBox()
            float r13 = r13.getBottom()
            float r12 = r12 + r13
            float r12 = r12 - r2
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            r3.set(r5, r12)
            goto L_0x162f
        L_0x160e:
            java.util.List<java.lang.Float> r3 = r11.heights
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            float r5 = r5.getBottom()
            float r5 = r5 - r2
            com.itextpdf.layout.layout.LayoutArea r12 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r12 = r12.getBBox()
            float r12 = r12.getHeight()
            float r12 = r12 / r32
            float r5 = r5 + r12
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            r3.add(r5)
        L_0x162f:
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            com.itextpdf.layout.layout.LayoutArea r5 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            float r5 = r5.getBottom()
            float r5 = r5 - r2
            com.itextpdf.kernel.geom.Rectangle r3 = r3.increaseHeight(r5)
            r3.setY(r2)
        L_0x1647:
            r2 = 0
            r11.applyFixedXOrYPosition(r2, r10)
            if (r0 == 0) goto L_0x1653
            r2 = r56
            r2.endMarginsCollapse(r10)
            goto L_0x1655
        L_0x1653:
            r2 = r56
        L_0x1655:
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r5 = 1
            r11.applyPaddings(r3, r5)
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r11.applyMargins(r3, r5)
            boolean r3 = r21.isComplete()
            if (r3 != 0) goto L_0x168b
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.footerRenderer
            if (r3 == 0) goto L_0x168b
            r3 = 108(0x6c, float:1.51E-43)
            java.lang.Object r3 = r11.getProperty(r3)
            com.itextpdf.layout.tagging.LayoutTaggingHelper r3 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r3
            if (r3 == 0) goto L_0x1681
            com.itextpdf.layout.renderer.TableRenderer r5 = r11.footerRenderer
            r3.markArtifactHint((com.itextpdf.layout.IPropertyContainer) r5)
        L_0x1681:
            r5 = 0
            r11.footerRenderer = r5
            com.itextpdf.layout.renderer.TableBorders r5 = r11.bordersHandler
            com.itextpdf.layout.borders.Border[] r12 = r5.tableBoundingBorders
            r5.skipFooter(r12)
        L_0x168b:
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.headerRenderer
            if (r3 != 0) goto L_0x1698
            boolean r3 = r21.isEmpty()
            if (r3 != 0) goto L_0x1696
            goto L_0x1698
        L_0x1696:
            r3 = 0
            goto L_0x1699
        L_0x1698:
            r3 = r8
        L_0x1699:
            r11.adjustFooterAndFixOccupiedArea(r10, r3)
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.headerRenderer
            if (r3 != 0) goto L_0x16a9
            boolean r3 = r21.isEmpty()
            if (r3 != 0) goto L_0x16a7
            goto L_0x16a9
        L_0x16a7:
            r3 = 0
            goto L_0x16aa
        L_0x16a9:
            r3 = r8
        L_0x16aa:
            r11.adjustCaptionAndFixOccupiedArea(r10, r3)
            com.itextpdf.layout.renderer.FloatingHelper.removeFloatsAboveRendererBottom(r4, r11)
            if (r23 != 0) goto L_0x16cf
            if (r25 != 0) goto L_0x16cf
            java.util.List<com.itextpdf.layout.renderer.CellRenderer[]> r3 = r11.rows
            int r3 = r3.size()
            if (r3 != 0) goto L_0x16c6
            com.itextpdf.layout.renderer.TableRenderer r3 = r11.footerRenderer
            if (r3 == 0) goto L_0x16cf
            boolean r3 = r21.isComplete()
            if (r3 == 0) goto L_0x16cf
        L_0x16c6:
            com.itextpdf.layout.layout.LayoutArea r3 = r11.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r3.decreaseHeight(r8)
        L_0x16cf:
            com.itextpdf.layout.layout.LayoutArea r3 = r88.getArea()
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            com.itextpdf.layout.layout.LayoutArea r3 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r11, r4, r3, r6, r0)
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r16 = 1
            r18 = 0
            r19 = 0
            r20 = 0
            r15 = r5
            r17 = r3
            r15.<init>(r16, r17, r18, r19, r20)
            return r5
        L_0x16ec:
            r0 = move-exception
            r1 = r0
            goto L_0x16f0
        L_0x16ef:
            throw r1
        L_0x16f0:
            goto L_0x16ef
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TableRenderer.layout(com.itextpdf.layout.layout.LayoutContext):com.itextpdf.layout.layout.LayoutResult");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.itextpdf.layout.tagging.LayoutTaggingHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(com.itextpdf.layout.renderer.DrawContext r14) {
        /*
            r13 = this;
            boolean r0 = r14.isTaggingEnabled()
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x002a
            r3 = 108(0x6c, float:1.51E-43)
            java.lang.Object r3 = r13.getProperty(r3)
            r1 = r3
            com.itextpdf.layout.tagging.LayoutTaggingHelper r1 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r1
            if (r1 != 0) goto L_0x0015
            r0 = 0
            goto L_0x002a
        L_0x0015:
            com.itextpdf.kernel.pdf.tagutils.TagTreePointer r3 = r1.useAutoTaggingPointerAndRememberItsPosition(r13)
            boolean r4 = r1.createTag((com.itextpdf.layout.renderer.IRenderer) r13, (com.itextpdf.kernel.pdf.tagutils.TagTreePointer) r3)
            if (r4 == 0) goto L_0x002a
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r4 = r3.getProperties()
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r5 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getLayoutAttributes(r13, r3)
            r4.addAttributes(r2, r5)
        L_0x002a:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r3 = r14.getCanvas()
            r13.beginTransformationIfApplied(r3)
            r13.applyDestinationsAndAnnotation(r14)
            boolean r3 = r13.isRelativePosition()
            if (r3 == 0) goto L_0x003d
            r13.applyRelativePositioningTranslation(r2)
        L_0x003d:
            r13.beginElementOpacityApplying(r14)
            com.itextpdf.layout.renderer.DivRenderer r2 = r13.captionRenderer
            r4 = 0
            if (r2 == 0) goto L_0x0052
            com.itextpdf.layout.layout.LayoutArea r2 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            goto L_0x0053
        L_0x0052:
            r2 = 0
        L_0x0053:
            com.itextpdf.layout.property.CaptionSide r5 = com.itextpdf.layout.property.CaptionSide.BOTTOM
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 == 0) goto L_0x0064
            com.itextpdf.layout.renderer.DivRenderer r6 = r13.captionRenderer
            r7 = 119(0x77, float:1.67E-43)
            java.lang.Object r6 = r6.getProperty(r7)
            com.itextpdf.layout.property.CaptionSide r6 = (com.itextpdf.layout.property.CaptionSide) r6
            goto L_0x0065
        L_0x0064:
            r6 = 0
        L_0x0065:
            boolean r5 = r5.equals(r6)
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 == 0) goto L_0x0083
            com.itextpdf.layout.layout.LayoutArea r6 = r13.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r7 = r6.getBBox()
            if (r5 == 0) goto L_0x0077
            r8 = 0
            goto L_0x0078
        L_0x0077:
            r8 = r2
        L_0x0078:
            r9 = 0
            if (r5 == 0) goto L_0x007d
            r10 = r2
            goto L_0x007e
        L_0x007d:
            r10 = 0
        L_0x007e:
            r11 = 0
            r12 = 0
            r7.applyMargins(r8, r9, r10, r11, r12)
        L_0x0083:
            r13.drawBackground(r14)
            com.itextpdf.layout.renderer.TableBorders r6 = r13.bordersHandler
            boolean r6 = r6 instanceof com.itextpdf.layout.renderer.SeparatedTableBorders
            if (r6 == 0) goto L_0x009b
            boolean r6 = r13.isHeaderRenderer()
            if (r6 != 0) goto L_0x009b
            boolean r6 = r13.isFooterRenderer()
            if (r6 != 0) goto L_0x009b
            r13.drawBorder(r14)
        L_0x009b:
            r13.drawChildren(r14)
            r13.drawPositionedChildren(r14)
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 == 0) goto L_0x00bb
            com.itextpdf.layout.layout.LayoutArea r6 = r13.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r7 = r6.getBBox()
            if (r5 == 0) goto L_0x00af
            r8 = 0
            goto L_0x00b0
        L_0x00af:
            r8 = r2
        L_0x00b0:
            r9 = 0
            if (r5 == 0) goto L_0x00b5
            r10 = r2
            goto L_0x00b6
        L_0x00b5:
            r10 = 0
        L_0x00b6:
            r11 = 0
            r12 = 1
            r7.applyMargins(r8, r9, r10, r11, r12)
        L_0x00bb:
            r13.drawCaption(r14)
            r13.endElementOpacityApplying(r14)
            r4 = 1
            if (r3 == 0) goto L_0x00c7
            r13.applyRelativePositioningTranslation(r4)
        L_0x00c7:
            r13.flushed = r4
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r4 = r14.getCanvas()
            r13.endTransformationIfApplied(r4)
            if (r0 == 0) goto L_0x00e8
            boolean r4 = r13.isLastRendererForModelElement
            if (r4 == 0) goto L_0x00e5
            com.itextpdf.layout.IPropertyContainer r4 = r13.getModelElement()
            com.itextpdf.layout.element.Table r4 = (com.itextpdf.layout.element.Table) r4
            boolean r4 = r4.isComplete()
            if (r4 == 0) goto L_0x00e5
            r1.finishTaggingHint(r13)
        L_0x00e5:
            r1.restoreAutoTaggingPointerPosition(r13)
        L_0x00e8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TableRenderer.draw(com.itextpdf.layout.renderer.DrawContext):void");
    }

    public void drawChildren(DrawContext drawContext) {
        TableRenderer tableRenderer = this.headerRenderer;
        if (tableRenderer != null) {
            tableRenderer.draw(drawContext);
        }
        for (IRenderer child : this.childRenderers) {
            child.draw(drawContext);
        }
        if (this.bordersHandler instanceof CollapsedTableBorders) {
            drawBorders(drawContext);
        }
        TableRenderer tableRenderer2 = this.footerRenderer;
        if (tableRenderer2 != null) {
            tableRenderer2.draw(drawContext);
        }
    }

    /* access modifiers changed from: protected */
    public void drawBackgrounds(DrawContext drawContext) {
        boolean shrinkBackgroundArea = (this.bordersHandler instanceof CollapsedTableBorders) && (isHeaderRenderer() || isFooterRenderer());
        if (shrinkBackgroundArea) {
            this.occupiedArea.getBBox().applyMargins(this.bordersHandler.getMaxTopWidth() / 2.0f, this.bordersHandler.getRightBorderMaxWidth() / 2.0f, this.bordersHandler.getMaxBottomWidth() / 2.0f, this.bordersHandler.getLeftBorderMaxWidth() / 2.0f, false);
        }
        super.drawBackground(drawContext);
        if (shrinkBackgroundArea) {
            this.occupiedArea.getBBox().applyMargins(this.bordersHandler.getMaxTopWidth() / 2.0f, this.bordersHandler.getRightBorderMaxWidth() / 2.0f, this.bordersHandler.getMaxBottomWidth() / 2.0f, this.bordersHandler.getLeftBorderMaxWidth() / 2.0f, true);
        }
        TableRenderer tableRenderer = this.headerRenderer;
        if (tableRenderer != null) {
            tableRenderer.drawBackgrounds(drawContext);
        }
        TableRenderer tableRenderer2 = this.footerRenderer;
        if (tableRenderer2 != null) {
            tableRenderer2.drawBackgrounds(drawContext);
        }
    }

    /* access modifiers changed from: protected */
    public void drawCaption(DrawContext drawContext) {
        if (this.captionRenderer != null && !isFooterRenderer() && !isHeaderRenderer()) {
            this.captionRenderer.draw(drawContext);
        }
    }

    public void drawBackground(DrawContext drawContext) {
        if (!isFooterRenderer() && !isHeaderRenderer()) {
            drawBackgrounds(drawContext);
        }
    }

    public IRenderer getNextRenderer() {
        TableRenderer nextTable = new TableRenderer();
        nextTable.modelElement = this.modelElement;
        return nextTable;
    }

    public void move(float dxRight, float dyUp) {
        super.move(dxRight, dyUp);
        TableRenderer tableRenderer = this.headerRenderer;
        if (tableRenderer != null) {
            tableRenderer.move(dxRight, dyUp);
        }
        TableRenderer tableRenderer2 = this.footerRenderer;
        if (tableRenderer2 != null) {
            tableRenderer2.move(dxRight, dyUp);
        }
    }

    /* access modifiers changed from: protected */
    public TableRenderer[] split(int row) {
        return split(row, false);
    }

    /* access modifiers changed from: protected */
    public TableRenderer[] split(int row, boolean hasContent) {
        return split(row, hasContent, false);
    }

    /* access modifiers changed from: protected */
    public TableRenderer[] split(int row, boolean hasContent, boolean cellWithBigRowspanAdded) {
        TableRenderer splitRenderer = createSplitRenderer(new Table.RowRange(this.rowRange.getStartRow(), this.rowRange.getStartRow() + row));
        splitRenderer.rows = this.rows.subList(0, row);
        splitRenderer.bordersHandler = this.bordersHandler;
        splitRenderer.heights = this.heights;
        splitRenderer.columnWidths = this.columnWidths;
        splitRenderer.countedColumnWidth = this.countedColumnWidth;
        splitRenderer.totalWidthForColumns = this.totalWidthForColumns;
        TableRenderer overflowRenderer = createOverflowRenderer(new Table.RowRange(this.rowRange.getStartRow() + row, this.rowRange.getFinishRow()));
        if (row == 0 && !hasContent && !cellWithBigRowspanAdded && this.rowRange.getStartRow() == 0) {
            overflowRenderer.isOriginalNonSplitRenderer = this.isOriginalNonSplitRenderer;
        }
        List<CellRenderer[]> list = this.rows;
        overflowRenderer.rows = list.subList(row, list.size());
        splitRenderer.occupiedArea = this.occupiedArea;
        overflowRenderer.bordersHandler = this.bordersHandler;
        return new TableRenderer[]{splitRenderer, overflowRenderer};
    }

    /* access modifiers changed from: protected */
    public TableRenderer createSplitRenderer(Table.RowRange rowRange2) {
        TableRenderer splitRenderer = (TableRenderer) getNextRenderer();
        splitRenderer.rowRange = rowRange2;
        splitRenderer.parent = this.parent;
        splitRenderer.modelElement = this.modelElement;
        splitRenderer.childRenderers = this.childRenderers;
        splitRenderer.addAllProperties(getOwnProperties());
        splitRenderer.headerRenderer = this.headerRenderer;
        splitRenderer.footerRenderer = this.footerRenderer;
        splitRenderer.isLastRendererForModelElement = false;
        splitRenderer.topBorderMaxWidth = this.topBorderMaxWidth;
        splitRenderer.captionRenderer = this.captionRenderer;
        splitRenderer.isOriginalNonSplitRenderer = this.isOriginalNonSplitRenderer;
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public TableRenderer createOverflowRenderer(Table.RowRange rowRange2) {
        TableRenderer overflowRenderer = (TableRenderer) getNextRenderer();
        overflowRenderer.setRowRange(rowRange2);
        overflowRenderer.parent = this.parent;
        overflowRenderer.modelElement = this.modelElement;
        overflowRenderer.addAllProperties(getOwnProperties());
        overflowRenderer.isOriginalNonSplitRenderer = false;
        overflowRenderer.countedColumnWidth = this.countedColumnWidth;
        return overflowRenderer;
    }

    /* access modifiers changed from: protected */
    public Float retrieveWidth(float parentBoxWidth) {
        Float tableWidth = super.retrieveWidth(parentBoxWidth);
        Table tableModel = (Table) getModelElement();
        if (tableWidth != null && tableWidth.floatValue() != 0.0f) {
            return tableWidth;
        }
        float totalColumnWidthInPercent = 0.0f;
        for (int col = 0; col < tableModel.getNumberOfColumns(); col++) {
            UnitValue columnWidth = tableModel.getColumnWidth(col);
            if (columnWidth.isPercentValue()) {
                totalColumnWidthInPercent += columnWidth.getValue();
            }
        }
        Float tableWidth2 = Float.valueOf(parentBoxWidth);
        if (totalColumnWidthInPercent > 0.0f) {
            return Float.valueOf((parentBoxWidth * totalColumnWidthInPercent) / 100.0f);
        }
        return tableWidth2;
    }

    public MinMaxWidth getMinMaxWidth() {
        if (this.isOriginalNonSplitRenderer) {
            initializeTableLayoutBorders();
        }
        float rightMaxBorder = this.bordersHandler.getRightBorderMaxWidth();
        float leftMaxBorder = this.bordersHandler.getLeftBorderMaxWidth();
        TableWidths tableWidths = new TableWidths(this, MinMaxWidthUtils.getInfWidth(), true, rightMaxBorder, leftMaxBorder);
        float maxColTotalWidth = 0.0f;
        for (float column : this.isOriginalNonSplitRenderer ? tableWidths.layout() : this.countedColumnWidth) {
            maxColTotalWidth += column;
        }
        float minWidth = this.isOriginalNonSplitRenderer ? tableWidths.getMinWidth() : maxColTotalWidth;
        UnitValue marginRightUV = getPropertyAsUnitValue(45);
        Class<TableRenderer> cls = TableRenderer.class;
        if (!marginRightUV.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
        }
        UnitValue marginLefttUV = getPropertyAsUnitValue(44);
        if (!marginLefttUV.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        return new MinMaxWidth(minWidth, maxColTotalWidth, marginLefttUV.getValue() + marginRightUV.getValue() + (rightMaxBorder / 2.0f) + (leftMaxBorder / 2.0f));
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Float getLastYLineRecursively() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean allowLastYLineRecursiveExtraction() {
        return false;
    }

    private void initializeTableLayoutBorders() {
        TableBorders tableBorders;
        if (BorderCollapsePropertyValue.SEPARATE.equals(getProperty(114))) {
            tableBorders = new SeparatedTableBorders(this.rows, ((Table) getModelElement()).getNumberOfColumns(), getBorders());
        } else {
            tableBorders = new CollapsedTableBorders(this.rows, ((Table) getModelElement()).getNumberOfColumns(), getBorders());
        }
        this.bordersHandler = tableBorders;
        tableBorders.initializeBorders();
        this.bordersHandler.setTableBoundingBorders(getBorders());
        this.bordersHandler.setRowRange(this.rowRange.getStartRow(), this.rowRange.getFinishRow());
        initializeHeaderAndFooter(true);
        this.bordersHandler.updateBordersOnNewPage(this.isOriginalNonSplitRenderer, isFooterRenderer() || isHeaderRenderer(), this, this.headerRenderer, this.footerRenderer);
        correctRowRange();
    }

    private void correctRowRange() {
        if (this.rows.size() < (this.rowRange.getFinishRow() - this.rowRange.getStartRow()) + 1) {
            this.rowRange = new Table.RowRange(this.rowRange.getStartRow(), (this.rowRange.getStartRow() + this.rows.size()) - 1);
        }
    }

    public void drawBorder(DrawContext drawContext) {
        if (this.bordersHandler instanceof SeparatedTableBorders) {
            super.drawBorder(drawContext);
        }
    }

    /* access modifiers changed from: protected */
    public void drawBorders(DrawContext drawContext) {
        boolean z = true;
        boolean z2 = this.headerRenderer != null;
        if (this.footerRenderer == null) {
            z = false;
        }
        drawBorders(drawContext, z2, z);
    }

    private void drawBorders(DrawContext drawContext, boolean hasHeader, boolean hasFooter) {
        float startY;
        float height = this.occupiedArea.getBBox().getHeight();
        TableRenderer tableRenderer = this.footerRenderer;
        if (tableRenderer != null) {
            height -= tableRenderer.occupiedArea.getBBox().getHeight();
        }
        TableRenderer tableRenderer2 = this.headerRenderer;
        if (tableRenderer2 != null) {
            height -= tableRenderer2.occupiedArea.getBBox().getHeight();
        }
        if (height >= 1.0E-4f) {
            float startX = getOccupiedArea().getBBox().getX() + (this.bordersHandler.getLeftBorderMaxWidth() / 2.0f);
            float startY2 = getOccupiedArea().getBBox().getY() + getOccupiedArea().getBBox().getHeight();
            TableRenderer tableRenderer3 = this.headerRenderer;
            if (tableRenderer3 != null) {
                startY = (startY2 - tableRenderer3.occupiedArea.getBBox().getHeight()) + (this.topBorderMaxWidth / 2.0f);
            } else {
                startY = startY2 - (this.topBorderMaxWidth / 2.0f);
            }
            Class<TableRenderer> cls = TableRenderer.class;
            if (hasProperty(46)) {
                UnitValue topMargin = getPropertyAsUnitValue(46);
                if (topMargin != null && !topMargin.isPointValue()) {
                    LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
                }
                startY -= topMargin == null ? 0.0f : topMargin.getValue();
            }
            if (hasProperty(44)) {
                UnitValue leftMargin = getPropertyAsUnitValue(44);
                if (leftMargin != null && !leftMargin.isPointValue()) {
                    LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
                }
                startX += leftMargin == null ? 0.0f : leftMargin.getValue();
            }
            if (this.childRenderers.size() == 0) {
                Border[] borders = this.bordersHandler.tableBoundingBorders;
                if (borders[0] != null) {
                    if (borders[2] != null && this.heights.size() == 0) {
                        this.heights.add(0, Float.valueOf((borders[0].getWidth() / 2.0f) + (borders[2].getWidth() / 2.0f)));
                    }
                } else if (borders[2] != null) {
                    startY -= borders[2].getWidth() / 2.0f;
                }
                if (this.heights.size() == 0) {
                    this.heights.add(Float.valueOf(0.0f));
                }
            }
            boolean isTagged = drawContext.isTaggingEnabled();
            if (isTagged) {
                drawContext.getCanvas().openTag((CanvasTag) new CanvasArtifact());
            }
            boolean isTopTablePart = isTopTablePart();
            boolean isBottomTablePart = isBottomTablePart();
            boolean isComplete = getTable().isComplete();
            boolean isFooterRendererOfLargeTable = isFooterRendererOfLargeTable();
            this.bordersHandler.setRowRange(this.rowRange.getStartRow(), (this.rowRange.getStartRow() + this.heights.size()) - 1);
            TableBorders tableBorders = this.bordersHandler;
            if (tableBorders instanceof CollapsedTableBorders) {
                if (hasFooter) {
                    ((CollapsedTableBorders) tableBorders).setBottomBorderCollapseWith(this.footerRenderer.bordersHandler.getFirstHorizontalBorder());
                } else if (isBottomTablePart) {
                    ((CollapsedTableBorders) tableBorders).setBottomBorderCollapseWith((List<Border>) null);
                }
            }
            float y1 = startY;
            if (isFooterRendererOfLargeTable) {
                this.bordersHandler.drawHorizontalBorder(0, startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
            }
            if (this.heights.size() != 0) {
                y1 -= this.heights.get(0).floatValue();
            }
            for (int i = 1; i < this.heights.size(); i++) {
                this.bordersHandler.drawHorizontalBorder(i, startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
                if (i < this.heights.size()) {
                    y1 -= this.heights.get(i).floatValue();
                }
            }
            if (!isBottomTablePart && isComplete) {
                this.bordersHandler.drawHorizontalBorder(this.heights.size(), startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
            }
            float x1 = startX;
            float[] fArr = this.countedColumnWidth;
            if (fArr.length > 0) {
                x1 += fArr[0];
            }
            for (int i2 = 1; i2 < this.bordersHandler.getNumberOfColumns(); i2++) {
                this.bordersHandler.drawVerticalBorder(i2, startY, x1, drawContext.getCanvas(), this.heights);
                float[] fArr2 = this.countedColumnWidth;
                if (i2 < fArr2.length) {
                    x1 += fArr2[i2];
                }
            }
            if (isTopTablePart) {
                this.bordersHandler.drawHorizontalBorder(0, startX, startY, drawContext.getCanvas(), this.countedColumnWidth);
            }
            if (isBottomTablePart && (isComplete || (!this.isLastRendererForModelElement && !isEmptyTableRenderer()))) {
                this.bordersHandler.drawHorizontalBorder(this.heights.size(), startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
            }
            float f = startY;
            this.bordersHandler.drawVerticalBorder(0, f, startX, drawContext.getCanvas(), this.heights);
            TableBorders tableBorders2 = this.bordersHandler;
            tableBorders2.drawVerticalBorder(tableBorders2.getNumberOfColumns(), f, x1, drawContext.getCanvas(), this.heights);
            if (isTagged) {
                drawContext.getCanvas().closeTag();
            }
        }
    }

    private boolean isEmptyTableRenderer() {
        return this.rows.isEmpty() && this.heights.size() == 1 && this.heights.get(0).floatValue() == 0.0f;
    }

    private void applyFixedXOrYPosition(boolean isXPosition, Rectangle layoutBox) {
        if (isPositioned() && isFixedLayout()) {
            if (isXPosition) {
                layoutBox.setX(getPropertyAsFloat(34).floatValue());
            } else {
                move(0.0f, getPropertyAsFloat(14).floatValue() - this.occupiedArea.getBBox().getY());
            }
        }
    }

    private void adjustFooterAndFixOccupiedArea(Rectangle layoutBox, float verticalBorderSpacing) {
        TableRenderer tableRenderer = this.footerRenderer;
        if (tableRenderer != null) {
            tableRenderer.move(0.0f, layoutBox.getHeight() + verticalBorderSpacing);
            float footerHeight = this.footerRenderer.getOccupiedArea().getBBox().getHeight() - verticalBorderSpacing;
            this.occupiedArea.getBBox().moveDown(footerHeight).increaseHeight(footerHeight);
        }
    }

    private void adjustCaptionAndFixOccupiedArea(Rectangle layoutBox, float verticalBorderSpacing) {
        DivRenderer divRenderer = this.captionRenderer;
        if (divRenderer != null) {
            float captionHeight = divRenderer.getOccupiedArea().getBBox().getHeight();
            this.occupiedArea.getBBox().moveDown(captionHeight).increaseHeight(captionHeight);
            if (CaptionSide.BOTTOM.equals(this.captionRenderer.getProperty(119))) {
                this.captionRenderer.move(0.0f, layoutBox.getHeight() + verticalBorderSpacing);
            } else {
                this.occupiedArea.getBBox().moveUp(captionHeight);
            }
        }
    }

    private void correctLayoutedCellsOccupiedAreas(LayoutResult[] splits, int row, int[] targetOverflowRowIndex, Float blockMinHeight, Rectangle layoutBox, List<Boolean> rowsHasCellWithSetHeight, boolean isLastRenderer, boolean processBigRowspan, boolean skip) {
        float f;
        int numOfRowsWithFloatHeight;
        float additionalCellHeight;
        CellRenderer[] currentRow;
        float cellHeightInLastRow;
        Rectangle rectangle = layoutBox;
        List<Boolean> list = rowsHasCellWithSetHeight;
        int k = this.bordersHandler.getFinishRow();
        this.bordersHandler.setFinishRow(this.rowRange.getFinishRow());
        Border currentBorder = this.bordersHandler.getWidestHorizontalBorder(k + 1);
        this.bordersHandler.setFinishRow(k);
        if (skip) {
            this.bordersHandler.tableBoundingBorders[2] = getBorders()[2];
            TableBorders tableBorders = this.bordersHandler;
            tableBorders.skipFooter(tableBorders.tableBoundingBorders);
        }
        float f2 = 0.0f;
        if (this.bordersHandler instanceof CollapsedTableBorders) {
            f = currentBorder == null ? 0.0f : currentBorder.getWidth();
        } else {
            f = 0.0f;
        }
        float currentBottomIndent = f;
        TableBorders tableBorders2 = this.bordersHandler;
        if (tableBorders2 instanceof CollapsedTableBorders) {
            f2 = tableBorders2.getMaxBottomWidth();
        }
        float realBottomIndent = f2;
        if (this.heights.size() != 0) {
            List<Float> list2 = this.heights;
            List<Float> list3 = this.heights;
            list2.set(list2.size() - 1, Float.valueOf(list3.get(list3.size() - 1).floatValue() + ((realBottomIndent - currentBottomIndent) / 2.0f)));
            this.occupiedArea.getBBox().increaseHeight((realBottomIndent - currentBottomIndent) / 2.0f).moveDown((realBottomIndent - currentBottomIndent) / 2.0f);
            rectangle.decreaseHeight((realBottomIndent - currentBottomIndent) / 2.0f);
            if (processBigRowspan) {
                CellRenderer[] currentRow2 = this.rows.get(this.heights.size());
                int col = 0;
                while (col < currentRow2.length) {
                    CellRenderer cell = splits[col] == null ? currentRow2[col] : (CellRenderer) splits[col].getSplitRenderer();
                    if (cell == null) {
                        currentRow = currentRow2;
                    } else {
                        float height = 0.0f;
                        int rowspan = cell.getPropertyAsInteger(60).intValue();
                        int colspan = cell.getPropertyAsInteger(16).intValue();
                        TableBorders tableBorders3 = this.bordersHandler;
                        float[] indents = tableBorders3.getCellBorderIndents(tableBorders3 instanceof SeparatedTableBorders ? row : targetOverflowRowIndex[col], col, rowspan, colspan);
                        int l = (this.heights.size() - 1) - 1;
                        while (l > targetOverflowRowIndex[col] - rowspan && l >= 0) {
                            height += this.heights.get(l).floatValue();
                            l--;
                        }
                        if (this.bordersHandler instanceof SeparatedTableBorders) {
                            cellHeightInLastRow = cell.getOccupiedArea().getBBox().getHeight() - height;
                        } else {
                            cellHeightInLastRow = ((cell.getOccupiedArea().getBBox().getHeight() + (indents[0] / 2.0f)) + (indents[2] / 2.0f)) - height;
                        }
                        List<Float> list4 = this.heights;
                        float[] fArr = indents;
                        if (list4.get(list4.size() - 1).floatValue() < cellHeightInLastRow) {
                            if (this.bordersHandler instanceof SeparatedTableBorders) {
                                List<Float> list5 = this.heights;
                                float differenceToConsider = cellHeightInLastRow - list5.get(list5.size() - 1).floatValue();
                                this.occupiedArea.getBBox().moveDown(differenceToConsider);
                                this.occupiedArea.getBBox().increaseHeight(differenceToConsider);
                            }
                            List<Float> list6 = this.heights;
                            currentRow = currentRow2;
                            list6.set(list6.size() - 1, Float.valueOf(cellHeightInLastRow));
                        } else {
                            currentRow = currentRow2;
                        }
                    }
                    col++;
                    currentRow2 = currentRow;
                }
            }
        }
        int numOfRowsWithFloatHeight2 = 0;
        if (isLastRenderer) {
            float additionalHeight = 0.0f;
            if (blockMinHeight != null && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight() + (realBottomIndent / 2.0f)) {
                additionalHeight = Math.min(layoutBox.getHeight() - (realBottomIndent / 2.0f), (blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight()) - (realBottomIndent / 2.0f));
                for (int k2 = 0; k2 < rowsHasCellWithSetHeight.size(); k2++) {
                    if (Boolean.FALSE.equals(list.get(k2))) {
                        numOfRowsWithFloatHeight2++;
                    }
                }
            }
            float additionalCellHeight2 = additionalHeight / ((float) (numOfRowsWithFloatHeight2 == 0 ? this.heights.size() : numOfRowsWithFloatHeight2));
            for (int k3 = 0; k3 < this.heights.size(); k3++) {
                if (numOfRowsWithFloatHeight2 == 0 || Boolean.FALSE.equals(list.get(k3))) {
                    List<Float> list7 = this.heights;
                    list7.set(k3, Float.valueOf(list7.get(k3).floatValue() + additionalCellHeight2));
                }
            }
            additionalCellHeight = additionalCellHeight2;
            numOfRowsWithFloatHeight = numOfRowsWithFloatHeight2;
        } else {
            additionalCellHeight = 0.0f;
            numOfRowsWithFloatHeight = 0;
        }
        float cumulativeShift = 0.0f;
        int k4 = 0;
        while (k4 < this.heights.size()) {
            int finish = k;
            int finish2 = k4;
            Border currentBorder2 = currentBorder;
            float cumulativeShift2 = cumulativeShift;
            correctRowCellsOccupiedAreas(splits, row, targetOverflowRowIndex, k4, rowsHasCellWithSetHeight, cumulativeShift, additionalCellHeight);
            if (!isLastRenderer || (numOfRowsWithFloatHeight != 0 && !Boolean.FALSE.equals(list.get(finish2)))) {
                cumulativeShift = cumulativeShift2;
            } else {
                cumulativeShift = cumulativeShift2 + additionalCellHeight;
            }
            k4 = finish2 + 1;
            k = finish;
            currentBorder = currentBorder2;
        }
        float cumulativeShift3 = cumulativeShift;
        this.occupiedArea.getBBox().moveDown(cumulativeShift3).increaseHeight(cumulativeShift3);
        rectangle.decreaseHeight(cumulativeShift3);
    }

    private void correctRowCellsOccupiedAreas(LayoutResult[] splits, int row, int[] targetOverflowRowIndex, int currentRowIndex, List<Boolean> rowsHasCellWithSetHeight, float cumulativeShift, float additionalCellHeight) {
        float height;
        TableRenderer tableRenderer = this;
        int i = row;
        int i2 = currentRowIndex;
        CellRenderer[] currentRow = tableRenderer.rows.get(i2);
        int col = 0;
        while (col < currentRow.length) {
            CellRenderer cell = (i2 < i || splits[col] == null) ? currentRow[col] : (CellRenderer) splits[col].getSplitRenderer();
            if (cell == null) {
                List<Boolean> list = rowsHasCellWithSetHeight;
            } else {
                float height2 = 0.0f;
                int colspan = cell.getPropertyAsInteger(16).intValue();
                int rowspan = cell.getPropertyAsInteger(60).intValue();
                float rowspanOffset = 0.0f;
                TableBorders tableBorders = tableRenderer.bordersHandler;
                float[] indents = tableBorders.getCellBorderIndents((i2 < i || (tableBorders instanceof SeparatedTableBorders)) ? i2 : targetOverflowRowIndex[col], col, rowspan, colspan);
                int l = (i2 < i ? i2 : tableRenderer.heights.size() - 1) - 1;
                while (true) {
                    if (l <= (i2 < i ? i2 : targetOverflowRowIndex[col]) - rowspan || l < 0) {
                        List<Boolean> list2 = rowsHasCellWithSetHeight;
                        List<Float> list3 = tableRenderer.heights;
                    } else {
                        height2 += tableRenderer.heights.get(l).floatValue();
                        if (Boolean.FALSE.equals(rowsHasCellWithSetHeight.get(l))) {
                            rowspanOffset += additionalCellHeight;
                        }
                        l--;
                    }
                }
                List<Boolean> list22 = rowsHasCellWithSetHeight;
                List<Float> list32 = tableRenderer.heights;
                float height3 = height2 + list32.get(i2 < i ? i2 : list32.size() - 1).floatValue();
                if (!(tableRenderer.bordersHandler instanceof SeparatedTableBorders)) {
                    height = height3 - ((indents[0] / 2.0f) + (indents[2] / 2.0f));
                } else {
                    height = height3;
                }
                float shift = height - cell.getOccupiedArea().getBBox().getHeight();
                Rectangle bBox = cell.getOccupiedArea().getBBox();
                bBox.moveDown(shift);
                try {
                    cell.move(0.0f, -(cumulativeShift - rowspanOffset));
                    bBox.setHeight(height);
                    cell.applyVerticalAlignment();
                } catch (NullPointerException e) {
                    NullPointerException nullPointerException = e;
                    LoggerFactory.getLogger((Class<?>) TableRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Some of the cell's content might not end up placed correctly."));
                }
            }
            col++;
            tableRenderer = this;
        }
        List<Boolean> list4 = rowsHasCellWithSetHeight;
    }

    /* access modifiers changed from: protected */
    public void extendLastRow(CellRenderer[] lastRow, Rectangle freeBox) {
        if (lastRow != null && this.heights.size() != 0) {
            List<Float> list = this.heights;
            List<Float> list2 = this.heights;
            list.set(list.size() - 1, Float.valueOf(list2.get(list2.size() - 1).floatValue() + freeBox.getHeight()));
            this.occupiedArea.getBBox().moveDown(freeBox.getHeight()).increaseHeight(freeBox.getHeight());
            for (CellRenderer cell : lastRow) {
                if (cell != null) {
                    cell.occupiedArea.getBBox().moveDown(freeBox.getHeight()).increaseHeight(freeBox.getHeight());
                }
            }
            freeBox.moveUp(freeBox.getHeight()).setHeight(0.0f);
        }
    }

    private void setRowRange(Table.RowRange rowRange2) {
        this.rowRange = rowRange2;
        for (int row = rowRange2.getStartRow(); row <= rowRange2.getFinishRow(); row++) {
            this.rows.add(new CellRenderer[((Table) this.modelElement).getNumberOfColumns()]);
        }
    }

    private TableRenderer initFooterOrHeaderRenderer(boolean footer, Border[] tableBorders) {
        TableBorders tableBorders2;
        Table table = (Table) getModelElement();
        boolean isSeparated = BorderCollapsePropertyValue.SEPARATE.equals(getProperty(114));
        Table footerOrHeader = footer ? table.getFooter() : table.getHeader();
        int outerBorder = 2;
        boolean firstHeader = false;
        int innerBorder = footer ? 0 : 2;
        if (!footer) {
            outerBorder = 0;
        }
        TableRenderer renderer = (TableRenderer) footerOrHeader.createRendererSubTree().setParent(this);
        ensureFooterOrHeaderHasTheSamePropertiesAsParentTableRenderer(renderer);
        if (!footer && this.rowRange.getStartRow() == 0 && this.isOriginalNonSplitRenderer) {
            firstHeader = true;
        }
        LayoutTaggingHelper taggingHelper = (LayoutTaggingHelper) getProperty(108);
        if (taggingHelper != null) {
            taggingHelper.addKidsHint((IPropertyContainer) this, (Iterable<? extends IPropertyContainer>) Collections.singletonList(renderer));
            LayoutTaggingHelper.addTreeHints(taggingHelper, renderer);
            if (!footer && !firstHeader) {
                taggingHelper.markArtifactHint((IPropertyContainer) renderer);
            }
        }
        TableBorders tableBorders3 = this.bordersHandler;
        if (tableBorders3 instanceof SeparatedTableBorders) {
            if (table.isEmpty()) {
                if (!footer || this.headerRenderer == null) {
                    renderer.setBorders(tableBorders[innerBorder], innerBorder);
                }
                this.bordersHandler.tableBoundingBorders[innerBorder] = Border.NO_BORDER;
            }
            renderer.setBorders(tableBorders[1], 1);
            renderer.setBorders(tableBorders[3], 3);
            renderer.setBorders(tableBorders[outerBorder], outerBorder);
            this.bordersHandler.tableBoundingBorders[outerBorder] = Border.NO_BORDER;
        } else if (tableBorders3 instanceof CollapsedTableBorders) {
            Border[] borders = renderer.getBorders();
            if (table.isEmpty()) {
                renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[innerBorder], tableBorders[innerBorder]), innerBorder);
                this.bordersHandler.tableBoundingBorders[innerBorder] = Border.NO_BORDER;
            }
            renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[1], tableBorders[1]), 1);
            renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[3], tableBorders[3]), 3);
            renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[outerBorder], tableBorders[outerBorder]), outerBorder);
            this.bordersHandler.tableBoundingBorders[outerBorder] = Border.NO_BORDER;
        }
        if (isSeparated) {
            tableBorders2 = new SeparatedTableBorders(renderer.rows, ((Table) renderer.getModelElement()).getNumberOfColumns(), renderer.getBorders());
        } else {
            tableBorders2 = new CollapsedTableBorders(renderer.rows, ((Table) renderer.getModelElement()).getNumberOfColumns(), renderer.getBorders());
        }
        renderer.bordersHandler = tableBorders2;
        tableBorders2.initializeBorders();
        renderer.bordersHandler.setRowRange(renderer.rowRange.getStartRow(), renderer.rowRange.getFinishRow());
        renderer.bordersHandler.processAllBordersAndEmptyRows();
        renderer.correctRowRange();
        return renderer;
    }

    private void ensureFooterOrHeaderHasTheSamePropertiesAsParentTableRenderer(TableRenderer headerOrFooterRenderer) {
        headerOrFooterRenderer.setProperty(114, getProperty(114));
        if (this.bordersHandler instanceof SeparatedTableBorders) {
            headerOrFooterRenderer.setProperty(115, getPropertyAsFloat(115));
            headerOrFooterRenderer.setProperty(116, getPropertyAsFloat(116));
            headerOrFooterRenderer.setProperty(9, Border.NO_BORDER);
            headerOrFooterRenderer.setProperty(11, Border.NO_BORDER);
            headerOrFooterRenderer.setProperty(13, Border.NO_BORDER);
            headerOrFooterRenderer.setProperty(12, Border.NO_BORDER);
            headerOrFooterRenderer.setProperty(10, Border.NO_BORDER);
        }
    }

    private TableRenderer prepareFooterOrHeaderRendererForLayout(TableRenderer renderer, float layoutBoxWidth) {
        renderer.countedColumnWidth = this.countedColumnWidth;
        renderer.bordersHandler.leftBorderMaxWidth = this.bordersHandler.getLeftBorderMaxWidth();
        renderer.bordersHandler.rightBorderMaxWidth = this.bordersHandler.getRightBorderMaxWidth();
        if (hasProperty(77)) {
            renderer.setProperty(77, UnitValue.createPointValue(layoutBoxWidth));
        }
        return this;
    }

    private boolean isHeaderRenderer() {
        return (this.parent instanceof TableRenderer) && ((TableRenderer) this.parent).headerRenderer == this;
    }

    private boolean isFooterRenderer() {
        return (this.parent instanceof TableRenderer) && ((TableRenderer) this.parent).footerRenderer == this;
    }

    private boolean isFooterRendererOfLargeTable() {
        return isFooterRenderer() && (!((TableRenderer) this.parent).getTable().isComplete() || ((TableRenderer) this.parent).getTable().getLastRowBottomBorder().size() != 0);
    }

    private boolean isTopTablePart() {
        return this.headerRenderer == null && (!isFooterRenderer() || (((TableRenderer) this.parent).rows.size() == 0 && ((TableRenderer) this.parent).headerRenderer == null));
    }

    private boolean isBottomTablePart() {
        return this.footerRenderer == null && (!isHeaderRenderer() || (((TableRenderer) this.parent).rows.size() == 0 && ((TableRenderer) this.parent).footerRenderer == null));
    }

    private void calculateColumnWidths(float availableWidth) {
        if (this.countedColumnWidth == null || this.totalWidthForColumns != availableWidth) {
            this.countedColumnWidth = new TableWidths(this, availableWidth, false, this.bordersHandler.rightBorderMaxWidth, this.bordersHandler.leftBorderMaxWidth).layout();
        }
    }

    private float getTableWidth() {
        float sum = 0.0f;
        for (float column : this.countedColumnWidth) {
            sum += column;
        }
        TableBorders tableBorders = this.bordersHandler;
        if (!(tableBorders instanceof SeparatedTableBorders)) {
            return sum + (tableBorders.getRightBorderMaxWidth() / 2.0f) + (this.bordersHandler.getLeftBorderMaxWidth() / 2.0f);
        }
        float sum2 = sum + tableBorders.getRightBorderMaxWidth() + this.bordersHandler.getLeftBorderMaxWidth();
        Float horizontalSpacing = getPropertyAsFloat(115);
        return sum2 + (horizontalSpacing == null ? 0.0f : horizontalSpacing.floatValue());
    }

    private static class CellRendererInfo {
        public CellRenderer cellRenderer;
        public int column;
        public int finishRowInd;

        public CellRendererInfo(CellRenderer cellRenderer2, int column2, int finishRow) {
            this.cellRenderer = cellRenderer2;
            this.column = column2;
            this.finishRowInd = finishRow;
        }
    }

    private static class OverflowRowsWrapper {
        private boolean isReplaced = false;
        private HashMap<Integer, Boolean> isRowReplaced = new HashMap<>();
        private TableRenderer overflowRenderer;

        public OverflowRowsWrapper(TableRenderer overflowRenderer2) {
            this.overflowRenderer = overflowRenderer2;
        }

        public CellRenderer getCell(int row, int col) {
            return this.overflowRenderer.rows.get(row)[col];
        }

        public CellRenderer setCell(int row, int col, CellRenderer newCell) {
            if (!this.isReplaced) {
                this.overflowRenderer.rows = new ArrayList(this.overflowRenderer.rows);
                this.isReplaced = true;
            }
            if (!Boolean.TRUE.equals(this.isRowReplaced.get(Integer.valueOf(row)))) {
                this.overflowRenderer.rows.set(row, (CellRenderer[]) this.overflowRenderer.rows.get(row).clone());
            }
            this.overflowRenderer.rows.get(row)[col] = newCell;
            return newCell;
        }
    }

    private void enlargeCellWithBigRowspan(CellRenderer[] currentRow, OverflowRowsWrapper overflowRows, int row, int col, int minRowspan, TableRenderer[] splitResult, int[] targetOverflowRowIndex) {
        this.childRenderers.add(currentRow[col]);
        int i = row;
        while (i < row + minRowspan && i + 1 < this.rows.size() && splitResult[1].rows.get((i + 1) - row)[col] != null) {
            overflowRows.setCell(i - row, col, splitResult[1].rows.get((i + 1) - row)[col]);
            overflowRows.setCell((i + 1) - row, col, (CellRenderer) null);
            this.rows.get(i)[col] = this.rows.get(i + 1)[col];
            this.rows.get(i + 1)[col] = null;
            i++;
        }
        if (i != (row + minRowspan) - 1 && this.rows.get(i)[col] != null) {
            CellRenderer overflowCell = (CellRenderer) ((Cell) this.rows.get(i)[col].getModelElement()).getRenderer().setParent(this);
            overflowRows.setCell(i - row, col, (CellRenderer) null);
            overflowRows.setCell(targetOverflowRowIndex[col] - row, col, overflowCell);
            CellRenderer originalCell = this.rows.get(i)[col];
            this.rows.get(i)[col] = null;
            this.rows.get(targetOverflowRowIndex[col])[col] = originalCell;
            originalCell.isLastRendererForModelElement = false;
            overflowCell.setProperty(109, originalCell.getProperty(109));
        }
    }

    private void enlargeCell(int col, int row, int minRowspan, CellRenderer[] currentRow, OverflowRowsWrapper overflowRows, int[] targetOverflowRowIndex, TableRenderer[] splitResult) {
        int i = col;
        OverflowRowsWrapper overflowRowsWrapper = overflowRows;
        LayoutArea cellOccupiedArea = currentRow[i].getOccupiedArea();
        if (1 == minRowspan) {
            CellRenderer overflowCell = (CellRenderer) ((Cell) currentRow[i].getModelElement()).clone(true).getRenderer();
            overflowCell.setParent(this);
            overflowCell.deleteProperty(27);
            overflowCell.deleteProperty(85);
            overflowCell.deleteProperty(84);
            overflowRowsWrapper.setCell(0, col, (CellRenderer) null);
            overflowRowsWrapper.setCell(targetOverflowRowIndex[i] - row, col, overflowCell);
            this.childRenderers.add(currentRow[i]);
            CellRenderer originalCell = currentRow[i];
            currentRow[i] = null;
            this.rows.get(targetOverflowRowIndex[i])[i] = originalCell;
            originalCell.isLastRendererForModelElement = false;
            overflowCell.setProperty(109, originalCell.getProperty(109));
        } else {
            enlargeCellWithBigRowspan(currentRow, overflowRows, row, col, minRowspan, splitResult, targetOverflowRowIndex);
        }
        overflowRowsWrapper.getCell(targetOverflowRowIndex[i] - row, col).occupiedArea = cellOccupiedArea;
    }

    /* access modifiers changed from: package-private */
    public void applyMarginsAndPaddingsAndCalculateColumnWidths(Rectangle layoutBox) {
        UnitValue[] margins = getMargins();
        Class<TableRenderer> cls = TableRenderer.class;
        if (!margins[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
        }
        if (!margins[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        UnitValue[] paddings = getPaddings();
        if (!paddings[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 49));
        }
        if (!paddings[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 48));
        }
        calculateColumnWidths((((layoutBox.getWidth() - margins[1].getValue()) - margins[3].getValue()) - paddings[1].getValue()) - paddings[3].getValue());
    }
}

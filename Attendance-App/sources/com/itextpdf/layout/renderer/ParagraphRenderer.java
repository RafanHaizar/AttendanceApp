package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.LineLayoutResult;
import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.ParagraphOrphansControl;
import com.itextpdf.layout.property.ParagraphWidowsControl;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.util.List;

public class ParagraphRenderer extends BlockRenderer {
    protected List<LineRenderer> lines = null;
    @Deprecated
    protected float previousDescent = 0.0f;

    public ParagraphRenderer(Paragraph modelElement) {
        super(modelElement);
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        ParagraphOrphansControl orphansControl = (ParagraphOrphansControl) getProperty(121);
        ParagraphWidowsControl widowsControl = (ParagraphWidowsControl) getProperty(122);
        if (orphansControl == null && widowsControl == null) {
            return directLayout(layoutContext);
        }
        return OrphansWidowsLayoutHelper.orphansWidowsAwareLayout(this, layoutContext, orphansControl, widowsControl);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v44, resolved type: java.util.ArrayList} */
    /* JADX WARNING: type inference failed for: r0v158, types: [com.itextpdf.layout.renderer.IRenderer] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.layout.LayoutResult directLayout(com.itextpdf.layout.layout.LayoutContext r74) {
        /*
            r73 = this;
            r8 = r73
            r7 = 0
            boolean r9 = r74.isClippedHeight()
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            int r10 = r0.getPageNumber()
            r6 = 0
            r11 = 1
            com.itextpdf.layout.renderer.LineRenderer r0 = new com.itextpdf.layout.renderer.LineRenderer
            r0.<init>()
            com.itextpdf.layout.renderer.IRenderer r0 = r0.setParent(r8)
            r12 = r0
            com.itextpdf.layout.renderer.LineRenderer r12 = (com.itextpdf.layout.renderer.LineRenderer) r12
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.kernel.geom.Rectangle r13 = r0.clone()
            r0 = 0
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r2 = 89
            java.lang.Boolean r2 = r8.getPropertyAsBoolean(r2)
            boolean r14 = r1.equals(r2)
            if (r14 == 0) goto L_0x0044
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r1 = new com.itextpdf.layout.margincollapse.MarginsCollapseHandler
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r2 = r74.getMarginsCollapseInfo()
            r1.<init>(r8, r2)
            r0 = r1
            r15 = r0
            goto L_0x0045
        L_0x0044:
            r15 = r0
        L_0x0045:
            r5 = 103(0x67, float:1.44E-43)
            java.lang.Object r0 = r8.getProperty(r5)
            r4 = r0
            com.itextpdf.layout.property.OverflowPropertyValue r4 = (com.itextpdf.layout.property.OverflowPropertyValue) r4
            r0 = 118(0x76, float:1.65E-43)
            java.lang.Boolean r3 = r8.getPropertyAsBoolean(r0)
            r12.setProperty(r0, r3)
            r16 = 0
            java.util.List r2 = r74.getFloatRendererAreas()
            r0 = 99
            java.lang.Object r0 = r8.getProperty(r0)
            r1 = r0
            com.itextpdf.layout.property.FloatPropertyValue r1 = (com.itextpdf.layout.property.FloatPropertyValue) r1
            float r0 = com.itextpdf.layout.renderer.FloatingHelper.calculateClearHeightCorrection(r8, r2, r13)
            boolean r5 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r73)
            com.itextpdf.layout.renderer.FloatingHelper.applyClearance(r13, r15, r0, r5)
            float r5 = r13.getWidth()
            java.lang.Float r18 = r8.retrieveWidth(r5)
            boolean r5 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r8, r1)
            if (r5 == 0) goto L_0x00a6
            r5 = r0
            r0 = r73
            r19 = r1
            r1 = r13
            r20 = r2
            r2 = r18
            r21 = r3
            r3 = r20
            r22 = r4
            r4 = r19
            r23 = r11
            r17 = r12
            r12 = 103(0x67, float:1.44E-43)
            r11 = r5
            r5 = r22
            java.lang.Float r18 = com.itextpdf.layout.renderer.FloatingHelper.adjustFloatedBlockLayoutBox(r0, r1, r2, r3, r4, r5)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2 = r0
            r5 = r2
            goto L_0x00b7
        L_0x00a6:
            r19 = r1
            r20 = r2
            r21 = r3
            r22 = r4
            r23 = r11
            r17 = r12
            r12 = 103(0x67, float:1.44E-43)
            r11 = r0
            r5 = r20
        L_0x00b7:
            java.util.List r0 = r8.childRenderers
            int r0 = r0.size()
            if (r0 != 0) goto L_0x00c5
            r6 = 1
            r0 = 0
            r17 = r6
            r6 = r0
            goto L_0x00cb
        L_0x00c5:
            r72 = r17
            r17 = r6
            r6 = r72
        L_0x00cb:
            boolean r20 = r73.isPositioned()
            r0 = 55
            java.lang.Float r24 = r8.getPropertyAsFloat(r0)
            java.lang.Float r25 = r73.retrieveMaxHeight()
            r4 = 104(0x68, float:1.46E-43)
            if (r25 == 0) goto L_0x00e9
            float r0 = r25.floatValue()
            float r1 = r13.getHeight()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ee
        L_0x00e9:
            if (r9 != 0) goto L_0x00ee
            com.itextpdf.layout.property.OverflowPropertyValue r0 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            goto L_0x00f4
        L_0x00ee:
            java.lang.Object r0 = r8.getProperty(r4)
            com.itextpdf.layout.property.OverflowPropertyValue r0 = (com.itextpdf.layout.property.OverflowPropertyValue) r0
        L_0x00f4:
            r3 = r0
            if (r24 != 0) goto L_0x00fd
            boolean r0 = r73.isFixedLayout()
            if (r0 == 0) goto L_0x010d
        L_0x00fd:
            float r0 = r13.getHeight()
            r1 = 1232348160(0x49742400, float:1000000.0)
            float r0 = r1 - r0
            com.itextpdf.kernel.geom.Rectangle r0 = r13.moveDown(r0)
            r0.setHeight(r1)
        L_0x010d:
            if (r24 == 0) goto L_0x0120
            boolean r0 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r73)
            if (r0 != 0) goto L_0x0120
            float r0 = r13.getWidth()
            java.lang.Float r18 = com.itextpdf.layout.renderer.RotationUtils.retrieveRotatedLayoutWidth(r0, r8)
            r2 = r18
            goto L_0x0122
        L_0x0120:
            r2 = r18
        L_0x0122:
            if (r14 == 0) goto L_0x0127
            r15.startMarginsCollapse(r13)
        L_0x0127:
            com.itextpdf.layout.borders.Border[] r1 = r73.getBorders()
            com.itextpdf.layout.property.UnitValue[] r0 = r73.getPaddings()
            float r12 = r8.applyBordersPaddingsMargins(r13, r1, r0)
            r4 = r22
            r8.applyWidth(r13, r2, r4)
            r22 = 0
            r27 = r0
            r0 = r73
            r28 = r1
            r1 = r13
            r29 = r2
            r2 = r25
            r30 = r3
            r3 = r15
            r26 = r7
            r31 = r11
            r7 = 104(0x68, float:1.46E-43)
            r11 = r4
            r4 = r22
            r7 = r5
            r5 = r9
            r32 = r9
            r9 = r6
            r6 = r30
            boolean r26 = r0.applyMaxHeight(r1, r2, r3, r4, r5, r6)
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r0 = new com.itextpdf.layout.minmaxwidth.MinMaxWidth
            r0.<init>(r12)
            r6 = r0
            com.itextpdf.layout.renderer.MaxMaxWidthHandler r0 = new com.itextpdf.layout.renderer.MaxMaxWidthHandler
            r0.<init>(r6)
            r5 = r0
            if (r20 == 0) goto L_0x0170
            java.util.List r0 = java.util.Collections.singletonList(r13)
            r4 = r0
            goto L_0x017a
        L_0x0170:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            r0.<init>(r10, r13)
            java.util.List r0 = r8.initElementAreas(r0)
            r4 = r0
        L_0x017a:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.kernel.geom.Rectangle r1 = new com.itextpdf.kernel.geom.Rectangle
            float r2 = r13.getX()
            float r3 = r13.getY()
            float r33 = r13.getHeight()
            float r3 = r3 + r33
            r33 = r6
            float r6 = r13.getWidth()
            r34 = r12
            r12 = 0
            r1.<init>(r2, r3, r6, r12)
            r0.<init>(r10, r1)
            r8.occupiedArea = r0
            r73.shrinkOccupiedAreaForAbsolutePosition()
            r0 = 0
            r6 = 0
            java.lang.Object r1 = r4.get(r6)
            com.itextpdf.kernel.geom.Rectangle r1 = (com.itextpdf.kernel.geom.Rectangle) r1
            com.itextpdf.kernel.geom.Rectangle r1 = r1.clone()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r8.lines = r2
            java.util.List r2 = r8.childRenderers
            java.util.Iterator r2 = r2.iterator()
        L_0x01b9:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x01d8
            java.lang.Object r3 = r2.next()
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            if (r16 != 0) goto L_0x01d0
            boolean r36 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r3)
            if (r36 != 0) goto L_0x01ce
            goto L_0x01d0
        L_0x01ce:
            r12 = 0
            goto L_0x01d1
        L_0x01d0:
            r12 = 1
        L_0x01d1:
            r16 = r12
            r9.addChild(r3)
            r12 = 0
            goto L_0x01b9
        L_0x01d8:
            float r2 = r1.getY()
            float r3 = r1.getHeight()
            float r2 = r2 + r3
            r3 = 0
            r36 = 0
            r37 = 0
            java.util.ArrayList r38 = new java.util.ArrayList
            r38.<init>()
            r39 = r38
            r38 = 0
            java.util.HashSet r6 = new java.util.HashSet
            r6.<init>(r7)
            if (r14 == 0) goto L_0x0202
            java.util.List r12 = r8.childRenderers
            int r12 = r12.size()
            if (r12 <= 0) goto L_0x0202
            r12 = 0
            r15.startChildMarginsHandling(r12, r1)
        L_0x0202:
            boolean r12 = com.itextpdf.layout.renderer.BlockFormattingContextUtil.isRendererCreateBfc(r73)
            r43 = r37
            r37 = r17
            r17 = r2
            r2 = r9
            r9 = r0
            r72 = r3
            r3 = r1
            r1 = r38
            r38 = r36
            r36 = r23
            r23 = r72
        L_0x0219:
            r44 = r13
            if (r2 == 0) goto L_0x07db
            r0 = 67
            java.lang.Float r13 = r8.getPropertyAsFloat(r0)
            r2.setProperty(r0, r13)
            r0 = 69
            java.lang.Object r13 = r8.getProperty(r0)
            r2.setProperty(r0, r13)
            if (r37 == 0) goto L_0x0233
            r0 = 0
            goto L_0x023d
        L_0x0233:
            r0 = 18
            java.lang.Float r0 = r8.getPropertyAsFloat(r0)
            float r0 = r0.floatValue()
        L_0x023d:
            r13 = r0
            com.itextpdf.kernel.geom.Rectangle r0 = new com.itextpdf.kernel.geom.Rectangle
            r47 = r4
            float r4 = r3.getX()
            r48 = r6
            float r6 = r3.getY()
            r49 = r12
            float r12 = r3.getWidth()
            r50 = r15
            float r15 = r3.getHeight()
            r0.<init>(r4, r6, r12, r15)
            r12 = r0
            r15 = 103(0x67, float:1.44E-43)
            r2.setProperty(r15, r11)
            r6 = r30
            r4 = 104(0x68, float:1.46E-43)
            r2.setProperty(r4, r6)
            com.itextpdf.layout.layout.LineLayoutContext r0 = new com.itextpdf.layout.layout.LineLayoutContext
            com.itextpdf.layout.layout.LayoutArea r4 = new com.itextpdf.layout.layout.LayoutArea
            r4.<init>(r10, r12)
            if (r26 != 0) goto L_0x0276
            if (r32 == 0) goto L_0x0274
            goto L_0x0276
        L_0x0274:
            r15 = 0
            goto L_0x0277
        L_0x0276:
            r15 = 1
        L_0x0277:
            r30 = r6
            r6 = 0
            r0.<init>(r4, r6, r7, r15)
            com.itextpdf.layout.layout.LineLayoutContext r0 = r0.setTextIndent(r13)
            com.itextpdf.layout.layout.LineLayoutContext r15 = r0.setFloatOverflowedToNextPageWithNothing(r1)
            com.itextpdf.layout.renderer.IRenderer r0 = r2.setParent(r8)
            com.itextpdf.layout.renderer.LineRenderer r0 = (com.itextpdf.layout.renderer.LineRenderer) r0
            com.itextpdf.layout.layout.LayoutResult r0 = r0.layout(r15)
            r6 = r0
            com.itextpdf.layout.layout.LineLayoutResult r6 = (com.itextpdf.layout.layout.LineLayoutResult) r6
            int r0 = r6.getStatus()
            r4 = 3
            if (r0 != r4) goto L_0x02e6
            java.lang.Float r0 = com.itextpdf.layout.renderer.FloatingHelper.calculateLineShiftUnderFloats(r7, r3)
            if (r0 == 0) goto L_0x02b4
            float r4 = r0.floatValue()
            r3.decreaseHeight(r4)
            r36 = 1
            r13 = r44
            r4 = r47
            r6 = r48
            r12 = r49
            r15 = r50
            goto L_0x0219
        L_0x02b4:
            java.util.List r4 = r2.childRenderers
            boolean r4 = r4.isEmpty()
            r41 = 1
            r4 = r4 ^ 1
            r51 = r0
            java.util.List r0 = r2.childRenderers
            java.util.Iterator r0 = r0.iterator()
        L_0x02c6:
            boolean r52 = r0.hasNext()
            if (r52 == 0) goto L_0x02e2
            java.lang.Object r52 = r0.next()
            com.itextpdf.layout.renderer.IRenderer r52 = (com.itextpdf.layout.renderer.IRenderer) r52
            if (r4 == 0) goto L_0x02dd
            boolean r53 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r52)
            if (r53 == 0) goto L_0x02dd
            r53 = 1
            goto L_0x02df
        L_0x02dd:
            r53 = 0
        L_0x02df:
            r4 = r53
            goto L_0x02c6
        L_0x02e2:
            if (r4 == 0) goto L_0x02e6
            r43 = 1
        L_0x02e6:
            boolean r51 = r15.isFloatOverflowedToNextPageWithNothing()
            java.util.List r0 = r6.getFloatsOverflowedToNextPage()
            if (r0 == 0) goto L_0x02fa
            java.util.List r0 = r6.getFloatsOverflowedToNextPage()
            r4 = r39
            r4.addAll(r0)
            goto L_0x02fc
        L_0x02fa:
            r4 = r39
        L_0x02fc:
            r0 = 0
            r1 = 0
            r39 = r0
            boolean r0 = r6 instanceof com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            if (r0 == 0) goto L_0x031a
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r0 = r6.getMinMaxWidth()
            float r0 = r0.getMinWidth()
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r39 = r6.getMinMaxWidth()
            float r1 = r39.getMaxWidth()
            r72 = r1
            r1 = r0
            r0 = r72
            goto L_0x031d
        L_0x031a:
            r0 = r1
            r1 = r39
        L_0x031d:
            r5.updateMinChildWidth(r1)
            r5.updateMaxChildWidth(r0)
            r39 = 0
            r52 = r0
            int r0 = r6.getStatus()
            r53 = r10
            r10 = 1
            if (r0 != r10) goto L_0x0333
            r39 = r2
            goto L_0x0342
        L_0x0333:
            int r0 = r6.getStatus()
            r10 = 2
            if (r0 != r10) goto L_0x0342
            com.itextpdf.layout.renderer.IRenderer r0 = r6.getSplitRenderer()
            r39 = r0
            com.itextpdf.layout.renderer.LineRenderer r39 = (com.itextpdf.layout.renderer.LineRenderer) r39
        L_0x0342:
            if (r43 == 0) goto L_0x0349
            r39 = 0
            r10 = r39
            goto L_0x034b
        L_0x0349:
            r10 = r39
        L_0x034b:
            r0 = 70
            r39 = r1
            com.itextpdf.layout.property.TextAlignment r1 = com.itextpdf.layout.property.TextAlignment.LEFT
            java.lang.Object r0 = r8.getProperty(r0, r1)
            r54 = r0
            com.itextpdf.layout.property.TextAlignment r54 = (com.itextpdf.layout.property.TextAlignment) r54
            r45 = r52
            r1 = 26
            r0 = r73
            r52 = r12
            r12 = 26
            r1 = r54
            r55 = r2
            r2 = r6
            r56 = r3
            r3 = r10
            r12 = r4
            r57 = r47
            r47 = 104(0x68, float:1.46E-43)
            r4 = r56
            r58 = r5
            r5 = r7
            r59 = r15
            r60 = r33
            r15 = r48
            r33 = r6
            r6 = r43
            r48 = r12
            r12 = r7
            r7 = r13
            r0.applyTextAlignment(r1, r2, r3, r4, r5, r6, r7)
            com.itextpdf.layout.property.RenderingMode r0 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            r1 = 123(0x7b, float:1.72E-43)
            java.lang.Object r2 = r8.getProperty(r1)
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0396
            r0 = 0
            goto L_0x039e
        L_0x0396:
            r0 = 33
            java.lang.Object r0 = r8.getProperty(r0)
            com.itextpdf.layout.property.Leading r0 = (com.itextpdf.layout.property.Leading) r0
        L_0x039e:
            r6 = r0
            if (r10 == 0) goto L_0x03b4
            com.itextpdf.layout.layout.LayoutArea r0 = r10.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            float r0 = r0.getHeight()
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x03b4
            r0 = 1
            goto L_0x03b5
        L_0x03b4:
            r0 = 0
        L_0x03b5:
            r7 = r0
            if (r10 == 0) goto L_0x03ba
            r0 = 1
            goto L_0x03bb
        L_0x03ba:
            r0 = 0
        L_0x03bb:
            r1 = 0
            if (r0 == 0) goto L_0x0437
            com.itextpdf.layout.property.RenderingMode r2 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            r3 = 123(0x7b, float:1.72E-43)
            java.lang.Object r3 = r8.getProperty(r3)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0437
            if (r7 == 0) goto L_0x0408
            float r2 = r23 - r38
            if (r6 == 0) goto L_0x03d7
            float r3 = r10.getTopLeadingIndent(r6)
            goto L_0x03d8
        L_0x03d7:
            r3 = 0
        L_0x03d8:
            float r2 = r2 - r3
            float r3 = r10.getMaxAscent()
            float r2 = r2 - r3
            if (r10 == 0) goto L_0x03e8
            boolean r3 = r10.containsImage()
            if (r3 == 0) goto L_0x03e8
            float r2 = r2 + r23
        L_0x03e8:
            float r3 = r17 + r2
            float r4 = r10.getYLine()
            float r1 = r3 - r4
            if (r6 == 0) goto L_0x03f7
            float r3 = r10.getBottomLeadingIndent(r6)
            goto L_0x03f8
        L_0x03f7:
            r3 = 0
        L_0x03f8:
            r38 = r3
            r3 = 0
            int r4 = (r38 > r3 ? 1 : (r38 == r3 ? 0 : -1))
            if (r4 >= 0) goto L_0x0409
            boolean r4 = r10.containsImage()
            if (r4 == 0) goto L_0x0409
            r38 = 0
            goto L_0x0409
        L_0x0408:
            r3 = 0
        L_0x0409:
            if (r36 == 0) goto L_0x0417
            if (r10 == 0) goto L_0x0415
            if (r6 == 0) goto L_0x0415
            float r2 = r10.getTopLeadingIndent(r6)
            float r2 = -r2
            goto L_0x0416
        L_0x0415:
            r2 = 0
        L_0x0416:
            r1 = r2
        L_0x0417:
            if (r6 == 0) goto L_0x0431
            com.itextpdf.layout.layout.LayoutArea r2 = r10.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getY()
            float r2 = r2 + r1
            float r4 = r56.getY()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x042f
            goto L_0x0431
        L_0x042f:
            r2 = 0
            goto L_0x0432
        L_0x0431:
            r2 = 1
        L_0x0432:
            r0 = r2
            r35 = r0
            r5 = r1
            goto L_0x043b
        L_0x0437:
            r3 = 0
            r35 = r0
            r5 = r1
        L_0x043b:
            if (r35 != 0) goto L_0x0720
            if (r10 == 0) goto L_0x0463
            boolean r0 = isOverflowFit(r30)
            if (r0 == 0) goto L_0x0446
            goto L_0x0463
        L_0x0446:
            r61 = r9
            r63 = r13
            r69 = r27
            r67 = r28
            r62 = r30
            r65 = r31
            r64 = r50
            r27 = r55
            r13 = r56
            r0 = 0
            r3 = 0
            r9 = r5
            r28 = r14
            r56 = r48
            r14 = r60
            goto L_0x073b
        L_0x0463:
            int r0 = r9 + 1
            int r1 = r57.size()
            if (r0 >= r1) goto L_0x049e
            int r9 = r9 + 1
            r4 = r57
            java.lang.Object r0 = r4.get(r9)
            com.itextpdf.kernel.geom.Rectangle r0 = (com.itextpdf.kernel.geom.Rectangle) r0
            com.itextpdf.kernel.geom.Rectangle r0 = r0.clone()
            float r1 = r0.getY()
            float r2 = r0.getHeight()
            float r1 = r1 + r2
            r2 = 1
            r13 = r0
            r17 = r1
            r36 = r2
            r69 = r27
            r67 = r28
            r62 = r30
            r65 = r31
            r56 = r48
            r64 = r50
            r2 = r55
            r0 = 0
            r3 = 0
            r28 = r14
            r14 = r60
            goto L_0x07ba
        L_0x049e:
            r4 = r57
            boolean r18 = r73.isKeepTogether()
            if (r18 == 0) goto L_0x04cc
            r12.retainAll(r15)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r22 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 3
            r2 = 0
            r3 = 0
            com.itextpdf.layout.renderer.IRenderer r0 = r33.getCauseOfNothing()
            if (r0 != 0) goto L_0x04b7
            r40 = r8
            goto L_0x04bd
        L_0x04b7:
            com.itextpdf.layout.renderer.IRenderer r0 = r33.getCauseOfNothing()
            r40 = r0
        L_0x04bd:
            r0 = r22
            r57 = r4
            r4 = r73
            r61 = r9
            r9 = r5
            r5 = r40
            r0.<init>(r1, r2, r3, r4, r5)
            return r22
        L_0x04cc:
            r57 = r4
            r61 = r9
            r9 = r5
            if (r14 == 0) goto L_0x04e4
            if (r37 == 0) goto L_0x04df
            if (r16 == 0) goto L_0x04df
            r5 = r50
            r4 = r56
            r5.endChildMarginsHandling(r4)
            goto L_0x04e8
        L_0x04df:
            r5 = r50
            r4 = r56
            goto L_0x04e8
        L_0x04e4:
            r5 = r50
            r4 = r56
        L_0x04e8:
            if (r43 == 0) goto L_0x04ef
            if (r49 == 0) goto L_0x04ed
            goto L_0x04ef
        L_0x04ed:
            r0 = 0
            goto L_0x04f0
        L_0x04ef:
            r0 = 1
        L_0x04f0:
            r46 = r0
            if (r46 == 0) goto L_0x04fa
            com.itextpdf.layout.renderer.FloatingHelper.includeChildFloatsInOccupiedArea((java.util.List<com.itextpdf.kernel.geom.Rectangle>) r12, (com.itextpdf.layout.renderer.IRenderer) r8, (java.util.Set<com.itextpdf.kernel.geom.Rectangle>) r15)
            r8.fixOccupiedAreaIfOverflowedX(r11, r4)
        L_0x04fa:
            if (r14 == 0) goto L_0x04ff
            r5.endMarginsCollapse(r4)
        L_0x04ff:
            r0 = 0
            if (r46 != 0) goto L_0x0514
            r3 = r30
            com.itextpdf.layout.renderer.AbstractRenderer r1 = r8.applyMinHeight(r3, r4)
            if (r1 == 0) goto L_0x050c
            r2 = 1
            goto L_0x050d
        L_0x050c:
            r2 = 0
        L_0x050d:
            r0 = r2
            r73.applyVerticalAlignment()
            r30 = r0
            goto L_0x0518
        L_0x0514:
            r3 = r30
            r30 = r0
        L_0x0518:
            com.itextpdf.layout.renderer.ParagraphRenderer[] r47 = r73.split()
            r0 = 0
            r1 = r47[r0]
            java.util.List<com.itextpdf.layout.renderer.LineRenderer> r0 = r8.lines
            r1.lines = r0
            java.util.List<com.itextpdf.layout.renderer.LineRenderer> r0 = r8.lines
            java.util.Iterator r0 = r0.iterator()
        L_0x0529:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0546
            java.lang.Object r1 = r0.next()
            com.itextpdf.layout.renderer.LineRenderer r1 = (com.itextpdf.layout.renderer.LineRenderer) r1
            r50 = r0
            r2 = 0
            r0 = r47[r2]
            java.util.List r0 = r0.childRenderers
            java.util.List r2 = r1.getChildRenderers()
            r0.addAll(r2)
            r0 = r50
            goto L_0x0529
        L_0x0546:
            r0 = 1
            r1 = r47[r0]
            java.util.List r1 = r1.childRenderers
            r2 = r48
            r1.addAll(r2)
            if (r10 == 0) goto L_0x055d
            r1 = r47[r0]
            java.util.List r1 = r1.childRenderers
            java.util.List r0 = r10.getChildRenderers()
            r1.addAll(r0)
        L_0x055d:
            com.itextpdf.layout.renderer.IRenderer r0 = r33.getOverflowRenderer()
            if (r0 == 0) goto L_0x0573
            r0 = 1
            r1 = r47[r0]
            java.util.List r0 = r1.childRenderers
            com.itextpdf.layout.renderer.IRenderer r1 = r33.getOverflowRenderer()
            java.util.List r1 = r1.getChildRenderers()
            r0.addAll(r1)
        L_0x0573:
            if (r43 == 0) goto L_0x057f
            if (r49 != 0) goto L_0x057f
            if (r30 != 0) goto L_0x057f
            r0 = 1
            r1 = r47[r0]
            com.itextpdf.layout.renderer.FloatingHelper.removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(r1)
        L_0x057f:
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            float r0 = r0.getHeight()
            if (r46 != 0) goto L_0x05a8
            r48 = r0
            r1 = 2
            com.itextpdf.kernel.geom.Rectangle[] r0 = new com.itextpdf.kernel.geom.Rectangle[r1]
            r1 = 0
            r0[r1] = r4
            com.itextpdf.layout.layout.LayoutArea r1 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r41 = 1
            r0[r41] = r1
            com.itextpdf.kernel.geom.Rectangle r0 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r0)
            float r1 = r0.getHeight()
            r48 = r1
            goto L_0x05ac
        L_0x05a8:
            r48 = r0
            r41 = 1
        L_0x05ac:
            r50 = r47[r41]
            r0 = r73
            r1 = r48
            r56 = r2
            r2 = r26
            r62 = r3
            r3 = r73
            r63 = r13
            r13 = r4
            r4 = r50
            r64 = r5
            r5 = r46
            r0.updateHeightsOnSplit(r1, r2, r3, r4, r5)
            r8.correctFixedLayout(r13)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r5 = r27
            r1 = 1
            r8.applyPaddings(r0, r5, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r4 = r28
            r8.applyBorderBox(r0, r4, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r8.applyMargins(r0, r1)
            r73.applyAbsolutePositionIfNeeded(r74)
            java.util.List r0 = r74.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r1 = r74.getArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r3 = r31
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r8, r0, r1, r3, r14)
            if (r26 == 0) goto L_0x0615
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 0
            r1 = r47[r1]
            r31 = r3
            r28 = r4
            r3 = 0
            r4 = 1
            r0.<init>(r4, r2, r1, r3)
            r3 = r60
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r3)
            return r0
        L_0x0615:
            r31 = r3
            r28 = r4
            r3 = r60
            r1 = 0
            r4 = 1
            if (r37 == 0) goto L_0x0630
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = r47[r1]
            r4 = r47[r4]
            r27 = r5
            r5 = 2
            r0.<init>(r5, r2, r1, r4)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r3)
            return r0
        L_0x0630:
            r27 = r5
            r5 = 2
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r1 = 26
            java.lang.Boolean r1 = r8.getPropertyAsBoolean(r1)
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x06f6
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle[] r1 = new com.itextpdf.kernel.geom.Rectangle[r5]
            com.itextpdf.layout.layout.LayoutArea r4 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            r5 = 0
            r1[r5] = r4
            com.itextpdf.layout.layout.LayoutArea r4 = r55.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            r5 = 1
            r1[r5] = r4
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r1)
            r0.setBBox(r1)
            r8.fixOccupiedAreaIfOverflowedX(r11, r13)
            com.itextpdf.layout.renderer.IRenderer r0 = r8.parent
            r1 = 25
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r5)
            r0.setProperty(r1, r4)
            java.util.List<com.itextpdf.layout.renderer.LineRenderer> r0 = r8.lines
            r5 = r55
            r0.add(r5)
            int r0 = r33.getStatus()
            r1 = 2
            if (r1 != r0) goto L_0x06d5
            com.itextpdf.layout.renderer.IRenderer r4 = r33.getCauseOfNothing()
            java.util.List r0 = r5.childRenderers
            int r1 = r0.indexOf(r4)
            java.util.List r0 = r5.childRenderers
            r22 = r2
            java.util.List r2 = r5.childRenderers
            r60 = r3
            r3 = 0
            java.util.List r2 = r2.subList(r3, r1)
            r0.retainAll(r2)
            r0 = 1
            r2 = r47[r0]
            java.util.List r2 = r2.childRenderers
            r40 = r4
            r4 = r47[r0]
            java.util.List r4 = r4.childRenderers
            java.util.List r3 = r4.subList(r3, r1)
            r2.removeAll(r3)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r4 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r2 = 2
            r41 = r47[r0]
            r42 = 0
            r0 = r4
            r50 = r1
            r1 = r2
            r2 = r22
            r65 = r31
            r66 = r60
            r3 = r73
            r68 = r4
            r67 = r28
            r28 = r40
            r4 = r41
            r69 = r27
            r27 = r5
            r5 = r42
            r0.<init>(r1, r2, r3, r4, r5)
            r5 = r66
            r0 = r68
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r5)
            return r0
        L_0x06d5:
            r69 = r27
            r67 = r28
            r27 = r5
            r5 = r3
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r4 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 1
            r3 = 0
            r28 = 0
            r0 = r4
            r70 = r4
            r4 = r28
            r28 = r14
            r14 = r5
            r5 = r73
            r0.<init>(r1, r2, r3, r4, r5)
            r0 = r70
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r14)
            return r0
        L_0x06f6:
            r22 = r2
            r69 = r27
            r67 = r28
            r65 = r31
            r27 = r55
            r28 = r14
            r14 = r3
            r12.retainAll(r15)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r31 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 3
            r2 = 0
            r3 = 0
            com.itextpdf.layout.renderer.IRenderer r0 = r33.getCauseOfNothing()
            if (r0 != 0) goto L_0x0713
            r5 = r8
            goto L_0x0718
        L_0x0713:
            com.itextpdf.layout.renderer.IRenderer r0 = r33.getCauseOfNothing()
            r5 = r0
        L_0x0718:
            r0 = r31
            r4 = r73
            r0.<init>(r1, r2, r3, r4, r5)
            return r31
        L_0x0720:
            r61 = r9
            r63 = r13
            r69 = r27
            r67 = r28
            r62 = r30
            r65 = r31
            r64 = r50
            r27 = r55
            r13 = r56
            r0 = 0
            r3 = 0
            r9 = r5
            r28 = r14
            r56 = r48
            r14 = r60
        L_0x073b:
            if (r6 == 0) goto L_0x0746
            r10.applyLeading(r9)
            if (r7 == 0) goto L_0x0746
            float r17 = r10.getYLine()
        L_0x0746:
            if (r7 == 0) goto L_0x076b
            com.itextpdf.layout.layout.LayoutArea r1 = r8.occupiedArea
            r2 = 2
            com.itextpdf.kernel.geom.Rectangle[] r2 = new com.itextpdf.kernel.geom.Rectangle[r2]
            com.itextpdf.layout.layout.LayoutArea r4 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            r5 = 0
            r2[r5] = r4
            com.itextpdf.layout.layout.LayoutArea r4 = r10.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            r5 = 1
            r2[r5] = r4
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r2)
            r1.setBBox(r2)
            r8.fixOccupiedAreaIfOverflowedX(r11, r13)
        L_0x076b:
            r1 = 0
            com.itextpdf.layout.layout.LayoutArea r2 = r10.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getY()
            float r4 = r13.getY()
            float r2 = r2 - r4
            r13.setHeight(r2)
            java.util.List<com.itextpdf.layout.renderer.LineRenderer> r2 = r8.lines
            r2.add(r10)
            r2 = 1
            com.itextpdf.layout.renderer.IRenderer r4 = r33.getOverflowRenderer()
            com.itextpdf.layout.renderer.LineRenderer r4 = (com.itextpdf.layout.renderer.LineRenderer) r4
            float r5 = r10.getMaxDescent()
            boolean r22 = r56.isEmpty()
            if (r22 != 0) goto L_0x07b1
            com.itextpdf.layout.renderer.IRenderer r22 = r33.getOverflowRenderer()
            if (r22 != 0) goto L_0x07b1
            r22 = 1
            com.itextpdf.layout.renderer.LineRenderer r23 = new com.itextpdf.layout.renderer.LineRenderer
            r23.<init>()
            r4 = r23
            r36 = r1
            r37 = r2
            r2 = r4
            r23 = r5
            r43 = r22
            r9 = r61
            goto L_0x07ba
        L_0x07b1:
            r36 = r1
            r37 = r2
            r2 = r4
            r23 = r5
            r9 = r61
        L_0x07ba:
            r7 = r12
            r3 = r13
            r33 = r14
            r6 = r15
            r14 = r28
            r13 = r44
            r12 = r49
            r1 = r51
            r10 = r53
            r39 = r56
            r4 = r57
            r5 = r58
            r30 = r62
            r15 = r64
            r31 = r65
            r28 = r67
            r27 = r69
            goto L_0x0219
        L_0x07db:
            r13 = r3
            r57 = r4
            r58 = r5
            r61 = r9
            r53 = r10
            r49 = r12
            r64 = r15
            r69 = r27
            r67 = r28
            r62 = r30
            r65 = r31
            r56 = r39
            r27 = r2
            r15 = r6
            r12 = r7
            r28 = r14
            r14 = r33
            com.itextpdf.layout.property.RenderingMode r0 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            r2 = 123(0x7b, float:1.72E-43)
            java.lang.Object r2 = r8.getProperty(r2)
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0850
            r0 = r38
            boolean r2 = isOverflowFit(r62)
            if (r2 == 0) goto L_0x0833
            com.itextpdf.layout.layout.LayoutArea r2 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getY()
            float r3 = r13.getY()
            float r2 = r2 - r3
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0833
            com.itextpdf.layout.layout.LayoutArea r2 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getY()
            float r3 = r13.getY()
            float r0 = r2 - r3
        L_0x0833:
            com.itextpdf.layout.layout.LayoutArea r2 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r2.moveDown(r0)
            com.itextpdf.layout.layout.LayoutArea r2 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            com.itextpdf.layout.layout.LayoutArea r3 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            float r3 = r3.getHeight()
            float r3 = r3 + r0
            r2.setHeight(r3)
        L_0x0850:
            if (r28 == 0) goto L_0x0865
            java.util.List r0 = r8.childRenderers
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0862
            if (r16 == 0) goto L_0x0862
            r6 = r64
            r6.endChildMarginsHandling(r13)
            goto L_0x0867
        L_0x0862:
            r6 = r64
            goto L_0x0867
        L_0x0865:
            r6 = r64
        L_0x0867:
            if (r49 == 0) goto L_0x086f
            com.itextpdf.layout.renderer.FloatingHelper.includeChildFloatsInOccupiedArea((java.util.List<com.itextpdf.kernel.geom.Rectangle>) r12, (com.itextpdf.layout.renderer.IRenderer) r8, (java.util.Set<com.itextpdf.kernel.geom.Rectangle>) r15)
            r8.fixOccupiedAreaIfOverflowedX(r11, r13)
        L_0x086f:
            if (r26 == 0) goto L_0x0877
            r7 = r62
            r8.fixOccupiedAreaIfOverflowedY(r7, r13)
            goto L_0x0879
        L_0x0877:
            r7 = r62
        L_0x0879:
            if (r28 == 0) goto L_0x087e
            r6.endMarginsCollapse(r13)
        L_0x087e:
            com.itextpdf.layout.renderer.AbstractRenderer r9 = r8.applyMinHeight(r7, r13)
            if (r9 == 0) goto L_0x08a0
            boolean r0 = r73.isKeepTogether()
            if (r0 == 0) goto L_0x08a0
            r12.retainAll(r15)
            com.itextpdf.layout.layout.LayoutResult r10 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r4 = 0
            r0 = r10
            r51 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r73
            r5 = r73
            r0.<init>(r1, r2, r3, r4, r5)
            return r10
        L_0x08a0:
            r51 = r1
            r8.correctFixedLayout(r13)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r10 = r69
            r1 = 1
            r8.applyPaddings(r0, r10, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r5 = r67
            r8.applyBorderBox(r0, r5, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r8.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r8.applyMargins(r0, r1)
            r73.applyAbsolutePositionIfNeeded(r74)
            if (r24 == 0) goto L_0x093b
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.clone()
            r8.applyRotationLayout(r0)
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            boolean r0 = r8.isNotFittingLayoutArea(r0)
            if (r0 == 0) goto L_0x0938
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            boolean r0 = r8.isNotFittingWidth(r0)
            if (r0 == 0) goto L_0x0913
            com.itextpdf.layout.layout.LayoutArea r0 = r74.getArea()
            boolean r0 = r8.isNotFittingHeight(r0)
            if (r0 != 0) goto L_0x0913
            java.lang.Class r0 = r73.getClass()
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "It fits by height so it will be forced placed"
            r3 = 0
            r1[r3] = r2
            java.lang.String r2 = "Element does not fit current area. {0}"
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r1)
            r0.warn(r1)
            r22 = r5
            goto L_0x093d
        L_0x0913:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r1 = 26
            java.lang.Boolean r1 = r8.getPropertyAsBoolean(r1)
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0935
            r12.retainAll(r15)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r18 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 3
            r2 = 0
            r3 = 0
            r0 = r18
            r4 = r73
            r22 = r5
            r5 = r73
            r0.<init>(r1, r2, r3, r4, r5)
            return r18
        L_0x0935:
            r22 = r5
            goto L_0x093d
        L_0x0938:
            r22 = r5
            goto L_0x093d
        L_0x093b:
            r22 = r5
        L_0x093d:
            r73.applyVerticalAlignment()
            com.itextpdf.layout.renderer.FloatingHelper.removeFloatsAboveRendererBottom(r12, r8)
            java.util.List r0 = r74.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r1 = r74.getArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r5 = r28
            r4 = r65
            com.itextpdf.layout.layout.LayoutArea r18 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r8, r0, r1, r4, r5)
            if (r9 != 0) goto L_0x096f
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r63 = 1
            r65 = 0
            r66 = 0
            r67 = 0
            r62 = r0
            r64 = r18
            r62.<init>(r63, r64, r65, r66, r67)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r14)
            return r0
        L_0x096f:
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r3 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 2
            r28 = 0
            r0 = r3
            r2 = r18
            r71 = r3
            r3 = r73
            r30 = r4
            r4 = r9
            r31 = r5
            r5 = r28
            r0.<init>(r1, r2, r3, r4, r5)
            r0 = r71
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r0 = r0.setMinMaxWidth(r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.ParagraphRenderer.directLayout(com.itextpdf.layout.layout.LayoutContext):com.itextpdf.layout.layout.LayoutResult");
    }

    public IRenderer getNextRenderer() {
        return new ParagraphRenderer((Paragraph) this.modelElement);
    }

    public <T1> T1 getDefaultProperty(int property) {
        if ((property == 46 || property == 43) && (this.parent instanceof CellRenderer)) {
            return UnitValue.createPointValue(0.0f);
        }
        return super.getDefaultProperty(property);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<LineRenderer> list = this.lines;
        if (list == null || list.size() <= 0) {
            for (IRenderer renderer : this.childRenderers) {
                sb.append(renderer.toString());
            }
        } else {
            for (int i = 0; i < this.lines.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(this.lines.get(i).toString());
            }
        }
        return sb.toString();
    }

    public void drawChildren(DrawContext drawContext) {
        List<LineRenderer> list = this.lines;
        if (list != null) {
            for (LineRenderer line : list) {
                line.draw(drawContext);
            }
        }
    }

    public void move(float dxRight, float dyUp) {
        this.occupiedArea.getBBox().moveRight(dxRight);
        this.occupiedArea.getBBox().moveUp(dyUp);
        List<LineRenderer> list = this.lines;
        if (list != null) {
            for (LineRenderer line : list) {
                line.move(dxRight, dyUp);
            }
        }
    }

    public List<LineRenderer> getLines() {
        return this.lines;
    }

    /* access modifiers changed from: protected */
    public Float getFirstYLineRecursively() {
        List<LineRenderer> list = this.lines;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.lines.get(0).getFirstYLineRecursively();
    }

    /* access modifiers changed from: protected */
    public Float getLastYLineRecursively() {
        List<LineRenderer> list;
        if (!allowLastYLineRecursiveExtraction() || (list = this.lines) == null || list.size() == 0) {
            return null;
        }
        for (int i = this.lines.size() - 1; i >= 0; i--) {
            Float yLine = this.lines.get(i).getLastYLineRecursively();
            if (yLine != null) {
                return yLine;
            }
        }
        return null;
    }

    private ParagraphRenderer createOverflowRenderer() {
        return (ParagraphRenderer) getNextRenderer();
    }

    private ParagraphRenderer createSplitRenderer() {
        return (ParagraphRenderer) getNextRenderer();
    }

    /* access modifiers changed from: protected */
    public ParagraphRenderer createOverflowRenderer(IRenderer parent) {
        ParagraphRenderer overflowRenderer = createOverflowRenderer();
        overflowRenderer.parent = parent;
        fixOverflowRenderer(overflowRenderer);
        overflowRenderer.addAllProperties(getOwnProperties());
        return overflowRenderer;
    }

    /* access modifiers changed from: protected */
    public ParagraphRenderer createSplitRenderer(IRenderer parent) {
        ParagraphRenderer splitRenderer = createSplitRenderer();
        splitRenderer.parent = parent;
        splitRenderer.addAllProperties(getOwnProperties());
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createOverflowRenderer(int layoutResult) {
        return createOverflowRenderer(this.parent);
    }

    public MinMaxWidth getMinMaxWidth() {
        MinMaxWidth minMaxWidth = new MinMaxWidth();
        Float rotation = getPropertyAsFloat(55);
        if (!setMinMaxWidthBasedOnFixedWidth(minMaxWidth)) {
            Float minWidth = hasAbsoluteUnitValue(80) ? retrieveMinWidth(0.0f) : null;
            Float maxWidth = hasAbsoluteUnitValue(79) ? retrieveMaxWidth(0.0f) : null;
            if (minWidth == null || maxWidth == null) {
                boolean restoreRotation = hasOwnProperty(55);
                setProperty(55, (Object) null);
                MinMaxWidthLayoutResult result = (MinMaxWidthLayoutResult) layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0f))));
                if (restoreRotation) {
                    setProperty(55, rotation);
                } else {
                    deleteOwnProperty(55);
                }
                minMaxWidth = result.getMinMaxWidth();
            }
            if (minWidth != null) {
                minMaxWidth.setChildrenMinWidth(minWidth.floatValue());
            }
            if (maxWidth != null) {
                minMaxWidth.setChildrenMaxWidth(maxWidth.floatValue());
            }
            if (minMaxWidth.getChildrenMinWidth() > minMaxWidth.getChildrenMaxWidth()) {
                minMaxWidth.setChildrenMaxWidth(minMaxWidth.getChildrenMaxWidth());
            }
        } else {
            minMaxWidth.setAdditionalWidth(calculateAdditionalWidth(this));
        }
        return rotation != null ? RotationUtils.countRotationMinMaxWidth(minMaxWidth, this) : minMaxWidth;
    }

    /* access modifiers changed from: protected */
    public ParagraphRenderer[] split() {
        ParagraphRenderer splitRenderer = createSplitRenderer(this.parent);
        splitRenderer.occupiedArea = this.occupiedArea;
        splitRenderer.isLastRendererForModelElement = false;
        return new ParagraphRenderer[]{splitRenderer, createOverflowRenderer(this.parent)};
    }

    private void fixOverflowRenderer(ParagraphRenderer overflowRenderer) {
        if (overflowRenderer.getPropertyAsFloat(18).floatValue() != 0.0f) {
            overflowRenderer.setProperty(18, Float.valueOf(0.0f));
        }
    }

    private void alignStaticKids(LineRenderer renderer, float dxRight) {
        renderer.getOccupiedArea().getBBox().moveRight(dxRight);
        for (IRenderer childRenderer : renderer.getChildRenderers()) {
            if (!FloatingHelper.isRendererFloating(childRenderer)) {
                childRenderer.move(dxRight, 0.0f);
            }
        }
    }

    private void applyTextAlignment(TextAlignment textAlignment, LineLayoutResult result, LineRenderer processedRenderer, Rectangle layoutBox, List<Rectangle> floatRendererAreas, boolean onlyOverflowedFloatsLeft, float lineIndent) {
        if ((textAlignment != TextAlignment.JUSTIFIED || result.getStatus() != 2 || result.isSplitForcedByNewline() || onlyOverflowedFloatsLeft) && textAlignment != TextAlignment.JUSTIFIED_ALL) {
            if (textAlignment != TextAlignment.LEFT && processedRenderer != null) {
                Rectangle actualLineLayoutBox = layoutBox.clone();
                FloatingHelper.adjustLineAreaAccordingToFloats(floatRendererAreas, actualLineLayoutBox);
                float deltaX = Math.max(0.0f, (actualLineLayoutBox.getWidth() - lineIndent) - processedRenderer.getOccupiedArea().getBBox().getWidth());
                switch (C14751.$SwitchMap$com$itextpdf$layout$property$TextAlignment[textAlignment.ordinal()]) {
                    case 1:
                        alignStaticKids(processedRenderer, deltaX);
                        return;
                    case 2:
                        alignStaticKids(processedRenderer, deltaX / 2.0f);
                        return;
                    case 3:
                        if (BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7))) {
                            alignStaticKids(processedRenderer, deltaX);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        } else if (processedRenderer != null) {
            Rectangle actualLineLayoutBox2 = layoutBox.clone();
            FloatingHelper.adjustLineAreaAccordingToFloats(floatRendererAreas, actualLineLayoutBox2);
            processedRenderer.justify(actualLineLayoutBox2.getWidth() - lineIndent);
        }
    }

    /* renamed from: com.itextpdf.layout.renderer.ParagraphRenderer$1 */
    static /* synthetic */ class C14751 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$TextAlignment;

        static {
            int[] iArr = new int[TextAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$TextAlignment = iArr;
            try {
                iArr[TextAlignment.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.JUSTIFIED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}

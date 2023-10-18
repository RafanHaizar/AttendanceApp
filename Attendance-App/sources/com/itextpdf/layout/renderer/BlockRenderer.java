package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public abstract class BlockRenderer extends AbstractRenderer {
    protected BlockRenderer(IElement modelElement) {
        super(modelElement);
    }

    /* JADX WARNING: Removed duplicated region for block: B:118:0x0427  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0436  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0459  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x04de  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x05d3  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x04eb A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x03c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.layout.LayoutResult layout(com.itextpdf.layout.layout.LayoutContext r59) {
        /*
            r58 = this;
            r10 = r58
            r11 = 1
            r10.isLastRendererForModelElement = r11
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r12 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r13 = r0
            r7 = 0
            r8 = 0
            boolean r14 = r59.isClippedHeight()
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            int r15 = r0.getPageNumber()
            boolean r16 = r58.isPositioned()
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.kernel.geom.Rectangle r9 = r0.clone()
            java.util.List r6 = r59.getFloatRendererAreas()
            r0 = 99
            java.lang.Object r0 = r10.getProperty(r0)
            r5 = r0
            com.itextpdf.layout.property.FloatPropertyValue r5 = (com.itextpdf.layout.property.FloatPropertyValue) r5
            r0 = 55
            java.lang.Float r17 = r10.getPropertyAsFloat(r0)
            r0 = 103(0x67, float:1.44E-43)
            java.lang.Object r0 = r10.getProperty(r0)
            r4 = r0
            com.itextpdf.layout.property.OverflowPropertyValue r4 = (com.itextpdf.layout.property.OverflowPropertyValue) r4
            r0 = 0
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r2 = 89
            java.lang.Boolean r2 = r10.getPropertyAsBoolean(r2)
            boolean r3 = r1.equals(r2)
            if (r3 == 0) goto L_0x0066
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r1 = new com.itextpdf.layout.margincollapse.MarginsCollapseHandler
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r2 = r59.getMarginsCollapseInfo()
            r1.<init>(r10, r2)
            r0 = r1
            r2 = r0
            goto L_0x0067
        L_0x0066:
            r2 = r0
        L_0x0067:
            float r0 = r9.getWidth()
            java.lang.Float r0 = r10.retrieveWidth(r0)
            if (r17 != 0) goto L_0x0077
            boolean r1 = r58.isFixedLayout()
            if (r1 == 0) goto L_0x0087
        L_0x0077:
            float r1 = r9.getHeight()
            r11 = 1232348160(0x49742400, float:1000000.0)
            float r1 = r11 - r1
            com.itextpdf.kernel.geom.Rectangle r1 = r9.moveDown(r1)
            r1.setHeight(r11)
        L_0x0087:
            if (r17 == 0) goto L_0x0099
            boolean r1 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r10, r5)
            if (r1 != 0) goto L_0x0099
            float r1 = r9.getWidth()
            java.lang.Float r0 = com.itextpdf.layout.renderer.RotationUtils.retrieveRotatedLayoutWidth(r1, r10)
            r11 = r0
            goto L_0x009a
        L_0x0099:
            r11 = r0
        L_0x009a:
            boolean r19 = com.itextpdf.layout.renderer.BlockFormattingContextUtil.isRendererCreateBfc(r58)
            float r1 = com.itextpdf.layout.renderer.FloatingHelper.calculateClearHeightCorrection(r10, r6, r9)
            boolean r0 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r58)
            com.itextpdf.layout.renderer.FloatingHelper.applyClearance(r9, r2, r1, r0)
            boolean r0 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r10, r5)
            if (r0 == 0) goto L_0x00d0
            r0 = r58
            r20 = r8
            r8 = r1
            r1 = r9
            r21 = r7
            r7 = r2
            r2 = r11
            r22 = r11
            r11 = r3
            r3 = r6
            r23 = r4
            r4 = r5
            r24 = r5
            r5 = r23
            java.lang.Float r0 = com.itextpdf.layout.renderer.FloatingHelper.adjustFloatedBlockLayoutBox(r0, r1, r2, r3, r4, r5)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6 = r1
            r5 = r0
            goto L_0x00df
        L_0x00d0:
            r23 = r4
            r24 = r5
            r21 = r7
            r20 = r8
            r22 = r11
            r8 = r1
            r7 = r2
            r11 = r3
            r5 = r22
        L_0x00df:
            boolean r4 = r10 instanceof com.itextpdf.layout.renderer.CellRenderer
            if (r11 == 0) goto L_0x00e6
            r7.startMarginsCollapse(r9)
        L_0x00e6:
            com.itextpdf.layout.borders.Border[] r3 = r58.getBorders()
            com.itextpdf.layout.property.UnitValue[] r2 = r58.getPaddings()
            r10.applyBordersPaddingsMargins(r9, r3, r2)
            java.lang.Float r22 = r58.retrieveMaxHeight()
            if (r22 == 0) goto L_0x0103
            float r0 = r22.floatValue()
            float r1 = r9.getHeight()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0108
        L_0x0103:
            if (r14 != 0) goto L_0x0108
            com.itextpdf.layout.property.OverflowPropertyValue r0 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            goto L_0x0110
        L_0x0108:
            r0 = 104(0x68, float:1.46E-43)
            java.lang.Object r0 = r10.getProperty(r0)
            com.itextpdf.layout.property.OverflowPropertyValue r0 = (com.itextpdf.layout.property.OverflowPropertyValue) r0
        L_0x0110:
            r1 = r0
            r0 = r23
            r10.applyWidth(r9, r5, r0)
            r23 = r8
            r8 = r0
            r0 = r58
            r25 = r1
            r1 = r9
            r26 = r2
            r2 = r22
            r27 = r3
            r3 = r7
            r28 = r4
            r29 = r5
            r5 = r14
            r30 = r14
            r14 = r6
            r6 = r25
            boolean r6 = r0.applyMaxHeight(r1, r2, r3, r4, r5, r6)
            if (r16 == 0) goto L_0x013b
            java.util.List r0 = java.util.Collections.singletonList(r9)
            r5 = r0
            goto L_0x0145
        L_0x013b:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            r0.<init>(r15, r9)
            java.util.List r0 = r10.initElementAreas(r0)
            r5 = r0
        L_0x0145:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.kernel.geom.Rectangle r1 = new com.itextpdf.kernel.geom.Rectangle
            float r2 = r9.getX()
            float r3 = r9.getY()
            float r4 = r9.getHeight()
            float r3 = r3 + r4
            float r4 = r9.getWidth()
            r20 = r9
            r9 = 0
            r1.<init>(r2, r3, r4, r9)
            r0.<init>(r15, r1)
            r10.occupiedArea = r0
            r58.shrinkOccupiedAreaForAbsolutePosition()
            r0 = 0
            r9 = 0
            java.lang.Object r1 = r5.get(r9)
            com.itextpdf.kernel.geom.Rectangle r1 = (com.itextpdf.kernel.geom.Rectangle) r1
            com.itextpdf.kernel.geom.Rectangle r1 = r1.clone()
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>(r14)
            r4 = r2
            r2 = 0
            r3 = 0
            r31 = 0
            r37 = r2
            r38 = r3
            r39 = r21
            r21 = r0
            r2 = r1
            r1 = r31
        L_0x0189:
            java.util.List r0 = r10.childRenderers
            int r0 = r0.size()
            if (r1 >= r0) goto L_0x0828
            java.util.List r0 = r10.childRenderers
            java.lang.Object r0 = r0.get(r1)
            com.itextpdf.layout.renderer.IRenderer r0 = (com.itextpdf.layout.renderer.IRenderer) r0
            r0.setParent(r10)
            r34 = 0
            r9 = 0
            if (r39 == 0) goto L_0x01ca
            boolean r36 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r0)
            if (r36 == 0) goto L_0x01ca
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r12.put(r3, r9)
            r13.add(r0)
            r47 = r1
            r50 = r5
            r1 = r6
            r5 = r7
            r6 = r8
            r48 = r12
            r0 = r14
            r40 = r20
            r14 = r23
            r8 = r26
            r51 = r27
            r31 = 0
            r7 = r4
            r20 = r15
            goto L_0x080e
        L_0x01ca:
            boolean r36 = r13.isEmpty()
            if (r36 != 0) goto L_0x02f4
            r3 = 100
            java.lang.Object r3 = r0.getProperty(r3)
            com.itextpdf.layout.property.ClearPropertyValue r3 = (com.itextpdf.layout.property.ClearPropertyValue) r3
            boolean r3 = com.itextpdf.layout.renderer.FloatingHelper.isClearanceApplied(r13, r3)
            if (r3 == 0) goto L_0x02f4
            boolean r3 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r0)
            if (r3 == 0) goto L_0x020a
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r12.put(r3, r9)
            r13.add(r0)
            r3 = 1
            r47 = r1
            r39 = r3
            r50 = r5
            r1 = r6
            r5 = r7
            r6 = r8
            r48 = r12
            r0 = r14
            r40 = r20
            r14 = r23
            r8 = r26
            r51 = r27
            r31 = 0
            r7 = r4
            r20 = r15
            goto L_0x080e
        L_0x020a:
            if (r11 == 0) goto L_0x0211
            if (r28 != 0) goto L_0x0211
            r7.endMarginsCollapse(r2)
        L_0x0211:
            com.itextpdf.layout.renderer.FloatingHelper.includeChildFloatsInOccupiedArea((java.util.List<com.itextpdf.kernel.geom.Rectangle>) r14, (com.itextpdf.layout.renderer.IRenderer) r10, (java.util.Set<com.itextpdf.kernel.geom.Rectangle>) r4)
            r10.fixOccupiedAreaIfOverflowedX(r8, r2)
            com.itextpdf.layout.layout.LayoutResult r3 = new com.itextpdf.layout.layout.LayoutResult
            r40 = r1
            r1 = 3
            r3.<init>(r1, r9, r9, r0)
            if (r38 == 0) goto L_0x0223
            r9 = 2
            goto L_0x0224
        L_0x0223:
            r9 = 3
        L_0x0224:
            r1 = r0
            r0 = r58
            r47 = r40
            r40 = r8
            r8 = r1
            r1 = r47
            r48 = r15
            r15 = r2
            r2 = r9
            r49 = r7
            r7 = r4
            r4 = r12
            r50 = r5
            r5 = r13
            com.itextpdf.layout.renderer.AbstractRenderer[] r0 = r0.createSplitAndOverflowRenderers(r1, r2, r3, r4, r5)
            r1 = 0
            r1 = r0[r1]
            r2 = 1
            r4 = r0[r2]
            r10.updateHeightsOnSplit(r6, r1, r4)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r18 = r0
            r0 = r26
            r10.applyPaddings(r5, r0, r2)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r0 = r27
            r10.applyBorderBox(r5, r0, r2)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r10.applyMargins(r5, r2)
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r5 = 26
            java.lang.Boolean r5 = r10.getPropertyAsBoolean(r5)
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x02ce
            if (r6 == 0) goto L_0x027c
            r27 = r0
            r0 = r23
            goto L_0x02d2
        L_0x027c:
            r2 = 3
            if (r9 == r2) goto L_0x02af
            java.util.List r2 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r5 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r27 = r0
            r0 = r23
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r2, r5, r0, r11)
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r46 = 0
            r41 = r5
            r42 = r9
            r43 = r2
            r44 = r1
            r45 = r4
            r41.<init>(r42, r43, r44, r45, r46)
            r23 = r2
            com.itextpdf.layout.element.AreaBreak r2 = r3.getAreaBreak()
            com.itextpdf.layout.layout.LayoutResult r2 = r5.setAreaBreak(r2)
            return r2
        L_0x02af:
            r14.retainAll(r7)
            com.itextpdf.layout.layout.LayoutResult r2 = new com.itextpdf.layout.layout.LayoutResult
            r43 = 0
            r44 = 0
            com.itextpdf.layout.renderer.IRenderer r46 = r3.getCauseOfNothing()
            r41 = r2
            r42 = r9
            r45 = r4
            r41.<init>(r42, r43, r44, r45, r46)
            com.itextpdf.layout.element.AreaBreak r5 = r3.getAreaBreak()
            com.itextpdf.layout.layout.LayoutResult r2 = r2.setAreaBreak(r5)
            return r2
        L_0x02ce:
            r27 = r0
            r0 = r23
        L_0x02d2:
            java.util.List r2 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r5 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r2, r5, r0, r11)
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r42 = 1
            r45 = 0
            r46 = 0
            r41 = r5
            r43 = r2
            r44 = r1
            r41.<init>(r42, r43, r44, r45, r46)
            return r5
        L_0x02f4:
            r47 = r1
            r50 = r5
            r49 = r7
            r40 = r8
            r48 = r15
            r8 = r0
            r15 = r2
            r7 = r4
            r0 = r23
            if (r11 == 0) goto L_0x030e
            r5 = r49
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r34 = r5.startChildMarginsHandling(r8, r15)
            r4 = r34
            goto L_0x0312
        L_0x030e:
            r5 = r49
            r4 = r34
        L_0x0312:
            com.itextpdf.layout.renderer.IRenderer r1 = r8.setParent(r10)
            com.itextpdf.layout.layout.LayoutContext r2 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r3 = new com.itextpdf.layout.layout.LayoutArea
            r9 = r48
            r3.<init>(r9, r15)
            if (r6 != 0) goto L_0x0328
            if (r30 == 0) goto L_0x0324
            goto L_0x0328
        L_0x0324:
            r34 = r0
            r0 = 0
            goto L_0x032b
        L_0x0328:
            r34 = r0
            r0 = 1
        L_0x032b:
            r2.<init>(r3, r4, r14, r0)
            com.itextpdf.layout.layout.LayoutResult r0 = r1.layout(r2)
            r1 = 86
            r3 = r0
            int r0 = r0.getStatus()
            r2 = 1
            if (r0 == r2) goto L_0x075e
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r2 = 87
            java.lang.Boolean r2 = r10.getPropertyAsBoolean(r2)
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0395
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            java.lang.Boolean r2 = r10.getPropertyAsBoolean(r1)
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0359
            r2 = r40
            goto L_0x0397
        L_0x0359:
            com.itextpdf.layout.layout.LayoutArea r0 = r3.getOccupiedArea()
            if (r0 == 0) goto L_0x0390
            int r0 = r3.getStatus()
            r2 = 3
            if (r0 == r2) goto L_0x0390
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            r2 = 2
            com.itextpdf.kernel.geom.Rectangle[] r1 = new com.itextpdf.kernel.geom.Rectangle[r2]
            com.itextpdf.layout.layout.LayoutArea r2 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r31 = 0
            r1[r31] = r2
            com.itextpdf.layout.layout.LayoutArea r2 = r3.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r18 = 1
            r1[r18] = r2
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r1)
            r0.setBBox(r1)
            r2 = r40
            r10.fixOccupiedAreaIfOverflowedX(r2, r15)
            r40 = r4
            goto L_0x03b2
        L_0x0390:
            r2 = r40
            r40 = r4
            goto L_0x03b2
        L_0x0395:
            r2 = r40
        L_0x0397:
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            r40 = r4
            r1 = 2
            com.itextpdf.kernel.geom.Rectangle[] r4 = new com.itextpdf.kernel.geom.Rectangle[r1]
            com.itextpdf.layout.layout.LayoutArea r1 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r31 = 0
            r4[r31] = r1
            r1 = 1
            r4[r1] = r15
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r4)
            r0.setBBox(r1)
        L_0x03b2:
            if (r11 == 0) goto L_0x03be
            int r0 = r3.getStatus()
            r1 = 3
            if (r0 == r1) goto L_0x03be
            r5.endChildMarginsHandling(r15)
        L_0x03be:
            boolean r0 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r8)
            if (r0 == 0) goto L_0x0425
            int r0 = r3.getStatus()
            r1 = 3
            if (r0 != r1) goto L_0x03db
            if (r38 != 0) goto L_0x03db
            boolean r0 = r14.isEmpty()
            if (r0 == 0) goto L_0x03db
            boolean r0 = r58.isFirstOnRootArea()
            if (r0 == 0) goto L_0x03db
            r0 = 1
            goto L_0x03dc
        L_0x03db:
            r0 = 0
        L_0x03dc:
            if (r0 != 0) goto L_0x0423
            java.lang.Integer r1 = java.lang.Integer.valueOf(r47)
            int r4 = r3.getStatus()
            r41 = r0
            r0 = 2
            if (r4 != r0) goto L_0x03f0
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getSplitRenderer()
            goto L_0x03f1
        L_0x03f0:
            r0 = 0
        L_0x03f1:
            r12.put(r1, r0)
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getOverflowRenderer()
            r13.add(r0)
            int r0 = r3.getStatus()
            r1 = 3
            if (r0 != r1) goto L_0x0404
            r0 = 1
            goto L_0x0405
        L_0x0404:
            r0 = 0
        L_0x0405:
            r39 = r0
            r54 = r2
            r49 = r5
            r1 = r6
            r48 = r12
            r0 = r14
            r2 = r15
            r51 = r27
            r14 = r34
            r34 = r40
            r31 = 0
            r27 = r8
            r40 = r20
            r8 = r26
            r20 = r9
            r9 = 3
            goto L_0x077a
        L_0x0423:
            r41 = r0
        L_0x0425:
            if (r11 == 0) goto L_0x042a
            r5.endMarginsCollapse(r15)
        L_0x042a:
            com.itextpdf.layout.renderer.FloatingHelper.includeChildFloatsInOccupiedArea((java.util.List<com.itextpdf.kernel.geom.Rectangle>) r14, (com.itextpdf.layout.renderer.IRenderer) r10, (java.util.Set<com.itextpdf.kernel.geom.Rectangle>) r7)
            r10.fixOccupiedAreaIfOverflowedX(r2, r15)
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getSplitRenderer()
            if (r0 == 0) goto L_0x0443
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getSplitRenderer()
            com.itextpdf.layout.layout.LayoutArea r1 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r10.alignChildHorizontally(r0, r1)
        L_0x0443:
            if (r37 != 0) goto L_0x0451
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getCauseOfNothing()
            if (r0 == 0) goto L_0x0451
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getCauseOfNothing()
            r37 = r0
        L_0x0451:
            int r0 = r21 + 1
            int r1 = r50.size()
            if (r0 >= r1) goto L_0x04de
            com.itextpdf.layout.element.AreaBreak r0 = r3.getAreaBreak()
            if (r0 == 0) goto L_0x0474
            com.itextpdf.layout.element.AreaBreak r0 = r3.getAreaBreak()
            com.itextpdf.layout.property.AreaBreakType r0 = r0.getType()
            com.itextpdf.layout.property.AreaBreakType r1 = com.itextpdf.layout.property.AreaBreakType.NEXT_PAGE
            if (r0 == r1) goto L_0x046c
            goto L_0x0474
        L_0x046c:
            r41 = r2
            r4 = r47
            r2 = r50
            goto L_0x04e4
        L_0x0474:
            int r0 = r3.getStatus()
            r1 = 2
            if (r0 != r1) goto L_0x0495
            java.util.List r0 = r10.childRenderers
            com.itextpdf.layout.renderer.IRenderer r1 = r3.getSplitRenderer()
            r4 = r47
            r0.set(r4, r1)
            java.util.List r0 = r10.childRenderers
            int r1 = r4 + 1
            r41 = r2
            com.itextpdf.layout.renderer.IRenderer r2 = r3.getOverflowRenderer()
            r0.add(r1, r2)
            r1 = r4
            goto L_0x04b0
        L_0x0495:
            r41 = r2
            r4 = r47
            com.itextpdf.layout.renderer.IRenderer r0 = r3.getOverflowRenderer()
            if (r0 == 0) goto L_0x04a9
            java.util.List r0 = r10.childRenderers
            com.itextpdf.layout.renderer.IRenderer r1 = r3.getOverflowRenderer()
            r0.set(r4, r1)
            goto L_0x04ae
        L_0x04a9:
            java.util.List r0 = r10.childRenderers
            r0.remove(r4)
        L_0x04ae:
            int r1 = r4 + -1
        L_0x04b0:
            int r0 = r21 + 1
            r2 = r50
            java.lang.Object r4 = r2.get(r0)
            com.itextpdf.kernel.geom.Rectangle r4 = (com.itextpdf.kernel.geom.Rectangle) r4
            com.itextpdf.kernel.geom.Rectangle r4 = r4.clone()
            r21 = r0
            r47 = r1
            r2 = r4
            r49 = r5
            r1 = r6
            r48 = r12
            r0 = r14
            r51 = r27
            r14 = r34
            r34 = r40
            r54 = r41
            r31 = 0
            r27 = r8
            r40 = r20
            r8 = r26
            r20 = r9
            r9 = 3
            goto L_0x077a
        L_0x04de:
            r41 = r2
            r4 = r47
            r2 = r50
        L_0x04e4:
            int r0 = r3.getStatus()
            r1 = 2
            if (r0 != r1) goto L_0x05d3
            int r0 = r21 + 1
            int r1 = r2.size()
            if (r0 != r1) goto L_0x0576
            r33 = 2
            r52 = r26
            r51 = r27
            r1 = r34
            r0 = r58
            r26 = r8
            r8 = r1
            r1 = r4
            r47 = r14
            r27 = r41
            r14 = r2
            r2 = r33
            r53 = r4
            r34 = r40
            r4 = r12
            r40 = r5
            r5 = r13
            com.itextpdf.layout.renderer.AbstractRenderer[] r0 = r0.createSplitAndOverflowRenderers(r1, r2, r3, r4, r5)
            r1 = 0
            r1 = r0[r1]
            r2 = 1
            r4 = r0[r2]
            r5 = 26
            r4.deleteOwnProperty(r5)
            r10.updateHeightsOnSplit(r6, r1, r4)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r31 = r0
            r0 = r52
            r10.applyPaddings(r5, r0, r2)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r0 = r51
            r10.applyBorderBox(r5, r0, r2)
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r10.applyMargins(r5, r2)
            r10.correctFixedLayout(r15)
            java.util.List r2 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r5 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r2, r5, r8, r11)
            if (r6 == 0) goto L_0x0564
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r32 = r6
            r23 = r7
            r6 = 0
            r7 = 1
            r5.<init>(r7, r2, r1, r6)
            return r5
        L_0x0564:
            com.itextpdf.layout.layout.LayoutResult r5 = new com.itextpdf.layout.layout.LayoutResult
            r42 = 2
            r41 = r5
            r43 = r2
            r44 = r1
            r45 = r4
            r46 = r37
            r41.<init>(r42, r43, r44, r45, r46)
            return r5
        L_0x0576:
            r53 = r4
            r32 = r6
            r23 = r7
            r47 = r14
            r52 = r26
            r0 = r27
            r27 = r41
            r1 = 0
            r14 = r2
            r26 = r8
            r8 = r34
            r34 = r40
            r40 = r5
            java.util.List r2 = r10.childRenderers
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getSplitRenderer()
            r7 = r53
            r2.set(r7, r4)
            java.util.List r2 = r10.childRenderers
            int r4 = r7 + 1
            com.itextpdf.layout.renderer.IRenderer r5 = r3.getOverflowRenderer()
            r2.add(r4, r5)
            int r2 = r21 + 1
            java.lang.Object r4 = r14.get(r2)
            com.itextpdf.kernel.geom.Rectangle r4 = (com.itextpdf.kernel.geom.Rectangle) r4
            com.itextpdf.kernel.geom.Rectangle r4 = r4.clone()
            r51 = r0
            r21 = r2
            r2 = r4
            r48 = r12
            r50 = r14
            r54 = r27
            r1 = r32
            r49 = r40
            r0 = r47
            r31 = 0
            r47 = r7
            r14 = r8
            r40 = r20
            r7 = r23
            r27 = r26
            r8 = r52
            r20 = r9
            r9 = 3
            goto L_0x077a
        L_0x05d3:
            r32 = r6
            r23 = r7
            r47 = r14
            r52 = r26
            r0 = r27
            r27 = r41
            r1 = 0
            r6 = 0
            r14 = r2
            r7 = r4
            r26 = r8
            r8 = r34
            r34 = r40
            r40 = r5
            r5 = 26
            int r2 = r3.getStatus()
            r4 = 3
            if (r2 != r4) goto L_0x071f
            boolean r2 = r58.isKeepTogether()
            if (r38 == 0) goto L_0x05ff
            if (r2 != 0) goto L_0x05ff
            r33 = 2
            goto L_0x0601
        L_0x05ff:
            r33 = 3
        L_0x0601:
            r6 = r33
            r31 = 3
            r4 = r58
            r33 = 26
            r5 = r7
            r1 = r32
            r32 = r6
            r50 = r14
            r14 = r23
            r49 = r40
            r23 = r7
            r7 = r3
            r54 = r27
            r27 = r26
            r26 = r14
            r14 = r8
            r8 = r12
            r48 = r12
            r40 = r20
            r12 = 26
            r31 = 0
            r20 = r9
            r9 = r13
            com.itextpdf.layout.renderer.AbstractRenderer[] r4 = r4.createSplitAndOverflowRenderers(r5, r6, r7, r8, r9)
            r5 = r4[r31]
            r6 = 1
            r7 = r4[r6]
            boolean r6 = r58.isRelativePosition()
            if (r6 == 0) goto L_0x064a
            java.util.List r6 = r10.positionedRenderers
            int r6 = r6.size()
            if (r6 <= 0) goto L_0x064a
            java.util.ArrayList r6 = new java.util.ArrayList
            java.util.List r8 = r10.positionedRenderers
            r6.<init>(r8)
            r7.positionedRenderers = r6
        L_0x064a:
            r10.updateHeightsOnSplit(r1, r5, r7)
            if (r2 == 0) goto L_0x065e
            r5 = 0
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r6 = r7.childRenderers
            r6.clear()
            java.util.ArrayList r6 = new java.util.ArrayList
            java.util.List r8 = r10.childRenderers
            r6.<init>(r8)
            r7.childRenderers = r6
        L_0x065e:
            r10.correctFixedLayout(r15)
            com.itextpdf.layout.layout.LayoutArea r6 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            r8 = r52
            r9 = 1
            r10.applyPaddings(r6, r8, r9)
            com.itextpdf.layout.layout.LayoutArea r6 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            r10.applyBorderBox(r6, r0, r9)
            com.itextpdf.layout.layout.LayoutArea r6 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            r10.applyMargins(r6, r9)
            r58.applyAbsolutePositionIfNeeded(r59)
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            java.lang.Boolean r9 = r10.getPropertyAsBoolean(r12)
            boolean r6 = r6.equals(r9)
            if (r6 != 0) goto L_0x06f3
            if (r1 == 0) goto L_0x069b
            r51 = r0
            r18 = r2
            r9 = r26
            r6 = r32
            r0 = r47
            goto L_0x06fd
        L_0x069b:
            r6 = r32
            r9 = 3
            if (r6 == r9) goto L_0x06cc
            java.util.List r9 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r12 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r12 = r12.getBBox()
            com.itextpdf.layout.layout.LayoutArea r9 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r9, r12, r14, r11)
            com.itextpdf.layout.layout.LayoutResult r12 = new com.itextpdf.layout.layout.LayoutResult
            r46 = 0
            r41 = r12
            r42 = r6
            r43 = r9
            r44 = r5
            r45 = r7
            r41.<init>(r42, r43, r44, r45, r46)
            r51 = r0
            com.itextpdf.layout.element.AreaBreak r0 = r3.getAreaBreak()
            com.itextpdf.layout.layout.LayoutResult r0 = r12.setAreaBreak(r0)
            return r0
        L_0x06cc:
            r51 = r0
            r9 = r26
            r0 = r47
            r0.retainAll(r9)
            com.itextpdf.layout.layout.LayoutResult r12 = new com.itextpdf.layout.layout.LayoutResult
            r43 = 0
            r44 = 0
            com.itextpdf.layout.renderer.IRenderer r46 = r3.getCauseOfNothing()
            r41 = r12
            r42 = r6
            r45 = r7
            r41.<init>(r42, r43, r44, r45, r46)
            r18 = r2
            com.itextpdf.layout.element.AreaBreak r2 = r3.getAreaBreak()
            com.itextpdf.layout.layout.LayoutResult r2 = r12.setAreaBreak(r2)
            return r2
        L_0x06f3:
            r51 = r0
            r18 = r2
            r9 = r26
            r6 = r32
            r0 = r47
        L_0x06fd:
            java.util.List r2 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r12 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r12 = r12.getBBox()
            com.itextpdf.layout.layout.LayoutArea r2 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r2, r12, r14, r11)
            com.itextpdf.layout.layout.LayoutResult r12 = new com.itextpdf.layout.layout.LayoutResult
            r42 = 1
            r45 = 0
            r46 = 0
            r41 = r12
            r43 = r2
            r44 = r5
            r41.<init>(r42, r43, r44, r45, r46)
            return r12
        L_0x071f:
            r51 = r0
            r48 = r12
            r50 = r14
            r54 = r27
            r1 = r32
            r49 = r40
            r0 = r47
            r12 = 26
            r31 = 0
            r14 = r8
            r40 = r20
            r27 = r26
            r8 = r52
            r20 = r9
            r9 = 3
            r57 = r23
            r23 = r7
            r7 = r57
            r9 = r6
            r26 = r8
            r47 = r23
            r8 = r27
            r4 = r34
            r12 = r48
            r5 = r49
            r27 = r51
            r6 = r1
            r48 = r20
            r20 = r40
            r40 = r54
            r57 = r14
            r14 = r0
            r0 = r57
            goto L_0x0312
        L_0x075e:
            r49 = r5
            r1 = r6
            r48 = r12
            r0 = r14
            r51 = r27
            r14 = r34
            r54 = r40
            r23 = r47
            r31 = 0
            r34 = r4
            r27 = r8
            r40 = r20
            r8 = r26
            r20 = r9
            r9 = 3
            r2 = r15
        L_0x077a:
            if (r38 != 0) goto L_0x0785
            int r4 = r3.getStatus()
            if (r4 == r9) goto L_0x0783
            goto L_0x0785
        L_0x0783:
            r4 = 0
            goto L_0x0786
        L_0x0785:
            r4 = 1
        L_0x0786:
            com.itextpdf.layout.layout.LayoutArea r5 = r3.getOccupiedArea()
            if (r5 == 0) goto L_0x07bd
            boolean r5 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r27)
            if (r5 == 0) goto L_0x0798
            if (r19 == 0) goto L_0x0795
            goto L_0x0798
        L_0x0795:
            r6 = r54
            goto L_0x07bf
        L_0x0798:
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            r6 = 2
            com.itextpdf.kernel.geom.Rectangle[] r6 = new com.itextpdf.kernel.geom.Rectangle[r6]
            com.itextpdf.layout.layout.LayoutArea r9 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            r6[r31] = r9
            com.itextpdf.layout.layout.LayoutArea r9 = r3.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            r12 = 1
            r6[r12] = r9
            com.itextpdf.kernel.geom.Rectangle r6 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r6)
            r5.setBBox(r6)
            r6 = r54
            r10.fixOccupiedAreaIfOverflowedX(r6, r2)
            goto L_0x07bf
        L_0x07bd:
            r6 = r54
        L_0x07bf:
            if (r11 == 0) goto L_0x07c7
            r5 = r49
            r5.endChildMarginsHandling(r2)
            goto L_0x07c9
        L_0x07c7:
            r5 = r49
        L_0x07c9:
            int r9 = r3.getStatus()
            r12 = 1
            if (r9 != r12) goto L_0x07f9
            com.itextpdf.layout.layout.LayoutArea r9 = r3.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            float r9 = r9.getY()
            float r12 = r2.getY()
            float r9 = r9 - r12
            r2.setHeight(r9)
            com.itextpdf.layout.layout.LayoutArea r9 = r27.getOccupiedArea()
            if (r9 == 0) goto L_0x07f6
            com.itextpdf.layout.layout.LayoutArea r9 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            r12 = r27
            r10.alignChildHorizontally(r12, r9)
            goto L_0x07fb
        L_0x07f6:
            r12 = r27
            goto L_0x07fb
        L_0x07f9:
            r12 = r27
        L_0x07fb:
            if (r37 != 0) goto L_0x080c
            com.itextpdf.layout.renderer.IRenderer r9 = r3.getCauseOfNothing()
            if (r9 == 0) goto L_0x080c
            com.itextpdf.layout.renderer.IRenderer r9 = r3.getCauseOfNothing()
            r38 = r4
            r37 = r9
            goto L_0x080e
        L_0x080c:
            r38 = r4
        L_0x080e:
            r3 = 1
            int r4 = r47 + 1
            r26 = r8
            r23 = r14
            r15 = r20
            r20 = r40
            r12 = r48
            r27 = r51
            r9 = 0
            r14 = r0
            r8 = r6
            r6 = r1
            r1 = r4
            r4 = r7
            r7 = r5
            r5 = r50
            goto L_0x0189
        L_0x0828:
            r50 = r5
            r5 = r7
            r48 = r12
            r0 = r14
            r40 = r20
            r14 = r23
            r51 = r27
            r9 = 3
            r12 = 26
            r31 = 0
            r23 = r1
            r7 = r4
            r1 = r6
            r6 = r8
            r20 = r15
            r8 = r26
            r15 = r2
            if (r19 == 0) goto L_0x084b
            com.itextpdf.layout.renderer.FloatingHelper.includeChildFloatsInOccupiedArea((java.util.List<com.itextpdf.kernel.geom.Rectangle>) r0, (com.itextpdf.layout.renderer.IRenderer) r10, (java.util.Set<com.itextpdf.kernel.geom.Rectangle>) r7)
            r10.fixOccupiedAreaIfOverflowedX(r6, r15)
        L_0x084b:
            if (r1 == 0) goto L_0x0853
            r4 = r25
            r10.fixOccupiedAreaIfOverflowedY(r4, r15)
            goto L_0x0855
        L_0x0853:
            r4 = r25
        L_0x0855:
            if (r11 == 0) goto L_0x085a
            r5.endMarginsCollapse(r15)
        L_0x085a:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 86
            java.lang.Boolean r3 = r10.getPropertyAsBoolean(r3)
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x087f
            com.itextpdf.layout.layout.LayoutArea r2 = r10.occupiedArea
            r3 = 2
            com.itextpdf.kernel.geom.Rectangle[] r9 = new com.itextpdf.kernel.geom.Rectangle[r3]
            com.itextpdf.layout.layout.LayoutArea r3 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            r9[r31] = r3
            r3 = 1
            r9[r3] = r15
            com.itextpdf.kernel.geom.Rectangle r3 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r9)
            r2.setBBox(r3)
        L_0x087f:
            r9 = 1
            boolean r2 = r13.isEmpty()
            if (r2 != 0) goto L_0x0896
            if (r1 != 0) goto L_0x0896
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            java.lang.Boolean r3 = r10.getPropertyAsBoolean(r12)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0896
            r2 = 1
            goto L_0x0897
        L_0x0896:
            r2 = 0
        L_0x0897:
            r23 = r2
            r2 = 0
            if (r19 == 0) goto L_0x08a2
            if (r23 != 0) goto L_0x089f
            goto L_0x08a2
        L_0x089f:
            r25 = r2
            goto L_0x08a8
        L_0x08a2:
            com.itextpdf.layout.renderer.AbstractRenderer r2 = r10.applyMinHeight(r4, r15)
            r25 = r2
        L_0x08a8:
            if (r25 == 0) goto L_0x08ac
            r2 = 1
            goto L_0x08ad
        L_0x08ac:
            r2 = 0
        L_0x08ad:
            r26 = r2
            if (r26 == 0) goto L_0x08d9
            boolean r2 = r58.isKeepTogether()
            if (r2 == 0) goto L_0x08d9
            r0.retainAll(r7)
            com.itextpdf.layout.layout.LayoutResult r12 = new com.itextpdf.layout.layout.LayoutResult
            r2 = 3
            r3 = 0
            r18 = 0
            r56 = r0
            r55 = r8
            r8 = r51
            r0 = r12
            r27 = r1
            r1 = r2
            r2 = r3
            r3 = r18
            r41 = r4
            r4 = r58
            r42 = r5
            r5 = r58
            r0.<init>(r1, r2, r3, r4, r5)
            return r12
        L_0x08d9:
            r56 = r0
            r27 = r1
            r41 = r4
            r42 = r5
            r55 = r8
            r8 = r51
            if (r25 != 0) goto L_0x08e9
            if (r23 == 0) goto L_0x08f5
        L_0x08e9:
            if (r38 != 0) goto L_0x08f3
            boolean r0 = r13.isEmpty()
            if (r0 != 0) goto L_0x08f3
            r0 = 3
            goto L_0x08f4
        L_0x08f3:
            r0 = 2
        L_0x08f4:
            r9 = r0
        L_0x08f5:
            if (r23 == 0) goto L_0x0914
            if (r25 == 0) goto L_0x08fc
            r0 = 3
            if (r9 != r0) goto L_0x0900
        L_0x08fc:
            com.itextpdf.layout.renderer.AbstractRenderer r25 = r10.createOverflowRenderer(r9)
        L_0x0900:
            java.util.List r0 = r25.getChildRenderers()
            r0.addAll(r13)
            r0 = 2
            if (r9 != r0) goto L_0x0911
            if (r26 != 0) goto L_0x0911
            if (r19 != 0) goto L_0x0911
            com.itextpdf.layout.renderer.FloatingHelper.removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(r25)
        L_0x0911:
            r5 = r25
            goto L_0x0916
        L_0x0914:
            r5 = r25
        L_0x0916:
            r0 = r58
            int r1 = r48.size()
            if (r1 <= 0) goto L_0x0971
            r1 = 3
            if (r9 == r1) goto L_0x0971
            com.itextpdf.layout.renderer.AbstractRenderer r4 = r10.createSplitRenderer(r9)
            java.util.ArrayList r0 = new java.util.ArrayList
            java.util.List r1 = r10.childRenderers
            r0.<init>(r1)
            r4.childRenderers = r0
            r3 = r48
            r10.replaceSplitRendererKidFloats(r3, r4)
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            float r0 = r0.getHeight()
            if (r19 != 0) goto L_0x0959
            r1 = 2
            com.itextpdf.kernel.geom.Rectangle[] r1 = new com.itextpdf.kernel.geom.Rectangle[r1]
            r1[r31] = r15
            com.itextpdf.layout.layout.LayoutArea r2 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r18 = 1
            r1[r18] = r2
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.kernel.geom.Rectangle.getCommonRectangle(r1)
            float r0 = r1.getHeight()
            r25 = r0
            goto L_0x095b
        L_0x0959:
            r25 = r0
        L_0x095b:
            r0 = r58
            r1 = r25
            r2 = r27
            r43 = r3
            r3 = r4
            r32 = r4
            r4 = r5
            r44 = r5
            r5 = r19
            r0.updateHeightsOnSplit(r1, r2, r3, r4, r5)
            r25 = r32
            goto L_0x0977
        L_0x0971:
            r44 = r5
            r43 = r48
            r25 = r0
        L_0x0977:
            java.util.List r0 = r10.positionedRenderers
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x09ee
            java.util.List r0 = r10.positionedRenderers
            java.util.Iterator r0 = r0.iterator()
        L_0x0985:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x09eb
            java.lang.Object r1 = r0.next()
            com.itextpdf.layout.renderer.IRenderer r1 = (com.itextpdf.layout.renderer.IRenderer) r1
            com.itextpdf.layout.layout.LayoutArea r2 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.clone()
            r3 = 1148846080(0x447a0000, float:1000.0)
            com.itextpdf.kernel.geom.Rectangle r4 = r2.moveDown(r3)
            float r5 = r2.getHeight()
            float r5 = r5 + r3
            r4.setHeight(r5)
            com.itextpdf.layout.layout.LayoutArea r4 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r5 = r10.occupiedArea
            int r5 = r5.getPageNumber()
            com.itextpdf.layout.layout.LayoutArea r12 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r12 = r12.getBBox()
            com.itextpdf.kernel.geom.Rectangle r12 = r12.clone()
            r4.<init>(r5, r12)
            com.itextpdf.kernel.geom.Rectangle r5 = r4.getBBox()
            r33 = r0
            r12 = r55
            r0 = 1
            r10.applyPaddings(r5, r12, r0)
            com.itextpdf.kernel.geom.Rectangle r0 = r4.getBBox()
            r10.preparePositionedRendererAndAreaForLayout(r1, r2, r0)
            com.itextpdf.layout.layout.PositionedLayoutContext r0 = new com.itextpdf.layout.layout.PositionedLayoutContext
            com.itextpdf.layout.layout.LayoutArea r5 = new com.itextpdf.layout.layout.LayoutArea
            r34 = r3
            com.itextpdf.layout.layout.LayoutArea r3 = r10.occupiedArea
            int r3 = r3.getPageNumber()
            r5.<init>(r3, r2)
            r0.<init>(r5, r4)
            r1.layout(r0)
            r0 = r33
            r12 = 26
            goto L_0x0985
        L_0x09eb:
            r12 = r55
            goto L_0x09f0
        L_0x09ee:
            r12 = r55
        L_0x09f0:
            if (r16 == 0) goto L_0x09f5
            r10.correctFixedLayout(r15)
        L_0x09f5:
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r1 = 1
            r10.applyPaddings(r0, r12, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r10.applyBorderBox(r0, r8, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r10.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r10.applyMargins(r0, r1)
            r58.applyAbsolutePositionIfNeeded(r59)
            if (r17 == 0) goto L_0x0a8f
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.clone()
            r10.applyRotationLayout(r0)
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            boolean r0 = r10.isNotFittingLayoutArea(r0)
            if (r0 == 0) goto L_0x0a8a
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            boolean r0 = r10.isNotFittingWidth(r0)
            if (r0 == 0) goto L_0x0a60
            com.itextpdf.layout.layout.LayoutArea r0 = r59.getArea()
            boolean r0 = r10.isNotFittingHeight(r0)
            if (r0 != 0) goto L_0x0a60
            java.lang.Class r0 = r58.getClass()
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "It fits by height so it will be forced placed"
            r1[r31] = r2
            java.lang.String r2 = "Element does not fit current area. {0}"
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r1)
            r0.warn(r1)
            r54 = r6
            r6 = r56
            goto L_0x0a93
        L_0x0a60:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r1 = 26
            java.lang.Boolean r1 = r10.getPropertyAsBoolean(r1)
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0a85
            r5 = r56
            r5.retainAll(r7)
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r18 = new com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r1 = 3
            r2 = 0
            r3 = 0
            r0 = r18
            r4 = r58
            r54 = r6
            r6 = r5
            r5 = r58
            r0.<init>(r1, r2, r3, r4, r5)
            return r18
        L_0x0a85:
            r54 = r6
            r6 = r56
            goto L_0x0a93
        L_0x0a8a:
            r54 = r6
            r6 = r56
            goto L_0x0a93
        L_0x0a8f:
            r54 = r6
            r6 = r56
        L_0x0a93:
            r58.applyVerticalAlignment()
            com.itextpdf.layout.renderer.FloatingHelper.removeFloatsAboveRendererBottom(r6, r10)
            r0 = 3
            if (r9 == r0) goto L_0x0abe
            java.util.List r0 = r59.getFloatRendererAreas()
            com.itextpdf.layout.layout.LayoutArea r1 = r59.getArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            com.itextpdf.layout.layout.LayoutArea r0 = com.itextpdf.layout.renderer.FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(r10, r0, r1, r14, r11)
            com.itextpdf.layout.layout.LayoutResult r1 = new com.itextpdf.layout.layout.LayoutResult
            r31 = r1
            r32 = r9
            r33 = r0
            r34 = r25
            r35 = r44
            r36 = r37
            r31.<init>(r32, r33, r34, r35, r36)
            return r1
        L_0x0abe:
            java.util.List r0 = r10.positionedRenderers
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0ad2
            java.util.ArrayList r0 = new java.util.ArrayList
            java.util.List r1 = r10.positionedRenderers
            r0.<init>(r1)
            r1 = r44
            r1.positionedRenderers = r0
            goto L_0x0ad4
        L_0x0ad2:
            r1 = r44
        L_0x0ad4:
            r6.retainAll(r7)
            com.itextpdf.layout.layout.LayoutResult r0 = new com.itextpdf.layout.layout.LayoutResult
            r32 = 3
            r33 = 0
            r34 = 0
            r31 = r0
            r35 = r1
            r36 = r37
            r31.<init>(r32, r33, r34, r35, r36)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.BlockRenderer.layout(com.itextpdf.layout.layout.LayoutContext):com.itextpdf.layout.layout.LayoutResult");
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createSplitRenderer(int layoutResult) {
        AbstractRenderer splitRenderer = (AbstractRenderer) getNextRenderer();
        splitRenderer.parent = this.parent;
        splitRenderer.modelElement = this.modelElement;
        splitRenderer.occupiedArea = this.occupiedArea;
        splitRenderer.isLastRendererForModelElement = false;
        splitRenderer.addAllProperties(getOwnProperties());
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createOverflowRenderer(int layoutResult) {
        AbstractRenderer overflowRenderer = (AbstractRenderer) getNextRenderer();
        overflowRenderer.parent = this.parent;
        overflowRenderer.modelElement = this.modelElement;
        overflowRenderer.addAllProperties(getOwnProperties());
        return overflowRenderer;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: com.itextpdf.layout.tagging.LayoutTaggingHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(com.itextpdf.layout.renderer.DrawContext r13) {
        /*
            r12 = this;
            com.itextpdf.layout.layout.LayoutArea r0 = r12.occupiedArea
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x001c
            java.lang.Class<com.itextpdf.layout.renderer.BlockRenderer> r0 = com.itextpdf.layout.renderer.BlockRenderer.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r3 = "Drawing won't be performed."
            r1[r2] = r3
            java.lang.String r2 = "Occupied area has not been initialized. {0}"
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r1)
            r0.error(r1)
            return
        L_0x001c:
            boolean r0 = r13.isTaggingEnabled()
            r3 = 0
            if (r0 == 0) goto L_0x0055
            r4 = 108(0x6c, float:1.51E-43)
            java.lang.Object r4 = r12.getProperty(r4)
            r3 = r4
            com.itextpdf.layout.tagging.LayoutTaggingHelper r3 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r3
            if (r3 != 0) goto L_0x0030
            r0 = 0
            goto L_0x0055
        L_0x0030:
            com.itextpdf.kernel.pdf.tagutils.TagTreePointer r4 = r3.useAutoTaggingPointerAndRememberItsPosition(r12)
            boolean r5 = r3.createTag((com.itextpdf.layout.renderer.IRenderer) r12, (com.itextpdf.kernel.pdf.tagutils.TagTreePointer) r4)
            if (r5 == 0) goto L_0x0055
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r5 = r4.getProperties()
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r6 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getListAttributes(r12, r4)
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r5 = r5.addAttributes(r2, r6)
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r6 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getTableAttributes(r12, r4)
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r5 = r5.addAttributes(r2, r6)
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r6 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getLayoutAttributes(r12, r4)
            r5.addAttributes(r2, r6)
        L_0x0055:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r4 = r13.getCanvas()
            r12.beginTransformationIfApplied(r4)
            r12.applyDestinationsAndAnnotation(r13)
            boolean r4 = r12.isRelativePosition()
            if (r4 == 0) goto L_0x0068
            r12.applyRelativePositioningTranslation(r2)
        L_0x0068:
            r12.beginElementOpacityApplying(r13)
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r5 = r13.getCanvas()
            r12.beginRotationIfApplied(r5)
            com.itextpdf.layout.property.OverflowPropertyValue r5 = com.itextpdf.layout.property.OverflowPropertyValue.HIDDEN
            r6 = 103(0x67, float:1.44E-43)
            boolean r5 = r12.isOverflowProperty((com.itextpdf.layout.property.OverflowPropertyValue) r5, (int) r6)
            com.itextpdf.layout.property.OverflowPropertyValue r6 = com.itextpdf.layout.property.OverflowPropertyValue.HIDDEN
            r7 = 104(0x68, float:1.46E-43)
            boolean r6 = r12.isOverflowProperty((com.itextpdf.layout.property.OverflowPropertyValue) r6, (int) r7)
            if (r5 != 0) goto L_0x0086
            if (r6 == 0) goto L_0x0087
        L_0x0086:
            r2 = 1
        L_0x0087:
            r12.drawBackground(r13)
            r12.drawBorder(r13)
            if (r2 == 0) goto L_0x00f6
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r7 = r13.getCanvas()
            r7.saveState()
            com.itextpdf.layout.layout.LayoutArea r7 = r12.occupiedArea
            int r7 = r7.getPageNumber()
            if (r7 < r1) goto L_0x00b6
            com.itextpdf.kernel.pdf.PdfDocument r8 = r13.getDocument()
            int r8 = r8.getNumberOfPages()
            if (r7 <= r8) goto L_0x00a9
            goto L_0x00b6
        L_0x00a9:
            com.itextpdf.kernel.pdf.PdfDocument r8 = r13.getDocument()
            com.itextpdf.kernel.pdf.PdfPage r8 = r8.getPage((int) r7)
            com.itextpdf.kernel.geom.Rectangle r8 = r8.getPageSize()
            goto L_0x00c1
        L_0x00b6:
            com.itextpdf.kernel.geom.Rectangle r8 = new com.itextpdf.kernel.geom.Rectangle
            r9 = -923524096(0xffffffffc8f42400, float:-500000.0)
            r10 = 1232348160(0x49742400, float:1000000.0)
            r8.<init>(r9, r9, r10, r10)
        L_0x00c1:
            com.itextpdf.kernel.geom.Rectangle r9 = r12.getBorderAreaBBox()
            if (r5 == 0) goto L_0x00d6
            float r10 = r9.getX()
            com.itextpdf.kernel.geom.Rectangle r10 = r8.setX(r10)
            float r11 = r9.getWidth()
            r10.setWidth(r11)
        L_0x00d6:
            if (r6 == 0) goto L_0x00e7
            float r10 = r9.getY()
            com.itextpdf.kernel.geom.Rectangle r10 = r8.setY(r10)
            float r11 = r9.getHeight()
            r10.setHeight(r11)
        L_0x00e7:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r10 = r13.getCanvas()
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r10 = r10.rectangle(r8)
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r10 = r10.clip()
            r10.endPath()
        L_0x00f6:
            r12.drawChildren(r13)
            r12.drawPositionedChildren(r13)
            if (r2 == 0) goto L_0x0105
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r7 = r13.getCanvas()
            r7.restoreState()
        L_0x0105:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r7 = r13.getCanvas()
            r12.endRotationIfApplied(r7)
            r12.endElementOpacityApplying(r13)
            if (r4 == 0) goto L_0x0114
            r12.applyRelativePositioningTranslation(r1)
        L_0x0114:
            if (r0 == 0) goto L_0x0120
            boolean r7 = r12.isLastRendererForModelElement
            if (r7 == 0) goto L_0x011d
            r3.finishTaggingHint(r12)
        L_0x011d:
            r3.restoreAutoTaggingPointerPosition(r12)
        L_0x0120:
            r12.flushed = r1
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r1 = r13.getCanvas()
            r12.endTransformationIfApplied(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.BlockRenderer.draw(com.itextpdf.layout.renderer.DrawContext):void");
    }

    public Rectangle getOccupiedAreaBBox() {
        Rectangle bBox = this.occupiedArea.getBBox().clone();
        if (((Float) getProperty(55)) != null) {
            if (!hasOwnProperty(57) || !hasOwnProperty(56)) {
                LoggerFactory.getLogger((Class<?>) BlockRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.ROTATION_WAS_NOT_CORRECTLY_PROCESSED_FOR_RENDERER, getClass().getSimpleName()));
            } else {
                bBox.setWidth(getPropertyAsFloat(57).floatValue());
                bBox.setHeight(getPropertyAsFloat(56).floatValue());
            }
        }
        return bBox;
    }

    /* access modifiers changed from: protected */
    public void applyVerticalAlignment() {
        VerticalAlignment verticalAlignment = (VerticalAlignment) getProperty(75);
        if (verticalAlignment != null && verticalAlignment != VerticalAlignment.TOP && !this.childRenderers.isEmpty()) {
            float lowestChildBottom = Float.MAX_VALUE;
            if (!FloatingHelper.isRendererFloating(this) && !(this instanceof CellRenderer)) {
                int lastChildIndex = this.childRenderers.size() - 1;
                while (true) {
                    if (lastChildIndex < 0) {
                        break;
                    }
                    int lastChildIndex2 = lastChildIndex - 1;
                    IRenderer child = (IRenderer) this.childRenderers.get(lastChildIndex);
                    if (!FloatingHelper.isRendererFloating(child)) {
                        lowestChildBottom = child.getOccupiedArea().getBBox().getBottom();
                        break;
                    }
                    lastChildIndex = lastChildIndex2;
                }
            } else {
                for (IRenderer child2 : this.childRenderers) {
                    if (child2.getOccupiedArea().getBBox().getBottom() < lowestChildBottom) {
                        lowestChildBottom = child2.getOccupiedArea().getBBox().getBottom();
                    }
                }
            }
            if (lowestChildBottom != Float.MAX_VALUE) {
                float deltaY = lowestChildBottom - getInnerAreaBBox().getY();
                if (deltaY >= 0.0f) {
                    switch (C14701.$SwitchMap$com$itextpdf$layout$property$VerticalAlignment[verticalAlignment.ordinal()]) {
                        case 1:
                            for (IRenderer child3 : this.childRenderers) {
                                child3.move(0.0f, -deltaY);
                            }
                            return;
                        case 2:
                            for (IRenderer child4 : this.childRenderers) {
                                child4.move(0.0f, (-deltaY) / 2.0f);
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    /* renamed from: com.itextpdf.layout.renderer.BlockRenderer$1 */
    static /* synthetic */ class C14701 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$VerticalAlignment;

        static {
            int[] iArr = new int[VerticalAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$VerticalAlignment = iArr;
            try {
                iArr[VerticalAlignment.BOTTOM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$VerticalAlignment[VerticalAlignment.MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void applyRotationLayout(Rectangle layoutBox) {
        float angle = getPropertyAsFloat(55).floatValue();
        float x = this.occupiedArea.getBBox().getX();
        float y = this.occupiedArea.getBBox().getY();
        float height = this.occupiedArea.getBBox().getHeight();
        setProperty(57, Float.valueOf(this.occupiedArea.getBBox().getWidth()));
        setProperty(56, Float.valueOf(height));
        AffineTransform rotationTransform = new AffineTransform();
        if (isPositioned()) {
            Float rotationPointX = getPropertyAsFloat(58);
            Float rotationPointY = getPropertyAsFloat(59);
            if (rotationPointX == null || rotationPointY == null) {
                rotationPointX = Float.valueOf(x);
                rotationPointY = Float.valueOf(y);
            }
            rotationTransform.translate((double) rotationPointX.floatValue(), (double) rotationPointY.floatValue());
            rotationTransform.rotate((double) angle);
            rotationTransform.translate((double) (-rotationPointX.floatValue()), (double) (-rotationPointY.floatValue()));
            Rectangle newBBox = calculateBBox(transformPoints(rectangleToPointsList(this.occupiedArea.getBBox()), rotationTransform));
            this.occupiedArea.getBBox().setWidth(newBBox.getWidth());
            this.occupiedArea.getBBox().setHeight(newBBox.getHeight());
            move(newBBox.getX() - x, newBBox.getY() - y);
            float f = angle;
            float f2 = x;
            return;
        }
        List<Point> rotatedPoints = transformPoints(rectangleToPointsList(this.occupiedArea.getBBox()), AffineTransform.getRotateInstance((double) angle));
        float[] shift = calculateShiftToPositionBBoxOfPointsAt(x, y + height, rotatedPoints);
        for (Point point : rotatedPoints) {
            double x2 = point.getX();
            double d = (double) shift[0];
            Double.isNaN(d);
            double d2 = x2 + d;
            double y2 = point.getY();
            float angle2 = angle;
            double d3 = (double) shift[1];
            Double.isNaN(d3);
            point.setLocation(d2, y2 + d3);
            angle = angle2;
            x = x;
        }
        float f3 = x;
        Rectangle newBBox2 = calculateBBox(rotatedPoints);
        this.occupiedArea.getBBox().setWidth(newBBox2.getWidth());
        this.occupiedArea.getBBox().setHeight(newBBox2.getHeight());
        move(0.0f, height - newBBox2.getHeight());
    }

    /* access modifiers changed from: protected */
    public AffineTransform createRotationTransformInsideOccupiedArea() {
        AffineTransform rotationTransform = AffineTransform.getRotateInstance((double) ((Float) getProperty(55)).floatValue());
        float[] shift = calculateShiftToPositionBBoxOfPointsAt(this.occupiedArea.getBBox().getLeft(), this.occupiedArea.getBBox().getTop(), transformPoints(rectangleToPointsList(getOccupiedAreaBBox()), rotationTransform));
        rotationTransform.preConcatenate(AffineTransform.getTranslateInstance((double) shift[0], (double) shift[1]));
        return rotationTransform;
    }

    /* access modifiers changed from: protected */
    public void beginRotationIfApplied(PdfCanvas canvas) {
        if (getPropertyAsFloat(55) == null) {
            return;
        }
        if (!hasOwnProperty(56)) {
            LoggerFactory.getLogger((Class<?>) BlockRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.ROTATION_WAS_NOT_CORRECTLY_PROCESSED_FOR_RENDERER, getClass().getSimpleName()));
            return;
        }
        canvas.saveState().concatMatrix(createRotationTransformInsideOccupiedArea());
    }

    /* access modifiers changed from: protected */
    public void endRotationIfApplied(PdfCanvas canvas) {
        if (getPropertyAsFloat(55) != null && hasOwnProperty(56)) {
            canvas.restoreState();
        }
    }

    /* access modifiers changed from: package-private */
    public void correctFixedLayout(Rectangle layoutBox) {
        if (isFixedLayout()) {
            move(0.0f, getPropertyAsFloat(14).floatValue() - this.occupiedArea.getBBox().getY());
        }
    }

    /* access modifiers changed from: package-private */
    public void applyWidth(Rectangle parentBBox, Float blockWidth, OverflowPropertyValue overflowX) {
        Float rotation = getPropertyAsFloat(55);
        if (blockWidth == null || (blockWidth.floatValue() >= parentBBox.getWidth() && !isPositioned() && rotation == null && isOverflowFit(overflowX))) {
            Float minWidth = retrieveMinWidth(parentBBox.getWidth());
            if (minWidth != null && minWidth.floatValue() > parentBBox.getWidth()) {
                parentBBox.setWidth(minWidth.floatValue());
                return;
            }
            return;
        }
        parentBBox.setWidth(blockWidth.floatValue());
    }

    /* access modifiers changed from: package-private */
    public boolean applyMaxHeight(Rectangle parentBBox, Float blockMaxHeight, MarginsCollapseHandler marginsCollapseHandler, boolean isCellRenderer, boolean wasParentsHeightClipped, OverflowPropertyValue overflowY) {
        if (blockMaxHeight == null) {
            return false;
        }
        if (blockMaxHeight.floatValue() >= parentBBox.getHeight() && isOverflowFit(overflowY)) {
            return false;
        }
        boolean wasHeightClipped = false;
        if (blockMaxHeight.floatValue() <= parentBBox.getHeight()) {
            wasHeightClipped = true;
        }
        float heightDelta = parentBBox.getHeight() - blockMaxHeight.floatValue();
        if (marginsCollapseHandler != null && !isCellRenderer) {
            marginsCollapseHandler.processFixedHeightAdjustment(heightDelta);
        }
        parentBBox.moveUp(heightDelta).setHeight(blockMaxHeight.floatValue());
        return wasHeightClipped;
    }

    /* access modifiers changed from: package-private */
    public AbstractRenderer applyMinHeight(OverflowPropertyValue overflowY, Rectangle layoutBox) {
        AbstractRenderer overflowRenderer = null;
        Float blockMinHeight = retrieveMinHeight();
        if (!Boolean.TRUE.equals(getPropertyAsBoolean(26)) && blockMinHeight != null && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight()) {
            float blockBottom = this.occupiedArea.getBBox().getBottom() - (blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight());
            if (isFixedLayout()) {
                this.occupiedArea.getBBox().setY(blockBottom).setHeight(blockMinHeight.floatValue());
            } else if (!isOverflowFit(overflowY) || 1.0E-4f + blockBottom >= layoutBox.getBottom()) {
                this.occupiedArea.getBBox().setY(blockBottom).setHeight(blockMinHeight.floatValue());
            } else {
                this.occupiedArea.getBBox().increaseHeight(this.occupiedArea.getBBox().getBottom() - layoutBox.getBottom()).setY(layoutBox.getBottom());
                if (this.occupiedArea.getBBox().getHeight() < 0.0f) {
                    this.occupiedArea.getBBox().setHeight(0.0f);
                }
                this.isLastRendererForModelElement = false;
                overflowRenderer = createOverflowRenderer(2);
                overflowRenderer.updateMinHeight(UnitValue.createPointValue(blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight()));
                if (hasProperty(27)) {
                    overflowRenderer.updateHeight(UnitValue.createPointValue(retrieveHeight().floatValue() - this.occupiedArea.getBBox().getHeight()));
                }
            }
        }
        return overflowRenderer;
    }

    /* access modifiers changed from: package-private */
    public void fixOccupiedAreaIfOverflowedX(OverflowPropertyValue overflowX, Rectangle layoutBox) {
        if (!isOverflowFit(overflowX)) {
            if (this.occupiedArea.getBBox().getWidth() > layoutBox.getWidth() || this.occupiedArea.getBBox().getLeft() < layoutBox.getLeft()) {
                this.occupiedArea.getBBox().setX(layoutBox.getX()).setWidth(layoutBox.getWidth());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void fixOccupiedAreaIfOverflowedY(OverflowPropertyValue overflowY, Rectangle layoutBox) {
        if (!isOverflowFit(overflowY) && this.occupiedArea.getBBox().getBottom() < layoutBox.getBottom()) {
            float difference = layoutBox.getBottom() - this.occupiedArea.getBBox().getBottom();
            this.occupiedArea.getBBox().moveUp(difference).decreaseHeight(difference);
        }
    }

    /* access modifiers changed from: protected */
    public float applyBordersPaddingsMargins(Rectangle parentBBox, Border[] borders, UnitValue[] paddings) {
        float parentWidth = parentBBox.getWidth();
        applyMargins(parentBBox, false);
        applyBorderBox(parentBBox, borders, false);
        if (isFixedLayout()) {
            parentBBox.setX(getPropertyAsFloat(34).floatValue());
        }
        applyPaddings(parentBBox, paddings, false);
        return parentWidth - parentBBox.getWidth();
    }

    public MinMaxWidth getMinMaxWidth() {
        MinMaxWidth childMinMaxWidth;
        MinMaxWidth minMaxWidth = new MinMaxWidth(calculateAdditionalWidth(this));
        if (!setMinMaxWidthBasedOnFixedWidth(minMaxWidth)) {
            Float maxWidth = null;
            Float minWidth = hasAbsoluteUnitValue(80) ? retrieveMinWidth(0.0f) : null;
            if (hasAbsoluteUnitValue(79)) {
                maxWidth = retrieveMaxWidth(0.0f);
            }
            if (minWidth == null || maxWidth == null) {
                AbstractWidthHandler handler = new MaxMaxWidthHandler(minMaxWidth);
                int epsilonNum = 0;
                int curEpsNum = 0;
                float previousFloatingChildWidth = 0.0f;
                for (IRenderer childRenderer : this.childRenderers) {
                    childRenderer.setParent(this);
                    if (childRenderer instanceof AbstractRenderer) {
                        childMinMaxWidth = ((AbstractRenderer) childRenderer).getMinMaxWidth();
                    } else {
                        childMinMaxWidth = MinMaxWidthUtils.countDefaultMinMaxWidth(childRenderer);
                    }
                    handler.updateMaxChildWidth(childMinMaxWidth.getMaxWidth() + (FloatingHelper.isRendererFloating(childRenderer) ? previousFloatingChildWidth : 0.0f));
                    handler.updateMinChildWidth(childMinMaxWidth.getMinWidth());
                    previousFloatingChildWidth = FloatingHelper.isRendererFloating(childRenderer) ? childMinMaxWidth.getMaxWidth() + previousFloatingChildWidth : 0.0f;
                    if (FloatingHelper.isRendererFloating(childRenderer)) {
                        curEpsNum++;
                    } else {
                        epsilonNum = Math.max(epsilonNum, curEpsNum);
                        curEpsNum = 0;
                    }
                }
                int epsilonNum2 = Math.max(epsilonNum, curEpsNum);
                handler.minMaxWidth.setChildrenMaxWidth(handler.minMaxWidth.getChildrenMaxWidth() + (((float) epsilonNum2) * 1.0E-4f));
                handler.minMaxWidth.setChildrenMinWidth(handler.minMaxWidth.getChildrenMinWidth() + (((float) epsilonNum2) * 1.0E-4f));
            }
            if (minWidth != null) {
                minMaxWidth.setChildrenMinWidth(minWidth.floatValue());
            }
            if (maxWidth != null) {
                minMaxWidth.setChildrenMaxWidth(maxWidth.floatValue());
            } else if (minMaxWidth.getChildrenMinWidth() > minMaxWidth.getChildrenMaxWidth()) {
                minMaxWidth.setChildrenMaxWidth(minMaxWidth.getChildrenMinWidth());
            }
        }
        if (getPropertyAsFloat(55) != null) {
            return RotationUtils.countRotationMinMaxWidth(minMaxWidth, this);
        }
        return minMaxWidth;
    }

    private AbstractRenderer[] createSplitAndOverflowRenderers(int childPos, int layoutStatus, LayoutResult childResult, Map<Integer, IRenderer> waitingFloatsSplitRenderers, List<IRenderer> waitingOverflowFloatRenderers) {
        AbstractRenderer splitRenderer = createSplitRenderer(layoutStatus);
        splitRenderer.childRenderers = new ArrayList(this.childRenderers.subList(0, childPos));
        if (childResult.getStatus() == 2 && childResult.getSplitRenderer() != null) {
            splitRenderer.childRenderers.add(childResult.getSplitRenderer());
        }
        replaceSplitRendererKidFloats(waitingFloatsSplitRenderers, splitRenderer);
        for (IRenderer renderer : splitRenderer.childRenderers) {
            renderer.setParent(splitRenderer);
        }
        AbstractRenderer overflowRenderer = createOverflowRenderer(layoutStatus);
        overflowRenderer.childRenderers.addAll(waitingOverflowFloatRenderers);
        if (childResult.getOverflowRenderer() != null) {
            overflowRenderer.childRenderers.add(childResult.getOverflowRenderer());
        }
        overflowRenderer.childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
        if (childResult.getStatus() == 2) {
            overflowRenderer.deleteOwnProperty(26);
        }
        return new AbstractRenderer[]{splitRenderer, overflowRenderer};
    }

    private void replaceSplitRendererKidFloats(Map<Integer, IRenderer> waitingFloatsSplitRenderers, IRenderer splitRenderer) {
        for (Map.Entry<Integer, IRenderer> waitingSplitRenderer : waitingFloatsSplitRenderers.entrySet()) {
            if (waitingSplitRenderer.getValue() != null) {
                splitRenderer.getChildRenderers().set(waitingSplitRenderer.getKey().intValue(), waitingSplitRenderer.getValue());
            } else {
                splitRenderer.getChildRenderers().set(waitingSplitRenderer.getKey().intValue(), (Object) null);
            }
        }
        for (int i = splitRenderer.getChildRenderers().size() - 1; i >= 0; i--) {
            if (splitRenderer.getChildRenderers().get(i) == null) {
                splitRenderer.getChildRenderers().remove(i);
            }
        }
    }

    private List<Point> clipPolygon(List<Point> points, Point clipLineBeg, Point clipLineEnd) {
        List<Point> filteredPoints = new ArrayList<>();
        boolean prevOnRightSide = false;
        Point filteringPoint = points.get(0);
        if (checkPointSide(filteringPoint, clipLineBeg, clipLineEnd) >= 0) {
            filteredPoints.add(filteringPoint);
            prevOnRightSide = true;
        }
        Point prevPoint = filteringPoint;
        for (int i = 1; i < points.size() + 1; i++) {
            Point filteringPoint2 = points.get(i % points.size());
            if (checkPointSide(filteringPoint2, clipLineBeg, clipLineEnd) >= 0) {
                if (!prevOnRightSide) {
                    filteredPoints.add(getIntersectionPoint(prevPoint, filteringPoint2, clipLineBeg, clipLineEnd));
                }
                filteredPoints.add(filteringPoint2);
                prevOnRightSide = true;
            } else if (prevOnRightSide) {
                filteredPoints.add(getIntersectionPoint(prevPoint, filteringPoint2, clipLineBeg, clipLineEnd));
            }
            prevPoint = filteringPoint2;
        }
        return filteredPoints;
    }

    private int checkPointSide(Point filteredPoint, Point clipLineBeg, Point clipLineEnd) {
        double sgn = ((filteredPoint.getX() - clipLineBeg.getX()) * (clipLineEnd.getY() - clipLineBeg.getY())) - ((clipLineEnd.getX() - clipLineBeg.getX()) * (filteredPoint.getY() - clipLineBeg.getY()));
        if (Math.abs(sgn) < 0.001d) {
            return 0;
        }
        if (sgn > 0.0d) {
            return 1;
        }
        if (sgn < 0.0d) {
            return -1;
        }
        return 0;
    }

    private Point getIntersectionPoint(Point lineBeg, Point lineEnd, Point clipLineBeg, Point clipLineEnd) {
        double A1 = lineBeg.getY() - lineEnd.getY();
        double A2 = clipLineBeg.getY() - clipLineEnd.getY();
        double B1 = lineEnd.getX() - lineBeg.getX();
        double B2 = clipLineEnd.getX() - clipLineBeg.getX();
        double C1 = (lineBeg.getX() * lineEnd.getY()) - (lineBeg.getY() * lineEnd.getX());
        double C2 = (clipLineBeg.getX() * clipLineEnd.getY()) - (clipLineBeg.getY() * clipLineEnd.getX());
        double M = (B1 * A2) - (B2 * A1);
        double d = B1;
        double d2 = A1;
        return new Point(((B2 * C1) - (B1 * C2)) / M, ((C2 * A1) - (C1 * A2)) / M);
    }
}

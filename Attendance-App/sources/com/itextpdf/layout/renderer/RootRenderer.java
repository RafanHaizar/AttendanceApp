package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.RootLayoutArea;
import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.LoggerFactory;

public abstract class RootRenderer extends AbstractRenderer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected RootLayoutArea currentArea;
    protected int currentPageNumber;
    private boolean floatOverflowedCompletely = false;
    List<Rectangle> floatRendererAreas;
    protected boolean immediateFlush = true;
    private LayoutArea initialCurrentArea;
    private IRenderer keepWithNextHangingRenderer;
    private LayoutResult keepWithNextHangingRendererLayoutResult;
    private MarginsCollapseHandler marginsCollapseHandler;
    protected List<IRenderer> waitingDrawingElements = new ArrayList();
    private List<IRenderer> waitingNextPageRenderers = new ArrayList();

    /* access modifiers changed from: protected */
    public abstract void flushSingleRenderer(IRenderer iRenderer);

    /* access modifiers changed from: protected */
    public abstract LayoutArea updateCurrentArea(LayoutResult layoutResult);

    /* JADX WARNING: Removed duplicated region for block: B:133:0x037f  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x038c  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0391  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x03a5  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x03a8  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x03bb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addChild(com.itextpdf.layout.renderer.IRenderer r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = 108(0x6c, float:1.51E-43)
            java.lang.Object r1 = r0.getProperty(r1)
            com.itextpdf.layout.tagging.LayoutTaggingHelper r1 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r1
            if (r1 == 0) goto L_0x0012
            r2 = r25
            com.itextpdf.layout.tagging.LayoutTaggingHelper.addTreeHints(r1, r2)
            goto L_0x0014
        L_0x0012:
            r2 = r25
        L_0x0014:
            java.util.List r3 = r0.childRenderers
            int r3 = r3.size()
            java.util.List r4 = r0.positionedRenderers
            int r4 = r4.size()
            super.addChild(r25)
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 1
            r5.<init>(r6)
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r6)
        L_0x002e:
            java.util.List r8 = r0.childRenderers
            int r8 = r8.size()
            if (r8 <= r3) goto L_0x0045
            java.util.List r8 = r0.childRenderers
            java.lang.Object r8 = r8.get(r3)
            r5.add(r8)
            java.util.List r8 = r0.childRenderers
            r8.remove(r3)
            goto L_0x002e
        L_0x0045:
            java.util.List r8 = r0.positionedRenderers
            int r8 = r8.size()
            if (r8 <= r4) goto L_0x005c
            java.util.List r8 = r0.positionedRenderers
            java.lang.Object r8 = r8.get(r4)
            r7.add(r8)
            java.util.List r8 = r0.positionedRenderers
            r8.remove(r4)
            goto L_0x0045
        L_0x005c:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            r9 = 89
            java.lang.Boolean r9 = r0.getPropertyAsBoolean(r9)
            boolean r8 = r8.equals(r9)
            com.itextpdf.layout.layout.RootLayoutArea r9 = r0.currentArea
            r10 = 0
            if (r9 != 0) goto L_0x0079
            r0.updateCurrentAndInitialArea(r10)
            if (r8 == 0) goto L_0x0079
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r9 = new com.itextpdf.layout.margincollapse.MarginsCollapseHandler
            r9.<init>(r0, r10)
            r0.marginsCollapseHandler = r9
        L_0x0079:
            r9 = 0
        L_0x007a:
            com.itextpdf.layout.layout.RootLayoutArea r11 = r0.currentArea
            if (r11 == 0) goto L_0x03cd
            int r11 = r5.size()
            if (r9 >= r11) goto L_0x03cd
            com.itextpdf.layout.renderer.RootRendererAreaStateHandler r11 = new com.itextpdf.layout.renderer.RootRendererAreaStateHandler
            r11.<init>()
            java.lang.Object r13 = r5.get(r9)
            r2 = r13
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            boolean r13 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r2)
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r14 = r0.waitingNextPageRenderers
            r15 = 100
            java.lang.Object r16 = r2.getProperty(r15)
            r15 = r16
            com.itextpdf.layout.property.ClearPropertyValue r15 = (com.itextpdf.layout.property.ClearPropertyValue) r15
            boolean r14 = com.itextpdf.layout.renderer.FloatingHelper.isClearanceApplied(r14, r15)
            if (r13 == 0) goto L_0x00bd
            boolean r15 = r0.floatOverflowedCompletely
            if (r15 != 0) goto L_0x00ac
            if (r14 == 0) goto L_0x00bd
        L_0x00ac:
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r12 = r0.waitingNextPageRenderers
            r12.add(r2)
            r0.floatOverflowedCompletely = r6
            r18 = r1
            r19 = r3
            r21 = r4
            r22 = r5
            goto L_0x035f
        L_0x00bd:
            r0.processWaitingKeepWithNextElement(r2)
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            r16 = 0
            r17 = 0
            if (r8 == 0) goto L_0x00de
            com.itextpdf.layout.layout.RootLayoutArea r10 = r0.currentArea
            if (r10 == 0) goto L_0x00de
            if (r2 == 0) goto L_0x00de
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r12 = r0.marginsCollapseHandler
            com.itextpdf.kernel.geom.Rectangle r10 = r10.getBBox()
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r17 = r12.startChildMarginsHandling(r2, r10)
            r10 = r17
            goto L_0x00e0
        L_0x00de:
            r10 = r17
        L_0x00e0:
            java.lang.Class<com.itextpdf.layout.renderer.RootRenderer> r17 = com.itextpdf.layout.renderer.RootRenderer.class
            if (r14 != 0) goto L_0x0125
            com.itextpdf.layout.layout.RootLayoutArea r12 = r0.currentArea
            if (r12 == 0) goto L_0x0117
            if (r2 == 0) goto L_0x0117
            com.itextpdf.layout.renderer.IRenderer r12 = r2.setParent(r0)
            com.itextpdf.layout.layout.LayoutContext r6 = new com.itextpdf.layout.layout.LayoutContext
            r18 = r1
            com.itextpdf.layout.layout.RootLayoutArea r1 = r0.currentArea
            com.itextpdf.layout.layout.LayoutArea r1 = r1.clone()
            r19 = r3
            java.util.List<com.itextpdf.kernel.geom.Rectangle> r3 = r0.floatRendererAreas
            r6.<init>(r1, r10, r3)
            com.itextpdf.layout.layout.LayoutResult r1 = r12.layout(r6)
            r16 = r1
            int r1 = r1.getStatus()
            r3 = 1
            if (r1 == r3) goto L_0x010d
            goto L_0x0129
        L_0x010d:
            r23 = r2
            r21 = r4
            r22 = r5
            r3 = r16
            goto L_0x0307
        L_0x0117:
            r18 = r1
            r19 = r3
            r23 = r2
            r21 = r4
            r22 = r5
            r3 = r16
            goto L_0x0307
        L_0x0125:
            r18 = r1
            r19 = r3
        L_0x0129:
            r1 = 0
            if (r14 == 0) goto L_0x0135
            com.itextpdf.layout.layout.LayoutResult r3 = new com.itextpdf.layout.layout.LayoutResult
            r6 = 3
            r12 = 0
            r3.<init>(r6, r12, r12, r2)
            r1 = 1
            goto L_0x0137
        L_0x0135:
            r3 = r16
        L_0x0137:
            int r6 = r3.getStatus()
            r12 = 2
            r16 = 0
            if (r6 != r12) goto L_0x0173
            if (r13 == 0) goto L_0x0153
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r6 = r0.waitingNextPageRenderers
            com.itextpdf.layout.renderer.IRenderer r12 = r3.getOverflowRenderer()
            r6.add(r12)
            r23 = r2
            r21 = r4
            r22 = r5
            goto L_0x0307
        L_0x0153:
            com.itextpdf.layout.renderer.IRenderer r6 = r3.getSplitRenderer()
            r0.processRenderer(r6, r15)
            boolean r6 = r11.attemptGoForwardToStoredNextState(r0)
            if (r6 != 0) goto L_0x0169
            r1 = 1
            r23 = r2
            r21 = r4
            r22 = r5
            goto L_0x0379
        L_0x0169:
            r20 = r1
            r23 = r2
            r21 = r4
            r22 = r5
            goto L_0x0377
        L_0x0173:
            int r6 = r3.getStatus()
            r12 = 3
            if (r6 != r12) goto L_0x036f
            if (r14 != 0) goto L_0x036f
            com.itextpdf.layout.renderer.IRenderer r6 = r3.getOverflowRenderer()
            boolean r6 = r6 instanceof com.itextpdf.layout.renderer.ImageRenderer
            java.lang.String r12 = ""
            r20 = r1
            java.lang.String r1 = "Element does not fit current area. {0}"
            if (r6 == 0) goto L_0x020c
            com.itextpdf.layout.renderer.IRenderer r6 = r3.getOverflowRenderer()
            com.itextpdf.layout.renderer.ImageRenderer r6 = (com.itextpdf.layout.renderer.ImageRenderer) r6
            com.itextpdf.layout.layout.LayoutArea r6 = r6.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            float r6 = r6.getHeight()
            r21 = r4
            java.util.List<com.itextpdf.kernel.geom.Rectangle> r4 = r0.floatRendererAreas
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x01f1
            com.itextpdf.layout.layout.RootLayoutArea r4 = r0.currentArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            float r4 = r4.getHeight()
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x01c1
            com.itextpdf.layout.layout.RootLayoutArea r4 = r0.currentArea
            boolean r4 = r4.isEmptyArea()
            if (r4 != 0) goto L_0x01c1
            r22 = r5
            r23 = r6
            goto L_0x01f5
        L_0x01c1:
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getOverflowRenderer()
            com.itextpdf.layout.renderer.ImageRenderer r4 = (com.itextpdf.layout.renderer.ImageRenderer) r4
            r22 = r5
            com.itextpdf.layout.layout.RootLayoutArea r5 = r0.currentArea
            r4.autoScale(r5)
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getOverflowRenderer()
            r23 = r6
            r5 = 1
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r5)
            r5 = 26
            r4.setProperty(r5, r6)
            org.slf4j.Logger r4 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r17)
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r6[r16] = r12
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r1, r6)
            r4.warn(r1)
            r1 = r20
            goto L_0x0208
        L_0x01f1:
            r22 = r5
            r23 = r6
        L_0x01f5:
            if (r13 == 0) goto L_0x0207
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r1 = r0.waitingNextPageRenderers
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getOverflowRenderer()
            r1.add(r4)
            r1 = 1
            r0.floatOverflowedCompletely = r1
            r23 = r2
            goto L_0x0307
        L_0x0207:
            r1 = 1
        L_0x0208:
            r23 = r2
            goto L_0x0379
        L_0x020c:
            r21 = r4
            r22 = r5
            com.itextpdf.layout.layout.RootLayoutArea r4 = r0.currentArea
            boolean r4 = r4.isEmptyArea()
            if (r4 == 0) goto L_0x02ed
            com.itextpdf.layout.element.AreaBreak r4 = r3.getAreaBreak()
            if (r4 != 0) goto L_0x02ed
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            com.itextpdf.layout.renderer.IRenderer r5 = r3.getOverflowRenderer()
            com.itextpdf.layout.IPropertyContainer r5 = r5.getModelElement()
            r6 = 32
            java.lang.Object r5 = r5.getProperty(r6)
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x025e
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getOverflowRenderer()
            com.itextpdf.layout.IPropertyContainer r4 = r4.getModelElement()
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r16)
            r4.setProperty(r6, r5)
            org.slf4j.Logger r4 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r17)
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            java.lang.String r5 = "KeepTogether property will be ignored."
            r6[r16] = r5
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r1, r6)
            r4.warn(r1)
            if (r13 != 0) goto L_0x025a
            r11.attemptGoBackToStoredPreviousStateAndStoreNextState(r0)
        L_0x025a:
            r23 = r2
            goto L_0x0377
        L_0x025e:
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getCauseOfNothing()
            if (r4 == 0) goto L_0x02b8
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            com.itextpdf.layout.renderer.IRenderer r5 = r3.getCauseOfNothing()
            java.lang.Object r5 = r5.getProperty(r6)
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x02b8
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getCauseOfNothing()
        L_0x0278:
            com.itextpdf.layout.IPropertyContainer r5 = r4.getModelElement()
            if (r5 == 0) goto L_0x0288
            com.itextpdf.layout.IPropertyContainer r5 = r4.getModelElement()
            java.lang.Object r5 = r5.getOwnProperty(r6)
            if (r5 != 0) goto L_0x0290
        L_0x0288:
            r5 = r4
            com.itextpdf.layout.renderer.AbstractRenderer r5 = (com.itextpdf.layout.renderer.AbstractRenderer) r5
            com.itextpdf.layout.renderer.IRenderer r5 = r5.parent
            if (r5 != 0) goto L_0x02b6
        L_0x0290:
            com.itextpdf.layout.IPropertyContainer r5 = r4.getModelElement()
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r16)
            r5.setProperty(r6, r12)
            org.slf4j.Logger r5 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r17)
            r6 = 1
            java.lang.Object[] r12 = new java.lang.Object[r6]
            java.lang.String r6 = "KeepTogether property of inner element will be ignored."
            r12[r16] = r6
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r1, r12)
            r5.warn(r1)
            if (r13 != 0) goto L_0x02b2
            r11.attemptGoBackToStoredPreviousStateAndStoreNextState(r0)
        L_0x02b2:
            r23 = r2
            goto L_0x0377
        L_0x02b6:
            r4 = r5
            goto L_0x0278
        L_0x02b8:
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            r5 = 26
            java.lang.Object r6 = r2.getProperty(r5)
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L_0x02e5
            com.itextpdf.layout.renderer.IRenderer r4 = r3.getOverflowRenderer()
            r23 = r2
            r6 = 1
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r6)
            r4.setProperty(r5, r2)
            org.slf4j.Logger r2 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r17)
            java.lang.Object[] r4 = new java.lang.Object[r6]
            r4[r16] = r12
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r1, r4)
            r2.warn(r1)
            goto L_0x0377
        L_0x02e5:
            r23 = r2
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            throw r1
        L_0x02ed:
            r23 = r2
            r11.storePreviousState(r0)
            boolean r1 = r11.attemptGoForwardToStoredNextState(r0)
            if (r1 != 0) goto L_0x0377
            if (r13 == 0) goto L_0x036d
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r1 = r0.waitingNextPageRenderers
            com.itextpdf.layout.renderer.IRenderer r2 = r3.getOverflowRenderer()
            r1.add(r2)
            r1 = 1
            r0.floatOverflowedCompletely = r1
        L_0x0307:
            if (r8 == 0) goto L_0x0314
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r1 = r0.marginsCollapseHandler
            com.itextpdf.layout.layout.RootLayoutArea r2 = r0.currentArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            r1.endChildMarginsHandling(r2)
        L_0x0314:
            if (r3 == 0) goto L_0x0321
            com.itextpdf.layout.renderer.IRenderer r1 = r3.getSplitRenderer()
            if (r1 == 0) goto L_0x0321
            com.itextpdf.layout.renderer.IRenderer r2 = r3.getSplitRenderer()
            goto L_0x0323
        L_0x0321:
            r2 = r23
        L_0x0323:
            if (r2 == 0) goto L_0x035f
            if (r3 == 0) goto L_0x035f
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r4 = 81
            java.lang.Object r4 = r2.getProperty(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0355
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r4 = 26
            java.lang.Object r4 = r2.getProperty(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0350
            org.slf4j.Logger r1 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r17)
            java.lang.String r4 = "Element was placed in a forced way. Keep with next property will be ignored"
            r1.warn(r4)
            r0.shrinkCurrentAreaAndProcessRenderer(r2, r15, r3)
            goto L_0x035f
        L_0x0350:
            r0.keepWithNextHangingRenderer = r2
            r0.keepWithNextHangingRendererLayoutResult = r3
            goto L_0x035f
        L_0x0355:
            int r1 = r3.getStatus()
            r4 = 3
            if (r1 == r4) goto L_0x035f
            r0.shrinkCurrentAreaAndProcessRenderer(r2, r15, r3)
        L_0x035f:
            int r9 = r9 + 1
            r1 = r18
            r3 = r19
            r4 = r21
            r5 = r22
            r6 = 1
            r10 = 0
            goto L_0x007a
        L_0x036d:
            r1 = 1
            goto L_0x0379
        L_0x036f:
            r20 = r1
            r23 = r2
            r21 = r4
            r22 = r5
        L_0x0377:
            r1 = r20
        L_0x0379:
            com.itextpdf.layout.renderer.IRenderer r2 = r3.getOverflowRenderer()
            if (r8 == 0) goto L_0x038a
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r4 = r0.marginsCollapseHandler
            com.itextpdf.layout.layout.RootLayoutArea r5 = r0.currentArea
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            r4.endChildMarginsHandling(r5)
        L_0x038a:
            if (r1 == 0) goto L_0x038f
            r0.updateCurrentAndInitialArea(r3)
        L_0x038f:
            if (r8 == 0) goto L_0x03a5
            com.itextpdf.layout.margincollapse.MarginsCollapseHandler r4 = new com.itextpdf.layout.margincollapse.MarginsCollapseHandler
            r5 = 0
            r4.<init>(r0, r5)
            r0.marginsCollapseHandler = r4
            com.itextpdf.layout.layout.RootLayoutArea r6 = r0.currentArea
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            com.itextpdf.layout.margincollapse.MarginsCollapseInfo r4 = r4.startChildMarginsHandling(r2, r6)
            r10 = r4
            goto L_0x03a6
        L_0x03a5:
            r5 = 0
        L_0x03a6:
            if (r14 == 0) goto L_0x03bb
            java.util.List<com.itextpdf.layout.renderer.IRenderer> r4 = r0.waitingNextPageRenderers
            r6 = 100
            java.lang.Object r12 = r2.getProperty(r6)
            com.itextpdf.layout.property.ClearPropertyValue r12 = (com.itextpdf.layout.property.ClearPropertyValue) r12
            boolean r4 = com.itextpdf.layout.renderer.FloatingHelper.isClearanceApplied(r4, r12)
            if (r4 == 0) goto L_0x03bd
            r16 = 1
            goto L_0x03be
        L_0x03bb:
            r6 = 100
        L_0x03bd:
        L_0x03be:
            r14 = r16
            r16 = r3
            r1 = r18
            r3 = r19
            r4 = r21
            r5 = r22
            r6 = 1
            goto L_0x00e0
        L_0x03cd:
            r18 = r1
            r19 = r3
            r21 = r4
            r22 = r5
            r1 = 0
        L_0x03d6:
            int r3 = r7.size()
            if (r1 >= r3) goto L_0x047e
            java.util.List r3 = r0.positionedRenderers
            java.lang.Object r4 = r7.get(r1)
            r3.add(r4)
            java.util.List r3 = r0.positionedRenderers
            java.util.List r4 = r0.positionedRenderers
            int r4 = r4.size()
            r5 = 1
            int r4 = r4 - r5
            java.lang.Object r3 = r3.get(r4)
            r2 = r3
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            r3 = 51
            java.lang.Object r3 = r2.getProperty(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            if (r3 != 0) goto L_0x0406
            int r4 = r0.currentPageNumber
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
        L_0x0406:
            r4 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r6 = 52
            java.lang.Object r6 = r2.getProperty(r6)
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0431
            boolean r5 = com.itextpdf.layout.renderer.AbstractRenderer.noAbsolutePositionInfo(r2)
            if (r5 == 0) goto L_0x0431
            com.itextpdf.layout.layout.LayoutArea r5 = new com.itextpdf.layout.layout.LayoutArea
            int r6 = r3.intValue()
            com.itextpdf.layout.layout.RootLayoutArea r9 = r0.currentArea
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            com.itextpdf.kernel.geom.Rectangle r9 = r9.clone()
            r5.<init>(r6, r9)
            goto L_0x0444
        L_0x0431:
            com.itextpdf.layout.layout.LayoutArea r5 = new com.itextpdf.layout.layout.LayoutArea
            int r6 = r3.intValue()
            com.itextpdf.layout.layout.LayoutArea r9 = r0.initialCurrentArea
            com.itextpdf.kernel.geom.Rectangle r9 = r9.getBBox()
            com.itextpdf.kernel.geom.Rectangle r9 = r9.clone()
            r5.<init>(r6, r9)
        L_0x0444:
            com.itextpdf.kernel.geom.Rectangle r6 = r5.getBBox()
            com.itextpdf.kernel.geom.Rectangle r6 = r6.clone()
            com.itextpdf.kernel.geom.Rectangle r9 = r5.getBBox()
            r0.preparePositionedRendererAndAreaForLayout(r2, r6, r9)
            com.itextpdf.layout.layout.PositionedLayoutContext r9 = new com.itextpdf.layout.layout.PositionedLayoutContext
            com.itextpdf.layout.layout.LayoutArea r10 = new com.itextpdf.layout.layout.LayoutArea
            int r11 = r5.getPageNumber()
            r10.<init>(r11, r6)
            r9.<init>(r10, r5)
            r2.layout(r9)
            boolean r9 = r0.immediateFlush
            if (r9 == 0) goto L_0x0479
            r0.flushSingleRenderer(r2)
            java.util.List r9 = r0.positionedRenderers
            java.util.List r10 = r0.positionedRenderers
            int r10 = r10.size()
            r11 = 1
            int r10 = r10 - r11
            r9.remove(r10)
            goto L_0x047a
        L_0x0479:
            r11 = 1
        L_0x047a:
            int r1 = r1 + 1
            goto L_0x03d6
        L_0x047e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.RootRenderer.addChild(com.itextpdf.layout.renderer.IRenderer):void");
    }

    public void flush() {
        for (IRenderer resultRenderer : this.childRenderers) {
            flushSingleRenderer(resultRenderer);
        }
        for (IRenderer resultRenderer2 : this.positionedRenderers) {
            flushSingleRenderer(resultRenderer2);
        }
        this.childRenderers.clear();
        this.positionedRenderers.clear();
    }

    public void close() {
        addAllWaitingNextPageRenderers();
        IRenderer iRenderer = this.keepWithNextHangingRenderer;
        if (iRenderer != null) {
            iRenderer.setProperty(81, false);
            IRenderer rendererToBeAdded = this.keepWithNextHangingRenderer;
            this.keepWithNextHangingRenderer = null;
            addChild(rendererToBeAdded);
        }
        if (!this.immediateFlush) {
            flush();
        }
        flushWaitingDrawingElements(true);
        LayoutTaggingHelper taggingHelper = (LayoutTaggingHelper) getProperty(108);
        if (taggingHelper != null) {
            taggingHelper.releaseAllHints();
        }
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        throw new IllegalStateException("Layout is not supported for root renderers.");
    }

    public LayoutArea getCurrentArea() {
        if (this.currentArea == null) {
            updateCurrentAndInitialArea((LayoutResult) null);
        }
        return this.currentArea;
    }

    /* access modifiers changed from: protected */
    public void shrinkCurrentAreaAndProcessRenderer(IRenderer renderer, List<IRenderer> resultRenderers, LayoutResult result) {
        if (this.currentArea != null) {
            float resultRendererHeight = result.getOccupiedArea().getBBox().getHeight();
            this.currentArea.getBBox().setHeight(this.currentArea.getBBox().getHeight() - resultRendererHeight);
            if (this.currentArea.isEmptyArea() && (resultRendererHeight > 0.0f || FloatingHelper.isRendererFloating(renderer))) {
                this.currentArea.setEmptyArea(false);
            }
            processRenderer(renderer, resultRenderers);
        }
        if (!this.immediateFlush) {
            this.childRenderers.addAll(resultRenderers);
        }
    }

    /* access modifiers changed from: protected */
    public void flushWaitingDrawingElements() {
        flushWaitingDrawingElements(true);
    }

    /* access modifiers changed from: package-private */
    public void flushWaitingDrawingElements(boolean force) {
        Set<IRenderer> flushedElements = new HashSet<>();
        for (int i = 0; i < this.waitingDrawingElements.size(); i++) {
            IRenderer waitingDrawingElement = this.waitingDrawingElements.get(i);
            if (force || (waitingDrawingElement.getOccupiedArea() != null && waitingDrawingElement.getOccupiedArea().getPageNumber() < this.currentArea.getPageNumber())) {
                flushSingleRenderer(waitingDrawingElement);
                flushedElements.add(waitingDrawingElement);
            } else if (waitingDrawingElement.getOccupiedArea() == null) {
                flushedElements.add(waitingDrawingElement);
            }
        }
        this.waitingDrawingElements.removeAll(flushedElements);
    }

    private void processRenderer(IRenderer renderer, List<IRenderer> resultRenderers) {
        alignChildHorizontally(renderer, this.currentArea.getBBox());
        if (this.immediateFlush) {
            flushSingleRenderer(renderer);
        } else {
            resultRenderers.add(renderer);
        }
    }

    private void processWaitingKeepWithNextElement(IRenderer renderer) {
        LayoutArea rest;
        IRenderer iRenderer = renderer;
        if (this.keepWithNextHangingRenderer != null) {
            LayoutArea rest2 = this.currentArea.clone();
            rest2.getBBox().setHeight(rest2.getBBox().getHeight() - this.keepWithNextHangingRendererLayoutResult.getOccupiedArea().getBBox().getHeight());
            boolean ableToProcessKeepWithNext = false;
            int i = 1;
            if (iRenderer.setParent(this).layout(new LayoutContext(rest2)).getStatus() != 3) {
                shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList(), this.keepWithNextHangingRendererLayoutResult);
                ableToProcessKeepWithNext = true;
                LayoutArea layoutArea = rest2;
            } else {
                float originalElementHeight = this.keepWithNextHangingRendererLayoutResult.getOccupiedArea().getBBox().getHeight();
                List<Float> trySplitHeightPoints = new ArrayList<>();
                int i2 = 1;
                while (i2 <= 5 && originalElementHeight - (((float) i2) * 35.0f) > originalElementHeight / 2.0f) {
                    trySplitHeightPoints.add(Float.valueOf(originalElementHeight - (((float) i2) * 35.0f)));
                    i2++;
                }
                int i3 = 0;
                while (i3 < trySplitHeightPoints.size() && !ableToProcessKeepWithNext) {
                    float curElementSplitHeight = trySplitHeightPoints.get(i3).floatValue();
                    RootLayoutArea firstElementSplitLayoutArea = (RootLayoutArea) this.currentArea.clone();
                    firstElementSplitLayoutArea.getBBox().setHeight(curElementSplitHeight).moveUp(this.currentArea.getBBox().getHeight() - curElementSplitHeight);
                    LayoutResult firstElementSplitLayoutResult = this.keepWithNextHangingRenderer.setParent(this).layout(new LayoutContext(firstElementSplitLayoutArea.clone()));
                    if (firstElementSplitLayoutResult.getStatus() == 2) {
                        RootLayoutArea storedArea = this.currentArea;
                        updateCurrentAndInitialArea(firstElementSplitLayoutResult);
                        LayoutResult firstElementOverflowLayoutResult = firstElementSplitLayoutResult.getOverflowRenderer().layout(new LayoutContext(this.currentArea.clone()));
                        if (firstElementOverflowLayoutResult.getStatus() == i) {
                            LayoutArea secondElementLayoutArea = this.currentArea.clone();
                            secondElementLayoutArea.getBBox().setHeight(secondElementLayoutArea.getBBox().getHeight() - firstElementOverflowLayoutResult.getOccupiedArea().getBBox().getHeight());
                            rest = rest2;
                            if (iRenderer.setParent(this).layout(new LayoutContext(secondElementLayoutArea)).getStatus() != 3) {
                                ableToProcessKeepWithNext = true;
                                this.currentArea = firstElementSplitLayoutArea;
                                this.currentPageNumber = firstElementSplitLayoutArea.getPageNumber();
                                shrinkCurrentAreaAndProcessRenderer(firstElementSplitLayoutResult.getSplitRenderer(), new ArrayList(), firstElementSplitLayoutResult);
                                updateCurrentAndInitialArea(firstElementSplitLayoutResult);
                                shrinkCurrentAreaAndProcessRenderer(firstElementSplitLayoutResult.getOverflowRenderer(), new ArrayList(), firstElementOverflowLayoutResult);
                            }
                        } else {
                            rest = rest2;
                        }
                        if (!ableToProcessKeepWithNext) {
                            this.currentArea = storedArea;
                            this.currentPageNumber = storedArea.getPageNumber();
                        }
                    } else {
                        rest = rest2;
                    }
                    i3++;
                    rest2 = rest;
                    i = 1;
                }
            }
            if (!ableToProcessKeepWithNext && !this.currentArea.isEmptyArea()) {
                RootLayoutArea storedArea2 = this.currentArea;
                updateCurrentAndInitialArea((LayoutResult) null);
                LayoutResult firstElementLayoutResult = this.keepWithNextHangingRenderer.setParent(this).layout(new LayoutContext(this.currentArea.clone()));
                if (firstElementLayoutResult.getStatus() == 1) {
                    LayoutArea secondElementLayoutArea2 = this.currentArea.clone();
                    secondElementLayoutArea2.getBBox().setHeight(secondElementLayoutArea2.getBBox().getHeight() - firstElementLayoutResult.getOccupiedArea().getBBox().getHeight());
                    if (iRenderer.setParent(this).layout(new LayoutContext(secondElementLayoutArea2)).getStatus() != 3) {
                        ableToProcessKeepWithNext = true;
                        shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList(), this.keepWithNextHangingRendererLayoutResult);
                    }
                }
                if (!ableToProcessKeepWithNext) {
                    this.currentArea = storedArea2;
                    this.currentPageNumber = storedArea2.getPageNumber();
                }
            }
            if (!ableToProcessKeepWithNext) {
                LoggerFactory.getLogger((Class<?>) RootRenderer.class).warn(LogMessageConstant.RENDERER_WAS_NOT_ABLE_TO_PROCESS_KEEP_WITH_NEXT);
                shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList(), this.keepWithNextHangingRendererLayoutResult);
            }
            this.keepWithNextHangingRenderer = null;
            this.keepWithNextHangingRendererLayoutResult = null;
        }
    }

    private void updateCurrentAndInitialArea(LayoutResult overflowResult) {
        this.floatRendererAreas = new ArrayList();
        updateCurrentArea(overflowResult);
        RootLayoutArea rootLayoutArea = this.currentArea;
        this.initialCurrentArea = rootLayoutArea == null ? null : rootLayoutArea.clone();
        addWaitingNextPageRenderers();
    }

    private void addAllWaitingNextPageRenderers() {
        boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
        while (!this.waitingNextPageRenderers.isEmpty()) {
            if (marginsCollapsingEnabled) {
                this.marginsCollapseHandler = new MarginsCollapseHandler(this, (MarginsCollapseInfo) null);
            }
            updateCurrentAndInitialArea((LayoutResult) null);
        }
    }

    private void addWaitingNextPageRenderers() {
        this.floatOverflowedCompletely = false;
        List<IRenderer> waitingFloatRenderers = new ArrayList<>(this.waitingNextPageRenderers);
        this.waitingNextPageRenderers.clear();
        for (IRenderer renderer : waitingFloatRenderers) {
            addChild(renderer);
        }
    }
}

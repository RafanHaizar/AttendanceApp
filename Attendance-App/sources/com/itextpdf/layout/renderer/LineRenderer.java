package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.LineLayoutResult;
import com.itextpdf.layout.layout.TextLayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.RenderingMode;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.otf.ActualTextIterator;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.TextUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineRenderer extends AbstractRenderer {
    private static final float MIN_MAX_WIDTH_CORRECTION_EPS = 0.001f;
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) LineRenderer.class);
    protected byte[] levels;
    protected float maxAscent;
    private float maxBlockAscent;
    private float maxBlockDescent;
    protected float maxDescent;
    private float maxTextAscent;
    private float maxTextDescent;

    enum SpecialScriptsContainingSequenceStatus {
        MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE,
        MOVE_TO_PREVIOUS_TEXT_RENDERER_CONTAINING_SPECIAL_SCRIPTS,
        FORCED_SPLIT
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v0, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r38v5, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r38v6, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v16, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r38v7, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r38v8, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v140, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v96, resolved type: com.itextpdf.layout.layout.LineLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v11, resolved type: com.itextpdf.layout.layout.TextLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v108, resolved type: com.itextpdf.layout.layout.MinMaxWidthLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v110, resolved type: com.itextpdf.layout.layout.MinMaxWidthLayoutResult} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v26, resolved type: com.itextpdf.layout.property.OverflowPropertyValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v80, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v137, resolved type: com.itextpdf.layout.property.OverflowPropertyValue} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0480  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x056c  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x05e1  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x05e9  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x05fb  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0606  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x0625  */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x063a  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x0686  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0696  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x06ef  */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x0700  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0702  */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x070e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x0717  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x077d  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x0785 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x07ba  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x0800  */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x0802  */
    /* JADX WARNING: Removed duplicated region for block: B:263:0x080a  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x0949  */
    /* JADX WARNING: Removed duplicated region for block: B:289:0x096a  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x0995  */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x0999  */
    /* JADX WARNING: Removed duplicated region for block: B:352:0x0b25  */
    /* JADX WARNING: Removed duplicated region for block: B:491:0x09b4 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0245  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x024a  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02cf  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x02e5  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0308  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0322  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0335  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.layout.LayoutResult layout(com.itextpdf.layout.layout.LayoutContext r72) {
        /*
            r71 = this;
            r6 = r71
            r7 = r72
            com.itextpdf.layout.layout.LayoutArea r0 = r72.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.kernel.geom.Rectangle r8 = r0.clone()
            boolean r9 = r72.isClippedHeight()
            java.util.List r10 = r72.getFloatRendererAreas()
            r0 = 0
            r1 = 0
            r11 = 103(0x67, float:1.44E-43)
            if (r10 == 0) goto L_0x003a
            float r2 = r8.getWidth()
            com.itextpdf.layout.renderer.FloatingHelper.adjustLineAreaAccordingToFloats(r10, r8)
            float r3 = r8.getWidth()
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x003a
            java.lang.Object r3 = r6.getProperty(r11)
            r0 = r3
            com.itextpdf.layout.property.OverflowPropertyValue r0 = (com.itextpdf.layout.property.OverflowPropertyValue) r0
            r1 = 1
            com.itextpdf.layout.property.OverflowPropertyValue r3 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            r6.setProperty(r11, r3)
        L_0x003a:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 118(0x76, float:1.65E-43)
            java.lang.Object r3 = r6.getOwnProperty(r3)
            boolean r12 = r2.equals(r3)
            boolean r2 = r7 instanceof com.itextpdf.layout.layout.LineLayoutContext
            if (r2 == 0) goto L_0x004e
            r2 = r7
            com.itextpdf.layout.layout.LineLayoutContext r2 = (com.itextpdf.layout.layout.LineLayoutContext) r2
            goto L_0x0053
        L_0x004e:
            com.itextpdf.layout.layout.LineLayoutContext r2 = new com.itextpdf.layout.layout.LineLayoutContext
            r2.<init>(r7)
        L_0x0053:
            r13 = r2
            float r2 = r13.getTextIndent()
            r14 = 0
            int r2 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r2 == 0) goto L_0x0072
            float r2 = r13.getTextIndent()
            com.itextpdf.kernel.geom.Rectangle r2 = r8.moveRight(r2)
            float r3 = r8.getWidth()
            float r4 = r13.getTextIndent()
            float r3 = r3 - r4
            r2.setWidth(r3)
        L_0x0072:
            com.itextpdf.layout.layout.LayoutArea r2 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r3 = r72.getArea()
            int r3 = r3.getPageNumber()
            com.itextpdf.kernel.geom.Rectangle r4 = r8.clone()
            float r5 = r8.getHeight()
            com.itextpdf.kernel.geom.Rectangle r4 = r4.moveUp(r5)
            com.itextpdf.kernel.geom.Rectangle r4 = r4.setHeight(r14)
            com.itextpdf.kernel.geom.Rectangle r4 = r4.setWidth(r14)
            r2.<init>(r3, r4)
            r6.occupiedArea = r2
            r71.updateChildrenParent()
            r2 = 0
            com.itextpdf.layout.property.RenderingMode r3 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            r15 = 123(0x7b, float:1.72E-43)
            java.lang.Object r4 = r6.getProperty(r15)
            boolean r3 = r3.equals(r4)
            r5 = 0
            r4 = 1
            if (r3 == 0) goto L_0x00bc
            boolean r3 = r71.hasChildRendererInHtmlMode()
            if (r3 == 0) goto L_0x00bc
            float[] r3 = com.itextpdf.layout.renderer.LineHeightHelper.getActualAscenderDescender(r71)
            r15 = r3[r5]
            r6.maxAscent = r15
            r15 = r3[r4]
            r6.maxDescent = r15
            goto L_0x00c0
        L_0x00bc:
            r6.maxAscent = r14
            r6.maxDescent = r14
        L_0x00c0:
            r6.maxTextAscent = r14
            r6.maxTextDescent = r14
            r3 = -525502228(0xffffffffe0ad78ec, float:-1.0E20)
            r6.maxBlockAscent = r3
            r3 = 1621981420(0x60ad78ec, float:1.0E20)
            r6.maxBlockDescent = r3
            r3 = 0
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r15 = new com.itextpdf.layout.minmaxwidth.MinMaxWidth
            r15.<init>()
            if (r12 == 0) goto L_0x00dc
            com.itextpdf.layout.renderer.SumSumWidthHandler r5 = new com.itextpdf.layout.renderer.SumSumWidthHandler
            r5.<init>(r15)
            goto L_0x00e1
        L_0x00dc:
            com.itextpdf.layout.renderer.MaxSumWidthHandler r5 = new com.itextpdf.layout.renderer.MaxSumWidthHandler
            r5.<init>(r15)
        L_0x00e1:
            r71.resolveChildrenFonts()
            int r11 = r71.trimFirst()
            com.itextpdf.layout.property.BaseDirection r14 = r71.applyOtf()
            r6.updateBidiLevels(r11, r14)
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            java.util.LinkedHashMap r24 = new java.util.LinkedHashMap
            r24.<init>()
            r25 = r24
            java.util.ArrayList r24 = new java.util.ArrayList
            r24.<init>()
            r26 = r24
            java.util.ArrayList r24 = new java.util.ArrayList
            r24.<init>()
            r27 = r24
            r24 = 0
            java.util.HashMap r28 = new java.util.HashMap
            r28.<init>()
            r29 = r28
            r30 = r24
        L_0x0117:
            java.util.List r4 = r6.childRenderers
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x0b57
            java.util.List r4 = r6.childRenderers
            java.lang.Object r4 = r4.get(r3)
            com.itextpdf.layout.renderer.IRenderer r4 = (com.itextpdf.layout.renderer.IRenderer) r4
            r37 = 0
            com.itextpdf.kernel.geom.Rectangle r7 = new com.itextpdf.kernel.geom.Rectangle
            float r31 = r8.getX()
            r38 = r0
            float r0 = r31 + r2
            r39 = r11
            float r11 = r8.getY()
            float r31 = r8.getWidth()
            r40 = r15
            float r15 = r31 - r2
            r41 = r14
            float r14 = r8.getHeight()
            r7.<init>(r0, r11, r15, r14)
            boolean r0 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r0 == 0) goto L_0x0159
            r0 = 15
            r4.deleteOwnProperty(r0)
            r0 = 78
            r4.deleteOwnProperty(r0)
            goto L_0x01c5
        L_0x0159:
            boolean r0 = r4 instanceof com.itextpdf.layout.renderer.TabRenderer
            if (r0 == 0) goto L_0x01c5
            if (r21 == 0) goto L_0x019a
            java.util.List r0 = r6.childRenderers
            int r11 = r3 + -1
            java.lang.Object r0 = r0.get(r11)
            com.itextpdf.layout.renderer.IRenderer r0 = (com.itextpdf.layout.renderer.IRenderer) r0
            com.itextpdf.layout.layout.LayoutContext r11 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r14 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r15 = r72.getArea()
            int r15 = r15.getPageNumber()
            r14.<init>(r15, r7)
            r11.<init>((com.itextpdf.layout.layout.LayoutArea) r14, (boolean) r9)
            r0.layout(r11)
            com.itextpdf.layout.layout.LayoutArea r11 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            float r11 = r11.getWidth()
            float r2 = r2 + r11
            com.itextpdf.layout.layout.LayoutArea r11 = r0.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r11 = r11.getBBox()
            float r11 = r11.getWidth()
            r5.updateMaxChildWidth(r11)
        L_0x019a:
            float r0 = r8.getWidth()
            com.itextpdf.layout.element.TabStop r0 = r6.calculateTab(r4, r2, r0)
            java.util.List r11 = r6.childRenderers
            int r11 = r11.size()
            r14 = 1
            int r11 = r11 - r14
            if (r3 != r11) goto L_0x01b0
            r0 = 0
            r21 = r0
            goto L_0x01b2
        L_0x01b0:
            r21 = r0
        L_0x01b2:
            if (r21 == 0) goto L_0x01c5
            r30 = r3
            int r3 = r3 + 1
            r7 = r72
            r0 = r38
            r11 = r39
            r15 = r40
            r14 = r41
            r4 = 1
            goto L_0x0117
        L_0x01c5:
            if (r21 == 0) goto L_0x01dc
            com.itextpdf.layout.property.TabAlignment r0 = r21.getTabAlignment()
            com.itextpdf.layout.property.TabAlignment r11 = com.itextpdf.layout.property.TabAlignment.ANCHOR
            if (r0 != r11) goto L_0x01dc
            boolean r0 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r0 == 0) goto L_0x01dc
            r0 = 66
            java.lang.Character r11 = r21.getTabAnchor()
            r4.setProperty(r0, r11)
        L_0x01dc:
            r0 = 77
            java.lang.Object r11 = r4.getProperty(r0)
            r14 = 0
            boolean r15 = r4.hasOwnProperty(r0)
            boolean r0 = r11 instanceof com.itextpdf.layout.property.UnitValue
            if (r0 == 0) goto L_0x022b
            r0 = r11
            com.itextpdf.layout.property.UnitValue r0 = (com.itextpdf.layout.property.UnitValue) r0
            boolean r0 = r0.isPercentValue()
            if (r0 == 0) goto L_0x022b
            r0 = r11
            com.itextpdf.layout.property.UnitValue r0 = (com.itextpdf.layout.property.UnitValue) r0
            float r0 = r0.getValue()
            r31 = 1120403456(0x42c80000, float:100.0)
            float r0 = r0 / r31
            com.itextpdf.layout.layout.LayoutArea r31 = r72.getArea()
            com.itextpdf.kernel.geom.Rectangle r31 = r31.getBBox()
            float r31 = r31.getWidth()
            float r0 = r0 * r31
            float r0 = r6.decreaseRelativeWidthByChildAdditionalWidth(r4, r0)
            r19 = 0
            int r31 = (r0 > r19 ? 1 : (r0 == r19 ? 0 : -1))
            if (r31 <= 0) goto L_0x0226
            r31 = r14
            com.itextpdf.layout.property.UnitValue r14 = com.itextpdf.layout.property.UnitValue.createPointValue(r0)
            r32 = r0
            r0 = 77
            r4.setProperty(r0, r14)
            r14 = 1
            goto L_0x022f
        L_0x0226:
            r32 = r0
            r31 = r14
            goto L_0x022d
        L_0x022b:
            r31 = r14
        L_0x022d:
            r14 = r31
        L_0x022f:
            r0 = 99
            java.lang.Object r0 = r4.getProperty(r0)
            com.itextpdf.layout.property.FloatPropertyValue r0 = (com.itextpdf.layout.property.FloatPropertyValue) r0
            r43 = r2
            boolean r2 = r4 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r2 == 0) goto L_0x0245
            boolean r2 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r4, r0)
            if (r2 == 0) goto L_0x0245
            r2 = 1
            goto L_0x0246
        L_0x0245:
            r2 = 0
        L_0x0246:
            r44 = r2
            if (r44 == 0) goto L_0x0480
            r31 = 0
            r2 = r4
            com.itextpdf.layout.renderer.AbstractRenderer r2 = (com.itextpdf.layout.renderer.AbstractRenderer) r2
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r2 = com.itextpdf.layout.renderer.FloatingHelper.calculateMinMaxWidthForFloat(r2, r0)
            float r32 = r2.getMaxWidth()
            if (r1 != 0) goto L_0x0273
            if (r3 <= 0) goto L_0x0273
            r46 = r1
            r1 = 103(0x67, float:1.44E-43)
            java.lang.Object r18 = r6.getProperty(r1)
            r33 = r18
            com.itextpdf.layout.property.OverflowPropertyValue r33 = (com.itextpdf.layout.property.OverflowPropertyValue) r33
            r34 = 1
            r47 = r12
            com.itextpdf.layout.property.OverflowPropertyValue r12 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            r6.setProperty(r1, r12)
            r1 = r34
            goto L_0x027b
        L_0x0273:
            r46 = r1
            r47 = r12
            r33 = r38
            r1 = r46
        L_0x027b:
            boolean r12 = r13.isFloatOverflowedToNextPageWithNothing()
            if (r12 != 0) goto L_0x02c5
            boolean r12 = r27.isEmpty()
            if (r12 == 0) goto L_0x02c5
            if (r20 == 0) goto L_0x0299
            float r12 = r7.getWidth()
            int r12 = (r32 > r12 ? 1 : (r32 == r12 ? 0 : -1))
            if (r12 > 0) goto L_0x0292
            goto L_0x0299
        L_0x0292:
            r49 = r0
            r34 = r1
            r48 = r7
            goto L_0x02cb
        L_0x0299:
            com.itextpdf.layout.layout.LayoutContext r12 = new com.itextpdf.layout.layout.LayoutContext
            r34 = r1
            com.itextpdf.layout.layout.LayoutArea r1 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r35 = r72.getArea()
            r48 = r7
            int r7 = r35.getPageNumber()
            com.itextpdf.layout.layout.LayoutArea r35 = r72.getArea()
            com.itextpdf.kernel.geom.Rectangle r35 = r35.getBBox()
            r49 = r0
            com.itextpdf.kernel.geom.Rectangle r0 = r35.clone()
            r1.<init>(r7, r0)
            r0 = 0
            r12.<init>(r1, r0, r10, r9)
            com.itextpdf.layout.layout.LayoutResult r31 = r4.layout(r12)
            r0 = r31
            goto L_0x02cd
        L_0x02c5:
            r49 = r0
            r34 = r1
            r48 = r7
        L_0x02cb:
            r0 = r31
        L_0x02cd:
            if (r14 == 0) goto L_0x02dc
            if (r15 == 0) goto L_0x02d7
            r1 = 77
            r4.setProperty(r1, r11)
            goto L_0x02dc
        L_0x02d7:
            r1 = 77
            r4.deleteOwnProperty(r1)
        L_0x02dc:
            r1 = 0
            r7 = 0
            boolean r12 = r0 instanceof com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            r31 = 953267991(0x38d1b717, float:1.0E-4)
            if (r12 == 0) goto L_0x0308
            if (r14 != 0) goto L_0x02f2
            r12 = r0
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r12 = (com.itextpdf.layout.layout.MinMaxWidthLayoutResult) r12
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r12 = r12.getMinMaxWidth()
            float r1 = r12.getMinWidth()
        L_0x02f2:
            r12 = r0
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r12 = (com.itextpdf.layout.layout.MinMaxWidthLayoutResult) r12
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r12 = r12.getMinMaxWidth()
            float r7 = r12.getMaxWidth()
            float r12 = r1 + r31
            r5.updateMinChildWidth(r12)
            float r12 = r7 + r31
            r5.updateMaxChildWidth(r12)
            goto L_0x031a
        L_0x0308:
            float r12 = r2.getMinWidth()
            float r12 = r12 + r31
            r5.updateMinChildWidth(r12)
            float r12 = r2.getMaxWidth()
            float r12 = r12 + r31
            r5.updateMaxChildWidth(r12)
        L_0x031a:
            if (r0 != 0) goto L_0x0335
            boolean r12 = r13.isFloatOverflowedToNextPageWithNothing()
            if (r12 != 0) goto L_0x0335
            r12 = r27
            r12.add(r4)
            r27 = r1
            r31 = r2
            r50 = r5
            r5 = r25
            r2 = r26
            r25 = r7
            goto L_0x0439
        L_0x0335:
            r12 = r27
            boolean r27 = r13.isFloatOverflowedToNextPageWithNothing()
            if (r27 != 0) goto L_0x041a
            r27 = r1
            int r1 = r0.getStatus()
            r31 = r2
            r2 = 3
            if (r1 != r2) goto L_0x0354
            r50 = r5
            r5 = r25
            r2 = r26
            r25 = r7
            r7 = r49
            goto L_0x0428
        L_0x0354:
            int r1 = r0.getStatus()
            r2 = 2
            if (r1 != r2) goto L_0x03ef
            r23 = 1
            boolean r1 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r1 == 0) goto L_0x03c3
            com.itextpdf.layout.renderer.LineRenderer[] r1 = r6.splitNotFittingFloat(r3, r0)
            com.itextpdf.layout.renderer.IRenderer r2 = r0.getSplitRenderer()
            r50 = r5
            boolean r5 = r2 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r5 == 0) goto L_0x037b
            r5 = r2
            com.itextpdf.layout.renderer.TextRenderer r5 = (com.itextpdf.layout.renderer.TextRenderer) r5
            r5.trimFirst()
            r5 = r2
            com.itextpdf.layout.renderer.TextRenderer r5 = (com.itextpdf.layout.renderer.TextRenderer) r5
            r5.trimLast()
        L_0x037b:
            com.itextpdf.layout.layout.LayoutArea r5 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r5 = r5.getBBox()
            com.itextpdf.layout.layout.LayoutArea r16 = r72.getArea()
            com.itextpdf.kernel.geom.Rectangle r16 = r16.getBBox()
            r19 = r2
            float r2 = r16.getWidth()
            r5.setWidth(r2)
            com.itextpdf.layout.layout.LineLayoutResult r2 = new com.itextpdf.layout.layout.LineLayoutResult
            r52 = 2
            com.itextpdf.layout.layout.LayoutArea r5 = r6.occupiedArea
            r16 = 0
            r54 = r1[r16]
            r16 = 1
            r55 = r1[r16]
            r56 = 0
            r51 = r2
            r53 = r5
            r51.<init>(r52, r53, r54, r55, r56)
            r22 = r2
            r15 = r3
            r11 = r12
            r45 = r13
            r5 = r25
            r7 = r26
            r31 = r29
            r12 = r33
            r46 = r34
            r25 = r10
            r34 = r30
            r10 = r50
            goto L_0x0b79
        L_0x03c3:
            r50 = r5
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            com.itextpdf.layout.renderer.IRenderer r2 = r0.getSplitRenderer()
            r5 = r25
            r5.put(r1, r2)
            com.itextpdf.layout.renderer.IRenderer r1 = r0.getOverflowRenderer()
            r2 = r26
            r2.add(r1)
            com.itextpdf.layout.renderer.IRenderer r1 = r0.getSplitRenderer()
            com.itextpdf.layout.layout.LayoutArea r1 = r1.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r25 = r7
            r7 = r49
            r6.adjustLineOnFloatPlaced(r8, r3, r7, r1)
            goto L_0x0439
        L_0x03ef:
            r50 = r5
            r5 = r25
            r2 = r26
            r25 = r7
            r7 = r49
            r1 = 1
            r23 = r1
            boolean r1 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r1 == 0) goto L_0x040c
            r1 = r4
            com.itextpdf.layout.renderer.TextRenderer r1 = (com.itextpdf.layout.renderer.TextRenderer) r1
            r1.trimFirst()
            r1 = r4
            com.itextpdf.layout.renderer.TextRenderer r1 = (com.itextpdf.layout.renderer.TextRenderer) r1
            r1.trimLast()
        L_0x040c:
            com.itextpdf.layout.layout.LayoutArea r1 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r6.adjustLineOnFloatPlaced(r8, r3, r7, r1)
            r49 = r7
            goto L_0x0439
        L_0x041a:
            r27 = r1
            r31 = r2
            r50 = r5
            r5 = r25
            r2 = r26
            r25 = r7
            r7 = r49
        L_0x0428:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r49 = r7
            r7 = 0
            r5.put(r1, r7)
            r2.add(r4)
            r1 = 1
            r13.setFloatOverflowedToNextPageWithNothing(r1)
        L_0x0439:
            int r3 = r3 + 1
            if (r20 != 0) goto L_0x0465
            if (r0 == 0) goto L_0x0465
            int r1 = r0.getStatus()
            r7 = 3
            if (r1 != r7) goto L_0x0465
            boolean r1 = r10.isEmpty()
            if (r1 == 0) goto L_0x0465
            boolean r1 = r71.isFirstOnRootArea()
            if (r1 == 0) goto L_0x0465
            r7 = r2
            r15 = r3
            r25 = r10
            r11 = r12
            r45 = r13
            r31 = r29
            r12 = r33
            r46 = r34
            r10 = r50
            r34 = r30
            goto L_0x0b79
        L_0x0465:
            r7 = r72
            r26 = r2
            r25 = r5
            r27 = r12
            r0 = r33
            r1 = r34
            r11 = r39
            r15 = r40
            r14 = r41
            r2 = r43
            r12 = r47
            r5 = r50
            r4 = 1
            goto L_0x0117
        L_0x0480:
            r49 = r0
            r46 = r1
            r50 = r5
            r48 = r7
            r47 = r12
            r5 = r25
            r2 = r26
            r12 = r27
            r0 = 0
            boolean r7 = r6.isInlineBlockChild(r4)
            java.lang.String r1 = "Inline block element does not fit into parent element and will be clipped"
            r25 = r10
            r26 = 981668463(0x3a83126f, float:0.001)
            if (r14 != 0) goto L_0x0561
            if (r7 == 0) goto L_0x0558
            boolean r10 = r4 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r10 == 0) goto L_0x0558
            r10 = r4
            com.itextpdf.layout.renderer.AbstractRenderer r10 = (com.itextpdf.layout.renderer.AbstractRenderer) r10
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r0 = r10.getMinMaxWidth()
            float r10 = r0.getMaxWidth()
            com.itextpdf.layout.layout.LayoutArea r31 = r72.getArea()
            com.itextpdf.kernel.geom.Rectangle r31 = r31.getBBox()
            float r31 = r31.getWidth()
            float r32 = r13.getTextIndent()
            r51 = r2
            float r2 = r31 - r32
            if (r47 != 0) goto L_0x04f3
            float r31 = r48.getWidth()
            float r31 = r31 + r26
            int r31 = (r10 > r31 ? 1 : (r10 == r31 ? 0 : -1))
            if (r31 <= 0) goto L_0x04f3
            float r31 = r48.getWidth()
            int r31 = (r31 > r2 ? 1 : (r31 == r2 ? 0 : -1))
            if (r31 == 0) goto L_0x04f3
            com.itextpdf.layout.layout.LineLayoutResult r52 = new com.itextpdf.layout.layout.LineLayoutResult
            r32 = 3
            r33 = 0
            r34 = 0
            r31 = r52
            r35 = r4
            r36 = r4
            r31.<init>(r32, r33, r34, r35, r36)
            r34 = r1
            r32 = r2
            r37 = r31
            r2 = r48
            r31 = r5
            goto L_0x0544
        L_0x04f3:
            float r10 = r10 + r26
            r31 = r5
            float r5 = java.lang.Math.min(r10, r2)
            r32 = r2
            r2 = 103(0x67, float:1.44E-43)
            java.lang.Object r33 = r6.getProperty(r2)
            com.itextpdf.layout.property.OverflowPropertyValue r33 = (com.itextpdf.layout.property.OverflowPropertyValue) r33
            boolean r2 = isOverflowFit(r33)
            if (r2 != 0) goto L_0x0515
            float r2 = r0.getMinWidth()
            float r2 = r2 + r26
            float r5 = java.lang.Math.max(r2, r5)
        L_0x0515:
            r2 = r48
            r2.setWidth(r5)
            float r33 = r0.getMinWidth()
            float r34 = r2.getWidth()
            int r33 = (r33 > r34 ? 1 : (r33 == r34 ? 0 : -1))
            if (r33 <= 0) goto L_0x0540
            r33 = r5
            org.slf4j.Logger r5 = logger
            boolean r34 = r5.isWarnEnabled()
            if (r34 == 0) goto L_0x0533
            r5.warn(r1)
        L_0x0533:
            r34 = r1
            r5 = 1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r5)
            r5 = 26
            r4.setProperty(r5, r1)
            goto L_0x0544
        L_0x0540:
            r34 = r1
            r33 = r5
        L_0x0544:
            float r1 = r0.getChildrenMaxWidth()
            float r1 = r1 + r26
            r0.setChildrenMaxWidth(r1)
            float r1 = r0.getChildrenMinWidth()
            float r1 = r1 + r26
            r0.setChildrenMinWidth(r1)
            r10 = r0
            goto L_0x056a
        L_0x0558:
            r34 = r1
            r51 = r2
            r31 = r5
            r2 = r48
            goto L_0x0569
        L_0x0561:
            r34 = r1
            r51 = r2
            r31 = r5
            r2 = r48
        L_0x0569:
            r10 = r0
        L_0x056a:
            if (r37 != 0) goto L_0x05e1
            if (r46 != 0) goto L_0x0584
            if (r3 <= 0) goto L_0x0584
            r0 = 103(0x67, float:1.44E-43)
            java.lang.Object r1 = r6.getProperty(r0)
            com.itextpdf.layout.property.OverflowPropertyValue r1 = (com.itextpdf.layout.property.OverflowPropertyValue) r1
            r5 = 1
            r32 = r1
            com.itextpdf.layout.property.OverflowPropertyValue r1 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            r6.setProperty(r0, r1)
            r1 = r5
            r0 = r32
            goto L_0x0588
        L_0x0584:
            r0 = r38
            r1 = r46
        L_0x0588:
            boolean r5 = com.itextpdf.layout.renderer.TypographyUtils.isPdfCalligraphAvailable()
            if (r5 == 0) goto L_0x0597
            boolean r5 = isTextRendererAndRequiresSpecialScriptPreLayoutProcessing(r4)
            if (r5 == 0) goto L_0x0597
            r6.specialScriptPreLayoutProcessing(r3)
        L_0x0597:
            com.itextpdf.layout.layout.LayoutContext r5 = new com.itextpdf.layout.layout.LayoutContext
            r32 = r0
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r33 = r72.getArea()
            r35 = r1
            int r1 = r33.getPageNumber()
            r0.<init>(r1, r2)
            r5.<init>((com.itextpdf.layout.layout.LayoutArea) r0, (boolean) r9)
            com.itextpdf.layout.layout.LayoutResult r0 = r4.layout(r5)
            r5 = r29
            updateSpecialScriptLayoutResults(r5, r4, r3, r0)
            boolean r1 = r0 instanceof com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            if (r1 == 0) goto L_0x05d8
            if (r10 == 0) goto L_0x05d8
            r1 = r0
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r1 = (com.itextpdf.layout.layout.MinMaxWidthLayoutResult) r1
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r1 = r1.getMinMaxWidth()
            float r29 = r1.getChildrenMaxWidth()
            r33 = r0
            float r0 = r29 + r26
            r1.setChildrenMaxWidth(r0)
            float r0 = r1.getChildrenMinWidth()
            float r0 = r0 + r26
            r1.setChildrenMinWidth(r0)
            goto L_0x05da
        L_0x05d8:
            r33 = r0
        L_0x05da:
            r1 = r32
            r0 = r33
            r46 = r35
            goto L_0x05e7
        L_0x05e1:
            r5 = r29
            r0 = r37
            r1 = r38
        L_0x05e7:
            if (r14 == 0) goto L_0x05fb
            if (r15 == 0) goto L_0x05f3
            r48 = r2
            r2 = 77
            r4.setProperty(r2, r11)
            goto L_0x05fd
        L_0x05f3:
            r48 = r2
            r2 = 77
            r4.deleteOwnProperty(r2)
            goto L_0x05fd
        L_0x05fb:
            r48 = r2
        L_0x05fd:
            r2 = 0
            r26 = 0
            r29 = r2
            boolean r2 = r0 instanceof com.itextpdf.layout.layout.MinMaxWidthLayoutResult
            if (r2 == 0) goto L_0x0625
            if (r14 != 0) goto L_0x0614
            r2 = r0
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r2 = (com.itextpdf.layout.layout.MinMaxWidthLayoutResult) r2
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r2 = r2.getMinMaxWidth()
            float r2 = r2.getMinWidth()
            goto L_0x0616
        L_0x0614:
            r2 = r29
        L_0x0616:
            r29 = r0
            com.itextpdf.layout.layout.MinMaxWidthLayoutResult r29 = (com.itextpdf.layout.layout.MinMaxWidthLayoutResult) r29
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r29 = r29.getMinMaxWidth()
            float r26 = r29.getMaxWidth()
            r29 = r2
            goto L_0x0631
        L_0x0625:
            if (r10 == 0) goto L_0x0631
            float r2 = r10.getMinWidth()
            float r26 = r10.getMaxWidth()
            r29 = r2
        L_0x0631:
            r2 = 0
            r32 = 0
            r33 = r2
            boolean r2 = r4 instanceof com.itextpdf.layout.renderer.ILeafElementRenderer
            if (r2 == 0) goto L_0x0686
            int r2 = r0.getStatus()
            r35 = r10
            r10 = 3
            if (r2 == r10) goto L_0x0680
            com.itextpdf.layout.property.RenderingMode r2 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            r16 = r11
            r10 = 123(0x7b, float:1.72E-43)
            java.lang.Object r11 = r4.getProperty(r10)
            boolean r2 = r2.equals(r11)
            if (r2 == 0) goto L_0x066b
            boolean r2 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r2 == 0) goto L_0x066b
            r2 = r4
            com.itextpdf.layout.renderer.TextRenderer r2 = (com.itextpdf.layout.renderer.TextRenderer) r2
            float[] r2 = com.itextpdf.layout.renderer.LineHeightHelper.getActualAscenderDescender(r2)
            r11 = 0
            r17 = r2[r11]
            r24 = 1
            r32 = r2[r24]
            r10 = r17
            r2 = r32
            goto L_0x06f3
        L_0x066b:
            r11 = 0
            r2 = r4
            com.itextpdf.layout.renderer.ILeafElementRenderer r2 = (com.itextpdf.layout.renderer.ILeafElementRenderer) r2
            float r2 = r2.getAscent()
            r17 = r4
            com.itextpdf.layout.renderer.ILeafElementRenderer r17 = (com.itextpdf.layout.renderer.ILeafElementRenderer) r17
            float r32 = r17.getDescent()
            r10 = r2
            r2 = r32
            goto L_0x06f3
        L_0x0680:
            r16 = r11
            r10 = 123(0x7b, float:1.72E-43)
            r11 = 0
            goto L_0x068d
        L_0x0686:
            r35 = r10
            r16 = r11
            r10 = 123(0x7b, float:1.72E-43)
            r11 = 0
        L_0x068d:
            if (r7 == 0) goto L_0x06ef
            int r2 = r0.getStatus()
            r10 = 3
            if (r2 == r10) goto L_0x06ef
            boolean r2 = r4 instanceof com.itextpdf.layout.renderer.AbstractRenderer
            if (r2 == 0) goto L_0x06df
            r2 = r4
            com.itextpdf.layout.renderer.AbstractRenderer r2 = (com.itextpdf.layout.renderer.AbstractRenderer) r2
            java.lang.Float r2 = r2.getLastYLineRecursively()
            if (r2 != 0) goto L_0x06b2
            com.itextpdf.layout.layout.LayoutArea r36 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r36 = r36.getBBox()
            float r33 = r36.getHeight()
            r2 = r33
            goto L_0x06db
        L_0x06b2:
            com.itextpdf.layout.layout.LayoutArea r36 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r36 = r36.getBBox()
            float r36 = r36.getTop()
            float r37 = r2.floatValue()
            float r36 = r36 - r37
            float r33 = r2.floatValue()
            com.itextpdf.layout.layout.LayoutArea r37 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r37 = r37.getBBox()
            float r37 = r37.getBottom()
            float r10 = r33 - r37
            float r10 = -r10
            r32 = r10
            r2 = r36
        L_0x06db:
            r10 = r2
            r2 = r32
            goto L_0x06f3
        L_0x06df:
            com.itextpdf.layout.layout.LayoutArea r2 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getHeight()
            r10 = r2
            r2 = r32
            goto L_0x06f3
        L_0x06ef:
            r2 = r32
            r10 = r33
        L_0x06f3:
            boolean r11 = r0 instanceof com.itextpdf.layout.layout.TextLayoutResult
            if (r11 == 0) goto L_0x0702
            r11 = r0
            com.itextpdf.layout.layout.TextLayoutResult r11 = (com.itextpdf.layout.layout.TextLayoutResult) r11
            boolean r11 = r11.isSplitForcedByNewline()
            if (r11 == 0) goto L_0x0702
            r11 = 1
            goto L_0x0703
        L_0x0702:
            r11 = 0
        L_0x0703:
            r33 = r14
            int r14 = r0.getStatus()
            r36 = r15
            r15 = 1
            if (r14 != r15) goto L_0x0713
            if (r11 == 0) goto L_0x0711
            goto L_0x0713
        L_0x0711:
            r14 = 0
            goto L_0x0714
        L_0x0713:
            r14 = 1
        L_0x0714:
            r15 = 0
            if (r14 == 0) goto L_0x077d
            r37 = r15
            boolean r15 = r0 instanceof com.itextpdf.layout.layout.TextLayoutResult
            if (r15 == 0) goto L_0x0778
            r15 = r0
            com.itextpdf.layout.layout.TextLayoutResult r15 = (com.itextpdf.layout.layout.TextLayoutResult) r15
            boolean r15 = r15.isWordHasBeenSplit()
            if (r15 == 0) goto L_0x0773
            r15 = r4
            com.itextpdf.layout.renderer.TextRenderer r15 = (com.itextpdf.layout.renderer.TextRenderer) r15
            r38 = r0
            r0 = 1
            boolean r15 = r15.textContainsSpecialScriptGlyphs(r0)
            if (r15 != 0) goto L_0x0770
            if (r46 == 0) goto L_0x0739
            r0 = 103(0x67, float:1.44E-43)
            r6.setProperty(r0, r1)
        L_0x0739:
            com.itextpdf.layout.layout.LayoutContext r0 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r15 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r42 = r72.getArea()
            r52 = r1
            int r1 = r42.getPageNumber()
            r15.<init>(r1, r8)
            r0.<init>((com.itextpdf.layout.layout.LayoutArea) r15, (boolean) r9)
            com.itextpdf.layout.layout.LayoutResult r0 = r4.layout(r0)
            if (r46 == 0) goto L_0x075a
            com.itextpdf.layout.property.OverflowPropertyValue r1 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            r15 = 103(0x67, float:1.44E-43)
            r6.setProperty(r15, r1)
        L_0x075a:
            boolean r1 = r0 instanceof com.itextpdf.layout.layout.TextLayoutResult
            if (r1 == 0) goto L_0x076a
            r1 = r0
            com.itextpdf.layout.layout.TextLayoutResult r1 = (com.itextpdf.layout.layout.TextLayoutResult) r1
            boolean r1 = r1.isWordHasBeenSplit()
            if (r1 != 0) goto L_0x076a
            r1 = 1
            r15 = r1
            goto L_0x076c
        L_0x076a:
            r15 = r37
        L_0x076c:
            r37 = r15
            r15 = r3
            goto L_0x07b8
        L_0x0770:
            r52 = r1
            goto L_0x0783
        L_0x0773:
            r38 = r0
            r52 = r1
            goto L_0x0783
        L_0x0778:
            r38 = r0
            r52 = r1
            goto L_0x0783
        L_0x077d:
            r38 = r0
            r52 = r1
            r37 = r15
        L_0x0783:
            if (r14 == 0) goto L_0x07b7
            if (r11 != 0) goto L_0x07b7
            java.util.List r0 = r6.childRenderers
            java.lang.Object r0 = r0.get(r3)
            boolean r0 = r0 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r0 == 0) goto L_0x07b7
            java.util.List r0 = r6.childRenderers
            java.lang.Object r0 = r0.get(r3)
            com.itextpdf.layout.renderer.TextRenderer r0 = (com.itextpdf.layout.renderer.TextRenderer) r0
            r1 = 1
            boolean r0 = r0.textContainsSpecialScriptGlyphs(r1)
            if (r0 == 0) goto L_0x07b7
            com.itextpdf.layout.renderer.LineRenderer$LastFittingChildRendererData r0 = r6.getIndexAndLayoutResultOfTheLastRendererToRemainOnTheLine(r3, r5, r9, r12)
            int r1 = r0.childIndex
            float r1 = getCurWidthSpecialScriptsDecrement(r3, r1, r5)
            float r1 = r43 - r1
            int r3 = r0.childIndex
            com.itextpdf.layout.layout.LayoutResult r15 = r0.childLayoutResult
            r43 = r1
            r38 = r15
            r15 = r3
            goto L_0x07b8
        L_0x07b7:
            r15 = r3
        L_0x07b8:
            if (r37 != 0) goto L_0x07f8
            float r0 = r6.maxAscent
            float r0 = java.lang.Math.max(r0, r10)
            r6.maxAscent = r0
            boolean r0 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r0 == 0) goto L_0x07cf
            float r0 = r6.maxTextAscent
            float r0 = java.lang.Math.max(r0, r10)
            r6.maxTextAscent = r0
            goto L_0x07d9
        L_0x07cf:
            if (r44 != 0) goto L_0x07d9
            float r0 = r6.maxBlockAscent
            float r0 = java.lang.Math.max(r0, r10)
            r6.maxBlockAscent = r0
        L_0x07d9:
            float r0 = r6.maxDescent
            float r0 = java.lang.Math.min(r0, r2)
            r6.maxDescent = r0
            boolean r0 = r4 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r0 == 0) goto L_0x07ee
            float r0 = r6.maxTextDescent
            float r0 = java.lang.Math.min(r0, r2)
            r6.maxTextDescent = r0
            goto L_0x07f8
        L_0x07ee:
            if (r44 != 0) goto L_0x07f8
            float r0 = r6.maxBlockDescent
            float r0 = java.lang.Math.min(r0, r2)
            r6.maxBlockDescent = r0
        L_0x07f8:
            float r0 = r6.maxAscent
            float r1 = r6.maxDescent
            float r3 = r0 - r1
            if (r20 == 0) goto L_0x0802
            r0 = 0
            goto L_0x0806
        L_0x0802:
            float r0 = r13.getTextIndent()
        L_0x0806:
            r42 = r0
            if (r21 == 0) goto L_0x0949
            com.itextpdf.layout.property.TabAlignment r0 = com.itextpdf.layout.property.TabAlignment.LEFT
            com.itextpdf.layout.property.TabAlignment r1 = r21.getTabAlignment()
            if (r0 == r1) goto L_0x084e
            if (r14 != 0) goto L_0x084e
            java.util.List r0 = r6.childRenderers
            int r0 = r0.size()
            r24 = 1
            int r0 = r0 + -1
            if (r0 == r15) goto L_0x0850
            java.util.List r0 = r6.childRenderers
            int r1 = r15 + 1
            java.lang.Object r0 = r0.get(r1)
            boolean r0 = r0 instanceof com.itextpdf.layout.renderer.TabRenderer
            if (r0 == 0) goto L_0x082d
            goto L_0x0850
        L_0x082d:
            r53 = r10
            r54 = r11
            r57 = r12
            r45 = r13
            r58 = r31
            r24 = r34
            r13 = r48
            r10 = r50
            r55 = r51
            r51 = r2
            r11 = r3
            r12 = r4
            r31 = r5
            r34 = r30
            r30 = r49
            r49 = r52
            r4 = 0
            goto L_0x0968
        L_0x084e:
            r24 = 1
        L_0x0850:
            java.util.List r0 = r6.childRenderers
            r1 = r30
            java.lang.Object r0 = r0.get(r1)
            com.itextpdf.layout.renderer.IRenderer r0 = (com.itextpdf.layout.renderer.IRenderer) r0
            java.util.ArrayList r30 = new java.util.ArrayList
            r30.<init>()
            r53 = r30
            r30 = r0
            java.util.List r0 = r6.childRenderers
            r54 = r2
            int r2 = r1 + 1
            r55 = r1
            int r1 = r15 + 1
            java.util.List r0 = r0.subList(r2, r1)
            r2 = r53
            r2.addAll(r0)
            r1 = r30
            r30 = r49
            r0 = r71
            r53 = r10
            r10 = r34
            r49 = r52
            r34 = r55
            r52 = r1
            r1 = r8
            r45 = r13
            r13 = r48
            r55 = r51
            r51 = r54
            r48 = r2
            r54 = r11
            r11 = 3
            r2 = r43
            r11 = r3
            r3 = r21
            r24 = r10
            r57 = r12
            r10 = 1
            r12 = r4
            r4 = r48
            r58 = r31
            r10 = r50
            r31 = r5
            r5 = r52
            float r0 = r0.calculateTab(r1, r2, r3, r4, r5)
            com.itextpdf.layout.layout.LayoutContext r1 = new com.itextpdf.layout.layout.LayoutContext
            com.itextpdf.layout.layout.LayoutArea r2 = new com.itextpdf.layout.layout.LayoutArea
            com.itextpdf.layout.layout.LayoutArea r3 = r72.getArea()
            int r3 = r3.getPageNumber()
            r2.<init>(r3, r13)
            r1.<init>((com.itextpdf.layout.layout.LayoutArea) r2, (boolean) r9)
            r2 = r52
            r2.layout(r1)
            r1 = 0
            java.util.Iterator r3 = r48.iterator()
        L_0x08c9:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x08ed
            java.lang.Object r4 = r3.next()
            com.itextpdf.layout.renderer.IRenderer r4 = (com.itextpdf.layout.renderer.IRenderer) r4
            float r5 = r0 + r1
            r52 = r2
            r2 = 0
            r4.move(r5, r2)
            com.itextpdf.layout.layout.LayoutArea r2 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getWidth()
            float r1 = r1 + r2
            r2 = r52
            goto L_0x08c9
        L_0x08ed:
            r52 = r2
            com.itextpdf.layout.renderer.IRenderer r2 = r38.getSplitRenderer()
            if (r2 == 0) goto L_0x0911
            com.itextpdf.layout.renderer.IRenderer r2 = r38.getSplitRenderer()
            float r3 = r0 + r1
            com.itextpdf.layout.renderer.IRenderer r4 = r38.getSplitRenderer()
            com.itextpdf.layout.layout.LayoutArea r4 = r4.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            float r4 = r4.getWidth()
            float r3 = r3 - r4
            r4 = 0
            r2.move(r3, r4)
            goto L_0x0912
        L_0x0911:
            r4 = 0
        L_0x0912:
            com.itextpdf.layout.layout.LayoutArea r2 = r38.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getWidth()
            float r2 = r2 + r0
            com.itextpdf.layout.property.TabAlignment r3 = r21.getTabAlignment()
            com.itextpdf.layout.property.TabAlignment r5 = com.itextpdf.layout.property.TabAlignment.RIGHT
            if (r3 != r5) goto L_0x0936
            float r3 = r43 + r2
            float r5 = r21.getTabPosition()
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L_0x0936
            float r3 = r21.getTabPosition()
            goto L_0x0938
        L_0x0936:
            float r3 = r43 + r2
        L_0x0938:
            float r5 = r29 + r42
            r10.updateMinChildWidth(r5)
            float r5 = r0 + r26
            float r5 = r5 + r42
            r10.updateMaxChildWidth(r5)
            r0 = 0
            r21 = r0
            r2 = r3
            goto L_0x0997
        L_0x0949:
            r53 = r10
            r54 = r11
            r57 = r12
            r45 = r13
            r58 = r31
            r24 = r34
            r13 = r48
            r10 = r50
            r55 = r51
            r51 = r2
            r11 = r3
            r12 = r4
            r31 = r5
            r34 = r30
            r30 = r49
            r49 = r52
            r4 = 0
        L_0x0968:
            if (r21 != 0) goto L_0x0995
            com.itextpdf.layout.layout.LayoutArea r0 = r38.getOccupiedArea()
            if (r0 == 0) goto L_0x0988
            com.itextpdf.layout.layout.LayoutArea r0 = r38.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            if (r0 == 0) goto L_0x0988
            com.itextpdf.layout.layout.LayoutArea r0 = r38.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            float r0 = r0.getWidth()
            float r43 = r43 + r0
        L_0x0988:
            float r0 = r29 + r42
            r10.updateMinChildWidth(r0)
            float r0 = r26 + r42
            r10.updateMaxChildWidth(r0)
            r2 = r43
            goto L_0x0997
        L_0x0995:
            r2 = r43
        L_0x0997:
            if (r37 != 0) goto L_0x09b2
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = new com.itextpdf.kernel.geom.Rectangle
            float r3 = r8.getX()
            float r5 = r8.getY()
            float r19 = r8.getHeight()
            float r5 = r5 + r19
            float r5 = r5 - r11
            r1.<init>(r3, r5, r2, r11)
            r0.setBBox(r1)
        L_0x09b2:
            if (r14 == 0) goto L_0x0b25
            com.itextpdf.layout.renderer.LineRenderer[] r0 = r71.split()
            r1 = 0
            r3 = r0[r1]
            java.util.ArrayList r4 = new java.util.ArrayList
            java.util.List r5 = r6.childRenderers
            java.util.List r5 = r5.subList(r1, r15)
            r4.<init>(r5)
            r3.childRenderers = r4
            if (r37 == 0) goto L_0x09eb
            r1 = 1
            r3 = r0[r1]
            java.util.List r3 = r3.childRenderers
            r3.add(r12)
            r3 = r0[r1]
            java.util.List r1 = r3.childRenderers
            java.util.List r3 = r6.childRenderers
            int r4 = r15 + 1
            java.util.List r5 = r6.childRenderers
            int r5 = r5.size()
            java.util.List r3 = r3.subList(r4, r5)
            r1.addAll(r3)
            r19 = r2
            goto L_0x0a84
        L_0x09eb:
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r3 = 26
            java.lang.Boolean r3 = r6.getPropertyAsBoolean(r3)
            boolean r1 = r1.equals(r3)
            if (r7 == 0) goto L_0x0a01
            boolean r3 = r71.isFirstOnRootArea()
            if (r3 == 0) goto L_0x0a01
            r5 = 1
            goto L_0x0a02
        L_0x0a01:
            r5 = 0
        L_0x0a02:
            r3 = r5
            int r4 = r38.getStatus()
            r5 = 2
            if (r4 != r5) goto L_0x0a10
            if (r7 == 0) goto L_0x0a17
            if (r1 != 0) goto L_0x0a17
            if (r3 != 0) goto L_0x0a17
        L_0x0a10:
            int r4 = r38.getStatus()
            r5 = 1
            if (r4 != r5) goto L_0x0a24
        L_0x0a17:
            r4 = 0
            r5 = r0[r4]
            com.itextpdf.layout.renderer.IRenderer r4 = r38.getSplitRenderer()
            r5.addChild(r4)
            r4 = 1
            r20 = r4
        L_0x0a24:
            com.itextpdf.layout.renderer.IRenderer r4 = r38.getOverflowRenderer()
            if (r4 == 0) goto L_0x0a6a
            if (r7 == 0) goto L_0x0a39
            if (r1 != 0) goto L_0x0a39
            if (r3 != 0) goto L_0x0a39
            r4 = 1
            r5 = r0[r4]
            java.util.List r4 = r5.childRenderers
            r4.add(r12)
            goto L_0x0a6a
        L_0x0a39:
            if (r7 == 0) goto L_0x0a5e
            com.itextpdf.layout.renderer.IRenderer r4 = r38.getOverflowRenderer()
            java.util.List r4 = r4.getChildRenderers()
            int r4 = r4.size()
            if (r4 != 0) goto L_0x0a5e
            int r4 = r38.getStatus()
            r5 = 2
            if (r4 != r5) goto L_0x0a5e
            org.slf4j.Logger r4 = logger
            boolean r5 = r4.isWarnEnabled()
            if (r5 == 0) goto L_0x0a6a
            r5 = r24
            r4.warn(r5)
            goto L_0x0a6a
        L_0x0a5e:
            r4 = 1
            r5 = r0[r4]
            java.util.List r5 = r5.childRenderers
            com.itextpdf.layout.renderer.IRenderer r4 = r38.getOverflowRenderer()
            r5.add(r4)
        L_0x0a6a:
            r4 = 1
            r5 = r0[r4]
            java.util.List r4 = r5.childRenderers
            java.util.List r5 = r6.childRenderers
            r17 = r1
            int r1 = r15 + 1
            r19 = r2
            java.util.List r2 = r6.childRenderers
            int r2 = r2.size()
            java.util.List r1 = r5.subList(r1, r2)
            r4.addAll(r1)
        L_0x0a84:
            r1 = 0
            r2 = r0[r1]
            r5 = r58
            r6.replaceSplitRendererKidFloats(r5, r2)
            r2 = r0[r1]
            java.util.List r2 = r2.childRenderers
            r3 = r57
            r2.removeAll(r3)
            r2 = 1
            r4 = r0[r2]
            java.util.List r4 = r4.childRenderers
            r4.addAll(r1, r3)
            r1 = r0[r2]
            java.util.List r1 = r1.childRenderers
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0ab0
            boolean r1 = r55.isEmpty()
            if (r1 == 0) goto L_0x0ab0
            r1 = 0
            r0[r2] = r1
        L_0x0ab0:
            int r1 = r38.getStatus()
            r2 = 3
            if (r1 != r2) goto L_0x0abc
            com.itextpdf.layout.renderer.IRenderer r1 = r38.getCauseOfNothing()
            goto L_0x0ac4
        L_0x0abc:
            java.util.List r1 = r6.childRenderers
            java.lang.Object r1 = r1.get(r15)
            com.itextpdf.layout.renderer.IRenderer r1 = (com.itextpdf.layout.renderer.IRenderer) r1
        L_0x0ac4:
            r63 = r1
            r1 = 1
            r2 = r0[r1]
            if (r2 != 0) goto L_0x0ae2
            com.itextpdf.layout.layout.LineLayoutResult r2 = new com.itextpdf.layout.layout.LineLayoutResult
            r59 = 1
            com.itextpdf.layout.layout.LayoutArea r4 = r6.occupiedArea
            r17 = 0
            r61 = r0[r17]
            r62 = r0[r1]
            r58 = r2
            r60 = r4
            r58.<init>(r59, r60, r61, r62, r63)
            r1 = r2
            r2 = r55
            goto L_0x0b15
        L_0x0ae2:
            if (r20 != 0) goto L_0x0afb
            if (r23 == 0) goto L_0x0ae7
            goto L_0x0afb
        L_0x0ae7:
            com.itextpdf.layout.layout.LineLayoutResult r1 = new com.itextpdf.layout.layout.LineLayoutResult
            r65 = 3
            r66 = 0
            r2 = 0
            r67 = r0[r2]
            r2 = 1
            r68 = r0[r2]
            r69 = 0
            r64 = r1
            r64.<init>(r65, r66, r67, r68, r69)
            goto L_0x0b10
        L_0x0afb:
            com.itextpdf.layout.layout.LineLayoutResult r1 = new com.itextpdf.layout.layout.LineLayoutResult
            r65 = 2
            com.itextpdf.layout.layout.LayoutArea r2 = r6.occupiedArea
            r4 = 0
            r67 = r0[r4]
            r4 = 1
            r68 = r0[r4]
            r64 = r1
            r66 = r2
            r69 = r63
            r64.<init>(r65, r66, r67, r68, r69)
        L_0x0b10:
            r2 = r55
            r1.setFloatsOverflowedToNextPage(r2)
        L_0x0b15:
            if (r54 == 0) goto L_0x0b1b
            r4 = 1
            r1.setSplitForcedByNewline(r4)
        L_0x0b1b:
            r22 = r1
            r7 = r2
            r11 = r3
            r43 = r19
            r12 = r49
            goto L_0x0b79
        L_0x0b25:
            r19 = r2
            r2 = r55
            r3 = r57
            r5 = r58
            r20 = 1
            int r0 = r15 + 1
            r7 = r72
            r26 = r2
            r27 = r3
            r2 = r19
            r29 = r31
            r30 = r34
            r11 = r39
            r15 = r40
            r14 = r41
            r13 = r45
            r1 = r46
            r12 = r47
            r4 = 1
            r3 = r0
            r0 = r49
            r70 = r25
            r25 = r5
            r5 = r10
            r10 = r70
            goto L_0x0117
        L_0x0b57:
            r38 = r0
            r46 = r1
            r39 = r11
            r47 = r12
            r45 = r13
            r41 = r14
            r40 = r15
            r7 = r26
            r11 = r27
            r31 = r29
            r34 = r30
            r70 = r10
            r10 = r5
            r5 = r25
            r25 = r70
            r43 = r2
            r15 = r3
            r12 = r38
        L_0x0b79:
            if (r22 != 0) goto L_0x0c28
            boolean r0 = r11.isEmpty()
            if (r0 == 0) goto L_0x0b89
            boolean r0 = r7.isEmpty()
            if (r0 == 0) goto L_0x0b89
            r0 = 1
            goto L_0x0b8a
        L_0x0b89:
            r0 = 0
        L_0x0b8a:
            r13 = r0
            if (r20 != 0) goto L_0x0b8f
            if (r23 == 0) goto L_0x0b91
        L_0x0b8f:
            if (r13 != 0) goto L_0x0c19
        L_0x0b91:
            java.util.List r0 = r6.childRenderers
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0b9c
            r14 = r5
            goto L_0x0c1a
        L_0x0b9c:
            if (r13 == 0) goto L_0x0bae
            com.itextpdf.layout.layout.LineLayoutResult r0 = new com.itextpdf.layout.layout.LineLayoutResult
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            r2 = 0
            r3 = 1
            r0.<init>(r3, r1, r2, r2)
            r22 = r0
            r14 = r5
            r1 = r22
            goto L_0x0c2b
        L_0x0bae:
            if (r20 != 0) goto L_0x0bda
            if (r23 == 0) goto L_0x0bb4
            r14 = r5
            goto L_0x0bdb
        L_0x0bb4:
            boolean r0 = r11.isEmpty()
            if (r0 == 0) goto L_0x0bc0
            r0 = 0
            java.lang.Object r1 = r7.get(r0)
            goto L_0x0bc5
        L_0x0bc0:
            r0 = 0
            java.lang.Object r1 = r11.get(r0)
        L_0x0bc5:
            com.itextpdf.layout.renderer.IRenderer r1 = (com.itextpdf.layout.renderer.IRenderer) r1
            r14 = r5
            r5 = r1
            com.itextpdf.layout.layout.LineLayoutResult r16 = new com.itextpdf.layout.layout.LineLayoutResult
            r1 = 3
            r2 = 0
            r3 = 0
            r0 = r16
            r4 = r71
            r0.<init>(r1, r2, r3, r4, r5)
            r22 = r16
            r1 = r22
            goto L_0x0c2b
        L_0x0bda:
            r14 = r5
        L_0x0bdb:
            com.itextpdf.layout.renderer.LineRenderer[] r0 = r71.split()
            r1 = 0
            r2 = r0[r1]
            java.util.List r2 = r2.childRenderers
            java.util.List r3 = r6.childRenderers
            java.util.List r3 = r3.subList(r1, r15)
            r2.addAll(r3)
            r2 = r0[r1]
            r6.replaceSplitRendererKidFloats(r14, r2)
            r2 = r0[r1]
            java.util.List r2 = r2.childRenderers
            r2.removeAll(r11)
            r2 = 1
            r3 = r0[r2]
            java.util.List r3 = r3.childRenderers
            r3.addAll(r11)
            com.itextpdf.layout.layout.LineLayoutResult r3 = new com.itextpdf.layout.layout.LineLayoutResult
            r49 = 2
            com.itextpdf.layout.layout.LayoutArea r4 = r6.occupiedArea
            r51 = r0[r1]
            r52 = r0[r2]
            r53 = 0
            r48 = r3
            r50 = r4
            r48.<init>(r49, r50, r51, r52, r53)
            r1 = r3
            r1.setFloatsOverflowedToNextPage(r7)
            goto L_0x0c2b
        L_0x0c19:
            r14 = r5
        L_0x0c1a:
            com.itextpdf.layout.layout.LineLayoutResult r0 = new com.itextpdf.layout.layout.LineLayoutResult
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            r2 = 0
            r3 = 1
            r0.<init>(r3, r1, r2, r2)
            r22 = r0
            r1 = r22
            goto L_0x0c2b
        L_0x0c28:
            r14 = r5
            r1 = r22
        L_0x0c2b:
            if (r41 == 0) goto L_0x0eb5
            com.itextpdf.layout.property.BaseDirection r0 = com.itextpdf.layout.property.BaseDirection.NO_BIDI
            r2 = r41
            if (r2 == r0) goto L_0x0ea8
            r0 = 0
            int r3 = r1.getStatus()
            r4 = 2
            if (r3 != r4) goto L_0x0c44
            com.itextpdf.layout.renderer.IRenderer r3 = r1.getSplitRenderer()
            java.util.List r0 = r3.getChildRenderers()
            goto L_0x0c4f
        L_0x0c44:
            int r3 = r1.getStatus()
            r4 = 1
            if (r3 != r4) goto L_0x0c4f
            java.util.List r0 = r71.getChildRenderers()
        L_0x0c4f:
            if (r0 == 0) goto L_0x0e9b
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r16 = 0
            java.util.Iterator r17 = r0.iterator()
            r41 = r2
            r2 = r16
        L_0x0c6b:
            boolean r16 = r17.hasNext()
            if (r16 == 0) goto L_0x0d02
            java.lang.Object r16 = r17.next()
            r55 = r7
            r7 = r16
            com.itextpdf.layout.renderer.IRenderer r7 = (com.itextpdf.layout.renderer.IRenderer) r7
            if (r3 == 0) goto L_0x0c87
            r16 = r3
            r19 = r8
            r22 = r9
            r50 = r10
            goto L_0x0d0c
        L_0x0c87:
            r16 = r3
            boolean r3 = r7 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r3 == 0) goto L_0x0cd3
            r3 = r7
            com.itextpdf.layout.renderer.TextRenderer r3 = (com.itextpdf.layout.renderer.TextRenderer) r3
            com.itextpdf.io.font.otf.GlyphLine r3 = r3.line
            r19 = r8
            int r8 = r3.start
        L_0x0c96:
            r22 = r9
            int r9 = r3.end
            if (r8 >= r9) goto L_0x0cc9
            com.itextpdf.io.font.otf.Glyph r9 = r3.get(r8)
            boolean r9 = com.itextpdf.p026io.util.TextUtil.isNewLine((com.itextpdf.p026io.font.otf.Glyph) r9)
            if (r9 == 0) goto L_0x0cad
            r9 = 1
            r24 = r3
            r3 = r9
            r50 = r10
            goto L_0x0ccf
        L_0x0cad:
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r9 = new com.itextpdf.layout.renderer.LineRenderer$RendererGlyph
            r50 = r10
            com.itextpdf.io.font.otf.Glyph r10 = r3.get(r8)
            r24 = r3
            r3 = r7
            com.itextpdf.layout.renderer.TextRenderer r3 = (com.itextpdf.layout.renderer.TextRenderer) r3
            r9.<init>(r10, r3)
            r4.add(r9)
            int r8 = r8 + 1
            r9 = r22
            r3 = r24
            r10 = r50
            goto L_0x0c96
        L_0x0cc9:
            r24 = r3
            r50 = r10
            r3 = r16
        L_0x0ccf:
            r2 = r7
            com.itextpdf.layout.renderer.TextRenderer r2 = (com.itextpdf.layout.renderer.TextRenderer) r2
            goto L_0x0cf8
        L_0x0cd3:
            r19 = r8
            r22 = r9
            r50 = r10
            if (r2 == 0) goto L_0x0cf3
            boolean r3 = r5.containsKey(r2)
            if (r3 != 0) goto L_0x0ce9
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r5.put(r2, r3)
        L_0x0ce9:
            java.lang.Object r3 = r5.get(r2)
            java.util.List r3 = (java.util.List) r3
            r3.add(r7)
            goto L_0x0cf6
        L_0x0cf3:
            r13.add(r7)
        L_0x0cf6:
            r3 = r16
        L_0x0cf8:
            r8 = r19
            r9 = r22
            r10 = r50
            r7 = r55
            goto L_0x0c6b
        L_0x0d02:
            r16 = r3
            r55 = r7
            r19 = r8
            r22 = r9
            r50 = r10
        L_0x0d0c:
            int r3 = r4.size()
            byte[] r3 = new byte[r3]
            byte[] r7 = r6.levels
            if (r7 == 0) goto L_0x0d1e
            int r8 = r4.size()
            r9 = 0
            java.lang.System.arraycopy(r7, r9, r3, r9, r8)
        L_0x0d1e:
            byte[] r7 = r6.levels
            int[] r7 = com.itextpdf.layout.renderer.TypographyUtils.reorderLine(r4, r3, r7)
            if (r7 == 0) goto L_0x0e6a
            r0.clear()
            r8 = 0
            r9 = 0
            r10 = 0
            r17 = 0
            java.util.Iterator r24 = r13.iterator()
        L_0x0d32:
            boolean r26 = r24.hasNext()
            if (r26 == 0) goto L_0x0d48
            java.lang.Object r26 = r24.next()
            r27 = r2
            r2 = r26
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            r0.add(r2)
            r2 = r27
            goto L_0x0d32
        L_0x0d48:
            r27 = r2
        L_0x0d4a:
            int r2 = r4.size()
            if (r8 >= r2) goto L_0x0e52
            java.lang.Object r2 = r4.get(r8)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r2 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r2
            com.itextpdf.layout.renderer.TextRenderer r2 = r2.renderer
            r24 = r8
            com.itextpdf.layout.renderer.TextRenderer r8 = new com.itextpdf.layout.renderer.TextRenderer
            r26 = r9
            r9 = r2
            com.itextpdf.layout.renderer.TextRenderer r9 = (com.itextpdf.layout.renderer.TextRenderer) r9
            r8.<init>((com.itextpdf.layout.renderer.TextRenderer) r9)
            com.itextpdf.layout.renderer.TextRenderer r8 = r8.removeReversedRanges()
            r0.add(r8)
            r9 = r2
            com.itextpdf.layout.renderer.TextRenderer r9 = (com.itextpdf.layout.renderer.TextRenderer) r9
            boolean r9 = r5.containsKey(r9)
            if (r9 == 0) goto L_0x0d86
            r9 = r2
            com.itextpdf.layout.renderer.TextRenderer r9 = (com.itextpdf.layout.renderer.TextRenderer) r9
            java.lang.Object r9 = r5.get(r9)
            java.util.Collection r9 = (java.util.Collection) r9
            r0.addAll(r9)
            r9 = r2
            com.itextpdf.layout.renderer.TextRenderer r9 = (com.itextpdf.layout.renderer.TextRenderer) r9
            r5.remove(r9)
        L_0x0d86:
            com.itextpdf.io.font.otf.GlyphLine r9 = new com.itextpdf.io.font.otf.GlyphLine
            r29 = r5
            com.itextpdf.io.font.otf.GlyphLine r5 = r8.line
            r9.<init>((com.itextpdf.p026io.font.otf.GlyphLine) r5)
            r8.line = r9
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r9 = r24
        L_0x0d98:
            r57 = r11
            int r11 = r4.size()
            if (r9 >= r11) goto L_0x0e1d
            java.lang.Object r11 = r4.get(r9)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r11 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r11
            com.itextpdf.layout.renderer.TextRenderer r11 = r11.renderer
            if (r11 != r2) goto L_0x0e1d
            int r11 = r9 + 1
            r30 = r2
            int r2 = r4.size()
            if (r11 >= r2) goto L_0x0e06
            r2 = r7[r9]
            int r11 = r9 + 1
            r11 = r7[r11]
            r24 = 1
            int r11 = r11 + 1
            if (r2 != r11) goto L_0x0de3
            int r2 = r9 + 1
            java.lang.Object r2 = r4.get(r2)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r2 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r2
            com.itextpdf.io.font.otf.Glyph r2 = r2.glyph
            boolean r2 = com.itextpdf.p026io.util.TextUtil.isSpaceOrWhitespace(r2)
            if (r2 != 0) goto L_0x0de3
            java.lang.Object r2 = r4.get(r9)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r2 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r2
            com.itextpdf.io.font.otf.Glyph r2 = r2.glyph
            boolean r2 = com.itextpdf.p026io.util.TextUtil.isSpaceOrWhitespace(r2)
            if (r2 != 0) goto L_0x0de3
            r2 = 1
            r10 = r2
            r33 = r7
            goto L_0x0e08
        L_0x0de3:
            if (r10 == 0) goto L_0x0dff
            java.util.List r2 = r8.initReversedRanges()
            r33 = r7
            r11 = 2
            int[] r7 = new int[r11]
            int r11 = r26 - r17
            r24 = 0
            r7[r24] = r11
            int r11 = r9 - r17
            r24 = 1
            r7[r24] = r11
            r2.add(r7)
            r10 = 0
            goto L_0x0e01
        L_0x0dff:
            r33 = r7
        L_0x0e01:
            int r2 = r9 + 1
            r26 = r2
            goto L_0x0e08
        L_0x0e06:
            r33 = r7
        L_0x0e08:
            java.lang.Object r2 = r4.get(r9)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r2 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r2
            com.itextpdf.io.font.otf.Glyph r2 = r2.glyph
            r5.add(r2)
            int r9 = r9 + 1
            r2 = r30
            r7 = r33
            r11 = r57
            goto L_0x0d98
        L_0x0e1d:
            r30 = r2
            r33 = r7
            if (r10 == 0) goto L_0x0e40
            java.util.List r2 = r8.initReversedRanges()
            r7 = 2
            int[] r11 = new int[r7]
            int r7 = r26 - r17
            r24 = 0
            r11[r24] = r7
            int r7 = r9 + -1
            int r7 = r7 - r17
            r24 = 1
            r11[r24] = r7
            r2.add(r11)
            r7 = 0
            r10 = r9
            r26 = r10
            r10 = r7
        L_0x0e40:
            r17 = r26
            com.itextpdf.io.font.otf.GlyphLine r2 = r8.line
            r2.setGlyphs(r5)
            r8 = r9
            r9 = r26
            r5 = r29
            r7 = r33
            r11 = r57
            goto L_0x0d4a
        L_0x0e52:
            r29 = r5
            r33 = r7
            r24 = r8
            r26 = r9
            r57 = r11
            com.itextpdf.layout.layout.LayoutArea r2 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r2 = r2.getBBox()
            float r2 = r2.getLeft()
            adjustChildPositionsAfterReordering(r0, r2)
            goto L_0x0e72
        L_0x0e6a:
            r27 = r2
            r29 = r5
            r33 = r7
            r57 = r11
        L_0x0e72:
            int r2 = r1.getStatus()
            r5 = 2
            if (r2 != r5) goto L_0x0ebf
            com.itextpdf.layout.renderer.IRenderer r2 = r1.getOverflowRenderer()
            com.itextpdf.layout.renderer.LineRenderer r2 = (com.itextpdf.layout.renderer.LineRenderer) r2
            byte[] r5 = r6.levels
            if (r5 == 0) goto L_0x0ebf
            int r5 = r5.length
            int r7 = r3.length
            int r5 = r5 - r7
            byte[] r5 = new byte[r5]
            r2.levels = r5
            byte[] r7 = r6.levels
            int r8 = r3.length
            int r9 = r5.length
            r10 = 0
            java.lang.System.arraycopy(r7, r8, r5, r10, r9)
            byte[] r5 = r2.levels
            int r5 = r5.length
            if (r5 != 0) goto L_0x0ebf
            r5 = 0
            r2.levels = r5
            goto L_0x0ebf
        L_0x0e9b:
            r41 = r2
            r55 = r7
            r19 = r8
            r22 = r9
            r50 = r10
            r57 = r11
            goto L_0x0ebf
        L_0x0ea8:
            r41 = r2
            r55 = r7
            r19 = r8
            r22 = r9
            r50 = r10
            r57 = r11
            goto L_0x0ebf
        L_0x0eb5:
            r55 = r7
            r19 = r8
            r22 = r9
            r50 = r10
            r57 = r11
        L_0x0ebf:
            int r0 = r1.getStatus()
            r2 = 1
            if (r0 != r2) goto L_0x0ec8
            r0 = r6
            goto L_0x0ece
        L_0x0ec8:
            com.itextpdf.layout.renderer.IRenderer r0 = r1.getSplitRenderer()
            com.itextpdf.layout.renderer.LineRenderer r0 = (com.itextpdf.layout.renderer.LineRenderer) r0
        L_0x0ece:
            if (r20 != 0) goto L_0x0ed6
            if (r23 == 0) goto L_0x0ed3
            goto L_0x0ed6
        L_0x0ed3:
            r2 = r40
            goto L_0x0ee2
        L_0x0ed6:
            com.itextpdf.layout.renderer.LineRenderer r2 = r0.adjustChildrenYLine()
            r2.trimLast()
            r2 = r40
            r1.setMinMaxWidth(r2)
        L_0x0ee2:
            if (r46 == 0) goto L_0x0f03
            r3 = 103(0x67, float:1.44E-43)
            r6.setProperty(r3, r12)
            com.itextpdf.layout.renderer.IRenderer r4 = r1.getSplitRenderer()
            if (r4 == 0) goto L_0x0ef6
            com.itextpdf.layout.renderer.IRenderer r4 = r1.getSplitRenderer()
            r4.setProperty(r3, r12)
        L_0x0ef6:
            com.itextpdf.layout.renderer.IRenderer r4 = r1.getOverflowRenderer()
            if (r4 == 0) goto L_0x0f03
            com.itextpdf.layout.renderer.IRenderer r4 = r1.getOverflowRenderer()
            r4.setProperty(r3, r12)
        L_0x0f03:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.LineRenderer.layout(com.itextpdf.layout.layout.LayoutContext):com.itextpdf.layout.layout.LayoutResult");
    }

    public float getMaxAscent() {
        return this.maxAscent;
    }

    public float getMaxDescent() {
        return this.maxDescent;
    }

    public float getYLine() {
        return this.occupiedArea.getBBox().getY() - this.maxDescent;
    }

    public float getLeadingValue(Leading leading) {
        switch (leading.getType()) {
            case 1:
                return Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent);
            case 2:
                return getTopLeadingIndent(leading) + getBottomLeadingIndent(leading);
            default:
                throw new IllegalStateException();
        }
    }

    public IRenderer getNextRenderer() {
        return new LineRenderer();
    }

    /* access modifiers changed from: protected */
    public Float getFirstYLineRecursively() {
        return Float.valueOf(getYLine());
    }

    /* access modifiers changed from: protected */
    public Float getLastYLineRecursively() {
        return Float.valueOf(getYLine());
    }

    public void justify(float width) {
        IRenderer lastChildRenderer;
        float freeWidth;
        float ratio;
        float f = width;
        float ratio2 = getPropertyAsFloat(61).floatValue();
        IRenderer lastChildRenderer2 = getLastNonFloatChildRenderer();
        if (lastChildRenderer2 != null) {
            float freeWidth2 = ((this.occupiedArea.getBBox().getX() + f) - lastChildRenderer2.getOccupiedArea().getBBox().getX()) - lastChildRenderer2.getOccupiedArea().getBBox().getWidth();
            float baseFactor = freeWidth2 / ((((float) getNumberOfSpaces()) * ratio2) + ((1.0f - ratio2) * ((float) (baseCharactersCount() - 1))));
            if (Float.isInfinite(baseFactor)) {
                baseFactor = 0.0f;
            }
            float wordSpacing = ratio2 * baseFactor;
            float characterSpacing = (1.0f - ratio2) * baseFactor;
            float lastRightPos = this.occupiedArea.getBBox().getX();
            for (IRenderer child : this.childRenderers) {
                if (!FloatingHelper.isRendererFloating(child)) {
                    child.move(lastRightPos - child.getOccupiedArea().getBBox().getX(), 0.0f);
                    float childX = lastRightPos;
                    if (child instanceof TextRenderer) {
                        float childHSCale = ((TextRenderer) child).getPropertyAsFloat(29, Float.valueOf(1.0f)).floatValue();
                        Float oldCharacterSpacing = ((TextRenderer) child).getPropertyAsFloat(15);
                        ratio = ratio2;
                        Float oldWordSpacing = ((TextRenderer) child).getPropertyAsFloat(78);
                        freeWidth = freeWidth2;
                        child.setProperty(15, Float.valueOf((oldCharacterSpacing == null ? 0.0f : oldCharacterSpacing.floatValue()) + (characterSpacing / childHSCale)));
                        child.setProperty(78, Float.valueOf((oldWordSpacing == null ? 0.0f : oldWordSpacing.floatValue()) + (wordSpacing / childHSCale)));
                        float f2 = childHSCale;
                        lastChildRenderer = lastChildRenderer2;
                        child.getOccupiedArea().getBBox().setWidth(child.getOccupiedArea().getBBox().getWidth() + (((float) (child == lastChildRenderer2 ? ((TextRenderer) child).lineLength() - 1 : ((TextRenderer) child).lineLength())) * characterSpacing) + (((float) ((TextRenderer) child).getNumberOfSpaces()) * wordSpacing));
                    } else {
                        ratio = ratio2;
                        lastChildRenderer = lastChildRenderer2;
                        freeWidth = freeWidth2;
                    }
                    lastRightPos = childX + child.getOccupiedArea().getBBox().getWidth();
                    ratio2 = ratio;
                    freeWidth2 = freeWidth;
                    lastChildRenderer2 = lastChildRenderer;
                }
            }
            getOccupiedArea().getBBox().setWidth(f);
        }
    }

    /* access modifiers changed from: protected */
    public int getNumberOfSpaces() {
        int spaces = 0;
        for (IRenderer child : this.childRenderers) {
            if ((child instanceof TextRenderer) && !FloatingHelper.isRendererFloating(child)) {
                spaces += ((TextRenderer) child).getNumberOfSpaces();
            }
        }
        return spaces;
    }

    /* access modifiers changed from: protected */
    public int length() {
        int length = 0;
        for (IRenderer child : this.childRenderers) {
            if ((child instanceof TextRenderer) && !FloatingHelper.isRendererFloating(child)) {
                length += ((TextRenderer) child).lineLength();
            }
        }
        return length;
    }

    /* access modifiers changed from: protected */
    public int baseCharactersCount() {
        int count = 0;
        for (IRenderer child : this.childRenderers) {
            if ((child instanceof TextRenderer) && !FloatingHelper.isRendererFloating(child)) {
                count += ((TextRenderer) child).baseCharactersCount();
            }
        }
        return count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IRenderer renderer : this.childRenderers) {
            sb.append(renderer.toString());
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public LineRenderer createSplitRenderer() {
        return (LineRenderer) getNextRenderer();
    }

    /* access modifiers changed from: protected */
    public LineRenderer createOverflowRenderer() {
        return (LineRenderer) getNextRenderer();
    }

    /* access modifiers changed from: protected */
    public LineRenderer[] split() {
        LineRenderer splitRenderer = createSplitRenderer();
        splitRenderer.occupiedArea = this.occupiedArea.clone();
        splitRenderer.parent = this.parent;
        splitRenderer.maxAscent = this.maxAscent;
        splitRenderer.maxDescent = this.maxDescent;
        splitRenderer.maxTextAscent = this.maxTextAscent;
        splitRenderer.maxTextDescent = this.maxTextDescent;
        splitRenderer.maxBlockAscent = this.maxBlockAscent;
        splitRenderer.maxBlockDescent = this.maxBlockDescent;
        splitRenderer.levels = this.levels;
        splitRenderer.addAllProperties(getOwnProperties());
        LineRenderer overflowRenderer = createOverflowRenderer();
        overflowRenderer.parent = this.parent;
        overflowRenderer.addAllProperties(getOwnProperties());
        return new LineRenderer[]{splitRenderer, overflowRenderer};
    }

    /* access modifiers changed from: protected */
    public LineRenderer adjustChildrenYLine() {
        float actualYLine = (this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight()) - this.maxAscent;
        for (IRenderer renderer : this.childRenderers) {
            if (!FloatingHelper.isRendererFloating(renderer)) {
                if (renderer instanceof ILeafElementRenderer) {
                    renderer.move(0.0f, (actualYLine - renderer.getOccupiedArea().getBBox().getBottom()) + ((ILeafElementRenderer) renderer).getDescent());
                } else {
                    Float yLine = (!isInlineBlockChild(renderer) || !(renderer instanceof AbstractRenderer)) ? null : ((AbstractRenderer) renderer).getLastYLineRecursively();
                    renderer.move(0.0f, actualYLine - (yLine == null ? renderer.getOccupiedArea().getBBox().getBottom() : yLine.floatValue()));
                }
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void applyLeading(float deltaY) {
        this.occupiedArea.getBBox().moveUp(deltaY);
        this.occupiedArea.getBBox().decreaseHeight(deltaY);
        for (IRenderer child : this.childRenderers) {
            if (!FloatingHelper.isRendererFloating(child)) {
                child.move(0.0f, deltaY);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.itextpdf.layout.renderer.IRenderer} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.renderer.LineRenderer trimLast() {
        /*
            r5 = this;
            java.util.List r0 = r5.childRenderers
            int r0 = r0.size()
            r1 = 0
        L_0x0007:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x001b
            java.util.List r2 = r5.childRenderers
            java.lang.Object r2 = r2.get(r0)
            r1 = r2
            com.itextpdf.layout.renderer.IRenderer r1 = (com.itextpdf.layout.renderer.IRenderer) r1
            boolean r2 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r1)
            if (r2 != 0) goto L_0x0007
        L_0x001b:
            boolean r2 = r1 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r2 == 0) goto L_0x003c
            if (r0 < 0) goto L_0x003c
            r2 = r1
            com.itextpdf.layout.renderer.TextRenderer r2 = (com.itextpdf.layout.renderer.TextRenderer) r2
            float r2 = r2.trimLast()
            com.itextpdf.layout.layout.LayoutArea r3 = r5.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            com.itextpdf.layout.layout.LayoutArea r4 = r5.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r4 = r4.getBBox()
            float r4 = r4.getWidth()
            float r4 = r4 - r2
            r3.setWidth(r4)
        L_0x003c:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.LineRenderer.trimLast():com.itextpdf.layout.renderer.LineRenderer");
    }

    public boolean containsImage() {
        for (IRenderer renderer : this.childRenderers) {
            if (renderer instanceof ImageRenderer) {
                return true;
            }
        }
        return false;
    }

    public MinMaxWidth getMinMaxWidth() {
        return ((LineLayoutResult) layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0f))))).getMinMaxWidth();
    }

    /* access modifiers changed from: package-private */
    public boolean hasChildRendererInHtmlMode() {
        for (IRenderer childRenderer : this.childRenderers) {
            if (RenderingMode.HTML_MODE.equals(childRenderer.getProperty(123))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public float getTopLeadingIndent(Leading leading) {
        switch (leading.getType()) {
            case 1:
                return (Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent) - this.occupiedArea.getBBox().getHeight()) / 2.0f;
            case 2:
                UnitValue fontSize = (UnitValue) getProperty(24, UnitValue.createPointValue(0.0f));
                if (!fontSize.isPointValue()) {
                    logger.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 24));
                }
                float textAscent = (this.maxTextAscent != 0.0f || this.maxTextDescent != 0.0f || Math.abs(this.maxAscent) + Math.abs(this.maxDescent) == 0.0f || containsImage()) ? this.maxTextAscent : fontSize.getValue() * 0.8f;
                return Math.max((((textAscent - ((this.maxTextAscent != 0.0f || this.maxTextDescent != 0.0f || Math.abs(this.maxAscent) + Math.abs(this.maxDescent) == 0.0f || containsImage()) ? this.maxTextDescent : (-fontSize.getValue()) * 0.2f)) * (leading.getValue() - 1.0f)) / 2.0f) + textAscent, this.maxBlockAscent) - this.maxAscent;
            default:
                throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: package-private */
    public float getBottomLeadingIndent(Leading leading) {
        switch (leading.getType()) {
            case 1:
                return (Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent) - this.occupiedArea.getBBox().getHeight()) / 2.0f;
            case 2:
                UnitValue fontSize = (UnitValue) getProperty(24, UnitValue.createPointValue(0.0f));
                if (!fontSize.isPointValue()) {
                    logger.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 24));
                }
                float textAscent = (this.maxTextAscent == 0.0f && this.maxTextDescent == 0.0f && !containsImage()) ? fontSize.getValue() * 0.8f : this.maxTextAscent;
                float textDescent = (this.maxTextAscent == 0.0f && this.maxTextDescent == 0.0f && !containsImage()) ? (-fontSize.getValue()) * 0.2f : this.maxTextDescent;
                return Math.max((-textDescent) + (((textAscent - textDescent) * (leading.getValue() - 1.0f)) / 2.0f), -this.maxBlockDescent) + this.maxDescent;
            default:
                throw new IllegalStateException();
        }
    }

    static void adjustChildPositionsAfterReordering(List<IRenderer> children, float initialXPos) {
        float currentWidth;
        float currentXPos = initialXPos;
        for (IRenderer child : children) {
            if (!FloatingHelper.isRendererFloating(child)) {
                if (child instanceof TextRenderer) {
                    float currentWidth2 = ((TextRenderer) child).calculateLineWidth();
                    UnitValue[] margins = ((TextRenderer) child).getMargins();
                    if (!margins[1].isPointValue()) {
                        Logger logger2 = logger;
                        if (logger2.isErrorEnabled()) {
                            logger2.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, "right margin"));
                        }
                    }
                    if (!margins[3].isPointValue()) {
                        Logger logger3 = logger;
                        if (logger3.isErrorEnabled()) {
                            logger3.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, "left margin"));
                        }
                    }
                    UnitValue[] paddings = ((TextRenderer) child).getPaddings();
                    if (!paddings[1].isPointValue()) {
                        Logger logger4 = logger;
                        if (logger4.isErrorEnabled()) {
                            logger4.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, "right padding"));
                        }
                    }
                    if (!paddings[3].isPointValue()) {
                        Logger logger5 = logger;
                        if (logger5.isErrorEnabled()) {
                            logger5.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, "left padding"));
                        }
                    }
                    currentWidth = currentWidth2 + margins[1].getValue() + margins[3].getValue() + paddings[1].getValue() + paddings[3].getValue();
                    ((TextRenderer) child).occupiedArea.getBBox().setX(currentXPos).setWidth(currentWidth);
                } else {
                    currentWidth = child.getOccupiedArea().getBBox().getWidth();
                    child.move(currentXPos - child.getOccupiedArea().getBBox().getX(), 0.0f);
                }
                currentXPos += currentWidth;
            }
        }
    }

    private LineRenderer[] splitNotFittingFloat(int childPos, LayoutResult childResult) {
        LineRenderer[] split = split();
        split[0].childRenderers.addAll(this.childRenderers.subList(0, childPos));
        split[0].childRenderers.add(childResult.getSplitRenderer());
        split[1].childRenderers.add(childResult.getOverflowRenderer());
        split[1].childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
        return split;
    }

    private void adjustLineOnFloatPlaced(Rectangle layoutBox, int childPos, FloatPropertyValue kidFloatPropertyVal, Rectangle justPlacedFloatBox) {
        if (justPlacedFloatBox.getBottom() < layoutBox.getTop() && justPlacedFloatBox.getTop() >= layoutBox.getTop()) {
            float floatWidth = justPlacedFloatBox.getWidth();
            if (kidFloatPropertyVal.equals(FloatPropertyValue.LEFT)) {
                layoutBox.setWidth(layoutBox.getWidth() - floatWidth).moveRight(floatWidth);
                this.occupiedArea.getBBox().moveRight(floatWidth);
                for (int i = 0; i < childPos; i++) {
                    IRenderer prevChild = (IRenderer) this.childRenderers.get(i);
                    if (!FloatingHelper.isRendererFloating(prevChild)) {
                        prevChild.move(floatWidth, 0.0f);
                    }
                }
                return;
            }
            layoutBox.setWidth(layoutBox.getWidth() - floatWidth);
        }
    }

    private void replaceSplitRendererKidFloats(Map<Integer, IRenderer> floatsToNextPageSplitRenderers, LineRenderer splitRenderer) {
        for (Map.Entry<Integer, IRenderer> splitFloat : floatsToNextPageSplitRenderers.entrySet()) {
            if (splitFloat.getValue() != null) {
                splitRenderer.childRenderers.set(splitFloat.getKey().intValue(), splitFloat.getValue());
            } else {
                splitRenderer.childRenderers.set(splitFloat.getKey().intValue(), (Object) null);
            }
        }
        for (int i = splitRenderer.getChildRenderers().size() - 1; i >= 0; i--) {
            if (splitRenderer.getChildRenderers().get(i) == null) {
                splitRenderer.getChildRenderers().remove(i);
            }
        }
    }

    private IRenderer getLastNonFloatChildRenderer() {
        for (int i = this.childRenderers.size() - 1; i >= 0; i--) {
            if (!FloatingHelper.isRendererFloating((IRenderer) this.childRenderers.get(i))) {
                return (IRenderer) this.childRenderers.get(i);
            }
        }
        return null;
    }

    private TabStop getNextTabStop(float curWidth) {
        NavigableMap<Float, TabStop> tabStops = (NavigableMap) getProperty(69);
        Map.Entry<Float, TabStop> nextTabStopEntry = null;
        if (tabStops != null) {
            nextTabStopEntry = tabStops.higherEntry(Float.valueOf(curWidth));
        }
        if (nextTabStopEntry != null) {
            return nextTabStopEntry.getValue();
        }
        return null;
    }

    private TabStop calculateTab(IRenderer childRenderer, float curWidth, float lineWidth) {
        TabStop nextTabStop = getNextTabStop(curWidth);
        if (nextTabStop == null) {
            processDefaultTab(childRenderer, curWidth, lineWidth);
            return null;
        }
        childRenderer.setProperty(68, nextTabStop.getTabLeader());
        childRenderer.setProperty(77, UnitValue.createPointValue(nextTabStop.getTabPosition() - curWidth));
        childRenderer.setProperty(85, UnitValue.createPointValue(this.maxAscent - this.maxDescent));
        if (nextTabStop.getTabAlignment() == TabAlignment.LEFT) {
            return null;
        }
        return nextTabStop;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0063  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float calculateTab(com.itextpdf.kernel.geom.Rectangle r9, float r10, com.itextpdf.layout.element.TabStop r11, java.util.List<com.itextpdf.layout.renderer.IRenderer> r12, com.itextpdf.layout.renderer.IRenderer r13) {
        /*
            r8 = this;
            r0 = 0
            java.util.Iterator r1 = r12.iterator()
        L_0x0005:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x001f
            java.lang.Object r2 = r1.next()
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            com.itextpdf.layout.layout.LayoutArea r3 = r2.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r3 = r3.getBBox()
            float r3 = r3.getWidth()
            float r0 = r0 + r3
            goto L_0x0005
        L_0x001f:
            r1 = 0
            int[] r2 = com.itextpdf.layout.renderer.LineRenderer.C14711.$SwitchMap$com$itextpdf$layout$property$TabAlignment
            com.itextpdf.layout.property.TabAlignment r3 = r11.getTabAlignment()
            int r3 = r3.ordinal()
            r2 = r2[r3]
            switch(r2) {
                case 1: goto L_0x0079;
                case 2: goto L_0x006d;
                case 3: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x0081
        L_0x0030:
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            r3 = 0
            java.util.Iterator r4 = r12.iterator()
        L_0x0037:
            boolean r5 = r4.hasNext()
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r5 == 0) goto L_0x005f
            java.lang.Object r5 = r4.next()
            com.itextpdf.layout.renderer.IRenderer r5 = (com.itextpdf.layout.renderer.IRenderer) r5
            r7 = r5
            com.itextpdf.layout.renderer.TextRenderer r7 = (com.itextpdf.layout.renderer.TextRenderer) r7
            float r2 = r7.getTabAnchorCharacterPosition()
            int r7 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r7 == 0) goto L_0x0051
            goto L_0x005f
        L_0x0051:
            com.itextpdf.layout.layout.LayoutArea r6 = r5.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r6 = r6.getBBox()
            float r6 = r6.getWidth()
            float r3 = r3 + r6
            goto L_0x0037
        L_0x005f:
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x0064
            r2 = 0
        L_0x0064:
            float r4 = r11.getTabPosition()
            float r4 = r4 - r10
            float r4 = r4 - r2
            float r1 = r4 - r3
            goto L_0x0081
        L_0x006d:
            float r2 = r11.getTabPosition()
            float r2 = r2 - r10
            r3 = 1073741824(0x40000000, float:2.0)
            float r3 = r0 / r3
            float r1 = r2 - r3
            goto L_0x0081
        L_0x0079:
            float r2 = r11.getTabPosition()
            float r2 = r2 - r10
            float r1 = r2 - r0
        L_0x0081:
            r2 = 0
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0087
            r1 = 0
        L_0x0087:
            float r2 = r10 + r1
            float r2 = r2 + r0
            float r3 = r9.getWidth()
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x009b
            float r2 = r10 + r0
            float r2 = r2 + r1
            float r3 = r9.getWidth()
            float r2 = r2 - r3
            float r1 = r1 - r2
        L_0x009b:
            r2 = 77
            com.itextpdf.layout.property.UnitValue r3 = com.itextpdf.layout.property.UnitValue.createPointValue(r1)
            r13.setProperty(r2, r3)
            float r2 = r8.maxAscent
            float r3 = r8.maxDescent
            float r2 = r2 - r3
            com.itextpdf.layout.property.UnitValue r2 = com.itextpdf.layout.property.UnitValue.createPointValue(r2)
            r3 = 85
            r13.setProperty(r3, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.LineRenderer.calculateTab(com.itextpdf.kernel.geom.Rectangle, float, com.itextpdf.layout.element.TabStop, java.util.List, com.itextpdf.layout.renderer.IRenderer):float");
    }

    /* renamed from: com.itextpdf.layout.renderer.LineRenderer$1 */
    static /* synthetic */ class C14711 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$TabAlignment;

        static {
            int[] iArr = new int[TabAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$TabAlignment = iArr;
            try {
                iArr[TabAlignment.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TabAlignment[TabAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TabAlignment[TabAlignment.ANCHOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private void processDefaultTab(IRenderer tabRenderer, float curWidth, float lineWidth) {
        Float tabDefault = getPropertyAsFloat(67);
        Float tabWidth = Float.valueOf(tabDefault.floatValue() - (curWidth % tabDefault.floatValue()));
        if (tabWidth.floatValue() + curWidth > lineWidth) {
            tabWidth = Float.valueOf(lineWidth - curWidth);
        }
        tabRenderer.setProperty(77, UnitValue.createPointValue(tabWidth.floatValue()));
        tabRenderer.setProperty(85, UnitValue.createPointValue(this.maxAscent - this.maxDescent));
    }

    private void updateChildrenParent() {
        for (IRenderer renderer : this.childRenderers) {
            renderer.setParent(this);
        }
    }

    /* access modifiers changed from: package-private */
    public int trimFirst() {
        int totalNumberOfTrimmedGlyphs = 0;
        for (IRenderer renderer : this.childRenderers) {
            if (!FloatingHelper.isRendererFloating(renderer)) {
                if (!(renderer instanceof TextRenderer)) {
                    break;
                }
                TextRenderer textRenderer = (TextRenderer) renderer;
                GlyphLine currentText = textRenderer.getText();
                if (currentText != null) {
                    int prevTextStart = currentText.start;
                    textRenderer.trimFirst();
                    totalNumberOfTrimmedGlyphs += textRenderer.getText().start - prevTextStart;
                }
                if (textRenderer.length() > 0) {
                    break;
                }
            }
        }
        return totalNumberOfTrimmedGlyphs;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.itextpdf.layout.property.BaseDirection} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.layout.property.BaseDirection applyOtf() {
        /*
            r5 = this;
            r0 = 7
            java.lang.Object r1 = r5.getProperty(r0)
            com.itextpdf.layout.property.BaseDirection r1 = (com.itextpdf.layout.property.BaseDirection) r1
            java.util.List r2 = r5.childRenderers
            java.util.Iterator r2 = r2.iterator()
        L_0x000d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0031
            java.lang.Object r3 = r2.next()
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            boolean r4 = r3 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r4 == 0) goto L_0x0030
            r4 = r3
            com.itextpdf.layout.renderer.TextRenderer r4 = (com.itextpdf.layout.renderer.TextRenderer) r4
            r4.applyOtf()
            if (r1 == 0) goto L_0x0029
            com.itextpdf.layout.property.BaseDirection r4 = com.itextpdf.layout.property.BaseDirection.NO_BIDI
            if (r1 != r4) goto L_0x0030
        L_0x0029:
            java.lang.Object r4 = r3.getOwnProperty(r0)
            r1 = r4
            com.itextpdf.layout.property.BaseDirection r1 = (com.itextpdf.layout.property.BaseDirection) r1
        L_0x0030:
            goto L_0x000d
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.LineRenderer.applyOtf():com.itextpdf.layout.property.BaseDirection");
    }

    static boolean isTextRendererAndRequiresSpecialScriptPreLayoutProcessing(IRenderer childRenderer) {
        if (!(childRenderer instanceof TextRenderer) || ((TextRenderer) childRenderer).getSpecialScriptsWordBreakPoints() != null || !((TextRenderer) childRenderer).textContainsSpecialScriptGlyphs(false)) {
            return false;
        }
        return true;
    }

    static boolean isChildFloating(IRenderer childRenderer) {
        return (childRenderer instanceof AbstractRenderer) && FloatingHelper.isRendererFloating(childRenderer, (FloatPropertyValue) childRenderer.getProperty(99));
    }

    static void updateSpecialScriptLayoutResults(Map<Integer, LayoutResult> specialScriptLayoutResults, IRenderer childRenderer, int childPos, LayoutResult childResult) {
        if ((childRenderer instanceof TextRenderer) && ((TextRenderer) childRenderer).textContainsSpecialScriptGlyphs(true)) {
            specialScriptLayoutResults.put(Integer.valueOf(childPos), childResult);
        } else if (!specialScriptLayoutResults.isEmpty() && !isChildFloating(childRenderer)) {
            specialScriptLayoutResults.clear();
        }
    }

    static float getCurWidthSpecialScriptsDecrement(int childPos, int newChildPos, Map<Integer, LayoutResult> specialScriptLayoutResults) {
        float decrement = 0.0f;
        if (childPos != newChildPos) {
            for (int i = childPos - 1; i >= newChildPos; i--) {
                if (specialScriptLayoutResults.get(Integer.valueOf(i)) != null) {
                    decrement += specialScriptLayoutResults.get(Integer.valueOf(i)).getOccupiedArea().getBBox().getWidth();
                }
            }
        }
        return decrement;
    }

    /* access modifiers changed from: package-private */
    public void specialScriptPreLayoutProcessing(int childPos) {
        SpecialScriptsContainingTextRendererSequenceInfo info = getSpecialScriptsContainingTextRendererSequenceInfo(childPos);
        int numberOfSequentialTextRenderers = info.numberOfSequentialTextRenderers;
        String sequentialTextContent = info.sequentialTextContent;
        distributePossibleBreakPointsOverSequentialTextRenderers(childPos, numberOfSequentialTextRenderers, TypographyUtils.getPossibleBreaks(sequentialTextContent), info.indicesOfFloating);
    }

    /* access modifiers changed from: package-private */
    public SpecialScriptsContainingTextRendererSequenceInfo getSpecialScriptsContainingTextRendererSequenceInfo(int childPos) {
        StringBuilder sequentialTextContentBuilder = new StringBuilder();
        int numberOfSequentialTextRenderers = 0;
        List<Integer> indicesOfFloating = new ArrayList<>();
        for (int i = childPos; i < this.childRenderers.size(); i++) {
            if (!isChildFloating((IRenderer) this.childRenderers.get(i))) {
                if (!(this.childRenderers.get(i) instanceof TextRenderer) || !((TextRenderer) this.childRenderers.get(i)).textContainsSpecialScriptGlyphs(false)) {
                    break;
                }
                sequentialTextContentBuilder.append(((TextRenderer) this.childRenderers.get(i)).text.toString());
                numberOfSequentialTextRenderers++;
            } else {
                numberOfSequentialTextRenderers++;
                indicesOfFloating.add(Integer.valueOf(i));
            }
        }
        return new SpecialScriptsContainingTextRendererSequenceInfo(numberOfSequentialTextRenderers, sequentialTextContentBuilder.toString(), indicesOfFloating);
    }

    /* access modifiers changed from: package-private */
    public void distributePossibleBreakPointsOverSequentialTextRenderers(int childPos, int numberOfSequentialTextRenderers, List<Integer> possibleBreakPointsGlobal, List<Integer> indicesOfFloating) {
        int alreadyProcessedNumberOfCharsWithinGlyphLines = 0;
        int indexToBeginWith = 0;
        for (int i = 0; i < numberOfSequentialTextRenderers; i++) {
            if (!indicesOfFloating.contains(Integer.valueOf(i))) {
                TextRenderer childTextRenderer = (TextRenderer) this.childRenderers.get(childPos + i);
                List<Integer> amountOfCharsBetweenTextStartAndActualTextChunk = new ArrayList<>();
                List<Integer> glyphLineBasedIndicesOfActualTextChunkEnds = new ArrayList<>();
                fillActualTextChunkRelatedLists(childTextRenderer.getText(), amountOfCharsBetweenTextStartAndActualTextChunk, glyphLineBasedIndicesOfActualTextChunkEnds);
                List<Integer> possibleBreakPoints = new ArrayList<>();
                int j = indexToBeginWith;
                while (true) {
                    if (j >= possibleBreakPointsGlobal.size()) {
                        break;
                    }
                    int shiftedBreakPoint = possibleBreakPointsGlobal.get(j).intValue() - alreadyProcessedNumberOfCharsWithinGlyphLines;
                    int amountOfCharsBetweenTextStartAndTextEnd = amountOfCharsBetweenTextStartAndActualTextChunk.get(amountOfCharsBetweenTextStartAndActualTextChunk.size() - 1).intValue();
                    if (shiftedBreakPoint > amountOfCharsBetweenTextStartAndTextEnd) {
                        indexToBeginWith = j;
                        alreadyProcessedNumberOfCharsWithinGlyphLines += amountOfCharsBetweenTextStartAndTextEnd;
                        break;
                    }
                    possibleBreakPoints.add(Integer.valueOf(shiftedBreakPoint));
                    j++;
                }
                childTextRenderer.setSpecialScriptsWordBreakPoints(convertPossibleBreakPointsToGlyphLineBased(possibleBreakPoints, amountOfCharsBetweenTextStartAndActualTextChunk, glyphLineBasedIndicesOfActualTextChunkEnds));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public LastFittingChildRendererData getIndexAndLayoutResultOfTheLastRendererToRemainOnTheLine(int childPos, Map<Integer, LayoutResult> specialScriptLayoutResults, boolean wasParentsHeightClipped, List<IRenderer> floatsOverflowedToNextLine) {
        int analyzedTextRendererIndex;
        int indexOfRendererContainingLastFullyFittingWord;
        List<Integer> breakPoints;
        int possibleBreakPointPosition;
        int i = childPos;
        int indexOfRendererContainingLastFullyFittingWord2 = childPos;
        int splitPosition = 0;
        boolean needToSplitRendererContainingLastFullyFittingWord = false;
        int fittingLengthWithTrailingRightSideSpaces = 0;
        int amountOfTrailingRightSideSpaces = 0;
        Set<Integer> indicesOfFloats = new HashSet<>();
        LayoutResult childPosLayoutResult = specialScriptLayoutResults.get(Integer.valueOf(childPos));
        LayoutResult returnLayoutResult = null;
        int analyzedTextRendererIndex2 = childPos;
        while (true) {
            if (analyzedTextRendererIndex < 0) {
                break;
            }
            TextRenderer textRenderer = (TextRenderer) this.childRenderers.get(analyzedTextRendererIndex);
            if (analyzedTextRendererIndex != i) {
                fittingLengthWithTrailingRightSideSpaces = textRenderer.length();
                indexOfRendererContainingLastFullyFittingWord = indexOfRendererContainingLastFullyFittingWord2;
            } else if (childPosLayoutResult.getSplitRenderer() != null) {
                TextRenderer splitTextRenderer = (TextRenderer) childPosLayoutResult.getSplitRenderer();
                GlyphLine splitText = splitTextRenderer.text;
                if (splitTextRenderer.length() > 0) {
                    fittingLengthWithTrailingRightSideSpaces = splitTextRenderer.length();
                    while (true) {
                        indexOfRendererContainingLastFullyFittingWord = indexOfRendererContainingLastFullyFittingWord2;
                        if (splitText.end + amountOfTrailingRightSideSpaces >= splitText.size() || !TextUtil.isWhitespace(splitText.get(splitText.end + amountOfTrailingRightSideSpaces))) {
                            break;
                        }
                        fittingLengthWithTrailingRightSideSpaces++;
                        amountOfTrailingRightSideSpaces++;
                        indexOfRendererContainingLastFullyFittingWord2 = indexOfRendererContainingLastFullyFittingWord;
                    }
                } else {
                    indexOfRendererContainingLastFullyFittingWord = indexOfRendererContainingLastFullyFittingWord2;
                }
            } else {
                indexOfRendererContainingLastFullyFittingWord = indexOfRendererContainingLastFullyFittingWord2;
            }
            if (fittingLengthWithTrailingRightSideSpaces > 0 && (breakPoints = textRenderer.getSpecialScriptsWordBreakPoints()) != null && breakPoints.size() > 0) {
                boolean z = false;
                if (breakPoints.get(0).intValue() != -1 && (possibleBreakPointPosition = TextRenderer.findPossibleBreaksSplitPosition(textRenderer.getSpecialScriptsWordBreakPoints(), textRenderer.text.start + fittingLengthWithTrailingRightSideSpaces, false)) > -1) {
                    splitPosition = breakPoints.get(possibleBreakPointPosition).intValue() - amountOfTrailingRightSideSpaces;
                    if (splitPosition != textRenderer.text.end) {
                        z = true;
                    }
                    needToSplitRendererContainingLastFullyFittingWord = z;
                    if (!needToSplitRendererContainingLastFullyFittingWord) {
                        analyzedTextRendererIndex++;
                        while (analyzedTextRendererIndex <= i && isChildFloating((IRenderer) this.childRenderers.get(analyzedTextRendererIndex))) {
                            analyzedTextRendererIndex++;
                        }
                    }
                    indexOfRendererContainingLastFullyFittingWord2 = analyzedTextRendererIndex;
                }
            }
            int amountOfFloating = 0;
            while (analyzedTextRendererIndex - 1 >= 0 && isChildFloating((IRenderer) this.childRenderers.get(analyzedTextRendererIndex - 1))) {
                indicesOfFloats.add(Integer.valueOf(analyzedTextRendererIndex - 1));
                analyzedTextRendererIndex--;
                amountOfFloating++;
            }
            SpecialScriptsContainingSequenceStatus status = getSpecialScriptsContainingSequenceStatus(analyzedTextRendererIndex);
            if (status == SpecialScriptsContainingSequenceStatus.FORCED_SPLIT) {
                if (childPosLayoutResult.getStatus() != 3) {
                    returnLayoutResult = childPosLayoutResult;
                }
                indexOfRendererContainingLastFullyFittingWord2 = childPos;
            } else if (status == SpecialScriptsContainingSequenceStatus.MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE) {
                indexOfRendererContainingLastFullyFittingWord2 = analyzedTextRendererIndex + amountOfFloating;
                break;
            } else {
                analyzedTextRendererIndex2 = analyzedTextRendererIndex - 1;
                indexOfRendererContainingLastFullyFittingWord2 = indexOfRendererContainingLastFullyFittingWord;
            }
        }
        updateFloatsOverflowedToNextLine(floatsOverflowedToNextLine, indicesOfFloats, indexOfRendererContainingLastFullyFittingWord2);
        if (returnLayoutResult == null) {
            returnLayoutResult = childPosLayoutResult;
            TextRenderer childRenderer = (TextRenderer) this.childRenderers.get(indexOfRendererContainingLastFullyFittingWord2);
            if (!needToSplitRendererContainingLastFullyFittingWord) {
                boolean z2 = wasParentsHeightClipped;
                returnLayoutResult = new TextLayoutResult(3, (LayoutArea) null, (IRenderer) null, childRenderer);
            } else if ((fittingLengthWithTrailingRightSideSpaces - amountOfTrailingRightSideSpaces) + childRenderer.text.start != splitPosition) {
                LayoutArea layoutArea = childRenderer.getOccupiedArea();
                childRenderer.setSpecialScriptFirstNotFittingIndex(splitPosition);
                returnLayoutResult = childRenderer.layout(new LayoutContext(layoutArea, wasParentsHeightClipped));
                childRenderer.setSpecialScriptFirstNotFittingIndex(-1);
            } else {
                boolean z3 = wasParentsHeightClipped;
            }
        } else {
            boolean z4 = wasParentsHeightClipped;
        }
        return new LastFittingChildRendererData(indexOfRendererContainingLastFullyFittingWord2, returnLayoutResult);
    }

    /* access modifiers changed from: package-private */
    public void updateFloatsOverflowedToNextLine(List<IRenderer> floatsOverflowedToNextLine, Set<Integer> indicesOfFloats, int indexOfRendererContainingLastFullyFittingWord) {
        for (Integer intValue : indicesOfFloats) {
            int index = intValue.intValue();
            if (index > indexOfRendererContainingLastFullyFittingWord) {
                floatsOverflowedToNextLine.remove(this.childRenderers.get(index));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SpecialScriptsContainingSequenceStatus getSpecialScriptsContainingSequenceStatus(int analyzedTextRendererIndex) {
        boolean moveSequenceContainingSpecialScriptsOnNextLine = false;
        boolean moveToPreviousTextRendererContainingSpecialScripts = false;
        boolean forcedSplit = true;
        if (analyzedTextRendererIndex > 0) {
            IRenderer prevChildRenderer = (IRenderer) this.childRenderers.get(analyzedTextRendererIndex - 1);
            if (prevChildRenderer instanceof TextRenderer) {
                if (((TextRenderer) prevChildRenderer).textContainsSpecialScriptGlyphs(true)) {
                    moveToPreviousTextRendererContainingSpecialScripts = true;
                } else {
                    moveSequenceContainingSpecialScriptsOnNextLine = true;
                }
            } else if ((prevChildRenderer instanceof ImageRenderer) || isInlineBlockChild(prevChildRenderer)) {
                moveSequenceContainingSpecialScriptsOnNextLine = true;
            }
        }
        if (moveToPreviousTextRendererContainingSpecialScripts || moveSequenceContainingSpecialScriptsOnNextLine) {
            forcedSplit = false;
        }
        if (moveSequenceContainingSpecialScriptsOnNextLine) {
            return SpecialScriptsContainingSequenceStatus.MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE;
        }
        if (forcedSplit) {
            return SpecialScriptsContainingSequenceStatus.FORCED_SPLIT;
        }
        return SpecialScriptsContainingSequenceStatus.MOVE_TO_PREVIOUS_TEXT_RENDERER_CONTAINING_SPECIAL_SCRIPTS;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateBidiLevels(int r10, com.itextpdf.layout.property.BaseDirection r11) {
        /*
            r9 = this;
            if (r10 == 0) goto L_0x000d
            byte[] r0 = r9.levels
            if (r0 == 0) goto L_0x000d
            int r1 = r0.length
            byte[] r0 = java.util.Arrays.copyOfRange(r0, r10, r1)
            r9.levels = r0
        L_0x000d:
            r0 = 0
            byte[] r1 = r9.levels
            if (r1 != 0) goto L_0x0080
            if (r11 == 0) goto L_0x0080
            com.itextpdf.layout.property.BaseDirection r1 = com.itextpdf.layout.property.BaseDirection.NO_BIDI
            if (r11 == r1) goto L_0x0080
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0 = r1
            r1 = 0
            java.util.List r2 = r9.childRenderers
            java.util.Iterator r2 = r2.iterator()
        L_0x0025:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x006e
            java.lang.Object r3 = r2.next()
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            if (r1 == 0) goto L_0x0034
            goto L_0x006e
        L_0x0034:
            boolean r4 = r3 instanceof com.itextpdf.layout.renderer.TextRenderer
            if (r4 == 0) goto L_0x006d
            r4 = r3
            com.itextpdf.layout.renderer.TextRenderer r4 = (com.itextpdf.layout.renderer.TextRenderer) r4
            com.itextpdf.io.font.otf.GlyphLine r4 = r4.getText()
            int r5 = r4.start
        L_0x0041:
            int r6 = r4.end
            if (r5 >= r6) goto L_0x006d
            com.itextpdf.io.font.otf.Glyph r6 = r4.get(r5)
            boolean r7 = com.itextpdf.p026io.util.TextUtil.isNewLine((com.itextpdf.p026io.font.otf.Glyph) r6)
            if (r7 == 0) goto L_0x0051
            r1 = 1
            goto L_0x006d
        L_0x0051:
            boolean r7 = r6.hasValidUnicode()
            if (r7 == 0) goto L_0x005c
            int r7 = r6.getUnicode()
            goto L_0x0063
        L_0x005c:
            char[] r7 = r6.getUnicodeChars()
            r8 = 0
            char r7 = r7[r8]
        L_0x0063:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r7)
            r0.add(r8)
            int r5 = r5 + 1
            goto L_0x0041
        L_0x006d:
            goto L_0x0025
        L_0x006e:
            int r2 = r0.size()
            if (r2 <= 0) goto L_0x007d
            int[] r2 = com.itextpdf.p026io.util.ArrayUtil.toIntArray(r0)
            byte[] r2 = com.itextpdf.layout.renderer.TypographyUtils.getBidiLevels(r11, r2)
            goto L_0x007e
        L_0x007d:
            r2 = 0
        L_0x007e:
            r9.levels = r2
        L_0x0080:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.LineRenderer.updateBidiLevels(int, com.itextpdf.layout.property.BaseDirection):void");
    }

    private void resolveChildrenFonts() {
        List<IRenderer> newChildRenderers = new ArrayList<>(this.childRenderers.size());
        boolean updateChildRenderers = false;
        for (IRenderer child : this.childRenderers) {
            if (!(child instanceof TextRenderer)) {
                newChildRenderers.add(child);
            } else if (((TextRenderer) child).resolveFonts(newChildRenderers)) {
                updateChildRenderers = true;
            }
        }
        if (updateChildRenderers) {
            this.childRenderers = newChildRenderers;
        }
    }

    private float decreaseRelativeWidthByChildAdditionalWidth(IRenderer childRenderer, float normalizedChildWidth) {
        if (!(childRenderer instanceof AbstractRenderer)) {
            return normalizedChildWidth;
        }
        Rectangle dummyRect = new Rectangle(normalizedChildWidth, 0.0f);
        ((AbstractRenderer) childRenderer).applyMargins(dummyRect, false);
        if (!isBorderBoxSizing(childRenderer)) {
            ((AbstractRenderer) childRenderer).applyBorderBox(dummyRect, false);
            ((AbstractRenderer) childRenderer).applyPaddings(dummyRect, false);
        }
        return dummyRect.getWidth();
    }

    private boolean isInlineBlockChild(IRenderer child) {
        return (child instanceof BlockRenderer) || (child instanceof TableRenderer);
    }

    private static void fillActualTextChunkRelatedLists(GlyphLine glyphLine, List<Integer> amountOfCharsBetweenTextStartAndActualTextChunk, List<Integer> glyphLineBasedIndicesOfActualTextChunkEnds) {
        ActualTextIterator actualTextIterator = new ActualTextIterator(glyphLine);
        int amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = 0;
        while (actualTextIterator.hasNext()) {
            GlyphLine.GlyphLinePart part = actualTextIterator.next();
            if (part.actualText != null) {
                int nextAmountOfChars = part.actualText.length() + amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph;
                amountOfCharsBetweenTextStartAndActualTextChunk.add(Integer.valueOf(nextAmountOfChars));
                glyphLineBasedIndicesOfActualTextChunkEnds.add(Integer.valueOf(part.end));
                amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = nextAmountOfChars;
            } else {
                for (int j = part.start; j < part.end; j++) {
                    char[] chars = glyphLine.get(j).getChars();
                    int nextAmountOfChars2 = (chars != null ? chars.length : 0) + amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph;
                    amountOfCharsBetweenTextStartAndActualTextChunk.add(Integer.valueOf(nextAmountOfChars2));
                    glyphLineBasedIndicesOfActualTextChunkEnds.add(Integer.valueOf(j + 1));
                    amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = nextAmountOfChars2;
                }
            }
        }
    }

    private static List<Integer> convertPossibleBreakPointsToGlyphLineBased(List<Integer> possibleBreakPoints, List<Integer> amountOfChars, List<Integer> indices) {
        if (possibleBreakPoints.isEmpty()) {
            possibleBreakPoints.add(-1);
            return possibleBreakPoints;
        }
        List<Integer> glyphLineBased = new ArrayList<>();
        for (Integer intValue : possibleBreakPoints) {
            int found = TextRenderer.findPossibleBreaksSplitPosition(amountOfChars, intValue.intValue(), true);
            if (found >= 0) {
                glyphLineBased.add(indices.get(found));
            }
        }
        return glyphLineBased;
    }

    static class RendererGlyph {
        public Glyph glyph;
        public TextRenderer renderer;

        public RendererGlyph(Glyph glyph2, TextRenderer textRenderer) {
            this.glyph = glyph2;
            this.renderer = textRenderer;
        }
    }

    class SpecialScriptsContainingTextRendererSequenceInfo {
        List<Integer> indicesOfFloating;
        public int numberOfSequentialTextRenderers;
        public String sequentialTextContent;

        public SpecialScriptsContainingTextRendererSequenceInfo(int numberOfSequentialTextRenderers2, String sequentialTextContent2, List<Integer> indicesOfFloating2) {
            this.numberOfSequentialTextRenderers = numberOfSequentialTextRenderers2;
            this.sequentialTextContent = sequentialTextContent2;
            this.indicesOfFloating = indicesOfFloating2;
        }
    }

    class LastFittingChildRendererData {
        public int childIndex;
        public LayoutResult childLayoutResult;

        public LastFittingChildRendererData(int childIndex2, LayoutResult childLayoutResult2) {
            this.childIndex = childIndex2;
            this.childLayoutResult = childLayoutResult2;
        }
    }
}

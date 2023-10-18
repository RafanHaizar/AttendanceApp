package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.property.ClearPropertyValue;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.LoggerFactory;

class FloatingHelper {
    private FloatingHelper() {
    }

    static void adjustLineAreaAccordingToFloats(List<Rectangle> floatRendererAreas, Rectangle layoutBox) {
        adjustLayoutBoxAccordingToFloats(floatRendererAreas, layoutBox, (Float) null, 0.0f, (MarginsCollapseHandler) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0009  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0043 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static float adjustLayoutBoxAccordingToFloats(java.util.List<com.itextpdf.kernel.geom.Rectangle> r9, com.itextpdf.kernel.geom.Rectangle r10, java.lang.Float r11, float r12, com.itextpdf.layout.margincollapse.MarginsCollapseHandler r13) {
        /*
            r0 = r12
            r1 = 0
        L_0x0002:
            r2 = 2139095039(0x7f7fffff, float:3.4028235E38)
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L_0x0034
            r5 = r1[r4]
            if (r5 == 0) goto L_0x0014
            r5 = r1[r4]
            float r5 = r5.getBottom()
            goto L_0x0017
        L_0x0014:
            r5 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x0017:
            r6 = r1[r3]
            if (r6 == 0) goto L_0x0022
            r6 = r1[r3]
            float r6 = r6.getBottom()
            goto L_0x0025
        L_0x0022:
            r6 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x0025:
            float r7 = java.lang.Math.min(r5, r6)
            float r8 = r10.getY()
            float r7 = r7 - r8
            float r8 = r10.getHeight()
            float r8 = r8 - r7
            r0 = r8
        L_0x0034:
            float r5 = r10.getTop()
            float r5 = r5 - r0
            java.util.List r5 = getBoxesAtYLevel(r9, r5)
            boolean r6 = r5.isEmpty()
            if (r6 == 0) goto L_0x0047
            applyClearance(r10, r13, r0, r4)
            return r0
        L_0x0047:
            com.itextpdf.kernel.geom.Rectangle[] r1 = findLastLeftAndRightBoxes(r10, r5)
            r6 = r1[r4]
            if (r6 == 0) goto L_0x0056
            r6 = r1[r4]
            float r6 = r6.getRight()
            goto L_0x0057
        L_0x0056:
            r6 = 1
        L_0x0057:
            r7 = r1[r3]
            if (r7 == 0) goto L_0x0061
            r2 = r1[r3]
            float r2 = r2.getLeft()
        L_0x0061:
            int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r3 > 0) goto L_0x008f
            float r3 = r10.getRight()
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x008f
            float r3 = r10.getLeft()
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x0076
            goto L_0x008f
        L_0x0076:
            float r3 = r10.getRight()
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0082
            float r2 = r10.getRight()
        L_0x0082:
            float r3 = r10.getLeft()
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x0094
            float r6 = r10.getLeft()
            goto L_0x0094
        L_0x008f:
            float r6 = r10.getLeft()
            r2 = r6
        L_0x0094:
            if (r11 == 0) goto L_0x00a0
            float r3 = r11.floatValue()
            float r5 = r2 - r6
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x0002
        L_0x00a0:
            float r3 = r10.getWidth()
            float r5 = r2 - r6
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x00b3
            com.itextpdf.kernel.geom.Rectangle r3 = r10.setX(r6)
            float r5 = r2 - r6
            r3.setWidth(r5)
        L_0x00b3:
            applyClearance(r10, r13, r0, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.FloatingHelper.adjustLayoutBoxAccordingToFloats(java.util.List, com.itextpdf.kernel.geom.Rectangle, java.lang.Float, float, com.itextpdf.layout.margincollapse.MarginsCollapseHandler):float");
    }

    static Float calculateLineShiftUnderFloats(List<Rectangle> floatRendererAreas, Rectangle layoutBox) {
        float maxLastFloatBottom;
        List<Rectangle> boxesAtYLevel = getBoxesAtYLevel(floatRendererAreas, layoutBox.getTop());
        if (boxesAtYLevel.isEmpty()) {
            return null;
        }
        Rectangle[] lastLeftAndRightBoxes = findLastLeftAndRightBoxes(layoutBox, boxesAtYLevel);
        float left = lastLeftAndRightBoxes[0] != null ? lastLeftAndRightBoxes[0].getRight() : layoutBox.getLeft();
        float right = lastLeftAndRightBoxes[1] != null ? lastLeftAndRightBoxes[1].getLeft() : layoutBox.getRight();
        if (layoutBox.getLeft() >= left && layoutBox.getRight() <= right) {
            return null;
        }
        if (lastLeftAndRightBoxes[0] != null && lastLeftAndRightBoxes[1] != null) {
            maxLastFloatBottom = Math.max(lastLeftAndRightBoxes[0].getBottom(), lastLeftAndRightBoxes[1].getBottom());
        } else if (lastLeftAndRightBoxes[0] != null) {
            maxLastFloatBottom = lastLeftAndRightBoxes[0].getBottom();
        } else {
            maxLastFloatBottom = lastLeftAndRightBoxes[1].getBottom();
        }
        return Float.valueOf((layoutBox.getTop() - maxLastFloatBottom) + 1.0E-4f);
    }

    static void adjustFloatedTableLayoutBox(TableRenderer tableRenderer, Rectangle layoutBox, float tableWidth, List<Rectangle> floatRendererAreas, FloatPropertyValue floatPropertyValue) {
        tableRenderer.setProperty(28, (Object) null);
        UnitValue[] margins = tableRenderer.getMargins();
        Class<FloatingHelper> cls = FloatingHelper.class;
        if (!margins[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
        }
        if (!margins[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        adjustBlockAreaAccordingToFloatRenderers(floatRendererAreas, layoutBox, margins[1].getValue() + tableWidth + margins[3].getValue(), FloatPropertyValue.LEFT.equals(floatPropertyValue));
    }

    static Float adjustFloatedBlockLayoutBox(AbstractRenderer renderer, Rectangle parentBBox, Float blockWidth, List<Rectangle> floatRendererAreas, FloatPropertyValue floatPropertyValue, OverflowPropertyValue overflowX) {
        float floatElemWidth;
        renderer.setProperty(28, (Object) null);
        boolean overflowFit = AbstractRenderer.isOverflowFit(overflowX);
        if (blockWidth != null) {
            floatElemWidth = blockWidth.floatValue() + AbstractRenderer.calculateAdditionalWidth(renderer);
            if (overflowFit && floatElemWidth > parentBBox.getWidth()) {
                floatElemWidth = parentBBox.getWidth();
            }
        } else {
            MinMaxWidth minMaxWidth = calculateMinMaxWidthForFloat(renderer, floatPropertyValue);
            float maxWidth = minMaxWidth.getMaxWidth();
            if (maxWidth > parentBBox.getWidth()) {
                maxWidth = parentBBox.getWidth();
            }
            if (!overflowFit && minMaxWidth.getMinWidth() > parentBBox.getWidth()) {
                maxWidth = minMaxWidth.getMinWidth();
            }
            blockWidth = Float.valueOf((maxWidth - minMaxWidth.getAdditionalWidth()) + 1.0E-4f);
            floatElemWidth = maxWidth + 1.0E-4f;
        }
        adjustBlockAreaAccordingToFloatRenderers(floatRendererAreas, parentBBox, floatElemWidth, FloatPropertyValue.LEFT.equals(floatPropertyValue));
        return blockWidth;
    }

    private static void adjustBlockAreaAccordingToFloatRenderers(List<Rectangle> floatRendererAreas, Rectangle layoutBox, float blockWidth, boolean isFloatLeft) {
        float currY;
        if (!floatRendererAreas.isEmpty()) {
            if (floatRendererAreas.get(floatRendererAreas.size() - 1).getTop() < layoutBox.getTop()) {
                currY = floatRendererAreas.get(floatRendererAreas.size() - 1).getTop();
            } else {
                currY = layoutBox.getTop();
            }
            Rectangle[] lastLeftAndRightBoxes = null;
            float left = 0.0f;
            float right = 0.0f;
            while (true) {
                if (lastLeftAndRightBoxes == null || right - left < blockWidth) {
                    if (lastLeftAndRightBoxes != null) {
                        if (isFloatLeft) {
                            currY = (lastLeftAndRightBoxes[0] != null ? lastLeftAndRightBoxes[0] : lastLeftAndRightBoxes[1]).getBottom();
                        } else {
                            currY = (lastLeftAndRightBoxes[1] != null ? lastLeftAndRightBoxes[1] : lastLeftAndRightBoxes[0]).getBottom();
                        }
                    }
                    layoutBox.setHeight(currY - layoutBox.getY());
                    List<Rectangle> yLevelBoxes = getBoxesAtYLevel(floatRendererAreas, currY);
                    if (!yLevelBoxes.isEmpty()) {
                        lastLeftAndRightBoxes = findLastLeftAndRightBoxes(layoutBox, yLevelBoxes);
                        left = lastLeftAndRightBoxes[0] != null ? lastLeftAndRightBoxes[0].getRight() : layoutBox.getLeft();
                        right = lastLeftAndRightBoxes[1] != null ? lastLeftAndRightBoxes[1].getLeft() : layoutBox.getRight();
                    } else if (!isFloatLeft) {
                        adjustBoxForFloatRight(layoutBox, blockWidth);
                        return;
                    } else {
                        return;
                    }
                } else {
                    layoutBox.setX(left);
                    layoutBox.setWidth(right - left);
                    if (!isFloatLeft) {
                        adjustBoxForFloatRight(layoutBox, blockWidth);
                        return;
                    }
                    return;
                }
            }
        } else if (!isFloatLeft) {
            adjustBoxForFloatRight(layoutBox, blockWidth);
        }
    }

    static void removeFloatsAboveRendererBottom(List<Rectangle> floatRendererAreas, IRenderer renderer) {
        if (!isRendererFloating(renderer)) {
            float bottom = renderer.getOccupiedArea().getBBox().getBottom();
            for (int i = floatRendererAreas.size() - 1; i >= 0; i--) {
                if (floatRendererAreas.get(i).getBottom() >= bottom) {
                    floatRendererAreas.remove(i);
                }
            }
        }
    }

    static LayoutArea adjustResultOccupiedAreaForFloatAndClear(IRenderer renderer, List<Rectangle> floatRendererAreas, Rectangle parentBBox, float clearHeightCorrection, boolean marginsCollapsingEnabled) {
        LayoutArea occupiedArea = renderer.getOccupiedArea();
        LayoutArea editedArea = occupiedArea;
        if (isRendererFloating(renderer)) {
            LayoutArea editedArea2 = occupiedArea.clone();
            if (occupiedArea.getBBox().getWidth() > 0.0f) {
                floatRendererAreas.add(occupiedArea.getBBox());
            }
            editedArea2.getBBox().setY(parentBBox.getTop());
            editedArea2.getBBox().setHeight(0.0f);
            return editedArea2;
        } else if (clearHeightCorrection <= 0.0f || marginsCollapsingEnabled) {
            return editedArea;
        } else {
            LayoutArea editedArea3 = occupiedArea.clone();
            editedArea3.getBBox().increaseHeight(clearHeightCorrection);
            return editedArea3;
        }
    }

    static void includeChildFloatsInOccupiedArea(List<Rectangle> floatRendererAreas, IRenderer renderer, Set<Rectangle> nonChildFloatingRendererAreas) {
        renderer.getOccupiedArea().setBBox(includeChildFloatsInOccupiedArea(floatRendererAreas, renderer.getOccupiedArea().getBBox(), nonChildFloatingRendererAreas));
    }

    static Rectangle includeChildFloatsInOccupiedArea(List<Rectangle> floatRendererAreas, Rectangle occupiedAreaBbox, Set<Rectangle> nonChildFloatingRendererAreas) {
        for (Rectangle floatBox : floatRendererAreas) {
            if (!nonChildFloatingRendererAreas.contains(floatBox)) {
                occupiedAreaBbox = Rectangle.getCommonRectangle(occupiedAreaBbox, floatBox);
            }
        }
        return occupiedAreaBbox;
    }

    static MinMaxWidth calculateMinMaxWidthForFloat(AbstractRenderer renderer, FloatPropertyValue floatPropertyVal) {
        boolean floatPropIsRendererOwn = renderer.hasOwnProperty(99);
        renderer.setProperty(99, FloatPropertyValue.NONE);
        MinMaxWidth kidMinMaxWidth = renderer.getMinMaxWidth();
        if (floatPropIsRendererOwn) {
            renderer.setProperty(99, floatPropertyVal);
        } else {
            renderer.deleteOwnProperty(99);
        }
        return kidMinMaxWidth;
    }

    static float calculateClearHeightCorrection(IRenderer renderer, List<Rectangle> floatRendererAreas, Rectangle parentBBox) {
        float currY;
        ClearPropertyValue clearPropertyValue = (ClearPropertyValue) renderer.getProperty(100);
        if (clearPropertyValue == null || floatRendererAreas.isEmpty()) {
            return 0.0f;
        }
        if (floatRendererAreas.get(floatRendererAreas.size() - 1).getTop() < parentBBox.getTop()) {
            currY = floatRendererAreas.get(floatRendererAreas.size() - 1).getTop();
        } else {
            currY = parentBBox.getTop();
        }
        Rectangle[] lastLeftAndRightBoxes = findLastLeftAndRightBoxes(parentBBox, getBoxesAtYLevel(floatRendererAreas, currY));
        float lowestFloatBottom = Float.MAX_VALUE;
        boolean isBoth = clearPropertyValue.equals(ClearPropertyValue.BOTH);
        if ((clearPropertyValue.equals(ClearPropertyValue.LEFT) || isBoth) && lastLeftAndRightBoxes[0] != null) {
            for (Rectangle floatBox : floatRendererAreas) {
                if (floatBox.getBottom() < lowestFloatBottom && floatBox.getLeft() <= lastLeftAndRightBoxes[0].getLeft()) {
                    lowestFloatBottom = floatBox.getBottom();
                }
            }
        }
        if ((clearPropertyValue.equals(ClearPropertyValue.RIGHT) || isBoth) && lastLeftAndRightBoxes[1] != null) {
            for (Rectangle floatBox2 : floatRendererAreas) {
                if (floatBox2.getBottom() < lowestFloatBottom && floatBox2.getRight() >= lastLeftAndRightBoxes[1].getRight()) {
                    lowestFloatBottom = floatBox2.getBottom();
                }
            }
        }
        if (lowestFloatBottom < Float.MAX_VALUE) {
            return (parentBBox.getTop() - lowestFloatBottom) + 1.0E-4f;
        }
        return 0.0f;
    }

    static void applyClearance(Rectangle layoutBox, MarginsCollapseHandler marginsCollapseHandler, float clearHeightAdjustment, boolean isFloat) {
        if (clearHeightAdjustment > 0.0f) {
            if (marginsCollapseHandler == null || isFloat) {
                layoutBox.decreaseHeight(clearHeightAdjustment);
            } else {
                marginsCollapseHandler.applyClearance(clearHeightAdjustment);
            }
        }
    }

    static boolean isRendererFloating(IRenderer renderer) {
        return isRendererFloating(renderer, (FloatPropertyValue) renderer.getProperty(99));
    }

    static boolean isRendererFloating(IRenderer renderer, FloatPropertyValue kidFloatPropertyVal) {
        Integer position = (Integer) renderer.getProperty(52);
        if (!(position == null || position.intValue() != 3) || kidFloatPropertyVal == null || kidFloatPropertyVal.equals(FloatPropertyValue.NONE)) {
            return false;
        }
        return true;
    }

    static boolean isClearanceApplied(List<IRenderer> floatingRenderers, ClearPropertyValue clearPropertyValue) {
        if (clearPropertyValue == null || clearPropertyValue.equals(ClearPropertyValue.NONE)) {
            return false;
        }
        for (IRenderer floatingRenderer : floatingRenderers) {
            FloatPropertyValue floatPropertyValue = (FloatPropertyValue) floatingRenderer.getProperty(99);
            if (clearPropertyValue.equals(ClearPropertyValue.BOTH)) {
                return true;
            }
            if (floatPropertyValue.equals(FloatPropertyValue.LEFT) && clearPropertyValue.equals(ClearPropertyValue.LEFT)) {
                return true;
            }
            if (floatPropertyValue.equals(FloatPropertyValue.RIGHT) && clearPropertyValue.equals(ClearPropertyValue.RIGHT)) {
                return true;
            }
        }
        return false;
    }

    static void removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(IRenderer overflowRenderer) {
        overflowRenderer.setProperty(6, (Object) null);
        overflowRenderer.setProperty(90, (Object) null);
        overflowRenderer.setProperty(106, (Object) null);
        Border[] borders = AbstractRenderer.getBorders(overflowRenderer);
        overflowRenderer.setProperty(13, (Object) null);
        overflowRenderer.setProperty(10, (Object) null);
        if (borders[1] != null) {
            overflowRenderer.setProperty(12, new SolidBorder(ColorConstants.BLACK, borders[1].getWidth(), 0.0f));
        }
        if (borders[3] != null) {
            overflowRenderer.setProperty(11, new SolidBorder(ColorConstants.BLACK, borders[3].getWidth(), 0.0f));
        }
        overflowRenderer.setProperty(46, UnitValue.createPointValue(0.0f));
        overflowRenderer.setProperty(43, UnitValue.createPointValue(0.0f));
        overflowRenderer.setProperty(50, UnitValue.createPointValue(0.0f));
        overflowRenderer.setProperty(47, UnitValue.createPointValue(0.0f));
    }

    private static void adjustBoxForFloatRight(Rectangle layoutBox, float blockWidth) {
        layoutBox.setX(layoutBox.getRight() - blockWidth);
        layoutBox.setWidth(blockWidth);
    }

    private static Rectangle[] findLastLeftAndRightBoxes(Rectangle layoutBox, List<Rectangle> yLevelBoxes) {
        Rectangle lastLeftFloatAtY = null;
        Rectangle lastRightFloatAtY = null;
        float left = layoutBox.getLeft();
        for (Rectangle box : yLevelBoxes) {
            if (box.getLeft() < left) {
                left = box.getLeft();
            }
        }
        for (Rectangle box2 : yLevelBoxes) {
            if (left < box2.getLeft() || left >= box2.getRight()) {
                lastRightFloatAtY = box2;
            } else {
                lastLeftFloatAtY = box2;
                left = box2.getRight();
            }
        }
        return new Rectangle[]{lastLeftFloatAtY, lastRightFloatAtY};
    }

    private static List<Rectangle> getBoxesAtYLevel(List<Rectangle> floatRendererAreas, float currY) {
        List<Rectangle> yLevelBoxes = new ArrayList<>();
        for (Rectangle box : floatRendererAreas) {
            if (box.getBottom() + 1.0E-4f < currY && box.getTop() + 1.0E-4f >= currY) {
                yLevelBoxes.add(box);
            }
        }
        return yLevelBoxes;
    }
}

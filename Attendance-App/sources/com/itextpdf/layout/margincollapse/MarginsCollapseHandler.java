package com.itextpdf.layout.margincollapse;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.BlockFormattingContextUtil;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class MarginsCollapseHandler {
    private MarginsCollapseInfo backupCollapseInfo;
    private Rectangle backupLayoutBox;
    private MarginsCollapseInfo childMarginInfo;
    private MarginsCollapseInfo collapseInfo;
    private int firstNotEmptyKidIndex = 0;
    private boolean lastKidCollapsedAfterHasClearanceApplied;
    private MarginsCollapseInfo prevChildMarginInfo;
    private int processedChildrenNum = 0;
    private IRenderer renderer;
    private List<IRenderer> rendererChildren = new ArrayList();

    public MarginsCollapseHandler(IRenderer renderer2, MarginsCollapseInfo marginsCollapseInfo) {
        this.renderer = renderer2;
        this.collapseInfo = marginsCollapseInfo != null ? marginsCollapseInfo : new MarginsCollapseInfo();
    }

    public void processFixedHeightAdjustment(float heightDelta) {
        MarginsCollapseInfo marginsCollapseInfo = this.collapseInfo;
        marginsCollapseInfo.setBufferSpaceOnTop(marginsCollapseInfo.getBufferSpaceOnTop() + heightDelta);
        MarginsCollapseInfo marginsCollapseInfo2 = this.collapseInfo;
        marginsCollapseInfo2.setBufferSpaceOnBottom(marginsCollapseInfo2.getBufferSpaceOnBottom() + heightDelta);
    }

    public MarginsCollapseInfo startChildMarginsHandling(IRenderer child, Rectangle layoutBox) {
        boolean childIsBlockElement = true;
        if (this.backupLayoutBox != null) {
            restoreLayoutBoxAfterFailedLayoutAttempt(layoutBox);
            int i = this.processedChildrenNum - 1;
            this.processedChildrenNum = i;
            removeRendererChild(i);
            this.childMarginInfo = null;
        }
        this.rendererChildren.add(child);
        int childIndex = this.processedChildrenNum;
        this.processedChildrenNum = childIndex + 1;
        if (rendererIsFloated(child) || !isBlockElement(child)) {
            childIsBlockElement = false;
        }
        this.backupLayoutBox = layoutBox.clone();
        MarginsCollapseInfo marginsCollapseInfo = new MarginsCollapseInfo();
        this.backupCollapseInfo = marginsCollapseInfo;
        this.collapseInfo.copyTo(marginsCollapseInfo);
        prepareBoxForLayoutAttempt(layoutBox, childIndex, childIsBlockElement);
        if (childIsBlockElement) {
            this.childMarginInfo = createMarginsInfoForBlockChild(childIndex);
        }
        return this.childMarginInfo;
    }

    public void applyClearance(float clearHeightCorrection) {
        this.collapseInfo.setClearanceApplied(true);
        this.collapseInfo.getCollapseBefore().joinMargin(clearHeightCorrection);
    }

    private MarginsCollapseInfo createMarginsInfoForBlockChild(int childIndex) {
        MarginsCollapse parentCollapseBefore;
        boolean ignoreChildTopMargin = false;
        boolean ignoreChildBottomMargin = lastChildMarginAdjoinedToParent(this.renderer);
        if (childIndex == this.firstNotEmptyKidIndex) {
            ignoreChildTopMargin = firstChildMarginAdjoinedToParent(this.renderer);
        }
        if (childIndex == 0) {
            parentCollapseBefore = ignoreChildTopMargin ? this.collapseInfo.getCollapseBefore() : new MarginsCollapse();
        } else {
            MarginsCollapseInfo marginsCollapseInfo = this.prevChildMarginInfo;
            MarginsCollapse prevChildCollapseAfter = marginsCollapseInfo != null ? marginsCollapseInfo.getOwnCollapseAfter() : null;
            parentCollapseBefore = prevChildCollapseAfter != null ? prevChildCollapseAfter : new MarginsCollapse();
        }
        MarginsCollapseInfo childMarginsInfo = new MarginsCollapseInfo(ignoreChildTopMargin, ignoreChildBottomMargin, parentCollapseBefore, ignoreChildBottomMargin ? this.collapseInfo.getCollapseAfter().clone() : new MarginsCollapse());
        if (ignoreChildTopMargin && childIndex == this.firstNotEmptyKidIndex) {
            childMarginsInfo.setBufferSpaceOnTop(this.collapseInfo.getBufferSpaceOnTop());
        }
        if (ignoreChildBottomMargin) {
            childMarginsInfo.setBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom());
        }
        return childMarginsInfo;
    }

    public void endChildMarginsHandling(Rectangle layoutBox) {
        boolean z = true;
        int childIndex = this.processedChildrenNum - 1;
        if (!rendererIsFloated(getRendererChild(childIndex))) {
            MarginsCollapseInfo marginsCollapseInfo = this.childMarginInfo;
            if (marginsCollapseInfo != null) {
                if (this.firstNotEmptyKidIndex == childIndex && marginsCollapseInfo.isSelfCollapsing()) {
                    this.firstNotEmptyKidIndex = childIndex + 1;
                }
                MarginsCollapseInfo marginsCollapseInfo2 = this.collapseInfo;
                marginsCollapseInfo2.setSelfCollapsing(marginsCollapseInfo2.isSelfCollapsing() && this.childMarginInfo.isSelfCollapsing());
                if (!this.childMarginInfo.isSelfCollapsing() || !this.childMarginInfo.isClearanceApplied()) {
                    z = false;
                }
                this.lastKidCollapsedAfterHasClearanceApplied = z;
            } else {
                this.lastKidCollapsedAfterHasClearanceApplied = false;
                this.collapseInfo.setSelfCollapsing(false);
            }
            if (this.prevChildMarginInfo != null) {
                fixPrevChildOccupiedArea(childIndex);
                updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(this.prevChildMarginInfo.getOwnCollapseAfter());
            }
            if (this.firstNotEmptyKidIndex == childIndex && firstChildMarginAdjoinedToParent(this.renderer) && !this.collapseInfo.isSelfCollapsing()) {
                getRidOfCollapseArtifactsAtopOccupiedArea();
                if (this.childMarginInfo != null) {
                    processUsedChildBufferSpaceOnTop(layoutBox);
                }
            }
            this.prevChildMarginInfo = this.childMarginInfo;
            this.childMarginInfo = null;
            this.backupLayoutBox = null;
            this.backupCollapseInfo = null;
        }
    }

    public void startMarginsCollapse(Rectangle parentBBox) {
        this.collapseInfo.getCollapseBefore().joinMargin(getModelTopMargin(this.renderer));
        this.collapseInfo.getCollapseAfter().joinMargin(getModelBottomMargin(this.renderer));
        if (!firstChildMarginAdjoinedToParent(this.renderer)) {
            applyTopMargin(parentBBox, this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize());
        }
        if (!lastChildMarginAdjoinedToParent(this.renderer)) {
            applyBottomMargin(parentBBox, this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize());
        }
        ignoreModelTopMargin(this.renderer);
        ignoreModelBottomMargin(this.renderer);
    }

    public void endMarginsCollapse(Rectangle layoutBox) {
        MarginsCollapse ownCollapseAfter;
        if (this.backupLayoutBox != null) {
            restoreLayoutBoxAfterFailedLayoutAttempt(layoutBox);
        }
        MarginsCollapseInfo marginsCollapseInfo = this.prevChildMarginInfo;
        if (marginsCollapseInfo != null) {
            updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(marginsCollapseInfo.getCollapseAfter());
        }
        boolean lastChildMarginJoinedToParent = true;
        boolean couldBeSelfCollapsing = marginsCouldBeSelfCollapsing(this.renderer) && !this.lastKidCollapsedAfterHasClearanceApplied;
        boolean blockHasNoKidsWithContent = this.collapseInfo.isSelfCollapsing();
        if (firstChildMarginAdjoinedToParent(this.renderer) && blockHasNoKidsWithContent && !couldBeSelfCollapsing) {
            addNotYetAppliedTopMargin(layoutBox);
        }
        MarginsCollapseInfo marginsCollapseInfo2 = this.collapseInfo;
        marginsCollapseInfo2.setSelfCollapsing(marginsCollapseInfo2.isSelfCollapsing() && couldBeSelfCollapsing);
        if (!blockHasNoKidsWithContent && this.lastKidCollapsedAfterHasClearanceApplied) {
            applySelfCollapsedKidMarginWithClearance(layoutBox);
        }
        MarginsCollapseInfo marginsCollapseInfo3 = this.prevChildMarginInfo;
        if (marginsCollapseInfo3 == null || !marginsCollapseInfo3.isIgnoreOwnMarginBottom() || this.lastKidCollapsedAfterHasClearanceApplied) {
            lastChildMarginJoinedToParent = false;
        }
        if (lastChildMarginJoinedToParent) {
            ownCollapseAfter = this.prevChildMarginInfo.getOwnCollapseAfter();
        } else {
            ownCollapseAfter = new MarginsCollapse();
        }
        ownCollapseAfter.joinMargin(getModelBottomMargin(this.renderer));
        this.collapseInfo.setOwnCollapseAfter(ownCollapseAfter);
        if (this.collapseInfo.isSelfCollapsing()) {
            MarginsCollapseInfo marginsCollapseInfo4 = this.prevChildMarginInfo;
            if (marginsCollapseInfo4 != null) {
                this.collapseInfo.setCollapseAfter(marginsCollapseInfo4.getCollapseAfter());
            } else {
                this.collapseInfo.getCollapseAfter().joinMargin(this.collapseInfo.getCollapseBefore());
                this.collapseInfo.getOwnCollapseAfter().joinMargin(this.collapseInfo.getCollapseBefore());
            }
            if (!this.collapseInfo.isIgnoreOwnMarginBottom() && !this.collapseInfo.isIgnoreOwnMarginTop()) {
                overrideModelBottomMargin(this.renderer, this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize());
            }
        } else {
            MarginsCollapse marginsCollapseBefore = this.collapseInfo.getCollapseBefore();
            if (!this.collapseInfo.isIgnoreOwnMarginTop()) {
                overrideModelTopMargin(this.renderer, marginsCollapseBefore.getCollapsedMarginsSize());
            }
            if (lastChildMarginJoinedToParent) {
                this.collapseInfo.setCollapseAfter(this.prevChildMarginInfo.getCollapseAfter());
            }
            if (!this.collapseInfo.isIgnoreOwnMarginBottom()) {
                overrideModelBottomMargin(this.renderer, this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize());
            }
        }
        if (!lastChildMarginAdjoinedToParent(this.renderer)) {
            return;
        }
        if (this.prevChildMarginInfo != null || blockHasNoKidsWithContent) {
            applyBottomMargin(layoutBox, this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize());
        }
    }

    private void updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(MarginsCollapse collapseAfter) {
        if (this.prevChildMarginInfo.isSelfCollapsing() && this.prevChildMarginInfo.isIgnoreOwnMarginTop()) {
            this.collapseInfo.getCollapseBefore().joinMargin(collapseAfter);
        }
    }

    private void prepareBoxForLayoutAttempt(Rectangle layoutBox, int childIndex, boolean childIsBlockElement) {
        MarginsCollapseInfo marginsCollapseInfo = this.prevChildMarginInfo;
        float ownCollapsedMargins = 0.0f;
        if (marginsCollapseInfo != null) {
            boolean prevChildCanApplyCollapseAfter = true;
            if (!marginsCollapseInfo.isIgnoreOwnMarginBottom() && (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop())) {
                layoutBox.setHeight(layoutBox.getHeight() + this.prevChildMarginInfo.getCollapseAfter().getCollapsedMarginsSize());
            }
            if (this.prevChildMarginInfo.isSelfCollapsing() && this.prevChildMarginInfo.isIgnoreOwnMarginTop()) {
                prevChildCanApplyCollapseAfter = false;
            }
            if (!childIsBlockElement && prevChildCanApplyCollapseAfter) {
                MarginsCollapse ownCollapseAfter = this.prevChildMarginInfo.getOwnCollapseAfter();
                if (ownCollapseAfter != null) {
                    ownCollapsedMargins = ownCollapseAfter.getCollapsedMarginsSize();
                }
                layoutBox.setHeight(layoutBox.getHeight() - ownCollapsedMargins);
            }
        } else if (childIndex > this.firstNotEmptyKidIndex && lastChildMarginAdjoinedToParent(this.renderer)) {
            float bottomIndent = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize() - this.collapseInfo.getUsedBufferSpaceOnBottom();
            MarginsCollapseInfo marginsCollapseInfo2 = this.collapseInfo;
            marginsCollapseInfo2.setBufferSpaceOnBottom(marginsCollapseInfo2.getBufferSpaceOnBottom() + this.collapseInfo.getUsedBufferSpaceOnBottom());
            this.collapseInfo.setUsedBufferSpaceOnBottom(0.0f);
            layoutBox.setY(layoutBox.getY() - bottomIndent);
            layoutBox.setHeight(layoutBox.getHeight() + bottomIndent);
        }
        if (!childIsBlockElement) {
            if (childIndex == this.firstNotEmptyKidIndex && firstChildMarginAdjoinedToParent(this.renderer)) {
                applyTopMargin(layoutBox, this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize());
            }
            if (lastChildMarginAdjoinedToParent(this.renderer)) {
                applyBottomMargin(layoutBox, this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize());
            }
        }
    }

    private void restoreLayoutBoxAfterFailedLayoutAttempt(Rectangle layoutBox) {
        layoutBox.setX(this.backupLayoutBox.getX()).setY(this.backupLayoutBox.getY()).setWidth(this.backupLayoutBox.getWidth()).setHeight(this.backupLayoutBox.getHeight());
        this.backupCollapseInfo.copyTo(this.collapseInfo);
        this.backupLayoutBox = null;
        this.backupCollapseInfo = null;
    }

    private void applyTopMargin(Rectangle box, float topIndent) {
        float bufferLeftoversOnTop = this.collapseInfo.getBufferSpaceOnTop() - topIndent;
        float usedTopBuffer = bufferLeftoversOnTop > 0.0f ? topIndent : this.collapseInfo.getBufferSpaceOnTop();
        this.collapseInfo.setUsedBufferSpaceOnTop(usedTopBuffer);
        subtractUsedTopBufferFromBottomBuffer(usedTopBuffer);
        if (bufferLeftoversOnTop >= 0.0f) {
            this.collapseInfo.setBufferSpaceOnTop(bufferLeftoversOnTop);
            box.moveDown(topIndent);
            return;
        }
        box.moveDown(this.collapseInfo.getBufferSpaceOnTop());
        this.collapseInfo.setBufferSpaceOnTop(0.0f);
        box.setHeight(box.getHeight() + bufferLeftoversOnTop);
    }

    private void applyBottomMargin(Rectangle box, float bottomIndent) {
        float bottomIndentLeftovers = bottomIndent - this.collapseInfo.getBufferSpaceOnBottom();
        if (bottomIndentLeftovers < 0.0f) {
            this.collapseInfo.setUsedBufferSpaceOnBottom(bottomIndent);
            this.collapseInfo.setBufferSpaceOnBottom(-bottomIndentLeftovers);
            return;
        }
        MarginsCollapseInfo marginsCollapseInfo = this.collapseInfo;
        marginsCollapseInfo.setUsedBufferSpaceOnBottom(marginsCollapseInfo.getBufferSpaceOnBottom());
        this.collapseInfo.setBufferSpaceOnBottom(0.0f);
        box.setY(box.getY() + bottomIndentLeftovers);
        box.setHeight(box.getHeight() - bottomIndentLeftovers);
    }

    private void processUsedChildBufferSpaceOnTop(Rectangle layoutBox) {
        float childUsedBufferSpaceOnTop = this.childMarginInfo.getUsedBufferSpaceOnTop();
        if (childUsedBufferSpaceOnTop > 0.0f) {
            if (childUsedBufferSpaceOnTop > this.collapseInfo.getBufferSpaceOnTop()) {
                childUsedBufferSpaceOnTop = this.collapseInfo.getBufferSpaceOnTop();
            }
            MarginsCollapseInfo marginsCollapseInfo = this.collapseInfo;
            marginsCollapseInfo.setBufferSpaceOnTop(marginsCollapseInfo.getBufferSpaceOnTop() - childUsedBufferSpaceOnTop);
            this.collapseInfo.setUsedBufferSpaceOnTop(childUsedBufferSpaceOnTop);
            layoutBox.moveDown(childUsedBufferSpaceOnTop);
            subtractUsedTopBufferFromBottomBuffer(childUsedBufferSpaceOnTop);
        }
    }

    private void subtractUsedTopBufferFromBottomBuffer(float usedTopBuffer) {
        if (this.collapseInfo.getBufferSpaceOnTop() > this.collapseInfo.getBufferSpaceOnBottom()) {
            float bufferLeftoversOnTop = this.collapseInfo.getBufferSpaceOnTop() - usedTopBuffer;
            if (bufferLeftoversOnTop < this.collapseInfo.getBufferSpaceOnBottom()) {
                this.collapseInfo.setBufferSpaceOnBottom(bufferLeftoversOnTop);
                return;
            }
            return;
        }
        MarginsCollapseInfo marginsCollapseInfo = this.collapseInfo;
        marginsCollapseInfo.setBufferSpaceOnBottom(marginsCollapseInfo.getBufferSpaceOnBottom() - usedTopBuffer);
    }

    private void fixPrevChildOccupiedArea(int childIndex) {
        IRenderer prevRenderer = getRendererChild(childIndex - 1);
        Rectangle bBox = prevRenderer.getOccupiedArea().getBBox();
        boolean prevChildCanApplyCollapseAfter = false;
        if (!this.prevChildMarginInfo.isIgnoreOwnMarginBottom() && (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop())) {
            float bottomMargin = this.prevChildMarginInfo.getCollapseAfter().getCollapsedMarginsSize();
            bBox.setHeight(bBox.getHeight() - bottomMargin);
            bBox.moveUp(bottomMargin);
            ignoreModelBottomMargin(prevRenderer);
        }
        boolean isNotBlockChild = !isBlockElement(getRendererChild(childIndex));
        if (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop()) {
            prevChildCanApplyCollapseAfter = true;
        }
        if (isNotBlockChild && prevChildCanApplyCollapseAfter) {
            float ownCollapsedMargins = this.prevChildMarginInfo.getOwnCollapseAfter().getCollapsedMarginsSize();
            bBox.setHeight(bBox.getHeight() + ownCollapsedMargins);
            bBox.moveDown(ownCollapsedMargins);
            overrideModelBottomMargin(prevRenderer, ownCollapsedMargins);
        }
    }

    private void addNotYetAppliedTopMargin(Rectangle layoutBox) {
        float indentTop = this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize();
        this.renderer.getOccupiedArea().getBBox().moveDown(indentTop);
        applyTopMargin(layoutBox, indentTop);
    }

    private void applySelfCollapsedKidMarginWithClearance(Rectangle layoutBox) {
        float clearedKidMarginWithClearance = this.prevChildMarginInfo.getOwnCollapseAfter().getCollapsedMarginsSize();
        this.renderer.getOccupiedArea().getBBox().increaseHeight(clearedKidMarginWithClearance).moveDown(clearedKidMarginWithClearance);
        layoutBox.decreaseHeight(clearedKidMarginWithClearance);
    }

    private IRenderer getRendererChild(int index) {
        return this.rendererChildren.get(index);
    }

    private IRenderer removeRendererChild(int index) {
        return this.rendererChildren.remove(index);
    }

    private void getRidOfCollapseArtifactsAtopOccupiedArea() {
        this.renderer.getOccupiedArea().getBBox().decreaseHeight(this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize());
    }

    private static boolean marginsCouldBeSelfCollapsing(IRenderer renderer2) {
        return !(renderer2 instanceof TableRenderer) && !rendererIsFloated(renderer2) && !hasBottomBorders(renderer2) && !hasTopBorders(renderer2) && !hasBottomPadding(renderer2) && !hasTopPadding(renderer2) && !hasPositiveHeight(renderer2) && (!isBlockElement(renderer2) || !(renderer2 instanceof AbstractRenderer) || !(((AbstractRenderer) renderer2).getParent() instanceof LineRenderer));
    }

    private static boolean firstChildMarginAdjoinedToParent(IRenderer parent) {
        return !BlockFormattingContextUtil.isRendererCreateBfc(parent) && !(parent instanceof TableRenderer) && !hasTopBorders(parent) && !hasTopPadding(parent);
    }

    private static boolean lastChildMarginAdjoinedToParent(IRenderer parent) {
        return !BlockFormattingContextUtil.isRendererCreateBfc(parent) && !(parent instanceof TableRenderer) && !hasBottomBorders(parent) && !hasBottomPadding(parent) && !hasHeightProp(parent);
    }

    private static boolean isBlockElement(IRenderer renderer2) {
        return (renderer2 instanceof BlockRenderer) || (renderer2 instanceof TableRenderer);
    }

    private static boolean hasHeightProp(IRenderer renderer2) {
        return renderer2.getModelElement().hasProperty(27);
    }

    private static boolean hasPositiveHeight(IRenderer renderer2) {
        float f;
        float height = renderer2.getOccupiedArea().getBBox().getHeight();
        if (height == 0.0f) {
            UnitValue heightPropVal = (UnitValue) renderer2.getProperty(27);
            UnitValue minHeightPropVal = (UnitValue) renderer2.getProperty(85);
            if (minHeightPropVal != null) {
                f = minHeightPropVal.getValue();
            } else {
                f = heightPropVal != null ? heightPropVal.getValue() : 0.0f;
            }
            height = f;
        }
        return height > 0.0f;
    }

    private static boolean hasTopPadding(IRenderer renderer2) {
        UnitValue padding = (UnitValue) renderer2.getModelElement().getProperty(50);
        if (padding != null && !padding.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) MarginsCollapseHandler.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 50));
        }
        if (padding == null || padding.getValue() <= 0.0f) {
            return false;
        }
        return true;
    }

    private static boolean hasBottomPadding(IRenderer renderer2) {
        UnitValue padding = (UnitValue) renderer2.getModelElement().getProperty(47);
        if (padding != null && !padding.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) MarginsCollapseHandler.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 47));
        }
        if (padding == null || padding.getValue() <= 0.0f) {
            return false;
        }
        return true;
    }

    private static boolean hasTopBorders(IRenderer renderer2) {
        IPropertyContainer modelElement = renderer2.getModelElement();
        return modelElement.hasProperty(13) || modelElement.hasProperty(9);
    }

    private static boolean hasBottomBorders(IRenderer renderer2) {
        IPropertyContainer modelElement = renderer2.getModelElement();
        return modelElement.hasProperty(10) || modelElement.hasProperty(9);
    }

    private static boolean rendererIsFloated(IRenderer renderer2) {
        FloatPropertyValue floatPropertyValue;
        if (renderer2 == null || (floatPropertyValue = (FloatPropertyValue) renderer2.getProperty(99)) == null || floatPropertyValue.equals(FloatPropertyValue.NONE)) {
            return false;
        }
        return true;
    }

    private static float getModelTopMargin(IRenderer renderer2) {
        UnitValue marginUV = (UnitValue) renderer2.getModelElement().getProperty(46);
        if (marginUV != null && !marginUV.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) MarginsCollapseHandler.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 46));
        }
        if (marginUV == null || (renderer2 instanceof CellRenderer)) {
            return 0.0f;
        }
        return marginUV.getValue();
    }

    private static void ignoreModelTopMargin(IRenderer renderer2) {
        renderer2.setProperty(46, UnitValue.createPointValue(0.0f));
    }

    private static void overrideModelTopMargin(IRenderer renderer2, float collapsedMargins) {
        renderer2.setProperty(46, UnitValue.createPointValue(collapsedMargins));
    }

    private static float getModelBottomMargin(IRenderer renderer2) {
        UnitValue marginUV = (UnitValue) renderer2.getModelElement().getProperty(43);
        if (marginUV != null && !marginUV.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) MarginsCollapseHandler.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 46));
        }
        if (marginUV == null || (renderer2 instanceof CellRenderer)) {
            return 0.0f;
        }
        return marginUV.getValue();
    }

    private static void ignoreModelBottomMargin(IRenderer renderer2) {
        renderer2.setProperty(43, UnitValue.createPointValue(0.0f));
    }

    private static void overrideModelBottomMargin(IRenderer renderer2, float collapsedMargins) {
        renderer2.setProperty(43, UnitValue.createPointValue(collapsedMargins));
    }
}

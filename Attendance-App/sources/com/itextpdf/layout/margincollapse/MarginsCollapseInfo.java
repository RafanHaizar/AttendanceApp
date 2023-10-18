package com.itextpdf.layout.margincollapse;

import java.io.Serializable;

public class MarginsCollapseInfo implements Serializable {
    private float bufferSpaceOnBottom;
    private float bufferSpaceOnTop;
    private boolean clearanceApplied;
    private MarginsCollapse collapseAfter;
    private MarginsCollapse collapseBefore;
    private boolean ignoreOwnMarginBottom;
    private boolean ignoreOwnMarginTop;
    private boolean isSelfCollapsing;
    private MarginsCollapse ownCollapseAfter;
    private float usedBufferSpaceOnBottom;
    private float usedBufferSpaceOnTop;

    MarginsCollapseInfo() {
        this.ignoreOwnMarginTop = false;
        this.ignoreOwnMarginBottom = false;
        this.collapseBefore = new MarginsCollapse();
        this.collapseAfter = new MarginsCollapse();
        this.isSelfCollapsing = true;
        this.bufferSpaceOnTop = 0.0f;
        this.bufferSpaceOnBottom = 0.0f;
        this.usedBufferSpaceOnTop = 0.0f;
        this.usedBufferSpaceOnBottom = 0.0f;
        this.clearanceApplied = false;
    }

    MarginsCollapseInfo(boolean ignoreOwnMarginTop2, boolean ignoreOwnMarginBottom2, MarginsCollapse collapseBefore2, MarginsCollapse collapseAfter2) {
        this.ignoreOwnMarginTop = ignoreOwnMarginTop2;
        this.ignoreOwnMarginBottom = ignoreOwnMarginBottom2;
        this.collapseBefore = collapseBefore2;
        this.collapseAfter = collapseAfter2;
        this.isSelfCollapsing = true;
        this.bufferSpaceOnTop = 0.0f;
        this.bufferSpaceOnBottom = 0.0f;
        this.usedBufferSpaceOnTop = 0.0f;
        this.usedBufferSpaceOnBottom = 0.0f;
        this.clearanceApplied = false;
    }

    public void copyTo(MarginsCollapseInfo destInfo) {
        destInfo.ignoreOwnMarginTop = this.ignoreOwnMarginTop;
        destInfo.ignoreOwnMarginBottom = this.ignoreOwnMarginBottom;
        destInfo.collapseBefore = this.collapseBefore;
        destInfo.collapseAfter = this.collapseAfter;
        destInfo.setOwnCollapseAfter(this.ownCollapseAfter);
        destInfo.setSelfCollapsing(this.isSelfCollapsing);
        destInfo.setBufferSpaceOnTop(this.bufferSpaceOnTop);
        destInfo.setBufferSpaceOnBottom(this.bufferSpaceOnBottom);
        destInfo.setUsedBufferSpaceOnTop(this.usedBufferSpaceOnTop);
        destInfo.setUsedBufferSpaceOnBottom(this.usedBufferSpaceOnBottom);
        destInfo.setClearanceApplied(this.clearanceApplied);
    }

    public static MarginsCollapseInfo createDeepCopy(MarginsCollapseInfo instance) {
        MarginsCollapseInfo copy = new MarginsCollapseInfo();
        instance.copyTo(copy);
        copy.collapseBefore = instance.collapseBefore.clone();
        copy.collapseAfter = instance.collapseAfter.clone();
        MarginsCollapse marginsCollapse = instance.ownCollapseAfter;
        if (marginsCollapse != null) {
            copy.setOwnCollapseAfter(marginsCollapse.clone());
        }
        return copy;
    }

    public static void updateFromCopy(MarginsCollapseInfo originalInstance, MarginsCollapseInfo processedCopy) {
        originalInstance.ignoreOwnMarginTop = processedCopy.ignoreOwnMarginTop;
        originalInstance.ignoreOwnMarginBottom = processedCopy.ignoreOwnMarginBottom;
        originalInstance.collapseBefore.joinMargin(processedCopy.collapseBefore);
        originalInstance.collapseAfter.joinMargin(processedCopy.collapseAfter);
        if (processedCopy.getOwnCollapseAfter() != null) {
            if (originalInstance.getOwnCollapseAfter() == null) {
                originalInstance.setOwnCollapseAfter(new MarginsCollapse());
            }
            originalInstance.getOwnCollapseAfter().joinMargin(processedCopy.getOwnCollapseAfter());
        }
        originalInstance.setSelfCollapsing(processedCopy.isSelfCollapsing);
        originalInstance.setBufferSpaceOnTop(processedCopy.bufferSpaceOnTop);
        originalInstance.setBufferSpaceOnBottom(processedCopy.bufferSpaceOnBottom);
        originalInstance.setUsedBufferSpaceOnTop(processedCopy.usedBufferSpaceOnTop);
        originalInstance.setUsedBufferSpaceOnBottom(processedCopy.usedBufferSpaceOnBottom);
        originalInstance.setClearanceApplied(processedCopy.clearanceApplied);
    }

    /* access modifiers changed from: package-private */
    public MarginsCollapse getCollapseBefore() {
        return this.collapseBefore;
    }

    /* access modifiers changed from: package-private */
    public MarginsCollapse getCollapseAfter() {
        return this.collapseAfter;
    }

    /* access modifiers changed from: package-private */
    public void setCollapseAfter(MarginsCollapse collapseAfter2) {
        this.collapseAfter = collapseAfter2;
    }

    /* access modifiers changed from: package-private */
    public MarginsCollapse getOwnCollapseAfter() {
        return this.ownCollapseAfter;
    }

    /* access modifiers changed from: package-private */
    public void setOwnCollapseAfter(MarginsCollapse marginsCollapse) {
        this.ownCollapseAfter = marginsCollapse;
    }

    /* access modifiers changed from: package-private */
    public void setSelfCollapsing(boolean selfCollapsing) {
        this.isSelfCollapsing = selfCollapsing;
    }

    /* access modifiers changed from: package-private */
    public boolean isSelfCollapsing() {
        return this.isSelfCollapsing;
    }

    /* access modifiers changed from: package-private */
    public boolean isIgnoreOwnMarginTop() {
        return this.ignoreOwnMarginTop;
    }

    /* access modifiers changed from: package-private */
    public boolean isIgnoreOwnMarginBottom() {
        return this.ignoreOwnMarginBottom;
    }

    /* access modifiers changed from: package-private */
    public float getBufferSpaceOnTop() {
        return this.bufferSpaceOnTop;
    }

    /* access modifiers changed from: package-private */
    public void setBufferSpaceOnTop(float bufferSpaceOnTop2) {
        this.bufferSpaceOnTop = bufferSpaceOnTop2;
    }

    /* access modifiers changed from: package-private */
    public float getBufferSpaceOnBottom() {
        return this.bufferSpaceOnBottom;
    }

    /* access modifiers changed from: package-private */
    public void setBufferSpaceOnBottom(float bufferSpaceOnBottom2) {
        this.bufferSpaceOnBottom = bufferSpaceOnBottom2;
    }

    /* access modifiers changed from: package-private */
    public float getUsedBufferSpaceOnTop() {
        return this.usedBufferSpaceOnTop;
    }

    /* access modifiers changed from: package-private */
    public void setUsedBufferSpaceOnTop(float usedBufferSpaceOnTop2) {
        this.usedBufferSpaceOnTop = usedBufferSpaceOnTop2;
    }

    /* access modifiers changed from: package-private */
    public float getUsedBufferSpaceOnBottom() {
        return this.usedBufferSpaceOnBottom;
    }

    /* access modifiers changed from: package-private */
    public void setUsedBufferSpaceOnBottom(float usedBufferSpaceOnBottom2) {
        this.usedBufferSpaceOnBottom = usedBufferSpaceOnBottom2;
    }

    /* access modifiers changed from: package-private */
    public boolean isClearanceApplied() {
        return this.clearanceApplied;
    }

    /* access modifiers changed from: package-private */
    public void setClearanceApplied(boolean clearanceApplied2) {
        this.clearanceApplied = clearanceApplied2;
    }
}

package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ChainHead;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

public class Direct {
    private static final boolean APPLY_MATCH_PARENT = false;
    private static final boolean DEBUG = false;
    private static final boolean EARLY_TERMINATION = true;
    private static int hcount = 0;
    private static BasicMeasure.Measure measure = new BasicMeasure.Measure();
    private static int vcount = 0;

    public static void solvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        ConstraintWidget.DimensionBehaviour horizontal = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour vertical = layout.getVerticalDimensionBehaviour();
        hcount = 0;
        vcount = 0;
        layout.resetFinalResolution();
        ArrayList<ConstraintWidget> children = layout.getChildren();
        int count = children.size();
        for (int i = 0; i < count; i++) {
            children.get(i).resetFinalResolution();
        }
        boolean isRtl = layout.isRtl();
        if (horizontal == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalHorizontal(0, layout.getWidth());
        } else {
            constraintWidgetContainer.setFinalLeft(0);
        }
        boolean hasGuideline = false;
        boolean hasBarrier = false;
        for (int i2 = 0; i2 < count; i2++) {
            ConstraintWidget child = children.get(i2);
            if (child instanceof Guideline) {
                Guideline guideline = (Guideline) child;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && layout.isResolvedHorizontally()) {
                        guideline.setFinalValue(layout.getWidth() - guideline.getRelativeEnd());
                    } else if (layout.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * ((float) layout.getWidth())) + 0.5f));
                    }
                    hasGuideline = EARLY_TERMINATION;
                }
            } else if ((child instanceof Barrier) && ((Barrier) child).getOrientation() == 0) {
                hasBarrier = EARLY_TERMINATION;
            }
        }
        if (hasGuideline) {
            for (int i3 = 0; i3 < count; i3++) {
                ConstraintWidget child2 = children.get(i3);
                if (child2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) child2;
                    if (guideline2.getOrientation() == 1) {
                        horizontalSolvingPass(0, guideline2, measurer2, isRtl);
                    }
                }
            }
        }
        horizontalSolvingPass(0, constraintWidgetContainer, measurer2, isRtl);
        if (hasBarrier) {
            for (int i4 = 0; i4 < count; i4++) {
                ConstraintWidget child3 = children.get(i4);
                if (child3 instanceof Barrier) {
                    Barrier barrier = (Barrier) child3;
                    if (barrier.getOrientation() == 0) {
                        solveBarrier(0, barrier, measurer2, 0, isRtl);
                    }
                }
            }
        }
        if (vertical == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalVertical(0, layout.getHeight());
        } else {
            constraintWidgetContainer.setFinalTop(0);
        }
        boolean hasGuideline2 = false;
        boolean hasBarrier2 = false;
        for (int i5 = 0; i5 < count; i5++) {
            ConstraintWidget child4 = children.get(i5);
            if (child4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) child4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && layout.isResolvedVertically()) {
                        guideline3.setFinalValue(layout.getHeight() - guideline3.getRelativeEnd());
                    } else if (layout.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * ((float) layout.getHeight())) + 0.5f));
                    }
                    hasGuideline2 = EARLY_TERMINATION;
                }
            } else if ((child4 instanceof Barrier) && ((Barrier) child4).getOrientation() == 1) {
                hasBarrier2 = EARLY_TERMINATION;
            }
        }
        if (hasGuideline2) {
            for (int i6 = 0; i6 < count; i6++) {
                ConstraintWidget child5 = children.get(i6);
                if (child5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) child5;
                    if (guideline4.getOrientation() == 0) {
                        verticalSolvingPass(1, guideline4, measurer2);
                    }
                }
            }
        }
        verticalSolvingPass(0, constraintWidgetContainer, measurer2);
        if (hasBarrier2) {
            for (int i7 = 0; i7 < count; i7++) {
                ConstraintWidget child6 = children.get(i7);
                if (child6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) child6;
                    if (barrier2.getOrientation() == 1) {
                        solveBarrier(0, barrier2, measurer2, 1, isRtl);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < count; i8++) {
            ConstraintWidget child7 = children.get(i8);
            if (child7.isMeasureRequested()) {
                if (canMeasure(0, child7)) {
                    ConstraintWidgetContainer.measure(0, child7, measurer2, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
                    if (!(child7 instanceof Guideline)) {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                        verticalSolvingPass(0, child7, measurer2);
                    } else if (((Guideline) child7).getOrientation() == 0) {
                        verticalSolvingPass(0, child7, measurer2);
                    } else {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                    }
                }
            }
        }
    }

    private static void solveBarrier(int level, Barrier barrier, BasicMeasure.Measurer measurer, int orientation, boolean isRtl) {
        if (!barrier.allSolved()) {
            return;
        }
        if (orientation == 0) {
            horizontalSolvingPass(level + 1, barrier, measurer, isRtl);
        } else {
            verticalSolvingPass(level + 1, barrier, measurer);
        }
    }

    /* renamed from: ls */
    public static String m152ls(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }
        builder.append("+-(" + level + ") ");
        return builder.toString();
    }

    private static void horizontalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, boolean isRtl) {
        ConstraintWidget constraintWidget = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        boolean z = isRtl;
        if (!layout.isHorizontalSolvingPassDone()) {
            hcount++;
            if (!(constraintWidget instanceof ConstraintWidgetContainer) && layout.isMeasureRequested() && canMeasure(level + 1, constraintWidget)) {
                ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            ConstraintAnchor left = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
            int l = left.getFinalValue();
            int r = right.getFinalValue();
            if (left.getDependents() != null && left.hasFinalValue()) {
                Iterator<ConstraintAnchor> it = left.getDependents().iterator();
                while (it.hasNext()) {
                    ConstraintAnchor first = it.next();
                    ConstraintWidget widget = first.mOwner;
                    boolean canMeasure = canMeasure(level + 1, widget);
                    if (widget.isMeasureRequested() && canMeasure) {
                        ConstraintWidgetContainer.measure(level + 1, widget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    boolean bothConnected = ((first != widget.mLeft || widget.mRight.mTarget == null || !widget.mRight.mTarget.hasFinalValue()) && (first != widget.mRight || widget.mLeft.mTarget == null || !widget.mLeft.mTarget.hasFinalValue())) ? false : EARLY_TERMINATION;
                    if (widget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                        if (!widget.isMeasureRequested()) {
                            if (first == widget.mLeft && widget.mRight.mTarget == null) {
                                int x1 = widget.mLeft.getMargin() + l;
                                widget.setFinalHorizontal(x1, widget.getWidth() + x1);
                                horizontalSolvingPass(level + 1, widget, measurer2, z);
                            } else if (first == widget.mRight && widget.mLeft.mTarget == null) {
                                int x2 = l - widget.mRight.getMargin();
                                widget.setFinalHorizontal(x2 - widget.getWidth(), x2);
                                horizontalSolvingPass(level + 1, widget, measurer2, z);
                            } else if (bothConnected && !widget.isInHorizontalChain()) {
                                solveHorizontalCenterConstraints(level + 1, measurer2, widget, z);
                            }
                        }
                    } else if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintMaxWidth >= 0 && widget.mMatchConstraintMinWidth >= 0 && ((widget.getVisibility() == 8 || (widget.mMatchConstraintDefaultWidth == 0 && widget.getDimensionRatio() == 0.0f)) && !widget.isInHorizontalChain() && !widget.isInVirtualLayout() && bothConnected && !widget.isInHorizontalChain())) {
                        solveHorizontalMatchConstraint(level + 1, constraintWidget, measurer2, widget, z);
                    }
                }
            }
            if (!(constraintWidget instanceof Guideline)) {
                if (right.getDependents() != null && right.hasFinalValue()) {
                    Iterator<ConstraintAnchor> it2 = right.getDependents().iterator();
                    while (it2.hasNext()) {
                        ConstraintAnchor first2 = it2.next();
                        ConstraintWidget widget2 = first2.mOwner;
                        boolean canMeasure2 = canMeasure(level + 1, widget2);
                        if (widget2.isMeasureRequested() && canMeasure2) {
                            ConstraintWidgetContainer.measure(level + 1, widget2, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        boolean bothConnected2 = ((first2 != widget2.mLeft || widget2.mRight.mTarget == null || !widget2.mRight.mTarget.hasFinalValue()) && (first2 != widget2.mRight || widget2.mLeft.mTarget == null || !widget2.mLeft.mTarget.hasFinalValue())) ? false : EARLY_TERMINATION;
                        if (widget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            if (!canMeasure2) {
                                if (widget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.mMatchConstraintMaxWidth >= 0 && widget2.mMatchConstraintMinWidth >= 0) {
                                    if (widget2.getVisibility() != 8) {
                                        if (widget2.mMatchConstraintDefaultWidth == 0) {
                                            if (widget2.getDimensionRatio() != 0.0f) {
                                            }
                                        }
                                    }
                                    if (!widget2.isInHorizontalChain() && !widget2.isInVirtualLayout() && bothConnected2 && !widget2.isInHorizontalChain()) {
                                        solveHorizontalMatchConstraint(level + 1, constraintWidget, measurer2, widget2, z);
                                    }
                                }
                            }
                        }
                        if (!widget2.isMeasureRequested()) {
                            if (first2 == widget2.mLeft && widget2.mRight.mTarget == null) {
                                int x12 = widget2.mLeft.getMargin() + r;
                                widget2.setFinalHorizontal(x12, widget2.getWidth() + x12);
                                horizontalSolvingPass(level + 1, widget2, measurer2, z);
                            } else if (first2 == widget2.mRight && widget2.mLeft.mTarget == null) {
                                int x22 = r - widget2.mRight.getMargin();
                                widget2.setFinalHorizontal(x22 - widget2.getWidth(), x22);
                                horizontalSolvingPass(level + 1, widget2, measurer2, z);
                            } else if (bothConnected2 && !widget2.isInHorizontalChain()) {
                                solveHorizontalCenterConstraints(level + 1, measurer2, widget2, z);
                            }
                        }
                    }
                }
                layout.markHorizontalSolvingPassDone();
            }
        }
    }

    private static void verticalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer) {
        ConstraintWidget constraintWidget = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        if (!layout.isVerticalSolvingPassDone()) {
            vcount++;
            if (!(constraintWidget instanceof ConstraintWidgetContainer) && layout.isMeasureRequested() && canMeasure(level + 1, constraintWidget)) {
                ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            ConstraintAnchor top = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
            int t = top.getFinalValue();
            int b = bottom.getFinalValue();
            if (top.getDependents() != null && top.hasFinalValue()) {
                Iterator<ConstraintAnchor> it = top.getDependents().iterator();
                while (it.hasNext()) {
                    ConstraintAnchor first = it.next();
                    ConstraintWidget widget = first.mOwner;
                    boolean canMeasure = canMeasure(level + 1, widget);
                    if (widget.isMeasureRequested() && canMeasure) {
                        ConstraintWidgetContainer.measure(level + 1, widget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    boolean bothConnected = ((first != widget.mTop || widget.mBottom.mTarget == null || !widget.mBottom.mTarget.hasFinalValue()) && (first != widget.mBottom || widget.mTop.mTarget == null || !widget.mTop.mTarget.hasFinalValue())) ? false : EARLY_TERMINATION;
                    if (widget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                        if (!widget.isMeasureRequested()) {
                            if (first == widget.mTop && widget.mBottom.mTarget == null) {
                                int y1 = widget.mTop.getMargin() + t;
                                widget.setFinalVertical(y1, widget.getHeight() + y1);
                                verticalSolvingPass(level + 1, widget, measurer2);
                            } else if (first == widget.mBottom && widget.mTop.mTarget == null) {
                                int y2 = t - widget.mBottom.getMargin();
                                widget.setFinalVertical(y2 - widget.getHeight(), y2);
                                verticalSolvingPass(level + 1, widget, measurer2);
                            } else if (bothConnected && !widget.isInVerticalChain()) {
                                solveVerticalCenterConstraints(level + 1, measurer2, widget);
                            }
                        }
                    } else if (widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintMaxHeight >= 0 && widget.mMatchConstraintMinHeight >= 0 && ((widget.getVisibility() == 8 || (widget.mMatchConstraintDefaultHeight == 0 && widget.getDimensionRatio() == 0.0f)) && !widget.isInVerticalChain() && !widget.isInVirtualLayout() && bothConnected && !widget.isInVerticalChain())) {
                        solveVerticalMatchConstraint(level + 1, constraintWidget, measurer2, widget);
                    }
                }
            }
            if (!(constraintWidget instanceof Guideline)) {
                if (bottom.getDependents() != null && bottom.hasFinalValue()) {
                    Iterator<ConstraintAnchor> it2 = bottom.getDependents().iterator();
                    while (it2.hasNext()) {
                        ConstraintAnchor first2 = it2.next();
                        ConstraintWidget widget2 = first2.mOwner;
                        boolean canMeasure2 = canMeasure(level + 1, widget2);
                        if (widget2.isMeasureRequested() && canMeasure2) {
                            ConstraintWidgetContainer.measure(level + 1, widget2, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        boolean bothConnected2 = ((first2 != widget2.mTop || widget2.mBottom.mTarget == null || !widget2.mBottom.mTarget.hasFinalValue()) && (first2 != widget2.mBottom || widget2.mTop.mTarget == null || !widget2.mTop.mTarget.hasFinalValue())) ? false : EARLY_TERMINATION;
                        if (widget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            if (!canMeasure2) {
                                if (widget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.mMatchConstraintMaxHeight >= 0 && widget2.mMatchConstraintMinHeight >= 0) {
                                    if (widget2.getVisibility() != 8) {
                                        if (widget2.mMatchConstraintDefaultHeight == 0) {
                                            if (widget2.getDimensionRatio() != 0.0f) {
                                            }
                                        }
                                    }
                                    if (!widget2.isInVerticalChain() && !widget2.isInVirtualLayout() && bothConnected2 && !widget2.isInVerticalChain()) {
                                        solveVerticalMatchConstraint(level + 1, constraintWidget, measurer2, widget2);
                                    }
                                }
                            }
                        }
                        if (!widget2.isMeasureRequested()) {
                            if (first2 == widget2.mTop && widget2.mBottom.mTarget == null) {
                                int y12 = widget2.mTop.getMargin() + b;
                                widget2.setFinalVertical(y12, widget2.getHeight() + y12);
                                verticalSolvingPass(level + 1, widget2, measurer2);
                            } else if (first2 == widget2.mBottom && widget2.mTop.mTarget == null) {
                                int y22 = b - widget2.mBottom.getMargin();
                                widget2.setFinalVertical(y22 - widget2.getHeight(), y22);
                                verticalSolvingPass(level + 1, widget2, measurer2);
                            } else if (bothConnected2 && !widget2.isInVerticalChain()) {
                                solveVerticalCenterConstraints(level + 1, measurer2, widget2);
                            }
                        }
                    }
                }
                ConstraintAnchor baseline = constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
                if (baseline.getDependents() != null && baseline.hasFinalValue()) {
                    int baselineValue = baseline.getFinalValue();
                    Iterator<ConstraintAnchor> it3 = baseline.getDependents().iterator();
                    while (it3.hasNext()) {
                        ConstraintAnchor first3 = it3.next();
                        ConstraintWidget widget3 = first3.mOwner;
                        boolean canMeasure3 = canMeasure(level + 1, widget3);
                        if (widget3.isMeasureRequested() && canMeasure3) {
                            ConstraintWidgetContainer.measure(level + 1, widget3, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        if ((widget3.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure3) && !widget3.isMeasureRequested() && first3 == widget3.mBaseline) {
                            widget3.setFinalBaseline(first3.getMargin() + baselineValue);
                            try {
                                verticalSolvingPass(level + 1, widget3, measurer2);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
                layout.markVerticalSolvingPassDone();
            }
        }
    }

    private static void solveHorizontalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int d1;
        float bias = widget.getHorizontalBiasPercent();
        int start = widget.mLeft.mTarget.getFinalValue();
        int end = widget.mRight.mTarget.getFinalValue();
        int s1 = widget.mLeft.getMargin() + start;
        int s2 = end - widget.mRight.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int width = widget.getWidth();
        int distance = (s2 - s1) - width;
        if (s1 > s2) {
            distance = (s1 - s2) - width;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int x1 = s1 + d1;
        int x2 = x1 + width;
        if (s1 > s2) {
            x1 = s1 + d1;
            x2 = x1 - width;
        }
        widget.setFinalHorizontal(x1, x2);
        horizontalSolvingPass(level + 1, widget, measurer, isRtl);
    }

    private static void solveVerticalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int d1;
        float bias = widget.getVerticalBiasPercent();
        int start = widget.mTop.mTarget.getFinalValue();
        int end = widget.mBottom.mTarget.getFinalValue();
        int s1 = widget.mTop.getMargin() + start;
        int s2 = end - widget.mBottom.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int height = widget.getHeight();
        int distance = (s2 - s1) - height;
        if (s1 > s2) {
            distance = (s1 - s2) - height;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int y1 = s1 + d1;
        int y2 = y1 + height;
        if (s1 > s2) {
            y1 = s1 - d1;
            y2 = y1 - height;
        }
        widget.setFinalVertical(y1, y2);
        verticalSolvingPass(level + 1, widget, measurer);
    }

    private static void solveHorizontalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int parentWidth;
        float bias = widget.getHorizontalBiasPercent();
        int s1 = widget.mLeft.mTarget.getFinalValue() + widget.mLeft.getMargin();
        int s2 = widget.mRight.mTarget.getFinalValue() - widget.mRight.getMargin();
        if (s2 >= s1) {
            int width = widget.getWidth();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultWidth == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentWidth = layout.getWidth();
                    } else {
                        parentWidth = layout.getParent().getWidth();
                    }
                    width = (int) (widget.getHorizontalBiasPercent() * 0.5f * ((float) parentWidth));
                } else if (widget.mMatchConstraintDefaultWidth == 0) {
                    width = s2 - s1;
                }
                width = Math.max(widget.mMatchConstraintMinWidth, width);
                if (widget.mMatchConstraintMaxWidth > 0) {
                    width = Math.min(widget.mMatchConstraintMaxWidth, width);
                }
            }
            int x1 = s1 + ((int) ((((float) ((s2 - s1) - width)) * bias) + 0.5f));
            widget.setFinalHorizontal(x1, x1 + width);
            horizontalSolvingPass(level + 1, widget, measurer, isRtl);
        }
    }

    private static void solveVerticalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int parentHeight;
        float bias = widget.getVerticalBiasPercent();
        int s1 = widget.mTop.mTarget.getFinalValue() + widget.mTop.getMargin();
        int s2 = widget.mBottom.mTarget.getFinalValue() - widget.mBottom.getMargin();
        if (s2 >= s1) {
            int height = widget.getHeight();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultHeight == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentHeight = layout.getHeight();
                    } else {
                        parentHeight = layout.getParent().getHeight();
                    }
                    height = (int) (bias * 0.5f * ((float) parentHeight));
                } else if (widget.mMatchConstraintDefaultHeight == 0) {
                    height = s2 - s1;
                }
                height = Math.max(widget.mMatchConstraintMinHeight, height);
                if (widget.mMatchConstraintMaxHeight > 0) {
                    height = Math.min(widget.mMatchConstraintMaxHeight, height);
                }
            }
            int y1 = s1 + ((int) ((((float) ((s2 - s1) - height)) * bias) + 0.5f));
            widget.setFinalVertical(y1, y1 + height);
            verticalSolvingPass(level + 1, widget, measurer);
        }
    }

    private static boolean canMeasure(int level, ConstraintWidget layout) {
        ConstraintWidget.DimensionBehaviour horizontalBehaviour = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalBehaviour = layout.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer parent = layout.getParent() != null ? (ConstraintWidgetContainer) layout.getParent() : null;
        if (parent == null || parent.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        if (parent == null || parent.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        boolean isHorizontalFixed = (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedHorizontally() || horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(0)) || (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 1 && layout.hasResolvedTargets(0, layout.getWidth()))) ? EARLY_TERMINATION : false;
        boolean isVerticalFixed = (verticalBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedVertically() || verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(1)) || (verticalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 1 && layout.hasResolvedTargets(1, layout.getHeight()))) ? EARLY_TERMINATION : false;
        if (layout.mDimensionRatio > 0.0f && (isHorizontalFixed || isVerticalFixed)) {
            return EARLY_TERMINATION;
        }
        if (!isHorizontalFixed || !isVerticalFixed) {
            return false;
        }
        return EARLY_TERMINATION;
    }

    public static boolean solveChain(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead, boolean isChainSpread, boolean isChainSpreadInside, boolean isChainPacked) {
        ConstraintWidget widget;
        int i;
        ConstraintWidget next;
        boolean done;
        int current;
        float bias;
        ConstraintAnchor begin;
        boolean done2;
        BasicMeasure.Measure measure2;
        int totalSize;
        ConstraintWidget next2;
        if (isChainPacked) {
            return false;
        }
        if (orientation == 0) {
            if (!container.isResolvedHorizontally()) {
                return false;
            }
        } else if (!container.isResolvedVertically()) {
            return false;
        }
        boolean isRtl = container.isRtl();
        ConstraintWidget first = chainHead.getFirst();
        ConstraintWidget next3 = chainHead.getLast();
        ConstraintWidget firstVisibleWidget = chainHead.getFirstVisibleWidget();
        ConstraintWidget lastVisibleWidget = chainHead.getLastVisibleWidget();
        ConstraintWidget head = chainHead.getHead();
        ConstraintWidget widget2 = first;
        boolean done3 = false;
        ConstraintAnchor begin2 = first.mListAnchors[offset];
        ConstraintAnchor end = next3.mListAnchors[offset + 1];
        if (begin2.mTarget == null) {
            ConstraintWidget constraintWidget = next3;
            ConstraintWidget constraintWidget2 = head;
            ConstraintAnchor constraintAnchor = begin2;
        } else if (end.mTarget == null) {
            ConstraintWidget constraintWidget3 = first;
            ConstraintWidget constraintWidget4 = next3;
            ConstraintWidget constraintWidget5 = head;
            ConstraintAnchor constraintAnchor2 = begin2;
        } else {
            if (!begin2.mTarget.hasFinalValue()) {
                ConstraintWidget constraintWidget6 = next3;
                ConstraintWidget constraintWidget7 = head;
                ConstraintAnchor constraintAnchor3 = begin2;
            } else if (!end.mTarget.hasFinalValue()) {
                ConstraintWidget constraintWidget8 = first;
                ConstraintWidget constraintWidget9 = next3;
                ConstraintWidget constraintWidget10 = head;
                ConstraintAnchor constraintAnchor4 = begin2;
            } else {
                if (firstVisibleWidget == null) {
                    ConstraintWidget constraintWidget11 = next3;
                    ConstraintWidget constraintWidget12 = head;
                    ConstraintAnchor constraintAnchor5 = begin2;
                } else if (lastVisibleWidget == null) {
                    ConstraintWidget constraintWidget13 = first;
                    ConstraintWidget constraintWidget14 = next3;
                    ConstraintWidget constraintWidget15 = head;
                    ConstraintAnchor constraintAnchor6 = begin2;
                } else {
                    int startPoint = begin2.mTarget.getFinalValue() + firstVisibleWidget.mListAnchors[offset].getMargin();
                    int endPoint = end.mTarget.getFinalValue() - lastVisibleWidget.mListAnchors[offset + 1].getMargin();
                    int distance = endPoint - startPoint;
                    if (distance <= 0) {
                        return false;
                    }
                    int totalSize2 = 0;
                    BasicMeasure.Measure measure3 = new BasicMeasure.Measure();
                    int numWidgets = 0;
                    int numVisibleWidgets = 0;
                    while (!done3) {
                        boolean canMeasure = canMeasure(0 + 1, widget2);
                        if (!canMeasure) {
                            return false;
                        }
                        boolean z = canMeasure;
                        ConstraintWidget last = next3;
                        if (widget2.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            return false;
                        }
                        if (widget2.isMeasureRequested()) {
                            done2 = done3;
                            begin = begin2;
                            measure2 = measure3;
                            ConstraintWidgetContainer.measure(0 + 1, widget2, container.getMeasurer(), measure2, BasicMeasure.Measure.SELF_DIMENSIONS);
                        } else {
                            done2 = done3;
                            begin = begin2;
                            measure2 = measure3;
                        }
                        int totalSize3 = totalSize2 + widget2.mListAnchors[offset].getMargin();
                        if (orientation == 0) {
                            totalSize = totalSize3 + widget2.getWidth();
                        } else {
                            totalSize = totalSize3 + widget2.getHeight();
                        }
                        totalSize2 = totalSize + widget2.mListAnchors[offset + 1].getMargin();
                        numWidgets++;
                        if (widget2.getVisibility() != 8) {
                            numVisibleWidgets++;
                        } else {
                            int i2 = numVisibleWidgets;
                        }
                        ConstraintAnchor nextAnchor = widget2.mListAnchors[offset + 1].mTarget;
                        if (nextAnchor != null) {
                            next2 = nextAnchor.mOwner;
                            if (next2.mListAnchors[offset].mTarget == null || next2.mListAnchors[offset].mTarget.mOwner != widget2) {
                                next2 = null;
                            }
                        } else {
                            next2 = null;
                        }
                        if (next2 != null) {
                            widget2 = next2;
                            done3 = done2;
                        } else {
                            done3 = EARLY_TERMINATION;
                        }
                        measure3 = measure2;
                        next3 = last;
                        begin2 = begin;
                    }
                    ConstraintWidget last2 = next3;
                    boolean done4 = done3;
                    ConstraintAnchor constraintAnchor7 = begin2;
                    BasicMeasure.Measure measure4 = measure3;
                    int numWidgets2 = numWidgets;
                    int numVisibleWidgets2 = numVisibleWidgets;
                    if (numVisibleWidgets2 == 0 || numVisibleWidgets2 != numWidgets2 || distance < totalSize2) {
                        return false;
                    }
                    int gap = distance - totalSize2;
                    int i3 = numWidgets2;
                    if (isChainSpread) {
                        gap /= numVisibleWidgets2 + 1;
                        widget = widget2;
                        i = 1;
                    } else if (!isChainSpreadInside) {
                        widget = widget2;
                        i = 1;
                    } else if (numVisibleWidgets2 > 2) {
                        widget = widget2;
                        i = 1;
                        gap = (gap / numVisibleWidgets2) - 1;
                    } else {
                        widget = widget2;
                        i = 1;
                    }
                    if (numVisibleWidgets2 == i) {
                        if (orientation == 0) {
                            bias = head.getHorizontalBiasPercent();
                        } else {
                            bias = head.getVerticalBiasPercent();
                        }
                        ConstraintWidget constraintWidget16 = head;
                        int p1 = (int) (((float) startPoint) + 0.5f + (((float) gap) * bias));
                        if (orientation == 0) {
                            firstVisibleWidget.setFinalHorizontal(p1, firstVisibleWidget.getWidth() + p1);
                        } else {
                            firstVisibleWidget.setFinalVertical(p1, firstVisibleWidget.getHeight() + p1);
                        }
                        float f = bias;
                        horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                        return EARLY_TERMINATION;
                    }
                    if (isChainSpread) {
                        boolean done5 = false;
                        int current2 = startPoint + gap;
                        ConstraintWidget widget3 = first;
                        while (!done5) {
                            boolean done6 = done5;
                            ConstraintWidget first2 = first;
                            if (widget3.getVisibility() != 8) {
                                int current3 = current2 + widget3.mListAnchors[offset].getMargin();
                                if (orientation == 0) {
                                    widget3.setFinalHorizontal(current3, widget3.getWidth() + current3);
                                    horizontalSolvingPass(0 + 1, widget3, container.getMeasurer(), isRtl);
                                    current = current3 + widget3.getWidth();
                                } else {
                                    widget3.setFinalVertical(current3, widget3.getHeight() + current3);
                                    verticalSolvingPass(0 + 1, widget3, container.getMeasurer());
                                    current = current3 + widget3.getHeight();
                                }
                                current2 = current + widget3.mListAnchors[offset + 1].getMargin() + gap;
                            } else if (orientation == 0) {
                                widget3.setFinalHorizontal(current2, current2);
                                horizontalSolvingPass(0 + 1, widget3, container.getMeasurer(), isRtl);
                            } else {
                                widget3.setFinalVertical(current2, current2);
                                verticalSolvingPass(0 + 1, widget3, container.getMeasurer());
                            }
                            widget3.addToSolver(system, false);
                            ConstraintAnchor nextAnchor2 = widget3.mListAnchors[offset + 1].mTarget;
                            if (nextAnchor2 != null) {
                                next = nextAnchor2.mOwner;
                                ConstraintAnchor constraintAnchor8 = nextAnchor2;
                                if (next.mListAnchors[offset].mTarget == null || next.mListAnchors[offset].mTarget.mOwner != widget3) {
                                    next = null;
                                }
                            } else {
                                next = null;
                            }
                            if (next != null) {
                                widget3 = next;
                                done = done6;
                            } else {
                                done = EARLY_TERMINATION;
                            }
                            done5 = done;
                            first = first2;
                        }
                        boolean z2 = done5;
                        ConstraintWidget constraintWidget17 = first;
                        return EARLY_TERMINATION;
                    }
                    if (!isChainSpreadInside) {
                        ConstraintWidget constraintWidget18 = widget;
                        boolean z3 = done4;
                        return EARLY_TERMINATION;
                    } else if (numVisibleWidgets2 != 2) {
                        return false;
                    } else {
                        if (orientation == 0) {
                            firstVisibleWidget.setFinalHorizontal(startPoint, firstVisibleWidget.getWidth() + startPoint);
                            lastVisibleWidget.setFinalHorizontal(endPoint - lastVisibleWidget.getWidth(), endPoint);
                            horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                            horizontalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer(), isRtl);
                            return EARLY_TERMINATION;
                        }
                        firstVisibleWidget.setFinalVertical(startPoint, firstVisibleWidget.getHeight() + startPoint);
                        lastVisibleWidget.setFinalVertical(endPoint - lastVisibleWidget.getHeight(), endPoint);
                        verticalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer());
                        verticalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer());
                        return EARLY_TERMINATION;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }
}

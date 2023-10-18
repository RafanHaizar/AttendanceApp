package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

public abstract class WidgetRun implements Dependency {
    DimensionDependency dimension = new DimensionDependency(this);
    protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;
    public int matchConstraintsType;
    public int orientation = 0;
    boolean resolved = false;
    RunGroup runGroup;
    public DependencyNode start = new DependencyNode(this);
    ConstraintWidget widget;

    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    /* access modifiers changed from: package-private */
    public abstract void apply();

    /* access modifiers changed from: package-private */
    public abstract void applyToWidget();

    /* access modifiers changed from: package-private */
    public abstract void clear();

    /* access modifiers changed from: package-private */
    public abstract void reset();

    /* access modifiers changed from: package-private */
    public abstract boolean supportsWrapComputation();

    public WidgetRun(ConstraintWidget widget2) {
        this.widget = widget2;
    }

    public boolean isDimensionResolved() {
        return this.dimension.resolved;
    }

    public boolean isCenterConnection() {
        int connections = 0;
        int count = this.start.targets.size();
        for (int i = 0; i < count; i++) {
            if (this.start.targets.get(i).run != this) {
                connections++;
            }
        }
        int count2 = this.end.targets.size();
        for (int i2 = 0; i2 < count2; i2++) {
            if (this.end.targets.get(i2).run != this) {
                connections++;
            }
        }
        return connections >= 2;
    }

    public long wrapSize(int direction) {
        if (!this.dimension.resolved) {
            return 0;
        }
        long size = (long) this.dimension.value;
        if (isCenterConnection()) {
            return size + ((long) (this.start.margin - this.end.margin));
        }
        if (direction == 0) {
            return size + ((long) this.start.margin);
        }
        return size - ((long) this.end.margin);
    }

    /* access modifiers changed from: protected */
    public final DependencyNode getTarget(ConstraintAnchor anchor) {
        if (anchor.mTarget == null) {
            return null;
        }
        ConstraintWidget targetWidget = anchor.mTarget.mOwner;
        switch (C06321.f1014x6930e354[anchor.mTarget.mType.ordinal()]) {
            case 1:
                return targetWidget.horizontalRun.start;
            case 2:
                return targetWidget.horizontalRun.end;
            case 3:
                return targetWidget.verticalRun.start;
            case 4:
                return targetWidget.verticalRun.baseline;
            case 5:
                return targetWidget.verticalRun.end;
            default:
                return null;
        }
    }

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1 */
    static /* synthetic */ class C06321 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        static final /* synthetic */ int[] f1014x6930e354;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f1014x6930e354 = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1014x6930e354[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1014x6930e354[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1014x6930e354[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1014x6930e354[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateRunCenter(Dependency dependency, ConstraintAnchor startAnchor, ConstraintAnchor endAnchor, int orientation2) {
        float bias;
        DependencyNode startTarget = getTarget(startAnchor);
        DependencyNode endTarget = getTarget(endAnchor);
        if (startTarget.resolved && endTarget.resolved) {
            int startPos = startTarget.value + startAnchor.getMargin();
            int endPos = endTarget.value - endAnchor.getMargin();
            int distance = endPos - startPos;
            if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                resolveDimension(orientation2, distance);
            }
            if (this.dimension.resolved) {
                if (this.dimension.value == distance) {
                    this.start.resolve(startPos);
                    this.end.resolve(endPos);
                    return;
                }
                ConstraintWidget constraintWidget = this.widget;
                if (orientation2 == 0) {
                    bias = constraintWidget.getHorizontalBiasPercent();
                } else {
                    bias = constraintWidget.getVerticalBiasPercent();
                }
                if (startTarget == endTarget) {
                    startPos = startTarget.value;
                    endPos = endTarget.value;
                    bias = 0.5f;
                }
                this.start.resolve((int) (((float) startPos) + 0.5f + (((float) ((endPos - startPos) - this.dimension.value)) * bias)));
                this.end.resolve(this.start.value + this.dimension.value);
            }
        }
    }

    private void resolveDimension(int orientation2, int distance) {
        int value;
        switch (this.matchConstraintsType) {
            case 0:
                this.dimension.resolve(getLimitedDimension(distance, orientation2));
                return;
            case 1:
                this.dimension.resolve(Math.min(getLimitedDimension(this.dimension.wrapValue, orientation2), distance));
                return;
            case 2:
                ConstraintWidget parent = this.widget.getParent();
                if (parent != null) {
                    WidgetRun run = orientation2 == 0 ? parent.horizontalRun : parent.verticalRun;
                    if (run.dimension.resolved) {
                        ConstraintWidget constraintWidget = this.widget;
                        this.dimension.resolve(getLimitedDimension((int) ((((float) run.dimension.value) * (orientation2 == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight)) + 0.5f), orientation2));
                        return;
                    }
                    return;
                }
                return;
            case 3:
                if (this.widget.horizontalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.horizontalRun.matchConstraintsType != 3 || this.widget.verticalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.verticalRun.matchConstraintsType != 3) {
                    ConstraintWidget constraintWidget2 = this.widget;
                    WidgetRun run2 = orientation2 == 0 ? constraintWidget2.verticalRun : constraintWidget2.horizontalRun;
                    if (run2.dimension.resolved) {
                        float ratio = this.widget.getDimensionRatio();
                        if (orientation2 == 1) {
                            value = (int) ((((float) run2.dimension.value) / ratio) + 0.5f);
                        } else {
                            value = (int) ((((float) run2.dimension.value) * ratio) + 0.5f);
                        }
                        this.dimension.resolve(value);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void updateRunStart(Dependency dependency) {
    }

    /* access modifiers changed from: protected */
    public void updateRunEnd(Dependency dependency) {
    }

    public void update(Dependency dependency) {
    }

    /* access modifiers changed from: protected */
    public final int getLimitedDimension(int dimension2, int orientation2) {
        if (orientation2 == 0) {
            int max = this.widget.mMatchConstraintMaxWidth;
            int value = Math.max(this.widget.mMatchConstraintMinWidth, dimension2);
            if (max > 0) {
                value = Math.min(max, dimension2);
            }
            if (value != dimension2) {
                return value;
            }
            return dimension2;
        }
        int max2 = this.widget.mMatchConstraintMaxHeight;
        int value2 = Math.max(this.widget.mMatchConstraintMinHeight, dimension2);
        if (max2 > 0) {
            value2 = Math.min(max2, dimension2);
        }
        if (value2 != dimension2) {
            return value2;
        }
        return dimension2;
    }

    /* access modifiers changed from: protected */
    public final DependencyNode getTarget(ConstraintAnchor anchor, int orientation2) {
        if (anchor.mTarget == null) {
            return null;
        }
        ConstraintWidget targetWidget = anchor.mTarget.mOwner;
        WidgetRun run = orientation2 == 0 ? targetWidget.horizontalRun : targetWidget.verticalRun;
        switch (C06321.f1014x6930e354[anchor.mTarget.mType.ordinal()]) {
            case 1:
            case 3:
                return run.start;
            case 2:
            case 5:
                return run.end;
            default:
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public final void addTarget(DependencyNode node, DependencyNode target, int margin) {
        node.targets.add(target);
        node.margin = margin;
        target.dependencies.add(node);
    }

    /* access modifiers changed from: protected */
    public final void addTarget(DependencyNode node, DependencyNode target, int marginFactor, DimensionDependency dimensionDependency) {
        node.targets.add(target);
        node.targets.add(this.dimension);
        node.marginFactor = marginFactor;
        node.marginDependency = dimensionDependency;
        target.dependencies.add(node);
        dimensionDependency.dependencies.add(node);
    }

    public long getWrapDimension() {
        if (this.dimension.resolved) {
            return (long) this.dimension.value;
        }
        return 0;
    }

    public boolean isResolved() {
        return this.resolved;
    }
}

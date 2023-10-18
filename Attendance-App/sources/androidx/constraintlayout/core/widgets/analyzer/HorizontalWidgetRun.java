package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget widget) {
        super(widget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getWidth());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.widget.getParent()) != null && (parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int resolvedDimension = (parent2.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(resolvedDimension);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.widget.getParent()) != null && (parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
        }
        if (!this.dimension.resolved || !this.widget.measured) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                switch (this.widget.mMatchConstraintDefaultWidth) {
                    case 2:
                        ConstraintWidget parent3 = this.widget.getParent();
                        if (parent3 != null) {
                            DependencyNode targetDimension = parent3.verticalRun.dimension;
                            this.dimension.targets.add(targetDimension);
                            targetDimension.dependencies.add(this.dimension);
                            this.dimension.delegateToWidgetRun = true;
                            this.dimension.dependencies.add(this.start);
                            this.dimension.dependencies.add(this.end);
                            break;
                        }
                        break;
                    case 3:
                        if (this.widget.mMatchConstraintDefaultHeight != 3) {
                            DependencyNode targetDimension2 = this.widget.verticalRun.dimension;
                            this.dimension.targets.add(targetDimension2);
                            targetDimension2.dependencies.add(this.dimension);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                            this.dimension.delegateToWidgetRun = true;
                            this.dimension.dependencies.add(this.start);
                            this.dimension.dependencies.add(this.end);
                            this.start.targets.add(this.dimension);
                            this.end.targets.add(this.dimension);
                            break;
                        } else {
                            this.start.updateDelegate = this;
                            this.end.updateDelegate = this;
                            this.widget.verticalRun.start.updateDelegate = this;
                            this.widget.verticalRun.end.updateDelegate = this;
                            this.dimension.updateDelegate = this;
                            if (!this.widget.isInVerticalChain()) {
                                if (!this.widget.isInHorizontalChain()) {
                                    this.widget.verticalRun.dimension.targets.add(this.dimension);
                                    break;
                                } else {
                                    this.widget.verticalRun.dimension.targets.add(this.dimension);
                                    this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                                    break;
                                }
                            } else {
                                this.dimension.targets.add(this.widget.verticalRun.dimension);
                                this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                                this.widget.verticalRun.dimension.updateDelegate = this;
                                this.dimension.targets.add(this.widget.verticalRun.start);
                                this.dimension.targets.add(this.widget.verticalRun.end);
                                this.widget.verticalRun.start.dependencies.add(this.dimension);
                                this.widget.verticalRun.end.dependencies.add(this.dimension);
                                break;
                            }
                        }
                }
            }
            if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
                if (this.widget.mListAnchors[0].mTarget != null) {
                    DependencyNode target = getTarget(this.widget.mListAnchors[0]);
                    if (target != null) {
                        addTarget(this.start, target, this.widget.mListAnchors[0].getMargin());
                        addTarget(this.end, this.start, 1, this.dimension);
                    }
                } else if (this.widget.mListAnchors[1].mTarget != null) {
                    DependencyNode target2 = getTarget(this.widget.mListAnchors[1]);
                    if (target2 != null) {
                        addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                        addTarget(this.start, this.end, -1, this.dimension);
                    }
                } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
                    addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                    addTarget(this.end, this.start, 1, this.dimension);
                }
            } else if (this.widget.isInHorizontalChain()) {
                this.start.margin = this.widget.mListAnchors[0].getMargin();
                this.end.margin = -this.widget.mListAnchors[1].getMargin();
            } else {
                DependencyNode startTarget = getTarget(this.widget.mListAnchors[0]);
                DependencyNode endTarget = getTarget(this.widget.mListAnchors[1]);
                if (startTarget != null) {
                    startTarget.addDependency(this);
                }
                if (endTarget != null) {
                    endTarget.addDependency(this);
                }
                this.mRunType = WidgetRun.RunType.CENTER;
            }
        } else if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
            if (this.widget.mListAnchors[0].mTarget != null) {
                DependencyNode target3 = getTarget(this.widget.mListAnchors[0]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.widget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.dimension.value);
                }
            } else if (this.widget.mListAnchors[1].mTarget != null) {
                DependencyNode target4 = getTarget(this.widget.mListAnchors[1]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.dimension.value);
                }
            } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                addTarget(this.end, this.start, this.dimension.value);
            }
        } else if (this.widget.isInHorizontalChain()) {
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
            DependencyNode startTarget2 = getTarget(this.widget.mListAnchors[0]);
            if (startTarget2 != null) {
                addTarget(this.start, startTarget2, this.widget.mListAnchors[0].getMargin());
            }
            DependencyNode endTarget2 = getTarget(this.widget.mListAnchors[1]);
            if (endTarget2 != null) {
                addTarget(this.end, endTarget2, -this.widget.mListAnchors[1].getMargin());
            }
            this.start.delegateToWidgetRun = true;
            this.end.delegateToWidgetRun = true;
        }
    }

    private void computeInsetRatio(int[] dimensions, int x1, int x2, int y1, int y2, float ratio, int side) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        switch (side) {
            case -1:
                int candidateX1 = (int) ((((float) dy) * ratio) + 0.5f);
                int candidateY1 = dy;
                int candidateX2 = dx;
                int candidateY2 = (int) ((((float) dx) / ratio) + 0.5f);
                if (candidateX1 <= dx && candidateY1 <= dy) {
                    dimensions[0] = candidateX1;
                    dimensions[1] = candidateY1;
                    return;
                } else if (candidateX2 <= dx && candidateY2 <= dy) {
                    dimensions[0] = candidateX2;
                    dimensions[1] = candidateY2;
                    return;
                } else {
                    return;
                }
            case 0:
                dimensions[0] = (int) ((((float) dy) * ratio) + 0.5f);
                dimensions[1] = dy;
                return;
            case 1:
                dimensions[0] = dx;
                dimensions[1] = (int) ((((float) dx) * ratio) + 0.5f);
                return;
            default:
                return;
        }
    }

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1 */
    static /* synthetic */ class C06301 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType */
        static final /* synthetic */ int[] f1011x56910102;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            f1011x56910102 = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1011x56910102[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1011x56910102[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void update(Dependency dependency) {
        switch (C06301.f1011x56910102[this.mRunType.ordinal()]) {
            case 1:
                Dependency dependency2 = dependency;
                updateRunStart(dependency);
                break;
            case 2:
                Dependency dependency3 = dependency;
                updateRunEnd(dependency);
                break;
            case 3:
                updateRunCenter(dependency, this.widget.mLeft, this.widget.mRight, 0);
                return;
            default:
                Dependency dependency4 = dependency;
                break;
        }
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            switch (this.widget.mMatchConstraintDefaultWidth) {
                case 2:
                    ConstraintWidget parent = this.widget.getParent();
                    if (parent != null && parent.horizontalRun.dimension.resolved) {
                        this.dimension.resolve((int) ((((float) parent.horizontalRun.dimension.value) * this.widget.mMatchConstraintPercentWidth) + 0.5f));
                        break;
                    }
                case 3:
                    if (this.widget.mMatchConstraintDefaultHeight != 0 && this.widget.mMatchConstraintDefaultHeight != 3) {
                        int size = 0;
                        switch (this.widget.getDimensionRatioSide()) {
                            case -1:
                                size = (int) ((((float) this.widget.verticalRun.dimension.value) * this.widget.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                size = (int) ((((float) this.widget.verticalRun.dimension.value) / this.widget.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                size = (int) ((((float) this.widget.verticalRun.dimension.value) * this.widget.getDimensionRatio()) + 0.5f);
                                break;
                        }
                        this.dimension.resolve(size);
                        break;
                    } else {
                        DependencyNode secondStart = this.widget.verticalRun.start;
                        DependencyNode secondEnd = this.widget.verticalRun.end;
                        boolean s1 = this.widget.mLeft.mTarget != null;
                        boolean s2 = this.widget.mTop.mTarget != null;
                        boolean e1 = this.widget.mRight.mTarget != null;
                        boolean e2 = this.widget.mBottom.mTarget != null;
                        int definedSide = this.widget.getDimensionRatioSide();
                        if (!s1 || !s2 || !e1 || !e2) {
                            if (s1 && e1) {
                                if (this.start.readyToSolve && this.end.readyToSolve) {
                                    float ratio = this.widget.getDimensionRatio();
                                    int x1 = this.start.targets.get(0).value + this.start.margin;
                                    int x2 = this.end.targets.get(0).value - this.end.margin;
                                    switch (definedSide) {
                                        case -1:
                                        case 0:
                                            int ldx = getLimitedDimension(x2 - x1, 0);
                                            int dy = (int) ((((float) ldx) * ratio) + 0.5f);
                                            int ldy = getLimitedDimension(dy, 1);
                                            if (dy != ldy) {
                                                ldx = (int) ((((float) ldy) / ratio) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx);
                                            this.widget.verticalRun.dimension.resolve(ldy);
                                            break;
                                        case 1:
                                            int ldx2 = getLimitedDimension(x2 - x1, 0);
                                            int dy2 = (int) ((((float) ldx2) / ratio) + 0.5f);
                                            int ldy2 = getLimitedDimension(dy2, 1);
                                            if (dy2 != ldy2) {
                                                ldx2 = (int) ((((float) ldy2) * ratio) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx2);
                                            this.widget.verticalRun.dimension.resolve(ldy2);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            } else if (s2 && e2) {
                                if (secondStart.readyToSolve && secondEnd.readyToSolve) {
                                    float ratio2 = this.widget.getDimensionRatio();
                                    int y1 = secondStart.targets.get(0).value + secondStart.margin;
                                    int y2 = secondEnd.targets.get(0).value - secondEnd.margin;
                                    switch (definedSide) {
                                        case -1:
                                        case 1:
                                            int ldy3 = getLimitedDimension(y2 - y1, 1);
                                            int dx = (int) ((((float) ldy3) / ratio2) + 0.5f);
                                            int ldx3 = getLimitedDimension(dx, 0);
                                            if (dx != ldx3) {
                                                ldy3 = (int) ((((float) ldx3) * ratio2) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx3);
                                            this.widget.verticalRun.dimension.resolve(ldy3);
                                            break;
                                        case 0:
                                            int ldy4 = getLimitedDimension(y2 - y1, 1);
                                            int dx2 = (int) ((((float) ldy4) * ratio2) + 0.5f);
                                            int ldx4 = getLimitedDimension(dx2, 0);
                                            if (dx2 != ldx4) {
                                                ldy4 = (int) ((((float) ldx4) / ratio2) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx4);
                                            this.widget.verticalRun.dimension.resolve(ldy4);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            float ratio3 = this.widget.getDimensionRatio();
                            if (!secondStart.resolved || !secondEnd.resolved) {
                                if (this.start.resolved && this.end.resolved) {
                                    if (secondStart.readyToSolve && secondEnd.readyToSolve) {
                                        computeInsetRatio(tempDimensions, this.start.value + this.start.margin, this.end.value - this.end.margin, secondStart.targets.get(0).value + secondStart.margin, secondEnd.targets.get(0).value - secondEnd.margin, ratio3, definedSide);
                                        this.dimension.resolve(tempDimensions[0]);
                                        this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    } else {
                                        return;
                                    }
                                }
                                if (this.start.readyToSolve && this.end.readyToSolve && secondStart.readyToSolve && secondEnd.readyToSolve) {
                                    computeInsetRatio(tempDimensions, this.start.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, secondStart.targets.get(0).value + secondStart.margin, secondEnd.targets.get(0).value - secondEnd.margin, ratio3, definedSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    break;
                                } else {
                                    return;
                                }
                            } else if (this.start.readyToSolve && this.end.readyToSolve) {
                                computeInsetRatio(tempDimensions, this.start.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, secondStart.value + secondStart.margin, secondEnd.value - secondEnd.margin, ratio3, definedSide);
                                this.dimension.resolve(tempDimensions[0]);
                                this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    break;
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
                return;
            }
            if (this.dimension.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth != 0 || this.widget.isInHorizontalChain()) {
                if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                    int value = Math.min((this.end.targets.get(0).value + this.end.margin) - (this.start.targets.get(0).value + this.start.margin), this.dimension.wrapValue);
                    int max = this.widget.mMatchConstraintMaxWidth;
                    int value2 = Math.max(this.widget.mMatchConstraintMinWidth, value);
                    if (max > 0) {
                        value2 = Math.min(max, value2);
                    }
                    this.dimension.resolve(value2);
                }
                if (this.dimension.resolved) {
                    DependencyNode startTarget = this.start.targets.get(0);
                    DependencyNode endTarget = this.end.targets.get(0);
                    int startPos = startTarget.value + this.start.margin;
                    int endPos = endTarget.value + this.end.margin;
                    float bias = this.widget.getHorizontalBiasPercent();
                    if (startTarget == endTarget) {
                        startPos = startTarget.value;
                        endPos = endTarget.value;
                        bias = 0.5f;
                    }
                    this.start.resolve((int) (((float) startPos) + 0.5f + (((float) ((endPos - startPos) - this.dimension.value)) * bias)));
                    this.end.resolve(this.start.value + this.dimension.value);
                    return;
                }
                return;
            }
            int startPos2 = this.start.targets.get(0).value + this.start.margin;
            int endPos2 = this.end.targets.get(0).value + this.end.margin;
            this.start.resolve(startPos2);
            this.end.resolve(endPos2);
            this.dimension.resolve(endPos2 - startPos2);
        }
    }

    public void applyToWidget() {
        if (this.start.resolved) {
            this.widget.setX(this.start.value);
        }
    }
}

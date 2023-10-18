package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

public class Guideline extends ConstraintWidget {
    public static final int HORIZONTAL = 0;
    public static final int RELATIVE_BEGIN = 1;
    public static final int RELATIVE_END = 2;
    public static final int RELATIVE_PERCENT = 0;
    public static final int RELATIVE_UNKNOWN = -1;
    public static final int VERTICAL = 1;
    protected boolean guidelineUseRtl = true;
    private ConstraintAnchor mAnchor = this.mTop;
    private int mMinimumPosition = 0;
    private int mOrientation = 0;
    protected int mRelativeBegin = -1;
    protected int mRelativeEnd = -1;
    protected float mRelativePercent = -1.0f;
    private boolean resolved;

    public Guideline() {
        this.mAnchors.clear();
        this.mAnchors.add(this.mAnchor);
        int count = this.mListAnchors.length;
        for (int i = 0; i < count; i++) {
            this.mListAnchors[i] = this.mAnchor;
        }
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(src, map);
        Guideline srcGuideline = (Guideline) src;
        this.mRelativePercent = srcGuideline.mRelativePercent;
        this.mRelativeBegin = srcGuideline.mRelativeBegin;
        this.mRelativeEnd = srcGuideline.mRelativeEnd;
        this.guidelineUseRtl = srcGuideline.guidelineUseRtl;
        setOrientation(srcGuideline.mOrientation);
    }

    public boolean allowedInBarrier() {
        return true;
    }

    public int getRelativeBehaviour() {
        if (this.mRelativePercent != -1.0f) {
            return 0;
        }
        if (this.mRelativeBegin != -1) {
            return 1;
        }
        if (this.mRelativeEnd != -1) {
            return 2;
        }
        return -1;
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            this.mAnchors.clear();
            if (this.mOrientation == 1) {
                this.mAnchor = this.mLeft;
            } else {
                this.mAnchor = this.mTop;
            }
            this.mAnchors.add(this.mAnchor);
            int count = this.mListAnchors.length;
            for (int i = 0; i < count; i++) {
                this.mListAnchors[i] = this.mAnchor;
            }
        }
    }

    public ConstraintAnchor getAnchor() {
        return this.mAnchor;
    }

    public String getType() {
        return "Guideline";
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setMinimumPosition(int minimum) {
        this.mMinimumPosition = minimum;
    }

    /* renamed from: androidx.constraintlayout.core.widgets.Guideline$1 */
    static /* synthetic */ class C06291 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        static final /* synthetic */ int[] f1008x6930e354;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f1008x6930e354 = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1008x6930e354[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (C06291.f1008x6930e354[anchorType.ordinal()]) {
            case 1:
            case 2:
                if (this.mOrientation == 1) {
                    return this.mAnchor;
                }
                break;
            case 3:
            case 4:
                if (this.mOrientation == 0) {
                    return this.mAnchor;
                }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return null;
        }
        return null;
    }

    public void setGuidePercent(int value) {
        setGuidePercent(((float) value) / 100.0f);
    }

    public void setGuidePercent(float value) {
        if (value > -1.0f) {
            this.mRelativePercent = value;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = -1;
        }
    }

    public void setGuideBegin(int value) {
        if (value > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = value;
            this.mRelativeEnd = -1;
        }
    }

    public void setGuideEnd(int value) {
        if (value > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = value;
        }
    }

    public float getRelativePercent() {
        return this.mRelativePercent;
    }

    public int getRelativeBegin() {
        return this.mRelativeBegin;
    }

    public int getRelativeEnd() {
        return this.mRelativeEnd;
    }

    public void setFinalValue(int position) {
        this.mAnchor.setFinalValue(position);
        this.resolved = true;
    }

    public boolean isResolvedHorizontally() {
        return this.resolved;
    }

    public boolean isResolvedVertically() {
        return this.resolved;
    }

    public void addToSolver(LinearSystem system, boolean optimize) {
        ConstraintWidgetContainer parent = (ConstraintWidgetContainer) getParent();
        if (parent != null) {
            ConstraintAnchor begin = parent.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor end = parent.getAnchor(ConstraintAnchor.Type.RIGHT);
            boolean z = true;
            boolean parentWrapContent = this.mParent != null && this.mParent.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (this.mOrientation == 0) {
                begin = parent.getAnchor(ConstraintAnchor.Type.TOP);
                end = parent.getAnchor(ConstraintAnchor.Type.BOTTOM);
                if (this.mParent == null || this.mParent.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    z = false;
                }
                parentWrapContent = z;
            }
            if (this.resolved && this.mAnchor.hasFinalValue()) {
                SolverVariable guide = system.createObjectVariable(this.mAnchor);
                system.addEquality(guide, this.mAnchor.getFinalValue());
                if (this.mRelativeBegin != -1) {
                    if (parentWrapContent) {
                        system.addGreaterThan(system.createObjectVariable(end), guide, 0, 5);
                    }
                } else if (this.mRelativeEnd != -1 && parentWrapContent) {
                    SolverVariable parentRight = system.createObjectVariable(end);
                    system.addGreaterThan(guide, system.createObjectVariable(begin), 0, 5);
                    system.addGreaterThan(parentRight, guide, 0, 5);
                }
                this.resolved = false;
            } else if (this.mRelativeBegin != -1) {
                SolverVariable guide2 = system.createObjectVariable(this.mAnchor);
                system.addEquality(guide2, system.createObjectVariable(begin), this.mRelativeBegin, 8);
                if (parentWrapContent) {
                    system.addGreaterThan(system.createObjectVariable(end), guide2, 0, 5);
                }
            } else if (this.mRelativeEnd != -1) {
                SolverVariable guide3 = system.createObjectVariable(this.mAnchor);
                SolverVariable parentRight2 = system.createObjectVariable(end);
                system.addEquality(guide3, parentRight2, -this.mRelativeEnd, 8);
                if (parentWrapContent) {
                    system.addGreaterThan(guide3, system.createObjectVariable(begin), 0, 5);
                    system.addGreaterThan(parentRight2, guide3, 0, 5);
                }
            } else if (this.mRelativePercent != -1.0f) {
                system.addConstraint(LinearSystem.createRowDimensionPercent(system, system.createObjectVariable(this.mAnchor), system.createObjectVariable(end), this.mRelativePercent));
            }
        }
    }

    public void updateFromSolver(LinearSystem system, boolean optimize) {
        if (getParent() != null) {
            int value = system.getObjectVariableValue(this.mAnchor);
            if (this.mOrientation == 1) {
                setX(value);
                setY(0);
                setHeight(getParent().getHeight());
                setWidth(0);
                return;
            }
            setX(0);
            setY(value);
            setWidth(getParent().getWidth());
            setHeight(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void inferRelativePercentPosition() {
        float percent = ((float) getX()) / ((float) getParent().getWidth());
        if (this.mOrientation == 0) {
            percent = ((float) getY()) / ((float) getParent().getHeight());
        }
        setGuidePercent(percent);
    }

    /* access modifiers changed from: package-private */
    public void inferRelativeBeginPosition() {
        int position = getX();
        if (this.mOrientation == 0) {
            position = getY();
        }
        setGuideBegin(position);
    }

    /* access modifiers changed from: package-private */
    public void inferRelativeEndPosition() {
        int position = getParent().getWidth() - getX();
        if (this.mOrientation == 0) {
            position = getParent().getHeight() - getY();
        }
        setGuideEnd(position);
    }

    public void cyclePosition() {
        if (this.mRelativeBegin != -1) {
            inferRelativePercentPosition();
        } else if (this.mRelativePercent != -1.0f) {
            inferRelativeEndPosition();
        } else if (this.mRelativeEnd != -1) {
            inferRelativeBeginPosition();
        }
    }

    public boolean isPercent() {
        return this.mRelativePercent != -1.0f && this.mRelativeBegin == -1 && this.mRelativeEnd == -1;
    }
}

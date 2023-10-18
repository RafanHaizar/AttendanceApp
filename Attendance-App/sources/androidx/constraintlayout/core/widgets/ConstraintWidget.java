package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.media.MediaRuleConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidget {
    public static final int ANCHOR_BASELINE = 4;
    public static final int ANCHOR_BOTTOM = 3;
    public static final int ANCHOR_LEFT = 0;
    public static final int ANCHOR_RIGHT = 1;
    public static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int BOTH = 2;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    static final int DIMENSION_HORIZONTAL = 0;
    static final int DIMENSION_VERTICAL = 1;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
    public static final int WRAP_BEHAVIOR_INCLUDED = 0;
    public static final int WRAP_BEHAVIOR_SKIPPED = 3;
    public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
    private boolean OPTIMIZE_WRAP;
    private boolean OPTIMIZE_WRAP_ON_RESOLVED;
    public WidgetFrame frame;
    private boolean hasBaseline;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public HorizontalWidgetRun horizontalRun;
    private boolean horizontalSolvingPass;
    private boolean inPlaceholder;
    public boolean[] isTerminalWidget;
    protected ArrayList<ConstraintAnchor> mAnchors;
    private boolean mAnimated;
    public ConstraintAnchor mBaseline;
    int mBaselineDistance;
    public ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    public ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    public float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mGroupsToSolver;
    int mHeight;
    private int mHeightOverride;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    boolean mHorizontalWrapVisited;
    private boolean mInVirtualLayout;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    private int mLastHorizontalMeasureSpec;
    private int mLastVerticalMeasureSpec;
    public ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    private boolean mMeasureRequested;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    public ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    boolean mResolvedHasRatio;
    public int[] mResolvedMatchConstraintDefault;
    public ConstraintAnchor mRight;
    boolean mRightHasCentered;
    public ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    private int mWidthOverride;
    private int mWrapBehaviorInParent;

    /* renamed from: mX */
    protected int f1004mX;

    /* renamed from: mY */
    protected int f1005mY;
    public boolean measured;
    private boolean resolvedHorizontal;
    private boolean resolvedVertical;
    public WidgetRun[] run;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public VerticalWidgetRun verticalRun;
    private boolean verticalSolvingPass;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public WidgetRun getRun(int orientation) {
        if (orientation == 0) {
            return this.horizontalRun;
        }
        if (orientation == 1) {
            return this.verticalRun;
        }
        return null;
    }

    public void setFinalFrame(int left, int top, int right, int bottom, int baseline, int orientation) {
        setFrame(left, top, right, bottom);
        setBaselineDistance(baseline);
        if (orientation == 0) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = false;
        } else if (orientation == 1) {
            this.resolvedHorizontal = false;
            this.resolvedVertical = true;
        } else if (orientation == 2) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = true;
        } else {
            this.resolvedHorizontal = false;
            this.resolvedVertical = false;
        }
    }

    public void setFinalLeft(int x1) {
        this.mLeft.setFinalValue(x1);
        this.f1004mX = x1;
    }

    public void setFinalTop(int y1) {
        this.mTop.setFinalValue(y1);
        this.f1005mY = y1;
    }

    public void resetSolvingPassFlag() {
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
    }

    public boolean isHorizontalSolvingPassDone() {
        return this.horizontalSolvingPass;
    }

    public boolean isVerticalSolvingPassDone() {
        return this.verticalSolvingPass;
    }

    public void markHorizontalSolvingPassDone() {
        this.horizontalSolvingPass = true;
    }

    public void markVerticalSolvingPassDone() {
        this.verticalSolvingPass = true;
    }

    public void setFinalHorizontal(int x1, int x2) {
        if (!this.resolvedHorizontal) {
            this.mLeft.setFinalValue(x1);
            this.mRight.setFinalValue(x2);
            this.f1004mX = x1;
            this.mWidth = x2 - x1;
            this.resolvedHorizontal = true;
        }
    }

    public void setFinalVertical(int y1, int y2) {
        if (!this.resolvedVertical) {
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(y2);
            this.f1005mY = y1;
            this.mHeight = y2 - y1;
            if (this.hasBaseline) {
                this.mBaseline.setFinalValue(this.mBaselineDistance + y1);
            }
            this.resolvedVertical = true;
        }
    }

    public void setFinalBaseline(int baselineValue) {
        if (this.hasBaseline) {
            int y1 = baselineValue - this.mBaselineDistance;
            this.f1005mY = y1;
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(this.mHeight + y1);
            this.mBaseline.setFinalValue(baselineValue);
            this.resolvedVertical = true;
        }
    }

    public boolean isResolvedHorizontally() {
        return this.resolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue());
    }

    public boolean isResolvedVertically() {
        return this.resolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue());
    }

    public void resetFinalResolution() {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            this.mAnchors.get(i).resetFinalResolution();
        }
    }

    public void ensureMeasureRequested() {
        this.mMeasureRequested = true;
    }

    public boolean hasDependencies() {
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            if (this.mAnchors.get(i).hasDependents()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDanglingDimension(int orientation) {
        if (orientation == 0) {
            if ((this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2) {
                return true;
            }
            return false;
        }
        if ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0) + (this.mBaseline.mTarget != null ? 1 : 0) < 2) {
            return true;
        }
        return false;
    }

    public boolean hasResolvedTargets(int orientation, int size) {
        if (orientation == 0) {
            if (this.mLeft.mTarget == null || !this.mLeft.mTarget.hasFinalValue() || this.mRight.mTarget == null || !this.mRight.mTarget.hasFinalValue() || (this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.mTarget.getFinalValue() + this.mLeft.getMargin()) < size) {
                return false;
            }
            return true;
        } else if (this.mTop.mTarget == null || !this.mTop.mTarget.hasFinalValue() || this.mBottom.mTarget == null || !this.mBottom.mTarget.hasFinalValue() || (this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.mTarget.getFinalValue() + this.mTop.getMargin()) < size) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public void setInVirtualLayout(boolean inVirtualLayout) {
        this.mInVirtualLayout = inVirtualLayout;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxDimension[0] = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxDimension[1] = maxHeight;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void setHasBaseline(boolean hasBaseline2) {
        this.hasBaseline = hasBaseline2;
    }

    public boolean getHasBaseline() {
        return this.hasBaseline;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public void setInPlaceholder(boolean inPlaceholder2) {
        this.inPlaceholder = inPlaceholder2;
    }

    /* access modifiers changed from: protected */
    public void setInBarrier(int orientation, boolean value) {
        this.mIsInBarrier[orientation] = value;
    }

    public boolean isInBarrier(int orientation) {
        return this.mIsInBarrier[orientation];
    }

    public void setMeasureRequested(boolean measureRequested) {
        this.mMeasureRequested = measureRequested;
    }

    public boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public void setWrapBehaviorInParent(int behavior) {
        if (behavior >= 0 && behavior <= 3) {
            this.mWrapBehaviorInParent = behavior;
        }
    }

    public int getWrapBehaviorInParent() {
        return this.mWrapBehaviorInParent;
    }

    public int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public void setLastMeasureSpec(int horizontal, int vertical) {
        this.mLastHorizontalMeasureSpec = horizontal;
        this.mLastVerticalMeasureSpec = vertical;
        setMeasureRequested(false);
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f1004mX = 0;
        this.f1005mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mGroupsToSolver = false;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    private void serializeAnchor(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(a.mGoneMargin);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeCircle(StringBuilder ret, ConstraintAnchor a, float angle) {
        if (a.mTarget != null) {
            ret.append("circle : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(angle);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, float value, float def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, int value, int def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeDimensionRatio(StringBuilder ret, String type, float value, int whichSide) {
        if (value != 0.0f) {
            ret.append(type);
            ret.append(" :  [");
            ret.append(value);
            ret.append(",");
            ret.append(whichSide);
            ret.append("");
            ret.append("],\n");
        }
    }

    private void serializeSize(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float MatchConstraintPercent, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "size", size, Integer.MIN_VALUE);
        serializeAttribute(ret, MediaRuleConstants.MIN, min, 0);
        serializeAttribute(ret, MediaRuleConstants.MAX, max, Integer.MAX_VALUE);
        serializeAttribute(ret, "matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "matchPercent", matchConstraintDefault, 1);
        ret.append("},\n");
    }

    public StringBuilder serialize(StringBuilder ret) {
        ret.append("{\n");
        serializeAnchor(ret, "left", this.mLeft);
        serializeAnchor(ret, CommonCssConstants.TOP, this.mTop);
        serializeAnchor(ret, "right", this.mRight);
        serializeAnchor(ret, CommonCssConstants.BOTTOM, this.mBottom);
        serializeAnchor(ret, "baseline", this.mBaseline);
        serializeAnchor(ret, "centerX", this.mCenterX);
        serializeAnchor(ret, "centerY", this.mCenterY);
        serializeCircle(ret, this.mCenter, this.mCircleConstraintAngle);
        serializeSize(ret, "width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        serializeSize(ret, "height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(ret, "dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        ret.append("}\n");
        return ret;
    }

    public boolean oppositeDimensionDependsOn(int orientation) {
        int oppositeOrientation = orientation == 0 ? 1 : 0;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[orientation];
        DimensionBehaviour oppositeDimensionBehaviour = dimensionBehaviourArr[oppositeOrientation];
        if (dimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && oppositeDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            return true;
        }
        return false;
    }

    public boolean oppositeDimensionsTied() {
        return this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public ConstraintWidget() {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f1004mX = 0;
        this.f1005mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    public ConstraintWidget(String debugName) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f1004mX = 0;
        this.f1005mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
        setDebugName(debugName);
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f1004mX = 0;
        this.f1005mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        this.f1004mX = x;
        this.f1005mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
    }

    public ConstraintWidget(String debugName, int x, int y, int width, int height) {
        this(x, y, width, height);
        setDebugName(debugName);
    }

    public ConstraintWidget(int width, int height) {
        this(0, 0, width, height);
    }

    public void ensureWidgetRuns() {
        if (this.horizontalRun == null) {
            this.horizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.verticalRun == null) {
            this.verticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintWidget(String debugName, int width, int height) {
        this(width, height);
        setDebugName(debugName);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setWidthWrapContent(boolean widthWrapContent) {
        this.mIsWidthWrapContent = widthWrapContent;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void setHeightWrapContent(boolean heightWrapContent) {
        this.mIsHeightWrapContent = heightWrapContent;
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public void connectCircularConstraint(ConstraintWidget target, float angle, int radius) {
        immediateConnect(ConstraintAnchor.Type.CENTER, target, ConstraintAnchor.Type.CENTER, radius, 0);
        this.mCircleConstraintAngle = angle;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public void setAnimated(boolean animated) {
        this.mAnimated = animated;
    }

    public boolean isAnimated() {
        return this.mAnimated;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        system.createObjectVariable(this.mBaseline).setName(name + ".baseline");
    }

    public void createObjectVariables(LinearSystem system) {
        SolverVariable createObjectVariable = system.createObjectVariable(this.mLeft);
        SolverVariable createObjectVariable2 = system.createObjectVariable(this.mTop);
        SolverVariable createObjectVariable3 = system.createObjectVariable(this.mRight);
        SolverVariable createObjectVariable4 = system.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline);
        }
    }

    public String toString() {
        String str = "";
        StringBuilder append = new StringBuilder().append(this.mType != null ? "type: " + this.mType + " " : str);
        if (this.mDebugName != null) {
            str = "id: " + this.mDebugName + " ";
        }
        return append.append(str).append("(").append(this.f1004mX).append(", ").append(this.f1005mY).append(") - (").append(this.mWidth).append(" x ").append(this.mHeight).append(")").toString();
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.f1004mX;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.f1004mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.f1005mY;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.f1005mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            w = Math.max(this.mMatchConstraintMinWidth, w2);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = 0;
        }
        int i = this.mMatchConstraintMaxWidth;
        if (i <= 0 || i >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            h = Math.max(this.mMatchConstraintMinHeight, h2);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = 0;
        }
        int i = this.mMatchConstraintMaxHeight;
        if (i <= 0 || i >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getLength(int orientation) {
        if (orientation == 0) {
            return getWidth();
        }
        if (orientation == 1) {
            return getHeight();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.f1004mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.f1005mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public int getHorizontalMargin() {
        int margin = 0;
        ConstraintAnchor constraintAnchor = this.mLeft;
        if (constraintAnchor != null) {
            margin = 0 + constraintAnchor.mMargin;
        }
        ConstraintAnchor constraintAnchor2 = this.mRight;
        if (constraintAnchor2 != null) {
            return margin + constraintAnchor2.mMargin;
        }
        return margin;
    }

    public int getVerticalMargin() {
        int margin = 0;
        if (this.mLeft != null) {
            margin = 0 + this.mTop.mMargin;
        }
        if (this.mRight != null) {
            return margin + this.mBottom.mMargin;
        }
        return margin;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public float getBiasPercent(int orientation) {
        if (orientation == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (orientation == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.f1004mX = x;
    }

    public void setY(int y) {
        this.f1005mY = y;
    }

    public void setOrigin(int x, int y) {
        this.f1004mX = x;
        this.f1005mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int goneMargin) {
        switch (C06281.f1006x6930e354[type.ordinal()]) {
            case 1:
                this.mLeft.mGoneMargin = goneMargin;
                return;
            case 2:
                this.mTop.mGoneMargin = goneMargin;
                return;
            case 3:
                this.mRight.mGoneMargin = goneMargin;
                return;
            case 4:
                this.mBottom.mGoneMargin = goneMargin;
                return;
            case 5:
                this.mBaseline.mGoneMargin = goneMargin;
                return;
            default:
                return;
        }
    }

    public void setWidth(int w) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
    }

    public void setLength(int length, int orientation) {
        if (orientation == 0) {
            setWidth(length);
        } else if (orientation == 1) {
            setHeight(length);
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentWidth = percent;
        if (percent > 0.0f && percent < 1.0f && horizontalMatchStyle == 0) {
            this.mMatchConstraintDefaultWidth = 2;
        }
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentHeight = percent;
        if (percent > 0.0f && percent < 1.0f && verticalMatchStyle == 0) {
            this.mMatchConstraintDefaultHeight = 2;
        }
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
            commaIndex = 0;
        } else {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len - 1) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
        this.mHeight = h;
        int i2 = this.mMinHeight;
        if (h < i2) {
            this.mHeight = i2;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.f1004mX = left;
        this.f1005mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
        int i2 = this.mMinWidth;
        if (w < i2) {
            this.mWidth = i2;
        }
        if (this.mMatchConstraintMaxWidth > 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, this.mMatchConstraintMaxWidth);
        }
        if (this.mMatchConstraintMaxHeight > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, this.mMatchConstraintMaxHeight);
        }
        int i3 = this.mWidth;
        if (w != i3) {
            this.mWidthOverride = i3;
        }
        int i4 = this.mHeight;
        if (h != i4) {
            this.mHeightOverride = i4;
        }
    }

    public void setFrame(int start, int end, int orientation) {
        if (orientation == 0) {
            setHorizontalDimension(start, end);
        } else if (orientation == 1) {
            setVerticalDimension(start, end);
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.f1004mX = left;
        int i = right - left;
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.f1005mY = top;
        int i = bottom - top;
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public int getRelativePositioning(int orientation) {
        if (orientation == 0) {
            return this.mRelX;
        }
        if (orientation == 1) {
            return this.mRelY;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setRelativePositioning(int offset, int orientation) {
        if (orientation == 0) {
            this.mRelX = offset;
        } else if (orientation == 1) {
            this.mRelY = offset;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
        this.hasBaseline = baseline > 0;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mWeight[0] = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mWeight[1] = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        getAnchor(startType).connect(target.getAnchor(endType), margin, goneMargin, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin);
        }
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo) {
        connect(constraintFrom, target, constraintTo, 0);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin) {
        if (constraintFrom == ConstraintAnchor.Type.CENTER) {
            if (constraintTo == ConstraintAnchor.Type.CENTER) {
                ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
                ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
                ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
                boolean centerX = false;
                boolean centerY = false;
                if ((left == null || !left.isConnected()) && (right == null || !right.isConnected())) {
                    connect(ConstraintAnchor.Type.LEFT, target, ConstraintAnchor.Type.LEFT, 0);
                    connect(ConstraintAnchor.Type.RIGHT, target, ConstraintAnchor.Type.RIGHT, 0);
                    centerX = true;
                }
                if ((top == null || !top.isConnected()) && (bottom == null || !bottom.isConnected())) {
                    connect(ConstraintAnchor.Type.TOP, target, ConstraintAnchor.Type.TOP, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, target, ConstraintAnchor.Type.BOTTOM, 0);
                    centerY = true;
                }
                if (centerX && centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(ConstraintAnchor.Type.CENTER), 0);
                } else if (centerX) {
                    getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(ConstraintAnchor.Type.CENTER_X), 0);
                } else if (centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0);
                }
            } else if (constraintTo == ConstraintAnchor.Type.LEFT || constraintTo == ConstraintAnchor.Type.RIGHT) {
                connect(ConstraintAnchor.Type.LEFT, target, constraintTo, 0);
                connect(ConstraintAnchor.Type.RIGHT, target, constraintTo, 0);
                getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0);
            } else if (constraintTo == ConstraintAnchor.Type.TOP || constraintTo == ConstraintAnchor.Type.BOTTOM) {
                connect(ConstraintAnchor.Type.TOP, target, constraintTo, 0);
                connect(ConstraintAnchor.Type.BOTTOM, target, constraintTo, 0);
                getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0);
            }
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_X && (constraintTo == ConstraintAnchor.Type.LEFT || constraintTo == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor left2 = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = target.getAnchor(constraintTo);
            ConstraintAnchor right2 = getAnchor(ConstraintAnchor.Type.RIGHT);
            left2.connect(targetAnchor, 0);
            right2.connect(targetAnchor, 0);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(targetAnchor, 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_Y && (constraintTo == ConstraintAnchor.Type.TOP || constraintTo == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor targetAnchor2 = target.getAnchor(constraintTo);
            getAnchor(ConstraintAnchor.Type.TOP).connect(targetAnchor2, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(targetAnchor2, 0);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(targetAnchor2, 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_X && constraintTo == ConstraintAnchor.Type.CENTER_X) {
            getAnchor(ConstraintAnchor.Type.LEFT).connect(target.getAnchor(ConstraintAnchor.Type.LEFT), 0);
            getAnchor(ConstraintAnchor.Type.RIGHT).connect(target.getAnchor(ConstraintAnchor.Type.RIGHT), 0);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(constraintTo), 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_Y && constraintTo == ConstraintAnchor.Type.CENTER_Y) {
            getAnchor(ConstraintAnchor.Type.TOP).connect(target.getAnchor(ConstraintAnchor.Type.TOP), 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(target.getAnchor(ConstraintAnchor.Type.BOTTOM), 0);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(constraintTo), 0);
        } else {
            ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
            ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
            if (fromAnchor.isValidConnection(toAnchor)) {
                if (constraintFrom == ConstraintAnchor.Type.BASELINE) {
                    ConstraintAnchor top2 = getAnchor(ConstraintAnchor.Type.TOP);
                    ConstraintAnchor bottom2 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                    if (top2 != null) {
                        top2.reset();
                    }
                    if (bottom2 != null) {
                        bottom2.reset();
                    }
                } else if (constraintFrom == ConstraintAnchor.Type.TOP || constraintFrom == ConstraintAnchor.Type.BOTTOM) {
                    ConstraintAnchor baseline = getAnchor(ConstraintAnchor.Type.BASELINE);
                    if (baseline != null) {
                        baseline.reset();
                    }
                    ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
                    if (center.getTarget() != toAnchor) {
                        center.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(constraintFrom).getOpposite();
                    ConstraintAnchor centerY2 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
                    if (centerY2.isConnected()) {
                        opposite.reset();
                        centerY2.reset();
                    }
                } else if (constraintFrom == ConstraintAnchor.Type.LEFT || constraintFrom == ConstraintAnchor.Type.RIGHT) {
                    ConstraintAnchor center2 = getAnchor(ConstraintAnchor.Type.CENTER);
                    if (center2.getTarget() != toAnchor) {
                        center2.reset();
                    }
                    ConstraintAnchor opposite2 = getAnchor(constraintFrom).getOpposite();
                    ConstraintAnchor centerX2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
                    if (centerX2.isConnected()) {
                        opposite2.reset();
                        centerX2.reset();
                    }
                }
                fromAnchor.connect(toAnchor, margin);
            }
        }
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
            ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
            ConstraintAnchor centerX = getAnchor(ConstraintAnchor.Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (C06281.f1006x6930e354[anchorType.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(anchorType.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public DimensionBehaviour getDimensionBehaviour(int orientation) {
        if (orientation == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (orientation == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[0] = behaviour;
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[1] = behaviour;
    }

    public boolean isInHorizontalChain() {
        if (this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) {
            return true;
        }
        if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getPreviousChainMember(int orientation) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (orientation == 0) {
            if (this.mLeft.mTarget == null || this.mLeft.mTarget.mTarget != (constraintAnchor2 = this.mLeft)) {
                return null;
            }
            return constraintAnchor2.mTarget.mOwner;
        } else if (orientation == 1 && this.mTop.mTarget != null && this.mTop.mTarget.mTarget == (constraintAnchor = this.mTop)) {
            return constraintAnchor.mTarget.mOwner;
        } else {
            return null;
        }
    }

    public ConstraintWidget getNextChainMember(int orientation) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (orientation == 0) {
            if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != (constraintAnchor2 = this.mRight)) {
                return null;
            }
            return constraintAnchor2.mTarget.mOwner;
        } else if (orientation == 1 && this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == (constraintAnchor = this.mBottom)) {
            return constraintAnchor.mTarget.mOwner;
        } else {
            return null;
        }
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        if (this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) {
            return true;
        }
        if (this.mBottom.mTarget == null || this.mBottom.mTarget.mTarget != this.mBottom) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    private boolean isChainHead(int orientation) {
        int offset = orientation * 2;
        if (this.mListAnchors[offset].mTarget != null) {
            ConstraintAnchor constraintAnchor = this.mListAnchors[offset].mTarget.mTarget;
            ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
            return (constraintAnchor == constraintAnchorArr[offset] || constraintAnchorArr[offset + 1].mTarget == null || this.mListAnchors[offset + 1].mTarget.mTarget != this.mListAnchors[offset + 1]) ? false : true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:193:0x0352  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0355  */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0361  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x0364  */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x0373  */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x0375  */
    /* JADX WARNING: Removed duplicated region for block: B:210:0x037a  */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x037e  */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x0389  */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x038d  */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x03a2  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x04f2  */
    /* JADX WARNING: Removed duplicated region for block: B:257:0x0509  */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x056a  */
    /* JADX WARNING: Removed duplicated region for block: B:279:0x057d  */
    /* JADX WARNING: Removed duplicated region for block: B:280:0x0580  */
    /* JADX WARNING: Removed duplicated region for block: B:284:0x0587  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x062b  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x062e  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x066a  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x066e  */
    /* JADX WARNING: Removed duplicated region for block: B:329:0x069a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(androidx.constraintlayout.core.LinearSystem r74, boolean r75) {
        /*
            r73 = this;
            r15 = r73
            r14 = r74
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mLeft
            androidx.constraintlayout.core.SolverVariable r13 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mRight
            androidx.constraintlayout.core.SolverVariable r12 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mTop
            androidx.constraintlayout.core.SolverVariable r11 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBottom
            androidx.constraintlayout.core.SolverVariable r10 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBaseline
            androidx.constraintlayout.core.SolverVariable r9 = r14.createObjectVariable(r0)
            r0 = 0
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            r8 = 1
            r5 = 0
            if (r2 == 0) goto L_0x005d
            if (r2 == 0) goto L_0x0036
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.mListDimensionBehaviors
            r2 = r2[r5]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x0036
            r2 = 1
            goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            r0 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            if (r2 == 0) goto L_0x0046
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.mListDimensionBehaviors
            r2 = r2[r8]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x0046
            r2 = 1
            goto L_0x0047
        L_0x0046:
            r2 = 0
        L_0x0047:
            r1 = r2
            int r2 = r15.mWrapBehaviorInParent
            switch(r2) {
                case 1: goto L_0x0059;
                case 2: goto L_0x0055;
                case 3: goto L_0x0050;
                default: goto L_0x004d;
            }
        L_0x004d:
            r4 = r0
            r3 = r1
            goto L_0x005f
        L_0x0050:
            r0 = 0
            r1 = 0
            r4 = r0
            r3 = r1
            goto L_0x005f
        L_0x0055:
            r0 = 0
            r4 = r0
            r3 = r1
            goto L_0x005f
        L_0x0059:
            r1 = 0
            r4 = r0
            r3 = r1
            goto L_0x005f
        L_0x005d:
            r4 = r0
            r3 = r1
        L_0x005f:
            int r0 = r15.mVisibility
            r2 = 8
            if (r0 != r2) goto L_0x007a
            boolean r0 = r15.mAnimated
            if (r0 != 0) goto L_0x007a
            boolean r0 = r73.hasDependencies()
            if (r0 != 0) goto L_0x007a
            boolean[] r0 = r15.mIsInBarrier
            boolean r1 = r0[r5]
            if (r1 != 0) goto L_0x007a
            boolean r0 = r0[r8]
            if (r0 != 0) goto L_0x007a
            return
        L_0x007a:
            boolean r0 = r15.resolvedHorizontal
            if (r0 != 0) goto L_0x0082
            boolean r1 = r15.resolvedVertical
            if (r1 == 0) goto L_0x0101
        L_0x0082:
            if (r0 == 0) goto L_0x00b2
            int r0 = r15.f1004mX
            r14.addEquality(r13, r0)
            int r0 = r15.f1004mX
            int r1 = r15.mWidth
            int r0 = r0 + r1
            r14.addEquality(r12, r0)
            if (r4 == 0) goto L_0x00b2
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x00b2
            boolean r1 = r15.OPTIMIZE_WRAP_ON_RESOLVED
            if (r1 == 0) goto L_0x00a8
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r0 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mLeft
            r0.addHorizontalWrapMinVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mRight
            r0.addHorizontalWrapMaxVariable(r1)
            goto L_0x00b2
        L_0x00a8:
            r1 = 5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r14.addGreaterThan(r0, r12, r5, r1)
        L_0x00b2:
            boolean r0 = r15.resolvedVertical
            if (r0 == 0) goto L_0x00f4
            int r0 = r15.f1005mY
            r14.addEquality(r11, r0)
            int r0 = r15.f1005mY
            int r1 = r15.mHeight
            int r0 = r0 + r1
            r14.addEquality(r10, r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBaseline
            boolean r0 = r0.hasDependents()
            if (r0 == 0) goto L_0x00d3
            int r0 = r15.f1005mY
            int r1 = r15.mBaselineDistance
            int r0 = r0 + r1
            r14.addEquality(r9, r0)
        L_0x00d3:
            if (r3 == 0) goto L_0x00f4
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x00f4
            boolean r1 = r15.OPTIMIZE_WRAP_ON_RESOLVED
            if (r1 == 0) goto L_0x00ea
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r0 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mTop
            r0.addVerticalWrapMinVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mBottom
            r0.addVerticalWrapMaxVariable(r1)
            goto L_0x00f4
        L_0x00ea:
            r1 = 5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r14.addGreaterThan(r0, r10, r5, r1)
        L_0x00f4:
            boolean r0 = r15.resolvedHorizontal
            if (r0 == 0) goto L_0x0101
            boolean r0 = r15.resolvedVertical
            if (r0 == 0) goto L_0x0101
            r15.resolvedHorizontal = r5
            r15.resolvedVertical = r5
            return
        L_0x0101:
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            r6 = 1
            if (r0 == 0) goto L_0x0111
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            r16 = r3
            long r2 = r0.widgets
            long r2 = r2 + r6
            r0.widgets = r2
            goto L_0x0113
        L_0x0111:
            r16 = r3
        L_0x0113:
            if (r75 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            if (r0 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r15.verticalRun
            if (r1 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b2
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b2
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r0 == 0) goto L_0x0146
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            long r1 = r0.graphSolved
            long r1 = r1 + r6
            r0.graphSolved = r1
        L_0x0146:
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            int r0 = r0.value
            r14.addEquality(r13, r0)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r12, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            int r0 = r0.value
            r14.addEquality(r11, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r10, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.baseline
            int r0 = r0.value
            r14.addEquality(r9, r0)
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x01ad
            if (r4 == 0) goto L_0x0192
            boolean[] r0 = r15.isTerminalWidget
            boolean r0 = r0[r5]
            if (r0 == 0) goto L_0x0192
            boolean r0 = r73.isInHorizontalChain()
            if (r0 != 0) goto L_0x0192
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r1 = 8
            r14.addGreaterThan(r0, r12, r5, r1)
        L_0x0192:
            if (r16 == 0) goto L_0x01ad
            boolean[] r0 = r15.isTerminalWidget
            boolean r0 = r0[r8]
            if (r0 == 0) goto L_0x01ad
            boolean r0 = r73.isInVerticalChain()
            if (r0 != 0) goto L_0x01ad
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r1 = 8
            r14.addGreaterThan(r0, r10, r5, r1)
        L_0x01ad:
            r15.resolvedHorizontal = r5
            r15.resolvedVertical = r5
            return
        L_0x01b2:
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r0 == 0) goto L_0x01bd
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            long r1 = r0.linearSolved
            long r1 = r1 + r6
            r0.linearSolved = r1
        L_0x01bd:
            r0 = 0
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            if (r2 == 0) goto L_0x0234
            boolean r2 = r15.isChainHead(r5)
            if (r2 == 0) goto L_0x01d2
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r2 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r2
            r2.addChain(r15, r5)
            r0 = 1
            goto L_0x01d6
        L_0x01d2:
            boolean r0 = r73.isInHorizontalChain()
        L_0x01d6:
            boolean r2 = r15.isChainHead(r8)
            if (r2 == 0) goto L_0x01e5
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r2 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r2
            r2.addChain(r15, r8)
            r1 = 1
            goto L_0x01e9
        L_0x01e5:
            boolean r1 = r73.isInVerticalChain()
        L_0x01e9:
            if (r0 != 0) goto L_0x020a
            if (r4 == 0) goto L_0x020a
            int r2 = r15.mVisibility
            r3 = 8
            if (r2 == r3) goto L_0x020a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x020a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x020a
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mRight
            androidx.constraintlayout.core.SolverVariable r2 = r14.createObjectVariable(r2)
            r14.addGreaterThan(r2, r12, r5, r8)
        L_0x020a:
            if (r1 != 0) goto L_0x022f
            if (r16 == 0) goto L_0x022f
            int r2 = r15.mVisibility
            r3 = 8
            if (r2 == r3) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mBaseline
            if (r2 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mBottom
            androidx.constraintlayout.core.SolverVariable r2 = r14.createObjectVariable(r2)
            r14.addGreaterThan(r2, r10, r5, r8)
        L_0x022f:
            r56 = r0
            r57 = r1
            goto L_0x0238
        L_0x0234:
            r56 = r0
            r57 = r1
        L_0x0238:
            int r0 = r15.mWidth
            int r1 = r15.mMinWidth
            if (r0 >= r1) goto L_0x0240
            int r0 = r15.mMinWidth
        L_0x0240:
            int r1 = r15.mHeight
            int r2 = r15.mMinHeight
            if (r1 >= r2) goto L_0x0248
            int r1 = r15.mMinHeight
        L_0x0248:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r2 = r2[r5]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 == r3) goto L_0x0252
            r2 = 1
            goto L_0x0253
        L_0x0252:
            r2 = 0
        L_0x0253:
            r3 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r2 = r2[r8]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 == r6) goto L_0x025e
            r2 = 1
            goto L_0x025f
        L_0x025e:
            r2 = 0
        L_0x025f:
            r6 = 0
            int r7 = r15.mDimensionRatioSide
            r15.mResolvedDimensionRatioSide = r7
            float r7 = r15.mDimensionRatio
            r15.mResolvedDimensionRatio = r7
            int r8 = r15.mMatchConstraintDefaultWidth
            int r5 = r15.mMatchConstraintDefaultHeight
            r17 = 0
            r21 = r0
            int r7 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
            if (r7 <= 0) goto L_0x0332
            int r7 = r15.mVisibility
            r0 = 8
            if (r7 == r0) goto L_0x0332
            r6 = 1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r7 = 0
            r0 = r0[r7]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r7) goto L_0x0287
            if (r8 != 0) goto L_0x0287
            r8 = 3
        L_0x0287:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r7 = 1
            r0 = r0[r7]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r7) goto L_0x0293
            if (r5 != 0) goto L_0x0293
            r5 = 3
        L_0x0293:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r7 = 0
            r0 = r0[r7]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r17 = r1
            r1 = 3
            if (r0 != r7) goto L_0x02b5
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r7 = 1
            r0 = r0[r7]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r7) goto L_0x02b5
            if (r8 != r1) goto L_0x02b5
            if (r5 != r1) goto L_0x02b5
            r0 = r16
            r15.setupDimensionRatio(r4, r0, r3, r2)
            r23 = r0
            goto L_0x0336
        L_0x02b5:
            r0 = r16
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r15.mListDimensionBehaviors
            r1 = 0
            r7 = r7[r1]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r7 != r1) goto L_0x02ef
            r1 = 3
            if (r8 != r1) goto L_0x02ef
            r1 = 0
            r15.mResolvedDimensionRatioSide = r1
            float r1 = r15.mResolvedDimensionRatio
            int r7 = r15.mHeight
            float r7 = (float) r7
            float r1 = r1 * r7
            int r1 = (int) r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r15.mListDimensionBehaviors
            r16 = 1
            r7 = r7[r16]
            r23 = r0
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r7 == r0) goto L_0x02e6
            r8 = 4
            r6 = 0
            r0 = r1
            r58 = r5
            r59 = r8
            r28 = r17
            r8 = r6
            goto L_0x033f
        L_0x02e6:
            r0 = r1
            r58 = r5
            r59 = r8
            r28 = r17
            r8 = r6
            goto L_0x033f
        L_0x02ef:
            r23 = r0
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r1 = 1
            r0 = r0[r1]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r7) goto L_0x0336
            r0 = 3
            if (r5 != r0) goto L_0x0336
            r15.mResolvedDimensionRatioSide = r1
            int r0 = r15.mDimensionRatioSide
            r1 = -1
            if (r0 != r1) goto L_0x030b
            r0 = 1065353216(0x3f800000, float:1.0)
            float r1 = r15.mResolvedDimensionRatio
            float r0 = r0 / r1
            r15.mResolvedDimensionRatio = r0
        L_0x030b:
            float r0 = r15.mResolvedDimensionRatio
            int r1 = r15.mWidth
            float r1 = (float) r1
            float r0 = r0 * r1
            int r1 = (int) r0
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r7 = 0
            r0 = r0[r7]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 == r7) goto L_0x0328
            r5 = 4
            r6 = 0
            r28 = r1
            r58 = r5
            r59 = r8
            r0 = r21
            r8 = r6
            goto L_0x033f
        L_0x0328:
            r28 = r1
            r58 = r5
            r59 = r8
            r0 = r21
            r8 = r6
            goto L_0x033f
        L_0x0332:
            r17 = r1
            r23 = r16
        L_0x0336:
            r58 = r5
            r59 = r8
            r28 = r17
            r0 = r21
            r8 = r6
        L_0x033f:
            int[] r1 = r15.mResolvedMatchConstraintDefault
            r5 = 0
            r1[r5] = r59
            r5 = 1
            r1[r5] = r58
            r15.mResolvedHasRatio = r8
            if (r8 == 0) goto L_0x0355
            int r1 = r15.mResolvedDimensionRatioSide
            if (r1 == 0) goto L_0x0352
            r5 = -1
            if (r1 != r5) goto L_0x0355
        L_0x0352:
            r17 = 1
            goto L_0x0357
        L_0x0355:
            r17 = 0
        L_0x0357:
            if (r8 == 0) goto L_0x0364
            int r1 = r15.mResolvedDimensionRatioSide
            r5 = 1
            if (r1 == r5) goto L_0x0361
            r5 = -1
            if (r1 != r5) goto L_0x0364
        L_0x0361:
            r45 = 1
            goto L_0x0366
        L_0x0364:
            r45 = 0
        L_0x0366:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r5 = 0
            r1 = r1[r5]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r1 != r5) goto L_0x0375
            boolean r1 = r15 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r1 == 0) goto L_0x0375
            r1 = 1
            goto L_0x0376
        L_0x0375:
            r1 = 0
        L_0x0376:
            r29 = r1
            if (r29 == 0) goto L_0x037e
            r0 = 0
            r60 = r0
            goto L_0x0380
        L_0x037e:
            r60 = r0
        L_0x0380:
            r0 = 1
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mCenter
            boolean r1 = r1.isConnected()
            if (r1 == 0) goto L_0x038d
            r0 = 0
            r30 = r0
            goto L_0x038f
        L_0x038d:
            r30 = r0
        L_0x038f:
            boolean[] r0 = r15.mIsInBarrier
            r1 = 0
            boolean r61 = r0[r1]
            r1 = 1
            boolean r62 = r0[r1]
            int r0 = r15.mHorizontalResolution
            r5 = 2
            r31 = 0
            if (r0 == r5) goto L_0x04f2
            boolean r0 = r15.resolvedHorizontal
            if (r0 != 0) goto L_0x04f2
            if (r75 == 0) goto L_0x044c
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            if (r0 == 0) goto L_0x044c
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x044c
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            boolean r0 = r0.resolved
            if (r0 != 0) goto L_0x03ba
            r6 = 8
            goto L_0x044e
        L_0x03ba:
            if (r75 == 0) goto L_0x0434
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            int r0 = r0.value
            r14.addEquality(r13, r0)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r12, r0)
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x041c
            if (r4 == 0) goto L_0x0404
            boolean[] r0 = r15.isTerminalWidget
            r1 = 0
            boolean r0 = r0[r1]
            if (r0 == 0) goto L_0x0404
            boolean r0 = r73.isInHorizontalChain()
            if (r0 != 0) goto L_0x0404
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r6 = 8
            r14.addGreaterThan(r0, r12, r1, r6)
            r63 = r2
            r65 = r3
            r66 = r4
            r67 = r8
            r68 = r9
            r69 = r10
            r70 = r11
            r71 = r12
            r72 = r13
            r64 = r23
            goto L_0x0506
        L_0x0404:
            r6 = 8
            r63 = r2
            r65 = r3
            r66 = r4
            r67 = r8
            r68 = r9
            r69 = r10
            r70 = r11
            r71 = r12
            r72 = r13
            r64 = r23
            goto L_0x0506
        L_0x041c:
            r6 = 8
            r63 = r2
            r65 = r3
            r66 = r4
            r67 = r8
            r68 = r9
            r69 = r10
            r70 = r11
            r71 = r12
            r72 = r13
            r64 = r23
            goto L_0x0506
        L_0x0434:
            r6 = 8
            r63 = r2
            r65 = r3
            r66 = r4
            r67 = r8
            r68 = r9
            r69 = r10
            r70 = r11
            r71 = r12
            r72 = r13
            r64 = r23
            goto L_0x0506
        L_0x044c:
            r6 = 8
        L_0x044e:
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x045a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r7 = r0
            goto L_0x045c
        L_0x045a:
            r7 = r31
        L_0x045c:
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x0467
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mLeft
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            goto L_0x0469
        L_0x0467:
            r0 = r31
        L_0x0469:
            r16 = 8
            r6 = r0
            r18 = 1
            boolean[] r0 = r15.isTerminalWidget
            r20 = 0
            boolean r21 = r0[r20]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r22 = r0[r20]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mLeft
            r27 = r2
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mRight
            r32 = r2
            int r2 = r15.f1004mX
            r33 = r2
            int r2 = r15.mMinWidth
            int[] r5 = r15.mMaxDimension
            r35 = r5[r20]
            float r5 = r15.mHorizontalBiasPercent
            r19 = 1
            r0 = r0[r19]
            r36 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x0499
            r37 = 1
            goto L_0x049b
        L_0x0499:
            r37 = 0
        L_0x049b:
            int r0 = r15.mMatchConstraintMinWidth
            r24 = r0
            int r0 = r15.mMatchConstraintMaxWidth
            r25 = r0
            float r0 = r15.mMatchConstraintPercentWidth
            r26 = r0
            r0 = r73
            r1 = r74
            r63 = r27
            r16 = r32
            r27 = r33
            r32 = r2
            r2 = r18
            r65 = r3
            r64 = r23
            r3 = r4
            r66 = r4
            r4 = r64
            r18 = r5
            r5 = r21
            r67 = r8
            r8 = r22
            r68 = r9
            r9 = r29
            r69 = r10
            r10 = r36
            r70 = r11
            r11 = r16
            r71 = r12
            r12 = r27
            r72 = r13
            r13 = r60
            r14 = r32
            r15 = r35
            r16 = r18
            r18 = r37
            r19 = r56
            r20 = r57
            r21 = r61
            r22 = r59
            r23 = r58
            r27 = r30
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            goto L_0x0506
        L_0x04f2:
            r63 = r2
            r65 = r3
            r66 = r4
            r67 = r8
            r68 = r9
            r69 = r10
            r70 = r11
            r71 = r12
            r72 = r13
            r64 = r23
        L_0x0506:
            r0 = 1
            if (r75 == 0) goto L_0x056a
            r7 = r73
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            if (r1 == 0) goto L_0x056c
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x056c
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.end
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x056c
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.start
            int r1 = r1.value
            r8 = r74
            r9 = r70
            r8.addEquality(r9, r1)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.value
            r10 = r69
            r8.addEquality(r10, r1)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.baseline
            int r1 = r1.value
            r11 = r68
            r8.addEquality(r11, r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r7.mParent
            if (r1 == 0) goto L_0x0564
            if (r57 != 0) goto L_0x055f
            if (r64 == 0) goto L_0x055f
            boolean[] r2 = r7.isTerminalWidget
            r3 = 1
            boolean r2 = r2[r3]
            if (r2 == 0) goto L_0x055c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.mBottom
            androidx.constraintlayout.core.SolverVariable r1 = r8.createObjectVariable(r1)
            r2 = 8
            r12 = 0
            r8.addGreaterThan(r1, r10, r12, r2)
            goto L_0x0568
        L_0x055c:
            r2 = 8
            goto L_0x0562
        L_0x055f:
            r2 = 8
            r3 = 1
        L_0x0562:
            r12 = 0
            goto L_0x0568
        L_0x0564:
            r2 = 8
            r3 = 1
            r12 = 0
        L_0x0568:
            r0 = 0
            goto L_0x0578
        L_0x056a:
            r7 = r73
        L_0x056c:
            r8 = r74
            r11 = r68
            r10 = r69
            r9 = r70
            r2 = 8
            r3 = 1
            r12 = 0
        L_0x0578:
            int r1 = r7.mVerticalResolution
            r4 = 2
            if (r1 != r4) goto L_0x0580
            r0 = 0
            r13 = r0
            goto L_0x0581
        L_0x0580:
            r13 = r0
        L_0x0581:
            if (r13 == 0) goto L_0x066a
            boolean r0 = r7.resolvedVertical
            if (r0 != 0) goto L_0x066a
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r7.mListDimensionBehaviors
            r0 = r0[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x0595
            boolean r0 = r7 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x0595
            r0 = 1
            goto L_0x0596
        L_0x0595:
            r0 = 0
        L_0x0596:
            if (r0 == 0) goto L_0x059d
            r28 = 0
            r1 = r28
            goto L_0x059f
        L_0x059d:
            r1 = r28
        L_0x059f:
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r7.mParent
            if (r4 == 0) goto L_0x05aa
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mBottom
            androidx.constraintlayout.core.SolverVariable r4 = r8.createObjectVariable(r4)
            goto L_0x05ac
        L_0x05aa:
            r4 = r31
        L_0x05ac:
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r7.mParent
            if (r5 == 0) goto L_0x05b9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTop
            androidx.constraintlayout.core.SolverVariable r5 = r8.createObjectVariable(r5)
            r34 = r5
            goto L_0x05bb
        L_0x05b9:
            r34 = r31
        L_0x05bb:
            int r5 = r7.mBaselineDistance
            if (r5 > 0) goto L_0x05c3
            int r5 = r7.mVisibility
            if (r5 != r2) goto L_0x0607
        L_0x05c3:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r7.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x05f2
            int r5 = r73.getBaselineDistance()
            r8.addEquality(r11, r9, r5, r2)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r7.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r5 = r8.createObjectVariable(r5)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r7.mBaseline
            int r6 = r6.getMargin()
            r8.addEquality(r11, r5, r6, r2)
            r30 = 0
            if (r64 == 0) goto L_0x05ef
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r7.mBottom
            androidx.constraintlayout.core.SolverVariable r2 = r8.createObjectVariable(r2)
            r14 = 5
            r8.addGreaterThan(r4, r2, r12, r14)
        L_0x05ef:
            r2 = r30
            goto L_0x0609
        L_0x05f2:
            int r5 = r7.mVisibility
            if (r5 != r2) goto L_0x0600
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r7.mBaseline
            int r5 = r5.getMargin()
            r8.addEquality(r11, r9, r5, r2)
            goto L_0x0607
        L_0x0600:
            int r5 = r73.getBaselineDistance()
            r8.addEquality(r11, r9, r5, r2)
        L_0x0607:
            r2 = r30
        L_0x0609:
            r30 = 0
            boolean[] r5 = r7.isTerminalWidget
            boolean r33 = r5[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r7.mListDimensionBehaviors
            r36 = r5[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r7.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r7.mBottom
            int r15 = r7.f1005mY
            int r12 = r7.mMinHeight
            r68 = r11
            int[] r11 = r7.mMaxDimension
            r43 = r11[r3]
            float r11 = r7.mVerticalBiasPercent
            r16 = 0
            r5 = r5[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r3) goto L_0x062e
            r46 = 1
            goto L_0x0630
        L_0x062e:
            r46 = 0
        L_0x0630:
            int r3 = r7.mMatchConstraintMinHeight
            r52 = r3
            int r3 = r7.mMatchConstraintMaxHeight
            r53 = r3
            float r3 = r7.mMatchConstraintPercentHeight
            r54 = r3
            r28 = r73
            r29 = r74
            r31 = r64
            r32 = r66
            r35 = r4
            r37 = r0
            r38 = r6
            r39 = r14
            r40 = r15
            r41 = r1
            r42 = r12
            r44 = r11
            r47 = r57
            r48 = r56
            r49 = r62
            r50 = r58
            r51 = r59
            r55 = r2
            r28.applyConstraints(r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55)
            r29 = r0
            r28 = r1
            r30 = r2
            goto L_0x066c
        L_0x066a:
            r68 = r11
        L_0x066c:
            if (r67 == 0) goto L_0x0692
            r11 = 8
            int r0 = r7.mResolvedDimensionRatioSide
            r1 = 1
            if (r0 != r1) goto L_0x0684
            float r5 = r7.mResolvedDimensionRatio
            r0 = r74
            r1 = r10
            r2 = r9
            r3 = r71
            r4 = r72
            r6 = r11
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            goto L_0x0692
        L_0x0684:
            float r5 = r7.mResolvedDimensionRatio
            r0 = r74
            r1 = r71
            r2 = r72
            r3 = r10
            r4 = r9
            r6 = r11
            r0.addRatio(r1, r2, r3, r4, r5, r6)
        L_0x0692:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x06b8
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.getTarget()
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r0.getOwner()
            float r1 = r7.mCircleConstraintAngle
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r7.mCenter
            int r2 = r2.getMargin()
            r8.addCenterPoint(r7, r0, r1, r2)
        L_0x06b8:
            r0 = 0
            r7.resolvedHorizontal = r0
            r7.resolvedVertical = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.addToSolver(androidx.constraintlayout.core.LinearSystem, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    public void setupDimensionRatio(boolean hParentWrapContent, boolean vParentWrapContent, boolean horizontalDimensionFixed, boolean verticalDimensionFixed) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (horizontalDimensionFixed && !verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            int i = this.mMatchConstraintMinWidth;
            if (i > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (i == 0 && this.mMatchConstraintMinHeight > 0) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:263:0x060b, code lost:
        if ((r3 instanceof androidx.constraintlayout.core.widgets.Barrier) != false) goto L_0x0610;
     */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x0595  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x05dc  */
    /* JADX WARNING: Removed duplicated region for block: B:255:0x05fa A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x05fb  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x06da A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyConstraints(androidx.constraintlayout.core.LinearSystem r38, boolean r39, boolean r40, boolean r41, boolean r42, androidx.constraintlayout.core.SolverVariable r43, androidx.constraintlayout.core.SolverVariable r44, androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour r45, boolean r46, androidx.constraintlayout.core.widgets.ConstraintAnchor r47, androidx.constraintlayout.core.widgets.ConstraintAnchor r48, int r49, int r50, int r51, int r52, float r53, boolean r54, boolean r55, boolean r56, boolean r57, boolean r58, int r59, int r60, int r61, int r62, float r63, boolean r64) {
        /*
            r37 = this;
            r0 = r37
            r10 = r38
            r11 = r43
            r12 = r44
            r13 = r47
            r14 = r48
            r15 = r51
            r9 = r52
            r8 = r60
            r1 = r61
            r2 = r62
            androidx.constraintlayout.core.SolverVariable r7 = r10.createObjectVariable(r13)
            androidx.constraintlayout.core.SolverVariable r6 = r10.createObjectVariable(r14)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r47.getTarget()
            androidx.constraintlayout.core.SolverVariable r5 = r10.createObjectVariable(r3)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r48.getTarget()
            androidx.constraintlayout.core.SolverVariable r4 = r10.createObjectVariable(r3)
            androidx.constraintlayout.core.Metrics r3 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            if (r3 == 0) goto L_0x0040
            androidx.constraintlayout.core.Metrics r3 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            long r11 = r3.nonresolvedWidgets
            r16 = 1
            long r11 = r11 + r16
            r3.nonresolvedWidgets = r11
        L_0x0040:
            boolean r11 = r47.isConnected()
            boolean r12 = r48.isConnected()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.mCenter
            boolean r16 = r3.isConnected()
            r3 = 0
            r17 = 0
            if (r11 == 0) goto L_0x0055
            int r17 = r17 + 1
        L_0x0055:
            if (r12 == 0) goto L_0x0059
            int r17 = r17 + 1
        L_0x0059:
            if (r16 == 0) goto L_0x0060
            int r17 = r17 + 1
            r8 = r17
            goto L_0x0062
        L_0x0060:
            r8 = r17
        L_0x0062:
            if (r54 == 0) goto L_0x0069
            r17 = 3
            r14 = r17
            goto L_0x006b
        L_0x0069:
            r14 = r59
        L_0x006b:
            int[] r17 = androidx.constraintlayout.core.widgets.ConstraintWidget.C06281.f1007x6d00e4a2
            int r18 = r45.ordinal()
            r17 = r17[r18]
            switch(r17) {
                case 1: goto L_0x0083;
                case 2: goto L_0x0081;
                case 3: goto L_0x007f;
                case 4: goto L_0x0077;
                default: goto L_0x0076;
            }
        L_0x0076:
            goto L_0x0085
        L_0x0077:
            r13 = 4
            if (r14 == r13) goto L_0x007c
            r13 = 1
            goto L_0x007d
        L_0x007c:
            r13 = 0
        L_0x007d:
            r3 = r13
            goto L_0x0085
        L_0x007f:
            r3 = 0
            goto L_0x0085
        L_0x0081:
            r3 = 0
            goto L_0x0085
        L_0x0083:
            r3 = 0
        L_0x0085:
            int r13 = r0.mWidthOverride
            r18 = r4
            r4 = -1
            if (r13 == r4) goto L_0x0094
            if (r39 == 0) goto L_0x0094
            r3 = 0
            int r13 = r0.mWidthOverride
            r0.mWidthOverride = r4
            goto L_0x0096
        L_0x0094:
            r13 = r50
        L_0x0096:
            r50 = r3
            int r3 = r0.mHeightOverride
            if (r3 == r4) goto L_0x00a4
            if (r39 != 0) goto L_0x00a4
            r3 = 0
            int r13 = r0.mHeightOverride
            r0.mHeightOverride = r4
            goto L_0x00a6
        L_0x00a4:
            r3 = r50
        L_0x00a6:
            int r4 = r0.mVisibility
            r50 = r13
            r13 = 8
            if (r4 != r13) goto L_0x00b3
            r4 = 0
            r3 = 0
            r20 = r3
            goto L_0x00b7
        L_0x00b3:
            r4 = r50
            r20 = r3
        L_0x00b7:
            if (r64 == 0) goto L_0x00d2
            if (r11 != 0) goto L_0x00c5
            if (r12 != 0) goto L_0x00c5
            if (r16 != 0) goto L_0x00c5
            r3 = r49
            r10.addEquality(r7, r3)
            goto L_0x00d2
        L_0x00c5:
            r3 = r49
            if (r11 == 0) goto L_0x00d2
            if (r12 != 0) goto L_0x00d2
            int r3 = r47.getMargin()
            r10.addEquality(r7, r5, r3, r13)
        L_0x00d2:
            r3 = 3
            if (r20 != 0) goto L_0x0104
            if (r46 == 0) goto L_0x00ee
            r13 = 0
            r10.addEquality(r6, r7, r13, r3)
            if (r15 <= 0) goto L_0x00e3
            r3 = 8
            r10.addGreaterThan(r6, r7, r15, r3)
            goto L_0x00e5
        L_0x00e3:
            r3 = 8
        L_0x00e5:
            r13 = 2147483647(0x7fffffff, float:NaN)
            if (r9 >= r13) goto L_0x00f3
            r10.addLowerThan(r6, r7, r9, r3)
            goto L_0x00f3
        L_0x00ee:
            r3 = 8
            r10.addEquality(r6, r7, r4, r3)
        L_0x00f3:
            r13 = r42
            r26 = r2
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r1
            r8 = r6
            goto L_0x0228
        L_0x0104:
            r3 = 2
            if (r8 == r3) goto L_0x0130
            if (r54 != 0) goto L_0x0130
            r3 = 1
            if (r14 == r3) goto L_0x010e
            if (r14 != 0) goto L_0x0130
        L_0x010e:
            r20 = 0
            int r3 = java.lang.Math.max(r1, r4)
            if (r2 <= 0) goto L_0x011a
            int r3 = java.lang.Math.min(r2, r3)
        L_0x011a:
            r13 = 8
            r10.addEquality(r6, r7, r3, r13)
            r13 = r42
            r26 = r2
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r1
            r8 = r6
            goto L_0x0228
        L_0x0130:
            r3 = -2
            if (r1 != r3) goto L_0x0136
            r1 = r4
            r13 = r1
            goto L_0x0137
        L_0x0136:
            r13 = r1
        L_0x0137:
            if (r2 != r3) goto L_0x013c
            r1 = r4
            r3 = r1
            goto L_0x013d
        L_0x013c:
            r3 = r2
        L_0x013d:
            if (r4 <= 0) goto L_0x0143
            r1 = 1
            if (r14 == r1) goto L_0x0143
            r4 = 0
        L_0x0143:
            if (r13 <= 0) goto L_0x014e
            r1 = 8
            r10.addGreaterThan(r6, r7, r13, r1)
            int r4 = java.lang.Math.max(r4, r13)
        L_0x014e:
            if (r3 <= 0) goto L_0x0163
            r1 = 1
            if (r40 == 0) goto L_0x0157
            r2 = 1
            if (r14 != r2) goto L_0x0157
            r1 = 0
        L_0x0157:
            if (r1 == 0) goto L_0x015e
            r2 = 8
            r10.addLowerThan(r6, r7, r3, r2)
        L_0x015e:
            int r2 = java.lang.Math.min(r4, r3)
            r4 = r2
        L_0x0163:
            r1 = 1
            if (r14 != r1) goto L_0x0193
            if (r40 == 0) goto L_0x016f
            r1 = 8
            r10.addEquality(r6, r7, r4, r1)
            r2 = 5
            goto L_0x0182
        L_0x016f:
            r1 = 8
            if (r56 == 0) goto L_0x017b
            r2 = 5
            r10.addEquality(r6, r7, r4, r2)
            r10.addLowerThan(r6, r7, r4, r1)
            goto L_0x0182
        L_0x017b:
            r2 = 5
            r10.addEquality(r6, r7, r4, r2)
            r10.addLowerThan(r6, r7, r4, r1)
        L_0x0182:
            r26 = r3
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r8 = r6
            r18 = r13
            r13 = r42
            goto L_0x0228
        L_0x0193:
            r2 = 5
            r1 = 2
            if (r14 != r1) goto L_0x0217
            r1 = 0
            r24 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = r47.getType()
            r61 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            if (r2 == r1) goto L_0x01cc
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r1 = r47.getType()
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            if (r1 != r2) goto L_0x01ad
            goto L_0x01cc
        L_0x01ad:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.getAnchor(r2)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            r61 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r2.getAnchor(r1)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            r25 = r61
            r24 = r1
            goto L_0x01ea
        L_0x01cc:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.getAnchor(r2)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            r61 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r2.getAnchor(r1)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            r25 = r61
            r24 = r1
        L_0x01ea:
            androidx.constraintlayout.core.ArrayRow r1 = r38.createRow()
            r26 = 5
            r2 = r6
            r26 = r3
            r27 = r8
            r8 = 5
            r3 = r7
            r9 = r18
            r18 = r4
            r4 = r24
            r30 = r5
            r5 = r25
            r8 = r6
            r6 = r63
            androidx.constraintlayout.core.ArrayRow r1 = r1.createRowDimensionRatio(r2, r3, r4, r5, r6)
            r10.addConstraint(r1)
            if (r40 == 0) goto L_0x0210
            r1 = 0
            r20 = r1
        L_0x0210:
            r19 = r18
            r18 = r13
            r13 = r42
            goto L_0x0228
        L_0x0217:
            r26 = r3
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r4
            r8 = r6
            r1 = 1
            r19 = r18
            r18 = r13
            r13 = r1
        L_0x0228:
            if (r64 == 0) goto L_0x070c
            if (r56 == 0) goto L_0x0244
            r4 = r44
            r3 = r48
            r1 = r0
            r6 = r7
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r7 = r30
            r2 = 0
            r11 = r43
            r12 = r9
            goto L_0x0722
        L_0x0244:
            r6 = 5
            if (r11 != 0) goto L_0x025f
            if (r12 != 0) goto L_0x025f
            if (r16 != 0) goto L_0x025f
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d6
        L_0x025f:
            if (r11 == 0) goto L_0x0287
            if (r12 != 0) goto L_0x0287
            r5 = r47
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.mOwner
            if (r40 == 0) goto L_0x0271
            boolean r2 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 == 0) goto L_0x0271
            r6 = 8
        L_0x0271:
            r23 = r40
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d8
        L_0x0287:
            r5 = r47
            r4 = 0
            if (r11 != 0) goto L_0x02f3
            if (r12 == 0) goto L_0x02f3
            int r1 = r48.getMargin()
            int r1 = -r1
            r2 = 8
            r10.addEquality(r8, r9, r1, r2)
            if (r40 == 0) goto L_0x02de
            boolean r1 = r0.OPTIMIZE_WRAP
            if (r1 == 0) goto L_0x02c5
            boolean r1 = r7.isFinalValue
            if (r1 == 0) goto L_0x02c5
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mParent
            if (r1 == 0) goto L_0x02c5
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r1 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r1
            if (r39 == 0) goto L_0x02ae
            r1.addHorizontalWrapMinVariable(r5)
            goto L_0x02b1
        L_0x02ae:
            r1.addVerticalWrapMinVariable(r5)
        L_0x02b1:
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d6
        L_0x02c5:
            r3 = r43
            r1 = 5
            r10.addGreaterThan(r7, r3, r4, r1)
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d6
        L_0x02de:
            r3 = r43
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d6
        L_0x02f3:
            r3 = r43
            if (r11 == 0) goto L_0x06c5
            if (r12 == 0) goto L_0x06c5
            r23 = 1
            r1 = 0
            r24 = 0
            r2 = 0
            r25 = 5
            r31 = 4
            r32 = 6
            r25 = 5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r4.mOwner
            r42 = r1
            r33 = r11
            r11 = r14
            r14 = r48
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r14.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.mOwner
            r34 = r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r12 = r37.getParent()
            if (r20 == 0) goto L_0x0516
            if (r11 != 0) goto L_0x039b
            if (r26 != 0) goto L_0x0352
            if (r18 != 0) goto L_0x0352
            r24 = 1
            r17 = 8
            r22 = 8
            r50 = r2
            r15 = r30
            boolean r2 = r15.isFinalValue
            if (r2 == 0) goto L_0x0349
            boolean r2 = r9.isFinalValue
            if (r2 == 0) goto L_0x0349
            int r2 = r47.getMargin()
            r3 = 8
            r10.addEquality(r7, r15, r2, r3)
            int r2 = r48.getMargin()
            int r2 = -r2
            r10.addEquality(r8, r9, r2, r3)
            return
        L_0x0349:
            r2 = r42
            r25 = r17
            r31 = r22
            r22 = r50
            goto L_0x0362
        L_0x0352:
            r50 = r2
            r15 = r30
            r2 = 1
            r3 = 5
            r17 = 5
            r23 = 1
            r22 = 1
            r25 = r3
            r31 = r17
        L_0x0362:
            boolean r3 = r4 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 != 0) goto L_0x0382
            boolean r3 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 == 0) goto L_0x036b
            goto L_0x0382
        L_0x036b:
            r36 = r1
            r17 = r2
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r2 = r22
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0382:
            r31 = 4
            r36 = r1
            r17 = r2
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r2 = r22
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x039b:
            r50 = r2
            r15 = r30
            r2 = 2
            if (r11 != r2) goto L_0x03e3
            r2 = 1
            r25 = 5
            r31 = 5
            r23 = 1
            r3 = 1
            r42 = r2
            boolean r2 = r4 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 != 0) goto L_0x03cb
            boolean r2 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 == 0) goto L_0x03b5
            goto L_0x03cb
        L_0x03b5:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x03cb:
            r31 = 4
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x03e3:
            r2 = 1
            if (r11 != r2) goto L_0x0400
            r2 = 1
            r3 = 1
            r25 = 8
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0400:
            r3 = 3
            if (r11 != r3) goto L_0x04fd
            int r2 = r0.mResolvedDimensionRatioSide
            r3 = -1
            if (r2 != r3) goto L_0x045e
            r2 = 1
            r3 = 1
            r24 = 1
            r25 = 8
            r31 = 5
            if (r57 == 0) goto L_0x0446
            r31 = 5
            r32 = 4
            if (r40 == 0) goto L_0x0430
            r32 = 5
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0430:
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0446:
            r32 = 8
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x045e:
            r2 = 1
            r3 = 1
            r24 = 1
            if (r54 == 0) goto L_0x0492
            r42 = r2
            r61 = r11
            r35 = r27
            r2 = 2
            r11 = r60
            if (r11 == r2) goto L_0x0476
            r2 = 1
            if (r11 != r2) goto L_0x0473
            goto L_0x0476
        L_0x0473:
            r17 = 0
            goto L_0x0478
        L_0x0476:
            r17 = 1
        L_0x0478:
            r2 = r17
            if (r2 != 0) goto L_0x0480
            r25 = 8
            r31 = 5
        L_0x0480:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0492:
            r42 = r2
            r61 = r11
            r35 = r27
            r11 = r60
            r25 = 5
            if (r26 <= 0) goto L_0x04b2
            r31 = 5
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x04b2:
            if (r26 != 0) goto L_0x04eb
            if (r18 != 0) goto L_0x04eb
            if (r57 != 0) goto L_0x04cc
            r31 = 8
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x04cc:
            if (r4 == r12) goto L_0x04d4
            if (r1 == r12) goto L_0x04d4
            r2 = 4
            r25 = r2
            goto L_0x04d7
        L_0x04d4:
            r2 = 5
            r25 = r2
        L_0x04d7:
            r31 = 4
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x04eb:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x04fd:
            r61 = r11
            r35 = r27
            r11 = r60
            r17 = r42
            r2 = r50
            r36 = r1
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057d
        L_0x0516:
            r50 = r2
            r61 = r11
            r35 = r27
            r15 = r30
            r11 = r60
            r17 = 1
            r22 = 1
            boolean r2 = r15.isFinalValue
            if (r2 == 0) goto L_0x056e
            boolean r2 = r9.isFinalValue
            if (r2 == 0) goto L_0x056e
            int r21 = r47.getMargin()
            int r27 = r48.getMargin()
            r28 = 8
            r3 = r1
            r1 = r38
            r2 = r7
            r11 = r43
            r36 = r3
            r3 = r15
            r0 = r4
            r4 = r21
            r5 = r53
            r11 = r6
            r6 = r9
            r42 = r7
            r7 = r8
            r29 = r0
            r0 = r8
            r8 = r27
            r50 = r12
            r12 = r9
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            if (r40 == 0) goto L_0x056b
            if (r13 == 0) goto L_0x056b
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r14.mTarget
            if (r2 == 0) goto L_0x0563
            int r1 = r48.getMargin()
        L_0x0563:
            r9 = r44
            if (r12 == r9) goto L_0x056d
            r10.addGreaterThan(r9, r0, r1, r11)
            goto L_0x056d
        L_0x056b:
            r9 = r44
        L_0x056d:
            return
        L_0x056e:
            r36 = r1
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            r2 = r22
        L_0x057d:
            if (r2 == 0) goto L_0x058d
            if (r15 != r12) goto L_0x058d
            r7 = r50
            r8 = r29
            if (r8 == r7) goto L_0x0591
            r2 = 0
            r23 = 0
            r22 = r2
            goto L_0x0593
        L_0x058d:
            r7 = r50
            r8 = r29
        L_0x0591:
            r22 = r2
        L_0x0593:
            if (r17 == 0) goto L_0x05dc
            if (r20 != 0) goto L_0x05b1
            if (r55 != 0) goto L_0x05b1
            if (r57 != 0) goto L_0x05b1
            r6 = r11
            r11 = r43
            if (r15 != r11) goto L_0x05b4
            if (r12 != r9) goto L_0x05b4
            r1 = 8
            r2 = 8
            r3 = 0
            r4 = 0
            r32 = r1
            r27 = r2
            r25 = r3
            r23 = r4
            goto L_0x05ba
        L_0x05b1:
            r6 = r11
            r11 = r43
        L_0x05b4:
            r27 = r25
            r25 = r23
            r23 = r40
        L_0x05ba:
            int r4 = r47.getMargin()
            int r29 = r48.getMargin()
            r1 = r38
            r2 = r42
            r5 = 3
            r3 = r15
            r14 = 0
            r14 = 3
            r5 = r53
            r14 = r6
            r6 = r12
            r50 = r13
            r13 = r7
            r7 = r0
            r30 = r8
            r8 = r29
            r9 = r32
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x05ea
        L_0x05dc:
            r30 = r8
            r14 = r11
            r50 = r13
            r11 = r43
            r13 = r7
            r27 = r25
            r25 = r23
            r23 = r40
        L_0x05ea:
            r1 = r37
            r2 = r30
            int r3 = r1.mVisibility
            r4 = 8
            if (r3 != r4) goto L_0x05fb
            boolean r3 = r48.hasDependents()
            if (r3 != 0) goto L_0x05fb
            return
        L_0x05fb:
            if (r22 == 0) goto L_0x062a
            if (r23 == 0) goto L_0x0612
            if (r15 == r12) goto L_0x0612
            if (r20 != 0) goto L_0x0612
            boolean r3 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 != 0) goto L_0x060e
            r3 = r36
            boolean r4 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 == 0) goto L_0x0614
            goto L_0x0610
        L_0x060e:
            r3 = r36
        L_0x0610:
            r4 = 6
            goto L_0x0616
        L_0x0612:
            r3 = r36
        L_0x0614:
            r4 = r27
        L_0x0616:
            int r5 = r47.getMargin()
            r6 = r42
            r10.addGreaterThan(r6, r15, r5, r4)
            int r5 = r48.getMargin()
            int r5 = -r5
            r10.addLowerThan(r0, r12, r5, r4)
            r27 = r4
            goto L_0x062e
        L_0x062a:
            r6 = r42
            r3 = r36
        L_0x062e:
            if (r23 == 0) goto L_0x0647
            if (r58 == 0) goto L_0x0647
            boolean r4 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x0647
            boolean r4 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x0647
            if (r3 == r13) goto L_0x0647
            r31 = 6
            r27 = 6
            r25 = 1
            r4 = r27
            r5 = r31
            goto L_0x064b
        L_0x0647:
            r4 = r27
            r5 = r31
        L_0x064b:
            if (r25 == 0) goto L_0x0690
            if (r24 == 0) goto L_0x0672
            if (r57 == 0) goto L_0x0653
            if (r41 == 0) goto L_0x0672
        L_0x0653:
            r7 = r5
            if (r2 == r13) goto L_0x0658
            if (r3 != r13) goto L_0x0659
        L_0x0658:
            r7 = 6
        L_0x0659:
            boolean r8 = r2 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r8 != 0) goto L_0x0661
            boolean r8 = r3 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r8 == 0) goto L_0x0662
        L_0x0661:
            r7 = 5
        L_0x0662:
            boolean r8 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 != 0) goto L_0x066a
            boolean r8 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 == 0) goto L_0x066b
        L_0x066a:
            r7 = 5
        L_0x066b:
            if (r57 == 0) goto L_0x066e
            r7 = 5
        L_0x066e:
            int r5 = java.lang.Math.max(r7, r5)
        L_0x0672:
            if (r23 == 0) goto L_0x0681
            int r5 = java.lang.Math.min(r4, r5)
            if (r54 == 0) goto L_0x0681
            if (r57 != 0) goto L_0x0681
            if (r2 == r13) goto L_0x0680
            if (r3 != r13) goto L_0x0681
        L_0x0680:
            r5 = 4
        L_0x0681:
            int r7 = r47.getMargin()
            r10.addEquality(r6, r15, r7, r5)
            int r7 = r48.getMargin()
            int r7 = -r7
            r10.addEquality(r0, r12, r7, r5)
        L_0x0690:
            if (r23 == 0) goto L_0x069e
            r7 = 0
            if (r11 != r15) goto L_0x0699
            int r7 = r47.getMargin()
        L_0x0699:
            if (r15 == r11) goto L_0x069e
            r10.addGreaterThan(r6, r11, r7, r14)
        L_0x069e:
            if (r23 == 0) goto L_0x06c1
            if (r20 == 0) goto L_0x06c1
            r7 = r15
            if (r51 != 0) goto L_0x06be
            if (r18 != 0) goto L_0x06be
            if (r20 == 0) goto L_0x06b7
            r8 = r61
            r9 = 3
            if (r8 != r9) goto L_0x06b5
            r9 = 0
            r15 = 8
            r10.addGreaterThan(r0, r6, r9, r15)
            goto L_0x06d8
        L_0x06b5:
            r9 = 0
            goto L_0x06ba
        L_0x06b7:
            r8 = r61
            r9 = 0
        L_0x06ba:
            r10.addGreaterThan(r0, r6, r9, r14)
            goto L_0x06d8
        L_0x06be:
            r8 = r61
            goto L_0x06d8
        L_0x06c1:
            r8 = r61
            r7 = r15
            goto L_0x06d8
        L_0x06c5:
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
        L_0x06d6:
            r23 = r40
        L_0x06d8:
            if (r23 == 0) goto L_0x0707
            if (r50 == 0) goto L_0x0707
            r2 = 0
            r3 = r48
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r3.mTarget
            if (r4 == 0) goto L_0x06e7
            int r2 = r48.getMargin()
        L_0x06e7:
            r4 = r44
            if (r12 == r4) goto L_0x070b
            boolean r5 = r1.OPTIMIZE_WRAP
            if (r5 == 0) goto L_0x0703
            boolean r5 = r0.isFinalValue
            if (r5 == 0) goto L_0x0703
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r1.mParent
            if (r5 == 0) goto L_0x0703
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r5 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r5
            if (r39 == 0) goto L_0x06ff
            r5.addHorizontalWrapMaxVariable(r3)
            goto L_0x0702
        L_0x06ff:
            r5.addVerticalWrapMaxVariable(r3)
        L_0x0702:
            return
        L_0x0703:
            r10.addGreaterThan(r4, r0, r2, r14)
            goto L_0x070b
        L_0x0707:
            r4 = r44
            r3 = r48
        L_0x070b:
            return
        L_0x070c:
            r4 = r44
            r3 = r48
            r1 = r0
            r6 = r7
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r7 = r30
            r2 = 0
            r11 = r43
            r12 = r9
        L_0x0722:
            r5 = r35
            r9 = 2
            if (r5 >= r9) goto L_0x076d
            if (r40 == 0) goto L_0x076d
            if (r50 == 0) goto L_0x076d
            r9 = 8
            r10.addGreaterThan(r6, r11, r2, r9)
            if (r39 != 0) goto L_0x073b
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r1.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 != 0) goto L_0x0739
            goto L_0x073b
        L_0x0739:
            r13 = 0
            goto L_0x073c
        L_0x073b:
            r13 = 1
        L_0x073c:
            r9 = r13
            if (r39 != 0) goto L_0x0766
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r1.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0766
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r1.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r13.mOwner
            float r14 = r13.mDimensionRatio
            r15 = 0
            int r14 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r14 == 0) goto L_0x0765
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r14 = r13.mListDimensionBehaviors
            r14 = r14[r2]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 != r15) goto L_0x0765
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r14 = r13.mListDimensionBehaviors
            r15 = 1
            r14 = r14[r15]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 != r15) goto L_0x0765
            r9 = 1
            goto L_0x0766
        L_0x0765:
            r9 = 0
        L_0x0766:
            if (r9 == 0) goto L_0x076d
            r13 = 8
            r10.addGreaterThan(r4, r0, r2, r13)
        L_0x076d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.applyConstraints(androidx.constraintlayout.core.LinearSystem, boolean, boolean, boolean, boolean, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour, boolean, androidx.constraintlayout.core.widgets.ConstraintAnchor, androidx.constraintlayout.core.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, boolean, boolean, boolean, int, int, int, int, float, boolean):void");
    }

    /* renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1 */
    static /* synthetic */ class C06281 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        static final /* synthetic */ int[] f1006x6930e354;

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour */
        static final /* synthetic */ int[] f1007x6d00e4a2;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            f1007x6d00e4a2 = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1007x6d00e4a2[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1007x6d00e4a2[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1007x6d00e4a2[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            f1006x6930e354 = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1006x6930e354[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    public void updateFromSolver(LinearSystem system, boolean optimize) {
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int left = system.getObjectVariableValue(this.mLeft);
        int top = system.getObjectVariableValue(this.mTop);
        int right = system.getObjectVariableValue(this.mRight);
        int bottom = system.getObjectVariableValue(this.mBottom);
        if (optimize && (horizontalWidgetRun = this.horizontalRun) != null && horizontalWidgetRun.start.resolved && this.horizontalRun.end.resolved) {
            left = this.horizontalRun.start.value;
            right = this.horizontalRun.end.value;
        }
        if (optimize && (verticalWidgetRun = this.verticalRun) != null && verticalWidgetRun.start.resolved && this.verticalRun.end.resolved) {
            top = this.verticalRun.start.value;
            bottom = this.verticalRun.end.value;
        }
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        setFrame(left, top, right, bottom);
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        this.mHorizontalResolution = src.mHorizontalResolution;
        this.mVerticalResolution = src.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = src.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = src.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = src.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = src.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = src.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = src.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = src.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = src.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = src.mIsWidthWrapContent;
        this.mIsHeightWrapContent = src.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = src.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = src.mResolvedDimensionRatio;
        int[] iArr3 = src.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = src.mCircleConstraintAngle;
        this.hasBaseline = src.hasBaseline;
        this.inPlaceholder = src.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        ConstraintWidget constraintWidget = null;
        this.mParent = this.mParent == null ? null : map.get(src.mParent);
        this.mWidth = src.mWidth;
        this.mHeight = src.mHeight;
        this.mDimensionRatio = src.mDimensionRatio;
        this.mDimensionRatioSide = src.mDimensionRatioSide;
        this.f1004mX = src.f1004mX;
        this.f1005mY = src.f1005mY;
        this.mRelX = src.mRelX;
        this.mRelY = src.mRelY;
        this.mOffsetX = src.mOffsetX;
        this.mOffsetY = src.mOffsetY;
        this.mBaselineDistance = src.mBaselineDistance;
        this.mMinWidth = src.mMinWidth;
        this.mMinHeight = src.mMinHeight;
        this.mHorizontalBiasPercent = src.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = src.mVerticalBiasPercent;
        this.mCompanionWidget = src.mCompanionWidget;
        this.mContainerItemSkip = src.mContainerItemSkip;
        this.mVisibility = src.mVisibility;
        this.mAnimated = src.mAnimated;
        this.mDebugName = src.mDebugName;
        this.mType = src.mType;
        this.mDistToTop = src.mDistToTop;
        this.mDistToLeft = src.mDistToLeft;
        this.mDistToRight = src.mDistToRight;
        this.mDistToBottom = src.mDistToBottom;
        this.mLeftHasCentered = src.mLeftHasCentered;
        this.mRightHasCentered = src.mRightHasCentered;
        this.mTopHasCentered = src.mTopHasCentered;
        this.mBottomHasCentered = src.mBottomHasCentered;
        this.mHorizontalWrapVisited = src.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = src.mVerticalWrapVisited;
        this.mHorizontalChainStyle = src.mHorizontalChainStyle;
        this.mVerticalChainStyle = src.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = src.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = src.mVerticalChainFixedPosition;
        float[] fArr = this.mWeight;
        float[] fArr2 = src.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = src.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = src.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = src.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : map.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = src.mVerticalNextWidget;
        if (constraintWidget3 != null) {
            constraintWidget = map.get(constraintWidget3);
        }
        this.mVerticalNextWidget = constraintWidget;
    }

    public void updateFromRuns(boolean updateHorizontal, boolean updateVertical) {
        boolean updateHorizontal2 = updateHorizontal & this.horizontalRun.isResolved();
        boolean updateVertical2 = updateVertical & this.verticalRun.isResolved();
        int left = this.horizontalRun.start.value;
        int top = this.verticalRun.start.value;
        int right = this.horizontalRun.end.value;
        int bottom = this.verticalRun.end.value;
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        int w = right - left;
        int h2 = bottom - top;
        if (updateHorizontal2) {
            this.f1004mX = left;
        }
        if (updateVertical2) {
            this.f1005mY = top;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (updateHorizontal2) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
                w = this.mWidth;
            }
            this.mWidth = w;
            int i = this.mMinWidth;
            if (w < i) {
                this.mWidth = i;
            }
        }
        if (updateVertical2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h2 < this.mHeight) {
                h2 = this.mHeight;
            }
            this.mHeight = h2;
            int i2 = this.mMinHeight;
            if (h2 < i2) {
                this.mHeight = i2;
            }
        }
    }

    public void addChildrenToSolverByDependency(ConstraintWidgetContainer container, LinearSystem system, HashSet<ConstraintWidget> widgets, int orientation, boolean addSelf) {
        if (addSelf) {
            if (widgets.contains(this)) {
                Optimizer.checkMatchParent(container, system, this);
                widgets.remove(this);
                addToSolver(system, container.optimizeFor(64));
            } else {
                return;
            }
        }
        if (orientation == 0) {
            HashSet<ConstraintAnchor> dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator<ConstraintAnchor> it = dependents.iterator();
                while (it.hasNext()) {
                    it.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
            }
            HashSet<ConstraintAnchor> dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator<ConstraintAnchor> it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    it2.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
                return;
            }
            return;
        }
        HashSet<ConstraintAnchor> dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator<ConstraintAnchor> it3 = dependents3.iterator();
            while (it3.hasNext()) {
                it3.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator<ConstraintAnchor> it4 = dependents4.iterator();
            while (it4.hasNext()) {
                it4.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator<ConstraintAnchor> it5 = dependents5.iterator();
            while (it5.hasNext()) {
                it5.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
    }

    public void getSceneString(StringBuilder ret) {
        ret.append("  " + this.stringId + ":{\n");
        ret.append("    actualWidth:" + this.mWidth);
        ret.append("\n");
        ret.append("    actualHeight:" + this.mHeight);
        ret.append("\n");
        ret.append("    actualLeft:" + this.f1004mX);
        ret.append("\n");
        ret.append("    actualTop:" + this.f1005mY);
        ret.append("\n");
        getSceneString(ret, "left", this.mLeft);
        getSceneString(ret, CommonCssConstants.TOP, this.mTop);
        getSceneString(ret, "right", this.mRight);
        getSceneString(ret, CommonCssConstants.BOTTOM, this.mBottom);
        getSceneString(ret, "baseline", this.mBaseline);
        getSceneString(ret, "centerX", this.mCenterX);
        getSceneString(ret, "centerY", this.mCenterY);
        getSceneString(ret, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        getSceneString(ret, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(ret, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
        serializeAttribute(ret, "    verticalChainStyle", this.mVerticalChainStyle, 0);
        ret.append("  }");
    }

    private void getSceneString(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float MatchConstraintPercent, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "      size", size, 0);
        serializeAttribute(ret, "      min", min, 0);
        serializeAttribute(ret, "      max", max, Integer.MAX_VALUE);
        serializeAttribute(ret, "      matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "      matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "      matchPercent", MatchConstraintPercent, 1.0f);
        ret.append("    },\n");
    }

    private void getSceneString(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append("    ");
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("'");
            if (!(a.mGoneMargin == Integer.MIN_VALUE && a.mMargin == 0)) {
                ret.append(",");
                ret.append(a.mMargin);
                if (a.mGoneMargin != Integer.MIN_VALUE) {
                    ret.append(",");
                    ret.append(a.mGoneMargin);
                    ret.append(",");
                }
            }
            ret.append(" ] ,\n");
        }
    }
}

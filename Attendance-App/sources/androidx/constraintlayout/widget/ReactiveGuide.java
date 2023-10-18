package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.SharedValues;

public class ReactiveGuide extends View implements SharedValues.SharedValuesListener {
    private boolean mAnimateChange = false;
    private boolean mApplyToAllConstraintSets = true;
    private int mApplyToConstraintSetId = 0;
    private int mAttributeId = -1;

    public ReactiveGuide(Context context) {
        super(context);
        super.setVisibility(8);
        init((AttributeSet) null);
    }

    public ReactiveGuide(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setVisibility(8);
        init(attrs);
    }

    public ReactiveGuide(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setVisibility(8);
        init(attrs);
    }

    public ReactiveGuide(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        super.setVisibility(8);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0657R.styleable.ConstraintLayout_ReactiveGuide);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_valueId) {
                    this.mAttributeId = a.getResourceId(attr, this.mAttributeId);
                } else if (attr == C0657R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_animateChange) {
                    this.mAnimateChange = a.getBoolean(attr, this.mAnimateChange);
                } else if (attr == C0657R.styleable.f1028x2694048c) {
                    this.mApplyToConstraintSetId = a.getResourceId(attr, this.mApplyToConstraintSetId);
                } else if (attr == C0657R.styleable.f1027xfdeff96) {
                    this.mApplyToAllConstraintSets = a.getBoolean(attr, this.mApplyToAllConstraintSets);
                }
            }
            a.recycle();
        }
        if (this.mAttributeId != -1) {
            ConstraintLayout.getSharedValues().addListener(this.mAttributeId, this);
        }
    }

    public int getAttributeId() {
        return this.mAttributeId;
    }

    public void setAttributeId(int id) {
        SharedValues sharedValues = ConstraintLayout.getSharedValues();
        int i = this.mAttributeId;
        if (i != -1) {
            sharedValues.removeListener(i, this);
        }
        this.mAttributeId = id;
        if (id != -1) {
            sharedValues.addListener(id, this);
        }
    }

    public int getApplyToConstraintSetId() {
        return this.mApplyToConstraintSetId;
    }

    public void setApplyToConstraintSetId(int id) {
        this.mApplyToConstraintSetId = id;
    }

    public boolean isAnimatingChange() {
        return this.mAnimateChange;
    }

    public void setAnimateChange(boolean animate) {
        this.mAnimateChange = animate;
    }

    public void setVisibility(int visibility) {
    }

    public void draw(Canvas canvas) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    public void setGuidelineBegin(int margin) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
        params.guideBegin = margin;
        setLayoutParams(params);
    }

    public void setGuidelineEnd(int margin) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
        params.guideEnd = margin;
        setLayoutParams(params);
    }

    public void setGuidelinePercent(float ratio) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
        params.guidePercent = ratio;
        setLayoutParams(params);
    }

    public void onNewValue(int key, int newValue, int oldValue) {
        setGuidelineBegin(newValue);
        int id = getId();
        if (id > 0 && (getParent() instanceof MotionLayout)) {
            MotionLayout motionLayout = (MotionLayout) getParent();
            int currentState = motionLayout.getCurrentState();
            if (this.mApplyToConstraintSetId != 0) {
                currentState = this.mApplyToConstraintSetId;
            }
            if (this.mAnimateChange) {
                if (this.mApplyToAllConstraintSets) {
                    int[] ids = motionLayout.getConstraintSetIds();
                    for (int cs : ids) {
                        if (cs != currentState) {
                            changeValue(newValue, id, motionLayout, cs);
                        }
                    }
                }
                ConstraintSet constraintSet = motionLayout.cloneConstraintSet(currentState);
                constraintSet.setGuidelineEnd(id, newValue);
                motionLayout.updateStateAnimate(currentState, constraintSet, 1000);
            } else if (this.mApplyToAllConstraintSets) {
                int[] ids2 = motionLayout.getConstraintSetIds();
                for (int cs2 : ids2) {
                    changeValue(newValue, id, motionLayout, cs2);
                }
            } else {
                changeValue(newValue, id, motionLayout, currentState);
            }
        }
    }

    private void changeValue(int newValue, int id, MotionLayout motionLayout, int currentState) {
        ConstraintSet constraintSet = motionLayout.getConstraintSet(currentState);
        constraintSet.setGuidelineEnd(id, newValue);
        motionLayout.updateState(currentState, constraintSet);
    }
}

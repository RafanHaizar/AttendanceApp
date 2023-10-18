package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.Reference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;

public class GuidelineReference implements Facade, Reference {
    private Object key;
    private int mEnd = -1;
    private Guideline mGuidelineWidget;
    private int mOrientation;
    private float mPercent = 0.0f;
    private int mStart = -1;
    final State mState;

    public void setKey(Object key2) {
        this.key = key2;
    }

    public Object getKey() {
        return this.key;
    }

    public GuidelineReference(State state) {
        this.mState = state;
    }

    public GuidelineReference start(Object margin) {
        this.mStart = this.mState.convertDimension(margin);
        this.mEnd = -1;
        this.mPercent = 0.0f;
        return this;
    }

    public GuidelineReference end(Object margin) {
        this.mStart = -1;
        this.mEnd = this.mState.convertDimension(margin);
        this.mPercent = 0.0f;
        return this;
    }

    public GuidelineReference percent(float percent) {
        this.mStart = -1;
        this.mEnd = -1;
        this.mPercent = percent;
        return this;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void apply() {
        this.mGuidelineWidget.setOrientation(this.mOrientation);
        int i = this.mStart;
        if (i != -1) {
            this.mGuidelineWidget.setGuideBegin(i);
            return;
        }
        int i2 = this.mEnd;
        if (i2 != -1) {
            this.mGuidelineWidget.setGuideEnd(i2);
        } else {
            this.mGuidelineWidget.setGuidePercent(this.mPercent);
        }
    }

    public Facade getFacade() {
        return null;
    }

    public ConstraintWidget getConstraintWidget() {
        if (this.mGuidelineWidget == null) {
            this.mGuidelineWidget = new Guideline();
        }
        return this.mGuidelineWidget;
    }

    public void setConstraintWidget(ConstraintWidget widget) {
        if (widget instanceof Guideline) {
            this.mGuidelineWidget = (Guideline) widget;
        } else {
            this.mGuidelineWidget = null;
        }
    }
}

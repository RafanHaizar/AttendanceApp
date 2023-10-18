package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Group extends ConstraintHelper {
    public Group(Context context) {
        super(context);
    }

    public Group(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Group(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(AttributeSet attrs) {
        super.init(attrs);
        this.mUseViewMeasure = false;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        applyLayoutFeatures();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        applyLayoutFeatures();
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        applyLayoutFeatures();
    }

    /* access modifiers changed from: protected */
    public void applyLayoutFeaturesInConstraintSet(ConstraintLayout container) {
        applyLayoutFeatures(container);
    }

    public void updatePostLayout(ConstraintLayout container) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
        params.widget.setWidth(0);
        params.widget.setHeight(0);
    }
}

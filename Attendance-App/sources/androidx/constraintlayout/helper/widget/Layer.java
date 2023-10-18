package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.C0657R;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Layer extends ConstraintHelper {
    private static final String TAG = "Layer";
    private boolean mApplyElevationOnAttach;
    private boolean mApplyVisibilityOnAttach;
    protected float mComputedCenterX = Float.NaN;
    protected float mComputedCenterY = Float.NaN;
    protected float mComputedMaxX = Float.NaN;
    protected float mComputedMaxY = Float.NaN;
    protected float mComputedMinX = Float.NaN;
    protected float mComputedMinY = Float.NaN;
    ConstraintLayout mContainer;
    private float mGroupRotateAngle = Float.NaN;
    boolean mNeedBounds = true;
    private float mRotationCenterX = Float.NaN;
    private float mRotationCenterY = Float.NaN;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mShiftX = 0.0f;
    private float mShiftY = 0.0f;
    View[] mViews = null;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Layer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(AttributeSet attrs) {
        super.init(attrs);
        this.mUseViewMeasure = false;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0657R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.ConstraintLayout_Layout_android_visibility) {
                    this.mApplyVisibilityOnAttach = true;
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_android_elevation) {
                    this.mApplyElevationOnAttach = true;
                }
            }
            a.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ConstraintLayout) getParent();
        if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
            int visibility = getVisibility();
            float elevation = 0.0f;
            if (Build.VERSION.SDK_INT >= 21) {
                elevation = getElevation();
            }
            for (int i = 0; i < this.mCount; i++) {
                View view = this.mContainer.getViewById(this.mIds[i]);
                if (view != null) {
                    if (this.mApplyVisibilityOnAttach) {
                        view.setVisibility(visibility);
                    }
                    if (this.mApplyElevationOnAttach && elevation > 0.0f && Build.VERSION.SDK_INT >= 21) {
                        view.setTranslationZ(view.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    public void updatePreDraw(ConstraintLayout container) {
        this.mContainer = container;
        float rotate = getRotation();
        if (rotate != 0.0f) {
            this.mGroupRotateAngle = rotate;
        } else if (!Float.isNaN(this.mGroupRotateAngle)) {
            this.mGroupRotateAngle = rotate;
        }
    }

    public void setRotation(float angle) {
        this.mGroupRotateAngle = angle;
        transform();
    }

    public void setScaleX(float scaleX) {
        this.mScaleX = scaleX;
        transform();
    }

    public void setScaleY(float scaleY) {
        this.mScaleY = scaleY;
        transform();
    }

    public void setPivotX(float pivotX) {
        this.mRotationCenterX = pivotX;
        transform();
    }

    public void setPivotY(float pivotY) {
        this.mRotationCenterY = pivotY;
        transform();
    }

    public void setTranslationX(float dx) {
        this.mShiftX = dx;
        transform();
    }

    public void setTranslationY(float dy) {
        this.mShiftY = dy;
        transform();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        applyLayoutFeatures();
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        applyLayoutFeatures();
    }

    public void updatePostLayout(ConstraintLayout container) {
        reCacheViews();
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        ConstraintWidget widget = ((ConstraintLayout.LayoutParams) getLayoutParams()).getConstraintWidget();
        widget.setWidth(0);
        widget.setHeight(0);
        calcCenters();
        layout(((int) this.mComputedMinX) - getPaddingLeft(), ((int) this.mComputedMinY) - getPaddingTop(), ((int) this.mComputedMaxX) + getPaddingRight(), ((int) this.mComputedMaxY) + getPaddingBottom());
        transform();
    }

    private void reCacheViews() {
        if (this.mContainer != null && this.mCount != 0) {
            View[] viewArr = this.mViews;
            if (viewArr == null || viewArr.length != this.mCount) {
                this.mViews = new View[this.mCount];
            }
            for (int i = 0; i < this.mCount; i++) {
                this.mViews[i] = this.mContainer.getViewById(this.mIds[i]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void calcCenters() {
        if (this.mContainer != null) {
            if (!this.mNeedBounds && !Float.isNaN(this.mComputedCenterX) && !Float.isNaN(this.mComputedCenterY)) {
                return;
            }
            if (Float.isNaN(this.mRotationCenterX) || Float.isNaN(this.mRotationCenterY)) {
                View[] views = getViews(this.mContainer);
                int minx = views[0].getLeft();
                int miny = views[0].getTop();
                int maxx = views[0].getRight();
                int maxy = views[0].getBottom();
                for (int i = 0; i < this.mCount; i++) {
                    View view = views[i];
                    minx = Math.min(minx, view.getLeft());
                    miny = Math.min(miny, view.getTop());
                    maxx = Math.max(maxx, view.getRight());
                    maxy = Math.max(maxy, view.getBottom());
                }
                this.mComputedMaxX = (float) maxx;
                this.mComputedMaxY = (float) maxy;
                this.mComputedMinX = (float) minx;
                this.mComputedMinY = (float) miny;
                if (Float.isNaN(this.mRotationCenterX)) {
                    this.mComputedCenterX = (float) ((minx + maxx) / 2);
                } else {
                    this.mComputedCenterX = this.mRotationCenterX;
                }
                if (Float.isNaN(this.mRotationCenterY)) {
                    this.mComputedCenterY = (float) ((miny + maxy) / 2);
                } else {
                    this.mComputedCenterY = this.mRotationCenterY;
                }
            } else {
                this.mComputedCenterY = this.mRotationCenterY;
                this.mComputedCenterX = this.mRotationCenterX;
            }
        }
    }

    private void transform() {
        if (this.mContainer != null) {
            if (this.mViews == null) {
                reCacheViews();
            }
            calcCenters();
            double rad = Float.isNaN(this.mGroupRotateAngle) ? 0.0d : Math.toRadians((double) this.mGroupRotateAngle);
            float sin = (float) Math.sin(rad);
            float cos = (float) Math.cos(rad);
            float f = this.mScaleX;
            float m11 = f * cos;
            float f2 = this.mScaleY;
            float m12 = (-f2) * sin;
            float m21 = f * sin;
            float m22 = f2 * cos;
            int i = 0;
            while (i < this.mCount) {
                View view = this.mViews[i];
                float dx = ((float) ((view.getLeft() + view.getRight()) / 2)) - this.mComputedCenterX;
                float dy = ((float) ((view.getTop() + view.getBottom()) / 2)) - this.mComputedCenterY;
                double rad2 = rad;
                view.setTranslationX((((m11 * dx) + (m12 * dy)) - dx) + this.mShiftX);
                view.setTranslationY((((m21 * dx) + (m22 * dy)) - dy) + this.mShiftY);
                view.setScaleY(this.mScaleY);
                view.setScaleX(this.mScaleX);
                if (!Float.isNaN(this.mGroupRotateAngle)) {
                    view.setRotation(this.mGroupRotateAngle);
                }
                i++;
                rad = rad2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void applyLayoutFeaturesInConstraintSet(ConstraintLayout container) {
        applyLayoutFeatures(container);
    }
}

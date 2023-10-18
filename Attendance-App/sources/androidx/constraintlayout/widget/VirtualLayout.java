package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

public abstract class VirtualLayout extends ConstraintHelper {
    private boolean mApplyElevationOnAttach;
    private boolean mApplyVisibilityOnAttach;

    public VirtualLayout(Context context) {
        super(context);
    }

    public VirtualLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VirtualLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(AttributeSet attrs) {
        super.init(attrs);
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

    public void onMeasure(androidx.constraintlayout.core.widgets.VirtualLayout layout, int widthMeasureSpec, int heightMeasureSpec) {
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
            ViewParent parent = getParent();
            if (parent instanceof ConstraintLayout) {
                ConstraintLayout container = (ConstraintLayout) parent;
                int visibility = getVisibility();
                float elevation = 0.0f;
                if (Build.VERSION.SDK_INT >= 21) {
                    elevation = getElevation();
                }
                for (int i = 0; i < this.mCount; i++) {
                    View view = container.getViewById(this.mIds[i]);
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
}

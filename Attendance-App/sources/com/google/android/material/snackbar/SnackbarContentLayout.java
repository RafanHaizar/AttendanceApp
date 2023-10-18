package com.google.android.material.snackbar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.motion.MotionUtils;

public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private Button actionView;
    private final TimeInterpolator contentInterpolator;
    private int maxInlineActionWidth;
    private TextView messageView;

    public SnackbarContentLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.contentInterpolator = MotionUtils.resolveThemeInterpolator(context, C1087R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(C1087R.C1090id.snackbar_text);
        this.actionView = (Button) findViewById(C1087R.C1090id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.messageView;
    }

    public Button getActionView() {
        return this.actionView;
    }

    /* access modifiers changed from: package-private */
    public void updateActionTextColorAlphaIfNeeded(float actionTextColorAlpha) {
        if (actionTextColorAlpha != 1.0f) {
            this.actionView.setTextColor(MaterialColors.layer(MaterialColors.getColor(this, C1087R.attr.colorSurface), this.actionView.getCurrentTextColor(), actionTextColorAlpha));
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getOrientation() != 1) {
            int multiLineVPadding = getResources().getDimensionPixelSize(C1087R.dimen.design_snackbar_padding_vertical_2lines);
            int singleLineVPadding = getResources().getDimensionPixelSize(C1087R.dimen.design_snackbar_padding_vertical);
            Layout messageLayout = this.messageView.getLayout();
            boolean isMultiLine = messageLayout != null && messageLayout.getLineCount() > 1;
            boolean remeasure = false;
            if (!isMultiLine || this.maxInlineActionWidth <= 0 || this.actionView.getMeasuredWidth() <= this.maxInlineActionWidth) {
                int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
                if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                    remeasure = true;
                }
            } else if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true;
            }
            if (remeasure) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (this.messageView.getPaddingTop() == messagePadTop && this.messageView.getPaddingBottom() == messagePadBottom) {
            return changed;
        }
        updateTopBottomPadding(this.messageView, messagePadTop, messagePadBottom);
        return true;
    }

    private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
        } else {
            view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
        }
    }

    public void animateContentIn(int delay, int duration) {
        this.messageView.setAlpha(0.0f);
        this.messageView.animate().alpha(1.0f).setDuration((long) duration).setInterpolator(this.contentInterpolator).setStartDelay((long) delay).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(0.0f);
            this.actionView.animate().alpha(1.0f).setDuration((long) duration).setInterpolator(this.contentInterpolator).setStartDelay((long) delay).start();
        }
    }

    public void animateContentOut(int delay, int duration) {
        this.messageView.setAlpha(1.0f);
        this.messageView.animate().alpha(0.0f).setDuration((long) duration).setInterpolator(this.contentInterpolator).setStartDelay((long) delay).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(1.0f);
            this.actionView.animate().alpha(0.0f).setDuration((long) duration).setInterpolator(this.contentInterpolator).setStartDelay((long) delay).start();
        }
    }

    public void setMaxInlineActionWidth(int width) {
        this.maxInlineActionWidth = width;
    }
}

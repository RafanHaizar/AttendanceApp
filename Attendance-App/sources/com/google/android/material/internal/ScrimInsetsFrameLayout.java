package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.C1087R;

public class ScrimInsetsFrameLayout extends FrameLayout {
    private boolean drawBottomInsetForeground;
    private boolean drawLeftInsetForeground;
    private boolean drawRightInsetForeground;
    private boolean drawTopInsetForeground;
    Drawable insetForeground;
    Rect insets;
    private Rect tempRect;

    public ScrimInsetsFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.tempRect = new Rect();
        this.drawTopInsetForeground = true;
        this.drawBottomInsetForeground = true;
        this.drawLeftInsetForeground = true;
        this.drawRightInsetForeground = true;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.ScrimInsetsFrameLayout, defStyleAttr, C1087R.C1093style.Widget_Design_ScrimInsetsFrameLayout, new int[0]);
        this.insetForeground = a.getDrawable(C1087R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                if (ScrimInsetsFrameLayout.this.insets == null) {
                    ScrimInsetsFrameLayout.this.insets = new Rect();
                }
                ScrimInsetsFrameLayout.this.insets.set(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                ScrimInsetsFrameLayout.this.onInsetsChanged(insets);
                ScrimInsetsFrameLayout.this.setWillNotDraw(!insets.hasSystemWindowInsets() || ScrimInsetsFrameLayout.this.insetForeground == null);
                ViewCompat.postInvalidateOnAnimation(ScrimInsetsFrameLayout.this);
                return insets.consumeSystemWindowInsets();
            }
        });
    }

    public void setScrimInsetForeground(Drawable drawable) {
        this.insetForeground = drawable;
    }

    public void setDrawTopInsetForeground(boolean drawTopInsetForeground2) {
        this.drawTopInsetForeground = drawTopInsetForeground2;
    }

    public void setDrawBottomInsetForeground(boolean drawBottomInsetForeground2) {
        this.drawBottomInsetForeground = drawBottomInsetForeground2;
    }

    public void setDrawLeftInsetForeground(boolean drawLeftInsetForeground2) {
        this.drawLeftInsetForeground = drawLeftInsetForeground2;
    }

    public void setDrawRightInsetForeground(boolean drawRightInsetForeground2) {
        this.drawRightInsetForeground = drawRightInsetForeground2;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (this.insets != null && this.insetForeground != null) {
            int sc = canvas.save();
            canvas.translate((float) getScrollX(), (float) getScrollY());
            if (this.drawTopInsetForeground) {
                this.tempRect.set(0, 0, width, this.insets.top);
                this.insetForeground.setBounds(this.tempRect);
                this.insetForeground.draw(canvas);
            }
            if (this.drawBottomInsetForeground) {
                this.tempRect.set(0, height - this.insets.bottom, width, height);
                this.insetForeground.setBounds(this.tempRect);
                this.insetForeground.draw(canvas);
            }
            if (this.drawLeftInsetForeground) {
                this.tempRect.set(0, this.insets.top, this.insets.left, height - this.insets.bottom);
                this.insetForeground.setBounds(this.tempRect);
                this.insetForeground.draw(canvas);
            }
            if (this.drawRightInsetForeground) {
                this.tempRect.set(width - this.insets.right, this.insets.top, width, height - this.insets.bottom);
                this.insetForeground.setBounds(this.tempRect);
                this.insetForeground.draw(canvas);
            }
            canvas.restoreToCount(sc);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable = this.insetForeground;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Drawable drawable = this.insetForeground;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onInsetsChanged(WindowInsetsCompat insets2) {
    }
}

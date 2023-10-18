package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class TouchObserverFrameLayout extends FrameLayout {
    private View.OnTouchListener onTouchListener;

    public TouchObserverFrameLayout(Context context) {
        super(context);
    }

    public TouchObserverFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchObserverFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        View.OnTouchListener onTouchListener2 = this.onTouchListener;
        if (onTouchListener2 != null) {
            onTouchListener2.onTouch(this, ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener2) {
        this.onTouchListener = onTouchListener2;
    }
}

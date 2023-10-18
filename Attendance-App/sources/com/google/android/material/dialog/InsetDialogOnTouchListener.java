package com.google.android.material.dialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class InsetDialogOnTouchListener implements View.OnTouchListener {
    private final Dialog dialog;
    private final int leftInset;
    private final int prePieSlop;
    private final int topInset;

    public InsetDialogOnTouchListener(Dialog dialog2, Rect insets) {
        this.dialog = dialog2;
        this.leftInset = insets.left;
        this.topInset = insets.top;
        this.prePieSlop = ViewConfiguration.get(dialog2.getContext()).getScaledWindowTouchSlop();
    }

    public boolean onTouch(View view, MotionEvent event) {
        View insetView = view.findViewById(16908290);
        int insetLeft = this.leftInset + insetView.getLeft();
        int insetTop = this.topInset + insetView.getTop();
        if (new RectF((float) insetLeft, (float) insetTop, (float) (insetView.getWidth() + insetLeft), (float) (insetView.getHeight() + insetTop)).contains(event.getX(), event.getY())) {
            return false;
        }
        MotionEvent outsideEvent = MotionEvent.obtain(event);
        if (event.getAction() == 1) {
            outsideEvent.setAction(4);
        }
        if (Build.VERSION.SDK_INT < 28) {
            outsideEvent.setAction(0);
            int i = this.prePieSlop;
            outsideEvent.setLocation((float) ((-i) - 1), (float) ((-i) - 1));
        }
        view.performClick();
        return this.dialog.onTouchEvent(outsideEvent);
    }
}

package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class VisibilityAwareImageButton extends ImageButton {
    private int userSetVisibility;

    public VisibilityAwareImageButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.userSetVisibility = getVisibility();
    }

    public void setVisibility(int visibility) {
        internalSetVisibility(visibility, true);
    }

    public final void internalSetVisibility(int visibility, boolean fromUser) {
        super.setVisibility(visibility);
        if (fromUser) {
            this.userSetVisibility = visibility;
        }
    }

    public final int getUserSetVisibility() {
        return this.userSetVisibility;
    }
}

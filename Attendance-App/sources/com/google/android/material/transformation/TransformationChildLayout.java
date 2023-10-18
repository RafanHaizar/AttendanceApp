package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.circularreveal.CircularRevealFrameLayout;

@Deprecated
public class TransformationChildLayout extends CircularRevealFrameLayout {
    public TransformationChildLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TransformationChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}

package com.google.android.material.transition.platform;

import android.graphics.RectF;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TransitionUtils$$ExternalSyntheticLambda0 implements ShapeAppearanceModel.CornerSizeUnaryOperator {
    public final /* synthetic */ RectF f$0;

    public /* synthetic */ TransitionUtils$$ExternalSyntheticLambda0(RectF rectF) {
        this.f$0 = rectF;
    }

    public final CornerSize apply(CornerSize cornerSize) {
        return RelativeCornerSize.createFromCornerSize(this.f$0, cornerSize);
    }
}

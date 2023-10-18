package androidx.core.view;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ViewPropertyAnimatorCompat$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ViewPropertyAnimatorUpdateListener f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ ViewPropertyAnimatorCompat$$ExternalSyntheticLambda0(ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener, View view) {
        this.f$0 = viewPropertyAnimatorUpdateListener;
        this.f$1 = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.onAnimationUpdate(this.f$1);
    }
}

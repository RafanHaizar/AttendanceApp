package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;

public class Fade extends Visibility {

    /* renamed from: IN */
    public static final int f1066IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    public Fade(int fadingMode) {
        setMode(fadingMode);
    }

    public Fade() {
    }

    public Fade(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.FADE);
        setMode(TypedArrayUtils.getNamedInt(a, (XmlResourceParser) attrs, "fadingMode", 0, getMode()));
        a.recycle();
    }

    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA, Float.valueOf(ViewUtils.getTransitionAlpha(transitionValues.view)));
    }

    private Animator createAnimation(final View view, float startAlpha, float endAlpha) {
        if (startAlpha == endAlpha) {
            return null;
        }
        ViewUtils.setTransitionAlpha(view, startAlpha);
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, ViewUtils.TRANSITION_ALPHA, new float[]{endAlpha});
        anim.addListener(new FadeAnimatorListener(view));
        addListener(new TransitionListenerAdapter() {
            public void onTransitionEnd(Transition transition) {
                ViewUtils.setTransitionAlpha(view, 1.0f);
                ViewUtils.clearNonTransitionAlpha(view);
                transition.removeListener(this);
            }
        });
        return anim;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        float startAlpha = getStartAlpha(startValues, 0.0f);
        if (startAlpha == 1.0f) {
            startAlpha = 0.0f;
        }
        return createAnimation(view, startAlpha, 1.0f);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewUtils.saveNonTransitionAlpha(view);
        return createAnimation(view, getStartAlpha(startValues, 1.0f), 0.0f);
    }

    private static float getStartAlpha(TransitionValues startValues, float fallbackValue) {
        Float startAlphaFloat;
        float startAlpha = fallbackValue;
        if (startValues == null || (startAlphaFloat = (Float) startValues.values.get(PROPNAME_TRANSITION_ALPHA)) == null) {
            return startAlpha;
        }
        return startAlphaFloat.floatValue();
    }

    private static class FadeAnimatorListener extends AnimatorListenerAdapter {
        private boolean mLayerTypeChanged = false;
        private final View mView;

        FadeAnimatorListener(View view) {
            this.mView = view;
        }

        public void onAnimationStart(Animator animation) {
            if (ViewCompat.hasOverlappingRendering(this.mView) && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(2, (Paint) null);
            }
        }

        public void onAnimationEnd(Animator animation) {
            ViewUtils.setTransitionAlpha(this.mView, 1.0f);
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, (Paint) null);
            }
        }
    }
}

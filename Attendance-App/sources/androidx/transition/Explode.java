package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class Explode extends Visibility {
    private static final String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private int[] mTempLoc = new int[2];

    public Explode() {
        setPropagation(new CircularPropagation());
    }

    public Explode(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPropagation(new CircularPropagation());
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(this.mTempLoc);
        int[] iArr = this.mTempLoc;
        int left = iArr[0];
        int top = iArr[1];
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, new Rect(left, top, view.getWidth() + left, view.getHeight() + top));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        TransitionValues transitionValues = endValues;
        if (transitionValues == null) {
            return null;
        }
        Rect bounds = (Rect) transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        calculateOut(sceneRoot, bounds, this.mTempLoc);
        int[] iArr = this.mTempLoc;
        float startX = endX + ((float) iArr[0]);
        float startY = endY + ((float) iArr[1]);
        return TranslationAnimationCreator.createAnimation(view, endValues, bounds.left, bounds.top, startX, startY, endX, endY, sDecelerate, this);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        TransitionValues transitionValues = startValues;
        if (transitionValues == null) {
            return null;
        }
        Rect bounds = (Rect) transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        int viewPosX = bounds.left;
        int viewPosY = bounds.top;
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = startX;
        float endY = startY;
        int[] interruptedPosition = (int[]) transitionValues.view.getTag(C1010R.C1013id.transition_position);
        if (interruptedPosition != null) {
            endX += (float) (interruptedPosition[0] - bounds.left);
            endY += (float) (interruptedPosition[1] - bounds.top);
            bounds.offsetTo(interruptedPosition[0], interruptedPosition[1]);
        }
        calculateOut(sceneRoot, bounds, this.mTempLoc);
        int[] iArr = this.mTempLoc;
        float endX2 = endX + ((float) iArr[0]);
        float endY2 = endY + ((float) iArr[1]);
        return TranslationAnimationCreator.createAnimation(view, startValues, viewPosX, viewPosY, startX, startY, endX2, endY2, sAccelerate, this);
    }

    private void calculateOut(View sceneRoot, Rect bounds, int[] outVector) {
        int focalY;
        int focalX;
        View view = sceneRoot;
        view.getLocationOnScreen(this.mTempLoc);
        int[] iArr = this.mTempLoc;
        int sceneRootX = iArr[0];
        int sceneRootY = iArr[1];
        Rect epicenter = getEpicenter();
        if (epicenter == null) {
            focalX = (sceneRoot.getWidth() / 2) + sceneRootX + Math.round(sceneRoot.getTranslationX());
            focalY = (sceneRoot.getHeight() / 2) + sceneRootY + Math.round(sceneRoot.getTranslationY());
        } else {
            focalX = epicenter.centerX();
            focalY = epicenter.centerY();
        }
        float xVector = (float) (bounds.centerX() - focalX);
        float yVector = (float) (bounds.centerY() - focalY);
        if (xVector == 0.0f && yVector == 0.0f) {
            xVector = ((float) (Math.random() * 2.0d)) - 1.0f;
            Rect rect = epicenter;
            yVector = ((float) (Math.random() * 2.0d)) - 1.0f;
        }
        float vectorSize = calculateDistance(xVector, yVector);
        float maxDistance = calculateMaxDistance(view, focalX - sceneRootX, focalY - sceneRootY);
        outVector[0] = Math.round(maxDistance * (xVector / vectorSize));
        outVector[1] = Math.round(maxDistance * (yVector / vectorSize));
    }

    private static float calculateMaxDistance(View sceneRoot, int focalX, int focalY) {
        return calculateDistance((float) Math.max(focalX, sceneRoot.getWidth() - focalX), (float) Math.max(focalY, sceneRoot.getHeight() - focalY));
    }

    private static float calculateDistance(float x, float y) {
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }
}

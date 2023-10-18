package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class SidePropagation extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    public void setSide(int side) {
        this.mSide = side;
    }

    public void setPropagationSpeed(float propagationSpeed) {
        if (propagationSpeed != 0.0f) {
            this.mPropagationSpeed = propagationSpeed;
            return;
        }
        throw new IllegalArgumentException("propagationSpeed may not be 0");
    }

    public long getStartDelay(ViewGroup sceneRoot, Transition transition, TransitionValues startValues, TransitionValues endValues) {
        int directionMultiplier;
        TransitionValues positionValues;
        int epicenterY;
        int epicenterX;
        TransitionValues transitionValues = startValues;
        if (transitionValues == null && endValues == null) {
            return 0;
        }
        Rect epicenter = transition.getEpicenter();
        if (endValues == null || getViewVisibility(transitionValues) == 0) {
            directionMultiplier = -1;
            positionValues = startValues;
        } else {
            directionMultiplier = 1;
            positionValues = endValues;
        }
        int viewCenterX = getViewX(positionValues);
        int viewCenterY = getViewY(positionValues);
        int[] loc = new int[2];
        sceneRoot.getLocationOnScreen(loc);
        int left = loc[0] + Math.round(sceneRoot.getTranslationX());
        int top = loc[1] + Math.round(sceneRoot.getTranslationY());
        int right = left + sceneRoot.getWidth();
        int bottom = top + sceneRoot.getHeight();
        if (epicenter != null) {
            epicenterX = epicenter.centerX();
            epicenterY = epicenter.centerY();
        } else {
            epicenterY = (top + bottom) / 2;
            epicenterX = (left + right) / 2;
        }
        int[] iArr = loc;
        TransitionValues transitionValues2 = positionValues;
        float distanceFraction = ((float) distance(sceneRoot, viewCenterX, viewCenterY, epicenterX, epicenterY, left, top, right, bottom)) / ((float) getMaxDistance(sceneRoot));
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return (long) Math.round((((float) (((long) directionMultiplier) * duration)) / this.mPropagationSpeed) * distanceFraction);
    }

    private int distance(View sceneRoot, int viewX, int viewY, int epicenterX, int epicenterY, int left, int top, int right, int bottom) {
        int side;
        int i = this.mSide;
        int i2 = 5;
        boolean isRtl = false;
        if (i == 8388611) {
            if (ViewCompat.getLayoutDirection(sceneRoot) == 1) {
                isRtl = true;
            }
            if (!isRtl) {
                i2 = 3;
            }
            side = i2;
        } else if (i == 8388613) {
            if (ViewCompat.getLayoutDirection(sceneRoot) == 1) {
                isRtl = true;
            }
            if (isRtl) {
                i2 = 3;
            }
            side = i2;
        } else {
            side = this.mSide;
        }
        switch (side) {
            case 3:
                return (right - viewX) + Math.abs(epicenterY - viewY);
            case 5:
                return (viewX - left) + Math.abs(epicenterY - viewY);
            case 48:
                return (bottom - viewY) + Math.abs(epicenterX - viewX);
            case 80:
                return (viewY - top) + Math.abs(epicenterX - viewX);
            default:
                return 0;
        }
    }

    private int getMaxDistance(ViewGroup sceneRoot) {
        switch (this.mSide) {
            case 3:
            case 5:
            case GravityCompat.START:
            case GravityCompat.END:
                return sceneRoot.getWidth();
            default:
                return sceneRoot.getHeight();
        }
    }
}

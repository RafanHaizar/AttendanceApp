package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.motion.widget.MotionLayout;

public abstract class TransitionAdapter implements MotionLayout.TransitionListener {
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
    }

    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
    }

    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
    }

    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
    }
}

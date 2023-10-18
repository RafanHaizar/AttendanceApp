package com.google.android.material.animation;

public interface AnimatableView {

    public interface Listener {
        void onAnimationEnd();
    }

    void startAnimation(Listener listener);

    void stopAnimation();
}

package com.google.android.material.slider;

public interface BaseOnSliderTouchListener<S> {
    void onStartTrackingTouch(S s);

    void onStopTrackingTouch(S s);
}

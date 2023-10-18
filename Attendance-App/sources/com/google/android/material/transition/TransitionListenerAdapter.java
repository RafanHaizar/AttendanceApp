package com.google.android.material.transition;

import androidx.transition.Transition;

abstract class TransitionListenerAdapter implements Transition.TransitionListener {
    TransitionListenerAdapter() {
    }

    public void onTransitionStart(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
    }

    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }
}

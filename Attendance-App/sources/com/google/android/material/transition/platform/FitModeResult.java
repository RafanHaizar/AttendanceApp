package com.google.android.material.transition.platform;

class FitModeResult {
    final float currentEndHeight;
    final float currentEndWidth;
    final float currentStartHeight;
    final float currentStartWidth;
    final float endScale;
    final float startScale;

    FitModeResult(float startScale2, float endScale2, float currentStartWidth2, float currentStartHeight2, float currentEndWidth2, float currentEndHeight2) {
        this.startScale = startScale2;
        this.endScale = endScale2;
        this.currentStartWidth = currentStartWidth2;
        this.currentStartHeight = currentStartHeight2;
        this.currentEndWidth = currentEndWidth2;
        this.currentEndHeight = currentEndHeight2;
    }
}

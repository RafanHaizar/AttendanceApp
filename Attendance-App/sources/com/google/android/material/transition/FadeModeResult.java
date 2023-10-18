package com.google.android.material.transition;

class FadeModeResult {
    final int endAlpha;
    final boolean endOnTop;
    final int startAlpha;

    static FadeModeResult startOnTop(int startAlpha2, int endAlpha2) {
        return new FadeModeResult(startAlpha2, endAlpha2, false);
    }

    static FadeModeResult endOnTop(int startAlpha2, int endAlpha2) {
        return new FadeModeResult(startAlpha2, endAlpha2, true);
    }

    private FadeModeResult(int startAlpha2, int endAlpha2, boolean endOnTop2) {
        this.startAlpha = startAlpha2;
        this.endAlpha = endAlpha2;
        this.endOnTop = endOnTop2;
    }
}

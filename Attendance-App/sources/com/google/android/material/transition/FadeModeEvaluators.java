package com.google.android.material.transition;

class FadeModeEvaluators {
    private static final FadeModeEvaluator CROSS = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, fadeStartFraction, fadeEndFraction, progress), TransitionUtils.lerp(0, 255, fadeStartFraction, fadeEndFraction, progress));
        }
    };

    /* renamed from: IN */
    private static final FadeModeEvaluator f1127IN = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, fadeStartFraction, fadeEndFraction, progress));
        }
    };
    private static final FadeModeEvaluator OUT = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, fadeStartFraction, fadeEndFraction, progress), 255);
        }
    };
    private static final FadeModeEvaluator THROUGH = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            float fadeFractionThreshold = ((fadeEndFraction - fadeStartFraction) * threshold) + fadeStartFraction;
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, fadeStartFraction, fadeFractionThreshold, progress), TransitionUtils.lerp(0, 255, fadeFractionThreshold, fadeEndFraction, progress));
        }
    };

    static FadeModeEvaluator get(int fadeMode, boolean entering) {
        switch (fadeMode) {
            case 0:
                return entering ? f1127IN : OUT;
            case 1:
                return entering ? OUT : f1127IN;
            case 2:
                return CROSS;
            case 3:
                return THROUGH;
            default:
                throw new IllegalArgumentException("Invalid fade mode: " + fadeMode);
        }
    }

    private FadeModeEvaluators() {
    }
}

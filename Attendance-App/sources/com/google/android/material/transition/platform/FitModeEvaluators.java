package com.google.android.material.transition.platform;

import android.graphics.RectF;

class FitModeEvaluators {
    private static final FitModeEvaluator HEIGHT = new FitModeEvaluator() {
        public FitModeResult evaluate(float progress, float scaleStartFraction, float scaleEndFraction, float startWidth, float startHeight, float endWidth, float endHeight) {
            float currentHeight = TransitionUtils.lerp(startHeight, endHeight, scaleStartFraction, scaleEndFraction, progress, true);
            float startScale = currentHeight / startHeight;
            float endScale = currentHeight / endHeight;
            return new FitModeResult(startScale, endScale, startWidth * startScale, currentHeight, endWidth * endScale, currentHeight);
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartWidth > fitModeResult.currentEndWidth;
        }

        public void applyMask(RectF maskBounds, float maskMultiplier, FitModeResult fitModeResult) {
            float currentWidthDiff = Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth);
            maskBounds.left += (currentWidthDiff / 2.0f) * maskMultiplier;
            maskBounds.right -= (currentWidthDiff / 2.0f) * maskMultiplier;
        }
    };
    private static final FitModeEvaluator WIDTH = new FitModeEvaluator() {
        public FitModeResult evaluate(float progress, float scaleStartFraction, float scaleEndFraction, float startWidth, float startHeight, float endWidth, float endHeight) {
            float currentWidth = TransitionUtils.lerp(startWidth, endWidth, scaleStartFraction, scaleEndFraction, progress, true);
            float startScale = currentWidth / startWidth;
            float endScale = currentWidth / endWidth;
            return new FitModeResult(startScale, endScale, currentWidth, startHeight * startScale, currentWidth, endHeight * endScale);
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartHeight > fitModeResult.currentEndHeight;
        }

        public void applyMask(RectF maskBounds, float maskMultiplier, FitModeResult fitModeResult) {
            maskBounds.bottom -= Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight) * maskMultiplier;
        }
    };

    static FitModeEvaluator get(int fitMode, boolean entering, RectF startBounds, RectF endBounds) {
        switch (fitMode) {
            case 0:
                return shouldAutoFitToWidth(entering, startBounds, endBounds) ? WIDTH : HEIGHT;
            case 1:
                return WIDTH;
            case 2:
                return HEIGHT;
            default:
                throw new IllegalArgumentException("Invalid fit mode: " + fitMode);
        }
    }

    private static boolean shouldAutoFitToWidth(boolean entering, RectF startBounds, RectF endBounds) {
        float startWidth = startBounds.width();
        float startHeight = startBounds.height();
        float endWidth = endBounds.width();
        float endHeight = endBounds.height();
        float endHeightFitToWidth = (endHeight * startWidth) / endWidth;
        float startHeightFitToWidth = (startHeight * endWidth) / startWidth;
        if (entering) {
            if (endHeightFitToWidth >= startHeight) {
                return true;
            }
        } else if (startHeightFitToWidth >= endHeight) {
            return true;
        }
        return false;
    }

    private FitModeEvaluators() {
    }
}

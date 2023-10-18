package com.google.android.material.sidesheet;

final class SheetUtils {
    private SheetUtils() {
    }

    static boolean isSwipeMostlyHorizontal(float xVelocity, float yVelocity) {
        return Math.abs(xVelocity) > Math.abs(yVelocity);
    }
}

package com.google.android.material.math;

public final class MathUtils {
    public static final float DEFAULT_EPSILON = 1.0E-4f;

    private MathUtils() {
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot((double) (x2 - x1), (double) (y2 - y1));
    }

    public static float lerp(float start, float stop, float amount) {
        return ((1.0f - amount) * start) + (amount * stop);
    }

    public static boolean geq(float a, float b, float epsilon) {
        return a + epsilon >= b;
    }

    public static float distanceToFurthestCorner(float pointX, float pointY, float rectLeft, float rectTop, float rectRight, float rectBottom) {
        return max(dist(pointX, pointY, rectLeft, rectTop), dist(pointX, pointY, rectRight, rectTop), dist(pointX, pointY, rectRight, rectBottom), dist(pointX, pointY, rectLeft, rectBottom));
    }

    private static float max(float a, float b, float c, float d) {
        if (a > b && a > c && a > d) {
            return a;
        }
        if (b <= c || b <= d) {
            return c > d ? c : d;
        }
        return b;
    }

    public static float floorMod(float x, int y) {
        int r = (int) (x / ((float) y));
        if (Math.signum(x) * ((float) y) < 0.0f && ((float) (r * y)) != x) {
            r--;
        }
        return x - ((float) (r * y));
    }

    public static int floorMod(int x, int y) {
        int r = x / y;
        if ((x ^ y) < 0 && r * y != x) {
            r--;
        }
        return x - (r * y);
    }
}

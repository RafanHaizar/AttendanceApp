package com.google.android.material.ripple;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;

public class RippleUtils {
    private static final int[] ENABLED_PRESSED_STATE_SET = {16842910, 16842919};
    private static final int[] FOCUSED_STATE_SET = {16842908};
    private static final int[] HOVERED_FOCUSED_STATE_SET = {16843623, 16842908};
    private static final int[] HOVERED_STATE_SET = {16843623};
    static final String LOG_TAG = RippleUtils.class.getSimpleName();
    private static final int[] PRESSED_STATE_SET = {16842919};
    private static final int[] SELECTED_FOCUSED_STATE_SET = {16842913, 16842908};
    private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET = {16842913, 16843623, 16842908};
    private static final int[] SELECTED_HOVERED_STATE_SET = {16842913, 16843623};
    private static final int[] SELECTED_PRESSED_STATE_SET = {16842913, 16842919};
    private static final int[] SELECTED_STATE_SET = {16842913};
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
    public static final boolean USE_FRAMEWORK_RIPPLE = (Build.VERSION.SDK_INT >= 21);

    private RippleUtils() {
    }

    public static ColorStateList convertToRippleDrawableColor(ColorStateList rippleColor) {
        if (USE_FRAMEWORK_RIPPLE) {
            int[][] states = new int[3][];
            int[] colors = new int[3];
            states[0] = SELECTED_STATE_SET;
            colors[0] = getColorForState(rippleColor, SELECTED_PRESSED_STATE_SET);
            int i = 0 + 1;
            int[] iArr = FOCUSED_STATE_SET;
            states[i] = iArr;
            colors[i] = getColorForState(rippleColor, iArr);
            int i2 = i + 1;
            states[i2] = StateSet.NOTHING;
            colors[i2] = getColorForState(rippleColor, PRESSED_STATE_SET);
            int i3 = i2 + 1;
            return new ColorStateList(states, colors);
        }
        int[][] states2 = new int[10][];
        int[] colors2 = new int[10];
        int[] iArr2 = SELECTED_PRESSED_STATE_SET;
        states2[0] = iArr2;
        colors2[0] = getColorForState(rippleColor, iArr2);
        int i4 = 0 + 1;
        int[] iArr3 = SELECTED_HOVERED_FOCUSED_STATE_SET;
        states2[i4] = iArr3;
        colors2[i4] = getColorForState(rippleColor, iArr3);
        int i5 = i4 + 1;
        int[] iArr4 = SELECTED_FOCUSED_STATE_SET;
        states2[i5] = iArr4;
        colors2[i5] = getColorForState(rippleColor, iArr4);
        int i6 = i5 + 1;
        int[] iArr5 = SELECTED_HOVERED_STATE_SET;
        states2[i6] = iArr5;
        colors2[i6] = getColorForState(rippleColor, iArr5);
        int i7 = i6 + 1;
        states2[i7] = SELECTED_STATE_SET;
        colors2[i7] = 0;
        int i8 = i7 + 1;
        int[] iArr6 = PRESSED_STATE_SET;
        states2[i8] = iArr6;
        colors2[i8] = getColorForState(rippleColor, iArr6);
        int i9 = i8 + 1;
        int[] iArr7 = HOVERED_FOCUSED_STATE_SET;
        states2[i9] = iArr7;
        colors2[i9] = getColorForState(rippleColor, iArr7);
        int i10 = i9 + 1;
        int[] iArr8 = FOCUSED_STATE_SET;
        states2[i10] = iArr8;
        colors2[i10] = getColorForState(rippleColor, iArr8);
        int i11 = i10 + 1;
        int[] iArr9 = HOVERED_STATE_SET;
        states2[i11] = iArr9;
        colors2[i11] = getColorForState(rippleColor, iArr9);
        int i12 = i11 + 1;
        states2[i12] = StateSet.NOTHING;
        colors2[i12] = 0;
        int i13 = i12 + 1;
        return new ColorStateList(states2, colors2);
    }

    public static ColorStateList sanitizeRippleDrawableColor(ColorStateList rippleColor) {
        if (rippleColor == null) {
            return ColorStateList.valueOf(0);
        }
        if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 27 && Color.alpha(rippleColor.getDefaultColor()) == 0 && Color.alpha(rippleColor.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) != 0) {
            Log.w(LOG_TAG, TRANSPARENT_DEFAULT_COLOR_WARNING);
        }
        return rippleColor;
    }

    public static boolean shouldDrawRippleCompat(int[] stateSet) {
        boolean enabled = false;
        boolean interactedState = false;
        for (int state : stateSet) {
            if (state == 16842910) {
                enabled = true;
            } else if (state == 16842908) {
                interactedState = true;
            } else if (state == 16842919) {
                interactedState = true;
            } else if (state == 16843623) {
                interactedState = true;
            }
        }
        if (!enabled || !interactedState) {
            return false;
        }
        return true;
    }

    public static Drawable createOvalRippleLollipop(Context context, int padding) {
        return RippleUtilsLollipop.createOvalRipple(context, padding);
    }

    private static int getColorForState(ColorStateList rippleColor, int[] state) {
        int color;
        if (rippleColor != null) {
            color = rippleColor.getColorForState(state, rippleColor.getDefaultColor());
        } else {
            color = 0;
        }
        return USE_FRAMEWORK_RIPPLE ? doubleAlpha(color) : color;
    }

    private static int doubleAlpha(int color) {
        return ColorUtils.setAlphaComponent(color, Math.min(Color.alpha(color) * 2, 255));
    }

    private static class RippleUtilsLollipop {
        private RippleUtilsLollipop() {
        }

        /* access modifiers changed from: private */
        public static Drawable createOvalRipple(Context context, int padding) {
            GradientDrawable maskDrawable = new GradientDrawable();
            maskDrawable.setColor(-1);
            maskDrawable.setShape(1);
            return new RippleDrawable(MaterialColors.getColorStateList(context, C1087R.attr.colorControlHighlight, ColorStateList.valueOf(0)), (Drawable) null, new InsetDrawable(maskDrawable, padding, padding, padding, padding));
        }
    }
}

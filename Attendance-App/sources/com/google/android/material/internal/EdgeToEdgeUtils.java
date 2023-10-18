package com.google.android.material.internal;

import android.content.Context;
import android.os.Build;
import android.view.Window;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import com.google.android.material.color.MaterialColors;

public class EdgeToEdgeUtils {
    private static final int EDGE_TO_EDGE_BAR_ALPHA = 128;

    private EdgeToEdgeUtils() {
    }

    public static void applyEdgeToEdge(Window window, boolean edgeToEdgeEnabled) {
        applyEdgeToEdge(window, edgeToEdgeEnabled, (Integer) null, (Integer) null);
    }

    public static void applyEdgeToEdge(Window window, boolean edgeToEdgeEnabled, Integer statusBarOverlapBackgroundColor, Integer navigationBarOverlapBackgroundColor) {
        if (Build.VERSION.SDK_INT >= 21) {
            boolean useDefaultBackgroundColorForNavigationBar = false;
            boolean useDefaultBackgroundColorForStatusBar = statusBarOverlapBackgroundColor == null || statusBarOverlapBackgroundColor.intValue() == 0;
            if (navigationBarOverlapBackgroundColor == null || navigationBarOverlapBackgroundColor.intValue() == 0) {
                useDefaultBackgroundColorForNavigationBar = true;
            }
            if (useDefaultBackgroundColorForStatusBar || useDefaultBackgroundColorForNavigationBar) {
                int defaultBackgroundColor = MaterialColors.getColor(window.getContext(), 16842801, (int) ViewCompat.MEASURED_STATE_MASK);
                if (useDefaultBackgroundColorForStatusBar) {
                    statusBarOverlapBackgroundColor = Integer.valueOf(defaultBackgroundColor);
                }
                if (useDefaultBackgroundColorForNavigationBar) {
                    navigationBarOverlapBackgroundColor = Integer.valueOf(defaultBackgroundColor);
                }
            }
            WindowCompat.setDecorFitsSystemWindows(window, !edgeToEdgeEnabled);
            int statusBarColor = getStatusBarColor(window.getContext(), edgeToEdgeEnabled);
            int navigationBarColor = getNavigationBarColor(window.getContext(), edgeToEdgeEnabled);
            window.setStatusBarColor(statusBarColor);
            window.setNavigationBarColor(navigationBarColor);
            setLightStatusBar(window, isUsingLightSystemBar(statusBarColor, MaterialColors.isColorLight(statusBarOverlapBackgroundColor.intValue())));
            setLightNavigationBar(window, isUsingLightSystemBar(navigationBarColor, MaterialColors.isColorLight(navigationBarOverlapBackgroundColor.intValue())));
        }
    }

    public static void setLightStatusBar(Window window, boolean isLight) {
        WindowCompat.getInsetsController(window, window.getDecorView()).setAppearanceLightStatusBars(isLight);
    }

    public static void setLightNavigationBar(Window window, boolean isLight) {
        WindowCompat.getInsetsController(window, window.getDecorView()).setAppearanceLightNavigationBars(isLight);
    }

    private static int getStatusBarColor(Context context, boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled && Build.VERSION.SDK_INT < 23) {
            return ColorUtils.setAlphaComponent(MaterialColors.getColor(context, 16843857, (int) ViewCompat.MEASURED_STATE_MASK), 128);
        }
        if (isEdgeToEdgeEnabled) {
            return 0;
        }
        return MaterialColors.getColor(context, 16843857, (int) ViewCompat.MEASURED_STATE_MASK);
    }

    private static int getNavigationBarColor(Context context, boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled && Build.VERSION.SDK_INT < 27) {
            return ColorUtils.setAlphaComponent(MaterialColors.getColor(context, 16843858, (int) ViewCompat.MEASURED_STATE_MASK), 128);
        }
        if (isEdgeToEdgeEnabled) {
            return 0;
        }
        return MaterialColors.getColor(context, 16843858, (int) ViewCompat.MEASURED_STATE_MASK);
    }

    private static boolean isUsingLightSystemBar(int systemBarColor, boolean isLightBackground) {
        return MaterialColors.isColorLight(systemBarColor) || (systemBarColor == 0 && isLightBackground);
    }
}

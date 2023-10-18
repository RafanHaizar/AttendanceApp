package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

public class ElevationOverlayProvider {
    private static final float FORMULA_MULTIPLIER = 4.5f;
    private static final float FORMULA_OFFSET = 2.0f;
    private static final int OVERLAY_ACCENT_COLOR_ALPHA = ((int) Math.round(5.1000000000000005d));
    private final int colorSurface;
    private final float displayDensity;
    private final int elevationOverlayAccentColor;
    private final int elevationOverlayColor;
    private final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(Context context) {
        this(MaterialAttributes.resolveBoolean(context, C1087R.attr.elevationOverlayEnabled, false), MaterialColors.getColor(context, C1087R.attr.elevationOverlayColor, 0), MaterialColors.getColor(context, C1087R.attr.elevationOverlayAccentColor, 0), MaterialColors.getColor(context, C1087R.attr.colorSurface, 0), context.getResources().getDisplayMetrics().density);
    }

    public ElevationOverlayProvider(boolean elevationOverlayEnabled2, int elevationOverlayColor2, int elevationOverlayAccentColor2, int colorSurface2, float displayDensity2) {
        this.elevationOverlayEnabled = elevationOverlayEnabled2;
        this.elevationOverlayColor = elevationOverlayColor2;
        this.elevationOverlayAccentColor = elevationOverlayAccentColor2;
        this.colorSurface = colorSurface2;
        this.displayDensity = displayDensity2;
    }

    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation, View overlayView) {
        return compositeOverlayWithThemeSurfaceColorIfNeeded(elevation + getParentAbsoluteElevation(overlayView));
    }

    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation) {
        return compositeOverlayIfNeeded(this.colorSurface, elevation);
    }

    public int compositeOverlayIfNeeded(int backgroundColor, float elevation, View overlayView) {
        return compositeOverlayIfNeeded(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    public int compositeOverlayIfNeeded(int backgroundColor, float elevation) {
        if (!this.elevationOverlayEnabled || !isThemeSurfaceColor(backgroundColor)) {
            return backgroundColor;
        }
        return compositeOverlay(backgroundColor, elevation);
    }

    public int compositeOverlay(int backgroundColor, float elevation, View overlayView) {
        return compositeOverlay(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    public int compositeOverlay(int backgroundColor, float elevation) {
        int i;
        float overlayAlphaFraction = calculateOverlayAlphaFraction(elevation);
        int backgroundAlpha = Color.alpha(backgroundColor);
        int overlayColorOpaque = MaterialColors.layer(ColorUtils.setAlphaComponent(backgroundColor, 255), this.elevationOverlayColor, overlayAlphaFraction);
        if (overlayAlphaFraction > 0.0f && (i = this.elevationOverlayAccentColor) != 0) {
            overlayColorOpaque = MaterialColors.layer(overlayColorOpaque, ColorUtils.setAlphaComponent(i, OVERLAY_ACCENT_COLOR_ALPHA));
        }
        return ColorUtils.setAlphaComponent(overlayColorOpaque, backgroundAlpha);
    }

    public int calculateOverlayAlpha(float elevation) {
        return Math.round(calculateOverlayAlphaFraction(elevation) * 255.0f);
    }

    public float calculateOverlayAlphaFraction(float elevation) {
        float f = this.displayDensity;
        if (f <= 0.0f || elevation <= 0.0f) {
            return 0.0f;
        }
        return Math.min(((((float) Math.log1p((double) (elevation / f))) * FORMULA_MULTIPLIER) + 2.0f) / 100.0f, 1.0f);
    }

    public boolean isThemeElevationOverlayEnabled() {
        return this.elevationOverlayEnabled;
    }

    public int getThemeElevationOverlayColor() {
        return this.elevationOverlayColor;
    }

    public int getThemeSurfaceColor() {
        return this.colorSurface;
    }

    public float getParentAbsoluteElevation(View overlayView) {
        return ViewUtils.getParentAbsoluteElevation(overlayView);
    }

    private boolean isThemeSurfaceColor(int color) {
        return ColorUtils.setAlphaComponent(color, 255) == this.colorSurface;
    }
}

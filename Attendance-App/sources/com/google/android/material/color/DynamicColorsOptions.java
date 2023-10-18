package com.google.android.material.color;

import android.app.Activity;
import android.graphics.Bitmap;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.utilities.QuantizerCelebi;
import com.google.android.material.color.utilities.Score;

public class DynamicColorsOptions {
    /* access modifiers changed from: private */
    public static final DynamicColors.Precondition ALWAYS_ALLOW = new DynamicColors.Precondition() {
        public boolean shouldApplyDynamicColors(Activity activity, int theme) {
            return true;
        }
    };
    /* access modifiers changed from: private */
    public static final DynamicColors.OnAppliedCallback NO_OP_CALLBACK = new DynamicColors.OnAppliedCallback() {
        public void onApplied(Activity activity) {
        }
    };
    private Integer contentBasedSeedColor;
    private final DynamicColors.OnAppliedCallback onAppliedCallback;
    private final DynamicColors.Precondition precondition;
    private final int themeOverlay;

    private DynamicColorsOptions(Builder builder) {
        this.themeOverlay = builder.themeOverlay;
        this.precondition = builder.precondition;
        this.onAppliedCallback = builder.onAppliedCallback;
        if (builder.contentBasedSource != null) {
            this.contentBasedSeedColor = Integer.valueOf(extractSeedColorFromImage(builder.contentBasedSource));
        }
    }

    public int getThemeOverlay() {
        return this.themeOverlay;
    }

    public DynamicColors.Precondition getPrecondition() {
        return this.precondition;
    }

    public DynamicColors.OnAppliedCallback getOnAppliedCallback() {
        return this.onAppliedCallback;
    }

    public Integer getContentBasedSeedColor() {
        return this.contentBasedSeedColor;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Bitmap contentBasedSource;
        /* access modifiers changed from: private */
        public DynamicColors.OnAppliedCallback onAppliedCallback = DynamicColorsOptions.NO_OP_CALLBACK;
        /* access modifiers changed from: private */
        public DynamicColors.Precondition precondition = DynamicColorsOptions.ALWAYS_ALLOW;
        /* access modifiers changed from: private */
        public int themeOverlay;

        public Builder setThemeOverlay(int themeOverlay2) {
            this.themeOverlay = themeOverlay2;
            return this;
        }

        public Builder setPrecondition(DynamicColors.Precondition precondition2) {
            this.precondition = precondition2;
            return this;
        }

        public Builder setOnAppliedCallback(DynamicColors.OnAppliedCallback onAppliedCallback2) {
            this.onAppliedCallback = onAppliedCallback2;
            return this;
        }

        public Builder setContentBasedSource(Bitmap contentBasedSource2) {
            this.contentBasedSource = contentBasedSource2;
            return this;
        }

        public DynamicColorsOptions build() {
            return new DynamicColorsOptions(this);
        }
    }

    private static int extractSeedColorFromImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] bitmapPixels = new int[(width * height)];
        bitmap.getPixels(bitmapPixels, 0, width, 0, 0, width, height);
        return Score.score(QuantizerCelebi.quantize(bitmapPixels, 128)).get(0).intValue();
    }
}

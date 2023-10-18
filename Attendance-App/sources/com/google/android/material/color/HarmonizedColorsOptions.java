package com.google.android.material.color;

import com.google.android.material.C1087R;

public class HarmonizedColorsOptions {
    private final int colorAttributeToHarmonizeWith;
    private final HarmonizedColorAttributes colorAttributes;
    private final int[] colorResourceIds;

    public static HarmonizedColorsOptions createMaterialDefaults() {
        return new Builder().setColorAttributes(HarmonizedColorAttributes.createMaterialDefaults()).build();
    }

    private HarmonizedColorsOptions(Builder builder) {
        this.colorResourceIds = builder.colorResourceIds;
        this.colorAttributes = builder.colorAttributes;
        this.colorAttributeToHarmonizeWith = builder.colorAttributeToHarmonizeWith;
    }

    public int[] getColorResourceIds() {
        return this.colorResourceIds;
    }

    public HarmonizedColorAttributes getColorAttributes() {
        return this.colorAttributes;
    }

    public int getColorAttributeToHarmonizeWith() {
        return this.colorAttributeToHarmonizeWith;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public int colorAttributeToHarmonizeWith = C1087R.attr.colorPrimary;
        /* access modifiers changed from: private */
        public HarmonizedColorAttributes colorAttributes;
        /* access modifiers changed from: private */
        public int[] colorResourceIds = new int[0];

        public Builder setColorResourceIds(int[] colorResourceIds2) {
            this.colorResourceIds = colorResourceIds2;
            return this;
        }

        public Builder setColorAttributes(HarmonizedColorAttributes colorAttributes2) {
            this.colorAttributes = colorAttributes2;
            return this;
        }

        public Builder setColorAttributeToHarmonizeWith(int colorAttributeToHarmonizeWith2) {
            this.colorAttributeToHarmonizeWith = colorAttributeToHarmonizeWith2;
            return this;
        }

        public HarmonizedColorsOptions build() {
            return new HarmonizedColorsOptions(this);
        }
    }

    /* access modifiers changed from: package-private */
    public int getThemeOverlayResourceId(int defaultThemeOverlay) {
        HarmonizedColorAttributes harmonizedColorAttributes = this.colorAttributes;
        if (harmonizedColorAttributes == null || harmonizedColorAttributes.getThemeOverlay() == 0) {
            return defaultThemeOverlay;
        }
        return this.colorAttributes.getThemeOverlay();
    }
}

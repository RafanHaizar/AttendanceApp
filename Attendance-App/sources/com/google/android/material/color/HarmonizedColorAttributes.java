package com.google.android.material.color;

import com.google.android.material.C1087R;

public final class HarmonizedColorAttributes {
    private static final int[] HARMONIZED_MATERIAL_ATTRIBUTES = {C1087R.attr.colorError, C1087R.attr.colorOnError, C1087R.attr.colorErrorContainer, C1087R.attr.colorOnErrorContainer};
    private final int[] attributes;
    private final int themeOverlay;

    public static HarmonizedColorAttributes create(int[] attributes2) {
        return new HarmonizedColorAttributes(attributes2, 0);
    }

    public static HarmonizedColorAttributes create(int[] attributes2, int themeOverlay2) {
        return new HarmonizedColorAttributes(attributes2, themeOverlay2);
    }

    public static HarmonizedColorAttributes createMaterialDefaults() {
        return create(HARMONIZED_MATERIAL_ATTRIBUTES, C1087R.C1093style.ThemeOverlay_Material3_HarmonizedColors);
    }

    private HarmonizedColorAttributes(int[] attributes2, int themeOverlay2) {
        if (themeOverlay2 == 0 || attributes2.length != 0) {
            this.attributes = attributes2;
            this.themeOverlay = themeOverlay2;
            return;
        }
        throw new IllegalArgumentException("Theme overlay should be used with the accompanying int[] attributes.");
    }

    public int[] getAttributes() {
        return this.attributes;
    }

    public int getThemeOverlay() {
        return this.themeOverlay;
    }
}

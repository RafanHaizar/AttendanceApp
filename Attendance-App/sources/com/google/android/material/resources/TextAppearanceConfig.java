package com.google.android.material.resources;

@Deprecated
public class TextAppearanceConfig {
    private static boolean shouldLoadFontSynchronously;

    public static void setShouldLoadFontSynchronously(boolean flag) {
        shouldLoadFontSynchronously = flag;
    }

    public static boolean shouldLoadFontSynchronously() {
        return shouldLoadFontSynchronously;
    }
}

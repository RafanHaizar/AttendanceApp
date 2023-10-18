package com.google.android.material.color;

public final class ColorRoles {
    private final int accent;
    private final int accentContainer;
    private final int onAccent;
    private final int onAccentContainer;

    ColorRoles(int accent2, int onAccent2, int accentContainer2, int onAccentContainer2) {
        this.accent = accent2;
        this.onAccent = onAccent2;
        this.accentContainer = accentContainer2;
        this.onAccentContainer = onAccentContainer2;
    }

    public int getAccent() {
        return this.accent;
    }

    public int getOnAccent() {
        return this.onAccent;
    }

    public int getAccentContainer() {
        return this.accentContainer;
    }

    public int getOnAccentContainer() {
        return this.onAccentContainer;
    }
}

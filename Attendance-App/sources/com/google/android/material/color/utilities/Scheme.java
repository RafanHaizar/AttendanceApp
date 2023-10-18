package com.google.android.material.color.utilities;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Scheme {
    private int background;
    private int error;
    private int errorContainer;
    private int inverseOnSurface;
    private int inversePrimary;
    private int inverseSurface;
    private int onBackground;
    private int onError;
    private int onErrorContainer;
    private int onPrimary;
    private int onPrimaryContainer;
    private int onSecondary;
    private int onSecondaryContainer;
    private int onSurface;
    private int onSurfaceVariant;
    private int onTertiary;
    private int onTertiaryContainer;
    private int outline;
    private int outlineVariant;
    private int primary;
    private int primaryContainer;
    private int scrim;
    private int secondary;
    private int secondaryContainer;
    private int shadow;
    private int surface;
    private int surfaceVariant;
    private int tertiary;
    private int tertiaryContainer;

    public Scheme() {
    }

    public Scheme(int primary2, int onPrimary2, int primaryContainer2, int onPrimaryContainer2, int secondary2, int onSecondary2, int secondaryContainer2, int onSecondaryContainer2, int tertiary2, int onTertiary2, int tertiaryContainer2, int onTertiaryContainer2, int error2, int onError2, int errorContainer2, int onErrorContainer2, int background2, int onBackground2, int surface2, int onSurface2, int surfaceVariant2, int onSurfaceVariant2, int outline2, int outlineVariant2, int shadow2, int scrim2, int inverseSurface2, int inverseOnSurface2, int inversePrimary2) {
        this.primary = primary2;
        this.onPrimary = onPrimary2;
        this.primaryContainer = primaryContainer2;
        this.onPrimaryContainer = onPrimaryContainer2;
        this.secondary = secondary2;
        this.onSecondary = onSecondary2;
        this.secondaryContainer = secondaryContainer2;
        this.onSecondaryContainer = onSecondaryContainer2;
        this.tertiary = tertiary2;
        this.onTertiary = onTertiary2;
        this.tertiaryContainer = tertiaryContainer2;
        this.onTertiaryContainer = onTertiaryContainer2;
        this.error = error2;
        this.onError = onError2;
        this.errorContainer = errorContainer2;
        this.onErrorContainer = onErrorContainer2;
        this.background = background2;
        this.onBackground = onBackground2;
        this.surface = surface2;
        this.onSurface = onSurface2;
        this.surfaceVariant = surfaceVariant2;
        this.onSurfaceVariant = onSurfaceVariant2;
        this.outline = outline2;
        this.outlineVariant = outlineVariant2;
        this.shadow = shadow2;
        this.scrim = scrim2;
        this.inverseSurface = inverseSurface2;
        this.inverseOnSurface = inverseOnSurface2;
        this.inversePrimary = inversePrimary2;
    }

    public static Scheme light(int argb) {
        return lightFromCorePalette(CorePalette.m228of(argb));
    }

    public static Scheme dark(int argb) {
        return darkFromCorePalette(CorePalette.m228of(argb));
    }

    public static Scheme lightContent(int argb) {
        return lightFromCorePalette(CorePalette.contentOf(argb));
    }

    public static Scheme darkContent(int argb) {
        return darkFromCorePalette(CorePalette.contentOf(argb));
    }

    private static Scheme lightFromCorePalette(CorePalette core) {
        return new Scheme().withPrimary(core.f1104a1.tone(40)).withOnPrimary(core.f1104a1.tone(100)).withPrimaryContainer(core.f1104a1.tone(90)).withOnPrimaryContainer(core.f1104a1.tone(10)).withSecondary(core.f1105a2.tone(40)).withOnSecondary(core.f1105a2.tone(100)).withSecondaryContainer(core.f1105a2.tone(90)).withOnSecondaryContainer(core.f1105a2.tone(10)).withTertiary(core.f1106a3.tone(40)).withOnTertiary(core.f1106a3.tone(100)).withTertiaryContainer(core.f1106a3.tone(90)).withOnTertiaryContainer(core.f1106a3.tone(10)).withError(core.error.tone(40)).withOnError(core.error.tone(100)).withErrorContainer(core.error.tone(90)).withOnErrorContainer(core.error.tone(10)).withBackground(core.f1107n1.tone(99)).withOnBackground(core.f1107n1.tone(10)).withSurface(core.f1107n1.tone(99)).withOnSurface(core.f1107n1.tone(10)).withSurfaceVariant(core.f1108n2.tone(90)).withOnSurfaceVariant(core.f1108n2.tone(30)).withOutline(core.f1108n2.tone(50)).withOutlineVariant(core.f1108n2.tone(80)).withShadow(core.f1107n1.tone(0)).withScrim(core.f1107n1.tone(0)).withInverseSurface(core.f1107n1.tone(20)).withInverseOnSurface(core.f1107n1.tone(95)).withInversePrimary(core.f1104a1.tone(80));
    }

    private static Scheme darkFromCorePalette(CorePalette core) {
        return new Scheme().withPrimary(core.f1104a1.tone(80)).withOnPrimary(core.f1104a1.tone(20)).withPrimaryContainer(core.f1104a1.tone(30)).withOnPrimaryContainer(core.f1104a1.tone(90)).withSecondary(core.f1105a2.tone(80)).withOnSecondary(core.f1105a2.tone(20)).withSecondaryContainer(core.f1105a2.tone(30)).withOnSecondaryContainer(core.f1105a2.tone(90)).withTertiary(core.f1106a3.tone(80)).withOnTertiary(core.f1106a3.tone(20)).withTertiaryContainer(core.f1106a3.tone(30)).withOnTertiaryContainer(core.f1106a3.tone(90)).withError(core.error.tone(80)).withOnError(core.error.tone(20)).withErrorContainer(core.error.tone(30)).withOnErrorContainer(core.error.tone(80)).withBackground(core.f1107n1.tone(10)).withOnBackground(core.f1107n1.tone(90)).withSurface(core.f1107n1.tone(10)).withOnSurface(core.f1107n1.tone(90)).withSurfaceVariant(core.f1108n2.tone(30)).withOnSurfaceVariant(core.f1108n2.tone(80)).withOutline(core.f1108n2.tone(60)).withOutlineVariant(core.f1108n2.tone(30)).withShadow(core.f1107n1.tone(0)).withScrim(core.f1107n1.tone(0)).withInverseSurface(core.f1107n1.tone(90)).withInverseOnSurface(core.f1107n1.tone(20)).withInversePrimary(core.f1104a1.tone(40));
    }

    public int getPrimary() {
        return this.primary;
    }

    public void setPrimary(int primary2) {
        this.primary = primary2;
    }

    public Scheme withPrimary(int primary2) {
        this.primary = primary2;
        return this;
    }

    public int getOnPrimary() {
        return this.onPrimary;
    }

    public void setOnPrimary(int onPrimary2) {
        this.onPrimary = onPrimary2;
    }

    public Scheme withOnPrimary(int onPrimary2) {
        this.onPrimary = onPrimary2;
        return this;
    }

    public int getPrimaryContainer() {
        return this.primaryContainer;
    }

    public void setPrimaryContainer(int primaryContainer2) {
        this.primaryContainer = primaryContainer2;
    }

    public Scheme withPrimaryContainer(int primaryContainer2) {
        this.primaryContainer = primaryContainer2;
        return this;
    }

    public int getOnPrimaryContainer() {
        return this.onPrimaryContainer;
    }

    public void setOnPrimaryContainer(int onPrimaryContainer2) {
        this.onPrimaryContainer = onPrimaryContainer2;
    }

    public Scheme withOnPrimaryContainer(int onPrimaryContainer2) {
        this.onPrimaryContainer = onPrimaryContainer2;
        return this;
    }

    public int getSecondary() {
        return this.secondary;
    }

    public void setSecondary(int secondary2) {
        this.secondary = secondary2;
    }

    public Scheme withSecondary(int secondary2) {
        this.secondary = secondary2;
        return this;
    }

    public int getOnSecondary() {
        return this.onSecondary;
    }

    public void setOnSecondary(int onSecondary2) {
        this.onSecondary = onSecondary2;
    }

    public Scheme withOnSecondary(int onSecondary2) {
        this.onSecondary = onSecondary2;
        return this;
    }

    public int getSecondaryContainer() {
        return this.secondaryContainer;
    }

    public void setSecondaryContainer(int secondaryContainer2) {
        this.secondaryContainer = secondaryContainer2;
    }

    public Scheme withSecondaryContainer(int secondaryContainer2) {
        this.secondaryContainer = secondaryContainer2;
        return this;
    }

    public int getOnSecondaryContainer() {
        return this.onSecondaryContainer;
    }

    public void setOnSecondaryContainer(int onSecondaryContainer2) {
        this.onSecondaryContainer = onSecondaryContainer2;
    }

    public Scheme withOnSecondaryContainer(int onSecondaryContainer2) {
        this.onSecondaryContainer = onSecondaryContainer2;
        return this;
    }

    public int getTertiary() {
        return this.tertiary;
    }

    public void setTertiary(int tertiary2) {
        this.tertiary = tertiary2;
    }

    public Scheme withTertiary(int tertiary2) {
        this.tertiary = tertiary2;
        return this;
    }

    public int getOnTertiary() {
        return this.onTertiary;
    }

    public void setOnTertiary(int onTertiary2) {
        this.onTertiary = onTertiary2;
    }

    public Scheme withOnTertiary(int onTertiary2) {
        this.onTertiary = onTertiary2;
        return this;
    }

    public int getTertiaryContainer() {
        return this.tertiaryContainer;
    }

    public void setTertiaryContainer(int tertiaryContainer2) {
        this.tertiaryContainer = tertiaryContainer2;
    }

    public Scheme withTertiaryContainer(int tertiaryContainer2) {
        this.tertiaryContainer = tertiaryContainer2;
        return this;
    }

    public int getOnTertiaryContainer() {
        return this.onTertiaryContainer;
    }

    public void setOnTertiaryContainer(int onTertiaryContainer2) {
        this.onTertiaryContainer = onTertiaryContainer2;
    }

    public Scheme withOnTertiaryContainer(int onTertiaryContainer2) {
        this.onTertiaryContainer = onTertiaryContainer2;
        return this;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int error2) {
        this.error = error2;
    }

    public Scheme withError(int error2) {
        this.error = error2;
        return this;
    }

    public int getOnError() {
        return this.onError;
    }

    public void setOnError(int onError2) {
        this.onError = onError2;
    }

    public Scheme withOnError(int onError2) {
        this.onError = onError2;
        return this;
    }

    public int getErrorContainer() {
        return this.errorContainer;
    }

    public void setErrorContainer(int errorContainer2) {
        this.errorContainer = errorContainer2;
    }

    public Scheme withErrorContainer(int errorContainer2) {
        this.errorContainer = errorContainer2;
        return this;
    }

    public int getOnErrorContainer() {
        return this.onErrorContainer;
    }

    public void setOnErrorContainer(int onErrorContainer2) {
        this.onErrorContainer = onErrorContainer2;
    }

    public Scheme withOnErrorContainer(int onErrorContainer2) {
        this.onErrorContainer = onErrorContainer2;
        return this;
    }

    public int getBackground() {
        return this.background;
    }

    public void setBackground(int background2) {
        this.background = background2;
    }

    public Scheme withBackground(int background2) {
        this.background = background2;
        return this;
    }

    public int getOnBackground() {
        return this.onBackground;
    }

    public void setOnBackground(int onBackground2) {
        this.onBackground = onBackground2;
    }

    public Scheme withOnBackground(int onBackground2) {
        this.onBackground = onBackground2;
        return this;
    }

    public int getSurface() {
        return this.surface;
    }

    public void setSurface(int surface2) {
        this.surface = surface2;
    }

    public Scheme withSurface(int surface2) {
        this.surface = surface2;
        return this;
    }

    public int getOnSurface() {
        return this.onSurface;
    }

    public void setOnSurface(int onSurface2) {
        this.onSurface = onSurface2;
    }

    public Scheme withOnSurface(int onSurface2) {
        this.onSurface = onSurface2;
        return this;
    }

    public int getSurfaceVariant() {
        return this.surfaceVariant;
    }

    public void setSurfaceVariant(int surfaceVariant2) {
        this.surfaceVariant = surfaceVariant2;
    }

    public Scheme withSurfaceVariant(int surfaceVariant2) {
        this.surfaceVariant = surfaceVariant2;
        return this;
    }

    public int getOnSurfaceVariant() {
        return this.onSurfaceVariant;
    }

    public void setOnSurfaceVariant(int onSurfaceVariant2) {
        this.onSurfaceVariant = onSurfaceVariant2;
    }

    public Scheme withOnSurfaceVariant(int onSurfaceVariant2) {
        this.onSurfaceVariant = onSurfaceVariant2;
        return this;
    }

    public int getOutline() {
        return this.outline;
    }

    public void setOutline(int outline2) {
        this.outline = outline2;
    }

    public Scheme withOutline(int outline2) {
        this.outline = outline2;
        return this;
    }

    public int getOutlineVariant() {
        return this.outlineVariant;
    }

    public void setOutlineVariant(int outlineVariant2) {
        this.outlineVariant = outlineVariant2;
    }

    public Scheme withOutlineVariant(int outlineVariant2) {
        this.outlineVariant = outlineVariant2;
        return this;
    }

    public int getShadow() {
        return this.shadow;
    }

    public void setShadow(int shadow2) {
        this.shadow = shadow2;
    }

    public Scheme withShadow(int shadow2) {
        this.shadow = shadow2;
        return this;
    }

    public int getScrim() {
        return this.scrim;
    }

    public void setScrim(int scrim2) {
        this.scrim = scrim2;
    }

    public Scheme withScrim(int scrim2) {
        this.scrim = scrim2;
        return this;
    }

    public int getInverseSurface() {
        return this.inverseSurface;
    }

    public void setInverseSurface(int inverseSurface2) {
        this.inverseSurface = inverseSurface2;
    }

    public Scheme withInverseSurface(int inverseSurface2) {
        this.inverseSurface = inverseSurface2;
        return this;
    }

    public int getInverseOnSurface() {
        return this.inverseOnSurface;
    }

    public void setInverseOnSurface(int inverseOnSurface2) {
        this.inverseOnSurface = inverseOnSurface2;
    }

    public Scheme withInverseOnSurface(int inverseOnSurface2) {
        this.inverseOnSurface = inverseOnSurface2;
        return this;
    }

    public int getInversePrimary() {
        return this.inversePrimary;
    }

    public void setInversePrimary(int inversePrimary2) {
        this.inversePrimary = inversePrimary2;
    }

    public Scheme withInversePrimary(int inversePrimary2) {
        this.inversePrimary = inversePrimary2;
        return this;
    }

    public String toString() {
        return "Scheme{primary=" + this.primary + ", onPrimary=" + this.onPrimary + ", primaryContainer=" + this.primaryContainer + ", onPrimaryContainer=" + this.onPrimaryContainer + ", secondary=" + this.secondary + ", onSecondary=" + this.onSecondary + ", secondaryContainer=" + this.secondaryContainer + ", onSecondaryContainer=" + this.onSecondaryContainer + ", tertiary=" + this.tertiary + ", onTertiary=" + this.onTertiary + ", tertiaryContainer=" + this.tertiaryContainer + ", onTertiaryContainer=" + this.onTertiaryContainer + ", error=" + this.error + ", onError=" + this.onError + ", errorContainer=" + this.errorContainer + ", onErrorContainer=" + this.onErrorContainer + ", background=" + this.background + ", onBackground=" + this.onBackground + ", surface=" + this.surface + ", onSurface=" + this.onSurface + ", surfaceVariant=" + this.surfaceVariant + ", onSurfaceVariant=" + this.onSurfaceVariant + ", outline=" + this.outline + ", outlineVariant=" + this.outlineVariant + ", shadow=" + this.shadow + ", scrim=" + this.scrim + ", inverseSurface=" + this.inverseSurface + ", inverseOnSurface=" + this.inverseOnSurface + ", inversePrimary=" + this.inversePrimary + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scheme) || !super.equals(object)) {
            return false;
        }
        Scheme scheme = (Scheme) object;
        if (this.primary == scheme.primary && this.onPrimary == scheme.onPrimary && this.primaryContainer == scheme.primaryContainer && this.onPrimaryContainer == scheme.onPrimaryContainer && this.secondary == scheme.secondary && this.onSecondary == scheme.onSecondary && this.secondaryContainer == scheme.secondaryContainer && this.onSecondaryContainer == scheme.onSecondaryContainer && this.tertiary == scheme.tertiary && this.onTertiary == scheme.onTertiary && this.tertiaryContainer == scheme.tertiaryContainer && this.onTertiaryContainer == scheme.onTertiaryContainer && this.error == scheme.error && this.onError == scheme.onError && this.errorContainer == scheme.errorContainer && this.onErrorContainer == scheme.onErrorContainer && this.background == scheme.background && this.onBackground == scheme.onBackground && this.surface == scheme.surface && this.onSurface == scheme.onSurface && this.surfaceVariant == scheme.surfaceVariant && this.onSurfaceVariant == scheme.onSurfaceVariant && this.outline == scheme.outline && this.outlineVariant == scheme.outlineVariant && this.shadow == scheme.shadow && this.scrim == scheme.scrim && this.inverseSurface == scheme.inverseSurface && this.inverseOnSurface == scheme.inverseOnSurface && this.inversePrimary == scheme.inversePrimary) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((super.hashCode() * 31) + this.primary) * 31) + this.onPrimary) * 31) + this.primaryContainer) * 31) + this.onPrimaryContainer) * 31) + this.secondary) * 31) + this.onSecondary) * 31) + this.secondaryContainer) * 31) + this.onSecondaryContainer) * 31) + this.tertiary) * 31) + this.onTertiary) * 31) + this.tertiaryContainer) * 31) + this.onTertiaryContainer) * 31) + this.error) * 31) + this.onError) * 31) + this.errorContainer) * 31) + this.onErrorContainer) * 31) + this.background) * 31) + this.onBackground) * 31) + this.surface) * 31) + this.onSurface) * 31) + this.surfaceVariant) * 31) + this.onSurfaceVariant) * 31) + this.outline) * 31) + this.outlineVariant) * 31) + this.shadow) * 31) + this.scrim) * 31) + this.inverseSurface) * 31) + this.inverseOnSurface) * 31) + this.inversePrimary;
    }
}

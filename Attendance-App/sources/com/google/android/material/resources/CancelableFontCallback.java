package com.google.android.material.resources;

import android.graphics.Typeface;

public final class CancelableFontCallback extends TextAppearanceFontCallback {
    private final ApplyFont applyFont;
    private boolean cancelled;
    private final Typeface fallbackFont;

    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont2, Typeface fallbackFont2) {
        this.fallbackFont = fallbackFont2;
        this.applyFont = applyFont2;
    }

    public void onFontRetrieved(Typeface font, boolean fontResolvedSynchronously) {
        updateIfNotCancelled(font);
    }

    public void onFontRetrievalFailed(int reason) {
        updateIfNotCancelled(this.fallbackFont);
    }

    public void cancel() {
        this.cancelled = true;
    }

    private void updateIfNotCancelled(Typeface updatedFont) {
        if (!this.cancelled) {
            this.applyFont.apply(updatedFont);
        }
    }
}

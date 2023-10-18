package com.itextpdf.kernel.pdf;

public class PdfDashPattern {
    private float dash;
    private float gap;
    private float phase;

    public PdfDashPattern() {
        this.dash = -1.0f;
        this.gap = -1.0f;
        this.phase = -1.0f;
    }

    public PdfDashPattern(float dash2) {
        this.dash = -1.0f;
        this.gap = -1.0f;
        this.phase = -1.0f;
        this.dash = dash2;
    }

    public PdfDashPattern(float dash2, float gap2) {
        this.dash = -1.0f;
        this.gap = -1.0f;
        this.phase = -1.0f;
        this.dash = dash2;
        this.gap = gap2;
    }

    public PdfDashPattern(float dash2, float gap2, float phase2) {
        this(dash2, gap2);
        this.phase = phase2;
    }

    public float getDash() {
        return this.dash;
    }

    public float getGap() {
        return this.gap;
    }

    public float getPhase() {
        return this.phase;
    }
}

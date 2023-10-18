package com.itextpdf.layout.property;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;

public class TransparentColor {
    private Color color;
    private float opacity;

    public TransparentColor(Color color2) {
        this.color = color2;
        this.opacity = 1.0f;
    }

    public TransparentColor(Color color2, float opacity2) {
        this.color = color2;
        this.opacity = opacity2;
    }

    public Color getColor() {
        return this.color;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void applyFillTransparency(PdfCanvas canvas) {
        applyTransparency(canvas, false);
    }

    public void applyStrokeTransparency(PdfCanvas canvas) {
        applyTransparency(canvas, true);
    }

    private void applyTransparency(PdfCanvas canvas, boolean isStroke) {
        if (isTransparent()) {
            PdfExtGState extGState = new PdfExtGState();
            if (isStroke) {
                extGState.setStrokeOpacity(this.opacity);
            } else {
                extGState.setFillOpacity(this.opacity);
            }
            canvas.setExtGState(extGState);
        }
    }

    private boolean isTransparent() {
        return this.opacity < 1.0f;
    }
}

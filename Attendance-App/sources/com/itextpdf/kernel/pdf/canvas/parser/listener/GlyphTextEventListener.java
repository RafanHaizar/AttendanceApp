package com.itextpdf.kernel.pdf.canvas.parser.listener;

public class GlyphTextEventListener extends GlyphEventListener implements ITextExtractionStrategy {
    public GlyphTextEventListener(ITextExtractionStrategy delegate) {
        super(delegate);
    }

    public String getResultantText() {
        if (this.delegate instanceof ITextExtractionStrategy) {
            return ((ITextExtractionStrategy) this.delegate).getResultantText();
        }
        return null;
    }
}

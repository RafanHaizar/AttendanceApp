package com.itextpdf.layout.layout;

import com.itextpdf.layout.renderer.IRenderer;

public class TextLayoutResult extends MinMaxWidthLayoutResult {
    protected boolean splitForcedByNewline;
    protected boolean wordHasBeenSplit;

    public TextLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
        super(status, occupiedArea, splitRenderer, overflowRenderer);
    }

    public TextLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
        super(status, occupiedArea, splitRenderer, overflowRenderer, cause);
    }

    public boolean isWordHasBeenSplit() {
        return this.wordHasBeenSplit;
    }

    public TextLayoutResult setWordHasBeenSplit(boolean wordHasBeenSplit2) {
        this.wordHasBeenSplit = wordHasBeenSplit2;
        return this;
    }

    public boolean isSplitForcedByNewline() {
        return this.splitForcedByNewline;
    }

    public TextLayoutResult setSplitForcedByNewline(boolean isSplitForcedByNewline) {
        this.splitForcedByNewline = isSplitForcedByNewline;
        return this;
    }
}

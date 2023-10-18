package com.itextpdf.kernel.pdf.canvas.parser.listener;

public class TextChunk {
    protected final ITextChunkLocation location;
    protected final String text;

    public TextChunk(String string, ITextChunkLocation loc) {
        this.text = string;
        this.location = loc;
    }

    public String getText() {
        return this.text;
    }

    public ITextChunkLocation getLocation() {
        return this.location;
    }

    /* access modifiers changed from: package-private */
    public void printDiagnostics() {
        System.out.println("Text (@" + this.location.getStartLocation() + " -> " + this.location.getEndLocation() + "): " + this.text);
        System.out.println("orientationMagnitude: " + this.location.orientationMagnitude());
        System.out.println("distPerpendicular: " + this.location.distPerpendicular());
        System.out.println("distParallel: " + this.location.distParallelStart());
    }

    /* access modifiers changed from: package-private */
    public boolean sameLine(TextChunk lastChunk) {
        return getLocation().sameLine(lastChunk.getLocation());
    }
}

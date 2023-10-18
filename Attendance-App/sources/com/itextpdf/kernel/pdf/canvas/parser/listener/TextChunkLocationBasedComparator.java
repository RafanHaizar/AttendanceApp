package com.itextpdf.kernel.pdf.canvas.parser.listener;

import java.util.Comparator;

class TextChunkLocationBasedComparator implements Comparator<TextChunk> {
    private Comparator<ITextChunkLocation> locationComparator;

    public TextChunkLocationBasedComparator(Comparator<ITextChunkLocation> locationComparator2) {
        this.locationComparator = locationComparator2;
    }

    public int compare(TextChunk o1, TextChunk o2) {
        return this.locationComparator.compare(o1.location, o2.location);
    }
}

package com.itextpdf.p026io.source;

import java.io.IOException;

/* renamed from: com.itextpdf.io.source.IndependentRandomAccessSource */
public class IndependentRandomAccessSource implements IRandomAccessSource {
    private final IRandomAccessSource source;

    public IndependentRandomAccessSource(IRandomAccessSource source2) {
        this.source = source2;
    }

    public int get(long position) throws IOException {
        return this.source.get(position);
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        return this.source.get(position, bytes, off, len);
    }

    public long length() {
        return this.source.length();
    }

    public void close() throws IOException {
    }
}

package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.source.WindowRandomAccessSource */
public class WindowRandomAccessSource implements IRandomAccessSource, Serializable {
    private static final long serialVersionUID = -8539987600466289182L;
    private final long length;
    private final long offset;
    private final IRandomAccessSource source;

    public WindowRandomAccessSource(IRandomAccessSource source2, long offset2) {
        this(source2, offset2, source2.length() - offset2);
    }

    public WindowRandomAccessSource(IRandomAccessSource source2, long offset2, long length2) {
        this.source = source2;
        this.offset = offset2;
        this.length = length2;
    }

    public int get(long position) throws IOException {
        if (position >= this.length) {
            return -1;
        }
        return this.source.get(this.offset + position);
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        long j = this.length;
        if (position >= j) {
            return -1;
        }
        return this.source.get(this.offset + position, bytes, off, (int) Math.min((long) len, j - position));
    }

    public long length() {
        return this.length;
    }

    public void close() throws IOException {
        this.source.close();
    }
}

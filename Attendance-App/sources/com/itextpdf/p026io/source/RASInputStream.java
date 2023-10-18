package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.itextpdf.io.source.RASInputStream */
public class RASInputStream extends InputStream {
    private long position = 0;
    private final IRandomAccessSource source;

    public RASInputStream(IRandomAccessSource source2) {
        this.source = source2;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int count = this.source.get(this.position, b, off, len);
        this.position += (long) count;
        return count;
    }

    public int read() throws IOException {
        IRandomAccessSource iRandomAccessSource = this.source;
        long j = this.position;
        this.position = 1 + j;
        return iRandomAccessSource.get(j);
    }
}

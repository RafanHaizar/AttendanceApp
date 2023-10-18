package com.itextpdf.p026io.source;

import java.io.IOException;

/* renamed from: com.itextpdf.io.source.ThreadSafeRandomAccessSource */
public class ThreadSafeRandomAccessSource implements IRandomAccessSource {
    private final Object lockObj = new Object();
    private final IRandomAccessSource source;

    public ThreadSafeRandomAccessSource(IRandomAccessSource source2) {
        this.source = source2;
    }

    public int get(long position) throws IOException {
        int i;
        synchronized (this.lockObj) {
            i = this.source.get(position);
        }
        return i;
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        int i;
        synchronized (this.lockObj) {
            i = this.source.get(position, bytes, off, len);
        }
        return i;
    }

    public long length() {
        long length;
        synchronized (this.lockObj) {
            length = this.source.length();
        }
        return length;
    }

    public void close() throws IOException {
        synchronized (this.lockObj) {
            this.source.close();
        }
    }
}

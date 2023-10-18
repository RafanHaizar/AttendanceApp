package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.RandomAccessFile;

/* renamed from: com.itextpdf.io.source.RAFRandomAccessSource */
class RAFRandomAccessSource implements IRandomAccessSource {
    private final long length;
    private final RandomAccessFile raf;

    public RAFRandomAccessSource(RandomAccessFile raf2) throws IOException {
        this.raf = raf2;
        this.length = raf2.length();
    }

    public int get(long position) throws IOException {
        if (position > this.length) {
            return -1;
        }
        if (this.raf.getFilePointer() != position) {
            this.raf.seek(position);
        }
        return this.raf.read();
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        if (position > this.length) {
            return -1;
        }
        if (this.raf.getFilePointer() != position) {
            this.raf.seek(position);
        }
        return this.raf.read(bytes, off, len);
    }

    public long length() {
        return this.length;
    }

    public void close() throws IOException {
        this.raf.close();
    }
}

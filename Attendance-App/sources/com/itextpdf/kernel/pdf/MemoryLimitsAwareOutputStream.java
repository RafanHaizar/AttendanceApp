package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

class MemoryLimitsAwareOutputStream extends ByteArrayOutputStream {
    private static final int DEFAULT_MAX_STREAM_SIZE = 2147483639;
    private int maxStreamSize = DEFAULT_MAX_STREAM_SIZE;

    public MemoryLimitsAwareOutputStream() {
    }

    public MemoryLimitsAwareOutputStream(int size) {
        super(size);
    }

    public long getMaxStreamSize() {
        return (long) this.maxStreamSize;
    }

    public MemoryLimitsAwareOutputStream setMaxStreamSize(int maxStreamSize2) {
        this.maxStreamSize = maxStreamSize2;
        return this;
    }

    public synchronized void write(byte[] b, int off, int len) {
        if (off >= 0) {
            if (off <= b.length && len >= 0 && (off + len) - b.length <= 0) {
                int minCapacity = this.count + len;
                if (minCapacity < 0) {
                    throw new MemoryLimitsAwareException(PdfException.DuringDecompressionSingleStreamOccupiedMoreThanMaxIntegerValue);
                } else if (minCapacity <= this.maxStreamSize) {
                    int newCapacity = this.buf.length << 1;
                    if (newCapacity < 0 || newCapacity - minCapacity < 0) {
                        newCapacity = minCapacity;
                    }
                    int newCapacity2 = this.maxStreamSize;
                    if (newCapacity - newCapacity2 > 0) {
                        this.buf = Arrays.copyOf(this.buf, newCapacity2);
                    }
                    super.write(b, off, len);
                } else {
                    throw new MemoryLimitsAwareException(PdfException.DuringDecompressionSingleStreamOccupiedMoreMemoryThanAllowed);
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}

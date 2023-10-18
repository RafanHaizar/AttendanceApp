package com.itextpdf.kernel.xmp.impl;

import java.io.IOException;
import java.io.OutputStream;

public final class CountOutputStream extends OutputStream {
    private int bytesWritten = 0;
    private final OutputStream output;

    CountOutputStream(OutputStream output2) {
        this.output = output2;
    }

    public void write(byte[] buf, int off, int len) throws IOException {
        this.output.write(buf, off, len);
        this.bytesWritten += len;
    }

    public void write(byte[] buf) throws IOException {
        this.output.write(buf);
        this.bytesWritten += buf.length;
    }

    public void write(int b) throws IOException {
        this.output.write(b);
        this.bytesWritten++;
    }

    public int getBytesWritten() {
        return this.bytesWritten;
    }
}

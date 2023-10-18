package org.bouncycastle.util.p023io;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.bouncycastle.util.io.SimpleOutputStream */
public abstract class SimpleOutputStream extends OutputStream {
    public void close() {
    }

    public void flush() {
    }

    public void write(int i) throws IOException {
        write(new byte[]{(byte) i}, 0, 1);
    }
}

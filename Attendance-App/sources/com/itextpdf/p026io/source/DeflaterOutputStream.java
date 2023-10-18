package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

/* renamed from: com.itextpdf.io.source.DeflaterOutputStream */
public class DeflaterOutputStream extends java.util.zip.DeflaterOutputStream {
    public DeflaterOutputStream(OutputStream out, int level, int size) {
        super(out, new Deflater(level), size);
    }

    public DeflaterOutputStream(OutputStream out, int level) {
        this(out, level, 512);
    }

    public DeflaterOutputStream(OutputStream out) {
        this(out, -1);
    }

    public void close() throws IOException {
        finish();
        super.close();
    }

    public void finish() throws IOException {
        super.finish();
        this.def.end();
    }
}

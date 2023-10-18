package com.itextpdf.p026io.source;

import java.io.IOException;

/* renamed from: com.itextpdf.io.source.IRandomAccessSource */
public interface IRandomAccessSource {
    void close() throws IOException;

    int get(long j) throws IOException;

    int get(long j, byte[] bArr, int i, int i2) throws IOException;

    long length();
}

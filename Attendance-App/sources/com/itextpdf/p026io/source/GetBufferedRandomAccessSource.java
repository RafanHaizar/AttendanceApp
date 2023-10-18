package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.Serializable;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.source.GetBufferedRandomAccessSource */
public class GetBufferedRandomAccessSource implements IRandomAccessSource, Serializable {
    private static final long serialVersionUID = -8922625738755763494L;
    private final byte[] getBuffer;
    private long getBufferEnd = -1;
    private long getBufferStart = -1;
    private final IRandomAccessSource source;

    public GetBufferedRandomAccessSource(IRandomAccessSource source2) {
        this.source = source2;
        this.getBuffer = new byte[((int) Math.min(Math.max(source2.length() / 4, 1), 4096))];
        this.getBufferStart = -1;
        this.getBufferEnd = -1;
    }

    public int get(long position) throws IOException {
        if (position < this.getBufferStart || position > this.getBufferEnd) {
            IRandomAccessSource iRandomAccessSource = this.source;
            byte[] bArr = this.getBuffer;
            int count = iRandomAccessSource.get(position, bArr, 0, bArr.length);
            if (count == -1) {
                return -1;
            }
            this.getBufferStart = position;
            this.getBufferEnd = (((long) count) + position) - 1;
        }
        return this.getBuffer[(int) (position - this.getBufferStart)] & UByte.MAX_VALUE;
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        return this.source.get(position, bytes, off, len);
    }

    public long length() {
        return this.source.length();
    }

    public void close() throws IOException {
        this.source.close();
        this.getBufferStart = -1;
        this.getBufferEnd = -1;
    }
}

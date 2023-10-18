package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.Serializable;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.source.ArrayRandomAccessSource */
class ArrayRandomAccessSource implements IRandomAccessSource, Serializable {
    private static final long serialVersionUID = 8497059230517630513L;
    private byte[] array;

    public ArrayRandomAccessSource(byte[] array2) {
        if (array2 != null) {
            this.array = array2;
            return;
        }
        throw new IllegalArgumentException("Passed byte array can not be null.");
    }

    public int get(long offset) {
        byte[] bArr = this.array;
        if (offset >= ((long) bArr.length)) {
            return -1;
        }
        return bArr[(int) offset] & UByte.MAX_VALUE;
    }

    public int get(long offset, byte[] bytes, int off, int len) {
        byte[] bArr = this.array;
        if (bArr == null) {
            throw new IllegalStateException("Already closed");
        } else if (offset >= ((long) bArr.length)) {
            return -1;
        } else {
            if (((long) len) + offset > ((long) bArr.length)) {
                len = (int) (((long) bArr.length) - offset);
            }
            System.arraycopy(bArr, (int) offset, bytes, off, len);
            return len;
        }
    }

    public long length() {
        return (long) this.array.length;
    }

    public void close() throws IOException {
        this.array = null;
    }
}

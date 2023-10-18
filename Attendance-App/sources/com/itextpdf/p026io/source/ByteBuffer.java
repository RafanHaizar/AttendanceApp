package com.itextpdf.p026io.source;

import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.source.ByteBuffer */
public class ByteBuffer implements Serializable {
    private static final byte[] bytes = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    private static final long serialVersionUID = -4380712536267312975L;
    private byte[] buffer;
    protected int count;

    public ByteBuffer() {
        this(128);
    }

    public ByteBuffer(int size) {
        this.buffer = new byte[(size < 1 ? 128 : size)];
    }

    public static int getHex(int v) {
        if (v >= 48 && v <= 57) {
            return v - 48;
        }
        if (v >= 65 && v <= 70) {
            return (v - 65) + 10;
        }
        if (v < 97 || v > 102) {
            return -1;
        }
        return (v - 97) + 10;
    }

    public ByteBuffer append(byte b) {
        int newCount = this.count + 1;
        byte[] bArr = this.buffer;
        if (newCount > bArr.length) {
            byte[] newBuffer = new byte[Math.max(bArr.length << 1, newCount)];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.count);
            this.buffer = newBuffer;
        }
        this.buffer[this.count] = b;
        this.count = newCount;
        return this;
    }

    public ByteBuffer append(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0 || len == 0) {
            return this;
        }
        int newCount = this.count + len;
        byte[] bArr = this.buffer;
        if (newCount > bArr.length) {
            byte[] newBuffer = new byte[Math.max(bArr.length << 1, newCount)];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.count);
            this.buffer = newBuffer;
        }
        System.arraycopy(b, off, this.buffer, this.count, len);
        this.count = newCount;
        return this;
    }

    public ByteBuffer append(byte[] b) {
        return append(b, 0, b.length);
    }

    public ByteBuffer append(int b) {
        return append((byte) b);
    }

    public ByteBuffer append(String str) {
        return append(ByteUtils.getIsoBytes(str));
    }

    public ByteBuffer appendHex(byte b) {
        byte[] bArr = bytes;
        append(bArr[(b >> 4) & 15]);
        return append(bArr[b & 15]);
    }

    public byte get(int index) {
        if (index < this.count) {
            return this.buffer[index];
        }
        throw new IndexOutOfBoundsException(MessageFormatUtil.format("Index: {0}, Size: {1}", Integer.valueOf(index), Integer.valueOf(this.count)));
    }

    public byte[] getInternalBuffer() {
        return this.buffer;
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int capacity() {
        return this.buffer.length;
    }

    public ByteBuffer reset() {
        this.count = 0;
        return this;
    }

    public byte[] toByteArray(int off, int len) {
        byte[] newBuf = new byte[len];
        System.arraycopy(this.buffer, off, newBuf, 0, len);
        return newBuf;
    }

    public byte[] toByteArray() {
        return toByteArray(0, this.count);
    }

    public boolean startsWith(byte[] b) {
        if (size() < b.length) {
            return false;
        }
        for (int k = 0; k < b.length; k++) {
            if (this.buffer[k] != b[k]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer prepend(byte b) {
        byte[] bArr = this.buffer;
        int length = bArr.length;
        int i = this.count;
        bArr[(length - i) - 1] = b;
        this.count = i + 1;
        return this;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer prepend(byte[] b) {
        byte[] bArr = this.buffer;
        System.arraycopy(b, 0, bArr, (bArr.length - this.count) - b.length, b.length);
        this.count += b.length;
        return this;
    }
}

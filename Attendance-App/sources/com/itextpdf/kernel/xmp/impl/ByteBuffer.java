package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

public class ByteBuffer {
    private byte[] buffer;
    private String encoding;
    private int length;

    public ByteBuffer(int initialCapacity) {
        this.encoding = null;
        this.buffer = new byte[initialCapacity];
        this.length = 0;
    }

    public ByteBuffer(byte[] buffer2) {
        this.encoding = null;
        this.buffer = buffer2;
        this.length = buffer2.length;
    }

    public ByteBuffer(byte[] buffer2, int length2) {
        this.encoding = null;
        if (length2 <= buffer2.length) {
            this.buffer = buffer2;
            this.length = length2;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
    }

    public ByteBuffer(InputStream in) throws IOException {
        this.encoding = null;
        this.length = 0;
        this.buffer = new byte[16384];
        while (true) {
            int read = in.read(this.buffer, this.length, 16384);
            int read2 = read;
            if (read > 0) {
                int i = this.length + read2;
                this.length = i;
                if (read2 == 16384) {
                    ensureCapacity(i + 16384);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public ByteBuffer(byte[] buffer2, int offset, int length2) {
        this.encoding = null;
        if (length2 <= buffer2.length - offset) {
            byte[] bArr = new byte[length2];
            this.buffer = bArr;
            System.arraycopy(buffer2, offset, bArr, 0, length2);
            this.length = length2;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
    }

    public InputStream getByteStream() {
        return new ByteArrayInputStream(this.buffer, 0, this.length);
    }

    public int length() {
        return this.length;
    }

    public byte byteAt(int index) {
        if (index < this.length) {
            return this.buffer[index];
        }
        throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
    }

    public int charAt(int index) {
        if (index < this.length) {
            return this.buffer[index] & UByte.MAX_VALUE;
        }
        throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
    }

    public void append(byte b) {
        ensureCapacity(this.length + 1);
        byte[] bArr = this.buffer;
        int i = this.length;
        this.length = i + 1;
        bArr[i] = b;
    }

    public void append(byte[] bytes, int offset, int len) {
        ensureCapacity(this.length + len);
        System.arraycopy(bytes, offset, this.buffer, this.length, len);
        this.length += len;
    }

    public void append(byte[] bytes) {
        append(bytes, 0, bytes.length);
    }

    public void append(ByteBuffer anotherBuffer) {
        append(anotherBuffer.buffer, 0, anotherBuffer.length);
    }

    public String getEncoding() {
        if (this.encoding == null) {
            int i = this.length;
            if (i < 2) {
                this.encoding = PdfEncodings.UTF8;
            } else {
                byte[] bArr = this.buffer;
                byte b = bArr[0];
                if (b == 0) {
                    if (i < 4 || bArr[1] != 0) {
                        this.encoding = "UTF-16BE";
                    } else if ((bArr[2] & UByte.MAX_VALUE) == 254 && (bArr[3] & UByte.MAX_VALUE) == 255) {
                        this.encoding = "UTF-32BE";
                    } else {
                        this.encoding = "UTF-32";
                    }
                } else if ((b & UByte.MAX_VALUE) < 128) {
                    if (bArr[1] != 0) {
                        this.encoding = PdfEncodings.UTF8;
                    } else if (i < 4 || bArr[2] != 0) {
                        this.encoding = "UTF-16LE";
                    } else {
                        this.encoding = "UTF-32LE";
                    }
                } else if ((b & UByte.MAX_VALUE) == 239) {
                    this.encoding = PdfEncodings.UTF8;
                } else if ((b & UByte.MAX_VALUE) == 254) {
                    this.encoding = "UTF-16";
                } else if (i < 4 || bArr[2] != 0) {
                    this.encoding = "UTF-16";
                } else {
                    this.encoding = "UTF-32";
                }
            }
        }
        return this.encoding;
    }

    private void ensureCapacity(int requestedLength) {
        if (requestedLength > this.buffer.length) {
            byte[] oldBuf = this.buffer;
            byte[] bArr = new byte[(oldBuf.length * 2)];
            this.buffer = bArr;
            System.arraycopy(oldBuf, 0, bArr, 0, oldBuf.length);
        }
    }
}

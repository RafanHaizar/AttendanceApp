package com.itextpdf.p026io.source;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import kotlin.UByte;
import org.bouncycastle.asn1.cmc.BodyPartID;

/* renamed from: com.itextpdf.io.source.RandomAccessFileOrArray */
public class RandomAccessFileOrArray implements DataInput, Serializable {
    public static boolean plainRandomAccess = false;
    private static final long serialVersionUID = -169314546265954851L;
    private byte back;
    private IRandomAccessSource byteSource;
    private long byteSourcePosition;
    private boolean isBack = false;

    public RandomAccessFileOrArray(IRandomAccessSource byteSource2) {
        this.byteSource = byteSource2;
    }

    public RandomAccessFileOrArray createView() {
        ensureByteSourceIsThreadSafe();
        return new RandomAccessFileOrArray(new IndependentRandomAccessSource(this.byteSource));
    }

    public IRandomAccessSource createSourceView() {
        ensureByteSourceIsThreadSafe();
        return new IndependentRandomAccessSource(this.byteSource);
    }

    public void pushBack(byte b) {
        this.back = b;
        this.isBack = true;
    }

    public int read() throws IOException {
        if (this.isBack) {
            this.isBack = false;
            return this.back & UByte.MAX_VALUE;
        }
        IRandomAccessSource iRandomAccessSource = this.byteSource;
        long j = this.byteSourcePosition;
        this.byteSourcePosition = 1 + j;
        return iRandomAccessSource.get(j);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int byteSourceCount;
        if (len == 0) {
            return 0;
        }
        int count = 0;
        if (this.isBack && len > 0) {
            this.isBack = false;
            b[off] = this.back;
            len--;
            count = 0 + 1;
            off++;
        }
        if (len > 0 && (byteSourceCount = this.byteSource.get(this.byteSourcePosition, b, off, len)) > 0) {
            count += byteSourceCount;
            this.byteSourcePosition += (long) byteSourceCount;
        }
        if (count == 0) {
            return -1;
        }
        return count;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        int n = 0;
        do {
            int count = read(b, off + n, len - n);
            if (count >= 0) {
                n += count;
            } else {
                throw new EOFException();
            }
        } while (n < len);
    }

    public long skip(long n) throws IOException {
        if (n <= 0) {
            return 0;
        }
        int adj = 0;
        if (this.isBack) {
            this.isBack = false;
            if (n == 1) {
                return 1;
            }
            n--;
            adj = 1;
        }
        long pos = getPosition();
        long len = length();
        long newpos = pos + n;
        if (newpos > len) {
            newpos = len;
        }
        seek(newpos);
        return (newpos - pos) + ((long) adj);
    }

    public int skipBytes(int n) throws IOException {
        return (int) skip((long) n);
    }

    public void close() throws IOException {
        this.isBack = false;
        this.byteSource.close();
    }

    public long length() throws IOException {
        return this.byteSource.length();
    }

    public void seek(long pos) throws IOException {
        this.byteSourcePosition = pos;
        this.isBack = false;
    }

    public long getPosition() throws IOException {
        return this.byteSourcePosition - (this.isBack ? 1 : 0);
    }

    public boolean readBoolean() throws IOException {
        int ch = read();
        if (ch >= 0) {
            return ch != 0;
        }
        throw new EOFException();
    }

    public byte readByte() throws IOException {
        int ch = read();
        if (ch >= 0) {
            return (byte) ch;
        }
        throw new EOFException();
    }

    public int readUnsignedByte() throws IOException {
        int ch = read();
        if (ch >= 0) {
            return ch;
        }
        throw new EOFException();
    }

    public short readShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (short) ((ch1 << 8) + ch2);
        }
        throw new EOFException();
    }

    public final short readShortLE() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (short) ((ch2 << 8) + ch1);
        }
        throw new EOFException();
    }

    public int readUnsignedShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (ch1 << 8) + ch2;
        }
        throw new EOFException();
    }

    public final int readUnsignedShortLE() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (ch2 << 8) + ch1;
        }
        throw new EOFException();
    }

    public char readChar() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (char) ((ch1 << 8) + ch2);
        }
        throw new EOFException();
    }

    public final char readCharLE() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) >= 0) {
            return (char) ((ch2 << 8) + ch2);
        }
        throw new EOFException();
    }

    public int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) >= 0) {
            return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
        }
        throw new EOFException();
    }

    public final int readIntLE() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) >= 0) {
            return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
        }
        throw new EOFException();
    }

    public final long readUnsignedInt() throws IOException {
        long ch1 = (long) read();
        long ch2 = (long) read();
        long ch3 = (long) read();
        long ch4 = (long) read();
        if ((ch1 | ch2 | ch3 | ch4) >= 0) {
            return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
        }
        throw new EOFException();
    }

    public final long readUnsignedIntLE() throws IOException {
        long ch1 = (long) read();
        long ch2 = (long) read();
        long ch3 = (long) read();
        long ch4 = (long) read();
        if ((ch1 | ch2 | ch3 | ch4) >= 0) {
            return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
        }
        throw new EOFException();
    }

    public long readLong() throws IOException {
        return (((long) readInt()) << 32) + (((long) readInt()) & BodyPartID.bodyIdMax);
    }

    public final long readLongLE() throws IOException {
        return (((long) readIntLE()) << 32) + (((long) readIntLE()) & BodyPartID.bodyIdMax);
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public final float readFloatLE() throws IOException {
        return Float.intBitsToFloat(readIntLE());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final double readDoubleLE() throws IOException {
        return Double.longBitsToDouble(readLongLE());
    }

    public String readLine() throws IOException {
        StringBuilder input = new StringBuilder();
        int c = -1;
        boolean eol = false;
        while (!eol) {
            int read = read();
            c = read;
            switch (read) {
                case -1:
                case 10:
                    eol = true;
                    break;
                case 13:
                    eol = true;
                    long cur = getPosition();
                    if (read() == 10) {
                        break;
                    } else {
                        seek(cur);
                        break;
                    }
                default:
                    input.append((char) c);
                    break;
            }
        }
        if (c == -1 && input.length() == 0) {
            return null;
        }
        return input.toString();
    }

    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    public String readString(int length, String encoding) throws IOException {
        byte[] buf = new byte[length];
        readFully(buf);
        return new String(buf, encoding);
    }

    private void ensureByteSourceIsThreadSafe() {
        IRandomAccessSource iRandomAccessSource = this.byteSource;
        if (!(iRandomAccessSource instanceof ThreadSafeRandomAccessSource)) {
            this.byteSource = new ThreadSafeRandomAccessSource(iRandomAccessSource);
        }
    }
}

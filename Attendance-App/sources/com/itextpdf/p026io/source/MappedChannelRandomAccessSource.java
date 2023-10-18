package com.itextpdf.p026io.source;

import java.io.IOException;
import java.nio.channels.FileChannel;

/* renamed from: com.itextpdf.io.source.MappedChannelRandomAccessSource */
class MappedChannelRandomAccessSource implements IRandomAccessSource {
    private final FileChannel channel;
    private final long length;
    private final long offset;
    private ByteBufferRandomAccessSource source;

    public MappedChannelRandomAccessSource(FileChannel channel2, long offset2, long length2) {
        if (offset2 < 0) {
            throw new IllegalArgumentException(offset2 + " is negative");
        } else if (length2 > 0) {
            this.channel = channel2;
            this.offset = offset2;
            this.length = length2;
            this.source = null;
        } else {
            throw new IllegalArgumentException(length2 + " is zero or negative");
        }
    }

    /* access modifiers changed from: package-private */
    public void open() throws IOException {
        if (this.source == null) {
            if (this.channel.isOpen()) {
                this.source = new ByteBufferRandomAccessSource(this.channel.map(FileChannel.MapMode.READ_ONLY, this.offset, this.length));
                return;
            }
            throw new IllegalStateException("Channel is closed");
        }
    }

    public int get(long position) throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            return byteBufferRandomAccessSource.get(position);
        }
        throw new IOException("RandomAccessSource not opened");
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            return byteBufferRandomAccessSource.get(position, bytes, off, len);
        }
        throw new IOException("RandomAccessSource not opened");
    }

    public long length() {
        return this.length;
    }

    public void close() throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            byteBufferRandomAccessSource.close();
            this.source = null;
        }
    }

    public String toString() {
        return getClass().getName() + " (" + this.offset + ", " + this.length + ")";
    }
}

package com.itextpdf.p026io.codec.brotli.dec;

import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.codec.brotli.dec.BrotliInputStream */
public class BrotliInputStream extends InputStream {
    public static final int DEFAULT_INTERNAL_BUFFER_SIZE = 16384;
    private byte[] buffer;
    private int bufferOffset;
    private int remainingBufferBytes;
    private final State state;

    public BrotliInputStream(InputStream source) throws IOException {
        this(source, 16384, (byte[]) null);
    }

    public BrotliInputStream(InputStream source, int byteReadBufferSize) throws IOException {
        this(source, byteReadBufferSize, (byte[]) null);
    }

    public BrotliInputStream(InputStream source, int byteReadBufferSize, byte[] customDictionary) throws IOException {
        State state2 = new State();
        this.state = state2;
        if (byteReadBufferSize <= 0) {
            throw new IllegalArgumentException("Bad buffer size:" + byteReadBufferSize);
        } else if (source != null) {
            this.buffer = new byte[byteReadBufferSize];
            this.remainingBufferBytes = 0;
            this.bufferOffset = 0;
            try {
                State.setInput(state2, source);
                if (customDictionary != null) {
                    Decode.setCustomDictionary(state2, customDictionary);
                }
            } catch (BrotliRuntimeException ex) {
                throw new IOException("Brotli decoder initialization failed", ex);
            }
        } else {
            throw new IllegalArgumentException("source is null");
        }
    }

    public void close() throws IOException {
        State.close(this.state);
    }

    public int read() throws IOException {
        if (this.bufferOffset >= this.remainingBufferBytes) {
            byte[] bArr = this.buffer;
            int read = read(bArr, 0, bArr.length);
            this.remainingBufferBytes = read;
            this.bufferOffset = 0;
            if (read == -1) {
                return -1;
            }
        }
        byte[] bArr2 = this.buffer;
        int i = this.bufferOffset;
        this.bufferOffset = i + 1;
        return bArr2[i] & UByte.MAX_VALUE;
    }

    public int read(byte[] destBuffer, int destOffset, int destLen) throws IOException {
        if (destOffset < 0) {
            throw new IllegalArgumentException("Bad offset: " + destOffset);
        } else if (destLen < 0) {
            throw new IllegalArgumentException("Bad length: " + destLen);
        } else if (destOffset + destLen > destBuffer.length) {
            throw new IllegalArgumentException("Buffer overflow: " + (destOffset + destLen) + " > " + destBuffer.length);
        } else if (destLen == 0) {
            return 0;
        } else {
            int copyLen = Math.max(this.remainingBufferBytes - this.bufferOffset, 0);
            if (copyLen != 0) {
                copyLen = Math.min(copyLen, destLen);
                System.arraycopy(this.buffer, this.bufferOffset, destBuffer, destOffset, copyLen);
                this.bufferOffset += copyLen;
                destOffset += copyLen;
                destLen -= copyLen;
                if (destLen == 0) {
                    return copyLen;
                }
            }
            try {
                this.state.output = destBuffer;
                this.state.outputOffset = destOffset;
                this.state.outputLength = destLen;
                this.state.outputUsed = 0;
                Decode.decompress(this.state);
                if (this.state.outputUsed == 0) {
                    return -1;
                }
                return this.state.outputUsed + copyLen;
            } catch (BrotliRuntimeException ex) {
                throw new IOException("Brotli stream decoding failed", ex);
            }
        }
    }
}

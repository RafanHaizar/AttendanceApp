package com.itextpdf.p026io.codec.brotli.dec;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.itextpdf.io.codec.brotli.dec.BitReader */
final class BitReader {
    private static final int BYTE_BUFFER_SIZE = 4160;
    private static final int BYTE_READ_SIZE = 4096;
    private static final int CAPACITY = 1024;
    private static final int INT_BUFFER_SIZE = 1040;
    private static final int SLACK = 16;
    long accumulator;
    int bitOffset;
    private final byte[] byteBuffer = new byte[BYTE_BUFFER_SIZE];
    private boolean endOfStreamReached;
    private InputStream input;
    private final int[] intBuffer = new int[1040];
    private int intOffset;
    private final IntReader intReader = new IntReader();
    private int tailBytes = 0;

    BitReader() {
    }

    static void readMoreInput(BitReader br) {
        int i = br.intOffset;
        if (i > 1015) {
            if (!br.endOfStreamReached) {
                int readOffset = i << 2;
                int bytesRead = 4096 - readOffset;
                byte[] bArr = br.byteBuffer;
                System.arraycopy(bArr, readOffset, bArr, 0, bytesRead);
                br.intOffset = 0;
                while (true) {
                    if (bytesRead >= 4096) {
                        break;
                    }
                    try {
                        int len = br.input.read(br.byteBuffer, bytesRead, 4096 - bytesRead);
                        if (len <= 0) {
                            br.endOfStreamReached = true;
                            br.tailBytes = bytesRead;
                            bytesRead += 3;
                            break;
                        }
                        bytesRead += len;
                    } catch (IOException e) {
                        throw new BrotliRuntimeException("Failed to read input", e);
                    }
                }
                IntReader.convert(br.intReader, bytesRead >> 2);
            } else if (intAvailable(br) < -2) {
                throw new BrotliRuntimeException("No more input");
            }
        }
    }

    static void checkHealth(BitReader br, boolean endOfStream) {
        if (br.endOfStreamReached) {
            int byteOffset = ((br.intOffset << 2) + ((br.bitOffset + 7) >> 3)) - 8;
            int i = br.tailBytes;
            if (byteOffset > i) {
                throw new BrotliRuntimeException("Read after end");
            } else if (endOfStream && byteOffset != i) {
                throw new BrotliRuntimeException("Unused bytes after end");
            }
        }
    }

    static void fillBitWindow(BitReader br) {
        int i = br.bitOffset;
        if (i >= 32) {
            int[] iArr = br.intBuffer;
            int i2 = br.intOffset;
            br.intOffset = i2 + 1;
            br.accumulator = (((long) iArr[i2]) << 32) | (br.accumulator >>> 32);
            br.bitOffset = i - 32;
        }
    }

    static int readBits(BitReader br, int n) {
        fillBitWindow(br);
        long j = br.accumulator;
        int i = br.bitOffset;
        int val = ((int) (j >>> i)) & ((1 << n) - 1);
        br.bitOffset = i + n;
        return val;
    }

    static void init(BitReader br, InputStream input2) {
        if (br.input == null) {
            IntReader.init(br.intReader, br.byteBuffer, br.intBuffer);
            br.input = input2;
            br.accumulator = 0;
            br.bitOffset = 64;
            br.intOffset = 1024;
            br.endOfStreamReached = false;
            prepare(br);
            return;
        }
        throw new IllegalStateException("Bit reader already has associated input stream");
    }

    private static void prepare(BitReader br) {
        readMoreInput(br);
        checkHealth(br, false);
        fillBitWindow(br);
        fillBitWindow(br);
    }

    static void reload(BitReader br) {
        if (br.bitOffset == 64) {
            prepare(br);
        }
    }

    static void close(BitReader br) throws IOException {
        InputStream is = br.input;
        br.input = null;
        if (is != null) {
            is.close();
        }
    }

    static void jumpToByteBoundary(BitReader br) {
        int padding = (64 - br.bitOffset) & 7;
        if (padding != 0 && readBits(br, padding) != 0) {
            throw new BrotliRuntimeException("Corrupted padding bits");
        }
    }

    static int intAvailable(BitReader br) {
        int limit = 1024;
        if (br.endOfStreamReached) {
            limit = (br.tailBytes + 3) >> 2;
        }
        return limit - br.intOffset;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0021 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void copyBytes(com.itextpdf.p026io.codec.brotli.dec.BitReader r5, byte[] r6, int r7, int r8) {
        /*
            int r0 = r5.bitOffset
            r0 = r0 & 7
            if (r0 != 0) goto L_0x008b
        L_0x0006:
            int r0 = r5.bitOffset
            r1 = 64
            if (r0 == r1) goto L_0x001f
            if (r8 == 0) goto L_0x001f
            int r1 = r7 + 1
            long r2 = r5.accumulator
            long r2 = r2 >>> r0
            int r3 = (int) r2
            byte r2 = (byte) r3
            r6[r7] = r2
            int r0 = r0 + 8
            r5.bitOffset = r0
            int r8 = r8 + -1
            r7 = r1
            goto L_0x0006
        L_0x001f:
            if (r8 != 0) goto L_0x0022
            return
        L_0x0022:
            int r0 = intAvailable(r5)
            int r1 = r8 >> 2
            int r0 = java.lang.Math.min(r0, r1)
            if (r0 <= 0) goto L_0x0044
            int r1 = r5.intOffset
            int r1 = r1 << 2
            byte[] r2 = r5.byteBuffer
            int r3 = r0 << 2
            java.lang.System.arraycopy(r2, r1, r6, r7, r3)
            int r2 = r0 << 2
            int r7 = r7 + r2
            int r2 = r0 << 2
            int r8 = r8 - r2
            int r2 = r5.intOffset
            int r2 = r2 + r0
            r5.intOffset = r2
        L_0x0044:
            if (r8 != 0) goto L_0x0047
            return
        L_0x0047:
            int r1 = intAvailable(r5)
            if (r1 <= 0) goto L_0x006a
            fillBitWindow(r5)
        L_0x0050:
            if (r8 == 0) goto L_0x0065
            int r1 = r7 + 1
            long r2 = r5.accumulator
            int r4 = r5.bitOffset
            long r2 = r2 >>> r4
            int r3 = (int) r2
            byte r2 = (byte) r3
            r6[r7] = r2
            int r4 = r4 + 8
            r5.bitOffset = r4
            int r8 = r8 + -1
            r7 = r1
            goto L_0x0050
        L_0x0065:
            r1 = 0
            checkHealth(r5, r1)
            return
        L_0x006a:
            if (r8 <= 0) goto L_0x0089
            java.io.InputStream r1 = r5.input     // Catch:{ IOException -> 0x0080 }
            int r1 = r1.read(r6, r7, r8)     // Catch:{ IOException -> 0x0080 }
            r2 = -1
            if (r1 == r2) goto L_0x0078
            int r7 = r7 + r1
            int r8 = r8 - r1
            goto L_0x006a
        L_0x0078:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r2 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException     // Catch:{ IOException -> 0x0080 }
            java.lang.String r3 = "Unexpected end of input"
            r2.<init>(r3)     // Catch:{ IOException -> 0x0080 }
            throw r2     // Catch:{ IOException -> 0x0080 }
        L_0x0080:
            r1 = move-exception
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r2 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            java.lang.String r3 = "Failed to read input"
            r2.<init>(r3, r1)
            throw r2
        L_0x0089:
            return
        L_0x008b:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r0 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            java.lang.String r1 = "Unaligned copyBytes"
            r0.<init>(r1)
            goto L_0x0094
        L_0x0093:
            throw r0
        L_0x0094:
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.brotli.dec.BitReader.copyBytes(com.itextpdf.io.codec.brotli.dec.BitReader, byte[], int, int):void");
    }
}

package com.itextpdf.p026io.util;

import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.source.IRandomAccessSource;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.itextpdf.io.util.StreamUtil */
public final class StreamUtil {
    private static final int TRANSFER_SIZE = 65536;
    private static final byte[] escB = ByteUtils.getIsoBytes("\\b");
    private static final byte[] escF = ByteUtils.getIsoBytes("\\f");
    private static final byte[] escN = ByteUtils.getIsoBytes("\\n");
    private static final byte[] escR = ByteUtils.getIsoBytes("\\r");
    private static final byte[] escT = ByteUtils.getIsoBytes("\\t");

    private StreamUtil() {
    }

    public static void skip(InputStream stream, long size) throws IOException {
        while (size > 0) {
            long n = stream.skip(size);
            if (n > 0) {
                size -= n;
            } else {
                return;
            }
        }
    }

    public static byte[] createEscapedString(byte[] bytes) {
        return createBufferedEscapedString(bytes).toByteArray();
    }

    public static void writeEscapedString(OutputStream outputStream, byte[] bytes) {
        ByteBuffer buf = createBufferedEscapedString(bytes);
        try {
            outputStream.write(buf.getInternalBuffer(), 0, buf.size());
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteBytes, (Throwable) e);
        }
    }

    public static void writeHexedString(OutputStream outputStream, byte[] bytes) {
        ByteBuffer buf = createBufferedHexedString(bytes);
        try {
            outputStream.write(buf.getInternalBuffer(), 0, buf.size());
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteBytes, (Throwable) e);
        }
    }

    public static ByteBuffer createBufferedEscapedString(byte[] bytes) {
        ByteBuffer buf = new ByteBuffer((bytes.length * 2) + 2);
        buf.append(40);
        for (byte b : bytes) {
            switch (b) {
                case 8:
                    buf.append(escB);
                    break;
                case 9:
                    buf.append(escT);
                    break;
                case 10:
                    buf.append(escN);
                    break;
                case 12:
                    buf.append(escF);
                    break;
                case 13:
                    buf.append(escR);
                    break;
                case 40:
                case 41:
                case 92:
                    buf.append(92).append(b);
                    break;
                default:
                    if (b >= 8 || b < 0) {
                        if (b >= 8 && b < 32) {
                            buf.append("\\0").append(Integer.toOctalString(b));
                            break;
                        } else {
                            buf.append(b);
                            break;
                        }
                    } else {
                        buf.append("\\00").append(Integer.toOctalString(b));
                        break;
                    }
                    break;
            }
        }
        buf.append(41);
        return buf;
    }

    public static ByteBuffer createBufferedHexedString(byte[] bytes) {
        ByteBuffer buf = new ByteBuffer((bytes.length * 2) + 2);
        buf.append(60);
        for (byte b : bytes) {
            buf.appendHex(b);
        }
        buf.append(62);
        return buf;
    }

    public static void transferBytes(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[65536];
        while (true) {
            int len = input.read(buffer, 0, 65536);
            if (len > 0) {
                output.write(buffer, 0, len);
            } else {
                return;
            }
        }
    }

    public static void transferBytes(RandomAccessFileOrArray input, OutputStream output) throws IOException {
        byte[] buffer = new byte[65536];
        while (true) {
            int len = input.read(buffer, 0, 65536);
            if (len > 0) {
                output.write(buffer, 0, len);
            } else {
                return;
            }
        }
    }

    public static byte[] inputStreamToArray(InputStream stream) throws IOException {
        byte[] b = new byte[8192];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while (true) {
            int read = stream.read(b);
            if (read < 1) {
                output.close();
                return output.toByteArray();
            }
            output.write(b, 0, read);
        }
    }

    public static void copyBytes(IRandomAccessSource source, long start, long length, OutputStream output) throws IOException {
        if (length > 0) {
            byte[] buf = new byte[8192];
            long idx = start;
            long length2 = length;
            while (length2 > 0) {
                long n = (long) source.get(idx, buf, 0, (int) Math.min((long) buf.length, length2));
                if (n > 0) {
                    output.write(buf, 0, (int) n);
                    idx += n;
                    length2 -= n;
                } else {
                    OutputStream outputStream = output;
                    throw new EOFException();
                }
            }
            OutputStream outputStream2 = output;
        }
    }

    public static void readFully(InputStream input, byte[] b, int off, int len) throws IOException {
        if (len >= 0) {
            int n = 0;
            while (n < len) {
                int count = input.read(b, off + n, len - n);
                if (count >= 0) {
                    n += count;
                } else {
                    throw new EOFException();
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}

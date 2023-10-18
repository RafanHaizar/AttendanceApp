package com.itextpdf.p026io.source;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.DecimalFormatUtil;
import java.nio.charset.StandardCharsets;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.ByteUtils */
public final class ByteUtils {
    static boolean HighPrecision = false;
    private static final byte[] bytes = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    private static final byte[] negOne = {45, 49};
    private static final byte[] one = {49};
    private static final byte[] zero = {48};

    public static byte[] getIsoBytes(String text) {
        if (text == null) {
            return null;
        }
        int len = text.length();
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = (byte) text.charAt(k);
        }
        return b;
    }

    public static byte[] getIsoBytes(byte pre, String text) {
        return getIsoBytes(pre, text, (byte) 0);
    }

    public static byte[] getIsoBytes(byte pre, String text, byte post) {
        if (text == null) {
            return null;
        }
        int len = text.length();
        int start = 0;
        if (pre != 0) {
            len++;
            start = 1;
        }
        if (post != 0) {
            len++;
        }
        byte[] b = new byte[len];
        if (pre != 0) {
            b[0] = pre;
        }
        if (post != 0) {
            b[len - 1] = post;
        }
        for (int k = 0; k < text.length(); k++) {
            b[k + start] = (byte) text.charAt(k);
        }
        return b;
    }

    public static byte[] getIsoBytes(int n) {
        return getIsoBytes(n, (ByteBuffer) null);
    }

    public static byte[] getIsoBytes(double d) {
        return getIsoBytes(d, (ByteBuffer) null);
    }

    static byte[] getIsoBytes(int n, ByteBuffer buffer) {
        boolean negative = false;
        if (n < 0) {
            negative = true;
            n = -n;
        }
        int intLen = intSize(n);
        ByteBuffer buf = buffer == null ? new ByteBuffer(intLen + negative) : buffer;
        for (int i = 0; i < intLen; i++) {
            buf.prepend(bytes[n % 10]);
            n /= 10;
        }
        if (negative) {
            buf.prepend((byte) 45);
        }
        if (buffer == null) {
            return buf.getInternalBuffer();
        }
        return null;
    }

    static byte[] getIsoBytes(double d, ByteBuffer buffer) {
        return getIsoBytes(d, buffer, HighPrecision);
    }

    static byte[] getIsoBytes(double d, ByteBuffer buffer, boolean highPrecision) {
        ByteBuffer buf;
        long v;
        int intLen;
        byte[] result;
        double d2 = d;
        ByteBuffer byteBuffer = buffer;
        Class<ByteUtils> cls = ByteUtils.class;
        if (!highPrecision) {
            boolean negative = false;
            if (Math.abs(d) >= 1.5E-5d) {
                if (d2 < 0.0d) {
                    negative = true;
                    d2 = -d2;
                }
                if (d2 < 1.0d) {
                    double d3 = d2 + 5.0E-6d;
                    if (d3 >= 1.0d) {
                        if (negative) {
                            result = negOne;
                        } else {
                            result = one;
                        }
                        if (byteBuffer == null) {
                            return result;
                        }
                        byteBuffer.prepend(result);
                        return null;
                    }
                    int v2 = (int) (100000.0d * d3);
                    int len = 5;
                    while (len > 0 && v2 % 10 == 0) {
                        v2 /= 10;
                        len--;
                    }
                    if (byteBuffer != null) {
                        buf = byteBuffer;
                    } else {
                        buf = new ByteBuffer(negative ? len + 3 : len + 2);
                    }
                    for (int i = 0; i < len; i++) {
                        buf.prepend(bytes[v2 % 10]);
                        v2 /= 10;
                    }
                    buf.prepend((byte) 46).prepend((byte) 48);
                    if (negative) {
                        buf.prepend((byte) 45);
                    }
                } else if (d2 <= 32767.0d) {
                    int v3 = (int) (100.0d * (d2 + 0.005d));
                    if (v3 >= 1000000) {
                        intLen = 5;
                    } else if (v3 >= 100000) {
                        intLen = 4;
                    } else if (v3 >= 10000) {
                        intLen = 3;
                    } else if (v3 >= 1000) {
                        intLen = 2;
                    } else {
                        intLen = 1;
                    }
                    int fracLen = 0;
                    if (v3 % 100 != 0) {
                        fracLen = 2;
                        if (v3 % 10 != 0) {
                            fracLen = 2 + 1;
                        } else {
                            v3 /= 10;
                        }
                    } else {
                        v3 /= 100;
                    }
                    ByteBuffer buf2 = byteBuffer != null ? byteBuffer : new ByteBuffer(intLen + fracLen + negative);
                    for (int i2 = 0; i2 < fracLen - 1; i2++) {
                        buf2.prepend(bytes[v3 % 10]);
                        v3 /= 10;
                    }
                    if (fracLen > 0) {
                        buf2.prepend((byte) 46);
                    }
                    for (int i3 = 0; i3 < intLen; i3++) {
                        buf2.prepend(bytes[v3 % 10]);
                        v3 /= 10;
                    }
                    if (negative) {
                        buf2.prepend((byte) 45);
                    }
                    buf = buf2;
                } else {
                    double d4 = d2 + 0.5d;
                    if (d4 > 9.223372036854776E18d) {
                        v = Long.MAX_VALUE;
                    } else {
                        if (Double.isNaN(d4)) {
                            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ATTEMPT_PROCESS_NAN);
                            d4 = 0.0d;
                        }
                        v = (long) d4;
                    }
                    int intLen2 = longSize(v);
                    buf = byteBuffer == null ? new ByteBuffer(intLen2 + negative) : byteBuffer;
                    for (int i4 = 0; i4 < intLen2; i4++) {
                        buf.prepend(bytes[(int) (v % 10)]);
                        v /= 10;
                    }
                    if (negative) {
                        buf.prepend((byte) 45);
                    }
                }
                if (byteBuffer == null) {
                    return buf.getInternalBuffer();
                }
                return null;
            } else if (byteBuffer == null) {
                return zero;
            } else {
                byteBuffer.prepend(zero);
                return null;
            }
        } else if (Math.abs(d) >= 1.0E-6d) {
            if (Double.isNaN(d)) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ATTEMPT_PROCESS_NAN);
                d2 = 0.0d;
            }
            byte[] result2 = DecimalFormatUtil.formatNumber(d2, "0.######").getBytes(StandardCharsets.ISO_8859_1);
            if (byteBuffer == null) {
                return result2;
            }
            byteBuffer.prepend(result2);
            return null;
        } else if (byteBuffer == null) {
            return zero;
        } else {
            byteBuffer.prepend(zero);
            return null;
        }
    }

    private static int longSize(long l) {
        long m = 10;
        for (int i = 1; i < 19; i++) {
            if (l < m) {
                return i;
            }
            m *= 10;
        }
        return 19;
    }

    private static int intSize(int l) {
        long m = 10;
        for (int i = 1; i < 10; i++) {
            if (((long) l) < m) {
                return i;
            }
            m *= 10;
        }
        return 10;
    }
}

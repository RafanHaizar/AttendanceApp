package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.Utf8;
import java.nio.ByteBuffer;

public final class Utf8Safe extends Utf8 {
    private static int computeEncodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < 128) {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c >= 2048) {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
                utf8Length += (127 - c) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (utf8Length >= utf16Length) {
            return utf8Length;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) utf8Length) + 4294967296L));
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int i = start;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 2048) {
                utf8Length += (127 - c) >>> 31;
            } else {
                utf8Length += 2;
                if (55296 <= c && c <= 57343) {
                    if (Character.codePointAt(sequence, i) >= 65536) {
                        i++;
                    } else {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    }
                }
            }
            i++;
        }
        return utf8Length;
    }

    public static String decodeUtf8Array(byte[] bytes, int index, int size) {
        int offset;
        if ((index | size | ((bytes.length - index) - size)) >= 0) {
            int offset2 = index;
            int limit = offset2 + size;
            char[] resultArr = new char[size];
            int resultPos = 0;
            while (offset < limit) {
                byte b = bytes[offset];
                if (!Utf8.DecodeUtil.isOneByte(b)) {
                    break;
                }
                offset2 = offset + 1;
                Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos);
                resultPos++;
            }
            int resultPos2 = resultPos;
            while (offset < limit) {
                int offset3 = offset + 1;
                byte byte1 = bytes[offset];
                if (Utf8.DecodeUtil.isOneByte(byte1)) {
                    int resultPos3 = resultPos2 + 1;
                    Utf8.DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                    while (offset3 < limit) {
                        byte b2 = bytes[offset3];
                        if (!Utf8.DecodeUtil.isOneByte(b2)) {
                            break;
                        }
                        offset3++;
                        Utf8.DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                        resultPos3++;
                    }
                    offset = offset3;
                    resultPos2 = resultPos3;
                } else if (Utf8.DecodeUtil.isTwoBytes(byte1) != 0) {
                    if (offset3 < limit) {
                        Utf8.DecodeUtil.handleTwoBytes(byte1, bytes[offset3], resultArr, resultPos2);
                        offset = offset3 + 1;
                        resultPos2++;
                    } else {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                } else if (Utf8.DecodeUtil.isThreeBytes(byte1)) {
                    if (offset3 < limit - 1) {
                        int offset4 = offset3 + 1;
                        Utf8.DecodeUtil.handleThreeBytes(byte1, bytes[offset3], bytes[offset4], resultArr, resultPos2);
                        offset = offset4 + 1;
                        resultPos2++;
                    } else {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                } else if (offset3 < limit - 2) {
                    int offset5 = offset3 + 1;
                    byte b3 = bytes[offset3];
                    int offset6 = offset5 + 1;
                    Utf8.DecodeUtil.handleFourBytes(byte1, b3, bytes[offset5], bytes[offset6], resultArr, resultPos2);
                    offset = offset6 + 1;
                    resultPos2 = resultPos2 + 1 + 1;
                } else {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
            }
            return new String(resultArr, 0, resultPos2);
        }
        throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[]{Integer.valueOf(bytes.length), Integer.valueOf(index), Integer.valueOf(size)}));
    }

    public static String decodeUtf8Buffer(ByteBuffer buffer, int offset, int length) {
        if ((offset | length | ((buffer.limit() - offset) - length)) >= 0) {
            int limit = offset + length;
            char[] resultArr = new char[length];
            int resultPos = 0;
            while (offset < limit) {
                byte b = buffer.get(offset);
                if (!Utf8.DecodeUtil.isOneByte(b)) {
                    break;
                }
                offset = offset + 1;
                Utf8.DecodeUtil.handleOneByte(b, resultArr, resultPos);
                resultPos++;
            }
            int resultPos2 = resultPos;
            while (offset < limit) {
                int offset2 = offset + 1;
                byte byte1 = buffer.get(offset);
                if (Utf8.DecodeUtil.isOneByte(byte1)) {
                    int resultPos3 = resultPos2 + 1;
                    Utf8.DecodeUtil.handleOneByte(byte1, resultArr, resultPos2);
                    while (offset2 < limit) {
                        byte b2 = buffer.get(offset2);
                        if (!Utf8.DecodeUtil.isOneByte(b2)) {
                            break;
                        }
                        offset2++;
                        Utf8.DecodeUtil.handleOneByte(b2, resultArr, resultPos3);
                        resultPos3++;
                    }
                    offset = offset2;
                    resultPos2 = resultPos3;
                } else if (Utf8.DecodeUtil.isTwoBytes(byte1) != 0) {
                    if (offset2 < limit) {
                        Utf8.DecodeUtil.handleTwoBytes(byte1, buffer.get(offset2), resultArr, resultPos2);
                        offset = offset2 + 1;
                        resultPos2++;
                    } else {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                } else if (Utf8.DecodeUtil.isThreeBytes(byte1)) {
                    if (offset2 < limit - 1) {
                        int offset3 = offset2 + 1;
                        Utf8.DecodeUtil.handleThreeBytes(byte1, buffer.get(offset2), buffer.get(offset3), resultArr, resultPos2);
                        offset = offset3 + 1;
                        resultPos2++;
                    } else {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                } else if (offset2 < limit - 2) {
                    int offset4 = offset2 + 1;
                    byte b3 = buffer.get(offset2);
                    int offset5 = offset4 + 1;
                    Utf8.DecodeUtil.handleFourBytes(byte1, b3, buffer.get(offset4), buffer.get(offset5), resultArr, resultPos2);
                    offset = offset5 + 1;
                    resultPos2 = resultPos2 + 1 + 1;
                } else {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
            }
            return new String(resultArr, 0, resultPos2);
        }
        throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(buffer.limit()), Integer.valueOf(offset), Integer.valueOf(length)}));
    }

    public int encodedLength(CharSequence in) {
        return computeEncodedLength(in);
    }

    public String decodeUtf8(ByteBuffer buffer, int offset, int length) throws IllegalArgumentException {
        if (buffer.hasArray()) {
            return decodeUtf8Array(buffer.array(), buffer.arrayOffset() + offset, length);
        }
        return decodeUtf8Buffer(buffer, offset, length);
    }

    private static void encodeUtf8Buffer(CharSequence in, ByteBuffer out) {
        int outIx;
        int inLength = in.length();
        int outIx2 = out.position();
        int inIx = 0;
        while (inIx < inLength) {
            try {
                char charAt = in.charAt(inIx);
                char c = charAt;
                if (charAt >= 128) {
                    break;
                }
                out.put(outIx2 + inIx, (byte) c);
                inIx++;
            } catch (IndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
            }
        }
        if (inIx == inLength) {
            out.position(outIx2 + inIx);
            return;
        }
        outIx2 += inIx;
        while (inIx < inLength) {
            char c2 = in.charAt(inIx);
            if (c2 < 128) {
                out.put(outIx2, (byte) c2);
            } else if (c2 < 2048) {
                outIx = outIx2 + 1;
                try {
                    out.put(outIx2, (byte) ((c2 >>> 6) | 192));
                    out.put(outIx, (byte) ((c2 & '?') | 128));
                    outIx2 = outIx;
                } catch (IndexOutOfBoundsException e2) {
                    outIx2 = outIx;
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
                }
            } else if (c2 < 55296 || 57343 < c2) {
                outIx = outIx2 + 1;
                out.put(outIx2, (byte) ((c2 >>> 12) | 224));
                outIx2 = outIx + 1;
                out.put(outIx, (byte) (((c2 >>> 6) & 63) | 128));
                out.put(outIx2, (byte) ((c2 & '?') | 128));
            } else {
                if (inIx + 1 != inLength) {
                    inIx++;
                    char charAt2 = in.charAt(inIx);
                    char low = charAt2;
                    if (Character.isSurrogatePair(c2, charAt2)) {
                        int codePoint = Character.toCodePoint(c2, low);
                        int outIx3 = outIx2 + 1;
                        try {
                            out.put(outIx2, (byte) ((codePoint >>> 18) | 240));
                            outIx2 = outIx3 + 1;
                            out.put(outIx3, (byte) (((codePoint >>> 12) & 63) | 128));
                            outIx3 = outIx2 + 1;
                            out.put(outIx2, (byte) (((codePoint >>> 6) & 63) | 128));
                            out.put(outIx3, (byte) ((codePoint & 63) | 128));
                            outIx2 = outIx3;
                        } catch (IndexOutOfBoundsException e3) {
                            outIx2 = outIx3;
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
                        }
                    }
                }
                throw new UnpairedSurrogateException(inIx, inLength);
            }
            inIx++;
            outIx2++;
        }
        out.position(outIx2);
    }

    private static int encodeUtf8Array(CharSequence in, byte[] out, int offset, int length) {
        int utf16Length = in.length();
        int j = offset;
        int i = 0;
        int limit = offset + length;
        while (i < utf16Length && i + j < limit) {
            char charAt = in.charAt(i);
            char c = charAt;
            if (charAt >= 128) {
                break;
            }
            out[j + i] = (byte) c;
            i++;
        }
        if (i == utf16Length) {
            return j + utf16Length;
        }
        int j2 = j + i;
        while (i < utf16Length) {
            char c2 = in.charAt(i);
            if (c2 < 128 && j2 < limit) {
                out[j2] = (byte) c2;
                j2++;
            } else if (c2 < 2048 && j2 <= limit - 2) {
                int j3 = j2 + 1;
                out[j2] = (byte) ((c2 >>> 6) | 960);
                j2 = j3 + 1;
                out[j3] = (byte) ((c2 & '?') | 128);
            } else if ((c2 < 55296 || 57343 < c2) && j2 <= limit - 3) {
                int j4 = j2 + 1;
                out[j2] = (byte) ((c2 >>> 12) | 480);
                int j5 = j4 + 1;
                out[j4] = (byte) (((c2 >>> 6) & 63) | 128);
                out[j5] = (byte) ((c2 & '?') | 128);
                j2 = j5 + 1;
            } else if (j2 <= limit - 4) {
                if (i + 1 != in.length()) {
                    i++;
                    char charAt2 = in.charAt(i);
                    char low = charAt2;
                    if (Character.isSurrogatePair(c2, charAt2)) {
                        int codePoint = Character.toCodePoint(c2, low);
                        int j6 = j2 + 1;
                        out[j2] = (byte) ((codePoint >>> 18) | 240);
                        int j7 = j6 + 1;
                        out[j6] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int j8 = j7 + 1;
                        out[j7] = (byte) (((codePoint >>> 6) & 63) | 128);
                        j2 = j8 + 1;
                        out[j8] = (byte) ((codePoint & 63) | 128);
                    }
                }
                throw new UnpairedSurrogateException(i - 1, utf16Length);
            } else if (55296 > c2 || c2 > 57343 || (i + 1 != in.length() && Character.isSurrogatePair(c2, in.charAt(i + 1)))) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + c2 + " at index " + j2);
            } else {
                throw new UnpairedSurrogateException(i, utf16Length);
            }
            i++;
        }
        return j2;
    }

    public void encodeUtf8(CharSequence in, ByteBuffer out) {
        if (out.hasArray()) {
            int start = out.arrayOffset();
            out.position(encodeUtf8Array(in, out.array(), out.position() + start, out.remaining()) - start);
            return;
        }
        encodeUtf8Buffer(in, out);
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int index, int length) {
            super("Unpaired surrogate at index " + index + " of " + length);
        }
    }
}

package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.UnsupportedEncodingException;

public class Latin1Converter {
    private static final int STATE_START = 0;
    private static final int STATE_UTF8CHAR = 11;

    private Latin1Converter() {
    }

    public static ByteBuffer convert(ByteBuffer buffer) {
        if (!PdfEncodings.UTF8.equals(buffer.getEncoding())) {
            return buffer;
        }
        byte[] readAheadBuffer = new byte[8];
        int state = 0;
        int expectedBytes = 0;
        ByteBuffer out = new ByteBuffer((buffer.length() * 4) / 3);
        int state2 = 0;
        int i = 0;
        while (i < buffer.length()) {
            int b = buffer.charAt(i);
            switch (state2) {
                case 11:
                    if (expectedBytes > 0 && (b & 192) == 128) {
                        int readAhead = state + 1;
                        readAheadBuffer[state] = (byte) b;
                        expectedBytes--;
                        if (expectedBytes != 0) {
                            state = readAhead;
                            break;
                        } else {
                            out.append(readAheadBuffer, 0, readAhead);
                            state = 0;
                            state2 = 0;
                            break;
                        }
                    } else {
                        out.append(convertToUTF8(readAheadBuffer[0]));
                        i -= state;
                        state = 0;
                        state2 = 0;
                        break;
                    }
                default:
                    if (b >= 127) {
                        if (b < 192) {
                            out.append(convertToUTF8((byte) b));
                            break;
                        } else {
                            expectedBytes = -1;
                            int test = b;
                            while (expectedBytes < 8 && (test & 128) == 128) {
                                expectedBytes++;
                                test <<= 1;
                            }
                            readAheadBuffer[state] = (byte) b;
                            state2 = 11;
                            state++;
                            break;
                        }
                    } else {
                        out.append((byte) b);
                        break;
                    }
            }
            i++;
        }
        if (state2 == 11) {
            for (int j = 0; j < state; j++) {
                out.append(convertToUTF8(readAheadBuffer[j]));
            }
        }
        return out;
    }

    private static byte[] convertToUTF8(byte ch) {
        int c = ch & 255;
        if (c >= 128) {
            if (c == 129 || c == 141 || c == 143 || c == 144 || c == 157) {
                return new byte[]{32};
            }
            try {
                return new String(new byte[]{ch}, "cp1252").getBytes(PdfEncodings.UTF8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return new byte[]{ch};
    }
}

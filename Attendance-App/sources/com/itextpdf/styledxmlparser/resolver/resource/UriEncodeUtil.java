package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.BitSet;

class UriEncodeUtil {
    private static final int caseDiff = 32;
    private static String dfltEncName = PdfEncodings.UTF8;
    private static BitSet unreservedAndReserved = new BitSet(256);

    UriEncodeUtil() {
    }

    static {
        for (int i = 97; i <= 122; i++) {
            unreservedAndReserved.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            unreservedAndReserved.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            unreservedAndReserved.set(i3);
        }
        unreservedAndReserved.set(45);
        unreservedAndReserved.set(95);
        unreservedAndReserved.set(46);
        unreservedAndReserved.set(126);
        unreservedAndReserved.set(58);
        unreservedAndReserved.set(47);
        unreservedAndReserved.set(63);
        unreservedAndReserved.set(35);
        unreservedAndReserved.set(91);
        unreservedAndReserved.set(93);
        unreservedAndReserved.set(64);
        unreservedAndReserved.set(33);
        unreservedAndReserved.set(36);
        unreservedAndReserved.set(38);
        unreservedAndReserved.set(39);
        unreservedAndReserved.set(92);
        unreservedAndReserved.set(40);
        unreservedAndReserved.set(41);
        unreservedAndReserved.set(42);
        unreservedAndReserved.set(43);
        unreservedAndReserved.set(44);
        unreservedAndReserved.set(59);
        unreservedAndReserved.set(61);
    }

    public static String encode(String s) {
        return encode(s, dfltEncName);
    }

    public static String encode(String s, String enc) {
        BitSet bitSet;
        int charAt;
        int d;
        boolean needToChange = false;
        StringBuffer out = new StringBuffer(s.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        if (enc != null) {
            try {
                Charset charset = Charset.forName(enc);
                int i = 0;
                boolean firstHash = true;
                while (i < s.length()) {
                    int c = s.charAt(i);
                    if (92 == c) {
                        out.append('/');
                        needToChange = true;
                        i++;
                    } else if (37 == c) {
                        int v = -1;
                        if (i + 2 < s.length()) {
                            try {
                                v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                            } catch (NumberFormatException e) {
                                v = -1;
                            }
                            if (v >= 0) {
                                out.append((char) c);
                            }
                        }
                        if (v < 0) {
                            needToChange = true;
                            out.append("%25");
                        }
                        i++;
                    } else if (35 == c) {
                        if (firstHash) {
                            out.append((char) c);
                            firstHash = false;
                        } else {
                            out.append("%23");
                            needToChange = true;
                        }
                        i++;
                    } else if (unreservedAndReserved.get(c)) {
                        out.append((char) c);
                        i++;
                    } else {
                        do {
                            charArrayWriter.write(c);
                            if (c >= 55296 && c <= 56319 && i + 1 < s.length() && (d = s.charAt(i + 1)) >= 56320 && d <= 57343) {
                                charArrayWriter.write(d);
                                i++;
                            }
                            i++;
                            if (i >= s.length()) {
                                break;
                            }
                            bitSet = unreservedAndReserved;
                            charAt = s.charAt(i);
                            c = charAt;
                        } while (!bitSet.get(charAt));
                        charArrayWriter.flush();
                        byte[] ba = new String(charArrayWriter.toCharArray()).getBytes(charset);
                        for (int j = 0; j < ba.length; j++) {
                            out.append('%');
                            char ch = Character.forDigit((ba[j] >> 4) & 15, 16);
                            if (Character.isLetter(ch)) {
                                ch = (char) (ch - ' ');
                            }
                            out.append(ch);
                            char ch2 = Character.forDigit(ba[j] & 15, 16);
                            if (Character.isLetter(ch2)) {
                                ch2 = (char) (ch2 - ' ');
                            }
                            out.append(ch2);
                        }
                        charArrayWriter.reset();
                        needToChange = true;
                    }
                }
                return needToChange ? out.toString() : s;
            } catch (IllegalCharsetNameException e2) {
                throw new StyledXMLParserException("Unsupported encoding exception.");
            }
        } else {
            throw new StyledXMLParserException("Unsupported encoding exception.");
        }
    }
}

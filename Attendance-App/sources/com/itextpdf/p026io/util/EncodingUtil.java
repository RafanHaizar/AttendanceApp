package com.itextpdf.p026io.util;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/* renamed from: com.itextpdf.io.util.EncodingUtil */
public final class EncodingUtil {
    private EncodingUtil() {
    }

    public static byte[] convertToBytes(char[] chars, String encoding) throws CharacterCodingException {
        CharsetEncoder ce = Charset.forName(encoding).newEncoder();
        ce.onUnmappableCharacter(CodingErrorAction.IGNORE);
        ByteBuffer bb = ce.encode(CharBuffer.wrap(chars));
        bb.rewind();
        int lim = bb.limit();
        int offset = PdfEncodings.UTF8.equals(encoding) ? 3 : 0;
        byte[] br = new byte[(lim + offset)];
        if (PdfEncodings.UTF8.equals(encoding)) {
            br[0] = -17;
            br[1] = -69;
            br[2] = -65;
        }
        bb.get(br, offset, lim);
        return br;
    }

    public static String convertToString(byte[] bytes, String encoding) throws UnsupportedEncodingException {
        if (encoding.equals(PdfEncodings.UTF8) && bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65) {
            return new String(bytes, 3, bytes.length - 3, PdfEncodings.UTF8);
        }
        return new String(bytes, encoding);
    }
}

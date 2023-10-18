package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.PdfTokenizer;
import java.io.ByteArrayOutputStream;

public class ASCIIHexDecodeFilter extends MemoryLimitsAwareFilter {
    public static byte[] ASCIIHexDecode(byte[] in) {
        return ASCIIHexDecodeInternal(in, new ByteArrayOutputStream());
    }

    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        return ASCIIHexDecodeInternal(b, enableMemoryLimitsAwareHandler(streamDictionary));
    }

    private static byte[] ASCIIHexDecodeInternal(byte[] in, ByteArrayOutputStream out) {
        int ch;
        boolean first = true;
        int n1 = 0;
        int k = 0;
        while (k < in.length && (ch = in[k] & 255) != 62) {
            if (!PdfTokenizer.isWhitespace(ch)) {
                int n = ByteBuffer.getHex(ch);
                if (n != -1) {
                    if (first) {
                        n1 = n;
                    } else {
                        out.write((byte) ((n1 << 4) + n));
                    }
                    first = !first;
                } else {
                    throw new PdfException(PdfException.IllegalCharacterInAsciihexdecode);
                }
            }
            k++;
        }
        if (!first) {
            out.write((byte) (n1 << 4));
        }
        return out.toByteArray();
    }
}

package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import java.io.ByteArrayOutputStream;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

public class RunLengthDecodeFilter extends MemoryLimitsAwareFilter {
    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        byte dupCount;
        int i;
        ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
        int i2 = 0;
        while (i2 < b.length && (dupCount = b[i2]) != Byte.MIN_VALUE) {
            if ((dupCount & ByteCompanionObject.MIN_VALUE) == 0) {
                int bytesToCopy = dupCount + 1;
                outputStream.write(b, i2 + 1, bytesToCopy);
                i = i2 + bytesToCopy;
            } else {
                i = i2 + 1;
                for (int j = 0; j < 257 - (dupCount & UByte.MAX_VALUE); j++) {
                    outputStream.write(b[i]);
                }
            }
            i2 = i + 1;
        }
        return outputStream.toByteArray();
    }
}

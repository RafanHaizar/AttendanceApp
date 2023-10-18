package com.itextpdf.kernel.pdf.filters;

public class CCITTFaxDecodeFilter implements IFilterHandler {
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] decode(byte[] r21, com.itextpdf.kernel.pdf.PdfName r22, com.itextpdf.kernel.pdf.PdfObject r23, com.itextpdf.kernel.pdf.PdfDictionary r24) {
        /*
            r20 = this;
            r7 = r21
            r8 = r23
            r9 = r24
            com.itextpdf.kernel.pdf.PdfName r0 = com.itextpdf.kernel.pdf.PdfName.Width
            com.itextpdf.kernel.pdf.PdfNumber r10 = r9.getAsNumber(r0)
            com.itextpdf.kernel.pdf.PdfName r0 = com.itextpdf.kernel.pdf.PdfName.Height
            com.itextpdf.kernel.pdf.PdfNumber r11 = r9.getAsNumber(r0)
            if (r10 == 0) goto L_0x00dc
            if (r11 == 0) goto L_0x00dc
            int r12 = r10.intValue()
            int r13 = r11.intValue()
            boolean r0 = r8 instanceof com.itextpdf.kernel.pdf.PdfDictionary
            if (r0 == 0) goto L_0x0026
            r0 = r8
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            goto L_0x0027
        L_0x0026:
            r0 = 0
        L_0x0027:
            r14 = r0
            r0 = 0
            r1 = 0
            r2 = 0
            if (r14 == 0) goto L_0x005d
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.f1344K
            com.itextpdf.kernel.pdf.PdfNumber r3 = r14.getAsNumber(r3)
            if (r3 == 0) goto L_0x0039
            int r0 = r3.intValue()
        L_0x0039:
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.BlackIs1
            com.itextpdf.kernel.pdf.PdfBoolean r4 = r14.getAsBoolean(r4)
            if (r4 == 0) goto L_0x0045
            boolean r1 = r4.getValue()
        L_0x0045:
            com.itextpdf.kernel.pdf.PdfName r5 = com.itextpdf.kernel.pdf.PdfName.EncodedByteAlign
            com.itextpdf.kernel.pdf.PdfBoolean r4 = r14.getAsBoolean(r5)
            if (r4 == 0) goto L_0x0057
            boolean r2 = r4.getValue()
            r15 = r0
            r16 = r1
            r17 = r2
            goto L_0x0062
        L_0x0057:
            r15 = r0
            r16 = r1
            r17 = r2
            goto L_0x0062
        L_0x005d:
            r15 = r0
            r16 = r1
            r17 = r2
        L_0x0062:
            int r0 = r12 + 7
            int r0 = r0 / 8
            int r0 = r0 * r13
            byte[] r5 = new byte[r0]
            com.itextpdf.io.codec.TIFFFaxDecompressor r0 = new com.itextpdf.io.codec.TIFFFaxDecompressor
            r0.<init>()
            r6 = r0
            r0 = 1
            if (r15 == 0) goto L_0x0097
            if (r15 <= 0) goto L_0x0078
            r8 = r5
            r9 = r6
            goto L_0x0099
        L_0x0078:
            r1 = 0
            if (r17 == 0) goto L_0x007f
            r3 = 4
            goto L_0x0081
        L_0x007f:
            r3 = 0
        L_0x0081:
            long r18 = r1 | r3
            com.itextpdf.io.codec.TIFFFaxDecoder r1 = new com.itextpdf.io.codec.TIFFFaxDecoder
            r1.<init>(r0, r12, r13)
            r0 = r1
            r3 = 0
            r1 = r5
            r2 = r21
            r4 = r13
            r8 = r5
            r9 = r6
            r5 = r18
            r0.decodeT6(r1, r2, r3, r4, r5)
            r5 = r8
            goto L_0x00ca
        L_0x0097:
            r8 = r5
            r9 = r6
        L_0x0099:
            r1 = 0
            if (r15 <= 0) goto L_0x009e
            r2 = 1
            goto L_0x009f
        L_0x009e:
            r2 = 0
        L_0x009f:
            if (r17 == 0) goto L_0x00a3
            r3 = 4
            goto L_0x00a4
        L_0x00a3:
            r3 = 0
        L_0x00a4:
            r2 = r2 | r3
            r3 = 3
            r9.SetOptions(r0, r3, r2, r1)
            r9.decodeRaw(r8, r7, r12, r13)
            int r3 = r9.fails
            if (r3 <= 0) goto L_0x00c8
            int r3 = r12 + 7
            int r3 = r3 / 8
            int r3 = r3 * r13
            byte[] r3 = new byte[r3]
            int r4 = r9.fails
            r5 = 2
            r9.SetOptions(r0, r5, r2, r1)
            r9.decodeRaw(r3, r7, r12, r13)
            int r0 = r9.fails
            if (r0 >= r4) goto L_0x00c8
            r0 = r3
            r5 = r0
            goto L_0x00c9
        L_0x00c8:
            r5 = r8
        L_0x00c9:
        L_0x00ca:
            if (r16 != 0) goto L_0x00da
            int r0 = r5.length
            r1 = 0
        L_0x00ce:
            if (r1 >= r0) goto L_0x00da
            byte r2 = r5[r1]
            r2 = r2 ^ 255(0xff, float:3.57E-43)
            byte r2 = (byte) r2
            r5[r1] = r2
            int r1 = r1 + 1
            goto L_0x00ce
        L_0x00da:
            r0 = r5
            return r0
        L_0x00dc:
            com.itextpdf.kernel.PdfException r0 = new com.itextpdf.kernel.PdfException
            java.lang.String r1 = "Filter CCITTFaxDecode is only supported for images"
            r0.<init>((java.lang.String) r1)
            goto L_0x00e5
        L_0x00e4:
            throw r0
        L_0x00e5:
            goto L_0x00e4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.filters.CCITTFaxDecodeFilter.decode(byte[], com.itextpdf.kernel.pdf.PdfName, com.itextpdf.kernel.pdf.PdfObject, com.itextpdf.kernel.pdf.PdfDictionary):byte[]");
    }
}

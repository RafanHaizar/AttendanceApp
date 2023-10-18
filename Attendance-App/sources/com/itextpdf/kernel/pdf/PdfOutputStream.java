package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.filters.FlateDecodeFilter;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.source.OutputStream;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.slf4j.LoggerFactory;

public class PdfOutputStream extends OutputStream<PdfOutputStream> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] closeDict = ByteUtils.getIsoBytes(">>");
    private static final byte[] endIndirect = ByteUtils.getIsoBytes(" R");
    private static final byte[] endIndirectWithZeroGenNr = ByteUtils.getIsoBytes(" 0 R");
    private static final byte[] endstream = ByteUtils.getIsoBytes("\nendstream");
    private static final byte[] openDict = ByteUtils.getIsoBytes("<<");
    private static final long serialVersionUID = -548180479472231600L;
    private static final byte[] stream = ByteUtils.getIsoBytes("stream\n");
    protected PdfEncryption crypto;
    protected PdfDocument document = null;
    private byte[] duplicateContentBuffer = null;

    public PdfOutputStream(java.io.OutputStream outputStream) {
        super(outputStream);
    }

    public PdfOutputStream write(PdfObject pdfObject) {
        PdfDocument pdfDocument;
        if (pdfObject.checkState(64) && (pdfDocument = this.document) != null) {
            pdfObject.makeIndirect(pdfDocument);
            pdfObject = pdfObject.getIndirectReference();
        }
        if (!pdfObject.checkState(256)) {
            switch (pdfObject.getType()) {
                case 1:
                    write((PdfArray) pdfObject);
                    break;
                case 2:
                case 7:
                    write((PdfPrimitiveObject) pdfObject);
                    break;
                case 3:
                    write((PdfDictionary) pdfObject);
                    break;
                case 4:
                    write((PdfLiteral) pdfObject);
                    break;
                case 5:
                    write((PdfIndirectReference) pdfObject);
                    break;
                case 6:
                    write((PdfName) pdfObject);
                    break;
                case 8:
                    write((PdfNumber) pdfObject);
                    break;
                case 9:
                    write((PdfStream) pdfObject);
                    break;
                case 10:
                    write((PdfString) pdfObject);
                    break;
            }
            return this;
        }
        throw new PdfException(PdfException.CannotWriteObjectAfterItWasReleased);
    }

    /* access modifiers changed from: package-private */
    public void write(long bytes, int size) throws IOException {
        if (bytes >= 0) {
            while (true) {
                size--;
                if (size >= 0) {
                    write((int) (byte) ((int) ((bytes >> (size * 8)) & 255)));
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: package-private */
    public void write(int bytes, int size) throws IOException {
        write(((long) bytes) & BodyPartID.bodyIdMax, size);
    }

    private void write(PdfArray pdfArray) {
        writeByte(91);
        for (int i = 0; i < pdfArray.size(); i++) {
            PdfObject value = pdfArray.get(i, false);
            PdfIndirectReference indirectReference = value.getIndirectReference();
            PdfIndirectReference indirectReference2 = indirectReference;
            if (indirectReference != null) {
                write(indirectReference2);
            } else {
                write(value);
            }
            if (i < pdfArray.size() - 1) {
                writeSpace();
            }
        }
        writeByte(93);
    }

    private void write(PdfDictionary pdfDictionary) {
        writeBytes(openDict);
        for (PdfName key : pdfDictionary.keySet()) {
            boolean isAlreadyWriteSpace = false;
            write(key);
            PdfObject value = pdfDictionary.get(key, false);
            if (value == null) {
                LoggerFactory.getLogger((Class<?>) PdfOutputStream.class).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_KEY_VALUE_KEY_0_HAS_NULL_VALUE, key));
                value = PdfNull.PDF_NULL;
            }
            if (value.getType() == 8 || value.getType() == 4 || value.getType() == 2 || value.getType() == 7 || value.getType() == 5 || value.checkState(64)) {
                isAlreadyWriteSpace = true;
                writeSpace();
            }
            PdfIndirectReference indirectReference = value.getIndirectReference();
            PdfIndirectReference indirectReference2 = indirectReference;
            if (indirectReference != null) {
                if (!isAlreadyWriteSpace) {
                    writeSpace();
                }
                write(indirectReference2);
            } else {
                write(value);
            }
        }
        writeBytes(closeDict);
    }

    private void write(PdfIndirectReference indirectReference) {
        if (this.document == null || indirectReference.getDocument().equals(this.document)) {
            Class<PdfOutputStream> cls = PdfOutputStream.class;
            if (indirectReference.isFree()) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.FLUSHED_OBJECT_CONTAINS_FREE_REFERENCE);
                write((PdfPrimitiveObject) PdfNull.PDF_NULL);
            } else if (indirectReference.refersTo == null && (indirectReference.checkState(8) || indirectReference.getReader() == null || (indirectReference.getOffset() <= 0 && indirectReference.getIndex() < 0))) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.FLUSHED_OBJECT_CONTAINS_REFERENCE_WHICH_NOT_REFER_TO_ANY_OBJECT);
                write((PdfPrimitiveObject) PdfNull.PDF_NULL);
            } else if (indirectReference.getGenNumber() == 0) {
                ((PdfOutputStream) writeInteger(indirectReference.getObjNumber())).writeBytes(endIndirectWithZeroGenNr);
            } else {
                ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) writeInteger(indirectReference.getObjNumber())).writeSpace()).writeInteger(indirectReference.getGenNumber())).writeBytes(endIndirect);
            }
        } else {
            throw new PdfException(PdfException.PdfIndirectObjectBelongsToOtherPdfDocument);
        }
    }

    private void write(PdfPrimitiveObject pdfPrimitive) {
        writeBytes(pdfPrimitive.getInternalContent());
    }

    private void write(PdfLiteral literal) {
        literal.setPosition(getCurrentPos());
        writeBytes(literal.getInternalContent());
    }

    private void write(PdfString pdfString) {
        pdfString.encrypt(this.crypto);
        if (pdfString.isHexWriting()) {
            writeByte(60);
            writeBytes(pdfString.getInternalContent());
            writeByte(62);
            return;
        }
        writeByte(40);
        writeBytes(pdfString.getInternalContent());
        writeByte(41);
    }

    private void write(PdfName name) {
        writeByte(47);
        writeBytes(name.getInternalContent());
    }

    private void write(PdfNumber pdfNumber) {
        if (pdfNumber.hasContent()) {
            writeBytes(pdfNumber.getInternalContent());
        } else if (pdfNumber.isDoubleNumber()) {
            writeDouble(pdfNumber.getValue());
        } else {
            writeInteger(pdfNumber.intValue());
        }
    }

    private boolean isNotMetadataPdfStream(PdfStream pdfStream) {
        return pdfStream.getAsName(PdfName.Type) == null || (pdfStream.getAsName(PdfName.Type) != null && !pdfStream.getAsName(PdfName.Type).equals(PdfName.Metadata));
    }

    private boolean isXRefStream(PdfStream pdfStream) {
        return PdfName.XRef.equals(pdfStream.getAsName(PdfName.Type));
    }

    /* JADX WARNING: Removed duplicated region for block: B:75:0x0187 A[Catch:{ IOException -> 0x01ca, IOException -> 0x01db }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void write(com.itextpdf.kernel.pdf.PdfStream r13) {
        /*
            r12 = this;
            int r0 = r13.getCompressionLevel()     // Catch:{ IOException -> 0x01db }
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            if (r0 != 0) goto L_0x0020
            com.itextpdf.kernel.pdf.PdfDocument r1 = r12.document     // Catch:{ IOException -> 0x01db }
            if (r1 == 0) goto L_0x001c
            com.itextpdf.kernel.pdf.PdfWriter r1 = r1.getWriter()     // Catch:{ IOException -> 0x01db }
            int r1 = r1.getCompressionLevel()     // Catch:{ IOException -> 0x01db }
            goto L_0x001d
        L_0x001c:
            r1 = -1
        L_0x001d:
            r13.setCompressionLevel(r1)     // Catch:{ IOException -> 0x01db }
        L_0x0020:
            int r1 = r13.getCompressionLevel()     // Catch:{ IOException -> 0x01db }
            if (r1 == 0) goto L_0x0028
            r1 = 1
            goto L_0x0029
        L_0x0028:
            r1 = 0
        L_0x0029:
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.Filter     // Catch:{ IOException -> 0x01db }
            boolean r4 = r13.containsKey(r4)     // Catch:{ IOException -> 0x01db }
            if (r4 != 0) goto L_0x0038
            boolean r4 = r12.isNotMetadataPdfStream(r13)     // Catch:{ IOException -> 0x01db }
            if (r4 == 0) goto L_0x0038
            goto L_0x0039
        L_0x0038:
            r2 = 0
        L_0x0039:
            java.io.InputStream r4 = r13.getInputStream()     // Catch:{ IOException -> 0x01db }
            if (r4 == 0) goto L_0x00b2
            r4 = r12
            r5 = 0
            r6 = 0
            com.itextpdf.kernel.pdf.PdfEncryption r7 = r12.crypto     // Catch:{ IOException -> 0x01db }
            if (r7 == 0) goto L_0x0054
            boolean r7 = r7.isEmbeddedFilesOnly()     // Catch:{ IOException -> 0x01db }
            if (r7 != 0) goto L_0x0054
            com.itextpdf.kernel.pdf.PdfEncryption r7 = r12.crypto     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.crypto.OutputStreamEncryption r7 = r7.getEncryptionStream(r4)     // Catch:{ IOException -> 0x01db }
            r6 = r7
            r4 = r7
        L_0x0054:
            if (r1 == 0) goto L_0x006b
            if (r2 != 0) goto L_0x005a
            if (r0 == 0) goto L_0x006b
        L_0x005a:
            r12.updateCompressionFilter(r13)     // Catch:{ IOException -> 0x01db }
            com.itextpdf.io.source.DeflaterOutputStream r7 = new com.itextpdf.io.source.DeflaterOutputStream     // Catch:{ IOException -> 0x01db }
            int r8 = r13.getCompressionLevel()     // Catch:{ IOException -> 0x01db }
            r9 = 32768(0x8000, float:4.5918E-41)
            r7.<init>(r4, r8, r9)     // Catch:{ IOException -> 0x01db }
            r5 = r7
            r4 = r7
        L_0x006b:
            r12.write((com.itextpdf.kernel.pdf.PdfDictionary) r13)     // Catch:{ IOException -> 0x01db }
            byte[] r7 = stream     // Catch:{ IOException -> 0x01db }
            r12.writeBytes(r7)     // Catch:{ IOException -> 0x01db }
            long r7 = r12.getCurrentPos()     // Catch:{ IOException -> 0x01db }
            r9 = 4192(0x1060, float:5.874E-42)
            byte[] r9 = new byte[r9]     // Catch:{ IOException -> 0x01db }
        L_0x007b:
            java.io.InputStream r10 = r13.getInputStream()     // Catch:{ IOException -> 0x01db }
            int r10 = r10.read(r9)     // Catch:{ IOException -> 0x01db }
            if (r10 > 0) goto L_0x00ae
            if (r5 == 0) goto L_0x008b
            r5.finish()     // Catch:{ IOException -> 0x01db }
        L_0x008b:
            if (r6 == 0) goto L_0x0090
            r6.finish()     // Catch:{ IOException -> 0x01db }
        L_0x0090:
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Length     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.pdf.PdfNumber r3 = r13.getAsNumber(r3)     // Catch:{ IOException -> 0x01db }
            long r10 = r12.getCurrentPos()     // Catch:{ IOException -> 0x01db }
            long r10 = r10 - r7
            int r11 = (int) r10     // Catch:{ IOException -> 0x01db }
            r3.setValue((int) r11)     // Catch:{ IOException -> 0x01db }
            int r10 = r3.intValue()     // Catch:{ IOException -> 0x01db }
            r13.updateLength(r10)     // Catch:{ IOException -> 0x01db }
            byte[] r10 = endstream     // Catch:{ IOException -> 0x01db }
            r12.writeBytes(r10)     // Catch:{ IOException -> 0x01db }
            goto L_0x01c2
        L_0x00ae:
            r4.write(r9, r3, r10)     // Catch:{ IOException -> 0x01db }
            goto L_0x007b
        L_0x00b2:
            com.itextpdf.kernel.pdf.PdfOutputStream r4 = r13.getOutputStream()     // Catch:{ IOException -> 0x01db }
            if (r4 != 0) goto L_0x00e5
            com.itextpdf.kernel.pdf.PdfIndirectReference r4 = r13.getIndirectReference()     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.pdf.PdfReader r4 = r4.getReader()     // Catch:{ IOException -> 0x01db }
            if (r4 == 0) goto L_0x00e5
            com.itextpdf.kernel.pdf.PdfIndirectReference r4 = r13.getIndirectReference()     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.pdf.PdfReader r4 = r4.getReader()     // Catch:{ IOException -> 0x01db }
            byte[] r3 = r4.readStreamBytes(r13, r3)     // Catch:{ IOException -> 0x01db }
            if (r0 == 0) goto L_0x00d5
            byte[] r4 = r12.decodeFlateBytes(r13, r3)     // Catch:{ IOException -> 0x01db }
            r3 = r4
        L_0x00d5:
            com.itextpdf.io.source.ByteArrayOutputStream r4 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ IOException -> 0x01db }
            int r5 = r3.length     // Catch:{ IOException -> 0x01db }
            r4.<init>(r5)     // Catch:{ IOException -> 0x01db }
            r13.initOutputStream(r4)     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.pdf.PdfOutputStream r4 = r13.getOutputStream()     // Catch:{ IOException -> 0x01db }
            r4.write((byte[]) r3)     // Catch:{ IOException -> 0x01db }
        L_0x00e5:
            com.itextpdf.kernel.pdf.PdfOutputStream r3 = r13.getOutputStream()     // Catch:{ IOException -> 0x01db }
            if (r3 == 0) goto L_0x01d3
            java.lang.String r3 = "Error in outputStream"
            if (r1 == 0) goto L_0x0149
            boolean r4 = r12.containsFlateFilter(r13)     // Catch:{ IOException -> 0x01ca }
            if (r4 != 0) goto L_0x0149
            if (r2 != 0) goto L_0x00f9
            if (r0 == 0) goto L_0x0149
        L_0x00f9:
            r12.updateCompressionFilter(r13)     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r4 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ IOException -> 0x01ca }
            r4.<init>()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.DeflaterOutputStream r5 = new com.itextpdf.io.source.DeflaterOutputStream     // Catch:{ IOException -> 0x01ca }
            int r6 = r13.getCompressionLevel()     // Catch:{ IOException -> 0x01ca }
            r5.<init>(r4, r6)     // Catch:{ IOException -> 0x01ca }
            boolean r6 = r13 instanceof com.itextpdf.kernel.pdf.PdfObjectStream     // Catch:{ IOException -> 0x01ca }
            if (r6 == 0) goto L_0x012c
            r3 = r13
            com.itextpdf.kernel.pdf.PdfObjectStream r3 = (com.itextpdf.kernel.pdf.PdfObjectStream) r3     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.pdf.PdfOutputStream r6 = r3.getIndexStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r6 = r6.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r6 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r6     // Catch:{ IOException -> 0x01ca }
            r6.writeTo(r5)     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.pdf.PdfOutputStream r6 = r3.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r6 = r6.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r6 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r6     // Catch:{ IOException -> 0x01ca }
            r6.writeTo(r5)     // Catch:{ IOException -> 0x01ca }
            goto L_0x013f
        L_0x012c:
            com.itextpdf.kernel.pdf.PdfOutputStream r6 = r13.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            if (r6 == 0) goto L_0x0143
            com.itextpdf.kernel.pdf.PdfOutputStream r3 = r13.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r3 = r3.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r3 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r3     // Catch:{ IOException -> 0x01ca }
            r3.writeTo(r5)     // Catch:{ IOException -> 0x01ca }
        L_0x013f:
            r5.finish()     // Catch:{ IOException -> 0x01ca }
            goto L_0x0181
        L_0x0143:
            java.lang.AssertionError r6 = new java.lang.AssertionError     // Catch:{ IOException -> 0x01ca }
            r6.<init>(r3)     // Catch:{ IOException -> 0x01ca }
            throw r6     // Catch:{ IOException -> 0x01ca }
        L_0x0149:
            boolean r4 = r13 instanceof com.itextpdf.kernel.pdf.PdfObjectStream     // Catch:{ IOException -> 0x01ca }
            if (r4 == 0) goto L_0x0170
            r3 = r13
            com.itextpdf.kernel.pdf.PdfObjectStream r3 = (com.itextpdf.kernel.pdf.PdfObjectStream) r3     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r4 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ IOException -> 0x01ca }
            r4.<init>()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.pdf.PdfOutputStream r5 = r3.getIndexStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r5 = r5.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r5 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r5     // Catch:{ IOException -> 0x01ca }
            r5.writeTo(r4)     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.pdf.PdfOutputStream r5 = r3.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r5 = r5.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r5 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r5     // Catch:{ IOException -> 0x01ca }
            r5.writeTo(r4)     // Catch:{ IOException -> 0x01ca }
            goto L_0x0181
        L_0x0170:
            com.itextpdf.kernel.pdf.PdfOutputStream r4 = r13.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            if (r4 == 0) goto L_0x01c4
            com.itextpdf.kernel.pdf.PdfOutputStream r3 = r13.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            java.io.OutputStream r3 = r3.getOutputStream()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.io.source.ByteArrayOutputStream r3 = (com.itextpdf.p026io.source.ByteArrayOutputStream) r3     // Catch:{ IOException -> 0x01ca }
            r4 = r3
        L_0x0181:
            boolean r3 = r12.checkEncryption(r13)     // Catch:{ IOException -> 0x01ca }
            if (r3 == 0) goto L_0x0199
            com.itextpdf.io.source.ByteArrayOutputStream r3 = new com.itextpdf.io.source.ByteArrayOutputStream     // Catch:{ IOException -> 0x01ca }
            r3.<init>()     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.pdf.PdfEncryption r5 = r12.crypto     // Catch:{ IOException -> 0x01ca }
            com.itextpdf.kernel.crypto.OutputStreamEncryption r5 = r5.getEncryptionStream(r3)     // Catch:{ IOException -> 0x01ca }
            r4.writeTo(r5)     // Catch:{ IOException -> 0x01ca }
            r5.finish()     // Catch:{ IOException -> 0x01ca }
            r4 = r3
        L_0x0199:
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Length     // Catch:{ IOException -> 0x01db }
            com.itextpdf.kernel.pdf.PdfNumber r5 = new com.itextpdf.kernel.pdf.PdfNumber     // Catch:{ IOException -> 0x01db }
            int r6 = r4.size()     // Catch:{ IOException -> 0x01db }
            r5.<init>((int) r6)     // Catch:{ IOException -> 0x01db }
            r13.put(r3, r5)     // Catch:{ IOException -> 0x01db }
            int r3 = r4.size()     // Catch:{ IOException -> 0x01db }
            r13.updateLength(r3)     // Catch:{ IOException -> 0x01db }
            r12.write((com.itextpdf.kernel.pdf.PdfDictionary) r13)     // Catch:{ IOException -> 0x01db }
            byte[] r3 = stream     // Catch:{ IOException -> 0x01db }
            r12.writeBytes(r3)     // Catch:{ IOException -> 0x01db }
            r4.writeTo(r12)     // Catch:{ IOException -> 0x01db }
            r4.close()     // Catch:{ IOException -> 0x01db }
            byte[] r3 = endstream     // Catch:{ IOException -> 0x01db }
            r12.writeBytes(r3)     // Catch:{ IOException -> 0x01db }
        L_0x01c2:
            return
        L_0x01c4:
            java.lang.AssertionError r4 = new java.lang.AssertionError     // Catch:{ IOException -> 0x01ca }
            r4.<init>(r3)     // Catch:{ IOException -> 0x01ca }
            throw r4     // Catch:{ IOException -> 0x01ca }
        L_0x01ca:
            r3 = move-exception
            com.itextpdf.kernel.PdfException r4 = new com.itextpdf.kernel.PdfException     // Catch:{ IOException -> 0x01db }
            java.lang.String r5 = "I/O exception."
            r4.<init>((java.lang.String) r5, (java.lang.Throwable) r3)     // Catch:{ IOException -> 0x01db }
            throw r4     // Catch:{ IOException -> 0x01db }
        L_0x01d3:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ IOException -> 0x01db }
            java.lang.String r4 = "PdfStream lost OutputStream"
            r3.<init>(r4)     // Catch:{ IOException -> 0x01db }
            throw r3     // Catch:{ IOException -> 0x01db }
        L_0x01db:
            r0 = move-exception
            com.itextpdf.kernel.PdfException r1 = new com.itextpdf.kernel.PdfException
            java.lang.String r2 = "Cannot write to PdfStream."
            r1.<init>(r2, r0, r13)
            goto L_0x01e5
        L_0x01e4:
            throw r1
        L_0x01e5:
            goto L_0x01e4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfOutputStream.write(com.itextpdf.kernel.pdf.PdfStream):void");
    }

    /* access modifiers changed from: protected */
    public boolean checkEncryption(PdfStream pdfStream) {
        PdfEncryption pdfEncryption = this.crypto;
        if (pdfEncryption == null || pdfEncryption.isEmbeddedFilesOnly() || isXRefStream(pdfStream)) {
            return false;
        }
        PdfObject filter = pdfStream.get(PdfName.Filter, true);
        if (filter != null) {
            if (PdfName.Crypt.equals(filter)) {
                return false;
            }
            if (filter.getType() == 1) {
                PdfArray filters = (PdfArray) filter;
                if (filters.isEmpty() || !PdfName.Crypt.equals(filters.get(0, true))) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean containsFlateFilter(PdfStream pdfStream) {
        PdfObject filter = pdfStream.get(PdfName.Filter);
        if (filter == null) {
            return false;
        }
        if (filter.getType() == 6) {
            if (PdfName.FlateDecode.equals(filter)) {
                return true;
            }
            return false;
        } else if (filter.getType() != 1) {
            throw new PdfException(PdfException.FilterIsNotANameOrArray);
        } else if (((PdfArray) filter).contains(PdfName.FlateDecode)) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void updateCompressionFilter(PdfStream pdfStream) {
        PdfObject filter = pdfStream.get(PdfName.Filter);
        if (filter == null) {
            pdfStream.put(PdfName.Filter, PdfName.FlateDecode);
            return;
        }
        PdfArray filters = new PdfArray();
        filters.add(PdfName.FlateDecode);
        if (filter instanceof PdfArray) {
            filters.addAll((PdfArray) filter);
        } else {
            filters.add(filter);
        }
        PdfObject decodeParms = pdfStream.get(PdfName.DecodeParms);
        if (decodeParms != null) {
            if (decodeParms instanceof PdfDictionary) {
                PdfArray array = new PdfArray();
                array.add(new PdfNull());
                array.add(decodeParms);
                pdfStream.put(PdfName.DecodeParms, array);
            } else if (decodeParms instanceof PdfArray) {
                ((PdfArray) decodeParms).add(0, new PdfNull());
            } else {
                throw new PdfException(PdfException.DecodeParameterType1IsNotSupported).setMessageParams(decodeParms.getClass().toString());
            }
        }
        pdfStream.put(PdfName.Filter, filters);
    }

    /* access modifiers changed from: protected */
    public byte[] decodeFlateBytes(PdfStream stream2, byte[] bytes) {
        PdfName filterName;
        PdfDictionary decodeParams;
        PdfObject filterObject = stream2.get(PdfName.Filter);
        if (filterObject == null) {
            return bytes;
        }
        PdfArray filtersArray = null;
        if (filterObject instanceof PdfName) {
            filterName = (PdfName) filterObject;
        } else if (filterObject instanceof PdfArray) {
            filtersArray = (PdfArray) filterObject;
            filterName = filtersArray.getAsName(0);
        } else {
            throw new PdfException(PdfException.FilterIsNotANameOrArray);
        }
        if (!PdfName.FlateDecode.equals(filterName)) {
            return bytes;
        }
        PdfArray decodeParamsArray = null;
        PdfObject decodeParamsObject = stream2.get(PdfName.DecodeParms);
        if (decodeParamsObject == null) {
            decodeParams = null;
        } else if (decodeParamsObject.getType() == 3) {
            decodeParams = (PdfDictionary) decodeParamsObject;
        } else if (decodeParamsObject.getType() == 1) {
            decodeParamsArray = decodeParamsObject;
            decodeParams = decodeParamsArray.getAsDictionary(0);
        } else {
            throw new PdfException(PdfException.DecodeParameterType1IsNotSupported).setMessageParams(decodeParamsObject.getClass().toString());
        }
        byte[] res = FlateDecodeFilter.flateDecode(bytes, true);
        if (res == null) {
            res = FlateDecodeFilter.flateDecode(bytes, false);
        }
        byte[] bytes2 = FlateDecodeFilter.decodePredictor(res, decodeParams);
        PdfObject filterObject2 = null;
        if (filtersArray != null) {
            filtersArray.remove(0);
            if (filtersArray.size() == 1) {
                filterObject2 = filtersArray.get(0);
            } else if (!filtersArray.isEmpty()) {
                filterObject2 = filtersArray;
            }
        }
        PdfObject decodeParamsObject2 = null;
        if (decodeParamsArray != null) {
            decodeParamsArray.remove(0);
            if (decodeParamsArray.size() == 1 && decodeParamsArray.get(0).getType() != 7) {
                decodeParamsObject2 = decodeParamsArray.get(0);
            } else if (!decodeParamsArray.isEmpty()) {
                decodeParamsObject2 = decodeParamsArray;
            }
        }
        if (filterObject2 == null) {
            stream2.remove(PdfName.Filter);
        } else {
            stream2.put(PdfName.Filter, filterObject2);
        }
        if (decodeParamsObject2 == null) {
            stream2.remove(PdfName.DecodeParms);
        } else {
            stream2.put(PdfName.DecodeParms, decodeParamsObject2);
        }
        return bytes2;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.outputStream == null && this.duplicateContentBuffer != null) {
            this.outputStream = new ByteArrayOutputStream();
            write(this.duplicateContentBuffer);
            this.duplicateContentBuffer = null;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        java.io.OutputStream tempOutputStream = this.outputStream;
        if (this.outputStream instanceof java.io.ByteArrayOutputStream) {
            this.duplicateContentBuffer = ((java.io.ByteArrayOutputStream) this.outputStream).toByteArray();
        }
        this.outputStream = null;
        out.defaultWriteObject();
        this.outputStream = tempOutputStream;
    }
}

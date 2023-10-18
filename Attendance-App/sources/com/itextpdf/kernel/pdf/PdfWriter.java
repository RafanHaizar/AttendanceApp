package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class PdfWriter extends PdfOutputStream implements Serializable {
    private static final byte[] endobj = ByteUtils.getIsoBytes("\nendobj\n");
    private static final byte[] obj = ByteUtils.getIsoBytes(" obj\n");
    private static final long serialVersionUID = -6875544505477707103L;
    private Map<PdfDocument.IndirectRefDescription, PdfIndirectReference> copiedObjects;
    private PdfOutputStream duplicateStream;
    protected boolean isUserWarnedAboutAcroFormCopying;
    PdfObjectStream objectStream;
    protected WriterProperties properties;
    private SmartModePdfObjectsSerializer smartModeSerializer;

    public PdfWriter(File file) throws FileNotFoundException {
        this(file.getAbsolutePath());
    }

    public PdfWriter(OutputStream os) {
        this(os, new WriterProperties());
    }

    public PdfWriter(OutputStream os, WriterProperties properties2) {
        super(FileUtil.wrapWithBufferedOutputStream(os));
        this.duplicateStream = null;
        this.objectStream = null;
        this.copiedObjects = new LinkedHashMap();
        this.smartModeSerializer = new SmartModePdfObjectsSerializer();
        this.properties = properties2;
        if (properties2.debugMode) {
            setDebugMode();
        }
    }

    public PdfWriter(String filename) throws FileNotFoundException {
        this(filename, new WriterProperties());
    }

    public PdfWriter(String filename, WriterProperties properties2) throws FileNotFoundException {
        this(FileUtil.getBufferedOutputStream(filename), properties2);
    }

    public boolean isFullCompression() {
        if (this.properties.isFullCompression != null) {
            return this.properties.isFullCompression.booleanValue();
        }
        return false;
    }

    public int getCompressionLevel() {
        return this.properties.compressionLevel;
    }

    public PdfWriter setCompressionLevel(int compressionLevel) {
        this.properties.setCompressionLevel(compressionLevel);
        return this;
    }

    public PdfWriter setSmartMode(boolean smartMode) {
        this.properties.smartMode = smartMode;
        return this;
    }

    public void write(int b) throws IOException {
        super.write(b);
        PdfOutputStream pdfOutputStream = this.duplicateStream;
        if (pdfOutputStream != null) {
            pdfOutputStream.write(b);
        }
    }

    public void write(byte[] b) throws IOException {
        super.write(b);
        PdfOutputStream pdfOutputStream = this.duplicateStream;
        if (pdfOutputStream != null) {
            pdfOutputStream.write(b);
        }
    }

    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        PdfOutputStream pdfOutputStream = this.duplicateStream;
        if (pdfOutputStream != null) {
            pdfOutputStream.write(b, off, len);
        }
    }

    public void close() throws IOException {
        Class<PdfWriter> cls = PdfWriter.class;
        try {
            super.close();
            try {
                PdfOutputStream pdfOutputStream = this.duplicateStream;
                if (pdfOutputStream != null) {
                    pdfOutputStream.close();
                }
            } catch (Exception ex) {
                LoggerFactory.getLogger((Class<?>) cls).error("Closing of the duplicatedStream failed.", (Throwable) ex);
            }
        } catch (Throwable th) {
            try {
                if (this.duplicateStream != null) {
                    this.duplicateStream.close();
                }
            } catch (Exception ex2) {
                LoggerFactory.getLogger((Class<?>) cls).error("Closing of the duplicatedStream failed.", (Throwable) ex2);
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public PdfObjectStream getObjectStream() {
        if (!isFullCompression()) {
            return null;
        }
        PdfObjectStream pdfObjectStream = this.objectStream;
        if (pdfObjectStream == null) {
            this.objectStream = new PdfObjectStream(this.document);
        } else if (pdfObjectStream.getSize() == 200) {
            this.objectStream.flush();
            this.objectStream = new PdfObjectStream(this.objectStream);
        }
        return this.objectStream;
    }

    /* access modifiers changed from: protected */
    public void initCryptoIfSpecified(PdfVersion version) {
        EncryptionProperties encryptProps = this.properties.encryptionProperties;
        if (this.properties.isStandardEncryptionUsed()) {
            this.crypto = new PdfEncryption(encryptProps.userPassword, encryptProps.ownerPassword, encryptProps.standardEncryptPermissions, encryptProps.encryptionAlgorithm, ByteUtils.getIsoBytes(this.document.getOriginalDocumentId().getValue()), version);
        } else if (this.properties.isPublicKeyEncryptionUsed()) {
            this.crypto = new PdfEncryption(encryptProps.publicCertificates, encryptProps.publicKeyEncryptPermissions, encryptProps.encryptionAlgorithm, version);
        }
    }

    /* access modifiers changed from: protected */
    public void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
        PdfIndirectReference indirectReference = pdfObject.getIndirectReference();
        if (!isFullCompression() || !canBeInObjStm) {
            indirectReference.setOffset(getCurrentPos());
            writeToBody(pdfObject);
        } else {
            getObjectStream().addObject(pdfObject);
        }
        indirectReference.setState(1).clearState(32);
        switch (pdfObject.getType()) {
            case 1:
                PdfArray array = (PdfArray) pdfObject;
                markArrayContentToFlush(array);
                array.releaseContent();
                return;
            case 2:
            case 6:
            case 7:
            case 8:
            case 10:
                ((PdfPrimitiveObject) pdfObject).content = null;
                return;
            case 3:
            case 9:
                PdfDictionary dictionary = (PdfDictionary) pdfObject;
                markDictionaryContentToFlush(dictionary);
                dictionary.releaseContent();
                return;
            case 5:
                markObjectToFlush(((PdfIndirectReference) pdfObject).getRefersTo(false));
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0032, code lost:
        r1 = new com.itextpdf.kernel.pdf.PdfDocument.IndirectRefDescription(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        r3 = r7.smartModeSerializer.serializeObject(r8);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.pdf.PdfObject copyObject(com.itextpdf.kernel.pdf.PdfObject r8, com.itextpdf.kernel.pdf.PdfDocument r9, boolean r10) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.itextpdf.kernel.pdf.PdfIndirectReference
            if (r0 == 0) goto L_0x000b
            r0 = r8
            com.itextpdf.kernel.pdf.PdfIndirectReference r0 = (com.itextpdf.kernel.pdf.PdfIndirectReference) r0
            com.itextpdf.kernel.pdf.PdfObject r8 = r0.getRefersTo()
        L_0x000b:
            if (r8 != 0) goto L_0x000f
            com.itextpdf.kernel.pdf.PdfNull r8 = com.itextpdf.kernel.pdf.PdfNull.PDF_NULL
        L_0x000f:
            com.itextpdf.kernel.pdf.PdfName r0 = com.itextpdf.kernel.pdf.PdfName.Catalog
            boolean r0 = checkTypeOfPdfDictionary(r8, r0)
            if (r0 == 0) goto L_0x0024
            java.lang.Class<com.itextpdf.kernel.pdf.PdfReader> r0 = com.itextpdf.kernel.pdf.PdfReader.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.String r1 = "Make copy of Catalog dictionary is forbidden."
            r0.warn(r1)
            com.itextpdf.kernel.pdf.PdfNull r8 = com.itextpdf.kernel.pdf.PdfNull.PDF_NULL
        L_0x0024:
            com.itextpdf.kernel.pdf.PdfIndirectReference r0 = r8.getIndirectReference()
            r1 = 0
            if (r10 != 0) goto L_0x002f
            if (r0 == 0) goto L_0x002f
            r2 = 1
            goto L_0x0030
        L_0x002f:
            r2 = 0
        L_0x0030:
            if (r2 == 0) goto L_0x0047
            com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription r3 = new com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription
            r3.<init>(r0)
            r1 = r3
            java.util.Map<com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription, com.itextpdf.kernel.pdf.PdfIndirectReference> r3 = r7.copiedObjects
            java.lang.Object r3 = r3.get(r1)
            com.itextpdf.kernel.pdf.PdfIndirectReference r3 = (com.itextpdf.kernel.pdf.PdfIndirectReference) r3
            if (r3 == 0) goto L_0x0047
            com.itextpdf.kernel.pdf.PdfObject r4 = r3.getRefersTo()
            return r4
        L_0x0047:
            r3 = 0
            com.itextpdf.kernel.pdf.WriterProperties r4 = r7.properties
            boolean r4 = r4.smartMode
            if (r4 == 0) goto L_0x006e
            if (r2 == 0) goto L_0x006e
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.Page
            boolean r4 = checkTypeOfPdfDictionary(r8, r4)
            if (r4 != 0) goto L_0x006e
            com.itextpdf.kernel.pdf.SmartModePdfObjectsSerializer r4 = r7.smartModeSerializer
            com.itextpdf.kernel.pdf.SerializedObjectContent r3 = r4.serializeObject(r8)
            com.itextpdf.kernel.pdf.SmartModePdfObjectsSerializer r4 = r7.smartModeSerializer
            com.itextpdf.kernel.pdf.PdfIndirectReference r4 = r4.getSavedSerializedObject(r3)
            if (r4 == 0) goto L_0x006e
            java.util.Map<com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription, com.itextpdf.kernel.pdf.PdfIndirectReference> r5 = r7.copiedObjects
            r5.put(r1, r4)
            com.itextpdf.kernel.pdf.PdfObject r5 = r4.refersTo
            return r5
        L_0x006e:
            com.itextpdf.kernel.pdf.PdfObject r4 = r8.newInstance()
            if (r0 == 0) goto L_0x0090
            if (r1 != 0) goto L_0x007c
            com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription r5 = new com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription
            r5.<init>(r0)
            r1 = r5
        L_0x007c:
            com.itextpdf.kernel.pdf.PdfObject r5 = r4.makeIndirect(r9)
            com.itextpdf.kernel.pdf.PdfIndirectReference r5 = r5.getIndirectReference()
            if (r3 == 0) goto L_0x008b
            com.itextpdf.kernel.pdf.SmartModePdfObjectsSerializer r6 = r7.smartModeSerializer
            r6.saveSerializedObject(r3, r5)
        L_0x008b:
            java.util.Map<com.itextpdf.kernel.pdf.PdfDocument$IndirectRefDescription, com.itextpdf.kernel.pdf.PdfIndirectReference> r6 = r7.copiedObjects
            r6.put(r1, r5)
        L_0x0090:
            r4.copyContent(r8, r9)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfWriter.copyObject(com.itextpdf.kernel.pdf.PdfObject, com.itextpdf.kernel.pdf.PdfDocument, boolean):com.itextpdf.kernel.pdf.PdfObject");
    }

    /* access modifiers changed from: protected */
    public void writeToBody(PdfObject pdfObj) throws IOException {
        if (this.crypto != null) {
            this.crypto.setHashKeyForNextObject(pdfObj.getIndirectReference().getObjNumber(), pdfObj.getIndirectReference().getGenNumber());
        }
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) writeInteger(pdfObj.getIndirectReference().getObjNumber())).writeSpace()).writeInteger(pdfObj.getIndirectReference().getGenNumber())).writeBytes(obj);
        write(pdfObj);
        writeBytes(endobj);
    }

    /* access modifiers changed from: protected */
    public void writeHeader() {
        ((PdfOutputStream) ((PdfOutputStream) writeByte(37)).writeString(this.document.getPdfVersion().toString())).writeString("\n%âãÏÓ\n");
    }

    /* access modifiers changed from: protected */
    public void flushWaitingObjects(Set<PdfIndirectReference> forbiddenToFlush) {
        PdfObject obj2;
        PdfXrefTable xref = this.document.getXref();
        boolean needFlush = true;
        while (needFlush) {
            needFlush = false;
            for (int i = 1; i < xref.size(); i++) {
                PdfIndirectReference indirectReference = xref.get(i);
                if (indirectReference != null && !indirectReference.isFree() && indirectReference.checkState(32) && !forbiddenToFlush.contains(indirectReference) && (obj2 = indirectReference.getRefersTo(false)) != null) {
                    obj2.flush();
                    needFlush = true;
                }
            }
        }
        PdfObjectStream pdfObjectStream = this.objectStream;
        if (pdfObjectStream != null && pdfObjectStream.getSize() > 0) {
            this.objectStream.flush();
            this.objectStream = null;
        }
    }

    /* access modifiers changed from: protected */
    public void flushModifiedWaitingObjects(Set<PdfIndirectReference> forbiddenToFlush) {
        PdfObject obj2;
        PdfXrefTable xref = this.document.getXref();
        for (int i = 1; i < xref.size(); i++) {
            PdfIndirectReference indirectReference = xref.get(i);
            if (indirectReference != null && !indirectReference.isFree() && !forbiddenToFlush.contains(indirectReference) && indirectReference.checkState(8) && (obj2 = indirectReference.getRefersTo(false)) != null && !obj2.equals(this.objectStream)) {
                obj2.flush();
            }
        }
        PdfObjectStream pdfObjectStream = this.objectStream;
        if (pdfObjectStream != null && pdfObjectStream.getSize() > 0) {
            this.objectStream.flush();
            this.objectStream = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void flushCopiedObjects(long docId) {
        List<PdfDocument.IndirectRefDescription> remove = new ArrayList<>();
        for (Map.Entry<PdfDocument.IndirectRefDescription, PdfIndirectReference> copiedObject : this.copiedObjects.entrySet()) {
            if (copiedObject.getKey().docId == docId && copiedObject.getValue().refersTo != null) {
                copiedObject.getValue().refersTo.flush();
                remove.add(copiedObject.getKey());
            }
        }
        for (PdfDocument.IndirectRefDescription ird : remove) {
            this.copiedObjects.remove(ird);
        }
    }

    private void markArrayContentToFlush(PdfArray array) {
        for (int i = 0; i < array.size(); i++) {
            markObjectToFlush(array.get(i, false));
        }
    }

    private void markDictionaryContentToFlush(PdfDictionary dictionary) {
        for (PdfObject item : dictionary.values(false)) {
            markObjectToFlush(item);
        }
    }

    private void markObjectToFlush(PdfObject pdfObject) {
        if (pdfObject != null) {
            PdfIndirectReference indirectReference = pdfObject.getIndirectReference();
            if (indirectReference != null) {
                if (!indirectReference.checkState(1)) {
                    indirectReference.setState(32);
                }
            } else if (pdfObject.getType() == 5) {
                if (!pdfObject.checkState(1)) {
                    pdfObject.setState(32);
                }
            } else if (pdfObject.getType() == 1) {
                markArrayContentToFlush((PdfArray) pdfObject);
            } else if (pdfObject.getType() == 3) {
                markDictionaryContentToFlush((PdfDictionary) pdfObject);
            }
        }
    }

    private PdfWriter setDebugMode() {
        this.duplicateStream = new PdfOutputStream(new ByteArrayOutputStream());
        return this;
    }

    private byte[] getDebugBytes() throws IOException {
        PdfOutputStream pdfOutputStream = this.duplicateStream;
        if (pdfOutputStream == null) {
            return null;
        }
        pdfOutputStream.flush();
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) this.duplicateStream.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        return byteArrayOutputStream.toByteArray();
    }

    private static boolean checkTypeOfPdfDictionary(PdfObject dictionary, PdfName expectedType) {
        return dictionary.isDictionary() && expectedType.equals(((PdfDictionary) dictionary).getAsName(PdfName.Type));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.outputStream == null) {
            this.outputStream = new ByteArrayOutputStream().assignBytes(getDebugBytes());
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (this.duplicateStream != null) {
            OutputStream tempOutputStream = this.outputStream;
            this.outputStream = null;
            out.defaultWriteObject();
            this.outputStream = tempOutputStream;
            return;
        }
        throw new NotSerializableException(getClass().getName() + ": debug mode is disabled!");
    }
}

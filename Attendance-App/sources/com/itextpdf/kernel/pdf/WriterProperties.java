package com.itextpdf.kernel.pdf;

import java.io.Serializable;
import java.security.cert.Certificate;

public class WriterProperties implements Serializable {
    private static final long serialVersionUID = -8692165914703604764L;
    protected boolean addUAXmpMetadata = false;
    protected boolean addXmpMetadata;
    protected int compressionLevel = -1;
    protected boolean debugMode = false;
    protected EncryptionProperties encryptionProperties = new EncryptionProperties();
    protected PdfString initialDocumentId;
    protected Boolean isFullCompression = null;
    protected PdfString modifiedDocumentId;
    protected PdfVersion pdfVersion;
    protected boolean smartMode = false;

    public WriterProperties setPdfVersion(PdfVersion version) {
        this.pdfVersion = version;
        return this;
    }

    public WriterProperties useSmartMode() {
        this.smartMode = true;
        return this;
    }

    public WriterProperties addXmpMetadata() {
        this.addXmpMetadata = true;
        return this;
    }

    public WriterProperties setCompressionLevel(int compressionLevel2) {
        this.compressionLevel = compressionLevel2;
        return this;
    }

    public WriterProperties setFullCompressionMode(boolean fullCompressionMode) {
        this.isFullCompression = Boolean.valueOf(fullCompressionMode);
        return this;
    }

    public WriterProperties setStandardEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionAlgorithm) {
        this.encryptionProperties.setStandardEncryption(userPassword, ownerPassword, permissions, encryptionAlgorithm);
        return this;
    }

    public WriterProperties setPublicKeyEncryption(Certificate[] certs, int[] permissions, int encryptionAlgorithm) {
        this.encryptionProperties.setPublicKeyEncryption(certs, permissions, encryptionAlgorithm);
        return this;
    }

    public WriterProperties setInitialDocumentId(PdfString initialDocumentId2) {
        this.initialDocumentId = initialDocumentId2;
        return this;
    }

    public WriterProperties setModifiedDocumentId(PdfString modifiedDocumentId2) {
        this.modifiedDocumentId = modifiedDocumentId2;
        return this;
    }

    public WriterProperties useDebugMode() {
        this.debugMode = true;
        return this;
    }

    public WriterProperties addUAXmpMetadata() {
        this.addUAXmpMetadata = true;
        return addXmpMetadata();
    }

    /* access modifiers changed from: package-private */
    public boolean isStandardEncryptionUsed() {
        return this.encryptionProperties.isStandardEncryptionUsed();
    }

    /* access modifiers changed from: package-private */
    public boolean isPublicKeyEncryptionUsed() {
        return this.encryptionProperties.isPublicKeyEncryptionUsed();
    }
}

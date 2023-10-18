package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;

public class StandardHandlerUsingStandard128 extends StandardHandlerUsingStandard40 {
    private static final long serialVersionUID = 7184848757909055679L;

    public StandardHandlerUsingStandard128(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
        super(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId);
    }

    public StandardHandlerUsingStandard128(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
        super(encryptionDictionary, password, documentId, encryptMetadata);
    }

    /* access modifiers changed from: protected */
    public void calculatePermissions(int permissions) {
        this.permissions = (long) ((permissions | -3904) & -4);
    }

    /* access modifiers changed from: protected */
    public byte[] computeOwnerKey(byte[] userPad, byte[] ownerPad) {
        byte[] ownerKey = new byte[32];
        byte[] digest = this.md5.digest(ownerPad);
        byte[] mkey = new byte[(this.keyLength / 8)];
        for (int k = 0; k < 50; k++) {
            this.md5.update(digest, 0, mkey.length);
            System.arraycopy(this.md5.digest(), 0, digest, 0, mkey.length);
        }
        System.arraycopy(userPad, 0, ownerKey, 0, 32);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < mkey.length; j++) {
                mkey[j] = (byte) (digest[j] ^ i);
            }
            this.arcfour.prepareARCFOURKey(mkey);
            this.arcfour.encryptARCFOUR(ownerKey);
        }
        return ownerKey;
    }

    /* access modifiers changed from: protected */
    public void computeGlobalEncryptionKey(byte[] userPad, byte[] ownerKey, boolean encryptMetadata) {
        this.mkey = new byte[(this.keyLength / 8)];
        this.md5.reset();
        this.md5.update(userPad);
        this.md5.update(ownerKey);
        this.md5.update(new byte[]{(byte) ((int) this.permissions), (byte) ((int) (this.permissions >> 8)), (byte) ((int) (this.permissions >> 16)), (byte) ((int) (this.permissions >> 24))}, 0, 4);
        if (this.documentId != null) {
            this.md5.update(this.documentId);
        }
        if (!encryptMetadata) {
            this.md5.update(metadataPad);
        }
        byte[] digest = new byte[this.mkey.length];
        System.arraycopy(this.md5.digest(), 0, digest, 0, this.mkey.length);
        for (int k = 0; k < 50; k++) {
            System.arraycopy(this.md5.digest(digest), 0, digest, 0, this.mkey.length);
        }
        System.arraycopy(digest, 0, this.mkey, 0, this.mkey.length);
    }

    /* access modifiers changed from: protected */
    public byte[] computeUserKey() {
        byte[] userKey = new byte[32];
        this.md5.update(pad);
        byte[] digest = this.md5.digest(this.documentId);
        System.arraycopy(digest, 0, userKey, 0, 16);
        for (int k = 16; k < 32; k++) {
            userKey[k] = 0;
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < this.mkey.length; j++) {
                digest[j] = (byte) (this.mkey[j] ^ i);
            }
            this.arcfour.prepareARCFOURKey(digest, 0, this.mkey.length);
            this.arcfour.encryptARCFOUR(userKey, 0, 16);
        }
        return userKey;
    }

    /* access modifiers changed from: protected */
    public void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
        if (encryptMetadata) {
            encryptionDictionary.put(PdfName.f1376R, new PdfNumber(3));
            encryptionDictionary.put(PdfName.f1406V, new PdfNumber(2));
            return;
        }
        encryptionDictionary.put(PdfName.EncryptMetadata, PdfBoolean.FALSE);
        encryptionDictionary.put(PdfName.f1376R, new PdfNumber(4));
        encryptionDictionary.put(PdfName.f1406V, new PdfNumber(4));
        PdfDictionary stdcf = new PdfDictionary();
        stdcf.put(PdfName.Length, new PdfNumber(16));
        if (embeddedFilesOnly) {
            stdcf.put(PdfName.AuthEvent, PdfName.EFOpen);
            encryptionDictionary.put(PdfName.EFF, PdfName.StdCF);
            encryptionDictionary.put(PdfName.StrF, PdfName.Identity);
            encryptionDictionary.put(PdfName.StmF, PdfName.Identity);
        } else {
            stdcf.put(PdfName.AuthEvent, PdfName.DocOpen);
            encryptionDictionary.put(PdfName.StrF, PdfName.StdCF);
            encryptionDictionary.put(PdfName.StmF, PdfName.StdCF);
        }
        stdcf.put(PdfName.CFM, PdfName.f1407V2);
        PdfDictionary cf = new PdfDictionary();
        cf.put(PdfName.StdCF, stdcf);
        encryptionDictionary.put(PdfName.f1304CF, cf);
    }

    /* access modifiers changed from: protected */
    public boolean isValidPassword(byte[] uValue, byte[] userKey) {
        return !equalsArray(uValue, userKey, 16);
    }
}

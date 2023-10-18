package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.crypto.AesDecryptor;
import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.OutputStreamAesEncryption;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import java.io.OutputStream;

public class StandardHandlerUsingAes128 extends StandardHandlerUsingStandard128 {
    private static final byte[] salt = {115, 65, 108, 84};
    private static final long serialVersionUID = -5459302622100333593L;

    public StandardHandlerUsingAes128(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
        super(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId);
    }

    public StandardHandlerUsingAes128(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
        super(encryptionDictionary, password, documentId, encryptMetadata);
    }

    public OutputStreamEncryption getEncryptionStream(OutputStream os) {
        return new OutputStreamAesEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public IDecryptor getDecryptor() {
        return new AesDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public void setHashKeyForNextObject(int objNumber, int objGeneration) {
        this.md5.reset();
        this.extra[0] = (byte) objNumber;
        this.extra[1] = (byte) (objNumber >> 8);
        this.extra[2] = (byte) (objNumber >> 16);
        this.extra[3] = (byte) objGeneration;
        this.extra[4] = (byte) (objGeneration >> 8);
        this.md5.update(this.mkey);
        this.md5.update(this.extra);
        this.md5.update(salt);
        this.nextObjectKey = this.md5.digest();
        this.nextObjectKeySize = this.mkey.length + 5;
        if (this.nextObjectKeySize > 16) {
            this.nextObjectKeySize = 16;
        }
    }

    /* access modifiers changed from: protected */
    public void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
        if (!encryptMetadata) {
            encryptionDictionary.put(PdfName.EncryptMetadata, PdfBoolean.FALSE);
        }
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
        stdcf.put(PdfName.CFM, PdfName.AESV2);
        PdfDictionary cf = new PdfDictionary();
        cf.put(PdfName.StdCF, stdcf);
        encryptionDictionary.put(PdfName.f1304CF, cf);
    }
}

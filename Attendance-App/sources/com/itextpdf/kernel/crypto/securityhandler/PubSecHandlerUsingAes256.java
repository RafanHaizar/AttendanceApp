package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import java.security.Key;
import java.security.cert.Certificate;

public class PubSecHandlerUsingAes256 extends PubSecHandlerUsingAes128 {
    private static final long serialVersionUID = -9158784716845784422L;

    public PubSecHandlerUsingAes256(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
        super(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
    }

    public PubSecHandlerUsingAes256(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
        super(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
    }

    public void setHashKeyForNextObject(int objNumber, int objGeneration) {
    }

    /* access modifiers changed from: protected */
    public String getDigestAlgorithm() {
        return "SHA-256";
    }

    /* access modifiers changed from: protected */
    public void initKey(byte[] globalKey, int keyLength) {
        this.nextObjectKey = globalKey;
        this.nextObjectKeySize = 32;
    }

    /* access modifiers changed from: protected */
    public void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
        encryptionDictionary.put(PdfName.Filter, PdfName.Adobe_PubSec);
        encryptionDictionary.put(PdfName.SubFilter, PdfName.Adbe_pkcs7_s5);
        encryptionDictionary.put(PdfName.f1376R, new PdfNumber(5));
        encryptionDictionary.put(PdfName.f1406V, new PdfNumber(5));
        PdfArray recipients = createRecipientsArray();
        PdfDictionary stdcf = new PdfDictionary();
        stdcf.put(PdfName.Recipients, recipients);
        if (!encryptMetadata) {
            stdcf.put(PdfName.EncryptMetadata, PdfBoolean.FALSE);
        }
        stdcf.put(PdfName.CFM, PdfName.AESV3);
        stdcf.put(PdfName.Length, new PdfNumber(256));
        PdfDictionary cf = new PdfDictionary();
        cf.put(PdfName.DefaultCryptFilter, stdcf);
        encryptionDictionary.put(PdfName.f1304CF, cf);
        if (embeddedFilesOnly) {
            encryptionDictionary.put(PdfName.EFF, PdfName.DefaultCryptFilter);
            encryptionDictionary.put(PdfName.StrF, PdfName.Identity);
            encryptionDictionary.put(PdfName.StmF, PdfName.Identity);
            return;
        }
        encryptionDictionary.put(PdfName.StrF, PdfName.DefaultCryptFilter);
        encryptionDictionary.put(PdfName.StmF, PdfName.DefaultCryptFilter);
    }
}

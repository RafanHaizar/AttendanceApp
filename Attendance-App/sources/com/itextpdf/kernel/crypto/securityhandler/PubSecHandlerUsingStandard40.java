package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import com.itextpdf.kernel.crypto.OutputStreamStandardEncryption;
import com.itextpdf.kernel.crypto.StandardDecryptor;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import java.io.OutputStream;
import java.security.Key;
import java.security.cert.Certificate;

public class PubSecHandlerUsingStandard40 extends PubKeySecurityHandler {
    private static final long serialVersionUID = -4875474035831723279L;

    public PubSecHandlerUsingStandard40(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
        initKeyAndFillDictionary(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
    }

    public PubSecHandlerUsingStandard40(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
        initKeyAndReadDictionary(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
    }

    public OutputStreamEncryption getEncryptionStream(OutputStream os) {
        return new OutputStreamStandardEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public IDecryptor getDecryptor() {
        return new StandardDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    /* access modifiers changed from: protected */
    public String getDigestAlgorithm() {
        return "SHA-1";
    }

    /* access modifiers changed from: protected */
    public void initKey(byte[] globalKey, int keyLength) {
        this.mkey = new byte[(keyLength / 8)];
        System.arraycopy(globalKey, 0, this.mkey, 0, this.mkey.length);
    }

    /* access modifiers changed from: protected */
    public void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
        encryptionDictionary.put(PdfName.Filter, PdfName.Adobe_PubSec);
        encryptionDictionary.put(PdfName.f1376R, new PdfNumber(2));
        PdfArray recipients = createRecipientsArray();
        encryptionDictionary.put(PdfName.f1406V, new PdfNumber(1));
        encryptionDictionary.put(PdfName.SubFilter, PdfName.Adbe_pkcs7_s4);
        encryptionDictionary.put(PdfName.Recipients, recipients);
    }
}

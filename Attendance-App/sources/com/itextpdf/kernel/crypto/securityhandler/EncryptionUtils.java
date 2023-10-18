package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfEncryptor;
import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.RecipientInformation;

final class EncryptionUtils {
    EncryptionUtils() {
    }

    static byte[] generateSeed(int seedLength) {
        try {
            KeyGenerator key = KeyGenerator.getInstance("AES");
            key.init(192, new SecureRandom());
            byte[] seedBytes = new byte[seedLength];
            System.arraycopy(key.generateKey().getEncoded(), 0, seedBytes, 0, seedLength);
            return seedBytes;
        } catch (NoSuchAlgorithmException e) {
            return SecureRandom.getSeed(seedLength);
        }
    }

    static byte[] fetchEnvelopedData(Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, PdfArray recipients) {
        boolean foundRecipient = false;
        byte[] envelopedData = null;
        try {
            X509CertificateHolder certHolder = new X509CertificateHolder(certificate.getEncoded());
            if (externalDecryptionProcess == null) {
                int i = 0;
                while (i < recipients.size()) {
                    try {
                        for (RecipientInformation recipientInfo : new CMSEnvelopedData(recipients.getAsString(i).getValueBytes()).getRecipientInfos().getRecipients()) {
                            if (recipientInfo.getRID().match(certHolder) && !foundRecipient) {
                                envelopedData = PdfEncryptor.getContent(recipientInfo, (PrivateKey) certificateKey, certificateKeyProvider);
                                foundRecipient = true;
                            }
                        }
                        i++;
                    } catch (Exception f) {
                        throw new PdfException(PdfException.PdfDecryption, (Throwable) f);
                    }
                }
            } else {
                int i2 = 0;
                while (i2 < recipients.size()) {
                    try {
                        RecipientInformation recipientInfo2 = new CMSEnvelopedData(recipients.getAsString(i2).getValueBytes()).getRecipientInfos().get(externalDecryptionProcess.getCmsRecipientId());
                        if (recipientInfo2 != null) {
                            envelopedData = recipientInfo2.getContent(externalDecryptionProcess.getCmsRecipient());
                            foundRecipient = true;
                        }
                        i2++;
                    } catch (Exception f2) {
                        throw new PdfException(PdfException.PdfDecryption, (Throwable) f2);
                    }
                }
            }
            if (foundRecipient && envelopedData != null) {
                return envelopedData;
            }
            throw new PdfException(PdfException.BadCertificateAndKey);
        } catch (Exception f3) {
            throw new PdfException(PdfException.PdfDecryption, (Throwable) f3);
        }
    }

    static byte[] cipherBytes(X509Certificate x509certificate, byte[] abyte0, AlgorithmIdentifier algorithmidentifier) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(algorithmidentifier.getAlgorithm().getId());
        try {
            cipher.init(1, x509certificate);
        } catch (InvalidKeyException e) {
            cipher.init(1, x509certificate.getPublicKey());
        }
        return cipher.doFinal(abyte0);
    }

    static DERForRecipientParams calculateDERForRecipientParams(byte[] in) throws IOException, GeneralSecurityException {
        DERForRecipientParams parameters = new DERForRecipientParams();
        AlgorithmParameters algorithmparameters = AlgorithmParameterGenerator.getInstance("1.2.840.113549.3.2").generateParameters();
        ASN1Primitive derobject = new ASN1InputStream((InputStream) new ByteArrayInputStream(algorithmparameters.getEncoded("ASN.1"))).readObject();
        KeyGenerator keygenerator = KeyGenerator.getInstance("1.2.840.113549.3.2");
        keygenerator.init(128);
        SecretKey secretkey = keygenerator.generateKey();
        Cipher cipher = Cipher.getInstance("1.2.840.113549.3.2");
        cipher.init(1, secretkey, algorithmparameters);
        parameters.abyte0 = secretkey.getEncoded();
        parameters.abyte1 = cipher.doFinal(in);
        parameters.algorithmIdentifier = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.3.2"), derobject);
        return parameters;
    }

    static class DERForRecipientParams {
        byte[] abyte0;
        byte[] abyte1;
        AlgorithmIdentifier algorithmIdentifier;

        DERForRecipientParams() {
        }
    }
}

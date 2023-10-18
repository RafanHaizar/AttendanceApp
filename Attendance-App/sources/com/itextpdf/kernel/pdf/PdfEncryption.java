package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import com.itextpdf.kernel.crypto.securityhandler.PubKeySecurityHandler;
import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingAes128;
import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingAes256;
import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingStandard128;
import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingStandard40;
import com.itextpdf.kernel.crypto.securityhandler.SecurityHandler;
import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingAes128;
import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingAes256;
import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingStandard128;
import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingStandard40;
import com.itextpdf.kernel.crypto.securityhandler.StandardSecurityHandler;
import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.util.SystemUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import org.slf4j.Marker;

public class PdfEncryption extends PdfObjectWrapper<PdfDictionary> {
    private static final int AES_128 = 4;
    private static final int AES_256 = 5;
    private static final int STANDARD_ENCRYPTION_128 = 3;
    private static final int STANDARD_ENCRYPTION_40 = 2;
    private static long seq = SystemUtil.getTimeBasedSeed();
    private static final long serialVersionUID = -6864863940808467156L;
    private int cryptoMode;
    private byte[] documentId;
    private boolean embeddedFilesOnly;
    private boolean encryptMetadata;
    private Long permissions;
    private SecurityHandler securityHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PdfEncryption(byte[] userPassword, byte[] ownerPassword, int permissions2, int encryptionType, byte[] documentId2, PdfVersion version) {
        super(new PdfDictionary());
        int permissions3;
        PdfVersion pdfVersion = version;
        this.documentId = documentId2;
        if (pdfVersion == null || pdfVersion.compareTo(PdfVersion.PDF_2_0) < 0) {
            permissions3 = permissions2;
        } else {
            permissions3 = fixAccessibilityPermissionPdf20(permissions2);
        }
        switch (setCryptoMode(encryptionType)) {
            case 2:
                StandardHandlerUsingStandard40 standardHandlerUsingStandard40 = new StandardHandlerUsingStandard40((PdfDictionary) getPdfObject(), userPassword, ownerPassword, permissions3, this.encryptMetadata, this.embeddedFilesOnly, documentId2);
                this.permissions = Long.valueOf(standardHandlerUsingStandard40.getPermissions());
                this.securityHandler = standardHandlerUsingStandard40;
                return;
            case 3:
                StandardHandlerUsingStandard128 standardHandlerUsingStandard128 = new StandardHandlerUsingStandard128((PdfDictionary) getPdfObject(), userPassword, ownerPassword, permissions3, this.encryptMetadata, this.embeddedFilesOnly, documentId2);
                this.permissions = Long.valueOf(standardHandlerUsingStandard128.getPermissions());
                this.securityHandler = standardHandlerUsingStandard128;
                return;
            case 4:
                StandardHandlerUsingAes128 standardHandlerUsingAes128 = new StandardHandlerUsingAes128((PdfDictionary) getPdfObject(), userPassword, ownerPassword, permissions3, this.encryptMetadata, this.embeddedFilesOnly, documentId2);
                this.permissions = Long.valueOf(standardHandlerUsingAes128.getPermissions());
                this.securityHandler = standardHandlerUsingAes128;
                return;
            case 5:
                StandardHandlerUsingAes256 handlerAes256 = new StandardHandlerUsingAes256((PdfDictionary) getPdfObject(), userPassword, ownerPassword, permissions3, this.encryptMetadata, this.embeddedFilesOnly, version);
                this.permissions = Long.valueOf(handlerAes256.getPermissions());
                this.securityHandler = handlerAes256;
                return;
            default:
                return;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PdfEncryption(Certificate[] certs, int[] permissions2, int encryptionType, PdfVersion version) {
        super(new PdfDictionary());
        int[] iArr = permissions2;
        PdfVersion pdfVersion = version;
        if (pdfVersion != null && pdfVersion.compareTo(PdfVersion.PDF_2_0) >= 0) {
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = fixAccessibilityPermissionPdf20(iArr[i]);
            }
        }
        int i2 = encryptionType;
        switch (setCryptoMode(encryptionType)) {
            case 2:
                this.securityHandler = new PubSecHandlerUsingStandard40((PdfDictionary) getPdfObject(), certs, permissions2, this.encryptMetadata, this.embeddedFilesOnly);
                return;
            case 3:
                this.securityHandler = new PubSecHandlerUsingStandard128((PdfDictionary) getPdfObject(), certs, permissions2, this.encryptMetadata, this.embeddedFilesOnly);
                return;
            case 4:
                this.securityHandler = new PubSecHandlerUsingAes128((PdfDictionary) getPdfObject(), certs, permissions2, this.encryptMetadata, this.embeddedFilesOnly);
                return;
            case 5:
                this.securityHandler = new PubSecHandlerUsingAes256((PdfDictionary) getPdfObject(), certs, permissions2, this.encryptMetadata, this.embeddedFilesOnly);
                return;
            default:
                return;
        }
    }

    public PdfEncryption(PdfDictionary pdfDict, byte[] password, byte[] documentId2) {
        super(pdfDict);
        setForbidRelease();
        this.documentId = documentId2;
        switch (readAndSetCryptoModeForStdHandler(pdfDict)) {
            case 2:
                StandardHandlerUsingStandard40 handlerStd40 = new StandardHandlerUsingStandard40((PdfDictionary) getPdfObject(), password, documentId2, this.encryptMetadata);
                this.permissions = Long.valueOf(handlerStd40.getPermissions());
                this.securityHandler = handlerStd40;
                return;
            case 3:
                StandardHandlerUsingStandard128 handlerStd128 = new StandardHandlerUsingStandard128((PdfDictionary) getPdfObject(), password, documentId2, this.encryptMetadata);
                this.permissions = Long.valueOf(handlerStd128.getPermissions());
                this.securityHandler = handlerStd128;
                return;
            case 4:
                StandardHandlerUsingAes128 handlerAes128 = new StandardHandlerUsingAes128((PdfDictionary) getPdfObject(), password, documentId2, this.encryptMetadata);
                this.permissions = Long.valueOf(handlerAes128.getPermissions());
                this.securityHandler = handlerAes128;
                return;
            case 5:
                StandardHandlerUsingAes256 aes256Handler = new StandardHandlerUsingAes256((PdfDictionary) getPdfObject(), password);
                this.permissions = Long.valueOf(aes256Handler.getPermissions());
                this.encryptMetadata = aes256Handler.isEncryptMetadata();
                this.securityHandler = aes256Handler;
                return;
            default:
                return;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PdfEncryption(PdfDictionary pdfDict, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess) {
        super(pdfDict);
        setForbidRelease();
        switch (readAndSetCryptoModeForPubSecHandler(pdfDict)) {
            case 2:
                this.securityHandler = new PubSecHandlerUsingStandard40((PdfDictionary) getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
                return;
            case 3:
                this.securityHandler = new PubSecHandlerUsingStandard128((PdfDictionary) getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
                return;
            case 4:
                this.securityHandler = new PubSecHandlerUsingAes128((PdfDictionary) getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
                return;
            case 5:
                this.securityHandler = new PubSecHandlerUsingAes256((PdfDictionary) getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
                return;
            default:
                return;
        }
    }

    public static byte[] generateNewDocumentId() {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            long time = SystemUtil.getTimeBasedSeed();
            StringBuilder append = new StringBuilder().append(time).append(Marker.ANY_NON_NULL_MARKER).append(SystemUtil.getFreeMemory()).append(Marker.ANY_NON_NULL_MARKER);
            long j = seq;
            seq = 1 + j;
            return md5.digest(append.append(j).toString().getBytes(StandardCharsets.ISO_8859_1));
        } catch (Exception e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    public static PdfObject createInfoId(byte[] id, boolean modified) {
        if (modified) {
            return createInfoId(id, generateNewDocumentId());
        }
        return createInfoId(id, id);
    }

    public static PdfObject createInfoId(byte[] firstId, byte[] secondId) {
        if (firstId.length < 16) {
            firstId = padByteArrayTo16(firstId);
        }
        if (secondId.length < 16) {
            secondId = padByteArrayTo16(secondId);
        }
        ByteBuffer buf = new ByteBuffer(90);
        buf.append(91).append(60);
        for (byte appendHex : firstId) {
            buf.appendHex(appendHex);
        }
        buf.append(62).append(60);
        for (byte appendHex2 : secondId) {
            buf.appendHex(appendHex2);
        }
        buf.append(62).append(93);
        return new PdfLiteral(buf.toByteArray());
    }

    private static byte[] padByteArrayTo16(byte[] documentId2) {
        byte[] paddingBytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        System.arraycopy(documentId2, 0, paddingBytes, 0, documentId2.length);
        return paddingBytes;
    }

    public Long getPermissions() {
        return this.permissions;
    }

    public int getCryptoMode() {
        return this.cryptoMode;
    }

    public boolean isMetadataEncrypted() {
        return this.encryptMetadata;
    }

    public boolean isEmbeddedFilesOnly() {
        return this.embeddedFilesOnly;
    }

    public byte[] getDocumentId() {
        return this.documentId;
    }

    public void setHashKeyForNextObject(int objNumber, int objGeneration) {
        this.securityHandler.setHashKeyForNextObject(objNumber, objGeneration);
    }

    public OutputStreamEncryption getEncryptionStream(OutputStream os) {
        return this.securityHandler.getEncryptionStream(os);
    }

    public byte[] encryptByteArray(byte[] b) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        OutputStreamEncryption ose = getEncryptionStream(ba);
        try {
            ose.write(b);
            ose.finish();
            return ba.toByteArray();
        } catch (IOException e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    public byte[] decryptByteArray(byte[] b) {
        try {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            IDecryptor dec = this.securityHandler.getDecryptor();
            byte[] b2 = dec.update(b, 0, b.length);
            if (b2 != null) {
                ba.write(b2);
            }
            byte[] b22 = dec.finish();
            if (b22 != null) {
                ba.write(b22);
            }
            return ba.toByteArray();
        } catch (IOException e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    public boolean isOpenedWithFullPermission() {
        SecurityHandler securityHandler2 = this.securityHandler;
        if (!(securityHandler2 instanceof PubKeySecurityHandler) && (securityHandler2 instanceof StandardSecurityHandler)) {
            return ((StandardSecurityHandler) securityHandler2).isUsedOwnerPassword();
        }
        return true;
    }

    public byte[] computeUserPassword(byte[] ownerPassword) {
        SecurityHandler securityHandler2 = this.securityHandler;
        if (securityHandler2 instanceof StandardHandlerUsingStandard40) {
            return ((StandardHandlerUsingStandard40) securityHandler2).computeUserPassword(ownerPassword, (PdfDictionary) getPdfObject());
        }
        return null;
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private void setKeyLength(int keyLength) {
        if (keyLength != 40) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Length, new PdfNumber(keyLength));
        }
    }

    private int setCryptoMode(int mode) {
        return setCryptoMode(mode, 0);
    }

    private int setCryptoMode(int mode, int length) {
        this.cryptoMode = mode;
        this.encryptMetadata = (mode & 8) != 8;
        this.embeddedFilesOnly = (mode & 24) == 24;
        switch (mode & 7) {
            case 0:
                this.encryptMetadata = true;
                this.embeddedFilesOnly = false;
                setKeyLength(40);
                return 2;
            case 1:
                this.embeddedFilesOnly = false;
                if (length > 0) {
                    setKeyLength(length);
                } else {
                    setKeyLength(128);
                }
                return 3;
            case 2:
                setKeyLength(128);
                return 4;
            case 3:
                setKeyLength(256);
                return 5;
            default:
                throw new PdfException(PdfException.NoValidEncryptionMode);
        }
    }

    private int readAndSetCryptoModeForStdHandler(PdfDictionary encDict) {
        int cryptoMode2;
        int cryptoMode3;
        int length = 0;
        PdfNumber rValue = encDict.getAsNumber(PdfName.f1376R);
        if (rValue != null) {
            switch (rValue.intValue()) {
                case 2:
                    cryptoMode2 = 0;
                    break;
                case 3:
                    PdfNumber lengthValue = encDict.getAsNumber(PdfName.Length);
                    if (lengthValue != null) {
                        length = lengthValue.intValue();
                        if (length <= 128 && length >= 40 && length % 8 == 0) {
                            cryptoMode2 = 1;
                            break;
                        } else {
                            throw new PdfException(PdfException.IllegalLengthValue);
                        }
                    } else {
                        throw new PdfException(PdfException.IllegalLengthValue);
                    }
                    break;
                case 4:
                    PdfDictionary dic = (PdfDictionary) encDict.get(PdfName.f1304CF);
                    if (dic != null) {
                        PdfDictionary dic2 = (PdfDictionary) dic.get(PdfName.StdCF);
                        if (dic2 != null) {
                            if (PdfName.f1407V2.equals(dic2.get(PdfName.CFM))) {
                                cryptoMode3 = 1;
                            } else if (PdfName.AESV2.equals(dic2.get(PdfName.CFM))) {
                                cryptoMode3 = 2;
                            } else {
                                throw new PdfException(PdfException.NoCompatibleEncryptionFound);
                            }
                            PdfBoolean em = encDict.getAsBoolean(PdfName.EncryptMetadata);
                            if (em != null && !em.getValue()) {
                                cryptoMode2 = cryptoMode3 | 8;
                                break;
                            } else {
                                cryptoMode2 = cryptoMode3;
                                break;
                            }
                        } else {
                            throw new PdfException(PdfException.StdcfNotFoundEncryption);
                        }
                    } else {
                        throw new PdfException(PdfException.CfNotFoundEncryption);
                    }
                    break;
                case 5:
                case 6:
                    cryptoMode2 = 3;
                    PdfBoolean em5 = encDict.getAsBoolean(PdfName.EncryptMetadata);
                    if (em5 != null && !em5.getValue()) {
                        cryptoMode2 = 3 | 8;
                        break;
                    }
                default:
                    throw new PdfException(PdfException.UnknownEncryptionTypeREq1).setMessageParams(rValue);
            }
            return setCryptoMode(cryptoMode2, length);
        }
        throw new PdfException(PdfException.IllegalRValue);
    }

    private int readAndSetCryptoModeForPubSecHandler(PdfDictionary encDict) {
        int cryptoMode2;
        int length;
        PdfNumber vValue = encDict.getAsNumber(PdfName.f1406V);
        if (vValue != null) {
            switch (vValue.intValue()) {
                case 1:
                    cryptoMode2 = 0;
                    length = 40;
                    break;
                case 2:
                    PdfNumber lengthValue = encDict.getAsNumber(PdfName.Length);
                    if (lengthValue != null) {
                        length = lengthValue.intValue();
                        if (length <= 128 && length >= 40 && length % 8 == 0) {
                            cryptoMode2 = 1;
                            break;
                        } else {
                            throw new PdfException(PdfException.IllegalLengthValue);
                        }
                    } else {
                        throw new PdfException(PdfException.IllegalLengthValue);
                    }
                case 4:
                case 5:
                    PdfDictionary dic = encDict.getAsDictionary(PdfName.f1304CF);
                    if (dic != null) {
                        PdfDictionary dic2 = (PdfDictionary) dic.get(PdfName.DefaultCryptFilter);
                        if (dic2 != null) {
                            if (PdfName.f1407V2.equals(dic2.get(PdfName.CFM))) {
                                cryptoMode2 = 1;
                                length = 128;
                            } else if (PdfName.AESV2.equals(dic2.get(PdfName.CFM))) {
                                cryptoMode2 = 2;
                                length = 128;
                            } else if (PdfName.AESV3.equals(dic2.get(PdfName.CFM))) {
                                cryptoMode2 = 3;
                                length = 256;
                            } else {
                                throw new PdfException(PdfException.NoCompatibleEncryptionFound);
                            }
                            PdfBoolean em = dic2.getAsBoolean(PdfName.EncryptMetadata);
                            if (em != null && !em.getValue()) {
                                cryptoMode2 |= 8;
                                break;
                            }
                        } else {
                            throw new PdfException(PdfException.DefaultcryptfilterNotFoundEncryption);
                        }
                    } else {
                        throw new PdfException(PdfException.CfNotFoundEncryption);
                    }
                default:
                    throw new PdfException(PdfException.UnknownEncryptionTypeVEq1, (Object) vValue);
            }
            return setCryptoMode(cryptoMode2, length);
        }
        throw new PdfException(PdfException.IllegalVValue);
    }

    private int fixAccessibilityPermissionPdf20(int permissions2) {
        return permissions2 | 512;
    }
}

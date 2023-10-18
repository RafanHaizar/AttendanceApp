package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.ARCFOUREncryption;
import com.itextpdf.kernel.crypto.BadPasswordException;
import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import com.itextpdf.kernel.crypto.OutputStreamStandardEncryption;
import com.itextpdf.kernel.crypto.StandardDecryptor;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import java.io.OutputStream;
import kotlin.jvm.internal.ByteCompanionObject;

public class StandardHandlerUsingStandard40 extends StandardSecurityHandler {
    protected static final byte[] metadataPad = {-1, -1, -1, -1};
    protected static final byte[] pad = {40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, ByteCompanionObject.MIN_VALUE, 47, 12, -87, -2, 100, 83, 105, 122};
    private static final long serialVersionUID = -7951837491441953183L;
    protected ARCFOUREncryption arcfour = new ARCFOUREncryption();
    protected byte[] documentId;
    protected int keyLength;

    public StandardHandlerUsingStandard40(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId2) {
        initKeyAndFillDictionary(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId2);
    }

    public StandardHandlerUsingStandard40(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId2, boolean encryptMetadata) {
        initKeyAndReadDictionary(encryptionDictionary, password, documentId2, encryptMetadata);
    }

    public OutputStreamEncryption getEncryptionStream(OutputStream os) {
        return new OutputStreamStandardEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public IDecryptor getDecryptor() {
        return new StandardDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public byte[] computeUserPassword(byte[] ownerPassword, PdfDictionary encryptionDictionary) {
        byte[] userPad = computeOwnerKey(getIsoBytes(encryptionDictionary.getAsString(PdfName.f1361O)), padPassword(ownerPassword));
        int i = 0;
        while (i < userPad.length) {
            boolean match = true;
            int j = 0;
            while (true) {
                if (j >= userPad.length - i) {
                    break;
                } else if (userPad[i + j] != pad[j]) {
                    match = false;
                    break;
                } else {
                    j++;
                }
            }
            if (!match) {
                i++;
            } else {
                byte[] userPassword = new byte[i];
                System.arraycopy(userPad, 0, userPassword, 0, i);
                return userPassword;
            }
        }
        return userPad;
    }

    /* access modifiers changed from: protected */
    public void calculatePermissions(int permissions) {
        this.permissions = (long) ((permissions | -64) & -4);
    }

    /* access modifiers changed from: protected */
    public byte[] computeOwnerKey(byte[] userPad, byte[] ownerPad) {
        byte[] ownerKey = new byte[32];
        this.arcfour.prepareARCFOURKey(this.md5.digest(ownerPad), 0, 5);
        this.arcfour.encryptARCFOUR(userPad, ownerKey);
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
        System.arraycopy(digest, 0, this.mkey, 0, this.mkey.length);
    }

    /* access modifiers changed from: protected */
    public byte[] computeUserKey() {
        byte[] userKey = new byte[32];
        this.arcfour.prepareARCFOURKey(this.mkey);
        this.arcfour.encryptARCFOUR(pad, userKey);
        return userKey;
    }

    /* access modifiers changed from: protected */
    public void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
        encryptionDictionary.put(PdfName.f1376R, new PdfNumber(2));
        encryptionDictionary.put(PdfName.f1406V, new PdfNumber(1));
    }

    /* access modifiers changed from: protected */
    public boolean isValidPassword(byte[] uValue, byte[] userKey) {
        return !equalsArray(uValue, userKey, 32);
    }

    private void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId2) {
        byte[] ownerPassword2 = generateOwnerPasswordIfNullOrEmpty(ownerPassword);
        calculatePermissions(permissions);
        this.documentId = documentId2;
        this.keyLength = getKeyLength(encryptionDictionary);
        byte[] userPad = padPassword(userPassword);
        byte[] ownerKey = computeOwnerKey(userPad, padPassword(ownerPassword2));
        computeGlobalEncryptionKey(userPad, ownerKey, encryptMetadata);
        setStandardHandlerDicEntries(encryptionDictionary, computeUserKey(), ownerKey);
        setSpecificHandlerDicEntries(encryptionDictionary, encryptMetadata, embeddedFilesOnly);
    }

    private void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId2, boolean encryptMetadata) {
        byte[] uValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.f1403U));
        byte[] oValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.f1361O));
        this.permissions = ((PdfNumber) encryptionDictionary.get(PdfName.f1367P)).longValue();
        this.documentId = documentId2;
        this.keyLength = getKeyLength(encryptionDictionary);
        checkPassword(encryptMetadata, uValue, oValue, padPassword(password));
    }

    private void checkPassword(boolean encryptMetadata, byte[] uValue, byte[] oValue, byte[] paddedPassword) {
        computeGlobalEncryptionKey(computeOwnerKey(oValue, paddedPassword), oValue, encryptMetadata);
        if (isValidPassword(uValue, computeUserKey())) {
            computeGlobalEncryptionKey(paddedPassword, oValue, encryptMetadata);
            if (!isValidPassword(uValue, computeUserKey())) {
                this.usedOwnerPassword = false;
                return;
            }
            throw new BadPasswordException(PdfException.BadUserPassword);
        }
    }

    private byte[] padPassword(byte[] password) {
        byte[] userPad = new byte[32];
        if (password == null) {
            System.arraycopy(pad, 0, userPad, 0, 32);
        } else {
            System.arraycopy(password, 0, userPad, 0, Math.min(password.length, 32));
            if (password.length < 32) {
                System.arraycopy(pad, 0, userPad, password.length, 32 - password.length);
            }
        }
        return userPad;
    }

    private int getKeyLength(PdfDictionary encryptionDict) {
        Integer keyLength2 = encryptionDict.getAsInt(PdfName.Length);
        if (keyLength2 != null) {
            return keyLength2.intValue();
        }
        return 40;
    }
}

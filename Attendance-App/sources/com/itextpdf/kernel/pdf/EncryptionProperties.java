package com.itextpdf.kernel.pdf;

import java.io.Serializable;
import java.security.SecureRandom;
import java.security.cert.Certificate;

public class EncryptionProperties implements Serializable {
    private static final long serialVersionUID = 3926570647944137843L;
    protected int encryptionAlgorithm;
    protected byte[] ownerPassword;
    protected Certificate[] publicCertificates;
    protected int[] publicKeyEncryptPermissions;
    protected int standardEncryptPermissions;
    protected byte[] userPassword;

    public EncryptionProperties setStandardEncryption(byte[] userPassword2, byte[] ownerPassword2, int permissions, int encryptionAlgorithm2) {
        clearEncryption();
        this.userPassword = userPassword2;
        if (ownerPassword2 != null) {
            this.ownerPassword = ownerPassword2;
        } else {
            byte[] bArr = new byte[16];
            this.ownerPassword = bArr;
            randomBytes(bArr);
        }
        this.standardEncryptPermissions = permissions;
        this.encryptionAlgorithm = encryptionAlgorithm2;
        return this;
    }

    public EncryptionProperties setPublicKeyEncryption(Certificate[] certs, int[] permissions, int encryptionAlgorithm2) {
        clearEncryption();
        this.publicCertificates = certs;
        this.publicKeyEncryptPermissions = permissions;
        this.encryptionAlgorithm = encryptionAlgorithm2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean isStandardEncryptionUsed() {
        return this.ownerPassword != null;
    }

    /* access modifiers changed from: package-private */
    public boolean isPublicKeyEncryptionUsed() {
        return this.publicCertificates != null;
    }

    private void clearEncryption() {
        this.publicCertificates = null;
        this.publicKeyEncryptPermissions = null;
        this.userPassword = null;
        this.ownerPassword = null;
    }

    private static void randomBytes(byte[] bytes) {
        new SecureRandom().nextBytes(bytes);
    }
}

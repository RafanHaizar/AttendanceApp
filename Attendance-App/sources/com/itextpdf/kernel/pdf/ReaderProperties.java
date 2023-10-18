package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import java.io.Serializable;
import java.security.Key;
import java.security.cert.Certificate;

public class ReaderProperties implements Serializable {
    private static final long serialVersionUID = 5569118801793215916L;
    protected Certificate certificate;
    protected Key certificateKey;
    protected String certificateKeyProvider;
    protected IExternalDecryptionProcess externalDecryptionProcess;
    protected MemoryLimitsAwareHandler memoryLimitsAwareHandler;
    protected byte[] password;

    public ReaderProperties setPassword(byte[] password2) {
        clearEncryptionParams();
        this.password = password2;
        return this;
    }

    public ReaderProperties setPublicKeySecurityParams(Certificate certificate2, Key certificateKey2, String certificateKeyProvider2, IExternalDecryptionProcess externalDecryptionProcess2) {
        clearEncryptionParams();
        this.certificate = certificate2;
        this.certificateKey = certificateKey2;
        this.certificateKeyProvider = certificateKeyProvider2;
        this.externalDecryptionProcess = externalDecryptionProcess2;
        return this;
    }

    public ReaderProperties setPublicKeySecurityParams(Certificate certificate2, IExternalDecryptionProcess externalDecryptionProcess2) {
        clearEncryptionParams();
        this.certificate = certificate2;
        this.externalDecryptionProcess = externalDecryptionProcess2;
        return this;
    }

    private void clearEncryptionParams() {
        this.password = null;
        this.certificate = null;
        this.certificateKey = null;
        this.certificateKeyProvider = null;
        this.externalDecryptionProcess = null;
    }

    public ReaderProperties setMemoryLimitsAwareHandler(MemoryLimitsAwareHandler memoryLimitsAwareHandler2) {
        this.memoryLimitsAwareHandler = memoryLimitsAwareHandler2;
        return this;
    }
}

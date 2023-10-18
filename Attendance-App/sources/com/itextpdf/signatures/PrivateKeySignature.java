package com.itextpdf.signatures;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

public class PrivateKeySignature implements IExternalSignature {
    private String encryptionAlgorithm;
    private String hashAlgorithm;

    /* renamed from: pk */
    private PrivateKey f1608pk;
    private String provider;

    public PrivateKeySignature(PrivateKey pk, String hashAlgorithm2, String provider2) {
        this.f1608pk = pk;
        this.provider = provider2;
        this.hashAlgorithm = DigestAlgorithms.getDigest(DigestAlgorithms.getAllowedDigest(hashAlgorithm2));
        this.encryptionAlgorithm = SignUtils.getPrivateKeyAlgorithm(pk);
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public String getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }

    public byte[] sign(byte[] message) throws GeneralSecurityException {
        Signature sig = SignUtils.getSignatureHelper(this.hashAlgorithm + "with" + this.encryptionAlgorithm, this.provider);
        sig.initSign(this.f1608pk);
        sig.update(message);
        return sig.sign();
    }
}

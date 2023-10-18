package org.bouncycastle.jcajce.util;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.ExemptionMechanism;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

public class ProviderJcaJceHelper implements JcaJceHelper {
    protected final Provider provider;

    public ProviderJcaJceHelper(Provider provider2) {
        this.provider = provider2;
    }

    public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String str) throws NoSuchAlgorithmException {
        return AlgorithmParameterGenerator.getInstance(str, this.provider);
    }

    public AlgorithmParameters createAlgorithmParameters(String str) throws NoSuchAlgorithmException {
        return AlgorithmParameters.getInstance(str, this.provider);
    }

    public CertPathBuilder createCertPathBuilder(String str) throws NoSuchAlgorithmException {
        return CertPathBuilder.getInstance(str, this.provider);
    }

    public CertPathValidator createCertPathValidator(String str) throws NoSuchAlgorithmException {
        return CertPathValidator.getInstance(str, this.provider);
    }

    public CertStore createCertStore(String str, CertStoreParameters certStoreParameters) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return CertStore.getInstance(str, certStoreParameters, this.provider);
    }

    public CertificateFactory createCertificateFactory(String str) throws CertificateException {
        return CertificateFactory.getInstance(str, this.provider);
    }

    public Cipher createCipher(String str) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance(str, this.provider);
    }

    public MessageDigest createDigest(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(str, this.provider);
    }

    public ExemptionMechanism createExemptionMechanism(String str) throws NoSuchAlgorithmException {
        return ExemptionMechanism.getInstance(str, this.provider);
    }

    public KeyAgreement createKeyAgreement(String str) throws NoSuchAlgorithmException {
        return KeyAgreement.getInstance(str, this.provider);
    }

    public KeyFactory createKeyFactory(String str) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(str, this.provider);
    }

    public KeyGenerator createKeyGenerator(String str) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(str, this.provider);
    }

    public KeyPairGenerator createKeyPairGenerator(String str) throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(str, this.provider);
    }

    public KeyStore createKeyStore(String str) throws KeyStoreException {
        return KeyStore.getInstance(str, this.provider);
    }

    public Mac createMac(String str) throws NoSuchAlgorithmException {
        return Mac.getInstance(str, this.provider);
    }

    public MessageDigest createMessageDigest(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(str, this.provider);
    }

    public SecretKeyFactory createSecretKeyFactory(String str) throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(str, this.provider);
    }

    public SecureRandom createSecureRandom(String str) throws NoSuchAlgorithmException {
        return SecureRandom.getInstance(str, this.provider);
    }

    public Signature createSignature(String str) throws NoSuchAlgorithmException {
        return Signature.getInstance(str, this.provider);
    }
}

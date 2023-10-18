package org.bouncycastle.pkcs.jcajce;

import java.io.OutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.misc.ScryptParams;
import org.bouncycastle.asn1.p005bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.util.PBKDF2Config;
import org.bouncycastle.crypto.util.PBKDFConfig;
import org.bouncycastle.crypto.util.ScryptConfig;
import org.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.bouncycastle.jcajce.p014io.CipherOutputStream;
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.SecretKeySizeProvider;

public class JcePKCSPBEOutputEncryptorBuilder {
    private ASN1ObjectIdentifier algorithm;
    private JcaJceHelper helper;
    private int iterationCount;
    private ASN1ObjectIdentifier keyEncAlgorithm;
    private SecretKeySizeProvider keySizeProvider;
    private final PBKDFConfig pbkdf;
    private PBKDF2Config.Builder pbkdfBuilder;
    private SecureRandom random;

    public JcePKCSPBEOutputEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.helper = new DefaultJcaJceHelper();
        this.keySizeProvider = DefaultSecretKeySizeProvider.INSTANCE;
        this.iterationCount = 1024;
        this.pbkdfBuilder = new PBKDF2Config.Builder();
        this.pbkdf = null;
        if (isPKCS12(aSN1ObjectIdentifier)) {
            this.algorithm = aSN1ObjectIdentifier;
        } else {
            this.algorithm = PKCSObjectIdentifiers.id_PBES2;
        }
        this.keyEncAlgorithm = aSN1ObjectIdentifier;
    }

    public JcePKCSPBEOutputEncryptorBuilder(PBKDFConfig pBKDFConfig, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.helper = new DefaultJcaJceHelper();
        this.keySizeProvider = DefaultSecretKeySizeProvider.INSTANCE;
        this.iterationCount = 1024;
        this.pbkdfBuilder = new PBKDF2Config.Builder();
        this.algorithm = PKCSObjectIdentifiers.id_PBES2;
        this.pbkdf = pBKDFConfig;
        this.keyEncAlgorithm = aSN1ObjectIdentifier;
    }

    /* access modifiers changed from: private */
    public static byte[] PKCS12PasswordToBytes(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[((cArr.length + 1) * 2)];
        for (int i = 0; i != cArr.length; i++) {
            int i2 = i * 2;
            char c = cArr[i];
            bArr[i2] = (byte) (c >>> 8);
            bArr[i2 + 1] = (byte) c;
        }
        return bArr;
    }

    /* access modifiers changed from: private */
    public static byte[] PKCS5PasswordToBytes(char[] cArr) {
        if (cArr == null) {
            return new byte[0];
        }
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i != length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }

    /* access modifiers changed from: private */
    public boolean isPKCS12(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return aSN1ObjectIdentifier.mo1052on(PKCSObjectIdentifiers.pkcs_12PbeIds) || aSN1ObjectIdentifier.mo1052on(BCObjectIdentifiers.bc_pbe_sha1_pkcs12) || aSN1ObjectIdentifier.mo1052on(BCObjectIdentifiers.bc_pbe_sha256_pkcs12);
    }

    public OutputEncryptor build(final char[] cArr) throws OperatorCreationException {
        final Cipher cipher;
        final AlgorithmIdentifier algorithmIdentifier;
        if (this.random == null) {
            this.random = new SecureRandom();
        }
        try {
            if (isPKCS12(this.algorithm)) {
                byte[] bArr = new byte[20];
                this.random.nextBytes(bArr);
                cipher = this.helper.createCipher(this.algorithm.getId());
                cipher.init(1, new PKCS12KeyWithParameters(cArr, bArr, this.iterationCount));
                algorithmIdentifier = new AlgorithmIdentifier(this.algorithm, new PKCS12PBEParams(bArr, this.iterationCount));
            } else if (this.algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_PBES2)) {
                PBKDFConfig pBKDFConfig = this.pbkdf;
                if (pBKDFConfig == null) {
                    pBKDFConfig = this.pbkdfBuilder.build();
                }
                if (MiscObjectIdentifiers.id_scrypt.equals((ASN1Primitive) pBKDFConfig.getAlgorithm())) {
                    ScryptConfig scryptConfig = (ScryptConfig) pBKDFConfig;
                    byte[] bArr2 = new byte[scryptConfig.getSaltLength()];
                    this.random.nextBytes(bArr2);
                    ScryptParams scryptParams = new ScryptParams(bArr2, scryptConfig.getCostParameter(), scryptConfig.getBlockSize(), scryptConfig.getParallelizationParameter());
                    SecretKey generateSecret = this.helper.createSecretKeyFactory("SCRYPT").generateSecret(new ScryptKeySpec(cArr, bArr2, scryptConfig.getCostParameter(), scryptConfig.getBlockSize(), scryptConfig.getParallelizationParameter(), this.keySizeProvider.getKeySize(new AlgorithmIdentifier(this.keyEncAlgorithm))));
                    Cipher createCipher = this.helper.createCipher(this.keyEncAlgorithm.getId());
                    createCipher.init(1, generateSecret, this.random);
                    algorithmIdentifier = new AlgorithmIdentifier(this.algorithm, new PBES2Parameters(new KeyDerivationFunc(MiscObjectIdentifiers.id_scrypt, scryptParams), new EncryptionScheme(this.keyEncAlgorithm, ASN1Primitive.fromByteArray(createCipher.getParameters().getEncoded()))));
                    cipher = createCipher;
                } else {
                    PBKDF2Config pBKDF2Config = (PBKDF2Config) pBKDFConfig;
                    byte[] bArr3 = new byte[pBKDF2Config.getSaltLength()];
                    this.random.nextBytes(bArr3);
                    SecretKey generateSecret2 = this.helper.createSecretKeyFactory(JceUtils.getAlgorithm(pBKDF2Config.getPRF().getAlgorithm())).generateSecret(new PBEKeySpec(cArr, bArr3, pBKDF2Config.getIterationCount(), this.keySizeProvider.getKeySize(new AlgorithmIdentifier(this.keyEncAlgorithm))));
                    Cipher createCipher2 = this.helper.createCipher(this.keyEncAlgorithm.getId());
                    createCipher2.init(1, generateSecret2, this.random);
                    algorithmIdentifier = new AlgorithmIdentifier(this.algorithm, createCipher2.getParameters() != null ? new PBES2Parameters(new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(bArr3, pBKDF2Config.getIterationCount(), pBKDF2Config.getPRF())), new EncryptionScheme(this.keyEncAlgorithm, ASN1Primitive.fromByteArray(createCipher2.getParameters().getEncoded()))) : new PBES2Parameters(new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(bArr3, pBKDF2Config.getIterationCount(), pBKDF2Config.getPRF())), new EncryptionScheme(this.keyEncAlgorithm)));
                    cipher = createCipher2;
                }
            } else {
                throw new OperatorCreationException("unrecognised algorithm");
            }
            return new OutputEncryptor() {
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return algorithmIdentifier;
                }

                public GenericKey getKey() {
                    return JcePKCSPBEOutputEncryptorBuilder.this.isPKCS12(algorithmIdentifier.getAlgorithm()) ? new GenericKey(algorithmIdentifier, JcePKCSPBEOutputEncryptorBuilder.PKCS12PasswordToBytes(cArr)) : new GenericKey(algorithmIdentifier, JcePKCSPBEOutputEncryptorBuilder.PKCS5PasswordToBytes(cArr));
                }

                public OutputStream getOutputStream(OutputStream outputStream) {
                    return new CipherOutputStream(outputStream, cipher);
                }
            };
        } catch (Exception e) {
            throw new OperatorCreationException("unable to create OutputEncryptor: " + e.getMessage(), e);
        }
    }

    public JcePKCSPBEOutputEncryptorBuilder setIterationCount(int i) {
        if (this.pbkdf == null) {
            this.iterationCount = i;
            this.pbkdfBuilder.withIterationCount(i);
            return this;
        }
        throw new IllegalStateException("set iteration count using PBKDFDef");
    }

    public JcePKCSPBEOutputEncryptorBuilder setKeySizeProvider(SecretKeySizeProvider secretKeySizeProvider) {
        this.keySizeProvider = secretKeySizeProvider;
        return this;
    }

    public JcePKCSPBEOutputEncryptorBuilder setPRF(AlgorithmIdentifier algorithmIdentifier) {
        if (this.pbkdf == null) {
            this.pbkdfBuilder.withPRF(algorithmIdentifier);
            return this;
        }
        throw new IllegalStateException("set PRF count using PBKDFDef");
    }

    public JcePKCSPBEOutputEncryptorBuilder setProvider(String str) {
        this.helper = new NamedJcaJceHelper(str);
        return this;
    }

    public JcePKCSPBEOutputEncryptorBuilder setProvider(Provider provider) {
        this.helper = new ProviderJcaJceHelper(provider);
        return this;
    }

    public JcePKCSPBEOutputEncryptorBuilder setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}

package org.bouncycastle.jcajce.provider.asymmetric.p015dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.agreement.DHUnifiedAgreement;
import org.bouncycastle.crypto.agreement.MQVBasicAgreement;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.DHMQVPrivateParameters;
import org.bouncycastle.crypto.params.DHMQVPublicParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.params.DHUPrivateParameters;
import org.bouncycastle.crypto.params.DHUPublicParameters;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import org.bouncycastle.jcajce.spec.DHUParameterSpec;
import org.bouncycastle.jcajce.spec.MQVParameterSpec;
import org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;

/* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi */
public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private DHUParameterSpec dheParameters;

    /* renamed from: g */
    private BigInteger f648g;
    private final BasicAgreement mqvAgreement;
    private MQVParameterSpec mqvParameters;

    /* renamed from: p */
    private BigInteger f649p;
    private byte[] result;
    private final DHUnifiedAgreement unifiedAgreement;

    /* renamed from: x */
    private BigInteger f650x;

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA1CKDF */
    public static class DHUwithSHA1CKDF extends KeyAgreementSpi {
        public DHUwithSHA1CKDF() {
            super("DHUwithSHA1CKDF", new DHUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA1KDF */
    public static class DHUwithSHA1KDF extends KeyAgreementSpi {
        public DHUwithSHA1KDF() {
            super("DHUwithSHA1KDF", new DHUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA224CKDF */
    public static class DHUwithSHA224CKDF extends KeyAgreementSpi {
        public DHUwithSHA224CKDF() {
            super("DHUwithSHA224CKDF", new DHUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA224KDF */
    public static class DHUwithSHA224KDF extends KeyAgreementSpi {
        public DHUwithSHA224KDF() {
            super("DHUwithSHA224KDF", new DHUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA256CKDF */
    public static class DHUwithSHA256CKDF extends KeyAgreementSpi {
        public DHUwithSHA256CKDF() {
            super("DHUwithSHA256CKDF", new DHUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA256KDF */
    public static class DHUwithSHA256KDF extends KeyAgreementSpi {
        public DHUwithSHA256KDF() {
            super("DHUwithSHA256KDF", new DHUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA384CKDF */
    public static class DHUwithSHA384CKDF extends KeyAgreementSpi {
        public DHUwithSHA384CKDF() {
            super("DHUwithSHA384CKDF", new DHUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA384KDF */
    public static class DHUwithSHA384KDF extends KeyAgreementSpi {
        public DHUwithSHA384KDF() {
            super("DHUwithSHA384KDF", new DHUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA512CKDF */
    public static class DHUwithSHA512CKDF extends KeyAgreementSpi {
        public DHUwithSHA512CKDF() {
            super("DHUwithSHA512CKDF", new DHUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHUwithSHA512KDF */
    public static class DHUwithSHA512KDF extends KeyAgreementSpi {
        public DHUwithSHA512KDF() {
            super("DHUwithSHA512KDF", new DHUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithRFC2631KDF */
    public static class DHwithRFC2631KDF extends KeyAgreementSpi {
        public DHwithRFC2631KDF() {
            super("DHwithRFC2631KDF", new DHKEKGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA1CKDF */
    public static class DHwithSHA1CKDF extends KeyAgreementSpi {
        public DHwithSHA1CKDF() {
            super("DHwithSHA1CKDF", new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA1KDF */
    public static class DHwithSHA1KDF extends KeyAgreementSpi {
        public DHwithSHA1KDF() {
            super("DHwithSHA1CKDF", new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA224CKDF */
    public static class DHwithSHA224CKDF extends KeyAgreementSpi {
        public DHwithSHA224CKDF() {
            super("DHwithSHA224CKDF", new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA224KDF */
    public static class DHwithSHA224KDF extends KeyAgreementSpi {
        public DHwithSHA224KDF() {
            super("DHwithSHA224CKDF", new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA256CKDF */
    public static class DHwithSHA256CKDF extends KeyAgreementSpi {
        public DHwithSHA256CKDF() {
            super("DHwithSHA256CKDF", new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA256KDF */
    public static class DHwithSHA256KDF extends KeyAgreementSpi {
        public DHwithSHA256KDF() {
            super("DHwithSHA256CKDF", new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA384CKDF */
    public static class DHwithSHA384CKDF extends KeyAgreementSpi {
        public DHwithSHA384CKDF() {
            super("DHwithSHA384CKDF", new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA384KDF */
    public static class DHwithSHA384KDF extends KeyAgreementSpi {
        public DHwithSHA384KDF() {
            super("DHwithSHA384KDF", new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA512CKDF */
    public static class DHwithSHA512CKDF extends KeyAgreementSpi {
        public DHwithSHA512CKDF() {
            super("DHwithSHA512CKDF", new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithSHA512KDF */
    public static class DHwithSHA512KDF extends KeyAgreementSpi {
        public DHwithSHA512KDF() {
            super("DHwithSHA512KDF", new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA1CKDF */
    public static class MQVwithSHA1CKDF extends KeyAgreementSpi {
        public MQVwithSHA1CKDF() {
            super("MQVwithSHA1CKDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA1KDF */
    public static class MQVwithSHA1KDF extends KeyAgreementSpi {
        public MQVwithSHA1KDF() {
            super("MQVwithSHA1KDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA224CKDF */
    public static class MQVwithSHA224CKDF extends KeyAgreementSpi {
        public MQVwithSHA224CKDF() {
            super("MQVwithSHA224CKDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA224KDF */
    public static class MQVwithSHA224KDF extends KeyAgreementSpi {
        public MQVwithSHA224KDF() {
            super("MQVwithSHA224KDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA256CKDF */
    public static class MQVwithSHA256CKDF extends KeyAgreementSpi {
        public MQVwithSHA256CKDF() {
            super("MQVwithSHA256CKDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA256KDF */
    public static class MQVwithSHA256KDF extends KeyAgreementSpi {
        public MQVwithSHA256KDF() {
            super("MQVwithSHA256KDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA384CKDF */
    public static class MQVwithSHA384CKDF extends KeyAgreementSpi {
        public MQVwithSHA384CKDF() {
            super("MQVwithSHA384CKDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA384KDF */
    public static class MQVwithSHA384KDF extends KeyAgreementSpi {
        public MQVwithSHA384KDF() {
            super("MQVwithSHA384KDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA512CKDF */
    public static class MQVwithSHA512CKDF extends KeyAgreementSpi {
        public MQVwithSHA512CKDF() {
            super("MQVwithSHA512CKDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$MQVwithSHA512KDF */
    public static class MQVwithSHA512KDF extends KeyAgreementSpi {
        public MQVwithSHA512KDF() {
            super("MQVwithSHA512KDF", (BasicAgreement) new MQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    public KeyAgreementSpi() {
        this("Diffie-Hellman", (DerivationFunction) null);
    }

    public KeyAgreementSpi(String str, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.unifiedAgreement = null;
        this.mqvAgreement = basicAgreement;
    }

    public KeyAgreementSpi(String str, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.unifiedAgreement = null;
        this.mqvAgreement = null;
    }

    public KeyAgreementSpi(String str, DHUnifiedAgreement dHUnifiedAgreement, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.unifiedAgreement = dHUnifiedAgreement;
        this.mqvAgreement = null;
    }

    private DHPrivateKeyParameters generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof DHPrivateKey)) {
            throw new InvalidKeyException("private key not a DHPrivateKey");
        } else if (privateKey instanceof BCDHPrivateKey) {
            return ((BCDHPrivateKey) privateKey).engineGetKeyParameters();
        } else {
            DHPrivateKey dHPrivateKey = (DHPrivateKey) privateKey;
            DHParameterSpec params = dHPrivateKey.getParams();
            return new DHPrivateKeyParameters(dHPrivateKey.getX(), new DHParameters(params.getP(), params.getG(), (BigInteger) null, params.getL()));
        }
    }

    private DHPublicKeyParameters generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof DHPublicKey)) {
            throw new InvalidKeyException("public key not a DHPublicKey");
        } else if (publicKey instanceof BCDHPublicKey) {
            return ((BCDHPublicKey) publicKey).engineGetKeyParameters();
        } else {
            DHPublicKey dHPublicKey = (DHPublicKey) publicKey;
            DHParameterSpec params = dHPublicKey.getParams();
            return params instanceof DHDomainParameterSpec ? new DHPublicKeyParameters(dHPublicKey.getY(), ((DHDomainParameterSpec) params).getDomainParameters()) : new DHPublicKeyParameters(dHPublicKey.getY(), new DHParameters(params.getP(), params.getG(), (BigInteger) null, params.getL()));
        }
    }

    /* access modifiers changed from: protected */
    public byte[] bigIntToBytes(BigInteger bigInteger) {
        int bitLength = (this.f649p.bitLength() + 7) / 8;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == bitLength) {
            return byteArray;
        }
        if (byteArray[0] == 0 && byteArray.length == bitLength + 1) {
            int length = byteArray.length - 1;
            byte[] bArr = new byte[length];
            System.arraycopy(byteArray, 1, bArr, 0, length);
            return bArr;
        }
        byte[] bArr2 = new byte[bitLength];
        System.arraycopy(byteArray, 0, bArr2, bitLength - byteArray.length, byteArray.length);
        return bArr2;
    }

    /* access modifiers changed from: protected */
    public byte[] calcSecret() {
        return this.result;
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean z) throws InvalidKeyException, IllegalStateException {
        if (this.f650x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        } else if (key instanceof DHPublicKey) {
            DHPublicKey dHPublicKey = (DHPublicKey) key;
            if (!dHPublicKey.getParams().getG().equals(this.f648g) || !dHPublicKey.getParams().getP().equals(this.f649p)) {
                throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
            }
            BigInteger y = dHPublicKey.getY();
            if (y != null && y.compareTo(TWO) >= 0) {
                BigInteger bigInteger = this.f649p;
                BigInteger bigInteger2 = ONE;
                if (y.compareTo(bigInteger.subtract(bigInteger2)) < 0) {
                    if (this.unifiedAgreement != null) {
                        if (z) {
                            this.result = this.unifiedAgreement.calculateAgreement(new DHUPublicParameters(generatePublicKeyParameter((PublicKey) key), generatePublicKeyParameter(this.dheParameters.getOtherPartyEphemeralKey())));
                            return null;
                        }
                        throw new IllegalStateException("unified Diffie-Hellman can use only two key pairs");
                    } else if (this.mqvAgreement == null) {
                        BigInteger modPow = y.modPow(this.f650x, this.f649p);
                        if (modPow.compareTo(bigInteger2) != 0) {
                            this.result = bigIntToBytes(modPow);
                            if (z) {
                                return null;
                            }
                            return new BCDHPublicKey(modPow, dHPublicKey.getParams());
                        }
                        throw new InvalidKeyException("Shared key can't be 1");
                    } else if (z) {
                        this.result = bigIntToBytes(this.mqvAgreement.calculateAgreement(new DHMQVPublicParameters(generatePublicKeyParameter((PublicKey) key), generatePublicKeyParameter(this.mqvParameters.getOtherPartyEphemeralKey()))));
                        return null;
                    } else {
                        throw new IllegalStateException("MQV Diffie-Hellman can use only two key pairs");
                    }
                }
            }
            throw new InvalidKeyException("Invalid DH PublicKey");
        } else {
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        if (this.f650x != null) {
            return super.engineGenerateSecret(bArr, i);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String str) throws NoSuchAlgorithmException {
        if (this.f650x != null) {
            return str.equals("TlsPremasterSecret") ? new SecretKeySpec(trimZeroes(this.result), str) : super.engineGenerateSecret(str);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.f650x != null) {
            return super.engineGenerateSecret();
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (key instanceof DHPrivateKey) {
            DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
            this.f649p = dHPrivateKey.getParams().getP();
            this.f648g = dHPrivateKey.getParams().getG();
            BigInteger x = dHPrivateKey.getX();
            this.f650x = x;
            this.result = bigIntToBytes(x);
            return;
        }
        throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        DHMQVPrivateParameters dHMQVPrivateParameters;
        BasicAgreement basicAgreement;
        DHUPrivateParameters dHUPrivateParameters;
        DHUnifiedAgreement dHUnifiedAgreement;
        if (key instanceof DHPrivateKey) {
            DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
            if (algorithmParameterSpec == null) {
                this.f649p = dHPrivateKey.getParams().getP();
                this.f648g = dHPrivateKey.getParams().getG();
            } else if (algorithmParameterSpec instanceof DHParameterSpec) {
                DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
                this.f649p = dHParameterSpec.getP();
                this.f648g = dHParameterSpec.getG();
                this.dheParameters = null;
                this.ukmParameters = null;
            } else if (algorithmParameterSpec instanceof DHUParameterSpec) {
                if (this.unifiedAgreement != null) {
                    this.f649p = dHPrivateKey.getParams().getP();
                    this.f648g = dHPrivateKey.getParams().getG();
                    DHUParameterSpec dHUParameterSpec = (DHUParameterSpec) algorithmParameterSpec;
                    this.dheParameters = dHUParameterSpec;
                    this.ukmParameters = dHUParameterSpec.getUserKeyingMaterial();
                    if (this.dheParameters.getEphemeralPublicKey() != null) {
                        dHUnifiedAgreement = this.unifiedAgreement;
                        dHUPrivateParameters = new DHUPrivateParameters(generatePrivateKeyParameter(dHPrivateKey), generatePrivateKeyParameter(this.dheParameters.getEphemeralPrivateKey()), generatePublicKeyParameter(this.dheParameters.getEphemeralPublicKey()));
                    } else {
                        dHUnifiedAgreement = this.unifiedAgreement;
                        dHUPrivateParameters = new DHUPrivateParameters(generatePrivateKeyParameter(dHPrivateKey), generatePrivateKeyParameter(this.dheParameters.getEphemeralPrivateKey()));
                    }
                    dHUnifiedAgreement.init(dHUPrivateParameters);
                } else {
                    throw new InvalidAlgorithmParameterException("agreement algorithm not DHU based");
                }
            } else if (algorithmParameterSpec instanceof MQVParameterSpec) {
                if (this.mqvAgreement != null) {
                    this.f649p = dHPrivateKey.getParams().getP();
                    this.f648g = dHPrivateKey.getParams().getG();
                    MQVParameterSpec mQVParameterSpec = (MQVParameterSpec) algorithmParameterSpec;
                    this.mqvParameters = mQVParameterSpec;
                    this.ukmParameters = mQVParameterSpec.getUserKeyingMaterial();
                    if (this.mqvParameters.getEphemeralPublicKey() != null) {
                        basicAgreement = this.mqvAgreement;
                        dHMQVPrivateParameters = new DHMQVPrivateParameters(generatePrivateKeyParameter(dHPrivateKey), generatePrivateKeyParameter(this.mqvParameters.getEphemeralPrivateKey()), generatePublicKeyParameter(this.mqvParameters.getEphemeralPublicKey()));
                    } else {
                        basicAgreement = this.mqvAgreement;
                        dHMQVPrivateParameters = new DHMQVPrivateParameters(generatePrivateKeyParameter(dHPrivateKey), generatePrivateKeyParameter(this.mqvParameters.getEphemeralPrivateKey()));
                    }
                    basicAgreement.init(dHMQVPrivateParameters);
                } else {
                    throw new InvalidAlgorithmParameterException("agreement algorithm not MQV based");
                }
            } else if (!(algorithmParameterSpec instanceof UserKeyingMaterialSpec)) {
                throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            } else if (this.kdf != null) {
                this.f649p = dHPrivateKey.getParams().getP();
                this.f648g = dHPrivateKey.getParams().getG();
                this.dheParameters = null;
                this.ukmParameters = ((UserKeyingMaterialSpec) algorithmParameterSpec).getUserKeyingMaterial();
            } else {
                throw new InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
            }
            BigInteger x = dHPrivateKey.getX();
            this.f650x = x;
            this.result = bigIntToBytes(x);
            return;
        }
        throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
    }
}

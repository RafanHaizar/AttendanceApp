package org.bouncycastle.jcajce.provider.asymmetric.edec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.generators.Ed448KeyPairGenerator;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.generators.X448KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed448KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X448KeyGenerationParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jcajce.spec.XDHParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;

public class KeyPairGeneratorSpi extends java.security.KeyPairGeneratorSpi {
    private static final int Ed25519 = 1;
    private static final int Ed448 = 0;
    private static final int EdDSA = -1;
    private static final int X25519 = 3;
    private static final int X448 = 2;
    private static final int XDH = -2;
    private int algorithm;
    private AsymmetricCipherKeyPairGenerator generator;
    private boolean initialised;
    private SecureRandom secureRandom;

    public static final class Ed25519 extends KeyPairGeneratorSpi {
        public Ed25519() {
            super(1, new Ed25519KeyPairGenerator());
        }
    }

    public static final class Ed448 extends KeyPairGeneratorSpi {
        public Ed448() {
            super(0, new Ed448KeyPairGenerator());
        }
    }

    public static final class EdDSA extends KeyPairGeneratorSpi {
        public EdDSA() {
            super(-1, (AsymmetricCipherKeyPairGenerator) null);
        }
    }

    public static final class X25519 extends KeyPairGeneratorSpi {
        public X25519() {
            super(3, new X25519KeyPairGenerator());
        }
    }

    public static final class X448 extends KeyPairGeneratorSpi {
        public X448() {
            super(2, new X448KeyPairGenerator());
        }
    }

    public static final class XDH extends KeyPairGeneratorSpi {
        public XDH() {
            super(-2, (AsymmetricCipherKeyPairGenerator) null);
        }
    }

    KeyPairGeneratorSpi(int i, AsymmetricCipherKeyPairGenerator asymmetricCipherKeyPairGenerator) {
        this.algorithm = i;
        this.generator = asymmetricCipherKeyPairGenerator;
    }

    private void algorithmCheck(int i) throws InvalidAlgorithmParameterException {
        int i2 = this.algorithm;
        if (i2 == i) {
            return;
        }
        if (i2 == 1 || i2 == 0) {
            throw new InvalidAlgorithmParameterException("parameterSpec for wrong curve type");
        } else if (i2 == -1 && i != 1 && i != 0) {
            throw new InvalidAlgorithmParameterException("parameterSpec for wrong curve type");
        } else if (i2 == 3 || i2 == 2) {
            throw new InvalidAlgorithmParameterException("parameterSpec for wrong curve type");
        } else if (i2 != -2 || i == 3 || i == 2) {
            this.algorithm = i;
        } else {
            throw new InvalidAlgorithmParameterException("parameterSpec for wrong curve type");
        }
    }

    private void initializeGenerator(String str) throws InvalidAlgorithmParameterException {
        int i;
        AsymmetricCipherKeyPairGenerator asymmetricCipherKeyPairGenerator;
        if (str.equalsIgnoreCase(EdDSAParameterSpec.Ed448) || str.equals(EdECObjectIdentifiers.id_Ed448.getId())) {
            i = 0;
            algorithmCheck(0);
            asymmetricCipherKeyPairGenerator = new Ed448KeyPairGenerator();
        } else if (str.equalsIgnoreCase(EdDSAParameterSpec.Ed25519) || str.equals(EdECObjectIdentifiers.id_Ed25519.getId())) {
            i = 1;
            algorithmCheck(1);
            asymmetricCipherKeyPairGenerator = new Ed25519KeyPairGenerator();
        } else if (str.equalsIgnoreCase(XDHParameterSpec.X448) || str.equals(EdECObjectIdentifiers.id_X448.getId())) {
            i = 2;
            algorithmCheck(2);
            asymmetricCipherKeyPairGenerator = new X448KeyPairGenerator();
        } else if (str.equalsIgnoreCase(XDHParameterSpec.X25519) || str.equals(EdECObjectIdentifiers.id_X25519.getId())) {
            i = 3;
            algorithmCheck(3);
            asymmetricCipherKeyPairGenerator = new X25519KeyPairGenerator();
        } else {
            return;
        }
        this.generator = asymmetricCipherKeyPairGenerator;
        setupGenerator(i);
    }

    private void setupGenerator(int i) {
        AsymmetricCipherKeyPairGenerator asymmetricCipherKeyPairGenerator;
        KeyGenerationParameters keyGenerationParameters;
        this.initialised = true;
        if (this.secureRandom == null) {
            this.secureRandom = new SecureRandom();
        }
        switch (i) {
            case -2:
            case 3:
                asymmetricCipherKeyPairGenerator = this.generator;
                keyGenerationParameters = new X25519KeyGenerationParameters(this.secureRandom);
                break;
            case -1:
            case 1:
                asymmetricCipherKeyPairGenerator = this.generator;
                keyGenerationParameters = new Ed25519KeyGenerationParameters(this.secureRandom);
                break;
            case 0:
                asymmetricCipherKeyPairGenerator = this.generator;
                keyGenerationParameters = new Ed448KeyGenerationParameters(this.secureRandom);
                break;
            case 2:
                asymmetricCipherKeyPairGenerator = this.generator;
                keyGenerationParameters = new X448KeyGenerationParameters(this.secureRandom);
                break;
            default:
                return;
        }
        asymmetricCipherKeyPairGenerator.init(keyGenerationParameters);
    }

    public KeyPair generateKeyPair() {
        if (this.generator != null) {
            if (!this.initialised) {
                setupGenerator(this.algorithm);
            }
            AsymmetricCipherKeyPair generateKeyPair = this.generator.generateKeyPair();
            switch (this.algorithm) {
                case 0:
                    return new KeyPair(new BCEdDSAPublicKey(generateKeyPair.getPublic()), new BCEdDSAPrivateKey(generateKeyPair.getPrivate()));
                case 1:
                    return new KeyPair(new BCEdDSAPublicKey(generateKeyPair.getPublic()), new BCEdDSAPrivateKey(generateKeyPair.getPrivate()));
                case 2:
                    return new KeyPair(new BCXDHPublicKey(generateKeyPair.getPublic()), new BCXDHPrivateKey(generateKeyPair.getPrivate()));
                case 3:
                    return new KeyPair(new BCXDHPublicKey(generateKeyPair.getPublic()), new BCXDHPrivateKey(generateKeyPair.getPrivate()));
                default:
                    throw new IllegalStateException("generator not correctly initialized");
            }
        } else {
            throw new IllegalStateException("generator not correctly initialized");
        }
    }

    public void initialize(int i, SecureRandom secureRandom2) {
        int i2;
        this.secureRandom = secureRandom2;
        switch (i) {
            case 255:
            case 256:
                switch (this.algorithm) {
                    case -2:
                    case 3:
                        i2 = 3;
                        break;
                    case -1:
                    case 1:
                        i2 = 1;
                        break;
                    default:
                        throw new InvalidParameterException("key size not configurable");
                }
            case 448:
                switch (this.algorithm) {
                    case -2:
                    case 2:
                        i2 = 2;
                        break;
                    case -1:
                    case 0:
                        i2 = 0;
                        break;
                    default:
                        throw new InvalidParameterException("key size not configurable");
                }
            default:
                throw new InvalidParameterException("unknown key size");
        }
        setupGenerator(i2);
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom2) throws InvalidAlgorithmParameterException {
        String curveName;
        this.secureRandom = secureRandom2;
        if (algorithmParameterSpec instanceof ECGenParameterSpec) {
            curveName = ((ECGenParameterSpec) algorithmParameterSpec).getName();
        } else if (algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec) {
            curveName = ((ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName();
        } else if (algorithmParameterSpec instanceof EdDSAParameterSpec) {
            curveName = ((EdDSAParameterSpec) algorithmParameterSpec).getCurveName();
        } else if (algorithmParameterSpec instanceof XDHParameterSpec) {
            curveName = ((XDHParameterSpec) algorithmParameterSpec).getCurveName();
        } else {
            String nameFrom = ECUtil.getNameFrom(algorithmParameterSpec);
            if (nameFrom != null) {
                initializeGenerator(nameFrom);
                return;
            }
            throw new InvalidAlgorithmParameterException("invalid parameterSpec: " + algorithmParameterSpec);
        }
        initializeGenerator(curveName);
    }
}

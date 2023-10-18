package org.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Hashtable;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.DSAKeyPairGenerator;
import org.bouncycastle.crypto.generators.DSAParametersGenerator;
import org.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Properties;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    private static Object lock = new Object();
    private static Hashtable params = new Hashtable();
    DSAKeyPairGenerator engine = new DSAKeyPairGenerator();
    boolean initialised = false;
    DSAKeyGenerationParameters param;
    SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    int strength = 2048;

    public KeyPairGeneratorSpi() {
        super("DSA");
    }

    public KeyPair generateKeyPair() {
        DSAParametersGenerator dSAParametersGenerator;
        int i;
        SecureRandom secureRandom;
        if (!this.initialised) {
            Integer valueOf = Integers.valueOf(this.strength);
            if (params.containsKey(valueOf)) {
                this.param = (DSAKeyGenerationParameters) params.get(valueOf);
            } else {
                synchronized (lock) {
                    if (params.containsKey(valueOf)) {
                        this.param = (DSAKeyGenerationParameters) params.get(valueOf);
                    } else {
                        int defaultCertainty = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
                        int i2 = this.strength;
                        if (i2 == 1024) {
                            dSAParametersGenerator = new DSAParametersGenerator();
                            if (Properties.isOverrideSet("org.bouncycastle.dsa.FIPS186-2for1024bits")) {
                                i = this.strength;
                                secureRandom = this.random;
                            } else {
                                dSAParametersGenerator.init(new DSAParameterGenerationParameters(1024, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, defaultCertainty, this.random));
                                DSAKeyGenerationParameters dSAKeyGenerationParameters = new DSAKeyGenerationParameters(this.random, dSAParametersGenerator.generateParameters());
                                this.param = dSAKeyGenerationParameters;
                                params.put(valueOf, dSAKeyGenerationParameters);
                            }
                        } else if (i2 > 1024) {
                            DSAParameterGenerationParameters dSAParameterGenerationParameters = new DSAParameterGenerationParameters(i2, 256, defaultCertainty, this.random);
                            dSAParametersGenerator = new DSAParametersGenerator(new SHA256Digest());
                            dSAParametersGenerator.init(dSAParameterGenerationParameters);
                            DSAKeyGenerationParameters dSAKeyGenerationParameters2 = new DSAKeyGenerationParameters(this.random, dSAParametersGenerator.generateParameters());
                            this.param = dSAKeyGenerationParameters2;
                            params.put(valueOf, dSAKeyGenerationParameters2);
                        } else {
                            dSAParametersGenerator = new DSAParametersGenerator();
                            i = this.strength;
                            secureRandom = this.random;
                        }
                        dSAParametersGenerator.init(i, defaultCertainty, secureRandom);
                        DSAKeyGenerationParameters dSAKeyGenerationParameters22 = new DSAKeyGenerationParameters(this.random, dSAParametersGenerator.generateParameters());
                        this.param = dSAKeyGenerationParameters22;
                        params.put(valueOf, dSAKeyGenerationParameters22);
                    }
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }
        AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new KeyPair(new BCDSAPublicKey((DSAPublicKeyParameters) generateKeyPair.getPublic()), new BCDSAPrivateKey((DSAPrivateKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        boolean z;
        if (i < 512 || i > 4096 || ((i < 1024 && i % 64 != 0) || (i >= 1024 && i % 1024 != 0))) {
            throw new InvalidParameterException("strength must be from 512 - 4096 and a multiple of 1024 above 1024");
        }
        DSAParameterSpec dSADefaultParameters = BouncyCastleProvider.CONFIGURATION.getDSADefaultParameters(i);
        if (dSADefaultParameters != null) {
            DSAKeyGenerationParameters dSAKeyGenerationParameters = new DSAKeyGenerationParameters(secureRandom, new DSAParameters(dSADefaultParameters.getP(), dSADefaultParameters.getQ(), dSADefaultParameters.getG()));
            this.param = dSAKeyGenerationParameters;
            this.engine.init(dSAKeyGenerationParameters);
            z = true;
        } else {
            this.strength = i;
            this.random = secureRandom;
            z = false;
        }
        this.initialised = z;
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof DSAParameterSpec) {
            DSAParameterSpec dSAParameterSpec = (DSAParameterSpec) algorithmParameterSpec;
            DSAKeyGenerationParameters dSAKeyGenerationParameters = new DSAKeyGenerationParameters(secureRandom, new DSAParameters(dSAParameterSpec.getP(), dSAParameterSpec.getQ(), dSAParameterSpec.getG()));
            this.param = dSAKeyGenerationParameters;
            this.engine.init(dSAKeyGenerationParameters);
            this.initialised = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
    }
}

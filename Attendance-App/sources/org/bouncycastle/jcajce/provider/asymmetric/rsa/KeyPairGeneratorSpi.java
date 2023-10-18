package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    static final BigInteger defaultPublicExponent = BigInteger.valueOf(65537);
    RSAKeyPairGenerator engine;
    RSAKeyGenerationParameters param;

    public KeyPairGeneratorSpi() {
        super("RSA");
        this.engine = new RSAKeyPairGenerator();
        RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(defaultPublicExponent, CryptoServicesRegistrar.getSecureRandom(), 2048, PrimeCertaintyCalculator.getDefaultCertainty(2048));
        this.param = rSAKeyGenerationParameters;
        this.engine.init(rSAKeyGenerationParameters);
    }

    public KeyPairGeneratorSpi(String str) {
        super(str);
    }

    public KeyPair generateKeyPair() {
        AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new KeyPair(new BCRSAPublicKey((RSAKeyParameters) generateKeyPair.getPublic()), new BCRSAPrivateCrtKey((RSAPrivateCrtKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(defaultPublicExponent, secureRandom, i, PrimeCertaintyCalculator.getDefaultCertainty(i));
        this.param = rSAKeyGenerationParameters;
        this.engine.init(rSAKeyGenerationParameters);
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof RSAKeyGenParameterSpec) {
            RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec) algorithmParameterSpec;
            RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(rSAKeyGenParameterSpec.getPublicExponent(), secureRandom, rSAKeyGenParameterSpec.getKeysize(), PrimeCertaintyCalculator.getDefaultCertainty(2048));
            this.param = rSAKeyGenerationParameters;
            this.engine.init(rSAKeyGenerationParameters);
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
    }
}

package org.bouncycastle.pqc.jcajce.provider.qtesla;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAKeyPairGenerator;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.qtesla.QTESLASecurityCategory;
import org.bouncycastle.pqc.jcajce.spec.QTESLAParameterSpec;
import org.bouncycastle.util.Integers;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    private static final Map catLookup;
    private QTESLAKeyPairGenerator engine = new QTESLAKeyPairGenerator();
    private boolean initialised = false;
    private QTESLAKeyGenerationParameters param;
    private SecureRandom random = CryptoServicesRegistrar.getSecureRandom();

    static {
        HashMap hashMap = new HashMap();
        catLookup = hashMap;
        hashMap.put(QTESLASecurityCategory.getName(5), Integers.valueOf(5));
        hashMap.put(QTESLASecurityCategory.getName(6), Integers.valueOf(6));
    }

    public KeyPairGeneratorSpi() {
        super("qTESLA");
    }

    public KeyPair generateKeyPair() {
        if (!this.initialised) {
            QTESLAKeyGenerationParameters qTESLAKeyGenerationParameters = new QTESLAKeyGenerationParameters(6, this.random);
            this.param = qTESLAKeyGenerationParameters;
            this.engine.init(qTESLAKeyGenerationParameters);
            this.initialised = true;
        }
        AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new KeyPair(new BCqTESLAPublicKey((QTESLAPublicKeyParameters) generateKeyPair.getPublic()), new BCqTESLAPrivateKey((QTESLAPrivateKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof QTESLAParameterSpec) {
            QTESLAKeyGenerationParameters qTESLAKeyGenerationParameters = new QTESLAKeyGenerationParameters(((Integer) catLookup.get(((QTESLAParameterSpec) algorithmParameterSpec).getSecurityCategory())).intValue(), secureRandom);
            this.param = qTESLAKeyGenerationParameters;
            this.engine.init(qTESLAKeyGenerationParameters);
            this.initialised = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a QTESLAParameterSpec");
    }
}

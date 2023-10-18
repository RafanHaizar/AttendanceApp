package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Salsa20 {

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Salsa20 IV";
        }
    }

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new Salsa20Engine(), 8);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Salsa20", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = Salsa20.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            configurableProvider.addAlgorithm("Cipher.SALSA20", sb.append(str).append("$Base").toString());
            configurableProvider.addAlgorithm("KeyGenerator.SALSA20", str + "$KeyGen");
            configurableProvider.addAlgorithm("AlgorithmParameters.SALSA20", str + "$AlgParams");
        }
    }

    private Salsa20() {
    }
}

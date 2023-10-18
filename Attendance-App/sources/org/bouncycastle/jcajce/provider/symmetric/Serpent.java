package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.asn1.gnu.GNUObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.engines.TnepresEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Serpent {

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Serpent IV";
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new SerpentEngine()), 128);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128)), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new SerpentEngine();
                }
            });
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Serpent", 192, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String PREFIX = Serpent.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            configurableProvider.addAlgorithm("Cipher.Serpent", sb.append(str).append("$ECB").toString());
            configurableProvider.addAlgorithm("KeyGenerator.Serpent", str + "$KeyGen");
            configurableProvider.addAlgorithm("AlgorithmParameters.Serpent", str + "$AlgParams");
            configurableProvider.addAlgorithm("Cipher.Tnepres", str + "$TECB");
            configurableProvider.addAlgorithm("KeyGenerator.Tnepres", str + "$TKeyGen");
            configurableProvider.addAlgorithm("AlgorithmParameters.Tnepres", str + "$TAlgParams");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_ECB, str + "$ECB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_ECB, str + "$ECB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_ECB, str + "$ECB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CBC, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CBC, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CBC, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CFB, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CFB, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CFB, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_OFB, str + "$OFB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_OFB, str + "$OFB");
            configurableProvider.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_OFB, str + "$OFB");
            addGMacAlgorithm(configurableProvider, "SERPENT", str + "$SerpentGMAC", str + "$KeyGen");
            addGMacAlgorithm(configurableProvider, "TNEPRES", str + "$TSerpentGMAC", str + "$TKeyGen");
            addPoly1305Algorithm(configurableProvider, "SERPENT", str + "$Poly1305", str + "$Poly1305KeyGen");
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new SerpentEngine(), 128)), 128);
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new SerpentEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-Serpent", 256, new Poly1305KeyGenerator());
        }
    }

    public static class SerpentGMAC extends BaseMac {
        public SerpentGMAC() {
            super(new GMac(new GCMBlockCipher(new SerpentEngine())));
        }
    }

    public static class TAlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Tnepres IV";
        }
    }

    public static class TECB extends BaseBlockCipher {
        public TECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new TnepresEngine();
                }
            });
        }
    }

    public static class TKeyGen extends BaseKeyGenerator {
        public TKeyGen() {
            super("Tnepres", 192, new CipherKeyGenerator());
        }
    }

    public static class TSerpentGMAC extends BaseMac {
        public TSerpentGMAC() {
            super(new GMac(new GCMBlockCipher(new TnepresEngine())));
        }
    }

    private Serpent() {
    }
}

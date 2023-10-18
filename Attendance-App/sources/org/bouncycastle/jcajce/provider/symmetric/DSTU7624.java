package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.p007ua.UAObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.engines.DSTU7624Engine;
import org.bouncycastle.crypto.engines.DSTU7624WrapEngine;
import org.bouncycastle.crypto.macs.KGMac;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.KCCMBlockCipher;
import org.bouncycastle.crypto.modes.KCTRBlockCipher;
import org.bouncycastle.crypto.modes.KGCMBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public class DSTU7624 {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        private final int ivLength;

        public AlgParamGen(int i) {
            this.ivLength = i / 8;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[this.ivLength];
            if (this.random == null) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance("DSTU7624");
                createParametersInstance.init(new IvParameterSpec(bArr));
                return createParametersInstance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSTU7624 parameter generation.");
        }
    }

    public static class AlgParamGen128 extends AlgParamGen {
        AlgParamGen128() {
            super(128);
        }
    }

    public static class AlgParamGen256 extends AlgParamGen {
        AlgParamGen256() {
            super(256);
        }
    }

    public static class AlgParamGen512 extends AlgParamGen {
        AlgParamGen512() {
            super(512);
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "DSTU7624 IV";
        }
    }

    public static class CBC128 extends BaseBlockCipher {
        public CBC128() {
            super((BlockCipher) new CBCBlockCipher(new DSTU7624Engine(128)), 128);
        }
    }

    public static class CBC256 extends BaseBlockCipher {
        public CBC256() {
            super((BlockCipher) new CBCBlockCipher(new DSTU7624Engine(256)), 256);
        }
    }

    public static class CBC512 extends BaseBlockCipher {
        public CBC512() {
            super((BlockCipher) new CBCBlockCipher(new DSTU7624Engine(512)), 512);
        }
    }

    public static class CCM128 extends BaseBlockCipher {
        public CCM128() {
            super((AEADBlockCipher) new KCCMBlockCipher(new DSTU7624Engine(128)));
        }
    }

    public static class CCM256 extends BaseBlockCipher {
        public CCM256() {
            super((AEADBlockCipher) new KCCMBlockCipher(new DSTU7624Engine(256)));
        }
    }

    public static class CCM512 extends BaseBlockCipher {
        public CCM512() {
            super((AEADBlockCipher) new KCCMBlockCipher(new DSTU7624Engine(512)));
        }
    }

    public static class CFB128 extends BaseBlockCipher {
        public CFB128() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new DSTU7624Engine(128), 128)), 128);
        }
    }

    public static class CFB256 extends BaseBlockCipher {
        public CFB256() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new DSTU7624Engine(256), 256)), 256);
        }
    }

    public static class CFB512 extends BaseBlockCipher {
        public CFB512() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new DSTU7624Engine(512), 512)), 512);
        }
    }

    public static class CTR128 extends BaseBlockCipher {
        public CTR128() {
            super(new BufferedBlockCipher(new KCTRBlockCipher(new DSTU7624Engine(128))), 128);
        }
    }

    public static class CTR256 extends BaseBlockCipher {
        public CTR256() {
            super(new BufferedBlockCipher(new KCTRBlockCipher(new DSTU7624Engine(256))), 256);
        }
    }

    public static class CTR512 extends BaseBlockCipher {
        public CTR512() {
            super(new BufferedBlockCipher(new KCTRBlockCipher(new DSTU7624Engine(512))), 512);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new DSTU7624Engine(128);
                }
            });
        }
    }

    public static class ECB128 extends BaseBlockCipher {
        public ECB128() {
            super((BlockCipher) new DSTU7624Engine(128));
        }
    }

    public static class ECB256 extends BaseBlockCipher {
        public ECB256() {
            super((BlockCipher) new DSTU7624Engine(256));
        }
    }

    public static class ECB512 extends BaseBlockCipher {
        public ECB512() {
            super((BlockCipher) new DSTU7624Engine(512));
        }
    }

    public static class ECB_128 extends BaseBlockCipher {
        public ECB_128() {
            super((BlockCipher) new DSTU7624Engine(128));
        }
    }

    public static class ECB_256 extends BaseBlockCipher {
        public ECB_256() {
            super((BlockCipher) new DSTU7624Engine(256));
        }
    }

    public static class ECB_512 extends BaseBlockCipher {
        public ECB_512() {
            super((BlockCipher) new DSTU7624Engine(512));
        }
    }

    public static class GCM128 extends BaseBlockCipher {
        public GCM128() {
            super((AEADBlockCipher) new KGCMBlockCipher(new DSTU7624Engine(128)));
        }
    }

    public static class GCM256 extends BaseBlockCipher {
        public GCM256() {
            super((AEADBlockCipher) new KGCMBlockCipher(new DSTU7624Engine(256)));
        }
    }

    public static class GCM512 extends BaseBlockCipher {
        public GCM512() {
            super((AEADBlockCipher) new KGCMBlockCipher(new DSTU7624Engine(512)));
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new KGMac(new KGCMBlockCipher(new DSTU7624Engine(128)), 128));
        }
    }

    public static class GMAC128 extends BaseMac {
        public GMAC128() {
            super(new KGMac(new KGCMBlockCipher(new DSTU7624Engine(128)), 128));
        }
    }

    public static class GMAC256 extends BaseMac {
        public GMAC256() {
            super(new KGMac(new KGCMBlockCipher(new DSTU7624Engine(256)), 256));
        }
    }

    public static class GMAC512 extends BaseMac {
        public GMAC512() {
            super(new KGMac(new KGCMBlockCipher(new DSTU7624Engine(512)), 512));
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int i) {
            super("DSTU7624", i, new CipherKeyGenerator());
        }
    }

    public static class KeyGen128 extends KeyGen {
        public KeyGen128() {
            super(128);
        }
    }

    public static class KeyGen256 extends KeyGen {
        public KeyGen256() {
            super(256);
        }
    }

    public static class KeyGen512 extends KeyGen {
        public KeyGen512() {
            super(512);
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String PREFIX = DSTU7624.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            configurableProvider.addAlgorithm("AlgorithmParameters.DSTU7624", sb.append(str).append("$AlgParams128").toString());
            configurableProvider.addAlgorithm("AlgorithmParameters", UAObjectIdentifiers.dstu7624cbc_128, str + "$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters", UAObjectIdentifiers.dstu7624cbc_256, str + "$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters", UAObjectIdentifiers.dstu7624cbc_512, str + "$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.DSTU7624", str + "$AlgParamGen128");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator", UAObjectIdentifiers.dstu7624cbc_128, str + "$AlgParamGen128");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator", UAObjectIdentifiers.dstu7624cbc_256, str + "$AlgParamGen256");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator", UAObjectIdentifiers.dstu7624cbc_512, str + "$AlgParamGen512");
            configurableProvider.addAlgorithm("Cipher.DSTU7624", str + "$ECB_128");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-128", str + "$ECB_128");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-256", str + "$ECB_256");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-512", str + "$ECB_512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ecb_128, str + "$ECB128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ecb_256, str + "$ECB256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ecb_512, str + "$ECB512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cbc_128, str + "$CBC128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cbc_256, str + "$CBC256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cbc_512, str + "$CBC512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ofb_128, str + "$OFB128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ofb_256, str + "$OFB256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ofb_512, str + "$OFB512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cfb_128, str + "$CFB128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cfb_256, str + "$CFB256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624cfb_512, str + "$CFB512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ctr_128, str + "$CTR128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ctr_256, str + "$CTR256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ctr_512, str + "$CTR512");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ccm_128, str + "$CCM128");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ccm_256, str + "$CCM256");
            configurableProvider.addAlgorithm("Cipher", UAObjectIdentifiers.dstu7624ccm_512, str + "$CCM512");
            configurableProvider.addAlgorithm("Cipher.DSTU7624KW", str + "$Wrap");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.DSTU7624WRAP", "DSTU7624KW");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-128KW", str + "$Wrap128");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher." + UAObjectIdentifiers.dstu7624kw_128.getId(), "DSTU7624-128KW");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.DSTU7624-128WRAP", "DSTU7624-128KW");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-256KW", str + "$Wrap256");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher." + UAObjectIdentifiers.dstu7624kw_256.getId(), "DSTU7624-256KW");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.DSTU7624-256WRAP", "DSTU7624-256KW");
            configurableProvider.addAlgorithm("Cipher.DSTU7624-512KW", str + "$Wrap512");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher." + UAObjectIdentifiers.dstu7624kw_512.getId(), "DSTU7624-512KW");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.DSTU7624-512WRAP", "DSTU7624-512KW");
            configurableProvider.addAlgorithm("Mac.DSTU7624GMAC", str + "$GMAC");
            configurableProvider.addAlgorithm("Mac.DSTU7624-128GMAC", str + "$GMAC128");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + UAObjectIdentifiers.dstu7624gmac_128.getId(), "DSTU7624-128GMAC");
            configurableProvider.addAlgorithm("Mac.DSTU7624-256GMAC", str + "$GMAC256");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + UAObjectIdentifiers.dstu7624gmac_256.getId(), "DSTU7624-256GMAC");
            configurableProvider.addAlgorithm("Mac.DSTU7624-512GMAC", str + "$GMAC512");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + UAObjectIdentifiers.dstu7624gmac_512.getId(), "DSTU7624-512GMAC");
            configurableProvider.addAlgorithm("KeyGenerator.DSTU7624", str + "$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624kw_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624kw_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624kw_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ecb_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ecb_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ecb_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cbc_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cbc_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cbc_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ofb_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ofb_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ofb_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cfb_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cfb_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624cfb_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ctr_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ctr_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ctr_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ccm_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ccm_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624ccm_512, str + "$KeyGen512");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624gmac_128, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624gmac_256, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", UAObjectIdentifiers.dstu7624gmac_512, str + "$KeyGen512");
        }
    }

    public static class OFB128 extends BaseBlockCipher {
        public OFB128() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new DSTU7624Engine(128), 128)), 128);
        }
    }

    public static class OFB256 extends BaseBlockCipher {
        public OFB256() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new DSTU7624Engine(256), 256)), 256);
        }
    }

    public static class OFB512 extends BaseBlockCipher {
        public OFB512() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new DSTU7624Engine(512), 512)), 512);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new DSTU7624WrapEngine(128));
        }
    }

    public static class Wrap128 extends BaseWrapCipher {
        public Wrap128() {
            super(new DSTU7624WrapEngine(128));
        }
    }

    public static class Wrap256 extends BaseWrapCipher {
        public Wrap256() {
            super(new DSTU7624WrapEngine(256));
        }
    }

    public static class Wrap512 extends BaseWrapCipher {
        public Wrap512() {
            super(new DSTU7624WrapEngine(512));
        }
    }

    private DSTU7624() {
    }
}

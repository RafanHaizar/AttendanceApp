package org.bouncycastle.jcajce.provider.digest;

import com.itextpdf.p026io.codec.TIFFConstants;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.RIPEMD320Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class RIPEMD320 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new RIPEMD320Digest());
        }

        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest) super.clone();
            digest.digest = new RIPEMD320Digest((RIPEMD320Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new RIPEMD320Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACRIPEMD320", TIFFConstants.TIFFTAG_COLORMAP, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = RIPEMD320.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            configurableProvider.addAlgorithm("MessageDigest.RIPEMD320", sb.append(str).append("$Digest").toString());
            addHMACAlgorithm(configurableProvider, "RIPEMD320", str + "$HashMac", str + "$KeyGenerator");
        }
    }

    private RIPEMD320() {
    }
}

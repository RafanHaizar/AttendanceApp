package org.bouncycastle.jcajce.provider.asymmetric.p016ec;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.DSAExt;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.NullDigest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.DSAEncoding;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.ECNRSigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.crypto.signers.PlainDSAEncoding;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.provider.asymmetric.util.DSABase;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;

/* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi */
public class SignatureSpi extends DSABase {

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecCVCDSA */
    public static class ecCVCDSA extends SignatureSpi {
        public ecCVCDSA() {
            super(DigestFactory.createSHA1(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecCVCDSA224 */
    public static class ecCVCDSA224 extends SignatureSpi {
        public ecCVCDSA224() {
            super(DigestFactory.createSHA224(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecCVCDSA256 */
    public static class ecCVCDSA256 extends SignatureSpi {
        public ecCVCDSA256() {
            super(DigestFactory.createSHA256(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecCVCDSA384 */
    public static class ecCVCDSA384 extends SignatureSpi {
        public ecCVCDSA384() {
            super(DigestFactory.createSHA384(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecCVCDSA512 */
    public static class ecCVCDSA512 extends SignatureSpi {
        public ecCVCDSA512() {
            super(DigestFactory.createSHA512(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA */
    public static class ecDSA extends SignatureSpi {
        public ecDSA() {
            super(DigestFactory.createSHA1(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA224 */
    public static class ecDSA224 extends SignatureSpi {
        public ecDSA224() {
            super(DigestFactory.createSHA224(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA256 */
    public static class ecDSA256 extends SignatureSpi {
        public ecDSA256() {
            super(DigestFactory.createSHA256(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA384 */
    public static class ecDSA384 extends SignatureSpi {
        public ecDSA384() {
            super(DigestFactory.createSHA384(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA512 */
    public static class ecDSA512 extends SignatureSpi {
        public ecDSA512() {
            super(DigestFactory.createSHA512(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSARipeMD160 */
    public static class ecDSARipeMD160 extends SignatureSpi {
        public ecDSARipeMD160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSASha3_224 */
    public static class ecDSASha3_224 extends SignatureSpi {
        public ecDSASha3_224() {
            super(DigestFactory.createSHA3_224(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSASha3_256 */
    public static class ecDSASha3_256 extends SignatureSpi {
        public ecDSASha3_256() {
            super(DigestFactory.createSHA3_256(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSASha3_384 */
    public static class ecDSASha3_384 extends SignatureSpi {
        public ecDSASha3_384() {
            super(DigestFactory.createSHA3_384(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSASha3_512 */
    public static class ecDSASha3_512 extends SignatureSpi {
        public ecDSASha3_512() {
            super(DigestFactory.createSHA3_512(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSAnone */
    public static class ecDSAnone extends SignatureSpi {
        public ecDSAnone() {
            super(new NullDigest(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSA */
    public static class ecDetDSA extends SignatureSpi {
        public ecDetDSA() {
            super(DigestFactory.createSHA1(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA1())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSA224 */
    public static class ecDetDSA224 extends SignatureSpi {
        public ecDetDSA224() {
            super(DigestFactory.createSHA224(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA224())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSA256 */
    public static class ecDetDSA256 extends SignatureSpi {
        public ecDetDSA256() {
            super(DigestFactory.createSHA256(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA256())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSA384 */
    public static class ecDetDSA384 extends SignatureSpi {
        public ecDetDSA384() {
            super(DigestFactory.createSHA384(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA384())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSA512 */
    public static class ecDetDSA512 extends SignatureSpi {
        public ecDetDSA512() {
            super(DigestFactory.createSHA512(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA512())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSASha3_224 */
    public static class ecDetDSASha3_224 extends SignatureSpi {
        public ecDetDSASha3_224() {
            super(DigestFactory.createSHA3_224(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_224())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSASha3_256 */
    public static class ecDetDSASha3_256 extends SignatureSpi {
        public ecDetDSASha3_256() {
            super(DigestFactory.createSHA3_256(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_256())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSASha3_384 */
    public static class ecDetDSASha3_384 extends SignatureSpi {
        public ecDetDSASha3_384() {
            super(DigestFactory.createSHA3_384(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_384())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDetDSASha3_512 */
    public static class ecDetDSASha3_512 extends SignatureSpi {
        public ecDetDSASha3_512() {
            super(DigestFactory.createSHA3_512(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_512())), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecNR */
    public static class ecNR extends SignatureSpi {
        public ecNR() {
            super(DigestFactory.createSHA1(), new ECNRSigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecNR224 */
    public static class ecNR224 extends SignatureSpi {
        public ecNR224() {
            super(DigestFactory.createSHA224(), new ECNRSigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecNR256 */
    public static class ecNR256 extends SignatureSpi {
        public ecNR256() {
            super(DigestFactory.createSHA256(), new ECNRSigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecNR384 */
    public static class ecNR384 extends SignatureSpi {
        public ecNR384() {
            super(DigestFactory.createSHA384(), new ECNRSigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecNR512 */
    public static class ecNR512 extends SignatureSpi {
        public ecNR512() {
            super(DigestFactory.createSHA512(), new ECNRSigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecPlainDSARP160 */
    public static class ecPlainDSARP160 extends SignatureSpi {
        public ecPlainDSARP160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), PlainDSAEncoding.INSTANCE);
        }
    }

    SignatureSpi(Digest digest, DSAExt dSAExt, DSAEncoding dSAEncoding) {
        super(digest, dSAExt, dSAEncoding);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        AsymmetricKeyParameter generatePrivateKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
        this.digest.reset();
        if (this.appRandom != null) {
            this.signer.init(true, new ParametersWithRandom(generatePrivateKeyParameter, this.appRandom));
        } else {
            this.signer.init(true, generatePrivateKeyParameter);
        }
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        AsymmetricKeyParameter generatePublicKeyParameter = ECUtils.generatePublicKeyParameter(publicKey);
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
    }
}

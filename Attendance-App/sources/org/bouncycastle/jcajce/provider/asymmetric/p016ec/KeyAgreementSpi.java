package org.bouncycastle.jcajce.provider.asymmetric.p016ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.p008x9.X9IntegerConverter;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.bouncycastle.crypto.agreement.ECDHCUnifiedAgreement;
import org.bouncycastle.crypto.agreement.ECMQVBasicAgreement;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.ECDHUPublicParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.MQVPublicParameters;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.bouncycastle.jcajce.spec.DHUParameterSpec;
import org.bouncycastle.jcajce.spec.MQVParameterSpec;
import org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.MQVPublicKey;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi */
public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final X9IntegerConverter converter = new X9IntegerConverter();
    private Object agreement;
    private DHUParameterSpec dheParameters;
    private String kaAlgorithm;
    private MQVParameterSpec mqvParameters;
    private ECDomainParameters parameters;
    private byte[] result;

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$CDHwithSHA1KDFAndSharedInfo */
    public static class CDHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA1KDFAndSharedInfo() {
            super("ECCDHwithSHA1KDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$CDHwithSHA224KDFAndSharedInfo */
    public static class CDHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA224KDFAndSharedInfo() {
            super("ECCDHwithSHA224KDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$CDHwithSHA256KDFAndSharedInfo */
    public static class CDHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA256KDFAndSharedInfo() {
            super("ECCDHwithSHA256KDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$CDHwithSHA384KDFAndSharedInfo */
    public static class CDHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA384KDFAndSharedInfo() {
            super("ECCDHwithSHA384KDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$CDHwithSHA512KDFAndSharedInfo */
    public static class CDHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA512KDFAndSharedInfo() {
            super("ECCDHwithSHA512KDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DH */
    public static class C0306DH extends KeyAgreementSpi {
        public C0306DH() {
            super("ECDH", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) null);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHC */
    public static class DHC extends KeyAgreementSpi {
        public DHC() {
            super("ECDHC", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) null);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUC */
    public static class DHUC extends KeyAgreementSpi {
        public DHUC() {
            super("ECCDHU", new ECDHCUnifiedAgreement(), (DerivationFunction) null);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA1CKDF */
    public static class DHUwithSHA1CKDF extends KeyAgreementSpi {
        public DHUwithSHA1CKDF() {
            super("ECCDHUwithSHA1CKDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA1KDF */
    public static class DHUwithSHA1KDF extends KeyAgreementSpi {
        public DHUwithSHA1KDF() {
            super("ECCDHUwithSHA1KDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA224CKDF */
    public static class DHUwithSHA224CKDF extends KeyAgreementSpi {
        public DHUwithSHA224CKDF() {
            super("ECCDHUwithSHA224CKDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA224KDF */
    public static class DHUwithSHA224KDF extends KeyAgreementSpi {
        public DHUwithSHA224KDF() {
            super("ECCDHUwithSHA224KDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA256CKDF */
    public static class DHUwithSHA256CKDF extends KeyAgreementSpi {
        public DHUwithSHA256CKDF() {
            super("ECCDHUwithSHA256CKDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA256KDF */
    public static class DHUwithSHA256KDF extends KeyAgreementSpi {
        public DHUwithSHA256KDF() {
            super("ECCDHUwithSHA256KDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA384CKDF */
    public static class DHUwithSHA384CKDF extends KeyAgreementSpi {
        public DHUwithSHA384CKDF() {
            super("ECCDHUwithSHA384CKDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA384KDF */
    public static class DHUwithSHA384KDF extends KeyAgreementSpi {
        public DHUwithSHA384KDF() {
            super("ECCDHUwithSHA384KDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA512CKDF */
    public static class DHUwithSHA512CKDF extends KeyAgreementSpi {
        public DHUwithSHA512CKDF() {
            super("ECCDHUwithSHA512CKDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHUwithSHA512KDF */
    public static class DHUwithSHA512KDF extends KeyAgreementSpi {
        public DHUwithSHA512KDF() {
            super("ECCDHUwithSHA512KDF", new ECDHCUnifiedAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA1CKDF */
    public static class DHwithSHA1CKDF extends KeyAgreementSpi {
        public DHwithSHA1CKDF() {
            super("ECDHwithSHA1CKDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA1KDF */
    public static class DHwithSHA1KDF extends KeyAgreementSpi {
        public DHwithSHA1KDF() {
            super("ECDHwithSHA1KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA1KDFAndSharedInfo */
    public static class DHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA1KDFAndSharedInfo() {
            super("ECDHwithSHA1KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA224KDFAndSharedInfo */
    public static class DHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA224KDFAndSharedInfo() {
            super("ECDHwithSHA224KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA256CKDF */
    public static class DHwithSHA256CKDF extends KeyAgreementSpi {
        public DHwithSHA256CKDF() {
            super("ECDHwithSHA256CKDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA256KDFAndSharedInfo */
    public static class DHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA256KDFAndSharedInfo() {
            super("ECDHwithSHA256KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA384CKDF */
    public static class DHwithSHA384CKDF extends KeyAgreementSpi {
        public DHwithSHA384CKDF() {
            super("ECDHwithSHA384CKDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA384KDFAndSharedInfo */
    public static class DHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA384KDFAndSharedInfo() {
            super("ECDHwithSHA384KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA512CKDF */
    public static class DHwithSHA512CKDF extends KeyAgreementSpi {
        public DHwithSHA512CKDF() {
            super("ECDHwithSHA512CKDF", (BasicAgreement) new ECDHCBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DHwithSHA512KDFAndSharedInfo */
    public static class DHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA512KDFAndSharedInfo() {
            super("ECDHwithSHA512KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithRIPEMD160KDF */
    public static class ECKAEGwithRIPEMD160KDF extends KeyAgreementSpi {
        public ECKAEGwithRIPEMD160KDF() {
            super("ECKAEGwithRIPEMD160KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(new RIPEMD160Digest()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithSHA1KDF */
    public static class ECKAEGwithSHA1KDF extends KeyAgreementSpi {
        public ECKAEGwithSHA1KDF() {
            super("ECKAEGwithSHA1KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithSHA224KDF */
    public static class ECKAEGwithSHA224KDF extends KeyAgreementSpi {
        public ECKAEGwithSHA224KDF() {
            super("ECKAEGwithSHA224KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithSHA256KDF */
    public static class ECKAEGwithSHA256KDF extends KeyAgreementSpi {
        public ECKAEGwithSHA256KDF() {
            super("ECKAEGwithSHA256KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithSHA384KDF */
    public static class ECKAEGwithSHA384KDF extends KeyAgreementSpi {
        public ECKAEGwithSHA384KDF() {
            super("ECKAEGwithSHA384KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$ECKAEGwithSHA512KDF */
    public static class ECKAEGwithSHA512KDF extends KeyAgreementSpi {
        public ECKAEGwithSHA512KDF() {
            super("ECKAEGwithSHA512KDF", (BasicAgreement) new ECDHBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQV */
    public static class MQV extends KeyAgreementSpi {
        public MQV() {
            super("ECMQV", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) null);
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA1CKDF */
    public static class MQVwithSHA1CKDF extends KeyAgreementSpi {
        public MQVwithSHA1CKDF() {
            super("ECMQVwithSHA1CKDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA1KDF */
    public static class MQVwithSHA1KDF extends KeyAgreementSpi {
        public MQVwithSHA1KDF() {
            super("ECMQVwithSHA1KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA1KDFAndSharedInfo */
    public static class MQVwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA1KDFAndSharedInfo() {
            super("ECMQVwithSHA1KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA1()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA224CKDF */
    public static class MQVwithSHA224CKDF extends KeyAgreementSpi {
        public MQVwithSHA224CKDF() {
            super("ECMQVwithSHA224CKDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA224KDF */
    public static class MQVwithSHA224KDF extends KeyAgreementSpi {
        public MQVwithSHA224KDF() {
            super("ECMQVwithSHA224KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA224KDFAndSharedInfo */
    public static class MQVwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA224KDFAndSharedInfo() {
            super("ECMQVwithSHA224KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA224()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA256CKDF */
    public static class MQVwithSHA256CKDF extends KeyAgreementSpi {
        public MQVwithSHA256CKDF() {
            super("ECMQVwithSHA256CKDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA256KDF */
    public static class MQVwithSHA256KDF extends KeyAgreementSpi {
        public MQVwithSHA256KDF() {
            super("ECMQVwithSHA256KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA256KDFAndSharedInfo */
    public static class MQVwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA256KDFAndSharedInfo() {
            super("ECMQVwithSHA256KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA384CKDF */
    public static class MQVwithSHA384CKDF extends KeyAgreementSpi {
        public MQVwithSHA384CKDF() {
            super("ECMQVwithSHA384CKDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA384KDF */
    public static class MQVwithSHA384KDF extends KeyAgreementSpi {
        public MQVwithSHA384KDF() {
            super("ECMQVwithSHA384KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA384KDFAndSharedInfo */
    public static class MQVwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA384KDFAndSharedInfo() {
            super("ECMQVwithSHA384KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA384()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA512CKDF */
    public static class MQVwithSHA512CKDF extends KeyAgreementSpi {
        public MQVwithSHA512CKDF() {
            super("ECMQVwithSHA512CKDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA512KDF */
    public static class MQVwithSHA512KDF extends KeyAgreementSpi {
        public MQVwithSHA512KDF() {
            super("ECMQVwithSHA512KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    /* renamed from: org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$MQVwithSHA512KDFAndSharedInfo */
    public static class MQVwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA512KDFAndSharedInfo() {
            super("ECMQVwithSHA512KDF", (BasicAgreement) new ECMQVBasicAgreement(), (DerivationFunction) new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    protected KeyAgreementSpi(String str, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.kaAlgorithm = str;
        this.agreement = basicAgreement;
    }

    protected KeyAgreementSpi(String str, ECDHCUnifiedAgreement eCDHCUnifiedAgreement, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.kaAlgorithm = str;
        this.agreement = eCDHCUnifiedAgreement;
    }

    private static String getSimpleName(Class cls) {
        String name = cls.getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: org.bouncycastle.crypto.params.ECPublicKeyParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: org.bouncycastle.crypto.params.ECPublicKeyParameters} */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initFromKey(java.security.Key r6, java.security.spec.AlgorithmParameterSpec r7) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        /*
            r5 = this;
            java.lang.Object r0 = r5.agreement
            boolean r1 = r0 instanceof org.bouncycastle.crypto.agreement.ECMQVBasicAgreement
            java.lang.String r2 = " for initialisation"
            java.lang.String r3 = " key agreement requires "
            r4 = 0
            if (r1 == 0) goto L_0x00a9
            r5.mqvParameters = r4
            boolean r0 = r6 instanceof org.bouncycastle.jce.interfaces.MQVPrivateKey
            if (r0 != 0) goto L_0x003d
            boolean r1 = r7 instanceof org.bouncycastle.jcajce.spec.MQVParameterSpec
            if (r1 == 0) goto L_0x0016
            goto L_0x003d
        L_0x0016:
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = r5.kaAlgorithm
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.Class<org.bouncycastle.jcajce.spec.MQVParameterSpec> r0 = org.bouncycastle.jcajce.spec.MQVParameterSpec.class
            java.lang.String r0 = getSimpleName(r0)
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.StringBuilder r7 = r7.append(r2)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x003d:
            if (r0 == 0) goto L_0x0067
            org.bouncycastle.jce.interfaces.MQVPrivateKey r6 = (org.bouncycastle.jce.interfaces.MQVPrivateKey) r6
            java.security.PrivateKey r7 = r6.getStaticPrivateKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r7 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r7)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r7 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r7
            java.security.PrivateKey r0 = r6.getEphemeralPrivateKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r0)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r0 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r0
            java.security.PublicKey r1 = r6.getEphemeralPublicKey()
            if (r1 == 0) goto L_0x0095
            java.security.PublicKey r6 = r6.getEphemeralPublicKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r6 = org.bouncycastle.jcajce.provider.asymmetric.p016ec.ECUtils.generatePublicKeyParameter(r6)
            r4 = r6
            org.bouncycastle.crypto.params.ECPublicKeyParameters r4 = (org.bouncycastle.crypto.params.ECPublicKeyParameters) r4
            goto L_0x0095
        L_0x0067:
            org.bouncycastle.jcajce.spec.MQVParameterSpec r7 = (org.bouncycastle.jcajce.spec.MQVParameterSpec) r7
            java.security.PrivateKey r6 = (java.security.PrivateKey) r6
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r6 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r6)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r6 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r6
            java.security.PrivateKey r0 = r7.getEphemeralPrivateKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r0)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r0 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r0
            java.security.PublicKey r1 = r7.getEphemeralPublicKey()
            if (r1 == 0) goto L_0x008c
            java.security.PublicKey r1 = r7.getEphemeralPublicKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r1 = org.bouncycastle.jcajce.provider.asymmetric.p016ec.ECUtils.generatePublicKeyParameter(r1)
            r4 = r1
            org.bouncycastle.crypto.params.ECPublicKeyParameters r4 = (org.bouncycastle.crypto.params.ECPublicKeyParameters) r4
        L_0x008c:
            r5.mqvParameters = r7
            byte[] r7 = r7.getUserKeyingMaterial()
            r5.ukmParameters = r7
            r7 = r6
        L_0x0095:
            org.bouncycastle.crypto.params.MQVPrivateParameters r6 = new org.bouncycastle.crypto.params.MQVPrivateParameters
            r6.<init>(r7, r0, r4)
            org.bouncycastle.crypto.params.ECDomainParameters r7 = r7.getParameters()
            r5.parameters = r7
            java.lang.Object r7 = r5.agreement
            org.bouncycastle.crypto.agreement.ECMQVBasicAgreement r7 = (org.bouncycastle.crypto.agreement.ECMQVBasicAgreement) r7
            r7.init(r6)
            goto L_0x014c
        L_0x00a9:
            boolean r1 = r7 instanceof org.bouncycastle.jcajce.spec.DHUParameterSpec
            if (r1 == 0) goto L_0x0116
            boolean r0 = r0 instanceof org.bouncycastle.crypto.agreement.ECDHCUnifiedAgreement
            if (r0 == 0) goto L_0x00f1
            org.bouncycastle.jcajce.spec.DHUParameterSpec r7 = (org.bouncycastle.jcajce.spec.DHUParameterSpec) r7
            java.security.PrivateKey r6 = (java.security.PrivateKey) r6
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r6 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r6)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r6 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r6
            java.security.PrivateKey r0 = r7.getEphemeralPrivateKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r0)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r0 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r0
            java.security.PublicKey r1 = r7.getEphemeralPublicKey()
            if (r1 == 0) goto L_0x00d6
            java.security.PublicKey r1 = r7.getEphemeralPublicKey()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r1 = org.bouncycastle.jcajce.provider.asymmetric.p016ec.ECUtils.generatePublicKeyParameter(r1)
            r4 = r1
            org.bouncycastle.crypto.params.ECPublicKeyParameters r4 = (org.bouncycastle.crypto.params.ECPublicKeyParameters) r4
        L_0x00d6:
            r5.dheParameters = r7
            byte[] r7 = r7.getUserKeyingMaterial()
            r5.ukmParameters = r7
            org.bouncycastle.crypto.params.ECDHUPrivateParameters r7 = new org.bouncycastle.crypto.params.ECDHUPrivateParameters
            r7.<init>(r6, r0, r4)
            org.bouncycastle.crypto.params.ECDomainParameters r6 = r6.getParameters()
            r5.parameters = r6
            java.lang.Object r6 = r5.agreement
            org.bouncycastle.crypto.agreement.ECDHCUnifiedAgreement r6 = (org.bouncycastle.crypto.agreement.ECDHCUnifiedAgreement) r6
            r6.init(r7)
            goto L_0x014c
        L_0x00f1:
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = r5.kaAlgorithm
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.String r0 = " key agreement cannot be used with "
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.Class<org.bouncycastle.jcajce.spec.DHUParameterSpec> r0 = org.bouncycastle.jcajce.spec.DHUParameterSpec.class
            java.lang.String r0 = getSimpleName(r0)
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0116:
            boolean r0 = r6 instanceof java.security.PrivateKey
            if (r0 == 0) goto L_0x014d
            org.bouncycastle.crypto.DerivationFunction r0 = r5.kdf
            if (r0 != 0) goto L_0x012b
            boolean r0 = r7 instanceof org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec
            if (r0 != 0) goto L_0x0123
            goto L_0x012b
        L_0x0123:
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r7 = "no KDF specified for UserKeyingMaterialSpec"
            r6.<init>(r7)
            throw r6
        L_0x012b:
            java.security.PrivateKey r6 = (java.security.PrivateKey) r6
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r6 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r6)
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r6 = (org.bouncycastle.crypto.params.ECPrivateKeyParameters) r6
            org.bouncycastle.crypto.params.ECDomainParameters r0 = r6.getParameters()
            r5.parameters = r0
            boolean r0 = r7 instanceof org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec
            if (r0 == 0) goto L_0x0143
            org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec r7 = (org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec) r7
            byte[] r4 = r7.getUserKeyingMaterial()
        L_0x0143:
            r5.ukmParameters = r4
            java.lang.Object r7 = r5.agreement
            org.bouncycastle.crypto.BasicAgreement r7 = (org.bouncycastle.crypto.BasicAgreement) r7
            r7.init(r6)
        L_0x014c:
            return
        L_0x014d:
            java.security.InvalidKeyException r6 = new java.security.InvalidKeyException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = r5.kaAlgorithm
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.Class<org.bouncycastle.jce.interfaces.ECPrivateKey> r0 = org.bouncycastle.jce.interfaces.ECPrivateKey.class
            java.lang.String r0 = getSimpleName(r0)
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.StringBuilder r7 = r7.append(r2)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.p016ec.KeyAgreementSpi.initFromKey(java.security.Key, java.security.spec.AlgorithmParameterSpec):void");
    }

    /* access modifiers changed from: protected */
    public byte[] bigIntToBytes(BigInteger bigInteger) {
        X9IntegerConverter x9IntegerConverter = converter;
        return x9IntegerConverter.integerToBytes(bigInteger, x9IntegerConverter.getByteLength(this.parameters.getCurve()));
    }

    /* access modifiers changed from: protected */
    public byte[] calcSecret() {
        return Arrays.clone(this.result);
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean z) throws InvalidKeyException, IllegalStateException {
        CipherParameters cipherParameters;
        if (this.parameters == null) {
            throw new IllegalStateException(this.kaAlgorithm + " not initialised.");
        } else if (z) {
            Object obj = this.agreement;
            if (obj instanceof ECMQVBasicAgreement) {
                if (!(key instanceof MQVPublicKey)) {
                    cipherParameters = new MQVPublicParameters((ECPublicKeyParameters) ECUtils.generatePublicKeyParameter((PublicKey) key), (ECPublicKeyParameters) ECUtils.generatePublicKeyParameter(this.mqvParameters.getOtherPartyEphemeralKey()));
                } else {
                    MQVPublicKey mQVPublicKey = (MQVPublicKey) key;
                    cipherParameters = new MQVPublicParameters((ECPublicKeyParameters) ECUtils.generatePublicKeyParameter(mQVPublicKey.getStaticKey()), (ECPublicKeyParameters) ECUtils.generatePublicKeyParameter(mQVPublicKey.getEphemeralKey()));
                }
            } else if (obj instanceof ECDHCUnifiedAgreement) {
                cipherParameters = new ECDHUPublicParameters((ECPublicKeyParameters) ECUtils.generatePublicKeyParameter((PublicKey) key), (ECPublicKeyParameters) ECUtils.generatePublicKeyParameter(this.dheParameters.getOtherPartyEphemeralKey()));
            } else if (key instanceof PublicKey) {
                cipherParameters = ECUtils.generatePublicKeyParameter((PublicKey) key);
            } else {
                throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(ECPublicKey.class) + " for doPhase");
            }
            try {
                Object obj2 = this.agreement;
                if (obj2 instanceof BasicAgreement) {
                    this.result = bigIntToBytes(((BasicAgreement) obj2).calculateAgreement(cipherParameters));
                    return null;
                }
                this.result = ((ECDHCUnifiedAgreement) obj2).calculateAgreement(cipherParameters);
                return null;
            } catch (Exception e) {
                throw new InvalidKeyException("calculation failed: " + e.getMessage()) {
                    public Throwable getCause() {
                        return e;
                    }
                };
            }
        } else {
            throw new IllegalStateException(this.kaAlgorithm + " can only be between two parties.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            initFromKey(key, (AlgorithmParameterSpec) null);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null || (algorithmParameterSpec instanceof MQVParameterSpec) || (algorithmParameterSpec instanceof UserKeyingMaterialSpec) || (algorithmParameterSpec instanceof DHUParameterSpec)) {
            initFromKey(key, algorithmParameterSpec);
            return;
        }
        throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
    }
}

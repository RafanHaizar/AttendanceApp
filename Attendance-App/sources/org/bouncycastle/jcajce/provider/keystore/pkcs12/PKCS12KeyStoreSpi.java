package org.bouncycastle.jcajce.provider.keystore.pkcs12;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.GOST28147Parameters;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import org.bouncycastle.asn1.pkcs.CertBag;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.EncryptedData;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.asn1.pkcs.SafeBag;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.cms.CMSEnvelopedGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.PKCS12Key;
import org.bouncycastle.jcajce.PKCS12StoreParameter;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.interfaces.BCKeyStore;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.JDKPKCS12StoreParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.Strings;

public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 51200;
    static final int NULL = 0;
    static final String PKCS12_MAX_IT_COUNT_PROPERTY = "org.bouncycastle.pkcs12.max_it_count";
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final DefaultSecretKeyProvider keySizeProvider = new DefaultSecretKeyProvider();
    private ASN1ObjectIdentifier certAlgorithm;
    private CertificateFactory certFact;
    private IgnoresCaseHashtable certs = new IgnoresCaseHashtable();
    private Hashtable chainCerts = new Hashtable();
    private final JcaJceHelper helper = new BCJcaJceHelper();
    private int itCount = 102400;
    private ASN1ObjectIdentifier keyAlgorithm;
    private Hashtable keyCerts = new Hashtable();
    private IgnoresCaseHashtable keys = new IgnoresCaseHashtable();
    private Hashtable localIds = new Hashtable();
    private AlgorithmIdentifier macAlgorithm = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    protected SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    private int saltLength = 20;

    public static class BCPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(new BCJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    public static class BCPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore3DES() {
            super(new BCJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    private class CertId {

        /* renamed from: id */
        byte[] f666id;

        CertId(PublicKey publicKey) {
            this.f666id = PKCS12KeyStoreSpi.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        CertId(byte[] bArr) {
            this.f666id = bArr;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CertId)) {
                return false;
            }
            return Arrays.areEqual(this.f666id, ((CertId) obj).f666id);
        }

        public int hashCode() {
            return Arrays.hashCode(this.f666id);
        }
    }

    public static class DefPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public DefPKCS12KeyStore() {
            super(new DefaultJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    public static class DefPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        public DefPKCS12KeyStore3DES() {
            super(new DefaultJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    private static class DefaultSecretKeyProvider {
        private final Map KEY_SIZES;

        DefaultSecretKeyProvider() {
            HashMap hashMap = new HashMap();
            hashMap.put(new ASN1ObjectIdentifier(CMSEnvelopedGenerator.CAST5_CBC), Integers.valueOf(128));
            hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
            hashMap.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
            hashMap.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
            hashMap.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
            hashMap.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
            hashMap.put(CryptoProObjectIdentifiers.gostR28147_gcfb, Integers.valueOf(256));
            this.KEY_SIZES = Collections.unmodifiableMap(hashMap);
        }

        public int getKeySize(AlgorithmIdentifier algorithmIdentifier) {
            Integer num = (Integer) this.KEY_SIZES.get(algorithmIdentifier.getAlgorithm());
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
    }

    private static class IgnoresCaseHashtable {
        private Hashtable keys;
        private Hashtable orig;

        private IgnoresCaseHashtable() {
            this.orig = new Hashtable();
            this.keys = new Hashtable();
        }

        public Enumeration elements() {
            return this.orig.elements();
        }

        public Object get(String str) {
            String str2 = (String) this.keys.get(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.get(str2);
        }

        public Enumeration keys() {
            return this.orig.keys();
        }

        public void put(String str, Object obj) {
            String lowerCase = str == null ? null : Strings.toLowerCase(str);
            String str2 = (String) this.keys.get(lowerCase);
            if (str2 != null) {
                this.orig.remove(str2);
            }
            this.keys.put(lowerCase, str);
            this.orig.put(str, obj);
        }

        public Object remove(String str) {
            String str2 = (String) this.keys.remove(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.remove(str2);
        }

        public int size() {
            return this.orig.size();
        }
    }

    public PKCS12KeyStoreSpi(JcaJceHelper jcaJceHelper, ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1ObjectIdentifier aSN1ObjectIdentifier2) {
        this.keyAlgorithm = aSN1ObjectIdentifier;
        this.certAlgorithm = aSN1ObjectIdentifier2;
        try {
            this.certFact = jcaJceHelper.createCertificateFactory("X.509");
        } catch (Exception e) {
            throw new IllegalArgumentException("can't create cert factory - " + e.toString());
        }
    }

    private byte[] calculatePbeMac(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr, int i, char[] cArr, boolean z, byte[] bArr2) throws Exception {
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i);
        Mac createMac = this.helper.createMac(aSN1ObjectIdentifier.getId());
        createMac.init(new PKCS12Key(cArr, z), pBEParameterSpec);
        createMac.update(bArr2);
        return createMac.doFinal();
    }

    private Cipher createCipher(int i, char[] cArr, AlgorithmIdentifier algorithmIdentifier) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {
        SecretKey secretKey;
        AlgorithmParameterSpec algorithmParameterSpec;
        PBES2Parameters instance = PBES2Parameters.getInstance(algorithmIdentifier.getParameters());
        PBKDF2Params instance2 = PBKDF2Params.getInstance(instance.getKeyDerivationFunc().getParameters());
        AlgorithmIdentifier instance3 = AlgorithmIdentifier.getInstance(instance.getEncryptionScheme());
        SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(instance.getKeyDerivationFunc().getAlgorithm().getId());
        if (instance2.isDefaultPrf()) {
            secretKey = createSecretKeyFactory.generateSecret(new PBEKeySpec(cArr, instance2.getSalt(), validateIterationCount(instance2.getIterationCount()), keySizeProvider.getKeySize(instance3)));
        } else {
            secretKey = createSecretKeyFactory.generateSecret(new PBKDF2KeySpec(cArr, instance2.getSalt(), validateIterationCount(instance2.getIterationCount()), keySizeProvider.getKeySize(instance3), instance2.getPrf()));
        }
        Cipher instance4 = Cipher.getInstance(instance.getEncryptionScheme().getAlgorithm().getId());
        ASN1Encodable parameters = instance.getEncryptionScheme().getParameters();
        if (parameters instanceof ASN1OctetString) {
            algorithmParameterSpec = new IvParameterSpec(ASN1OctetString.getInstance(parameters).getOctets());
        } else {
            GOST28147Parameters instance5 = GOST28147Parameters.getInstance(parameters);
            algorithmParameterSpec = new GOST28147ParameterSpec(instance5.getEncryptionParamSet(), instance5.getIV());
        }
        instance4.init(i, secretKey, algorithmParameterSpec);
        return instance4;
    }

    private SafeBag createSafeBag(String str, Certificate certificate) throws CertificateEncodingException {
        CertBag certBag = new CertBag(x509Certificate, new DEROctetString(certificate.getEncoded()));
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        boolean z = false;
        if (certificate instanceof PKCS12BagAttributeCarrier) {
            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) certificate;
            DERBMPString dERBMPString = (DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
            if ((dERBMPString == null || !dERBMPString.getString().equals(str)) && str != null) {
                pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str));
            }
            Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
            while (bagAttributeKeys.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                if (!aSN1ObjectIdentifier.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                    ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                    aSN1EncodableVector2.add(aSN1ObjectIdentifier);
                    aSN1EncodableVector2.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier)));
                    aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
                    z = true;
                }
            }
        }
        if (!z) {
            ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
            aSN1EncodableVector3.add(pkcs_9_at_friendlyName);
            aSN1EncodableVector3.add(new DERSet((ASN1Encodable) new DERBMPString(str)));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector3));
        }
        return new SafeBag(certBag, certBag.toASN1Primitive(), new DERSet(aSN1EncodableVector));
    }

    /* access modifiers changed from: private */
    public SubjectKeyIdentifier createSubjectKeyId(PublicKey publicKey) {
        try {
            return new SubjectKeyIdentifier(getDigest(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())));
        } catch (Exception e) {
            throw new RuntimeException("error creating key");
        }
    }

    private void doStore(OutputStream outputStream, char[] cArr, boolean z) throws IOException {
        String str;
        boolean z2;
        boolean z3;
        ContentInfo contentInfo;
        OutputStream outputStream2 = outputStream;
        char[] cArr2 = cArr;
        int size = this.keys.size();
        String str2 = ASN1Encoding.BER;
        if (size == 0) {
            if (cArr2 == null) {
                Enumeration keys2 = this.certs.keys();
                ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                while (keys2.hasMoreElements()) {
                    try {
                        String str3 = (String) keys2.nextElement();
                        aSN1EncodableVector.add(createSafeBag(str3, (Certificate) this.certs.get(str3)));
                    } catch (CertificateEncodingException e) {
                        throw new IOException("Error encoding certificate: " + e.toString());
                    }
                }
                ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.data;
                if (z) {
                    DEROctetString dEROctetString = new DEROctetString(new DERSequence(aSN1EncodableVector).getEncoded());
                    new Pfx(new ContentInfo(PKCSObjectIdentifiers.data, new DEROctetString(new DERSequence((ASN1Encodable) contentInfo).getEncoded())), (MacData) null).encodeTo(outputStream2, ASN1Encoding.DER);
                    return;
                }
                contentInfo = new ContentInfo(aSN1ObjectIdentifier, new BEROctetString(new BERSequence(aSN1EncodableVector).getEncoded()));
                new Pfx(new ContentInfo(PKCSObjectIdentifiers.data, new BEROctetString(new BERSequence((ASN1Encodable) contentInfo).getEncoded())), (MacData) null).encodeTo(outputStream2, str2);
                return;
            }
        } else if (cArr2 == null) {
            throw new NullPointerException("no password supplied for PKCS#12 KeyStore");
        }
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        Enumeration keys3 = this.keys.keys();
        while (keys3.hasMoreElements()) {
            byte[] bArr = new byte[20];
            this.random.nextBytes(bArr);
            String str4 = (String) keys3.nextElement();
            PrivateKey privateKey = (PrivateKey) this.keys.get(str4);
            PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams(bArr, MIN_ITERATIONS);
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(this.keyAlgorithm, pKCS12PBEParams.toASN1Primitive()), wrapKey(this.keyAlgorithm.getId(), privateKey, pKCS12PBEParams, cArr2));
            ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
            if (privateKey instanceof PKCS12BagAttributeCarrier) {
                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) privateKey;
                DERBMPString dERBMPString = (DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
                if (dERBMPString == null || !dERBMPString.getString().equals(str4)) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str4));
                }
                if (pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate(str4).getPublicKey()));
                }
                Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                z3 = false;
                while (bagAttributeKeys.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                    ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
                    aSN1EncodableVector4.add(aSN1ObjectIdentifier2);
                    aSN1EncodableVector4.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2)));
                    aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector4));
                    z3 = true;
                }
            } else {
                z3 = false;
            }
            if (!z3) {
                ASN1EncodableVector aSN1EncodableVector5 = new ASN1EncodableVector();
                Certificate engineGetCertificate = engineGetCertificate(str4);
                aSN1EncodableVector5.add(pkcs_9_at_localKeyId);
                aSN1EncodableVector5.add(new DERSet((ASN1Encodable) createSubjectKeyId(engineGetCertificate.getPublicKey())));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector5));
                ASN1EncodableVector aSN1EncodableVector6 = new ASN1EncodableVector();
                aSN1EncodableVector6.add(pkcs_9_at_friendlyName);
                aSN1EncodableVector6.add(new DERSet((ASN1Encodable) new DERBMPString(str4)));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector6));
            }
            aSN1EncodableVector2.add(new SafeBag(pkcs8ShroudedKeyBag, encryptedPrivateKeyInfo.toASN1Primitive(), new DERSet(aSN1EncodableVector3)));
        }
        BEROctetString bEROctetString = new BEROctetString(new DERSequence(aSN1EncodableVector2).getEncoded(ASN1Encoding.DER));
        byte[] bArr2 = new byte[20];
        this.random.nextBytes(bArr2);
        ASN1EncodableVector aSN1EncodableVector7 = new ASN1EncodableVector();
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(this.certAlgorithm, new PKCS12PBEParams(bArr2, MIN_ITERATIONS).toASN1Primitive());
        Hashtable hashtable = new Hashtable();
        Enumeration keys4 = this.keys.keys();
        while (keys4.hasMoreElements()) {
            try {
                String str5 = (String) keys4.nextElement();
                Certificate engineGetCertificate2 = engineGetCertificate(str5);
                Enumeration enumeration = keys4;
                CertBag certBag = new CertBag(x509Certificate, new DEROctetString(engineGetCertificate2.getEncoded()));
                ASN1EncodableVector aSN1EncodableVector8 = new ASN1EncodableVector();
                if (engineGetCertificate2 instanceof PKCS12BagAttributeCarrier) {
                    PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) engineGetCertificate2;
                    DERBMPString dERBMPString2 = (DERBMPString) pKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_friendlyName);
                    if (dERBMPString2 == null || !dERBMPString2.getString().equals(str5)) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str5));
                    }
                    if (pKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate2.getPublicKey()));
                    }
                    Enumeration bagAttributeKeys2 = pKCS12BagAttributeCarrier2.getBagAttributeKeys();
                    z2 = false;
                    while (bagAttributeKeys2.hasMoreElements()) {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = (ASN1ObjectIdentifier) bagAttributeKeys2.nextElement();
                        Enumeration enumeration2 = bagAttributeKeys2;
                        ASN1EncodableVector aSN1EncodableVector9 = new ASN1EncodableVector();
                        aSN1EncodableVector9.add(aSN1ObjectIdentifier3);
                        aSN1EncodableVector9.add(new DERSet(pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier3)));
                        aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector9));
                        bagAttributeKeys2 = enumeration2;
                        str2 = str2;
                        z2 = true;
                    }
                    str = str2;
                } else {
                    str = str2;
                    z2 = false;
                }
                if (!z2) {
                    ASN1EncodableVector aSN1EncodableVector10 = new ASN1EncodableVector();
                    aSN1EncodableVector10.add(pkcs_9_at_localKeyId);
                    aSN1EncodableVector10.add(new DERSet((ASN1Encodable) createSubjectKeyId(engineGetCertificate2.getPublicKey())));
                    aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector10));
                    ASN1EncodableVector aSN1EncodableVector11 = new ASN1EncodableVector();
                    aSN1EncodableVector11.add(pkcs_9_at_friendlyName);
                    aSN1EncodableVector11.add(new DERSet((ASN1Encodable) new DERBMPString(str5)));
                    aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector11));
                }
                aSN1EncodableVector7.add(new SafeBag(certBag, certBag.toASN1Primitive(), new DERSet(aSN1EncodableVector8)));
                hashtable.put(engineGetCertificate2, engineGetCertificate2);
                char[] cArr3 = cArr;
                keys4 = enumeration;
                str2 = str;
            } catch (CertificateEncodingException e2) {
                throw new IOException("Error encoding certificate: " + e2.toString());
            }
        }
        String str6 = str2;
        Enumeration keys5 = this.certs.keys();
        while (keys5.hasMoreElements()) {
            try {
                String str7 = (String) keys5.nextElement();
                Certificate certificate = (Certificate) this.certs.get(str7);
                if (this.keys.get(str7) == null) {
                    aSN1EncodableVector7.add(createSafeBag(str7, certificate));
                    hashtable.put(certificate, certificate);
                }
            } catch (CertificateEncodingException e3) {
                throw new IOException("Error encoding certificate: " + e3.toString());
            }
        }
        Set usedCertificateSet = getUsedCertificateSet();
        Enumeration keys6 = this.chainCerts.keys();
        while (keys6.hasMoreElements()) {
            try {
                Certificate certificate2 = (Certificate) this.chainCerts.get((CertId) keys6.nextElement());
                if (usedCertificateSet.contains(certificate2)) {
                    if (hashtable.get(certificate2) == null) {
                        CertBag certBag2 = new CertBag(x509Certificate, new DEROctetString(certificate2.getEncoded()));
                        ASN1EncodableVector aSN1EncodableVector12 = new ASN1EncodableVector();
                        if (certificate2 instanceof PKCS12BagAttributeCarrier) {
                            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier) certificate2;
                            Enumeration bagAttributeKeys3 = pKCS12BagAttributeCarrier3.getBagAttributeKeys();
                            while (bagAttributeKeys3.hasMoreElements()) {
                                ASN1ObjectIdentifier aSN1ObjectIdentifier4 = (ASN1ObjectIdentifier) bagAttributeKeys3.nextElement();
                                if (!aSN1ObjectIdentifier4.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                    ASN1EncodableVector aSN1EncodableVector13 = new ASN1EncodableVector();
                                    aSN1EncodableVector13.add(aSN1ObjectIdentifier4);
                                    aSN1EncodableVector13.add(new DERSet(pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier4)));
                                    aSN1EncodableVector12.add(new DERSequence(aSN1EncodableVector13));
                                    hashtable = hashtable;
                                }
                            }
                        }
                        Hashtable hashtable2 = hashtable;
                        aSN1EncodableVector7.add(new SafeBag(certBag, certBag2.toASN1Primitive(), new DERSet(aSN1EncodableVector12)));
                        hashtable = hashtable2;
                    }
                }
            } catch (CertificateEncodingException e4) {
                throw new IOException("Error encoding certificate: " + e4.toString());
            }
        }
        ContentInfo contentInfo2 = new ContentInfo(data, new BEROctetString(new AuthenticatedSafe(new ContentInfo[]{new ContentInfo(data, bEROctetString), new ContentInfo(encryptedData, new EncryptedData(data, algorithmIdentifier, new BEROctetString(cryptData(true, algorithmIdentifier, cArr, false, new DERSequence(aSN1EncodableVector7).getEncoded(ASN1Encoding.DER)))).toASN1Primitive())}).getEncoded(z ? ASN1Encoding.DER : str6)));
        byte[] bArr3 = new byte[this.saltLength];
        this.random.nextBytes(bArr3);
        try {
            new Pfx(contentInfo2, new MacData(new DigestInfo(this.macAlgorithm, calculatePbeMac(this.macAlgorithm.getAlgorithm(), bArr3, this.itCount, cArr, false, ((ASN1OctetString) contentInfo2.getContent()).getOctets())), bArr3, this.itCount)).encodeTo(outputStream2, z ? ASN1Encoding.DER : str6);
        } catch (Exception e5) {
            throw new IOException("error constructing MAC: " + e5.toString());
        }
    }

    private static byte[] getDigest(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        Digest createSHA1 = DigestFactory.createSHA1();
        byte[] bArr = new byte[createSHA1.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        createSHA1.update(bytes, 0, bytes.length);
        createSHA1.doFinal(bArr, 0);
        return bArr;
    }

    private Set getUsedCertificateSet() {
        HashSet hashSet = new HashSet();
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            Certificate[] engineGetCertificateChain = engineGetCertificateChain((String) keys2.nextElement());
            for (int i = 0; i != engineGetCertificateChain.length; i++) {
                hashSet.add(engineGetCertificateChain[i]);
            }
        }
        Enumeration keys3 = this.certs.keys();
        while (keys3.hasMoreElements()) {
            hashSet.add(engineGetCertificate((String) keys3.nextElement()));
        }
        return hashSet;
    }

    private int validateIterationCount(BigInteger bigInteger) {
        int intValue = bigInteger.intValue();
        if (intValue >= 0) {
            BigInteger asBigInteger = Properties.asBigInteger(PKCS12_MAX_IT_COUNT_PROPERTY);
            if (asBigInteger == null || asBigInteger.intValue() >= intValue) {
                return intValue;
            }
            throw new IllegalStateException("iteration count " + intValue + " greater than " + asBigInteger.intValue());
        }
        throw new IllegalStateException("negative iteration count found");
    }

    /* access modifiers changed from: protected */
    public byte[] cryptData(boolean z, AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z2, byte[] bArr) throws IOException {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        int i = z ? 1 : 2;
        if (algorithm.mo1052on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
            try {
                PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), instance.getIterations().intValue());
                PKCS12Key pKCS12Key = new PKCS12Key(cArr, z2);
                Cipher createCipher = this.helper.createCipher(algorithm.getId());
                createCipher.init(i, pKCS12Key, pBEParameterSpec);
                return createCipher.doFinal(bArr);
            } catch (Exception e) {
                throw new IOException("exception decrypting data - " + e.toString());
            }
        } else if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_PBES2)) {
            try {
                return createCipher(i, cArr, algorithmIdentifier).doFinal(bArr);
            } catch (Exception e2) {
                throw new IOException("exception decrypting data - " + e2.toString());
            }
        } else {
            throw new IOException("unknown PBE algorithm: " + algorithm);
        }
    }

    public Enumeration engineAliases() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys2 = this.certs.keys();
        while (keys2.hasMoreElements()) {
            hashtable.put(keys2.nextElement(), "cert");
        }
        Enumeration keys3 = this.keys.keys();
        while (keys3.hasMoreElements()) {
            String str = (String) keys3.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    public boolean engineContainsAlias(String str) {
        return (this.certs.get(str) == null && this.keys.get(str) == null) ? false : true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.security.cert.Certificate} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineDeleteEntry(java.lang.String r6) throws java.security.KeyStoreException {
        /*
            r5 = this;
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r5.keys
            java.lang.Object r0 = r0.remove(r6)
            java.security.Key r0 = (java.security.Key) r0
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = r5.certs
            java.lang.Object r1 = r1.remove(r6)
            java.security.cert.Certificate r1 = (java.security.cert.Certificate) r1
            if (r1 == 0) goto L_0x0020
            java.util.Hashtable r2 = r5.chainCerts
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r3 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r4 = r1.getPublicKey()
            r3.<init>((java.security.PublicKey) r4)
            r2.remove(r3)
        L_0x0020:
            if (r0 == 0) goto L_0x0045
            java.util.Hashtable r0 = r5.localIds
            java.lang.Object r6 = r0.remove(r6)
            java.lang.String r6 = (java.lang.String) r6
            if (r6 == 0) goto L_0x0035
            java.util.Hashtable r0 = r5.keyCerts
            java.lang.Object r6 = r0.remove(r6)
            r1 = r6
            java.security.cert.Certificate r1 = (java.security.cert.Certificate) r1
        L_0x0035:
            if (r1 == 0) goto L_0x0045
            java.util.Hashtable r6 = r5.chainCerts
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r0 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r1 = r1.getPublicKey()
            r0.<init>((java.security.PublicKey) r1)
            r6.remove(r0)
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineDeleteEntry(java.lang.String):void");
    }

    public Certificate engineGetCertificate(String str) {
        if (str != null) {
            Certificate certificate = (Certificate) this.certs.get(str);
            if (certificate != null) {
                return certificate;
            }
            String str2 = (String) this.localIds.get(str);
            return (Certificate) (str2 != null ? this.keyCerts.get(str2) : this.keyCerts.get(str));
        }
        throw new IllegalArgumentException("null alias passed to getCertificate.");
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration elements = this.certs.elements();
        Enumeration keys2 = this.certs.keys();
        while (elements.hasMoreElements()) {
            String str = (String) keys2.nextElement();
            if (((Certificate) elements.nextElement()).equals(certificate)) {
                return str;
            }
        }
        Enumeration elements2 = this.keyCerts.elements();
        Enumeration keys3 = this.keyCerts.keys();
        while (elements2.hasMoreElements()) {
            String str2 = (String) keys3.nextElement();
            if (((Certificate) elements2.nextElement()).equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String r9) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x00cb
            boolean r0 = r8.engineIsKeyEntry(r9)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.security.cert.Certificate r9 = r8.engineGetCertificate(r9)
            if (r9 == 0) goto L_0x00ca
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
        L_0x0015:
            if (r9 == 0) goto L_0x00b6
            r2 = r9
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier
            java.lang.String r3 = r3.getId()
            byte[] r3 = r2.getExtensionValue(r3)
            if (r3 == 0) goto L_0x0067
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException -> 0x005c }
            r4.<init>((byte[]) r3)     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.asn1.ASN1Primitive r3 = r4.readObject()     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.asn1.ASN1OctetString r3 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r3)     // Catch:{ IOException -> 0x005c }
            byte[] r3 = r3.getOctets()     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException -> 0x005c }
            r4.<init>((byte[]) r3)     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.asn1.ASN1Primitive r3 = r4.readObject()     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.asn1.x509.AuthorityKeyIdentifier r3 = org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(r3)     // Catch:{ IOException -> 0x005c }
            byte[] r4 = r3.getKeyIdentifier()     // Catch:{ IOException -> 0x005c }
            if (r4 == 0) goto L_0x0067
            java.util.Hashtable r4 = r8.chainCerts     // Catch:{ IOException -> 0x005c }
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r5 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId     // Catch:{ IOException -> 0x005c }
            byte[] r3 = r3.getKeyIdentifier()     // Catch:{ IOException -> 0x005c }
            r5.<init>((byte[]) r3)     // Catch:{ IOException -> 0x005c }
            java.lang.Object r3 = r4.get(r5)     // Catch:{ IOException -> 0x005c }
            java.security.cert.Certificate r3 = (java.security.cert.Certificate) r3     // Catch:{ IOException -> 0x005c }
            goto L_0x0068
        L_0x005c:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x0067:
            r3 = r1
        L_0x0068:
            if (r3 != 0) goto L_0x00a5
            java.security.Principal r4 = r2.getIssuerDN()
            java.security.Principal r5 = r2.getSubjectDN()
            boolean r5 = r4.equals(r5)
            if (r5 != 0) goto L_0x00a5
            java.util.Hashtable r5 = r8.chainCerts
            java.util.Enumeration r5 = r5.keys()
        L_0x007e:
            boolean r6 = r5.hasMoreElements()
            if (r6 == 0) goto L_0x00a5
            java.util.Hashtable r6 = r8.chainCerts
            java.lang.Object r7 = r5.nextElement()
            java.lang.Object r6 = r6.get(r7)
            java.security.cert.X509Certificate r6 = (java.security.cert.X509Certificate) r6
            java.security.Principal r7 = r6.getSubjectDN()
            boolean r7 = r7.equals(r4)
            if (r7 == 0) goto L_0x007e
            java.security.PublicKey r7 = r6.getPublicKey()     // Catch:{ Exception -> 0x00a3 }
            r2.verify(r7)     // Catch:{ Exception -> 0x00a3 }
            r3 = r6
            goto L_0x00a5
        L_0x00a3:
            r6 = move-exception
            goto L_0x007e
        L_0x00a5:
            boolean r2 = r0.contains(r9)
            if (r2 == 0) goto L_0x00ae
        L_0x00ab:
            r9 = r1
            goto L_0x0015
        L_0x00ae:
            r0.addElement(r9)
            if (r3 == r9) goto L_0x00ab
            r9 = r3
            goto L_0x0015
        L_0x00b6:
            int r9 = r0.size()
            java.security.cert.Certificate[] r1 = new java.security.cert.Certificate[r9]
            r2 = 0
        L_0x00bd:
            if (r2 == r9) goto L_0x00ca
            java.lang.Object r3 = r0.elementAt(r2)
            java.security.cert.Certificate r3 = (java.security.cert.Certificate) r3
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x00bd
        L_0x00ca:
            return r1
        L_0x00cb:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "null alias passed to getCertificateChain."
            r9.<init>(r0)
            goto L_0x00d4
        L_0x00d3:
            throw r9
        L_0x00d4:
            goto L_0x00d3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineGetCertificateChain(java.lang.String):java.security.cert.Certificate[]");
    }

    public Date engineGetCreationDate(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        } else if (this.keys.get(str) == null && this.certs.get(str) == null) {
            return null;
        } else {
            return new Date();
        }
    }

    public Key engineGetKey(String str, char[] cArr) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        if (str != null) {
            return (Key) this.keys.get(str);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    public boolean engineIsCertificateEntry(String str) {
        return this.certs.get(str) != null && this.keys.get(str) == null;
    }

    public boolean engineIsKeyEntry(String str) {
        return this.keys.get(str) != null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: org.bouncycastle.asn1.ASN1Primitive} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: org.bouncycastle.asn1.ASN1Primitive} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v30, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v15, resolved type: org.bouncycastle.asn1.ASN1Primitive} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v30, resolved type: org.bouncycastle.asn1.ASN1Primitive} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v37, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v38, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v42, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v43, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v45, resolved type: org.bouncycastle.asn1.ASN1OctetString} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x04ae  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x04cd  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineLoad(java.io.InputStream r23, char[] r24) throws java.io.IOException {
        /*
            r22 = this;
            r8 = r22
            r0 = r23
            r9 = r24
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream
            r1.<init>(r0)
            r0 = 10
            r1.mark(r0)
            int r0 = r1.read()
            r2 = 48
            if (r0 != r2) goto L_0x060a
            r1.reset()
            org.bouncycastle.asn1.ASN1InputStream r0 = new org.bouncycastle.asn1.ASN1InputStream
            r0.<init>((java.io.InputStream) r1)
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.readObject()     // Catch:{ Exception -> 0x05ff }
            org.bouncycastle.asn1.pkcs.Pfx r0 = org.bouncycastle.asn1.pkcs.Pfx.getInstance(r0)     // Catch:{ Exception -> 0x05ff }
            org.bouncycastle.asn1.pkcs.ContentInfo r10 = r0.getAuthSafe()
            java.util.Vector r11 = new java.util.Vector
            r11.<init>()
            org.bouncycastle.asn1.pkcs.MacData r1 = r0.getMacData()
            r12 = 1
            r13 = 0
            if (r1 == 0) goto L_0x00da
            if (r9 == 0) goto L_0x00d2
            org.bouncycastle.asn1.pkcs.MacData r0 = r0.getMacData()
            org.bouncycastle.asn1.x509.DigestInfo r14 = r0.getMac()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r14.getAlgorithmId()
            r8.macAlgorithm = r1
            byte[] r15 = r0.getSalt()
            java.math.BigInteger r0 = r0.getIterationCount()
            int r0 = r8.validateIterationCount(r0)
            r8.itCount = r0
            int r0 = r15.length
            r8.saltLength = r0
            org.bouncycastle.asn1.ASN1Encodable r0 = r10.getContent()
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0
            byte[] r0 = r0.getOctets()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r8.macAlgorithm     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r1.getAlgorithm()     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            int r4 = r8.itCount     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            r6 = 0
            r1 = r22
            r3 = r15
            r5 = r24
            r7 = r0
            byte[] r1 = r1.calculatePbeMac(r2, r3, r4, r5, r6, r7)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            byte[] r14 = r14.getDigest()     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            boolean r1 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r1, r14)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            if (r1 != 0) goto L_0x00ed
            int r1 = r9.length     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            java.lang.String r7 = "PKCS12 key store mac invalid - wrong password or corrupted file."
            if (r1 > 0) goto L_0x00ab
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r8.macAlgorithm     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r1.getAlgorithm()     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            int r4 = r8.itCount     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            r6 = 1
            r1 = r22
            r3 = r15
            r5 = r24
            r15 = r7
            r7 = r0
            byte[] r0 = r1.calculatePbeMac(r2, r3, r4, r5, r6, r7)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            boolean r0 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r0, r14)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            if (r0 == 0) goto L_0x00a5
            r0 = 1
            goto L_0x00ee
        L_0x00a5:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            r0.<init>(r15)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            throw r0     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
        L_0x00ab:
            r15 = r7
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            r0.<init>(r15)     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
            throw r0     // Catch:{ IOException -> 0x00d0, Exception -> 0x00b2 }
        L_0x00b2:
            r0 = move-exception
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "error constructing MAC: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x00d0:
            r0 = move-exception
            throw r0
        L_0x00d2:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "no password supplied when one expected"
            r0.<init>(r1)
            throw r0
        L_0x00da:
            java.lang.String r0 = "org.bouncycastle.pkcs12.ignore_useless_passwd"
            boolean r0 = org.bouncycastle.util.Properties.isOverrideSet(r0)
            if (r0 != 0) goto L_0x00ed
            if (r9 != 0) goto L_0x00e5
            goto L_0x00ed
        L_0x00e5:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "password supplied for keystore that does not require one"
            r0.<init>(r1)
            throw r0
        L_0x00ed:
            r0 = 0
        L_0x00ee:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r7 = 0
            r1.<init>()
            r8.keys = r1
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            r8.localIds = r1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r10.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = data
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            java.lang.String r14 = "unmarked"
            java.lang.String r15 = "attempt to add existing attribute with different value"
            if (r1 == 0) goto L_0x04ae
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream
            org.bouncycastle.asn1.ASN1Encodable r2 = r10.getContent()
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2
            byte[] r2 = r2.getOctets()
            r1.<init>((byte[]) r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()
            org.bouncycastle.asn1.pkcs.AuthenticatedSafe r1 = org.bouncycastle.asn1.pkcs.AuthenticatedSafe.getInstance(r1)
            org.bouncycastle.asn1.pkcs.ContentInfo[] r10 = r1.getContentInfo()
            r6 = 0
            r16 = 0
        L_0x012b:
            int r1 = r10.length
            if (r6 == r1) goto L_0x04b0
            r1 = r10[r6]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = data
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            if (r1 == 0) goto L_0x0270
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream
            r2 = r10[r6]
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getContent()
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r2)
            byte[] r2 = r2.getOctets()
            r1.<init>((byte[]) r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            r2 = 0
        L_0x0158:
            int r3 = r1.size()
            if (r2 == r3) goto L_0x026b
            org.bouncycastle.asn1.ASN1Encodable r3 = r1.getObjectAt(r2)
            org.bouncycastle.asn1.pkcs.SafeBag r3 = org.bouncycastle.asn1.pkcs.SafeBag.getInstance(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = pkcs8ShroudedKeyBag
            boolean r4 = r4.equals((org.bouncycastle.asn1.ASN1Primitive) r5)
            if (r4 == 0) goto L_0x022f
            org.bouncycastle.asn1.ASN1Encodable r4 = r3.getBagValue()
            org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo r4 = org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(r4)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r5 = r4.getEncryptionAlgorithm()
            byte[] r4 = r4.getEncryptedData()
            java.security.PrivateKey r4 = r8.unwrapKey(r5, r4, r9, r0)
            org.bouncycastle.asn1.ASN1Set r5 = r3.getBagAttributes()
            if (r5 == 0) goto L_0x0207
            org.bouncycastle.asn1.ASN1Set r3 = r3.getBagAttributes()
            java.util.Enumeration r3 = r3.getObjects()
            r5 = r7
            r17 = r5
        L_0x0197:
            boolean r18 = r3.hasMoreElements()
            if (r18 == 0) goto L_0x020a
            java.lang.Object r18 = r3.nextElement()
            r7 = r18
            org.bouncycastle.asn1.ASN1Sequence r7 = (org.bouncycastle.asn1.ASN1Sequence) r7
            org.bouncycastle.asn1.ASN1Encodable r18 = r7.getObjectAt(r13)
            r13 = r18
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r13
            org.bouncycastle.asn1.ASN1Encodable r7 = r7.getObjectAt(r12)
            org.bouncycastle.asn1.ASN1Set r7 = (org.bouncycastle.asn1.ASN1Set) r7
            int r18 = r7.size()
            if (r18 <= 0) goto L_0x01e2
            r12 = 0
            org.bouncycastle.asn1.ASN1Encodable r7 = r7.getObjectAt(r12)
            org.bouncycastle.asn1.ASN1Primitive r7 = (org.bouncycastle.asn1.ASN1Primitive) r7
            boolean r12 = r4 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r12 == 0) goto L_0x01e3
            r12 = r4
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12
            org.bouncycastle.asn1.ASN1Encodable r20 = r12.getBagAttribute(r13)
            if (r20 == 0) goto L_0x01de
            org.bouncycastle.asn1.ASN1Primitive r12 = r20.toASN1Primitive()
            boolean r12 = r12.equals((org.bouncycastle.asn1.ASN1Primitive) r7)
            if (r12 == 0) goto L_0x01d8
            goto L_0x01e3
        L_0x01d8:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r15)
            throw r0
        L_0x01de:
            r12.setBagAttribute(r13, r7)
            goto L_0x01e3
        L_0x01e2:
            r7 = 0
        L_0x01e3:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName
            boolean r12 = r13.equals((org.bouncycastle.asn1.ASN1Primitive) r12)
            if (r12 == 0) goto L_0x01f7
            org.bouncycastle.asn1.DERBMPString r7 = (org.bouncycastle.asn1.DERBMPString) r7
            java.lang.String r5 = r7.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r7 = r8.keys
            r7.put(r5, r4)
            goto L_0x0203
        L_0x01f7:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_localKeyId
            boolean r12 = r13.equals((org.bouncycastle.asn1.ASN1Primitive) r12)
            if (r12 == 0) goto L_0x0203
            r17 = r7
            org.bouncycastle.asn1.ASN1OctetString r17 = (org.bouncycastle.asn1.ASN1OctetString) r17
        L_0x0203:
            r7 = 0
            r12 = 1
            r13 = 0
            goto L_0x0197
        L_0x0207:
            r5 = 0
            r17 = 0
        L_0x020a:
            if (r17 == 0) goto L_0x0227
            java.lang.String r3 = new java.lang.String
            byte[] r7 = r17.getOctets()
            byte[] r7 = org.bouncycastle.util.encoders.Hex.encode(r7)
            r3.<init>(r7)
            if (r5 != 0) goto L_0x0221
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r5 = r8.keys
            r5.put(r3, r4)
            goto L_0x0264
        L_0x0221:
            java.util.Hashtable r4 = r8.localIds
            r4.put(r5, r3)
            goto L_0x0264
        L_0x0227:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r8.keys
            r3.put(r14, r4)
            r16 = 1
            goto L_0x0264
        L_0x022f:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = certBag
            boolean r4 = r4.equals((org.bouncycastle.asn1.ASN1Primitive) r5)
            if (r4 == 0) goto L_0x023f
            r11.addElement(r3)
            goto L_0x0264
        L_0x023f:
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "extra in data "
            java.lang.StringBuilder r5 = r5.append(r7)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = r3.getBagId()
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            r4.println(r5)
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.String r3 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r3)
            r4.println(r3)
        L_0x0264:
            int r2 = r2 + 1
            r7 = 0
            r12 = 1
            r13 = 0
            goto L_0x0158
        L_0x026b:
            r17 = r0
            r13 = r6
            goto L_0x04a5
        L_0x0270:
            r1 = r10[r6]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = encryptedData
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            if (r1 == 0) goto L_0x0460
            r1 = r10[r6]
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getContent()
            org.bouncycastle.asn1.pkcs.EncryptedData r1 = org.bouncycastle.asn1.pkcs.EncryptedData.getInstance(r1)
            r2 = 0
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = r1.getEncryptionAlgorithm()
            org.bouncycastle.asn1.ASN1OctetString r1 = r1.getContent()
            byte[] r7 = r1.getOctets()
            r1 = r22
            r4 = r24
            r5 = r0
            r13 = r6
            r6 = r7
            byte[] r1 = r1.cryptData(r2, r3, r4, r5, r6)
            org.bouncycastle.asn1.ASN1Primitive r1 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r1)
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            r12 = 0
        L_0x02a7:
            int r2 = r1.size()
            if (r12 == r2) goto L_0x045d
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r12)
            org.bouncycastle.asn1.pkcs.SafeBag r2 = org.bouncycastle.asn1.pkcs.SafeBag.getInstance(r2)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r2.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = certBag
            boolean r3 = r3.equals((org.bouncycastle.asn1.ASN1Primitive) r4)
            if (r3 == 0) goto L_0x02ca
            r11.addElement(r2)
            r17 = r0
            r21 = r1
            goto L_0x0455
        L_0x02ca:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r2.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = pkcs8ShroudedKeyBag
            boolean r3 = r3.equals((org.bouncycastle.asn1.ASN1Primitive) r4)
            if (r3 == 0) goto L_0x0385
            org.bouncycastle.asn1.ASN1Encodable r3 = r2.getBagValue()
            org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo r3 = org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(r3)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = r3.getEncryptionAlgorithm()
            byte[] r3 = r3.getEncryptedData()
            java.security.PrivateKey r3 = r8.unwrapKey(r4, r3, r9, r0)
            r4 = r3
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r4 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r4
            org.bouncycastle.asn1.ASN1Set r2 = r2.getBagAttributes()
            java.util.Enumeration r2 = r2.getObjects()
            r5 = 0
            r6 = 0
        L_0x02f7:
            boolean r7 = r2.hasMoreElements()
            if (r7 == 0) goto L_0x0364
            java.lang.Object r7 = r2.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r7 = (org.bouncycastle.asn1.ASN1Sequence) r7
            r17 = r0
            r0 = 0
            org.bouncycastle.asn1.ASN1Encodable r19 = r7.getObjectAt(r0)
            r0 = r19
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r0
            r21 = r1
            r1 = 1
            org.bouncycastle.asn1.ASN1Encodable r7 = r7.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Set r7 = (org.bouncycastle.asn1.ASN1Set) r7
            int r1 = r7.size()
            if (r1 <= 0) goto L_0x033f
            r1 = 0
            org.bouncycastle.asn1.ASN1Encodable r7 = r7.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Primitive r7 = (org.bouncycastle.asn1.ASN1Primitive) r7
            org.bouncycastle.asn1.ASN1Encodable r1 = r4.getBagAttribute(r0)
            if (r1 == 0) goto L_0x033b
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r7)
            if (r1 == 0) goto L_0x0335
            goto L_0x0340
        L_0x0335:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r15)
            throw r0
        L_0x033b:
            r4.setBagAttribute(r0, r7)
            goto L_0x0340
        L_0x033f:
            r7 = 0
        L_0x0340:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = pkcs_9_at_friendlyName
            boolean r1 = r0.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 == 0) goto L_0x0354
            org.bouncycastle.asn1.DERBMPString r7 = (org.bouncycastle.asn1.DERBMPString) r7
            java.lang.String r6 = r7.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r8.keys
            r0.put(r6, r3)
            goto L_0x035f
        L_0x0354:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = pkcs_9_at_localKeyId
            boolean r0 = r0.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r0 == 0) goto L_0x035f
            r5 = r7
            org.bouncycastle.asn1.ASN1OctetString r5 = (org.bouncycastle.asn1.ASN1OctetString) r5
        L_0x035f:
            r0 = r17
            r1 = r21
            goto L_0x02f7
        L_0x0364:
            r17 = r0
            r21 = r1
            java.lang.String r0 = new java.lang.String
            byte[] r1 = r5.getOctets()
            byte[] r1 = org.bouncycastle.util.encoders.Hex.encode(r1)
            r0.<init>(r1)
            if (r6 != 0) goto L_0x037e
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = r8.keys
            r1.put(r0, r3)
            goto L_0x0455
        L_0x037e:
            java.util.Hashtable r1 = r8.localIds
            r1.put(r6, r0)
            goto L_0x0455
        L_0x0385:
            r17 = r0
            r21 = r1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r2.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = keyBag
            boolean r0 = r0.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r0 == 0) goto L_0x0430
            org.bouncycastle.asn1.ASN1Encodable r0 = r2.getBagValue()
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(r0)
            java.security.PrivateKey r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(r0)
            r1 = r0
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r1 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r1
            org.bouncycastle.asn1.ASN1Set r2 = r2.getBagAttributes()
            java.util.Enumeration r2 = r2.getObjects()
            r3 = 0
            r4 = 0
        L_0x03ae:
            boolean r5 = r2.hasMoreElements()
            if (r5 == 0) goto L_0x0415
            java.lang.Object r5 = r2.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r5 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r5)
            r6 = 0
            org.bouncycastle.asn1.ASN1Encodable r7 = r5.getObjectAt(r6)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r7)
            r6 = 1
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r6)
            org.bouncycastle.asn1.ASN1Set r5 = org.bouncycastle.asn1.ASN1Set.getInstance(r5)
            int r6 = r5.size()
            if (r6 <= 0) goto L_0x03ae
            r6 = 0
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r6)
            org.bouncycastle.asn1.ASN1Primitive r5 = (org.bouncycastle.asn1.ASN1Primitive) r5
            org.bouncycastle.asn1.ASN1Encodable r6 = r1.getBagAttribute(r7)
            if (r6 == 0) goto L_0x03f2
            org.bouncycastle.asn1.ASN1Primitive r6 = r6.toASN1Primitive()
            boolean r6 = r6.equals((org.bouncycastle.asn1.ASN1Primitive) r5)
            if (r6 == 0) goto L_0x03ec
            goto L_0x03f5
        L_0x03ec:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r15)
            throw r0
        L_0x03f2:
            r1.setBagAttribute(r7, r5)
        L_0x03f5:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = pkcs_9_at_friendlyName
            boolean r6 = r7.equals((org.bouncycastle.asn1.ASN1Primitive) r6)
            if (r6 == 0) goto L_0x0409
            org.bouncycastle.asn1.DERBMPString r5 = (org.bouncycastle.asn1.DERBMPString) r5
            java.lang.String r4 = r5.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r5 = r8.keys
            r5.put(r4, r0)
            goto L_0x03ae
        L_0x0409:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = pkcs_9_at_localKeyId
            boolean r6 = r7.equals((org.bouncycastle.asn1.ASN1Primitive) r6)
            if (r6 == 0) goto L_0x03ae
            r3 = r5
            org.bouncycastle.asn1.ASN1OctetString r3 = (org.bouncycastle.asn1.ASN1OctetString) r3
            goto L_0x03ae
        L_0x0415:
            java.lang.String r1 = new java.lang.String
            byte[] r2 = r3.getOctets()
            byte[] r2 = org.bouncycastle.util.encoders.Hex.encode(r2)
            r1.<init>(r2)
            if (r4 != 0) goto L_0x042a
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r2 = r8.keys
            r2.put(r1, r0)
            goto L_0x0455
        L_0x042a:
            java.util.Hashtable r0 = r8.localIds
            r0.put(r4, r1)
            goto L_0x0455
        L_0x0430:
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "extra in encryptedData "
            java.lang.StringBuilder r1 = r1.append(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r2.getBagId()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r1 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r2)
            r0.println(r1)
        L_0x0455:
            int r12 = r12 + 1
            r0 = r17
            r1 = r21
            goto L_0x02a7
        L_0x045d:
            r17 = r0
            goto L_0x04a5
        L_0x0460:
            r17 = r0
            r13 = r6
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "extra "
            java.lang.StringBuilder r1 = r1.append(r2)
            r3 = r10[r13]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r3.getContentType()
            java.lang.String r3 = r3.getId()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r10[r13]
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getContent()
            java.lang.String r2 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
        L_0x04a5:
            int r6 = r13 + 1
            r0 = r17
            r7 = 0
            r12 = 1
            r13 = 0
            goto L_0x012b
        L_0x04ae:
            r16 = 0
        L_0x04b0:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r1 = 0
            r0.<init>()
            r8.certs = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r8.chainCerts = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r8.keyCerts = r0
            r12 = 0
        L_0x04c7:
            int r0 = r11.size()
            if (r12 == r0) goto L_0x05fe
            java.lang.Object r0 = r11.elementAt(r12)
            org.bouncycastle.asn1.pkcs.SafeBag r0 = (org.bouncycastle.asn1.pkcs.SafeBag) r0
            org.bouncycastle.asn1.ASN1Encodable r2 = r0.getBagValue()
            org.bouncycastle.asn1.pkcs.CertBag r2 = org.bouncycastle.asn1.pkcs.CertBag.getInstance(r2)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r2.getCertId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = x509Certificate
            boolean r3 = r3.equals((org.bouncycastle.asn1.ASN1Primitive) r4)
            if (r3 == 0) goto L_0x05e1
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x05d6 }
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getCertValue()     // Catch:{ Exception -> 0x05d6 }
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2     // Catch:{ Exception -> 0x05d6 }
            byte[] r2 = r2.getOctets()     // Catch:{ Exception -> 0x05d6 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x05d6 }
            java.security.cert.CertificateFactory r2 = r8.certFact     // Catch:{ Exception -> 0x05d6 }
            java.security.cert.Certificate r2 = r2.generateCertificate(r3)     // Catch:{ Exception -> 0x05d6 }
            org.bouncycastle.asn1.ASN1Set r3 = r0.getBagAttributes()
            if (r3 == 0) goto L_0x0577
            org.bouncycastle.asn1.ASN1Set r0 = r0.getBagAttributes()
            java.util.Enumeration r0 = r0.getObjects()
            r3 = r1
            r4 = r3
        L_0x050c:
            boolean r5 = r0.hasMoreElements()
            if (r5 == 0) goto L_0x0574
            java.lang.Object r5 = r0.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r5 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r5)
            r6 = 0
            org.bouncycastle.asn1.ASN1Encodable r7 = r5.getObjectAt(r6)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r7)
            r9 = 1
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r9)
            org.bouncycastle.asn1.ASN1Set r5 = org.bouncycastle.asn1.ASN1Set.getInstance(r5)
            int r10 = r5.size()
            if (r10 <= 0) goto L_0x050c
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r6)
            org.bouncycastle.asn1.ASN1Primitive r5 = (org.bouncycastle.asn1.ASN1Primitive) r5
            boolean r10 = r2 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r10 == 0) goto L_0x0559
            r10 = r2
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r10 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r10
            org.bouncycastle.asn1.ASN1Encodable r13 = r10.getBagAttribute(r7)
            if (r13 == 0) goto L_0x0556
            org.bouncycastle.asn1.ASN1Primitive r10 = r13.toASN1Primitive()
            boolean r10 = r10.equals((org.bouncycastle.asn1.ASN1Primitive) r5)
            if (r10 == 0) goto L_0x0550
            goto L_0x0559
        L_0x0550:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r15)
            throw r0
        L_0x0556:
            r10.setBagAttribute(r7, r5)
        L_0x0559:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_friendlyName
            boolean r10 = r7.equals((org.bouncycastle.asn1.ASN1Primitive) r10)
            if (r10 == 0) goto L_0x0568
            org.bouncycastle.asn1.DERBMPString r5 = (org.bouncycastle.asn1.DERBMPString) r5
            java.lang.String r3 = r5.getString()
            goto L_0x050c
        L_0x0568:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_localKeyId
            boolean r7 = r7.equals((org.bouncycastle.asn1.ASN1Primitive) r10)
            if (r7 == 0) goto L_0x050c
            r4 = r5
            org.bouncycastle.asn1.ASN1OctetString r4 = (org.bouncycastle.asn1.ASN1OctetString) r4
            goto L_0x050c
        L_0x0574:
            r6 = 0
            r9 = 1
            goto L_0x057b
        L_0x0577:
            r6 = 0
            r9 = 1
            r3 = r1
            r4 = r3
        L_0x057b:
            java.util.Hashtable r0 = r8.chainCerts
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r5 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r7 = r2.getPublicKey()
            r5.<init>((java.security.PublicKey) r7)
            r0.put(r5, r2)
            if (r16 == 0) goto L_0x05b7
            java.util.Hashtable r0 = r8.keyCerts
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x05d2
            java.lang.String r0 = new java.lang.String
            java.security.PublicKey r3 = r2.getPublicKey()
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r3 = r8.createSubjectKeyId(r3)
            byte[] r3 = r3.getKeyIdentifier()
            byte[] r3 = org.bouncycastle.util.encoders.Hex.encode(r3)
            r0.<init>(r3)
            java.util.Hashtable r3 = r8.keyCerts
            r3.put(r0, r2)
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r2 = r8.keys
            java.lang.Object r3 = r2.remove(r14)
            r2.put(r0, r3)
            goto L_0x05d2
        L_0x05b7:
            if (r4 == 0) goto L_0x05cb
            java.lang.String r0 = new java.lang.String
            byte[] r4 = r4.getOctets()
            byte[] r4 = org.bouncycastle.util.encoders.Hex.encode(r4)
            r0.<init>(r4)
            java.util.Hashtable r4 = r8.keyCerts
            r4.put(r0, r2)
        L_0x05cb:
            if (r3 == 0) goto L_0x05d2
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r8.certs
            r0.put(r3, r2)
        L_0x05d2:
            int r12 = r12 + 1
            goto L_0x04c7
        L_0x05d6:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x05e1:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Unsupported certificate type: "
            java.lang.StringBuilder r1 = r1.append(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r2.getCertId()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x05fe:
            return
        L_0x05ff:
            r0 = move-exception
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r0 = r0.getMessage()
            r1.<init>(r0)
            throw r1
        L_0x060a:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "stream does not represent a PKCS12 key store"
            r0.<init>(r1)
            goto L_0x0613
        L_0x0612:
            throw r0
        L_0x0613:
            goto L_0x0612
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineLoad(java.io.InputStream, char[]):void");
    }

    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        if (this.keys.get(str) == null) {
            this.certs.put(str, certificate);
            this.chainCerts.put(new CertId(certificate.getPublicKey()), certificate);
            return;
        }
        throw new KeyStoreException("There is a key entry with the name " + str + ".");
    }

    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws KeyStoreException {
        boolean z = key instanceof PrivateKey;
        if (!z) {
            throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
        } else if (!z || certificateArr != null) {
            if (this.keys.get(str) != null) {
                engineDeleteEntry(str);
            }
            this.keys.put(str, key);
            if (certificateArr != null) {
                this.certs.put(str, certificateArr[0]);
                for (int i = 0; i != certificateArr.length; i++) {
                    this.chainCerts.put(new CertId(certificateArr[i].getPublicKey()), certificateArr[i]);
                }
            }
        } else {
            throw new KeyStoreException("no certificate chain for private key");
        }
    }

    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        throw new RuntimeException("operation not supported");
    }

    public int engineSize() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys2 = this.certs.keys();
        while (keys2.hasMoreElements()) {
            hashtable.put(keys2.nextElement(), "cert");
        }
        Enumeration keys3 = this.keys.keys();
        while (keys3.hasMoreElements()) {
            String str = (String) keys3.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    public void engineStore(OutputStream outputStream, char[] cArr) throws IOException {
        doStore(outputStream, cArr, false);
    }

    public void engineStore(KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        PKCS12StoreParameter pKCS12StoreParameter;
        char[] cArr;
        if (loadStoreParameter != null) {
            boolean z = loadStoreParameter instanceof PKCS12StoreParameter;
            if (z || (loadStoreParameter instanceof JDKPKCS12StoreParameter)) {
                if (z) {
                    pKCS12StoreParameter = (PKCS12StoreParameter) loadStoreParameter;
                } else {
                    JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (JDKPKCS12StoreParameter) loadStoreParameter;
                    pKCS12StoreParameter = new PKCS12StoreParameter(jDKPKCS12StoreParameter.getOutputStream(), loadStoreParameter.getProtectionParameter(), jDKPKCS12StoreParameter.isUseDEREncoding());
                }
                KeyStore.ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
                if (protectionParameter == null) {
                    cArr = null;
                } else if (protectionParameter instanceof KeyStore.PasswordProtection) {
                    cArr = ((KeyStore.PasswordProtection) protectionParameter).getPassword();
                } else {
                    throw new IllegalArgumentException("No support for protection parameter of type " + protectionParameter.getClass().getName());
                }
                doStore(pKCS12StoreParameter.getOutputStream(), cArr, pKCS12StoreParameter.isForDEREncoding());
                return;
            }
            throw new IllegalArgumentException("No support for 'param' of type " + loadStoreParameter.getClass().getName());
        }
        throw new IllegalArgumentException("'param' arg cannot be null");
    }

    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    /* access modifiers changed from: protected */
    public PrivateKey unwrapKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z) throws IOException {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        try {
            if (algorithm.mo1052on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
                PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
                PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), validateIterationCount(instance.getIterations()));
                Cipher createCipher = this.helper.createCipher(algorithm.getId());
                createCipher.init(4, new PKCS12Key(cArr, z), pBEParameterSpec);
                return (PrivateKey) createCipher.unwrap(bArr, "", 2);
            } else if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_PBES2)) {
                return (PrivateKey) createCipher(4, cArr, algorithmIdentifier).unwrap(bArr, "", 2);
            } else {
                throw new IOException("exception unwrapping private key - cannot recognise: " + algorithm);
            }
        } catch (Exception e) {
            throw new IOException("exception unwrapping private key - " + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] wrapKey(String str, Key key, PKCS12PBEParams pKCS12PBEParams, char[] cArr) throws IOException {
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(str);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            Cipher createCipher = this.helper.createCipher(str);
            createCipher.init(3, createSecretKeyFactory.generateSecret(pBEKeySpec), pBEParameterSpec);
            return createCipher.wrap(key);
        } catch (Exception e) {
            throw new IOException("exception encrypting data - " + e.toString());
        }
    }
}

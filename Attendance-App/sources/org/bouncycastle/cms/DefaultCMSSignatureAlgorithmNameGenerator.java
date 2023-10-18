package org.bouncycastle.cms;

import com.itextpdf.signatures.DigestAlgorithms;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.bsi.BSIObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.eac.EACObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.p006gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;

public class DefaultCMSSignatureAlgorithmNameGenerator implements CMSSignatureAlgorithmNameGenerator {
    private final Map digestAlgs;
    private final Map encryptionAlgs;

    public DefaultCMSSignatureAlgorithmNameGenerator() {
        HashMap hashMap = new HashMap();
        this.encryptionAlgs = hashMap;
        HashMap hashMap2 = new HashMap();
        this.digestAlgs = hashMap2;
        addEntries(NISTObjectIdentifiers.dsa_with_sha224, "SHA224", "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha256, "SHA256", "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha384, "SHA384", "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha512, "SHA512", "DSA");
        addEntries(NISTObjectIdentifiers.id_dsa_with_sha3_224, "SHA3-224", "DSA");
        addEntries(NISTObjectIdentifiers.id_dsa_with_sha3_256, "SHA3-256", "DSA");
        addEntries(NISTObjectIdentifiers.id_dsa_with_sha3_384, "SHA3-384", "DSA");
        addEntries(NISTObjectIdentifiers.id_dsa_with_sha3_512, "SHA3-512", "DSA");
        addEntries(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_224, "SHA3-224", "RSA");
        addEntries(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_256, "SHA3-256", "RSA");
        addEntries(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_384, "SHA3-384", "RSA");
        addEntries(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_512, "SHA3-512", "RSA");
        addEntries(NISTObjectIdentifiers.id_ecdsa_with_sha3_224, "SHA3-224", "ECDSA");
        addEntries(NISTObjectIdentifiers.id_ecdsa_with_sha3_256, "SHA3-256", "ECDSA");
        addEntries(NISTObjectIdentifiers.id_ecdsa_with_sha3_384, "SHA3-384", "ECDSA");
        addEntries(NISTObjectIdentifiers.id_ecdsa_with_sha3_512, "SHA3-512", "ECDSA");
        addEntries(OIWObjectIdentifiers.dsaWithSHA1, "SHA1", "DSA");
        addEntries(OIWObjectIdentifiers.md4WithRSA, "MD4", "RSA");
        addEntries(OIWObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
        addEntries(OIWObjectIdentifiers.md5WithRSA, "MD5", "RSA");
        addEntries(OIWObjectIdentifiers.sha1WithRSA, "SHA1", "RSA");
        addEntries(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2", "RSA");
        addEntries(PKCSObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
        addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5", "RSA");
        addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1", "RSA");
        addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224", "RSA");
        addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256", "RSA");
        addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384", "RSA");
        addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512", "RSA");
        addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128", "RSA");
        addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, DigestAlgorithms.RIPEMD160, "RSA");
        addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256", "RSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1", "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224", "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256", "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384", "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512", "ECDSA");
        addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1", "DSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1", "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224", "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256", "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384", "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512", "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "SHA1", "RSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "SHA256", "RSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "SHA1", "RSAandMGF1");
        addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "SHA256", "RSAandMGF1");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA1, "SHA1", "PLAIN-ECDSA");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA224, "SHA224", "PLAIN-ECDSA");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA256, "SHA256", "PLAIN-ECDSA");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA384, "SHA384", "PLAIN-ECDSA");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA512, "SHA512", "PLAIN-ECDSA");
        addEntries(BSIObjectIdentifiers.ecdsa_plain_RIPEMD160, DigestAlgorithms.RIPEMD160, "PLAIN-ECDSA");
        addEntries(GMObjectIdentifiers.sm2sign_with_sha256, "SHA256", "SM2");
        ASN1ObjectIdentifier aSN1ObjectIdentifier = GMObjectIdentifiers.sm2sign_with_sm3;
        Object obj = DigestAlgorithms.RIPEMD160;
        addEntries(aSN1ObjectIdentifier, "SM3", "SM2");
        hashMap.put(X9ObjectIdentifiers.id_dsa, "DSA");
        hashMap.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        hashMap.put(TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, "RSA");
        hashMap.put(X509ObjectIdentifiers.id_ea_rsa, "RSA");
        hashMap.put(PKCSObjectIdentifiers.id_RSASSA_PSS, "RSAandMGF1");
        hashMap.put(CryptoProObjectIdentifiers.gostR3410_94, "GOST3410");
        hashMap.put(CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410");
        hashMap.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.6.2"), "ECGOST3410");
        hashMap.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.1.5"), "GOST3410");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256, "ECGOST3410-2012-256");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512, "ECGOST3410-2012-512");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "ECGOST3410");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3410");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_256, "ECGOST3410-2012-256");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_512, "ECGOST3410-2012-512");
        hashMap2.put(PKCSObjectIdentifiers.md2, "MD2");
        hashMap2.put(PKCSObjectIdentifiers.md4, "MD4");
        hashMap2.put(PKCSObjectIdentifiers.md5, "MD5");
        hashMap2.put(OIWObjectIdentifiers.idSHA1, "SHA1");
        hashMap2.put(NISTObjectIdentifiers.id_sha224, "SHA224");
        hashMap2.put(NISTObjectIdentifiers.id_sha256, "SHA256");
        hashMap2.put(NISTObjectIdentifiers.id_sha384, "SHA384");
        hashMap2.put(NISTObjectIdentifiers.id_sha512, "SHA512");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_224, "SHA3-224");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_256, "SHA3-256");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_384, "SHA3-384");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_512, "SHA3-512");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd160, obj);
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
        hashMap2.put(CryptoProObjectIdentifiers.gostR3411, "GOST3411");
        hashMap2.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.2.1"), "GOST3411");
        hashMap2.put(RosstandartObjectIdentifiers.id_tc26_gost_3411_12_256, "GOST3411-2012-256");
        hashMap2.put(RosstandartObjectIdentifiers.id_tc26_gost_3411_12_512, "GOST3411-2012-512");
        hashMap2.put(GMObjectIdentifiers.sm3, "SM3");
    }

    private void addEntries(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, String str2) {
        this.digestAlgs.put(aSN1ObjectIdentifier, str);
        this.encryptionAlgs.put(aSN1ObjectIdentifier, str2);
    }

    private String getDigestAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) this.digestAlgs.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    private String getEncryptionAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) this.encryptionAlgs.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    public String getSignatureName(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        String digestAlgName = getDigestAlgName(algorithmIdentifier2.getAlgorithm());
        return (!digestAlgName.equals(algorithmIdentifier2.getAlgorithm().getId()) ? new StringBuilder().append(digestAlgName) : new StringBuilder().append(getDigestAlgName(algorithmIdentifier.getAlgorithm()))).append("with").append(getEncryptionAlgName(algorithmIdentifier2.getAlgorithm())).toString();
    }

    /* access modifiers changed from: protected */
    public void setSigningDigestAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        this.digestAlgs.put(aSN1ObjectIdentifier, str);
    }

    /* access modifiers changed from: protected */
    public void setSigningEncryptionAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        this.encryptionAlgs.put(aSN1ObjectIdentifier, str);
    }
}

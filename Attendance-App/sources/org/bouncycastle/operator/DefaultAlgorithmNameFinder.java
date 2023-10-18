package org.bouncycastle.operator;

import com.itextpdf.signatures.DigestAlgorithms;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.bsi.BSIObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.eac.EACObjectIdentifiers;
import org.bouncycastle.asn1.gnu.GNUObjectIdentifiers;
import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DefaultAlgorithmNameFinder implements AlgorithmNameFinder {
    private static final Map algorithms;

    static {
        HashMap hashMap = new HashMap();
        algorithms = hashMap;
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_RIPEMD160, "RIPEMD160WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA1, "SHA1WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA224, "SHA224WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA256, "SHA256WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA384, "SHA384WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA512, "SHA512WITHPLAIN-ECDSA");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410-2001");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410-94");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411, "GOST3411");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_256, "GOST3411WITHECGOST3410-2012-256");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_512, "GOST3411WITHECGOST3410-2012-512");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512WITHCVC-ECDSA");
        hashMap.put(NISTObjectIdentifiers.id_sha224, "SHA224");
        hashMap.put(NISTObjectIdentifiers.id_sha256, "SHA256");
        hashMap.put(NISTObjectIdentifiers.id_sha384, "SHA384");
        hashMap.put(NISTObjectIdentifiers.id_sha512, "SHA512");
        hashMap.put(NISTObjectIdentifiers.id_sha3_224, "SHA3-224");
        hashMap.put(NISTObjectIdentifiers.id_sha3_256, "SHA3-256");
        hashMap.put(NISTObjectIdentifiers.id_sha3_384, "SHA3-384");
        hashMap.put(NISTObjectIdentifiers.id_sha3_512, "SHA3-512");
        hashMap.put(OIWObjectIdentifiers.elGamalAlgorithm, "ELGAMAL");
        hashMap.put(OIWObjectIdentifiers.idSHA1, "SHA1");
        hashMap.put(OIWObjectIdentifiers.md5WithRSA, "MD5WITHRSA");
        hashMap.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.id_RSAES_OAEP, "RSAOAEP");
        hashMap.put(PKCSObjectIdentifiers.id_RSASSA_PSS, "RSAPSS");
        hashMap.put(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.md5, "MD5");
        hashMap.put(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        hashMap.put(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        hashMap.put(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_224, "SHA3-224WITHRSA");
        hashMap.put(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_256, "SHA3-256WITHRSA");
        hashMap.put(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_384, "SHA3-384WITHRSA");
        hashMap.put(NISTObjectIdentifiers.id_rsassa_pkcs1_v1_5_with_sha3_512, "SHA3-512WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
        hashMap.put(TeleTrusTObjectIdentifiers.ripemd160, DigestAlgorithms.RIPEMD160);
        hashMap.put(TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, "RIPEMD160WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256WITHRSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "ECDSAWITHSHA1");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        hashMap.put(NISTObjectIdentifiers.id_ecdsa_with_sha3_224, "SHA3-224WITHECDSA");
        hashMap.put(NISTObjectIdentifiers.id_ecdsa_with_sha3_256, "SHA3-256WITHECDSA");
        hashMap.put(NISTObjectIdentifiers.id_ecdsa_with_sha3_384, "SHA3-384WITHECDSA");
        hashMap.put(NISTObjectIdentifiers.id_ecdsa_with_sha3_512, "SHA3-512WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha384, "SHA384WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha512, "SHA512WITHDSA");
        hashMap.put(NISTObjectIdentifiers.id_dsa_with_sha3_224, "SHA3-224WITHDSA");
        hashMap.put(NISTObjectIdentifiers.id_dsa_with_sha3_256, "SHA3-256WITHDSA");
        hashMap.put(NISTObjectIdentifiers.id_dsa_with_sha3_384, "SHA3-384WITHDSA");
        hashMap.put(NISTObjectIdentifiers.id_dsa_with_sha3_512, "SHA3-512WITHDSA");
        hashMap.put(GNUObjectIdentifiers.Tiger_192, "Tiger");
        hashMap.put(PKCSObjectIdentifiers.RC2_CBC, "RC2/CBC");
        hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE-3KEY/CBC");
        hashMap.put(NISTObjectIdentifiers.id_aes128_ECB, "AES-128/ECB");
        hashMap.put(NISTObjectIdentifiers.id_aes192_ECB, "AES-192/ECB");
        hashMap.put(NISTObjectIdentifiers.id_aes256_ECB, "AES-256/ECB");
        hashMap.put(NISTObjectIdentifiers.id_aes128_CBC, "AES-128/CBC");
        hashMap.put(NISTObjectIdentifiers.id_aes192_CBC, "AES-192/CBC");
        hashMap.put(NISTObjectIdentifiers.id_aes256_CBC, "AES-256/CBC");
        hashMap.put(NISTObjectIdentifiers.id_aes128_CFB, "AES-128/CFB");
        hashMap.put(NISTObjectIdentifiers.id_aes192_CFB, "AES-192/CFB");
        hashMap.put(NISTObjectIdentifiers.id_aes256_CFB, "AES-256/CFB");
        hashMap.put(NISTObjectIdentifiers.id_aes128_OFB, "AES-128/OFB");
        hashMap.put(NISTObjectIdentifiers.id_aes192_OFB, "AES-192/OFB");
        hashMap.put(NISTObjectIdentifiers.id_aes256_OFB, "AES-256/OFB");
        hashMap.put(NTTObjectIdentifiers.id_camellia128_cbc, "CAMELLIA-128/CBC");
        hashMap.put(NTTObjectIdentifiers.id_camellia192_cbc, "CAMELLIA-192/CBC");
        hashMap.put(NTTObjectIdentifiers.id_camellia256_cbc, "CAMELLIA-256/CBC");
        hashMap.put(KISAObjectIdentifiers.id_seedCBC, "SEED/CBC");
        hashMap.put(MiscObjectIdentifiers.as_sys_sec_alg_ideaCBC, "IDEA/CBC");
        hashMap.put(MiscObjectIdentifiers.cast5CBC, "CAST5/CBC");
        hashMap.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_ECB, "Blowfish/ECB");
        hashMap.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC, "Blowfish/CBC");
        hashMap.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CFB, "Blowfish/CFB");
        hashMap.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_OFB, "Blowfish/OFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_128_ECB, "Serpent-128/ECB");
        hashMap.put(GNUObjectIdentifiers.Serpent_128_CBC, "Serpent-128/CBC");
        hashMap.put(GNUObjectIdentifiers.Serpent_128_CFB, "Serpent-128/CFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_128_OFB, "Serpent-128/OFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_192_ECB, "Serpent-192/ECB");
        hashMap.put(GNUObjectIdentifiers.Serpent_192_CBC, "Serpent-192/CBC");
        hashMap.put(GNUObjectIdentifiers.Serpent_192_CFB, "Serpent-192/CFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_192_OFB, "Serpent-192/OFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_256_ECB, "Serpent-256/ECB");
        hashMap.put(GNUObjectIdentifiers.Serpent_256_CBC, "Serpent-256/CBC");
        hashMap.put(GNUObjectIdentifiers.Serpent_256_CFB, "Serpent-256/CFB");
        hashMap.put(GNUObjectIdentifiers.Serpent_256_OFB, "Serpent-256/OFB");
    }

    public String getAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) algorithms.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    public String getAlgorithmName(AlgorithmIdentifier algorithmIdentifier) {
        return getAlgorithmName(algorithmIdentifier.getAlgorithm());
    }

    public boolean hasAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return algorithms.containsKey(aSN1ObjectIdentifier);
    }
}

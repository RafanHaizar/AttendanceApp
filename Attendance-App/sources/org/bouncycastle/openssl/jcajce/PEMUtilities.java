package org.bouncycastle.openssl.jcajce;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.openssl.EncryptionException;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.util.Integers;

class PEMUtilities {
    private static final Map KEYSIZES;
    private static final Set PKCS5_SCHEME_1;
    private static final Set PKCS5_SCHEME_2;
    private static final Map PRFS;
    private static final Map PRFS_SALT;

    static {
        HashMap hashMap = new HashMap();
        KEYSIZES = hashMap;
        HashSet hashSet = new HashSet();
        PKCS5_SCHEME_1 = hashSet;
        HashSet hashSet2 = new HashSet();
        PKCS5_SCHEME_2 = hashSet2;
        HashMap hashMap2 = new HashMap();
        PRFS = hashMap2;
        HashMap hashMap3 = new HashMap();
        PRFS_SALT = hashMap3;
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
        hashSet2.add(PKCSObjectIdentifiers.id_PBES2);
        hashSet2.add(PKCSObjectIdentifiers.des_EDE3_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes128_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes192_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes256_CBC);
        hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), Integers.valueOf(192));
        hashMap.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), Integers.valueOf(128));
        hashMap.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), Integers.valueOf(192));
        hashMap.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), Integers.valueOf(256));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4.getId(), Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, Integers.valueOf(40));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, Integers.valueOf(192));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC, Integers.valueOf(40));
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA1, "PBKDF2withHMACSHA1");
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA256, "PBKDF2withHMACSHA256");
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA512, "PBKDF2withHMACSHA512");
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA224, "PBKDF2withHMACSHA224");
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA384, "PBKDF2withHMACSHA384");
        hashMap2.put(NISTObjectIdentifiers.id_hmacWithSHA3_224, "PBKDF2withHMACSHA3-224");
        hashMap2.put(NISTObjectIdentifiers.id_hmacWithSHA3_256, "PBKDF2withHMACSHA3-256");
        hashMap2.put(NISTObjectIdentifiers.id_hmacWithSHA3_384, "PBKDF2withHMACSHA3-384");
        hashMap2.put(NISTObjectIdentifiers.id_hmacWithSHA3_512, "PBKDF2withHMACSHA3-512");
        hashMap2.put(CryptoProObjectIdentifiers.gostR3411Hmac, "PBKDF2withHMACGOST3411");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA1, Integers.valueOf(20));
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA256, Integers.valueOf(32));
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA512, Integers.valueOf(64));
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA224, Integers.valueOf(28));
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA384, Integers.valueOf(48));
        hashMap3.put(NISTObjectIdentifiers.id_hmacWithSHA3_224, Integers.valueOf(28));
        hashMap3.put(NISTObjectIdentifiers.id_hmacWithSHA3_256, Integers.valueOf(32));
        hashMap3.put(NISTObjectIdentifiers.id_hmacWithSHA3_384, Integers.valueOf(48));
        hashMap3.put(NISTObjectIdentifiers.id_hmacWithSHA3_512, Integers.valueOf(64));
        hashMap3.put(CryptoProObjectIdentifiers.gostR3411Hmac, Integers.valueOf(32));
    }

    PEMUtilities() {
    }

    static byte[] crypt(boolean z, JcaJceHelper jcaJceHelper, byte[] bArr, char[] cArr, String str, byte[] bArr2) throws PEMException {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        SecretKey secretKey;
        byte[] bArr3;
        AlgorithmParameterSpec rC2ParameterSpec;
        JcaJceHelper jcaJceHelper2 = jcaJceHelper;
        char[] cArr2 = cArr;
        String str7 = str;
        byte[] bArr4 = bArr2;
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(bArr4);
        if (str7.endsWith("-CFB")) {
            str3 = "CFB";
            str2 = "NoPadding";
        } else {
            str3 = "CBC";
            str2 = "PKCS5Padding";
        }
        if (str7.endsWith("-ECB") || "DES-EDE".equals(str7) || "DES-EDE3".equals(str7)) {
            str3 = "ECB";
            ivParameterSpec = null;
        }
        AlgorithmParameterSpec algorithmParameterSpec = ivParameterSpec;
        if (str7.endsWith("-OFB")) {
            str5 = "OFB";
            str4 = "NoPadding";
        } else {
            str4 = str2;
            str5 = str3;
        }
        int i = 1;
        if (str7.startsWith("DES-EDE")) {
            str6 = "DESede";
            secretKey = getKey(jcaJceHelper, cArr, str6, 24, bArr2, !str7.startsWith("DES-EDE3"));
        } else if (str7.startsWith("DES-")) {
            str6 = "DES";
            secretKey = getKey(jcaJceHelper, cArr2, str6, 8, bArr4);
        } else if (str7.startsWith("BF-")) {
            str6 = "Blowfish";
            secretKey = getKey(jcaJceHelper, cArr2, str6, 16, bArr4);
        } else {
            int i2 = 128;
            if (str7.startsWith("RC2-")) {
                if (str7.startsWith("RC2-40-")) {
                    i2 = 40;
                } else if (str7.startsWith("RC2-64-")) {
                    i2 = 64;
                }
                str6 = "RC2";
                secretKey = getKey(jcaJceHelper, cArr2, str6, i2 / 8, bArr4);
                if (algorithmParameterSpec != null) {
                    rC2ParameterSpec = new RC2ParameterSpec(i2, bArr4);
                }
                algorithmParameterSpec = rC2ParameterSpec;
            } else if (str7.startsWith("AES-")) {
                if (bArr4.length > 8) {
                    bArr3 = new byte[8];
                    System.arraycopy(bArr4, 0, bArr3, 0, 8);
                } else {
                    bArr3 = bArr4;
                }
                if (!str7.startsWith("AES-128-")) {
                    if (str7.startsWith("AES-192-")) {
                        i2 = 192;
                    } else if (str7.startsWith("AES-256-")) {
                        i2 = 256;
                    } else {
                        throw new EncryptionException("unknown AES encryption with private key");
                    }
                }
                str6 = "AES";
                secretKey = getKey(jcaJceHelper, cArr2, str6, i2 / 8, bArr3);
            } else {
                throw new EncryptionException("unknown encryption with private key");
            }
        }
        try {
            Cipher createCipher = jcaJceHelper.createCipher(str6 + "/" + str5 + "/" + str4);
            if (!z) {
                i = 2;
            }
            if (algorithmParameterSpec == null) {
                createCipher.init(i, secretKey);
            } else {
                createCipher.init(i, secretKey, algorithmParameterSpec);
            }
            byte[] bArr5 = bArr;
            return createCipher.doFinal(bArr);
        } catch (Exception e) {
            throw new EncryptionException("exception using cipher - please check password and data.", e);
        }
    }

    public static SecretKey generateSecretKeyForPKCS5Scheme2(JcaJceHelper jcaJceHelper, String str, char[] cArr, byte[] bArr, int i) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(jcaJceHelper.createSecretKeyFactory("PBKDF2with8BIT").generateSecret(new PBEKeySpec(cArr, bArr, i, getKeySize(str))).getEncoded(), str);
    }

    public static SecretKey generateSecretKeyForPKCS5Scheme2(JcaJceHelper jcaJceHelper, String str, char[] cArr, byte[] bArr, int i, AlgorithmIdentifier algorithmIdentifier) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String str2 = (String) PRFS.get(algorithmIdentifier.getAlgorithm());
        if (str2 != null) {
            return new SecretKeySpec(jcaJceHelper.createSecretKeyFactory(str2).generateSecret(new PBEKeySpec(cArr, bArr, i, getKeySize(str))).getEncoded(), str);
        }
        throw new NoSuchAlgorithmException("unknown PRF in PKCS#2: " + algorithmIdentifier.getAlgorithm());
    }

    private static SecretKey getKey(JcaJceHelper jcaJceHelper, char[] cArr, String str, int i, byte[] bArr) throws PEMException {
        return getKey(jcaJceHelper, cArr, str, i, bArr, false);
    }

    private static SecretKey getKey(JcaJceHelper jcaJceHelper, char[] cArr, String str, int i, byte[] bArr, boolean z) throws PEMException {
        try {
            byte[] encoded = jcaJceHelper.createSecretKeyFactory("PBKDF-OpenSSL").generateSecret(new PBEKeySpec(cArr, bArr, 1, i * 8)).getEncoded();
            if (z && encoded.length >= 24) {
                System.arraycopy(encoded, 0, encoded, 16, 8);
            }
            return new SecretKeySpec(encoded, str);
        } catch (GeneralSecurityException e) {
            throw new PEMException("Unable to create OpenSSL PBDKF: " + e.getMessage(), e);
        }
    }

    static int getKeySize(String str) {
        Map map = KEYSIZES;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        throw new IllegalStateException("no key size for algorithm: " + str);
    }

    static int getSaltSize(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Map map = PRFS_SALT;
        if (map.containsKey(aSN1ObjectIdentifier)) {
            return ((Integer) map.get(aSN1ObjectIdentifier)).intValue();
        }
        throw new IllegalStateException("no salt size for algorithm: " + aSN1ObjectIdentifier);
    }

    static boolean isHmacSHA1(AlgorithmIdentifier algorithmIdentifier) {
        return algorithmIdentifier == null || algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_hmacWithSHA1);
    }

    public static boolean isPKCS12(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return aSN1ObjectIdentifier.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
    }

    static boolean isPKCS5Scheme1(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_1.contains(aSN1ObjectIdentifier);
    }

    static boolean isPKCS5Scheme2(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_2.contains(aSN1ObjectIdentifier);
    }
}

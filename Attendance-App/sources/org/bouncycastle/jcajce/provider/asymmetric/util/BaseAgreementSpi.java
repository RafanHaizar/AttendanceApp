package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.gnu.GNUObjectIdentifiers;
import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.agreement.kdf.DHKDFParameters;
import org.bouncycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;

public abstract class BaseAgreementSpi extends KeyAgreementSpi {
    private static final Map<String, ASN1ObjectIdentifier> defaultOids;
    private static final Hashtable des;
    private static final Map<String, Integer> keySizes;
    private static final Map<String, String> nameTable;
    private static final Hashtable oids;
    protected final String kaAlgorithm;
    protected final DerivationFunction kdf;
    protected byte[] ukmParameters;

    static {
        HashMap hashMap = new HashMap();
        defaultOids = hashMap;
        HashMap hashMap2 = new HashMap();
        keySizes = hashMap2;
        HashMap hashMap3 = new HashMap();
        nameTable = hashMap3;
        Hashtable hashtable = new Hashtable();
        oids = hashtable;
        Hashtable hashtable2 = new Hashtable();
        des = hashtable2;
        Integer valueOf = Integers.valueOf(64);
        Integer valueOf2 = Integers.valueOf(128);
        Integer valueOf3 = Integers.valueOf(192);
        Integer valueOf4 = Integers.valueOf(256);
        hashMap2.put("DES", valueOf);
        hashMap2.put("DESEDE", valueOf3);
        hashMap2.put("BLOWFISH", valueOf2);
        hashMap2.put("AES", valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_ECB.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_ECB.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_ECB.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_CFB.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_CFB.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_CFB.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_OFB.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_OFB.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_OFB.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_wrap.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_wrap.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_wrap.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_CCM.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_CCM.getId(), valueOf4);
        hashMap2.put(NISTObjectIdentifiers.id_aes128_GCM.getId(), valueOf2);
        hashMap2.put(NISTObjectIdentifiers.id_aes192_GCM.getId(), valueOf3);
        hashMap2.put(NISTObjectIdentifiers.id_aes256_GCM.getId(), valueOf4);
        hashMap2.put(NTTObjectIdentifiers.id_camellia128_wrap.getId(), valueOf2);
        hashMap2.put(NTTObjectIdentifiers.id_camellia192_wrap.getId(), valueOf3);
        hashMap2.put(NTTObjectIdentifiers.id_camellia256_wrap.getId(), valueOf4);
        hashMap2.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), valueOf2);
        hashMap2.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), valueOf3);
        hashMap2.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), valueOf3);
        hashMap2.put(OIWObjectIdentifiers.desCBC.getId(), valueOf);
        hashMap2.put(CryptoProObjectIdentifiers.gostR28147_gcfb.getId(), valueOf4);
        hashMap2.put(CryptoProObjectIdentifiers.id_Gost28147_89_None_KeyWrap.getId(), valueOf4);
        hashMap2.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_KeyWrap.getId(), valueOf4);
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), Integers.valueOf(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256));
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), valueOf4);
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), Integers.valueOf(384));
        hashMap2.put(PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), Integers.valueOf(512));
        hashMap.put("DESEDE", PKCSObjectIdentifiers.des_EDE3_CBC);
        hashMap.put("AES", NISTObjectIdentifiers.id_aes256_CBC);
        hashMap.put("CAMELLIA", NTTObjectIdentifiers.id_camellia256_cbc);
        hashMap.put("SEED", KISAObjectIdentifiers.id_seedCBC);
        hashMap.put("DES", OIWObjectIdentifiers.desCBC);
        hashMap3.put(MiscObjectIdentifiers.cast5CBC.getId(), "CAST5");
        hashMap3.put(MiscObjectIdentifiers.as_sys_sec_alg_ideaCBC.getId(), "IDEA");
        hashMap3.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_ECB.getId(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC.getId(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CFB.getId(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_OFB.getId(), "Blowfish");
        hashMap3.put(OIWObjectIdentifiers.desECB.getId(), "DES");
        hashMap3.put(OIWObjectIdentifiers.desCBC.getId(), "DES");
        hashMap3.put(OIWObjectIdentifiers.desCFB.getId(), "DES");
        hashMap3.put(OIWObjectIdentifiers.desOFB.getId(), "DES");
        hashMap3.put(OIWObjectIdentifiers.desEDE.getId(), "DESede");
        hashMap3.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DESede");
        hashMap3.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DESede");
        hashMap3.put(PKCSObjectIdentifiers.id_alg_CMSRC2wrap.getId(), "RC2");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), "HmacSHA1");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA224.getId(), "HmacSHA224");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), "HmacSHA256");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), "HmacSHA384");
        hashMap3.put(PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), "HmacSHA512");
        hashMap3.put(NTTObjectIdentifiers.id_camellia128_cbc.getId(), "Camellia");
        hashMap3.put(NTTObjectIdentifiers.id_camellia192_cbc.getId(), "Camellia");
        hashMap3.put(NTTObjectIdentifiers.id_camellia256_cbc.getId(), "Camellia");
        hashMap3.put(NTTObjectIdentifiers.id_camellia128_wrap.getId(), "Camellia");
        hashMap3.put(NTTObjectIdentifiers.id_camellia192_wrap.getId(), "Camellia");
        hashMap3.put(NTTObjectIdentifiers.id_camellia256_wrap.getId(), "Camellia");
        hashMap3.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), "SEED");
        hashMap3.put(KISAObjectIdentifiers.id_seedCBC.getId(), "SEED");
        hashMap3.put(KISAObjectIdentifiers.id_seedMAC.getId(), "SEED");
        hashMap3.put(CryptoProObjectIdentifiers.gostR28147_gcfb.getId(), "GOST28147");
        hashMap3.put(NISTObjectIdentifiers.id_aes128_wrap.getId(), "AES");
        hashMap3.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), "AES");
        hashMap3.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), "AES");
        hashtable.put("DESEDE", PKCSObjectIdentifiers.des_EDE3_CBC);
        hashtable.put("AES", NISTObjectIdentifiers.id_aes256_CBC);
        hashtable.put("DES", OIWObjectIdentifiers.desCBC);
        hashtable2.put("DES", "DES");
        hashtable2.put("DESEDE", "DES");
        hashtable2.put(OIWObjectIdentifiers.desCBC.getId(), "DES");
        hashtable2.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DES");
        hashtable2.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DES");
    }

    public BaseAgreementSpi(String str, DerivationFunction derivationFunction) {
        this.kaAlgorithm = str;
        this.kdf = derivationFunction;
    }

    protected static String getAlgorithm(String str) {
        if (str.indexOf(91) > 0) {
            return str.substring(0, str.indexOf(91));
        }
        if (str.startsWith(NISTObjectIdentifiers.aes.getId())) {
            return "AES";
        }
        if (str.startsWith(GNUObjectIdentifiers.Serpent.getId())) {
            return "Serpent";
        }
        String str2 = nameTable.get(Strings.toUpperCase(str));
        return str2 != null ? str2 : str;
    }

    protected static int getKeySize(String str) {
        if (str.indexOf(91) > 0) {
            return Integer.parseInt(str.substring(str.indexOf(91) + 1, str.indexOf(93)));
        }
        String upperCase = Strings.toUpperCase(str);
        Map<String, Integer> map = keySizes;
        if (!map.containsKey(upperCase)) {
            return -1;
        }
        return map.get(upperCase).intValue();
    }

    private byte[] getSharedSecretBytes(byte[] bArr, String str, int i) throws NoSuchAlgorithmException {
        DerivationParameters derivationParameters;
        DerivationFunction derivationFunction = this.kdf;
        if (derivationFunction != null) {
            if (i >= 0) {
                int i2 = i / 8;
                byte[] bArr2 = new byte[i2];
                if (!(derivationFunction instanceof DHKEKGenerator)) {
                    derivationParameters = new KDFParameters(bArr, this.ukmParameters);
                } else if (str != null) {
                    try {
                        derivationParameters = new DHKDFParameters(new ASN1ObjectIdentifier(str), i, bArr, this.ukmParameters);
                    } catch (IllegalArgumentException e) {
                        throw new NoSuchAlgorithmException("no OID for algorithm: " + str);
                    }
                } else {
                    throw new NoSuchAlgorithmException("algorithm OID is null");
                }
                this.kdf.init(derivationParameters);
                this.kdf.generateBytes(bArr2, 0, i2);
                Arrays.clear(bArr);
                return bArr2;
            }
            throw new NoSuchAlgorithmException("unknown algorithm encountered: " + str);
        } else if (i <= 0) {
            return bArr;
        } else {
            int i3 = i / 8;
            byte[] bArr3 = new byte[i3];
            System.arraycopy(bArr, 0, bArr3, 0, i3);
            Arrays.clear(bArr);
            return bArr3;
        }
    }

    protected static byte[] trimZeroes(byte[] bArr) {
        if (bArr[0] != 0) {
            return bArr;
        }
        int i = 0;
        while (i < bArr.length && bArr[i] == 0) {
            i++;
        }
        int length = bArr.length - i;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i, bArr2, 0, length);
        return bArr2;
    }

    /* access modifiers changed from: protected */
    public abstract byte[] calcSecret();

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        byte[] engineGenerateSecret = engineGenerateSecret();
        if (bArr.length - i >= engineGenerateSecret.length) {
            System.arraycopy(engineGenerateSecret, 0, bArr, i, engineGenerateSecret.length);
            return engineGenerateSecret.length;
        }
        throw new ShortBufferException(this.kaAlgorithm + " key agreement: need " + engineGenerateSecret.length + " bytes");
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String str) throws NoSuchAlgorithmException {
        String upperCase = Strings.toUpperCase(str);
        Hashtable hashtable = oids;
        String id = hashtable.containsKey(upperCase) ? ((ASN1ObjectIdentifier) hashtable.get(upperCase)).getId() : str;
        byte[] sharedSecretBytes = getSharedSecretBytes(calcSecret(), id, getKeySize(id));
        String algorithm = getAlgorithm(str);
        if (des.containsKey(algorithm)) {
            DESParameters.setOddParity(sharedSecretBytes);
        }
        return new SecretKeySpec(sharedSecretBytes, algorithm);
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.kdf == null) {
            return calcSecret();
        }
        byte[] calcSecret = calcSecret();
        try {
            return getSharedSecretBytes(calcSecret, (String) null, calcSecret.length * 8);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}

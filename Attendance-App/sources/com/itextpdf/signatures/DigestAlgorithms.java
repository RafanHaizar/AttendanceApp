package com.itextpdf.signatures;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

public class DigestAlgorithms {
    public static final String RIPEMD160 = "RIPEMD160";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";
    private static final Map<String, String> allowedDigests;
    private static final Map<String, String> digestNames;
    private static final Map<String, String> fixNames;

    static {
        HashMap hashMap = new HashMap();
        digestNames = hashMap;
        HashMap hashMap2 = new HashMap();
        fixNames = hashMap2;
        HashMap hashMap3 = new HashMap();
        allowedDigests = hashMap3;
        hashMap.put("1.2.840.113549.2.5", "MD5");
        hashMap.put("1.2.840.113549.2.2", "MD2");
        hashMap.put("1.3.14.3.2.26", "SHA1");
        hashMap.put("2.16.840.1.101.3.4.2.4", "SHA224");
        hashMap.put("2.16.840.1.101.3.4.2.1", "SHA256");
        hashMap.put("2.16.840.1.101.3.4.2.2", "SHA384");
        hashMap.put("2.16.840.1.101.3.4.2.3", "SHA512");
        hashMap.put("1.3.36.3.2.2", "RIPEMD128");
        hashMap.put("1.3.36.3.2.1", RIPEMD160);
        hashMap.put("1.3.36.3.2.3", "RIPEMD256");
        hashMap.put("1.2.840.113549.1.1.4", "MD5");
        hashMap.put("1.2.840.113549.1.1.2", "MD2");
        hashMap.put("1.2.840.113549.1.1.5", "SHA1");
        hashMap.put("1.2.840.113549.1.1.14", "SHA224");
        hashMap.put("1.2.840.113549.1.1.11", "SHA256");
        hashMap.put("1.2.840.113549.1.1.12", "SHA384");
        hashMap.put("1.2.840.113549.1.1.13", "SHA512");
        hashMap.put("1.2.840.113549.2.5", "MD5");
        hashMap.put("1.2.840.113549.2.2", "MD2");
        hashMap.put("1.2.840.10040.4.3", "SHA1");
        hashMap.put("2.16.840.1.101.3.4.3.1", "SHA224");
        hashMap.put("2.16.840.1.101.3.4.3.2", "SHA256");
        hashMap.put("2.16.840.1.101.3.4.3.3", "SHA384");
        hashMap.put("2.16.840.1.101.3.4.3.4", "SHA512");
        hashMap.put("1.3.36.3.3.1.3", "RIPEMD128");
        hashMap.put("1.3.36.3.3.1.2", RIPEMD160);
        hashMap.put("1.3.36.3.3.1.4", "RIPEMD256");
        hashMap.put("1.2.643.2.2.9", "GOST3411");
        hashMap2.put("SHA256", "SHA-256");
        hashMap2.put("SHA384", "SHA-384");
        hashMap2.put("SHA512", "SHA-512");
        hashMap3.put("MD2", "1.2.840.113549.2.2");
        hashMap3.put("MD-2", "1.2.840.113549.2.2");
        hashMap3.put("MD5", "1.2.840.113549.2.5");
        hashMap3.put("MD-5", "1.2.840.113549.2.5");
        Object obj = "1.3.14.3.2.26";
        hashMap3.put("SHA1", obj);
        hashMap3.put("SHA-1", obj);
        Object obj2 = "2.16.840.1.101.3.4.2.4";
        hashMap3.put("SHA224", obj2);
        hashMap3.put(McElieceCCA2KeyGenParameterSpec.SHA224, obj2);
        Object obj3 = "2.16.840.1.101.3.4.2.1";
        hashMap3.put("SHA256", obj3);
        hashMap3.put("SHA-256", obj3);
        Object obj4 = "2.16.840.1.101.3.4.2.2";
        hashMap3.put("SHA384", obj4);
        hashMap3.put("SHA-384", obj4);
        Object obj5 = "2.16.840.1.101.3.4.2.3";
        hashMap3.put("SHA512", obj5);
        hashMap3.put("SHA-512", obj5);
        Object obj6 = "1.3.36.3.2.2";
        hashMap3.put("RIPEMD128", obj6);
        hashMap3.put("RIPEMD-128", obj6);
        Object obj7 = "1.3.36.3.2.1";
        hashMap3.put(RIPEMD160, obj7);
        hashMap3.put("RIPEMD-160", obj7);
        Object obj8 = "1.3.36.3.2.3";
        hashMap3.put("RIPEMD256", obj8);
        hashMap3.put("RIPEMD-256", obj8);
        hashMap3.put("GOST3411", "1.2.643.2.2.9");
    }

    public static MessageDigest getMessageDigestFromOid(String digestOid, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        return getMessageDigest(getDigest(digestOid), provider);
    }

    public static MessageDigest getMessageDigest(String hashAlgorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SignUtils.getMessageDigest(hashAlgorithm, provider);
    }

    public static byte[] digest(InputStream data, String hashAlgorithm, String provider) throws GeneralSecurityException, IOException {
        return digest(data, getMessageDigest(hashAlgorithm, provider));
    }

    public static byte[] digest(InputStream data, MessageDigest messageDigest) throws IOException {
        byte[] buf = new byte[8192];
        while (true) {
            int read = data.read(buf);
            int n = read;
            if (read <= 0) {
                return messageDigest.digest();
            }
            messageDigest.update(buf, 0, n);
        }
    }

    public static String getDigest(String oid) {
        String ret = digestNames.get(oid);
        if (ret == null) {
            return oid;
        }
        return ret;
    }

    public static String normalizeDigestName(String algo) {
        Map<String, String> map = fixNames;
        if (map.containsKey(algo)) {
            return map.get(algo);
        }
        return algo;
    }

    public static String getAllowedDigest(String name) {
        return allowedDigests.get(name.toUpperCase());
    }
}

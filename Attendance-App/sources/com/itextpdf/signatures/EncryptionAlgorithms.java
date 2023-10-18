package com.itextpdf.signatures;

import java.util.HashMap;
import java.util.Map;

public class EncryptionAlgorithms {
    static final Map<String, String> algorithmNames;

    static {
        HashMap hashMap = new HashMap();
        algorithmNames = hashMap;
        hashMap.put(SecurityIDs.ID_RSA, "RSA");
        hashMap.put(SecurityIDs.ID_DSA, "DSA");
        hashMap.put("1.2.840.113549.1.1.2", "RSA");
        hashMap.put("1.2.840.113549.1.1.4", "RSA");
        hashMap.put("1.2.840.113549.1.1.5", "RSA");
        hashMap.put("1.2.840.113549.1.1.14", "RSA");
        hashMap.put("1.2.840.113549.1.1.11", "RSA");
        hashMap.put("1.2.840.113549.1.1.12", "RSA");
        hashMap.put("1.2.840.113549.1.1.13", "RSA");
        hashMap.put("1.2.840.10040.4.3", "DSA");
        hashMap.put("2.16.840.1.101.3.4.3.1", "DSA");
        hashMap.put("2.16.840.1.101.3.4.3.2", "DSA");
        hashMap.put("1.3.14.3.2.29", "RSA");
        hashMap.put("1.3.36.3.3.1.2", "RSA");
        hashMap.put("1.3.36.3.3.1.3", "RSA");
        hashMap.put("1.3.36.3.3.1.4", "RSA");
        hashMap.put("1.2.643.2.2.19", "ECGOST3410");
        hashMap.put(SecurityIDs.ID_ECDSA, "ECDSA");
        hashMap.put("1.2.840.10045.4.1", "ECDSA");
        hashMap.put("1.2.840.10045.4.3", "ECDSA");
        hashMap.put("1.2.840.10045.4.3.2", "ECDSA");
        hashMap.put("1.2.840.10045.4.3.3", "ECDSA");
        hashMap.put("1.2.840.10045.4.3.4", "ECDSA");
    }

    public static String getAlgorithm(String oid) {
        String ret = algorithmNames.get(oid);
        if (ret == null) {
            return oid;
        }
        return ret;
    }
}

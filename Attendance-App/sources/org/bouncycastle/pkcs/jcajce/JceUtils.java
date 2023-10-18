package org.bouncycastle.pkcs.jcajce;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

class JceUtils {
    private static final Map PRFS;

    static {
        HashMap hashMap = new HashMap();
        PRFS = hashMap;
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA1, "PBKDF2withHMACSHA1");
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA256, "PBKDF2withHMACSHA256");
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA512, "PBKDF2withHMACSHA512");
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA224, "PBKDF2withHMACSHA224");
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA384, "PBKDF2withHMACSHA384");
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_224, "PBKDF2withHMACSHA3-224");
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_256, "PBKDF2withHMACSHA3-256");
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_384, "PBKDF2withHMACSHA3-384");
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_512, "PBKDF2withHMACSHA3-512");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411Hmac, "PBKDF2withHMACGOST3411");
    }

    JceUtils() {
    }

    static String getAlgorithm(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Map map = PRFS;
        if (map.containsKey(aSN1ObjectIdentifier)) {
            return (String) map.get(aSN1ObjectIdentifier);
        }
        throw new IllegalStateException("no prf for algorithm: " + aSN1ObjectIdentifier);
    }
}

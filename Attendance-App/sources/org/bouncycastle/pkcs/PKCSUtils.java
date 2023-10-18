package org.bouncycastle.pkcs;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.util.Integers;

class PKCSUtils {
    private static final Map PRFS_SALT;

    static {
        HashMap hashMap = new HashMap();
        PRFS_SALT = hashMap;
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA1, Integers.valueOf(20));
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA256, Integers.valueOf(32));
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA512, Integers.valueOf(64));
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA224, Integers.valueOf(28));
        hashMap.put(PKCSObjectIdentifiers.id_hmacWithSHA384, Integers.valueOf(48));
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_224, Integers.valueOf(28));
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_256, Integers.valueOf(32));
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_384, Integers.valueOf(48));
        hashMap.put(NISTObjectIdentifiers.id_hmacWithSHA3_512, Integers.valueOf(64));
        hashMap.put(CryptoProObjectIdentifiers.gostR3411Hmac, Integers.valueOf(32));
    }

    PKCSUtils() {
    }

    static int getSaltSize(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Map map = PRFS_SALT;
        if (map.containsKey(aSN1ObjectIdentifier)) {
            return ((Integer) map.get(aSN1ObjectIdentifier)).intValue();
        }
        throw new IllegalStateException("no salt size for algorithm: " + aSN1ObjectIdentifier);
    }
}

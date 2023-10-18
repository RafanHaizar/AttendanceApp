package org.bouncycastle.cms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DefaultCMSSignatureEncryptionAlgorithmFinder implements CMSSignatureEncryptionAlgorithmFinder {
    private static final Map GOST_ENC;
    private static final Set RSA_PKCS1d5;

    static {
        HashSet hashSet = new HashSet();
        RSA_PKCS1d5 = hashSet;
        HashMap hashMap = new HashMap();
        GOST_ENC = hashMap;
        hashSet.add(PKCSObjectIdentifiers.md2WithRSAEncryption);
        hashSet.add(PKCSObjectIdentifiers.md4WithRSAEncryption);
        hashSet.add(PKCSObjectIdentifiers.md5WithRSAEncryption);
        hashSet.add(PKCSObjectIdentifiers.sha1WithRSAEncryption);
        hashSet.add(OIWObjectIdentifiers.md4WithRSAEncryption);
        hashSet.add(OIWObjectIdentifiers.md4WithRSA);
        hashSet.add(OIWObjectIdentifiers.md5WithRSA);
        hashSet.add(OIWObjectIdentifiers.sha1WithRSA);
        hashSet.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        hashSet.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        hashSet.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, DERNull.INSTANCE));
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_256, new AlgorithmIdentifier(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256, DERNull.INSTANCE));
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_512, new AlgorithmIdentifier(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512, DERNull.INSTANCE));
    }

    public AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        if (RSA_PKCS1d5.contains(algorithmIdentifier.getAlgorithm())) {
            return new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
        }
        Map map = GOST_ENC;
        return map.containsKey(algorithmIdentifier.getAlgorithm()) ? (AlgorithmIdentifier) map.get(algorithmIdentifier.getAlgorithm()) : algorithmIdentifier;
    }
}

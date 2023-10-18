package org.bouncycastle.crypto.util;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.p006gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Integers;

public class PBKDF2Config extends PBKDFConfig {
    private static final Map PRFS_SALT;
    public static final AlgorithmIdentifier PRF_SHA1 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE);
    public static final AlgorithmIdentifier PRF_SHA256 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256, DERNull.INSTANCE);
    public static final AlgorithmIdentifier PRF_SHA3_256 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_hmacWithSHA3_256, DERNull.INSTANCE);
    public static final AlgorithmIdentifier PRF_SHA3_512 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_hmacWithSHA3_512, DERNull.INSTANCE);
    public static final AlgorithmIdentifier PRF_SHA512 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA512, DERNull.INSTANCE);
    private final int iterationCount;
    private final AlgorithmIdentifier prf;
    private final int saltLength;

    public static class Builder {
        /* access modifiers changed from: private */
        public int iterationCount = 1024;
        /* access modifiers changed from: private */
        public AlgorithmIdentifier prf = PBKDF2Config.PRF_SHA1;
        /* access modifiers changed from: private */
        public int saltLength = -1;

        public PBKDF2Config build() {
            return new PBKDF2Config(this);
        }

        public Builder withIterationCount(int i) {
            this.iterationCount = i;
            return this;
        }

        public Builder withPRF(AlgorithmIdentifier algorithmIdentifier) {
            this.prf = algorithmIdentifier;
            return this;
        }

        public Builder withSaltLength(int i) {
            this.saltLength = i;
            return this;
        }
    }

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
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_hmac_gost_3411_12_256, Integers.valueOf(32));
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_hmac_gost_3411_12_512, Integers.valueOf(64));
        hashMap.put(GMObjectIdentifiers.hmac_sm3, Integers.valueOf(32));
    }

    private PBKDF2Config(Builder builder) {
        super(PKCSObjectIdentifiers.id_PBKDF2);
        this.iterationCount = builder.iterationCount;
        AlgorithmIdentifier access$200 = builder.prf;
        this.prf = access$200;
        this.saltLength = builder.saltLength < 0 ? getSaltSize(access$200.getAlgorithm()) : builder.saltLength;
    }

    static int getSaltSize(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Map map = PRFS_SALT;
        if (map.containsKey(aSN1ObjectIdentifier)) {
            return ((Integer) map.get(aSN1ObjectIdentifier)).intValue();
        }
        throw new IllegalStateException("no salt size for algorithm: " + aSN1ObjectIdentifier);
    }

    public int getIterationCount() {
        return this.iterationCount;
    }

    public AlgorithmIdentifier getPRF() {
        return this.prf;
    }

    public int getSaltLength() {
        return this.saltLength;
    }
}

package org.bouncycastle.operator.p021bc;

import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* renamed from: org.bouncycastle.operator.bc.SEEDUtil */
class SEEDUtil {
    SEEDUtil() {
    }

    static AlgorithmIdentifier determineKeyEncAlg() {
        return new AlgorithmIdentifier(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
    }
}

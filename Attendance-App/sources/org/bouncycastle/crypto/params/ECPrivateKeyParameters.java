package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class ECPrivateKeyParameters extends ECKeyParameters {

    /* renamed from: d */
    private final BigInteger f579d;

    public ECPrivateKeyParameters(BigInteger bigInteger, ECDomainParameters eCDomainParameters) {
        super(true, eCDomainParameters);
        this.f579d = eCDomainParameters.validatePrivateScalar(bigInteger);
    }

    public BigInteger getD() {
        return this.f579d;
    }
}

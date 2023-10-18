package org.bouncycastle.crypto.params;

import org.bouncycastle.math.p018ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {

    /* renamed from: q */
    private final ECPoint f580q;

    public ECPublicKeyParameters(ECPoint eCPoint, ECDomainParameters eCDomainParameters) {
        super(false, eCDomainParameters);
        this.f580q = eCDomainParameters.validatePublicPoint(eCPoint);
    }

    public ECPoint getQ() {
        return this.f580q;
    }
}

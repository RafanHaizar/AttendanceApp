package org.bouncycastle.jce.spec;

import org.bouncycastle.math.p018ec.ECPoint;

public class ECPublicKeySpec extends ECKeySpec {

    /* renamed from: q */
    private ECPoint f698q;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f698q = eCPoint.getCurve() != null ? eCPoint.normalize() : eCPoint;
    }

    public ECPoint getQ() {
        return this.f698q;
    }
}

package org.bouncycastle.math.p018ec.endo;

import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.p018ec.PreCompInfo;

/* renamed from: org.bouncycastle.math.ec.endo.EndoPreCompInfo */
public class EndoPreCompInfo implements PreCompInfo {
    protected ECEndomorphism endomorphism;
    protected ECPoint mappedPoint;

    public ECEndomorphism getEndomorphism() {
        return this.endomorphism;
    }

    public ECPoint getMappedPoint() {
        return this.mappedPoint;
    }

    public void setEndomorphism(ECEndomorphism eCEndomorphism) {
        this.endomorphism = eCEndomorphism;
    }

    public void setMappedPoint(ECPoint eCPoint) {
        this.mappedPoint = eCPoint;
    }
}

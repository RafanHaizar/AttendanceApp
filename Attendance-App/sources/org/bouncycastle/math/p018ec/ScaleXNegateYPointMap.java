package org.bouncycastle.math.p018ec;

/* renamed from: org.bouncycastle.math.ec.ScaleXNegateYPointMap */
public class ScaleXNegateYPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleXNegateYPointMap(ECFieldElement eCFieldElement) {
        this.scale = eCFieldElement;
    }

    public ECPoint map(ECPoint eCPoint) {
        return eCPoint.scaleXNegateY(this.scale);
    }
}

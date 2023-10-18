package org.bouncycastle.math.p018ec;

/* renamed from: org.bouncycastle.math.ec.ScaleYNegateXPointMap */
public class ScaleYNegateXPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleYNegateXPointMap(ECFieldElement eCFieldElement) {
        this.scale = eCFieldElement;
    }

    public ECPoint map(ECPoint eCPoint) {
        return eCPoint.scaleYNegateX(this.scale);
    }
}

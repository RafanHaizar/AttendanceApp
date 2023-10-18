package org.bouncycastle.math.p018ec;

/* renamed from: org.bouncycastle.math.ec.ScaleXPointMap */
public class ScaleXPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleXPointMap(ECFieldElement eCFieldElement) {
        this.scale = eCFieldElement;
    }

    public ECPoint map(ECPoint eCPoint) {
        return eCPoint.scaleX(this.scale);
    }
}

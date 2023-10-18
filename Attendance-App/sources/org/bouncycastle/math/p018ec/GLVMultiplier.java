package org.bouncycastle.math.p018ec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.endo.ECEndomorphism;
import org.bouncycastle.math.p018ec.endo.EndoUtil;
import org.bouncycastle.math.p018ec.endo.GLVEndomorphism;

/* renamed from: org.bouncycastle.math.ec.GLVMultiplier */
public class GLVMultiplier extends AbstractECMultiplier {
    protected final ECCurve curve;
    protected final GLVEndomorphism glvEndomorphism;

    public GLVMultiplier(ECCurve eCCurve, GLVEndomorphism gLVEndomorphism) {
        if (eCCurve == null || eCCurve.getOrder() == null) {
            throw new IllegalArgumentException("Need curve with known group order");
        }
        this.curve = eCCurve;
        this.glvEndomorphism = gLVEndomorphism;
    }

    /* access modifiers changed from: protected */
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        if (this.curve.equals(eCPoint.getCurve())) {
            BigInteger[] decomposeScalar = this.glvEndomorphism.decomposeScalar(bigInteger.mod(eCPoint.getCurve().getOrder()));
            BigInteger bigInteger2 = decomposeScalar[0];
            BigInteger bigInteger3 = decomposeScalar[1];
            return this.glvEndomorphism.hasEfficientPointMap() ? ECAlgorithms.implShamirsTrickWNaf((ECEndomorphism) this.glvEndomorphism, eCPoint, bigInteger2, bigInteger3) : ECAlgorithms.implShamirsTrickWNaf(eCPoint, bigInteger2, EndoUtil.mapPoint(this.glvEndomorphism, eCPoint), bigInteger3);
        }
        throw new IllegalStateException();
    }
}

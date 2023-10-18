package org.bouncycastle.math.p018ec.endo;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPointMap;
import org.bouncycastle.math.p018ec.ScaleXPointMap;

/* renamed from: org.bouncycastle.math.ec.endo.GLVTypeBEndomorphism */
public class GLVTypeBEndomorphism implements GLVEndomorphism {
    protected final GLVTypeBParameters parameters;
    protected final ECPointMap pointMap;

    public GLVTypeBEndomorphism(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        this.parameters = gLVTypeBParameters;
        this.pointMap = new ScaleXPointMap(eCCurve.fromBigInteger(gLVTypeBParameters.getBeta()));
    }

    public BigInteger[] decomposeScalar(BigInteger bigInteger) {
        return EndoUtil.decomposeScalar(this.parameters.getSplitParams(), bigInteger);
    }

    public ECPointMap getPointMap() {
        return this.pointMap;
    }

    public boolean hasEfficientPointMap() {
        return true;
    }
}

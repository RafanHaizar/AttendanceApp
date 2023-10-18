package org.bouncycastle.math.p018ec.endo;

import java.math.BigInteger;

/* renamed from: org.bouncycastle.math.ec.endo.GLVTypeAParameters */
public class GLVTypeAParameters {

    /* renamed from: i */
    protected final BigInteger f814i;
    protected final BigInteger lambda;
    protected final ScalarSplitParameters splitParams;

    public GLVTypeAParameters(BigInteger bigInteger, BigInteger bigInteger2, ScalarSplitParameters scalarSplitParameters) {
        this.f814i = bigInteger;
        this.lambda = bigInteger2;
        this.splitParams = scalarSplitParameters;
    }

    public BigInteger getI() {
        return this.f814i;
    }

    public BigInteger getLambda() {
        return this.lambda;
    }

    public ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }
}

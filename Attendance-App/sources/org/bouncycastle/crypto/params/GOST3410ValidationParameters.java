package org.bouncycastle.crypto.params;

public class GOST3410ValidationParameters {

    /* renamed from: c */
    private int f591c;

    /* renamed from: cL */
    private long f592cL;

    /* renamed from: x0 */
    private int f593x0;
    private long x0L;

    public GOST3410ValidationParameters(int i, int i2) {
        this.f593x0 = i;
        this.f591c = i2;
    }

    public GOST3410ValidationParameters(long j, long j2) {
        this.x0L = j;
        this.f592cL = j2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410ValidationParameters)) {
            return false;
        }
        GOST3410ValidationParameters gOST3410ValidationParameters = (GOST3410ValidationParameters) obj;
        return gOST3410ValidationParameters.f591c == this.f591c && gOST3410ValidationParameters.f593x0 == this.f593x0 && gOST3410ValidationParameters.f592cL == this.f592cL && gOST3410ValidationParameters.x0L == this.x0L;
    }

    public int getC() {
        return this.f591c;
    }

    public long getCL() {
        return this.f592cL;
    }

    public int getX0() {
        return this.f593x0;
    }

    public long getX0L() {
        return this.x0L;
    }

    public int hashCode() {
        int i = this.f593x0 ^ this.f591c;
        long j = this.x0L;
        long j2 = this.f592cL;
        return (((i ^ ((int) j)) ^ ((int) (j >> 32))) ^ ((int) j2)) ^ ((int) (j2 >> 32));
    }
}

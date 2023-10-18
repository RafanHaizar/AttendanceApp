package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPrivateKeyParameters extends CramerShoupKeyParameters {

    /* renamed from: pk */
    private CramerShoupPublicKeyParameters f552pk;

    /* renamed from: x1 */
    private BigInteger f553x1;

    /* renamed from: x2 */
    private BigInteger f554x2;

    /* renamed from: y1 */
    private BigInteger f555y1;

    /* renamed from: y2 */
    private BigInteger f556y2;

    /* renamed from: z */
    private BigInteger f557z;

    public CramerShoupPrivateKeyParameters(CramerShoupParameters cramerShoupParameters, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5) {
        super(true, cramerShoupParameters);
        this.f553x1 = bigInteger;
        this.f554x2 = bigInteger2;
        this.f555y1 = bigInteger3;
        this.f556y2 = bigInteger4;
        this.f557z = bigInteger5;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupPrivateKeyParameters)) {
            return false;
        }
        CramerShoupPrivateKeyParameters cramerShoupPrivateKeyParameters = (CramerShoupPrivateKeyParameters) obj;
        return cramerShoupPrivateKeyParameters.getX1().equals(this.f553x1) && cramerShoupPrivateKeyParameters.getX2().equals(this.f554x2) && cramerShoupPrivateKeyParameters.getY1().equals(this.f555y1) && cramerShoupPrivateKeyParameters.getY2().equals(this.f556y2) && cramerShoupPrivateKeyParameters.getZ().equals(this.f557z) && super.equals(obj);
    }

    public CramerShoupPublicKeyParameters getPk() {
        return this.f552pk;
    }

    public BigInteger getX1() {
        return this.f553x1;
    }

    public BigInteger getX2() {
        return this.f554x2;
    }

    public BigInteger getY1() {
        return this.f555y1;
    }

    public BigInteger getY2() {
        return this.f556y2;
    }

    public BigInteger getZ() {
        return this.f557z;
    }

    public int hashCode() {
        return ((((this.f553x1.hashCode() ^ this.f554x2.hashCode()) ^ this.f555y1.hashCode()) ^ this.f556y2.hashCode()) ^ this.f557z.hashCode()) ^ super.hashCode();
    }

    public void setPk(CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters) {
        this.f552pk = cramerShoupPublicKeyParameters;
    }
}

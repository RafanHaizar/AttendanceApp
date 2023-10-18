package org.bouncycastle.crypto.p012ec;

import org.bouncycastle.math.p018ec.ECPoint;

/* renamed from: org.bouncycastle.crypto.ec.ECPair */
public class ECPair {

    /* renamed from: x */
    private final ECPoint f311x;

    /* renamed from: y */
    private final ECPoint f312y;

    public ECPair(ECPoint eCPoint, ECPoint eCPoint2) {
        this.f311x = eCPoint;
        this.f312y = eCPoint2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ECPair) {
            return equals((ECPair) obj);
        }
        return false;
    }

    public boolean equals(ECPair eCPair) {
        return eCPair.getX().equals(getX()) && eCPair.getY().equals(getY());
    }

    public ECPoint getX() {
        return this.f311x;
    }

    public ECPoint getY() {
        return this.f312y;
    }

    public int hashCode() {
        return this.f311x.hashCode() + (this.f312y.hashCode() * 37);
    }
}

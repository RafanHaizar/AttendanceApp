package org.bouncycastle.asn1.p008x9;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.asn1.x9.X9ECPoint */
public class X9ECPoint extends ASN1Object {

    /* renamed from: c */
    private ECCurve f109c;
    private final ASN1OctetString encoding;

    /* renamed from: p */
    private ECPoint f110p;

    public X9ECPoint(ECCurve eCCurve, ASN1OctetString aSN1OctetString) {
        this(eCCurve, aSN1OctetString.getOctets());
    }

    public X9ECPoint(ECCurve eCCurve, byte[] bArr) {
        this.f109c = eCCurve;
        this.encoding = new DEROctetString(Arrays.clone(bArr));
    }

    public X9ECPoint(ECPoint eCPoint, boolean z) {
        this.f110p = eCPoint.normalize();
        this.encoding = new DEROctetString(eCPoint.getEncoded(z));
    }

    public synchronized ECPoint getPoint() {
        if (this.f110p == null) {
            this.f110p = this.f109c.decodePoint(this.encoding.getOctets()).normalize();
        }
        return this.f110p;
    }

    public byte[] getPointEncoding() {
        return Arrays.clone(this.encoding.getOctets());
    }

    public boolean isPointCompressed() {
        byte[] octets = this.encoding.getOctets();
        if (octets == null || octets.length <= 0) {
            return false;
        }
        byte b = octets[0];
        return b == 2 || b == 3;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.encoding;
    }
}

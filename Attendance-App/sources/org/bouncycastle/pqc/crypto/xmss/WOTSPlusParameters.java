package org.bouncycastle.pqc.crypto.xmss;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.Digest;

final class WOTSPlusParameters {
    private final int digestSize;
    private final int len;
    private final int len1;
    private final int len2;
    private final XMSSOid oid;
    private final ASN1ObjectIdentifier treeDigest;
    private final int winternitzParameter;

    protected WOTSPlusParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (aSN1ObjectIdentifier != null) {
            this.treeDigest = aSN1ObjectIdentifier;
            Digest digest = DigestUtil.getDigest(aSN1ObjectIdentifier);
            int digestSize2 = XMSSUtil.getDigestSize(digest);
            this.digestSize = digestSize2;
            this.winternitzParameter = 16;
            double d = (double) (digestSize2 * 8);
            double log2 = (double) XMSSUtil.log2(16);
            Double.isNaN(d);
            Double.isNaN(log2);
            int ceil = (int) Math.ceil(d / log2);
            this.len1 = ceil;
            int floor = ((int) Math.floor((double) (XMSSUtil.log2((16 - 1) * ceil) / XMSSUtil.log2(16)))) + 1;
            this.len2 = floor;
            int i = ceil + floor;
            this.len = i;
            WOTSPlusOid lookup = WOTSPlusOid.lookup(digest.getAlgorithmName(), digestSize2, 16, i);
            this.oid = lookup;
            if (lookup == null) {
                throw new IllegalArgumentException("cannot find OID for digest algorithm: " + digest.getAlgorithmName());
            }
            return;
        }
        throw new NullPointerException("treeDigest == null");
    }

    /* access modifiers changed from: protected */
    public int getLen() {
        return this.len;
    }

    /* access modifiers changed from: protected */
    public int getLen1() {
        return this.len1;
    }

    /* access modifiers changed from: protected */
    public int getLen2() {
        return this.len2;
    }

    /* access modifiers changed from: protected */
    public XMSSOid getOid() {
        return this.oid;
    }

    public ASN1ObjectIdentifier getTreeDigest() {
        return this.treeDigest;
    }

    /* access modifiers changed from: protected */
    public int getTreeDigestSize() {
        return this.digestSize;
    }

    /* access modifiers changed from: protected */
    public int getWinternitzParameter() {
        return this.winternitzParameter;
    }
}

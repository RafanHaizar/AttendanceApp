package org.bouncycastle.pqc.crypto.xmss;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.Digest;

public final class XMSSParameters {
    private final int height;

    /* renamed from: k */
    private final int f942k;
    private final XMSSOid oid;
    private final String treeDigest;
    private final ASN1ObjectIdentifier treeDigestOID;
    private final int treeDigestSize;
    private final int winternitzParameter;
    private final WOTSPlusParameters wotsPlusParams;

    public XMSSParameters(int i, Digest digest) {
        if (i < 2) {
            throw new IllegalArgumentException("height must be >= 2");
        } else if (digest != null) {
            this.height = i;
            this.f942k = determineMinK();
            String algorithmName = digest.getAlgorithmName();
            this.treeDigest = algorithmName;
            ASN1ObjectIdentifier digestOID = DigestUtil.getDigestOID(digest.getAlgorithmName());
            this.treeDigestOID = digestOID;
            WOTSPlusParameters wOTSPlusParameters = new WOTSPlusParameters(digestOID);
            this.wotsPlusParams = wOTSPlusParameters;
            int treeDigestSize2 = wOTSPlusParameters.getTreeDigestSize();
            this.treeDigestSize = treeDigestSize2;
            int winternitzParameter2 = wOTSPlusParameters.getWinternitzParameter();
            this.winternitzParameter = winternitzParameter2;
            this.oid = DefaultXMSSOid.lookup(algorithmName, treeDigestSize2, winternitzParameter2, wOTSPlusParameters.getLen(), i);
        } else {
            throw new NullPointerException("digest == null");
        }
    }

    private int determineMinK() {
        int i = 2;
        while (true) {
            int i2 = this.height;
            if (i > i2) {
                throw new IllegalStateException("should never happen...");
            } else if ((i2 - i) % 2 == 0) {
                return i;
            } else {
                i++;
            }
        }
    }

    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public int getK() {
        return this.f942k;
    }

    /* access modifiers changed from: package-private */
    public int getLen() {
        return this.wotsPlusParams.getLen();
    }

    /* access modifiers changed from: package-private */
    public XMSSOid getOid() {
        return this.oid;
    }

    /* access modifiers changed from: package-private */
    public String getTreeDigest() {
        return this.treeDigest;
    }

    /* access modifiers changed from: package-private */
    public ASN1ObjectIdentifier getTreeDigestOID() {
        return this.treeDigestOID;
    }

    public int getTreeDigestSize() {
        return this.treeDigestSize;
    }

    /* access modifiers changed from: package-private */
    public WOTSPlus getWOTSPlus() {
        return new WOTSPlus(this.wotsPlusParams);
    }

    /* access modifiers changed from: package-private */
    public int getWinternitzParameter() {
        return this.winternitzParameter;
    }
}

package org.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.pqc.crypto.xmss.XMSSReducedSignature;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Encodable;

public final class XMSSMTSignature implements XMSSStoreableObjectInterface, Encodable {
    private final long index;
    private final XMSSMTParameters params;
    private final byte[] random;
    private final List<XMSSReducedSignature> reducedSignatures;

    public static class Builder {
        /* access modifiers changed from: private */
        public long index = 0;
        /* access modifiers changed from: private */
        public final XMSSMTParameters params;
        /* access modifiers changed from: private */
        public byte[] random = null;
        /* access modifiers changed from: private */
        public List<XMSSReducedSignature> reducedSignatures = null;
        /* access modifiers changed from: private */
        public byte[] signature = null;

        public Builder(XMSSMTParameters xMSSMTParameters) {
            this.params = xMSSMTParameters;
        }

        public XMSSMTSignature build() {
            return new XMSSMTSignature(this);
        }

        public Builder withIndex(long j) {
            this.index = j;
            return this;
        }

        public Builder withRandom(byte[] bArr) {
            this.random = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withReducedSignatures(List<XMSSReducedSignature> list) {
            this.reducedSignatures = list;
            return this;
        }

        public Builder withSignature(byte[] bArr) {
            this.signature = Arrays.clone(bArr);
            return this;
        }
    }

    private XMSSMTSignature(Builder builder) {
        XMSSMTParameters access$000 = builder.params;
        this.params = access$000;
        if (access$000 != null) {
            int treeDigestSize = access$000.getTreeDigestSize();
            byte[] access$100 = builder.signature;
            if (access$100 != null) {
                int len = access$000.getWOTSPlus().getParams().getLen();
                double height = (double) access$000.getHeight();
                Double.isNaN(height);
                int ceil = (int) Math.ceil(height / 8.0d);
                int height2 = ((access$000.getHeight() / access$000.getLayers()) + len) * treeDigestSize;
                if (access$100.length == ceil + treeDigestSize + (access$000.getLayers() * height2)) {
                    long bytesToXBigEndian = XMSSUtil.bytesToXBigEndian(access$100, 0, ceil);
                    this.index = bytesToXBigEndian;
                    if (XMSSUtil.isIndexValid(access$000.getHeight(), bytesToXBigEndian)) {
                        int i = ceil + 0;
                        this.random = XMSSUtil.extractBytesAtOffset(access$100, i, treeDigestSize);
                        this.reducedSignatures = new ArrayList();
                        for (int i2 = i + treeDigestSize; i2 < access$100.length; i2 += height2) {
                            this.reducedSignatures.add(new XMSSReducedSignature.Builder(this.params.getXMSSParameters()).withReducedSignature(XMSSUtil.extractBytesAtOffset(access$100, i2, height2)).build());
                        }
                        return;
                    }
                    throw new IllegalArgumentException("index out of bounds");
                }
                throw new IllegalArgumentException("signature has wrong size");
            }
            this.index = builder.index;
            byte[] access$300 = builder.random;
            if (access$300 == null) {
                this.random = new byte[treeDigestSize];
            } else if (access$300.length == treeDigestSize) {
                this.random = access$300;
            } else {
                throw new IllegalArgumentException("size of random needs to be equal to size of digest");
            }
            List<XMSSReducedSignature> access$400 = builder.reducedSignatures;
            this.reducedSignatures = access$400 == null ? new ArrayList<>() : access$400;
            return;
        }
        throw new NullPointerException("params == null");
    }

    public byte[] getEncoded() throws IOException {
        return toByteArray();
    }

    public long getIndex() {
        return this.index;
    }

    public byte[] getRandom() {
        return XMSSUtil.cloneArray(this.random);
    }

    public List<XMSSReducedSignature> getReducedSignatures() {
        return this.reducedSignatures;
    }

    public byte[] toByteArray() {
        int treeDigestSize = this.params.getTreeDigestSize();
        int len = this.params.getWOTSPlus().getParams().getLen();
        double height = (double) this.params.getHeight();
        Double.isNaN(height);
        int ceil = (int) Math.ceil(height / 8.0d);
        int height2 = ((this.params.getHeight() / this.params.getLayers()) + len) * treeDigestSize;
        byte[] bArr = new byte[(ceil + treeDigestSize + (this.params.getLayers() * height2))];
        XMSSUtil.copyBytesAtOffset(bArr, XMSSUtil.toBytesBigEndian(this.index, ceil), 0);
        int i = ceil + 0;
        XMSSUtil.copyBytesAtOffset(bArr, this.random, i);
        int i2 = i + treeDigestSize;
        for (XMSSReducedSignature byteArray : this.reducedSignatures) {
            XMSSUtil.copyBytesAtOffset(bArr, byteArray.toByteArray(), i2);
            i2 += height2;
        }
        return bArr;
    }
}

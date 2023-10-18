package org.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.Pack;

public final class XMSSPublicKeyParameters extends XMSSKeyParameters implements XMSSStoreableObjectInterface, Encodable {
    private final int oid;
    private final XMSSParameters params;
    private final byte[] publicSeed;
    private final byte[] root;

    public static class Builder {
        /* access modifiers changed from: private */
        public final XMSSParameters params;
        /* access modifiers changed from: private */
        public byte[] publicKey = null;
        /* access modifiers changed from: private */
        public byte[] publicSeed = null;
        /* access modifiers changed from: private */
        public byte[] root = null;

        public Builder(XMSSParameters xMSSParameters) {
            this.params = xMSSParameters;
        }

        public XMSSPublicKeyParameters build() {
            return new XMSSPublicKeyParameters(this);
        }

        public Builder withPublicKey(byte[] bArr) {
            this.publicKey = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withPublicSeed(byte[] bArr) {
            this.publicSeed = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withRoot(byte[] bArr) {
            this.root = XMSSUtil.cloneArray(bArr);
            return this;
        }
    }

    private XMSSPublicKeyParameters(Builder builder) {
        super(false, builder.params.getTreeDigest());
        XMSSParameters access$000 = builder.params;
        this.params = access$000;
        if (access$000 != null) {
            int treeDigestSize = access$000.getTreeDigestSize();
            byte[] access$100 = builder.publicKey;
            if (access$100 == null) {
                if (access$000.getOid() != null) {
                    this.oid = access$000.getOid().getOid();
                } else {
                    this.oid = 0;
                }
                byte[] access$200 = builder.root;
                if (access$200 == null) {
                    this.root = new byte[treeDigestSize];
                } else if (access$200.length == treeDigestSize) {
                    this.root = access$200;
                } else {
                    throw new IllegalArgumentException("length of root must be equal to length of digest");
                }
                byte[] access$300 = builder.publicSeed;
                if (access$300 == null) {
                    this.publicSeed = new byte[treeDigestSize];
                } else if (access$300.length == treeDigestSize) {
                    this.publicSeed = access$300;
                } else {
                    throw new IllegalArgumentException("length of publicSeed must be equal to length of digest");
                }
            } else if (access$100.length == treeDigestSize + treeDigestSize) {
                this.oid = 0;
                this.root = XMSSUtil.extractBytesAtOffset(access$100, 0, treeDigestSize);
                this.publicSeed = XMSSUtil.extractBytesAtOffset(access$100, treeDigestSize + 0, treeDigestSize);
            } else if (access$100.length == treeDigestSize + 4 + treeDigestSize) {
                this.oid = Pack.bigEndianToInt(access$100, 0);
                this.root = XMSSUtil.extractBytesAtOffset(access$100, 4, treeDigestSize);
                this.publicSeed = XMSSUtil.extractBytesAtOffset(access$100, 4 + treeDigestSize, treeDigestSize);
            } else {
                throw new IllegalArgumentException("public key has wrong size");
            }
        } else {
            throw new NullPointerException("params == null");
        }
    }

    public byte[] getEncoded() throws IOException {
        return toByteArray();
    }

    public XMSSParameters getParameters() {
        return this.params;
    }

    public byte[] getPublicSeed() {
        return XMSSUtil.cloneArray(this.publicSeed);
    }

    public byte[] getRoot() {
        return XMSSUtil.cloneArray(this.root);
    }

    public byte[] toByteArray() {
        byte[] bArr;
        int treeDigestSize = this.params.getTreeDigestSize();
        int i = this.oid;
        int i2 = 0;
        if (i != 0) {
            bArr = new byte[(treeDigestSize + 4 + treeDigestSize)];
            Pack.intToBigEndian(i, bArr, 0);
            i2 = 4;
        } else {
            bArr = new byte[(treeDigestSize + treeDigestSize)];
        }
        XMSSUtil.copyBytesAtOffset(bArr, this.root, i2);
        XMSSUtil.copyBytesAtOffset(bArr, this.publicSeed, i2 + treeDigestSize);
        return bArr;
    }
}

package org.bouncycastle.pqc.crypto.xmss;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.crypto.StateAwareMessageSigner;
import org.bouncycastle.pqc.crypto.xmss.OTSHashAddress;
import org.bouncycastle.pqc.crypto.xmss.XMSSSignature;
import org.bouncycastle.util.Arrays;

public class XMSSSigner implements StateAwareMessageSigner {
    private boolean hasGenerated;
    private boolean initSign;
    private KeyedHashFunctions khf;
    private XMSSParameters params;
    private XMSSPrivateKeyParameters privateKey;
    private XMSSPublicKeyParameters publicKey;
    private WOTSPlus wotsPlus;

    private WOTSPlusSignature wotsSign(byte[] bArr, OTSHashAddress oTSHashAddress) {
        if (bArr.length != this.params.getTreeDigestSize()) {
            throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
        } else if (oTSHashAddress != null) {
            WOTSPlus wOTSPlus = this.wotsPlus;
            wOTSPlus.importKeys(wOTSPlus.getWOTSPlusSecretKey(this.privateKey.getSecretKeySeed(), oTSHashAddress), this.privateKey.getPublicSeed());
            return this.wotsPlus.sign(bArr, oTSHashAddress);
        } else {
            throw new NullPointerException("otsHashAddress == null");
        }
    }

    public byte[] generateSignature(byte[] bArr) {
        byte[] byteArray;
        if (bArr == null) {
            throw new NullPointerException("message == null");
        } else if (this.initSign) {
            XMSSPrivateKeyParameters xMSSPrivateKeyParameters = this.privateKey;
            if (xMSSPrivateKeyParameters != null) {
                synchronized (xMSSPrivateKeyParameters) {
                    if (this.privateKey.getUsagesRemaining() <= 0) {
                        throw new IllegalStateException("no usages of private key remaining");
                    } else if (!this.privateKey.getBDSState().getAuthenticationPath().isEmpty()) {
                        try {
                            int index = this.privateKey.getIndex();
                            this.hasGenerated = true;
                            long j = (long) index;
                            byte[] PRF = this.khf.PRF(this.privateKey.getSecretKeyPRF(), XMSSUtil.toBytesBigEndian(j, 32));
                            byteArray = new XMSSSignature.Builder(this.params).withIndex(index).withRandom(PRF).withWOTSPlusSignature(wotsSign(this.khf.HMsg(Arrays.concatenate(PRF, this.privateKey.getRoot(), XMSSUtil.toBytesBigEndian(j, this.params.getTreeDigestSize())), bArr), (OTSHashAddress) new OTSHashAddress.Builder().withOTSAddress(index).build())).withAuthPath(this.privateKey.getBDSState().getAuthenticationPath()).build().toByteArray();
                        } finally {
                            this.privateKey.getBDSState().markUsed();
                            this.privateKey.rollKey();
                        }
                    } else {
                        throw new IllegalStateException("not initialized");
                    }
                }
                return byteArray;
            }
            throw new IllegalStateException("signing key no longer usable");
        } else {
            throw new IllegalStateException("signer not initialized for signature generation");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.crypto.params.AsymmetricKeyParameter getUpdatedPrivateKey() {
        /*
            r3 = this;
            org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters r0 = r3.privateKey
            monitor-enter(r0)
            boolean r1 = r3.hasGenerated     // Catch:{ all -> 0x001a }
            if (r1 == 0) goto L_0x000e
            org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters r1 = r3.privateKey     // Catch:{ all -> 0x001a }
            r2 = 0
            r3.privateKey = r2     // Catch:{ all -> 0x001a }
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            return r1
        L_0x000e:
            org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters r1 = r3.privateKey     // Catch:{ all -> 0x001a }
            if (r1 == 0) goto L_0x0018
            org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters r2 = r1.getNextKey()     // Catch:{ all -> 0x001a }
            r3.privateKey = r2     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            return r1
        L_0x001a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pqc.crypto.xmss.XMSSSigner.getUpdatedPrivateKey():org.bouncycastle.crypto.params.AsymmetricKeyParameter");
    }

    public long getUsagesRemaining() {
        return this.privateKey.getUsagesRemaining();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        XMSSParameters xMSSParameters;
        if (z) {
            this.initSign = true;
            this.hasGenerated = false;
            XMSSPrivateKeyParameters xMSSPrivateKeyParameters = (XMSSPrivateKeyParameters) cipherParameters;
            this.privateKey = xMSSPrivateKeyParameters;
            xMSSParameters = xMSSPrivateKeyParameters.getParameters();
        } else {
            this.initSign = false;
            XMSSPublicKeyParameters xMSSPublicKeyParameters = (XMSSPublicKeyParameters) cipherParameters;
            this.publicKey = xMSSPublicKeyParameters;
            xMSSParameters = xMSSPublicKeyParameters.getParameters();
        }
        this.params = xMSSParameters;
        WOTSPlus wOTSPlus = this.params.getWOTSPlus();
        this.wotsPlus = wOTSPlus;
        this.khf = wOTSPlus.getKhf();
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        XMSSSignature build = new XMSSSignature.Builder(this.params).withSignature(bArr2).build();
        int index = build.getIndex();
        this.wotsPlus.importKeys(new byte[this.params.getTreeDigestSize()], this.publicKey.getPublicSeed());
        long j = (long) index;
        byte[] HMsg = this.khf.HMsg(Arrays.concatenate(build.getRandom(), this.publicKey.getRoot(), XMSSUtil.toBytesBigEndian(j, this.params.getTreeDigestSize())), bArr);
        int height = this.params.getHeight();
        int leafIndex = XMSSUtil.getLeafIndex(j, height);
        return Arrays.constantTimeAreEqual(XMSSVerifierUtil.getRootNodeFromSignature(this.wotsPlus, height, HMsg, build, (OTSHashAddress) new OTSHashAddress.Builder().withOTSAddress(index).build(), leafIndex).getValue(), this.publicKey.getRoot());
    }
}

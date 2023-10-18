package org.bouncycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.bouncycastle.util.Arrays;

public class GMSSSigner implements MessageSigner {
    private byte[][][] currentAuthPaths;
    private GMSSDigestProvider digestProvider;
    private GMSSParameters gmssPS;
    private GMSSRandom gmssRandom;
    private GMSSUtil gmssUtil = new GMSSUtil();
    private int[] index;
    GMSSKeyParameters key;
    private int mdLength;
    private Digest messDigestOTS;
    private Digest messDigestTrees;
    private int numLayer;
    private WinternitzOTSignature ots;
    private byte[] pubKeyBytes;
    private SecureRandom random;
    private byte[][] subtreeRootSig;

    public GMSSSigner(GMSSDigestProvider gMSSDigestProvider) {
        this.digestProvider = gMSSDigestProvider;
        Digest digest = gMSSDigestProvider.get();
        this.messDigestTrees = digest;
        this.messDigestOTS = digest;
        this.mdLength = digest.getDigestSize();
        this.gmssRandom = new GMSSRandom(this.messDigestTrees);
    }

    private void initSign() {
        int i;
        this.messDigestTrees.reset();
        GMSSPrivateKeyParameters gMSSPrivateKeyParameters = (GMSSPrivateKeyParameters) this.key;
        if (gMSSPrivateKeyParameters.isUsed()) {
            throw new IllegalStateException("Private key already used");
        } else if (gMSSPrivateKeyParameters.getIndex(0) < gMSSPrivateKeyParameters.getNumLeafs(0)) {
            GMSSParameters parameters = gMSSPrivateKeyParameters.getParameters();
            this.gmssPS = parameters;
            this.numLayer = parameters.getNumOfLayers();
            byte[] bArr = gMSSPrivateKeyParameters.getCurrentSeeds()[this.numLayer - 1];
            int i2 = this.mdLength;
            byte[] bArr2 = new byte[i2];
            byte[] bArr3 = new byte[i2];
            System.arraycopy(bArr, 0, bArr3, 0, i2);
            this.ots = new WinternitzOTSignature(this.gmssRandom.nextSeed(bArr3), this.digestProvider.get(), this.gmssPS.getWinternitzParameter()[this.numLayer - 1]);
            byte[][][] currentAuthPaths2 = gMSSPrivateKeyParameters.getCurrentAuthPaths();
            this.currentAuthPaths = new byte[this.numLayer][][];
            int i3 = 0;
            while (true) {
                i = this.numLayer;
                if (i3 >= i) {
                    break;
                }
                byte[][][] bArr4 = this.currentAuthPaths;
                int length = currentAuthPaths2[i3].length;
                int[] iArr = new int[2];
                iArr[1] = this.mdLength;
                iArr[0] = length;
                bArr4[i3] = (byte[][]) Array.newInstance(Byte.TYPE, iArr);
                int i4 = 0;
                while (true) {
                    byte[][] bArr5 = currentAuthPaths2[i3];
                    if (i4 >= bArr5.length) {
                        break;
                    }
                    System.arraycopy(bArr5[i4], 0, this.currentAuthPaths[i3][i4], 0, this.mdLength);
                    i4++;
                }
                i3++;
            }
            this.index = new int[i];
            System.arraycopy(gMSSPrivateKeyParameters.getIndex(), 0, this.index, 0, this.numLayer);
            this.subtreeRootSig = new byte[(this.numLayer - 1)][];
            for (int i5 = 0; i5 < this.numLayer - 1; i5++) {
                byte[] subtreeRootSig2 = gMSSPrivateKeyParameters.getSubtreeRootSig(i5);
                byte[][] bArr6 = this.subtreeRootSig;
                byte[] bArr7 = new byte[subtreeRootSig2.length];
                bArr6[i5] = bArr7;
                System.arraycopy(subtreeRootSig2, 0, bArr7, 0, subtreeRootSig2.length);
            }
            gMSSPrivateKeyParameters.markUsed();
        } else {
            throw new IllegalStateException("No more signatures can be generated");
        }
    }

    private void initVerify() {
        this.messDigestTrees.reset();
        GMSSPublicKeyParameters gMSSPublicKeyParameters = (GMSSPublicKeyParameters) this.key;
        this.pubKeyBytes = gMSSPublicKeyParameters.getPublicKey();
        GMSSParameters parameters = gMSSPublicKeyParameters.getParameters();
        this.gmssPS = parameters;
        this.numLayer = parameters.getNumOfLayers();
    }

    public byte[] generateSignature(byte[] bArr) {
        byte[] bArr2 = new byte[this.mdLength];
        byte[] signature = this.ots.getSignature(bArr);
        byte[] concatenateArray = this.gmssUtil.concatenateArray(this.currentAuthPaths[this.numLayer - 1]);
        byte[] intToBytesLittleEndian = this.gmssUtil.intToBytesLittleEndian(this.index[this.numLayer - 1]);
        int length = intToBytesLittleEndian.length + signature.length + concatenateArray.length;
        byte[] bArr3 = new byte[length];
        System.arraycopy(intToBytesLittleEndian, 0, bArr3, 0, intToBytesLittleEndian.length);
        System.arraycopy(signature, 0, bArr3, intToBytesLittleEndian.length, signature.length);
        System.arraycopy(concatenateArray, 0, bArr3, intToBytesLittleEndian.length + signature.length, concatenateArray.length);
        byte[] bArr4 = new byte[0];
        for (int i = (this.numLayer - 1) - 1; i >= 0; i--) {
            byte[] concatenateArray2 = this.gmssUtil.concatenateArray(this.currentAuthPaths[i]);
            byte[] intToBytesLittleEndian2 = this.gmssUtil.intToBytesLittleEndian(this.index[i]);
            int length2 = bArr4.length;
            byte[] bArr5 = new byte[length2];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            bArr4 = new byte[(intToBytesLittleEndian2.length + length2 + this.subtreeRootSig[i].length + concatenateArray2.length)];
            System.arraycopy(bArr5, 0, bArr4, 0, length2);
            System.arraycopy(intToBytesLittleEndian2, 0, bArr4, length2, intToBytesLittleEndian2.length);
            byte[] bArr6 = this.subtreeRootSig[i];
            System.arraycopy(bArr6, 0, bArr4, intToBytesLittleEndian2.length + length2, bArr6.length);
            System.arraycopy(concatenateArray2, 0, bArr4, length2 + intToBytesLittleEndian2.length + this.subtreeRootSig[i].length, concatenateArray2.length);
        }
        byte[] bArr7 = new byte[(bArr4.length + length)];
        System.arraycopy(bArr3, 0, bArr7, 0, length);
        System.arraycopy(bArr4, 0, bArr7, length, bArr4.length);
        return bArr7;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.random = parametersWithRandom.getRandom();
                this.key = (GMSSPrivateKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.random = CryptoServicesRegistrar.getSecureRandom();
                this.key = (GMSSPrivateKeyParameters) cipherParameters;
            }
            initSign();
            return;
        }
        this.key = (GMSSPublicKeyParameters) cipherParameters;
        initVerify();
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        this.messDigestOTS.reset();
        int i = 0;
        for (int i2 = this.numLayer - 1; i2 >= 0; i2--) {
            WinternitzOTSVerify winternitzOTSVerify = new WinternitzOTSVerify(this.digestProvider.get(), this.gmssPS.getWinternitzParameter()[i2]);
            int signatureLength = winternitzOTSVerify.getSignatureLength();
            int bytesToIntLittleEndian = this.gmssUtil.bytesToIntLittleEndian(bArr2, i);
            int i3 = i + 4;
            byte[] bArr3 = new byte[signatureLength];
            System.arraycopy(bArr2, i3, bArr3, 0, signatureLength);
            i = i3 + signatureLength;
            bArr = winternitzOTSVerify.Verify(bArr, bArr3);
            if (bArr == null) {
                System.err.println("OTS Public Key is null in GMSSSignature.verify");
                return false;
            }
            int i4 = this.gmssPS.getHeightOfTrees()[i2];
            int[] iArr = new int[2];
            iArr[1] = this.mdLength;
            iArr[0] = i4;
            byte[][] bArr4 = (byte[][]) Array.newInstance(Byte.TYPE, iArr);
            for (byte[] arraycopy : bArr4) {
                System.arraycopy(bArr2, i, arraycopy, 0, this.mdLength);
                i += this.mdLength;
            }
            byte[] bArr5 = new byte[this.mdLength];
            int length = (1 << bArr4.length) + bytesToIntLittleEndian;
            for (int i5 = 0; i5 < bArr4.length; i5++) {
                int i6 = this.mdLength;
                int i7 = i6 << 1;
                byte[] bArr6 = new byte[i7];
                if (length % 2 == 0) {
                    System.arraycopy(bArr, 0, bArr6, 0, i6);
                    byte[] bArr7 = bArr4[i5];
                    int i8 = this.mdLength;
                    System.arraycopy(bArr7, 0, bArr6, i8, i8);
                    length /= 2;
                } else {
                    System.arraycopy(bArr4[i5], 0, bArr6, 0, i6);
                    System.arraycopy(bArr, 0, bArr6, this.mdLength, bArr.length);
                    length = (length - 1) / 2;
                }
                this.messDigestTrees.update(bArr6, 0, i7);
                bArr = new byte[this.messDigestTrees.getDigestSize()];
                this.messDigestTrees.doFinal(bArr, 0);
            }
        }
        return Arrays.areEqual(this.pubKeyBytes, bArr);
    }
}

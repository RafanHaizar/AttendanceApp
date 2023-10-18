package org.bouncycastle.pqc.crypto.gmss;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

public class GMSSLeaf {
    private byte[] concHashs;
    private GMSSRandom gmssRandom;

    /* renamed from: i */
    private int f871i;

    /* renamed from: j */
    private int f872j;
    private int keysize;
    private byte[] leaf;
    private int mdsize;
    private Digest messDigestOTS;
    byte[] privateKeyOTS;
    private byte[] seed;
    private int steps;
    private int two_power_w;

    /* renamed from: w */
    private int f873w;

    GMSSLeaf(Digest digest, int i, int i2) {
        this.f873w = i;
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        int digestSize = this.messDigestOTS.getDigestSize();
        this.mdsize = digestSize;
        double d = (double) (digestSize << 3);
        double d2 = (double) i;
        Double.isNaN(d);
        Double.isNaN(d2);
        int ceil = (int) Math.ceil(d / d2);
        double log = (double) getLog((ceil << i) + 1);
        Double.isNaN(log);
        Double.isNaN(d2);
        int ceil2 = ceil + ((int) Math.ceil(log / d2));
        this.keysize = ceil2;
        int i3 = 1 << i;
        this.two_power_w = i3;
        double d3 = (double) (((i3 - 1) * ceil2) + 1 + ceil2);
        double d4 = (double) i2;
        Double.isNaN(d3);
        Double.isNaN(d4);
        this.steps = (int) Math.ceil(d3 / d4);
        int i4 = this.mdsize;
        this.seed = new byte[i4];
        this.leaf = new byte[i4];
        this.privateKeyOTS = new byte[i4];
        this.concHashs = new byte[(i4 * this.keysize)];
    }

    public GMSSLeaf(Digest digest, int i, int i2, byte[] bArr) {
        this.f873w = i;
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        int digestSize = this.messDigestOTS.getDigestSize();
        this.mdsize = digestSize;
        double d = (double) (digestSize << 3);
        double d2 = (double) i;
        Double.isNaN(d);
        Double.isNaN(d2);
        int ceil = (int) Math.ceil(d / d2);
        double log = (double) getLog((ceil << i) + 1);
        Double.isNaN(log);
        Double.isNaN(d2);
        int ceil2 = ceil + ((int) Math.ceil(log / d2));
        this.keysize = ceil2;
        int i3 = 1 << i;
        this.two_power_w = i3;
        double d3 = (double) (((i3 - 1) * ceil2) + 1 + ceil2);
        double d4 = (double) i2;
        Double.isNaN(d3);
        Double.isNaN(d4);
        this.steps = (int) Math.ceil(d3 / d4);
        int i4 = this.mdsize;
        this.seed = new byte[i4];
        this.leaf = new byte[i4];
        this.privateKeyOTS = new byte[i4];
        this.concHashs = new byte[(i4 * this.keysize)];
        initLeafCalc(bArr);
    }

    public GMSSLeaf(Digest digest, byte[][] bArr, int[] iArr) {
        this.f871i = iArr[0];
        this.f872j = iArr[1];
        this.steps = iArr[2];
        this.f873w = iArr[3];
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        int digestSize = this.messDigestOTS.getDigestSize();
        this.mdsize = digestSize;
        double d = (double) (digestSize << 3);
        double d2 = (double) this.f873w;
        Double.isNaN(d);
        Double.isNaN(d2);
        int ceil = (int) Math.ceil(d / d2);
        double log = (double) getLog((ceil << this.f873w) + 1);
        double d3 = (double) this.f873w;
        Double.isNaN(log);
        Double.isNaN(d3);
        this.keysize = ceil + ((int) Math.ceil(log / d3));
        this.two_power_w = 1 << this.f873w;
        this.privateKeyOTS = bArr[0];
        this.seed = bArr[1];
        this.concHashs = bArr[2];
        this.leaf = bArr[3];
    }

    private GMSSLeaf(GMSSLeaf gMSSLeaf) {
        this.messDigestOTS = gMSSLeaf.messDigestOTS;
        this.mdsize = gMSSLeaf.mdsize;
        this.keysize = gMSSLeaf.keysize;
        this.gmssRandom = gMSSLeaf.gmssRandom;
        this.leaf = Arrays.clone(gMSSLeaf.leaf);
        this.concHashs = Arrays.clone(gMSSLeaf.concHashs);
        this.f871i = gMSSLeaf.f871i;
        this.f872j = gMSSLeaf.f872j;
        this.two_power_w = gMSSLeaf.two_power_w;
        this.f873w = gMSSLeaf.f873w;
        this.steps = gMSSLeaf.steps;
        this.seed = Arrays.clone(gMSSLeaf.seed);
        this.privateKeyOTS = Arrays.clone(gMSSLeaf.privateKeyOTS);
    }

    private int getLog(int i) {
        int i2 = 1;
        int i3 = 2;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    private void updateLeafCalc() {
        byte[] bArr = new byte[this.messDigestOTS.getDigestSize()];
        for (int i = 0; i < this.steps + 10000; i++) {
            int i2 = this.f871i;
            if (i2 == this.keysize && this.f872j == this.two_power_w - 1) {
                Digest digest = this.messDigestOTS;
                byte[] bArr2 = this.concHashs;
                digest.update(bArr2, 0, bArr2.length);
                byte[] bArr3 = new byte[this.messDigestOTS.getDigestSize()];
                this.leaf = bArr3;
                this.messDigestOTS.doFinal(bArr3, 0);
                return;
            }
            if (i2 == 0 || this.f872j == this.two_power_w - 1) {
                this.f871i = i2 + 1;
                this.f872j = 0;
                this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
            } else {
                Digest digest2 = this.messDigestOTS;
                byte[] bArr4 = this.privateKeyOTS;
                digest2.update(bArr4, 0, bArr4.length);
                this.privateKeyOTS = bArr;
                this.messDigestOTS.doFinal(bArr, 0);
                int i3 = this.f872j + 1;
                this.f872j = i3;
                if (i3 == this.two_power_w - 1) {
                    byte[] bArr5 = this.privateKeyOTS;
                    byte[] bArr6 = this.concHashs;
                    int i4 = this.mdsize;
                    System.arraycopy(bArr5, 0, bArr6, (this.f871i - 1) * i4, i4);
                }
            }
        }
        throw new IllegalStateException("unable to updateLeaf in steps: " + this.steps + " " + this.f871i + " " + this.f872j);
    }

    public byte[] getLeaf() {
        return Arrays.clone(this.leaf);
    }

    public byte[][] getStatByte() {
        return new byte[][]{this.privateKeyOTS, this.seed, this.concHashs, this.leaf};
    }

    public int[] getStatInt() {
        return new int[]{this.f871i, this.f872j, this.steps, this.f873w};
    }

    /* access modifiers changed from: package-private */
    public void initLeafCalc(byte[] bArr) {
        this.f871i = 0;
        this.f872j = 0;
        byte[] bArr2 = new byte[this.mdsize];
        System.arraycopy(bArr, 0, bArr2, 0, this.seed.length);
        this.seed = this.gmssRandom.nextSeed(bArr2);
    }

    /* access modifiers changed from: package-private */
    public GMSSLeaf nextLeaf() {
        GMSSLeaf gMSSLeaf = new GMSSLeaf(this);
        gMSSLeaf.updateLeafCalc();
        return gMSSLeaf;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            str = str + getStatInt()[i] + " ";
        }
        String str2 = str + " " + this.mdsize + " " + this.keysize + " " + this.two_power_w + " ";
        byte[][] statByte = getStatByte();
        for (int i2 = 0; i2 < 4; i2++) {
            str2 = (statByte[i2] != null ? new StringBuilder().append(str2).append(new String(Hex.encode(statByte[i2]))).append(" ") : new StringBuilder().append(str2).append("null ")).toString();
        }
        return str2;
    }
}

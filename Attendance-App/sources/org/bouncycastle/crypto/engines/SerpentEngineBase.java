package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public abstract class SerpentEngineBase implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;
    static final int PHI = -1640531527;
    static final int ROUNDS = 32;

    /* renamed from: X0 */
    protected int f419X0;

    /* renamed from: X1 */
    protected int f420X1;

    /* renamed from: X2 */
    protected int f421X2;

    /* renamed from: X3 */
    protected int f422X3;
    protected boolean encrypting;
    protected int[] wKey;

    SerpentEngineBase() {
    }

    protected static int rotateLeft(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    protected static int rotateRight(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    /* access modifiers changed from: protected */
    /* renamed from: LT */
    public final void mo3864LT() {
        int rotateLeft = rotateLeft(this.f419X0, 13);
        int rotateLeft2 = rotateLeft(this.f421X2, 3);
        this.f420X1 = rotateLeft((this.f420X1 ^ rotateLeft) ^ rotateLeft2, 1);
        int rotateLeft3 = rotateLeft((this.f422X3 ^ rotateLeft2) ^ (rotateLeft << 3), 7);
        this.f422X3 = rotateLeft3;
        this.f419X0 = rotateLeft((rotateLeft ^ this.f420X1) ^ rotateLeft3, 5);
        this.f421X2 = rotateLeft((this.f422X3 ^ rotateLeft2) ^ (this.f420X1 << 7), 22);
    }

    /* access modifiers changed from: protected */
    public abstract void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2);

    /* access modifiers changed from: protected */
    public abstract void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2);

    public String getAlgorithmName() {
        return "Serpent";
    }

    public int getBlockSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public final void ib0(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i2 ^ i;
        int i7 = (i5 | i6) ^ i4;
        int i8 = i3 ^ i7;
        int i9 = i6 ^ i8;
        this.f421X2 = i9;
        int i10 = (i6 & i4) ^ i5;
        int i11 = (i9 & i10) ^ i7;
        this.f420X1 = i11;
        int i12 = (i & i7) ^ (i11 | i8);
        this.f422X3 = i12;
        this.f419X0 = i12 ^ (i10 ^ i8);
    }

    /* access modifiers changed from: protected */
    public final void ib1(int i, int i2, int i3, int i4) {
        int i5 = i4 ^ i2;
        int i6 = i ^ (i2 & i5);
        int i7 = i5 ^ i6;
        int i8 = i3 ^ i7;
        this.f422X3 = i8;
        int i9 = i2 ^ (i5 & i6);
        int i10 = i6 ^ (i8 | i9);
        this.f420X1 = i10;
        int i11 = i10 ^ -1;
        int i12 = i9 ^ i8;
        this.f419X0 = i11 ^ i12;
        this.f421X2 = (i11 | i12) ^ i7;
    }

    /* access modifiers changed from: protected */
    public final void ib2(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i4;
        int i6 = i ^ i3;
        int i7 = i3 ^ i5;
        int i8 = (i2 & i7) ^ i6;
        this.f419X0 = i8;
        int i9 = (((i | (i5 ^ -1)) ^ i4) | i6) ^ i5;
        this.f422X3 = i9;
        int i10 = i7 ^ -1;
        int i11 = i9 | i8;
        this.f420X1 = i10 ^ i11;
        this.f421X2 = (i11 ^ i6) ^ (i4 & i10);
    }

    /* access modifiers changed from: protected */
    public final void ib3(int i, int i2, int i3, int i4) {
        int i5 = i | i2;
        int i6 = i2 ^ i3;
        int i7 = i ^ (i2 & i6);
        int i8 = i3 ^ i7;
        int i9 = i4 | i7;
        int i10 = i6 ^ i9;
        this.f419X0 = i10;
        int i11 = (i9 | i6) ^ i4;
        this.f421X2 = i8 ^ i11;
        int i12 = i5 ^ i11;
        int i13 = i7 ^ (i10 & i12);
        this.f422X3 = i13;
        this.f420X1 = i13 ^ (i12 ^ i10);
    }

    /* access modifiers changed from: protected */
    public final void ib4(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ ((i3 | i4) & i);
        int i6 = i3 ^ (i & i5);
        int i7 = i4 ^ i6;
        this.f420X1 = i7;
        int i8 = i ^ -1;
        int i9 = (i6 & i7) ^ i5;
        this.f422X3 = i9;
        int i10 = i4 ^ (i7 | i8);
        this.f419X0 = i9 ^ i10;
        this.f421X2 = (i8 ^ i7) ^ (i5 & i10);
    }

    /* access modifiers changed from: protected */
    public final void ib5(int i, int i2, int i3, int i4) {
        int i5 = i3 ^ -1;
        int i6 = (i2 & i5) ^ i4;
        int i7 = i & i6;
        int i8 = (i2 ^ i5) ^ i7;
        this.f422X3 = i8;
        int i9 = i8 | i2;
        this.f420X1 = i6 ^ (i & i9);
        int i10 = i4 | i;
        this.f419X0 = (i5 ^ i9) ^ i10;
        this.f421X2 = ((i ^ i3) | i7) ^ (i2 & i10);
    }

    /* access modifiers changed from: protected */
    public final void ib6(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i3 ^ i6;
        int i8 = (i3 | i5) ^ i4;
        this.f420X1 = i7 ^ i8;
        int i9 = i6 ^ (i7 & i8);
        int i10 = i8 ^ (i2 | i9);
        this.f422X3 = i10;
        int i11 = i2 | i10;
        this.f419X0 = i9 ^ i11;
        this.f421X2 = (i4 & i5) ^ (i11 ^ i7);
    }

    /* access modifiers changed from: protected */
    public final void ib7(int i, int i2, int i3, int i4) {
        int i5 = (i & i2) | i3;
        int i6 = (i | i2) & i4;
        int i7 = i5 ^ i6;
        this.f422X3 = i7;
        int i8 = i2 ^ i6;
        int i9 = ((i7 ^ (i4 ^ -1)) | i8) ^ i;
        this.f420X1 = i9;
        int i10 = (i8 ^ i3) ^ (i4 | i9);
        this.f419X0 = i10;
        this.f421X2 = ((i & i7) ^ i10) ^ (i5 ^ i9);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.encrypting = z;
            this.wKey = makeWorkingKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to " + getAlgorithmName() + " init - " + cipherParameters.getClass().getName());
    }

    /* access modifiers changed from: protected */
    public final void inverseLT() {
        int rotateRight = (rotateRight(this.f421X2, 22) ^ this.f422X3) ^ (this.f420X1 << 7);
        int rotateRight2 = rotateRight(this.f419X0, 5) ^ this.f420X1;
        int i = this.f422X3;
        int i2 = rotateRight2 ^ i;
        int rotateRight3 = rotateRight(i, 7);
        int rotateRight4 = rotateRight(this.f420X1, 1);
        this.f422X3 = (rotateRight3 ^ rotateRight) ^ (i2 << 3);
        this.f420X1 = (rotateRight4 ^ i2) ^ rotateRight;
        this.f421X2 = rotateRight(rotateRight, 3);
        this.f419X0 = rotateRight(i2, 13);
    }

    /* access modifiers changed from: protected */
    public abstract int[] makeWorkingKey(byte[] bArr);

    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.wKey == null) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 16;
        } else {
            decryptBlock(bArr, i, bArr2, i2);
            return 16;
        }
    }

    public void reset() {
    }

    /* access modifiers changed from: protected */
    public final void sb0(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i3 ^ i5;
        int i7 = i2 ^ i6;
        int i8 = (i4 & i) ^ i7;
        this.f422X3 = i8;
        int i9 = i ^ (i2 & i5);
        this.f421X2 = (i3 | i9) ^ i7;
        int i10 = (i6 ^ i9) & i8;
        this.f420X1 = (i6 ^ -1) ^ i10;
        this.f419X0 = (i9 ^ -1) ^ i10;
    }

    /* access modifiers changed from: protected */
    public final void sb1(int i, int i2, int i3, int i4) {
        int i5 = (i ^ -1) ^ i2;
        int i6 = (i | i5) ^ i3;
        int i7 = i4 ^ i6;
        this.f421X2 = i7;
        int i8 = i2 ^ (i4 | i5);
        int i9 = i7 ^ i5;
        int i10 = (i6 & i8) ^ i9;
        this.f422X3 = i10;
        int i11 = i8 ^ i6;
        this.f420X1 = i10 ^ i11;
        this.f419X0 = i6 ^ (i11 & i9);
    }

    /* access modifiers changed from: protected */
    public final void sb2(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i2 ^ i4;
        int i7 = (i3 & i5) ^ i6;
        this.f419X0 = i7;
        int i8 = i3 ^ i5;
        int i9 = i2 & (i3 ^ i7);
        int i10 = i8 ^ i9;
        this.f422X3 = i10;
        int i11 = i ^ ((i9 | i4) & (i7 | i8));
        this.f421X2 = i11;
        this.f420X1 = (i11 ^ (i4 | i5)) ^ (i6 ^ i10);
    }

    /* access modifiers changed from: protected */
    public final void sb3(int i, int i2, int i3, int i4) {
        int i5 = i ^ i2;
        int i6 = i & i3;
        int i7 = i | i4;
        int i8 = i3 ^ i4;
        int i9 = i6 | (i5 & i7);
        int i10 = i8 ^ i9;
        this.f421X2 = i10;
        int i11 = (i7 ^ i2) ^ i9;
        int i12 = i5 ^ (i8 & i11);
        this.f419X0 = i12;
        int i13 = i12 & i10;
        this.f420X1 = i11 ^ i13;
        this.f422X3 = (i2 | i4) ^ (i8 ^ i13);
    }

    /* access modifiers changed from: protected */
    public final void sb4(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i3 ^ (i4 & i5);
        int i7 = i2 | i6;
        this.f422X3 = i5 ^ i7;
        int i8 = i2 ^ -1;
        int i9 = (i5 | i8) ^ i6;
        this.f419X0 = i9;
        int i10 = i8 ^ i5;
        int i11 = (i7 & i10) ^ (i9 & i);
        this.f421X2 = i11;
        this.f420X1 = (i ^ i6) ^ (i10 & i11);
    }

    /* access modifiers changed from: protected */
    public final void sb5(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i ^ i4;
        int i8 = (i3 ^ i5) ^ (i6 | i7);
        this.f419X0 = i8;
        int i9 = i4 & i8;
        int i10 = (i6 ^ i8) ^ i9;
        this.f420X1 = i10;
        int i11 = i7 ^ (i8 | i5);
        this.f421X2 = (i6 | i9) ^ i11;
        this.f422X3 = (i11 & i10) ^ (i2 ^ i9);
    }

    /* access modifiers changed from: protected */
    public final void sb6(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i4;
        int i7 = i2 ^ i6;
        int i8 = i3 ^ (i5 | i6);
        int i9 = i2 ^ i8;
        this.f420X1 = i9;
        int i10 = (i6 | i9) ^ i4;
        int i11 = (i8 & i10) ^ i7;
        this.f421X2 = i11;
        int i12 = i10 ^ i8;
        this.f419X0 = i11 ^ i12;
        this.f422X3 = (i12 & i7) ^ (i8 ^ -1);
    }

    /* access modifiers changed from: protected */
    public final void sb7(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i3;
        int i6 = (i3 & i5) ^ i4;
        int i7 = i ^ i6;
        int i8 = i2 ^ ((i4 | i5) & i7);
        this.f420X1 = i8;
        int i9 = (i & i7) ^ i5;
        this.f422X3 = i9;
        int i10 = (i8 | i6) ^ i7;
        int i11 = i6 ^ (i9 & i10);
        this.f421X2 = i11;
        this.f419X0 = (i9 & i11) ^ (i10 ^ -1);
    }
}

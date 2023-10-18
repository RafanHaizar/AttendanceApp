package org.bouncycastle.crypto.engines;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.util.Pack;

public class ChaCha7539Engine extends Salsa20Engine {
    /* access modifiers changed from: protected */
    public void advanceCounter() {
        int[] iArr = this.engineState;
        int i = iArr[12] + 1;
        iArr[12] = i;
        if (i == 0) {
            throw new IllegalStateException("attempt to increase counter past 2^32.");
        }
    }

    /* access modifiers changed from: protected */
    public void advanceCounter(long j) {
        int i = (int) j;
        if (((int) (j >>> 32)) <= 0) {
            int i2 = this.engineState[12];
            int[] iArr = this.engineState;
            iArr[12] = iArr[12] + i;
            if (i2 != 0 && this.engineState[12] < i2) {
                throw new IllegalStateException("attempt to increase counter past 2^32.");
            }
            return;
        }
        throw new IllegalStateException("attempt to increase counter past 2^32.");
    }

    /* access modifiers changed from: protected */
    public void generateKeyStream(byte[] bArr) {
        ChaChaEngine.chachaCore(this.rounds, this.engineState, this.f418x);
        Pack.intToLittleEndian(this.f418x, bArr, 0);
    }

    public String getAlgorithmName() {
        return "ChaCha7539";
    }

    /* access modifiers changed from: protected */
    public long getCounter() {
        return ((long) this.engineState[12]) & BodyPartID.bodyIdMax;
    }

    /* access modifiers changed from: protected */
    public int getNonceSize() {
        return 12;
    }

    /* access modifiers changed from: protected */
    public void resetCounter() {
        this.engineState[12] = 0;
    }

    /* access modifiers changed from: protected */
    public void retreatCounter() {
        if (this.engineState[12] != 0) {
            int[] iArr = this.engineState;
            iArr[12] = iArr[12] - 1;
            return;
        }
        throw new IllegalStateException("attempt to reduce counter past zero.");
    }

    /* access modifiers changed from: protected */
    public void retreatCounter(long j) {
        int i = (int) j;
        if (((int) (j >>> 32)) != 0) {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        } else if ((((long) this.engineState[12]) & BodyPartID.bodyIdMax) >= (BodyPartID.bodyIdMax & ((long) i))) {
            int[] iArr = this.engineState;
            iArr[12] = iArr[12] - i;
        } else {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
    }

    /* access modifiers changed from: protected */
    public void setKey(byte[] bArr, byte[] bArr2) {
        if (bArr != null) {
            if (bArr.length == 32) {
                packTauOrSigma(bArr.length, this.engineState, 0);
                Pack.littleEndianToInt(bArr, 0, this.engineState, 4, 8);
            } else {
                throw new IllegalArgumentException(getAlgorithmName() + " requires 256 bit key");
            }
        }
        Pack.littleEndianToInt(bArr2, 0, this.engineState, 13, 3);
    }
}

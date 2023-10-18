package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.Zuc128CoreEngine;

public final class Zuc128Mac implements Mac {
    private static final int TOPBIT = 128;
    private int theByteIndex;
    private final InternalZuc128Engine theEngine = new InternalZuc128Engine();
    private final int[] theKeyStream = new int[2];
    private int theMac;
    private Zuc128CoreEngine theState;
    private int theWordIndex;

    private static class InternalZuc128Engine extends Zuc128CoreEngine {
        private InternalZuc128Engine() {
        }

        /* access modifiers changed from: package-private */
        public int createKeyStreamWord() {
            return super.makeKeyStreamWord();
        }
    }

    private int getFinalWord() {
        if (this.theByteIndex != 0) {
            return this.theEngine.createKeyStreamWord();
        }
        int[] iArr = this.theKeyStream;
        int length = (this.theWordIndex + 1) % iArr.length;
        this.theWordIndex = length;
        return iArr[length];
    }

    private int getKeyStreamWord(int i) {
        int[] iArr = this.theKeyStream;
        int i2 = this.theWordIndex;
        int i3 = iArr[i2];
        if (i == 0) {
            return i3;
        }
        int i4 = iArr[(i2 + 1) % iArr.length];
        return (i4 >>> (32 - i)) | (i3 << i);
    }

    private void initKeyStream() {
        int i = 0;
        this.theMac = 0;
        while (true) {
            int[] iArr = this.theKeyStream;
            if (i < iArr.length - 1) {
                iArr[i] = this.theEngine.createKeyStreamWord();
                i++;
            } else {
                this.theWordIndex = iArr.length - 1;
                this.theByteIndex = 3;
                return;
            }
        }
    }

    private void shift4NextByte() {
        int i = (this.theByteIndex + 1) % 4;
        this.theByteIndex = i;
        if (i == 0) {
            this.theKeyStream[this.theWordIndex] = this.theEngine.createKeyStreamWord();
            this.theWordIndex = (this.theWordIndex + 1) % this.theKeyStream.length;
        }
    }

    private void updateMac(int i) {
        this.theMac = getKeyStreamWord(i) ^ this.theMac;
    }

    public int doFinal(byte[] bArr, int i) {
        shift4NextByte();
        int keyStreamWord = this.theMac ^ getKeyStreamWord(this.theByteIndex * 8);
        this.theMac = keyStreamWord;
        int finalWord = keyStreamWord ^ getFinalWord();
        this.theMac = finalWord;
        Zuc128CoreEngine.encode32be(finalWord, bArr, i);
        reset();
        return getMacSize();
    }

    public String getAlgorithmName() {
        return "Zuc128Mac";
    }

    public int getMacSize() {
        return 4;
    }

    public void init(CipherParameters cipherParameters) {
        this.theEngine.init(true, cipherParameters);
        this.theState = (Zuc128CoreEngine) this.theEngine.copy();
        initKeyStream();
    }

    public void reset() {
        Zuc128CoreEngine zuc128CoreEngine = this.theState;
        if (zuc128CoreEngine != null) {
            this.theEngine.reset(zuc128CoreEngine);
        }
        initKeyStream();
    }

    public void update(byte b) {
        shift4NextByte();
        int i = this.theByteIndex * 8;
        int i2 = 128;
        int i3 = 0;
        while (i2 > 0) {
            if ((b & i2) != 0) {
                updateMac(i + i3);
            }
            i2 >>= 1;
            i3++;
        }
    }

    public void update(byte[] bArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            update(bArr[i + i3]);
        }
    }
}

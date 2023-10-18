package org.bouncycastle.crypto.prng;

public class ReversedWindowGenerator implements RandomGenerator {
    private final RandomGenerator generator;
    private byte[] window;
    private int windowCount;

    public ReversedWindowGenerator(RandomGenerator randomGenerator, int i) {
        if (randomGenerator == null) {
            throw new IllegalArgumentException("generator cannot be null");
        } else if (i >= 2) {
            this.generator = randomGenerator;
            this.window = new byte[i];
        } else {
            throw new IllegalArgumentException("windowSize must be at least 2");
        }
    }

    private void doNextBytes(byte[] bArr, int i, int i2) {
        synchronized (this) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.windowCount < 1) {
                    RandomGenerator randomGenerator = this.generator;
                    byte[] bArr2 = this.window;
                    randomGenerator.nextBytes(bArr2, 0, bArr2.length);
                    this.windowCount = this.window.length;
                }
                byte[] bArr3 = this.window;
                int i4 = this.windowCount - 1;
                this.windowCount = i4;
                bArr[i3 + i] = bArr3[i4];
            }
        }
    }

    public void addSeedMaterial(long j) {
        synchronized (this) {
            this.windowCount = 0;
            this.generator.addSeedMaterial(j);
        }
    }

    public void addSeedMaterial(byte[] bArr) {
        synchronized (this) {
            this.windowCount = 0;
            this.generator.addSeedMaterial(bArr);
        }
    }

    public void nextBytes(byte[] bArr) {
        doNextBytes(bArr, 0, bArr.length);
    }

    public void nextBytes(byte[] bArr, int i, int i2) {
        doNextBytes(bArr, i, i2);
    }
}

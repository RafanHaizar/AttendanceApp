package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

public final class KDFFeedbackParameters implements DerivationParameters {
    private static final int UNUSED_R = -1;
    private final byte[] fixedInputData;

    /* renamed from: iv */
    private final byte[] f598iv;

    /* renamed from: ki */
    private final byte[] f599ki;

    /* renamed from: r */
    private final int f600r;
    private final boolean useCounter;

    private KDFFeedbackParameters(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, boolean z) {
        if (bArr != null) {
            this.f599ki = Arrays.clone(bArr);
            if (bArr3 == null) {
                this.fixedInputData = new byte[0];
            } else {
                this.fixedInputData = Arrays.clone(bArr3);
            }
            this.f600r = i;
            if (bArr2 == null) {
                this.f598iv = new byte[0];
            } else {
                this.f598iv = Arrays.clone(bArr2);
            }
            this.useCounter = z;
            return;
        }
        throw new IllegalArgumentException("A KDF requires Ki (a seed) as input");
    }

    public static KDFFeedbackParameters createWithCounter(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        if (i == 8 || i == 16 || i == 24 || i == 32) {
            return new KDFFeedbackParameters(bArr, bArr2, bArr3, i, true);
        }
        throw new IllegalArgumentException("Length of counter should be 8, 16, 24 or 32");
    }

    public static KDFFeedbackParameters createWithoutCounter(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return new KDFFeedbackParameters(bArr, bArr2, bArr3, -1, false);
    }

    public byte[] getFixedInputData() {
        return Arrays.clone(this.fixedInputData);
    }

    public byte[] getIV() {
        return this.f598iv;
    }

    public byte[] getKI() {
        return this.f599ki;
    }

    public int getR() {
        return this.f600r;
    }

    public boolean useCounter() {
        return this.useCounter;
    }
}

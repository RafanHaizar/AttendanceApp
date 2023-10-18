package org.bouncycastle.crypto.prng.drbg;

import java.util.Hashtable;
import kotlin.UByte;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.pqc.crypto.sphincs.SPHINCSKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

public class HashSP800DRBG implements SP80090DRBG {
    private static final int MAX_BITS_REQUEST = 262144;
    private static final byte[] ONE = {1};
    private static final long RESEED_MAX = 140737488355328L;
    private static final Hashtable seedlens;

    /* renamed from: _C */
    private byte[] f628_C;

    /* renamed from: _V */
    private byte[] f629_V;
    private Digest _digest;
    private EntropySource _entropySource;
    private long _reseedCounter;
    private int _securityStrength;
    private int _seedLength;

    static {
        Hashtable hashtable = new Hashtable();
        seedlens = hashtable;
        hashtable.put("SHA-1", Integers.valueOf(440));
        hashtable.put(McElieceCCA2KeyGenParameterSpec.SHA224, Integers.valueOf(440));
        hashtable.put("SHA-256", Integers.valueOf(440));
        hashtable.put(SPHINCSKeyParameters.SHA512_256, Integers.valueOf(440));
        hashtable.put("SHA-512/224", Integers.valueOf(440));
        hashtable.put("SHA-384", Integers.valueOf(888));
        hashtable.put("SHA-512", Integers.valueOf(888));
    }

    public HashSP800DRBG(Digest digest, int i, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        if (i > Utils.getMaxSecurityStrength(digest)) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (entropySource.entropySize() >= i) {
            this._digest = digest;
            this._entropySource = entropySource;
            this._securityStrength = i;
            this._seedLength = ((Integer) seedlens.get(digest.getAlgorithmName())).intValue();
            byte[] hash_df = Utils.hash_df(this._digest, Arrays.concatenate(getEntropy(), bArr2, bArr), this._seedLength);
            this.f629_V = hash_df;
            byte[] bArr3 = new byte[(hash_df.length + 1)];
            System.arraycopy(hash_df, 0, bArr3, 1, hash_df.length);
            this.f628_C = Utils.hash_df(this._digest, bArr3, this._seedLength);
            this._reseedCounter = 1;
        } else {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        }
    }

    private void addTo(byte[] bArr, byte[] bArr2) {
        byte b = 0;
        for (int i = 1; i <= bArr2.length; i++) {
            int i2 = (bArr[bArr.length - i] & UByte.MAX_VALUE) + (bArr2[bArr2.length - i] & UByte.MAX_VALUE) + b;
            b = i2 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - i] = (byte) i2;
        }
        for (int length = bArr2.length + 1; length <= bArr.length; length++) {
            int i3 = (bArr[bArr.length - length] & UByte.MAX_VALUE) + b;
            b = i3 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - length] = (byte) i3;
        }
    }

    private void doHash(byte[] bArr, byte[] bArr2) {
        this._digest.update(bArr, 0, bArr.length);
        this._digest.doFinal(bArr2, 0);
    }

    private byte[] getEntropy() {
        byte[] entropy = this._entropySource.getEntropy();
        if (entropy.length >= (this._securityStrength + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }

    private byte[] hash(byte[] bArr) {
        byte[] bArr2 = new byte[this._digest.getDigestSize()];
        doHash(bArr, bArr2);
        return bArr2;
    }

    private byte[] hashgen(byte[] bArr, int i) {
        int i2 = i / 8;
        int digestSize = i2 / this._digest.getDigestSize();
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[i2];
        int digestSize2 = this._digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize2];
        for (int i3 = 0; i3 <= digestSize; i3++) {
            doHash(bArr2, bArr4);
            int i4 = i3 * digestSize2;
            int i5 = i2 - i4;
            if (i5 > digestSize2) {
                i5 = digestSize2;
            }
            System.arraycopy(bArr4, 0, bArr3, i4, i5);
            addTo(bArr2, ONE);
        }
        return bArr3;
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        int length = bArr.length * 8;
        if (length > 262144) {
            throw new IllegalArgumentException("Number of bits per request limited to 262144");
        } else if (this._reseedCounter > RESEED_MAX) {
            return -1;
        } else {
            if (z) {
                reseed(bArr2);
                bArr2 = null;
            }
            if (bArr2 != null) {
                byte[] bArr3 = this.f629_V;
                byte[] bArr4 = new byte[(bArr3.length + 1 + bArr2.length)];
                bArr4[0] = 2;
                System.arraycopy(bArr3, 0, bArr4, 1, bArr3.length);
                System.arraycopy(bArr2, 0, bArr4, this.f629_V.length + 1, bArr2.length);
                addTo(this.f629_V, hash(bArr4));
            }
            byte[] hashgen = hashgen(this.f629_V, length);
            byte[] bArr5 = this.f629_V;
            byte[] bArr6 = new byte[(bArr5.length + 1)];
            System.arraycopy(bArr5, 0, bArr6, 1, bArr5.length);
            bArr6[0] = 3;
            addTo(this.f629_V, hash(bArr6));
            addTo(this.f629_V, this.f628_C);
            long j = this._reseedCounter;
            addTo(this.f629_V, new byte[]{(byte) ((int) (j >> 24)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 8)), (byte) ((int) j)});
            this._reseedCounter++;
            System.arraycopy(hashgen, 0, bArr, 0, bArr.length);
            return length;
        }
    }

    public int getBlockSize() {
        return this._digest.getDigestSize() * 8;
    }

    public void reseed(byte[] bArr) {
        byte[] hash_df = Utils.hash_df(this._digest, Arrays.concatenate(ONE, this.f629_V, getEntropy(), bArr), this._seedLength);
        this.f629_V = hash_df;
        byte[] bArr2 = new byte[(hash_df.length + 1)];
        bArr2[0] = 0;
        System.arraycopy(hash_df, 0, bArr2, 1, hash_df.length);
        this.f628_C = Utils.hash_df(this._digest, bArr2, this._seedLength);
        this._reseedCounter = 1;
    }
}

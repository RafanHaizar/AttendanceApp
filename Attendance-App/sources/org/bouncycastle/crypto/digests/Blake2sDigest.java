package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class Blake2sDigest implements ExtendedDigest {
    private static final int BLOCK_LENGTH_BYTES = 64;
    private static final int ROUNDS = 10;
    private static final int[] blake2s_IV = {1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225};
    private static final byte[][] blake2s_sigma = {new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, new byte[]{14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3}, new byte[]{11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4}, new byte[]{7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8}, new byte[]{9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13}, new byte[]{2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9}, new byte[]{12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11}, new byte[]{13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10}, new byte[]{6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5}, new byte[]{10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0}};
    private byte[] buffer;
    private int bufferPos;
    private int[] chainValue;
    private int depth;
    private int digestLength;

    /* renamed from: f0 */
    private int f172f0;
    private int fanout;
    private int innerHashLength;
    private int[] internalState;
    private byte[] key;
    private int keyLength;
    private int leafLength;
    private int nodeDepth;
    private long nodeOffset;
    private byte[] personalization;
    private byte[] salt;

    /* renamed from: t0 */
    private int f173t0;

    /* renamed from: t1 */
    private int f174t1;

    public Blake2sDigest() {
        this(256);
    }

    public Blake2sDigest(int i) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        if (i < 8 || i > 256 || i % 8 != 0) {
            throw new IllegalArgumentException("BLAKE2s digest bit length must be a multiple of 8 and not greater than 256");
        }
        this.digestLength = i / 8;
        init((byte[]) null, (byte[]) null, (byte[]) null);
    }

    Blake2sDigest(int i, int i2, long j) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        this.digestLength = i;
        this.nodeOffset = j;
        this.fanout = 0;
        this.depth = 0;
        this.leafLength = i2;
        this.innerHashLength = i2;
        this.nodeDepth = 0;
        init((byte[]) null, (byte[]) null, (byte[]) null);
    }

    Blake2sDigest(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, long j) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        this.digestLength = i;
        this.nodeOffset = j;
        init(bArr2, bArr3, bArr);
    }

    public Blake2sDigest(Blake2sDigest blake2sDigest) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        this.bufferPos = blake2sDigest.bufferPos;
        this.buffer = Arrays.clone(blake2sDigest.buffer);
        this.keyLength = blake2sDigest.keyLength;
        this.key = Arrays.clone(blake2sDigest.key);
        this.digestLength = blake2sDigest.digestLength;
        this.internalState = Arrays.clone(this.internalState);
        this.chainValue = Arrays.clone(blake2sDigest.chainValue);
        this.f173t0 = blake2sDigest.f173t0;
        this.f174t1 = blake2sDigest.f174t1;
        this.f172f0 = blake2sDigest.f172f0;
        this.salt = Arrays.clone(blake2sDigest.salt);
        this.personalization = Arrays.clone(blake2sDigest.personalization);
        this.fanout = blake2sDigest.fanout;
        this.depth = blake2sDigest.depth;
        this.leafLength = blake2sDigest.leafLength;
        this.nodeOffset = blake2sDigest.nodeOffset;
        this.nodeDepth = blake2sDigest.nodeDepth;
        this.innerHashLength = blake2sDigest.innerHashLength;
    }

    public Blake2sDigest(byte[] bArr) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        init((byte[]) null, (byte[]) null, bArr);
    }

    public Blake2sDigest(byte[] bArr, int i, byte[] bArr2, byte[] bArr3) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.f172f0 = 0;
        if (i < 1 || i > 32) {
            throw new IllegalArgumentException("Invalid digest length (required: 1 - 32)");
        }
        this.digestLength = i;
        init(bArr2, bArr3, bArr);
    }

    /* renamed from: G */
    private void m29G(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.internalState;
        int i7 = iArr[i3] + iArr[i4] + i;
        iArr[i3] = i7;
        iArr[i6] = rotr32(iArr[i6] ^ i7, 16);
        int[] iArr2 = this.internalState;
        int i8 = iArr2[i5] + iArr2[i6];
        iArr2[i5] = i8;
        iArr2[i4] = rotr32(i8 ^ iArr2[i4], 12);
        int[] iArr3 = this.internalState;
        int i9 = iArr3[i3] + iArr3[i4] + i2;
        iArr3[i3] = i9;
        iArr3[i6] = rotr32(iArr3[i6] ^ i9, 8);
        int[] iArr4 = this.internalState;
        int i10 = iArr4[i5] + iArr4[i6];
        iArr4[i5] = i10;
        iArr4[i4] = rotr32(i10 ^ iArr4[i4], 7);
    }

    private void compress(byte[] bArr, int i) {
        initializeInternalState();
        int[] iArr = new int[16];
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            iArr[i3] = Pack.littleEndianToInt(bArr, (i3 * 4) + i);
        }
        for (int i4 = 0; i4 < 10; i4++) {
            byte[][] bArr2 = blake2s_sigma;
            byte[] bArr3 = bArr2[i4];
            m29G(iArr[bArr3[0]], iArr[bArr3[1]], 0, 4, 8, 12);
            byte[] bArr4 = bArr2[i4];
            m29G(iArr[bArr4[2]], iArr[bArr4[3]], 1, 5, 9, 13);
            byte[] bArr5 = bArr2[i4];
            m29G(iArr[bArr5[4]], iArr[bArr5[5]], 2, 6, 10, 14);
            byte[] bArr6 = bArr2[i4];
            m29G(iArr[bArr6[6]], iArr[bArr6[7]], 3, 7, 11, 15);
            byte[] bArr7 = bArr2[i4];
            m29G(iArr[bArr7[8]], iArr[bArr7[9]], 0, 5, 10, 15);
            byte[] bArr8 = bArr2[i4];
            m29G(iArr[bArr8[10]], iArr[bArr8[11]], 1, 6, 11, 12);
            byte[] bArr9 = bArr2[i4];
            m29G(iArr[bArr9[12]], iArr[bArr9[13]], 2, 7, 8, 13);
            byte[] bArr10 = bArr2[i4];
            m29G(iArr[bArr10[14]], iArr[bArr10[15]], 3, 4, 9, 14);
        }
        while (true) {
            int[] iArr2 = this.chainValue;
            if (i2 < iArr2.length) {
                int i5 = iArr2[i2];
                int[] iArr3 = this.internalState;
                iArr2[i2] = (i5 ^ iArr3[i2]) ^ iArr3[i2 + 8];
                i2++;
            } else {
                return;
            }
        }
    }

    private void init(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.buffer = new byte[64];
        if (bArr3 != null && bArr3.length > 0) {
            if (bArr3.length <= 32) {
                byte[] bArr4 = new byte[bArr3.length];
                this.key = bArr4;
                System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                this.keyLength = bArr3.length;
                System.arraycopy(bArr3, 0, this.buffer, 0, bArr3.length);
                this.bufferPos = 64;
            } else {
                throw new IllegalArgumentException("Keys > 32 bytes are not supported");
            }
        }
        if (this.chainValue == null) {
            int[] iArr = new int[8];
            this.chainValue = iArr;
            int[] iArr2 = blake2s_IV;
            iArr[0] = iArr2[0] ^ ((this.digestLength | (this.keyLength << 8)) | ((this.fanout << 16) | (this.depth << 24)));
            iArr[1] = iArr2[1] ^ this.leafLength;
            long j = this.nodeOffset;
            iArr[2] = ((int) j) ^ iArr2[2];
            int i = iArr2[3];
            iArr[3] = ((((int) (j >> 32)) | (this.nodeDepth << 16)) | (this.innerHashLength << 24)) ^ i;
            iArr[4] = iArr2[4];
            iArr[5] = iArr2[5];
            if (bArr != null) {
                if (bArr.length == 8) {
                    byte[] bArr5 = new byte[8];
                    this.salt = bArr5;
                    System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
                    int[] iArr3 = this.chainValue;
                    iArr3[4] = iArr3[4] ^ Pack.littleEndianToInt(bArr, 0);
                    int[] iArr4 = this.chainValue;
                    iArr4[5] = Pack.littleEndianToInt(bArr, 4) ^ iArr4[5];
                } else {
                    throw new IllegalArgumentException("Salt length must be exactly 8 bytes");
                }
            }
            int[] iArr5 = this.chainValue;
            iArr5[6] = iArr2[6];
            iArr5[7] = iArr2[7];
            if (bArr2 == null) {
                return;
            }
            if (bArr2.length == 8) {
                byte[] bArr6 = new byte[8];
                this.personalization = bArr6;
                System.arraycopy(bArr2, 0, bArr6, 0, bArr2.length);
                int[] iArr6 = this.chainValue;
                iArr6[6] = iArr6[6] ^ Pack.littleEndianToInt(bArr2, 0);
                int[] iArr7 = this.chainValue;
                iArr7[7] = Pack.littleEndianToInt(bArr2, 4) ^ iArr7[7];
                return;
            }
            throw new IllegalArgumentException("Personalization length must be exactly 8 bytes");
        }
    }

    private void initializeInternalState() {
        int[] iArr = this.chainValue;
        System.arraycopy(iArr, 0, this.internalState, 0, iArr.length);
        int[] iArr2 = blake2s_IV;
        System.arraycopy(iArr2, 0, this.internalState, this.chainValue.length, 4);
        int[] iArr3 = this.internalState;
        iArr3[12] = this.f173t0 ^ iArr2[4];
        iArr3[13] = this.f174t1 ^ iArr2[5];
        iArr3[14] = this.f172f0 ^ iArr2[6];
        iArr3[15] = iArr2[7];
    }

    private int rotr32(int i, int i2) {
        return (i << (32 - i2)) | (i >>> i2);
    }

    public void clearKey() {
        byte[] bArr = this.key;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            Arrays.fill(this.buffer, (byte) 0);
        }
    }

    public void clearSalt() {
        byte[] bArr = this.salt;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
    }

    public int doFinal(byte[] bArr, int i) {
        int[] iArr;
        int i2;
        this.f172f0 = -1;
        int i3 = this.f173t0;
        int i4 = this.bufferPos;
        int i5 = i3 + i4;
        this.f173t0 = i5;
        if (i5 < 0 && i4 > (-i5)) {
            this.f174t1++;
        }
        compress(this.buffer, 0);
        Arrays.fill(this.buffer, (byte) 0);
        Arrays.fill(this.internalState, 0);
        int i6 = 0;
        while (true) {
            iArr = this.chainValue;
            if (i6 >= iArr.length || (i2 = i6 * 4) >= this.digestLength) {
                Arrays.fill(iArr, 0);
                reset();
            } else {
                byte[] intToLittleEndian = Pack.intToLittleEndian(iArr[i6]);
                int i7 = this.digestLength;
                if (i2 < i7 - 4) {
                    System.arraycopy(intToLittleEndian, 0, bArr, i2 + i, 4);
                } else {
                    System.arraycopy(intToLittleEndian, 0, bArr, i + i2, i7 - i2);
                }
                i6++;
            }
        }
        Arrays.fill(iArr, 0);
        reset();
        return this.digestLength;
    }

    public String getAlgorithmName() {
        return "BLAKE2s";
    }

    public int getByteLength() {
        return 64;
    }

    public int getDigestSize() {
        return this.digestLength;
    }

    public void reset() {
        this.bufferPos = 0;
        this.f172f0 = 0;
        this.f173t0 = 0;
        this.f174t1 = 0;
        this.chainValue = null;
        Arrays.fill(this.buffer, (byte) 0);
        byte[] bArr = this.key;
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.buffer, 0, bArr.length);
            this.bufferPos = 64;
        }
        init(this.salt, this.personalization, this.key);
    }

    public void update(byte b) {
        int i = this.bufferPos;
        if (64 - i == 0) {
            int i2 = this.f173t0 + 64;
            this.f173t0 = i2;
            if (i2 == 0) {
                this.f174t1++;
            }
            compress(this.buffer, 0);
            Arrays.fill(this.buffer, (byte) 0);
            this.buffer[0] = b;
            this.bufferPos = 1;
            return;
        }
        this.buffer[i] = b;
        this.bufferPos = i + 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(byte[] r5, int r6, int r7) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x005b
            if (r7 != 0) goto L_0x0005
            goto L_0x005b
        L_0x0005:
            int r0 = r4.bufferPos
            r1 = 0
            if (r0 == 0) goto L_0x0039
            int r2 = 64 - r0
            if (r2 >= r7) goto L_0x002e
            byte[] r3 = r4.buffer
            java.lang.System.arraycopy(r5, r6, r3, r0, r2)
            int r0 = r4.f173t0
            int r0 = r0 + 64
            r4.f173t0 = r0
            if (r0 != 0) goto L_0x0021
            int r0 = r4.f174t1
            int r0 = r0 + 1
            r4.f174t1 = r0
        L_0x0021:
            byte[] r0 = r4.buffer
            r4.compress(r0, r1)
            r4.bufferPos = r1
            byte[] r0 = r4.buffer
            org.bouncycastle.util.Arrays.fill((byte[]) r0, (byte) r1)
            goto L_0x003a
        L_0x002e:
            byte[] r1 = r4.buffer
            java.lang.System.arraycopy(r5, r6, r1, r0, r7)
        L_0x0033:
            int r5 = r4.bufferPos
            int r5 = r5 + r7
            r4.bufferPos = r5
            return
        L_0x0039:
            r2 = 0
        L_0x003a:
            int r7 = r7 + r6
            int r0 = r7 + -64
            int r6 = r6 + r2
        L_0x003e:
            if (r6 >= r0) goto L_0x0054
            int r2 = r4.f173t0
            int r2 = r2 + 64
            r4.f173t0 = r2
            if (r2 != 0) goto L_0x004e
            int r2 = r4.f174t1
            int r2 = r2 + 1
            r4.f174t1 = r2
        L_0x004e:
            r4.compress(r5, r6)
            int r6 = r6 + 64
            goto L_0x003e
        L_0x0054:
            byte[] r0 = r4.buffer
            int r7 = r7 - r6
            java.lang.System.arraycopy(r5, r6, r0, r1, r7)
            goto L_0x0033
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.digests.Blake2sDigest.update(byte[], int, int):void");
    }
}

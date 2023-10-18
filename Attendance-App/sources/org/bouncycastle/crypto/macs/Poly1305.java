package org.bouncycastle.crypto.macs;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Pack;

public class Poly1305 implements Mac {
    private static final int BLOCK_SIZE = 16;
    private final BlockCipher cipher;
    private final byte[] currentBlock;
    private int currentBlockOffset;

    /* renamed from: h0 */
    private int f464h0;

    /* renamed from: h1 */
    private int f465h1;

    /* renamed from: h2 */
    private int f466h2;

    /* renamed from: h3 */
    private int f467h3;

    /* renamed from: h4 */
    private int f468h4;

    /* renamed from: k0 */
    private int f469k0;

    /* renamed from: k1 */
    private int f470k1;

    /* renamed from: k2 */
    private int f471k2;

    /* renamed from: k3 */
    private int f472k3;

    /* renamed from: r0 */
    private int f473r0;

    /* renamed from: r1 */
    private int f474r1;

    /* renamed from: r2 */
    private int f475r2;

    /* renamed from: r3 */
    private int f476r3;

    /* renamed from: r4 */
    private int f477r4;

    /* renamed from: s1 */
    private int f478s1;

    /* renamed from: s2 */
    private int f479s2;

    /* renamed from: s3 */
    private int f480s3;

    /* renamed from: s4 */
    private int f481s4;
    private final byte[] singleByte;

    public Poly1305() {
        this.singleByte = new byte[1];
        this.currentBlock = new byte[16];
        this.currentBlockOffset = 0;
        this.cipher = null;
    }

    public Poly1305(BlockCipher blockCipher) {
        this.singleByte = new byte[1];
        this.currentBlock = new byte[16];
        this.currentBlockOffset = 0;
        if (blockCipher.getBlockSize() == 16) {
            this.cipher = blockCipher;
            return;
        }
        throw new IllegalArgumentException("Poly1305 requires a 128 bit block cipher.");
    }

    private static final long mul32x32_64(int i, int i2) {
        return (((long) i) & BodyPartID.bodyIdMax) * ((long) i2);
    }

    private void processBlock() {
        int i = this.currentBlockOffset;
        if (i < 16) {
            this.currentBlock[i] = 1;
            for (int i2 = i + 1; i2 < 16; i2++) {
                this.currentBlock[i2] = 0;
            }
        }
        long littleEndianToInt = ((long) Pack.littleEndianToInt(this.currentBlock, 0)) & BodyPartID.bodyIdMax;
        long littleEndianToInt2 = ((long) Pack.littleEndianToInt(this.currentBlock, 4)) & BodyPartID.bodyIdMax;
        long littleEndianToInt3 = ((long) Pack.littleEndianToInt(this.currentBlock, 8)) & BodyPartID.bodyIdMax;
        long littleEndianToInt4 = BodyPartID.bodyIdMax & ((long) Pack.littleEndianToInt(this.currentBlock, 12));
        int i3 = (int) (((long) this.f464h0) + (littleEndianToInt & 67108863));
        this.f464h0 = i3;
        this.f465h1 = (int) (((long) this.f465h1) + ((((littleEndianToInt2 << 32) | littleEndianToInt) >>> 26) & 67108863));
        this.f466h2 = (int) (((long) this.f466h2) + (((littleEndianToInt2 | (littleEndianToInt3 << 32)) >>> 20) & 67108863));
        this.f467h3 = (int) (((long) this.f467h3) + ((((littleEndianToInt4 << 32) | littleEndianToInt3) >>> 14) & 67108863));
        int i4 = (int) (((long) this.f468h4) + (littleEndianToInt4 >>> 8));
        this.f468h4 = i4;
        if (this.currentBlockOffset == 16) {
            this.f468h4 = i4 + 16777216;
        }
        long mul32x32_64 = mul32x32_64(i3, this.f473r0) + mul32x32_64(this.f465h1, this.f481s4) + mul32x32_64(this.f466h2, this.f480s3) + mul32x32_64(this.f467h3, this.f479s2) + mul32x32_64(this.f468h4, this.f478s1);
        long mul32x32_642 = mul32x32_64(this.f464h0, this.f474r1) + mul32x32_64(this.f465h1, this.f473r0) + mul32x32_64(this.f466h2, this.f481s4) + mul32x32_64(this.f467h3, this.f480s3) + mul32x32_64(this.f468h4, this.f479s2);
        long mul32x32_643 = mul32x32_64(this.f464h0, this.f475r2) + mul32x32_64(this.f465h1, this.f474r1) + mul32x32_64(this.f466h2, this.f473r0) + mul32x32_64(this.f467h3, this.f481s4) + mul32x32_64(this.f468h4, this.f480s3);
        long mul32x32_644 = mul32x32_64(this.f464h0, this.f476r3) + mul32x32_64(this.f465h1, this.f475r2) + mul32x32_64(this.f466h2, this.f474r1) + mul32x32_64(this.f467h3, this.f473r0) + mul32x32_64(this.f468h4, this.f481s4);
        long mul32x32_645 = mul32x32_64(this.f464h0, this.f477r4) + mul32x32_64(this.f465h1, this.f476r3) + mul32x32_64(this.f466h2, this.f475r2) + mul32x32_64(this.f467h3, this.f474r1) + mul32x32_64(this.f468h4, this.f473r0);
        int i5 = ((int) mul32x32_64) & 67108863;
        this.f464h0 = i5;
        long j = mul32x32_642 + (mul32x32_64 >>> 26);
        int i6 = ((int) j) & 67108863;
        this.f465h1 = i6;
        long j2 = mul32x32_643 + (j >>> 26);
        this.f466h2 = ((int) j2) & 67108863;
        long j3 = mul32x32_644 + (j2 >>> 26);
        this.f467h3 = ((int) j3) & 67108863;
        long j4 = mul32x32_645 + (j3 >>> 26);
        this.f468h4 = ((int) j4) & 67108863;
        int i7 = i5 + (((int) (j4 >>> 26)) * 5);
        this.f464h0 = i7;
        this.f465h1 = i6 + (i7 >>> 26);
        this.f464h0 = i7 & 67108863;
    }

    private void setKey(byte[] bArr, byte[] bArr2) {
        if (bArr.length == 32) {
            int i = 16;
            if (this.cipher == null || (bArr2 != null && bArr2.length == 16)) {
                int littleEndianToInt = Pack.littleEndianToInt(bArr, 0);
                int littleEndianToInt2 = Pack.littleEndianToInt(bArr, 4);
                int littleEndianToInt3 = Pack.littleEndianToInt(bArr, 8);
                int littleEndianToInt4 = Pack.littleEndianToInt(bArr, 12);
                this.f473r0 = 67108863 & littleEndianToInt;
                int i2 = ((littleEndianToInt >>> 26) | (littleEndianToInt2 << 6)) & 67108611;
                this.f474r1 = i2;
                int i3 = ((littleEndianToInt2 >>> 20) | (littleEndianToInt3 << 12)) & 67092735;
                this.f475r2 = i3;
                int i4 = ((littleEndianToInt3 >>> 14) | (littleEndianToInt4 << 18)) & 66076671;
                this.f476r3 = i4;
                int i5 = (littleEndianToInt4 >>> 8) & 1048575;
                this.f477r4 = i5;
                this.f478s1 = i2 * 5;
                this.f479s2 = i3 * 5;
                this.f480s3 = i4 * 5;
                this.f481s4 = i5 * 5;
                BlockCipher blockCipher = this.cipher;
                if (blockCipher != null) {
                    byte[] bArr3 = new byte[16];
                    blockCipher.init(true, new KeyParameter(bArr, 16, 16));
                    this.cipher.processBlock(bArr2, 0, bArr3, 0);
                    bArr = bArr3;
                    i = 0;
                }
                this.f469k0 = Pack.littleEndianToInt(bArr, i + 0);
                this.f470k1 = Pack.littleEndianToInt(bArr, i + 4);
                this.f471k2 = Pack.littleEndianToInt(bArr, i + 8);
                this.f472k3 = Pack.littleEndianToInt(bArr, i + 12);
                return;
            }
            throw new IllegalArgumentException("Poly1305 requires a 128 bit IV.");
        }
        throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
    }

    public int doFinal(byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        if (i + 16 <= bArr.length) {
            if (this.currentBlockOffset > 0) {
                processBlock();
            }
            int i2 = this.f465h1;
            int i3 = this.f464h0;
            int i4 = i2 + (i3 >>> 26);
            this.f465h1 = i4;
            int i5 = i3 & 67108863;
            this.f464h0 = i5;
            int i6 = this.f466h2 + (i4 >>> 26);
            this.f466h2 = i6;
            int i7 = i4 & 67108863;
            this.f465h1 = i7;
            int i8 = this.f467h3 + (i6 >>> 26);
            this.f467h3 = i8;
            int i9 = i6 & 67108863;
            this.f466h2 = i9;
            int i10 = this.f468h4 + (i8 >>> 26);
            this.f468h4 = i10;
            int i11 = i8 & 67108863;
            this.f467h3 = i11;
            int i12 = i5 + ((i10 >>> 26) * 5);
            this.f464h0 = i12;
            int i13 = i10 & 67108863;
            this.f468h4 = i13;
            int i14 = i7 + (i12 >>> 26);
            this.f465h1 = i14;
            int i15 = i12 & 67108863;
            this.f464h0 = i15;
            int i16 = i15 + 5;
            int i17 = (i16 >>> 26) + i14;
            int i18 = (i17 >>> 26) + i9;
            int i19 = (i18 >>> 26) + i11;
            int i20 = 67108863 & i19;
            int i21 = ((i19 >>> 26) + i13) - PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE;
            int i22 = (i21 >>> 31) - 1;
            int i23 = i22 ^ -1;
            int i24 = (i15 & i23) | (i16 & 67108863 & i22);
            this.f464h0 = i24;
            int i25 = (i14 & i23) | (i17 & 67108863 & i22);
            this.f465h1 = i25;
            int i26 = (i9 & i23) | (i18 & 67108863 & i22);
            this.f466h2 = i26;
            int i27 = (i20 & i22) | (i11 & i23);
            this.f467h3 = i27;
            int i28 = (i13 & i23) | (i21 & i22);
            this.f468h4 = i28;
            long j = (((long) (i24 | (i25 << 26))) & BodyPartID.bodyIdMax) + (((long) this.f469k0) & BodyPartID.bodyIdMax);
            long j2 = (((long) ((i25 >>> 6) | (i26 << 20))) & BodyPartID.bodyIdMax) + (((long) this.f470k1) & BodyPartID.bodyIdMax);
            long j3 = (((long) ((i26 >>> 12) | (i27 << 14))) & BodyPartID.bodyIdMax) + (((long) this.f471k2) & BodyPartID.bodyIdMax);
            Pack.intToLittleEndian((int) j, bArr, i);
            long j4 = j2 + (j >>> 32);
            Pack.intToLittleEndian((int) j4, bArr, i + 4);
            long j5 = j3 + (j4 >>> 32);
            Pack.intToLittleEndian((int) j5, bArr, i + 8);
            Pack.intToLittleEndian((int) ((((long) ((i27 >>> 18) | (i28 << 8))) & BodyPartID.bodyIdMax) + (BodyPartID.bodyIdMax & ((long) this.f472k3)) + (j5 >>> 32)), bArr, i + 12);
            reset();
            return 16;
        }
        throw new OutputLengthException("Output buffer is too short.");
    }

    public String getAlgorithmName() {
        return this.cipher == null ? "Poly1305" : "Poly1305-" + this.cipher.getAlgorithmName();
    }

    public int getMacSize() {
        return 16;
    }

    public void init(CipherParameters cipherParameters) throws IllegalArgumentException {
        byte[] bArr;
        if (this.cipher == null) {
            bArr = null;
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            cipherParameters = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("Poly1305 requires an IV when used with a block cipher.");
        }
        if (cipherParameters instanceof KeyParameter) {
            setKey(((KeyParameter) cipherParameters).getKey(), bArr);
            reset();
            return;
        }
        throw new IllegalArgumentException("Poly1305 requires a key.");
    }

    public void reset() {
        this.currentBlockOffset = 0;
        this.f468h4 = 0;
        this.f467h3 = 0;
        this.f466h2 = 0;
        this.f465h1 = 0;
        this.f464h0 = 0;
    }

    public void update(byte b) throws IllegalStateException {
        byte[] bArr = this.singleByte;
        bArr[0] = b;
        update(bArr, 0, 1);
    }

    public void update(byte[] bArr, int i, int i2) throws DataLengthException, IllegalStateException {
        int i3 = 0;
        while (i2 > i3) {
            if (this.currentBlockOffset == 16) {
                processBlock();
                this.currentBlockOffset = 0;
            }
            int min = Math.min(i2 - i3, 16 - this.currentBlockOffset);
            System.arraycopy(bArr, i3 + i, this.currentBlock, this.currentBlockOffset, min);
            i3 += min;
            this.currentBlockOffset += min;
        }
    }
}

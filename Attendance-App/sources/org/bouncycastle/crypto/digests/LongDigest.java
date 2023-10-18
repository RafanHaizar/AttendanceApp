package org.bouncycastle.crypto.digests;

import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public abstract class LongDigest implements ExtendedDigest, Memoable, EncodableDigest {
    private static final int BYTE_LENGTH = 128;

    /* renamed from: K */
    static final long[] f204K = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};

    /* renamed from: H1 */
    protected long f205H1;

    /* renamed from: H2 */
    protected long f206H2;

    /* renamed from: H3 */
    protected long f207H3;

    /* renamed from: H4 */
    protected long f208H4;

    /* renamed from: H5 */
    protected long f209H5;

    /* renamed from: H6 */
    protected long f210H6;

    /* renamed from: H7 */
    protected long f211H7;

    /* renamed from: H8 */
    protected long f212H8;

    /* renamed from: W */
    private long[] f213W;
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf;
    private int xBufOff;

    protected LongDigest() {
        this.xBuf = new byte[8];
        this.f213W = new long[80];
        this.xBufOff = 0;
        reset();
    }

    protected LongDigest(LongDigest longDigest) {
        this.xBuf = new byte[8];
        this.f213W = new long[80];
        copyIn(longDigest);
    }

    /* renamed from: Ch */
    private long m38Ch(long j, long j2, long j3) {
        return ((j ^ -1) & j3) ^ (j2 & j);
    }

    private long Maj(long j, long j2, long j3) {
        return ((j & j3) ^ (j & j2)) ^ (j2 & j3);
    }

    private long Sigma0(long j) {
        return (j >>> 7) ^ (((j << 63) | (j >>> 1)) ^ ((j << 56) | (j >>> 8)));
    }

    private long Sigma1(long j) {
        return (j >>> 6) ^ (((j << 45) | (j >>> 19)) ^ ((j << 3) | (j >>> 61)));
    }

    private long Sum0(long j) {
        return ((j >>> 39) | (j << 25)) ^ (((j << 36) | (j >>> 28)) ^ ((j << 30) | (j >>> 34)));
    }

    private long Sum1(long j) {
        return ((j >>> 41) | (j << 23)) ^ (((j << 50) | (j >>> 14)) ^ ((j << 46) | (j >>> 18)));
    }

    private void adjustByteCounts() {
        long j = this.byteCount1;
        if (j > 2305843009213693951L) {
            this.byteCount2 += j >>> 61;
            this.byteCount1 = j & 2305843009213693951L;
        }
    }

    /* access modifiers changed from: protected */
    public void copyIn(LongDigest longDigest) {
        byte[] bArr = longDigest.xBuf;
        System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
        this.xBufOff = longDigest.xBufOff;
        this.byteCount1 = longDigest.byteCount1;
        this.byteCount2 = longDigest.byteCount2;
        this.f205H1 = longDigest.f205H1;
        this.f206H2 = longDigest.f206H2;
        this.f207H3 = longDigest.f207H3;
        this.f208H4 = longDigest.f208H4;
        this.f209H5 = longDigest.f209H5;
        this.f210H6 = longDigest.f210H6;
        this.f211H7 = longDigest.f211H7;
        this.f212H8 = longDigest.f212H8;
        long[] jArr = longDigest.f213W;
        System.arraycopy(jArr, 0, this.f213W, 0, jArr.length);
        this.wOff = longDigest.wOff;
    }

    public void finish() {
        adjustByteCounts();
        long j = this.byteCount1 << 3;
        long j2 = this.byteCount2;
        byte b = ByteCompanionObject.MIN_VALUE;
        while (true) {
            update(b);
            if (this.xBufOff != 0) {
                b = 0;
            } else {
                processLength(j, j2);
                processBlock();
                return;
            }
        }
    }

    public int getByteLength() {
        return 128;
    }

    /* access modifiers changed from: protected */
    public int getEncodedStateSize() {
        return (this.wOff * 8) + 96;
    }

    /* access modifiers changed from: protected */
    public void populateState(byte[] bArr) {
        System.arraycopy(this.xBuf, 0, bArr, 0, this.xBufOff);
        Pack.intToBigEndian(this.xBufOff, bArr, 8);
        Pack.longToBigEndian(this.byteCount1, bArr, 12);
        Pack.longToBigEndian(this.byteCount2, bArr, 20);
        Pack.longToBigEndian(this.f205H1, bArr, 28);
        Pack.longToBigEndian(this.f206H2, bArr, 36);
        Pack.longToBigEndian(this.f207H3, bArr, 44);
        Pack.longToBigEndian(this.f208H4, bArr, 52);
        Pack.longToBigEndian(this.f209H5, bArr, 60);
        Pack.longToBigEndian(this.f210H6, bArr, 68);
        Pack.longToBigEndian(this.f211H7, bArr, 76);
        Pack.longToBigEndian(this.f212H8, bArr, 84);
        Pack.intToBigEndian(this.wOff, bArr, 92);
        for (int i = 0; i < this.wOff; i++) {
            Pack.longToBigEndian(this.f213W[i], bArr, (i * 8) + 96);
        }
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        adjustByteCounts();
        for (int i = 16; i <= 79; i++) {
            long[] jArr = this.f213W;
            long Sigma1 = Sigma1(jArr[i - 2]);
            long[] jArr2 = this.f213W;
            jArr[i] = Sigma1 + jArr2[i - 7] + Sigma0(jArr2[i - 15]) + this.f213W[i - 16];
        }
        long j = this.f205H1;
        long j2 = this.f206H2;
        long j3 = this.f207H3;
        long j4 = this.f208H4;
        long j5 = this.f209H5;
        long j6 = this.f210H6;
        long j7 = this.f211H7;
        long j8 = j6;
        long j9 = j4;
        int i2 = 0;
        long j10 = j2;
        long j11 = j3;
        long j12 = j5;
        int i3 = 0;
        long j13 = this.f212H8;
        long j14 = j7;
        long j15 = j;
        long j16 = j14;
        while (i3 < 10) {
            int i4 = i3;
            long j17 = j12;
            long[] jArr3 = f204K;
            int i5 = i2 + 1;
            long Sum1 = j13 + Sum1(j12) + m38Ch(j12, j8, j16) + jArr3[i2] + this.f213W[i2];
            long Sum0 = Sum1 + Sum0(j15) + Maj(j15, j10, j11);
            long j18 = j9 + Sum1;
            long j19 = j18;
            int i6 = i5 + 1;
            long Sum12 = j16 + Sum1(j18) + m38Ch(j18, j17, j8) + jArr3[i5] + this.f213W[i5];
            long j20 = Sum0;
            long j21 = j11 + Sum12;
            long Sum02 = Sum12 + Sum0(Sum0) + Maj(Sum0, j15, j10);
            long Sum13 = Sum1(j21);
            long j22 = j21;
            long j23 = Sum02;
            int i7 = i6 + 1;
            long Ch = j8 + Sum13 + m38Ch(j21, j19, j17) + jArr3[i6] + this.f213W[i6];
            long j24 = j10 + Ch;
            long Sum03 = Ch + Sum0(j23) + Maj(j23, j20, j15);
            long Sum14 = Sum1(j24);
            long j25 = j24;
            long j26 = Sum03;
            int i8 = i7 + 1;
            long Ch2 = j17 + Sum14 + m38Ch(j24, j22, j19) + jArr3[i7] + this.f213W[i7];
            long j27 = j15 + Ch2;
            long Sum04 = Ch2 + Sum0(j26) + Maj(j26, j23, j20);
            long Sum15 = Sum1(j27);
            long j28 = j27;
            long j29 = Sum04;
            int i9 = i8 + 1;
            long Ch3 = j19 + Sum15 + m38Ch(j27, j25, j22) + jArr3[i8] + this.f213W[i8];
            long j30 = j26;
            long j31 = j26;
            long j32 = j20 + Ch3;
            long Sum05 = Ch3 + Sum0(j29) + Maj(j29, j30, j23);
            long Sum16 = Sum1(j32);
            long j33 = j32;
            long j34 = Sum05;
            int i10 = i9 + 1;
            long Ch4 = j22 + Sum16 + m38Ch(j32, j28, j25) + jArr3[i9] + this.f213W[i9];
            long j35 = j23 + Ch4;
            long Sum06 = Ch4 + Sum0(j34) + Maj(j34, j29, j31);
            long Sum17 = Sum1(j35);
            j16 = j35;
            long j36 = Sum06;
            int i11 = i10 + 1;
            long Ch5 = j25 + Sum17 + m38Ch(j35, j33, j28) + jArr3[i10] + this.f213W[i10];
            long j37 = j31 + Ch5;
            long j38 = j34;
            long j39 = j34;
            long j40 = j37;
            long Sum07 = Ch5 + Sum0(j36) + Maj(j36, j38, j29);
            long Sum18 = Sum1(j40);
            j8 = j40;
            j10 = Sum07;
            long Ch6 = j28 + Sum18 + m38Ch(j40, j16, j33) + jArr3[i11] + this.f213W[i11];
            long Sum08 = Ch6 + Sum0(j10) + Maj(j10, j36, j39);
            i3 = i4 + 1;
            j12 = j29 + Ch6;
            j11 = j36;
            j13 = j33;
            j9 = j39;
            i2 = i11 + 1;
            j15 = Sum08;
        }
        this.f205H1 += j15;
        this.f206H2 += j10;
        this.f207H3 += j11;
        this.f208H4 += j9;
        this.f209H5 += j12;
        this.f210H6 += j8;
        this.f211H7 += j16;
        this.f212H8 += j13;
        this.wOff = 0;
        for (int i12 = 0; i12 < 16; i12++) {
            this.f213W[i12] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j, long j2) {
        if (this.wOff > 14) {
            processBlock();
        }
        long[] jArr = this.f213W;
        jArr[14] = j2;
        jArr[15] = j;
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        this.f213W[this.wOff] = Pack.bigEndianToLong(bArr, i);
        int i2 = this.wOff + 1;
        this.wOff = i2;
        if (i2 == 16) {
            processBlock();
        }
    }

    public void reset() {
        this.byteCount1 = 0;
        this.byteCount2 = 0;
        int i = 0;
        this.xBufOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        this.wOff = 0;
        while (true) {
            long[] jArr = this.f213W;
            if (i != jArr.length) {
                jArr[i] = 0;
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void restoreState(byte[] bArr) {
        int bigEndianToInt = Pack.bigEndianToInt(bArr, 8);
        this.xBufOff = bigEndianToInt;
        System.arraycopy(bArr, 0, this.xBuf, 0, bigEndianToInt);
        this.byteCount1 = Pack.bigEndianToLong(bArr, 12);
        this.byteCount2 = Pack.bigEndianToLong(bArr, 20);
        this.f205H1 = Pack.bigEndianToLong(bArr, 28);
        this.f206H2 = Pack.bigEndianToLong(bArr, 36);
        this.f207H3 = Pack.bigEndianToLong(bArr, 44);
        this.f208H4 = Pack.bigEndianToLong(bArr, 52);
        this.f209H5 = Pack.bigEndianToLong(bArr, 60);
        this.f210H6 = Pack.bigEndianToLong(bArr, 68);
        this.f211H7 = Pack.bigEndianToLong(bArr, 76);
        this.f212H8 = Pack.bigEndianToLong(bArr, 84);
        this.wOff = Pack.bigEndianToInt(bArr, 92);
        for (int i = 0; i < this.wOff; i++) {
            this.f213W[i] = Pack.bigEndianToLong(bArr, (i * 8) + 96);
        }
    }

    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        int i2 = i + 1;
        this.xBufOff = i2;
        bArr[i] = b;
        if (i2 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount1++;
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.xBufOff != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.xBuf.length) {
            processWord(bArr, i);
            byte[] bArr2 = this.xBuf;
            i += bArr2.length;
            i2 -= bArr2.length;
            this.byteCount1 += (long) bArr2.length;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}

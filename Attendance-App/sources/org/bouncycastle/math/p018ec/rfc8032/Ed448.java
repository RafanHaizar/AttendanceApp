package org.bouncycastle.math.p018ec.rfc8032;

import java.security.SecureRandom;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.p018ec.rfc7748.X448;
import org.bouncycastle.math.p018ec.rfc7748.X448Field;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* renamed from: org.bouncycastle.math.ec.rfc8032.Ed448 */
public abstract class Ed448 {
    private static final int[] B_x = {118276190, 40534716, 9670182, 135141552, 85017403, 259173222, 68333082, 171784774, 174973732, 15824510, 73756743, 57518561, 94773951, 248652241, 107736333, 82941708};
    private static final int[] B_y = {36764180, 8885695, 130592152, 20104429, 163904957, 30304195, 121295871, 5901357, 125344798, 171541512, 175338348, 209069246, 3626697, 38307682, 24032956, 110359655};
    private static final int C_d = -39081;
    private static final byte[] DOM4_PREFIX = Strings.toByteArray("SigEd448");

    /* renamed from: L */
    private static final int[] f835L = {-1420278541, 595116690, -1916432555, 560775794, -1361693040, -1001465015, 2093622249, -1, -1, -1, -1, -1, -1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK};
    private static final int L4_0 = 43969588;
    private static final int L4_1 = 30366549;
    private static final int L4_2 = 163752818;
    private static final int L4_3 = 258169998;
    private static final int L4_4 = 96434764;
    private static final int L4_5 = 227822194;
    private static final int L4_6 = 149865618;
    private static final int L4_7 = 550336261;
    private static final int L_0 = 78101261;
    private static final int L_1 = 141809365;
    private static final int L_2 = 175155932;
    private static final int L_3 = 64542499;
    private static final int L_4 = 158326419;
    private static final int L_5 = 191173276;
    private static final int L_6 = 104575268;
    private static final int L_7 = 137584065;
    private static final long M26L = 67108863;
    private static final long M28L = 268435455;
    private static final long M32L = 4294967295L;

    /* renamed from: P */
    private static final int[] f836P = {-1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1};
    private static final int POINT_BYTES = 57;
    private static final int PRECOMP_BLOCKS = 5;
    private static final int PRECOMP_MASK = 15;
    private static final int PRECOMP_POINTS = 16;
    private static final int PRECOMP_SPACING = 18;
    private static final int PRECOMP_TEETH = 5;
    public static final int PREHASH_SIZE = 64;
    public static final int PUBLIC_KEY_SIZE = 57;
    private static final int SCALAR_BYTES = 57;
    private static final int SCALAR_INTS = 14;
    public static final int SECRET_KEY_SIZE = 57;
    public static final int SIGNATURE_SIZE = 114;
    private static final int WNAF_WIDTH_BASE = 7;
    private static int[] precompBase = null;
    private static PointExt[] precompBaseTable = null;
    private static final Object precompLock = new Object();

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed448$Algorithm */
    public static final class Algorithm {
        public static final int Ed448 = 0;
        public static final int Ed448ph = 1;
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed448$PointExt */
    private static class PointExt {

        /* renamed from: x */
        int[] f837x;

        /* renamed from: y */
        int[] f838y;

        /* renamed from: z */
        int[] f839z;

        private PointExt() {
            this.f837x = X448Field.create();
            this.f838y = X448Field.create();
            this.f839z = X448Field.create();
        }
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed448$PointPrecomp */
    private static class PointPrecomp {

        /* renamed from: x */
        int[] f840x;

        /* renamed from: y */
        int[] f841y;

        private PointPrecomp() {
            this.f840x = X448Field.create();
            this.f841y = X448Field.create();
        }
    }

    private static byte[] calculateS(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int[] iArr = new int[28];
        decodeScalar(bArr, 0, iArr);
        int[] iArr2 = new int[14];
        decodeScalar(bArr2, 0, iArr2);
        int[] iArr3 = new int[14];
        decodeScalar(bArr3, 0, iArr3);
        Nat.mulAddTo(14, iArr2, iArr3, iArr);
        byte[] bArr4 = new byte[114];
        for (int i = 0; i < 28; i++) {
            encode32(iArr[i], bArr4, i * 4);
        }
        return reduceScalar(bArr4);
    }

    private static boolean checkContextVar(byte[] bArr) {
        return bArr != null && bArr.length < 256;
    }

    private static int checkPoint(int[] iArr, int[] iArr2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        X448Field.sqr(iArr, create2);
        X448Field.sqr(iArr2, create3);
        X448Field.mul(create2, create3, create);
        X448Field.add(create2, create3, create2);
        X448Field.mul(create, 39081, create);
        X448Field.subOne(create);
        X448Field.add(create, create2, create);
        X448Field.normalize(create);
        return X448Field.isZero(create);
    }

    private static int checkPoint(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        X448Field.sqr(iArr, create2);
        X448Field.sqr(iArr2, create3);
        X448Field.sqr(iArr3, create4);
        X448Field.mul(create2, create3, create);
        X448Field.add(create2, create3, create2);
        X448Field.mul(create2, create4, create2);
        X448Field.sqr(create4, create4);
        X448Field.mul(create, 39081, create);
        X448Field.sub(create, create4, create);
        X448Field.add(create, create2, create);
        X448Field.normalize(create);
        return X448Field.isZero(create);
    }

    private static boolean checkPointVar(byte[] bArr) {
        if ((bArr[56] & ByteCompanionObject.MAX_VALUE) != 0) {
            return false;
        }
        int[] iArr = new int[14];
        decode32(bArr, 0, iArr, 0, 14);
        return !Nat.gte(14, iArr, f836P);
    }

    private static boolean checkScalarVar(byte[] bArr) {
        if (bArr[56] != 0) {
            return false;
        }
        int[] iArr = new int[14];
        decodeScalar(bArr, 0, iArr);
        return !Nat.gte(14, iArr, f835L);
    }

    public static Xof createPrehash() {
        return createXof();
    }

    private static Xof createXof() {
        return new SHAKEDigest(256);
    }

    private static int decode16(byte[] bArr, int i) {
        return ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | (bArr[i] & UByte.MAX_VALUE);
    }

    private static int decode24(byte[] bArr, int i) {
        int i2 = i + 1;
        return ((bArr[i2 + 1] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8);
    }

    private static int decode32(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (bArr[i3 + 1] << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8) | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    private static void decode32(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            iArr[i2 + i4] = decode32(bArr, (i4 * 4) + i);
        }
    }

    private static boolean decodePointVar(byte[] bArr, int i, boolean z, PointExt pointExt) {
        byte[] copyOfRange = Arrays.copyOfRange(bArr, i, i + 57);
        boolean z2 = false;
        if (!checkPointVar(copyOfRange)) {
            return false;
        }
        byte b = copyOfRange[56];
        int i2 = (b & ByteCompanionObject.MIN_VALUE) >>> 7;
        copyOfRange[56] = (byte) (b & ByteCompanionObject.MAX_VALUE);
        X448Field.decode(copyOfRange, 0, pointExt.f838y);
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        X448Field.sqr(pointExt.f838y, create);
        X448Field.mul(create, 39081, create2);
        X448Field.negate(create, create);
        X448Field.addOne(create);
        X448Field.addOne(create2);
        if (!X448Field.sqrtRatioVar(create, create2, pointExt.f837x)) {
            return false;
        }
        X448Field.normalize(pointExt.f837x);
        if (i2 == 1 && X448Field.isZeroVar(pointExt.f837x)) {
            return false;
        }
        if (i2 != (pointExt.f837x[0] & 1)) {
            z2 = true;
        }
        if (z ^ z2) {
            X448Field.negate(pointExt.f837x, pointExt.f837x);
        }
        pointExtendXY(pointExt);
        return true;
    }

    private static void decodeScalar(byte[] bArr, int i, int[] iArr) {
        decode32(bArr, i, iArr, 0, 14);
    }

    private static void dom4(Xof xof, byte b, byte[] bArr) {
        byte[] bArr2 = DOM4_PREFIX;
        xof.update(bArr2, 0, bArr2.length);
        xof.update(b);
        xof.update((byte) bArr.length);
        xof.update(bArr, 0, bArr.length);
    }

    private static void encode24(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        bArr[i3 + 1] = (byte) (i >>> 16);
    }

    private static void encode32(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 16);
        bArr[i4 + 1] = (byte) (i >>> 24);
    }

    private static void encode56(long j, byte[] bArr, int i) {
        encode32((int) j, bArr, i);
        encode24((int) (j >>> 32), bArr, i + 4);
    }

    private static int encodePoint(PointExt pointExt, byte[] bArr, int i) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        X448Field.inv(pointExt.f839z, create2);
        X448Field.mul(pointExt.f837x, create2, create);
        X448Field.mul(pointExt.f838y, create2, create2);
        X448Field.normalize(create);
        X448Field.normalize(create2);
        int checkPoint = checkPoint(create, create2);
        X448Field.encode(create2, bArr, i);
        bArr[(i + 57) - 1] = (byte) ((create[0] & 1) << 7);
        return checkPoint;
    }

    public static void generatePrivateKey(SecureRandom secureRandom, byte[] bArr) {
        secureRandom.nextBytes(bArr);
    }

    public static void generatePublicKey(byte[] bArr, int i, byte[] bArr2, int i2) {
        Xof createXof = createXof();
        byte[] bArr3 = new byte[114];
        createXof.update(bArr, i, 57);
        createXof.doFinal(bArr3, 0, 114);
        byte[] bArr4 = new byte[57];
        pruneScalar(bArr3, 0, bArr4);
        scalarMultBaseEncoded(bArr4, bArr2, i2);
    }

    private static byte[] getWNAF(int[] iArr, int i) {
        int i2;
        int[] iArr2 = new int[28];
        int i3 = 0;
        int i4 = 14;
        int i5 = 28;
        int i6 = 0;
        while (true) {
            i4--;
            if (i4 < 0) {
                break;
            }
            int i7 = iArr[i4];
            int i8 = i5 - 1;
            iArr2[i8] = (i6 << 16) | (i7 >>> 16);
            i5 = i8 - 1;
            iArr2[i5] = i7;
            i6 = i7;
        }
        byte[] bArr = new byte[447];
        int i9 = 1 << i;
        int i10 = i9 - 1;
        int i11 = i9 >>> 1;
        int i12 = 0;
        int i13 = 0;
        while (i3 < 28) {
            int i14 = iArr2[i3];
            while (i2 < 16) {
                int i15 = i14 >>> i2;
                if ((i15 & 1) == i13) {
                    i2++;
                } else {
                    int i16 = (i15 & i10) + i13;
                    int i17 = i16 & i11;
                    int i18 = i16 - (i17 << 1);
                    i13 = i17 >>> (i - 1);
                    bArr[(i3 << 4) + i2] = (byte) i18;
                    i2 += i;
                }
            }
            i3++;
            i12 = i2 - 16;
        }
        return bArr;
    }

    private static int getWindow4(int[] iArr, int i) {
        return (iArr[i >>> 3] >>> ((i & 7) << 2)) & 15;
    }

    private static void implSign(Xof xof, byte[] bArr, byte[] bArr2, byte[] bArr3, int i, byte[] bArr4, byte b, byte[] bArr5, int i2, int i3, byte[] bArr6, int i4) {
        dom4(xof, b, bArr4);
        xof.update(bArr, 57, 57);
        xof.update(bArr5, i2, i3);
        xof.doFinal(bArr, 0, bArr.length);
        byte[] reduceScalar = reduceScalar(bArr);
        byte[] bArr7 = new byte[57];
        scalarMultBaseEncoded(reduceScalar, bArr7, 0);
        dom4(xof, b, bArr4);
        xof.update(bArr7, 0, 57);
        xof.update(bArr3, i, 57);
        xof.update(bArr5, i2, i3);
        xof.doFinal(bArr, 0, bArr.length);
        byte[] calculateS = calculateS(reduceScalar, reduceScalar(bArr), bArr2);
        System.arraycopy(bArr7, 0, bArr6, i4, 57);
        System.arraycopy(calculateS, 0, bArr6, i4 + 57, 57);
    }

    private static void implSign(byte[] bArr, int i, byte[] bArr2, byte b, byte[] bArr3, int i2, int i3, byte[] bArr4, int i4) {
        if (checkContextVar(bArr2)) {
            Xof createXof = createXof();
            byte[] bArr5 = new byte[114];
            byte[] bArr6 = bArr;
            int i5 = i;
            createXof.update(bArr, i, 57);
            createXof.doFinal(bArr5, 0, 114);
            byte[] bArr7 = new byte[57];
            pruneScalar(bArr5, 0, bArr7);
            byte[] bArr8 = new byte[57];
            scalarMultBaseEncoded(bArr7, bArr8, 0);
            implSign(createXof, bArr5, bArr7, bArr8, 0, bArr2, b, bArr3, i2, i3, bArr4, i4);
            return;
        }
        throw new IllegalArgumentException("ctx");
    }

    private static void implSign(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte b, byte[] bArr4, int i3, int i4, byte[] bArr5, int i5) {
        if (checkContextVar(bArr3)) {
            Xof createXof = createXof();
            byte[] bArr6 = new byte[114];
            byte[] bArr7 = bArr;
            int i6 = i;
            createXof.update(bArr, i, 57);
            createXof.doFinal(bArr6, 0, 114);
            byte[] bArr8 = new byte[57];
            pruneScalar(bArr6, 0, bArr8);
            implSign(createXof, bArr6, bArr8, bArr2, i2, bArr3, b, bArr4, i3, i4, bArr5, i5);
            return;
        }
        throw new IllegalArgumentException("ctx");
    }

    private static boolean implVerify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte b, byte[] bArr4, int i3, int i4) {
        if (checkContextVar(bArr3)) {
            int i5 = i + 57;
            byte[] copyOfRange = Arrays.copyOfRange(bArr, i, i5);
            byte[] copyOfRange2 = Arrays.copyOfRange(bArr, i5, i + 114);
            if (!checkPointVar(copyOfRange) || !checkScalarVar(copyOfRange2)) {
                return false;
            }
            PointExt pointExt = new PointExt();
            if (!decodePointVar(bArr2, i2, true, pointExt)) {
                return false;
            }
            Xof createXof = createXof();
            byte[] bArr5 = new byte[114];
            dom4(createXof, b, bArr3);
            createXof.update(copyOfRange, 0, 57);
            createXof.update(bArr2, i2, 57);
            createXof.update(bArr4, i3, i4);
            createXof.doFinal(bArr5, 0, 114);
            byte[] reduceScalar = reduceScalar(bArr5);
            int[] iArr = new int[14];
            decodeScalar(copyOfRange2, 0, iArr);
            int[] iArr2 = new int[14];
            decodeScalar(reduceScalar, 0, iArr2);
            PointExt pointExt2 = new PointExt();
            scalarMultStrausVar(iArr, iArr2, pointExt, pointExt2);
            byte[] bArr6 = new byte[57];
            return encodePoint(pointExt2, bArr6, 0) != 0 && Arrays.areEqual(bArr6, copyOfRange);
        }
        throw new IllegalArgumentException("ctx");
    }

    private static void pointAdd(PointExt pointExt, PointExt pointExt2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        int[] create8 = X448Field.create();
        X448Field.mul(pointExt.f839z, pointExt2.f839z, create);
        X448Field.sqr(create, create2);
        X448Field.mul(pointExt.f837x, pointExt2.f837x, create3);
        X448Field.mul(pointExt.f838y, pointExt2.f838y, create4);
        X448Field.mul(create3, create4, create5);
        X448Field.mul(create5, 39081, create5);
        X448Field.add(create2, create5, create6);
        X448Field.sub(create2, create5, create7);
        X448Field.add(pointExt.f837x, pointExt.f838y, create2);
        X448Field.add(pointExt2.f837x, pointExt2.f838y, create5);
        X448Field.mul(create2, create5, create8);
        X448Field.add(create4, create3, create2);
        X448Field.sub(create4, create3, create5);
        X448Field.carry(create2);
        X448Field.sub(create8, create2, create8);
        X448Field.mul(create8, create, create8);
        X448Field.mul(create5, create, create5);
        X448Field.mul(create6, create8, pointExt2.f837x);
        X448Field.mul(create5, create7, pointExt2.f838y);
        X448Field.mul(create6, create7, pointExt2.f839z);
    }

    private static void pointAddPrecomp(PointPrecomp pointPrecomp, PointExt pointExt) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        X448Field.sqr(pointExt.f839z, create);
        X448Field.mul(pointPrecomp.f840x, pointExt.f837x, create2);
        X448Field.mul(pointPrecomp.f841y, pointExt.f838y, create3);
        X448Field.mul(create2, create3, create4);
        X448Field.mul(create4, 39081, create4);
        X448Field.add(create, create4, create5);
        X448Field.sub(create, create4, create6);
        X448Field.add(pointPrecomp.f840x, pointPrecomp.f841y, create);
        X448Field.add(pointExt.f837x, pointExt.f838y, create4);
        X448Field.mul(create, create4, create7);
        X448Field.add(create3, create2, create);
        X448Field.sub(create3, create2, create4);
        X448Field.carry(create);
        X448Field.sub(create7, create, create7);
        X448Field.mul(create7, pointExt.f839z, create7);
        X448Field.mul(create4, pointExt.f839z, create4);
        X448Field.mul(create5, create7, pointExt.f837x);
        X448Field.mul(create4, create6, pointExt.f838y);
        X448Field.mul(create5, create6, pointExt.f839z);
    }

    private static void pointAddVar(boolean z, PointExt pointExt, PointExt pointExt2) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        int[] create8 = X448Field.create();
        if (z) {
            X448Field.sub(pointExt.f838y, pointExt.f837x, create8);
            iArr2 = create2;
            iArr3 = create5;
            iArr4 = create6;
            iArr = create7;
        } else {
            X448Field.add(pointExt.f838y, pointExt.f837x, create8);
            iArr3 = create2;
            iArr2 = create5;
            iArr = create6;
            iArr4 = create7;
        }
        X448Field.mul(pointExt.f839z, pointExt2.f839z, create);
        X448Field.sqr(create, create2);
        X448Field.mul(pointExt.f837x, pointExt2.f837x, create3);
        X448Field.mul(pointExt.f838y, pointExt2.f838y, create4);
        X448Field.mul(create3, create4, create5);
        X448Field.mul(create5, 39081, create5);
        X448Field.add(create2, create5, iArr);
        X448Field.sub(create2, create5, iArr4);
        X448Field.add(pointExt2.f837x, pointExt2.f838y, create5);
        X448Field.mul(create8, create5, create8);
        X448Field.add(create4, create3, iArr3);
        X448Field.sub(create4, create3, iArr2);
        X448Field.carry(iArr3);
        X448Field.sub(create8, create2, create8);
        X448Field.mul(create8, create, create8);
        X448Field.mul(create5, create, create5);
        X448Field.mul(create6, create8, pointExt2.f837x);
        X448Field.mul(create5, create7, pointExt2.f838y);
        X448Field.mul(create6, create7, pointExt2.f839z);
    }

    private static PointExt pointCopy(PointExt pointExt) {
        PointExt pointExt2 = new PointExt();
        pointCopy(pointExt, pointExt2);
        return pointExt2;
    }

    private static void pointCopy(PointExt pointExt, PointExt pointExt2) {
        X448Field.copy(pointExt.f837x, 0, pointExt2.f837x, 0);
        X448Field.copy(pointExt.f838y, 0, pointExt2.f838y, 0);
        X448Field.copy(pointExt.f839z, 0, pointExt2.f839z, 0);
    }

    private static void pointDouble(PointExt pointExt) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        X448Field.add(pointExt.f837x, pointExt.f838y, create);
        X448Field.sqr(create, create);
        X448Field.sqr(pointExt.f837x, create2);
        X448Field.sqr(pointExt.f838y, create3);
        X448Field.add(create2, create3, create4);
        X448Field.carry(create4);
        X448Field.sqr(pointExt.f839z, create5);
        X448Field.add(create5, create5, create5);
        X448Field.carry(create5);
        X448Field.sub(create4, create5, create6);
        X448Field.sub(create, create4, create);
        X448Field.sub(create2, create3, create2);
        X448Field.mul(create, create6, pointExt.f837x);
        X448Field.mul(create4, create2, pointExt.f838y);
        X448Field.mul(create4, create6, pointExt.f839z);
    }

    private static void pointExtendXY(PointExt pointExt) {
        X448Field.one(pointExt.f839z);
    }

    private static void pointLookup(int i, int i2, PointPrecomp pointPrecomp) {
        int i3 = i * 16 * 2 * 16;
        for (int i4 = 0; i4 < 16; i4++) {
            int i5 = ((i4 ^ i2) - 1) >> 31;
            X448Field.cmov(i5, precompBase, i3, pointPrecomp.f840x, 0);
            int i6 = i3 + 16;
            X448Field.cmov(i5, precompBase, i6, pointPrecomp.f841y, 0);
            i3 = i6 + 16;
        }
    }

    private static void pointLookup(int[] iArr, int i, int[] iArr2, PointExt pointExt) {
        int window4 = getWindow4(iArr, i);
        int i2 = (window4 >>> 3) ^ 1;
        int i3 = (window4 ^ (-i2)) & 7;
        int i4 = 0;
        for (int i5 = 0; i5 < 8; i5++) {
            int i6 = ((i5 ^ i3) - 1) >> 31;
            X448Field.cmov(i6, iArr2, i4, pointExt.f837x, 0);
            int i7 = i4 + 16;
            X448Field.cmov(i6, iArr2, i7, pointExt.f838y, 0);
            int i8 = i7 + 16;
            X448Field.cmov(i6, iArr2, i8, pointExt.f839z, 0);
            i4 = i8 + 16;
        }
        X448Field.cnegate(i2, pointExt.f837x);
    }

    private static int[] pointPrecomp(PointExt pointExt, int i) {
        PointExt pointCopy = pointCopy(pointExt);
        PointExt pointCopy2 = pointCopy(pointCopy);
        pointDouble(pointCopy2);
        int[] createTable = X448Field.createTable(i * 3);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            X448Field.copy(pointCopy.f837x, 0, createTable, i2);
            int i4 = i2 + 16;
            X448Field.copy(pointCopy.f838y, 0, createTable, i4);
            int i5 = i4 + 16;
            X448Field.copy(pointCopy.f839z, 0, createTable, i5);
            i2 = i5 + 16;
            i3++;
            if (i3 == i) {
                return createTable;
            }
            pointAdd(pointCopy2, pointCopy);
        }
    }

    private static PointExt[] pointPrecompVar(PointExt pointExt, int i) {
        PointExt pointCopy = pointCopy(pointExt);
        pointDouble(pointCopy);
        PointExt[] pointExtArr = new PointExt[i];
        pointExtArr[0] = pointCopy(pointExt);
        for (int i2 = 1; i2 < i; i2++) {
            PointExt pointCopy2 = pointCopy(pointExtArr[i2 - 1]);
            pointExtArr[i2] = pointCopy2;
            pointAddVar(false, pointCopy, pointCopy2);
        }
        return pointExtArr;
    }

    private static void pointSetNeutral(PointExt pointExt) {
        X448Field.zero(pointExt.f837x);
        X448Field.one(pointExt.f838y);
        X448Field.one(pointExt.f839z);
    }

    public static void precompute() {
        synchronized (precompLock) {
            if (precompBase == null) {
                PointExt pointExt = new PointExt();
                X448Field.copy(B_x, 0, pointExt.f837x, 0);
                X448Field.copy(B_y, 0, pointExt.f838y, 0);
                pointExtendXY(pointExt);
                precompBaseTable = pointPrecompVar(pointExt, 32);
                precompBase = X448Field.createTable(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
                int i = 0;
                for (int i2 = 0; i2 < 5; i2++) {
                    PointExt[] pointExtArr = new PointExt[5];
                    PointExt pointExt2 = new PointExt();
                    pointSetNeutral(pointExt2);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= 5) {
                            break;
                        }
                        pointAddVar(true, pointExt, pointExt2);
                        pointDouble(pointExt);
                        pointExtArr[i3] = pointCopy(pointExt);
                        if (i2 + i3 != 8) {
                            for (int i4 = 1; i4 < 18; i4++) {
                                pointDouble(pointExt);
                            }
                        }
                        i3++;
                    }
                    PointExt[] pointExtArr2 = new PointExt[16];
                    pointExtArr2[0] = pointExt2;
                    int i5 = 1;
                    for (int i6 = 0; i6 < 4; i6++) {
                        int i7 = 1 << i6;
                        int i8 = 0;
                        while (i8 < i7) {
                            PointExt pointCopy = pointCopy(pointExtArr2[i5 - i7]);
                            pointExtArr2[i5] = pointCopy;
                            pointAddVar(false, pointExtArr[i6], pointCopy);
                            i8++;
                            i5++;
                        }
                    }
                    for (int i9 = 0; i9 < 16; i9++) {
                        PointExt pointExt3 = pointExtArr2[i9];
                        X448Field.inv(pointExt3.f839z, pointExt3.f839z);
                        X448Field.mul(pointExt3.f837x, pointExt3.f839z, pointExt3.f837x);
                        X448Field.mul(pointExt3.f838y, pointExt3.f839z, pointExt3.f838y);
                        X448Field.copy(pointExt3.f837x, 0, precompBase, i);
                        int i10 = i + 16;
                        X448Field.copy(pointExt3.f838y, 0, precompBase, i10);
                        i = i10 + 16;
                    }
                }
            }
        }
    }

    private static void pruneScalar(byte[] bArr, int i, byte[] bArr2) {
        System.arraycopy(bArr, i, bArr2, 0, 56);
        bArr2[0] = (byte) (bArr2[0] & 252);
        bArr2[55] = (byte) (bArr2[55] | ByteCompanionObject.MIN_VALUE);
        bArr2[56] = 0;
    }

    private static byte[] reduceScalar(byte[] bArr) {
        byte[] bArr2 = bArr;
        long decode32 = ((long) decode32(bArr2, 7)) & 4294967295L;
        long decode24 = ((long) (decode24(bArr2, 32) << 4)) & 4294967295L;
        long decode242 = ((long) (decode24(bArr2, 11) << 4)) & 4294967295L;
        long decode322 = ((long) decode32(bArr2, 35)) & 4294967295L;
        long decode323 = ((long) decode32(bArr2, 42)) & 4294967295L;
        long decode243 = ((long) (decode24(bArr2, 46) << 4)) & 4294967295L;
        long decode244 = ((long) (decode24(bArr2, 18) << 4)) & 4294967295L;
        long decode324 = ((long) decode32(bArr2, 21)) & 4294967295L;
        long decode325 = ((long) decode32(bArr2, 28)) & 4294967295L;
        long decode245 = ((long) (decode24(bArr2, 39) << 4)) & 4294967295L;
        long decode246 = ((long) (decode24(bArr2, 53) << 4)) & 4294967295L;
        long decode326 = ((long) decode32(bArr2, 70)) & 4294967295L;
        long decode247 = ((long) (decode24(bArr2, 74) << 4)) & 4294967295L;
        long decode327 = ((long) decode32(bArr2, 77)) & 4294967295L;
        long decode248 = ((long) (decode24(bArr2, 81) << 4)) & 4294967295L;
        long decode328 = ((long) decode32(bArr2, 84)) & 4294967295L;
        long decode249 = ((long) (decode24(bArr2, 88) << 4)) & 4294967295L;
        long decode329 = ((long) decode32(bArr2, 91)) & 4294967295L;
        long decode2410 = ((long) (decode24(bArr2, 95) << 4)) & 4294967295L;
        long decode3210 = ((long) decode32(bArr2, 98)) & 4294967295L;
        long decode2411 = ((long) (decode24(bArr2, 102) << 4)) & 4294967295L;
        long decode3211 = ((long) decode32(bArr2, 105)) & 4294967295L;
        long decode16 = ((long) decode16(bArr2, 112)) & 4294967295L;
        long j = decode248 + (decode16 * 550336261);
        long decode2412 = (((long) (decode24(bArr2, 109) << 4)) & 4294967295L) + (decode3211 >>> 28);
        long j2 = decode3211 & M28L;
        long decode3212 = (((long) decode32(bArr2, 56)) & 4294967295L) + (decode16 * 43969588) + (decode2412 * 30366549);
        long decode2413 = (((long) (decode24(bArr2, 60) << 4)) & 4294967295L) + (decode16 * 30366549) + (decode2412 * 163752818);
        long decode3213 = (((long) decode32(bArr2, 63)) & 4294967295L) + (decode16 * 163752818) + (decode2412 * 258169998);
        long decode2414 = (((long) (decode24(bArr2, 67) << 4)) & 4294967295L) + (decode16 * 258169998) + (decode2412 * 96434764);
        long j3 = decode327 + (decode16 * 149865618) + (decode2412 * 550336261);
        long decode3214 = (((long) decode32(bArr2, 49)) & 4294967295L) + (j2 * 43969588);
        long j4 = decode2411 + (decode3210 >>> 28);
        long j5 = decode3210 & M28L;
        long j6 = decode326 + (decode16 * 96434764) + (decode2412 * 227822194) + (j2 * 149865618) + (j4 * 550336261);
        long j7 = decode2410 + (decode329 >>> 28);
        long j8 = decode329 & M28L;
        long j9 = decode3213 + (j2 * 96434764) + (j4 * 227822194) + (j5 * 149865618) + (j7 * 550336261);
        long j10 = decode2413 + (j2 * 258169998) + (j4 * 96434764) + (j5 * 227822194) + (j7 * 149865618) + (j8 * 550336261);
        long j11 = decode249 + (decode328 >>> 28);
        long j12 = decode328 & M28L;
        long j13 = decode247 + (decode16 * 227822194) + (decode2412 * 149865618) + (j2 * 550336261) + (j6 >>> 28);
        long j14 = j6 & M28L;
        long j15 = j3 + (j13 >>> 28);
        long j16 = j13 & M28L;
        long j17 = j + (j15 >>> 28);
        long j18 = j15 & M28L;
        long j19 = j12 + (j17 >>> 28);
        long j20 = j17 & M28L;
        long decode2415 = (((long) (decode24(bArr2, 25) << 4)) & 4294967295L) + (j20 * 43969588);
        long j21 = decode325 + (j19 * 43969588) + (j20 * 30366549);
        long j22 = decode24 + (j11 * 43969588) + (j19 * 30366549) + (j20 * 163752818);
        long j23 = decode322 + (j8 * 43969588) + (j11 * 30366549) + (j19 * 163752818) + (j20 * 258169998);
        long j24 = decode245 + (j7 * 43969588) + (j8 * 30366549) + (j11 * 163752818) + (j19 * 258169998) + (j20 * 96434764);
        long j25 = decode323 + (j5 * 43969588) + (j7 * 30366549) + (j8 * 163752818) + (j11 * 258169998) + (j19 * 96434764) + (j20 * 227822194);
        long j26 = decode3214 + (j4 * 30366549) + (j5 * 163752818) + (j7 * 258169998) + (j8 * 96434764) + (j11 * 227822194) + (j19 * 149865618) + (j20 * 550336261);
        long j27 = decode324 + (j18 * 43969588);
        long j28 = decode243 + (j4 * 43969588) + (j5 * 30366549) + (j7 * 163752818) + (j8 * 258169998) + (j11 * 96434764) + (j19 * 227822194) + (j20 * 149865618) + (j18 * 550336261);
        long j29 = j9 + (j10 >>> 28);
        long j30 = j10 & M28L;
        long j31 = decode2414 + (j2 * 227822194) + (j4 * 149865618) + (j5 * 550336261) + (j29 >>> 28);
        long j32 = j29 & M28L;
        long j33 = j14 + (j31 >>> 28);
        long j34 = j31 & M28L;
        long j35 = j16 + (j33 >>> 28);
        long j36 = j33 & M28L;
        long decode3215 = (((long) decode32(bArr2, 14)) & 4294967295L) + (j36 * 43969588);
        long j37 = decode242 + (j34 * 43969588);
        long j38 = decode3215 + (j34 * 30366549);
        long j39 = decode244 + (j35 * 43969588) + (j36 * 30366549) + (j34 * 163752818);
        long j40 = j27 + (j35 * 30366549) + (j36 * 163752818) + (j34 * 258169998);
        long j41 = decode2415 + (j18 * 30366549) + (j35 * 163752818) + (j36 * 258169998) + (j34 * 96434764);
        long j42 = j21 + (j18 * 163752818) + (j35 * 258169998) + (j36 * 96434764) + (j34 * 227822194);
        long j43 = j23 + (j18 * 96434764) + (j35 * 227822194) + (j36 * 149865618) + (j34 * 550336261);
        long j44 = decode246 + (decode2412 * 43969588) + (j2 * 30366549) + (j4 * 163752818) + (j5 * 258169998) + (j7 * 96434764) + (j8 * 227822194) + (j11 * 149865618) + (j19 * 550336261) + (j26 >>> 28);
        long j45 = j26 & M28L;
        long j46 = decode3212 + (j2 * 163752818) + (j4 * 258169998) + (j5 * 96434764) + (j7 * 227822194) + (j8 * 149865618) + (j11 * 550336261) + (j44 >>> 28);
        long j47 = j44 & M28L;
        long j48 = j30 + (j46 >>> 28);
        long j49 = j46 & M28L;
        long j50 = j32 + (j48 >>> 28);
        long j51 = j48 & M28L;
        long j52 = decode32 + (j50 * 43969588);
        long j53 = j37 + (j50 * 30366549);
        long j54 = j38 + (j50 * 163752818);
        long j55 = j40 + (j50 * 96434764);
        long j56 = j41 + (j50 * 227822194);
        long j57 = j22 + (j18 * 258169998) + (j35 * 96434764) + (j36 * 227822194) + (j34 * 149865618) + (j50 * 550336261);
        long j58 = j47 & M26L;
        long j59 = (j49 * 4) + (j47 >>> 26) + 1;
        long decode3216 = (((long) decode32(bArr2, 0)) & 4294967295L) + (78101261 * j59);
        long decode2416 = (((long) (decode24(bArr2, 4) << 4)) & 4294967295L) + (43969588 * j51) + (141809365 * j59) + (decode3216 >>> 28);
        long j60 = decode3216 & M28L;
        long j61 = j52 + (30366549 * j51) + (175155932 * j59) + (decode2416 >>> 28);
        long j62 = decode2416 & M28L;
        long j63 = j53 + (163752818 * j51) + (64542499 * j59) + (j61 >>> 28);
        long j64 = j61 & M28L;
        long j65 = j54 + (258169998 * j51) + (158326419 * j59) + (j63 >>> 28);
        long j66 = j63 & M28L;
        long j67 = j39 + (j50 * 258169998) + (96434764 * j51) + (191173276 * j59) + (j65 >>> 28);
        long j68 = j65 & M28L;
        long j69 = j55 + (227822194 * j51) + (104575268 * j59) + (j67 >>> 28);
        long j70 = j67 & M28L;
        long j71 = j56 + (149865618 * j51) + (j59 * 137584065) + (j69 >>> 28);
        long j72 = j69 & M28L;
        long j73 = j42 + (j50 * 149865618) + (j51 * 550336261) + (j71 >>> 28);
        long j74 = j71 & M28L;
        long j75 = j57 + (j73 >>> 28);
        long j76 = j73 & M28L;
        long j77 = j43 + (j75 >>> 28);
        long j78 = j75 & M28L;
        long j79 = j24 + (j18 * 227822194) + (j35 * 149865618) + (j36 * 550336261) + (j77 >>> 28);
        long j80 = j77 & M28L;
        long j81 = j25 + (j18 * 149865618) + (j35 * 550336261) + (j79 >>> 28);
        long j82 = j79 & M28L;
        long j83 = j28 + (j81 >>> 28);
        long j84 = j81 & M28L;
        long j85 = j45 + (j83 >>> 28);
        long j86 = j83 & M28L;
        long j87 = j58 + (j85 >>> 28);
        long j88 = j85 & M28L;
        long j89 = M26L & j87;
        long j90 = (j87 >>> 26) - 1;
        long j91 = j60 - (78101261 & j90);
        long j92 = (j62 - (141809365 & j90)) + (j91 >> 28);
        long j93 = j91 & M28L;
        long j94 = (j64 - (175155932 & j90)) + (j92 >> 28);
        long j95 = j92 & M28L;
        long j96 = (j66 - (64542499 & j90)) + (j94 >> 28);
        long j97 = j94 & M28L;
        long j98 = (j68 - (158326419 & j90)) + (j96 >> 28);
        long j99 = j96 & M28L;
        long j100 = (j70 - (191173276 & j90)) + (j98 >> 28);
        long j101 = j98 & M28L;
        long j102 = (j72 - (104575268 & j90)) + (j100 >> 28);
        long j103 = j100 & M28L;
        long j104 = (j74 - (j90 & 137584065)) + (j102 >> 28);
        long j105 = j102 & M28L;
        long j106 = j76 + (j104 >> 28);
        long j107 = j104 & M28L;
        long j108 = j78 + (j106 >> 28);
        long j109 = j106 & M28L;
        long j110 = j80 + (j108 >> 28);
        long j111 = j108 & M28L;
        long j112 = j82 + (j110 >> 28);
        long j113 = j110 & M28L;
        long j114 = j84 + (j112 >> 28);
        long j115 = j112 & M28L;
        long j116 = j86 + (j114 >> 28);
        long j117 = j114 & M28L;
        long j118 = j88 + (j116 >> 28);
        long j119 = j116 & M28L;
        long j120 = j118 & M28L;
        byte[] bArr3 = new byte[57];
        encode56((j95 << 28) | j93, bArr3, 0);
        encode56((j99 << 28) | j97, bArr3, 7);
        encode56(j101 | (j103 << 28), bArr3, 14);
        encode56(j105 | (j107 << 28), bArr3, 21);
        encode56(j109 | (j111 << 28), bArr3, 28);
        encode56(j113 | (j115 << 28), bArr3, 35);
        encode56(j117 | (j119 << 28), bArr3, 42);
        encode56(((j89 + (j118 >> 28)) << 28) | j120, bArr3, 49);
        return bArr3;
    }

    private static void scalarMult(byte[] bArr, PointExt pointExt, PointExt pointExt2) {
        precompute();
        int[] iArr = new int[14];
        decodeScalar(bArr, 0, iArr);
        Nat.shiftDownBits(14, iArr, 2, 0);
        Nat.cadd(14, (iArr[0] ^ -1) & 1, iArr, f835L, iArr);
        Nat.shiftDownBit(14, iArr, 1);
        int[] pointPrecomp = pointPrecomp(pointExt, 8);
        pointLookup(iArr, 111, pointPrecomp, pointExt2);
        PointExt pointExt3 = new PointExt();
        for (int i = 110; i >= 0; i--) {
            for (int i2 = 0; i2 < 4; i2++) {
                pointDouble(pointExt2);
            }
            pointLookup(iArr, i, pointPrecomp, pointExt3);
            pointAdd(pointExt3, pointExt2);
        }
        for (int i3 = 0; i3 < 2; i3++) {
            pointDouble(pointExt2);
        }
    }

    private static void scalarMultBase(byte[] bArr, PointExt pointExt) {
        precompute();
        pointSetNeutral(pointExt);
        int[] iArr = new int[15];
        decodeScalar(bArr, 0, iArr);
        iArr[14] = Nat.cadd(14, (iArr[0] ^ -1) & 1, iArr, f835L, iArr) + 4;
        Nat.shiftDownBit(15, iArr, 0);
        PointPrecomp pointPrecomp = new PointPrecomp();
        int i = 17;
        while (true) {
            int i2 = i;
            for (int i3 = 0; i3 < 5; i3++) {
                int i4 = 0;
                for (int i5 = 0; i5 < 5; i5++) {
                    i4 = (i4 & ((1 << i5) ^ -1)) ^ ((iArr[i2 >>> 5] >>> (i2 & 31)) << i5);
                    i2 += 18;
                }
                int i6 = (i4 >>> 4) & 1;
                pointLookup(i3, ((-i6) ^ i4) & 15, pointPrecomp);
                X448Field.cnegate(i6, pointPrecomp.f840x);
                pointAddPrecomp(pointPrecomp, pointExt);
            }
            i--;
            if (i >= 0) {
                pointDouble(pointExt);
            } else {
                return;
            }
        }
    }

    private static void scalarMultBaseEncoded(byte[] bArr, byte[] bArr2, int i) {
        PointExt pointExt = new PointExt();
        scalarMultBase(bArr, pointExt);
        if (encodePoint(pointExt, bArr2, i) == 0) {
            throw new IllegalStateException();
        }
    }

    public static void scalarMultBaseXY(X448.Friend friend, byte[] bArr, int i, int[] iArr, int[] iArr2) {
        if (friend != null) {
            byte[] bArr2 = new byte[57];
            pruneScalar(bArr, i, bArr2);
            PointExt pointExt = new PointExt();
            scalarMultBase(bArr2, pointExt);
            if (checkPoint(pointExt.f837x, pointExt.f838y, pointExt.f839z) != 0) {
                X448Field.copy(pointExt.f837x, 0, iArr, 0);
                X448Field.copy(pointExt.f838y, 0, iArr2, 0);
                return;
            }
            throw new IllegalStateException();
        }
        throw new NullPointerException("This method is only for use by X448");
    }

    private static void scalarMultStrausVar(int[] iArr, int[] iArr2, PointExt pointExt, PointExt pointExt2) {
        precompute();
        byte[] wnaf = getWNAF(iArr, 7);
        byte[] wnaf2 = getWNAF(iArr2, 5);
        PointExt[] pointPrecompVar = pointPrecompVar(pointExt, 8);
        pointSetNeutral(pointExt2);
        int i = 446;
        while (true) {
            byte b = wnaf[i];
            boolean z = false;
            if (b != 0) {
                int i2 = b >> 31;
                pointAddVar(i2 != 0, precompBaseTable[(b ^ i2) >>> 1], pointExt2);
            }
            byte b2 = wnaf2[i];
            if (b2 != 0) {
                int i3 = b2 >> 31;
                int i4 = (b2 ^ i3) >>> 1;
                if (i3 != 0) {
                    z = true;
                }
                pointAddVar(z, pointPrecompVar[i4], pointExt2);
            }
            i--;
            if (i >= 0) {
                pointDouble(pointExt2);
            } else {
                return;
            }
        }
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, int i4, byte[] bArr5, int i5) {
        implSign(bArr, i, bArr2, i2, bArr3, (byte) 0, bArr4, i3, i4, bArr5, i5);
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2, int i3, byte[] bArr4, int i4) {
        implSign(bArr, i, bArr2, (byte) 0, bArr3, i2, i3, bArr4, i4);
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, Xof xof, byte[] bArr4, int i3) {
        byte[] bArr5 = new byte[64];
        if (64 == xof.doFinal(bArr5, 0, 64)) {
            implSign(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr5, 0, 64, bArr4, i3);
            return;
        }
        throw new IllegalArgumentException("ph");
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, byte[] bArr5, int i4) {
        implSign(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, i3, 64, bArr5, i4);
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, Xof xof, byte[] bArr3, int i2) {
        byte[] bArr4 = new byte[64];
        if (64 == xof.doFinal(bArr4, 0, 64)) {
            implSign(bArr, i, bArr2, (byte) 1, bArr4, 0, 64, bArr3, i2);
            return;
        }
        throw new IllegalArgumentException("ph");
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2, byte[] bArr4, int i3) {
        implSign(bArr, i, bArr2, (byte) 1, bArr3, i2, 64, bArr4, i3);
    }

    public static boolean verify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, int i4) {
        return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 0, bArr4, i3, i4);
    }

    public static boolean verifyPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, Xof xof) {
        byte[] bArr4 = new byte[64];
        if (64 == xof.doFinal(bArr4, 0, 64)) {
            return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, 0, 64);
        }
        throw new IllegalArgumentException("ph");
    }

    public static boolean verifyPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3) {
        return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, i3, 64);
    }
}

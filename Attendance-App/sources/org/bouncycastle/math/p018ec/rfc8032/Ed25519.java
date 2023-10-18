package org.bouncycastle.math.p018ec.rfc8032;

import java.security.SecureRandom;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.math.p018ec.rfc7748.X25519;
import org.bouncycastle.math.p018ec.rfc7748.X25519Field;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519 */
public abstract class Ed25519 {
    private static final int[] B_x = {52811034, 25909283, 8072341, 50637101, 13785486, 30858332, 20483199, 20966410, 43936626, 4379245};
    private static final int[] B_y = {40265304, 26843545, 6710886, 53687091, 13421772, 40265318, 26843545, 6710886, 53687091, 13421772};
    private static final int[] C_d = {56195235, 47411844, 25868126, 40503822, 57364, 58321048, 30416477, 31930572, 57760639, 10749657};
    private static final int[] C_d2 = {45281625, 27714825, 18181821, 13898781, 114729, 49533232, 60832955, 30306712, 48412415, 4722099};
    private static final int[] C_d4 = {23454386, 55429651, 2809210, 27797563, 229458, 31957600, 54557047, 27058993, 29715967, 9444199};
    private static final byte[] DOM2_PREFIX = Strings.toByteArray("SigEd25519 no Ed25519 collisions");

    /* renamed from: L */
    private static final int[] f817L = {1559614445, 1477600026, -1560830762, 350157278, 0, 0, 0, 268435456};

    /* renamed from: L0 */
    private static final int f818L0 = -50998291;

    /* renamed from: L1 */
    private static final int f819L1 = 19280294;

    /* renamed from: L2 */
    private static final int f820L2 = 127719000;

    /* renamed from: L3 */
    private static final int f821L3 = -6428113;

    /* renamed from: L4 */
    private static final int f822L4 = 5343;
    private static final long M28L = 268435455;
    private static final long M32L = 4294967295L;

    /* renamed from: P */
    private static final int[] f823P = {-19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE};
    private static final int POINT_BYTES = 32;
    private static final int PRECOMP_BLOCKS = 8;
    private static final int PRECOMP_MASK = 7;
    private static final int PRECOMP_POINTS = 8;
    private static final int PRECOMP_SPACING = 8;
    private static final int PRECOMP_TEETH = 4;
    public static final int PREHASH_SIZE = 64;
    public static final int PUBLIC_KEY_SIZE = 32;
    private static final int SCALAR_BYTES = 32;
    private static final int SCALAR_INTS = 8;
    public static final int SECRET_KEY_SIZE = 32;
    public static final int SIGNATURE_SIZE = 64;
    private static final int WNAF_WIDTH_BASE = 7;
    private static int[] precompBase = null;
    private static PointExt[] precompBaseTable = null;
    private static final Object precompLock = new Object();

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$Algorithm */
    public static final class Algorithm {
        public static final int Ed25519 = 0;
        public static final int Ed25519ctx = 1;
        public static final int Ed25519ph = 2;
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$PointAccum */
    private static class PointAccum {

        /* renamed from: u */
        int[] f824u;

        /* renamed from: v */
        int[] f825v;

        /* renamed from: x */
        int[] f826x;

        /* renamed from: y */
        int[] f827y;

        /* renamed from: z */
        int[] f828z;

        private PointAccum() {
            this.f826x = X25519Field.create();
            this.f827y = X25519Field.create();
            this.f828z = X25519Field.create();
            this.f824u = X25519Field.create();
            this.f825v = X25519Field.create();
        }
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$PointAffine */
    private static class PointAffine {

        /* renamed from: x */
        int[] f829x;

        /* renamed from: y */
        int[] f830y;

        private PointAffine() {
            this.f829x = X25519Field.create();
            this.f830y = X25519Field.create();
        }
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$PointExt */
    private static class PointExt {

        /* renamed from: t */
        int[] f831t;

        /* renamed from: x */
        int[] f832x;

        /* renamed from: y */
        int[] f833y;

        /* renamed from: z */
        int[] f834z;

        private PointExt() {
            this.f832x = X25519Field.create();
            this.f833y = X25519Field.create();
            this.f834z = X25519Field.create();
            this.f831t = X25519Field.create();
        }
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$PointPrecomp */
    private static class PointPrecomp {
        int[] xyd;
        int[] ymx_h;
        int[] ypx_h;

        private PointPrecomp() {
            this.ypx_h = X25519Field.create();
            this.ymx_h = X25519Field.create();
            this.xyd = X25519Field.create();
        }
    }

    private static byte[] calculateS(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int[] iArr = new int[16];
        decodeScalar(bArr, 0, iArr);
        int[] iArr2 = new int[8];
        decodeScalar(bArr2, 0, iArr2);
        int[] iArr3 = new int[8];
        decodeScalar(bArr3, 0, iArr3);
        Nat256.mulAddTo(iArr2, iArr3, iArr);
        byte[] bArr4 = new byte[64];
        for (int i = 0; i < 16; i++) {
            encode32(iArr[i], bArr4, i * 4);
        }
        return reduceScalar(bArr4);
    }

    private static boolean checkContextVar(byte[] bArr, byte b) {
        return (bArr == null && b == 0) || (bArr != null && bArr.length < 256);
    }

    private static int checkPoint(int[] iArr, int[] iArr2) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        X25519Field.sqr(iArr, create2);
        X25519Field.sqr(iArr2, create3);
        X25519Field.mul(create2, create3, create);
        X25519Field.sub(create3, create2, create3);
        X25519Field.mul(create, C_d, create);
        X25519Field.addOne(create);
        X25519Field.sub(create, create3, create);
        X25519Field.normalize(create);
        return X25519Field.isZero(create);
    }

    private static int checkPoint(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        X25519Field.sqr(iArr, create2);
        X25519Field.sqr(iArr2, create3);
        X25519Field.sqr(iArr3, create4);
        X25519Field.mul(create2, create3, create);
        X25519Field.sub(create3, create2, create3);
        X25519Field.mul(create3, create4, create3);
        X25519Field.sqr(create4, create4);
        X25519Field.mul(create, C_d, create);
        X25519Field.add(create, create4, create);
        X25519Field.sub(create, create3, create);
        X25519Field.normalize(create);
        return X25519Field.isZero(create);
    }

    private static boolean checkPointVar(byte[] bArr) {
        int[] iArr = new int[8];
        decode32(bArr, 0, iArr, 0, 8);
        iArr[7] = iArr[7] & Integer.MAX_VALUE;
        return !Nat256.gte(iArr, f823P);
    }

    private static boolean checkScalarVar(byte[] bArr) {
        int[] iArr = new int[8];
        decodeScalar(bArr, 0, iArr);
        return !Nat256.gte(iArr, f817L);
    }

    private static Digest createDigest() {
        return new SHA512Digest();
    }

    public static Digest createPrehash() {
        return createDigest();
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

    private static boolean decodePointVar(byte[] bArr, int i, boolean z, PointAffine pointAffine) {
        byte[] copyOfRange = Arrays.copyOfRange(bArr, i, i + 32);
        boolean z2 = false;
        if (!checkPointVar(copyOfRange)) {
            return false;
        }
        byte b = copyOfRange[31];
        int i2 = (b & ByteCompanionObject.MIN_VALUE) >>> 7;
        copyOfRange[31] = (byte) (b & ByteCompanionObject.MAX_VALUE);
        X25519Field.decode(copyOfRange, 0, pointAffine.f830y);
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        X25519Field.sqr(pointAffine.f830y, create);
        X25519Field.mul(C_d, create, create2);
        X25519Field.subOne(create);
        X25519Field.addOne(create2);
        if (!X25519Field.sqrtRatioVar(create, create2, pointAffine.f829x)) {
            return false;
        }
        X25519Field.normalize(pointAffine.f829x);
        if (i2 == 1 && X25519Field.isZeroVar(pointAffine.f829x)) {
            return false;
        }
        if (i2 != (pointAffine.f829x[0] & 1)) {
            z2 = true;
        }
        if (z ^ z2) {
            X25519Field.negate(pointAffine.f829x, pointAffine.f829x);
        }
        return true;
    }

    private static void decodeScalar(byte[] bArr, int i, int[] iArr) {
        decode32(bArr, i, iArr, 0, 8);
    }

    private static void dom2(Digest digest, byte b, byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = DOM2_PREFIX;
            digest.update(bArr2, 0, bArr2.length);
            digest.update(b);
            digest.update((byte) bArr.length);
            digest.update(bArr, 0, bArr.length);
        }
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

    private static int encodePoint(PointAccum pointAccum, byte[] bArr, int i) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        X25519Field.inv(pointAccum.f828z, create2);
        X25519Field.mul(pointAccum.f826x, create2, create);
        X25519Field.mul(pointAccum.f827y, create2, create2);
        X25519Field.normalize(create);
        X25519Field.normalize(create2);
        int checkPoint = checkPoint(create, create2);
        X25519Field.encode(create2, bArr, i);
        int i2 = (i + 32) - 1;
        bArr[i2] = (byte) (((create[0] & 1) << 7) | bArr[i2]);
        return checkPoint;
    }

    public static void generatePrivateKey(SecureRandom secureRandom, byte[] bArr) {
        secureRandom.nextBytes(bArr);
    }

    public static void generatePublicKey(byte[] bArr, int i, byte[] bArr2, int i2) {
        Digest createDigest = createDigest();
        byte[] bArr3 = new byte[createDigest.getDigestSize()];
        createDigest.update(bArr, i, 32);
        createDigest.doFinal(bArr3, 0);
        byte[] bArr4 = new byte[32];
        pruneScalar(bArr3, 0, bArr4);
        scalarMultBaseEncoded(bArr4, bArr2, i2);
    }

    private static byte[] getWNAF(int[] iArr, int i) {
        int i2;
        int[] iArr2 = new int[16];
        int i3 = 0;
        int i4 = 8;
        int i5 = 16;
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
        byte[] bArr = new byte[253];
        int i9 = 1 << i;
        int i10 = i9 - 1;
        int i11 = i9 >>> 1;
        int i12 = 0;
        int i13 = 0;
        while (i3 < 16) {
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

    private static void implSign(Digest digest, byte[] bArr, byte[] bArr2, byte[] bArr3, int i, byte[] bArr4, byte b, byte[] bArr5, int i2, int i3, byte[] bArr6, int i4) {
        dom2(digest, b, bArr4);
        digest.update(bArr, 32, 32);
        digest.update(bArr5, i2, i3);
        digest.doFinal(bArr, 0);
        byte[] reduceScalar = reduceScalar(bArr);
        byte[] bArr7 = new byte[32];
        scalarMultBaseEncoded(reduceScalar, bArr7, 0);
        dom2(digest, b, bArr4);
        digest.update(bArr7, 0, 32);
        digest.update(bArr3, i, 32);
        digest.update(bArr5, i2, i3);
        digest.doFinal(bArr, 0);
        byte[] calculateS = calculateS(reduceScalar, reduceScalar(bArr), bArr2);
        System.arraycopy(bArr7, 0, bArr6, i4, 32);
        System.arraycopy(calculateS, 0, bArr6, i4 + 32, 32);
    }

    private static void implSign(byte[] bArr, int i, byte[] bArr2, byte b, byte[] bArr3, int i2, int i3, byte[] bArr4, int i4) {
        if (checkContextVar(bArr2, b)) {
            Digest createDigest = createDigest();
            byte[] bArr5 = new byte[createDigest.getDigestSize()];
            byte[] bArr6 = bArr;
            int i5 = i;
            createDigest.update(bArr, i, 32);
            createDigest.doFinal(bArr5, 0);
            byte[] bArr7 = new byte[32];
            pruneScalar(bArr5, 0, bArr7);
            byte[] bArr8 = new byte[32];
            scalarMultBaseEncoded(bArr7, bArr8, 0);
            implSign(createDigest, bArr5, bArr7, bArr8, 0, bArr2, b, bArr3, i2, i3, bArr4, i4);
            return;
        }
        throw new IllegalArgumentException("ctx");
    }

    private static void implSign(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte b, byte[] bArr4, int i3, int i4, byte[] bArr5, int i5) {
        if (checkContextVar(bArr3, b)) {
            Digest createDigest = createDigest();
            byte[] bArr6 = new byte[createDigest.getDigestSize()];
            byte[] bArr7 = bArr;
            int i6 = i;
            createDigest.update(bArr, i, 32);
            createDigest.doFinal(bArr6, 0);
            byte[] bArr8 = new byte[32];
            pruneScalar(bArr6, 0, bArr8);
            implSign(createDigest, bArr6, bArr8, bArr2, i2, bArr3, b, bArr4, i3, i4, bArr5, i5);
            return;
        }
        throw new IllegalArgumentException("ctx");
    }

    private static boolean implVerify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte b, byte[] bArr4, int i3, int i4) {
        if (checkContextVar(bArr3, b)) {
            int i5 = i + 32;
            byte[] copyOfRange = Arrays.copyOfRange(bArr, i, i5);
            byte[] copyOfRange2 = Arrays.copyOfRange(bArr, i5, i + 64);
            if (!checkPointVar(copyOfRange) || !checkScalarVar(copyOfRange2)) {
                return false;
            }
            PointAffine pointAffine = new PointAffine();
            if (!decodePointVar(bArr2, i2, true, pointAffine)) {
                return false;
            }
            Digest createDigest = createDigest();
            byte[] bArr5 = new byte[createDigest.getDigestSize()];
            dom2(createDigest, b, bArr3);
            createDigest.update(copyOfRange, 0, 32);
            createDigest.update(bArr2, i2, 32);
            createDigest.update(bArr4, i3, i4);
            createDigest.doFinal(bArr5, 0);
            byte[] reduceScalar = reduceScalar(bArr5);
            int[] iArr = new int[8];
            decodeScalar(copyOfRange2, 0, iArr);
            int[] iArr2 = new int[8];
            decodeScalar(reduceScalar, 0, iArr2);
            PointAccum pointAccum = new PointAccum();
            scalarMultStrausVar(iArr, iArr2, pointAffine, pointAccum);
            byte[] bArr6 = new byte[32];
            return encodePoint(pointAccum, bArr6, 0) != 0 && Arrays.areEqual(bArr6, copyOfRange);
        }
        throw new IllegalArgumentException("ctx");
    }

    private static void pointAdd(PointExt pointExt, PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] iArr = pointAccum.f824u;
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] iArr2 = pointAccum.f825v;
        X25519Field.apm(pointAccum.f827y, pointAccum.f826x, create2, create);
        X25519Field.apm(pointExt.f833y, pointExt.f832x, create4, create3);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointAccum.f824u, pointAccum.f825v, create3);
        X25519Field.mul(create3, pointExt.f831t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointAccum.f828z, pointExt.f834z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, iArr2, iArr);
        X25519Field.apm(create4, create3, create6, create5);
        X25519Field.carry(create6);
        X25519Field.mul(iArr, create5, pointAccum.f826x);
        X25519Field.mul(create6, iArr2, pointAccum.f827y);
        X25519Field.mul(create5, create6, pointAccum.f828z);
    }

    private static void pointAdd(PointExt pointExt, PointExt pointExt2) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] create7 = X25519Field.create();
        int[] create8 = X25519Field.create();
        X25519Field.apm(pointExt.f833y, pointExt.f832x, create2, create);
        X25519Field.apm(pointExt2.f833y, pointExt2.f832x, create4, create3);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointExt.f831t, pointExt2.f831t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointExt.f834z, pointExt2.f834z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, create8, create5);
        X25519Field.apm(create4, create3, create7, create6);
        X25519Field.carry(create7);
        X25519Field.mul(create5, create6, pointExt2.f832x);
        X25519Field.mul(create7, create8, pointExt2.f833y);
        X25519Field.mul(create6, create7, pointExt2.f834z);
        X25519Field.mul(create5, create8, pointExt2.f831t);
    }

    private static void pointAddPrecomp(PointPrecomp pointPrecomp, PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] iArr = pointAccum.f824u;
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] iArr2 = pointAccum.f825v;
        X25519Field.apm(pointAccum.f827y, pointAccum.f826x, create2, create);
        X25519Field.mul(create, pointPrecomp.ymx_h, create);
        X25519Field.mul(create2, pointPrecomp.ypx_h, create2);
        X25519Field.mul(pointAccum.f824u, pointAccum.f825v, create3);
        X25519Field.mul(create3, pointPrecomp.xyd, create3);
        X25519Field.apm(create2, create, iArr2, iArr);
        X25519Field.apm(pointAccum.f828z, create3, create5, create4);
        X25519Field.carry(create5);
        X25519Field.mul(iArr, create4, pointAccum.f826x);
        X25519Field.mul(create5, iArr2, pointAccum.f827y);
        X25519Field.mul(create4, create5, pointAccum.f828z);
    }

    private static void pointAddVar(boolean z, PointExt pointExt, PointAccum pointAccum) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] iArr5 = pointAccum.f824u;
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] iArr6 = pointAccum.f825v;
        if (z) {
            iArr = create3;
            iArr4 = create4;
            iArr3 = create5;
            iArr2 = create6;
        } else {
            iArr4 = create3;
            iArr = create4;
            iArr2 = create5;
            iArr3 = create6;
        }
        X25519Field.apm(pointAccum.f827y, pointAccum.f826x, create2, create);
        X25519Field.apm(pointExt.f833y, pointExt.f832x, iArr, iArr4);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointAccum.f824u, pointAccum.f825v, create3);
        X25519Field.mul(create3, pointExt.f831t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointAccum.f828z, pointExt.f834z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, iArr6, iArr5);
        X25519Field.apm(create4, create3, iArr3, iArr2);
        X25519Field.carry(iArr3);
        X25519Field.mul(iArr5, create5, pointAccum.f826x);
        X25519Field.mul(create6, iArr6, pointAccum.f827y);
        X25519Field.mul(create5, create6, pointAccum.f828z);
    }

    private static void pointAddVar(boolean z, PointExt pointExt, PointExt pointExt2, PointExt pointExt3) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        PointExt pointExt4 = pointExt;
        PointExt pointExt5 = pointExt2;
        PointExt pointExt6 = pointExt3;
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] create7 = X25519Field.create();
        int[] create8 = X25519Field.create();
        if (z) {
            iArr4 = create3;
            iArr3 = create4;
            iArr2 = create6;
            iArr = create7;
        } else {
            iArr3 = create3;
            iArr4 = create4;
            iArr = create6;
            iArr2 = create7;
        }
        X25519Field.apm(pointExt4.f833y, pointExt4.f832x, create2, create);
        X25519Field.apm(pointExt5.f833y, pointExt5.f832x, iArr4, iArr3);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointExt4.f831t, pointExt5.f831t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointExt4.f834z, pointExt5.f834z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, create8, create5);
        X25519Field.apm(create4, create3, iArr2, iArr);
        X25519Field.carry(iArr2);
        X25519Field.mul(create5, create6, pointExt6.f832x);
        int[] iArr5 = create7;
        X25519Field.mul(iArr5, create8, pointExt6.f833y);
        X25519Field.mul(create6, iArr5, pointExt6.f834z);
        X25519Field.mul(create5, create8, pointExt6.f831t);
    }

    private static PointExt pointCopy(PointAccum pointAccum) {
        PointExt pointExt = new PointExt();
        X25519Field.copy(pointAccum.f826x, 0, pointExt.f832x, 0);
        X25519Field.copy(pointAccum.f827y, 0, pointExt.f833y, 0);
        X25519Field.copy(pointAccum.f828z, 0, pointExt.f834z, 0);
        X25519Field.mul(pointAccum.f824u, pointAccum.f825v, pointExt.f831t);
        return pointExt;
    }

    private static PointExt pointCopy(PointAffine pointAffine) {
        PointExt pointExt = new PointExt();
        X25519Field.copy(pointAffine.f829x, 0, pointExt.f832x, 0);
        X25519Field.copy(pointAffine.f830y, 0, pointExt.f833y, 0);
        pointExtendXY(pointExt);
        return pointExt;
    }

    private static PointExt pointCopy(PointExt pointExt) {
        PointExt pointExt2 = new PointExt();
        pointCopy(pointExt, pointExt2);
        return pointExt2;
    }

    private static void pointCopy(PointAffine pointAffine, PointAccum pointAccum) {
        X25519Field.copy(pointAffine.f829x, 0, pointAccum.f826x, 0);
        X25519Field.copy(pointAffine.f830y, 0, pointAccum.f827y, 0);
        pointExtendXY(pointAccum);
    }

    private static void pointCopy(PointExt pointExt, PointExt pointExt2) {
        X25519Field.copy(pointExt.f832x, 0, pointExt2.f832x, 0);
        X25519Field.copy(pointExt.f833y, 0, pointExt2.f833y, 0);
        X25519Field.copy(pointExt.f834z, 0, pointExt2.f834z, 0);
        X25519Field.copy(pointExt.f831t, 0, pointExt2.f831t, 0);
    }

    private static void pointDouble(PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] iArr = pointAccum.f824u;
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] iArr2 = pointAccum.f825v;
        X25519Field.sqr(pointAccum.f826x, create);
        X25519Field.sqr(pointAccum.f827y, create2);
        X25519Field.sqr(pointAccum.f828z, create3);
        X25519Field.add(create3, create3, create3);
        X25519Field.apm(create, create2, iArr2, create5);
        X25519Field.add(pointAccum.f826x, pointAccum.f827y, iArr);
        X25519Field.sqr(iArr, iArr);
        X25519Field.sub(iArr2, iArr, iArr);
        X25519Field.add(create3, create5, create4);
        X25519Field.carry(create4);
        X25519Field.mul(iArr, create4, pointAccum.f826x);
        X25519Field.mul(create5, iArr2, pointAccum.f827y);
        X25519Field.mul(create4, create5, pointAccum.f828z);
    }

    private static void pointExtendXY(PointAccum pointAccum) {
        X25519Field.one(pointAccum.f828z);
        X25519Field.copy(pointAccum.f826x, 0, pointAccum.f824u, 0);
        X25519Field.copy(pointAccum.f827y, 0, pointAccum.f825v, 0);
    }

    private static void pointExtendXY(PointExt pointExt) {
        X25519Field.one(pointExt.f834z);
        X25519Field.mul(pointExt.f832x, pointExt.f833y, pointExt.f831t);
    }

    private static void pointLookup(int i, int i2, PointPrecomp pointPrecomp) {
        int i3 = i * 8 * 3 * 10;
        for (int i4 = 0; i4 < 8; i4++) {
            int i5 = ((i4 ^ i2) - 1) >> 31;
            X25519Field.cmov(i5, precompBase, i3, pointPrecomp.ypx_h, 0);
            int i6 = i3 + 10;
            X25519Field.cmov(i5, precompBase, i6, pointPrecomp.ymx_h, 0);
            int i7 = i6 + 10;
            X25519Field.cmov(i5, precompBase, i7, pointPrecomp.xyd, 0);
            i3 = i7 + 10;
        }
    }

    private static void pointLookup(int[] iArr, int i, PointExt pointExt) {
        int i2 = i * 40;
        X25519Field.copy(iArr, i2, pointExt.f832x, 0);
        int i3 = i2 + 10;
        X25519Field.copy(iArr, i3, pointExt.f833y, 0);
        int i4 = i3 + 10;
        X25519Field.copy(iArr, i4, pointExt.f834z, 0);
        X25519Field.copy(iArr, i4 + 10, pointExt.f831t, 0);
    }

    private static void pointLookup(int[] iArr, int i, int[] iArr2, PointExt pointExt) {
        int window4 = getWindow4(iArr, i);
        int i2 = (window4 >>> 3) ^ 1;
        int i3 = (window4 ^ (-i2)) & 7;
        int i4 = 0;
        for (int i5 = 0; i5 < 8; i5++) {
            int i6 = ((i5 ^ i3) - 1) >> 31;
            X25519Field.cmov(i6, iArr2, i4, pointExt.f832x, 0);
            int i7 = i4 + 10;
            X25519Field.cmov(i6, iArr2, i7, pointExt.f833y, 0);
            int i8 = i7 + 10;
            X25519Field.cmov(i6, iArr2, i8, pointExt.f834z, 0);
            int i9 = i8 + 10;
            X25519Field.cmov(i6, iArr2, i9, pointExt.f831t, 0);
            i4 = i9 + 10;
        }
        X25519Field.cnegate(i2, pointExt.f832x);
        X25519Field.cnegate(i2, pointExt.f831t);
    }

    private static int[] pointPrecomp(PointAffine pointAffine, int i) {
        PointExt pointCopy = pointCopy(pointAffine);
        PointExt pointCopy2 = pointCopy(pointCopy);
        pointAdd(pointCopy, pointCopy2);
        int[] createTable = X25519Field.createTable(i * 4);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            X25519Field.copy(pointCopy.f832x, 0, createTable, i2);
            int i4 = i2 + 10;
            X25519Field.copy(pointCopy.f833y, 0, createTable, i4);
            int i5 = i4 + 10;
            X25519Field.copy(pointCopy.f834z, 0, createTable, i5);
            int i6 = i5 + 10;
            X25519Field.copy(pointCopy.f831t, 0, createTable, i6);
            i2 = i6 + 10;
            i3++;
            if (i3 == i) {
                return createTable;
            }
            pointAdd(pointCopy2, pointCopy);
        }
    }

    private static PointExt[] pointPrecompVar(PointExt pointExt, int i) {
        PointExt pointExt2 = new PointExt();
        pointAddVar(false, pointExt, pointExt, pointExt2);
        PointExt[] pointExtArr = new PointExt[i];
        pointExtArr[0] = pointCopy(pointExt);
        for (int i2 = 1; i2 < i; i2++) {
            PointExt pointExt3 = pointExtArr[i2 - 1];
            PointExt pointExt4 = new PointExt();
            pointExtArr[i2] = pointExt4;
            pointAddVar(false, pointExt3, pointExt2, pointExt4);
        }
        return pointExtArr;
    }

    private static void pointSetNeutral(PointAccum pointAccum) {
        X25519Field.zero(pointAccum.f826x);
        X25519Field.one(pointAccum.f827y);
        X25519Field.one(pointAccum.f828z);
        X25519Field.zero(pointAccum.f824u);
        X25519Field.one(pointAccum.f825v);
    }

    private static void pointSetNeutral(PointExt pointExt) {
        X25519Field.zero(pointExt.f832x);
        X25519Field.one(pointExt.f833y);
        X25519Field.one(pointExt.f834z);
        X25519Field.zero(pointExt.f831t);
    }

    public static void precompute() {
        int i;
        synchronized (precompLock) {
            if (precompBase == null) {
                PointExt pointExt = new PointExt();
                int[] iArr = B_x;
                X25519Field.copy(iArr, 0, pointExt.f832x, 0);
                int[] iArr2 = B_y;
                X25519Field.copy(iArr2, 0, pointExt.f833y, 0);
                pointExtendXY(pointExt);
                precompBaseTable = pointPrecompVar(pointExt, 32);
                PointAccum pointAccum = new PointAccum();
                X25519Field.copy(iArr, 0, pointAccum.f826x, 0);
                X25519Field.copy(iArr2, 0, pointAccum.f827y, 0);
                pointExtendXY(pointAccum);
                precompBase = X25519Field.createTable(192);
                int i2 = 0;
                for (int i3 = 0; i3 < 8; i3++) {
                    PointExt[] pointExtArr = new PointExt[4];
                    PointExt pointExt2 = new PointExt();
                    pointSetNeutral(pointExt2);
                    int i4 = 0;
                    while (true) {
                        i = 1;
                        if (i4 >= 4) {
                            break;
                        }
                        pointAddVar(true, pointExt2, pointCopy(pointAccum), pointExt2);
                        pointDouble(pointAccum);
                        pointExtArr[i4] = pointCopy(pointAccum);
                        if (i3 + i4 != 10) {
                            while (i < 8) {
                                pointDouble(pointAccum);
                                i++;
                            }
                        }
                        i4++;
                    }
                    PointExt[] pointExtArr2 = new PointExt[8];
                    pointExtArr2[0] = pointExt2;
                    int i5 = 0;
                    int i6 = 1;
                    while (i5 < 3) {
                        int i7 = i << i5;
                        int i8 = 0;
                        while (i8 < i7) {
                            PointExt pointExt3 = pointExtArr2[i6 - i7];
                            PointExt pointExt4 = pointExtArr[i5];
                            PointExt pointExt5 = new PointExt();
                            pointExtArr2[i6] = pointExt5;
                            pointAddVar(false, pointExt3, pointExt4, pointExt5);
                            i8++;
                            i6++;
                        }
                        i5++;
                        i = 1;
                    }
                    for (int i9 = 0; i9 < 8; i9++) {
                        PointExt pointExt6 = pointExtArr2[i9];
                        int[] create = X25519Field.create();
                        int[] create2 = X25519Field.create();
                        X25519Field.add(pointExt6.f834z, pointExt6.f834z, create);
                        X25519Field.inv(create, create2);
                        X25519Field.mul(pointExt6.f832x, create2, create);
                        X25519Field.mul(pointExt6.f833y, create2, create2);
                        PointPrecomp pointPrecomp = new PointPrecomp();
                        X25519Field.apm(create2, create, pointPrecomp.ypx_h, pointPrecomp.ymx_h);
                        X25519Field.mul(create, create2, pointPrecomp.xyd);
                        X25519Field.mul(pointPrecomp.xyd, C_d4, pointPrecomp.xyd);
                        X25519Field.normalize(pointPrecomp.ypx_h);
                        X25519Field.normalize(pointPrecomp.ymx_h);
                        X25519Field.copy(pointPrecomp.ypx_h, 0, precompBase, i2);
                        int i10 = i2 + 10;
                        X25519Field.copy(pointPrecomp.ymx_h, 0, precompBase, i10);
                        int i11 = i10 + 10;
                        X25519Field.copy(pointPrecomp.xyd, 0, precompBase, i11);
                        i2 = i11 + 10;
                    }
                }
            }
        }
    }

    private static void pruneScalar(byte[] bArr, int i, byte[] bArr2) {
        System.arraycopy(bArr, i, bArr2, 0, 32);
        bArr2[0] = (byte) (bArr2[0] & 248);
        byte b = (byte) (bArr2[31] & ByteCompanionObject.MAX_VALUE);
        bArr2[31] = b;
        bArr2[31] = (byte) (b | 64);
    }

    private static byte[] reduceScalar(byte[] bArr) {
        byte[] bArr2 = bArr;
        long decode32 = ((long) decode32(bArr2, 7)) & 4294967295L;
        long decode24 = ((long) (decode24(bArr2, 18) << 4)) & 4294967295L;
        long decode322 = ((long) decode32(bArr2, 21)) & 4294967295L;
        long decode323 = ((long) decode32(bArr2, 28)) & 4294967295L;
        long decode242 = ((long) (decode24(bArr2, 46) << 4)) & 4294967295L;
        long decode324 = ((long) decode32(bArr2, 49)) & 4294967295L;
        long decode243 = ((long) (decode24(bArr2, 53) << 4)) & 4294967295L;
        long decode325 = ((long) decode32(bArr2, 56)) & 4294967295L;
        long decode244 = 4294967295L & ((long) (decode24(bArr2, 60) << 4));
        long j = ((long) bArr2[63]) & 255;
        long j2 = decode242 - (j * 5343);
        long j3 = decode244 + (decode325 >> 28);
        long j4 = decode325 & M28L;
        long j5 = decode323 - (j3 * -50998291);
        long decode245 = ((((long) (decode24(bArr2, 32) << 4)) & 4294967295L) - (j * -50998291)) - (j3 * 19280294);
        long decode326 = ((((long) decode32(bArr2, 35)) & 4294967295L) - (j * 19280294)) - (j3 * 127719000);
        long decode327 = ((((long) decode32(bArr2, 42)) & 4294967295L) - (j * -6428113)) - (j3 * 5343);
        long decode246 = (((long) (decode24(bArr2, 25) << 4)) & 4294967295L) - (j4 * -50998291);
        long j6 = j5 - (j4 * 19280294);
        long j7 = decode245 - (j4 * 127719000);
        long j8 = decode326 - (j4 * -6428113);
        long decode247 = (((((long) (decode24(bArr2, 39) << 4)) & 4294967295L) - (j * 127719000)) - (j3 * -6428113)) - (j4 * 5343);
        long j9 = decode243 + (decode324 >> 28);
        long j10 = decode324 & M28L;
        long j11 = decode322 - (j9 * -50998291);
        long j12 = decode246 - (j9 * 19280294);
        long j13 = j6 - (j9 * 127719000);
        long j14 = j8 - (j9 * 5343);
        long j15 = (j7 - (j9 * -6428113)) - (j10 * 5343);
        long j16 = j2 + (decode327 >> 28);
        long decode328 = (((long) decode32(bArr2, 14)) & 4294967295L) - (j16 * -50998291);
        long j17 = (decode24 - (j10 * -50998291)) - (j16 * 19280294);
        long j18 = (j11 - (j10 * 19280294)) - (j16 * 127719000);
        long j19 = (j13 - (j10 * -6428113)) - (j16 * 5343);
        long j20 = (decode327 & M28L) + (decode247 >> 28);
        long decode248 = (((long) (decode24(bArr2, 11) << 4)) & 4294967295L) - (j20 * -50998291);
        long j21 = decode328 - (j20 * 19280294);
        long j22 = j17 - (j20 * 127719000);
        long j23 = ((j12 - (j10 * 127719000)) - (j16 * -6428113)) - (j20 * 5343);
        long j24 = (decode247 & M28L) + (j14 >> 28);
        long j25 = j14 & M28L;
        long j26 = (j18 - (j20 * -6428113)) - (j24 * 5343);
        long j27 = j25 + (j15 >> 28);
        long j28 = j15 & M28L;
        long j29 = (decode32 - (j24 * -50998291)) - (j27 * 19280294);
        long j30 = (decode248 - (j24 * 19280294)) - (j27 * 127719000);
        long j31 = (j21 - (j24 * 127719000)) - (j27 * -6428113);
        long j32 = (j22 - (j24 * -6428113)) - (j27 * 5343);
        long j33 = j19 + (j23 >> 28);
        long j34 = j23 & M28L;
        long j35 = j33 & M28L;
        long j36 = j35 >>> 27;
        long j37 = j28 + (j33 >> 28) + j36;
        long decode329 = (((long) decode32(bArr2, 0)) & 4294967295L) - (j37 * -50998291);
        long decode249 = (((((long) (decode24(bArr2, 4) << 4)) & 4294967295L) - (j27 * -50998291)) - (j37 * 19280294)) + (decode329 >> 28);
        long j38 = decode329 & M28L;
        long j39 = (j29 - (j37 * 127719000)) + (decode249 >> 28);
        long j40 = decode249 & M28L;
        long j41 = (j30 - (j37 * -6428113)) + (j39 >> 28);
        long j42 = j39 & M28L;
        long j43 = (j31 - (j37 * 5343)) + (j41 >> 28);
        long j44 = j41 & M28L;
        long j45 = j32 + (j43 >> 28);
        long j46 = j43 & M28L;
        long j47 = j26 + (j45 >> 28);
        long j48 = j45 & M28L;
        long j49 = j34 + (j47 >> 28);
        long j50 = j47 & M28L;
        long j51 = j35 + (j49 >> 28);
        long j52 = j49 & M28L;
        long j53 = j51 >> 28;
        long j54 = j51 & M28L;
        long j55 = j53 - j36;
        long j56 = j38 + (j55 & -50998291);
        long j57 = j40 + (j55 & 19280294) + (j56 >> 28);
        long j58 = j56 & M28L;
        long j59 = j42 + (j55 & 127719000) + (j57 >> 28);
        long j60 = j57 & M28L;
        long j61 = j44 + (j55 & -6428113) + (j59 >> 28);
        long j62 = j59 & M28L;
        long j63 = j46 + (j55 & 5343) + (j61 >> 28);
        long j64 = j61 & M28L;
        long j65 = j48 + (j63 >> 28);
        long j66 = j63 & M28L;
        long j67 = j50 + (j65 >> 28);
        long j68 = j65 & M28L;
        long j69 = j52 + (j67 >> 28);
        long j70 = j67 & M28L;
        long j71 = j69 & M28L;
        byte[] bArr3 = new byte[32];
        encode56(j58 | (j60 << 28), bArr3, 0);
        encode56((j64 << 28) | j62, bArr3, 7);
        encode56(j66 | (j68 << 28), bArr3, 14);
        encode56(j70 | (j71 << 28), bArr3, 21);
        encode32((int) (j54 + (j69 >> 28)), bArr3, 28);
        return bArr3;
    }

    private static void scalarMult(byte[] bArr, PointAffine pointAffine, PointAccum pointAccum) {
        precompute();
        int[] iArr = new int[8];
        decodeScalar(bArr, 0, iArr);
        Nat.shiftDownBits(8, iArr, 3, 1);
        Nat.cadd(8, (iArr[0] ^ -1) & 1, iArr, f817L, iArr);
        Nat.shiftDownBit(8, iArr, 0);
        pointCopy(pointAffine, pointAccum);
        int[] pointPrecomp = pointPrecomp(pointAffine, 8);
        PointExt pointExt = new PointExt();
        pointLookup(pointPrecomp, 7, pointExt);
        pointAdd(pointExt, pointAccum);
        int i = 62;
        while (true) {
            pointLookup(iArr, i, pointPrecomp, pointExt);
            pointAdd(pointExt, pointAccum);
            pointDouble(pointAccum);
            pointDouble(pointAccum);
            pointDouble(pointAccum);
            i--;
            if (i >= 0) {
                pointDouble(pointAccum);
            } else {
                return;
            }
        }
    }

    private static void scalarMultBase(byte[] bArr, PointAccum pointAccum) {
        precompute();
        pointSetNeutral(pointAccum);
        int[] iArr = new int[8];
        decodeScalar(bArr, 0, iArr);
        Nat.cadd(8, (iArr[0] ^ -1) & 1, iArr, f817L, iArr);
        Nat.shiftDownBit(8, iArr, 1);
        for (int i = 0; i < 8; i++) {
            iArr[i] = Interleave.shuffle2(iArr[i]);
        }
        PointPrecomp pointPrecomp = new PointPrecomp();
        int i2 = 28;
        while (true) {
            for (int i3 = 0; i3 < 8; i3++) {
                int i4 = iArr[i3] >>> i2;
                int i5 = (i4 >>> 3) & 1;
                pointLookup(i3, (i4 ^ (-i5)) & 7, pointPrecomp);
                X25519Field.cswap(i5, pointPrecomp.ypx_h, pointPrecomp.ymx_h);
                X25519Field.cnegate(i5, pointPrecomp.xyd);
                pointAddPrecomp(pointPrecomp, pointAccum);
            }
            i2 -= 4;
            if (i2 >= 0) {
                pointDouble(pointAccum);
            } else {
                return;
            }
        }
    }

    private static void scalarMultBaseEncoded(byte[] bArr, byte[] bArr2, int i) {
        PointAccum pointAccum = new PointAccum();
        scalarMultBase(bArr, pointAccum);
        if (encodePoint(pointAccum, bArr2, i) == 0) {
            throw new IllegalStateException();
        }
    }

    public static void scalarMultBaseYZ(X25519.Friend friend, byte[] bArr, int i, int[] iArr, int[] iArr2) {
        if (friend != null) {
            byte[] bArr2 = new byte[32];
            pruneScalar(bArr, i, bArr2);
            PointAccum pointAccum = new PointAccum();
            scalarMultBase(bArr2, pointAccum);
            if (checkPoint(pointAccum.f826x, pointAccum.f827y, pointAccum.f828z) != 0) {
                X25519Field.copy(pointAccum.f827y, 0, iArr, 0);
                X25519Field.copy(pointAccum.f828z, 0, iArr2, 0);
                return;
            }
            throw new IllegalStateException();
        }
        throw new NullPointerException("This method is only for use by X25519");
    }

    private static void scalarMultStrausVar(int[] iArr, int[] iArr2, PointAffine pointAffine, PointAccum pointAccum) {
        precompute();
        byte[] wnaf = getWNAF(iArr, 7);
        byte[] wnaf2 = getWNAF(iArr2, 5);
        PointExt[] pointPrecompVar = pointPrecompVar(pointCopy(pointAffine), 8);
        pointSetNeutral(pointAccum);
        int i = 252;
        while (true) {
            byte b = wnaf[i];
            boolean z = false;
            if (b != 0) {
                int i2 = b >> 31;
                pointAddVar(i2 != 0, precompBaseTable[(b ^ i2) >>> 1], pointAccum);
            }
            byte b2 = wnaf2[i];
            if (b2 != 0) {
                int i3 = b2 >> 31;
                int i4 = (b2 ^ i3) >>> 1;
                if (i3 != 0) {
                    z = true;
                }
                pointAddVar(z, pointPrecompVar[i4], pointAccum);
            }
            i--;
            if (i >= 0) {
                pointDouble(pointAccum);
            } else {
                return;
            }
        }
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, int i2, int i3, byte[] bArr3, int i4) {
        implSign(bArr, i, (byte[]) null, (byte) 0, bArr2, i2, i3, bArr3, i4);
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, int i3, int i4, byte[] bArr4, int i5) {
        implSign(bArr, i, bArr2, i2, (byte[]) null, (byte) 0, bArr3, i3, i4, bArr4, i5);
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, int i4, byte[] bArr5, int i5) {
        implSign(bArr, i, bArr2, i2, bArr3, (byte) 0, bArr4, i3, i4, bArr5, i5);
    }

    public static void sign(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2, int i3, byte[] bArr4, int i4) {
        implSign(bArr, i, bArr2, (byte) 0, bArr3, i2, i3, bArr4, i4);
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, Digest digest, byte[] bArr4, int i3) {
        byte[] bArr5 = new byte[64];
        if (64 == digest.doFinal(bArr5, 0)) {
            implSign(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr5, 0, 64, bArr4, i3);
            return;
        }
        throw new IllegalArgumentException("ph");
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, byte[] bArr5, int i4) {
        implSign(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, i3, 64, bArr5, i4);
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, Digest digest, byte[] bArr3, int i2) {
        byte[] bArr4 = new byte[64];
        if (64 == digest.doFinal(bArr4, 0)) {
            implSign(bArr, i, bArr2, (byte) 1, bArr4, 0, 64, bArr3, i2);
            return;
        }
        throw new IllegalArgumentException("ph");
    }

    public static void signPrehash(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2, byte[] bArr4, int i3) {
        implSign(bArr, i, bArr2, (byte) 1, bArr3, i2, 64, bArr4, i3);
    }

    public static boolean verify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, int i3, int i4) {
        return implVerify(bArr, i, bArr2, i2, (byte[]) null, (byte) 0, bArr3, i3, i4);
    }

    public static boolean verify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3, int i4) {
        return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 0, bArr4, i3, i4);
    }

    public static boolean verifyPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, Digest digest) {
        byte[] bArr4 = new byte[64];
        if (64 == digest.doFinal(bArr4, 0)) {
            return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, 0, 64);
        }
        throw new IllegalArgumentException("ph");
    }

    public static boolean verifyPrehash(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3) {
        return implVerify(bArr, i, bArr2, i2, bArr3, (byte) 1, bArr4, i3, 64);
    }
}

package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat192;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP192K1Field */
public class SecP192K1Field {

    /* renamed from: P */
    static final int[] f763P = {-4553, -2, -1, -1, -1, -1};

    /* renamed from: P5 */
    private static final int f764P5 = -1;
    static final int[] PExt = {20729809, 9106, 1, 0, 0, 0, -9106, -3, -1, -1, -1, -1};
    private static final int PExt11 = -1;
    private static final int[] PExtInv = {-20729809, -9107, -2, -1, -1, -1, 9105, 2};
    private static final int PInv33 = 4553;

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat192.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && Nat192.gte(iArr3, f763P))) {
            Nat.add33To(6, PInv33, iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && Nat.gte(12, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (Nat.addTo(iArr4.length, iArr4, iArr3) != 0) {
                Nat.incAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && Nat192.gte(iArr2, f763P))) {
            Nat.add33To(6, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat192.fromBigInteger(bigInteger);
        if (fromBigInteger[5] == -1) {
            int[] iArr = f763P;
            if (Nat192.gte(fromBigInteger, iArr)) {
                Nat192.subFrom(iArr, fromBigInteger);
            }
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(6, iArr, 0, iArr2);
        } else {
            Nat.shiftDownBit(6, iArr2, Nat192.add(iArr, f763P, iArr2));
        }
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = Nat192.createExt();
        Nat192.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat192.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && Nat.gte(12, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (Nat.addTo(iArr4.length, iArr4, iArr3) != 0) {
                Nat.incAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (Nat192.isZero(iArr)) {
            Nat192.zero(iArr2);
        } else {
            Nat192.sub(f763P, iArr, iArr2);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (Nat192.mul33DWordAdd(PInv33, Nat192.mul33Add(PInv33, iArr, 6, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[5] == -1 && Nat192.gte(iArr2, f763P))) {
            Nat.add33To(6, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i != 0 && Nat192.mul33WordAdd(PInv33, i, iArr, 0) != 0) || (iArr[5] == -1 && Nat192.gte(iArr, f763P))) {
            Nat.add33To(6, PInv33, iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = Nat192.createExt();
        Nat192.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] createExt = Nat192.createExt();
        Nat192.square(iArr, createExt);
        while (true) {
            reduce(createExt, iArr2);
            i--;
            if (i > 0) {
                Nat192.square(iArr2, createExt);
            } else {
                return;
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat192.sub(iArr, iArr2, iArr3) != 0) {
            Nat.sub33From(6, PInv33, iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(12, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (Nat.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                Nat.decAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (Nat.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && Nat192.gte(iArr2, f763P))) {
            Nat.add33To(6, PInv33, iArr2);
        }
    }
}

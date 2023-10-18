package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.bouncycastle.asn1.cmc.BodyPartID;

@Metadata(mo112d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, mo113d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: UnsignedUtils.kt */
public final class UnsignedKt {
    public static final int uintCompare(int v1, int v2) {
        return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, Integer.MIN_VALUE ^ v2);
    }

    public static final int ulongCompare(long v1, long v2) {
        return Intrinsics.compare(v1 ^ Long.MIN_VALUE, Long.MIN_VALUE ^ v2);
    }

    /* renamed from: uintDivide-J1ME1BU  reason: not valid java name */
    public static final int m1692uintDivideJ1ME1BU(int v1, int v2) {
        return UInt.m1439constructorimpl((int) ((((long) v1) & BodyPartID.bodyIdMax) / (BodyPartID.bodyIdMax & ((long) v2))));
    }

    /* renamed from: uintRemainder-J1ME1BU  reason: not valid java name */
    public static final int m1693uintRemainderJ1ME1BU(int v1, int v2) {
        return UInt.m1439constructorimpl((int) ((((long) v1) & BodyPartID.bodyIdMax) % (BodyPartID.bodyIdMax & ((long) v2))));
    }

    /* renamed from: ulongDivide-eb3DHEI  reason: not valid java name */
    public static final long m1694ulongDivideeb3DHEI(long v1, long v2) {
        long dividend = v1;
        long divisor = v2;
        long j = 0;
        if (divisor < 0) {
            if (ulongCompare(v1, v2) >= 0) {
                j = 1;
            }
            return ULong.m1517constructorimpl(j);
        } else if (dividend >= 0) {
            return ULong.m1517constructorimpl(dividend / divisor);
        } else {
            int i = 1;
            long quotient = ((dividend >>> 1) / divisor) << 1;
            if (ulongCompare(ULong.m1517constructorimpl(dividend - (quotient * divisor)), ULong.m1517constructorimpl(divisor)) < 0) {
                i = 0;
            }
            return ULong.m1517constructorimpl(((long) i) + quotient);
        }
    }

    /* renamed from: ulongRemainder-eb3DHEI  reason: not valid java name */
    public static final long m1695ulongRemaindereb3DHEI(long v1, long v2) {
        long dividend = v1;
        long divisor = v2;
        long j = 0;
        if (divisor < 0) {
            if (ulongCompare(v1, v2) < 0) {
                return v1;
            }
            return ULong.m1517constructorimpl(v1 - v2);
        } else if (dividend >= 0) {
            return ULong.m1517constructorimpl(dividend % divisor);
        } else {
            long rem = dividend - ((((dividend >>> 1) / divisor) << 1) * divisor);
            if (ulongCompare(ULong.m1517constructorimpl(rem), ULong.m1517constructorimpl(divisor)) >= 0) {
                j = divisor;
            }
            return ULong.m1517constructorimpl(rem - j);
        }
    }

    public static final int doubleToUInt(double v) {
        if (Double.isNaN(v) || v <= uintToDouble(0)) {
            return 0;
        }
        if (v >= uintToDouble(-1)) {
            return -1;
        }
        if (v <= 2.147483647E9d) {
            return UInt.m1439constructorimpl((int) v);
        }
        double d = (double) Integer.MAX_VALUE;
        Double.isNaN(d);
        return UInt.m1439constructorimpl(UInt.m1439constructorimpl((int) (v - d)) + UInt.m1439constructorimpl(Integer.MAX_VALUE));
    }

    public static final long doubleToULong(double v) {
        if (Double.isNaN(v) || v <= ulongToDouble(0)) {
            return 0;
        }
        if (v >= ulongToDouble(-1)) {
            return -1;
        }
        if (v < 9.223372036854776E18d) {
            return ULong.m1517constructorimpl((long) v);
        }
        return ULong.m1517constructorimpl(ULong.m1517constructorimpl((long) (v - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    public static final double uintToDouble(int v) {
        double d = (double) (Integer.MAX_VALUE & v);
        double d2 = (double) ((v >>> 31) << 30);
        double d3 = (double) 2;
        Double.isNaN(d2);
        Double.isNaN(d3);
        Double.isNaN(d);
        return d + (d2 * d3);
    }

    public static final double ulongToDouble(long v) {
        double d = (double) (v >>> 11);
        double d2 = (double) 2048;
        Double.isNaN(d);
        Double.isNaN(d2);
        double d3 = d * d2;
        double d4 = (double) (2047 & v);
        Double.isNaN(d4);
        return d3 + d4;
    }

    public static final String ulongToString(long v) {
        return ulongToString(v, 10);
    }

    public static final String ulongToString(long v, int base) {
        if (v >= 0) {
            String l = Long.toString(v, CharsKt.checkRadix(base));
            Intrinsics.checkNotNullExpressionValue(l, "toString(this, checkRadix(radix))");
            return l;
        }
        long quotient = ((v >>> 1) / ((long) base)) << 1;
        long rem = v - (((long) base) * quotient);
        if (rem >= ((long) base)) {
            rem -= (long) base;
            quotient++;
        }
        StringBuilder sb = new StringBuilder();
        String l2 = Long.toString(quotient, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(l2, "toString(this, checkRadix(radix))");
        StringBuilder append = sb.append(l2);
        String l3 = Long.toString(rem, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(l3, "toString(this, checkRadix(radix))");
        return append.append(l3).toString();
    }
}

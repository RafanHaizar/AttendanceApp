package org.bouncycastle.pqc.math.linearalgebra;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.PointerIconCompat;
import com.itextpdf.kernel.pdf.canvas.wmf.MetaDo;
import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.Primes;
import org.bouncycastle.util.BigIntegers;

public final class IntegerFunctions {
    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final int[] SMALL_PRIMES = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
    private static final long SMALL_PRIME_PRODUCT = 152125131763605L;
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final int[] jacobiTable = {0, 1, 0, -1, 0, -1, 0, 1};

    /* renamed from: sr */
    private static SecureRandom f963sr = null;

    private IntegerFunctions() {
    }

    public static BigInteger binomial(int i, int i2) {
        BigInteger bigInteger = ONE;
        if (i == 0) {
            return i2 == 0 ? bigInteger : ZERO;
        }
        if (i2 > (i >>> 1)) {
            i2 = i - i2;
        }
        for (int i3 = 1; i3 <= i2; i3++) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf((long) (i - (i3 - 1)))).divide(BigInteger.valueOf((long) i3));
        }
        return bigInteger;
    }

    public static int bitCount(int i) {
        int i2 = 0;
        while (i != 0) {
            i2 += i & 1;
            i >>>= 1;
        }
        return i2;
    }

    public static int ceilLog(int i) {
        int i2 = 0;
        int i3 = 1;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    public static int ceilLog(BigInteger bigInteger) {
        int i = 0;
        for (BigInteger bigInteger2 = ONE; bigInteger2.compareTo(bigInteger) < 0; bigInteger2 = bigInteger2.shiftLeft(1)) {
            i++;
        }
        return i;
    }

    public static int ceilLog256(int i) {
        if (i == 0) {
            return 1;
        }
        if (i < 0) {
            i = -i;
        }
        int i2 = 0;
        while (i > 0) {
            i2++;
            i >>>= 8;
        }
        return i2;
    }

    public static int ceilLog256(long j) {
        if (j == 0) {
            return 1;
        }
        if (j < 0) {
            j = -j;
        }
        int i = 0;
        while (j > 0) {
            i++;
            j >>>= 8;
        }
        return i;
    }

    public static BigInteger divideAndRound(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger.signum() < 0 ? divideAndRound(bigInteger.negate(), bigInteger2).negate() : bigInteger2.signum() < 0 ? divideAndRound(bigInteger, bigInteger2.negate()).negate() : bigInteger.shiftLeft(1).add(bigInteger2).divide(bigInteger2.shiftLeft(1));
    }

    public static BigInteger[] divideAndRound(BigInteger[] bigIntegerArr, BigInteger bigInteger) {
        BigInteger[] bigIntegerArr2 = new BigInteger[bigIntegerArr.length];
        for (int i = 0; i < bigIntegerArr.length; i++) {
            bigIntegerArr2[i] = divideAndRound(bigIntegerArr[i], bigInteger);
        }
        return bigIntegerArr2;
    }

    public static int[] extGCD(int i, int i2) {
        BigInteger[] extgcd = extgcd(BigInteger.valueOf((long) i), BigInteger.valueOf((long) i2));
        return new int[]{extgcd[0].intValue(), extgcd[1].intValue(), extgcd[2].intValue()};
    }

    public static BigInteger[] extgcd(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigInteger3 = ONE;
        BigInteger bigInteger4 = ZERO;
        if (bigInteger2.signum() != 0) {
            BigInteger bigInteger5 = bigInteger;
            BigInteger bigInteger6 = bigInteger2;
            while (bigInteger6.signum() != 0) {
                BigInteger[] divideAndRemainder = bigInteger5.divideAndRemainder(bigInteger6);
                BigInteger bigInteger7 = divideAndRemainder[0];
                BigInteger bigInteger8 = divideAndRemainder[1];
                BigInteger bigInteger9 = bigInteger4;
                bigInteger4 = bigInteger3.subtract(bigInteger7.multiply(bigInteger4));
                bigInteger3 = bigInteger9;
                BigInteger bigInteger10 = bigInteger8;
                bigInteger5 = bigInteger6;
                bigInteger6 = bigInteger10;
            }
            bigInteger4 = bigInteger5.subtract(bigInteger.multiply(bigInteger3)).divide(bigInteger2);
            bigInteger = bigInteger5;
        }
        return new BigInteger[]{bigInteger, bigInteger3, bigInteger4};
    }

    public static float floatPow(float f, int i) {
        float f2 = 1.0f;
        while (i > 0) {
            f2 *= f;
            i--;
        }
        return f2;
    }

    public static int floorLog(int i) {
        if (i <= 0) {
            return -1;
        }
        int i2 = 0;
        for (int i3 = i >>> 1; i3 > 0; i3 >>>= 1) {
            i2++;
        }
        return i2;
    }

    public static int floorLog(BigInteger bigInteger) {
        int i = -1;
        for (BigInteger bigInteger2 = ONE; bigInteger2.compareTo(bigInteger) <= 0; bigInteger2 = bigInteger2.shiftLeft(1)) {
            i++;
        }
        return i;
    }

    public static int gcd(int i, int i2) {
        return BigInteger.valueOf((long) i).gcd(BigInteger.valueOf((long) i2)).intValue();
    }

    public static float intRoot(int i, int i2) {
        float floatPow;
        float f = (float) (i / i2);
        float f2 = 0.0f;
        while (((double) Math.abs(f2 - f)) > 1.0E-4d) {
            while (true) {
                floatPow = floatPow(f, i2);
                if (!Float.isInfinite(floatPow)) {
                    break;
                }
                f = (f + f2) / 2.0f;
            }
            f2 = f;
            f -= (floatPow - ((float) i)) / (((float) i2) * floatPow(f, i2 - 1));
        }
        return f;
    }

    public static byte[] integerToOctets(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.abs().toByteArray();
        if ((bigInteger.bitLength() & 7) != 0) {
            return byteArray;
        }
        int bitLength = bigInteger.bitLength() >> 3;
        byte[] bArr = new byte[bitLength];
        System.arraycopy(byteArray, 1, bArr, 0, bitLength);
        return bArr;
    }

    public static boolean isIncreasing(int[] iArr) {
        for (int i = 1; i < iArr.length; i++) {
            int i2 = i - 1;
            if (iArr[i2] >= iArr[i]) {
                System.out.println("a[" + i2 + "] = " + iArr[i2] + " >= " + iArr[i] + " = a[" + i + "]");
                return false;
            }
        }
        return true;
    }

    public static int isPower(int i, int i2) {
        if (i <= 0) {
            return -1;
        }
        int i3 = 0;
        while (i > 1) {
            if (i % i2 != 0) {
                return -1;
            }
            i /= i2;
            i3++;
        }
        return i3;
    }

    public static boolean isPrime(int i) {
        if (i < 2) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        if ((i & 1) == 0) {
            return false;
        }
        if (i < 42) {
            int i2 = 0;
            while (true) {
                int[] iArr = SMALL_PRIMES;
                if (i2 >= iArr.length) {
                    break;
                } else if (i == iArr[i2]) {
                    return true;
                } else {
                    i2++;
                }
            }
        }
        if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0 || i % 11 == 0 || i % 13 == 0 || i % 17 == 0 || i % 19 == 0 || i % 23 == 0 || i % 29 == 0 || i % 31 == 0 || i % 37 == 0 || i % 41 == 0) {
            return false;
        }
        return BigInteger.valueOf((long) i).isProbablePrime(20);
    }

    public static int jacobi(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigInteger3 = ZERO;
        if (bigInteger2.equals(bigInteger3)) {
            return bigInteger.abs().equals(ONE) ? 1 : 0;
        }
        if (!bigInteger.testBit(0) && !bigInteger2.testBit(0)) {
            return 0;
        }
        long j = 1;
        if (bigInteger2.signum() == -1) {
            bigInteger2 = bigInteger2.negate();
            if (bigInteger.signum() == -1) {
                j = -1;
            }
        }
        while (!bigInteger2.testBit(0)) {
            bigInteger3 = bigInteger3.add(ONE);
            bigInteger2 = bigInteger2.divide(TWO);
        }
        if (bigInteger3.testBit(0)) {
            j *= (long) jacobiTable[bigInteger.intValue() & 7];
        }
        if (bigInteger.signum() < 0) {
            if (bigInteger2.testBit(1)) {
                j = -j;
            }
            bigInteger = bigInteger.negate();
        }
        while (bigInteger.signum() != 0) {
            BigInteger bigInteger4 = ZERO;
            while (!bigInteger.testBit(0)) {
                bigInteger4 = bigInteger4.add(ONE);
                bigInteger = bigInteger.divide(TWO);
            }
            if (bigInteger4.testBit(0)) {
                j *= (long) jacobiTable[bigInteger2.intValue() & 7];
            }
            if (bigInteger.compareTo(bigInteger2) >= 0) {
                BigInteger bigInteger5 = bigInteger2;
                bigInteger2 = bigInteger;
                bigInteger = bigInteger5;
            } else if (bigInteger2.testBit(1) && bigInteger.testBit(1)) {
                j = -j;
            }
            BigInteger subtract = bigInteger2.subtract(bigInteger);
            bigInteger2 = bigInteger;
            bigInteger = subtract;
        }
        if (bigInteger2.equals(ONE)) {
            return (int) j;
        }
        return 0;
    }

    public static BigInteger leastCommonMultiple(BigInteger[] bigIntegerArr) {
        int length = bigIntegerArr.length;
        BigInteger bigInteger = bigIntegerArr[0];
        for (int i = 1; i < length; i++) {
            bigInteger = bigInteger.multiply(bigIntegerArr[i]).divide(bigInteger.gcd(bigIntegerArr[i]));
        }
        return bigInteger;
    }

    public static int leastDiv(int i) {
        if (i < 0) {
            i = -i;
        }
        if (i == 0) {
            return 1;
        }
        if ((i & 1) == 0) {
            return 2;
        }
        for (int i2 = 3; i2 <= i / i2; i2 += 2) {
            if (i % i2 == 0) {
                return i2;
            }
        }
        return i;
    }

    public static double log(double d) {
        double d2 = 1.0d;
        if (d > 0.0d && d < 1.0d) {
            return -log(1.0d / d);
        }
        int i = 0;
        double d3 = d;
        while (d3 > 2.0d) {
            d3 /= 2.0d;
            i++;
            d2 *= 2.0d;
        }
        double logBKM = logBKM(d / d2);
        double d4 = (double) i;
        Double.isNaN(d4);
        return d4 + logBKM;
    }

    public static double log(long j) {
        int floorLog = floorLog(BigInteger.valueOf(j));
        double d = (double) j;
        double d2 = (double) ((long) (1 << floorLog));
        Double.isNaN(d);
        Double.isNaN(d2);
        double logBKM = logBKM(d / d2);
        double d3 = (double) floorLog;
        Double.isNaN(d3);
        return d3 + logBKM;
    }

    private static double logBKM(double d) {
        double[] dArr = {1.0d, 0.5849625007211562d, 0.32192809488736235d, 0.16992500144231237d, 0.0874628412503394d, 0.044394119358453436d, 0.02236781302845451d, 0.01122725542325412d, 0.005624549193878107d, 0.0028150156070540383d, 0.0014081943928083889d, 7.042690112466433E-4d, 3.5217748030102726E-4d, 1.7609948644250602E-4d, 8.80524301221769E-5d, 4.4026886827316716E-5d, 2.2013611360340496E-5d, 1.1006847667481442E-5d, 5.503434330648604E-6d, 2.751719789561283E-6d, 1.375860550841138E-6d, 6.879304394358497E-7d, 3.4396526072176454E-7d, 1.7198264061184464E-7d, 8.599132286866321E-8d, 4.299566207501687E-8d, 2.1497831197679756E-8d, 1.0748915638882709E-8d, 5.374457829452062E-9d, 2.687228917228708E-9d, 1.3436144592400231E-9d, 6.718072297764289E-10d, 3.3590361492731876E-10d, 1.6795180747343547E-10d, 8.397590373916176E-11d, 4.1987951870191886E-11d, 2.0993975935248694E-11d, 1.0496987967662534E-11d, 5.2484939838408146E-12d, 2.624246991922794E-12d, 1.3121234959619935E-12d, 6.56061747981146E-13d, 3.2803087399061026E-13d, 1.6401543699531447E-13d, 8.200771849765956E-14d, 4.1003859248830365E-14d, 2.0501929624415328E-14d, 1.02509648122077E-14d, 5.1254824061038595E-15d, 2.5627412030519317E-15d, 1.2813706015259665E-15d, 6.406853007629834E-16d, 3.203426503814917E-16d, 1.6017132519074588E-16d, 8.008566259537294E-17d, 4.004283129768647E-17d, 2.0021415648843235E-17d, 1.0010707824421618E-17d, 5.005353912210809E-18d, 2.5026769561054044E-18d, 1.2513384780527022E-18d, 6.256692390263511E-19d, 3.1283461951317555E-19d, 1.5641730975658778E-19d, 7.820865487829389E-20d, 3.9104327439146944E-20d, 1.9552163719573472E-20d, 9.776081859786736E-21d, 4.888040929893368E-21d, 2.444020464946684E-21d, 1.222010232473342E-21d, 6.11005116236671E-22d, 3.055025581183355E-22d, 1.5275127905916775E-22d, 7.637563952958387E-23d, 3.818781976479194E-23d, 1.909390988239597E-23d, 9.546954941197984E-24d, 4.773477470598992E-24d, 2.386738735299496E-24d, 1.193369367649748E-24d, 5.96684683824874E-25d, 2.98342341912437E-25d, 1.491711709562185E-25d, 7.458558547810925E-26d, 3.7292792739054626E-26d, 1.8646396369527313E-26d, 9.323198184763657E-27d, 4.661599092381828E-27d, 2.330799546190914E-27d, 1.165399773095457E-27d, 5.826998865477285E-28d, 2.9134994327386427E-28d, 1.4567497163693213E-28d, 7.283748581846607E-29d, 3.6418742909233034E-29d, 1.8209371454616517E-29d, 9.104685727308258E-30d, 4.552342863654129E-30d, 2.2761714318270646E-30d};
        double d2 = 1.0d;
        double d3 = 0.0d;
        double d4 = 1.0d;
        for (int i = 0; i < 53; i++) {
            double d5 = (d2 * d4) + d2;
            if (d5 <= d) {
                d3 += dArr[i];
                d2 = d5;
            }
            d4 *= 0.5d;
        }
        return d3;
    }

    public static int maxPower(int i) {
        int i2 = 0;
        if (i != 0) {
            for (int i3 = 1; (i & i3) == 0; i3 <<= 1) {
                i2++;
            }
        }
        return i2;
    }

    public static long mod(long j, long j2) {
        long j3 = j % j2;
        return j3 < 0 ? j3 + j2 : j3;
    }

    public static int modInverse(int i, int i2) {
        return BigInteger.valueOf((long) i).modInverse(BigInteger.valueOf((long) i2)).intValue();
    }

    public static long modInverse(long j, long j2) {
        return BigInteger.valueOf(j).modInverse(BigInteger.valueOf(j2)).longValue();
    }

    public static int modPow(int i, int i2, int i3) {
        if (i3 <= 0 || i3 * i3 > Integer.MAX_VALUE || i2 < 0) {
            return 0;
        }
        int i4 = ((i % i3) + i3) % i3;
        int i5 = 1;
        while (i2 > 0) {
            if ((i2 & 1) == 1) {
                i5 = (i5 * i4) % i3;
            }
            i4 = (i4 * i4) % i3;
            i2 >>>= 1;
        }
        return i5;
    }

    public static BigInteger nextPrime(long j) {
        if (j <= 1) {
            return BigInteger.valueOf(2);
        }
        if (j == 2) {
            return BigInteger.valueOf(3);
        }
        boolean z = false;
        long j2 = 0;
        for (long j3 = j + 1 + (j & 1); j3 <= (j << 1) && !z; j3 += 2) {
            for (long j4 = 3; j4 <= (j3 >> 1) && !z; j4 += 2) {
                if (j3 % j4 == 0) {
                    z = true;
                }
            }
            if (!z) {
                j2 = j3;
            }
            z = !z;
        }
        return BigInteger.valueOf(j2);
    }

    public static BigInteger nextProbablePrime(BigInteger bigInteger) {
        return nextProbablePrime(bigInteger, 20);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        if (r7.bitLength() <= 6) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        r0 = r7.remainder(java.math.BigInteger.valueOf(SMALL_PRIME_PRODUCT)).longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0045, code lost:
        if ((r0 % 3) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004d, code lost:
        if ((r0 % 5) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
        if ((r0 % 7) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005d, code lost:
        if ((r0 % 11) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0065, code lost:
        if ((r0 % 13) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006d, code lost:
        if ((r0 % 17) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0075, code lost:
        if ((r0 % 19) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        if ((r0 % 23) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0085, code lost:
        if ((r0 % 29) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        if ((r0 % 31) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        if ((r0 % 37) == 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009c, code lost:
        if ((r0 % 41) != 0) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009e, code lost:
        r0 = TWO;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a6, code lost:
        if (r7.bitLength() >= 4) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a8, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ad, code lost:
        if (r7.isProbablePrime(r8) == false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00af, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001f, code lost:
        if (r7.testBit(0) == false) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        r7 = r7.add(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.math.BigInteger nextProbablePrime(java.math.BigInteger r7, int r8) {
        /*
            int r0 = r7.signum()
            if (r0 < 0) goto L_0x00b0
            int r0 = r7.signum()
            if (r0 == 0) goto L_0x00b0
            java.math.BigInteger r0 = ONE
            boolean r1 = r7.equals(r0)
            if (r1 == 0) goto L_0x0016
            goto L_0x00b0
        L_0x0016:
            java.math.BigInteger r7 = r7.add(r0)
            r1 = 0
            boolean r1 = r7.testBit(r1)
            if (r1 != 0) goto L_0x0025
        L_0x0021:
            java.math.BigInteger r7 = r7.add(r0)
        L_0x0025:
            int r0 = r7.bitLength()
            r1 = 6
            if (r0 <= r1) goto L_0x00a1
            r0 = 152125131763605(0x8a5b6470af95, double:7.515980147347E-310)
            java.math.BigInteger r0 = java.math.BigInteger.valueOf(r0)
            java.math.BigInteger r0 = r7.remainder(r0)
            long r0 = r0.longValue()
            r2 = 3
            long r2 = r0 % r2
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 5
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 7
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 11
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 13
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 17
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 19
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 23
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 29
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 31
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 37
            long r2 = r0 % r2
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x009e
            r2 = 41
            long r0 = r0 % r2
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x00a1
        L_0x009e:
            java.math.BigInteger r0 = TWO
            goto L_0x0021
        L_0x00a1:
            int r0 = r7.bitLength()
            r1 = 4
            if (r0 >= r1) goto L_0x00a9
            return r7
        L_0x00a9:
            boolean r0 = r7.isProbablePrime(r8)
            if (r0 == 0) goto L_0x009e
            return r7
        L_0x00b0:
            java.math.BigInteger r7 = TWO
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pqc.math.linearalgebra.IntegerFunctions.nextProbablePrime(java.math.BigInteger, int):java.math.BigInteger");
    }

    public static int nextSmallerPrime(int i) {
        if (i <= 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if ((i & 1) == 0) {
            i--;
            if (i <= 3 || isPrime(i)) {
                return i;
            }
        }
        i -= 2;
        return i;
    }

    public static BigInteger octetsToInteger(byte[] bArr) {
        return octetsToInteger(bArr, 0, bArr.length);
    }

    public static BigInteger octetsToInteger(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(i2 + 1)];
        bArr2[0] = 0;
        System.arraycopy(bArr, i, bArr2, 1, i2);
        return new BigInteger(bArr2);
    }

    public static int order(int i, int i2) {
        int i3 = i % i2;
        if (i3 != 0) {
            int i4 = 1;
            while (i3 != 1) {
                i3 = (i3 * i) % i2;
                if (i3 < 0) {
                    i3 += i2;
                }
                i4++;
            }
            return i4;
        }
        throw new IllegalArgumentException(i + " is not an element of Z/(" + i2 + "Z)^*; it is not meaningful to compute its order.");
    }

    public static boolean passesSmallPrimeTest(BigInteger bigInteger) {
        int[] iArr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 199, Primes.SMALL_FACTOR_LIMIT, 223, 227, 229, 233, 239, 241, 251, 257, 263, TIFFConstants.TIFFTAG_DOCUMENTNAME, TIFFConstants.TIFFTAG_MAKE, TIFFConstants.TIFFTAG_SAMPLESPERPIXEL, TIFFConstants.TIFFTAG_MAXSAMPLEVALUE, TIFFConstants.TIFFTAG_YRESOLUTION, TIFFConstants.TIFFTAG_GROUP4OPTIONS, 307, 311, 313, 317, 331, TIFFConstants.TIFFTAG_TARGETPRINTER, TIFFConstants.TIFFTAG_JPEGTABLES, 349, 353, 359, 367, 373, 379, 383, 389, 397, TypedValues.CycleType.TYPE_CURVE_FIT, 409, 419, TypedValues.CycleType.TYPE_WAVE_SHAPE, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, TypedValues.PositionType.TYPE_PERCENT_WIDTH, 509, 521, MetaDo.META_SETWINDOWORG, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, TypedValues.MotionType.TYPE_PATHMOTION_ARC, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, TypedValues.TransitionType.TYPE_FROM, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, PointerIconCompat.TYPE_VERTICAL_TEXT, PointerIconCompat.TYPE_ALL_SCROLL, PointerIconCompat.TYPE_ZOOM_OUT, 1021, 1031, 1033, 1039, MetaDo.META_FLOODFILL, MetaDo.META_RECTANGLE, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499};
        for (int i = 0; i < 239; i++) {
            if (bigInteger.mod(BigInteger.valueOf((long) iArr[i])).equals(ZERO)) {
                return false;
            }
        }
        return true;
    }

    public static int pow(int i, int i2) {
        int i3 = 1;
        while (i2 > 0) {
            if ((i2 & 1) == 1) {
                i3 *= i;
            }
            i *= i;
            i2 >>>= 1;
        }
        return i3;
    }

    public static long pow(long j, int i) {
        long j2 = 1;
        while (i > 0) {
            if ((i & 1) == 1) {
                j2 *= j;
            }
            j *= j;
            i >>>= 1;
        }
        return j2;
    }

    public static BigInteger randomize(BigInteger bigInteger) {
        if (f963sr == null) {
            f963sr = CryptoServicesRegistrar.getSecureRandom();
        }
        return randomize(bigInteger, f963sr);
    }

    public static BigInteger randomize(BigInteger bigInteger, SecureRandom secureRandom) {
        int bitLength = bigInteger.bitLength();
        BigInteger valueOf = BigInteger.valueOf(0);
        if (secureRandom == null && (secureRandom = f963sr) == null) {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        for (int i = 0; i < 20; i++) {
            valueOf = BigIntegers.createRandomBigInteger(bitLength, secureRandom);
            if (valueOf.compareTo(bigInteger) < 0) {
                return valueOf;
            }
        }
        return valueOf.mod(bigInteger);
    }

    public static BigInteger reduceInto(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return bigInteger.subtract(bigInteger2).mod(bigInteger3.subtract(bigInteger2)).add(bigInteger2);
    }

    public static BigInteger ressol(BigInteger bigInteger, BigInteger bigInteger2) throws IllegalArgumentException {
        BigInteger bigInteger3;
        BigInteger bigInteger4 = bigInteger2;
        BigInteger bigInteger5 = ZERO;
        BigInteger bigInteger6 = bigInteger;
        if (bigInteger6.compareTo(bigInteger5) < 0) {
            bigInteger6 = bigInteger.add(bigInteger2);
        }
        if (bigInteger6.equals(bigInteger5)) {
            return bigInteger5;
        }
        if (bigInteger4.equals(TWO)) {
            return bigInteger6;
        }
        int i = 1;
        if (!bigInteger4.testBit(0) || !bigInteger4.testBit(1)) {
            BigInteger subtract = bigInteger4.subtract(ONE);
            long j = 0;
            while (!subtract.testBit(0)) {
                j++;
                subtract = subtract.shiftRight(1);
            }
            BigInteger bigInteger7 = ONE;
            BigInteger shiftRight = subtract.subtract(bigInteger7).shiftRight(1);
            BigInteger modPow = bigInteger6.modPow(shiftRight, bigInteger4);
            BigInteger remainder = modPow.multiply(modPow).remainder(bigInteger4).multiply(bigInteger6).remainder(bigInteger4);
            BigInteger remainder2 = modPow.multiply(bigInteger6).remainder(bigInteger4);
            if (remainder.equals(bigInteger7)) {
                return remainder2;
            }
            BigInteger bigInteger8 = TWO;
            while (jacobi(bigInteger8, bigInteger4) == 1) {
                bigInteger8 = bigInteger8.add(ONE);
            }
            BigInteger modPow2 = bigInteger8.modPow(shiftRight.multiply(TWO).add(ONE), bigInteger4);
            while (remainder.compareTo(ONE) == i) {
                long j2 = 0;
                BigInteger bigInteger9 = remainder;
                while (true) {
                    bigInteger3 = ONE;
                    if (bigInteger9.equals(bigInteger3)) {
                        break;
                    }
                    bigInteger9 = bigInteger9.multiply(bigInteger9).mod(bigInteger4);
                    j2++;
                }
                long j3 = j - j2;
                if (j3 != 0) {
                    for (long j4 = 0; j4 < j3 - 1; j4++) {
                        bigInteger3 = bigInteger3.shiftLeft(1);
                    }
                    BigInteger modPow3 = modPow2.modPow(bigInteger3, bigInteger4);
                    remainder2 = remainder2.multiply(modPow3).remainder(bigInteger4);
                    modPow2 = modPow3.multiply(modPow3).remainder(bigInteger4);
                    remainder = remainder.multiply(modPow2).mod(bigInteger4);
                    j = j2;
                    i = 1;
                } else {
                    throw new IllegalArgumentException("No quadratic residue: " + bigInteger6 + ", " + bigInteger4);
                }
            }
            return remainder2;
        } else if (jacobi(bigInteger6, bigInteger4) == 1) {
            return bigInteger6.modPow(bigInteger4.add(ONE).shiftRight(2), bigInteger4);
        } else {
            throw new IllegalArgumentException("No quadratic residue: " + bigInteger6 + ", " + bigInteger4);
        }
    }

    public static BigInteger squareRoot(BigInteger bigInteger) {
        int i;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = ZERO;
        if (bigInteger.compareTo(bigInteger4) >= 0) {
            int bitLength = bigInteger.bitLength();
            if ((bitLength & 1) != 0) {
                int i2 = bitLength - 1;
                bigInteger2 = bigInteger4;
                bigInteger4 = bigInteger4.add(ONE);
                i = i2;
            } else {
                i = bitLength;
                bigInteger2 = bigInteger4;
            }
            while (i > 0) {
                BigInteger bigInteger5 = FOUR;
                BigInteger multiply = bigInteger2.multiply(bigInteger5);
                int i3 = i - 1;
                int i4 = bigInteger.testBit(i3) ? 2 : 0;
                i = i3 - 1;
                BigInteger add = multiply.add(BigInteger.valueOf((long) (i4 + (bigInteger.testBit(i) ? 1 : 0))));
                BigInteger multiply2 = bigInteger3.multiply(bigInteger5);
                BigInteger bigInteger6 = ONE;
                BigInteger add2 = multiply2.add(bigInteger6);
                bigInteger3 = bigInteger3.multiply(TWO);
                if (add.compareTo(add2) != -1) {
                    bigInteger3 = bigInteger3.add(bigInteger6);
                    add = add.subtract(add2);
                }
            }
            return bigInteger3;
        }
        throw new ArithmeticException("cannot extract root of negative number" + bigInteger + ".");
    }
}

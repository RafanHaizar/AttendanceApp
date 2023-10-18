package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410ValidationParameters;
import org.bouncycastle.util.BigIntegers;

public class GOST3410ParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private SecureRandom init_random;
    private int size;
    private int typeproc;

    private int procedure_A(int i, int i2, BigInteger[] bigIntegerArr, int i3) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        int i4;
        BigInteger bigInteger3;
        int i5;
        int i6 = i;
        while (true) {
            if (i6 >= 0 && i6 <= 65536) {
                break;
            }
            i6 = this.init_random.nextInt() / 32768;
        }
        int i7 = i2;
        while (true) {
            if (i7 >= 0 && i7 <= 65536 && i7 / 2 != 0) {
                break;
            }
            i7 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Integer.toString(i7));
        BigInteger bigInteger5 = new BigInteger("19381");
        BigInteger bigInteger6 = new BigInteger(Integer.toString(i6));
        int i8 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i3};
        int i9 = 0;
        int i10 = 0;
        while (iArr[i9] >= 17) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i10 = i9 + 1;
            iArr[i10] = iArr[i9] / 2;
            i9 = i10;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[(i10 + 1)];
        int i11 = 16;
        bigIntegerArr4[i10] = new BigInteger("8003", 16);
        int i12 = i10 - 1;
        int i13 = 0;
        while (true) {
            if (i13 >= i10) {
                bigInteger = bigIntegerArr3[0];
                break;
            }
            int i14 = iArr[i12] / i11;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i8, bigIntegerArr5, i8, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[(i14 + 1)];
                System.arraycopy(bigIntegerArr5, i8, bigIntegerArr2, i8, length2);
                int i15 = 0;
                while (i15 < i14) {
                    int i16 = i15 + 1;
                    bigIntegerArr2[i16] = bigIntegerArr2[i15].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i11));
                    i15 = i16;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i17 = 0; i17 < i14; i17++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i17].multiply(TWO.pow(i17 * 16)));
                }
                bigIntegerArr2[i8] = bigIntegerArr2[i14];
                BigInteger bigInteger8 = TWO;
                int i18 = i12 + 1;
                BigInteger add = bigInteger8.pow(iArr[i12] - 1).divide(bigIntegerArr4[i18]).add(bigInteger8.pow(iArr[i12] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i18].multiply(bigInteger8.pow(i14 * 16))));
                BigInteger mod = add.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (mod.compareTo(bigInteger9) == 0) {
                    add = add.add(bigInteger9);
                }
                int i19 = 0;
                while (true) {
                    BigInteger bigInteger10 = bigInteger4;
                    bigInteger2 = bigInteger5;
                    long j = (long) i19;
                    i4 = i10;
                    BigInteger multiply = bigIntegerArr4[i18].multiply(add.add(BigInteger.valueOf(j)));
                    BigInteger bigInteger11 = ONE;
                    BigInteger add2 = multiply.add(bigInteger11);
                    bigIntegerArr4[i12] = add2;
                    bigInteger3 = bigInteger10;
                    BigInteger bigInteger12 = TWO;
                    i5 = i14;
                    if (add2.compareTo(bigInteger12.pow(iArr[i12])) != 1) {
                        if (bigInteger12.modPow(bigIntegerArr4[i18].multiply(add.add(BigInteger.valueOf(j))), bigIntegerArr4[i12]).compareTo(bigInteger11) == 0 && bigInteger12.modPow(add.add(BigInteger.valueOf(j)), bigIntegerArr4[i12]).compareTo(bigInteger11) != 0) {
                            break;
                        }
                        i19 += 2;
                        i10 = i4;
                        bigInteger5 = bigInteger2;
                        bigInteger4 = bigInteger3;
                        i14 = i5;
                    } else {
                        break;
                    }
                }
                i10 = i4;
                bigInteger5 = bigInteger2;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger4 = bigInteger3;
                i14 = i5;
                i8 = 0;
                i11 = 16;
            }
            i12--;
            if (i12 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i13++;
            i10 = i4;
            bigInteger5 = bigInteger2;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger4 = bigInteger3;
            i8 = 0;
            i11 = 16;
        }
        return bigInteger.intValue();
    }

    private long procedure_Aa(long j, long j2, BigInteger[] bigIntegerArr, int i) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int i2;
        long j3 = j;
        while (true) {
            if (j3 >= 0 && j3 <= 4294967296L) {
                break;
            }
            j3 = (long) (this.init_random.nextInt() * 2);
        }
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L && j4 / 2 != 0) {
                break;
            }
            j4 = (long) ((this.init_random.nextInt() * 2) + 1);
        }
        BigInteger bigInteger4 = new BigInteger(Long.toString(j4));
        BigInteger bigInteger5 = new BigInteger("97781173");
        BigInteger bigInteger6 = new BigInteger(Long.toString(j3));
        int i3 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i};
        int i4 = 0;
        int i5 = 0;
        while (iArr[i4] >= 33) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i5 = i4 + 1;
            iArr[i5] = iArr[i4] / 2;
            i4 = i5;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[(i5 + 1)];
        bigIntegerArr4[i5] = new BigInteger("8000000B", 16);
        int i6 = i5 - 1;
        int i7 = 0;
        while (true) {
            if (i7 >= i5) {
                bigInteger = bigIntegerArr3[0];
                break;
            }
            int i8 = 32;
            int i9 = iArr[i6] / 32;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i3, bigIntegerArr5, i3, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[(i9 + 1)];
                System.arraycopy(bigIntegerArr5, i3, bigIntegerArr2, i3, length2);
                int i10 = 0;
                while (i10 < i9) {
                    int i11 = i10 + 1;
                    bigIntegerArr2[i11] = bigIntegerArr2[i10].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i8));
                    i10 = i11;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i12 = 0; i12 < i9; i12++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i12].multiply(TWO.pow(i12 * 32)));
                }
                bigIntegerArr2[i3] = bigIntegerArr2[i9];
                BigInteger bigInteger8 = TWO;
                int i13 = i6 + 1;
                BigInteger add = bigInteger8.pow(iArr[i6] - 1).divide(bigIntegerArr4[i13]).add(bigInteger8.pow(iArr[i6] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i13].multiply(bigInteger8.pow(i9 * 32))));
                BigInteger mod = add.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (mod.compareTo(bigInteger9) == 0) {
                    add = add.add(bigInteger9);
                }
                int i14 = 0;
                while (true) {
                    long j5 = (long) i14;
                    bigInteger2 = bigInteger4;
                    BigInteger multiply = bigIntegerArr4[i13].multiply(add.add(BigInteger.valueOf(j5)));
                    BigInteger bigInteger10 = ONE;
                    BigInteger add2 = multiply.add(bigInteger10);
                    bigIntegerArr4[i6] = add2;
                    bigInteger3 = bigInteger5;
                    BigInteger bigInteger11 = TWO;
                    i2 = i5;
                    if (add2.compareTo(bigInteger11.pow(iArr[i6])) != 1) {
                        if (bigInteger11.modPow(bigIntegerArr4[i13].multiply(add.add(BigInteger.valueOf(j5))), bigIntegerArr4[i6]).compareTo(bigInteger10) == 0 && bigInteger11.modPow(add.add(BigInteger.valueOf(j5)), bigIntegerArr4[i6]).compareTo(bigInteger10) != 0) {
                            break;
                        }
                        i14 += 2;
                        bigInteger4 = bigInteger2;
                        i5 = i2;
                        bigInteger5 = bigInteger3;
                    } else {
                        break;
                    }
                }
                bigInteger4 = bigInteger2;
                i5 = i2;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger5 = bigInteger3;
                i3 = 0;
                i8 = 32;
            }
            i6--;
            if (i6 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i7++;
            bigInteger4 = bigInteger2;
            i5 = i2;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger5 = bigInteger3;
            i3 = 0;
        }
        return bigInteger.longValue();
    }

    private void procedure_B(int i, int i2, BigInteger[] bigIntegerArr) {
        int i3 = i;
        while (true) {
            if (i3 >= 0 && i3 <= 65536) {
                break;
            }
            i3 = this.init_random.nextInt() / 32768;
        }
        int i4 = i2;
        while (true) {
            if (i4 >= 0 && i4 <= 65536 && i4 / 2 != 0) {
                break;
            }
            i4 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Integer.toString(i4));
        BigInteger bigInteger2 = new BigInteger("19381");
        int procedure_A = procedure_A(i3, i4, bigIntegerArr2, 256);
        char c = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        int procedure_A2 = procedure_A(procedure_A, i4, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[65];
        bigIntegerArr3[0] = new BigInteger(Integer.toString(procedure_A2));
        while (true) {
            int i5 = 0;
            while (i5 < 64) {
                int i6 = i5 + 1;
                bigIntegerArr3[i6] = bigIntegerArr3[i5].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(16));
                i5 = i6;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i7 = 0; i7 < 64; i7++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i7].multiply(TWO.pow(i7 * 16)));
            }
            bigIntegerArr3[c] = bigIntegerArr3[64];
            BigInteger bigInteger6 = TWO;
            int i8 = 1024;
            BigInteger add = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger mod = add.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (mod.compareTo(bigInteger7) == 0) {
                add = add.add(bigInteger7);
            }
            BigInteger bigInteger8 = add;
            int i9 = 0;
            while (true) {
                long j = (long) i9;
                BigInteger multiply = bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j)));
                BigInteger bigInteger9 = ONE;
                BigInteger add2 = multiply.add(bigInteger9);
                BigInteger bigInteger10 = TWO;
                if (add2.compareTo(bigInteger10.pow(i8)) == 1) {
                    break;
                } else if (bigInteger10.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j))), add2).compareTo(bigInteger9) != 0 || bigInteger10.modPow(bigInteger3.multiply(bigInteger8.add(BigInteger.valueOf(j))), add2).compareTo(bigInteger9) == 0) {
                    i9 += 2;
                    i8 = 1024;
                } else {
                    bigIntegerArr[0] = add2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                }
            }
            c = 0;
        }
    }

    private void procedure_Bb(long j, long j2, BigInteger[] bigIntegerArr) {
        long j3 = j;
        while (true) {
            if (j3 >= 0 && j3 <= 4294967296L) {
                break;
            }
            j3 = (long) (this.init_random.nextInt() * 2);
        }
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L && j4 / 2 != 0) {
                break;
            }
            j4 = (long) ((this.init_random.nextInt() * 2) + 1);
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Long.toString(j4));
        BigInteger bigInteger2 = new BigInteger("97781173");
        long j5 = j4;
        BigInteger[] bigIntegerArr3 = bigIntegerArr2;
        long procedure_Aa = procedure_Aa(j3, j5, bigIntegerArr3, 256);
        char c = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        long procedure_Aa2 = procedure_Aa(procedure_Aa, j5, bigIntegerArr3, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr4 = new BigInteger[33];
        bigIntegerArr4[0] = new BigInteger(Long.toString(procedure_Aa2));
        while (true) {
            int i = 0;
            while (i < 32) {
                int i2 = i + 1;
                bigIntegerArr4[i2] = bigIntegerArr4[i].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(32));
                i = i2;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i3 = 0; i3 < 32; i3++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr4[i3].multiply(TWO.pow(i3 * 32)));
            }
            bigIntegerArr4[c] = bigIntegerArr4[32];
            BigInteger bigInteger6 = TWO;
            int i4 = 1024;
            BigInteger add = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger mod = add.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (mod.compareTo(bigInteger7) == 0) {
                add = add.add(bigInteger7);
            }
            int i5 = 0;
            while (true) {
                long j6 = (long) i5;
                BigInteger multiply = bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j6)));
                BigInteger bigInteger8 = ONE;
                BigInteger add2 = multiply.add(bigInteger8);
                BigInteger bigInteger9 = TWO;
                if (add2.compareTo(bigInteger9.pow(i4)) == 1) {
                    break;
                } else if (bigInteger9.modPow(bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j6))), add2).compareTo(bigInteger8) != 0 || bigInteger9.modPow(bigInteger3.multiply(add.add(BigInteger.valueOf(j6))), add2).compareTo(bigInteger8) == 0) {
                    i5 += 2;
                    i4 = 1024;
                } else {
                    bigIntegerArr[0] = add2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                }
            }
            c = 0;
        }
    }

    private BigInteger procedure_C(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger subtract = bigInteger.subtract(ONE);
        BigInteger divide = subtract.divide(bigInteger2);
        int bitLength = bigInteger.bitLength();
        while (true) {
            BigInteger createRandomBigInteger = BigIntegers.createRandomBigInteger(bitLength, this.init_random);
            BigInteger bigInteger3 = ONE;
            if (createRandomBigInteger.compareTo(bigInteger3) > 0 && createRandomBigInteger.compareTo(subtract) < 0) {
                BigInteger modPow = createRandomBigInteger.modPow(divide, bigInteger);
                if (modPow.compareTo(bigInteger3) != 0) {
                    return modPow;
                }
            }
        }
    }

    public GOST3410Parameters generateParameters() {
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (this.typeproc == 1) {
            int nextInt = this.init_random.nextInt();
            int nextInt2 = this.init_random.nextInt();
            switch (this.size) {
                case 512:
                    procedure_A(nextInt, nextInt2, bigIntegerArr, 512);
                    break;
                case 1024:
                    procedure_B(nextInt, nextInt2, bigIntegerArr);
                    break;
                default:
                    throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
            }
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            return new GOST3410Parameters(bigInteger, bigInteger2, procedure_C(bigInteger, bigInteger2), new GOST3410ValidationParameters(nextInt, nextInt2));
        }
        long nextLong = this.init_random.nextLong();
        long nextLong2 = this.init_random.nextLong();
        switch (this.size) {
            case 512:
                procedure_Aa(nextLong, nextLong2, bigIntegerArr, 512);
                break;
            case 1024:
                procedure_Bb(nextLong, nextLong2, bigIntegerArr);
                break;
            default:
                throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
        }
        BigInteger bigInteger3 = bigIntegerArr[0];
        BigInteger bigInteger4 = bigIntegerArr[1];
        return new GOST3410Parameters(bigInteger3, bigInteger4, procedure_C(bigInteger3, bigInteger4), new GOST3410ValidationParameters(nextLong, nextLong2));
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.size = i;
        this.typeproc = i2;
        this.init_random = secureRandom;
    }
}

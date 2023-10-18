package kotlin.random;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.bouncycastle.asn1.cmc.BodyPartID;

@Metadata(mo112d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016¨\u0006\u0018"}, mo113d2 = {"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Default", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Random.kt */
public abstract class Random {
    public static final Default Default = new Default((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final Random defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();

    public abstract int nextBits(int i);

    public int nextInt() {
        return nextBits(32);
    }

    public int nextInt(int until) {
        return nextInt(0, until);
    }

    public int nextInt(int from, int until) {
        int bitCount;
        int bits;
        int v;
        int rnd;
        boolean z;
        RandomKt.checkRangeBounds(from, until);
        int n = until - from;
        if (n > 0 || n == Integer.MIN_VALUE) {
            if (((-n) & n) == n) {
                bitCount = nextBits(RandomKt.fastLog2(n));
            } else {
                do {
                    bits = nextInt() >>> 1;
                    v = bits % n;
                } while ((bits - v) + (n - 1) < 0);
                bitCount = v;
            }
            return from + bitCount;
        }
        do {
            rnd = nextInt();
            z = false;
            if (from <= rnd && rnd < until) {
                z = true;
                continue;
            }
        } while (!z);
        return rnd;
    }

    public long nextLong() {
        return (((long) nextInt()) << 32) + ((long) nextInt());
    }

    public long nextLong(long until) {
        return nextLong(0, until);
    }

    public long nextLong(long from, long until) {
        long rnd;
        boolean z;
        long rnd2;
        long bits;
        long v;
        long j;
        RandomKt.checkRangeBounds(from, until);
        long n = until - from;
        if (n > 0) {
            if (((-n) & n) == n) {
                int nLow = (int) n;
                int nHigh = (int) (n >>> 32);
                if (nLow != 0) {
                    j = BodyPartID.bodyIdMax & ((long) nextBits(RandomKt.fastLog2(nLow)));
                } else if (nHigh == 1) {
                    j = BodyPartID.bodyIdMax & ((long) nextInt());
                } else {
                    j = (BodyPartID.bodyIdMax & ((long) nextInt())) + (((long) nextBits(RandomKt.fastLog2(nHigh))) << 32);
                }
                rnd2 = j;
            } else {
                do {
                    bits = nextLong() >>> 1;
                    v = bits % n;
                } while ((bits - v) + (n - 1) < 0);
                rnd2 = v;
            }
            return from + rnd2;
        }
        do {
            rnd = nextLong();
            z = false;
            if (from <= rnd && rnd < until) {
                z = true;
                continue;
            }
        } while (!z);
        return rnd;
    }

    public boolean nextBoolean() {
        return nextBits(1) != 0;
    }

    public double nextDouble() {
        return PlatformRandomKt.doubleFromParts(nextBits(26), nextBits(27));
    }

    public double nextDouble(double until) {
        return nextDouble(0.0d, until);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0059  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public double nextDouble(double r9, double r11) {
        /*
            r8 = this;
            kotlin.random.RandomKt.checkRangeBounds((double) r9, (double) r11)
            double r0 = r11 - r9
            boolean r2 = java.lang.Double.isInfinite(r0)
            if (r2 == 0) goto L_0x0045
            boolean r2 = java.lang.Double.isInfinite(r9)
            r3 = 1
            r4 = 0
            if (r2 != 0) goto L_0x001b
            boolean r2 = java.lang.Double.isNaN(r9)
            if (r2 != 0) goto L_0x001b
            r2 = 1
            goto L_0x001c
        L_0x001b:
            r2 = 0
        L_0x001c:
            if (r2 == 0) goto L_0x0045
            boolean r2 = java.lang.Double.isInfinite(r11)
            if (r2 != 0) goto L_0x002b
            boolean r2 = java.lang.Double.isNaN(r11)
            if (r2 != 0) goto L_0x002b
            goto L_0x002c
        L_0x002b:
            r3 = 0
        L_0x002c:
            if (r3 == 0) goto L_0x0045
            double r2 = r8.nextDouble()
            r4 = 2
            double r4 = (double) r4
            java.lang.Double.isNaN(r4)
            double r6 = r11 / r4
            java.lang.Double.isNaN(r4)
            double r4 = r9 / r4
            double r6 = r6 - r4
            double r2 = r2 * r6
            double r4 = r9 + r2
            double r4 = r4 + r2
            goto L_0x004d
        L_0x0045:
            double r2 = r8.nextDouble()
            double r2 = r2 * r0
            double r4 = r9 + r2
        L_0x004d:
            r2 = r4
            int r4 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r4 < 0) goto L_0x0059
            r4 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            double r4 = java.lang.Math.nextAfter(r11, r4)
            goto L_0x005a
        L_0x0059:
            r4 = r2
        L_0x005a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.random.Random.nextDouble(double, double):double");
    }

    public float nextFloat() {
        return ((float) nextBits(24)) / 1.6777216E7f;
    }

    public static /* synthetic */ byte[] nextBytes$default(Random random, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return random.nextBytes(bArr, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
    }

    public byte[] nextBytes(byte[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(array, "array");
        boolean z = true;
        if (new IntRange(0, array.length).contains(fromIndex) && new IntRange(0, array.length).contains(toIndex)) {
            if (fromIndex > toIndex) {
                z = false;
            }
            if (z) {
                int steps = (toIndex - fromIndex) / 4;
                int position = fromIndex;
                for (int i = 0; i < steps; i++) {
                    int i2 = i;
                    int v = nextInt();
                    array[position] = (byte) v;
                    array[position + 1] = (byte) (v >>> 8);
                    array[position + 2] = (byte) (v >>> 16);
                    array[position + 3] = (byte) (v >>> 24);
                    position += 4;
                }
                int remainder = toIndex - position;
                int vr = nextBits(remainder * 8);
                for (int i3 = 0; i3 < remainder; i3++) {
                    array[position + i3] = (byte) (vr >>> (i3 * 8));
                }
                return array;
            }
            throw new IllegalArgumentException(("fromIndex (" + fromIndex + ") must be not greater than toIndex (" + toIndex + ").").toString());
        }
        throw new IllegalArgumentException(("fromIndex (" + fromIndex + ") or toIndex (" + toIndex + ") are out of range: 0.." + array.length + '.').toString());
    }

    public byte[] nextBytes(byte[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return nextBytes(array, 0, array.length);
    }

    public byte[] nextBytes(int size) {
        return nextBytes(new byte[size]);
    }

    @Metadata(mo112d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\u0018\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, mo113d2 = {"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "writeReplace", "", "Serialized", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Random.kt */
    public static final class Default extends Random implements Serializable {
        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Default() {
        }

        @Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\bÂ\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000¨\u0006\b"}, mo113d2 = {"Lkotlin/random/Random$Default$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "serialVersionUID", "", "readResolve", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* compiled from: Random.kt */
        private static final class Serialized implements Serializable {
            public static final Serialized INSTANCE = new Serialized();
            private static final long serialVersionUID = 0;

            private Serialized() {
            }

            private final Object readResolve() {
                return Random.Default;
            }
        }

        private final Object writeReplace() {
            return Serialized.INSTANCE;
        }

        public int nextBits(int bitCount) {
            return Random.defaultRandom.nextBits(bitCount);
        }

        public int nextInt() {
            return Random.defaultRandom.nextInt();
        }

        public int nextInt(int until) {
            return Random.defaultRandom.nextInt(until);
        }

        public int nextInt(int from, int until) {
            return Random.defaultRandom.nextInt(from, until);
        }

        public long nextLong() {
            return Random.defaultRandom.nextLong();
        }

        public long nextLong(long until) {
            return Random.defaultRandom.nextLong(until);
        }

        public long nextLong(long from, long until) {
            return Random.defaultRandom.nextLong(from, until);
        }

        public boolean nextBoolean() {
            return Random.defaultRandom.nextBoolean();
        }

        public double nextDouble() {
            return Random.defaultRandom.nextDouble();
        }

        public double nextDouble(double until) {
            return Random.defaultRandom.nextDouble(until);
        }

        public double nextDouble(double from, double until) {
            return Random.defaultRandom.nextDouble(from, until);
        }

        public float nextFloat() {
            return Random.defaultRandom.nextFloat();
        }

        public byte[] nextBytes(byte[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            return Random.defaultRandom.nextBytes(array);
        }

        public byte[] nextBytes(int size) {
            return Random.defaultRandom.nextBytes(size);
        }

        public byte[] nextBytes(byte[] array, int fromIndex, int toIndex) {
            Intrinsics.checkNotNullParameter(array, "array");
            return Random.defaultRandom.nextBytes(array, fromIndex, toIndex);
        }
    }
}

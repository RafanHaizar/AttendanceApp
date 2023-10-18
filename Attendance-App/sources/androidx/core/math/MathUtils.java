package androidx.core.math;

public class MathUtils {
    private MathUtils() {
    }

    public static int addExact(int x, int y) {
        int r = x + y;
        if (((x ^ r) & (y ^ r)) >= 0) {
            return r;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long addExact(long x, long y) {
        long r = x + y;
        if (((x ^ r) & (y ^ r)) >= 0) {
            return r;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int subtractExact(int x, int y) {
        int r = x - y;
        if (((x ^ y) & (x ^ r)) >= 0) {
            return r;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long subtractExact(long x, long y) {
        long r = x - y;
        if (((x ^ y) & (x ^ r)) >= 0) {
            return r;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int multiplyExact(int x, int y) {
        long r = ((long) x) * ((long) y);
        if (((long) ((int) r)) == r) {
            return (int) r;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long multiplyExact(long x, long y) {
        long r = x * y;
        if (((Math.abs(x) | Math.abs(y)) >>> 31) == 0 || ((y == 0 || r / y == x) && (x != Long.MIN_VALUE || y != -1))) {
            return r;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int incrementExact(int a) {
        if (a != Integer.MAX_VALUE) {
            return a + 1;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long incrementExact(long a) {
        if (a != Long.MAX_VALUE) {
            return 1 + a;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int decrementExact(int a) {
        if (a != Integer.MIN_VALUE) {
            return a - 1;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long decrementExact(long a) {
        if (a != Long.MIN_VALUE) {
            return a - 1;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int negateExact(int a) {
        if (a != Integer.MIN_VALUE) {
            return -a;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long negateExact(long a) {
        if (a != Long.MIN_VALUE) {
            return -a;
        }
        throw new ArithmeticException("long overflow");
    }

    public static int toIntExact(long value) {
        if (((long) ((int) value)) == value) {
            return (int) value;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static long clamp(long value, long min, long max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}

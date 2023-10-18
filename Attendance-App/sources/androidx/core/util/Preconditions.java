package androidx.core.util;

import android.text.TextUtils;
import java.util.Locale;

public final class Preconditions {
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, String messageTemplate, Object... messageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(messageTemplate, messageArgs));
        }
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T string) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException();
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T string, Object errorMessage) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T string, String messageTemplate, Object... messageArgs) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException(String.format(messageTemplate, messageArgs));
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void checkState(boolean expression) {
        checkState(expression, (String) null);
    }

    public static int checkFlagsArgument(int requestedFlags, int allowedFlags) {
        if ((requestedFlags & allowedFlags) == requestedFlags) {
            return requestedFlags;
        }
        throw new IllegalArgumentException("Requested flags 0x" + Integer.toHexString(requestedFlags) + ", but only 0x" + Integer.toHexString(allowedFlags) + " are allowed");
    }

    public static int checkArgumentNonnegative(int value, String errorMessage) {
        if (value >= 0) {
            return value;
        }
        throw new IllegalArgumentException(errorMessage);
    }

    public static int checkArgumentNonnegative(int value) {
        if (value >= 0) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public static int checkArgumentInRange(int value, int lower, int upper, String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[]{valueName, Integer.valueOf(lower), Integer.valueOf(upper)}));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[]{valueName, Integer.valueOf(lower), Integer.valueOf(upper)}));
        }
    }

    public static long checkArgumentInRange(long value, long lower, long upper, String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[]{valueName, Long.valueOf(lower), Long.valueOf(upper)}));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[]{valueName, Long.valueOf(lower), Long.valueOf(upper)}));
        }
    }

    public static float checkArgumentInRange(float value, float lower, float upper, String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", new Object[]{valueName, Float.valueOf(lower), Float.valueOf(upper)}));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", new Object[]{valueName, Float.valueOf(lower), Float.valueOf(upper)}));
        }
    }

    public static double checkArgumentInRange(double value, double lower, double upper, String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", new Object[]{valueName, Double.valueOf(lower), Double.valueOf(upper)}));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", new Object[]{valueName, Double.valueOf(lower), Double.valueOf(upper)}));
        }
    }

    public static float checkArgumentFinite(float value, String valueName) {
        if (Float.isNaN(value)) {
            throw new IllegalArgumentException(valueName + " must not be NaN");
        } else if (!Float.isInfinite(value)) {
            return value;
        } else {
            throw new IllegalArgumentException(valueName + " must not be infinite");
        }
    }

    private Preconditions() {
    }
}

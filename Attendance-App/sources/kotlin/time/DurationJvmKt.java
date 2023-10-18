package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0011"}, mo113d2 = {"durationAssertionsEnabled", "", "getDurationAssertionsEnabled", "()Z", "precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", "", "formatToExactDecimals", "", "value", "", "formatUpToDecimals", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: DurationJvm.kt */
public final class DurationJvmKt {
    private static final boolean durationAssertionsEnabled = false;
    private static final ThreadLocal<DecimalFormat>[] precisionFormats;

    static {
        Class<Duration> cls = Duration.class;
        ThreadLocal<DecimalFormat>[] threadLocalArr = new ThreadLocal[4];
        for (int i = 0; i < 4; i++) {
            threadLocalArr[i] = new ThreadLocal<>();
        }
        precisionFormats = threadLocalArr;
    }

    public static final boolean getDurationAssertionsEnabled() {
        return durationAssertionsEnabled;
    }

    private static final DecimalFormat createFormatForDecimals(int decimals) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        DecimalFormat $this$createFormatForDecimals_u24lambda_u2d0 = decimalFormat;
        if (decimals > 0) {
            $this$createFormatForDecimals_u24lambda_u2d0.setMinimumFractionDigits(decimals);
        }
        $this$createFormatForDecimals_u24lambda_u2d0.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    public static final String formatToExactDecimals(double value, int decimals) {
        DecimalFormat format;
        ThreadLocal<DecimalFormat>[] threadLocalArr = precisionFormats;
        if (decimals < threadLocalArr.length) {
            ThreadLocal<DecimalFormat> threadLocal = threadLocalArr[decimals];
            DecimalFormat decimalFormat = threadLocal.get();
            if (decimalFormat == null) {
                decimalFormat = createFormatForDecimals(decimals);
                threadLocal.set(decimalFormat);
            } else {
                Intrinsics.checkNotNullExpressionValue(decimalFormat, "get() ?: default().also(this::set)");
            }
            format = decimalFormat;
        } else {
            format = createFormatForDecimals(decimals);
        }
        String format2 = format.format(value);
        Intrinsics.checkNotNullExpressionValue(format2, "format.format(value)");
        return format2;
    }

    public static final String formatUpToDecimals(double value, int decimals) {
        DecimalFormat $this$formatUpToDecimals_u24lambda_u2d2 = createFormatForDecimals(0);
        $this$formatUpToDecimals_u24lambda_u2d2.setMaximumFractionDigits(decimals);
        String format = $this$formatUpToDecimals_u24lambda_u2d2.format(value);
        Intrinsics.checkNotNullExpressionValue(format, "createFormatForDecimals(… }\n        .format(value)");
        return format;
    }
}

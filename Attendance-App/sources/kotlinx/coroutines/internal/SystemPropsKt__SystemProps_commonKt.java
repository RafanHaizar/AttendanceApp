package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.text.StringsKt;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u0000\u001a,\u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u0000\u001a,\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\b2\b\b\u0002\u0010\u0006\u001a\u00020\b2\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0000¨\u0006\t"}, mo113d2 = {"systemProp", "", "propertyName", "", "defaultValue", "", "minValue", "maxValue", "", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/internal/SystemPropsKt")
/* compiled from: SystemProps.common.kt */
final /* synthetic */ class SystemPropsKt__SystemProps_commonKt {
    public static final boolean systemProp(String propertyName, boolean defaultValue) {
        String systemProp = SystemPropsKt.systemProp(propertyName);
        return systemProp == null ? defaultValue : Boolean.parseBoolean(systemProp);
    }

    public static /* synthetic */ int systemProp$default(String str, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            i2 = 1;
        }
        if ((i4 & 8) != 0) {
            i3 = Integer.MAX_VALUE;
        }
        return SystemPropsKt.systemProp(str, i, i2, i3);
    }

    public static final int systemProp(String propertyName, int defaultValue, int minValue, int maxValue) {
        return (int) SystemPropsKt.systemProp(propertyName, (long) defaultValue, (long) minValue, (long) maxValue);
    }

    public static /* synthetic */ long systemProp$default(String str, long j, long j2, long j3, int i, Object obj) {
        long j4;
        long j5;
        if ((i & 4) != 0) {
            j4 = 1;
        } else {
            j4 = j2;
        }
        if ((i & 8) != 0) {
            j5 = Long.MAX_VALUE;
        } else {
            j5 = j3;
        }
        return SystemPropsKt.systemProp(str, j, j4, j5);
    }

    public static final long systemProp(String propertyName, long defaultValue, long minValue, long maxValue) {
        String value = SystemPropsKt.systemProp(propertyName);
        if (value == null) {
            return defaultValue;
        }
        Long longOrNull = StringsKt.toLongOrNull(value);
        if (longOrNull != null) {
            long parsed = longOrNull.longValue();
            boolean z = false;
            if (minValue <= parsed && parsed <= maxValue) {
                z = true;
            }
            if (z) {
                return parsed;
            }
            throw new IllegalStateException(("System property '" + propertyName + "' should be in range " + minValue + ".." + maxValue + ", but is '" + parsed + '\'').toString());
        }
        throw new IllegalStateException(("System property '" + propertyName + "' has unrecognized value '" + value + '\'').toString());
    }
}

package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(mo112d1 = {"kotlinx/coroutines/internal/SystemPropsKt__SystemPropsKt", "kotlinx/coroutines/internal/SystemPropsKt__SystemProps_commonKt"}, mo114k = 4, mo115mv = {1, 6, 0}, mo117xi = 48)
public final class SystemPropsKt {
    public static final int getAVAILABLE_PROCESSORS() {
        return SystemPropsKt__SystemPropsKt.getAVAILABLE_PROCESSORS();
    }

    public static final int systemProp(String propertyName, int defaultValue, int minValue, int maxValue) {
        return SystemPropsKt__SystemProps_commonKt.systemProp(propertyName, defaultValue, minValue, maxValue);
    }

    public static final long systemProp(String propertyName, long defaultValue, long minValue, long maxValue) {
        return SystemPropsKt__SystemProps_commonKt.systemProp(propertyName, defaultValue, minValue, maxValue);
    }

    public static final String systemProp(String propertyName) {
        return SystemPropsKt__SystemPropsKt.systemProp(propertyName);
    }

    public static final boolean systemProp(String propertyName, boolean defaultValue) {
        return SystemPropsKt__SystemProps_commonKt.systemProp(propertyName, defaultValue);
    }
}

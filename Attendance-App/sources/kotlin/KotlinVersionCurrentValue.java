package kotlin;

import kotlin.jvm.JvmStatic;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, mo113d2 = {"Lkotlin/KotlinVersionCurrentValue;", "", "()V", "get", "Lkotlin/KotlinVersion;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: KotlinVersion.kt */
final class KotlinVersionCurrentValue {
    public static final KotlinVersionCurrentValue INSTANCE = new KotlinVersionCurrentValue();

    private KotlinVersionCurrentValue() {
    }

    @JvmStatic
    public static final KotlinVersion get() {
        return new KotlinVersion(1, 7, 10);
    }
}

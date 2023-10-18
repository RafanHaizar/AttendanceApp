package kotlin.random;

import java.util.Random;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"}, mo113d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: PlatformRandom.kt */
public final class PlatformRandomKt {
    public static final Random asJavaRandom(Random $this$asJavaRandom) {
        Random impl;
        Intrinsics.checkNotNullParameter($this$asJavaRandom, "<this>");
        AbstractPlatformRandom abstractPlatformRandom = $this$asJavaRandom instanceof AbstractPlatformRandom ? (AbstractPlatformRandom) $this$asJavaRandom : null;
        return (abstractPlatformRandom == null || (impl = abstractPlatformRandom.getImpl()) == null) ? new KotlinRandom($this$asJavaRandom) : impl;
    }

    public static final Random asKotlinRandom(Random $this$asKotlinRandom) {
        Random impl;
        Intrinsics.checkNotNullParameter($this$asKotlinRandom, "<this>");
        KotlinRandom kotlinRandom = $this$asKotlinRandom instanceof KotlinRandom ? (KotlinRandom) $this$asKotlinRandom : null;
        return (kotlinRandom == null || (impl = kotlinRandom.getImpl()) == null) ? new PlatformRandom($this$asKotlinRandom) : impl;
    }

    private static final Random defaultPlatformRandom() {
        return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
    }

    public static final double doubleFromParts(int hi26, int low27) {
        double d = (double) ((((long) hi26) << 27) + ((long) low27));
        Double.isNaN(d);
        return d / 9.007199254740992E15d;
    }
}

package kotlin.random;

import java.io.Serializable;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000 \t2\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001\tB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, mo113d2 = {"Lkotlin/random/PlatformRandom;", "Lkotlin/random/AbstractPlatformRandom;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "impl", "Ljava/util/Random;", "(Ljava/util/Random;)V", "getImpl", "()Ljava/util/Random;", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: PlatformRandom.kt */
final class PlatformRandom extends AbstractPlatformRandom implements Serializable {
    private static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    @Deprecated
    private static final long serialVersionUID = 0;
    private final Random impl;

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo113d2 = {"Lkotlin/random/PlatformRandom$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: PlatformRandom.kt */
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public PlatformRandom(Random impl2) {
        Intrinsics.checkNotNullParameter(impl2, "impl");
        this.impl = impl2;
    }

    public Random getImpl() {
        return this.impl;
    }
}

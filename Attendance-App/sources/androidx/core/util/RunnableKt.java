package androidx.core.util;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002¨\u0006\u0004"}, mo113d2 = {"asRunnable", "Ljava/lang/Runnable;", "Lkotlin/coroutines/Continuation;", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Runnable.kt */
public final class RunnableKt {
    public static final Runnable asRunnable(Continuation<? super Unit> $this$asRunnable) {
        Intrinsics.checkNotNullParameter($this$asRunnable, "<this>");
        return new ContinuationRunnable($this$asRunnable);
    }
}

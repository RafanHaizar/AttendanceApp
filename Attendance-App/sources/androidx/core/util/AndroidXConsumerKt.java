package androidx.core.util;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003¨\u0006\u0004"}, mo113d2 = {"asAndroidXConsumer", "Landroidx/core/util/Consumer;", "T", "Lkotlin/coroutines/Continuation;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AndroidXConsumer.kt */
public final class AndroidXConsumerKt {
    public static final <T> Consumer<T> asAndroidXConsumer(Continuation<? super T> $this$asAndroidXConsumer) {
        Intrinsics.checkNotNullParameter($this$asAndroidXConsumer, "<this>");
        return new AndroidXContinuationConsumer<>($this$asAndroidXConsumer);
    }
}

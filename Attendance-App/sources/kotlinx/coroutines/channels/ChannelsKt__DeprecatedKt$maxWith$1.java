package kotlinx.coroutines.channels;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", mo130f = "Deprecated.kt", mo131i = {0, 0, 0, 1, 1, 1, 1}, mo132l = {420, 422}, mo133m = "maxWith", mo134n = {"comparator", "$this$consume$iv", "iterator", "comparator", "$this$consume$iv", "iterator", "max"}, mo135s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$maxWith$1<E> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;

    ChannelsKt__DeprecatedKt$maxWith$1(Continuation<? super ChannelsKt__DeprecatedKt$maxWith$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChannelsKt__DeprecatedKt.maxWith((ReceiveChannel) null, (Comparator) null, this);
    }
}

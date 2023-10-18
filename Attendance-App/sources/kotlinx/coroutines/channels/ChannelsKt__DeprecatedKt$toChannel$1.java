package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.channels.SendChannel;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1}, mo132l = {487, 278}, mo133m = "toChannel", mo134n = {"destination", "$this$consume$iv$iv", "destination", "$this$consume$iv$iv"}, mo135s = {"L$0", "L$1", "L$0", "L$1"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$toChannel$1<E, C extends SendChannel<? super E>> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;

    ChannelsKt__DeprecatedKt$toChannel$1(Continuation<? super ChannelsKt__DeprecatedKt$toChannel$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChannelsKt.toChannel((ReceiveChannel) null, null, this);
    }
}

package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.TickerChannelsKt", mo130f = "TickerChannels.kt", mo131i = {0, 0, 1, 1, 2, 2}, mo132l = {106, 108, 109}, mo133m = "fixedDelayTicker", mo134n = {"channel", "delayMillis", "channel", "delayMillis", "channel", "delayMillis"}, mo135s = {"L$0", "J$0", "L$0", "J$0", "L$0", "J$0"})
/* compiled from: TickerChannels.kt */
final class TickerChannelsKt$fixedDelayTicker$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;

    TickerChannelsKt$fixedDelayTicker$1(Continuation<? super TickerChannelsKt$fixedDelayTicker$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return TickerChannelsKt.fixedDelayTicker(0, 0, (SendChannel<? super Unit>) null, this);
    }
}

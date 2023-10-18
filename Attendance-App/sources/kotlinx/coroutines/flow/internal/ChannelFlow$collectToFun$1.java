package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "it", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.ChannelFlow$collectToFun$1", mo130f = "ChannelFlow.kt", mo131i = {}, mo132l = {60}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: ChannelFlow.kt */
final class ChannelFlow$collectToFun$1 extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChannelFlow<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlow$collectToFun$1(ChannelFlow<T> channelFlow, Continuation<? super ChannelFlow$collectToFun$1> continuation) {
        super(2, continuation);
        this.this$0 = channelFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelFlow$collectToFun$1 channelFlow$collectToFun$1 = new ChannelFlow$collectToFun$1(this.this$0, continuation);
        channelFlow$collectToFun$1.L$0 = obj;
        return channelFlow$collectToFun$1;
    }

    public final Object invoke(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelFlow$collectToFun$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ChannelFlow<T> channelFlow = this.this$0;
                this.label = 1;
                if (channelFlow.collectTo((ProducerScope) this.L$0, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}

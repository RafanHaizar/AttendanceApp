package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__CollectKt$launchIn$1", mo130f = "Collect.kt", mo131i = {}, mo132l = {50}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Collect.kt */
final class FlowKt__CollectKt$launchIn$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_launchIn;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__CollectKt$launchIn$1(Flow<? extends T> flow, Continuation<? super FlowKt__CollectKt$launchIn$1> continuation) {
        super(2, continuation);
        this.$this_launchIn = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FlowKt__CollectKt$launchIn$1(this.$this_launchIn, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__CollectKt$launchIn$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (FlowKt.collect(this.$this_launchIn, this) != coroutine_suspended) {
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

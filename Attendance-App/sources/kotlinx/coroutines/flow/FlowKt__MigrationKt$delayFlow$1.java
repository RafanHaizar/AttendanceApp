package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$delayFlow$1", mo130f = "Migration.kt", mo131i = {}, mo132l = {415}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Migration.kt */
final class FlowKt__MigrationKt$delayFlow$1 extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $timeMillis;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__MigrationKt$delayFlow$1(long j, Continuation<? super FlowKt__MigrationKt$delayFlow$1> continuation) {
        super(2, continuation);
        this.$timeMillis = j;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FlowKt__MigrationKt$delayFlow$1(this.$timeMillis, continuation);
    }

    public final Object invoke(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return ((FlowKt__MigrationKt$delayFlow$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (DelayKt.delay(this.$timeMillis, this) != coroutine_suspended) {
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

package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(mo112d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H@"}, mo113d2 = {"<anonymous>", "", "T"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$1", mo130f = "Delay.kt", mo131i = {}, mo132l = {233}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$debounceInternal$1$3$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector<T> $downstream;
    final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$debounceInternal$1$3$1(FlowCollector<? super T> flowCollector, Ref.ObjectRef<Object> objectRef, Continuation<? super FlowKt__DelayKt$debounceInternal$1$3$1> continuation) {
        super(1, continuation);
        this.$downstream = flowCollector;
        this.$lastValue = objectRef;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new FlowKt__DelayKt$debounceInternal$1$3$1(this.$downstream, this.$lastValue, continuation);
    }

    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((FlowKt__DelayKt$debounceInternal$1$3$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        FlowKt__DelayKt$debounceInternal$1$3$1 flowKt__DelayKt$debounceInternal$1$3$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                FlowCollector<T> flowCollector = this.$downstream;
                Object this_$iv = NullSurrogateKt.NULL;
                Object value$iv = this.$lastValue.element;
                if (value$iv == this_$iv) {
                    value$iv = null;
                }
                this.label = 1;
                if (flowCollector.emit(value$iv, this) != coroutine_suspended) {
                    flowKt__DelayKt$debounceInternal$1$3$1 = this;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                flowKt__DelayKt$debounceInternal$1$3$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        flowKt__DelayKt$debounceInternal$1$3$1.$lastValue.element = null;
        return Unit.INSTANCE;
    }
}

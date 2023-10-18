package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007H@"}, mo113d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$1$1", mo130f = "Zip.kt", mo131i = {}, mo132l = {33, 33}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Zip.kt */
final class FlowKt__ZipKt$combine$1$1 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ZipKt$combine$1$1(Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super FlowKt__ZipKt$combine$1$1> continuation) {
        super(3, continuation);
        this.$transform = function3;
    }

    public final Object invoke(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
        FlowKt__ZipKt$combine$1$1 flowKt__ZipKt$combine$1$1 = new FlowKt__ZipKt$combine$1$1(this.$transform, continuation);
        flowKt__ZipKt$combine$1$1.L$0 = flowCollector;
        flowKt__ZipKt$combine$1$1.L$1 = objArr;
        return flowKt__ZipKt$combine$1$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        FlowCollector flowCollector;
        FlowKt__ZipKt$combine$1$1 flowKt__ZipKt$combine$1$1;
        Object $result2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                FlowCollector $this$combineInternal = (FlowCollector) this.L$0;
                Object[] it = (Object[]) this.L$1;
                Function3<T1, T2, Continuation<? super R>, Object> function3 = this.$transform;
                Object obj = it[0];
                Object obj2 = it[1];
                this.L$0 = $this$combineInternal;
                this.label = 1;
                Object invoke = function3.invoke(obj, obj2, this);
                if (invoke != coroutine_suspended) {
                    $result2 = $result;
                    $result = invoke;
                    flowCollector = $this$combineInternal;
                    flowKt__ZipKt$combine$1$1 = this;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                flowCollector = (FlowCollector) this.L$0;
                flowKt__ZipKt$combine$1$1 = this;
                $result2 = $result;
                break;
            case 2:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        flowKt__ZipKt$combine$1$1.L$0 = null;
        flowKt__ZipKt$combine$1$1.label = 2;
        if (flowCollector.emit($result, flowKt__ZipKt$combine$1$1) == coroutine_suspended) {
            return coroutine_suspended;
        }
        Object obj3 = $result2;
        FlowKt__ZipKt$combine$1$1 flowKt__ZipKt$combine$1$12 = flowKt__ZipKt$combine$1$1;
        return Unit.INSTANCE;
    }
}

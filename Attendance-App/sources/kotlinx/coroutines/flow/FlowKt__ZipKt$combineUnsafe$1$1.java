package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineUnsafe$1$1", mo130f = "Zip.kt", mo131i = {}, mo132l = {262, 262}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Zip.kt */
public final class FlowKt__ZipKt$combineUnsafe$1$1 extends SuspendLambda implements Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<T[], Continuation<? super R>, Object> $transform;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__ZipKt$combineUnsafe$1$1(Function2<? super T[], ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super FlowKt__ZipKt$combineUnsafe$1$1> continuation) {
        super(3, continuation);
        this.$transform = function2;
    }

    public final Object invoke(FlowCollector<? super R> flowCollector, T[] tArr, Continuation<? super Unit> continuation) {
        FlowKt__ZipKt$combineUnsafe$1$1 flowKt__ZipKt$combineUnsafe$1$1 = new FlowKt__ZipKt$combineUnsafe$1$1(this.$transform, continuation);
        flowKt__ZipKt$combineUnsafe$1$1.L$0 = flowCollector;
        flowKt__ZipKt$combineUnsafe$1$1.L$1 = tArr;
        return flowKt__ZipKt$combineUnsafe$1$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        FlowCollector flowCollector;
        FlowKt__ZipKt$combineUnsafe$1$1 flowKt__ZipKt$combineUnsafe$1$1;
        Object $result2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                FlowCollector $this$combineInternal = (FlowCollector) this.L$0;
                Function2<T[], Continuation<? super R>, Object> function2 = this.$transform;
                this.L$0 = $this$combineInternal;
                this.label = 1;
                Object invoke = function2.invoke((Object[]) this.L$1, this);
                if (invoke != coroutine_suspended) {
                    $result2 = $result;
                    $result = invoke;
                    flowCollector = $this$combineInternal;
                    flowKt__ZipKt$combineUnsafe$1$1 = this;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                flowCollector = (FlowCollector) this.L$0;
                flowKt__ZipKt$combineUnsafe$1$1 = this;
                $result2 = $result;
                break;
            case 2:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        flowKt__ZipKt$combineUnsafe$1$1.L$0 = null;
        flowKt__ZipKt$combineUnsafe$1$1.label = 2;
        if (flowCollector.emit($result, flowKt__ZipKt$combineUnsafe$1$1) == coroutine_suspended) {
            return coroutine_suspended;
        }
        Object obj = $result2;
        FlowKt__ZipKt$combineUnsafe$1$1 flowKt__ZipKt$combineUnsafe$1$12 = flowKt__ZipKt$combineUnsafe$1$1;
        return Unit.INSTANCE;
    }

    public final Object invokeSuspend$$forInline(Object $result) {
        Function2<T[], Continuation<? super R>, Object> function2 = this.$transform;
        InlineMarker.mark(0);
        ((FlowCollector) this.L$0).emit(function2.invoke((Object[]) this.L$1, this), this);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}

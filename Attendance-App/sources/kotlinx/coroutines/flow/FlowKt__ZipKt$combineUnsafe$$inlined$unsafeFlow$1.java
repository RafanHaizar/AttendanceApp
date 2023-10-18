package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ZipKt$combineUnsafe$$inlined$unsafeFlow$1 implements Flow<R> {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function2 $transform$inlined;

    public FlowKt__ZipKt$combineUnsafe$$inlined$unsafeFlow$1(Flow[] flowArr, Function2 function2) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined = function2;
    }

    public Object collect(FlowCollector<? super R> collector, Continuation<? super Unit> $completion) {
        Continuation<? super Unit> continuation = $completion;
        Flow[] flowArr = this.$flows$inlined;
        Function0 access$nullArrayFactory = FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt();
        Intrinsics.needClassReification();
        Object combineInternal = CombineKt.combineInternal(collector, flowArr, access$nullArrayFactory, new FlowKt__ZipKt$combineUnsafe$1$1(this.$transform$inlined, (Continuation<? super FlowKt__ZipKt$combineUnsafe$1$1>) null), $completion);
        if (combineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return combineInternal;
        }
        return Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector collector, Continuation $completion) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, $completion) {
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__ZipKt$combineUnsafe$$inlined$unsafeFlow$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        InlineMarker.mark(5);
        Continuation continuation = $completion;
        Flow[] flowArr = this.$flows$inlined;
        Function0 access$nullArrayFactory = FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt();
        Intrinsics.needClassReification();
        InlineMarker.mark(0);
        CombineKt.combineInternal(collector, flowArr, access$nullArrayFactory, new FlowKt__ZipKt$combineUnsafe$1$1(this.$transform$inlined, (Continuation<? super FlowKt__ZipKt$combineUnsafe$1$1>) null), $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}

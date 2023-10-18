package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__LimitKt$take$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ int $count$inlined;
    final /* synthetic */ Flow $this_take$inlined;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1.C00301
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1.C00301) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            r9 = 0
            java.lang.Object r1 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ AbortFlowException -> 0x0036 }
            goto L_0x005f
        L_0x0036:
            r2 = move-exception
            goto L_0x0063
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            r3 = r10
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = 0
            kotlin.jvm.internal.Ref$IntRef r4 = new kotlin.jvm.internal.Ref$IntRef
            r4.<init>()
            kotlinx.coroutines.flow.Flow r5 = r2.$this_take$inlined     // Catch:{ AbortFlowException -> 0x0060 }
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1 r6 = new kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1     // Catch:{ AbortFlowException -> 0x0060 }
            int r7 = r2.$count$inlined     // Catch:{ AbortFlowException -> 0x0060 }
            r6.<init>(r4, r7, r9)     // Catch:{ AbortFlowException -> 0x0060 }
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6     // Catch:{ AbortFlowException -> 0x0060 }
            r10.L$0 = r9     // Catch:{ AbortFlowException -> 0x0060 }
            r7 = 1
            r10.label = r7     // Catch:{ AbortFlowException -> 0x0060 }
            java.lang.Object r5 = r5.collect(r6, r10)     // Catch:{ AbortFlowException -> 0x0060 }
            if (r5 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r1 = r9
            r9 = r3
        L_0x005f:
            goto L_0x0066
        L_0x0060:
            r2 = move-exception
            r1 = r9
            r9 = r3
        L_0x0063:
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r2, r1)
        L_0x0066:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__LimitKt$take$$inlined$unsafeFlow$1(Flow flow, int i) {
        this.$this_take$inlined = flow;
        this.$count$inlined = i;
    }
}

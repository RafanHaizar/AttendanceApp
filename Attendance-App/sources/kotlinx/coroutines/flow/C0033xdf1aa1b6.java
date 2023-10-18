package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/FlowKt__LimitKt$collectWhile$collector$1", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 */
/* compiled from: Limit.kt */
public final class C0033xdf1aa1b6 implements FlowCollector<T> {
    final /* synthetic */ FlowCollector $$this$flow$inlined;
    final /* synthetic */ Function3 $transform$inlined;

    public C0033xdf1aa1b6(Function3 function3, FlowCollector flowCollector) {
        this.$transform$inlined = function3;
        this.$$this$flow$inlined = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.C0033xdf1aa1b6.C00341
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1$1 r0 = (kotlinx.coroutines.flow.C0033xdf1aa1b6.C00341) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1$1
            r0.<init>(r7, r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            r8 = 0
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 r1 = (kotlinx.coroutines.flow.C0033xdf1aa1b6) r1
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r0
            goto L_0x0059
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            r3 = r9
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = 0
            kotlin.jvm.functions.Function3 r4 = r2.$transform$inlined
            kotlinx.coroutines.flow.FlowCollector r5 = r2.$$this$flow$inlined
            r9.L$0 = r2
            r6 = 1
            r9.label = r6
            r6 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            java.lang.Object r4 = r4.invoke(r5, r8, r9)
            r5 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r5)
            if (r4 != r1) goto L_0x0057
            return r1
        L_0x0057:
            r1 = r2
            r8 = r3
        L_0x0059:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r8 = r4.booleanValue()
            if (r8 == 0) goto L_0x0064
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x0064:
            kotlinx.coroutines.flow.internal.AbortFlowException r8 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r2 = r1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            r8.<init>(r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.C0033xdf1aa1b6.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/FlowKt__LimitKt$collectWhile$collector$1", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1  reason: invalid class name */
/* compiled from: Limit.kt */
public final class FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1 implements FlowCollector<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    public FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1(Function2 function2, FlowCollector flowCollector) {
        this.$predicate$inlined = function2;
        this.$this_unsafeFlow$inlined = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1.C00321
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1.C00321) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0037;
                case 2: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002e:
            r9 = 0
            java.lang.Object r1 = r10.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1 r1 = (kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1) r1
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0082
        L_0x0037:
            r9 = 0
            java.lang.Object r2 = r10.L$1
            java.lang.Object r4 = r10.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1 r4 = (kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1) r4
            kotlin.ResultKt.throwOnFailure(r0)
            r5 = r0
            r7 = r4
            r4 = r2
            r2 = r7
            goto L_0x0068
        L_0x0046:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            r4 = r10
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r4 = 0
            kotlin.jvm.functions.Function2 r5 = r2.$predicate$inlined
            r10.L$0 = r2
            r10.L$1 = r9
            r10.label = r3
            r6 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            java.lang.Object r5 = r5.invoke(r9, r10)
            r6 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            if (r5 != r1) goto L_0x0065
            return r1
        L_0x0065:
            r7 = r4
            r4 = r9
            r9 = r7
        L_0x0068:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x0084
            kotlinx.coroutines.flow.FlowCollector r5 = r2.$this_unsafeFlow$inlined
            r10.L$0 = r2
            r6 = 0
            r10.L$1 = r6
            r6 = 2
            r10.label = r6
            java.lang.Object r4 = r5.emit(r4, r10)
            if (r4 != r1) goto L_0x0081
            return r1
        L_0x0081:
            r1 = r2
        L_0x0082:
            r2 = r1
            goto L_0x0085
        L_0x0084:
            r3 = 0
        L_0x0085:
            if (r3 == 0) goto L_0x008b
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L_0x008b:
            kotlinx.coroutines.flow.internal.AbortFlowException r9 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r1 = r2
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            r9.<init>(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

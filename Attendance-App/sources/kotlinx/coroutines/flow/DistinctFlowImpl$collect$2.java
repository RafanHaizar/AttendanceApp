package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Distinct.kt */
final class DistinctFlowImpl$collect$2<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ FlowCollector<T> $collector;
    final /* synthetic */ Ref.ObjectRef<Object> $previousKey;
    final /* synthetic */ DistinctFlowImpl<T> this$0;

    DistinctFlowImpl$collect$2(DistinctFlowImpl<T> distinctFlowImpl, Ref.ObjectRef<Object> objectRef, FlowCollector<? super T> flowCollector) {
        this.this$0 = distinctFlowImpl;
        this.$previousKey = objectRef;
        this.$collector = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.DistinctFlowImpl$collect$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.DistinctFlowImpl$collect$2$emit$1 r0 = (kotlinx.coroutines.flow.DistinctFlowImpl$collect$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.DistinctFlowImpl$collect$2$emit$1 r0 = new kotlinx.coroutines.flow.DistinctFlowImpl$collect$2$emit$1
            r0.<init>(r6, r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x005a
        L_0x0031:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            kotlinx.coroutines.flow.DistinctFlowImpl<T> r3 = r2.this$0
            kotlin.jvm.functions.Function1<T, java.lang.Object> r3 = r3.keySelector
            java.lang.Object r3 = r3.invoke(r7)
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r4 = r2.$previousKey
            T r4 = r4.element
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r4 == r5) goto L_0x005d
            kotlinx.coroutines.flow.DistinctFlowImpl<T> r4 = r2.this$0
            kotlin.jvm.functions.Function2<java.lang.Object, java.lang.Object, java.lang.Boolean> r4 = r4.areEquivalent
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r5 = r2.$previousKey
            T r5 = r5.element
            java.lang.Object r4 = r4.invoke(r5, r3)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x005a
            goto L_0x005d
        L_0x005a:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L_0x005d:
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r4 = r2.$previousKey
            r4.element = r3
            kotlinx.coroutines.flow.FlowCollector<T> r3 = r2.$collector
            r4 = 1
            r8.label = r4
            java.lang.Object r7 = r3.emit(r7, r8)
            if (r7 != r1) goto L_0x005a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.DistinctFlowImpl$collect$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

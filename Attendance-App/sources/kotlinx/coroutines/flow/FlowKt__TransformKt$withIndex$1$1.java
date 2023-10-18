package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Transform.kt */
final class FlowKt__TransformKt$withIndex$1$1<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ Ref.IntRef $index;
    final /* synthetic */ FlowCollector<IndexedValue<? extends T>> $this_unsafeFlow;

    FlowKt__TransformKt$withIndex$1$1(FlowCollector<? super IndexedValue<? extends T>> flowCollector, Ref.IntRef intRef) {
        this.$this_unsafeFlow = flowCollector;
        this.$index = intRef;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1$emit$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0055
        L_0x0031:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            kotlinx.coroutines.flow.FlowCollector<kotlin.collections.IndexedValue<? extends T>> r3 = r2.$this_unsafeFlow
            kotlin.collections.IndexedValue r4 = new kotlin.collections.IndexedValue
            kotlin.jvm.internal.Ref$IntRef r5 = r2.$index
            int r5 = r5.element
            kotlin.jvm.internal.Ref$IntRef r6 = r2.$index
            int r7 = r5 + 1
            r6.element = r7
            r2 = r5
            r5 = 0
            if (r2 < 0) goto L_0x0058
            r4.<init>(r2, r9)
            r2 = 1
            r10.label = r2
            java.lang.Object r9 = r3.emit(r4, r10)
            if (r9 != r1) goto L_0x0055
            return r1
        L_0x0055:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L_0x0058:
            java.lang.ArithmeticException r9 = new java.lang.ArithmeticException
            java.lang.String r1 = "Index overflow has happened"
            r9.<init>(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$withIndex$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

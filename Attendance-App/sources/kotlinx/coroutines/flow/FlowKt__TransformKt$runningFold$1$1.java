package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Transform.kt */
final class FlowKt__TransformKt$runningFold$1$1<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ Ref.ObjectRef<R> $accumulator;
    final /* synthetic */ Function3<R, T, Continuation<? super R>, Object> $operation;
    final /* synthetic */ FlowCollector<R> $this_unsafeFlow;

    FlowKt__TransformKt$runningFold$1$1(Ref.ObjectRef<R> objectRef, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, FlowCollector<? super R> flowCollector) {
        this.$accumulator = objectRef;
        this.$operation = function3;
        this.$this_unsafeFlow = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0071 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0031;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0072
        L_0x0031:
            java.lang.Object r9 = r10.L$1
            kotlin.jvm.internal.Ref$ObjectRef r9 = (kotlin.jvm.internal.Ref.ObjectRef) r9
            java.lang.Object r2 = r10.L$0
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1 r2 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1) r2
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r2
            r2 = r0
            goto L_0x005b
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            kotlin.jvm.internal.Ref$ObjectRef<R> r3 = r2.$accumulator
            kotlin.jvm.functions.Function3<R, T, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r4 = r2.$operation
            T r5 = r3.element
            r10.L$0 = r2
            r10.L$1 = r3
            r6 = 1
            r10.label = r6
            java.lang.Object r9 = r4.invoke(r5, r9, r10)
            if (r9 != r1) goto L_0x0057
            return r1
        L_0x0057:
            r7 = r2
            r2 = r9
            r9 = r3
            r3 = r7
        L_0x005b:
            r9.element = r2
            kotlinx.coroutines.flow.FlowCollector<R> r9 = r3.$this_unsafeFlow
            kotlin.jvm.internal.Ref$ObjectRef<R> r2 = r3.$accumulator
            T r2 = r2.element
            r4 = 0
            r10.L$0 = r4
            r10.L$1 = r4
            r4 = 2
            r10.label = r4
            java.lang.Object r9 = r9.emit(r2, r10)
            if (r9 != r1) goto L_0x0072
            return r1
        L_0x0072:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

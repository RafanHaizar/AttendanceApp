package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: Reduce.kt */
public final class FlowKt__ReduceKt$fold$2<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ Ref.ObjectRef<R> $accumulator;
    final /* synthetic */ Function3<R, T, Continuation<? super R>, Object> $operation;

    public FlowKt__ReduceKt$fold$2(Ref.ObjectRef<R> objectRef, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3) {
        this.$accumulator = objectRef;
        this.$operation = function3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2$emit$1
            r0.<init>(r7, r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            java.lang.Object r8 = r9.L$0
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            kotlin.ResultKt.throwOnFailure(r0)
            r1 = r0
            goto L_0x004e
        L_0x0036:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            kotlin.jvm.internal.Ref$ObjectRef<R> r3 = r2.$accumulator
            kotlin.jvm.functions.Function3<R, T, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r4 = r2.$operation
            T r5 = r3.element
            r9.L$0 = r3
            r6 = 1
            r9.label = r6
            java.lang.Object r8 = r4.invoke(r5, r8, r9)
            if (r8 != r1) goto L_0x004c
            return r1
        L_0x004c:
            r1 = r8
            r8 = r3
        L_0x004e:
            r8.element = r1
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object emit$$forInline(T value, Continuation<? super Unit> $completion) {
        InlineMarker.mark(4);
        new FlowKt__ReduceKt$fold$2$emit$1(this, $completion);
        InlineMarker.mark(5);
        Ref.ObjectRef<R> objectRef = this.$accumulator;
        objectRef.element = this.$operation.invoke(objectRef.element, value, $completion);
        return Unit.INSTANCE;
    }
}

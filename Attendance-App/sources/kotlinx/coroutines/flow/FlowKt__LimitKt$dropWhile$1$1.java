package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$dropWhile$1$1<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ Ref.BooleanRef $matched;
    final /* synthetic */ Function2<T, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ FlowCollector<T> $this_unsafeFlow;

    FlowKt__LimitKt$dropWhile$1$1(Ref.BooleanRef booleanRef, FlowCollector<? super T> flowCollector, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        this.$matched = booleanRef;
        this.$this_unsafeFlow = flowCollector;
        this.$predicate = function2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r7v0, types: [T, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            r0.<init>(r6, r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x0039;
                case 2: goto L_0x002e;
                case 3: goto L_0x0039;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002e:
            java.lang.Object r7 = r8.L$1
            java.lang.Object r2 = r8.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1 r2 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1) r2
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r0
            goto L_0x0065
        L_0x0039:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0052
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            kotlin.jvm.internal.Ref$BooleanRef r4 = r2.$matched
            boolean r4 = r4.element
            if (r4 == 0) goto L_0x0055
            kotlinx.coroutines.flow.FlowCollector<T> r4 = r2.$this_unsafeFlow
            r8.label = r3
            java.lang.Object r7 = r4.emit(r7, r8)
            if (r7 != r1) goto L_0x0052
            return r1
        L_0x0052:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L_0x0055:
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r4 = r2.$predicate
            r8.L$0 = r2
            r8.L$1 = r7
            r5 = 2
            r8.label = r5
            java.lang.Object r4 = r4.invoke(r7, r8)
            if (r4 != r1) goto L_0x0065
            return r1
        L_0x0065:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x0082
            kotlin.jvm.internal.Ref$BooleanRef r4 = r2.$matched
            r4.element = r3
            kotlinx.coroutines.flow.FlowCollector<T> r3 = r2.$this_unsafeFlow
            r4 = 0
            r8.L$0 = r4
            r8.L$1 = r4
            r4 = 3
            r8.label = r4
            java.lang.Object r7 = r3.emit(r7, r8)
            if (r7 != r1) goto L_0x0052
            return r1
        L_0x0082:
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Count.kt */
final class FlowKt__CountKt$count$4<T> implements FlowCollector, SuspendFunction {

    /* renamed from: $i */
    final /* synthetic */ Ref.IntRef f7$i;
    final /* synthetic */ Function2<T, Continuation<? super Boolean>, Object> $predicate;

    FlowKt__CountKt$count$4(Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, Ref.IntRef intRef) {
        this.$predicate = function2;
        this.f7$i = intRef;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1
            r0.<init>(r5, r7)
        L_0x0019:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002e:
            java.lang.Object r6 = r7.L$0
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4 r6 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$4) r6
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            r6 = r0
            goto L_0x0049
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r4 = r2.$predicate
            r7.L$0 = r2
            r7.label = r3
            java.lang.Object r6 = r4.invoke(r6, r7)
            if (r6 != r1) goto L_0x0049
            return r1
        L_0x0049:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x005c
            kotlin.jvm.internal.Ref$IntRef r6 = r2.f7$i
            int r1 = r6.element
            int r1 = r1 + r3
            r6.element = r1
            kotlin.jvm.internal.Ref$IntRef r6 = r2.f7$i
            int r6 = r6.element
        L_0x005c:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__CountKt$count$4.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

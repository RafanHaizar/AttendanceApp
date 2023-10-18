package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Errors.kt */
final class FlowKt__ErrorsKt$catchImpl$2<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ FlowCollector<T> $collector;
    final /* synthetic */ Ref.ObjectRef<Throwable> $fromDownstream;

    FlowKt__ErrorsKt$catchImpl$2(FlowCollector<? super T> flowCollector, Ref.ObjectRef<Throwable> objectRef) {
        this.$collector = flowCollector;
        this.$fromDownstream = objectRef;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1
            r0.<init>(r5, r7)
        L_0x0019:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r7.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2 r6 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0035 }
            goto L_0x004b
        L_0x0035:
            r1 = move-exception
            goto L_0x0051
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            kotlinx.coroutines.flow.FlowCollector<T> r3 = r2.$collector     // Catch:{ all -> 0x004f }
            r7.L$0 = r2     // Catch:{ all -> 0x004f }
            r4 = 1
            r7.label = r4     // Catch:{ all -> 0x004f }
            java.lang.Object r3 = r3.emit(r6, r7)     // Catch:{ all -> 0x004f }
            if (r3 != r1) goto L_0x004a
            return r1
        L_0x004a:
            r6 = r2
        L_0x004b:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x004f:
            r1 = move-exception
            r6 = r2
        L_0x0051:
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Throwable> r2 = r6.$fromDownstream
            r2.element = r1
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

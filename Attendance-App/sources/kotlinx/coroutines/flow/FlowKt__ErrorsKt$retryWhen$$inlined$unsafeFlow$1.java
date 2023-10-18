package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function4;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function4 $predicate$inlined;
    final /* synthetic */ Flow $this_retryWhen$inlined;

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0066, code lost:
        r6 = 0;
        r7 = r2.$this_retryWhen$inlined;
        r15.L$0 = r2;
        r15.L$1 = r14;
        r15.L$2 = null;
        r15.J$0 = r4;
        r15.I$0 = 0;
        r15.label = 1;
        r7 = kotlinx.coroutines.flow.FlowKt.catchImpl(r7, r14, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x007b, code lost:
        if (r7 != r1) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007d, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007e, code lost:
        r5 = r14;
        r14 = r3;
        r3 = r4;
        r12 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r2;
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0087, code lost:
        r0 = (java.lang.Throwable) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0089, code lost:
        if (r0 == null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x008b, code lost:
        r6 = r7.$predicate$inlined;
        r8 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r3);
        r15.L$0 = r7;
        r15.L$1 = r5;
        r15.L$2 = r0;
        r15.J$0 = r3;
        r15.label = 2;
        kotlin.jvm.internal.InlineMarker.mark(6);
        r6 = r6.invoke(r5, r0, r8, r15);
        kotlin.jvm.internal.InlineMarker.mark(7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a8, code lost:
        if (r6 != r2) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00aa, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ab, code lost:
        r10 = r5;
        r5 = r0;
        r0 = r6;
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b5, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b7, code lost:
        r4 = r3 + 1;
        r3 = r14;
        r14 = r6;
        r6 = 1;
        r0 = r1;
        r1 = r2;
        r2 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00c4, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c5, code lost:
        r0 = r1;
        r1 = r2;
        r2 = r7;
        r10 = r3;
        r3 = r14;
        r14 = r5;
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00cc, code lost:
        if (r6 != 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d1, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.FlowCollector<? super T>, code=kotlinx.coroutines.flow.FlowCollector, for r14v0, types: [kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.C00291
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.C00291) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1
            r0.<init>(r13, r15)
        L_0x0019:
            r15 = r0
            java.lang.Object r0 = r15.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r15.label
            switch(r2) {
                case 0: goto L_0x005c;
                case 1: goto L_0x0047;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L_0x002d:
            r14 = 0
            long r2 = r15.J$0
            java.lang.Object r4 = r15.L$2
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r5 = r15.L$1
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r6 = r15.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r6 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r6
            kotlin.ResultKt.throwOnFailure(r0)
            r7 = r6
            r6 = r5
            r5 = r4
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x00af
        L_0x0047:
            r14 = 0
            int r2 = r15.I$0
            long r3 = r15.J$0
            java.lang.Object r5 = r15.L$1
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r6 = r15.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r6 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r6
            kotlin.ResultKt.throwOnFailure(r0)
            r7 = r6
            r6 = r2
            r2 = r1
            r1 = r0
            goto L_0x0087
        L_0x005c:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r13
            r3 = r15
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = 0
            r4 = 0
        L_0x0066:
            r6 = 0
            kotlinx.coroutines.flow.Flow r7 = r2.$this_retryWhen$inlined
            r15.L$0 = r2
            r15.L$1 = r14
            r8 = 0
            r15.L$2 = r8
            r15.J$0 = r4
            r15.I$0 = r6
            r8 = 1
            r15.label = r8
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.catchImpl(r7, r14, r15)
            if (r7 != r1) goto L_0x007e
            return r1
        L_0x007e:
            r10 = r4
            r5 = r14
            r14 = r3
            r3 = r10
            r12 = r1
            r1 = r0
            r0 = r7
            r7 = r2
            r2 = r12
        L_0x0087:
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            if (r0 == 0) goto L_0x00c5
            kotlin.jvm.functions.Function4 r6 = r7.$predicate$inlined
            java.lang.Long r8 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r3)
            r15.L$0 = r7
            r15.L$1 = r5
            r15.L$2 = r0
            r15.J$0 = r3
            r9 = 2
            r15.label = r9
            r9 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r9)
            java.lang.Object r6 = r6.invoke(r5, r0, r8, r15)
            r8 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r8)
            if (r6 != r2) goto L_0x00ab
            return r2
        L_0x00ab:
            r10 = r5
            r5 = r0
            r0 = r6
            r6 = r10
        L_0x00af:
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x00c4
            r0 = 1
            r8 = 1
            long r3 = r3 + r8
            r4 = r3
            r3 = r14
            r14 = r6
            r6 = r0
            r0 = r1
            r1 = r2
            r2 = r7
            goto L_0x00cc
        L_0x00c4:
            throw r5
        L_0x00c5:
            r0 = r1
            r1 = r2
            r2 = r7
            r10 = r3
            r3 = r14
            r14 = r5
            r4 = r10
        L_0x00cc:
            if (r6 != 0) goto L_0x0066
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1(Flow flow, Function4 function4) {
        this.$this_retryWhen$inlined = flow;
        this.$predicate$inlined = function4;
    }
}

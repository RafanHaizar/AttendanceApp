package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6 implements Flow<T> {
    final /* synthetic */ Object[] $this_asFlow$inlined;

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6.C00161
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6$1 r0 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6.C00161) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6$1 r0 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6$1
            r0.<init>(r10, r12)
        L_0x0019:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            switch(r2) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r2 = 0
            r3 = 0
            int r4 = r12.I$1
            int r5 = r12.I$0
            java.lang.Object r6 = r12.L$1
            java.lang.Object[] r6 = (java.lang.Object[]) r6
            java.lang.Object r7 = r12.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x006e
        L_0x0040:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r3 = r12
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = 0
            java.lang.Object[] r2 = r2.$this_asFlow$inlined
            r4 = 0
            int r5 = r2.length
            r6 = 0
            r7 = r11
            r11 = r3
            r3 = r2
            r2 = r4
            r4 = r5
        L_0x0052:
            if (r6 >= r4) goto L_0x0071
            r5 = r3[r6]
            int r6 = r6 + 1
            r8 = 0
            r12.L$0 = r7
            r12.L$1 = r3
            r12.I$0 = r6
            r12.I$1 = r4
            r9 = 1
            r12.label = r9
            java.lang.Object r5 = r7.emit(r5, r12)
            if (r5 != r1) goto L_0x006b
            return r1
        L_0x006b:
            r5 = r6
            r6 = r3
            r3 = r8
        L_0x006e:
            r3 = r6
            r6 = r5
            goto L_0x0052
        L_0x0071:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6(Object[] objArr) {
        this.$this_asFlow$inlined = objArr;
    }
}

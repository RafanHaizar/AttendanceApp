package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_onStart$inlined;

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0087 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.C00251
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.C00251) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1$1
            r0.<init>(r7, r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0043;
                case 1: goto L_0x0032;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            r8 = 0
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0088
        L_0x0032:
            r8 = 0
            java.lang.Object r2 = r9.L$2
            kotlinx.coroutines.flow.internal.SafeCollector r2 = (kotlinx.coroutines.flow.internal.SafeCollector) r2
            java.lang.Object r3 = r9.L$1
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r4 = r9.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r4 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x008b }
            goto L_0x0071
        L_0x0043:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r7
            r2 = r9
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3 = r8
            r8 = 0
            kotlinx.coroutines.flow.internal.SafeCollector r2 = new kotlinx.coroutines.flow.internal.SafeCollector
            r5 = 0
            kotlin.coroutines.CoroutineContext r6 = r9.getContext()
            r2.<init>(r3, r6)
            kotlin.jvm.functions.Function2 r5 = r4.$action$inlined     // Catch:{ all -> 0x008b }
            r9.L$0 = r4     // Catch:{ all -> 0x008b }
            r9.L$1 = r3     // Catch:{ all -> 0x008b }
            r9.L$2 = r2     // Catch:{ all -> 0x008b }
            r6 = 1
            r9.label = r6     // Catch:{ all -> 0x008b }
            r6 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r6)     // Catch:{ all -> 0x008b }
            java.lang.Object r5 = r5.invoke(r2, r9)     // Catch:{ all -> 0x008b }
            r6 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r6)     // Catch:{ all -> 0x008b }
            if (r5 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r2.releaseIntercepted()
            kotlinx.coroutines.flow.Flow r2 = r4.$this_onStart$inlined
            r5 = 0
            r9.L$0 = r5
            r9.L$1 = r5
            r9.L$2 = r5
            r5 = 2
            r9.label = r5
            java.lang.Object r2 = r2.collect(r3, r9)
            if (r2 != r1) goto L_0x0088
            return r1
        L_0x0088:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x008b:
            r1 = move-exception
            r2.releaseIntercepted()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(Function2 function2, Flow flow) {
        this.$action$inlined = function2;
        this.$this_onStart$inlined = flow;
    }
}

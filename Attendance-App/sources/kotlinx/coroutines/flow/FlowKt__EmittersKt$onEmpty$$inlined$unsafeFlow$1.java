package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_onEmpty$inlined;

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.C00241
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.C00241) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            r0.<init>(r9, r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x004a;
                case 1: goto L_0x0039;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002d:
            r10 = 0
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r1 = (kotlinx.coroutines.flow.internal.SafeCollector) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0036 }
            goto L_0x009e
        L_0x0036:
            r2 = move-exception
            goto L_0x00a6
        L_0x0039:
            r10 = 0
            java.lang.Object r2 = r11.L$2
            kotlin.jvm.internal.Ref$BooleanRef r2 = (kotlin.jvm.internal.Ref.BooleanRef) r2
            java.lang.Object r3 = r11.L$1
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r4 = r11.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 r4 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1) r4
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0073
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r9
            r2 = r11
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3 = r10
            r10 = 0
            kotlin.jvm.internal.Ref$BooleanRef r2 = new kotlin.jvm.internal.Ref$BooleanRef
            r2.<init>()
            r5 = 1
            r2.element = r5
            kotlinx.coroutines.flow.Flow r6 = r4.$this_onEmpty$inlined
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1 r7 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1
            r7.<init>(r2, r3)
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            r11.L$0 = r4
            r11.L$1 = r3
            r11.L$2 = r2
            r11.label = r5
            java.lang.Object r5 = r6.collect(r7, r11)
            if (r5 != r1) goto L_0x0073
            return r1
        L_0x0073:
            boolean r5 = r2.element
            if (r5 == 0) goto L_0x00aa
            kotlinx.coroutines.flow.internal.SafeCollector r2 = new kotlinx.coroutines.flow.internal.SafeCollector
            r5 = 0
            kotlin.coroutines.CoroutineContext r6 = r11.getContext()
            r2.<init>(r3, r6)
            kotlin.jvm.functions.Function2 r3 = r4.$action$inlined     // Catch:{ all -> 0x00a2 }
            r11.L$0 = r2     // Catch:{ all -> 0x00a2 }
            r5 = 0
            r11.L$1 = r5     // Catch:{ all -> 0x00a2 }
            r11.L$2 = r5     // Catch:{ all -> 0x00a2 }
            r5 = 2
            r11.label = r5     // Catch:{ all -> 0x00a2 }
            r5 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r5)     // Catch:{ all -> 0x00a2 }
            java.lang.Object r3 = r3.invoke(r2, r11)     // Catch:{ all -> 0x00a2 }
            r5 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r5)     // Catch:{ all -> 0x00a2 }
            if (r3 != r1) goto L_0x009d
            return r1
        L_0x009d:
            r1 = r2
        L_0x009e:
            r1.releaseIntercepted()
            goto L_0x00aa
        L_0x00a2:
            r1 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
        L_0x00a6:
            r1.releaseIntercepted()
            throw r2
        L_0x00aa:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_onEmpty$inlined = flow;
        this.$action$inlined = function2;
    }
}

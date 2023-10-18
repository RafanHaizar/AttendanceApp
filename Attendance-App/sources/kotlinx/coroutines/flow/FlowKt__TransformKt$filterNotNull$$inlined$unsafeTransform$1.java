package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d0 = collector;
        Continuation continuation = $completion;
        Object collect = this.$this_unsafeTransform$inlined.collect(new Object() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
                /*
                    r5 = this;
                    boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.C00532.C00541
                    if (r0 == 0) goto L_0x0014
                    r0 = r7
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.C00532.C00541) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r7 = r0.label
                    int r7 = r7 - r2
                    r0.label = r7
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r5, r7)
                L_0x0019:
                    r7 = r0
                    java.lang.Object r0 = r7.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r7.label
                    switch(r2) {
                        case 0: goto L_0x0032;
                        case 1: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                    java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                    r6.<init>(r7)
                    throw r6
                L_0x002d:
                    r6 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x004a
                L_0x0032:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r5
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r2 = r7
                    kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                    r2 = r3
                    r3 = 0
                    if (r6 == 0) goto L_0x004b
                    r4 = 1
                    r7.label = r4
                    java.lang.Object r6 = r2.emit(r6, r7)
                    if (r6 != r1) goto L_0x0049
                    return r1
                L_0x0049:
                    r6 = r3
                L_0x004a:
                    goto L_0x004c
                L_0x004b:
                L_0x004c:
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.C00532.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}

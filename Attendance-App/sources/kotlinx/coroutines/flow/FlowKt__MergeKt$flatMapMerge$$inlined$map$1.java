package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__MergeKt$flatMapMerge$$inlined$map$1 implements Flow<Flow<? extends R>> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ Function2 $transform$inlined$1;

    public FlowKt__MergeKt$flatMapMerge$$inlined$map$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$transform$inlined$1 = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d0 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$transform$inlined$1;
        Object collect = flow.collect(new Object() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x003d  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x005f A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0060  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1.C00372.C00381
                    if (r0 == 0) goto L_0x0014
                    r0 = r9
                    kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1.C00372.C00381) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r9 = r0.label
                    int r9 = r9 - r2
                    r0.label = r9
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1$2$1
                    r0.<init>(r7, r9)
                L_0x0019:
                    r9 = r0
                    java.lang.Object r0 = r9.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r9.label
                    switch(r2) {
                        case 0: goto L_0x003d;
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
                    goto L_0x0061
                L_0x0032:
                    r8 = 0
                    java.lang.Object r2 = r9.L$0
                    kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r8
                    r8 = r0
                    goto L_0x0053
                L_0x003d:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r7
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = 0
                    kotlin.jvm.functions.Function2 r5 = r5
                    r9.L$0 = r3
                    r6 = 1
                    r9.label = r6
                    java.lang.Object r8 = r5.invoke(r8, r9)
                    if (r8 != r1) goto L_0x0052
                    return r1
                L_0x0052:
                    r2 = r3
                L_0x0053:
                    r3 = 0
                    r9.L$0 = r3
                    r3 = 2
                    r9.label = r3
                    java.lang.Object r8 = r2.emit(r8, r9)
                    if (r8 != r1) goto L_0x0060
                    return r1
                L_0x0060:
                    r8 = r4
                L_0x0061:
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapMerge$$inlined$map$1.C00372.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}

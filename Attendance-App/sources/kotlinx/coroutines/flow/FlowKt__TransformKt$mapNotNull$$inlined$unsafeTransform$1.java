package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1 implements Flow<R> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ Function2 $transform$inlined$1;

    public FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
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
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0059  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.C00601
                    if (r0 == 0) goto L_0x0014
                    r0 = r9
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.C00601) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r9 = r0.label
                    int r9 = r9 - r2
                    r0.label = r9
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1
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
                    goto L_0x0067
                L_0x0032:
                    r8 = 0
                    java.lang.Object r2 = r9.L$0
                    kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r8
                    r8 = r0
                    goto L_0x0056
                L_0x003d:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r7
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = r9
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = 0
                    kotlin.jvm.functions.Function2 r5 = r5
                    r9.L$0 = r3
                    r6 = 1
                    r9.label = r6
                    java.lang.Object r8 = r5.invoke(r8, r9)
                    if (r8 != r1) goto L_0x0055
                    return r1
                L_0x0055:
                    r2 = r3
                L_0x0056:
                    if (r8 != 0) goto L_0x0059
                    goto L_0x0068
                L_0x0059:
                    r3 = 0
                    r9.L$0 = r3
                    r3 = 2
                    r9.label = r3
                    java.lang.Object r8 = r2.emit(r8, r9)
                    if (r8 != r1) goto L_0x0066
                    return r1
                L_0x0066:
                    r8 = r4
                L_0x0067:
                L_0x0068:
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00592 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
                    }
                };
                InlineMarker.mark(5);
                FlowCollector $this$mapNotNull_u24lambda_u2d5 = $this$unsafeTransform_u24lambda_u2d0;
                Continuation continuation = $completion;
                Object transformed = r5.invoke(value, $completion);
                if (transformed != null) {
                    InlineMarker.mark(0);
                    $this$mapNotNull_u24lambda_u2d5.emit(transformed, $completion);
                    InlineMarker.mark(1);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector collector, Continuation $completion) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, $completion) {
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        InlineMarker.mark(5);
        final FlowCollector $this$unsafeTransform_u24lambda_u2d0 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$transform$inlined$1;
        InlineMarker.mark(0);
        flow.collect(new Object() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.C00601
                    if (r0 == 0) goto L_0x0014
                    r0 = r9
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.C00601) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r9 = r0.label
                    int r9 = r9 - r2
                    r0.label = r9
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1
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
                    goto L_0x0067
                L_0x0032:
                    r8 = 0
                    java.lang.Object r2 = r9.L$0
                    kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r8
                    r8 = r0
                    goto L_0x0056
                L_0x003d:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r7
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = r9
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = 0
                    kotlin.jvm.functions.Function2 r5 = r5
                    r9.L$0 = r3
                    r6 = 1
                    r9.label = r6
                    java.lang.Object r8 = r5.invoke(r8, r9)
                    if (r8 != r1) goto L_0x0055
                    return r1
                L_0x0055:
                    r2 = r3
                L_0x0056:
                    if (r8 != 0) goto L_0x0059
                    goto L_0x0068
                L_0x0059:
                    r3 = 0
                    r9.L$0 = r3
                    r3 = 2
                    r9.label = r3
                    java.lang.Object r8 = r2.emit(r8, r9)
                    if (r8 != r1) goto L_0x0066
                    return r1
                L_0x0066:
                    r8 = r4
                L_0x0067:
                L_0x0068:
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.C00592.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00592 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
                    }
                };
                InlineMarker.mark(5);
                FlowCollector $this$mapNotNull_u24lambda_u2d5 = $this$unsafeTransform_u24lambda_u2d0;
                Continuation continuation = $completion;
                Object transformed = function2.invoke(value, $completion);
                if (transformed != null) {
                    InlineMarker.mark(0);
                    $this$mapNotNull_u24lambda_u2d5.emit(transformed, $completion);
                    InlineMarker.mark(1);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}

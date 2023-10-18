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
public final class FlowKt__TransformKt$filter$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filter$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$predicate$inlined = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d0 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$predicate$inlined;
        Object collect = flow.collect(new Object() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0040  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0065  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.C00461
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.C00461) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0040;
                        case 1: goto L_0x0032;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002d:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0074
                L_0x0032:
                    r9 = 0
                    java.lang.Object r2 = r10.L$1
                    kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                    java.lang.Object r3 = r10.L$0
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r3
                    r3 = r2
                    r2 = r0
                    goto L_0x005d
                L_0x0040:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = r10
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = 0
                    kotlin.jvm.functions.Function2 r5 = r5
                    r10.L$0 = r9
                    r10.L$1 = r3
                    r6 = 1
                    r10.label = r6
                    java.lang.Object r2 = r5.invoke(r9, r10)
                    if (r2 != r1) goto L_0x005a
                    return r1
                L_0x005a:
                    r7 = r4
                    r4 = r9
                    r9 = r7
                L_0x005d:
                    java.lang.Boolean r2 = (java.lang.Boolean) r2
                    boolean r2 = r2.booleanValue()
                    if (r2 == 0) goto L_0x0075
                    r2 = 0
                    r10.L$0 = r2
                    r10.L$1 = r2
                    r2 = 2
                    r10.label = r2
                    java.lang.Object r2 = r3.emit(r4, r10)
                    if (r2 != r1) goto L_0x0074
                    return r1
                L_0x0074:
                    goto L_0x0076
                L_0x0075:
                L_0x0076:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00452 this$0;

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
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d0;
                Object value2 = value;
                Continuation continuation = $completion;
                if (((Boolean) r5.invoke(value2, $completion)).booleanValue()) {
                    InlineMarker.mark(0);
                    $this$filter_u24lambda_u2d0.emit(value2, $completion);
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
            final /* synthetic */ FlowKt__TransformKt$filter$$inlined$unsafeTransform$1 this$0;

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
        final Function2 function2 = this.$predicate$inlined;
        InlineMarker.mark(0);
        flow.collect(new Object() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.C00461
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.C00461) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0040;
                        case 1: goto L_0x0032;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002d:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0074
                L_0x0032:
                    r9 = 0
                    java.lang.Object r2 = r10.L$1
                    kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                    java.lang.Object r3 = r10.L$0
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r3
                    r3 = r2
                    r2 = r0
                    goto L_0x005d
                L_0x0040:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = r10
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = 0
                    kotlin.jvm.functions.Function2 r5 = r5
                    r10.L$0 = r9
                    r10.L$1 = r3
                    r6 = 1
                    r10.label = r6
                    java.lang.Object r2 = r5.invoke(r9, r10)
                    if (r2 != r1) goto L_0x005a
                    return r1
                L_0x005a:
                    r7 = r4
                    r4 = r9
                    r9 = r7
                L_0x005d:
                    java.lang.Boolean r2 = (java.lang.Boolean) r2
                    boolean r2 = r2.booleanValue()
                    if (r2 == 0) goto L_0x0075
                    r2 = 0
                    r10.L$0 = r2
                    r10.L$1 = r2
                    r2 = 2
                    r10.label = r2
                    java.lang.Object r2 = r3.emit(r4, r10)
                    if (r2 != r1) goto L_0x0074
                    return r1
                L_0x0074:
                    goto L_0x0076
                L_0x0075:
                L_0x0076:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C00452.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00452 this$0;

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
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d0;
                Object value2 = value;
                Continuation continuation = $completion;
                if (((Boolean) function2.invoke(value2, $completion)).booleanValue()) {
                    InlineMarker.mark(0);
                    $this$filter_u24lambda_u2d0.emit(value2, $completion);
                    InlineMarker.mark(1);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}

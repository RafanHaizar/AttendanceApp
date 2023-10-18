package kotlinx.coroutines.flow;

import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$filter$$inlined$unsafeTransform$1"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 implements Flow<Object> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filterIsInstance$$inlined$filter$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d0 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        Intrinsics.needClassReification();
        Object collect = flow.collect(new Object() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.C00491
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.C00491) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0032;
                        case 1: goto L_0x002d;
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
                    goto L_0x0053
                L_0x0032:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    kotlinx.coroutines.flow.FlowCollector r2 = r0
                    r3 = 0
                    r4 = r10
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = r9
                    r5 = 0
                    r6 = 3
                    java.lang.String r7 = "R"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r6, r7)
                    boolean r4 = r4 instanceof java.lang.Object
                    if (r4 == 0) goto L_0x0054
                    r4 = 1
                    r10.label = r4
                    java.lang.Object r9 = r2.emit(r9, r10)
                    if (r9 != r1) goto L_0x0052
                    return r1
                L_0x0052:
                    r9 = r3
                L_0x0053:
                    goto L_0x0055
                L_0x0054:
                L_0x0055:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00482 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d0;
                Object value2 = value;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(3, SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE);
                if (value2 instanceof Object) {
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
            final /* synthetic */ FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 this$0;

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
        Intrinsics.needClassReification();
        InlineMarker.mark(0);
        flow.collect(new Object() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.C00491
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.C00491) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0032;
                        case 1: goto L_0x002d;
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
                    goto L_0x0053
                L_0x0032:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    kotlinx.coroutines.flow.FlowCollector r2 = r0
                    r3 = 0
                    r4 = r10
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r4 = r9
                    r5 = 0
                    r6 = 3
                    java.lang.String r7 = "R"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r6, r7)
                    boolean r4 = r4 instanceof java.lang.Object
                    if (r4 == 0) goto L_0x0054
                    r4 = 1
                    r10.label = r4
                    java.lang.Object r9 = r2.emit(r9, r10)
                    if (r9 != r1) goto L_0x0052
                    return r1
                L_0x0052:
                    r9 = r3
                L_0x0053:
                    goto L_0x0055
                L_0x0054:
                L_0x0055:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.C00482.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C00482 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d0;
                Object value2 = value;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(3, SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE);
                if (value2 instanceof Object) {
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

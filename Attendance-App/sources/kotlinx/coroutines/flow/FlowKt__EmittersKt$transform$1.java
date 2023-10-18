package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1", mo130f = "Emitters.kt", mo131i = {}, mo132l = {40}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Emitters.kt */
public final class FlowKt__EmittersKt$transform$1 extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_transform;
    final /* synthetic */ Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__EmittersKt$transform$1(Flow<? extends T> flow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super FlowKt__EmittersKt$transform$1> continuation) {
        super(2, continuation);
        this.$this_transform = flow;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__EmittersKt$transform$1 flowKt__EmittersKt$transform$1 = new FlowKt__EmittersKt$transform$1(this.$this_transform, this.$transform, continuation);
        flowKt__EmittersKt$transform$1.L$0 = obj;
        return flowKt__EmittersKt$transform$1;
    }

    public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
        return ((FlowKt__EmittersKt$transform$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                final FlowCollector $this$flow = (FlowCollector) this.L$0;
                Flow<T> flow = this.$this_transform;
                final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> function3 = this.$transform;
                this.label = 1;
                if (flow.collect(new Object() {
                    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
                    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1
                            if (r0 == 0) goto L_0x0014
                            r0 = r8
                            kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r1 = r1 & r2
                            if (r1 == 0) goto L_0x0014
                            int r8 = r0.label
                            int r8 = r8 - r2
                            r0.label = r8
                            goto L_0x0019
                        L_0x0014:
                            kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1
                            r0.<init>(r6, r8)
                        L_0x0019:
                            r8 = r0
                            java.lang.Object r0 = r8.result
                            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r2 = r8.label
                            switch(r2) {
                                case 0: goto L_0x0031;
                                case 1: goto L_0x002d;
                                default: goto L_0x0025;
                            }
                        L_0x0025:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r8)
                            throw r7
                        L_0x002d:
                            kotlin.ResultKt.throwOnFailure(r0)
                            goto L_0x0043
                        L_0x0031:
                            kotlin.ResultKt.throwOnFailure(r0)
                            r2 = r6
                            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r3 = r3
                            kotlinx.coroutines.flow.FlowCollector<R> r4 = r0
                            r5 = 1
                            r8.label = r5
                            java.lang.Object r7 = r3.invoke(r4, r7, r8)
                            if (r7 != r1) goto L_0x0043
                            return r1
                        L_0x0043:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1.C00271.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }

                    public final Object emit$$forInline(T value, Continuation<? super Unit> $completion) {
                        InlineMarker.mark(4);
                        new FlowKt__EmittersKt$transform$1$1$emit$1(this, $completion);
                        InlineMarker.mark(5);
                        r3.invoke($this$flow, value, $completion);
                        return Unit.INSTANCE;
                    }
                }, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }

    public final Object invokeSuspend$$forInline(Object $result) {
        final FlowCollector $this$flow = (FlowCollector) this.L$0;
        Flow<T> flow = this.$this_transform;
        final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> function3 = this.$transform;
        InlineMarker.mark(0);
        flow.collect(new Object() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
                /*
                    r6 = this;
                    boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1
                    if (r0 == 0) goto L_0x0014
                    r0 = r8
                    kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r8 = r0.label
                    int r8 = r8 - r2
                    r0.label = r8
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1$1$emit$1
                    r0.<init>(r6, r8)
                L_0x0019:
                    r8 = r0
                    java.lang.Object r0 = r8.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r8.label
                    switch(r2) {
                        case 0: goto L_0x0031;
                        case 1: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                    java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                    r7.<init>(r8)
                    throw r7
                L_0x002d:
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0043
                L_0x0031:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r6
                    kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r3 = r3
                    kotlinx.coroutines.flow.FlowCollector<R> r4 = r0
                    r5 = 1
                    r8.label = r5
                    java.lang.Object r7 = r3.invoke(r4, r7, r8)
                    if (r7 != r1) goto L_0x0043
                    return r1
                L_0x0043:
                    kotlin.Unit r7 = kotlin.Unit.INSTANCE
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$transform$1.C00271.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(T value, Continuation<? super Unit> $completion) {
                InlineMarker.mark(4);
                new FlowKt__EmittersKt$transform$1$1$emit$1(this, $completion);
                InlineMarker.mark(5);
                function3.invoke($this$flow, value, $completion);
                return Unit.INSTANCE;
            }
        }, this);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}

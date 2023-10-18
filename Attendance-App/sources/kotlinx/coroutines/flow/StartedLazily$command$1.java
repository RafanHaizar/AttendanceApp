package kotlinx.coroutines.flow;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo113d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.StartedLazily$command$1", mo130f = "SharingStarted.kt", mo131i = {}, mo132l = {155}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: SharingStarted.kt */
final class StartedLazily$command$1 extends SuspendLambda implements Function2<FlowCollector<? super SharingCommand>, Continuation<? super Unit>, Object> {
    final /* synthetic */ StateFlow<Integer> $subscriptionCount;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StartedLazily$command$1(StateFlow<Integer> stateFlow, Continuation<? super StartedLazily$command$1> continuation) {
        super(2, continuation);
        this.$subscriptionCount = stateFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        StartedLazily$command$1 startedLazily$command$1 = new StartedLazily$command$1(this.$subscriptionCount, continuation);
        startedLazily$command$1.L$0 = obj;
        return startedLazily$command$1;
    }

    public final Object invoke(FlowCollector<? super SharingCommand> flowCollector, Continuation<? super Unit> continuation) {
        return ((StartedLazily$command$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                final FlowCollector $this$flow = (FlowCollector) this.L$0;
                final Ref.BooleanRef started = new Ref.BooleanRef();
                this.label = 1;
                if (this.$subscriptionCount.collect(new Object() {
                    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
                    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final java.lang.Object emit(int r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1
                            if (r0 == 0) goto L_0x0014
                            r0 = r7
                            kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1 r0 = (kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r1 = r1 & r2
                            if (r1 == 0) goto L_0x0014
                            int r7 = r0.label
                            int r7 = r7 - r2
                            r0.label = r7
                            goto L_0x0019
                        L_0x0014:
                            kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1 r0 = new kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1
                            r0.<init>(r5, r7)
                        L_0x0019:
                            r7 = r0
                            java.lang.Object r0 = r7.result
                            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r2 = r7.label
                            switch(r2) {
                                case 0: goto L_0x0031;
                                case 1: goto L_0x002d;
                                default: goto L_0x0025;
                            }
                        L_0x0025:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L_0x002d:
                            kotlin.ResultKt.throwOnFailure(r0)
                            goto L_0x004f
                        L_0x0031:
                            kotlin.ResultKt.throwOnFailure(r0)
                            r2 = r5
                            if (r6 <= 0) goto L_0x004f
                            kotlin.jvm.internal.Ref$BooleanRef r3 = r3
                            boolean r3 = r3.element
                            if (r3 != 0) goto L_0x004f
                            kotlin.jvm.internal.Ref$BooleanRef r6 = r3
                            r3 = 1
                            r6.element = r3
                            kotlinx.coroutines.flow.FlowCollector<kotlinx.coroutines.flow.SharingCommand> r6 = r2
                            kotlinx.coroutines.flow.SharingCommand r4 = kotlinx.coroutines.flow.SharingCommand.START
                            r7.label = r3
                            java.lang.Object r6 = r6.emit(r4, r7)
                            if (r6 != r1) goto L_0x004f
                            return r1
                        L_0x004f:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedLazily$command$1.C00851.emit(int, kotlin.coroutines.Continuation):java.lang.Object");
                    }

                    public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                        return emit(((Number) value).intValue(), (Continuation<? super Unit>) $completion);
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
        throw new KotlinNothingValueException();
    }
}

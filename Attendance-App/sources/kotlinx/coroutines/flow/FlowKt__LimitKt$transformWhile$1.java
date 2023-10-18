package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1", mo130f = "Limit.kt", mo131i = {0}, mo132l = {153}, mo133m = "invokeSuspend", mo134n = {"collector$iv"}, mo135s = {"L$0"})
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$transformWhile$1 extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_transformWhile;
    final /* synthetic */ Function3<FlowCollector<? super R>, T, Continuation<? super Boolean>, Object> $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__LimitKt$transformWhile$1(Flow<? extends T> flow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Boolean>, ? extends Object> function3, Continuation<? super FlowKt__LimitKt$transformWhile$1> continuation) {
        super(2, continuation);
        this.$this_transformWhile = flow;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__LimitKt$transformWhile$1 flowKt__LimitKt$transformWhile$1 = new FlowKt__LimitKt$transformWhile$1(this.$this_transformWhile, this.$transform, continuation);
        flowKt__LimitKt$transformWhile$1.L$0 = obj;
        return flowKt__LimitKt$transformWhile$1;
    }

    public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
        return ((FlowKt__LimitKt$transformWhile$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x001d;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0011:
            r0 = r8
            r1 = 0
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 r2 = (kotlinx.coroutines.flow.C0033xdf1aa1b6) r2
            kotlin.ResultKt.throwOnFailure(r9)     // Catch:{ AbortFlowException -> 0x001b }
            goto L_0x0046
        L_0x001b:
            r3 = move-exception
            goto L_0x004b
        L_0x001d:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlinx.coroutines.flow.Flow<T> r3 = r1.$this_transformWhile
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r4 = r1.$transform
            r5 = 0
            kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 r6 = new kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1
            r6.<init>(r4, r2)
            r4 = r6
            r2 = r4
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2     // Catch:{ AbortFlowException -> 0x0047 }
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch:{ AbortFlowException -> 0x0047 }
            r1.L$0 = r4     // Catch:{ AbortFlowException -> 0x0047 }
            r7 = 1
            r1.label = r7     // Catch:{ AbortFlowException -> 0x0047 }
            java.lang.Object r2 = r3.collect(r2, r6)     // Catch:{ AbortFlowException -> 0x0047 }
            if (r2 != r0) goto L_0x0043
            return r0
        L_0x0043:
            r0 = r1
            r2 = r4
            r1 = r5
        L_0x0046:
            goto L_0x0051
        L_0x0047:
            r3 = move-exception
            r0 = r1
            r2 = r4
            r1 = r5
        L_0x004b:
            r4 = r2
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r3, r4)
        L_0x0051:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

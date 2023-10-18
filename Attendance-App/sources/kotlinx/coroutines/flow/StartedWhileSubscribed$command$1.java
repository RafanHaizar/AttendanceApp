package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H@"}, mo113d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;", "count", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", mo130f = "SharingStarted.kt", mo131i = {1, 2, 3}, mo132l = {178, 180, 182, 183, 185}, mo133m = "invokeSuspend", mo134n = {"$this$transformLatest", "$this$transformLatest", "$this$transformLatest"}, mo135s = {"L$0", "L$0", "L$0"})
/* compiled from: SharingStarted.kt */
final class StartedWhileSubscribed$command$1 extends SuspendLambda implements Function3<FlowCollector<? super SharingCommand>, Integer, Continuation<? super Unit>, Object> {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StartedWhileSubscribed this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StartedWhileSubscribed$command$1(StartedWhileSubscribed startedWhileSubscribed, Continuation<? super StartedWhileSubscribed$command$1> continuation) {
        super(3, continuation);
        this.this$0 = startedWhileSubscribed;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector<? super SharingCommand>) (FlowCollector) obj, ((Number) obj2).intValue(), (Continuation<? super Unit>) (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super SharingCommand> flowCollector, int i, Continuation<? super Unit> continuation) {
        StartedWhileSubscribed$command$1 startedWhileSubscribed$command$1 = new StartedWhileSubscribed$command$1(this.this$0, continuation);
        startedWhileSubscribed$command$1.L$0 = flowCollector;
        startedWhileSubscribed$command$1.I$0 = i;
        return startedWhileSubscribed$command$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0075, code lost:
        if (r1.this$0.replayExpiration <= 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0077, code lost:
        r1.L$0 = r2;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0085, code lost:
        if (r2.emit(kotlinx.coroutines.flow.SharingCommand.STOP, r1) != r0) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0087, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0088, code lost:
        r1.L$0 = r2;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009a, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r1.this$0.replayExpiration, r1) != r0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009c, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009e, code lost:
        r1.L$0 = null;
        r1.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ad, code lost:
        if (r2.emit(kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE, r1) != r0) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00af, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b0, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b4, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0038;
                case 1: goto L_0x0033;
                case 2: goto L_0x002a;
                case 3: goto L_0x0021;
                case 4: goto L_0x0017;
                case 5: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0011:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x00b1
        L_0x0017:
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x009d
        L_0x0021:
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0088
        L_0x002a:
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x006b
        L_0x0033:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0055
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            int r3 = r1.I$0
            if (r3 <= 0) goto L_0x0056
            kotlinx.coroutines.flow.SharingCommand r3 = kotlinx.coroutines.flow.SharingCommand.START
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 1
            r1.label = r5
            java.lang.Object r2 = r2.emit(r3, r4)
            if (r2 != r0) goto L_0x0054
            return r0
        L_0x0054:
            r0 = r1
        L_0x0055:
            goto L_0x00b2
        L_0x0056:
            kotlinx.coroutines.flow.StartedWhileSubscribed r3 = r1.this$0
            long r3 = r3.stopTimeout
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r6 = 2
            r1.label = r6
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r3 != r0) goto L_0x006b
            return r0
        L_0x006b:
            kotlinx.coroutines.flow.StartedWhileSubscribed r3 = r1.this$0
            long r3 = r3.replayExpiration
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x009e
            kotlinx.coroutines.flow.SharingCommand r3 = kotlinx.coroutines.flow.SharingCommand.STOP
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r2
            r5 = 3
            r1.label = r5
            java.lang.Object r3 = r2.emit(r3, r4)
            if (r3 != r0) goto L_0x0088
            return r0
        L_0x0088:
            kotlinx.coroutines.flow.StartedWhileSubscribed r3 = r1.this$0
            long r3 = r3.replayExpiration
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r6 = 4
            r1.label = r6
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r3 != r0) goto L_0x009d
            return r0
        L_0x009d:
        L_0x009e:
            kotlinx.coroutines.flow.SharingCommand r3 = kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 0
            r1.L$0 = r5
            r5 = 5
            r1.label = r5
            java.lang.Object r2 = r2.emit(r3, r4)
            if (r2 != r0) goto L_0x00b0
            return r0
        L_0x00b0:
            r0 = r1
        L_0x00b1:
        L_0x00b2:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedWhileSubscribed$command$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

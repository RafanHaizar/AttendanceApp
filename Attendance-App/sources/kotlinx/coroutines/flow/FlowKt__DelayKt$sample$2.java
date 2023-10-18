package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2", mo130f = "Delay.kt", mo131i = {0, 0, 0, 0}, mo132l = {352}, mo133m = "invokeSuspend", mo134n = {"downstream", "values", "lastValue", "ticker"}, mo135s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$sample$2 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $periodMillis;
    final /* synthetic */ Flow<T> $this_sample;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$sample$2(long j, Flow<? extends T> flow, Continuation<? super FlowKt__DelayKt$sample$2> continuation) {
        super(3, continuation);
        this.$periodMillis = j;
        this.$this_sample = flow;
    }

    public final Object invoke(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        FlowKt__DelayKt$sample$2 flowKt__DelayKt$sample$2 = new FlowKt__DelayKt$sample$2(this.$periodMillis, this.$this_sample, continuation);
        flowKt__DelayKt$sample$2.L$0 = coroutineScope;
        flowKt__DelayKt$sample$2.L$1 = flowCollector;
        return flowKt__DelayKt$sample$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:8|(1:9)|10|11|12|13|14|15|16|17|18|19|20|29|(1:31)|(1:33)(4:34|35|6|(1:36)(0))|33) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:8|9|10|11|12|13|14|15|16|17|18|19|20|29|(1:31)|(1:33)(4:34|35|6|(1:36)(0))|33) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b1, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b4, code lost:
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b7, code lost:
        r15 = null;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0075  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r17
            int r2 = r1.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0035;
                case 1: goto L_0x0014;
                default: goto L_0x000c;
            }
        L_0x000c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0014:
            r2 = r17
            r4 = r18
            r5 = 0
            java.lang.Object r6 = r2.L$3
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r2.L$2
            kotlin.jvm.internal.Ref$ObjectRef r7 = (kotlin.jvm.internal.Ref.ObjectRef) r7
            java.lang.Object r8 = r2.L$1
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r9 = r2.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            kotlin.ResultKt.throwOnFailure(r4)
            r15 = r3
            r16 = r2
            r2 = r0
            r0 = r4
            r4 = r16
            goto L_0x00d3
        L_0x0035:
            kotlin.ResultKt.throwOnFailure(r18)
            r2 = r17
            r4 = r18
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.CoroutineScope r5 = (kotlinx.coroutines.CoroutineScope) r5
            java.lang.Object r6 = r2.L$1
            r13 = r6
            kotlinx.coroutines.flow.FlowCollector r13 = (kotlinx.coroutines.flow.FlowCollector) r13
            r7 = 0
            r8 = -1
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1 r6 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1
            kotlinx.coroutines.flow.Flow<T> r9 = r2.$this_sample
            r6.<init>(r9, r3)
            r9 = r6
            kotlin.jvm.functions.Function2 r9 = (kotlin.jvm.functions.Function2) r9
            r10 = 1
            r11 = 0
            r6 = r5
            kotlinx.coroutines.channels.ReceiveChannel r14 = kotlinx.coroutines.channels.ProduceKt.produce$default(r6, r7, r8, r9, r10, r11)
            kotlin.jvm.internal.Ref$ObjectRef r6 = new kotlin.jvm.internal.Ref$ObjectRef
            r6.<init>()
            r15 = r6
            long r7 = r2.$periodMillis
            r9 = 0
            r11 = 2
            r12 = 0
            r6 = r5
            kotlinx.coroutines.channels.ReceiveChannel r6 = kotlinx.coroutines.flow.FlowKt__DelayKt.fixedPeriodTicker$default(r6, r7, r9, r11, r12)
            r5 = r4
            r9 = r13
            r8 = r14
            r7 = r15
            r4 = r2
            r2 = r0
        L_0x006f:
            T r0 = r7.element
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
            if (r0 == r10) goto L_0x00d8
            r10 = 0
            r4.L$0 = r9
            r4.L$1 = r8
            r4.L$2 = r7
            r4.L$3 = r6
            r0 = 1
            r4.label = r0
            r11 = r4
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r12 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r11)
            r13 = r0
            r0 = r13
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x00b9 }
            r14 = 0
            kotlinx.coroutines.selects.SelectClause1 r15 = r8.getOnReceiveCatching()     // Catch:{ all -> 0x00b9 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$1 r3 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$1     // Catch:{ all -> 0x00b6 }
            r1 = 0
            r3.<init>(r7, r6, r1)     // Catch:{ all -> 0x00b3 }
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3     // Catch:{ all -> 0x00b6 }
            r0.invoke(r15, r3)     // Catch:{ all -> 0x00b6 }
            kotlinx.coroutines.selects.SelectClause1 r1 = r6.getOnReceive()     // Catch:{ all -> 0x00b6 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$2 r3 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$2     // Catch:{ all -> 0x00b6 }
            r15 = 0
            r3.<init>(r7, r9, r15)     // Catch:{ all -> 0x00b1 }
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3     // Catch:{ all -> 0x00b1 }
            r0.invoke(r1, r3)     // Catch:{ all -> 0x00b1 }
            goto L_0x00be
        L_0x00b1:
            r0 = move-exception
            goto L_0x00bb
        L_0x00b3:
            r0 = move-exception
            r15 = r1
            goto L_0x00bb
        L_0x00b6:
            r0 = move-exception
            r15 = 0
            goto L_0x00bb
        L_0x00b9:
            r0 = move-exception
            r15 = r3
        L_0x00bb:
            r13.handleBuilderException(r0)
        L_0x00be:
            java.lang.Object r0 = r13.getResult()
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r1) goto L_0x00ce
            r1 = r4
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r1)
        L_0x00ce:
            if (r0 != r2) goto L_0x00d1
            return r2
        L_0x00d1:
            r0 = r5
            r5 = r10
        L_0x00d3:
            r1 = r17
            r5 = r0
            r3 = r15
            goto L_0x006f
        L_0x00d8:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

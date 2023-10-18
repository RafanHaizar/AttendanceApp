package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "T", "inner", "Lkotlinx/coroutines/flow/Flow;", "emit", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Merge.kt */
final class ChannelFlowMerge$collectTo$2<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ SendingCollector<T> $collector;
    final /* synthetic */ Job $job;
    final /* synthetic */ ProducerScope<T> $scope;
    final /* synthetic */ Semaphore $semaphore;

    ChannelFlowMerge$collectTo$2(Job job, Semaphore semaphore, ProducerScope<? super T> producerScope, SendingCollector<T> sendingCollector) {
        this.$job = job;
        this.$semaphore = semaphore;
        this.$scope = producerScope;
        this.$collector = sendingCollector;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.Flow<? extends T>, code=kotlinx.coroutines.flow.Flow, for r10v0, types: [kotlinx.coroutines.flow.Flow<? extends T>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(final kotlinx.coroutines.flow.Flow r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1
            r0.<init>(r9, r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x0039;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002d:
            java.lang.Object r10 = r11.L$1
            kotlinx.coroutines.flow.Flow r10 = (kotlinx.coroutines.flow.Flow) r10
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2 r1 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2) r1
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0056
        L_0x0039:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r9
            kotlinx.coroutines.Job r3 = r2.$job
            if (r3 != 0) goto L_0x0042
            goto L_0x0045
        L_0x0042:
            kotlinx.coroutines.JobKt.ensureActive((kotlinx.coroutines.Job) r3)
        L_0x0045:
            kotlinx.coroutines.sync.Semaphore r3 = r2.$semaphore
            r11.L$0 = r2
            r11.L$1 = r10
            r4 = 1
            r11.label = r4
            java.lang.Object r3 = r3.acquire(r11)
            if (r3 != r1) goto L_0x0055
            return r1
        L_0x0055:
            r1 = r2
        L_0x0056:
            kotlinx.coroutines.channels.ProducerScope<T> r2 = r1.$scope
            r3 = r2
            kotlinx.coroutines.CoroutineScope r3 = (kotlinx.coroutines.CoroutineScope) r3
            r4 = 0
            r5 = 0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1 r2 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1
            kotlinx.coroutines.flow.internal.SendingCollector<T> r6 = r1.$collector
            kotlinx.coroutines.sync.Semaphore r7 = r1.$semaphore
            r8 = 0
            r2.<init>(r10, r6, r7, r8)
            r6 = r2
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
            r7 = 3
            kotlinx.coroutines.Job unused = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r3, r4, r5, r6, r7, r8)
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2.emit(kotlinx.coroutines.flow.Flow, kotlin.coroutines.Continuation):java.lang.Object");
    }
}

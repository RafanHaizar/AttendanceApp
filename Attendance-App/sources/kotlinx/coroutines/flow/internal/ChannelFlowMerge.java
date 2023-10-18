package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.sync.SemaphoreKt;

@Metadata(mo112d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B?\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0014J\u001f\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H@ø\u0001\u0000¢\u0006\u0002\u0010\u0013J&\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u0017H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0004X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowMerge;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "concurrency", "", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;ILkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "additionalToStringProps", "", "collectTo", "", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Merge.kt */
public final class ChannelFlowMerge<T> extends ChannelFlow<T> {
    private final int concurrency;
    private final Flow<Flow<T>> flow;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ ChannelFlowMerge(kotlinx.coroutines.flow.Flow r7, int r8, kotlin.coroutines.CoroutineContext r9, int r10, kotlinx.coroutines.channels.BufferOverflow r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x000a
            kotlin.coroutines.EmptyCoroutineContext r9 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            kotlin.coroutines.CoroutineContext r9 = (kotlin.coroutines.CoroutineContext) r9
            r3 = r9
            goto L_0x000b
        L_0x000a:
            r3 = r9
        L_0x000b:
            r9 = r12 & 8
            if (r9 == 0) goto L_0x0012
            r10 = -2
            r4 = -2
            goto L_0x0013
        L_0x0012:
            r4 = r10
        L_0x0013:
            r9 = r12 & 16
            if (r9 == 0) goto L_0x001b
            kotlinx.coroutines.channels.BufferOverflow r11 = kotlinx.coroutines.channels.BufferOverflow.SUSPEND
            r5 = r11
            goto L_0x001c
        L_0x001b:
            r5 = r11
        L_0x001c:
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge.<init>(kotlinx.coroutines.flow.Flow, int, kotlin.coroutines.CoroutineContext, int, kotlinx.coroutines.channels.BufferOverflow, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public ChannelFlowMerge(Flow<? extends Flow<? extends T>> flow2, int concurrency2, CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        super(context, capacity, onBufferOverflow);
        this.flow = flow2;
        this.concurrency = concurrency2;
    }

    /* access modifiers changed from: protected */
    public ChannelFlow<T> create(CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        return new ChannelFlowMerge<>(this.flow, this.concurrency, context, capacity, onBufferOverflow);
    }

    public ReceiveChannel<T> produceImpl(CoroutineScope scope) {
        return ProduceKt.produce(scope, this.context, this.capacity, getCollectToFun$kotlinx_coroutines_core());
    }

    /* access modifiers changed from: protected */
    public Object collectTo(ProducerScope<? super T> scope, Continuation<? super Unit> $completion) {
        Object collect = this.flow.collect(new ChannelFlowMerge$collectTo$2((Job) $completion.getContext().get(Job.Key), SemaphoreKt.Semaphore$default(this.concurrency, 0, 2, (Object) null), scope, new SendingCollector(scope)), $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public String additionalToStringProps() {
        return Intrinsics.stringPlus("concurrency=", Integer.valueOf(this.concurrency));
    }
}

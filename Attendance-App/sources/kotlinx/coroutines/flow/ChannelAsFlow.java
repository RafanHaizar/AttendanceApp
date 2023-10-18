package kotlinx.coroutines.flow;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.SendingCollector;

@Metadata(mo112d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u001aB;\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u000eH\u0014¢\u0006\u0004\b\u000f\u0010\u0010J!\u0010\u0014\u001a\u00020\u00132\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H@ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J!\u0010\u0018\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0016H@ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019J-\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001a2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u0015\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dH\u0016¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0013H\u0002¢\u0006\u0004\b \u0010!J\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0017\u001a\u00020\"H\u0016¢\u0006\u0004\b#\u0010$R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010%R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010&\u0002\u0004\n\u0002\b\u0019¨\u0006'"}, mo113d2 = {"Lkotlinx/coroutines/flow/ChannelAsFlow;", "T", "Lkotlinx/coroutines/channels/ReceiveChannel;", "channel", "", "consume", "Lkotlin/coroutines/CoroutineContext;", "context", "", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "<init>", "(Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "", "additionalToStringProps", "()Ljava/lang/String;", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "", "collect", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ProducerScope;", "scope", "collectTo", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "create", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "dropChannelOperators", "()Lkotlinx/coroutines/flow/Flow;", "markConsumed", "()V", "Lkotlinx/coroutines/CoroutineScope;", "produceImpl", "(Lkotlinx/coroutines/CoroutineScope;)Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Z", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Channels.kt */
final class ChannelAsFlow<T> extends ChannelFlow<T> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater consumed$FU = AtomicIntegerFieldUpdater.newUpdater(ChannelAsFlow.class, "consumed");
    private final ReceiveChannel<T> channel;
    private final boolean consume;
    private volatile /* synthetic */ int consumed;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ ChannelAsFlow(kotlinx.coroutines.channels.ReceiveChannel r7, boolean r8, kotlin.coroutines.CoroutineContext r9, int r10, kotlinx.coroutines.channels.BufferOverflow r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
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
            r10 = -3
            r4 = -3
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
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.ChannelAsFlow.<init>(kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.CoroutineContext, int, kotlinx.coroutines.channels.BufferOverflow, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public ChannelAsFlow(ReceiveChannel<? extends T> channel2, boolean consume2, CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        super(context, capacity, onBufferOverflow);
        this.channel = channel2;
        this.consume = consume2;
        this.consumed = 0;
    }

    private final void markConsumed() {
        if (this.consume) {
            boolean z = true;
            if (consumed$FU.getAndSet(this, 1) != 0) {
                z = false;
            }
            if (!z) {
                throw new IllegalStateException("ReceiveChannel.consumeAsFlow can be collected just once".toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public ChannelFlow<T> create(CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        return new ChannelAsFlow<>(this.channel, this.consume, context, capacity, onBufferOverflow);
    }

    public Flow<T> dropChannelOperators() {
        return new ChannelAsFlow<>(this.channel, this.consume, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    public Object collectTo(ProducerScope<? super T> scope, Continuation<? super Unit> $completion) {
        Object access$emitAllImpl$FlowKt__ChannelsKt = FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(new SendingCollector(scope), this.channel, this.consume, $completion);
        return access$emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? access$emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    public ReceiveChannel<T> produceImpl(CoroutineScope scope) {
        markConsumed();
        if (this.capacity == -3) {
            return this.channel;
        }
        return super.produceImpl(scope);
    }

    public Object collect(FlowCollector<? super T> collector, Continuation<? super Unit> $completion) {
        if (this.capacity == -3) {
            markConsumed();
            Object access$emitAllImpl$FlowKt__ChannelsKt = FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(collector, this.channel, this.consume, $completion);
            return access$emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? access$emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
        }
        Object collect = super.collect(collector, $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public String additionalToStringProps() {
        return Intrinsics.stringPlus("channel=", this.channel);
    }
}

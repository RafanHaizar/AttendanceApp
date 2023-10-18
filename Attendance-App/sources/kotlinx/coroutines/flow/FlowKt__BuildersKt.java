package kotlinx.coroutines.flow;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo112d1 = {"\u0000f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\u001c\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aK\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001aK\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\u0012\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001aK\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0010\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0011\u001a+\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0013\"\u0002H\u0002¢\u0006\u0002\u0010\u0014\u001a\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0016H\u0007\u001a!\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0013¢\u0006\u0002\u0010\u0014\u001a\u0010\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0001*\u00020\u0018\u001a\u0010\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00190\u0001*\u00020\u001a\u001a\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001b\u001a\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001c\u001a\u0010\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0001*\u00020\u001d\u001a\u0010\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00190\u0001*\u00020\u001e\u001a\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001f\u001a6\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0 H\u0007ø\u0001\u0000¢\u0006\u0002\u0010!\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, mo113d2 = {"callbackFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "channelFlow", "emptyFlow", "flow", "Lkotlinx/coroutines/flow/FlowCollector;", "flowOf", "value", "(Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "elements", "", "([Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "asFlow", "Lkotlin/Function0;", "", "", "", "", "", "", "Lkotlin/ranges/IntRange;", "Lkotlin/ranges/LongRange;", "Lkotlin/sequences/Sequence;", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Builders.kt */
final /* synthetic */ class FlowKt__BuildersKt {
    public static final <T> Flow<T> flow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return new SafeFlow<>(block);
    }

    public static final <T> Flow<T> asFlow(Function0<? extends T> $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$1($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Function1<? super Continuation<? super T>, ? extends Object> $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$2($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Iterable<? extends T> $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Iterator<? extends T> $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$4($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Sequence<? extends T> $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5($this$asFlow);
    }

    public static final <T> Flow<T> flowOf(T... elements) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(elements);
    }

    public static final <T> Flow<T> flowOf(T value) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(value);
    }

    public static final <T> Flow<T> emptyFlow() {
        return EmptyFlow.INSTANCE;
    }

    public static final <T> Flow<T> asFlow(T[] $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6($this$asFlow);
    }

    public static final Flow<Integer> asFlow(int[] $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7($this$asFlow);
    }

    public static final Flow<Long> asFlow(long[] $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8($this$asFlow);
    }

    public static final Flow<Integer> asFlow(IntRange $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$9($this$asFlow);
    }

    public static final Flow<Long> asFlow(LongRange $this$asFlow) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10($this$asFlow);
    }

    public static final <T> Flow<T> channelFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return new ChannelFlowBuilder<>(block, (CoroutineContext) null, 0, (BufferOverflow) null, 14, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> callbackFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return new CallbackFlowBuilder<>(block, (CoroutineContext) null, 0, (BufferOverflow) null, 14, (DefaultConstructorMarker) null);
    }
}

package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(mo112d1 = {"\u00000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a[\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00072\"\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\tH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00010\r\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u000eH\u0000\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0002\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, mo113d2 = {"withContextUndispatched", "T", "V", "newContext", "Lkotlin/coroutines/CoroutineContext;", "value", "countOrElement", "", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asChannelFlow", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "withUndispatchedContextCollector", "Lkotlinx/coroutines/flow/FlowCollector;", "emitContext", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ChannelFlow.kt */
public final class ChannelFlowKt {
    public static final <T> ChannelFlow<T> asChannelFlow(Flow<? extends T> $this$asChannelFlow) {
        ChannelFlow<T> channelFlow = $this$asChannelFlow instanceof ChannelFlow ? (ChannelFlow) $this$asChannelFlow : null;
        return channelFlow == null ? new ChannelFlowOperatorImpl<>($this$asChannelFlow, (CoroutineContext) null, 0, (BufferOverflow) null, 14, (DefaultConstructorMarker) null) : channelFlow;
    }

    /* access modifiers changed from: private */
    public static final <T> FlowCollector<T> withUndispatchedContextCollector(FlowCollector<? super T> $this$withUndispatchedContextCollector, CoroutineContext emitContext) {
        if ($this$withUndispatchedContextCollector instanceof SendingCollector ? true : $this$withUndispatchedContextCollector instanceof NopCollector) {
            return $this$withUndispatchedContextCollector;
        }
        return new UndispatchedContextCollector<>($this$withUndispatchedContextCollector, emitContext);
    }

    public static /* synthetic */ Object withContextUndispatched$default(CoroutineContext coroutineContext, Object obj, Object obj2, Function2 function2, Continuation continuation, int i, Object obj3) {
        if ((i & 4) != 0) {
            obj2 = ThreadContextKt.threadContextElements(coroutineContext);
        }
        return withContextUndispatched(coroutineContext, obj, obj2, function2, continuation);
    }

    /* JADX INFO: finally extract failed */
    public static final <T, V> Object withContextUndispatched(CoroutineContext newContext, V value, Object countOrElement, Function2<? super V, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        Continuation uCont = $completion;
        Object oldValue$iv = ThreadContextKt.updateThreadContext(newContext, countOrElement);
        try {
            Object invoke = ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(block, 2)).invoke(value, new StackFrameContinuation(uCont, newContext));
            ThreadContextKt.restoreThreadContext(newContext, oldValue$iv);
            if (invoke == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return invoke;
        } catch (Throwable th) {
            ThreadContextKt.restoreThreadContext(newContext, oldValue$iv);
            throw th;
        }
    }
}

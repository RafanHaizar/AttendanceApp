package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(mo112d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2-\b\u0002\u0010\n\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000bj\u0004\u0018\u0001`\u00112/\b\u0001\u0010\u0012\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0013¢\u0006\u0002\b\u0017H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00192\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, mo113d2 = {"broadcast", "Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Broadcast.kt */
public final class BroadcastKt {
    public static /* synthetic */ BroadcastChannel broadcast$default(ReceiveChannel receiveChannel, int i, CoroutineStart coroutineStart, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        if ((i2 & 2) != 0) {
            coroutineStart = CoroutineStart.LAZY;
        }
        return broadcast(receiveChannel, i, coroutineStart);
    }

    public static final <E> BroadcastChannel<E> broadcast(ReceiveChannel<? extends E> $this$broadcast, int capacity, CoroutineStart start) {
        return broadcast$default(CoroutineScopeKt.plus(CoroutineScopeKt.plus(GlobalScope.INSTANCE, Dispatchers.getUnconfined()), new BroadcastKt$broadcast$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key)), (CoroutineContext) null, capacity, start, new BroadcastKt$broadcast$1($this$broadcast), new BroadcastKt$broadcast$2($this$broadcast, (Continuation<? super BroadcastKt$broadcast$2>) null), 1, (Object) null);
    }

    public static /* synthetic */ BroadcastChannel broadcast$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, int i, CoroutineStart coroutineStart, Function1 function1, Function2 function2, int i2, Object obj) {
        CoroutineContext coroutineContext2;
        int i3;
        CoroutineStart coroutineStart2;
        Function1 function12;
        if ((i2 & 1) != 0) {
            coroutineContext2 = EmptyCoroutineContext.INSTANCE;
        } else {
            coroutineContext2 = coroutineContext;
        }
        if ((i2 & 2) != 0) {
            i3 = 1;
        } else {
            i3 = i;
        }
        if ((i2 & 4) != 0) {
            coroutineStart2 = CoroutineStart.LAZY;
        } else {
            coroutineStart2 = coroutineStart;
        }
        if ((i2 & 8) != 0) {
            function12 = null;
        } else {
            function12 = function1;
        }
        return broadcast(coroutineScope, coroutineContext2, i3, coroutineStart2, function12, function2);
    }

    public static final <E> BroadcastChannel<E> broadcast(CoroutineScope $this$broadcast, CoroutineContext context, int capacity, CoroutineStart start, Function1<? super Throwable, Unit> onCompletion, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> block) {
        BroadcastCoroutine coroutine;
        CoroutineContext newContext = CoroutineContextKt.newCoroutineContext($this$broadcast, context);
        BroadcastChannel channel = BroadcastChannelKt.BroadcastChannel(capacity);
        if (start.isLazy()) {
            coroutine = new LazyBroadcastCoroutine(newContext, channel, block);
        } else {
            coroutine = new BroadcastCoroutine(newContext, channel, true);
        }
        if (onCompletion != null) {
            coroutine.invokeOnCompletion(onCompletion);
        }
        coroutine.start(start, coroutine, block);
        return coroutine;
    }
}

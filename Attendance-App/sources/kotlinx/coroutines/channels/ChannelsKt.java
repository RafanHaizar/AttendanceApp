package kotlinx.coroutines.channels;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(mo112d1 = {"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt", "kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt"}, mo114k = 4, mo115mv = {1, 6, 0}, mo117xi = 48)
public final class ChannelsKt {
    public static final String DEFAULT_CLOSE_MESSAGE = "Channel was closed";

    public static final void cancelConsumed(ReceiveChannel<?> $this$cancelConsumed, Throwable cause) {
        ChannelsKt__Channels_commonKt.cancelConsumed($this$cancelConsumed, cause);
    }

    public static final <E, R> R consume(BroadcastChannel<E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        return ChannelsKt__Channels_commonKt.consume($this$consume, block);
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        return ChannelsKt__Channels_commonKt.consume($this$consume, block);
    }

    public static final <E> Object consumeEach(BroadcastChannel<E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, $completion);
    }

    public static final <E> Object consumeEach(ReceiveChannel<? extends E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, $completion);
    }

    public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> $this$consumes) {
        return ChannelsKt__DeprecatedKt.consumes($this$consumes);
    }

    public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... channels) {
        return ChannelsKt__DeprecatedKt.consumesAll(channels);
    }

    public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> $this$distinctBy, CoroutineContext context, Function2<? super E, ? super Continuation<? super K>, ? extends Object> selector) {
        return ChannelsKt__DeprecatedKt.distinctBy($this$distinctBy, context, selector);
    }

    public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> $this$filter, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__DeprecatedKt.filter($this$filter, context, predicate);
    }

    public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> $this$filterNotNull) {
        return ChannelsKt__DeprecatedKt.filterNotNull($this$filterNotNull);
    }

    public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> $this$map, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__DeprecatedKt.map($this$map, context, transform);
    }

    public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> $this$mapIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__DeprecatedKt.mapIndexed($this$mapIndexed, context, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'onReceiveCatching'")
    public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> $this$onReceiveOrNull) {
        return ChannelsKt__Channels_commonKt.onReceiveOrNull($this$onReceiveOrNull);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> $this$receiveOrNull, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.receiveOrNull($this$receiveOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySendBlocking'. Consider handling the result of 'trySendBlocking' explicitly and rethrow exception if necessary", replaceWith = @ReplaceWith(expression = "trySendBlocking(element)", imports = {}))
    public static final <E> void sendBlocking(SendChannel<? super E> $this$sendBlocking, E element) {
        ChannelsKt__ChannelsKt.sendBlocking($this$sendBlocking, element);
    }

    public static final <E, C extends SendChannel<? super E>> Object toChannel(ReceiveChannel<? extends E> $this$toChannel, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__DeprecatedKt.toChannel($this$toChannel, destination, $completion);
    }

    public static final <E, C extends Collection<? super E>> Object toCollection(ReceiveChannel<? extends E> $this$toCollection, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__DeprecatedKt.toCollection($this$toCollection, destination, $completion);
    }

    public static final <E> Object toList(ReceiveChannel<? extends E> $this$toList, Continuation<? super List<? extends E>> $completion) {
        return ChannelsKt__Channels_commonKt.toList($this$toList, $completion);
    }

    public static final <K, V, M extends Map<? super K, ? super V>> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> $this$toMap, M destination, Continuation<? super M> $completion) {
        return ChannelsKt__DeprecatedKt.toMap($this$toMap, destination, $completion);
    }

    public static final <E> Object toMutableSet(ReceiveChannel<? extends E> $this$toMutableSet, Continuation<? super Set<E>> $completion) {
        return ChannelsKt__DeprecatedKt.toMutableSet($this$toMutableSet, $completion);
    }

    public static final <E> Object trySendBlocking(SendChannel<? super E> $this$trySendBlocking, E element) {
        return ChannelsKt__ChannelsKt.trySendBlocking($this$trySendBlocking, element);
    }

    public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other, CoroutineContext context, Function2<? super E, ? super R, ? extends V> transform) {
        return ChannelsKt__DeprecatedKt.zip($this$zip, other, context, transform);
    }
}

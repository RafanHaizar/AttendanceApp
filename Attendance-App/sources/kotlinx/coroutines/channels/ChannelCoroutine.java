package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(mo112d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0004B+\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\b\u0010\"\u001a\u00020\u0003H\u0016J\u0012\u0010\"\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$H\u0007J\u0016\u0010\"\u001a\u00020\u00032\u000e\u0010#\u001a\n\u0018\u00010%j\u0004\u0018\u0001`&J\u0010\u0010'\u001a\u00020\u00032\u0006\u0010#\u001a\u00020$H\u0016J\u0013\u0010(\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$H\u0001J.\u0010)\u001a\u00020\u00032#\u0010*\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010$¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00030+H\u0001J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000/H\u0003J\u0016\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00028\u0000H\u0001¢\u0006\u0002\u00102J\u0010\u00103\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0002\u00104J\u0011\u00105\u001a\u00028\u0000HAø\u0001\u0000¢\u0006\u0002\u00106J\"\u00107\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019HAø\u0001\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b8\u00106J\u0013\u00109\u001a\u0004\u0018\u00018\u0000HAø\u0001\u0000¢\u0006\u0002\u00106J\u0019\u0010:\u001a\u00020\u00032\u0006\u00101\u001a\u00028\u0000HAø\u0001\u0000¢\u0006\u0002\u0010;J\u001f\u0010<\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H\u0001ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b=\u00104J'\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00030\u00192\u0006\u00101\u001a\u00028\u0000H\u0001ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b?\u0010@R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015X\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R!\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00190\u0015X\u0005ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u001c\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00158VX\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R$\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001f0\u001eX\u0005¢\u0006\u0006\u001a\u0004\b \u0010!\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006A"}, mo113d2 = {"Lkotlinx/coroutines/channels/ChannelCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/Channel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "initParentJob", "", "active", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;ZZ)V", "get_channel", "()Lkotlinx/coroutines/channels/Channel;", "channel", "getChannel", "isClosedForReceive", "()Z", "isClosedForSend", "isEmpty", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "offer", "element", "(Ljava/lang/Object;)Z", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveOrNull", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryReceive", "tryReceive-PtdJZtk", "trySend", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ChannelCoroutine.kt */
public class ChannelCoroutine<E> extends AbstractCoroutine<Unit> implements Channel<E> {
    private final Channel<E> _channel;

    public boolean close(Throwable th) {
        return this._channel.close(th);
    }

    public SelectClause1<E> getOnReceive() {
        return this._channel.getOnReceive();
    }

    public SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        return this._channel.getOnReceiveCatching();
    }

    public SelectClause1<E> getOnReceiveOrNull() {
        return this._channel.getOnReceiveOrNull();
    }

    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return this._channel.getOnSend();
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> function1) {
        this._channel.invokeOnClose(function1);
    }

    public boolean isClosedForReceive() {
        return this._channel.isClosedForReceive();
    }

    public boolean isClosedForSend() {
        return this._channel.isClosedForSend();
    }

    public boolean isEmpty() {
        return this._channel.isEmpty();
    }

    public ChannelIterator<E> iterator() {
        return this._channel.iterator();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e) {
        return this._channel.offer(e);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    public E poll() {
        return this._channel.poll();
    }

    public Object receive(Continuation<? super E> continuation) {
        return this._channel.receive(continuation);
    }

    /* renamed from: receiveCatching-JP2dKIU  reason: not valid java name */
    public Object m1243receiveCatchingJP2dKIU(Continuation<? super ChannelResult<? extends E>> continuation) {
        Object r0 = this._channel.m1251receiveCatchingJP2dKIU(continuation);
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        return r0;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public Object receiveOrNull(Continuation<? super E> continuation) {
        return this._channel.receiveOrNull(continuation);
    }

    public Object send(E e, Continuation<? super Unit> continuation) {
        return this._channel.send(e, continuation);
    }

    /* renamed from: tryReceive-PtdJZtk  reason: not valid java name */
    public Object m1244tryReceivePtdJZtk() {
        return this._channel.m1252tryReceivePtdJZtk();
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1245trySendJP2dKIU(E e) {
        return this._channel.m1253trySendJP2dKIU(e);
    }

    /* access modifiers changed from: protected */
    public final Channel<E> get_channel() {
        return this._channel;
    }

    public ChannelCoroutine(CoroutineContext parentContext, Channel<E> _channel2, boolean initParentJob, boolean active) {
        super(parentContext, initParentJob, active);
        this._channel = _channel2;
    }

    public final Channel<E> getChannel() {
        return this;
    }

    public /* synthetic */ void cancel() {
        cancelInternal(new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public final /* synthetic */ boolean cancel(Throwable cause) {
        cancelInternal(new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this));
        return true;
    }

    public final void cancel(CancellationException cause) {
        CancellationException cancellationException;
        if (!isCancelled()) {
            if (cause == null) {
                cancellationException = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
            } else {
                cancellationException = cause;
            }
            cancelInternal(cancellationException);
        }
    }

    public void cancelInternal(Throwable cause) {
        CancellationException exception = JobSupport.toCancellationException$default(this, cause, (String) null, 1, (Object) null);
        this._channel.cancel(exception);
        cancelCoroutine(exception);
    }
}

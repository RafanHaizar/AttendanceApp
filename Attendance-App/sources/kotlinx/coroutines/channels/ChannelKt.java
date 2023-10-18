package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.ChannelResult;

@Metadata(mo112d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0007\u001a>\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u001aX\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\r\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u000b0\bH\bø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a^\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\u0015\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\t0\bH\bø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0016\u0010\u0013\u001a^\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\u0015\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\t0\bH\bø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0017\u0010\u0013\u001a\\\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\t0\bH\bø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u001a\u0010\u0013\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001b"}, mo113d2 = {"Channel", "Lkotlinx/coroutines/channels/Channel;", "E", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "onUndeliveredElement", "Lkotlin/Function1;", "", "getOrElse", "T", "Lkotlinx/coroutines/channels/ChannelResult;", "onFailure", "", "Lkotlin/ParameterName;", "name", "exception", "getOrElse-WpGqRn0", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onClosed", "action", "onClosed-WpGqRn0", "onFailure-WpGqRn0", "onSuccess", "value", "onSuccess-WpGqRn0", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Channel.kt */
public final class ChannelKt {
    /* renamed from: getOrElse-WpGqRn0  reason: not valid java name */
    public static final <T> T m1246getOrElseWpGqRn0(Object $this$getOrElse, Function1<? super Throwable, ? extends T> onFailure) {
        return $this$getOrElse instanceof ChannelResult.Failed ? onFailure.invoke(ChannelResult.m1263exceptionOrNullimpl($this$getOrElse)) : $this$getOrElse;
    }

    /* renamed from: onSuccess-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1249onSuccessWpGqRn0(Object $this$onSuccess, Function1<? super T, Unit> action) {
        if (!($this$onSuccess instanceof ChannelResult.Failed)) {
            action.invoke($this$onSuccess);
        }
        return $this$onSuccess;
    }

    /* renamed from: onFailure-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1248onFailureWpGqRn0(Object $this$onFailure, Function1<? super Throwable, Unit> action) {
        if ($this$onFailure instanceof ChannelResult.Failed) {
            action.invoke(ChannelResult.m1263exceptionOrNullimpl($this$onFailure));
        }
        return $this$onFailure;
    }

    /* renamed from: onClosed-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1247onClosedWpGqRn0(Object $this$onClosed, Function1<? super Throwable, Unit> action) {
        if ($this$onClosed instanceof ChannelResult.Closed) {
            action.invoke(ChannelResult.m1263exceptionOrNullimpl($this$onClosed));
        }
        return $this$onClosed;
    }

    public static /* synthetic */ Channel Channel$default(int i, BufferOverflow bufferOverflow, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        return Channel(i, bufferOverflow, function1);
    }

    public static final <E> Channel<E> Channel(int capacity, BufferOverflow onBufferOverflow, Function1<? super E, Unit> onUndeliveredElement) {
        AbstractChannel abstractChannel;
        AbstractChannel abstractChannel2;
        int i = 1;
        switch (capacity) {
            case -2:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    i = Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core();
                }
                return new ArrayChannel<>(i, onBufferOverflow, onUndeliveredElement);
            case -1:
                if (onBufferOverflow != BufferOverflow.SUSPEND) {
                    i = 0;
                }
                if (i != 0) {
                    return new ConflatedChannel<>(onUndeliveredElement);
                }
                throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
            case 0:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    abstractChannel = new RendezvousChannel(onUndeliveredElement);
                } else {
                    abstractChannel = new ArrayChannel(1, onBufferOverflow, onUndeliveredElement);
                }
                return abstractChannel;
            case Integer.MAX_VALUE:
                return new LinkedListChannel<>(onUndeliveredElement);
            default:
                if (capacity == 1 && onBufferOverflow == BufferOverflow.DROP_OLDEST) {
                    abstractChannel2 = new ConflatedChannel(onUndeliveredElement);
                } else {
                    abstractChannel2 = new ArrayChannel(capacity, onBufferOverflow, onUndeliveredElement);
                }
                return abstractChannel2;
        }
    }

    public static /* synthetic */ Channel Channel$default(int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return Channel$default(i, (BufferOverflow) null, (Function1) null, 6, (Object) null);
    }
}

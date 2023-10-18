package kotlinx.coroutines.channels;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo112d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\b\u0007\u0018\u0000 B*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000G:\u0004CBDEB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00028\u0000¢\u0006\u0004\b\u0003\u0010\u0004B\u0007¢\u0006\u0004\b\u0003\u0010\u0005J?\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00062\u0014\u0010\b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0017¢\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u000f\u001a\u00020\u00132\u000e\u0010\r\u001a\n\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012H\u0016¢\u0006\u0004\b\u000f\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0004\b\u0015\u0010\u0010J\u001d\u0010\u0016\u001a\u00020\u00132\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J)\u0010\u001b\u001a\u00020\u00132\u0018\u0010\u001a\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00130\u0018j\u0002`\u0019H\u0016¢\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u00132\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u0019\u0010!\u001a\u0004\u0018\u00010 2\u0006\u0010\u001f\u001a\u00028\u0000H\u0002¢\u0006\u0004\b!\u0010\"J\u0015\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016¢\u0006\u0004\b$\u0010%JX\u0010.\u001a\u00020\u0013\"\u0004\b\u0001\u0010&2\f\u0010(\u001a\b\u0012\u0004\u0012\u00028\u00010'2\u0006\u0010\u001f\u001a\u00028\u00002(\u0010-\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000*\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010+\u0012\u0006\u0012\u0004\u0018\u00010,0)H\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010/J?\u00100\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u00062\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b0\u0010\u000bJ\u001b\u00101\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b1\u00102J&\u00106\u001a\b\u0012\u0004\u0012\u00020\u0013032\u0006\u0010\u001f\u001a\u00028\u0000H\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b4\u00105R\u0014\u00107\u001a\u00020\u000e8VX\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R&\u0010<\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000*098VX\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0017\u0010\u0002\u001a\u00028\u00008F¢\u0006\f\u0012\u0004\b?\u0010\u0005\u001a\u0004\b=\u0010>R\u0013\u0010A\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b@\u0010>\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006F"}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "E", "value", "<init>", "(Ljava/lang/Object;)V", "()V", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "list", "subscriber", "addSubscriber", "([Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "close", "closeSubscriber", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "element", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "offerInternal", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "removeSubscriber", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "isClosedForSend", "()Z", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "getValue", "()Ljava/lang/Object;", "getValue$annotations", "getValueOrNull", "valueOrNull", "Companion", "Closed", "State", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConflatedBroadcastChannel.kt */
public final class ConflatedBroadcastChannel<E> implements BroadcastChannel<E> {
    @Deprecated
    private static final Closed CLOSED = new Closed((Throwable) null);
    private static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    @Deprecated
    private static final State<Object> INITIAL_STATE;
    @Deprecated
    private static final Symbol UNDEFINED;
    private static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU;
    private static final /* synthetic */ AtomicIntegerFieldUpdater _updating$FU;
    private static final /* synthetic */ AtomicReferenceFieldUpdater onCloseHandler$FU;
    private volatile /* synthetic */ Object _state;
    private volatile /* synthetic */ int _updating;
    private volatile /* synthetic */ Object onCloseHandler;

    public static /* synthetic */ void getValue$annotations() {
    }

    public ConflatedBroadcastChannel() {
        this._state = INITIAL_STATE;
        this._updating = 0;
        this.onCloseHandler = null;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E element) {
        return BroadcastChannel.DefaultImpls.offer(this, element);
    }

    public ConflatedBroadcastChannel(E value) {
        this();
        _state$FU.lazySet(this, new State(value, (Subscriber<E>[]) null));
    }

    @Metadata(mo112d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Companion;", "", "()V", "CLOSED", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "INITIAL_STATE", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Symbol symbol = new Symbol("UNDEFINED");
        UNDEFINED = symbol;
        INITIAL_STATE = new State<>(symbol, (Subscriber<E>[]) null);
        Class<ConflatedBroadcastChannel> cls = ConflatedBroadcastChannel.class;
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state");
        _updating$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_updating");
        onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "onCloseHandler");
    }

    @Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0014\u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R \u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "E", "", "value", "subscribers", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "(Ljava/lang/Object;[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class State<E> {
        public final Subscriber<E>[] subscribers;
        public final Object value;

        public State(Object value2, Subscriber<E>[] subscribers2) {
            this.value = value2;
            this.subscribers = subscribers2;
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "", "closeCause", "", "(Ljava/lang/Throwable;)V", "sendException", "getSendException", "()Ljava/lang/Throwable;", "valueException", "getValueException", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Closed {
        public final Throwable closeCause;

        public Closed(Throwable closeCause2) {
            this.closeCause = closeCause2;
        }

        public final Throwable getSendException() {
            Throwable th = this.closeCause;
            return th == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : th;
        }

        public final Throwable getValueException() {
            Throwable th = this.closeCause;
            return th == null ? new IllegalStateException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : th;
        }
    }

    public final E getValue() {
        Object state = this._state;
        if (state instanceof Closed) {
            throw ((Closed) state).getValueException();
        } else if (!(state instanceof State)) {
            throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", state).toString());
        } else if (((State) state).value != UNDEFINED) {
            return ((State) state).value;
        } else {
            throw new IllegalStateException("No value");
        }
    }

    public final E getValueOrNull() {
        Object state = this._state;
        if (state instanceof Closed) {
            return null;
        }
        if (state instanceof State) {
            Object this_$iv = UNDEFINED;
            Object value$iv = ((State) state).value;
            if (value$iv == this_$iv) {
                return null;
            }
            return value$iv;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", state).toString());
    }

    public boolean isClosedForSend() {
        return this._state instanceof Closed;
    }

    public ReceiveChannel<E> openSubscription() {
        Object state;
        Subscriber subscriber = new Subscriber(this);
        do {
            state = this._state;
            if (state instanceof Closed) {
                subscriber.close(((Closed) state).closeCause);
                return subscriber;
            } else if (state instanceof State) {
                if (((State) state).value != UNDEFINED) {
                    subscriber.offerInternal(((State) state).value);
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", state).toString());
            }
        } while (!C0613xc40028dd.m151m(_state$FU, this, state, new State(((State) state).value, addSubscriber(((State) state).subscribers, subscriber))));
        return subscriber;
    }

    /* access modifiers changed from: private */
    public final void closeSubscriber(Subscriber<E> subscriber) {
        Object state;
        Object obj;
        Subscriber<E>[] subscriberArr;
        do {
            state = this._state;
            if (!(state instanceof Closed)) {
                if (state instanceof State) {
                    obj = ((State) state).value;
                    subscriberArr = ((State) state).subscribers;
                    Intrinsics.checkNotNull(subscriberArr);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", state).toString());
                }
            } else {
                return;
            }
        } while (!C0613xc40028dd.m151m(_state$FU, this, state, new State(obj, removeSubscriber(subscriberArr, subscriber))));
    }

    private final Subscriber<E>[] addSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        if (list != null) {
            return (Subscriber[]) ArraysKt.plus((T[]) list, subscriber);
        }
        Subscriber<E>[] subscriberArr = new Subscriber[1];
        for (int i = 0; i < 1; i++) {
            subscriberArr[i] = subscriber;
        }
        return subscriberArr;
    }

    private final Subscriber<E>[] removeSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        int n = list.length;
        int i = ArraysKt.indexOf((T[]) list, subscriber);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(i >= 0)) {
                throw new AssertionError();
            }
        }
        if (n == 1) {
            return null;
        }
        Subscriber<E>[] subscriberArr = new Subscriber[(n - 1)];
        Subscriber<E>[] subscriberArr2 = list;
        Subscriber<E>[] subscriberArr3 = subscriberArr;
        ArraysKt.copyInto$default((Object[]) subscriberArr2, (Object[]) subscriberArr3, 0, 0, i, 6, (Object) null);
        ArraysKt.copyInto$default((Object[]) subscriberArr2, (Object[]) subscriberArr3, i, i + 1, 0, 8, (Object) null);
        return subscriberArr;
    }

    /* renamed from: close */
    public boolean cancel(Throwable cause) {
        Object state;
        int i;
        do {
            state = this._state;
            i = 0;
            if (state instanceof Closed) {
                return false;
            }
            if (!(state instanceof State)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", state).toString());
            }
        } while (!C0613xc40028dd.m151m(_state$FU, this, state, cause == null ? CLOSED : new Closed(cause)));
        Subscriber[] subscriberArr = ((State) state).subscribers;
        if (subscriberArr != null) {
            int length = subscriberArr.length;
            while (i < length) {
                Subscriber it = subscriberArr[i];
                i++;
                it.close(cause);
            }
        }
        invokeOnCloseHandler(cause);
        return true;
    }

    private final void invokeOnCloseHandler(Throwable cause) {
        Object handler = this.onCloseHandler;
        if (handler != null && handler != AbstractChannelKt.HANDLER_INVOKED && C0613xc40028dd.m151m(onCloseHandler$FU, this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(handler, 1)).invoke(cause);
        }
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = onCloseHandler$FU;
        if (!C0613xc40028dd.m151m(atomicReferenceFieldUpdater, this, (Object) null, handler)) {
            Object value = this.onCloseHandler;
            if (value == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Another handler was already registered: ", value));
        }
        Object state = this._state;
        if ((state instanceof Closed) && C0613xc40028dd.m151m(atomicReferenceFieldUpdater, this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            handler.invoke(((Closed) state).closeCause);
        }
    }

    public void cancel(CancellationException cause) {
        cancel(cause);
    }

    public Object send(E element, Continuation<? super Unit> $completion) {
        Closed it = offerInternal(element);
        if (it != null) {
            throw it.getSendException();
        } else if (IntrinsicsKt.getCOROUTINE_SUSPENDED() == null) {
            return null;
        } else {
            return Unit.INSTANCE;
        }
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1250trySendJP2dKIU(E element) {
        Closed it = offerInternal(element);
        if (it == null) {
            return ChannelResult.Companion.m1274successJP2dKIU(Unit.INSTANCE);
        }
        return ChannelResult.Companion.m1272closedJP2dKIU(it.getSendException());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: kotlinx.coroutines.channels.ConflatedBroadcastChannel$Closed} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: kotlinx.coroutines.channels.ConflatedBroadcastChannel$State} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: kotlinx.coroutines.channels.ConflatedBroadcastChannel$State} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlinx.coroutines.channels.ConflatedBroadcastChannel.Closed offerInternal(E r14) {
        /*
            r13 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = _updating$FU
            r1 = 1
            r2 = 0
            boolean r0 = r0.compareAndSet(r13, r2, r1)
            r1 = 0
            if (r0 != 0) goto L_0x000c
            return r1
        L_0x000c:
            r0 = r13
            r3 = 0
        L_0x000f:
            java.lang.Object r4 = r0._state     // Catch:{ all -> 0x0061 }
            r5 = 0
            boolean r6 = r4 instanceof kotlinx.coroutines.channels.ConflatedBroadcastChannel.Closed     // Catch:{ all -> 0x0061 }
            if (r6 == 0) goto L_0x001e
            r1 = r4
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$Closed r1 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel.Closed) r1     // Catch:{ all -> 0x0061 }
        L_0x001b:
            r13._updating = r2
            return r1
        L_0x001e:
            boolean r6 = r4 instanceof kotlinx.coroutines.channels.ConflatedBroadcastChannel.State     // Catch:{ all -> 0x0061 }
            if (r6 == 0) goto L_0x0051
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$State r6 = new kotlinx.coroutines.channels.ConflatedBroadcastChannel$State     // Catch:{ all -> 0x0061 }
            r7 = r4
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$State r7 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel.State) r7     // Catch:{ all -> 0x0061 }
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$Subscriber<E>[] r7 = r7.subscribers     // Catch:{ all -> 0x0061 }
            r6.<init>(r14, r7)     // Catch:{ all -> 0x0061 }
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = _state$FU     // Catch:{ all -> 0x0061 }
            boolean r7 = androidx.concurrent.futures.C0613xc40028dd.m151m(r7, r13, r4, r6)     // Catch:{ all -> 0x0061 }
            if (r7 == 0) goto L_0x004e
            r0 = r4
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$State r0 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel.State) r0     // Catch:{ all -> 0x0061 }
            kotlinx.coroutines.channels.ConflatedBroadcastChannel$Subscriber<E>[] r0 = r0.subscribers     // Catch:{ all -> 0x0061 }
            if (r0 != 0) goto L_0x003c
            goto L_0x004c
        L_0x003c:
            r7 = 0
            int r8 = r0.length     // Catch:{ all -> 0x0061 }
            r9 = 0
        L_0x003f:
            if (r9 >= r8) goto L_0x004b
            r10 = r0[r9]     // Catch:{ all -> 0x0061 }
            int r9 = r9 + 1
            r11 = r10
            r12 = 0
            r11.offerInternal(r14)     // Catch:{ all -> 0x0061 }
            goto L_0x003f
        L_0x004b:
        L_0x004c:
            goto L_0x001b
        L_0x004e:
            goto L_0x000f
        L_0x0051:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0061 }
            java.lang.String r1 = "Invalid state "
            java.lang.String r1 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r4)     // Catch:{ all -> 0x0061 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0061 }
            r0.<init>(r1)     // Catch:{ all -> 0x0061 }
            throw r0     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r0 = move-exception
            r13._updating = r2
            goto L_0x0066
        L_0x0065:
            throw r0
        L_0x0066:
            goto L_0x0065
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ConflatedBroadcastChannel.offerInternal(java.lang.Object):kotlinx.coroutines.channels.ConflatedBroadcastChannel$Closed");
    }

    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return new ConflatedBroadcastChannel$onSend$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        if (select.trySelect()) {
            Closed it = offerInternal(element);
            if (it == null) {
                UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
            } else {
                select.resumeSelectWithException(it.getSendException());
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ConflatedChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "onCancelIdempotent", "", "wasClosed", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Subscriber<E> extends ConflatedChannel<E> implements ReceiveChannel<E> {
        private final ConflatedBroadcastChannel<E> broadcastChannel;

        public Subscriber(ConflatedBroadcastChannel<E> broadcastChannel2) {
            super((Function1) null);
            this.broadcastChannel = broadcastChannel2;
        }

        /* access modifiers changed from: protected */
        public void onCancelIdempotent(boolean wasClosed) {
            if (wasClosed) {
                this.broadcastChannel.closeSubscriber(this);
            }
        }

        public Object offerInternal(E element) {
            return super.offerInternal(element);
        }
    }
}

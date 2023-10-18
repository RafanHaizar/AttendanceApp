package kotlinx.coroutines.channels;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo112d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u000006:\u0004defgB)\u0012 \u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0013\u001a\u000e\u0012\u0002\b\u00030\u0011j\u0006\u0012\u0002\b\u0003`\u00122\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u0018H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u001b\u0010\u001f\u001a\u00020\u00032\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010!\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010\"J\u001b\u0010!\u001a\u00020\b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010#J)\u0010&\u001a\u00020\u00032\u0018\u0010%\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00030\u0002j\u0002`$H\u0016¢\u0006\u0004\b&\u0010\u0007J\u0019\u0010'\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0004\b+\u0010,J#\u0010/\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010.\u001a\u0006\u0012\u0002\b\u00030-H\u0014¢\u0006\u0004\b/\u00100J\u0017\u00102\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u000201H\u0014¢\u0006\u0004\b2\u00103JX\u00109\u001a\u00020\u0003\"\u0004\b\u0001\u001042\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010\u0010\u001a\u00028\u00002(\u00108\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000107\u0012\u0006\u0012\u0004\u0018\u00010\u001a05H\u0002ø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010;J\u001d\u0010=\u001a\b\u0012\u0002\b\u0003\u0018\u00010<2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b=\u0010>J\u001b\u0010?\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b?\u0010;J\u0017\u0010@\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010<H\u0014¢\u0006\u0004\b@\u0010AJ\u0011\u0010B\u001a\u0004\u0018\u00010\u0018H\u0004¢\u0006\u0004\bB\u0010CJ\u000f\u0010E\u001a\u00020DH\u0016¢\u0006\u0004\bE\u0010FJ$\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030G2\u0006\u0010\u0010\u001a\u00028\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\bH\u0010,J+\u0010J\u001a\u00020\u0003*\u0006\u0012\u0002\b\u0003072\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\bJ\u0010KR\u0014\u0010M\u001a\u00020D8TX\u0004¢\u0006\u0006\u001a\u0004\bL\u0010FR\u001a\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001a\u0010R\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010OR\u0014\u0010S\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bU\u0010TR\u0011\u0010V\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\bV\u0010TR\u0014\u0010W\u001a\u00020\n8BX\u0004¢\u0006\u0006\u001a\u0004\bW\u0010TR#\u0010[\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000060X8F¢\u0006\u0006\u001a\u0004\bY\u0010ZR.\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00048\u0004X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\\R\u001a\u0010^\u001a\u00020]8\u0004X\u0004¢\u0006\f\n\u0004\b^\u0010_\u001a\u0004\b`\u0010aR\u0014\u0010c\u001a\u00020D8BX\u0004¢\u0006\u0006\u001a\u0004\bb\u0010F\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006h"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "trySend", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "isBufferAlwaysFull", "()Z", "isBufferFull", "isClosedForSend", "isFullImpl", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractChannel.kt */
public abstract class AbstractSendChannel<E> implements SendChannel<E> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");
    private volatile /* synthetic */ Object onCloseHandler = null;
    protected final Function1<E, Unit> onUndeliveredElement;
    private final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferAlwaysFull();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferFull();

    public AbstractSendChannel(Function1<? super E, Unit> onUndeliveredElement2) {
        this.onUndeliveredElement = onUndeliveredElement2;
    }

    /* access modifiers changed from: protected */
    public final LockFreeLinkedListHead getQueue() {
        return this.queue;
    }

    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        ReceiveOrClosed receive;
        Symbol token;
        do {
            receive = takeFirstReceiveOrPeekClosed();
            if (receive == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            token = receive.tryResumeReceive(element, (LockFreeLinkedListNode.PrepareOp) null);
        } while (token == null);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                throw new AssertionError();
            }
        }
        receive.completeResumeReceive(element);
        return receive.getOfferResult();
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        TryOfferDesc offerOp = describeTryOffer(element);
        Object failure = select.performAtomicTrySelect(offerOp);
        if (failure != null) {
            return failure;
        }
        ReceiveOrClosed receive = (ReceiveOrClosed) offerOp.getResult();
        receive.completeResumeReceive(element);
        return receive.getOfferResult();
    }

    /* access modifiers changed from: protected */
    public final Closed<?> getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        Closed it = prevNode instanceof Closed ? (Closed) prevNode : null;
        if (it == null) {
            return null;
        }
        helpClose(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public final Closed<?> getClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        Closed it = nextNode instanceof Closed ? (Closed) nextNode : null;
        if (it == null) {
            return null;
        }
        helpClose(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode first$iv;
        LockFreeLinkedListNode next$iv;
        LockFreeLinkedListNode this_$iv = this.queue;
        while (true) {
            first$iv = (LockFreeLinkedListNode) this_$iv.getNext();
            if (first$iv != this_$iv) {
                if (first$iv instanceof Send) {
                    if (((((Send) first$iv) instanceof Closed) && !first$iv.isRemoved()) || (next$iv = first$iv.removeOrNext()) == null) {
                        break;
                    }
                    next$iv.helpRemovePrev();
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                first$iv = null;
                break;
            }
        }
        return (Send) first$iv;
    }

    /* access modifiers changed from: protected */
    public final ReceiveOrClosed<?> sendBuffered(E element) {
        LockFreeLinkedListNode prev$iv;
        LockFreeLinkedListNode this_$iv = this.queue;
        LockFreeLinkedListNode node$iv = new SendBuffered(element);
        do {
            prev$iv = this_$iv.getPrevNode();
            LockFreeLinkedListNode prev = prev$iv;
            if (prev instanceof ReceiveOrClosed) {
                return (ReceiveOrClosed) prev;
            }
        } while (!prev$iv.addNext(node$iv, this_$iv));
        return null;
    }

    /* access modifiers changed from: protected */
    public final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E element) {
        return new SendBufferedDesc<>(this.queue, element);
    }

    @Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static class SendBufferedDesc<E> extends LockFreeLinkedListNode.AddLastDesc<SendBuffered<? extends E>> {
        public SendBufferedDesc(LockFreeLinkedListHead queue, E element) {
            super(queue, new SendBuffered(element));
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            if (affected instanceof Closed) {
                return affected;
            }
            if (affected instanceof ReceiveOrClosed) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }
    }

    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    /* access modifiers changed from: private */
    public final boolean isFullImpl() {
        return !(this.queue.getNextNode() instanceof ReceiveOrClosed) && isBufferFull();
    }

    public final Object send(E element, Continuation<? super Unit> $completion) {
        if (offerInternal(element) == AbstractChannelKt.OFFER_SUCCESS) {
            return Unit.INSTANCE;
        }
        Object sendSuspend = sendSuspend(element, $completion);
        return sendSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? sendSuspend : Unit.INSTANCE;
    }

    public boolean offer(E element) {
        UndeliveredElementException it;
        try {
            return SendChannel.DefaultImpls.offer(this, element);
        } catch (Throwable e) {
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
                throw e;
            }
            ExceptionsKt.addSuppressed(it, e);
            throw it;
        }
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public final Object m1258trySendJP2dKIU(E element) {
        Object result = offerInternal(element);
        if (result == AbstractChannelKt.OFFER_SUCCESS) {
            return ChannelResult.Companion.m1274successJP2dKIU(Unit.INSTANCE);
        }
        if (result == AbstractChannelKt.OFFER_FAILED) {
            Closed closedForSend = getClosedForSend();
            if (closedForSend == null) {
                return ChannelResult.Companion.m1273failurePtdJZtk();
            }
            return ChannelResult.Companion.m1272closedJP2dKIU(helpCloseAndGetSendException(closedForSend));
        } else if (result instanceof Closed) {
            return ChannelResult.Companion.m1272closedJP2dKIU(helpCloseAndGetSendException((Closed) result));
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("trySend returned ", result).toString());
        }
    }

    private final Throwable helpCloseAndGetSendException(Closed<?> closed) {
        helpClose(closed);
        return closed.getSendException();
    }

    private final Throwable helpCloseAndGetSendException(E element, Closed<?> closed) {
        UndeliveredElementException it;
        helpClose(closed);
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            return closed.getSendException();
        }
        ExceptionsKt.addSuppressed(it, closed.getSendException());
        throw it;
    }

    /* access modifiers changed from: private */
    public final Object sendSuspend(E element, Continuation<? super Unit> $completion) {
        SendElement send;
        CancellableContinuationImpl<? super Unit> orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        CancellableContinuation cont = orCreateCancellableContinuation;
        while (true) {
            if (isFullImpl()) {
                if (this.onUndeliveredElement == null) {
                    send = new SendElement(element, cont);
                } else {
                    send = new SendElementWithUndeliveredHandler(element, cont, this.onUndeliveredElement);
                }
                Object enqueueResult = enqueueSend(send);
                if (enqueueResult == null) {
                    CancellableContinuationKt.removeOnCancellation(cont, send);
                    break;
                } else if (enqueueResult instanceof Closed) {
                    helpCloseAndResumeWithSendException(cont, element, (Closed) enqueueResult);
                    break;
                } else if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("enqueueSend returned ", enqueueResult).toString());
                }
            }
            Object offerResult = offerInternal(element);
            if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
                break;
            } else if (offerResult != AbstractChannelKt.OFFER_FAILED) {
                if (offerResult instanceof Closed) {
                    helpCloseAndResumeWithSendException(cont, element, (Closed) offerResult);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("offerInternal returned ", offerResult).toString());
                }
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final void helpCloseAndResumeWithSendException(Continuation<?> $this$helpCloseAndResumeWithSendException, E element, Closed<?> closed) {
        UndeliveredElementException it;
        helpClose(closed);
        Throwable sendException = closed.getSendException();
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            Result.Companion companion = Result.Companion;
            $this$helpCloseAndResumeWithSendException.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(sendException)));
            return;
        }
        ExceptionsKt.addSuppressed(it, sendException);
        Result.Companion companion2 = Result.Companion;
        $this$helpCloseAndResumeWithSendException.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(it)));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0035 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object enqueueSend(kotlinx.coroutines.channels.Send r8) {
        /*
            r7 = this;
            boolean r0 = r7.isBufferAlwaysFull()
            if (r0 == 0) goto L_0x0022
            kotlinx.coroutines.internal.LockFreeLinkedListHead r0 = r7.queue
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r1 = 0
        L_0x000b:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = r0.getPrevNode()
            r3 = r2
            r4 = 0
            boolean r5 = r3 instanceof kotlinx.coroutines.channels.ReceiveOrClosed
            if (r5 == 0) goto L_0x0017
            return r3
        L_0x0017:
            r3 = r8
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r3
            boolean r3 = r2.addNext(r3, r0)
            if (r3 == 0) goto L_0x000b
            goto L_0x0055
        L_0x0022:
            kotlinx.coroutines.internal.LockFreeLinkedListHead r0 = r7.queue
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r1 = 0
            r2 = r0
            r3 = 0
            kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 r4 = new kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1
            r5 = r8
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r5
            r4.<init>(r5, r7)
            kotlinx.coroutines.internal.LockFreeLinkedListNode$CondAddOp r4 = (kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp) r4
            r2 = r4
        L_0x0035:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = r0.getPrevNode()
            r4 = r3
            r5 = 0
            boolean r6 = r4 instanceof kotlinx.coroutines.channels.ReceiveOrClosed
            if (r6 == 0) goto L_0x0041
            return r4
        L_0x0041:
            r4 = r8
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r4
            int r4 = r3.tryCondAddNext(r4, r0, r2)
            switch(r4) {
                case 1: goto L_0x004f;
                case 2: goto L_0x004d;
                default: goto L_0x004c;
            }
        L_0x004c:
            goto L_0x0035
        L_0x004d:
            r4 = 0
            goto L_0x0050
        L_0x004f:
            r4 = 1
        L_0x0050:
            if (r4 != 0) goto L_0x0055
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.channels.AbstractChannelKt.ENQUEUE_FAILED
            return r0
        L_0x0055:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.AbstractSendChannel.enqueueSend(kotlinx.coroutines.channels.Send):java.lang.Object");
    }

    public boolean close(Throwable cause) {
        boolean z;
        Closed closed = new Closed(cause);
        LockFreeLinkedListNode this_$iv = this.queue;
        while (true) {
            LockFreeLinkedListNode prev$iv = this_$iv.getPrevNode();
            z = true;
            if (!(prev$iv instanceof Closed)) {
                if (prev$iv.addNext(closed, this_$iv)) {
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        boolean closeAdded = z;
        helpClose(closeAdded ? closed : (Closed) this.queue.getPrevNode());
        if (closeAdded) {
            invokeOnCloseHandler(cause);
        }
        return closeAdded;
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
        Closed closedToken = getClosedForSend();
        if (closedToken != null && C0613xc40028dd.m151m(atomicReferenceFieldUpdater, this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            handler.invoke(closedToken.closeCause);
        }
    }

    private final void helpClose(Closed<?> closed) {
        Object closedList = InlineList.m1288constructorimpl$default((Object) null, 1, (DefaultConstructorMarker) null);
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            Receive previous = prevNode instanceof Receive ? (Receive) prevNode : null;
            if (previous == null) {
                break;
            } else if (!previous.remove()) {
                previous.helpRemove();
            } else {
                closedList = InlineList.m1293plusFjFbRPM(closedList, previous);
            }
        }
        if (closedList != null) {
            if (!(closedList instanceof ArrayList)) {
                ((Receive) closedList).resumeReceiveClosed(closed);
            } else if (closedList != null) {
                ArrayList list$iv = (ArrayList) closedList;
                int size = list$iv.size() - 1;
                if (size >= 0) {
                    do {
                        int i$iv = size;
                        size--;
                        ((Receive) list$iv.get(i$iv)).resumeReceiveClosed(closed);
                    } while (size >= 0);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
        onClosedIdempotent(closed);
    }

    /* access modifiers changed from: protected */
    public void onClosedIdempotent(LockFreeLinkedListNode closed) {
    }

    /* access modifiers changed from: protected */
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        LockFreeLinkedListNode first$iv;
        LockFreeLinkedListNode next$iv;
        LockFreeLinkedListNode this_$iv = this.queue;
        while (true) {
            first$iv = (LockFreeLinkedListNode) this_$iv.getNext();
            if (first$iv != this_$iv) {
                if (first$iv instanceof ReceiveOrClosed) {
                    if (((((ReceiveOrClosed) first$iv) instanceof Closed) && !first$iv.isRemoved()) || (next$iv = first$iv.removeOrNext()) == null) {
                        break;
                    }
                    next$iv.helpRemovePrev();
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                first$iv = null;
                break;
            }
        }
        return (ReceiveOrClosed) first$iv;
    }

    /* access modifiers changed from: protected */
    public final TryOfferDesc<E> describeTryOffer(E element) {
        return new TryOfferDesc<>(element, this.queue);
    }

    @Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011H\u0016R\u0012\u0010\u0005\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0012"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "element", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "Ljava/lang/Object;", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    protected static final class TryOfferDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>> {
        public final E element;

        public TryOfferDesc(E element2, LockFreeLinkedListHead queue) {
            super(queue);
            this.element = element2;
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            if (affected instanceof Closed) {
                return affected;
            }
            if (!(affected instanceof ReceiveOrClosed)) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }

        public Object onPrepare(LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol token = ((ReceiveOrClosed) prepareOp.affected).tryResumeReceive(this.element, prepareOp);
            if (token == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            if (token == AtomicKt.RETRY_ATOMIC) {
                return AtomicKt.RETRY_ATOMIC;
            }
            if (!DebugKt.getASSERTIONS_ENABLED()) {
                return null;
            }
            if (token == CancellableContinuationImplKt.RESUME_TOKEN) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final SelectClause2<E, SendChannel<E>> getOnSend() {
        return new AbstractSendChannel$onSend$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (isFullImpl()) {
                SendSelect node = new SendSelect(element, this, select, block);
                Object enqueueResult = enqueueSend(node);
                if (enqueueResult == null) {
                    select.disposeOnSelect(node);
                    return;
                } else if (enqueueResult instanceof Closed) {
                    throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) enqueueResult));
                } else if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueResult + ' ').toString());
                }
            }
            Object offerResult = offerSelectInternal(element, select);
            if (offerResult != SelectKt.getALREADY_SELECTED()) {
                if (offerResult != AbstractChannelKt.OFFER_FAILED && offerResult != AtomicKt.RETRY_ATOMIC) {
                    if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                        UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                        return;
                    } else if (offerResult instanceof Closed) {
                        throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) offerResult));
                    } else {
                        throw new IllegalStateException(Intrinsics.stringPlus("offerSelectInternal returned ", offerResult).toString());
                    }
                }
            } else {
                return;
            }
        }
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '{' + getQueueDebugStateString() + '}' + getBufferDebugString();
    }

    private final String getQueueDebugStateString() {
        String result;
        LockFreeLinkedListNode head = this.queue.getNextNode();
        if (head == this.queue) {
            return "EmptyQueue";
        }
        if (head instanceof Closed) {
            result = head.toString();
        } else if (head instanceof Receive) {
            result = "ReceiveQueued";
        } else if (head instanceof Send) {
            result = "SendQueued";
        } else {
            result = Intrinsics.stringPlus("UNEXPECTED:", head);
        }
        LockFreeLinkedListNode tail = this.queue.getPrevNode();
        if (tail == head) {
            return result;
        }
        String result2 = result + ",queueSize=" + countQueueSize();
        if (tail instanceof Closed) {
            return result2 + ",closedForSend=" + tail;
        }
        return result2;
    }

    private final int countQueueSize() {
        int size = 0;
        LockFreeLinkedListHead this_$iv = this.queue;
        for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) this_$iv.getNext(); !Intrinsics.areEqual((Object) cur$iv, (Object) this_$iv); cur$iv = cur$iv.getNextNode()) {
            if (cur$iv instanceof LockFreeLinkedListNode) {
                LockFreeLinkedListNode lockFreeLinkedListNode = cur$iv;
                size++;
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public String getBufferDebugString() {
        return "";
    }

    @Metadata(mo112d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u00032\u00020\u0004BV\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t\u0012(\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0014\u0010\u0017\u001a\u00020\u00152\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0015H\u0016R7\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000b8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00028\u0001X\u0004¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t8\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", "E", "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "undeliveredElement", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static final class SendSelect<E, R> extends Send implements DisposableHandle {
        public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;
        public final AbstractSendChannel<E> channel;
        private final E pollResult;
        public final SelectInstance<R> select;

        public E getPollResult() {
            return this.pollResult;
        }

        public SendSelect(E pollResult2, AbstractSendChannel<E> channel2, SelectInstance<? super R> select2, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block2) {
            this.pollResult = pollResult2;
            this.channel = channel2;
            this.select = select2;
            this.block = block2;
        }

        public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp otherOp) {
            return (Symbol) this.select.trySelectOther(otherOp);
        }

        public void completeResumeSend() {
            CancellableKt.startCoroutineCancellable$default(this.block, this.channel, this.select.getCompletion(), (Function1) null, 4, (Object) null);
        }

        public void dispose() {
            if (remove()) {
                undeliveredElement();
            }
        }

        public void resumeSendClosed(Closed<?> closed) {
            if (this.select.trySelect()) {
                this.select.resumeSelectWithException(closed.getSendException());
            }
        }

        public void undeliveredElement() {
            Function1<E, Unit> function1 = this.channel.onUndeliveredElement;
            if (function1 != null) {
                OnUndeliveredElementKt.callUndeliveredElement(function1, getPollResult(), this.select.getCompletion().getContext());
            }
        }

        public String toString() {
            return "SendSelect@" + DebugStringsKt.getHexAddress(this) + '(' + getPollResult() + ")[" + this.channel + ", " + this.select + ']';
        }
    }

    @Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    public static final class SendBuffered<E> extends Send {
        public final E element;

        public SendBuffered(E element2) {
            this.element = element2;
        }

        public Object getPollResult() {
            return this.element;
        }

        public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp otherOp) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            Symbol symbol2 = symbol;
            if (otherOp != null) {
                otherOp.finishPrepare();
            }
            return symbol;
        }

        public void completeResumeSend() {
        }

        public void resumeSendClosed(Closed<?> closed) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                throw new AssertionError();
            }
        }

        public String toString() {
            return "SendBuffered@" + DebugStringsKt.getHexAddress(this) + '(' + this.element + ')';
        }
    }
}

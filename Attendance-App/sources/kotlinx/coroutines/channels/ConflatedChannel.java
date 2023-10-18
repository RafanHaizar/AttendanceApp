package kotlinx.coroutines.channels;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.channels.AbstractSendChannel;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo112d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0018\u001a\u00020\r2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u001aH\u0014J\u0015\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u00002\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\rH\u0014J\n\u0010$\u001a\u0004\u0018\u00010\u0017H\u0014J\u0016\u0010%\u001a\u0004\u0018\u00010\u00172\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014J\u0014\u0010&\u001a\u0004\u0018\u00010'2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0017H\u0002R\u0014\u0010\b\u001a\u00020\t8TX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000eR\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u000e¢\u0006\u0002\n\u0000¨\u0006("}, mo113d2 = {"Lkotlinx/coroutines/channels/ConflatedChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isEmpty", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "value", "", "enqueueReceiveInternal", "receive", "Lkotlinx/coroutines/channels/Receive;", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotent", "wasClosed", "pollInternal", "pollSelectInternal", "updateValueLocked", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConflatedChannel.kt */
public class ConflatedChannel<E> extends AbstractChannel<E> {
    private final ReentrantLock lock = new ReentrantLock();
    private Object value = AbstractChannelKt.EMPTY;

    public ConflatedChannel(Function1<? super E, Unit> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysEmpty() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferEmpty() {
        return this.value == AbstractChannelKt.EMPTY;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferFull() {
        return false;
    }

    public boolean isEmpty() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            return isEmptyImpl();
        } finally {
            lock2.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        ReceiveOrClosed receiveOrClosed;
        Symbol token;
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend == null) {
                if (this.value == AbstractChannelKt.EMPTY) {
                    do {
                        ReceiveOrClosed takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (takeFirstReceiveOrPeekClosed != null) {
                            receiveOrClosed = takeFirstReceiveOrPeekClosed;
                            if (receiveOrClosed instanceof Closed) {
                                lock2.unlock();
                                return receiveOrClosed;
                            }
                            token = receiveOrClosed.tryResumeReceive(element, (LockFreeLinkedListNode.PrepareOp) null);
                        }
                    } while (token == null);
                    if (DebugKt.getASSERTIONS_ENABLED()) {
                        if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                            throw new AssertionError();
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    receiveOrClosed.completeResumeReceive(element);
                    return receiveOrClosed.getOfferResult();
                }
                UndeliveredElementException it = updateValueLocked(element);
                if (it == null) {
                    Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
                    lock2.unlock();
                    return symbol;
                }
                throw it;
            }
            lock2.unlock();
            return closedForSend;
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend == null) {
                if (this.value == AbstractChannelKt.EMPTY) {
                    while (true) {
                        AbstractSendChannel.TryOfferDesc offerOp = describeTryOffer(element);
                        Object failure = select.performAtomicTrySelect(offerOp);
                        if (failure == null) {
                            Object receive = offerOp.getResult();
                            Unit unit = Unit.INSTANCE;
                            lock2.unlock();
                            Intrinsics.checkNotNull(receive);
                            ((ReceiveOrClosed) receive).completeResumeReceive(element);
                            return ((ReceiveOrClosed) receive).getOfferResult();
                        } else if (failure == AbstractChannelKt.OFFER_FAILED) {
                            break;
                        } else if (failure != AtomicKt.RETRY_ATOMIC) {
                            if (failure != SelectKt.getALREADY_SELECTED()) {
                                if (!(failure instanceof Closed)) {
                                    throw new IllegalStateException(Intrinsics.stringPlus("performAtomicTrySelect(describeTryOffer) returned ", failure).toString());
                                }
                            }
                            return failure;
                        }
                    }
                }
                if (!select.trySelect()) {
                    Object already_selected = SelectKt.getALREADY_SELECTED();
                    lock2.unlock();
                    return already_selected;
                }
                UndeliveredElementException it = updateValueLocked(element);
                if (it == null) {
                    Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
                    lock2.unlock();
                    return symbol;
                }
                throw it;
            }
            lock2.unlock();
            return closedForSend;
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public Object pollInternal() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (this.value == AbstractChannelKt.EMPTY) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object result = this.value;
            this.value = AbstractChannelKt.EMPTY;
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            return result;
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public Object pollSelectInternal(SelectInstance<?> select) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (this.value == AbstractChannelKt.EMPTY) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            } else if (!select.trySelect()) {
                Object already_selected = SelectKt.getALREADY_SELECTED();
                lock2.unlock();
                return already_selected;
            } else {
                Object result = this.value;
                this.value = AbstractChannelKt.EMPTY;
                Unit unit = Unit.INSTANCE;
                lock2.unlock();
                return result;
            }
        } finally {
            lock2.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void onCancelIdempotent(boolean wasClosed) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            UndeliveredElementException it = updateValueLocked(AbstractChannelKt.EMPTY);
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            super.onCancelIdempotent(wasClosed);
            if (it != null) {
                throw it;
            }
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    private final UndeliveredElementException updateValueLocked(Object element) {
        Function1 function1;
        Object old = this.value;
        UndeliveredElementException undeliveredElementException = null;
        if (!(old == AbstractChannelKt.EMPTY || (function1 = this.onUndeliveredElement) == null)) {
            undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, old, (UndeliveredElementException) null, 2, (Object) null);
        }
        UndeliveredElementException undeliveredElementException2 = undeliveredElementException;
        this.value = element;
        return undeliveredElementException2;
    }

    /* access modifiers changed from: protected */
    public boolean enqueueReceiveInternal(Receive<? super E> receive) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            return super.enqueueReceiveInternal(receive);
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public String getBufferDebugString() {
        return "(value=" + this.value + ')';
    }
}

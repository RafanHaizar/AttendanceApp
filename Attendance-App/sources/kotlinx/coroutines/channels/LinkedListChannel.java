package kotlinx.coroutines.channels;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.AbstractSendChannel;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo112d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0014¢\u0006\u0002\u0010\u0015J/\u0010\u0016\u001a\u00020\u00052\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001dR\u0014\u0010\b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001e"}, mo113d2 = {"Lkotlinx/coroutines/channels/LinkedListChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: LinkedListChannel.kt */
public class LinkedListChannel<E> extends AbstractChannel<E> {
    public LinkedListChannel(Function1<? super E, Unit> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferEmpty() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferFull() {
        return false;
    }

    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        ReceiveOrClosed sendResult;
        do {
            Object result = super.offerInternal(element);
            if (result == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (result == AbstractChannelKt.OFFER_FAILED) {
                sendResult = sendBuffered(element);
                if (sendResult == null) {
                    return AbstractChannelKt.OFFER_SUCCESS;
                }
            } else if (result instanceof Closed) {
                return result;
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid offerInternal result ", result).toString());
            }
        } while (!(sendResult instanceof Closed));
        return sendResult;
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Object result;
        while (true) {
            if (getHasReceiveOrClosed()) {
                result = super.offerSelectInternal(element, select);
            } else {
                result = select.performAtomicTrySelect(describeSendBuffered(element));
                if (result == null) {
                    result = AbstractChannelKt.OFFER_SUCCESS;
                }
            }
            if (result == SelectKt.getALREADY_SELECTED()) {
                return SelectKt.getALREADY_SELECTED();
            }
            if (result == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (result != AbstractChannelKt.OFFER_FAILED && result != AtomicKt.RETRY_ATOMIC) {
                if (result instanceof Closed) {
                    return result;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid result ", result).toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: onCancelIdempotentList-w-w6eGU  reason: not valid java name */
    public void m1276onCancelIdempotentListww6eGU(Object list, Closed<?> closed) {
        UndeliveredElementException it = null;
        if (list != null) {
            UndeliveredElementException undeliveredElementException = null;
            if (!(list instanceof ArrayList)) {
                Send it2 = (Send) list;
                if (it2 instanceof AbstractSendChannel.SendBuffered) {
                    Function1 function1 = this.onUndeliveredElement;
                    if (function1 != null) {
                        undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) it2).element, (UndeliveredElementException) null);
                    }
                    it = undeliveredElementException;
                } else {
                    it2.resumeSendClosed(closed);
                }
            } else if (list != null) {
                ArrayList list$iv = (ArrayList) list;
                int size = list$iv.size() - 1;
                if (size >= 0) {
                    do {
                        int i$iv = size;
                        size--;
                        Send it3 = (Send) list$iv.get(i$iv);
                        if (it3 instanceof AbstractSendChannel.SendBuffered) {
                            Function1 function12 = this.onUndeliveredElement;
                            it = function12 == null ? null : OnUndeliveredElementKt.callUndeliveredElementCatchingException(function12, ((AbstractSendChannel.SendBuffered) it3).element, it);
                            continue;
                        } else {
                            it3.resumeSendClosed(closed);
                            continue;
                        }
                    } while (size >= 0);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
        if (it != null) {
            throw it;
        }
    }
}

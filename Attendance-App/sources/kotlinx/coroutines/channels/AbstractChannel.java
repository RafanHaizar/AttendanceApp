package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BeforeResumeCancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo112d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0007STUVWXYB'\u0012 \u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0019\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u0016\u0010\u0019\u001a\u00020\u00062\u000e\u0010\u001a\u001a\n\u0018\u00010\u001cj\u0004\u0018\u0001`\u001dJ\u0017\u0010\u001e\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b\u001fJ\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0004J\u0016\u0010\"\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0002J\u0016\u0010%\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0014JR\u0010&\u001a\u00020\n\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\u0006\u0010.\u001a\u00020/H\u0002ø\u0001\u0000¢\u0006\u0002\u00100J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00028\u000002H\u0002J\u0010\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\nH\u0014J/\u00105\u001a\u00020\u00062\f\u00106\u001a\b\u0012\u0004\u0012\u000208072\n\u00109\u001a\u0006\u0012\u0002\b\u00030:H\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b;\u0010<J\b\u0010=\u001a\u00020\u0006H\u0014J\b\u0010>\u001a\u00020\u0006H\u0014J\n\u0010?\u001a\u0004\u0018\u00010,H\u0014J\u0016\u0010@\u001a\u0004\u0018\u00010,2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)H\u0014J\u0011\u0010#\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\"\u0010B\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bC\u0010AJ\u001f\u0010D\u001a\u0002H'\"\u0004\b\u0001\u0010'2\u0006\u0010.\u001a\u00020/H@ø\u0001\u0000¢\u0006\u0002\u0010EJR\u0010F\u001a\u00020\u0006\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+H\u0002ø\u0001\u0000¢\u0006\u0002\u0010GJ \u0010H\u001a\u00020\u00062\n\u0010I\u001a\u0006\u0012\u0002\b\u00030J2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0002J\u0010\u0010K\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010LH\u0014J\u001c\u0010M\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bN\u0010OJX\u0010P\u001a\u00020\u0006\"\u0004\b\u0001\u0010'* \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010,H\u0002ø\u0001\u0000¢\u0006\u0002\u0010RR\u0014\u0010\t\u001a\u00020\n8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0012\u0010\u000e\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0011\u001a\u00020\n8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00170\u00138Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0015\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006Z"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "isEmptyImpl", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveInternal", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onCancelIdempotent", "wasClosed", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "onReceiveDequeued", "onReceiveEnqueued", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceiveMode", "(Lkotlinx/coroutines/selects/SelectInstance;ILkotlin/jvm/functions/Function2;)V", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "tryStartBlockUnintercepted", "value", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/selects/SelectInstance;ILjava/lang/Object;)V", "Itr", "ReceiveElement", "ReceiveElementWithUndeliveredHandler", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractChannel.kt */
public abstract class AbstractChannel<E> extends AbstractSendChannel<E> implements Channel<E> {
    /* access modifiers changed from: protected */
    public abstract boolean isBufferAlwaysEmpty();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferEmpty();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    public SelectClause1<E> getOnReceiveOrNull() {
        return Channel.DefaultImpls.getOnReceiveOrNull(this);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    public E poll() {
        return Channel.DefaultImpls.poll(this);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public Object receiveOrNull(Continuation<? super E> $completion) {
        return Channel.DefaultImpls.receiveOrNull(this, $completion);
    }

    public AbstractChannel(Function1<? super E, Unit> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public Object pollInternal() {
        while (true) {
            Send send = takeFirstSendOrPeekClosed();
            if (send == null) {
                return AbstractChannelKt.POLL_FAILED;
            }
            Symbol token = send.tryResumeSend((LockFreeLinkedListNode.PrepareOp) null);
            if (token != null) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                        throw new AssertionError();
                    }
                }
                send.completeResumeSend();
                return send.getPollResult();
            }
            send.undeliveredElement();
        }
    }

    /* access modifiers changed from: protected */
    public Object pollSelectInternal(SelectInstance<?> select) {
        TryPollDesc pollOp = describeTryPoll();
        Object failure = select.performAtomicTrySelect(pollOp);
        if (failure != null) {
            return failure;
        }
        ((Send) pollOp.getResult()).completeResumeSend();
        return ((Send) pollOp.getResult()).getPollResult();
    }

    /* access modifiers changed from: protected */
    public final boolean getHasReceiveOrClosed() {
        return getQueue().getNextNode() instanceof ReceiveOrClosed;
    }

    public boolean isClosedForReceive() {
        return getClosedForReceive() != null && isBufferEmpty();
    }

    public boolean isEmpty() {
        return isEmptyImpl();
    }

    /* access modifiers changed from: protected */
    public final boolean isEmptyImpl() {
        return !(getQueue().getNextNode() instanceof Send) && isBufferEmpty();
    }

    public final Object receive(Continuation<? super E> $completion) {
        Object result = pollInternal();
        if (result == AbstractChannelKt.POLL_FAILED || (result instanceof Closed)) {
            return receiveSuspend(0, $completion);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public final <R> Object receiveSuspend(int receiveMode, Continuation<? super R> $completion) {
        ReceiveElement receive;
        CancellableContinuationImpl<? super R> orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        CancellableContinuation cont = orCreateCancellableContinuation;
        if (this.onUndeliveredElement == null) {
            receive = new ReceiveElement(cont, receiveMode);
        } else {
            receive = new ReceiveElementWithUndeliveredHandler(cont, receiveMode, this.onUndeliveredElement);
        }
        while (true) {
            if (!enqueueReceive(receive)) {
                Object result = pollInternal();
                if (!(result instanceof Closed)) {
                    if (result != AbstractChannelKt.POLL_FAILED) {
                        cont.resume(receive.resumeValue(result), receive.resumeOnCancellationFun(result));
                        break;
                    }
                } else {
                    receive.resumeReceiveClosed((Closed) result);
                    break;
                }
            } else {
                removeReceiveOnCancel(cont, receive);
                break;
            }
        }
        Object result2 = orCreateCancellableContinuation.getResult();
        if (result2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003d A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enqueueReceiveInternal(kotlinx.coroutines.channels.Receive<? super E> r10) {
        /*
            r9 = this;
            boolean r0 = r9.isBufferAlwaysEmpty()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0028
            kotlinx.coroutines.internal.LockFreeLinkedListHead r0 = r9.getQueue()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r3 = 0
        L_0x000f:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = r0.getPrevNode()
            r5 = r4
            r6 = 0
            boolean r7 = r5 instanceof kotlinx.coroutines.channels.Send
            r5 = r7 ^ 1
            if (r5 != 0) goto L_0x001d
            goto L_0x0058
        L_0x001d:
            r5 = r10
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r5
            boolean r5 = r4.addNext(r5, r0)
            if (r5 == 0) goto L_0x000f
            r1 = 1
            goto L_0x0058
        L_0x0028:
            kotlinx.coroutines.internal.LockFreeLinkedListHead r0 = r9.getQueue()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r3 = 0
            r4 = r0
            r5 = 0
            kotlinx.coroutines.channels.AbstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1 r6 = new kotlinx.coroutines.channels.AbstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1
            r7 = r10
            kotlinx.coroutines.internal.LockFreeLinkedListNode r7 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r7
            r6.<init>(r7, r9)
            kotlinx.coroutines.internal.LockFreeLinkedListNode$CondAddOp r6 = (kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp) r6
            r4 = r6
        L_0x003d:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = r0.getPrevNode()
            r6 = r5
            r7 = 0
            boolean r8 = r6 instanceof kotlinx.coroutines.channels.Send
            r6 = r8 ^ 1
            if (r6 != 0) goto L_0x004b
            goto L_0x0058
        L_0x004b:
            r6 = r10
            kotlinx.coroutines.internal.LockFreeLinkedListNode r6 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r6
            int r6 = r5.tryCondAddNext(r6, r0, r4)
            switch(r6) {
                case 1: goto L_0x0057;
                case 2: goto L_0x0056;
                default: goto L_0x0055;
            }
        L_0x0055:
            goto L_0x003d
        L_0x0056:
            goto L_0x0058
        L_0x0057:
            r1 = 1
        L_0x0058:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.AbstractChannel.enqueueReceiveInternal(kotlinx.coroutines.channels.Receive):boolean");
    }

    /* access modifiers changed from: private */
    public final boolean enqueueReceive(Receive<? super E> receive) {
        boolean result = enqueueReceiveInternal(receive);
        if (result) {
            onReceiveEnqueued();
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* renamed from: receiveCatching-JP2dKIU  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object m1240receiveCatchingJP2dKIU(kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.AbstractChannel$receiveCatching$1
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.channels.AbstractChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.AbstractChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.AbstractChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.AbstractChannel$receiveCatching$1
            r0.<init>(r5, r6)
        L_0x0019:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L_0x0032;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r0
            goto L_0x0061
        L_0x0032:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            java.lang.Object r3 = r2.pollInternal()
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.channels.AbstractChannelKt.POLL_FAILED
            if (r3 == r4) goto L_0x0057
            r1 = r3
            r2 = 0
            boolean r3 = r1 instanceof kotlinx.coroutines.channels.Closed
            if (r3 == 0) goto L_0x0050
            kotlinx.coroutines.channels.ChannelResult$Companion r3 = kotlinx.coroutines.channels.ChannelResult.Companion
            r4 = r1
            kotlinx.coroutines.channels.Closed r4 = (kotlinx.coroutines.channels.Closed) r4
            java.lang.Throwable r4 = r4.closeCause
            java.lang.Object r3 = r3.m1272closedJP2dKIU(r4)
            goto L_0x0056
        L_0x0050:
            kotlinx.coroutines.channels.ChannelResult$Companion r3 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Object r3 = r3.m1274successJP2dKIU(r1)
        L_0x0056:
            return r3
        L_0x0057:
            r3 = 1
            r6.label = r3
            java.lang.Object r2 = r2.receiveSuspend(r3, r6)
            if (r2 != r1) goto L_0x0061
            return r1
        L_0x0061:
            kotlinx.coroutines.channels.ChannelResult r2 = (kotlinx.coroutines.channels.ChannelResult) r2
            java.lang.Object r1 = r2.m1271unboximpl()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.AbstractChannel.m1240receiveCatchingJP2dKIU(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* renamed from: tryReceive-PtdJZtk  reason: not valid java name */
    public final Object m1241tryReceivePtdJZtk() {
        Object result = pollInternal();
        if (result == AbstractChannelKt.POLL_FAILED) {
            return ChannelResult.Companion.m1273failurePtdJZtk();
        }
        if (result instanceof Closed) {
            return ChannelResult.Companion.m1272closedJP2dKIU(((Closed) result).closeCause);
        }
        return ChannelResult.Companion.m1274successJP2dKIU(result);
    }

    public final void cancel(CancellationException cause) {
        if (!isClosedForReceive()) {
            cancel(cause == null ? new CancellationException(Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " was cancelled")) : cause);
        }
    }

    /* renamed from: cancelInternal$kotlinx_coroutines_core */
    public final boolean cancel(Throwable cause) {
        boolean it = close(cause);
        onCancelIdempotent(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public void onCancelIdempotent(boolean wasClosed) {
        Closed closed = getClosedForSend();
        if (closed != null) {
            Object list = InlineList.m1288constructorimpl$default((Object) null, 1, (DefaultConstructorMarker) null);
            while (true) {
                LockFreeLinkedListNode previous = closed.getPrevNode();
                if (previous instanceof LockFreeLinkedListHead) {
                    m1239onCancelIdempotentListww6eGU(list, closed);
                    return;
                } else if (DebugKt.getASSERTIONS_ENABLED() && (previous instanceof Send) == 0) {
                    throw new AssertionError();
                } else if (!previous.remove()) {
                    previous.helpRemove();
                } else {
                    list = InlineList.m1293plusFjFbRPM(list, (Send) previous);
                }
            }
        } else {
            throw new IllegalStateException("Cannot happen".toString());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: onCancelIdempotentList-w-w6eGU  reason: not valid java name */
    public void m1239onCancelIdempotentListww6eGU(Object list, Closed<?> closed) {
        if (list != null) {
            if (!(list instanceof ArrayList)) {
                ((Send) list).resumeSendClosed(closed);
            } else if (list != null) {
                ArrayList list$iv = (ArrayList) list;
                int size = list$iv.size() - 1;
                if (size >= 0) {
                    do {
                        int i$iv = size;
                        size--;
                        ((Send) list$iv.get(i$iv)).resumeSendClosed(closed);
                    } while (size >= 0);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
    }

    public final ChannelIterator<E> iterator() {
        return new Itr<>(this);
    }

    /* access modifiers changed from: protected */
    public final TryPollDesc<E> describeTryPoll() {
        return new TryPollDesc<>(getQueue());
    }

    @Metadata(mo112d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u0012\u0012\u0004\u0012\u00020\u00030\u0002j\b\u0012\u0004\u0012\u00020\u0003`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0016\u0010\f\u001a\u0004\u0018\u00010\t2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\u0012"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "onRemoved", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    protected static final class TryPollDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<Send> {
        public TryPollDesc(LockFreeLinkedListHead queue) {
            super(queue);
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            if (affected instanceof Closed) {
                return affected;
            }
            if (!(affected instanceof Send)) {
                return AbstractChannelKt.POLL_FAILED;
            }
            return null;
        }

        public Object onPrepare(LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol token = ((Send) prepareOp.affected).tryResumeSend(prepareOp);
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

        public void onRemoved(LockFreeLinkedListNode affected) {
            ((Send) affected).undeliveredElement();
        }
    }

    public final SelectClause1<E> getOnReceive() {
        return new AbstractChannel$onReceive$1(this);
    }

    public final SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        return new AbstractChannel$onReceiveCatching$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectReceiveMode(SelectInstance<? super R> select, int receiveMode, Function2<Object, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (!isEmptyImpl()) {
                Object pollResult = pollSelectInternal(select);
                if (pollResult != SelectKt.getALREADY_SELECTED()) {
                    if (!(pollResult == AbstractChannelKt.POLL_FAILED || pollResult == AtomicKt.RETRY_ATOMIC)) {
                        tryStartBlockUnintercepted(block, select, receiveMode, pollResult);
                    }
                } else {
                    return;
                }
            } else if (enqueueReceiveSelect(select, block, receiveMode)) {
                return;
            }
        }
    }

    private final <R> void tryStartBlockUnintercepted(Function2<Object, ? super Continuation<? super R>, ? extends Object> $this$tryStartBlockUnintercepted, SelectInstance<? super R> select, int receiveMode, Object value) {
        if (value instanceof Closed) {
            switch (receiveMode) {
                case 0:
                    throw StackTraceRecoveryKt.recoverStackTrace(((Closed) value).getReceiveException());
                case 1:
                    if (select.trySelect()) {
                        UndispatchedKt.startCoroutineUnintercepted($this$tryStartBlockUnintercepted, ChannelResult.m1259boximpl(ChannelResult.Companion.m1272closedJP2dKIU(((Closed) value).closeCause)), select.getCompletion());
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (receiveMode == 1) {
            Object $this$toResult$iv = value;
            UndispatchedKt.startCoroutineUnintercepted($this$tryStartBlockUnintercepted, ChannelResult.m1259boximpl($this$toResult$iv instanceof Closed ? ChannelResult.Companion.m1272closedJP2dKIU(((Closed) $this$toResult$iv).closeCause) : ChannelResult.Companion.m1274successJP2dKIU($this$toResult$iv)), select.getCompletion());
        } else {
            UndispatchedKt.startCoroutineUnintercepted($this$tryStartBlockUnintercepted, value, select.getCompletion());
        }
    }

    private final <R> boolean enqueueReceiveSelect(SelectInstance<? super R> select, Function2<Object, ? super Continuation<? super R>, ? extends Object> block, int receiveMode) {
        ReceiveSelect node = new ReceiveSelect(this, select, block, receiveMode);
        boolean result = enqueueReceive(node);
        if (result) {
            select.disposeOnSelect(node);
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed = super.takeFirstReceiveOrPeekClosed();
        ReceiveOrClosed it = takeFirstReceiveOrPeekClosed;
        if (it != null && !(it instanceof Closed)) {
            onReceiveDequeued();
        }
        return takeFirstReceiveOrPeekClosed;
    }

    /* access modifiers changed from: protected */
    public void onReceiveEnqueued() {
    }

    /* access modifiers changed from: protected */
    public void onReceiveDequeued() {
    }

    /* access modifiers changed from: private */
    public final void removeReceiveOnCancel(CancellableContinuation<?> cont, Receive<?> receive) {
        cont.invokeOnCancellation(new RemoveReceiveOnCancel(receive));
    }

    @Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/BeforeResumeCancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private final class RemoveReceiveOnCancel extends BeforeResumeCancelHandler {
        private final Receive<?> receive;

        public RemoveReceiveOnCancel(Receive<?> receive2) {
            this.receive = receive2;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return Unit.INSTANCE;
        }

        public void invoke(Throwable cause) {
            if (this.receive.remove()) {
                AbstractChannel.this.onReceiveDequeued();
            }
        }

        public String toString() {
            return "RemoveReceiveOnCancel[" + this.receive + ']';
        }
    }

    @Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0011\u0010\f\u001a\u00020\rHBø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0011\u0010\u0010\u001a\u00020\rH@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u0011\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010\tR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "E", "Lkotlinx/coroutines/channels/ChannelIterator;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "(Lkotlinx/coroutines/channels/AbstractChannel;)V", "result", "", "getResult", "()Ljava/lang/Object;", "setResult", "(Ljava/lang/Object;)V", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextResult", "hasNextSuspend", "next", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static final class Itr<E> implements ChannelIterator<E> {
        public final AbstractChannel<E> channel;
        private Object result = AbstractChannelKt.POLL_FAILED;

        public Itr(AbstractChannel<E> channel2) {
            this.channel = channel2;
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        public /* synthetic */ Object next(Continuation $completion) {
            return ChannelIterator.DefaultImpls.next(this, $completion);
        }

        public final Object getResult() {
            return this.result;
        }

        public final void setResult(Object obj) {
            this.result = obj;
        }

        public Object hasNext(Continuation<? super Boolean> $completion) {
            if (getResult() != AbstractChannelKt.POLL_FAILED) {
                return Boxing.boxBoolean(hasNextResult(getResult()));
            }
            setResult(this.channel.pollInternal());
            if (getResult() != AbstractChannelKt.POLL_FAILED) {
                return Boxing.boxBoolean(hasNextResult(getResult()));
            }
            return hasNextSuspend($completion);
        }

        private final boolean hasNextResult(Object result2) {
            if (!(result2 instanceof Closed)) {
                return true;
            }
            if (((Closed) result2).closeCause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(((Closed) result2).getReceiveException());
        }

        /* access modifiers changed from: private */
        public final Object hasNextSuspend(Continuation<? super Boolean> $completion) {
            CancellableContinuationImpl<? super Boolean> orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
            CancellableContinuation cont = orCreateCancellableContinuation;
            ReceiveHasNext receive = new ReceiveHasNext(this, cont);
            while (true) {
                if (this.channel.enqueueReceive(receive)) {
                    this.channel.removeReceiveOnCancel(cont, receive);
                    break;
                }
                Object result2 = this.channel.pollInternal();
                setResult(result2);
                if (result2 instanceof Closed) {
                    if (((Closed) result2).closeCause == null) {
                        Result.Companion companion = Result.Companion;
                        cont.resumeWith(Result.m1345constructorimpl(Boxing.boxBoolean(false)));
                    } else {
                        Result.Companion companion2 = Result.Companion;
                        cont.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(((Closed) result2).getReceiveException())));
                    }
                } else if (result2 != AbstractChannelKt.POLL_FAILED) {
                    Boolean boxBoolean = Boxing.boxBoolean(true);
                    Function1 function1 = this.channel.onUndeliveredElement;
                    cont.resume(boxBoolean, function1 == null ? null : OnUndeliveredElementKt.bindCancellationFun(function1, result2, cont.getContext()));
                }
            }
            Object result3 = orCreateCancellableContinuation.getResult();
            if (result3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result3;
        }

        public E next() {
            E e = this.result;
            if (e instanceof Closed) {
                throw StackTraceRecoveryKt.recoverStackTrace(((Closed) e).getReceiveException());
            } else if (e != AbstractChannelKt.POLL_FAILED) {
                this.result = AbstractChannelKt.POLL_FAILED;
                return e;
            } else {
                throw new IllegalStateException("'hasNext' should be called prior to 'next' invocation");
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u00020\n2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00028\u0001¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000b\u001a\u00028\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010\u0018R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static class ReceiveElement<E> extends Receive<E> {
        public final CancellableContinuation<Object> cont;
        public final int receiveMode;

        public ReceiveElement(CancellableContinuation<Object> cont2, int receiveMode2) {
            this.cont = cont2;
            this.receiveMode = receiveMode2;
        }

        public final Object resumeValue(E value) {
            if (this.receiveMode == 1) {
                return ChannelResult.m1259boximpl(ChannelResult.Companion.m1274successJP2dKIU(value));
            }
            return value;
        }

        public Symbol tryResumeReceive(E value, LockFreeLinkedListNode.PrepareOp otherOp) {
            Object token = this.cont.tryResume(resumeValue(value), otherOp == null ? null : otherOp.desc, resumeOnCancellationFun(value));
            if (token == null) {
                return null;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                    throw new AssertionError();
                }
            }
            if (otherOp != null) {
                otherOp.finishPrepare();
            }
            return CancellableContinuationImplKt.RESUME_TOKEN;
        }

        public void completeResumeReceive(E value) {
            this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            if (this.receiveMode == 1) {
                Result.Companion companion = Result.Companion;
                this.cont.resumeWith(Result.m1345constructorimpl(ChannelResult.m1259boximpl(ChannelResult.Companion.m1272closedJP2dKIU(closed.closeCause))));
                return;
            }
            Result.Companion companion2 = Result.Companion;
            this.cont.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(closed.getReceiveException())));
        }

        public String toString() {
            return "ReceiveElement@" + DebugStringsKt.getHexAddress(this) + "[receiveMode=" + this.receiveMode + ']';
        }
    }

    @Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B;\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u001c\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010R&\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b8\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlinx/coroutines/CancellableContinuation;ILkotlin/jvm/functions/Function1;)V", "resumeOnCancellationFun", "", "value", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static final class ReceiveElementWithUndeliveredHandler<E> extends ReceiveElement<E> {
        public final Function1<E, Unit> onUndeliveredElement;

        public ReceiveElementWithUndeliveredHandler(CancellableContinuation<Object> cont, int receiveMode, Function1<? super E, Unit> onUndeliveredElement2) {
            super(cont, receiveMode);
            this.onUndeliveredElement = onUndeliveredElement2;
        }

        public Function1<Throwable, Unit> resumeOnCancellationFun(E value) {
            return OnUndeliveredElementKt.bindCancellationFun(this.onUndeliveredElement, value, this.cont.getContext());
        }
    }

    @Metadata(mo112d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n\u0018\u00010\u000e2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u00020\n2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J!\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000b\u001a\u00028\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016¢\u0006\u0002\u0010\u001aR\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveHasNext;", "E", "Lkotlinx/coroutines/channels/Receive;", "iterator", "Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/channels/AbstractChannel$Itr;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static class ReceiveHasNext<E> extends Receive<E> {
        public final CancellableContinuation<Boolean> cont;
        public final Itr<E> iterator;

        public ReceiveHasNext(Itr<E> iterator2, CancellableContinuation<? super Boolean> cont2) {
            this.iterator = iterator2;
            this.cont = cont2;
        }

        public Symbol tryResumeReceive(E value, LockFreeLinkedListNode.PrepareOp otherOp) {
            boolean z = true;
            Object token = this.cont.tryResume(true, otherOp == null ? null : otherOp.desc, resumeOnCancellationFun(value));
            if (token == null) {
                return null;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (token != CancellableContinuationImplKt.RESUME_TOKEN) {
                    z = false;
                }
                if (!z) {
                    throw new AssertionError();
                }
            }
            if (otherOp != null) {
                otherOp.finishPrepare();
            }
            return CancellableContinuationImplKt.RESUME_TOKEN;
        }

        public void completeResumeReceive(E value) {
            this.iterator.setResult(value);
            this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            Object token;
            if (closed.closeCause == null) {
                token = CancellableContinuation.DefaultImpls.tryResume$default(this.cont, false, (Object) null, 2, (Object) null);
            } else {
                token = this.cont.tryResumeWithException(closed.getReceiveException());
            }
            if (token != null) {
                this.iterator.setResult(closed);
                this.cont.completeResume(token);
            }
        }

        public Function1<Throwable, Unit> resumeOnCancellationFun(E value) {
            Function1 function1 = this.iterator.channel.onUndeliveredElement;
            if (function1 == null) {
                return null;
            }
            return OnUndeliveredElementKt.bindCancellationFun(function1, value, this.cont.getContext());
        }

        public String toString() {
            return Intrinsics.stringPlus("ReceiveHasNext@", DebugStringsKt.getHexAddress(this));
        }
    }

    @Metadata(mo112d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u00032\u00020\u0004BR\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b\u0012$\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u0012\u0006\u0010\r\u001a\u00020\u000eø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0016J#\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0019J\u0014\u0010\u001a\u001a\u00020\u00122\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J!\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0013\u001a\u00028\u00022\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016¢\u0006\u0002\u0010#R3\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b8\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, mo113d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveSelect;", "R", "E", "Lkotlinx/coroutines/channels/Receive;", "Lkotlinx/coroutines/DisposableHandle;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)V", "Lkotlin/jvm/functions/Function2;", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "dispose", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: AbstractChannel.kt */
    private static final class ReceiveSelect<R, E> extends Receive<E> implements DisposableHandle {
        public final Function2<Object, Continuation<? super R>, Object> block;
        public final AbstractChannel<E> channel;
        public final int receiveMode;
        public final SelectInstance<R> select;

        public ReceiveSelect(AbstractChannel<E> channel2, SelectInstance<? super R> select2, Function2<Object, ? super Continuation<? super R>, ? extends Object> block2, int receiveMode2) {
            this.channel = channel2;
            this.select = select2;
            this.block = block2;
            this.receiveMode = receiveMode2;
        }

        public Symbol tryResumeReceive(E value, LockFreeLinkedListNode.PrepareOp otherOp) {
            return (Symbol) this.select.trySelectOther(otherOp);
        }

        public void completeResumeReceive(E value) {
            CancellableKt.startCoroutineCancellable(this.block, this.receiveMode == 1 ? ChannelResult.m1259boximpl(ChannelResult.Companion.m1274successJP2dKIU(value)) : value, this.select.getCompletion(), resumeOnCancellationFun(value));
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            if (this.select.trySelect()) {
                switch (this.receiveMode) {
                    case 0:
                        this.select.resumeSelectWithException(closed.getReceiveException());
                        return;
                    case 1:
                        CancellableKt.startCoroutineCancellable$default(this.block, ChannelResult.m1259boximpl(ChannelResult.Companion.m1272closedJP2dKIU(closed.closeCause)), this.select.getCompletion(), (Function1) null, 4, (Object) null);
                        return;
                    default:
                        return;
                }
            }
        }

        public void dispose() {
            if (remove()) {
                this.channel.onReceiveDequeued();
            }
        }

        public Function1<Throwable, Unit> resumeOnCancellationFun(E value) {
            Function1 function1 = this.channel.onUndeliveredElement;
            if (function1 == null) {
                return null;
            }
            return OnUndeliveredElementKt.bindCancellationFun(function1, value, this.select.getCompletion().getContext());
        }

        public String toString() {
            return "ReceiveSelect@" + DebugStringsKt.getHexAddress(this) + '[' + this.select + ",receiveMode=" + this.receiveMode + ']';
        }
    }
}

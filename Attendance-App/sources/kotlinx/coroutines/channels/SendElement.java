package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo112d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\u0014\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\u00028\u0000X\u0004¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, mo113d2 = {"Lkotlinx/coroutines/channels/SendElement;", "E", "Lkotlinx/coroutines/channels/Send;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractChannel.kt */
public class SendElement<E> extends Send {
    public final CancellableContinuation<Unit> cont;
    private final E pollResult;

    public E getPollResult() {
        return this.pollResult;
    }

    public SendElement(E pollResult2, CancellableContinuation<? super Unit> cont2) {
        this.pollResult = pollResult2;
        this.cont = cont2;
    }

    public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp otherOp) {
        Object token = this.cont.tryResume(Unit.INSTANCE, otherOp == null ? null : otherOp.desc);
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

    public void completeResumeSend() {
        this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }

    public void resumeSendClosed(Closed<?> closed) {
        Result.Companion companion = Result.Companion;
        this.cont.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(closed.getSendException())));
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '(' + getPollResult() + ')';
    }
}

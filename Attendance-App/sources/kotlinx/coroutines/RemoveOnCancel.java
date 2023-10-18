package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/RemoveOnCancel;", "Lkotlinx/coroutines/BeforeResumeCancelHandler;", "node", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CancellableContinuation.kt */
final class RemoveOnCancel extends BeforeResumeCancelHandler {
    private final LockFreeLinkedListNode node;

    public RemoveOnCancel(LockFreeLinkedListNode node2) {
        this.node = node2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public void invoke(Throwable cause) {
        this.node.remove();
    }

    public String toString() {
        return "RemoveOnCancel[" + this.node + ']';
    }
}

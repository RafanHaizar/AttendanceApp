package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(mo112d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: LockFreeLinkedList.kt */
public final class AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 extends LockFreeLinkedListNode.CondAddOp {
    final /* synthetic */ LockFreeLinkedListNode $node;
    final /* synthetic */ AbstractSendChannel this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1(LockFreeLinkedListNode $node2, AbstractSendChannel abstractSendChannel) {
        super($node2);
        this.$node = $node2;
        this.this$0 = abstractSendChannel;
    }

    public Object prepare(LockFreeLinkedListNode affected) {
        if (this.this$0.isBufferFull() != 0) {
            return null;
        }
        return LockFreeLinkedListKt.getCONDITION_FALSE();
    }
}

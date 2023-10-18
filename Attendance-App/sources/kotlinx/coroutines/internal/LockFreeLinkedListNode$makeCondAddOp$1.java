package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(mo112d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007"}, mo113d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: LockFreeLinkedList.kt */
public final class LockFreeLinkedListNode$makeCondAddOp$1 extends LockFreeLinkedListNode.CondAddOp {
    final /* synthetic */ Function0<Boolean> $condition;
    final /* synthetic */ LockFreeLinkedListNode $node;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LockFreeLinkedListNode$makeCondAddOp$1(LockFreeLinkedListNode $node2, Function0<Boolean> $condition2) {
        super($node2);
        this.$node = $node2;
        this.$condition = $condition2;
    }

    public Object prepare(LockFreeLinkedListNode affected) {
        if (this.$condition.invoke().booleanValue()) {
            return null;
        }
        return LockFreeLinkedListKt.getCONDITION_FALSE();
    }
}

package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\b\u001a\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0000¨\u0006\b"}, mo113d2 = {"checkIndexOverflow", "", "index", "checkOwnership", "", "Lkotlinx/coroutines/flow/internal/AbortFlowException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: FlowExceptions.common.kt */
public final class FlowExceptions_commonKt {
    public static final void checkOwnership(AbortFlowException $this$checkOwnership, FlowCollector<?> owner) {
        if ($this$checkOwnership.getOwner() != owner) {
            throw $this$checkOwnership;
        }
    }

    public static final int checkIndexOverflow(int index) {
        if (index >= 0) {
            return index;
        }
        throw new ArithmeticException("Index overflow has happened");
    }
}

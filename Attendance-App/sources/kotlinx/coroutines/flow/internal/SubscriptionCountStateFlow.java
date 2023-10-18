package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002R\u0014\u0010\u0006\u001a\u00020\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "initialValue", "(I)V", "value", "getValue", "()Ljava/lang/Integer;", "increment", "", "delta", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractSharedFlow.kt */
final class SubscriptionCountStateFlow extends SharedFlowImpl<Integer> implements StateFlow<Integer> {
    public SubscriptionCountStateFlow(int initialValue) {
        super(1, Integer.MAX_VALUE, BufferOverflow.DROP_OLDEST);
        tryEmit(Integer.valueOf(initialValue));
    }

    public Integer getValue() {
        Integer valueOf;
        synchronized (this) {
            valueOf = Integer.valueOf(((Number) getLastReplayedLocked()).intValue());
        }
        return valueOf;
    }

    public final boolean increment(int delta) {
        boolean tryEmit;
        synchronized (this) {
            tryEmit = tryEmit(Integer.valueOf(((Number) getLastReplayedLocked()).intValue() + delta));
        }
        return tryEmit;
    }
}

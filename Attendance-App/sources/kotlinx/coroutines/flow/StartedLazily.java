package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/flow/StartedLazily;", "Lkotlinx/coroutines/flow/SharingStarted;", "()V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SharingStarted.kt */
final class StartedLazily implements SharingStarted {
    public Flow<SharingCommand> command(StateFlow<Integer> subscriptionCount) {
        return FlowKt.flow(new StartedLazily$command$1(subscriptionCount, (Continuation<? super StartedLazily$command$1>) null));
    }

    public String toString() {
        return "SharingStarted.Lazily";
    }
}

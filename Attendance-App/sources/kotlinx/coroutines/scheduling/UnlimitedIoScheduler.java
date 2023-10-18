package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\bj\u0002`\tH\u0016J\u001c\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\bj\u0002`\tH\u0017¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/UnlimitedIoScheduler;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Dispatcher.kt */
final class UnlimitedIoScheduler extends CoroutineDispatcher {
    public static final UnlimitedIoScheduler INSTANCE = new UnlimitedIoScheduler();

    private UnlimitedIoScheduler() {
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(block, TasksKt.BlockingContext, true);
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(block, TasksKt.BlockingContext, false);
    }
}

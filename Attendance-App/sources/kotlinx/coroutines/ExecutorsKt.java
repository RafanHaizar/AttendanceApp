package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0011\u0010\u0000\u001a\u00020\u0004*\u00020\u0005H\u0007¢\u0006\u0002\b\u0003\u001a\n\u0010\u0006\u001a\u00020\u0002*\u00020\u0001*\u0010\b\u0007\u0010\u0007\"\u00020\u00042\u00020\u0004B\u0002\b\b¨\u0006\t"}, mo113d2 = {"asCoroutineDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "from", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/ExecutorService;", "asExecutor", "CloseableCoroutineDispatcher", "Lkotlinx/coroutines/ExperimentalCoroutinesApi;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Executors.kt */
public final class ExecutorsKt {
    public static /* synthetic */ void CloseableCoroutineDispatcher$annotations() {
    }

    public static final ExecutorCoroutineDispatcher from(ExecutorService $this$asCoroutineDispatcher) {
        return new ExecutorCoroutineDispatcherImpl($this$asCoroutineDispatcher);
    }

    public static final CoroutineDispatcher from(Executor $this$asCoroutineDispatcher) {
        CoroutineDispatcher coroutineDispatcher = null;
        DispatcherExecutor dispatcherExecutor = $this$asCoroutineDispatcher instanceof DispatcherExecutor ? (DispatcherExecutor) $this$asCoroutineDispatcher : null;
        if (dispatcherExecutor != null) {
            coroutineDispatcher = dispatcherExecutor.dispatcher;
        }
        return coroutineDispatcher == null ? new ExecutorCoroutineDispatcherImpl($this$asCoroutineDispatcher) : coroutineDispatcher;
    }

    public static final Executor asExecutor(CoroutineDispatcher $this$asExecutor) {
        Executor executor = null;
        ExecutorCoroutineDispatcher executorCoroutineDispatcher = $this$asExecutor instanceof ExecutorCoroutineDispatcher ? (ExecutorCoroutineDispatcher) $this$asExecutor : null;
        if (executorCoroutineDispatcher != null) {
            executor = executorCoroutineDispatcher.getExecutor();
        }
        return executor == null ? new DispatcherExecutor($this$asExecutor) : executor;
    }
}

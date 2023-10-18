package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.ArrayQueue;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

@Metadata(mo112d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\u0012\u0010\u0016\u001a\u00020\u00132\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0010J\u0010\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\nH\u0016J\u0006\u0010\u001d\u001a\u00020\u0004J\b\u0010\u001e\u001a\u00020\u0004H\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0016R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00048TX\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0011\u0010\b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0005R\u0014\u0010\t\u001a\u00020\n8TX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0018\u00010\u000fX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, mo113d2 = {"Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "isActive", "", "()Z", "isEmpty", "isUnconfinedLoopActive", "isUnconfinedQueueEmpty", "nextTime", "", "getNextTime", "()J", "shared", "unconfinedQueue", "Lkotlinx/coroutines/internal/ArrayQueue;", "Lkotlinx/coroutines/DispatchedTask;", "useCount", "decrementUseCount", "", "unconfined", "delta", "dispatchUnconfined", "task", "incrementUseCount", "limitedParallelism", "parallelism", "", "processNextEvent", "processUnconfinedEvent", "shouldBeProcessedFromContext", "shutdown", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: EventLoop.common.kt */
public abstract class EventLoop extends CoroutineDispatcher {
    private boolean shared;
    private ArrayQueue<DispatchedTask<?>> unconfinedQueue;
    private long useCount;

    public long processNextEvent() {
        if (!processUnconfinedEvent()) {
            return Long.MAX_VALUE;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean isEmpty() {
        return isUnconfinedQueueEmpty();
    }

    /* access modifiers changed from: protected */
    public long getNextTime() {
        ArrayQueue queue = this.unconfinedQueue;
        if (queue != null && !queue.isEmpty()) {
            return 0;
        }
        return Long.MAX_VALUE;
    }

    public final boolean processUnconfinedEvent() {
        DispatchedTask task;
        ArrayQueue queue = this.unconfinedQueue;
        if (queue == null || (task = queue.removeFirstOrNull()) == null) {
            return false;
        }
        task.run();
        return true;
    }

    public boolean shouldBeProcessedFromContext() {
        return false;
    }

    public final void dispatchUnconfined(DispatchedTask<?> task) {
        ArrayQueue queue = this.unconfinedQueue;
        if (queue == null) {
            queue = new ArrayQueue();
            this.unconfinedQueue = queue;
        }
        queue.addLast(task);
    }

    public final boolean isActive() {
        return this.useCount > 0;
    }

    public final boolean isUnconfinedLoopActive() {
        return this.useCount >= delta(true);
    }

    public final boolean isUnconfinedQueueEmpty() {
        ArrayQueue<DispatchedTask<?>> arrayQueue = this.unconfinedQueue;
        if (arrayQueue == null) {
            return true;
        }
        return arrayQueue.isEmpty();
    }

    private final long delta(boolean unconfined) {
        return unconfined ? 4294967296L : 1;
    }

    public static /* synthetic */ void incrementUseCount$default(EventLoop eventLoop, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            eventLoop.incrementUseCount(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incrementUseCount");
    }

    public final void incrementUseCount(boolean unconfined) {
        this.useCount += delta(unconfined);
        if (!unconfined) {
            this.shared = true;
        }
    }

    public static /* synthetic */ void decrementUseCount$default(EventLoop eventLoop, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            eventLoop.decrementUseCount(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decrementUseCount");
    }

    public final void decrementUseCount(boolean unconfined) {
        long delta = this.useCount - delta(unconfined);
        this.useCount = delta;
        if (delta <= 0) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(this.useCount == 0)) {
                    throw new AssertionError();
                }
            }
            if (this.shared) {
                shutdown();
            }
        }
    }

    public final CoroutineDispatcher limitedParallelism(int parallelism) {
        LimitedDispatcherKt.checkParallelism(parallelism);
        return this;
    }

    public void shutdown() {
    }
}

package kotlinx.coroutines.internal;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

@Metadata(mo112d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0002J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015HAø\u0001\u0000¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0016J#\u0010\u001a\u001a\u00020\u00132\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u001bH\bJ\u001c\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0017J%\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00152\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u0001J\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0017J\b\u0010!\u001a\u00020\u0013H\u0016J\u001f\u0010\"\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00152\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00130$H\u0001J\b\u0010%\u001a\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\f\u0012\b\u0012\u00060\u0002j\u0002`\u00030\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rj\u0002`\u000eX\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, mo113d2 = {"Lkotlinx/coroutines/internal/LimitedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "Lkotlinx/coroutines/Delay;", "dispatcher", "parallelism", "", "(Lkotlinx/coroutines/CoroutineDispatcher;I)V", "queue", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "runningWorkers", "workerAllocationLock", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "addAndTryDispatching", "", "block", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "dispatchInternal", "Lkotlin/Function0;", "dispatchYield", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "limitedParallelism", "run", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "tryAllocateWorker", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: LimitedDispatcher.kt */
public final class LimitedDispatcher extends CoroutineDispatcher implements Runnable, Delay {
    private final /* synthetic */ Delay $$delegate_0;
    private final CoroutineDispatcher dispatcher;
    private final int parallelism;
    private final LockFreeTaskQueue<Runnable> queue;
    private volatile int runningWorkers;
    private final Object workerAllocationLock;

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long j, Continuation<? super Unit> continuation) {
        return this.$$delegate_0.delay(j, continuation);
    }

    public DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return this.$$delegate_0.invokeOnTimeout(j, runnable, coroutineContext);
    }

    public void scheduleResumeAfterDelay(long j, CancellableContinuation<? super Unit> cancellableContinuation) {
        this.$$delegate_0.scheduleResumeAfterDelay(j, cancellableContinuation);
    }

    public LimitedDispatcher(CoroutineDispatcher dispatcher2, int parallelism2) {
        this.dispatcher = dispatcher2;
        this.parallelism = parallelism2;
        Delay delay = dispatcher2 instanceof Delay ? (Delay) dispatcher2 : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
        this.queue = new LockFreeTaskQueue<>(false);
        this.workerAllocationLock = new Object();
    }

    public CoroutineDispatcher limitedParallelism(int parallelism2) {
        LimitedDispatcherKt.checkParallelism(parallelism2);
        if (parallelism2 >= this.parallelism) {
            return this;
        }
        return super.limitedParallelism(parallelism2);
    }

    public void run() {
        int fairnessCounter = 0;
        while (true) {
            Runnable task = this.queue.removeFirstOrNull();
            if (task != null) {
                try {
                    task.run();
                } catch (Throwable e) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, e);
                }
                fairnessCounter++;
                if (fairnessCounter >= 16 && this.dispatcher.isDispatchNeeded(this)) {
                    this.dispatcher.dispatch(this, this);
                    return;
                }
            } else {
                synchronized (this.workerAllocationLock) {
                    this.runningWorkers--;
                    if (this.queue.getSize() != 0) {
                        this.runningWorkers++;
                        fairnessCounter = 0;
                        Unit unit = Unit.INSTANCE;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        if (!addAndTryDispatching(block) && tryAllocateWorker()) {
            this.dispatcher.dispatch(this, this);
        }
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        if (!addAndTryDispatching(block) && tryAllocateWorker()) {
            this.dispatcher.dispatchYield(this, this);
        }
    }

    private final void dispatchInternal(Runnable block, Function0<Unit> dispatch) {
        if (!addAndTryDispatching(block) && tryAllocateWorker()) {
            dispatch.invoke();
        }
    }

    private final boolean tryAllocateWorker() {
        synchronized (this.workerAllocationLock) {
            if (this.runningWorkers >= this.parallelism) {
                return false;
            }
            this.runningWorkers++;
            return true;
        }
    }

    private final boolean addAndTryDispatching(Runnable block) {
        this.queue.addLast(block);
        return this.runningWorkers >= this.parallelism;
    }
}

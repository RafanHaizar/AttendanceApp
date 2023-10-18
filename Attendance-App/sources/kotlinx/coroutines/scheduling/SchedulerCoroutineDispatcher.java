package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;

@Metadata(mo112d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0010\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0002J\u001c\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018H\u0016J)\u0010\u0019\u001a\u00020\u00112\n\u0010\u0016\u001a\u00060\u0017j\u0002`\u00182\u0006\u0010\u0014\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000¢\u0006\u0002\b\u001dJ\u001c\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018H\u0016J\r\u0010\u001f\u001a\u00020\u0011H\u0000¢\u0006\u0002\b J\u0015\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0006H\u0000¢\u0006\u0002\b#J\r\u0010$\u001a\u00020\u0011H\u0000¢\u0006\u0002\b%R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "corePoolSize", "", "maxPoolSize", "idleWorkerKeepAliveNs", "", "schedulerName", "", "(IIJLjava/lang/String;)V", "coroutineScheduler", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "createScheduler", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchWithContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "tailDispatch", "", "dispatchWithContext$kotlinx_coroutines_core", "dispatchYield", "restore", "restore$kotlinx_coroutines_core", "shutdown", "timeout", "shutdown$kotlinx_coroutines_core", "usePrivateScheduler", "usePrivateScheduler$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Dispatcher.kt */
public class SchedulerCoroutineDispatcher extends ExecutorCoroutineDispatcher {
    private final int corePoolSize;
    private CoroutineScheduler coroutineScheduler;
    private final long idleWorkerKeepAliveNs;
    private final int maxPoolSize;
    private final String schedulerName;

    public SchedulerCoroutineDispatcher() {
        this(0, 0, 0, (String) null, 15, (DefaultConstructorMarker) null);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ SchedulerCoroutineDispatcher(int r4, int r5, long r6, java.lang.String r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L_0x0006
            int r4 = kotlinx.coroutines.scheduling.TasksKt.CORE_POOL_SIZE
        L_0x0006:
            r10 = r9 & 2
            if (r10 == 0) goto L_0x000e
            int r5 = kotlinx.coroutines.scheduling.TasksKt.MAX_POOL_SIZE
            r10 = r5
            goto L_0x000f
        L_0x000e:
            r10 = r5
        L_0x000f:
            r5 = r9 & 4
            if (r5 == 0) goto L_0x0017
            long r6 = kotlinx.coroutines.scheduling.TasksKt.IDLE_WORKER_KEEP_ALIVE_NS
            r0 = r6
            goto L_0x0018
        L_0x0017:
            r0 = r6
        L_0x0018:
            r5 = r9 & 8
            if (r5 == 0) goto L_0x0020
            java.lang.String r8 = "CoroutineScheduler"
            r2 = r8
            goto L_0x0021
        L_0x0020:
            r2 = r8
        L_0x0021:
            r5 = r3
            r6 = r4
            r7 = r10
            r8 = r0
            r10 = r2
            r5.<init>(r6, r7, r8, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.SchedulerCoroutineDispatcher.<init>(int, int, long, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public SchedulerCoroutineDispatcher(int corePoolSize2, int maxPoolSize2, long idleWorkerKeepAliveNs2, String schedulerName2) {
        this.corePoolSize = corePoolSize2;
        this.maxPoolSize = maxPoolSize2;
        this.idleWorkerKeepAliveNs = idleWorkerKeepAliveNs2;
        this.schedulerName = schedulerName2;
        this.coroutineScheduler = createScheduler();
    }

    public Executor getExecutor() {
        return this.coroutineScheduler;
    }

    private final CoroutineScheduler createScheduler() {
        return new CoroutineScheduler(this.corePoolSize, this.maxPoolSize, this.idleWorkerKeepAliveNs, this.schedulerName);
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        CoroutineScheduler.dispatch$default(this.coroutineScheduler, block, (TaskContext) null, false, 6, (Object) null);
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        CoroutineScheduler.dispatch$default(this.coroutineScheduler, block, (TaskContext) null, true, 2, (Object) null);
    }

    public final void dispatchWithContext$kotlinx_coroutines_core(Runnable block, TaskContext context, boolean tailDispatch) {
        this.coroutineScheduler.dispatch(block, context, tailDispatch);
    }

    public void close() {
        this.coroutineScheduler.close();
    }

    public final synchronized void usePrivateScheduler$kotlinx_coroutines_core() {
        this.coroutineScheduler.shutdown(1000);
        this.coroutineScheduler = createScheduler();
    }

    public final synchronized void shutdown$kotlinx_coroutines_core(long timeout) {
        this.coroutineScheduler.shutdown(timeout);
    }

    public final void restore$kotlinx_coroutines_core() {
        usePrivateScheduler$kotlinx_coroutines_core();
    }
}

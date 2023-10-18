package kotlinx.coroutines.scheduling;

import com.itextpdf.barcodes.Barcode128;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.AbstractTimeSource;
import kotlinx.coroutines.AbstractTimeSourceKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.ResizableAtomicArray;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo112d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000 X2\u00020\\2\u00020]:\u0003XYZB+\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u0012\u0010\u0011J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0016\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J!\u0010\u001d\u001a\u00020\n2\n\u0010\u001a\u001a\u00060\u0018j\u0002`\u00192\u0006\u0010\u001c\u001a\u00020\u001b¢\u0006\u0004\b\u001d\u0010\u001eJ\u0018\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u001f\u0010\u0011J\u0015\u0010!\u001a\b\u0018\u00010 R\u00020\u0000H\u0002¢\u0006\u0004\b!\u0010\"J\u0010\u0010#\u001a\u00020\u0013H\b¢\u0006\u0004\b#\u0010\u0015J\u0010\u0010$\u001a\u00020\u0001H\b¢\u0006\u0004\b$\u0010\u0017J-\u0010&\u001a\u00020\u00132\n\u0010\u001a\u001a\u00060\u0018j\u0002`\u00192\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010%\u001a\u00020\f¢\u0006\u0004\b&\u0010'J\u001b\u0010)\u001a\u00020\u00132\n\u0010(\u001a\u00060\u0018j\u0002`\u0019H\u0016¢\u0006\u0004\b)\u0010*J\u0010\u0010+\u001a\u00020\u0004H\b¢\u0006\u0004\b+\u0010,J\u0010\u0010-\u001a\u00020\u0001H\b¢\u0006\u0004\b-\u0010\u0017J\u001b\u0010/\u001a\u00020\u00012\n\u0010.\u001a\u00060 R\u00020\u0000H\u0002¢\u0006\u0004\b/\u00100J\u0015\u00101\u001a\b\u0018\u00010 R\u00020\u0000H\u0002¢\u0006\u0004\b1\u0010\"J\u0019\u00102\u001a\u00020\f2\n\u0010.\u001a\u00060 R\u00020\u0000¢\u0006\u0004\b2\u00103J)\u00106\u001a\u00020\u00132\n\u0010.\u001a\u00060 R\u00020\u00002\u0006\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u00020\u0001¢\u0006\u0004\b6\u00107J\u0010\u00108\u001a\u00020\u0004H\b¢\u0006\u0004\b8\u0010,J\u0015\u00109\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b9\u0010:J\u0015\u0010<\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\u0004¢\u0006\u0004\b<\u0010=J\u0017\u0010?\u001a\u00020\u00132\u0006\u0010>\u001a\u00020\fH\u0002¢\u0006\u0004\b?\u0010@J\r\u0010A\u001a\u00020\u0013¢\u0006\u0004\bA\u0010\u0015J\u000f\u0010B\u001a\u00020\u0006H\u0016¢\u0006\u0004\bB\u0010CJ\u0010\u0010D\u001a\u00020\fH\b¢\u0006\u0004\bD\u0010EJ\u0019\u0010F\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u0004H\u0002¢\u0006\u0004\bF\u0010GJ\u000f\u0010H\u001a\u00020\fH\u0002¢\u0006\u0004\bH\u0010EJ+\u0010I\u001a\u0004\u0018\u00010\n*\b\u0018\u00010 R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010%\u001a\u00020\fH\u0002¢\u0006\u0004\bI\u0010JR\u0015\u0010\u0010\u001a\u00020\u00018Â\u0002X\u0004¢\u0006\u0006\u001a\u0004\bK\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010LR\u0015\u0010\u001f\u001a\u00020\u00018Â\u0002X\u0004¢\u0006\u0006\u001a\u0004\bM\u0010\u0017R\u0014\u0010O\u001a\u00020N8\u0006X\u0004¢\u0006\u0006\n\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020N8\u0006X\u0004¢\u0006\u0006\n\u0004\bQ\u0010PR\u0014\u0010\u0005\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010RR\u0011\u0010S\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bS\u0010ER\u0014\u0010\u0003\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010LR\u0014\u0010\u0007\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010TR\u001e\u0010V\u001a\f\u0012\b\u0012\u00060 R\u00020\u00000U8\u0006X\u0004¢\u0006\u0006\n\u0004\bV\u0010W¨\u0006["}, mo113d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "", "corePoolSize", "maxPoolSize", "", "idleWorkerKeepAliveNs", "", "schedulerName", "<init>", "(IIJLjava/lang/String;)V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "addToGlobalQueue", "(Lkotlinx/coroutines/scheduling/Task;)Z", "state", "availableCpuPermits", "(J)I", "blockingTasks", "", "close", "()V", "createNewWorker", "()I", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "Lkotlinx/coroutines/scheduling/TaskContext;", "taskContext", "createTask", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;)Lkotlinx/coroutines/scheduling/Task;", "createdWorkers", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "currentWorker", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "decrementBlockingTasks", "decrementCreatedWorkers", "tailDispatch", "dispatch", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;Z)V", "command", "execute", "(Ljava/lang/Runnable;)V", "incrementBlockingTasks", "()J", "incrementCreatedWorkers", "worker", "parkedWorkersStackNextIndex", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)I", "parkedWorkersStackPop", "parkedWorkersStackPush", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)Z", "oldIndex", "newIndex", "parkedWorkersStackTopUpdate", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V", "releaseCpuPermit", "runSafely", "(Lkotlinx/coroutines/scheduling/Task;)V", "timeout", "shutdown", "(J)V", "skipUnpark", "signalBlockingWork", "(Z)V", "signalCpuWork", "toString", "()Ljava/lang/String;", "tryAcquireCpuPermit", "()Z", "tryCreateWorker", "(J)Z", "tryUnpark", "submitToLocalQueue", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "getAvailableCpuPermits", "I", "getCreatedWorkers", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalBlockingQueue", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalCpuQueue", "J", "isTerminated", "Ljava/lang/String;", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "workers", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "Companion", "Worker", "WorkerState", "kotlinx-coroutines-core", "Ljava/util/concurrent/Executor;", "Ljava/io/Closeable;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineScheduler.kt */
public final class CoroutineScheduler implements Executor, Closeable {
    private static final long BLOCKING_MASK = 4398044413952L;
    private static final int BLOCKING_SHIFT = 21;
    private static final int CLAIMED = 0;
    private static final long CPU_PERMITS_MASK = 9223367638808264704L;
    private static final int CPU_PERMITS_SHIFT = 42;
    private static final long CREATED_MASK = 2097151;
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int MAX_SUPPORTED_POOL_SIZE = 2097150;
    public static final int MIN_SUPPORTED_POOL_SIZE = 1;
    public static final Symbol NOT_IN_STACK = new Symbol("NOT_IN_STACK");
    private static final int PARKED = -1;
    private static final long PARKED_INDEX_MASK = 2097151;
    private static final long PARKED_VERSION_INC = 2097152;
    private static final long PARKED_VERSION_MASK = -2097152;
    private static final int TERMINATED = 1;
    private static final /* synthetic */ AtomicIntegerFieldUpdater _isTerminated$FU;
    static final /* synthetic */ AtomicLongFieldUpdater controlState$FU;
    private static final /* synthetic */ AtomicLongFieldUpdater parkedWorkersStack$FU;
    private volatile /* synthetic */ int _isTerminated;
    volatile /* synthetic */ long controlState;
    public final int corePoolSize;
    public final GlobalQueue globalBlockingQueue;
    public final GlobalQueue globalCpuQueue;
    public final long idleWorkerKeepAliveNs;
    public final int maxPoolSize;
    private volatile /* synthetic */ long parkedWorkersStack;
    public final String schedulerName;
    public final ResizableAtomicArray<Worker> workers;

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WorkerState.values().length];
            iArr[WorkerState.PARKING.ordinal()] = 1;
            iArr[WorkerState.BLOCKING.ordinal()] = 2;
            iArr[WorkerState.CPU_ACQUIRED.ordinal()] = 3;
            iArr[WorkerState.DORMANT.ordinal()] = 4;
            iArr[WorkerState.TERMINATED.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Metadata(mo112d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "", "(Ljava/lang/String;I)V", "CPU_ACQUIRED", "BLOCKING", "PARKING", "DORMANT", "TERMINATED", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public enum WorkerState {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        DORMANT,
        TERMINATED
    }

    public CoroutineScheduler(int corePoolSize2, int maxPoolSize2, long idleWorkerKeepAliveNs2, String schedulerName2) {
        this.corePoolSize = corePoolSize2;
        this.maxPoolSize = maxPoolSize2;
        this.idleWorkerKeepAliveNs = idleWorkerKeepAliveNs2;
        this.schedulerName = schedulerName2;
        boolean z = true;
        if (corePoolSize2 >= 1) {
            if (maxPoolSize2 >= corePoolSize2) {
                if (maxPoolSize2 <= 2097150) {
                    if (idleWorkerKeepAliveNs2 <= 0 ? false : z) {
                        this.globalCpuQueue = new GlobalQueue();
                        this.globalBlockingQueue = new GlobalQueue();
                        this.parkedWorkersStack = 0;
                        this.workers = new ResizableAtomicArray<>(corePoolSize2 + 1);
                        this.controlState = ((long) corePoolSize2) << 42;
                        this._isTerminated = 0;
                        return;
                    }
                    throw new IllegalArgumentException(("Idle worker keep alive time " + idleWorkerKeepAliveNs2 + " must be positive").toString());
                }
                throw new IllegalArgumentException(("Max pool size " + maxPoolSize2 + " should not exceed maximal supported number of threads 2097150").toString());
            }
            throw new IllegalArgumentException(("Max pool size " + maxPoolSize2 + " should be greater than or equals to core pool size " + corePoolSize2).toString());
        }
        throw new IllegalArgumentException(("Core pool size " + corePoolSize2 + " should be at least 1").toString());
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ CoroutineScheduler(int r7, int r8, long r9, java.lang.String r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x0008
            long r9 = kotlinx.coroutines.scheduling.TasksKt.IDLE_WORKER_KEEP_ALIVE_NS
            r3 = r9
            goto L_0x0009
        L_0x0008:
            r3 = r9
        L_0x0009:
            r9 = r12 & 8
            if (r9 == 0) goto L_0x0011
            java.lang.String r11 = "DefaultDispatcher"
            r5 = r11
            goto L_0x0012
        L_0x0011:
            r5 = r11
        L_0x0012:
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.<init>(int, int, long, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private final boolean addToGlobalQueue(Task task) {
        boolean z = true;
        if (task.taskContext.getTaskMode() != 1) {
            z = false;
        }
        if (z) {
            return this.globalBlockingQueue.addLast(task);
        }
        return this.globalCpuQueue.addLast(task);
    }

    public final void parkedWorkersStackTopUpdate(Worker worker, int oldIndex, int newIndex) {
        int i;
        while (true) {
            long top = this.parkedWorkersStack;
            int index = (int) (2097151 & top);
            long updVersion = (PARKED_VERSION_INC + top) & PARKED_VERSION_MASK;
            if (index != oldIndex) {
                i = index;
            } else if (newIndex == 0) {
                i = parkedWorkersStackNextIndex(worker);
            } else {
                i = newIndex;
            }
            int updIndex = i;
            if (updIndex >= 0) {
                if (parkedWorkersStack$FU.compareAndSet(this, top, updVersion | ((long) updIndex))) {
                    return;
                }
            }
        }
    }

    public final boolean parkedWorkersStackPush(Worker worker) {
        long top;
        long updVersion;
        int updIndex;
        if (worker.getNextParkedWorker() != NOT_IN_STACK) {
            return false;
        }
        do {
            top = this.parkedWorkersStack;
            int index = (int) (2097151 & top);
            updVersion = (PARKED_VERSION_INC + top) & PARKED_VERSION_MASK;
            updIndex = worker.getIndexInArray();
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if ((updIndex != 0 ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            worker.setNextParkedWorker(this.workers.get(index));
        } while (!parkedWorkersStack$FU.compareAndSet(this, top, updVersion | ((long) updIndex)));
        return true;
    }

    private final Worker parkedWorkersStackPop() {
        while (true) {
            long top = this.parkedWorkersStack;
            Worker worker = this.workers.get((int) (2097151 & top));
            if (worker == null) {
                return null;
            }
            Worker worker2 = worker;
            long updVersion = (PARKED_VERSION_INC + top) & PARKED_VERSION_MASK;
            int updIndex = parkedWorkersStackNextIndex(worker2);
            if (updIndex >= 0) {
                int i = updIndex;
                if (parkedWorkersStack$FU.compareAndSet(this, top, updVersion | ((long) updIndex))) {
                    worker2.setNextParkedWorker(NOT_IN_STACK);
                    return worker2;
                }
            }
        }
    }

    private final int parkedWorkersStackNextIndex(Worker worker) {
        Object next = worker.getNextParkedWorker();
        while (next != NOT_IN_STACK) {
            if (next == null) {
                return 0;
            }
            Worker nextWorker = (Worker) next;
            int updIndex = nextWorker.getIndexInArray();
            if (updIndex != 0) {
                return updIndex;
            }
            next = nextWorker.getNextParkedWorker();
        }
        return -1;
    }

    private final int getCreatedWorkers() {
        return (int) (this.controlState & 2097151);
    }

    private final int getAvailableCpuPermits() {
        return (int) ((CPU_PERMITS_MASK & this.controlState) >> 42);
    }

    private final int createdWorkers(long state) {
        return (int) (2097151 & state);
    }

    private final int blockingTasks(long state) {
        return (int) ((BLOCKING_MASK & state) >> 21);
    }

    public final int availableCpuPermits(long state) {
        return (int) ((CPU_PERMITS_MASK & state) >> 42);
    }

    private final int incrementCreatedWorkers() {
        return (int) (2097151 & controlState$FU.incrementAndGet(this));
    }

    private final int decrementCreatedWorkers() {
        return (int) (2097151 & controlState$FU.getAndDecrement(this));
    }

    private final long incrementBlockingTasks() {
        return controlState$FU.addAndGet(this, PARKED_VERSION_INC);
    }

    private final void decrementBlockingTasks() {
        controlState$FU.addAndGet(this, PARKED_VERSION_MASK);
    }

    private final boolean tryAcquireCpuPermit() {
        long state;
        do {
            state = this.controlState;
            if (((int) ((CPU_PERMITS_MASK & state) >> 42)) == 0) {
                return false;
            }
        } while (!controlState$FU.compareAndSet(this, state, state - 4398046511104L));
        return true;
    }

    private final long releaseCpuPermit() {
        return controlState$FU.addAndGet(this, 4398046511104L);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [boolean, int] */
    public final boolean isTerminated() {
        return this._isTerminated;
    }

    @Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000¨\u0006\u0014"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Companion;", "", "()V", "BLOCKING_MASK", "", "BLOCKING_SHIFT", "", "CLAIMED", "CPU_PERMITS_MASK", "CPU_PERMITS_SHIFT", "CREATED_MASK", "MAX_SUPPORTED_POOL_SIZE", "MIN_SUPPORTED_POOL_SIZE", "NOT_IN_STACK", "Lkotlinx/coroutines/internal/Symbol;", "PARKED", "PARKED_INDEX_MASK", "PARKED_VERSION_INC", "PARKED_VERSION_MASK", "TERMINATED", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Class<CoroutineScheduler> cls = CoroutineScheduler.class;
        parkedWorkersStack$FU = AtomicLongFieldUpdater.newUpdater(cls, "parkedWorkersStack");
        controlState$FU = AtomicLongFieldUpdater.newUpdater(cls, "controlState");
        _isTerminated$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_isTerminated");
    }

    public void execute(Runnable command) {
        dispatch$default(this, command, (TaskContext) null, false, 6, (Object) null);
    }

    public void close() {
        shutdown(10000);
    }

    public final void shutdown(long timeout) {
        int i;
        int i2;
        boolean z = false;
        if (_isTerminated$FU.compareAndSet(this, 0, 1)) {
            Worker currentWorker = currentWorker();
            synchronized (this.workers) {
                try {
                    i = (int) (this.controlState & 2097151);
                } catch (Throwable th) {
                    long j = timeout;
                    throw th;
                }
            }
            int created = i;
            if (1 <= created) {
                int i3 = 1;
                do {
                    i2 = i3;
                    i3++;
                    Worker worker = this.workers.get(i2);
                    Intrinsics.checkNotNull(worker);
                    Worker worker2 = worker;
                    if (worker2 != currentWorker) {
                        while (worker2.isAlive()) {
                            LockSupport.unpark(worker2);
                            worker2.join(timeout);
                        }
                        long j2 = timeout;
                        WorkerState state = worker2.state;
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if ((state == WorkerState.TERMINATED ? 1 : 0) == 0) {
                                throw new AssertionError();
                            }
                        }
                        worker2.localQueue.offloadAllWorkTo(this.globalBlockingQueue);
                        continue;
                    } else {
                        long j3 = timeout;
                        continue;
                    }
                } while (i2 != created);
            } else {
                long j4 = timeout;
            }
            this.globalBlockingQueue.close();
            this.globalCpuQueue.close();
            while (true) {
                Task task = currentWorker == null ? null : currentWorker.findTask(true);
                if (task == null && (task = (Task) this.globalCpuQueue.removeFirstOrNull()) == null && (task = (Task) this.globalBlockingQueue.removeFirstOrNull()) == null) {
                    break;
                }
                runSafely(task);
            }
            if (currentWorker != null) {
                currentWorker.tryReleaseCpu(WorkerState.TERMINATED);
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (((int) ((CPU_PERMITS_MASK & this.controlState) >> 42)) == this.corePoolSize) {
                    z = true;
                }
                if (!z) {
                    throw new AssertionError();
                }
            }
            this.parkedWorkersStack = 0;
            this.controlState = 0;
        }
    }

    public static /* synthetic */ void dispatch$default(CoroutineScheduler coroutineScheduler, Runnable runnable, TaskContext taskContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            taskContext = TasksKt.NonBlockingContext;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        coroutineScheduler.dispatch(runnable, taskContext, z);
    }

    public final void dispatch(Runnable block, TaskContext taskContext, boolean tailDispatch) {
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        if (timeSource != null) {
            timeSource.trackTask();
        }
        Task task = createTask(block, taskContext);
        Worker currentWorker = currentWorker();
        Task notAdded = submitToLocalQueue(currentWorker, task, tailDispatch);
        if (notAdded == null || addToGlobalQueue(notAdded)) {
            boolean skipUnpark = tailDispatch && currentWorker != null;
            if (task.taskContext.getTaskMode() != 0) {
                signalBlockingWork(skipUnpark);
            } else if (!skipUnpark) {
                signalCpuWork();
            }
        } else {
            throw new RejectedExecutionException(Intrinsics.stringPlus(this.schedulerName, " was terminated"));
        }
    }

    public final Task createTask(Runnable block, TaskContext taskContext) {
        long nanoTime = TasksKt.schedulerTimeSource.nanoTime();
        if (!(block instanceof Task)) {
            return new TaskImpl(block, nanoTime, taskContext);
        }
        ((Task) block).submissionTime = nanoTime;
        ((Task) block).taskContext = taskContext;
        return (Task) block;
    }

    private final void signalBlockingWork(boolean skipUnpark) {
        long stateSnapshot = controlState$FU.addAndGet(this, PARKED_VERSION_INC);
        if (!skipUnpark && !tryUnpark() && !tryCreateWorker(stateSnapshot)) {
            tryUnpark();
        }
    }

    public final void signalCpuWork() {
        if (!tryUnpark() && !tryCreateWorker$default(this, 0, 1, (Object) null)) {
            tryUnpark();
        }
    }

    static /* synthetic */ boolean tryCreateWorker$default(CoroutineScheduler coroutineScheduler, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = coroutineScheduler.controlState;
        }
        return coroutineScheduler.tryCreateWorker(j);
    }

    private final boolean tryCreateWorker(long state) {
        if (RangesKt.coerceAtLeast(((int) (2097151 & state)) - ((int) ((BLOCKING_MASK & state) >> 21)), 0) < this.corePoolSize) {
            int newCpuWorkers = createNewWorker();
            if (newCpuWorkers == 1 && this.corePoolSize > 1) {
                createNewWorker();
            }
            if (newCpuWorkers > 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean tryUnpark() {
        Worker worker;
        do {
            worker = parkedWorkersStackPop();
            if (worker == null) {
                return false;
            }
        } while (!Worker.workerCtl$FU.compareAndSet(worker, -1, 0));
        LockSupport.unpark(worker);
        return true;
    }

    private final int createNewWorker() {
        synchronized (this.workers) {
            if (isTerminated()) {
                return -1;
            }
            long state = this.controlState;
            int created = (int) (state & 2097151);
            int cpuWorkers = RangesKt.coerceAtLeast(created - ((int) ((BLOCKING_MASK & state) >> 21)), 0);
            if (cpuWorkers >= this.corePoolSize) {
                return 0;
            }
            if (created >= this.maxPoolSize) {
                return 0;
            }
            int newIndex = ((int) (this.controlState & 2097151)) + 1;
            if (newIndex > 0 && this.workers.get(newIndex) == null) {
                Worker worker = new Worker(newIndex);
                this.workers.setSynchronized(newIndex, worker);
                if (newIndex == ((int) (controlState$FU.incrementAndGet(this) & 2097151))) {
                    worker.start();
                    int cpuWorkers2 = cpuWorkers + 1;
                    return cpuWorkers2;
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    private final Task submitToLocalQueue(Worker $this$submitToLocalQueue, Task task, boolean tailDispatch) {
        if ($this$submitToLocalQueue == null || $this$submitToLocalQueue.state == WorkerState.TERMINATED) {
            return task;
        }
        if (task.taskContext.getTaskMode() == 0 && $this$submitToLocalQueue.state == WorkerState.BLOCKING) {
            return task;
        }
        $this$submitToLocalQueue.mayHaveLocalTasks = true;
        return $this$submitToLocalQueue.localQueue.add(task, tailDispatch);
    }

    private final Worker currentWorker() {
        Thread currentThread = Thread.currentThread();
        Worker it = currentThread instanceof Worker ? (Worker) currentThread : null;
        if (it != null && Intrinsics.areEqual((Object) CoroutineScheduler.this, (Object) this)) {
            return it;
        }
        return null;
    }

    public String toString() {
        int parkedWorkers = 0;
        int blockingWorkers = 0;
        int cpuWorkers = 0;
        int dormant = 0;
        int terminated = 0;
        ArrayList queueSizes = new ArrayList();
        int currentLength = this.workers.currentLength();
        int i = 1;
        while (i < currentLength) {
            int index = i;
            i++;
            Worker worker = this.workers.get(index);
            if (worker != null) {
                int queueSize = worker.localQueue.getSize$kotlinx_coroutines_core();
                switch (WhenMappings.$EnumSwitchMapping$0[worker.state.ordinal()]) {
                    case 1:
                        parkedWorkers++;
                        break;
                    case 2:
                        blockingWorkers++;
                        queueSizes.add(new StringBuilder().append(queueSize).append('b').toString());
                        break;
                    case 3:
                        cpuWorkers++;
                        queueSizes.add(new StringBuilder().append(queueSize).append(Barcode128.CODE_AB_TO_C).toString());
                        break;
                    case 4:
                        dormant++;
                        if (queueSize <= 0) {
                            break;
                        } else {
                            queueSizes.add(new StringBuilder().append(queueSize).append(Barcode128.CODE_AC_TO_B).toString());
                            break;
                        }
                    case 5:
                        terminated++;
                        break;
                }
            }
        }
        long state = this.controlState;
        StringBuilder sb = new StringBuilder();
        sb.append(this.schedulerName).append('@').append(DebugStringsKt.getHexAddress(this)).append("[Pool Size {core = ").append(this.corePoolSize).append(", max = ").append(this.maxPoolSize).append("}, Worker States {CPU = ").append(cpuWorkers).append(", blocking = ").append(blockingWorkers).append(", parked = ").append(parkedWorkers).append(", dormant = ").append(dormant).append(", terminated = ").append(terminated).append("}, running workers queues = ").append(queueSizes).append(", global CPU queue size = ").append(this.globalCpuQueue.getSize()).append(", global blocking queue size = ").append(this.globalBlockingQueue.getSize());
        sb.append(", Control State {created workers= ").append((int) (2097151 & state)).append(", blocking tasks = ").append((int) ((BLOCKING_MASK & state) >> 21)).append(", CPUs acquired = ").append(this.corePoolSize - ((int) ((CPU_PERMITS_MASK & state) >> 42))).append("}]");
        return sb.toString();
    }

    public final void runSafely(Task task) {
        AbstractTimeSource timeSource;
        try {
            task.run();
            timeSource = AbstractTimeSourceKt.getTimeSource();
            if (timeSource == null) {
                return;
            }
        } catch (Throwable th) {
            AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource2 != null) {
                timeSource2.unTrackTask();
            }
            throw th;
        }
        timeSource.unTrackTask();
    }

    @Metadata(mo112d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\b\u0004\u0018\u00002\u00020GB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\n\u0010\tJ\u0017\u0010\r\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0015\u0010\tJ\u000f\u0010\u0016\u001a\u00020\u000fH\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0011\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\u001f\u0010\u001cJ\u000f\u0010 \u001a\u00020\u0007H\u0002¢\u0006\u0004\b \u0010\u001cJ\u000f\u0010!\u001a\u00020\u000fH\u0002¢\u0006\u0004\b!\u0010\u0017J\u000f\u0010\"\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\"\u0010\u001cJ\u0015\u0010%\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020#¢\u0006\u0004\b%\u0010&J\u0019\u0010(\u001a\u0004\u0018\u00010\u000b2\u0006\u0010'\u001a\u00020\u000fH\u0002¢\u0006\u0004\b(\u0010\u0012J\u000f\u0010)\u001a\u00020\u0007H\u0002¢\u0006\u0004\b)\u0010\u001cR*\u0010*\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00018\u0006@FX\u000e¢\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010\tR\u0014\u00100\u001a\u00020/8\u0006X\u0004¢\u0006\u0006\n\u0004\b0\u00101R\u0016\u00102\u001a\u00020\u000f8\u0006@\u0006X\u000e¢\u0006\u0006\n\u0004\b2\u00103R\u0016\u00105\u001a\u0002048\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b5\u00106R$\u00108\u001a\u0004\u0018\u0001078\u0006@\u0006X\u000e¢\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u0016\u0010>\u001a\u00020\u00018\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b>\u0010+R\u0012\u0010B\u001a\u00020?8Æ\u0002¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0016\u0010C\u001a\u00020#8\u0006@\u0006X\u000e¢\u0006\u0006\n\u0004\bC\u0010DR\u0016\u0010E\u001a\u0002048\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\bE\u00106¨\u0006F"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "", "index", "<init>", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;)V", "taskMode", "", "afterTask", "(I)V", "beforeTask", "Lkotlinx/coroutines/scheduling/Task;", "task", "executeTask", "(Lkotlinx/coroutines/scheduling/Task;)V", "", "scanLocalQueue", "findAnyTask", "(Z)Lkotlinx/coroutines/scheduling/Task;", "findTask", "mode", "idleReset", "inStack", "()Z", "upperBound", "nextInt", "(I)I", "park", "()V", "pollGlobalQueues", "()Lkotlinx/coroutines/scheduling/Task;", "run", "runWorker", "tryAcquireCpuPermit", "tryPark", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "newState", "tryReleaseCpu", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;)Z", "blockingOnly", "trySteal", "tryTerminateWorker", "indexInArray", "I", "getIndexInArray", "()I", "setIndexInArray", "Lkotlinx/coroutines/scheduling/WorkQueue;", "localQueue", "Lkotlinx/coroutines/scheduling/WorkQueue;", "mayHaveLocalTasks", "Z", "", "minDelayUntilStealableTaskNs", "J", "", "nextParkedWorker", "Ljava/lang/Object;", "getNextParkedWorker", "()Ljava/lang/Object;", "setNextParkedWorker", "(Ljava/lang/Object;)V", "rngState", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "getScheduler", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "scheduler", "state", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "terminationDeadline", "kotlinx-coroutines-core", "Ljava/lang/Thread;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public final class Worker extends Thread {
        static final /* synthetic */ AtomicIntegerFieldUpdater workerCtl$FU = AtomicIntegerFieldUpdater.newUpdater(Worker.class, "workerCtl");
        private volatile int indexInArray;
        public final WorkQueue localQueue;
        public boolean mayHaveLocalTasks;
        private long minDelayUntilStealableTaskNs;
        private volatile Object nextParkedWorker;
        private int rngState;
        public WorkerState state;
        private long terminationDeadline;
        volatile /* synthetic */ int workerCtl;

        private Worker() {
            setDaemon(true);
            this.localQueue = new WorkQueue();
            this.state = WorkerState.DORMANT;
            this.workerCtl = 0;
            this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
            this.rngState = Random.Default.nextInt();
        }

        public final int getIndexInArray() {
            return this.indexInArray;
        }

        public final void setIndexInArray(int index) {
            setName(CoroutineScheduler.this.schedulerName + "-worker-" + (index == 0 ? "TERMINATED" : String.valueOf(index)));
            this.indexInArray = index;
        }

        public Worker(int index) {
            this();
            setIndexInArray(index);
        }

        public final CoroutineScheduler getScheduler() {
            return CoroutineScheduler.this;
        }

        public final Object getNextParkedWorker() {
            return this.nextParkedWorker;
        }

        public final void setNextParkedWorker(Object obj) {
            this.nextParkedWorker = obj;
        }

        private final boolean tryAcquireCpuPermit() {
            boolean z;
            if (this.state == WorkerState.CPU_ACQUIRED) {
                return true;
            }
            CoroutineScheduler this_$iv = CoroutineScheduler.this;
            CoroutineScheduler $this$loop$iv$iv = this_$iv;
            while (true) {
                long state$iv = $this$loop$iv$iv.controlState;
                CoroutineScheduler coroutineScheduler = this_$iv;
                if (((int) ((CoroutineScheduler.CPU_PERMITS_MASK & state$iv) >> 42)) != 0) {
                    if (CoroutineScheduler.controlState$FU.compareAndSet(this_$iv, state$iv, state$iv - 4398046511104L)) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                return false;
            }
            this.state = WorkerState.CPU_ACQUIRED;
            return true;
        }

        public final boolean tryReleaseCpu(WorkerState newState) {
            WorkerState previousState = this.state;
            boolean hadCpu = previousState == WorkerState.CPU_ACQUIRED;
            if (hadCpu) {
                CoroutineScheduler.controlState$FU.addAndGet(CoroutineScheduler.this, 4398046511104L);
            }
            if (previousState != newState) {
                this.state = newState;
            }
            return hadCpu;
        }

        public void run() {
            runWorker();
        }

        private final void runWorker() {
            boolean rescanned = false;
            while (!CoroutineScheduler.this.isTerminated() && this.state != WorkerState.TERMINATED) {
                Task task = findTask(this.mayHaveLocalTasks);
                if (task != null) {
                    rescanned = false;
                    this.minDelayUntilStealableTaskNs = 0;
                    executeTask(task);
                } else {
                    this.mayHaveLocalTasks = false;
                    if (this.minDelayUntilStealableTaskNs == 0) {
                        tryPark();
                    } else if (!rescanned) {
                        rescanned = true;
                    } else {
                        rescanned = false;
                        tryReleaseCpu(WorkerState.PARKING);
                        Thread.interrupted();
                        LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                        this.minDelayUntilStealableTaskNs = 0;
                    }
                }
            }
            tryReleaseCpu(WorkerState.TERMINATED);
        }

        private final void tryPark() {
            if (!inStack()) {
                CoroutineScheduler.this.parkedWorkersStackPush(this);
                return;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(this.localQueue.getSize$kotlinx_coroutines_core() == 0)) {
                    throw new AssertionError();
                }
            }
            this.workerCtl = -1;
            while (inStack() && this.workerCtl == -1 && !CoroutineScheduler.this.isTerminated() && this.state != WorkerState.TERMINATED) {
                tryReleaseCpu(WorkerState.PARKING);
                Thread.interrupted();
                park();
            }
        }

        private final boolean inStack() {
            return this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK;
        }

        private final void executeTask(Task task) {
            int taskMode = task.taskContext.getTaskMode();
            idleReset(taskMode);
            beforeTask(taskMode);
            CoroutineScheduler.this.runSafely(task);
            afterTask(taskMode);
        }

        private final void beforeTask(int taskMode) {
            if (taskMode != 0 && tryReleaseCpu(WorkerState.BLOCKING)) {
                CoroutineScheduler.this.signalCpuWork();
            }
        }

        private final void afterTask(int taskMode) {
            if (taskMode != 0) {
                CoroutineScheduler.controlState$FU.addAndGet(CoroutineScheduler.this, CoroutineScheduler.PARKED_VERSION_MASK);
                WorkerState currentState = this.state;
                if (currentState != WorkerState.TERMINATED) {
                    if (DebugKt.getASSERTIONS_ENABLED()) {
                        if (!(currentState == WorkerState.BLOCKING)) {
                            throw new AssertionError();
                        }
                    }
                    this.state = WorkerState.DORMANT;
                }
            }
        }

        public final int nextInt(int upperBound) {
            int r = this.rngState;
            int r2 = r ^ (r << 13);
            int r3 = r2 ^ (r2 >> 17);
            int r4 = r3 ^ (r3 << 5);
            this.rngState = r4;
            int mask = upperBound - 1;
            if ((mask & upperBound) == 0) {
                return r4 & mask;
            }
            return (Integer.MAX_VALUE & r4) % upperBound;
        }

        private final void park() {
            if (this.terminationDeadline == 0) {
                this.terminationDeadline = System.nanoTime() + CoroutineScheduler.this.idleWorkerKeepAliveNs;
            }
            LockSupport.parkNanos(CoroutineScheduler.this.idleWorkerKeepAliveNs);
            if (System.nanoTime() - this.terminationDeadline >= 0) {
                this.terminationDeadline = 0;
                tryTerminateWorker();
            }
        }

        private final void tryTerminateWorker() {
            Object lock$iv = CoroutineScheduler.this.workers;
            CoroutineScheduler this_$iv = CoroutineScheduler.this;
            synchronized (lock$iv) {
                if (!this_$iv.isTerminated()) {
                    if (((int) (this_$iv.controlState & 2097151)) > this_$iv.corePoolSize) {
                        if (workerCtl$FU.compareAndSet(this, -1, 1)) {
                            int oldIndex = getIndexInArray();
                            setIndexInArray(0);
                            this_$iv.parkedWorkersStackTopUpdate(this, oldIndex, 0);
                            CoroutineScheduler this_$iv2 = this_$iv;
                            CoroutineScheduler coroutineScheduler = this_$iv2;
                            int lastIndex = (int) (2097151 & CoroutineScheduler.controlState$FU.getAndDecrement(this_$iv2));
                            if (lastIndex != oldIndex) {
                                Worker worker = this_$iv.workers.get(lastIndex);
                                Intrinsics.checkNotNull(worker);
                                Worker lastWorker = worker;
                                this_$iv.workers.setSynchronized(oldIndex, lastWorker);
                                lastWorker.setIndexInArray(oldIndex);
                                this_$iv.parkedWorkersStackTopUpdate(lastWorker, lastIndex, oldIndex);
                            }
                            this_$iv.workers.setSynchronized(lastIndex, null);
                            Unit unit = Unit.INSTANCE;
                            this.state = WorkerState.TERMINATED;
                        }
                    }
                }
            }
        }

        private final void idleReset(int mode) {
            this.terminationDeadline = 0;
            if (this.state == WorkerState.PARKING) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    boolean z = true;
                    if (mode != 1) {
                        z = false;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                this.state = WorkerState.BLOCKING;
            }
        }

        public final Task findTask(boolean scanLocalQueue) {
            Task task;
            if (tryAcquireCpuPermit()) {
                return findAnyTask(scanLocalQueue);
            }
            if (scanLocalQueue) {
                task = this.localQueue.poll();
                if (task == null) {
                    task = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
                }
            } else {
                task = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            }
            return task == null ? trySteal(true) : task;
        }

        private final Task findAnyTask(boolean scanLocalQueue) {
            Task it;
            Task it2;
            if (scanLocalQueue) {
                boolean globalFirst = nextInt(CoroutineScheduler.this.corePoolSize * 2) == 0;
                if (globalFirst && (it2 = pollGlobalQueues()) != null) {
                    return it2;
                }
                Task it3 = this.localQueue.poll();
                if (it3 != null) {
                    return it3;
                }
                if (!globalFirst && (it = pollGlobalQueues()) != null) {
                    return it;
                }
            } else {
                Task it4 = pollGlobalQueues();
                if (it4 != null) {
                    return it4;
                }
            }
            return trySteal(false);
        }

        private final Task pollGlobalQueues() {
            if (nextInt(2) == 0) {
                Task it = (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
                if (it == null) {
                    return (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
                }
                return it;
            }
            Task it2 = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            if (it2 == null) {
                return (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
            }
            return it2;
        }

        private final Task trySteal(boolean blockingOnly) {
            int currentIndex;
            long stealResult;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if ((this.localQueue.getSize$kotlinx_coroutines_core() == 0 ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            int created = (int) (CoroutineScheduler.this.controlState & 2097151);
            if (created < 2) {
                return null;
            }
            int currentIndex2 = nextInt(created);
            long minDelay = Long.MAX_VALUE;
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            int i = 0;
            while (true) {
                long j = 0;
                if (i < created) {
                    int i2 = i + 1;
                    int currentIndex3 = currentIndex2 + 1;
                    if (currentIndex3 > created) {
                        currentIndex3 = 1;
                    }
                    Worker worker = coroutineScheduler.workers.get(currentIndex3);
                    if (worker == null || worker == this) {
                        currentIndex = currentIndex3;
                    } else {
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if (!(this.localQueue.getSize$kotlinx_coroutines_core() == 0)) {
                                throw new AssertionError();
                            }
                        }
                        if (blockingOnly) {
                            stealResult = this.localQueue.tryStealBlockingFrom(worker.localQueue);
                        } else {
                            stealResult = this.localQueue.tryStealFrom(worker.localQueue);
                        }
                        currentIndex = currentIndex3;
                        long stealResult2 = stealResult;
                        if (stealResult2 == -1) {
                            return this.localQueue.poll();
                        }
                        if (stealResult2 > 0) {
                            minDelay = Math.min(minDelay, stealResult2);
                        }
                    }
                    currentIndex2 = currentIndex;
                    i = i2;
                } else {
                    if (minDelay != Long.MAX_VALUE) {
                        j = minDelay;
                    }
                    this.minDelayUntilStealableTaskNs = j;
                    return null;
                }
            }
        }
    }
}

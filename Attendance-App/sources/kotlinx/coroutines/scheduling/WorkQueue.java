package kotlinx.coroutines.scheduling;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0;

@Metadata(mo112d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\b\u0000\u0018\u00002\u00020*B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J!\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\t\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0011\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0002¢\u0006\u0004\b\u0012\u0010\u0011J\u0017\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0000¢\u0006\u0004\b\u001a\u0010\u0019J\u001f\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001e\u001a\u00020\r*\u0004\u0018\u00010\u0003H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u001c\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030 8\u0002X\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R\u0014\u0010&\u001a\u00020#8@X\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010(\u001a\u00020#8@X\u0004¢\u0006\u0006\u001a\u0004\b'\u0010%¨\u0006)"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/WorkQueue;", "<init>", "()V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "fair", "add", "(Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "addLast", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalQueue", "", "offloadAllWorkTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)V", "poll", "()Lkotlinx/coroutines/scheduling/Task;", "pollBuffer", "queue", "pollTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "victim", "", "tryStealBlockingFrom", "(Lkotlinx/coroutines/scheduling/WorkQueue;)J", "tryStealFrom", "blockingOnly", "tryStealLastScheduled", "(Lkotlinx/coroutines/scheduling/WorkQueue;Z)J", "decrementIfBlocking", "(Lkotlinx/coroutines/scheduling/Task;)V", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "buffer", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "", "getBufferSize$kotlinx_coroutines_core", "()I", "bufferSize", "getSize$kotlinx_coroutines_core", "size", "kotlinx-coroutines-core", ""}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: WorkQueue.kt */
public final class WorkQueue {
    private static final /* synthetic */ AtomicIntegerFieldUpdater blockingTasksInBuffer$FU;
    private static final /* synthetic */ AtomicIntegerFieldUpdater consumerIndex$FU;
    private static final /* synthetic */ AtomicReferenceFieldUpdater lastScheduledTask$FU;
    private static final /* synthetic */ AtomicIntegerFieldUpdater producerIndex$FU;
    private volatile /* synthetic */ int blockingTasksInBuffer = 0;
    private final AtomicReferenceArray<Task> buffer = new AtomicReferenceArray<>(128);
    private volatile /* synthetic */ int consumerIndex = 0;
    private volatile /* synthetic */ Object lastScheduledTask = null;
    private volatile /* synthetic */ int producerIndex = 0;

    static {
        Class<WorkQueue> cls = WorkQueue.class;
        lastScheduledTask$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "lastScheduledTask");
        producerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "producerIndex");
        consumerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "consumerIndex");
        blockingTasksInBuffer$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "blockingTasksInBuffer");
    }

    public final int getBufferSize$kotlinx_coroutines_core() {
        return this.producerIndex - this.consumerIndex;
    }

    public final int getSize$kotlinx_coroutines_core() {
        return this.lastScheduledTask != null ? getBufferSize$kotlinx_coroutines_core() + 1 : getBufferSize$kotlinx_coroutines_core();
    }

    public final Task poll() {
        Task task = (Task) lastScheduledTask$FU.getAndSet(this, (Object) null);
        return task == null ? pollBuffer() : task;
    }

    public static /* synthetic */ Task add$default(WorkQueue workQueue, Task task, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return workQueue.add(task, z);
    }

    public final Task add(Task task, boolean fair) {
        if (fair) {
            return addLast(task);
        }
        Task previous = (Task) lastScheduledTask$FU.getAndSet(this, task);
        if (previous == null) {
            return null;
        }
        return addLast(previous);
    }

    private final Task addLast(Task task) {
        boolean z = true;
        if (task.taskContext.getTaskMode() != 1) {
            z = false;
        }
        if (z) {
            blockingTasksInBuffer$FU.incrementAndGet(this);
        }
        if (getBufferSize$kotlinx_coroutines_core() == 127) {
            return task;
        }
        int nextIndex = this.producerIndex & 127;
        while (this.buffer.get(nextIndex) != null) {
            Thread.yield();
        }
        this.buffer.lazySet(nextIndex, task);
        producerIndex$FU.incrementAndGet(this);
        return null;
    }

    public final long tryStealFrom(WorkQueue victim) {
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((getBufferSize$kotlinx_coroutines_core() == 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        Task task = victim.pollBuffer();
        if (task == null) {
            return tryStealLastScheduled(victim, false);
        }
        Task notAdded = add$default(this, task, false, 2, (Object) null);
        if (!DebugKt.getASSERTIONS_ENABLED()) {
            return -1;
        }
        if (notAdded != null) {
            z = false;
        }
        if (z) {
            return -1;
        }
        throw new AssertionError();
    }

    public final long tryStealBlockingFrom(WorkQueue victim) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((getBufferSize$kotlinx_coroutines_core() == 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        int end = victim.producerIndex;
        AtomicReferenceArray buffer2 = victim.buffer;
        for (int start = victim.consumerIndex; start != end; start++) {
            int index = start & 127;
            if (victim.blockingTasksInBuffer == 0) {
                break;
            }
            Task value = buffer2.get(index);
            if (value != null) {
                if ((value.taskContext.getTaskMode() == 1) && ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(buffer2, index, value, (Object) null)) {
                    blockingTasksInBuffer$FU.decrementAndGet(victim);
                    add$default(this, value, false, 2, (Object) null);
                    return -1;
                }
            }
        }
        return tryStealLastScheduled(victim, true);
    }

    public final void offloadAllWorkTo(GlobalQueue globalQueue) {
        Task it = (Task) lastScheduledTask$FU.getAndSet(this, (Object) null);
        if (it != null) {
            globalQueue.addLast(it);
        }
        do {
        } while (pollTo(globalQueue));
    }

    private final long tryStealLastScheduled(WorkQueue victim, boolean blockingOnly) {
        Task lastScheduled;
        do {
            lastScheduled = (Task) victim.lastScheduledTask;
            if (lastScheduled == null) {
                return -2;
            }
            if (blockingOnly) {
                boolean z = true;
                if (lastScheduled.taskContext.getTaskMode() != 1) {
                    z = false;
                }
                if (!z) {
                    return -2;
                }
            }
            long staleness = TasksKt.schedulerTimeSource.nanoTime() - lastScheduled.submissionTime;
            if (staleness < TasksKt.WORK_STEALING_TIME_RESOLUTION_NS) {
                return TasksKt.WORK_STEALING_TIME_RESOLUTION_NS - staleness;
            }
        } while (!C0613xc40028dd.m151m(lastScheduledTask$FU, victim, lastScheduled, (Object) null));
        add$default(this, lastScheduled, false, 2, (Object) null);
        return -1;
    }

    private final boolean pollTo(GlobalQueue queue) {
        Task task = pollBuffer();
        if (task == null) {
            return false;
        }
        queue.addLast(task);
        return true;
    }

    private final Task pollBuffer() {
        Task value;
        while (true) {
            int tailLocal = this.consumerIndex;
            if (tailLocal - this.producerIndex == 0) {
                return null;
            }
            int index = tailLocal & 127;
            if (consumerIndex$FU.compareAndSet(this, tailLocal, tailLocal + 1) && (value = this.buffer.getAndSet(index, (Object) null)) != null) {
                decrementIfBlocking(value);
                return value;
            }
        }
    }

    private final void decrementIfBlocking(Task $this$decrementIfBlocking) {
        if ($this$decrementIfBlocking != null) {
            boolean z = false;
            if ($this$decrementIfBlocking.taskContext.getTaskMode() == 1) {
                int value = blockingTasksInBuffer$FU.decrementAndGet(this);
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (value >= 0) {
                        z = true;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }
}

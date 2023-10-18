package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0;
import kotlinx.coroutines.internal.Segment;

@Metadata(mo112d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u001fB!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0004¢\u0006\u0004\b\n\u0010\u000bJ,\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\fH\b¢\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\u0004H\b¢\u0006\u0004\b\u0012\u0010\u0013J$\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\fH\b¢\u0006\u0004\b\u0014\u0010\u0015J\"\u0010\u0016\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\fH\b¢\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016¢\u0006\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001d\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001e"}, mo113d2 = {"Lkotlinx/coroutines/sync/SemaphoreSegment;", "", "id", "prev", "", "pointers", "<init>", "(JLkotlinx/coroutines/sync/SemaphoreSegment;I)V", "index", "", "cancel", "(I)V", "", "expected", "value", "", "cas", "(ILjava/lang/Object;Ljava/lang/Object;)Z", "get", "(I)Ljava/lang/Object;", "getAndSet", "(ILjava/lang/Object;)Ljava/lang/Object;", "set", "(ILjava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "getMaxSlots", "()I", "maxSlots", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/Segment;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Semaphore.kt */
final class SemaphoreSegment extends Segment<SemaphoreSegment> {
    /* synthetic */ AtomicReferenceArray acquirers = new AtomicReferenceArray(SemaphoreKt.SEGMENT_SIZE);

    public SemaphoreSegment(long id, SemaphoreSegment prev, int pointers) {
        super(id, prev, pointers);
    }

    public int getMaxSlots() {
        return SemaphoreKt.SEGMENT_SIZE;
    }

    public final Object get(int index) {
        return this.acquirers.get(index);
    }

    public final void set(int index, Object value) {
        this.acquirers.set(index, value);
    }

    public final boolean cas(int index, Object expected, Object value) {
        return ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(this.acquirers, index, expected, value);
    }

    public final Object getAndSet(int index, Object value) {
        return this.acquirers.getAndSet(index, value);
    }

    public final void cancel(int index) {
        this.acquirers.set(index, SemaphoreKt.CANCELLED);
        onSlotCleaned();
    }

    public String toString() {
        return "SemaphoreSegment[id=" + getId() + ", hashCode=" + hashCode() + ']';
    }
}

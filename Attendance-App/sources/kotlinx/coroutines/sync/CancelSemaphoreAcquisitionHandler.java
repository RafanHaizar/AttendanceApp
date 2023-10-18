package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlinx.coroutines.CancelHandler;

@Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Lkotlinx/coroutines/sync/CancelSemaphoreAcquisitionHandler;", "Lkotlinx/coroutines/CancelHandler;", "segment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "index", "", "(Lkotlinx/coroutines/sync/SemaphoreSegment;I)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Semaphore.kt */
final class CancelSemaphoreAcquisitionHandler extends CancelHandler {
    private final int index;
    private final SemaphoreSegment segment;

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public CancelSemaphoreAcquisitionHandler(SemaphoreSegment segment2, int index2) {
        this.segment = segment2;
        this.index = index2;
    }

    public void invoke(Throwable cause) {
        this.segment.cancel(this.index);
    }

    public String toString() {
        return "CancelSemaphoreAcquisitionHandler[" + this.segment + ", " + this.index + ']';
    }
}

package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;

@Metadata(mo112d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0006\u001a\u00020\u0007H\b\u001a\t\u0010\b\u001a\u00020\u0007H\b\u001a\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0007H\b\u001a\t\u0010\u000e\u001a\u00020\nH\b\u001a\t\u0010\u000f\u001a\u00020\nH\b\u001a\t\u0010\u0010\u001a\u00020\nH\b\u001a\u0011\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\b\u001a\t\u0010\u0014\u001a\u00020\nH\b\u001a\u0019\u0010\u0015\u001a\u00060\u0016j\u0002`\u00172\n\u0010\u0018\u001a\u00060\u0016j\u0002`\u0017H\b\"\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u0001X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0004\b\u0004\u0010\u0005¨\u0006\u0019"}, mo113d2 = {"timeSource", "Lkotlinx/coroutines/AbstractTimeSource;", "getTimeSource", "()Lkotlinx/coroutines/AbstractTimeSource;", "setTimeSource", "(Lkotlinx/coroutines/AbstractTimeSource;)V", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractTimeSource.kt */
public final class AbstractTimeSourceKt {
    private static AbstractTimeSource timeSource;

    public static final AbstractTimeSource getTimeSource() {
        return timeSource;
    }

    public static final void setTimeSource(AbstractTimeSource abstractTimeSource) {
        timeSource = abstractTimeSource;
    }

    private static final long currentTimeMillis() {
        AbstractTimeSource timeSource2 = getTimeSource();
        Long valueOf = timeSource2 == null ? null : Long.valueOf(timeSource2.currentTimeMillis());
        return valueOf == null ? System.currentTimeMillis() : valueOf.longValue();
    }

    private static final long nanoTime() {
        AbstractTimeSource timeSource2 = getTimeSource();
        Long valueOf = timeSource2 == null ? null : Long.valueOf(timeSource2.nanoTime());
        return valueOf == null ? System.nanoTime() : valueOf.longValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0007, code lost:
        r0 = r0.wrapTask(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.Runnable wrapTask(java.lang.Runnable r1) {
        /*
            kotlinx.coroutines.AbstractTimeSource r0 = getTimeSource()
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            goto L_0x000e
        L_0x0007:
            java.lang.Runnable r0 = r0.wrapTask(r1)
            if (r0 != 0) goto L_0x000f
            goto L_0x0006
        L_0x000e:
            r0 = r1
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AbstractTimeSourceKt.wrapTask(java.lang.Runnable):java.lang.Runnable");
    }

    private static final void trackTask() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.trackTask();
        }
    }

    private static final void unTrackTask() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.unTrackTask();
        }
    }

    private static final void registerTimeLoopThread() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.registerTimeLoopThread();
        }
    }

    private static final void unregisterTimeLoopThread() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.unregisterTimeLoopThread();
        }
    }

    private static final void parkNanos(Object blocker, long nanos) {
        Unit unit;
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 == null) {
            unit = null;
        } else {
            timeSource2.parkNanos(blocker, nanos);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            LockSupport.parkNanos(blocker, nanos);
        }
    }

    private static final void unpark(Thread thread) {
        Unit unit;
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 == null) {
            unit = null;
        } else {
            timeSource2.unpark(thread);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            LockSupport.unpark(thread);
        }
    }
}

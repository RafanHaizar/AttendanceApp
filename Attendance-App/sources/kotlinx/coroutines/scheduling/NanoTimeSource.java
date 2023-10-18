package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, mo113d2 = {"Lkotlinx/coroutines/scheduling/NanoTimeSource;", "Lkotlinx/coroutines/scheduling/SchedulerTimeSource;", "()V", "nanoTime", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Tasks.kt */
public final class NanoTimeSource extends SchedulerTimeSource {
    public static final NanoTimeSource INSTANCE = new NanoTimeSource();

    private NanoTimeSource() {
    }

    public long nanoTime() {
        return System.nanoTime();
    }
}

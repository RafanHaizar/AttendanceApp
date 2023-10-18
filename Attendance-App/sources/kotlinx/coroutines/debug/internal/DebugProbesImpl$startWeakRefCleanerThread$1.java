package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo113d2 = {"<anonymous>", "", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DebugProbesImpl.kt */
final class DebugProbesImpl$startWeakRefCleanerThread$1 extends Lambda implements Function0<Unit> {
    public static final DebugProbesImpl$startWeakRefCleanerThread$1 INSTANCE = new DebugProbesImpl$startWeakRefCleanerThread$1();

    DebugProbesImpl$startWeakRefCleanerThread$1() {
        super(0);
    }

    public final void invoke() {
        DebugProbesImpl.callerInfoCache.runWeakRefQueueCleaningLoopUntilInterrupted();
    }
}

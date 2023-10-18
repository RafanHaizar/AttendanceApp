package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0000\u001a\u0019\u0010\u0002\u001a\u00020\u00032\u000e\b\u0004\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\b\u001a\b\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, mo113d2 = {"createEventLoop", "Lkotlinx/coroutines/EventLoop;", "platformAutoreleasePool", "", "block", "Lkotlin/Function0;", "processNextEventInCurrentThread", "", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: EventLoop.kt */
public final class EventLoopKt {
    public static final EventLoop createEventLoop() {
        return new BlockingEventLoop(Thread.currentThread());
    }

    public static final long processNextEventInCurrentThread() {
        EventLoop currentOrNull$kotlinx_coroutines_core = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
        if (currentOrNull$kotlinx_coroutines_core == null) {
            return Long.MAX_VALUE;
        }
        return currentOrNull$kotlinx_coroutines_core.processNextEvent();
    }

    public static final void platformAutoreleasePool(Function0<Unit> block) {
        block.invoke();
    }
}

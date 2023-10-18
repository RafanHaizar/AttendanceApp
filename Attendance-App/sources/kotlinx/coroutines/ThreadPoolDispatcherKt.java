package kotlinx.coroutines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0007"}, mo113d2 = {"newFixedThreadPoolContext", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "nThreads", "", "name", "", "newSingleThreadContext", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadPoolDispatcher.kt */
public final class ThreadPoolDispatcherKt {
    public static final ExecutorCoroutineDispatcher newSingleThreadContext(String name) {
        return newFixedThreadPoolContext(1, name);
    }

    public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(int nThreads, String name) {
        boolean z = true;
        if (nThreads < 1) {
            z = false;
        }
        if (z) {
            return ExecutorsKt.from((ExecutorService) Executors.newScheduledThreadPool(nThreads, new ThreadPoolDispatcherKt$$ExternalSyntheticLambda0(nThreads, name, new AtomicInteger())));
        }
        throw new IllegalArgumentException(("Expected at least one thread, but " + nThreads + " specified").toString());
    }

    /* access modifiers changed from: private */
    /* renamed from: newFixedThreadPoolContext$lambda-1  reason: not valid java name */
    public static final Thread m1874newFixedThreadPoolContext$lambda1(int $nThreads, String $name, AtomicInteger $threadNo, Runnable runnable) {
        Thread t = new Thread(runnable, $nThreads == 1 ? $name : $name + '-' + $threadNo.incrementAndGet());
        t.setDaemon(true);
        return t;
    }
}

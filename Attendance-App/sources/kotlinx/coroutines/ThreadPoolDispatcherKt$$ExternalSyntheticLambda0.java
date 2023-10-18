package kotlinx.coroutines;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ThreadPoolDispatcherKt$$ExternalSyntheticLambda0 implements ThreadFactory {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ AtomicInteger f$2;

    public /* synthetic */ ThreadPoolDispatcherKt$$ExternalSyntheticLambda0(int i, String str, AtomicInteger atomicInteger) {
        this.f$0 = i;
        this.f$1 = str;
        this.f$2 = atomicInteger;
    }

    public final Thread newThread(Runnable runnable) {
        return ThreadPoolDispatcherKt.m1874newFixedThreadPoolContext$lambda1(this.f$0, this.f$1, this.f$2, runnable);
    }
}

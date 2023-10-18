package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a/\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00060\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a)\u0010\f\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\r\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00060\nH\u0002¢\u0006\u0002\u0010\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, mo113d2 = {"FINISHED", "", "INTERRUPTED", "INTERRUPTING", "WORKING", "runInterruptible", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runInterruptibleInExpectedContext", "coroutineContext", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Interruptible.kt */
public final class InterruptibleKt {
    private static final int FINISHED = 1;
    private static final int INTERRUPTED = 3;
    private static final int INTERRUPTING = 2;
    private static final int WORKING = 0;

    public static /* synthetic */ Object runInterruptible$default(CoroutineContext coroutineContext, Function0 function0, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return runInterruptible(coroutineContext, function0, continuation);
    }

    public static final <T> Object runInterruptible(CoroutineContext context, Function0<? extends T> block, Continuation<? super T> $completion) {
        return BuildersKt.withContext(context, new InterruptibleKt$runInterruptible$2(block, (Continuation<? super InterruptibleKt$runInterruptible$2>) null), $completion);
    }

    /* access modifiers changed from: private */
    public static final <T> T runInterruptibleInExpectedContext(CoroutineContext coroutineContext, Function0<? extends T> block) {
        ThreadState threadState;
        try {
            threadState = new ThreadState(JobKt.getJob(coroutineContext));
            threadState.setup();
            T invoke = block.invoke();
            threadState.clearInterrupt();
            return invoke;
        } catch (InterruptedException e) {
            throw new CancellationException("Blocking call was interrupted due to parent cancellation").initCause(e);
        } catch (Throwable th) {
            threadState.clearInterrupt();
            throw th;
        }
    }
}

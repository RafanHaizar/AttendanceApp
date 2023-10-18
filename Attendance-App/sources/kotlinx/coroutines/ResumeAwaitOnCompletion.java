package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"Lkotlinx/coroutines/ResumeAwaitOnCompletion;", "T", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: JobSupport.kt */
final class ResumeAwaitOnCompletion<T> extends JobNode {
    private final CancellableContinuationImpl<T> continuation;

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public ResumeAwaitOnCompletion(CancellableContinuationImpl<? super T> continuation2) {
        this.continuation = continuation2;
    }

    public void invoke(Throwable cause) {
        Object state = getJob().getState$kotlinx_coroutines_core();
        if (DebugKt.getASSERTIONS_ENABLED() && ((state instanceof Incomplete) ^ 1) == 0) {
            throw new AssertionError();
        } else if (state instanceof CompletedExceptionally) {
            Result.Companion companion = Result.Companion;
            this.continuation.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(((CompletedExceptionally) state).cause)));
        } else {
            Result.Companion companion2 = Result.Companion;
            this.continuation.resumeWith(Result.m1345constructorimpl(JobSupportKt.unboxState(state)));
        }
    }
}

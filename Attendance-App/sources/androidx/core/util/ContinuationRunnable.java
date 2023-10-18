package androidx.core.util;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0005H\u0016J\b\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"Landroidx/core/util/ContinuationRunnable;", "Ljava/lang/Runnable;", "Ljava/util/concurrent/atomic/AtomicBoolean;", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "run", "toString", "", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Runnable.kt */
final class ContinuationRunnable extends AtomicBoolean implements Runnable {
    private final Continuation<Unit> continuation;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ContinuationRunnable(Continuation<? super Unit> continuation2) {
        super(false);
        Intrinsics.checkNotNullParameter(continuation2, "continuation");
        this.continuation = continuation2;
    }

    public void run() {
        if (compareAndSet(false, true)) {
            Continuation<Unit> continuation2 = this.continuation;
            Result.Companion companion = Result.Companion;
            continuation2.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
        }
    }

    public String toString() {
        return "ContinuationRunnable(ran = " + get() + ')';
    }
}

package androidx.core.p001os;

import android.os.OutcomeReceiver;
import java.lang.Throwable;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00042\u00020\u0005B\u0013\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo113d2 = {"Landroidx/core/os/ContinuationOutcomeReceiver;", "R", "E", "", "Landroid/os/OutcomeReceiver;", "Ljava/util/concurrent/atomic/AtomicBoolean;", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "onError", "", "error", "(Ljava/lang/Throwable;)V", "onResult", "result", "(Ljava/lang/Object;)V", "toString", "", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.ContinuationOutcomeReceiver */
/* compiled from: OutcomeReceiver.kt */
final class ContinuationOutcomeReceiver<R, E extends Throwable> extends AtomicBoolean implements OutcomeReceiver<R, E> {
    private final Continuation<R> continuation;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ContinuationOutcomeReceiver(Continuation<? super R> continuation2) {
        super(false);
        Intrinsics.checkNotNullParameter(continuation2, "continuation");
        this.continuation = continuation2;
    }

    public void onResult(R result) {
        if (compareAndSet(false, true)) {
            Continuation<R> continuation2 = this.continuation;
            Result.Companion companion = Result.Companion;
            continuation2.resumeWith(Result.m1345constructorimpl(result));
        }
    }

    public void onError(E error) {
        Intrinsics.checkNotNullParameter(error, "error");
        if (compareAndSet(false, true)) {
            Continuation<R> continuation2 = this.continuation;
            Result.Companion companion = Result.Companion;
            continuation2.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(error)));
        }
    }

    public String toString() {
        return "ContinuationOutcomeReceiver(outcomeReceived = " + get() + ')';
    }
}

package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/ResumeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "invoke", "cause", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: JobSupport.kt */
final class ResumeOnCompletion extends JobNode {
    private final Continuation<Unit> continuation;

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public ResumeOnCompletion(Continuation<? super Unit> continuation2) {
        this.continuation = continuation2;
    }

    public void invoke(Throwable cause) {
        Continuation<Unit> continuation2 = this.continuation;
        Result.Companion companion = Result.Companion;
        continuation2.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
    }
}

package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(mo112d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0014J\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u000bR\"\u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n0\tX\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, mo113d2 = {"Lkotlinx/coroutines/UndispatchedCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "threadStateToRecover", "Ljava/lang/ThreadLocal;", "Lkotlin/Pair;", "", "afterResume", "", "state", "clearThreadContext", "", "saveThreadContext", "oldValue", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineContext.kt */
public final class UndispatchedCoroutine<T> extends ScopeCoroutine<T> {
    private ThreadLocal<Pair<CoroutineContext, Object>> threadStateToRecover;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UndispatchedCoroutine(CoroutineContext context, Continuation<? super T> uCont) {
        super(context.get(UndispatchedMarker.INSTANCE) == null ? context.plus(UndispatchedMarker.INSTANCE) : context, uCont);
        this.threadStateToRecover = new ThreadLocal<>();
    }

    public final void saveThreadContext(CoroutineContext context, Object oldValue) {
        this.threadStateToRecover.set(TuplesKt.m281to(context, oldValue));
    }

    public final boolean clearThreadContext() {
        if (this.threadStateToRecover.get() == null) {
            return false;
        }
        this.threadStateToRecover.set((Object) null);
        return true;
    }

    /* access modifiers changed from: protected */
    public void afterResume(Object state) {
        Pair $dstr$ctx$value = this.threadStateToRecover.get();
        UndispatchedCoroutine<?> undispatchedCoroutine = null;
        if ($dstr$ctx$value != null) {
            ThreadContextKt.restoreThreadContext((CoroutineContext) $dstr$ctx$value.component1(), $dstr$ctx$value.component2());
            this.threadStateToRecover.set(undispatchedCoroutine);
        }
        Object result = CompletionStateKt.recoverResult(state, this.uCont);
        Continuation continuation$iv = this.uCont;
        CoroutineContext context$iv = continuation$iv.getContext();
        Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, (Object) null);
        if (oldValue$iv != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation$iv, context$iv, oldValue$iv);
        } else {
            UndispatchedCoroutine undispatchedCoroutine2 = undispatchedCoroutine;
        }
        try {
            this.uCont.resumeWith(result);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
            }
        }
    }
}

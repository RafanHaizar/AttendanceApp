package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.LimitedDispatcher;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

@Metadata(mo112d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\tj\u0002`\nH&J\u001c\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\tj\u0002`\nH\u0017J \u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\r\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014H\u0017J\u0011\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0002J\u0012\u0010\u0017\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\rJ\b\u0010\u0018\u001a\u00020\u0019H\u0016¨\u0006\u001b"}, mo113d2 = {"Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "isDispatchNeeded", "", "limitedParallelism", "parallelism", "", "plus", "other", "releaseInterceptedContinuation", "toString", "", "Key", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineDispatcher.kt */
public abstract class CoroutineDispatcher extends AbstractCoroutineContextElement implements ContinuationInterceptor {
    public static final Key Key = new Key((DefaultConstructorMarker) null);

    public abstract void dispatch(CoroutineContext coroutineContext, Runnable runnable);

    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        return ContinuationInterceptor.DefaultImpls.get(this, key);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        return ContinuationInterceptor.DefaultImpls.minusKey(this, key);
    }

    public CoroutineDispatcher() {
        super(ContinuationInterceptor.Key);
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"Lkotlinx/coroutines/CoroutineDispatcher$Key;", "Lkotlin/coroutines/AbstractCoroutineContextKey;", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: CoroutineDispatcher.kt */
    public static final class Key extends AbstractCoroutineContextKey<ContinuationInterceptor, CoroutineDispatcher> {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
            super(ContinuationInterceptor.Key, C00051.INSTANCE);
        }
    }

    public boolean isDispatchNeeded(CoroutineContext context) {
        return true;
    }

    public CoroutineDispatcher limitedParallelism(int parallelism) {
        LimitedDispatcherKt.checkParallelism(parallelism);
        return new LimitedDispatcher(this, parallelism);
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        dispatch(context, block);
    }

    public final <T> Continuation<T> interceptContinuation(Continuation<? super T> continuation) {
        return new DispatchedContinuation<>(this, continuation);
    }

    public final void releaseInterceptedContinuation(Continuation<?> continuation) {
        ((DispatchedContinuation) continuation).release();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")
    public final CoroutineDispatcher plus(CoroutineDispatcher other) {
        return other;
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this);
    }
}

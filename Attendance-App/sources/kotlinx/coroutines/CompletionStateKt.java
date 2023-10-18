package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(mo112d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001aI\u0010\b\u001a\u0004\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012%\b\u0002\u0010\t\u001a\u001f\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a.\u0010\b\u001a\u0004\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, mo113d2 = {"recoverResult", "Lkotlin/Result;", "T", "state", "", "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toState", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CompletionState.kt */
public final class CompletionStateKt {
    public static /* synthetic */ Object toState$default(Object obj, Function1 function1, int i, Object obj2) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        return toState(obj, (Function1<? super Throwable, Unit>) function1);
    }

    public static final <T> Object toState(Object $this$toState, Function1<? super Throwable, Unit> onCancellation) {
        Throwable it = Result.m1348exceptionOrNullimpl($this$toState);
        if (it != null) {
            return new CompletedExceptionally(it, false, 2, (DefaultConstructorMarker) null);
        }
        Object it2 = $this$toState;
        if (onCancellation != null) {
            return new CompletedWithCancellation(it2, onCancellation);
        }
        return it2;
    }

    public static final <T> Object toState(Object $this$toState, CancellableContinuation<?> caller) {
        Throwable th;
        Throwable it = Result.m1348exceptionOrNullimpl($this$toState);
        if (it == null) {
            return $this$toState;
        }
        if (!DebugKt.getRECOVER_STACK_TRACES() || !(caller instanceof CoroutineStackFrame)) {
            th = it;
        } else {
            th = StackTraceRecoveryKt.recoverFromStackFrame(it, (CoroutineStackFrame) caller);
        }
        return new CompletedExceptionally(th, false, 2, (DefaultConstructorMarker) null);
    }

    public static final <T> Object recoverResult(Object state, Continuation<? super T> uCont) {
        if (state instanceof CompletedExceptionally) {
            Result.Companion companion = Result.Companion;
            Throwable exception$iv = ((CompletedExceptionally) state).cause;
            if (DebugKt.getRECOVER_STACK_TRACES() && (uCont instanceof CoroutineStackFrame)) {
                exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) uCont);
            }
            return Result.m1345constructorimpl(ResultKt.createFailure(exception$iv));
        }
        Result.Companion companion2 = Result.Companion;
        return Result.m1345constructorimpl(state);
    }
}

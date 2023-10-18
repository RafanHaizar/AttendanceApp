package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

@Metadata(mo112d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001c\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a#\u0010\u0006\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\b\u001a\u001e\u0010\t\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00032\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0000\u001a>\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u000b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u0003\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0003H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001ay\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u000b*\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u0003\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00102\u0006\u0010\u0011\u001a\u0002H\u000f2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00032%\b\u0002\u0010\u0012\u001a\u001f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, mo113d2 = {"dispatcherFailure", "", "completion", "Lkotlin/coroutines/Continuation;", "e", "", "runSafely", "block", "Lkotlin/Function0;", "startCoroutineCancellable", "fatalCompletion", "T", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "onCancellation", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Cancellable.kt */
public final class CancellableKt {
    public static final <T> void startCoroutineCancellable(Function1<? super Continuation<? super T>, ? extends Object> $this$startCoroutineCancellable, Continuation<? super T> completion) {
        try {
            Continuation<Unit> intercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutineCancellable, completion));
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith$default(intercepted, Result.m1345constructorimpl(Unit.INSTANCE), (Function1) null, 2, (Object) null);
        } catch (Throwable e$iv) {
            dispatcherFailure(completion, e$iv);
        }
    }

    public static /* synthetic */ void startCoroutineCancellable$default(Function2 function2, Object obj, Continuation continuation, Function1 function1, int i, Object obj2) {
        if ((i & 4) != 0) {
            function1 = null;
        }
        startCoroutineCancellable(function2, obj, continuation, function1);
    }

    public static final <R, T> void startCoroutineCancellable(Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutineCancellable, R receiver, Continuation<? super T> completion, Function1<? super Throwable, Unit> onCancellation) {
        try {
            Continuation<Unit> intercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutineCancellable, receiver, completion));
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith(intercepted, Result.m1345constructorimpl(Unit.INSTANCE), onCancellation);
        } catch (Throwable e$iv) {
            dispatcherFailure(completion, e$iv);
        }
    }

    public static final void startCoroutineCancellable(Continuation<? super Unit> $this$startCoroutineCancellable, Continuation<?> fatalCompletion) {
        try {
            Continuation<? super Unit> intercepted = IntrinsicsKt.intercepted($this$startCoroutineCancellable);
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith$default(intercepted, Result.m1345constructorimpl(Unit.INSTANCE), (Function1) null, 2, (Object) null);
        } catch (Throwable e$iv) {
            dispatcherFailure(fatalCompletion, e$iv);
        }
    }

    private static final void runSafely(Continuation<?> completion, Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable e) {
            dispatcherFailure(completion, e);
        }
    }

    private static final void dispatcherFailure(Continuation<?> completion, Throwable e) {
        Result.Companion companion = Result.Companion;
        completion.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(e)));
        throw e;
    }
}

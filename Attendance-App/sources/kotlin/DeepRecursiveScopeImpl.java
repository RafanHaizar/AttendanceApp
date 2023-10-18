package kotlin;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(mo112d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004BK\u00129\u0010\u0005\u001a5\b\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b\u0012\u0006\u0010\t\u001a\u00028\u0000ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u0019\u0010\u0015\u001a\u00028\u00012\u0006\u0010\t\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0016Jc\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000429\u0010\u0018\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b2\u000e\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\u0013H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u001cJ\u000b\u0010\u001d\u001a\u00028\u0001¢\u0006\u0002\u0010\u001eJ5\u0010\u0015\u001a\u0002H\u001f\"\u0004\b\u0002\u0010 \"\u0004\b\u0003\u0010\u001f*\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H\u001f0!2\u0006\u0010\t\u001a\u0002H H@ø\u0001\u0000¢\u0006\u0002\u0010\"R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fRF\u0010\u0010\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\bX\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0011R\u001e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0013X\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0014R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006#"}, mo113d2 = {"Lkotlin/DeepRecursiveScopeImpl;", "T", "R", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)V", "cont", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "function", "Lkotlin/jvm/functions/Function3;", "result", "Lkotlin/Result;", "Ljava/lang/Object;", "callRecursive", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crossFunctionCompletion", "currentFunction", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resumeWith", "", "(Ljava/lang/Object;)V", "runCallLoop", "()Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: DeepRecursive.kt */
final class DeepRecursiveScopeImpl<T, R> extends DeepRecursiveScope<T, R> implements Continuation<R> {
    /* access modifiers changed from: private */
    public Continuation<Object> cont = this;
    /* access modifiers changed from: private */
    public Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> function;
    /* access modifiers changed from: private */
    public Object result = DeepRecursiveKt.UNDEFINED_RESULT;
    private Object value;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DeepRecursiveScopeImpl(Function3<? super DeepRecursiveScope<T, R>, ? super T, ? super Continuation<? super R>, ? extends Object> block, T value2) {
        super((DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(block, "block");
        this.function = block;
        this.value = value2;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }

    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    public void resumeWith(Object result2) {
        this.cont = null;
        this.result = result2;
    }

    public Object callRecursive(T value2, Continuation<? super R> $completion) {
        Continuation cont2 = $completion;
        Intrinsics.checkNotNull(cont2, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        this.cont = cont2;
        this.value = value2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return coroutine_suspended;
    }

    public <U, S> Object callRecursive(DeepRecursiveFunction<U, S> $this$callRecursive, U value2, Continuation<? super S> $completion) {
        Continuation cont2 = $completion;
        Function3 function2 = $this$callRecursive.getBlock$kotlin_stdlib();
        Intrinsics.checkNotNull(function2, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.coroutines.SuspendFunction2<kotlin.DeepRecursiveScope<*, *>, kotlin.Any?, kotlin.Any?>{ kotlin.DeepRecursiveKt.DeepRecursiveFunctionBlock }");
        DeepRecursiveScopeImpl $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1 = this;
        Function3 currentFunction = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function;
        if (function2 != currentFunction) {
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function = function2;
            Intrinsics.checkNotNull(cont2, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.crossFunctionCompletion(currentFunction, cont2);
        } else {
            Intrinsics.checkNotNull(cont2, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = cont2;
        }
        $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.value = value2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return coroutine_suspended;
    }

    private final Continuation<Object> crossFunctionCompletion(Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> currentFunction, Continuation<Object> cont2) {
        return new C1590x3ce6e4bb(EmptyCoroutineContext.INSTANCE, this, currentFunction, cont2);
    }

    public final R runCallLoop() {
        while (true) {
            Object result2 = this.result;
            Continuation cont2 = this.cont;
            if (cont2 == null) {
                ResultKt.throwOnFailure(result2);
                return result2;
            } else if (Result.m1347equalsimpl0(DeepRecursiveKt.UNDEFINED_RESULT, result2)) {
                try {
                    Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> function3 = this.function;
                    Object obj = this.value;
                    Intrinsics.checkNotNull(function3, "null cannot be cast to non-null type kotlin.Function3<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.startCoroutineUninterceptedOrReturn, P of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.startCoroutineUninterceptedOrReturn, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.startCoroutineUninterceptedOrReturn>, kotlin.Any?>");
                    Object r = ((Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function3, 3)).invoke(this, obj, cont2);
                    if (r != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        Result.Companion companion = Result.Companion;
                        cont2.resumeWith(Result.m1345constructorimpl(r));
                    }
                } catch (Throwable e) {
                    Result.Companion companion2 = Result.Companion;
                    cont2.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(e)));
                }
            } else {
                this.result = DeepRecursiveKt.UNDEFINED_RESULT;
                cont2.resumeWith(result2);
            }
        }
    }
}

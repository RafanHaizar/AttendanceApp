package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u00032\u00020\u0004B\u001b\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ'\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u001bJ%\u0010\u001a\u001a\u0004\u0018\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001eJ\u001a\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!2\b\u0010\u0018\u001a\u0004\u0018\u00010\u001cH\u0002J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J \u0010$\u001a\u00020\u001c2\u000e\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0&H\u0016ø\u0001\u0000¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0010H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\r8\u0000X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0000X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006)"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/SafeCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collector", "collectContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collectContextSize", "", "completion", "Lkotlin/coroutines/Continuation;", "", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "lastEmissionContext", "checkContext", "currentContext", "previousContext", "value", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "uCont", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)Ljava/lang/Object;", "exceptionTransparencyViolated", "exception", "Lkotlinx/coroutines/flow/internal/DownstreamExceptionContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.kt */
public final class SafeCollector<T> extends ContinuationImpl implements FlowCollector<T>, CoroutineStackFrame {
    public final CoroutineContext collectContext;
    public final int collectContextSize;
    public final FlowCollector<T> collector;
    private Continuation<? super Unit> completion;
    private CoroutineContext lastEmissionContext;

    public SafeCollector(FlowCollector<? super T> collector2, CoroutineContext collectContext2) {
        super(NoOpContinuation.INSTANCE, EmptyCoroutineContext.INSTANCE);
        this.collector = collector2;
        this.collectContext = collectContext2;
        this.collectContextSize = ((Number) collectContext2.fold(0, SafeCollector$collectContextSize$1.INSTANCE)).intValue();
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<? super Unit> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this.lastEmissionContext;
        return coroutineContext == null ? EmptyCoroutineContext.INSTANCE : coroutineContext;
    }

    public Object invokeSuspend(Object result) {
        Throwable it = Result.m1348exceptionOrNullimpl(result);
        if (it != null) {
            this.lastEmissionContext = new DownstreamExceptionContext(it, getContext());
        }
        Continuation<? super Unit> continuation = this.completion;
        if (continuation != null) {
            continuation.resumeWith(result);
        }
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    public void releaseIntercepted() {
        super.releaseIntercepted();
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Continuation uCont = $completion;
        try {
            Object emit = emit((Continuation<? super Unit>) uCont, value);
            if (emit == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return emit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
        } catch (Throwable e) {
            this.lastEmissionContext = new DownstreamExceptionContext(e, uCont.getContext());
            throw e;
        }
    }

    private final Object emit(Continuation<? super Unit> uCont, T value) {
        CoroutineContext currentContext = uCont.getContext();
        JobKt.ensureActive(currentContext);
        CoroutineContext previousContext = this.lastEmissionContext;
        if (previousContext != currentContext) {
            checkContext(currentContext, previousContext, value);
            this.lastEmissionContext = currentContext;
        }
        this.completion = uCont;
        Object result = SafeCollectorKt.emitFun.invoke(this.collector, value, this);
        if (!Intrinsics.areEqual(result, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
            this.completion = null;
        }
        return result;
    }

    private final void checkContext(CoroutineContext currentContext, CoroutineContext previousContext, T value) {
        if (previousContext instanceof DownstreamExceptionContext) {
            exceptionTransparencyViolated((DownstreamExceptionContext) previousContext, value);
        }
        SafeCollector_commonKt.checkContext(this, currentContext);
    }

    private final void exceptionTransparencyViolated(DownstreamExceptionContext exception, Object value) {
        throw new IllegalStateException(StringsKt.trimIndent("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception " + exception.f11e + ", but then emission attempt of value '" + value + "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ").toString());
    }
}

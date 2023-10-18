package kotlinx.coroutines.selects;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilder;

@Metadata(mo112d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0001\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020Y2\b\u0012\u0004\u0012\u00028\u00000Z2\b\u0012\u0004\u0012\u00028\u00000[2\b\u0012\u0004\u0012\u00028\u00000\u00022\u00060Bj\u0002`C:\u0004TUVWB\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ.\u0010\u0011\u001a\u00020\b2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\rH\b¢\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\n\u0018\u00010\u0015j\u0004\u0018\u0001`\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\bH\u0002¢\u0006\u0004\b\u001d\u0010\fJ8\u0010!\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u001e2\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0016ø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0019\u0010%\u001a\u0004\u0018\u00010\u000e2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b%\u0010&J\u0017\u0010(\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u0019H\u0016¢\u0006\u0004\b(\u0010\u001cJ \u0010+\u001a\u00020\b2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000)H\u0016ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u000f\u0010.\u001a\u00020-H\u0016¢\u0006\u0004\b.\u0010/J\u000f\u00101\u001a\u000200H\u0016¢\u0006\u0004\b1\u00102J\u001b\u00105\u001a\u0004\u0018\u00010\u000e2\b\u00104\u001a\u0004\u0018\u000103H\u0016¢\u0006\u0004\b5\u00106J5\u00108\u001a\u00020\b*\u0002072\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0002ø\u0001\u0000¢\u0006\u0004\b8\u00109JG\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010:*\b\u0012\u0004\u0012\u00028\u00010;2\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010=J[\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010>\"\u0004\b\u0002\u0010:*\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020?2\u0006\u0010@\u001a\u00028\u00012\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010AR\u001c\u0010F\u001a\n\u0018\u00010Bj\u0004\u0018\u0001`C8VX\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028VX\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0014\u0010M\u001a\u00020J8VX\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0014\u0010N\u001a\u0002008VX\u0004¢\u0006\u0006\u001a\u0004\bN\u00102R(\u0010R\u001a\u0004\u0018\u00010\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u00068B@BX\u000e¢\u0006\f\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010\nR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010S\u0002\u0004\n\u0002\b\u0019¨\u0006X"}, mo113d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl;", "R", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/DisposableHandle;", "handle", "", "disposeOnSelect", "(Lkotlinx/coroutines/DisposableHandle;)V", "doAfterSelect", "()V", "Lkotlin/Function0;", "", "value", "block", "doResume", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "e", "handleBuilderException", "(Ljava/lang/Throwable;)V", "initCancellability", "", "timeMillis", "Lkotlin/Function1;", "onTimeout", "(JLkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/AtomicDesc;", "desc", "performAtomicTrySelect", "(Lkotlinx/coroutines/internal/AtomicDesc;)Ljava/lang/Object;", "exception", "resumeSelectWithException", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "", "trySelect", "()Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "otherOp", "trySelectOther", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectClause0;", "invoke", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getCompletion", "()Lkotlin/coroutines/Continuation;", "completion", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "context", "isSelected", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setParentHandle", "parentHandle", "Lkotlin/coroutines/Continuation;", "AtomicSelectOp", "DisposeNode", "PairSelectOp", "SelectOnCancelling", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstance;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Select.kt */
public final class SelectBuilderImpl<R> extends LockFreeLinkedListHead implements SelectBuilder<R>, SelectInstance<R>, Continuation<R>, CoroutineStackFrame {
    private static final /* synthetic */ AtomicReferenceFieldUpdater _result$FU;
    static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU;
    private volatile /* synthetic */ Object _parentHandle = null;
    private volatile /* synthetic */ Object _result = SelectKt.UNDECIDED;
    volatile /* synthetic */ Object _state = SelectKt.getNOT_SELECTED();
    private final Continuation<R> uCont;

    static {
        Class<SelectBuilderImpl> cls = SelectBuilderImpl.class;
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state");
        _result$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_result");
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        SelectBuilder.DefaultImpls.invoke(this, $this$invoke, block);
    }

    public SelectBuilderImpl(Continuation<? super R> uCont2) {
        this.uCont = uCont2;
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<R> continuation = this.uCont;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) this._parentHandle;
    }

    private final void setParentHandle(DisposableHandle value) {
        this._parentHandle = value;
    }

    public CoroutineContext getContext() {
        return this.uCont.getContext();
    }

    public Continuation<R> getCompletion() {
        return this;
    }

    private final void doResume(Function0<? extends Object> value, Function0<Unit> block) {
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result = this._result;
                if (result == SelectKt.UNDECIDED) {
                    if (C0613xc40028dd.m151m(_result$FU, this, SelectKt.UNDECIDED, value.invoke())) {
                        return;
                    }
                } else if (result != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (C0613xc40028dd.m151m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    block.invoke();
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public void resumeWith(Object result) {
        Throwable th;
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result$iv = this._result;
                if (result$iv == SelectKt.UNDECIDED) {
                    if (C0613xc40028dd.m151m(_result$FU, this, SelectKt.UNDECIDED, CompletionStateKt.toState$default(result, (Function1) null, 1, (Object) null))) {
                        return;
                    }
                } else if (result$iv != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (C0613xc40028dd.m151m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    if (Result.m1351isFailureimpl(result)) {
                        Continuation<R> continuation = this.uCont;
                        Throwable exception$iv = Result.m1348exceptionOrNullimpl(result);
                        Intrinsics.checkNotNull(exception$iv);
                        Result.Companion companion = Result.Companion;
                        if (!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) {
                            th = exception$iv;
                        } else {
                            th = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) continuation);
                        }
                        continuation.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(th)));
                        return;
                    }
                    this.uCont.resumeWith(result);
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public void resumeSelectWithException(Throwable exception) {
        Throwable th;
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result$iv = this._result;
                if (result$iv == SelectKt.UNDECIDED) {
                    Continuation<R> continuation = this.uCont;
                    if (!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) {
                        th = exception;
                    } else {
                        th = StackTraceRecoveryKt.recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
                    }
                    if (C0613xc40028dd.m151m(_result$FU, this, SelectKt.UNDECIDED, new CompletedExceptionally(th, false, 2, (DefaultConstructorMarker) null))) {
                        return;
                    }
                } else if (result$iv != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (C0613xc40028dd.m151m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    Continuation<R> intercepted = IntrinsicsKt.intercepted(this.uCont);
                    Result.Companion companion = Result.Companion;
                    intercepted.resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(exception)));
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final Object getResult() {
        if (!isSelected()) {
            initCancellability();
        }
        Object result = this._result;
        if (result == SelectKt.UNDECIDED) {
            if (C0613xc40028dd.m151m(_result$FU, this, SelectKt.UNDECIDED, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            result = this._result;
        }
        if (result == SelectKt.RESUMED) {
            throw new IllegalStateException("Already resumed");
        } else if (!(result instanceof CompletedExceptionally)) {
            return result;
        } else {
            throw ((CompletedExceptionally) result).cause;
        }
    }

    private final void initCancellability() {
        Job parent = (Job) getContext().get(Job.Key);
        if (parent != null) {
            DisposableHandle newRegistration = Job.DefaultImpls.invokeOnCompletion$default(parent, true, false, new SelectOnCancelling(), 2, (Object) null);
            setParentHandle(newRegistration);
            if (isSelected()) {
                newRegistration.dispose();
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002¨\u0006\u0007"}, mo113d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$SelectOnCancelling;", "Lkotlinx/coroutines/JobCancellingNode;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Select.kt */
    private final class SelectOnCancelling extends JobCancellingNode {
        public SelectOnCancelling() {
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return Unit.INSTANCE;
        }

        public void invoke(Throwable cause) {
            if (SelectBuilderImpl.this.trySelect()) {
                SelectBuilderImpl.this.resumeSelectWithException(getJob().getCancellationException());
            }
        }
    }

    public final void handleBuilderException(Throwable e) {
        if (trySelect()) {
            Result.Companion companion = Result.Companion;
            resumeWith(Result.m1345constructorimpl(ResultKt.createFailure(e)));
        } else if (!(e instanceof CancellationException)) {
            Object result = getResult();
            if (result instanceof CompletedExceptionally) {
                Throwable exception$iv = ((CompletedExceptionally) result).cause;
                if (DebugKt.getRECOVER_STACK_TRACES()) {
                    exception$iv = StackTraceRecoveryKt.unwrapImpl(exception$iv);
                }
                if (exception$iv == (!DebugKt.getRECOVER_STACK_TRACES() ? e : StackTraceRecoveryKt.unwrapImpl(e))) {
                    return;
                }
            }
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), e);
        }
    }

    public boolean isSelected() {
        while (true) {
            Object state = this._state;
            if (state == SelectKt.getNOT_SELECTED()) {
                return false;
            }
            if (!(state instanceof OpDescriptor)) {
                return true;
            }
            ((OpDescriptor) state).perform(this);
        }
    }

    public void disposeOnSelect(DisposableHandle handle) {
        DisposeNode node = new DisposeNode(handle);
        if (!isSelected()) {
            addLast(node);
            if (!isSelected()) {
                return;
            }
        }
        handle.dispose();
    }

    /* access modifiers changed from: private */
    public final void doAfterSelect() {
        DisposableHandle parentHandle = getParentHandle();
        if (parentHandle != null) {
            parentHandle.dispose();
        }
        LockFreeLinkedListHead this_$iv = this;
        for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) this_$iv.getNext(); !Intrinsics.areEqual((Object) cur$iv, (Object) this_$iv); cur$iv = cur$iv.getNextNode()) {
            if (cur$iv instanceof DisposeNode) {
                ((DisposeNode) cur$iv).handle.dispose();
            }
        }
    }

    public boolean trySelect() {
        Object result = trySelectOther((LockFreeLinkedListNode.PrepareOp) null);
        if (result == CancellableContinuationImplKt.RESUME_TOKEN) {
            return true;
        }
        if (result == null) {
            return false;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Unexpected trySelectIdempotent result ", result).toString());
    }

    public Object trySelectOther(LockFreeLinkedListNode.PrepareOp otherOp) {
        while (true) {
            Object state = this._state;
            if (state == SelectKt.getNOT_SELECTED()) {
                if (otherOp == null) {
                    if (C0613xc40028dd.m151m(_state$FU, this, SelectKt.getNOT_SELECTED(), (Object) null)) {
                        break;
                    }
                } else {
                    PairSelectOp pairSelectOp = new PairSelectOp(otherOp);
                    if (C0613xc40028dd.m151m(_state$FU, this, SelectKt.getNOT_SELECTED(), pairSelectOp)) {
                        Object decision = pairSelectOp.perform(this);
                        if (decision != null) {
                            return decision;
                        }
                    }
                }
            } else if (state instanceof OpDescriptor) {
                if (otherOp != null) {
                    AtomicOp<?> atomicOp = otherOp.getAtomicOp();
                    if ((atomicOp instanceof AtomicSelectOp) && ((AtomicSelectOp) atomicOp).impl == this) {
                        throw new IllegalStateException("Cannot use matching select clauses on the same object".toString());
                    } else if (atomicOp.isEarlierThan((OpDescriptor) state)) {
                        return AtomicKt.RETRY_ATOMIC;
                    }
                }
                ((OpDescriptor) state).perform(this);
            } else if (otherOp != null && state == otherOp.desc) {
                return CancellableContinuationImplKt.RESUME_TOKEN;
            } else {
                return null;
            }
        }
        doAfterSelect();
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }

    @Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016R\u0018\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, mo113d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$PairSelectOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Select.kt */
    private static final class PairSelectOp extends OpDescriptor {
        public final LockFreeLinkedListNode.PrepareOp otherOp;

        public PairSelectOp(LockFreeLinkedListNode.PrepareOp otherOp2) {
            this.otherOp = otherOp2;
        }

        public Object perform(Object affected) {
            if (affected != null) {
                SelectBuilderImpl impl = (SelectBuilderImpl) affected;
                this.otherOp.finishPrepare();
                Object decision = this.otherOp.getAtomicOp().decide((Object) null);
                C0613xc40028dd.m151m(SelectBuilderImpl._state$FU, impl, this, decision == null ? this.otherOp.desc : SelectKt.getNOT_SELECTED());
                return decision;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.selects.SelectBuilderImpl<*>");
        }

        public AtomicOp<?> getAtomicOp() {
            return this.otherOp.getAtomicOp();
        }
    }

    public Object performAtomicTrySelect(AtomicDesc desc) {
        return new AtomicSelectOp(this, desc).perform((Object) null);
    }

    public String toString() {
        return "SelectInstance(state=" + this._state + ", result=" + this._result + ')';
    }

    @Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0019\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0002J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\rH\u0002R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, mo113d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$AtomicSelectOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "", "impl", "Lkotlinx/coroutines/selects/SelectBuilderImpl;", "desc", "Lkotlinx/coroutines/internal/AtomicDesc;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/internal/AtomicDesc;)V", "opSequence", "", "getOpSequence", "()J", "complete", "", "affected", "failure", "completeSelect", "prepare", "prepareSelectOp", "toString", "", "undoPrepare", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Select.kt */
    private static final class AtomicSelectOp extends AtomicOp<Object> {
        public final AtomicDesc desc;
        public final SelectBuilderImpl<?> impl;
        private final long opSequence = SelectKt.selectOpSequenceNumber.next();

        public AtomicSelectOp(SelectBuilderImpl<?> impl2, AtomicDesc desc2) {
            this.impl = impl2;
            this.desc = desc2;
            desc2.setAtomicOp(this);
        }

        public long getOpSequence() {
            return this.opSequence;
        }

        public Object prepare(Object affected) {
            Object it;
            if (affected == null && (it = prepareSelectOp()) != null) {
                return it;
            }
            try {
                return this.desc.prepare(this);
            } catch (Throwable e) {
                if (affected == null) {
                    undoPrepare();
                }
                throw e;
            }
        }

        public void complete(Object affected, Object failure) {
            completeSelect(failure);
            this.desc.complete(this, failure);
        }

        private final Object prepareSelectOp() {
            SelectBuilderImpl $this$loop$iv = this.impl;
            while (true) {
                Object state = $this$loop$iv._state;
                if (state == this) {
                    return null;
                }
                if (state instanceof OpDescriptor) {
                    ((OpDescriptor) state).perform(this.impl);
                } else if (state != SelectKt.getNOT_SELECTED()) {
                    return SelectKt.getALREADY_SELECTED();
                } else {
                    if (C0613xc40028dd.m151m(SelectBuilderImpl._state$FU, this.impl, SelectKt.getNOT_SELECTED(), this)) {
                        return null;
                    }
                }
            }
        }

        private final void undoPrepare() {
            C0613xc40028dd.m151m(SelectBuilderImpl._state$FU, this.impl, this, SelectKt.getNOT_SELECTED());
        }

        private final void completeSelect(Object failure) {
            boolean selectSuccess = failure == null;
            if (C0613xc40028dd.m151m(SelectBuilderImpl._state$FU, this.impl, this, selectSuccess ? null : SelectKt.getNOT_SELECTED()) && selectSuccess) {
                this.impl.doAfterSelect();
            }
        }

        public String toString() {
            return "AtomicSelectOp(sequence=" + getOpSequence() + ')';
        }
    }

    public void invoke(SelectClause0 $this$invoke, Function1<? super Continuation<? super R>, ? extends Object> block) {
        $this$invoke.registerSelectClause0(this, block);
    }

    public <Q> void invoke(SelectClause1<? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        $this$invoke.registerSelectClause1(this, block);
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, P param, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        $this$invoke.registerSelectClause2(this, param, block);
    }

    public void onTimeout(long timeMillis, Function1<? super Continuation<? super R>, ? extends Object> block) {
        if (timeMillis > 0) {
            disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(timeMillis, new SelectBuilderImpl$onTimeout$$inlined$Runnable$1(this, block), getContext()));
        } else if (trySelect()) {
            UndispatchedKt.startCoroutineUnintercepted(block, getCompletion());
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo113d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$DisposeNode;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Select.kt */
    private static final class DisposeNode extends LockFreeLinkedListNode {
        public final DisposableHandle handle;

        public DisposeNode(DisposableHandle handle2) {
            this.handle = handle2;
        }
    }
}

package kotlinx.coroutines.flow;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

@Metadata(mo112d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0013B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u001b\u0010\u0006\u001a\u00020\u00052\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\t\u001a\u00020\bH@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ)\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\b\u0018\u00010\f0\u000b2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u000f\u001a\u00020\b¢\u0006\u0004\b\u000f\u0010\u0002J\r\u0010\u0010\u001a\u00020\u0005¢\u0006\u0004\b\u0010\u0010\u0011\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, mo113d2 = {"Lkotlinx/coroutines/flow/StateFlowSlot;", "<init>", "()V", "Lkotlinx/coroutines/flow/StateFlowImpl;", "flow", "", "allocateLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)Z", "", "awaitPending", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "Lkotlin/coroutines/Continuation;", "freeLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)[Lkotlin/coroutines/Continuation;", "makePending", "takePending", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: StateFlow.kt */
final class StateFlowSlot extends AbstractSharedFlowSlot<StateFlowImpl<?>> {
    static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(StateFlowSlot.class, Object.class, "_state");
    volatile /* synthetic */ Object _state = null;

    public boolean allocateLocked(StateFlowImpl<?> flow) {
        if (this._state != null) {
            return false;
        }
        this._state = StateFlowKt.NONE;
        return true;
    }

    public Continuation<Unit>[] freeLocked(StateFlowImpl<?> flow) {
        this._state = null;
        return AbstractSharedFlowKt.EMPTY_RESUMES;
    }

    public final void makePending() {
        while (true) {
            Object state = this._state;
            if (state != null && state != StateFlowKt.PENDING) {
                if (state == StateFlowKt.NONE) {
                    if (C0613xc40028dd.m151m(_state$FU, this, state, StateFlowKt.PENDING)) {
                        return;
                    }
                } else if (C0613xc40028dd.m151m(_state$FU, this, state, StateFlowKt.NONE)) {
                    Result.Companion companion = Result.Companion;
                    ((CancellableContinuationImpl) state).resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final boolean takePending() {
        Object state = _state$FU.getAndSet(this, StateFlowKt.NONE);
        Intrinsics.checkNotNull(state);
        if (DebugKt.getASSERTIONS_ENABLED() && ((state instanceof CancellableContinuationImpl) ^ 1) == 0) {
            throw new AssertionError();
        } else if (state == StateFlowKt.PENDING) {
            return true;
        } else {
            return false;
        }
    }

    public final Object awaitPending(Continuation<? super Unit> $completion) {
        boolean z = true;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        if (!DebugKt.getASSERTIONS_ENABLED() || ((this._state instanceof CancellableContinuationImpl) ^ 1) != 0) {
            if (!C0613xc40028dd.m151m(_state$FU, this, StateFlowKt.NONE, cont)) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (this._state != StateFlowKt.PENDING) {
                        z = false;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
            Object result = cancellable$iv.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        }
        throw new AssertionError();
    }
}

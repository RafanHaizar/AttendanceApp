package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.Task;

@Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, mo113d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", "", "takenState", "", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DispatchedTask.kt */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int resumeMode2) {
        this.resumeMode = resumeMode2;
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally == null) {
            return null;
        }
        return completedExceptionally.cause;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ea, code lost:
        if (r11.clearThreadContext() != false) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x010f, code lost:
        if (r11.clearThreadContext() != false) goto L_0x0111;
     */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e6 A[SYNTHETIC, Splitter:B:52:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x010b A[SYNTHETIC, Splitter:B:63:0x010b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r21 = this;
            r1 = r21
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001a
            r0 = 0
            int r2 = r1.resumeMode
            r3 = -1
            if (r2 == r3) goto L_0x0010
            r2 = 1
            goto L_0x0011
        L_0x0010:
            r2 = 0
        L_0x0011:
            if (r2 == 0) goto L_0x0014
            goto L_0x001a
        L_0x0014:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001a:
            kotlinx.coroutines.scheduling.TaskContext r2 = r1.taskContext
            r3 = 0
            kotlin.coroutines.Continuation r0 = r21.getDelegate$kotlinx_coroutines_core()     // Catch:{ all -> 0x0116 }
            kotlinx.coroutines.internal.DispatchedContinuation r0 = (kotlinx.coroutines.internal.DispatchedContinuation) r0     // Catch:{ all -> 0x0116 }
            r4 = r0
            kotlin.coroutines.Continuation<T> r0 = r4.continuation     // Catch:{ all -> 0x0116 }
            r5 = r0
            java.lang.Object r0 = r4.countOrElement     // Catch:{ all -> 0x0116 }
            r6 = r0
            r7 = 0
            kotlin.coroutines.CoroutineContext r0 = r5.getContext()     // Catch:{ all -> 0x0116 }
            r8 = r0
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r8, r6)     // Catch:{ all -> 0x0116 }
            r9 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0116 }
            r10 = 0
            if (r9 == r0) goto L_0x0040
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r5, r8, r9)     // Catch:{ all -> 0x0116 }
            goto L_0x0044
        L_0x0040:
            r0 = r10
            kotlinx.coroutines.UndispatchedCoroutine r0 = (kotlinx.coroutines.UndispatchedCoroutine) r0     // Catch:{ all -> 0x0116 }
            r0 = r10
        L_0x0044:
            r11 = r0
            r0 = 0
            kotlin.coroutines.CoroutineContext r12 = r5.getContext()     // Catch:{ all -> 0x0104 }
            java.lang.Object r13 = r21.takeState$kotlinx_coroutines_core()     // Catch:{ all -> 0x0104 }
            java.lang.Throwable r14 = r1.getExceptionalResult$kotlinx_coroutines_core(r13)     // Catch:{ all -> 0x0104 }
            if (r14 != 0) goto L_0x006f
            int r15 = r1.resumeMode     // Catch:{ all -> 0x0068 }
            boolean r15 = kotlinx.coroutines.DispatchedTaskKt.isCancellableMode(r15)     // Catch:{ all -> 0x0068 }
            if (r15 == 0) goto L_0x006f
            kotlinx.coroutines.Job$Key r10 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x0068 }
            kotlin.coroutines.CoroutineContext$Key r10 = (kotlin.coroutines.CoroutineContext.Key) r10     // Catch:{ all -> 0x0068 }
            kotlin.coroutines.CoroutineContext$Element r10 = r12.get(r10)     // Catch:{ all -> 0x0068 }
            kotlinx.coroutines.Job r10 = (kotlinx.coroutines.Job) r10     // Catch:{ all -> 0x0068 }
            goto L_0x006f
        L_0x0068:
            r0 = move-exception
            r19 = r4
            r20 = r6
            goto L_0x0109
        L_0x006f:
            if (r10 == 0) goto L_0x00be
            boolean r15 = r10.isActive()     // Catch:{ all -> 0x0104 }
            if (r15 != 0) goto L_0x00be
            java.util.concurrent.CancellationException r15 = r10.getCancellationException()     // Catch:{ all -> 0x0104 }
            r16 = r0
            r0 = r15
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x0104 }
            r1.cancelCompletedResult$kotlinx_coroutines_core(r13, r0)     // Catch:{ all -> 0x0104 }
            r0 = r5
            r17 = 0
            kotlin.Result$Companion r18 = kotlin.Result.Companion     // Catch:{ all -> 0x0104 }
            r18 = 0
            boolean r19 = kotlinx.coroutines.DebugKt.getRECOVER_STACK_TRACES()     // Catch:{ all -> 0x0104 }
            if (r19 == 0) goto L_0x00aa
            r19 = r4
            boolean r4 = r0 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame     // Catch:{ all -> 0x00a6 }
            if (r4 != 0) goto L_0x0099
            r20 = r6
            goto L_0x00ae
        L_0x0099:
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x00a6 }
            r20 = r6
            r6 = r0
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r6 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r6     // Catch:{ all -> 0x0102 }
            java.lang.Throwable r4 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverFromStackFrame(r4, r6)     // Catch:{ all -> 0x0102 }
            goto L_0x00b1
        L_0x00a6:
            r0 = move-exception
            r20 = r6
            goto L_0x0109
        L_0x00aa:
            r19 = r4
            r20 = r6
        L_0x00ae:
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x0102 }
        L_0x00b1:
            java.lang.Object r4 = kotlin.ResultKt.createFailure(r4)     // Catch:{ all -> 0x0102 }
            java.lang.Object r4 = kotlin.Result.m1345constructorimpl(r4)     // Catch:{ all -> 0x0102 }
            r0.resumeWith(r4)     // Catch:{ all -> 0x0102 }
            goto L_0x00e1
        L_0x00be:
            r16 = r0
            r19 = r4
            r20 = r6
            if (r14 == 0) goto L_0x00d4
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0102 }
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r14)     // Catch:{ all -> 0x0102 }
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r0)     // Catch:{ all -> 0x0102 }
            r5.resumeWith(r0)     // Catch:{ all -> 0x0102 }
            goto L_0x00e1
        L_0x00d4:
            java.lang.Object r0 = r1.getSuccessfulResult$kotlinx_coroutines_core(r13)     // Catch:{ all -> 0x0102 }
            kotlin.Result$Companion r4 = kotlin.Result.Companion     // Catch:{ all -> 0x0102 }
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r0)     // Catch:{ all -> 0x0102 }
            r5.resumeWith(r0)     // Catch:{ all -> 0x0102 }
        L_0x00e1:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0102 }
            if (r11 == 0) goto L_0x00ec
            boolean r0 = r11.clearThreadContext()     // Catch:{ all -> 0x0116 }
            if (r0 == 0) goto L_0x00ef
        L_0x00ec:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r8, r9)     // Catch:{ all -> 0x0116 }
        L_0x00ef:
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0100 }
            r0 = r1
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0     // Catch:{ all -> 0x0100 }
            r4 = 0
            r2.afterTask()     // Catch:{ all -> 0x0100 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0100 }
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r0)     // Catch:{ all -> 0x0100 }
            goto L_0x0133
        L_0x0100:
            r0 = move-exception
            goto L_0x0129
        L_0x0102:
            r0 = move-exception
            goto L_0x0109
        L_0x0104:
            r0 = move-exception
            r19 = r4
            r20 = r6
        L_0x0109:
            if (r11 == 0) goto L_0x0111
            boolean r4 = r11.clearThreadContext()     // Catch:{ all -> 0x0116 }
            if (r4 == 0) goto L_0x0114
        L_0x0111:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r8, r9)     // Catch:{ all -> 0x0116 }
        L_0x0114:
            throw r0     // Catch:{ all -> 0x0116 }
        L_0x0116:
            r0 = move-exception
            r3 = r0
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0128 }
            r0 = r1
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0     // Catch:{ all -> 0x0128 }
            r4 = 0
            r2.afterTask()     // Catch:{ all -> 0x0128 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0128 }
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r0)     // Catch:{ all -> 0x0128 }
            goto L_0x0133
        L_0x0128:
            r0 = move-exception
        L_0x0129:
            kotlin.Result$Companion r4 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r0)
        L_0x0133:
            java.lang.Throwable r4 = kotlin.Result.m1348exceptionOrNullimpl(r0)
            r1.handleFatalException(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedTask.run():void");
    }

    public final void handleFatalException(Throwable exception, Throwable finallyException) {
        if (exception != null || finallyException != null) {
            if (!(exception == null || finallyException == null)) {
                ExceptionsKt.addSuppressed(exception, finallyException);
            }
            Throwable cause = exception == null ? finallyException : exception;
            Intrinsics.checkNotNull(cause);
            CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", cause));
        }
    }
}

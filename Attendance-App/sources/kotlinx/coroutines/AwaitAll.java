package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u000e:\u0002\u000b\fB\u001d\u0012\u0014\u0010\u0004\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0002¢\u0006\u0004\b\u0005\u0010\u0006J\u0019\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H@ø\u0001\u0000¢\u0006\u0004\b\b\u0010\tR\"\u0010\u0004\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00028\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010\n\u0002\u0004\n\u0002\b\u0019¨\u0006\r"}, mo113d2 = {"Lkotlinx/coroutines/AwaitAll;", "T", "", "Lkotlinx/coroutines/Deferred;", "deferreds", "<init>", "([Lkotlinx/coroutines/Deferred;)V", "", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "[Lkotlinx/coroutines/Deferred;", "AwaitAllNode", "DisposeHandlersOnCancel", "kotlinx-coroutines-core", ""}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Await.kt */
final class AwaitAll<T> {
    static final /* synthetic */ AtomicIntegerFieldUpdater notCompletedCount$FU = AtomicIntegerFieldUpdater.newUpdater(AwaitAll.class, "notCompletedCount");
    /* access modifiers changed from: private */
    public final Deferred<T>[] deferreds;
    volatile /* synthetic */ int notCompletedCount;

    public AwaitAll(Deferred<? extends T>[] deferreds2) {
        this.deferreds = deferreds2;
        this.notCompletedCount = deferreds2.length;
    }

    public final Object await(Continuation<? super List<? extends T>> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        int length = this.deferreds.length;
        AwaitAllNode[] awaitAllNodeArr = new AwaitAllNode[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            Deferred deferred = this.deferreds[i2];
            deferred.start();
            AwaitAllNode awaitAllNode = new AwaitAllNode(cont);
            AwaitAllNode $this$await_u24lambda_u2d2_u24lambda_u2d0 = awaitAllNode;
            $this$await_u24lambda_u2d2_u24lambda_u2d0.setHandle(deferred.invokeOnCompletion($this$await_u24lambda_u2d2_u24lambda_u2d0));
            Unit unit = Unit.INSTANCE;
            awaitAllNodeArr[i2] = awaitAllNode;
        }
        AwaitAllNode[] nodes = awaitAllNodeArr;
        DisposeHandlersOnCancel disposer = new DisposeHandlersOnCancel(nodes);
        AwaitAllNode[] awaitAllNodeArr2 = nodes;
        int length2 = awaitAllNodeArr2.length;
        while (i < length2) {
            AwaitAllNode it = awaitAllNodeArr2[i];
            i++;
            it.setDisposer(disposer);
        }
        if (cont.isCompleted()) {
            disposer.disposeAll();
        } else {
            cont.invokeOnCancellation(disposer);
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    @Metadata(mo112d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u000e\u0012\f0\u0004R\b\u0012\u0004\u0012\u00028\u00000\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0006\u0010\b\u001a\u00020\tJ\u0013\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R \u0010\u0002\u001a\u0012\u0012\u000e\u0012\f0\u0004R\b\u0012\u0004\u0012\u00028\u00000\u00050\u0003X\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\u000f"}, mo113d2 = {"Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "nodes", "", "Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/AwaitAll;", "(Lkotlinx/coroutines/AwaitAll;[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;)V", "[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "disposeAll", "", "invoke", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Await.kt */
    private final class DisposeHandlersOnCancel extends CancelHandler {
        private final AwaitAll<T>.AwaitAllNode[] nodes;

        public DisposeHandlersOnCancel(AwaitAll<T>.AwaitAllNode[] nodes2) {
            this.nodes = nodes2;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return Unit.INSTANCE;
        }

        public final void disposeAll() {
            AwaitAllNode[] awaitAllNodeArr = this.nodes;
            int length = awaitAllNodeArr.length;
            int i = 0;
            while (i < length) {
                AwaitAll<T>.AwaitAllNode it = awaitAllNodeArr[i];
                i++;
                it.getHandle().dispose();
            }
        }

        public void invoke(Throwable cause) {
            disposeAll();
        }

        public String toString() {
            return "DisposeHandlersOnCancel[" + this.nodes + ']';
        }
    }

    @Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\b\u0004\u0018\u00002\u00020\u001cB\u001b\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\t\u0010\nR \u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u00018\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u000bR<\u0010\u0013\u001a\u000e\u0018\u00010\fR\b\u0012\u0004\u0012\u00028\u00000\r2\u0012\u0010\u000e\u001a\u000e\u0018\u00010\fR\b\u0012\u0004\u0012\u00028\u00000\r8F@FX\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\"\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X.¢\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, mo113d2 = {"Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/CancellableContinuation;", "", "continuation", "<init>", "(Lkotlinx/coroutines/AwaitAll;Lkotlinx/coroutines/CancellableContinuation;)V", "", "cause", "", "invoke", "(Ljava/lang/Throwable;)V", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/AwaitAll;", "value", "getDisposer", "()Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "setDisposer", "(Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;)V", "disposer", "Lkotlinx/coroutines/DisposableHandle;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "getHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setHandle", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core", "Lkotlinx/coroutines/JobNode;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Await.kt */
    private final class AwaitAllNode extends JobNode {
        private volatile /* synthetic */ Object _disposer = null;
        private final CancellableContinuation<List<? extends T>> continuation;
        public DisposableHandle handle;

        public AwaitAllNode(CancellableContinuation<? super List<? extends T>> continuation2) {
            this.continuation = continuation2;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return Unit.INSTANCE;
        }

        public final DisposableHandle getHandle() {
            DisposableHandle disposableHandle = this.handle;
            if (disposableHandle != null) {
                return disposableHandle;
            }
            Intrinsics.throwUninitializedPropertyAccessException("handle");
            return null;
        }

        public final void setHandle(DisposableHandle disposableHandle) {
            this.handle = disposableHandle;
        }

        public final AwaitAll<T>.DisposeHandlersOnCancel getDisposer() {
            return (DisposeHandlersOnCancel) this._disposer;
        }

        public final void setDisposer(AwaitAll<T>.DisposeHandlersOnCancel value) {
            this._disposer = value;
        }

        public void invoke(Throwable cause) {
            if (cause != null) {
                Object token = this.continuation.tryResumeWithException(cause);
                if (token != null) {
                    this.continuation.completeResume(token);
                    AwaitAll<T>.DisposeHandlersOnCancel disposer = getDisposer();
                    if (disposer != null) {
                        disposer.disposeAll();
                        return;
                    }
                    return;
                }
                return;
            }
            if (AwaitAll.notCompletedCount$FU.decrementAndGet(AwaitAll.this) == 0) {
                Continuation continuation2 = this.continuation;
                Result.Companion companion = Result.Companion;
                Deferred[] access$getDeferreds$p = AwaitAll.this.deferreds;
                Collection destination$iv$iv = new ArrayList(access$getDeferreds$p.length);
                Deferred[] deferredArr = access$getDeferreds$p;
                int length = deferredArr.length;
                int i = 0;
                while (i < length) {
                    Deferred it = deferredArr[i];
                    i++;
                    destination$iv$iv.add(it.getCompleted());
                }
                continuation2.resumeWith(Result.m1345constructorimpl((List) destination$iv$iv));
            }
        }
    }
}

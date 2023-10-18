package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;

@Metadata(mo112d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0017J\u0012\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0017J\u0018\u0010\u001c\u001a\u00020\u001f2\u000e\u0010\u001d\u001a\n\u0018\u00010 j\u0004\u0018\u0001`!H\u0017J\f\u0010\"\u001a\u00060 j\u0002`!H\u0017JA\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\n2'\u0010'\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u001e¢\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u001f0(j\u0002`+H\u0017J1\u0010#\u001a\u00020$2'\u0010'\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u001e¢\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u001f0(j\u0002`+H\u0017J\u0011\u0010,\u001a\u00020\u001fH@ø\u0001\u0000¢\u0006\u0002\u0010-J\b\u0010.\u001a\u00020\nH\u0017J\b\u0010/\u001a\u00020\u0012H\u0016R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00058VX\u0004¢\u0006\f\u0012\u0004\b\u0006\u0010\u0003\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\n8VX\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\u0003\u001a\u0004\b\t\u0010\fR\u001a\u0010\r\u001a\u00020\n8VX\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0003\u001a\u0004\b\r\u0010\fR\u001a\u0010\u000f\u001a\u00020\n8VX\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0011\u001a\u00020\u0012XT¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u00148VX\u0004¢\u0006\f\u0012\u0004\b\u0015\u0010\u0003\u001a\u0004\b\u0016\u0010\u0017\u0002\u0004\n\u0002\b\u0019¨\u00060"}, mo113d2 = {"Lkotlinx/coroutines/NonCancellable;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/Job;", "()V", "children", "Lkotlin/sequences/Sequence;", "getChildren$annotations", "getChildren", "()Lkotlin/sequences/Sequence;", "isActive", "", "isActive$annotations", "()Z", "isCancelled", "isCancelled$annotations", "isCompleted", "isCompleted$annotations", "message", "", "onJoin", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin$annotations", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "attachChild", "Lkotlinx/coroutines/ChildHandle;", "child", "Lkotlinx/coroutines/ChildJob;", "cancel", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "getCancellationException", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "invokeImmediately", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "join", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "toString", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: NonCancellable.kt */
public final class NonCancellable extends AbstractCoroutineContextElement implements Job {
    public static final NonCancellable INSTANCE = new NonCancellable();
    private static final String message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited";

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public static /* synthetic */ void getChildren$annotations() {
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public static /* synthetic */ void getOnJoin$annotations() {
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public static /* synthetic */ void isActive$annotations() {
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public static /* synthetic */ void isCancelled$annotations() {
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public static /* synthetic */ void isCompleted$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public Job plus(Job other) {
        return Job.DefaultImpls.plus((Job) this, other);
    }

    private NonCancellable() {
        super(Job.Key);
    }

    public boolean isActive() {
        return true;
    }

    public boolean isCompleted() {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public boolean start() {
        return false;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public Object join(Continuation<? super Unit> $completion) {
        throw new UnsupportedOperationException("This job is always active");
    }

    public SelectClause0 getOnJoin() {
        throw new UnsupportedOperationException("This job is always active");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public CancellationException getCancellationException() {
        throw new IllegalStateException("This job is always active");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> handler) {
        return NonDisposableHandle.INSTANCE;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public DisposableHandle invokeOnCompletion(boolean onCancelling, boolean invokeImmediately, Function1<? super Throwable, Unit> handler) {
        return NonDisposableHandle.INSTANCE;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public void cancel(CancellationException cause) {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ boolean cancel(Throwable cause) {
        return false;
    }

    public Sequence<Job> getChildren() {
        return SequencesKt.emptySequence();
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
    public ChildHandle attachChild(ChildJob child) {
        return NonDisposableHandle.INSTANCE;
    }

    public String toString() {
        return "NonCancellable";
    }
}

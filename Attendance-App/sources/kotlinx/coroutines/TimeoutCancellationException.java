package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u0003B\u000f\b\u0010\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0019\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\n\u0010\n\u001a\u0004\u0018\u00010\u0000H\u0016R\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/TimeoutCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "Lkotlinx/coroutines/CopyableThrowable;", "message", "", "(Ljava/lang/String;)V", "coroutine", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Lkotlinx/coroutines/Job;)V", "createCopy", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Timeout.kt */
public final class TimeoutCancellationException extends CancellationException implements CopyableThrowable<TimeoutCancellationException> {
    public final Job coroutine;

    public TimeoutCancellationException(String message, Job coroutine2) {
        super(message);
        this.coroutine = coroutine2;
    }

    public TimeoutCancellationException(String message) {
        this(message, (Job) null);
    }

    public TimeoutCancellationException createCopy() {
        String message = getMessage();
        if (message == null) {
            message = "";
        }
        TimeoutCancellationException it = new TimeoutCancellationException(message, this.coroutine);
        it.initCause(this);
        return it;
    }
}

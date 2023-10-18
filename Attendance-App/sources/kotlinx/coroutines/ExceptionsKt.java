package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\b*\n\u0010\u0000\"\u00020\u00012\u00020\u0001¨\u0006\n"}, mo113d2 = {"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "cause", "", "addSuppressedThrowable", "", "other", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Exceptions.kt */
public final class ExceptionsKt {
    public static final CancellationException CancellationException(String message, Throwable cause) {
        CancellationException $this$CancellationException_u24lambda_u2d0 = new CancellationException(message);
        $this$CancellationException_u24lambda_u2d0.initCause(cause);
        return $this$CancellationException_u24lambda_u2d0;
    }

    public static final void addSuppressedThrowable(Throwable $this$addSuppressedThrowable, Throwable other) {
        kotlin.ExceptionsKt.addSuppressed($this$addSuppressedThrowable, other);
    }
}

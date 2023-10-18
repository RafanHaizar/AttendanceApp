package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;

@Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"Lkotlinx/coroutines/DiagnosticCoroutineContextException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "fillInStackTrace", "", "getLocalizedMessage", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineExceptionHandlerImpl.kt */
final class DiagnosticCoroutineContextException extends RuntimeException {
    private final CoroutineContext context;

    public DiagnosticCoroutineContextException(CoroutineContext context2) {
        this.context = context2;
    }

    public String getLocalizedMessage() {
        return this.context.toString();
    }

    public Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}

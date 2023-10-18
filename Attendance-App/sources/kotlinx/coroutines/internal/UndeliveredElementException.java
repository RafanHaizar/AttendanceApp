package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo113d2 = {"Lkotlinx/coroutines/internal/UndeliveredElementException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: OnUndeliveredElement.kt */
public final class UndeliveredElementException extends RuntimeException {
    public UndeliveredElementException(String message, Throwable cause) {
        super(message, cause);
    }
}

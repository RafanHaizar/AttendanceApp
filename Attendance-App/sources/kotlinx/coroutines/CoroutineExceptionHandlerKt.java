package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u001a%\u0010\u0000\u001a\u00020\u00012\u001a\b\u0004\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003H\b\u001a\u0018\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0000¨\u0006\r"}, mo113d2 = {"CoroutineExceptionHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handler", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext;", "", "", "handleCoroutineException", "context", "exception", "handlerException", "originalException", "thrownException", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineExceptionHandler.kt */
public final class CoroutineExceptionHandlerKt {
    public static final void handleCoroutineException(CoroutineContext context, Throwable exception) {
        try {
            CoroutineExceptionHandler it = (CoroutineExceptionHandler) context.get(CoroutineExceptionHandler.Key);
            if (it == null) {
                CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(context, exception);
            } else {
                it.handleException(context, exception);
            }
        } catch (Throwable t) {
            CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(context, handlerException(exception, t));
        }
    }

    public static final Throwable handlerException(Throwable originalException, Throwable thrownException) {
        if (originalException == thrownException) {
            return originalException;
        }
        RuntimeException $this$handlerException_u24lambda_u2d1 = new RuntimeException("Exception while trying to handle coroutine exception", thrownException);
        ExceptionsKt.addSuppressed($this$handlerException_u24lambda_u2d1, originalException);
        return $this$handlerException_u24lambda_u2d1;
    }

    public static final CoroutineExceptionHandler CoroutineExceptionHandler(Function2<? super CoroutineContext, ? super Throwable, Unit> handler) {
        return new CoroutineExceptionHandlerKt$CoroutineExceptionHandler$1(handler, CoroutineExceptionHandler.Key);
    }
}

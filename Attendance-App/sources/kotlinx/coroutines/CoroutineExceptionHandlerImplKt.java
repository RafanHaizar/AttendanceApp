package kotlinx.coroutines;

import java.util.List;
import java.util.ServiceLoader;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"handlers", "", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleCoroutineExceptionImpl", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineExceptionHandlerImpl.kt */
public final class CoroutineExceptionHandlerImplKt {
    private static final List<CoroutineExceptionHandler> handlers = SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(CoroutineExceptionHandler.class, CoroutineExceptionHandler.class.getClassLoader()).iterator()));

    public static final void handleCoroutineExceptionImpl(CoroutineContext context, Throwable exception) {
        for (CoroutineExceptionHandler handler : handlers) {
            try {
                handler.handleException(context, exception);
            } catch (Throwable t) {
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, CoroutineExceptionHandlerKt.handlerException(exception, t));
            }
        }
        Thread currentThread2 = Thread.currentThread();
        try {
            Result.Companion companion = Result.Companion;
            ExceptionsKt.addSuppressed(exception, new DiagnosticCoroutineContextException(context));
            Result.m1345constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            Result.m1345constructorimpl(ResultKt.createFailure(th));
        }
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, exception);
    }
}

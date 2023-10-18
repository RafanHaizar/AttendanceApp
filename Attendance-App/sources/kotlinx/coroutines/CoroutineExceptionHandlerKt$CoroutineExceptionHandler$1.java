package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineExceptionHandler;

@Metadata(mo112d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00020\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, mo113d2 = {"kotlinx/coroutines/CoroutineExceptionHandlerKt$CoroutineExceptionHandler$1", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: CoroutineExceptionHandler.kt */
public final class CoroutineExceptionHandlerKt$CoroutineExceptionHandler$1 extends AbstractCoroutineContextElement implements CoroutineExceptionHandler {
    final /* synthetic */ Function2<CoroutineContext, Throwable, Unit> $handler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CoroutineExceptionHandlerKt$CoroutineExceptionHandler$1(Function2<? super CoroutineContext, ? super Throwable, Unit> $handler2, CoroutineExceptionHandler.Key $super_call_param$1) {
        super($super_call_param$1);
        this.$handler = $handler2;
    }

    public void handleException(CoroutineContext context, Throwable exception) {
        this.$handler.invoke(context, exception);
    }
}

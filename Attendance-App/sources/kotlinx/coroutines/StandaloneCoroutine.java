package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

@Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\b\u0012\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0014¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/StandaloneCoroutine;", "Lkotlinx/coroutines/AbstractCoroutine;", "", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Z)V", "handleJobException", "exception", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Builders.common.kt */
class StandaloneCoroutine extends AbstractCoroutine<Unit> {
    public StandaloneCoroutine(CoroutineContext parentContext, boolean active) {
        super(parentContext, true, active);
    }

    /* access modifiers changed from: protected */
    public boolean handleJobException(Throwable exception) {
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), exception);
        return true;
    }
}

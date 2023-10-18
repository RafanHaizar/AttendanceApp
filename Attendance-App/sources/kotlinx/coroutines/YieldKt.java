package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u0001H@ø\u0001\u0000¢\u0006\u0002\u0010\u0002\u0002\u0004\n\u0002\b\u0019¨\u0006\u0003"}, mo113d2 = {"yield", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Yield.kt */
public final class YieldKt {
    public static final Object yield(Continuation<? super Unit> $completion) {
        Object obj;
        Continuation uCont = $completion;
        CoroutineContext context = uCont.getContext();
        JobKt.ensureActive(context);
        Continuation<? super Unit> intercepted = IntrinsicsKt.intercepted(uCont);
        DispatchedContinuation cont = intercepted instanceof DispatchedContinuation ? (DispatchedContinuation) intercepted : null;
        if (cont == null) {
            obj = Unit.INSTANCE;
        } else {
            if (cont.dispatcher.isDispatchNeeded(context)) {
                cont.dispatchYield$kotlinx_coroutines_core(context, Unit.INSTANCE);
            } else {
                YieldContext yieldContext = new YieldContext();
                cont.dispatchYield$kotlinx_coroutines_core(context.plus(yieldContext), Unit.INSTANCE);
                if (yieldContext.dispatcherWasUnconfined) {
                    obj = DispatchedContinuationKt.yieldUndispatched(cont) ? IntrinsicsKt.getCOROUTINE_SUSPENDED() : Unit.INSTANCE;
                }
            }
            obj = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (obj == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return obj == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? obj : Unit.INSTANCE;
    }
}

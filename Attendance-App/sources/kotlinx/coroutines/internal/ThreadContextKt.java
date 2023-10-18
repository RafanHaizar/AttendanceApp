package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(mo112d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0000\u001a\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0000\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000\"$\u0010\u0002\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0004¢\u0006\u0002\n\u0000\",\u0010\u0006\u001a \u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00070\u0003X\u0004¢\u0006\u0002\n\u0000\" \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo113d2 = {"NO_THREAD_ELEMENTS", "Lkotlinx/coroutines/internal/Symbol;", "countAll", "Lkotlin/Function2;", "", "Lkotlin/coroutines/CoroutineContext$Element;", "findOne", "Lkotlinx/coroutines/ThreadContextElement;", "updateState", "Lkotlinx/coroutines/internal/ThreadState;", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "threadContextElements", "updateThreadContext", "countOrElement", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadContext.kt */
public final class ThreadContextKt {
    public static final Symbol NO_THREAD_ELEMENTS = new Symbol("NO_THREAD_ELEMENTS");
    private static final Function2<Object, CoroutineContext.Element, Object> countAll = ThreadContextKt$countAll$1.INSTANCE;
    private static final Function2<ThreadContextElement<?>, CoroutineContext.Element, ThreadContextElement<?>> findOne = ThreadContextKt$findOne$1.INSTANCE;
    private static final Function2<ThreadState, CoroutineContext.Element, ThreadState> updateState = ThreadContextKt$updateState$1.INSTANCE;

    public static final Object threadContextElements(CoroutineContext context) {
        Object fold = context.fold(0, countAll);
        Intrinsics.checkNotNull(fold);
        return fold;
    }

    public static final Object updateThreadContext(CoroutineContext context, Object countOrElement) {
        Object countOrElement2 = countOrElement == null ? threadContextElements(context) : countOrElement;
        if (countOrElement2 == 0) {
            return NO_THREAD_ELEMENTS;
        }
        if (countOrElement2 instanceof Integer) {
            return context.fold(new ThreadState(context, ((Number) countOrElement2).intValue()), updateState);
        }
        return ((ThreadContextElement) countOrElement2).updateThreadContext(context);
    }

    public static final void restoreThreadContext(CoroutineContext context, Object oldState) {
        if (oldState != NO_THREAD_ELEMENTS) {
            if (oldState instanceof ThreadState) {
                ((ThreadState) oldState).restore(context);
                return;
            }
            Object fold = context.fold(null, findOne);
            if (fold != null) {
                ((ThreadContextElement) fold).restoreThreadContext(context, oldState);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
        }
    }
}

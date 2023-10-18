package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.ThreadLocalElement;
import kotlinx.coroutines.internal.ThreadLocalKey;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\b\u001a\u0019\u0010\t\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\b\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, mo113d2 = {"asContextElement", "Lkotlinx/coroutines/ThreadContextElement;", "T", "Ljava/lang/ThreadLocal;", "value", "(Ljava/lang/ThreadLocal;Ljava/lang/Object;)Lkotlinx/coroutines/ThreadContextElement;", "ensurePresent", "", "(Ljava/lang/ThreadLocal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPresent", "", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadContextElement.kt */
public final class ThreadContextElementKt {
    public static /* synthetic */ ThreadContextElement asContextElement$default(ThreadLocal threadLocal, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = threadLocal.get();
        }
        return asContextElement(threadLocal, obj);
    }

    public static final <T> ThreadContextElement<T> asContextElement(ThreadLocal<T> $this$asContextElement, T value) {
        return new ThreadLocalElement<>(value, $this$asContextElement);
    }

    public static final Object isPresent(ThreadLocal<?> $this$isPresent, Continuation<? super Boolean> $completion) {
        return Boxing.boxBoolean($completion.getContext().get(new ThreadLocalKey($this$isPresent)) != null);
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [java.lang.Throwable, kotlin.coroutines.Continuation] */
    private static final Object isPresent$$forInline(ThreadLocal<?> $this$isPresent, Continuation<? super Boolean> $completion) {
        InlineMarker.mark(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    public static final Object ensurePresent(ThreadLocal<?> $this$ensurePresent, Continuation<? super Unit> $completion) {
        if ($completion.getContext().get(new ThreadLocalKey($this$ensurePresent)) != null) {
            return Unit.INSTANCE;
        }
        throw new IllegalStateException(("ThreadLocal " + $this$ensurePresent + " is missing from context " + $completion.getContext()).toString());
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [java.lang.Throwable, kotlin.coroutines.Continuation] */
    private static final Object ensurePresent$$forInline(ThreadLocal<?> $this$ensurePresent, Continuation<? super Unit> $completion) {
        InlineMarker.mark(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }
}

package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo113d2 = {"<no name provided>", "Lkotlinx/coroutines/ThreadContextElement;", "found", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadContext.kt */
final class ThreadContextKt$findOne$1 extends Lambda implements Function2<ThreadContextElement<?>, CoroutineContext.Element, ThreadContextElement<?>> {
    public static final ThreadContextKt$findOne$1 INSTANCE = new ThreadContextKt$findOne$1();

    ThreadContextKt$findOne$1() {
        super(2);
    }

    public final ThreadContextElement<?> invoke(ThreadContextElement<?> found, CoroutineContext.Element element) {
        if (found != null) {
            return found;
        }
        if (element instanceof ThreadContextElement) {
            return (ThreadContextElement) element;
        }
        return null;
    }
}

package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo113d2 = {"<no name provided>", "", "countOrElement", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadContext.kt */
final class ThreadContextKt$countAll$1 extends Lambda implements Function2<Object, CoroutineContext.Element, Object> {
    public static final ThreadContextKt$countAll$1 INSTANCE = new ThreadContextKt$countAll$1();

    ThreadContextKt$countAll$1() {
        super(2);
    }

    public final Object invoke(Object countOrElement, CoroutineContext.Element element) {
        if (!(element instanceof ThreadContextElement)) {
            return countOrElement;
        }
        Integer num = countOrElement instanceof Integer ? (Integer) countOrElement : null;
        int inCount = num == null ? 1 : num.intValue();
        return inCount == 0 ? element : Integer.valueOf(inCount + 1);
    }
}

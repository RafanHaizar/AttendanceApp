package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "result", "it", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(ZLkotlin/coroutines/CoroutineContext$Element;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineContext.kt */
final class CoroutineContextKt$hasCopyableElements$1 extends Lambda implements Function2<Boolean, CoroutineContext.Element, Boolean> {
    public static final CoroutineContextKt$hasCopyableElements$1 INSTANCE = new CoroutineContextKt$hasCopyableElements$1();

    CoroutineContextKt$hasCopyableElements$1() {
        super(2);
    }

    public final Boolean invoke(boolean result, CoroutineContext.Element it) {
        return Boolean.valueOf(result || (it instanceof CopyableThreadContextElement));
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        return invoke(((Boolean) p1).booleanValue(), (CoroutineContext.Element) p2);
    }
}

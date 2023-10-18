package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo113d2 = {"<anonymous>", "Lkotlin/coroutines/CoroutineContext;", "result", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineContext.kt */
final class CoroutineContextKt$foldCopies$folded$1 extends Lambda implements Function2<CoroutineContext, CoroutineContext.Element, CoroutineContext> {
    final /* synthetic */ boolean $isNewCoroutine;
    final /* synthetic */ Ref.ObjectRef<CoroutineContext> $leftoverContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CoroutineContextKt$foldCopies$folded$1(Ref.ObjectRef<CoroutineContext> objectRef, boolean z) {
        super(2);
        this.$leftoverContext = objectRef;
        this.$isNewCoroutine = z;
    }

    public final CoroutineContext invoke(CoroutineContext result, CoroutineContext.Element element) {
        if (!(element instanceof CopyableThreadContextElement)) {
            return result.plus(element);
        }
        CoroutineContext.Element newElement = ((CoroutineContext) this.$leftoverContext.element).get(element.getKey());
        if (newElement == null) {
            return result.plus(this.$isNewCoroutine ? ((CopyableThreadContextElement) element).copyForChild() : (CopyableThreadContextElement) element);
        }
        Ref.ObjectRef<CoroutineContext> objectRef = this.$leftoverContext;
        objectRef.element = ((CoroutineContext) objectRef.element).minusKey(element.getKey());
        return result.plus(((CopyableThreadContextElement) element).mergeForChild(newElement));
    }
}

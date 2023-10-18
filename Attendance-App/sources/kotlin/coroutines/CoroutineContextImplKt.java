package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0007¨\u0006\b"}, mo113d2 = {"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: CoroutineContextImpl.kt */
public final class CoroutineContextImplKt {
    public static final <E extends CoroutineContext.Element> E getPolymorphicElement(CoroutineContext.Element $this$getPolymorphicElement, CoroutineContext.Key<E> key) {
        Intrinsics.checkNotNullParameter($this$getPolymorphicElement, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if (key instanceof AbstractCoroutineContextKey) {
            if (!((AbstractCoroutineContextKey) key).isSubKey$kotlin_stdlib($this$getPolymorphicElement.getKey())) {
                return null;
            }
            E tryCast$kotlin_stdlib = ((AbstractCoroutineContextKey) key).tryCast$kotlin_stdlib($this$getPolymorphicElement);
            if (tryCast$kotlin_stdlib instanceof CoroutineContext.Element) {
                return tryCast$kotlin_stdlib;
            }
            return null;
        } else if ($this$getPolymorphicElement.getKey() == key) {
            return $this$getPolymorphicElement;
        } else {
            return null;
        }
    }

    public static final CoroutineContext minusPolymorphicKey(CoroutineContext.Element $this$minusPolymorphicKey, CoroutineContext.Key<?> key) {
        Intrinsics.checkNotNullParameter($this$minusPolymorphicKey, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return key instanceof AbstractCoroutineContextKey ? (!((AbstractCoroutineContextKey) key).isSubKey$kotlin_stdlib($this$minusPolymorphicKey.getKey()) || ((AbstractCoroutineContextKey) key).tryCast$kotlin_stdlib($this$minusPolymorphicKey) == null) ? $this$minusPolymorphicKey : EmptyCoroutineContext.INSTANCE : $this$minusPolymorphicKey.getKey() == key ? EmptyCoroutineContext.INSTANCE : $this$minusPolymorphicKey;
    }
}

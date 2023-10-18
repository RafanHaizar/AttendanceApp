package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo113d2 = {"Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: CoroutineContextImpl.kt */
public abstract class AbstractCoroutineContextElement implements CoroutineContext.Element {
    private final CoroutineContext.Key<?> key;

    public AbstractCoroutineContextElement(CoroutineContext.Key<?> key2) {
        Intrinsics.checkNotNullParameter(key2, "key");
        this.key = key2;
    }

    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        return CoroutineContext.Element.DefaultImpls.fold(this, initial, operation);
    }

    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key2) {
        return CoroutineContext.Element.DefaultImpls.get(this, key2);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key2) {
        return CoroutineContext.Element.DefaultImpls.minusKey(this, key2);
    }

    public CoroutineContext plus(CoroutineContext context) {
        return CoroutineContext.Element.DefaultImpls.plus(this, context);
    }

    public CoroutineContext.Key<?> getKey() {
        return this.key;
    }
}

package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002\"\b\b\u0001\u0010\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0005\u001a\u0002H\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, mo113d2 = {"<anonymous>", "K", "", "V", "k", "<anonymous parameter 1>", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConcurrentWeakMap.kt */
final class ConcurrentWeakMap$keys$1 extends Lambda implements Function2<K, V, K> {
    public static final ConcurrentWeakMap$keys$1 INSTANCE = new ConcurrentWeakMap$keys$1();

    ConcurrentWeakMap$keys$1() {
        super(2);
    }

    public final K invoke(K k, V $noName_1) {
        return k;
    }
}

package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0002J\u000e\u0010\u0004\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, mo113d2 = {"kotlin/collections/AbstractMap$keys$1$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AbstractMap.kt */
public final class AbstractMap$keys$1$iterator$1 implements Iterator<K>, KMappedMarker {
    final /* synthetic */ Iterator<Map.Entry<K, V>> $entryIterator;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    AbstractMap$keys$1$iterator$1(Iterator<? extends Map.Entry<? extends K, ? extends V>> $entryIterator2) {
        this.$entryIterator = $entryIterator2;
    }

    public boolean hasNext() {
        return this.$entryIterator.hasNext();
    }

    public K next() {
        return this.$entryIterator.next().getKey();
    }
}

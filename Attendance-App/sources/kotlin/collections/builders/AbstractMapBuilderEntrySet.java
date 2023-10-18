package kotlin.collections.builders;

import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b \u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0002H&¨\u0006\f"}, mo113d2 = {"Lkotlin/collections/builders/AbstractMapBuilderEntrySet;", "E", "", "K", "V", "Lkotlin/collections/AbstractMutableSet;", "()V", "contains", "", "element", "(Ljava/util/Map$Entry;)Z", "containsEntry", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: MapBuilder.kt */
public abstract class AbstractMapBuilderEntrySet<E extends Map.Entry<? extends K, ? extends V>, K, V> extends AbstractMutableSet<E> {
    public abstract boolean containsEntry(Map.Entry<? extends K, ? extends V> entry);

    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof Map.Entry)) {
            return false;
        }
        return contains((Map.Entry) element);
    }

    public final /* bridge */ boolean remove(Object element) {
        if (!(element instanceof Map.Entry)) {
            return false;
        }
        return remove((Map.Entry<?, ?>) (Map.Entry) element);
    }

    public /* bridge */ boolean remove(Map.Entry<?, ?> element) {
        return super.remove(element);
    }

    public final boolean contains(E element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return containsEntry(element);
    }
}

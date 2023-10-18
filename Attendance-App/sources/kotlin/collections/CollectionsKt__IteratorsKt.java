package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\bø\u0001\u0000\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n\u001a\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b0\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u0002\u0007\n\u0005\b20\u0001¨\u0006\t"}, mo113d2 = {"forEach", "", "T", "", "operation", "Lkotlin/Function1;", "iterator", "withIndex", "Lkotlin/collections/IndexedValue;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/CollectionsKt")
/* compiled from: Iterators.kt */
class CollectionsKt__IteratorsKt extends CollectionsKt__IteratorsJVMKt {
    private static final <T> Iterator<T> iterator(Iterator<? extends T> $this$iterator) {
        Intrinsics.checkNotNullParameter($this$iterator, "<this>");
        return $this$iterator;
    }

    public static final <T> Iterator<IndexedValue<T>> withIndex(Iterator<? extends T> $this$withIndex) {
        Intrinsics.checkNotNullParameter($this$withIndex, "<this>");
        return new IndexingIterator<>($this$withIndex);
    }

    public static final <T> void forEach(Iterator<? extends T> $this$forEach, Function1<? super T, Unit> operation) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        while ($this$forEach.hasNext()) {
            operation.invoke($this$forEach.next());
        }
    }
}

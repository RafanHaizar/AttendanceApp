package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0004¨\u0006\r"}, mo113d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/SetsKt")
/* compiled from: _Sets.kt */
class SetsKt___SetsKt extends SetsKt__SetsKt {
    public static final <T> Set<T> minus(Set<? extends T> $this$minus, T element) {
        boolean z;
        Intrinsics.checkNotNullParameter($this$minus, "<this>");
        Iterable result = new LinkedHashSet(MapsKt.mapCapacity($this$minus.size()));
        boolean removed = false;
        for (Object element$iv : $this$minus) {
            Object it = element$iv;
            if (removed || !Intrinsics.areEqual(it, (Object) element)) {
                z = true;
            } else {
                removed = true;
                z = false;
            }
            if (z) {
                result.add(element$iv);
            }
        }
        return (Set) ((Collection) result);
    }

    public static final <T> Set<T> minus(Set<? extends T> $this$minus, T[] elements) {
        Intrinsics.checkNotNullParameter($this$minus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        LinkedHashSet result = new LinkedHashSet($this$minus);
        CollectionsKt.removeAll(result, elements);
        return result;
    }

    public static final <T> Set<T> minus(Set<? extends T> $this$minus, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$minus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection other = BrittleContainsOptimizationKt.convertToSetForSetOperationWith(elements, $this$minus);
        if (other.isEmpty()) {
            return CollectionsKt.toSet($this$minus);
        }
        if (other instanceof Set) {
            Collection destination$iv = new LinkedHashSet();
            for (Object element$iv : $this$minus) {
                if (!other.contains(element$iv)) {
                    destination$iv.add(element$iv);
                }
            }
            return (Set) destination$iv;
        }
        LinkedHashSet result = new LinkedHashSet($this$minus);
        result.removeAll(other);
        return result;
    }

    public static final <T> Set<T> minus(Set<? extends T> $this$minus, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$minus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        LinkedHashSet result = new LinkedHashSet($this$minus);
        CollectionsKt.removeAll(result, elements);
        return result;
    }

    private static final <T> Set<T> minusElement(Set<? extends T> $this$minusElement, T element) {
        Intrinsics.checkNotNullParameter($this$minusElement, "<this>");
        return SetsKt.minus($this$minusElement, element);
    }

    public static final <T> Set<T> plus(Set<? extends T> $this$plus, T element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() + 1));
        result.addAll($this$plus);
        result.add(element);
        return result;
    }

    public static final <T> Set<T> plus(Set<? extends T> $this$plus, T[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() + elements.length));
        result.addAll($this$plus);
        CollectionsKt.addAll(result, elements);
        return result;
    }

    public static final <T> Set<T> plus(Set<? extends T> $this$plus, Iterable<? extends T> elements) {
        int i;
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Integer collectionSizeOrNull = CollectionsKt.collectionSizeOrNull(elements);
        if (collectionSizeOrNull != null) {
            i = $this$plus.size() + collectionSizeOrNull.intValue();
        } else {
            i = $this$plus.size() * 2;
        }
        LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity(i));
        result.addAll($this$plus);
        CollectionsKt.addAll(result, elements);
        return result;
    }

    public static final <T> Set<T> plus(Set<? extends T> $this$plus, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() * 2));
        result.addAll($this$plus);
        CollectionsKt.addAll(result, elements);
        return result;
    }

    private static final <T> Set<T> plusElement(Set<? extends T> $this$plusElement, T element) {
        Intrinsics.checkNotNullParameter($this$plusElement, "<this>");
        return SetsKt.plus($this$plusElement, element);
    }
}

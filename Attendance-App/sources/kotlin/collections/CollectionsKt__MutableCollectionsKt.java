package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;

@Metadata(mo112d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\t\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\b¢\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u001d\u0010\u001e\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010 \u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001d\u0010!\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010\"\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a-\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010#\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\b\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010$\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0002\b%¨\u0006&"}, mo113d2 = {"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "removeFirst", "(Ljava/util/List;)Ljava/lang/Object;", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/CollectionsKt")
/* compiled from: MutableCollections.kt */
class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
    private static final <T> boolean remove(Collection<? extends T> $this$remove, T element) {
        Intrinsics.checkNotNullParameter($this$remove, "<this>");
        return TypeIntrinsics.asMutableCollection($this$remove).remove(element);
    }

    private static final <T> boolean removeAll(Collection<? extends T> $this$removeAll, Collection<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return TypeIntrinsics.asMutableCollection($this$removeAll).removeAll(elements);
    }

    private static final <T> boolean retainAll(Collection<? extends T> $this$retainAll, Collection<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return TypeIntrinsics.asMutableCollection($this$retainAll).retainAll(elements);
    }

    private static final <T> void plusAssign(Collection<? super T> $this$plusAssign, T element) {
        Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
        $this$plusAssign.add(element);
    }

    private static final <T> void plusAssign(Collection<? super T> $this$plusAssign, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.addAll($this$plusAssign, elements);
    }

    private static final <T> void plusAssign(Collection<? super T> $this$plusAssign, T[] elements) {
        Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.addAll($this$plusAssign, elements);
    }

    private static final <T> void plusAssign(Collection<? super T> $this$plusAssign, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.addAll($this$plusAssign, elements);
    }

    private static final <T> void minusAssign(Collection<? super T> $this$minusAssign, T element) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        $this$minusAssign.remove(element);
    }

    private static final <T> void minusAssign(Collection<? super T> $this$minusAssign, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.removeAll($this$minusAssign, elements);
    }

    private static final <T> void minusAssign(Collection<? super T> $this$minusAssign, T[] elements) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.removeAll($this$minusAssign, elements);
    }

    private static final <T> void minusAssign(Collection<? super T> $this$minusAssign, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        CollectionsKt.removeAll($this$minusAssign, elements);
    }

    public static final <T> boolean addAll(Collection<? super T> $this$addAll, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$addAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (elements instanceof Collection) {
            return $this$addAll.addAll((Collection) elements);
        }
        boolean result = false;
        for (Object item : elements) {
            if ($this$addAll.add(item)) {
                result = true;
            }
        }
        return result;
    }

    public static final <T> boolean addAll(Collection<? super T> $this$addAll, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$addAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean result = false;
        for (Object item : elements) {
            if ($this$addAll.add(item)) {
                result = true;
            }
        }
        return result;
    }

    public static final <T> boolean addAll(Collection<? super T> $this$addAll, T[] elements) {
        Intrinsics.checkNotNullParameter($this$addAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return $this$addAll.addAll(ArraysKt.asList(elements));
    }

    public static final <T> boolean removeAll(Collection<? super T> $this$removeAll, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return TypeIntrinsics.asMutableCollection($this$removeAll).removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(elements, $this$removeAll));
    }

    public static final <T> boolean removeAll(Collection<? super T> $this$removeAll, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection set = BrittleContainsOptimizationKt.convertToSetForSetOperation(elements);
        return (set.isEmpty() ^ true) && $this$removeAll.removeAll(set);
    }

    public static final <T> boolean removeAll(Collection<? super T> $this$removeAll, T[] elements) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return ((elements.length == 0) ^ true) && $this$removeAll.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(elements));
    }

    public static final <T> boolean retainAll(Collection<? super T> $this$retainAll, Iterable<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return TypeIntrinsics.asMutableCollection($this$retainAll).retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(elements, $this$retainAll));
    }

    public static final <T> boolean retainAll(Collection<? super T> $this$retainAll, T[] elements) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (!(elements.length == 0)) {
            return $this$retainAll.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(elements));
        }
        return retainNothing$CollectionsKt__MutableCollectionsKt($this$retainAll);
    }

    public static final <T> boolean retainAll(Collection<? super T> $this$retainAll, Sequence<? extends T> elements) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection set = BrittleContainsOptimizationKt.convertToSetForSetOperation(elements);
        if (!set.isEmpty()) {
            return $this$retainAll.retainAll(set);
        }
        return retainNothing$CollectionsKt__MutableCollectionsKt($this$retainAll);
    }

    private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection<?> $this$retainNothing) {
        boolean result = !$this$retainNothing.isEmpty();
        $this$retainNothing.clear();
        return result;
    }

    public static final <T> boolean removeAll(Iterable<? extends T> $this$removeAll, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt($this$removeAll, predicate, true);
    }

    public static final <T> boolean retainAll(Iterable<? extends T> $this$retainAll, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt($this$retainAll, predicate, false);
    }

    private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable<? extends T> $this$filterInPlace, Function1<? super T, Boolean> predicate, boolean predicateResultToRemove) {
        boolean result = false;
        Iterator $this$filterInPlace_u24lambda_u2d0 = $this$filterInPlace.iterator();
        while ($this$filterInPlace_u24lambda_u2d0.hasNext()) {
            if (predicate.invoke($this$filterInPlace_u24lambda_u2d0.next()).booleanValue() == predicateResultToRemove) {
                $this$filterInPlace_u24lambda_u2d0.remove();
                result = true;
            }
        }
        return result;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use removeAt(index) instead.", replaceWith = @ReplaceWith(expression = "removeAt(index)", imports = {}))
    private static final <T> T remove(List<T> $this$remove, int index) {
        Intrinsics.checkNotNullParameter($this$remove, "<this>");
        return $this$remove.remove(index);
    }

    public static final <T> T removeFirst(List<T> $this$removeFirst) {
        Intrinsics.checkNotNullParameter($this$removeFirst, "<this>");
        if (!$this$removeFirst.isEmpty()) {
            return $this$removeFirst.remove(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static final <T> T removeFirstOrNull(List<T> $this$removeFirstOrNull) {
        Intrinsics.checkNotNullParameter($this$removeFirstOrNull, "<this>");
        if ($this$removeFirstOrNull.isEmpty()) {
            return null;
        }
        return $this$removeFirstOrNull.remove(0);
    }

    public static final <T> T removeLast(List<T> $this$removeLast) {
        Intrinsics.checkNotNullParameter($this$removeLast, "<this>");
        if (!$this$removeLast.isEmpty()) {
            return $this$removeLast.remove(CollectionsKt.getLastIndex($this$removeLast));
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static final <T> T removeLastOrNull(List<T> $this$removeLastOrNull) {
        Intrinsics.checkNotNullParameter($this$removeLastOrNull, "<this>");
        if ($this$removeLastOrNull.isEmpty()) {
            return null;
        }
        return $this$removeLastOrNull.remove(CollectionsKt.getLastIndex($this$removeLastOrNull));
    }

    public static final <T> boolean removeAll(List<T> $this$removeAll, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt($this$removeAll, predicate, true);
    }

    public static final <T> boolean retainAll(List<T> $this$retainAll, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return filterInPlace$CollectionsKt__MutableCollectionsKt($this$retainAll, predicate, false);
    }

    private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List<T> $this$filterInPlace, Function1<? super T, Boolean> predicate, boolean predicateResultToRemove) {
        if (!($this$filterInPlace instanceof RandomAccess)) {
            Intrinsics.checkNotNull($this$filterInPlace, "null cannot be cast to non-null type kotlin.collections.MutableIterable<T of kotlin.collections.CollectionsKt__MutableCollectionsKt.filterInPlace>");
            return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable($this$filterInPlace), predicate, predicateResultToRemove);
        }
        int writeIndex = 0;
        IntIterator it = new IntRange(0, CollectionsKt.getLastIndex($this$filterInPlace)).iterator();
        while (it.hasNext()) {
            int readIndex = it.nextInt();
            Object element = $this$filterInPlace.get(readIndex);
            if (predicate.invoke(element).booleanValue() != predicateResultToRemove) {
                if (writeIndex != readIndex) {
                    $this$filterInPlace.set(writeIndex, element);
                }
                writeIndex++;
            }
        }
        if (writeIndex >= $this$filterInPlace.size()) {
            return false;
        }
        int removeIndex = CollectionsKt.getLastIndex($this$filterInPlace);
        if (writeIndex > removeIndex) {
            return true;
        }
        while (true) {
            $this$filterInPlace.remove(removeIndex);
            if (removeIndex == writeIndex) {
                return true;
            }
            removeIndex--;
        }
    }
}

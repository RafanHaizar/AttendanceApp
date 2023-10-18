package kotlin.collections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000d\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0004\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001aA\u0010\u0006\u001a\u0002H\u0007\"\u0010\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\t\u001a\u0002H\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\n\u001a)\u0010\u000b\u001a\u0004\u0018\u0001H\f\"\u000e\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0003H\u0007¢\u0006\u0002\u0010\u000e\u001a\u0019\u0010\u000b\u001a\u0004\u0018\u00010\u000f*\b\u0012\u0004\u0012\u00020\u000f0\u0003H\u0007¢\u0006\u0002\u0010\u0010\u001a\u0019\u0010\u000b\u001a\u0004\u0018\u00010\u0011*\b\u0012\u0004\u0012\u00020\u00110\u0003H\u0007¢\u0006\u0002\u0010\u0012\u001aG\u0010\u0013\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0000\u0010\f\"\u000e\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\f0\u00032\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\u00020\u0015H\bø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a;\u0010\u0017\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00032\u001a\u0010\u0018\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\f0\u0019j\n\u0012\u0006\b\u0000\u0012\u0002H\f`\u001aH\u0007¢\u0006\u0002\u0010\u001b\u001a)\u0010\u001c\u001a\u0004\u0018\u0001H\f\"\u000e\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0003H\u0007¢\u0006\u0002\u0010\u000e\u001a\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u000f*\b\u0012\u0004\u0012\u00020\u000f0\u0003H\u0007¢\u0006\u0002\u0010\u0010\u001a\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u0011*\b\u0012\u0004\u0012\u00020\u00110\u0003H\u0007¢\u0006\u0002\u0010\u0012\u001aG\u0010\u001d\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0000\u0010\f\"\u000e\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\f0\u00032\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\u00020\u0015H\bø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a;\u0010\u001e\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00032\u001a\u0010\u0018\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\f0\u0019j\n\u0012\u0006\b\u0000\u0012\u0002H\f`\u001aH\u0007¢\u0006\u0002\u0010\u001b\u001a\u0016\u0010\u001f\u001a\u00020 \"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0!\u001a5\u0010\"\u001a\u00020#\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00032\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020#0\u0015H\bø\u0001\u0000¢\u0006\u0002\b$\u001a5\u0010\"\u001a\u00020%\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00032\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020%0\u0015H\bø\u0001\u0000¢\u0006\u0002\b&\u001a&\u0010'\u001a\b\u0012\u0004\u0012\u0002H\f0(\"\u000e\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0003\u001a8\u0010'\u001a\b\u0012\u0004\u0012\u0002H\f0(\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00032\u001a\u0010\u0018\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\f0\u0019j\n\u0012\u0006\b\u0000\u0012\u0002H\f`\u001a\u0002\u0007\n\u0005\b20\u0001¨\u0006)"}, mo113d2 = {"filterIsInstance", "", "R", "", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Ljava/lang/Iterable;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "max", "T", "", "(Ljava/lang/Iterable;)Ljava/lang/Comparable;", "", "(Ljava/lang/Iterable;)Ljava/lang/Double;", "", "(Ljava/lang/Iterable;)Ljava/lang/Float;", "maxBy", "selector", "Lkotlin/Function1;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/lang/Object;", "min", "minBy", "minWith", "reverse", "", "", "sumOf", "Ljava/math/BigDecimal;", "sumOfBigDecimal", "Ljava/math/BigInteger;", "sumOfBigInteger", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/CollectionsKt")
/* compiled from: _CollectionsJvm.kt */
class CollectionsKt___CollectionsJvmKt extends CollectionsKt__ReversedViewsKt {
    public static final <R> List<R> filterIsInstance(Iterable<?> $this$filterIsInstance, Class<R> klass) {
        Intrinsics.checkNotNullParameter($this$filterIsInstance, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (List) CollectionsKt.filterIsInstanceTo($this$filterIsInstance, new ArrayList(), klass);
    }

    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Iterable<?> $this$filterIsInstanceTo, C destination, Class<R> klass) {
        Intrinsics.checkNotNullParameter($this$filterIsInstanceTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(klass, "klass");
        for (Object element : $this$filterIsInstanceTo) {
            if (klass.isInstance(element)) {
                destination.add(element);
            }
        }
        return destination;
    }

    public static final <T> void reverse(List<T> $this$reverse) {
        Intrinsics.checkNotNullParameter($this$reverse, "<this>");
        Collections.reverse($this$reverse);
    }

    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(Iterable<? extends T> $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) CollectionsKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final <T> SortedSet<T> toSortedSet(Iterable<? extends T> $this$toSortedSet, Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (SortedSet) CollectionsKt.toCollection($this$toSortedSet, new TreeSet(comparator));
    }

    @Deprecated(message = "Use maxByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    public static final /* synthetic */ <T, R extends Comparable<? super R>> T maxBy(Iterable<? extends T> $this$maxBy, Function1<? super T, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxBy, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$maxBy.iterator();
        if (!iterator$iv.hasNext()) {
            return null;
        }
        Object maxElem$iv = iterator$iv.next();
        if (!iterator$iv.hasNext()) {
            return maxElem$iv;
        }
        Comparable maxValue$iv = (Comparable) selector.invoke(maxElem$iv);
        do {
            Object e$iv = iterator$iv.next();
            Comparable v$iv = (Comparable) selector.invoke(e$iv);
            if (maxValue$iv.compareTo(v$iv) < 0) {
                maxElem$iv = e$iv;
                maxValue$iv = v$iv;
            }
        } while (iterator$iv.hasNext());
        return maxElem$iv;
    }

    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    public static final /* synthetic */ Object maxWith(Iterable $this$maxWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter($this$maxWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return CollectionsKt.maxWithOrNull($this$maxWith, comparator);
    }

    @Deprecated(message = "Use minByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    public static final /* synthetic */ <T, R extends Comparable<? super R>> T minBy(Iterable<? extends T> $this$minBy, Function1<? super T, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minBy, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$minBy.iterator();
        if (!iterator$iv.hasNext()) {
            return null;
        }
        Object minElem$iv = iterator$iv.next();
        if (!iterator$iv.hasNext()) {
            return minElem$iv;
        }
        Comparable minValue$iv = (Comparable) selector.invoke(minElem$iv);
        do {
            Object e$iv = iterator$iv.next();
            Comparable v$iv = (Comparable) selector.invoke(e$iv);
            if (minValue$iv.compareTo(v$iv) > 0) {
                minElem$iv = e$iv;
                minValue$iv = v$iv;
            }
        } while (iterator$iv.hasNext());
        return minElem$iv;
    }

    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    public static final /* synthetic */ Object minWith(Iterable $this$minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter($this$minWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return CollectionsKt.minWithOrNull($this$minWith, comparator);
    }

    private static final <T> BigDecimal sumOfBigDecimal(Iterable<? extends T> $this$sumOf, Function1<? super T, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal sum = BigDecimal.valueOf(0);
        Intrinsics.checkNotNullExpressionValue(sum, "valueOf(this.toLong())");
        for (Object element : $this$sumOf) {
            BigDecimal add = sum.add((BigDecimal) selector.invoke(element));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    private static final <T> BigInteger sumOfBigInteger(Iterable<? extends T> $this$sumOf, Function1<? super T, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger sum = BigInteger.valueOf(0);
        Intrinsics.checkNotNullExpressionValue(sum, "valueOf(this.toLong())");
        for (Object element : $this$sumOf) {
            BigInteger add = sum.add((BigInteger) selector.invoke(element));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }
}

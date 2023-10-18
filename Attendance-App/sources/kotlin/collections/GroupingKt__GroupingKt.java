package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\bø\u0001\u0000\u001a·\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0016\u001a¿\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\bø\u0001\u0000\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001aØ\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\bø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u001a\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\bø\u0001\u0000\u001a¤\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\bø\u0001\u0000¢\u0006\u0002\u0010#\u0002\u0007\n\u0005\b20\u0001¨\u0006$"}, mo113d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/GroupingKt")
/* compiled from: Grouping.kt */
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
    public static final <T, K, R> Map<K, R> aggregate(Grouping<T, ? extends K> $this$aggregate, Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$aggregate, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Map destination$iv = new LinkedHashMap();
        Grouping $this$aggregateTo$iv = $this$aggregate;
        Iterator<T> sourceIterator = $this$aggregateTo$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
            Object accumulator$iv = destination$iv.get(key$iv);
            destination$iv.put(key$iv, operation.invoke(key$iv, accumulator$iv, e$iv, Boolean.valueOf(accumulator$iv == null && !destination$iv.containsKey(key$iv))));
        }
        return destination$iv;
    }

    public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(Grouping<T, ? extends K> $this$aggregateTo, M destination, Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$aggregateTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Iterator<T> sourceIterator = $this$aggregateTo.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e = sourceIterator.next();
            Object key = $this$aggregateTo.keyOf(e);
            Object accumulator = destination.get(key);
            destination.put(key, operation.invoke(key, accumulator, e, Boolean.valueOf(accumulator == null && !destination.containsKey(key))));
        }
        return destination;
    }

    public static final <T, K, R> Map<K, R> fold(Grouping<T, ? extends K> $this$fold, Function2<? super K, ? super T, ? extends R> initialValueSelector, Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        int $i$f$fold;
        Object key;
        Object obj;
        Function2<? super K, ? super T, ? extends R> function2 = initialValueSelector;
        Function3<? super K, ? super R, ? super T, ? extends R> function3 = operation;
        Intrinsics.checkNotNullParameter($this$fold, "<this>");
        Intrinsics.checkNotNullParameter(function2, "initialValueSelector");
        Intrinsics.checkNotNullParameter(function3, "operation");
        int $i$f$fold2 = 0;
        Map destination$iv$iv = new LinkedHashMap();
        Grouping $this$aggregateTo$iv$iv = $this$fold;
        Iterator<T> sourceIterator = $this$aggregateTo$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(next);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            Object acc = accumulator$iv$iv;
            Object e = next;
            Object key2 = key$iv$iv;
            if (accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv)) {
                $i$f$fold = $i$f$fold2;
                key = key2;
                obj = function2.invoke(key, e);
            } else {
                $i$f$fold = $i$f$fold2;
                key = key2;
                obj = acc;
            }
            destination$iv$iv.put(key$iv$iv, function3.invoke(key, obj, e));
            function2 = initialValueSelector;
            $i$f$fold2 = $i$f$fold;
        }
        return destination$iv$iv;
    }

    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(Grouping<T, ? extends K> $this$foldTo, M destination, Function2<? super K, ? super T, ? extends R> initialValueSelector, Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        M m = destination;
        Function2<? super K, ? super T, ? extends R> function2 = initialValueSelector;
        Function3<? super K, ? super R, ? super T, ? extends R> function3 = operation;
        Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
        Intrinsics.checkNotNullParameter(m, "destination");
        Intrinsics.checkNotNullParameter(function2, "initialValueSelector");
        Intrinsics.checkNotNullParameter(function3, "operation");
        Grouping $this$aggregateTo$iv = $this$foldTo;
        Iterator<T> sourceIterator = $this$aggregateTo$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            Object keyOf = $this$aggregateTo$iv.keyOf(next);
            Object accumulator$iv = m.get(keyOf);
            Object key = keyOf;
            Object e = next;
            m.put(keyOf, function3.invoke(key, accumulator$iv == null && !m.containsKey(keyOf) ? function2.invoke(key, e) : accumulator$iv, e));
            function2 = initialValueSelector;
        }
        return m;
    }

    public static final <T, K, R> Map<K, R> fold(Grouping<T, ? extends K> $this$fold, R initialValue, Function2<? super R, ? super T, ? extends R> operation) {
        Function2<? super R, ? super T, ? extends R> function2 = operation;
        Intrinsics.checkNotNullParameter($this$fold, "<this>");
        Intrinsics.checkNotNullParameter(function2, "operation");
        int $i$f$fold = 0;
        Map destination$iv$iv = new LinkedHashMap();
        Grouping $this$aggregateTo$iv$iv = $this$fold;
        Iterator<T> sourceIterator = $this$aggregateTo$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e = sourceIterator.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            int $i$f$fold2 = $i$f$fold;
            destination$iv$iv.put(key$iv$iv, function2.invoke(accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv) ? initialValue : accumulator$iv$iv, e));
            $i$f$fold = $i$f$fold2;
        }
        return destination$iv$iv;
    }

    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(Grouping<T, ? extends K> $this$foldTo, M destination, R initialValue, Function2<? super R, ? super T, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Grouping $this$aggregateTo$iv = $this$foldTo;
        Iterator<T> sourceIterator = $this$aggregateTo$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e = sourceIterator.next();
            Object key$iv = $this$aggregateTo$iv.keyOf(e);
            Object accumulator$iv = destination.get(key$iv);
            destination.put(key$iv, operation.invoke(accumulator$iv == null && !destination.containsKey(key$iv) ? initialValue : accumulator$iv, e));
        }
        return destination;
    }

    public static final <S, T extends S, K> Map<K, S> reduce(Grouping<T, ? extends K> $this$reduce, Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        Function3<? super K, ? super S, ? super T, ? extends S> function3 = operation;
        Intrinsics.checkNotNullParameter($this$reduce, "<this>");
        Intrinsics.checkNotNullParameter(function3, "operation");
        Map destination$iv$iv = new LinkedHashMap();
        Grouping $this$aggregateTo$iv$iv = $this$reduce;
        Iterator<T> sourceIterator = $this$aggregateTo$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv$iv = sourceIterator.next();
            Object keyOf = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(keyOf);
            Object key = keyOf;
            Object acc = accumulator$iv$iv;
            Object e = e$iv$iv;
            if (!(accumulator$iv$iv == null && !destination$iv$iv.containsKey(keyOf))) {
                e = function3.invoke(key, acc, e);
            }
            destination$iv$iv.put(keyOf, e);
        }
        return destination$iv$iv;
    }

    public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(Grouping<T, ? extends K> $this$reduceTo, M destination, Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        Intrinsics.checkNotNullParameter($this$reduceTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Grouping $this$aggregateTo$iv = $this$reduceTo;
        Iterator<T> sourceIterator = $this$aggregateTo$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object keyOf = $this$aggregateTo$iv.keyOf(e$iv);
            Object accumulator$iv = destination.get(keyOf);
            Object acc = accumulator$iv;
            Object key = keyOf;
            Object e = e$iv;
            if (!(accumulator$iv == null && !destination.containsKey(keyOf))) {
                e = operation.invoke(key, acc, e);
            }
            destination.put(keyOf, e);
        }
        return destination;
    }

    public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(Grouping<T, ? extends K> $this$eachCountTo, M destination) {
        M m = destination;
        Intrinsics.checkNotNullParameter($this$eachCountTo, "<this>");
        Intrinsics.checkNotNullParameter(m, "destination");
        Grouping $this$aggregateTo$iv$iv = $this$eachCountTo;
        Iterator<T> sourceIterator = $this$aggregateTo$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            T next = sourceIterator.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(next);
            Object accumulator$iv$iv = m.get(key$iv$iv);
            T t = next;
            m.put(key$iv$iv, Integer.valueOf(((Number) (accumulator$iv$iv == null && !m.containsKey(key$iv$iv) ? 0 : accumulator$iv$iv)).intValue() + 1));
        }
        return m;
    }
}

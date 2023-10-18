package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000d\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a4\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007H\u0001\u001aQ\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\t\u001a\u00020\u00012#\u0010\n\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\bø\u0001\u0000\u001aI\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052#\u0010\n\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\bø\u0001\u0000\u001a \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0001\u001a(\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0001\u001a2\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013\u001aa\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00040\u00172*\u0010\u0018\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00130\u0019\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013H\u0007¢\u0006\u0002\u0010\u001a\u001aY\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u001b\"\u0004\b\u0001\u0010\u00052*\u0010\u0018\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00130\u0019\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013¢\u0006\u0002\u0010\u001c\u001aC\u0010\u001d\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u001e2\u0006\u0010\u001f\u001a\u0002H\u00042\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00050!H\bø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u0019\u0010#\u001a\u00020$*\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0\u0003H\b\u001a2\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0000\u001a1\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\b\u001a:\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u001b\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\u001a@\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00040\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006)"}, mo113d2 = {"INT_MAX_POWER_OF_TWO", "", "build", "", "K", "V", "builder", "", "buildMapInternal", "capacity", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createMapBuilder", "mapCapacity", "expectedSize", "mapOf", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "comparator", "Ljava/util/Comparator;", "pairs", "", "(Ljava/util/Comparator;[Lkotlin/Pair;)Ljava/util/SortedMap;", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/MapsKt")
/* compiled from: MapsJVM.kt */
class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
    private static final int INT_MAX_POWER_OF_TWO = 1073741824;

    public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V> pair) {
        Intrinsics.checkNotNullParameter(pair, "pair");
        Map<K, V> singletonMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
        Intrinsics.checkNotNullExpressionValue(singletonMap, "singletonMap(pair.first, pair.second)");
        return singletonMap;
    }

    private static final <K, V> Map<K, V> buildMapInternal(Function1<? super Map<K, V>, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Map createMapBuilder = MapsKt.createMapBuilder();
        builderAction.invoke(createMapBuilder);
        return MapsKt.build(createMapBuilder);
    }

    private static final <K, V> Map<K, V> buildMapInternal(int capacity, Function1<? super Map<K, V>, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Map createMapBuilder = MapsKt.createMapBuilder(capacity);
        builderAction.invoke(createMapBuilder);
        return MapsKt.build(createMapBuilder);
    }

    public static final <K, V> Map<K, V> createMapBuilder() {
        return new MapBuilder<>();
    }

    public static final <K, V> Map<K, V> createMapBuilder(int capacity) {
        return new MapBuilder<>(capacity);
    }

    public static final <K, V> Map<K, V> build(Map<K, V> builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        return ((MapBuilder) builder).build();
    }

    public static final <K, V> V getOrPut(ConcurrentMap<K, V> $this$getOrPut, K key, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter($this$getOrPut, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        Object obj = $this$getOrPut.get(key);
        if (obj != null) {
            return obj;
        }
        Object invoke = defaultValue.invoke();
        Object putIfAbsent = $this$getOrPut.putIfAbsent(key, invoke);
        if (putIfAbsent == null) {
            return invoke;
        }
        return putIfAbsent;
    }

    public static final <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> $this$toSortedMap) {
        Intrinsics.checkNotNullParameter($this$toSortedMap, "<this>");
        return new TreeMap<>($this$toSortedMap);
    }

    public static final <K, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> $this$toSortedMap, Comparator<? super K> comparator) {
        Intrinsics.checkNotNullParameter($this$toSortedMap, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        TreeMap $this$toSortedMap_u24lambda_u2d1 = new TreeMap(comparator);
        $this$toSortedMap_u24lambda_u2d1.putAll($this$toSortedMap);
        return $this$toSortedMap_u24lambda_u2d1;
    }

    public static final <K extends Comparable<? super K>, V> SortedMap<K, V> sortedMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        TreeMap $this$sortedMapOf_u24lambda_u2d2 = new TreeMap();
        MapsKt.putAll($this$sortedMapOf_u24lambda_u2d2, pairs);
        return $this$sortedMapOf_u24lambda_u2d2;
    }

    public static final <K, V> SortedMap<K, V> sortedMapOf(Comparator<? super K> comparator, Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        TreeMap $this$sortedMapOf_u24lambda_u2d3 = new TreeMap(comparator);
        MapsKt.putAll($this$sortedMapOf_u24lambda_u2d3, pairs);
        return $this$sortedMapOf_u24lambda_u2d3;
    }

    private static final Properties toProperties(Map<String, String> $this$toProperties) {
        Intrinsics.checkNotNullParameter($this$toProperties, "<this>");
        Properties $this$toProperties_u24lambda_u2d4 = new Properties();
        $this$toProperties_u24lambda_u2d4.putAll($this$toProperties);
        return $this$toProperties_u24lambda_u2d4;
    }

    private static final <K, V> Map<K, V> toSingletonMapOrSelf(Map<K, ? extends V> $this$toSingletonMapOrSelf) {
        Intrinsics.checkNotNullParameter($this$toSingletonMapOrSelf, "<this>");
        return MapsKt.toSingletonMap($this$toSingletonMapOrSelf);
    }

    public static final <K, V> Map<K, V> toSingletonMap(Map<? extends K, ? extends V> $this$toSingletonMap) {
        Intrinsics.checkNotNullParameter($this$toSingletonMap, "<this>");
        Map.Entry $this$toSingletonMap_u24lambda_u2d5 = $this$toSingletonMap.entrySet().iterator().next();
        Map<K, V> singletonMap = Collections.singletonMap($this$toSingletonMap_u24lambda_u2d5.getKey(), $this$toSingletonMap_u24lambda_u2d5.getValue());
        Intrinsics.checkNotNullExpressionValue(singletonMap, "with(entries.iterator().…ingletonMap(key, value) }");
        return singletonMap;
    }

    public static final int mapCapacity(int expectedSize) {
        if (expectedSize < 0) {
            return expectedSize;
        }
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return (int) ((((float) expectedSize) / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }
}

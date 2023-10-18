package kotlin.collections;

import java.util.Comparator;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000(\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001ah\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00062\u001e\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0004\u0012\u0002H\u00040\bH\bø\u0001\u0000\u001ai\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000622\u0010\n\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00010\u000bj\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001`\fH\b\u001ah\u0010\r\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00062\u001e\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0004\u0012\u0002H\u00040\bH\bø\u0001\u0000\u001ah\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000622\u0010\n\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00010\u000bj\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001`\fH\u0007\u0002\u0007\n\u0005\b20\u0001¨\u0006\u000f"}, mo113d2 = {"maxBy", "", "K", "V", "R", "", "", "selector", "Lkotlin/Function1;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "minBy", "minWith", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/MapsKt")
/* compiled from: _MapsJvm.kt */
class MapsKt___MapsJvmKt extends MapsKt__MapsKt {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.util.Map$Entry<K, V>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: java.util.Map$Entry<K, V>} */
    /* JADX WARNING: Multi-variable type inference failed */
    @kotlin.Deprecated(message = "Use maxByOrNull instead.", replaceWith = @kotlin.ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @kotlin.DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final /* synthetic */ <K, V, R extends java.lang.Comparable<? super R>> java.util.Map.Entry<K, V> maxBy(java.util.Map<? extends K, ? extends V> r6, kotlin.jvm.functions.Function1<? super java.util.Map.Entry<? extends K, ? extends V>, ? extends R> r7) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "selector"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.util.Set r0 = r6.entrySet()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x001c
            r0 = 0
            goto L_0x0047
        L_0x001c:
            java.lang.Object r1 = r0.next()
            boolean r2 = r0.hasNext()
            if (r2 != 0) goto L_0x0028
        L_0x0026:
            r0 = r1
            goto L_0x0047
        L_0x0028:
            java.lang.Object r2 = r7.invoke(r1)
            java.lang.Comparable r2 = (java.lang.Comparable) r2
        L_0x002e:
            java.lang.Object r3 = r0.next()
            java.lang.Object r4 = r7.invoke(r3)
            java.lang.Comparable r4 = (java.lang.Comparable) r4
            int r5 = r2.compareTo(r4)
            if (r5 >= 0) goto L_0x0040
            r1 = r3
            r2 = r4
        L_0x0040:
            boolean r3 = r0.hasNext()
            if (r3 != 0) goto L_0x002e
            goto L_0x0026
        L_0x0047:
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.MapsKt___MapsJvmKt.maxBy(java.util.Map, kotlin.jvm.functions.Function1):java.util.Map$Entry");
    }

    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    private static final /* synthetic */ <K, V> Map.Entry<K, V> maxWith(Map<? extends K, ? extends V> $this$maxWith, Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        Intrinsics.checkNotNullParameter($this$maxWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.maxWithOrNull($this$maxWith.entrySet(), comparator);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.util.Map$Entry<K, V>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.util.Map$Entry<K, V>} */
    /* JADX WARNING: Multi-variable type inference failed */
    @kotlin.Deprecated(message = "Use minByOrNull instead.", replaceWith = @kotlin.ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @kotlin.DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ <K, V, R extends java.lang.Comparable<? super R>> java.util.Map.Entry<K, V> minBy(java.util.Map<? extends K, ? extends V> r7, kotlin.jvm.functions.Function1<? super java.util.Map.Entry<? extends K, ? extends V>, ? extends R> r8) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.String r0 = "selector"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            r0 = 0
            java.util.Set r1 = r7.entrySet()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
            boolean r2 = r1.hasNext()
            if (r2 != 0) goto L_0x001d
            r1 = 0
            goto L_0x0048
        L_0x001d:
            java.lang.Object r2 = r1.next()
            boolean r3 = r1.hasNext()
            if (r3 != 0) goto L_0x0029
        L_0x0027:
            r1 = r2
            goto L_0x0048
        L_0x0029:
            java.lang.Object r3 = r8.invoke(r2)
            java.lang.Comparable r3 = (java.lang.Comparable) r3
        L_0x002f:
            java.lang.Object r4 = r1.next()
            java.lang.Object r5 = r8.invoke(r4)
            java.lang.Comparable r5 = (java.lang.Comparable) r5
            int r6 = r3.compareTo(r5)
            if (r6 <= 0) goto L_0x0041
            r2 = r4
            r3 = r5
        L_0x0041:
            boolean r4 = r1.hasNext()
            if (r4 != 0) goto L_0x002f
            goto L_0x0027
        L_0x0048:
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.MapsKt___MapsJvmKt.minBy(java.util.Map, kotlin.jvm.functions.Function1):java.util.Map$Entry");
    }

    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    public static final /* synthetic */ Map.Entry minWith(Map $this$minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter($this$minWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.minWithOrNull($this$minWith.entrySet(), comparator);
    }
}

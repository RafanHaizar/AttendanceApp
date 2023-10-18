package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0000¢\u0006\u0002\u0010\u0004\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000\u001a,\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0000\u001a\u0018\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0002¨\u0006\u000b"}, mo113d2 = {"convertToSetForSetOperation", "", "T", "", "([Ljava/lang/Object;)Ljava/util/Collection;", "", "Lkotlin/sequences/Sequence;", "convertToSetForSetOperationWith", "source", "safeToConvertToSet", "", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: BrittleContainsOptimization.kt */
public final class BrittleContainsOptimizationKt {
    private static final <T> boolean safeToConvertToSet(Collection<? extends T> $this$safeToConvertToSet) {
        return CollectionSystemProperties.brittleContainsOptimizationEnabled != 0 && $this$safeToConvertToSet.size() > 2 && ($this$safeToConvertToSet instanceof ArrayList);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Iterable<? extends T>, java.lang.Object, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.util.Collection<T> convertToSetForSetOperationWith(java.lang.Iterable<? extends T> r2, java.lang.Iterable<? extends T> r3) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            java.lang.String r0 = "source"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            boolean r0 = r2 instanceof java.util.Set
            if (r0 == 0) goto L_0x0014
            r0 = r2
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x004f
        L_0x0014:
            boolean r0 = r2 instanceof java.util.Collection
            if (r0 == 0) goto L_0x003f
            boolean r0 = r3 instanceof java.util.Collection
            if (r0 == 0) goto L_0x002b
            r0 = r3
            java.util.Collection r0 = (java.util.Collection) r0
            int r0 = r0.size()
            r1 = 2
            if (r0 >= r1) goto L_0x002b
            r0 = r2
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x004f
        L_0x002b:
            r0 = r2
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = safeToConvertToSet(r0)
            if (r0 == 0) goto L_0x003b
            java.util.HashSet r0 = kotlin.collections.CollectionsKt.toHashSet(r2)
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x004f
        L_0x003b:
            r0 = r2
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x004f
        L_0x003f:
            r0 = 0
            boolean r0 = kotlin.collections.CollectionSystemProperties.brittleContainsOptimizationEnabled
            if (r0 == 0) goto L_0x0049
            java.util.HashSet r0 = kotlin.collections.CollectionsKt.toHashSet(r2)
            goto L_0x004d
        L_0x0049:
            java.util.List r0 = kotlin.collections.CollectionsKt.toList(r2)
        L_0x004d:
            java.util.Collection r0 = (java.util.Collection) r0
        L_0x004f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.BrittleContainsOptimizationKt.convertToSetForSetOperationWith(java.lang.Iterable, java.lang.Iterable):java.util.Collection");
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Iterable<? extends T>, java.lang.Object, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.util.Collection<T> convertToSetForSetOperation(java.lang.Iterable<? extends T> r1) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            boolean r0 = r1 instanceof java.util.Set
            if (r0 == 0) goto L_0x000e
            r0 = r1
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x0036
        L_0x000e:
            boolean r0 = r1 instanceof java.util.Collection
            if (r0 == 0) goto L_0x0026
            r0 = r1
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = safeToConvertToSet(r0)
            if (r0 == 0) goto L_0x0022
            java.util.HashSet r0 = kotlin.collections.CollectionsKt.toHashSet(r1)
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x0036
        L_0x0022:
            r0 = r1
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x0036
        L_0x0026:
            r0 = 0
            boolean r0 = kotlin.collections.CollectionSystemProperties.brittleContainsOptimizationEnabled
            if (r0 == 0) goto L_0x0030
            java.util.HashSet r0 = kotlin.collections.CollectionsKt.toHashSet(r1)
            goto L_0x0034
        L_0x0030:
            java.util.List r0 = kotlin.collections.CollectionsKt.toList(r1)
        L_0x0034:
            java.util.Collection r0 = (java.util.Collection) r0
        L_0x0036:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.BrittleContainsOptimizationKt.convertToSetForSetOperation(java.lang.Iterable):java.util.Collection");
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [kotlin.sequences.Sequence, kotlin.sequences.Sequence<? extends T>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.util.Collection<T> convertToSetForSetOperation(kotlin.sequences.Sequence<? extends T> r1) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            r0 = 0
            boolean r0 = kotlin.collections.CollectionSystemProperties.brittleContainsOptimizationEnabled
            if (r0 == 0) goto L_0x000f
            java.util.HashSet r0 = kotlin.sequences.SequencesKt.toHashSet(r1)
            goto L_0x0013
        L_0x000f:
            java.util.List r0 = kotlin.sequences.SequencesKt.toList(r1)
        L_0x0013:
            java.util.Collection r0 = (java.util.Collection) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.BrittleContainsOptimizationKt.convertToSetForSetOperation(kotlin.sequences.Sequence):java.util.Collection");
    }

    public static final <T> Collection<T> convertToSetForSetOperation(T[] $this$convertToSetForSetOperation) {
        Intrinsics.checkNotNullParameter($this$convertToSetForSetOperation, "<this>");
        return CollectionSystemProperties.brittleContainsOptimizationEnabled != 0 ? ArraysKt.toHashSet($this$convertToSetForSetOperation) : ArraysKt.asList($this$convertToSetForSetOperation);
    }
}

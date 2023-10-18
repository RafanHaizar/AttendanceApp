package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, mo113d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: Comparisons.kt */
public final class ComparisonsKt__ComparisonsKt$thenComparator$1<T> implements Comparator {
    final /* synthetic */ Function2<T, T, Integer> $comparison;
    final /* synthetic */ Comparator<T> $this_thenComparator;

    public ComparisonsKt__ComparisonsKt$thenComparator$1(Comparator<T> comparator, Function2<? super T, ? super T, Integer> function2) {
        this.$this_thenComparator = comparator;
        this.$comparison = function2;
    }

    public final int compare(T a, T b) {
        int previousCompare = this.$this_thenComparator.compare(a, b);
        return previousCompare != 0 ? previousCompare : this.$comparison.invoke(a, b).intValue();
    }
}

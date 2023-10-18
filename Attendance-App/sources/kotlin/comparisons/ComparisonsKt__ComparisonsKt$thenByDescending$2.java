package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u000e\u0010\u0004\u001a\n \u0005*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0006\u001a\n \u0005*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b"}, mo113d2 = {"<anonymous>", "", "T", "K", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: Comparisons.kt */
public final class ComparisonsKt__ComparisonsKt$thenByDescending$2<T> implements Comparator {
    final /* synthetic */ Comparator<? super K> $comparator;
    final /* synthetic */ Function1<T, K> $selector;
    final /* synthetic */ Comparator<T> $this_thenByDescending;

    public ComparisonsKt__ComparisonsKt$thenByDescending$2(Comparator<T> comparator, Comparator<? super K> comparator2, Function1<? super T, ? extends K> function1) {
        this.$this_thenByDescending = comparator;
        this.$comparator = comparator2;
        this.$selector = function1;
    }

    public final int compare(T a, T b) {
        int previousCompare = this.$this_thenByDescending.compare(a, b);
        if (previousCompare != 0) {
            return previousCompare;
        }
        Comparator<? super K> comparator = this.$comparator;
        Function1<T, K> function1 = this.$selector;
        return comparator.compare(function1.invoke(b), function1.invoke(a));
    }
}

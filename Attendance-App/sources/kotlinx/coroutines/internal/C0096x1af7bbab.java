package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, mo113d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$$inlined$sortedByDescending$1 */
/* compiled from: Comparisons.kt */
public final class C0096x1af7bbab<T> implements Comparator {
    public final int compare(T a, T b) {
        return ComparisonsKt.compareValues(Integer.valueOf(((Constructor) b).getParameterTypes().length), Integer.valueOf(((Constructor) a).getParameterTypes().length));
    }
}

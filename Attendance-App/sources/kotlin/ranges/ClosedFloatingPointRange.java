package kotlin.ranges;

import com.itextpdf.forms.xfdf.XfdfConstants;
import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0016J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\f¨\u0006\r"}, mo113d2 = {"Lkotlin/ranges/ClosedFloatingPointRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "contains", "", "value", "(Ljava/lang/Comparable;)Z", "isEmpty", "lessThanOrEquals", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Z", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Ranges.kt */
public interface ClosedFloatingPointRange<T extends Comparable<? super T>> extends ClosedRange<T> {
    boolean contains(T t);

    boolean isEmpty();

    boolean lessThanOrEquals(T t, T t2);

    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Ranges.kt */
    public static final class DefaultImpls {
        public static <T extends Comparable<? super T>> boolean contains(ClosedFloatingPointRange<T> $this, T value) {
            Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
            return $this.lessThanOrEquals($this.getStart(), value) && $this.lessThanOrEquals(value, $this.getEndInclusive());
        }

        public static <T extends Comparable<? super T>> boolean isEmpty(ClosedFloatingPointRange<T> $this) {
            return !$this.lessThanOrEquals($this.getStart(), $this.getEndInclusive());
        }
    }
}

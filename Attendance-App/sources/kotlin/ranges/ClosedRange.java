package kotlin.ranges;

import com.itextpdf.forms.xfdf.XfdfConstants;
import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\nH\u0016R\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u000e"}, mo113d2 = {"Lkotlin/ranges/ClosedRange;", "T", "", "", "endInclusive", "getEndInclusive", "()Ljava/lang/Comparable;", "start", "getStart", "contains", "", "value", "(Ljava/lang/Comparable;)Z", "isEmpty", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Range.kt */
public interface ClosedRange<T extends Comparable<? super T>> {
    boolean contains(T t);

    T getEndInclusive();

    T getStart();

    boolean isEmpty();

    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Range.kt */
    public static final class DefaultImpls {
        public static <T extends Comparable<? super T>> boolean contains(ClosedRange<T> $this, T value) {
            Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
            return value.compareTo($this.getStart()) >= 0 && value.compareTo($this.getEndInclusive()) <= 0;
        }

        public static <T extends Comparable<? super T>> boolean isEmpty(ClosedRange<T> $this) {
            return $this.getStart().compareTo($this.getEndInclusive()) > 0;
        }
    }
}

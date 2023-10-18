package androidx.core.util;

import android.util.Range;
import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedRange;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a7\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\f\u001a6\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0006\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010\u0007\u001a7\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\n\u001a0\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\u0002H\u00022\u0006\u0010\t\u001a\u0002H\u0002H\f¢\u0006\u0002\u0010\n\u001a(\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a(\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\fH\u0007¨\u0006\u000e"}, mo113d2 = {"and", "Landroid/util/Range;", "T", "", "other", "plus", "value", "(Landroid/util/Range;Ljava/lang/Comparable;)Landroid/util/Range;", "rangeTo", "that", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Landroid/util/Range;", "toClosedRange", "Lkotlin/ranges/ClosedRange;", "toRange", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Range.kt */
public final class RangeKt {
    public static final <T extends Comparable<? super T>> Range<T> rangeTo(T $this$rangeTo, T that) {
        Intrinsics.checkNotNullParameter($this$rangeTo, "<this>");
        Intrinsics.checkNotNullParameter(that, "that");
        return new Range<>($this$rangeTo, that);
    }

    public static final <T extends Comparable<? super T>> Range<T> plus(Range<T> $this$plus, T value) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        Range<T> extend = $this$plus.extend(value);
        Intrinsics.checkNotNullExpressionValue(extend, "extend(value)");
        return extend;
    }

    public static final <T extends Comparable<? super T>> Range<T> plus(Range<T> $this$plus, Range<T> other) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Range<T> extend = $this$plus.extend(other);
        Intrinsics.checkNotNullExpressionValue(extend, "extend(other)");
        return extend;
    }

    public static final <T extends Comparable<? super T>> Range<T> and(Range<T> $this$and, Range<T> other) {
        Intrinsics.checkNotNullParameter($this$and, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Range<T> intersect = $this$and.intersect(other);
        Intrinsics.checkNotNullExpressionValue(intersect, "intersect(other)");
        return intersect;
    }

    public static final <T extends Comparable<? super T>> ClosedRange<T> toClosedRange(Range<T> $this$toClosedRange) {
        Intrinsics.checkNotNullParameter($this$toClosedRange, "<this>");
        return new RangeKt$toClosedRange$1($this$toClosedRange);
    }

    public static final <T extends Comparable<? super T>> Range<T> toRange(ClosedRange<T> $this$toRange) {
        Intrinsics.checkNotNullParameter($this$toRange, "<this>");
        return new Range<>($this$toRange.getStart(), $this$toRange.getEndInclusive());
    }
}

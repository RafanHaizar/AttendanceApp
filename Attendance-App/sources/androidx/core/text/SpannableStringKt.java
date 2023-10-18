package androidx.core.text;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(mo112d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a%\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\n\u001a\u001d\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\n\u001a\r\u0010\u000b\u001a\u00020\u0002*\u00020\fH\b¨\u0006\r"}, mo113d2 = {"clearSpans", "", "Landroid/text/Spannable;", "set", "start", "", "end", "span", "", "range", "Lkotlin/ranges/IntRange;", "toSpannable", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: SpannableString.kt */
public final class SpannableStringKt {
    public static final Spannable toSpannable(CharSequence $this$toSpannable) {
        Intrinsics.checkNotNullParameter($this$toSpannable, "<this>");
        SpannableString valueOf = SpannableString.valueOf($this$toSpannable);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    public static final void clearSpans(Spannable $this$clearSpans) {
        Intrinsics.checkNotNullParameter($this$clearSpans, "<this>");
        Spanned $this$getSpans_u24default$iv = $this$clearSpans;
        Object[] $this$forEach$iv = $this$getSpans_u24default$iv.getSpans(0, $this$getSpans_u24default$iv.length(), Object.class);
        Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "getSpans(start, end, T::class.java)");
        for (Object element$iv : $this$forEach$iv) {
            $this$clearSpans.removeSpan(element$iv);
        }
    }

    public static final void set(Spannable $this$set, int start, int end, Object span) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(span, "span");
        $this$set.setSpan(span, start, end, 17);
    }

    public static final void set(Spannable $this$set, IntRange range, Object span) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(span, "span");
        $this$set.setSpan(span, range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
    }
}

package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import com.itextpdf.svg.SvgConstants;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0015\u0010\u0010\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\n\u001a3\u0010\u0013\u001a\u00020\u0014*\u00020\u00032!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00140\u0016H\bø\u0001\u0000\u001aH\u0010\u0019\u001a\u00020\u0014*\u00020\u000326\u0010\u0015\u001a2\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00140\u001aH\bø\u0001\u0000\u001a\u0015\u0010\u001c\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\u001b\u001a\u00020\rH\u0002\u001a\r\u0010\u001d\u001a\u00020\u0011*\u00020\u0003H\b\u001a\r\u0010\u001e\u001a\u00020\u0011*\u00020\u0003H\b\u001a\u0013\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020 *\u00020\u0003H\u0002\u001a\u0015\u0010!\u001a\u00020\u0014*\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\n\u001a\u0015\u0010\"\u001a\u00020\u0014*\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\n\u001a\u0017\u0010#\u001a\u00020\u0014*\u00020$2\b\b\u0001\u0010\f\u001a\u00020\rH\b\u001a5\u0010%\u001a\u00020\u0014*\u00020$2\b\b\u0003\u0010&\u001a\u00020\r2\b\b\u0003\u0010'\u001a\u00020\r2\b\b\u0003\u0010(\u001a\u00020\r2\b\b\u0003\u0010)\u001a\u00020\rH\b\u001a5\u0010*\u001a\u00020\u0014*\u00020$2\b\b\u0003\u0010+\u001a\u00020\r2\b\b\u0003\u0010'\u001a\u00020\r2\b\b\u0003\u0010,\u001a\u00020\r2\b\b\u0003\u0010)\u001a\u00020\rH\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0016\u0010\b\u001a\u00020\t*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0016\u0010\f\u001a\u00020\r*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0002\u0007\n\u0005\b20\u0001¨\u0006-"}, mo113d2 = {"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "descendants", "getDescendants", "indices", "Lkotlin/ranges/IntRange;", "getIndices", "(Landroid/view/ViewGroup;)Lkotlin/ranges/IntRange;", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", "view", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ViewGroup.kt */
public final class ViewGroupKt {
    public static final View get(ViewGroup $this$get, int index) {
        Intrinsics.checkNotNullParameter($this$get, "<this>");
        View childAt = $this$get.getChildAt(index);
        if (childAt != null) {
            return childAt;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + $this$get.getChildCount());
    }

    public static final boolean contains(ViewGroup $this$contains, View view) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        return $this$contains.indexOfChild(view) != -1;
    }

    public static final void plusAssign(ViewGroup $this$plusAssign, View view) {
        Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        $this$plusAssign.addView(view);
    }

    public static final void minusAssign(ViewGroup $this$minusAssign, View view) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        $this$minusAssign.removeView(view);
    }

    public static final int getSize(ViewGroup $this$size) {
        Intrinsics.checkNotNullParameter($this$size, "<this>");
        return $this$size.getChildCount();
    }

    public static final boolean isEmpty(ViewGroup $this$isEmpty) {
        Intrinsics.checkNotNullParameter($this$isEmpty, "<this>");
        return $this$isEmpty.getChildCount() == 0;
    }

    public static final boolean isNotEmpty(ViewGroup $this$isNotEmpty) {
        Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.getChildCount() != 0;
    }

    public static final void forEach(ViewGroup $this$forEach, Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int childCount = $this$forEach.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childAt = $this$forEach.getChildAt(index);
            Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(index)");
            action.invoke(childAt);
        }
    }

    public static final void forEachIndexed(ViewGroup $this$forEachIndexed, Function2<? super Integer, ? super View, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEachIndexed, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int childCount = $this$forEachIndexed.getChildCount();
        for (int index = 0; index < childCount; index++) {
            Integer valueOf = Integer.valueOf(index);
            View childAt = $this$forEachIndexed.getChildAt(index);
            Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(index)");
            action.invoke(valueOf, childAt);
        }
    }

    public static final IntRange getIndices(ViewGroup $this$indices) {
        Intrinsics.checkNotNullParameter($this$indices, "<this>");
        return RangesKt.until(0, $this$indices.getChildCount());
    }

    public static final Iterator<View> iterator(ViewGroup $this$iterator) {
        Intrinsics.checkNotNullParameter($this$iterator, "<this>");
        return new ViewGroupKt$iterator$1($this$iterator);
    }

    public static final Sequence<View> getChildren(ViewGroup $this$children) {
        Intrinsics.checkNotNullParameter($this$children, "<this>");
        return new ViewGroupKt$children$1($this$children);
    }

    public static final Sequence<View> getDescendants(ViewGroup $this$descendants) {
        Intrinsics.checkNotNullParameter($this$descendants, "<this>");
        return SequencesKt.sequence(new ViewGroupKt$descendants$1($this$descendants, (Continuation<? super ViewGroupKt$descendants$1>) null));
    }

    public static final void setMargins(ViewGroup.MarginLayoutParams $this$setMargins, int size) {
        Intrinsics.checkNotNullParameter($this$setMargins, "<this>");
        $this$setMargins.setMargins(size, size, size, size);
    }

    public static /* synthetic */ void updateMargins$default(ViewGroup.MarginLayoutParams $this$updateMargins_u24default, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $this$updateMargins_u24default.leftMargin;
        }
        if ((i & 2) != 0) {
            top = $this$updateMargins_u24default.topMargin;
        }
        if ((i & 4) != 0) {
            right = $this$updateMargins_u24default.rightMargin;
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMargins_u24default.bottomMargin;
        }
        Intrinsics.checkNotNullParameter($this$updateMargins_u24default, "<this>");
        $this$updateMargins_u24default.setMargins(left, top, right, bottom);
    }

    public static final void updateMargins(ViewGroup.MarginLayoutParams $this$updateMargins, int left, int top, int right, int bottom) {
        Intrinsics.checkNotNullParameter($this$updateMargins, "<this>");
        $this$updateMargins.setMargins(left, top, right, bottom);
    }

    public static /* synthetic */ void updateMarginsRelative$default(ViewGroup.MarginLayoutParams $this$updateMarginsRelative_u24default, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $this$updateMarginsRelative_u24default.getMarginStart();
        }
        if ((i & 2) != 0) {
            top = $this$updateMarginsRelative_u24default.topMargin;
        }
        if ((i & 4) != 0) {
            end = $this$updateMarginsRelative_u24default.getMarginEnd();
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMarginsRelative_u24default.bottomMargin;
        }
        Intrinsics.checkNotNullParameter($this$updateMarginsRelative_u24default, "<this>");
        $this$updateMarginsRelative_u24default.setMarginStart(start);
        $this$updateMarginsRelative_u24default.topMargin = top;
        $this$updateMarginsRelative_u24default.setMarginEnd(end);
        $this$updateMarginsRelative_u24default.bottomMargin = bottom;
    }

    public static final void updateMarginsRelative(ViewGroup.MarginLayoutParams $this$updateMarginsRelative, int start, int top, int end, int bottom) {
        Intrinsics.checkNotNullParameter($this$updateMarginsRelative, "<this>");
        $this$updateMarginsRelative.setMarginStart(start);
        $this$updateMarginsRelative.topMargin = top;
        $this$updateMarginsRelative.setMarginEnd(end);
        $this$updateMarginsRelative.bottomMargin = bottom;
    }
}

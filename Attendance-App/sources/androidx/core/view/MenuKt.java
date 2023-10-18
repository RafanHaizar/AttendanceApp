package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo112d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0003\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\u0002\u001a3\u0010\r\u001a\u00020\u000e*\u00020\u00032!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0010H\bø\u0001\u0000\u001aH\u0010\u0013\u001a\u00020\u000e*\u00020\u000326\u0010\u000f\u001a2\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0014H\bø\u0001\u0000\u001a\u0015\u0010\u0016\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0007H\n\u001a\r\u0010\u0017\u001a\u00020\u000b*\u00020\u0003H\b\u001a\r\u0010\u0018\u001a\u00020\u000b*\u00020\u0003H\b\u001a\u0013\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u001a*\u00020\u0003H\u0002\u001a\u0015\u0010\u001b\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a\u0015\u0010\u001c\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0007H\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0016\u0010\u0006\u001a\u00020\u0007*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0002\u0007\n\u0005\b20\u0001¨\u0006\u001d"}, mo113d2 = {"children", "Lkotlin/sequences/Sequence;", "Landroid/view/MenuItem;", "Landroid/view/Menu;", "getChildren", "(Landroid/view/Menu;)Lkotlin/sequences/Sequence;", "size", "", "getSize", "(Landroid/view/Menu;)I", "contains", "", "item", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "removeItemAt", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Menu.kt */
public final class MenuKt {
    public static final MenuItem get(Menu $this$get, int index) {
        Intrinsics.checkNotNullParameter($this$get, "<this>");
        MenuItem item = $this$get.getItem(index);
        Intrinsics.checkNotNullExpressionValue(item, "getItem(index)");
        return item;
    }

    public static final boolean contains(Menu $this$contains, MenuItem item) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        Intrinsics.checkNotNullParameter(item, "item");
        int size = $this$contains.size();
        for (int index = 0; index < size; index++) {
            if (Intrinsics.areEqual((Object) $this$contains.getItem(index), (Object) item)) {
                return true;
            }
        }
        return false;
    }

    public static final void minusAssign(Menu $this$minusAssign, MenuItem item) {
        Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
        Intrinsics.checkNotNullParameter(item, "item");
        $this$minusAssign.removeItem(item.getItemId());
    }

    public static final int getSize(Menu $this$size) {
        Intrinsics.checkNotNullParameter($this$size, "<this>");
        return $this$size.size();
    }

    public static final boolean isEmpty(Menu $this$isEmpty) {
        Intrinsics.checkNotNullParameter($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final boolean isNotEmpty(Menu $this$isNotEmpty) {
        Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final void forEach(Menu $this$forEach, Function1<? super MenuItem, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = $this$forEach.size();
        for (int index = 0; index < size; index++) {
            MenuItem item = $this$forEach.getItem(index);
            Intrinsics.checkNotNullExpressionValue(item, "getItem(index)");
            action.invoke(item);
        }
    }

    public static final void forEachIndexed(Menu $this$forEachIndexed, Function2<? super Integer, ? super MenuItem, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEachIndexed, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = $this$forEachIndexed.size();
        for (int index = 0; index < size; index++) {
            Integer valueOf = Integer.valueOf(index);
            MenuItem item = $this$forEachIndexed.getItem(index);
            Intrinsics.checkNotNullExpressionValue(item, "getItem(index)");
            action.invoke(valueOf, item);
        }
    }

    public static final Iterator<MenuItem> iterator(Menu $this$iterator) {
        Intrinsics.checkNotNullParameter($this$iterator, "<this>");
        return new MenuKt$iterator$1($this$iterator);
    }

    public static final void removeItemAt(Menu $this$removeItemAt, int index) {
        Unit unit;
        Intrinsics.checkNotNullParameter($this$removeItemAt, "<this>");
        MenuItem it = $this$removeItemAt.getItem(index);
        if (it != null) {
            $this$removeItemAt.removeItem(it.getItemId());
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static final Sequence<MenuItem> getChildren(Menu $this$children) {
        Intrinsics.checkNotNullParameter($this$children, "<this>");
        return new MenuKt$children$1($this$children);
    }
}

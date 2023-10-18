package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;

@Metadata(mo112d1 = {"\u0000#\n\u0000\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0002J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"androidx/core/view/MenuKt$iterator$1", "", "Landroid/view/MenuItem;", "index", "", "hasNext", "", "next", "remove", "", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Menu.kt */
public final class MenuKt$iterator$1 implements Iterator<MenuItem>, KMutableIterator {
    final /* synthetic */ Menu $this_iterator;
    private int index;

    MenuKt$iterator$1(Menu $receiver) {
        this.$this_iterator = $receiver;
    }

    public boolean hasNext() {
        return this.index < this.$this_iterator.size();
    }

    public MenuItem next() {
        Menu menu = this.$this_iterator;
        int i = this.index;
        this.index = i + 1;
        MenuItem item = menu.getItem(i);
        if (item != null) {
            return item;
        }
        throw new IndexOutOfBoundsException();
    }

    public void remove() {
        Unit unit;
        Menu $this$removeItemAt$iv = this.$this_iterator;
        this.index--;
        MenuItem it$iv = $this$removeItemAt$iv.getItem(this.index);
        if (it$iv != null) {
            Intrinsics.checkNotNullExpressionValue(it$iv, "getItem(index)");
            $this$removeItemAt$iv.removeItem(it$iv.getItemId());
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            throw new IndexOutOfBoundsException();
        }
    }
}

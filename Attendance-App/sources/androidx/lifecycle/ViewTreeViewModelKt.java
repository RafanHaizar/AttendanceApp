package androidx.lifecycle;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, mo113d2 = {"findViewTreeViewModelStoreOwner", "Landroidx/lifecycle/ViewModelStoreOwner;", "Landroid/view/View;", "lifecycle-viewmodel_release"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ViewTreeViewModel.kt */
public final class ViewTreeViewModelKt {
    public static final ViewModelStoreOwner findViewTreeViewModelStoreOwner(View $this$findViewTreeViewModelStoreOwner) {
        Intrinsics.checkNotNullParameter($this$findViewTreeViewModelStoreOwner, "<this>");
        return ViewTreeViewModelStoreOwner.get($this$findViewTreeViewModelStoreOwner);
    }
}

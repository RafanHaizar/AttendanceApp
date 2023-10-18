package androidx.core.view;

import android.view.View;
import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\u00020\u0001JP\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016¨\u0006\u000f"}, mo113d2 = {"androidx/core/view/ViewKt$doOnNextLayout$1", "Landroid/view/View$OnLayoutChangeListener;", "onLayoutChange", "", "view", "Landroid/view/View;", "left", "", "top", "right", "bottom", "oldLeft", "oldTop", "oldRight", "oldBottom", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: View.kt */
public final class ViewKt$doOnNextLayout$1 implements View.OnLayoutChangeListener {
    final /* synthetic */ Function1<View, Unit> $action;

    public ViewKt$doOnNextLayout$1(Function1<? super View, Unit> $action2) {
        this.$action = $action2;
    }

    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        view.removeOnLayoutChangeListener(this);
        this.$action.invoke(view);
    }
}

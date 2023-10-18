package androidx.savedstate;

import android.view.View;
import android.view.ViewParent;
import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo113d2 = {"<anonymous>", "Landroid/view/View;", "view", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: androidx.savedstate.ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1 */
/* compiled from: ViewTreeSavedStateRegistryOwner.kt */
final class C0982x10fac0e2 extends Lambda implements Function1<View, View> {
    public static final C0982x10fac0e2 INSTANCE = new C0982x10fac0e2();

    C0982x10fac0e2() {
        super(1);
    }

    public final View invoke(View view) {
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}

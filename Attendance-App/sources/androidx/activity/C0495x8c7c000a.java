package androidx.activity;

import android.view.View;
import android.view.ViewParent;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo113d2 = {"<anonymous>", "Landroid/view/View;", "it", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.activity.ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$1 */
/* compiled from: ViewTreeOnBackPressedDispatcherOwner.kt */
final class C0495x8c7c000a extends Lambda implements Function1<View, View> {
    public static final C0495x8c7c000a INSTANCE = new C0495x8c7c000a();

    C0495x8c7c000a() {
        super(1);
    }

    public final View invoke(View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        ViewParent parent = it.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}

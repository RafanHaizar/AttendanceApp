package androidx.activity;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "Landroidx/activity/OnBackPressedDispatcherOwner;", "it", "Landroid/view/View;", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.activity.ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2 */
/* compiled from: ViewTreeOnBackPressedDispatcherOwner.kt */
final class C0496x8c7c000b extends Lambda implements Function1<View, OnBackPressedDispatcherOwner> {
    public static final C0496x8c7c000b INSTANCE = new C0496x8c7c000b();

    C0496x8c7c000b() {
        super(1);
    }

    public final OnBackPressedDispatcherOwner invoke(View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Object tag = it.getTag(C0493R.C0494id.view_tree_on_back_pressed_dispatcher_owner);
        if (tag instanceof OnBackPressedDispatcherOwner) {
            return (OnBackPressedDispatcherOwner) tag;
        }
        return null;
    }
}

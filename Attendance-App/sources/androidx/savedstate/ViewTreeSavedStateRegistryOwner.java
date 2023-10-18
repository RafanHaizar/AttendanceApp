package androidx.savedstate;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u001b\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, mo113d2 = {"findViewTreeSavedStateRegistryOwner", "Landroidx/savedstate/SavedStateRegistryOwner;", "Landroid/view/View;", "get", "setViewTreeSavedStateRegistryOwner", "", "owner", "set", "savedstate_release"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ViewTreeSavedStateRegistryOwner.kt */
public final class ViewTreeSavedStateRegistryOwner {
    public static final void set(View $this$setViewTreeSavedStateRegistryOwner, SavedStateRegistryOwner owner) {
        Intrinsics.checkNotNullParameter($this$setViewTreeSavedStateRegistryOwner, "<this>");
        $this$setViewTreeSavedStateRegistryOwner.setTag(C0980R.C0981id.view_tree_saved_state_registry_owner, owner);
    }

    public static final SavedStateRegistryOwner get(View $this$findViewTreeSavedStateRegistryOwner) {
        Intrinsics.checkNotNullParameter($this$findViewTreeSavedStateRegistryOwner, "<this>");
        return (SavedStateRegistryOwner) SequencesKt.firstOrNull(SequencesKt.mapNotNull(SequencesKt.generateSequence($this$findViewTreeSavedStateRegistryOwner, C0982x10fac0e2.INSTANCE), C0983x10fac0e3.INSTANCE));
    }
}

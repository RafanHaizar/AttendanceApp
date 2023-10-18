package androidx.lifecycle;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo113d2 = {"<anonymous>", "Landroidx/lifecycle/SavedStateHandlesVM;", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SavedStateHandleSupport.kt */
final class SavedStateHandlesProvider$viewModel$2 extends Lambda implements Function0<SavedStateHandlesVM> {
    final /* synthetic */ ViewModelStoreOwner $viewModelStoreOwner;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SavedStateHandlesProvider$viewModel$2(ViewModelStoreOwner viewModelStoreOwner) {
        super(0);
        this.$viewModelStoreOwner = viewModelStoreOwner;
    }

    public final SavedStateHandlesVM invoke() {
        return SavedStateHandleSupport.getSavedStateHandlesVM(this.$viewModelStoreOwner);
    }
}

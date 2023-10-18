package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\nH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0017"}, mo113d2 = {"Landroidx/lifecycle/SavedStateHandlesProvider;", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "savedStateRegistry", "Landroidx/savedstate/SavedStateRegistry;", "viewModelStoreOwner", "Landroidx/lifecycle/ViewModelStoreOwner;", "(Landroidx/savedstate/SavedStateRegistry;Landroidx/lifecycle/ViewModelStoreOwner;)V", "restored", "", "restoredState", "Landroid/os/Bundle;", "viewModel", "Landroidx/lifecycle/SavedStateHandlesVM;", "getViewModel", "()Landroidx/lifecycle/SavedStateHandlesVM;", "viewModel$delegate", "Lkotlin/Lazy;", "consumeRestoredStateForKey", "key", "", "performRestore", "", "saveState", "lifecycle-viewmodel-savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SavedStateHandleSupport.kt */
public final class SavedStateHandlesProvider implements SavedStateRegistry.SavedStateProvider {
    private boolean restored;
    private Bundle restoredState;
    private final SavedStateRegistry savedStateRegistry;
    private final Lazy viewModel$delegate;

    public SavedStateHandlesProvider(SavedStateRegistry savedStateRegistry2, ViewModelStoreOwner viewModelStoreOwner) {
        Intrinsics.checkNotNullParameter(savedStateRegistry2, "savedStateRegistry");
        Intrinsics.checkNotNullParameter(viewModelStoreOwner, "viewModelStoreOwner");
        this.savedStateRegistry = savedStateRegistry2;
        this.viewModel$delegate = LazyKt.lazy(new SavedStateHandlesProvider$viewModel$2(viewModelStoreOwner));
    }

    private final SavedStateHandlesVM getViewModel() {
        return (SavedStateHandlesVM) this.viewModel$delegate.getValue();
    }

    public Bundle saveState() {
        Bundle bundle = new Bundle();
        Bundle $this$saveState_u24lambda_u2d1 = bundle;
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            $this$saveState_u24lambda_u2d1.putAll(bundle2);
        }
        for (Map.Entry element$iv : getViewModel().getHandles().entrySet()) {
            String key = (String) element$iv.getKey();
            Bundle savedState = ((SavedStateHandle) element$iv.getValue()).savedStateProvider().saveState();
            if (!Intrinsics.areEqual((Object) savedState, (Object) Bundle.EMPTY)) {
                $this$saveState_u24lambda_u2d1.putBundle(key, savedState);
            }
        }
        Bundle bundle3 = bundle;
        this.restored = false;
        return bundle;
    }

    public final void performRestore() {
        if (!this.restored) {
            this.restoredState = this.savedStateRegistry.consumeRestoredStateForKey("androidx.lifecycle.internal.SavedStateHandlesProvider");
            this.restored = true;
            getViewModel();
        }
    }

    public final Bundle consumeRestoredStateForKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        performRestore();
        Bundle bundle = this.restoredState;
        Bundle bundle2 = bundle != null ? bundle.getBundle(key) : null;
        Bundle bundle3 = bundle2;
        Bundle bundle4 = this.restoredState;
        if (bundle4 != null) {
            bundle4.remove(key);
        }
        Bundle bundle5 = this.restoredState;
        boolean z = false;
        if (bundle5 != null && bundle5.isEmpty()) {
            z = true;
        }
        if (z) {
            this.restoredState = null;
        }
        return bundle2;
    }
}

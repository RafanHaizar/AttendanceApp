package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u001e\u0010\u0004\u001a\u0002H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006*\u00020\u0007H\b¢\u0006\u0002\u0010\b¨\u0006\t"}, mo113d2 = {"defaultCreationExtras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "get", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/lifecycle/ViewModelProvider;", "(Landroidx/lifecycle/ViewModelProvider;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ViewModelProvider.kt */
public final class ViewModelProviderGetKt {
    public static final CreationExtras defaultCreationExtras(ViewModelStoreOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (!(owner instanceof HasDefaultViewModelProviderFactory)) {
            return CreationExtras.Empty.INSTANCE;
        }
        CreationExtras defaultViewModelCreationExtras = ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "{\n        owner.defaultV…ModelCreationExtras\n    }");
        return defaultViewModelCreationExtras;
    }

    public static final /* synthetic */ <VM extends ViewModel> VM get(ViewModelProvider $this$get) {
        Intrinsics.checkNotNullParameter($this$get, "<this>");
        Intrinsics.reifiedOperationMarker(4, "VM");
        Class<ViewModel> cls = ViewModel.class;
        Class cls2 = cls;
        return $this$get.get(cls);
    }
}

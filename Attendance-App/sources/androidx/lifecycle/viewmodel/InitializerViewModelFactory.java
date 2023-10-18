package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B!\u0012\u001a\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\"\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005J-\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eR\u001c\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u000f"}, mo113d2 = {"Landroidx/lifecycle/viewmodel/InitializerViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "initializers", "", "Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "([Landroidx/lifecycle/viewmodel/ViewModelInitializer;)V", "[Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "extras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: InitializerViewModelFactory.kt */
public final class InitializerViewModelFactory implements ViewModelProvider.Factory {
    private final ViewModelInitializer<?>[] initializers;

    public /* synthetic */ ViewModel create(Class cls) {
        return ViewModelProvider.Factory.CC.$default$create(this, cls);
    }

    public InitializerViewModelFactory(ViewModelInitializer<?>... initializers2) {
        Intrinsics.checkNotNullParameter(initializers2, "initializers");
        this.initializers = initializers2;
    }

    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        Object viewModel = null;
        for (ViewModelInitializer it : this.initializers) {
            if (Intrinsics.areEqual((Object) it.getClazz$lifecycle_viewmodel_release(), (Object) modelClass)) {
                Object invoke = it.getInitializer$lifecycle_viewmodel_release().invoke(extras);
                viewModel = invoke instanceof ViewModel ? (ViewModel) invoke : null;
            }
        }
        if (viewModel != null) {
            return viewModel;
        }
        throw new IllegalArgumentException("No initializer set for given class " + modelClass.getName());
    }
}

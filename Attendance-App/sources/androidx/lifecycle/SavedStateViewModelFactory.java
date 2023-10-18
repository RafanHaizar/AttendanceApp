package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u0019\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB#\b\u0017\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ%\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0015H\u0016¢\u0006\u0002\u0010\u0016J-\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016¢\u0006\u0002\u0010\u0019J+\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0015¢\u0006\u0002\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0013H\u0017R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0002X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, mo113d2 = {"Landroidx/lifecycle/SavedStateViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$OnRequeryFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "()V", "application", "Landroid/app/Application;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Landroid/app/Application;Landroidx/savedstate/SavedStateRegistryOwner;)V", "defaultArgs", "Landroid/os/Bundle;", "(Landroid/app/Application;Landroidx/savedstate/SavedStateRegistryOwner;Landroid/os/Bundle;)V", "factory", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "savedStateRegistry", "Landroidx/savedstate/SavedStateRegistry;", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "extras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "key", "", "(Ljava/lang/String;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "onRequery", "", "viewModel", "lifecycle-viewmodel-savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SavedStateViewModelFactory.kt */
public final class SavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    private Application application;
    private Bundle defaultArgs;
    private final ViewModelProvider.Factory factory;
    private Lifecycle lifecycle;
    private SavedStateRegistry savedStateRegistry;

    public SavedStateViewModelFactory() {
        this.factory = new ViewModelProvider.AndroidViewModelFactory();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SavedStateViewModelFactory(Application application2, SavedStateRegistryOwner owner) {
        this(application2, owner, (Bundle) null);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    public SavedStateViewModelFactory(Application application2, SavedStateRegistryOwner owner, Bundle defaultArgs2) {
        ViewModelProvider.Factory factory2;
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.savedStateRegistry = owner.getSavedStateRegistry();
        this.lifecycle = owner.getLifecycle();
        this.defaultArgs = defaultArgs2;
        this.application = application2;
        if (application2 != null) {
            factory2 = ViewModelProvider.AndroidViewModelFactory.Companion.getInstance(application2);
        } else {
            factory2 = new ViewModelProvider.AndroidViewModelFactory();
        }
        this.factory = factory2;
    }

    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        Constructor constructor;
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        String key = (String) extras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (key == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        } else if (extras.get(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY) != null && extras.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY) != null) {
            Application application2 = (Application) extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
            boolean isAndroidViewModel = AndroidViewModel.class.isAssignableFrom(modelClass);
            if (!isAndroidViewModel || application2 == null) {
                constructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE);
            } else {
                constructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
            }
            if (constructor == null) {
                return this.factory.create(modelClass, extras);
            }
            if (!isAndroidViewModel || application2 == null) {
                return SavedStateViewModelFactoryKt.newInstance(modelClass, constructor, SavedStateHandleSupport.createSavedStateHandle(extras));
            }
            return SavedStateViewModelFactoryKt.newInstance(modelClass, constructor, application2, SavedStateHandleSupport.createSavedStateHandle(extras));
        } else if (this.lifecycle != null) {
            return create(key, modelClass);
        } else {
            throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
        }
    }

    public final <T extends ViewModel> T create(String key, Class<T> modelClass) {
        Constructor constructor;
        ViewModel viewModel;
        Application application2;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        if (this.lifecycle != null) {
            boolean isAndroidViewModel = AndroidViewModel.class.isAssignableFrom(modelClass);
            if (!isAndroidViewModel || this.application == null) {
                constructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE);
            } else {
                constructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
            }
            if (constructor != null) {
                SavedStateHandleController controller = LegacySavedStateHandleController.create(this.savedStateRegistry, this.lifecycle, key, this.defaultArgs);
                if (!isAndroidViewModel || (application2 = this.application) == null) {
                    SavedStateHandle handle = controller.getHandle();
                    Intrinsics.checkNotNullExpressionValue(handle, "controller.handle");
                    viewModel = SavedStateViewModelFactoryKt.newInstance(modelClass, constructor, handle);
                } else {
                    Intrinsics.checkNotNull(application2);
                    SavedStateHandle handle2 = controller.getHandle();
                    Intrinsics.checkNotNullExpressionValue(handle2, "controller.handle");
                    viewModel = SavedStateViewModelFactoryKt.newInstance(modelClass, constructor, application2, handle2);
                }
                viewModel.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", controller);
                return viewModel;
            } else if (this.application != null) {
                return this.factory.create(modelClass);
            } else {
                return ViewModelProvider.NewInstanceFactory.Companion.getInstance().create(modelClass);
            }
        } else {
            throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName != null) {
            return create(canonicalName, modelClass);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public void onRequery(ViewModel viewModel) {
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        Lifecycle lifecycle2 = this.lifecycle;
        if (lifecycle2 != null) {
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, this.savedStateRegistry, lifecycle2);
        }
    }
}

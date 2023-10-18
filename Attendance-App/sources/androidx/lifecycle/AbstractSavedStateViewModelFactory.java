package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

public abstract class AbstractSavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";
    private Bundle mDefaultArgs;
    private Lifecycle mLifecycle;
    private SavedStateRegistry mSavedStateRegistry;

    /* access modifiers changed from: protected */
    public abstract <T extends ViewModel> T create(String str, Class<T> cls, SavedStateHandle savedStateHandle);

    public AbstractSavedStateViewModelFactory() {
    }

    public AbstractSavedStateViewModelFactory(SavedStateRegistryOwner owner, Bundle defaultArgs) {
        this.mSavedStateRegistry = owner.getSavedStateRegistry();
        this.mLifecycle = owner.getLifecycle();
        this.mDefaultArgs = defaultArgs;
    }

    public final <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        String key = (String) extras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (key == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        } else if (this.mSavedStateRegistry != null) {
            return create(key, modelClass);
        } else {
            return create(key, modelClass, SavedStateHandleSupport.createSavedStateHandle(extras));
        }
    }

    private <T extends ViewModel> T create(String key, Class<T> modelClass) {
        SavedStateHandleController controller = LegacySavedStateHandleController.create(this.mSavedStateRegistry, this.mLifecycle, key, this.mDefaultArgs);
        T viewmodel = create(key, modelClass, controller.getHandle());
        viewmodel.setTagIfAbsent(TAG_SAVED_STATE_HANDLE_CONTROLLER, controller);
        return viewmodel;
    }

    public final <T extends ViewModel> T create(Class<T> modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        } else if (this.mLifecycle != null) {
            return create(canonicalName, modelClass);
        } else {
            throw new UnsupportedOperationException("AbstractSavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
    }

    public void onRequery(ViewModel viewModel) {
        SavedStateRegistry savedStateRegistry = this.mSavedStateRegistry;
        if (savedStateRegistry != null) {
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, this.mLifecycle);
        }
    }
}

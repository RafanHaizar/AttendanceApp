package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

class LegacySavedStateHandleController {
    static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";

    private LegacySavedStateHandleController() {
    }

    static SavedStateHandleController create(SavedStateRegistry registry, Lifecycle lifecycle, String key, Bundle defaultArgs) {
        SavedStateHandleController controller = new SavedStateHandleController(key, SavedStateHandle.createHandle(registry.consumeRestoredStateForKey(key), defaultArgs));
        controller.attachToLifecycle(registry, lifecycle);
        tryToAddRecreator(registry, lifecycle);
        return controller;
    }

    static final class OnRecreation implements SavedStateRegistry.AutoRecreated {
        OnRecreation() {
        }

        public void onRecreated(SavedStateRegistryOwner owner) {
            if (owner instanceof ViewModelStoreOwner) {
                ViewModelStore viewModelStore = ((ViewModelStoreOwner) owner).getViewModelStore();
                SavedStateRegistry savedStateRegistry = owner.getSavedStateRegistry();
                for (String key : viewModelStore.keys()) {
                    LegacySavedStateHandleController.attachHandleIfNeeded(viewModelStore.get(key), savedStateRegistry, owner.getLifecycle());
                }
                if (!viewModelStore.keys().isEmpty()) {
                    savedStateRegistry.runOnNextRecreation(OnRecreation.class);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner");
        }
    }

    static void attachHandleIfNeeded(ViewModel viewModel, SavedStateRegistry registry, Lifecycle lifecycle) {
        SavedStateHandleController controller = (SavedStateHandleController) viewModel.getTag(TAG_SAVED_STATE_HANDLE_CONTROLLER);
        if (controller != null && !controller.isAttached()) {
            controller.attachToLifecycle(registry, lifecycle);
            tryToAddRecreator(registry, lifecycle);
        }
    }

    private static void tryToAddRecreator(final SavedStateRegistry registry, final Lifecycle lifecycle) {
        Lifecycle.State currentState = lifecycle.getCurrentState();
        if (currentState == Lifecycle.State.INITIALIZED || currentState.isAtLeast(Lifecycle.State.STARTED)) {
            registry.runOnNextRecreation(OnRecreation.class);
        } else {
            lifecycle.addObserver(new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_START) {
                        Lifecycle.this.removeObserver(this);
                        registry.runOnNextRecreation(OnRecreation.class);
                    }
                }
            });
        }
    }
}

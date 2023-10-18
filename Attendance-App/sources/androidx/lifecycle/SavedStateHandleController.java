package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;

final class SavedStateHandleController implements LifecycleEventObserver {
    private final SavedStateHandle mHandle;
    private boolean mIsAttached = false;
    private final String mKey;

    SavedStateHandleController(String key, SavedStateHandle handle) {
        this.mKey = key;
        this.mHandle = handle;
    }

    /* access modifiers changed from: package-private */
    public boolean isAttached() {
        return this.mIsAttached;
    }

    /* access modifiers changed from: package-private */
    public void attachToLifecycle(SavedStateRegistry registry, Lifecycle lifecycle) {
        if (!this.mIsAttached) {
            this.mIsAttached = true;
            lifecycle.addObserver(this);
            registry.registerSavedStateProvider(this.mKey, this.mHandle.savedStateProvider());
            return;
        }
        throw new IllegalStateException("Already attached to lifecycleOwner");
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.mIsAttached = false;
            source.getLifecycle().removeObserver(this);
        }
    }

    /* access modifiers changed from: package-private */
    public SavedStateHandle getHandle() {
        return this.mHandle;
    }
}

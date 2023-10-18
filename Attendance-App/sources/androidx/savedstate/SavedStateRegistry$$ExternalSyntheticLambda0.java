package androidx.savedstate;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SavedStateRegistry$$ExternalSyntheticLambda0 implements LifecycleEventObserver {
    public final /* synthetic */ SavedStateRegistry f$0;

    public /* synthetic */ SavedStateRegistry$$ExternalSyntheticLambda0(SavedStateRegistry savedStateRegistry) {
        this.f$0 = savedStateRegistry;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        SavedStateRegistry.m1319performAttach$lambda4(this.f$0, lifecycleOwner, event);
    }
}

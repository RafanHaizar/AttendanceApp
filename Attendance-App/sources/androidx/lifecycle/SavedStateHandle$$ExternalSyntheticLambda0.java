package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SavedStateHandle$$ExternalSyntheticLambda0 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ SavedStateHandle f$0;

    public /* synthetic */ SavedStateHandle$$ExternalSyntheticLambda0(SavedStateHandle savedStateHandle) {
        this.f$0 = savedStateHandle;
    }

    public final Bundle saveState() {
        return SavedStateHandle.m1318savedStateProvider$lambda0(this.f$0);
    }
}

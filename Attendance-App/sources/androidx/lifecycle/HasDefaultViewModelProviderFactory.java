package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.tools.r8.annotations.SynthesizedClassV2;

public interface HasDefaultViewModelProviderFactory {
    CreationExtras getDefaultViewModelCreationExtras();

    ViewModelProvider.Factory getDefaultViewModelProviderFactory();

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: androidx.lifecycle.HasDefaultViewModelProviderFactory$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static CreationExtras $default$getDefaultViewModelCreationExtras(HasDefaultViewModelProviderFactory _this) {
            return CreationExtras.Empty.INSTANCE;
        }
    }
}

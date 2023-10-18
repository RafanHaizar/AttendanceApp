package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

@Metadata(mo112d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a*\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0002\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0018H\u0007\u001a\u001f\u0010\u0019\u001a\u00020\u001a\"\f\b\u0000\u0010\u001b*\u00020\u0006*\u00020\t*\u0002H\u001bH\u0007¢\u0006\u0002\u0010\u001c\"\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0006X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00018\u0006X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000\"\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00018\u0006X\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\n\u001a\u00020\u000b*\u00020\u00068@X\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r\"\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\t8@X\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, mo113d2 = {"DEFAULT_ARGS_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "Landroid/os/Bundle;", "SAVED_STATE_KEY", "", "SAVED_STATE_REGISTRY_OWNER_KEY", "Landroidx/savedstate/SavedStateRegistryOwner;", "VIEWMODEL_KEY", "VIEW_MODEL_STORE_OWNER_KEY", "Landroidx/lifecycle/ViewModelStoreOwner;", "savedStateHandlesProvider", "Landroidx/lifecycle/SavedStateHandlesProvider;", "getSavedStateHandlesProvider", "(Landroidx/savedstate/SavedStateRegistryOwner;)Landroidx/lifecycle/SavedStateHandlesProvider;", "savedStateHandlesVM", "Landroidx/lifecycle/SavedStateHandlesVM;", "getSavedStateHandlesVM", "(Landroidx/lifecycle/ViewModelStoreOwner;)Landroidx/lifecycle/SavedStateHandlesVM;", "createSavedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "savedStateRegistryOwner", "viewModelStoreOwner", "key", "defaultArgs", "Landroidx/lifecycle/viewmodel/CreationExtras;", "enableSavedStateHandles", "", "T", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "lifecycle-viewmodel-savedstate_release"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SavedStateHandleSupport.kt */
public final class SavedStateHandleSupport {
    public static final CreationExtras.Key<Bundle> DEFAULT_ARGS_KEY = new SavedStateHandleSupport$DEFAULT_ARGS_KEY$1();
    private static final String SAVED_STATE_KEY = "androidx.lifecycle.internal.SavedStateHandlesProvider";
    public static final CreationExtras.Key<SavedStateRegistryOwner> SAVED_STATE_REGISTRY_OWNER_KEY = new SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1();
    private static final String VIEWMODEL_KEY = "androidx.lifecycle.internal.SavedStateHandlesVM";
    public static final CreationExtras.Key<ViewModelStoreOwner> VIEW_MODEL_STORE_OWNER_KEY = new SavedStateHandleSupport$VIEW_MODEL_STORE_OWNER_KEY$1();

    public static final <T extends SavedStateRegistryOwner & ViewModelStoreOwner> void enableSavedStateHandles(T $this$enableSavedStateHandles) {
        Intrinsics.checkNotNullParameter($this$enableSavedStateHandles, "<this>");
        Lifecycle.State currentState = $this$enableSavedStateHandles.getLifecycle().getCurrentState();
        Intrinsics.checkNotNullExpressionValue(currentState, "lifecycle.currentState");
        if (!(currentState == Lifecycle.State.INITIALIZED || currentState == Lifecycle.State.CREATED)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if ($this$enableSavedStateHandles.getSavedStateRegistry().getSavedStateProvider(SAVED_STATE_KEY) == null) {
            SavedStateHandlesProvider provider = new SavedStateHandlesProvider($this$enableSavedStateHandles.getSavedStateRegistry(), (ViewModelStoreOwner) $this$enableSavedStateHandles);
            $this$enableSavedStateHandles.getSavedStateRegistry().registerSavedStateProvider(SAVED_STATE_KEY, provider);
            $this$enableSavedStateHandles.getLifecycle().addObserver(new SavedStateHandleAttacher(provider));
        }
    }

    private static final SavedStateHandle createSavedStateHandle(SavedStateRegistryOwner savedStateRegistryOwner, ViewModelStoreOwner viewModelStoreOwner, String key, Bundle defaultArgs) {
        SavedStateHandlesProvider provider = getSavedStateHandlesProvider(savedStateRegistryOwner);
        SavedStateHandlesVM viewModel = getSavedStateHandlesVM(viewModelStoreOwner);
        SavedStateHandle savedStateHandle = viewModel.getHandles().get(key);
        if (savedStateHandle != null) {
            return savedStateHandle;
        }
        SavedStateHandle it = SavedStateHandle.Companion.createHandle(provider.consumeRestoredStateForKey(key), defaultArgs);
        viewModel.getHandles().put(key, it);
        return it;
    }

    public static final SavedStateHandle createSavedStateHandle(CreationExtras $this$createSavedStateHandle) {
        Intrinsics.checkNotNullParameter($this$createSavedStateHandle, "<this>");
        SavedStateRegistryOwner savedStateRegistryOwner = (SavedStateRegistryOwner) $this$createSavedStateHandle.get(SAVED_STATE_REGISTRY_OWNER_KEY);
        if (savedStateRegistryOwner != null) {
            ViewModelStoreOwner viewModelStateRegistryOwner = (ViewModelStoreOwner) $this$createSavedStateHandle.get(VIEW_MODEL_STORE_OWNER_KEY);
            if (viewModelStateRegistryOwner != null) {
                Bundle defaultArgs = (Bundle) $this$createSavedStateHandle.get(DEFAULT_ARGS_KEY);
                String key = (String) $this$createSavedStateHandle.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
                if (key != null) {
                    return createSavedStateHandle(savedStateRegistryOwner, viewModelStateRegistryOwner, key, defaultArgs);
                }
                throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_KEY`");
            }
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
        }
        throw new IllegalArgumentException("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
    }

    public static final SavedStateHandlesVM getSavedStateHandlesVM(ViewModelStoreOwner $this$savedStateHandlesVM) {
        Intrinsics.checkNotNullParameter($this$savedStateHandlesVM, "<this>");
        InitializerViewModelFactoryBuilder $this$_get_savedStateHandlesVM__u24lambda_u2d1 = new InitializerViewModelFactoryBuilder();
        $this$_get_savedStateHandlesVM__u24lambda_u2d1.addInitializer(Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class), SavedStateHandleSupport$savedStateHandlesVM$1$1.INSTANCE);
        return (SavedStateHandlesVM) new ViewModelProvider($this$savedStateHandlesVM, $this$_get_savedStateHandlesVM__u24lambda_u2d1.build()).get(VIEWMODEL_KEY, SavedStateHandlesVM.class);
    }

    public static final SavedStateHandlesProvider getSavedStateHandlesProvider(SavedStateRegistryOwner $this$savedStateHandlesProvider) {
        Intrinsics.checkNotNullParameter($this$savedStateHandlesProvider, "<this>");
        SavedStateRegistry.SavedStateProvider savedStateProvider = $this$savedStateHandlesProvider.getSavedStateRegistry().getSavedStateProvider(SAVED_STATE_KEY);
        SavedStateHandlesProvider savedStateHandlesProvider = savedStateProvider instanceof SavedStateHandlesProvider ? (SavedStateHandlesProvider) savedStateProvider : null;
        if (savedStateHandlesProvider != null) {
            return savedStateHandlesProvider;
        }
        throw new IllegalStateException("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
    }
}
